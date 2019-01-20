package com.smile.start.mapper;

import com.smile.start.dto.AuthRoleInfoDTO;
import com.smile.start.entity.AuthRoleInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:47, RoleInfoMapper.java
 * @since 1.8
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface RoleInfoMapper {
    AuthRoleInfoDTO do2dto(AuthRoleInfoDO authRoleInfoDO);
    AuthRoleInfoDO dto2do(AuthRoleInfoDTO authRoleInfoDTO);

    List<AuthRoleInfoDTO> doList2dtoList(List<AuthRoleInfoDO> authRoleInfoDOList);
}
