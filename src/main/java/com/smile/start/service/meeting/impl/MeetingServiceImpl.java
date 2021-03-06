/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.service.meeting.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.smile.start.commons.DateUtil;
import com.smile.start.dao.MeetingDao;
import com.smile.start.dao.ProjectMeetingDao;
import com.smile.start.dao.project.ProjectDao;
import com.smile.start.event.MeetingEvent;
import com.smile.start.event.MeetingRemindEvent;
import com.smile.start.model.base.BaseResult;
import com.smile.start.model.base.PageRequest;
import com.smile.start.model.enums.MeetingKind;
import com.smile.start.model.enums.MeetingStatus;
import com.smile.start.model.enums.StepStatus;
import com.smile.start.model.enums.fund.FundStatus;
import com.smile.start.model.enums.project.Progress;
import com.smile.start.model.enums.project.ProjectKind;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.meeting.Meeting;
import com.smile.start.model.meeting.MeetingExt;
import com.smile.start.model.meeting.MeetingSearch;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.Project;
import com.smile.start.model.project.ProjectItem;
import com.smile.start.model.project.ProjectMeeting;
import com.smile.start.service.AbstractService;
import com.smile.start.service.auth.UserInfoService;
import com.smile.start.service.engine.ProcessEngine;
import com.smile.start.service.fund.FundItemService;
import com.smile.start.service.fund.FundService;
import com.smile.start.service.meeting.MeetingService;
import com.smile.start.service.project.ProjectService;

/**
 * 实现
 * @author smile.jing
 * @version $Id: MeetingServiceImpl.java, v 0.1 Jan 17, 2019 7:51:56 PM smile.jing Exp $
 */
@Service
public class MeetingServiceImpl extends AbstractService implements MeetingService {

    /** 会议dao */
    @Resource
    private MeetingDao         meetingDao;

    /** 关联DAO */
    @Resource
    private ProjectMeetingDao  projectMeetingDao;

    /**  */
    @Resource
    private ProjectDao         projectDao;

    /**  */
    @Resource
    private ProjectService     projectService;

    /** 用户服务 */
    @Resource
    private UserInfoService    userInfoService;

    /** 流程引擎 */
    @Resource
    private ProcessEngine      processEngine;

    /** fundService */
    @Resource
    private FundService        fundService;

    /** fundItemService */
    @Resource
    private FundItemService    fundItemService;

    /** 上下文context */
    @Resource
    private ApplicationContext applicationContext;

    /** 
     * @see com.smile.start.service.meeting.MeetingService#search(com.smile.start.model.base.PageRequest)
     */
    @Override
    public PageInfo<MeetingExt> search(PageRequest<MeetingSearch> search) {
        PageHelper.startPage(search.getPageNum(), search.getPageSize(), "id desc");
        List<MeetingExt> meetingExts = meetingDao.findByParam(search.getCondition());
        meetingExts.forEach(ext -> toMeeting(ext));
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<MeetingExt> result = new PageInfo<>(meetingExts);
        return result;
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#getMeetings(com.smile.start.model.meeting.MeetingSearch)
     */
    @Override
    public List<Meeting> getMeetings(MeetingSearch search) {
        search.setStatus(MeetingStatus.END);
        search.setBeginTime(DateUtil.addDays(new Date(), -90));
        List<MeetingExt> meetingExts = meetingDao.findByParam(search);
        List<Meeting> meetings = Lists.newArrayListWithCapacity(meetingExts.size());
        meetingExts.forEach(ext -> meetings.add(ext));
        return meetings;
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#get(java.lang.Long)
     */
    @Override
    public Meeting get(Long id) {
        MeetingExt ext = meetingDao.get(id);
        return toMeeting(ext);
    }

    /**
     * 
     * @param meetingExt
     * @return
     */
    private Meeting toMeeting(MeetingExt meetingExt) {
        meetingExt.setOriginator(userInfoService.getUserById(meetingExt.getOriginator().getId()));
        List<Project> projects = projectDao.findByMeeting(meetingExt);
        meetingExt.setProjects(projects);
        if (StringUtils.isNoneBlank(meetingExt.getParticipantNoList())) {
            for (Long id : getIds(meetingExt.getParticipantNoList())) {
                meetingExt.addParticipant(userInfoService.getUserById(id));
            }
        }
        return meetingExt;
    }

    /**
     * id数组
     * @param idLists
     * @return
     */
    private Long[] getIds(String idLists) {
        String[] idStrings = idLists.split(",");
        Long[] ids = (Long[]) ConvertUtils.convert(idStrings, Long.class);
        return ids;

    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#(com.smile.start.model.meeting.Meeting)
     */
    @Override
    @Transactional
    public BaseResult createMeeting(MeetingExt meeting) {
        meeting.toParticipantNoList();
        long effect = meetingDao.insert(meeting);
        if (effect > 0 && MeetingKind.APPROVAL.equals(meeting.getKind())) {
            for (Project project : meeting.getProjects()) {
                updateProject(project);
                ProjectMeeting pm = new ProjectMeeting();
                pm.setMeetingId(meeting.getId());
                pm.setProjectId(project.getId());
                List<ProjectMeeting> result = projectMeetingDao.find(pm);
                if (result.size() > 0) {
                    throw new RuntimeException("当前项目已关联此会议纪要");
                }
                long peffect = projectMeetingDao.insert(pm);
                if (peffect < 0) {
                    throw new RuntimeException("插入会议纪要失败");
                }
            }
        }
        return new BaseResult();
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#updateMeeting(com.smile.start.model.meeting.MeetingExt)
     */
    @Override
    @Transactional
    public BaseResult updateMeeting(MeetingExt meeting) {
        meeting.toParticipantNoList();
        int effect = meetingDao.update(meeting);
        BaseResult result = new BaseResult();
        if (effect > 0) {
            result.setSuccess(true);
        } else {
            result.setErrorCode("VP00011002");
            result.setErrorMessage("更新会议失败,请重试!");
        }
        return result;
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#saveMinutes(com.smile.start.model.meeting.Meeting)
     */
    @Override
    @Transactional
    public BaseResult saveMinutes(Meeting meeting) {
        int effect = meetingDao.saveMinutes(meeting);
        if (!MeetingKind.APPROVAL.equals(meeting.getKind())) {
            if (effect > 0) {
                for (Project project : meeting.getProjects()) {
                    ProjectMeeting pm = new ProjectMeeting();
                    pm.setMeetingId(meeting.getId());
                    pm.setProjectId(project.getId());
                    List<ProjectMeeting> result = projectMeetingDao.find(pm);
                    if (result.size() > 0) {
                        projectMeetingDao.delete(pm);
                    }
                    long peffect = projectMeetingDao.insert(pm);
                    if (peffect < 0) {
                        throw new RuntimeException("插入会议纪要失败");
                    }
                    project = projectDao.get(project.getId());
                    if (ProjectKind.INVESTMENT.equals(project.getKind())) {
                        applicationContext.publishEvent(new MeetingEvent(this, project));
                    }
                }
            }
        } else {
            Project project = meeting.getProjects().get(0);
            List<ProjectItem> items = project.getItems();
            project = projectService.getProject(project.getId());
            project.setItems(items);
            if (Progress.INITIATE_REJECT.equals(project.getProgress())) {
                if (ProjectKind.FACTORING.equals(project.getKind())) {
                    project.setStep(-2);
                    projectService.turnover(project);
                } else if (ProjectKind.INVESTMENT.equals(project.getKind())) {
                    doInvestment(project, FundStatus.STOP);
                }
            } else {
                if (ProjectKind.FACTORING.equals(project.getKind())) {
                    project.setProgress(Progress.APPROVAL);
                    project.setStep(0);
                    processEngine.next(project, false);
                } else if (ProjectKind.INVESTMENT.equals(project.getKind())) {
                    doInvestment(project, FundStatus.DEEP_TUNING);
                }

            }
        }
        return new BaseResult();
    }

    /** 
     * @see
     */
    @Override
    public BaseResult relationMeeting(List<ProjectMeeting> pms) {
        for (ProjectMeeting pm : pms) {
            List<ProjectMeeting> result = projectMeetingDao.find(pm);
            if (result.size() > 0) {
                throw new RuntimeException("当前项目已关联此会议纪要");
            }
            projectMeetingDao.insert(pm);
        }
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        return result;
    }

    /** 
     * @see com.smile.start.service.meeting.MeetingService#schedule()
     */
    @Override
    public BaseResult schedule() {
        MeetingSearch search = new MeetingSearch();
        search.setBeginTime(new Date());
        List<MeetingExt> meetings = meetingDao.findNotEnd(search);
        meetings.stream().forEach(meeting -> update(meeting));
        return new BaseResult();
    }

    /**
     * 更新会议状态
     * @param meeting
     */
    private void update(MeetingExt meeting) {
        if (MeetingStatus.PLAN.equals(meeting.getStatus())) {
            meeting.setStatus(MeetingStatus.MEETING);
            meetingDao.update(meeting);
        } else if (MeetingStatus.MEETING.equals(meeting.getStatus())) {
            if (DateUtil.isBeforeNow(meeting.getEndTime())) {
                meeting.setStatus(MeetingStatus.END);
                meetingDao.update(meeting);
            }
        }
        if (DateUtil.isBeforeNow(DateUtil.addMinutes(meeting.getBeginTime(), -1 * meeting.getRemind()))) {
            applicationContext.publishEvent(new MeetingRemindEvent(this, meeting));
        }
    }

    /**
     * 处理项目信息
     * 
     * @param project
     */
    private void updateProject(Project project) {
        project = projectService.getProject(project.getId());
        switch (project.getKind()) {
            case FACTORING:
                doFactoring(project);
                break;
            case INVESTMENT:
                doInvestment(project, FundStatus.APPROVAL);
                break;
            default:
                break;
        }
    }

    /**
     * 保理处理逻辑
     * 
     * @param project
     */
    private void doFactoring(Project project) {
        project.setProgress(Progress.INITIATE);
        processEngine.changeStatus(project, StepStatus.PROCESSING);
    }

    /**
     * 直投处理逻辑
     * @param project
     */
    private void doInvestment(Project project, FundStatus status) {
        BaseProject<FundTarget> fund = new BaseProject<FundTarget>();
        fund.setId(project.getId());
        FundTarget target = new FundTarget();
        target.setProjectStep(status);
        fund.setDetail(target);
        fundService.modifyTarget(fund);
        if (!CollectionUtils.isEmpty(project.getItems())) {
            fundItemService.save(status, project.getItems());
        }
    }
}
