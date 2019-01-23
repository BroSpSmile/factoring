package com.smile.start.mapper;

import com.smile.start.dto.AuthUserInfoDTO;
import com.smile.start.entity.AuthUserInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/6 14:38, UserInfoMapper.java
 * @since 1.8
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface UserInfoMapper {
    AuthUserInfoDTO do2dto(AuthUserInfoDO authUserInfoDO);
    AuthUserInfoDO dto2do(AuthUserInfoDTO authUserInfoDTO);

    List<AuthUserInfoDTO> doList2dtoList(List<AuthUserInfoDO> doList);
}
