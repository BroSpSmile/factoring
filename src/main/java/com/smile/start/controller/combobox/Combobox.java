/**
 * jsszvip.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.smile.start.controller.combobox;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.smile.start.controller.BaseController;
import com.smile.start.dao.UserDao;
import com.smile.start.model.auth.User;
import com.smile.start.model.enums.MeetingStatus;
import com.smile.start.model.enums.Progress;

/**
 * 
 * @author smile.jing
 * @version $Id: Combobox.java, v 0.1 Jan 8, 2019 9:17:15 PM smile.jing Exp $
 */
@RestController
@RequestMapping("/combo")
public class Combobox extends BaseController {

    /** userDao */
    @Resource
    private UserDao userDao;

    /**
     * 
     * @return
     */
    @RequestMapping("/progress")
    public List<Item> getProgress() {
        Progress[] enums = Progress.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 
     * @return
     */
    @RequestMapping("/meetingStatus")
    public List<Item> getMeetingStatus() {
        MeetingStatus[] enums = MeetingStatus.values();
        List<Item> items = Lists.newArrayListWithCapacity(enums.length);
        Stream.of(enums).forEach(e -> items.add(new Item(e.getCode(), e.getDesc())));
        return items;
    }

    /**
     * 
     * @return
     */
    @RequestMapping("/users")
    public List<Item> getUsers() {
        List<User> users = userDao.findAll();
        List<Item> items = Lists.newArrayListWithCapacity(users.size());
        users.forEach(e -> items.add(new Item(String.valueOf(e.getId()), e.getUsername())));
        return items;
    }

}
