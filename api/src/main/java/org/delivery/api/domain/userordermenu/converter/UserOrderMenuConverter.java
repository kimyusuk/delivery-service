package org.delivery.api.domain.userordermenu.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.db.storemenu.StoreMenu;
import org.delivery.db.userorder.UserOrder;
import org.delivery.db.userordermenu.UserOrderMenu;

@Converter
public class UserOrderMenuConverter {
    public UserOrderMenu toEntity(
            UserOrder userOrder,
            StoreMenu storeMenu
    ) {
        return UserOrderMenu.builder()
                .userOrderId(userOrder.getId())
                .storeMenuId(storeMenu.getId())
                .build();
    }
}
