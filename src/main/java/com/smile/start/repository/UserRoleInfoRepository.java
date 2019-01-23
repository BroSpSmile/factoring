package com.smile.start.repository;

import com.smile.start.entity.AuthUserRoleInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 15:46, UserRoleInfoRepository.java
 * @since 1.8
 */
public interface UserRoleInfoRepository extends JpaRepository<AuthUserRoleInfoDO, Long> {

}
