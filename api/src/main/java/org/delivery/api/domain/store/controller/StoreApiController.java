package org.delivery.api.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.model.StoreResponse;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreApiController {
    private final StoreBusiness storeBusiness;
    @GetMapping("/search") // getMapping인 이유는 리퀘스트 파람이니까 = 필드 값을 통해서 주소를 통해 읽어오면 되니까. 사용자가 건들수 없어야해. 이게 핵심이야.
    public Api<StoreResponse> storeSearch(
            @RequestParam(required = false) // required = false => 필수 값이 아닐때도 값이 들어올 수 있도록 만들어준다. Request파람은 = 이미 만들어진 필드 값을 통해서 사용자로부터 요청을 받는것.
            StoreCategory storeCategory
    ) {
        var response = storeBusiness.getStoreListStrategyCategory(storeCategory);
        return Api.OK(response);

    }
}
