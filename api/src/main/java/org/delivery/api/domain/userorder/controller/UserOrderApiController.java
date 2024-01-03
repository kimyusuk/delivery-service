package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.UserSessionDto;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.model.UserOrderRequest;
import org.delivery.api.domain.userorder.model.UserOrderResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user-order")
@RequiredArgsConstructor
public class UserOrderApiController {
    private final UserOrderBusiness userOrderBusiness;

    @PostMapping("")
    public Api<UserOrderResponse> userOrder(
            @Valid @RequestBody Api<UserOrderRequest> userOrderRequest,
            @Parameter(hidden = true) @UserSession UserSessionDto userSessionDto // jsonBody로 인식돼서 객체 기입란이 나온다. 따라서 없애주는 양식은 @Parameter swagger 거를 이용해서 hidden 값에 true를 주면 된다.
    ) {
        var response = userOrderBusiness.userOrder(userSessionDto, userOrderRequest.getBody());
        return Api.OK(response);
    }

    // 현재 진행중인 주문건.
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(
            @Parameter(hidden = true) @UserSession UserSessionDto userSessionDto
    ) {
        var response = userOrderBusiness.current(userSessionDto);
        return Api.OK(response);
    }

    // 과거 주문 내역.
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(
            @Parameter(hidden = true) @UserSession UserSessionDto userSessionDto
    ) {
        var response = userOrderBusiness.history(userSessionDto);
        return Api.OK(response);
    }

    // 주문 한건에 대한 내역.
    @GetMapping("/id/{orderId}")
    public Api<List<UserOrderDetailResponse>> read(
            @Parameter(hidden = true) @UserSession UserSessionDto userSessionDto,
            @PathVariable Long orderId
    ) {
        var response = userOrderBusiness.read(userSessionDto, orderId);
        return Api.OK(response);
    }
}
