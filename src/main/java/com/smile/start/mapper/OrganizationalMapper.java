package com.smile.start.mapper;

import com.smile.start.model.dto.OrganizationalDTO;
import com.smile.start.model.auth.Organizational;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/1/29 17:04, OrganizationalMapper.java
 * @since 1.8
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface OrganizationalMapper {

    OrganizationalDTO do2dto(Organizational organizational);
    Organizational dto2do(OrganizationalDTO dto);

    List<OrganizationalDTO> doList2dtoList(List<Organizational> organizationals);
}
