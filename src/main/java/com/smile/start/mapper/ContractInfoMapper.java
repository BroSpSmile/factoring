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

    ContractBaseInfoDTO do2dto(ContractInfo contractInfo);
    ContractInfo dto2do(ContractBaseInfoDTO dto);
    List<ContractBaseInfoDTO> doList2dtoListBase(List<ContractInfo> doList);
    List<ContractInfo> dtoList2doListBase(List<ContractBaseInfoDTO> dtoList);

    ContractSignListDTO do2dto(ContractSignList contractSignList);
    ContractSignList dto2do(ContractSignListDTO dto);
    List<ContractSignList> dtoList2doListSign(List<ContractSignListDTO> dtoList);
    List<ContractSignListDTO> doList2dtoListSign(List<ContractSignList> doList);

    ContractExtendInfoDTO do2dto(ContractExtendInfo contractExtendInfo);
    ContractExtendInfo dto2do(ContractExtendInfoDTO dto);

    ContractReceivableAgreementDTO do2dto(ContractReceivableAgreement contractReceivableAgreement);
    ContractReceivableAgreement dto2do(ContractReceivableAgreementDTO dto);

    ContractReceivableConfirmationDTO do2dto(ContractReceivableConfirmation contractReceivableConfirmation);
    ContractReceivableConfirmation dto2do(ContractReceivableConfirmationDTO dto);

    ContractAttach dto2do(ContractAttachDTO dto);
    ContractAttachDTO do2dto(ContractAttach contractAttach);
    List<ContractAttachDTO> doList2dtoListAttach(List<ContractAttach> doList);

    ContractAuditRecordDTO do2dto(ContractAuditRecord contractAuditRecord);
    List<ContractAuditRecordDTO> doList2dtoListAuditRecord(List<ContractAuditRecord> doList);
}
