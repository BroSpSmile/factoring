/**
 *
 */
package com.smile.start.service.fund;

import java.util.List;

import com.smile.start.model.base.BaseResult;
import com.smile.start.model.enums.FundStatus;
import com.smile.start.model.enums.ProjectItemType;
import com.smile.start.model.fund.FundTarget;
import com.smile.start.model.project.BaseProject;
import com.smile.start.model.project.ProjectItem;

/**
 *
 * @author Administrator
 */
public interface FundItemService {

    /**
     * 保存附件
     * @param item 附件
     * @return
     */
    BaseResult save(ProjectItem item);

    /**
     * 保存附件
     * @param items 附件
     * @return
     */
    BaseResult save(FundStatus status, List<ProjectItem> items);

    /**
     * 刪除附件
     * @param item 附件
     * @return
     */
    BaseResult delete(ProjectItem item);

    /**
     * 根据状态删除附件
     * @param project 项目
     * @param  type 类型
     * @return
     */
    BaseResult delete(BaseProject<FundTarget> project, ProjectItemType type);

    /**
     * 根据附件ID获取附件信息
     * @param id 编号
     * @return
     */
    ProjectItem getById(Long id);

    /**
     * 获取全部附件
     * @param project 项目
     * @return
     */
    List<ProjectItem> getAll(BaseProject<FundTarget> project);

    /**
     * 根据状态获取附件
     * @param project 项目
     * @param type 类型
     * @return
     */
    List<ProjectItem> getItemByType(BaseProject<FundTarget> project, ProjectItemType type);
}
