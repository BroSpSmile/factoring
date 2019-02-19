package com.smile.start.mapper;

import com.smile.start.dto.*;
import com.smile.start.model.contract.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * @author Joseph
 * @version v1.0 2019/2/19 20:55, ContractInfoMapper.java
 * @since 1.8
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface ContractInfoMapper {

    ContractInfoDTO do2dto(ContractInfo contractInfo);
    ContractInfo dto2do(ContractInfoDTO dto);

    ContractSignListDTO do2dto(ContractSignList contractSignList);
    ContractSignList dto2do(ContractSignListDTO dto);
    List<ContractSignList> dtoList2doList(List<ContractSignListDTO> dtoList);

    ContractExtendInfoDTO do2dto(ContractExtendInfo contractExtendInfo);
    ContractExtendInfo dto2do(ContractExtendInfoDTO dto);

    ContractReceivableAgreementDTO do2dto(ContractReceivableAgreement contractReceivableAgreement);
    ContractReceivableAgreement dto2do(ContractReceivableAgreementDTO dto);

    ContractReceivableConfirmationDTO do2dto(ContractReceivableConfirmation contractReceivableConfirmation);
    ContractReceivableConfirmation dto2do(ContractReceivableConfirmationDTO dto);
}
