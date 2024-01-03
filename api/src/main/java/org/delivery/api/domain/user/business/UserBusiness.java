package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.model.TokenResponse;
import org.delivery.api.domain.user.model.UserLoginRequest;
import org.delivery.api.domain.user.model.UserRegisterRequest;
import org.delivery.api.domain.user.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.UserSessionDto;
import org.delivery.api.domain.user.service.UserService;

@Business
@RequiredArgsConstructor
public class UserBusiness {
    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    /*
      1. UserRegisterRequest -> to entity = entity.
      2. entity -> save = entity.
      3. save -> toUserResponse = UserResponse.
      4. return -> response = UserResponse.
    * */
    public UserResponse register(UserRegisterRequest request) {
        var entity = userConverter.toEntity(request);
        var savedEntity = userService.register(entity); // save 영역
        var response = userConverter.toResponse(savedEntity);
        return response;
//        이 아래 코드는 윗 코드와 같은 코드다
//        return Optional.ofNullable(request)
//                .map(userConverter::toEntity)
//                .map(userService::register) // save 영역
//                .map(userConverter::toResponse)
//                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request null"));
    }
    /*
         1. email, password를 가지고 체크.
         2. 유요한 사용자가 있는지 체크.
         3. 사용자가 있다면, token 생성. token을 줘야한다.
         4. token => response로 내려줘야 한다.
       * */
    public UserResponse loginZero(UserLoginRequest request) {
        var userEntity = userService.login(request.getEmail(), request.getPassword());
        // 사용자가 없으면 throw
        // TODO 사용자가 있으면 토큰 생성
        return userConverter.toResponse(userEntity);
    }
    public TokenResponse login(UserLoginRequest request) {
        var userEntity = userService.login(request.getEmail(), request.getPassword());
        // 사용자가 없으면 throw

        // TODO 사용자가 있으면 토큰 생성
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }

    /*
      1. 로그인이 됐을 때 자기 자신을 반환 시켜주는 메소드.
    * */
    public UserResponse me(Long userId) {
        var userEntity = userService.getUserStrategyId(userId);
        var response = userConverter.toResponse(userEntity);
        return response;
    }

    public UserResponse me2(UserSessionDto userSessionDto) {
        var userEntity = userService.getUserStrategyId(userSessionDto.getId());
        var response = userConverter.toResponse(userEntity);
        return response;
    }

}
