package com.smile.start.mapper;

import com.smile.start.model.dto.BankInfoDTO;
import com.smile.start.model.common.BankInfo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/3/9 14:32, BankInfoMapper.java
 * @since 1.8
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface BankInfoMapper {

    BankInfoDTO do2dto(BankInfo bankInfo);
    BankInfo dto2do(BankInfoDTO dto);
    List<BankInfoDTO> doList2dtoList(List<BankInfo> doList);
}
