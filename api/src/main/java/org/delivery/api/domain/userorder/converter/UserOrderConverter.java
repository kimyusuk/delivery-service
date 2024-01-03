package org.delivery.api.domain.userorder.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.domain.user.model.UserSessionDto;
import org.delivery.api.domain.userorder.model.UserOrderResponse;
import org.delivery.db.storemenu.StoreMenu;
import org.delivery.db.userorder.UserOrder;

import java.math.BigDecimal;
import java.util.List;

@Converter
public class UserOrderConverter {
    public UserOrder toEntity(
            UserSessionDto userSessionDto,
            List<StoreMenu> storeMenuList
            ) {
        var totalAmount = storeMenuList
                .stream()
                .map(it -> it.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        for (StoreMenu it : storeMenuList) {
//            BigDecimal amount = it.getAmount();
//            totalAmount = totalAmount.add(amount);
//        }
        return UserOrder.builder()
                .userId(userSessionDto.getId())
                .amount(totalAmount)
                .build();
    }

    public UserOrderResponse toResponse(UserOrder userOrder) {
        return UserOrderResponse.builder()
                .id(userOrder.getId())
                .status(userOrder.getStatus())
                .amount(userOrder.getAmount())
                .orderedAt(userOrder.getOrderedAt())
                .acceptedAt(userOrder.getAcceptedAt())
                .cookingStartedAt(userOrder.getCookingStartedAt())
                .deliveryStartedAt(userOrder.getDeliveryStartedAt())
                .receivedAt(userOrder.getReceivedAt())
                .build();
    }

}
