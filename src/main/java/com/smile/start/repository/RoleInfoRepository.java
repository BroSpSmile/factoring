package com.smile.start.repository;

import com.smile.start.entity.AuthRoleInfoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:40, RoleInfoRepository.java
 * @since 1.8
 */
public interface RoleInfoRepository extends JpaRepository<AuthRoleInfoDO, Long> {

    @Query(value = "select r from AuthRoleInfoDO r,AuthUserRoleInfoDO ur " +
            "where r.serialNo = ur.roleSerialNo" +
            " and ur.userSerialNo = ?1")
    List<AuthRoleInfoDO> findByUserSerialNo(String userSerialNo);

    AuthRoleInfoDO findByRoleCode(String roleCode);
}
