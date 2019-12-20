package com.smile.start.mapper;

import com.smile.start.model.dto.SignListTemplateDTO;
import com.smile.start.model.common.SignListTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/13 20:50, SignListTemplateMapper.java
 * @since 1.8
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface SignListTemplateMapper {
    SignListTemplateDTO do2dto(SignListTemplate signListTemplate);
    SignListTemplate dto2do(SignListTemplateDTO signListTemplateDTO);

    List<SignListTemplateDTO> doList2dtoList(List<SignListTemplate> doList);
}
