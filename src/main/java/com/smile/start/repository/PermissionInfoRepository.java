package com.smile.start.repository;

import com.smile.start.entity.AuthPermissionInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:40, PermissionInfoRepository.java
 * @since 1.8
 */
public interface PermissionInfoRepository extends JpaRepository<AuthPermissionInfoDO, Long> {
    @Query(value = "select p from AuthPermissionInfoDO p, " +
            "AuthRolePermissionInfoDO rp, " +
            "AuthRoleInfoDO r," +
            "AuthUserRoleInfoDO ur" +
            " where ur.roleSerialNo = r.serialNo" +
            " and r.serialNo = rp.roleSerialNo" +
            " and rp.permissionSerialNo = p.serialNo" +
            " and ur.userSerialNo = ?1")
    List<AuthPermissionInfoDO> findByUserSerialNo(String userSerialNo);
}
