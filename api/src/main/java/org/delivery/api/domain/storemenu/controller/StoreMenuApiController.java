package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/store-menu")
@RequiredArgsConstructor
public class StoreMenuApiController { // 메뉴를 보여주는데 로그인 된 상태에서만 보여준다잉. ㅇㅋ?
    private final StoreMenuBusiness storeMenuBusiness;

    @GetMapping("/search")
    public Api<List<StoreMenuResponse>> getAllMenuOfOneStore(@RequestParam Long storeId) { // 이것도 쿼리파라미터로 맵핑 시켜줘야한다.
        var menuList = storeMenuBusiness.getAllStoreMenuStrategyStoreIdStatus(storeId);
        return Api.OK(menuList);
    }

}
