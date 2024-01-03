package org.delivery.api.domain.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.model.StoreRegisterRequest;
import org.delivery.api.domain.store.model.StoreResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/open-api/store")
@RequiredArgsConstructor
public class StoreOpenApiController {
    private final StoreBusiness storeBusiness;

    @PostMapping("/register")
    public Api<StoreResponse> register(
            @Valid @RequestBody
            Api<StoreRegisterRequest> storeRegisterRequest
    ) {
        var response = storeBusiness.register(storeRegisterRequest.getBody());
        return Api.OK(response);
    }
}
