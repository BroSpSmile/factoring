package com.smile.start.repository;

import com.smile.start.entity.AuthRolePermissionInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 15:47, RolePermissionInfoRepository.java
 * @since 1.8
 */
public interface RolePermissionInfoRepository extends JpaRepository<AuthRolePermissionInfoDO, Long> {
}
