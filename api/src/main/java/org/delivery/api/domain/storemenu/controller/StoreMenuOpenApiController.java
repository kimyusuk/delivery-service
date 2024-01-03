package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/open-api/store-menu")
@RequiredArgsConstructor
public class StoreMenuOpenApiController {
    private final StoreMenuBusiness storeMenuBusiness;

    @PostMapping("/register")
    public Api<StoreMenuResponse> register(@Valid @RequestBody Api<StoreMenuRegisterRequest> storeMenuRegisterRequest) { // 클라이언트로부터 완전히 새로운 데이터를 받아줘야하니까. 리퀘스트 바디다.
        var register =  storeMenuBusiness.register(storeMenuRegisterRequest.getBody());
        return Api.OK(register);
    }
}
