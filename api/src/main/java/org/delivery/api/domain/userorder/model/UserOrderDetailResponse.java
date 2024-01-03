package org.delivery.api.domain.userorder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.domain.store.model.StoreResponse;
import org.delivery.api.domain.storemenu.model.StoreMenuResponse;
import org.delivery.db.storemenu.StoreMenu;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDetailResponse {
    private UserOrderResponse userOrderResponse; // 건당 주문이 내려지고.
    private StoreResponse storeResponse; // 가게 정보가 내려지고.
    private List<StoreMenuResponse> storeMenuResponseList; // 가게의 메뉴가 묶음 형식으로 정보가 내려진다.

}
