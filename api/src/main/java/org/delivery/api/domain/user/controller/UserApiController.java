package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.model.UserResponse;
import org.delivery.api.domain.user.model.UserSessionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController { // 로그인이 된 사용자가 사용할 수 있는 컨트롤러
    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me() { // request header에 사용자 토큰이 들어있기 때문에 별도로 parameter를 받지 않아도 사용자의 정보를 받아 올 수 있다.
        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var response = userBusiness.me(Long.parseLong(userId.toString()));
        return Api.OK(response);
    }
    @GetMapping("/me2")
    public Api<UserResponse> me2(@UserSession UserSessionDto userSessionDto) {
        var response = userBusiness.me2(userSessionDto);
        return Api.OK(response);
    }
}
