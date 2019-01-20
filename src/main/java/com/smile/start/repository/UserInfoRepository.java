package com.smile.start.repository;

import com.smile.start.entity.AuthUserInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:40, UserInfoRepository.java
 * @since 1.8
 */
public interface UserInfoRepository extends JpaRepository<AuthUserInfoDO, Long> {
}
