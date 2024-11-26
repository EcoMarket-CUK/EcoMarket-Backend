package com.api.jaebichuri.shipping.service;

import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.shipping.dto.shippingDetailsDto;
import com.api.jaebichuri.shipping.dto.ShippingStatusCountDto;
import com.api.jaebichuri.shipping.entity.Shipping;
import com.api.jaebichuri.shipping.enums.ShippingStatus;
import com.api.jaebichuri.shipping.mapper.ShippingMapper;
import com.api.jaebichuri.shipping.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final ShippingMapper shippingMapper;

    @Transactional(readOnly = true)
    public ShippingStatusCountDto getShippingStatusCount(Member member) {
        int paymentCount = shippingRepository.countByShippingStatusAndAuction_Seller(ShippingStatus.PAYMENT_CONFIRMED, member);
        int preparingCount = shippingRepository.countByShippingStatusAndAuction_Seller(ShippingStatus.SHIPPING_PREPARING, member);
        int shippingCount = shippingRepository.countByShippingStatusAndAuction_Seller(ShippingStatus.SHIPPING, member);
        int deliveredCount = shippingRepository.countByShippingStatusAndAuction_Seller(ShippingStatus.DELIVERED, member);

        return new ShippingStatusCountDto(paymentCount, preparingCount, shippingCount, deliveredCount);
    }

    @Transactional(readOnly = true)
    public List<shippingDetailsDto> getShippingDetails(Member member) {
        List<Shipping> shipping = shippingRepository.findByAuction_Seller(member);

        return shippingMapper.toShippingDetailsDtoList(shipping);
    }

}
