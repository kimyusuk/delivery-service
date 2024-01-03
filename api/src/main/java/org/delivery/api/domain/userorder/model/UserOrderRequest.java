package org.delivery.api.domain.userorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderRequest {
    // 주문 = 특정 사용자가, 특정 메뉴를 주문
    // 특정 사용자 = 로그인된 세션에 들어있는 사용자.
    // 주문 할때는 메뉴 id만 올려주면 되는 것.
    @NotNull
    private List<Long> storeMenuIdList;

}
