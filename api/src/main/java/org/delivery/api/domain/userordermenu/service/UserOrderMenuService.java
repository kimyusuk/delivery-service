package org.delivery.api.domain.userordermenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userordermenu.UserOrderMenu;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderMenuService {
    private final UserOrderMenuRepository userOrderMenuRepository;
    // userOrderMenu를 id로 storeId, userOrderMenuId 등을 가져 오지 않고,
    // user가 주문을 할 시에 => orderMenu가 동작하게 되고, 이때 orderMenu가 동작할때 자동으로 userOrderMenu가 딸려와야한다.
    // 즉 주문내역 안에는: 주문 기능과 + 메뉴 정보가 담겨 있기 때문에 주문 아이디를 받아서 메뉴 값을 return 시켜 주면 된다.

    public List<UserOrderMenu> getUserOrderMenu(Long userOrderId) {
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
    }

    public UserOrderMenu order(UserOrderMenu userOrderMenu) {
        return Optional.ofNullable(userOrderMenu)
                .map(it -> {
                    it.setStatus(UserOrderMenuStatus.REGISTERED);
                    return userOrderMenuRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

}
