package com.smile.start.mapper;

import com.smile.start.dto.AuthPermissionInfoDTO;
import com.smile.start.model.auth.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:47, PermissionInfoMapper.java
 * @since 1.8
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface PermissionInfoMapper {
    AuthPermissionInfoDTO do2dto(Permission permission);
    Permission dto2do(AuthPermissionInfoDTO permissionInfoDTO);

    List<AuthPermissionInfoDTO> doList2dtoList(List<Permission> doList);
}
