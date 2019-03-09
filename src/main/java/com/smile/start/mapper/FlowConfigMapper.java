package com.smile.start.mapper;

import com.smile.start.dto.FlowConfigDTO;
import com.smile.start.dto.FlowStatusDTO;
import com.smile.start.model.common.FlowConfig;
import com.smile.start.model.common.FlowStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/22 13:17, FlowConfigMapper.java
 * @since 1.8
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface FlowConfigMapper {

    @Mapping(source = "gmtCreate", target = "gmtCreate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    FlowConfig dto2do(FlowConfigDTO dto);
    @Mapping(source = "gmtCreate", target = "gmtCreate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    FlowConfigDTO do2dto(FlowConfig flowConfig);
    List<FlowConfigDTO> doList2dtoListConfig(List<FlowConfig> doList);

    FlowStatus dto2do(FlowStatusDTO dto);
    FlowStatusDTO do2dto(FlowStatus flowStatus);
    List<FlowStatusDTO> doList2dtoListStatus(List<FlowStatus> doList);
}
