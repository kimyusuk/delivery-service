package org.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.model.UserRegisterRequest;
import org.delivery.api.domain.user.model.UserResponse;
import org.delivery.db.user.User;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserConverter {
    // Optional.ofNullable(request) 요청받은 request 값이 null 값일 수도 있다 라고 쓰는 명령어들이 이것이다.
    public User toEntity(UserRegisterRequest request) { // 값이 있는 경우에는 map으로 전환 시켜서 온전한 데이터들을 빼준다.
        return Optional.ofNullable(request)
                .map(it -> User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .address(request.getAddress())
                .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null")); // null 값일 경우 Exception 우리가 만든 것으로 터트린다.
    }

    public UserResponse toResponse(User user) {
        return Optional.ofNullable(user)
                .map(it -> UserResponse
                        .builder()
                        .id(user.getId())
                        .name(user.getName())
                        .status(user.getStatus())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .registeredAt(user.getRegisteredAt())
                        .unregisteredAt(user.getUnregisteredAt())
                        .lastLoginAt(user.getLastLoginAt())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity is Null"));
    }
}
