package com.smile.start.service.entrustauth;

import com.smile.start.model.enums.Step;
import com.smile.start.model.project.EntrustAuth;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:40, UserInfoService.java
 * @since 1.8
 */
public interface EntrustAuthService {

    Long insert(EntrustAuth entrustAuth);

    EntrustAuth query(Long projectId);

    int update(EntrustAuth entrustAuth);

    void delete(Long projectId);

    List<Long> getEntrustAuthProjectIds(Long userId, Step type);
}
