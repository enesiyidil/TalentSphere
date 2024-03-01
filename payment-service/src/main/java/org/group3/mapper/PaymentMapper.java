package org.group3.mapper;

import org.group3.dto.request.PaymentRequestDto;
import org.group3.dto.response.PaymentFindAllInfoResponseDto;
import org.group3.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment requestDtoToPayment(PaymentRequestDto dto);

    PaymentFindAllInfoResponseDto paymentToPaymentFindAllInfoResponseDto(Payment payment);

}
