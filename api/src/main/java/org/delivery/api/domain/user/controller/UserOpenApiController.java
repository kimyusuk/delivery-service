package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.token.model.TokenResponse;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.model.UserLoginRequest;
import org.delivery.api.domain.user.model.UserRegisterRequest;
import org.delivery.api.domain.user.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/open-api/user")
@RequiredArgsConstructor
public class UserOpenApiController { // 사용자 가입창 webconfig 보면 interceptor 발동 안되게 exclude를 해놓은 상태다.
    private final UserBusiness userBusiness;

    // 사용자 가입 요청
    @PostMapping("/register")
    public Api<UserResponse> register(@Valid @RequestBody Api<UserRegisterRequest> request) {
        var response = userBusiness.register(request.getBody());
        return Api.OK(response);
    }

    // 사용자 로그인
    @PostMapping("/loginzero")
    public Api<UserResponse> loginZero(@Valid @RequestBody Api<UserLoginRequest> request) {
        var response = userBusiness.login(request.getBody());
        return Api.OK(response);
    }
    @PostMapping("/login")
    public Api<TokenResponse> login(@Valid @RequestBody Api<UserLoginRequest> request) {
        var response = userBusiness.login(request.getBody());
        return Api.OK(response);
/*      결과 값은 이런식으로 나온다.
        {
          "result": {
            "result_code": 200,
            "result_message": "성공",
            "result_description": "성공"
          },
          "body": {
            "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImV4cCI6MTcwMDc1MDQwOX0.-BNRP2zxYonItOpRw78y-5eGq5ljHuscYo6lhLqEb34", = 1시간짜리 즉 secreteCode + data(userId) + 만료시간
            "access_token_expired_at": "2023-11-23T23:40:09.9750968", = 우리가 직접적으로 볼수 있는 만료시간. 1시간 짜리.
            "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImV4cCI6MTcwMDc5MDAwOX0.jTU9GYowQz4xSo-qrwkA9VNWU6z48ejXval2UBTvEoE", = 12시간짜리
            "refresh_token_expired_at": "2023-11-24T10:40:09.9961226" = 우리가 직접적으로 볼수 있는 만료시간. 12시간 짜리.
          }
        }
*/
    }
}
