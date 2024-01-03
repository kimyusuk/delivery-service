package org.delivery.storeadmin.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.Store;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUser;
import org.delivery.storeadmin.common.annotation.Converter;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.model.StoreUserResponse;

@Converter
@RequiredArgsConstructor
public class StoreUserConverter {
    public StoreUser toEntity(StoreUserRegisterRequest storeUserRegisterRequest, Store store) {
        return StoreUser.builder()
                .email(storeUserRegisterRequest.getEmail())
                .password(storeUserRegisterRequest.getPassword())
                .role(storeUserRegisterRequest.getRole())
                .storeId(store.getId()) // todo null 일때 에러 체크 확인 필요.
                .build();
    }

    public StoreUserResponse toResponse(StoreUser storeUser, Store store) {
        return StoreUserResponse.builder()
            .user(
                StoreUserResponse.UserResponse.builder()
                        .id(storeUser.getId())
                        .email(storeUser.getEmail())
                        .status(storeUser.getStatus())
                        .role(storeUser.getRole())
                        .registeredAt(storeUser.getRegisteredAt())
                        .unregisteredAt(storeUser.getUnregisteredAt())
                        .lastLoginAt(storeUser.getLastLoginAt())
                        .build()
            )
            .store(
                StoreUserResponse.StoreResponse.builder()
                        .id(store.getId())
                        .name(store.getName())
                        .build()
            )
            .build();
    }

    public StoreUserResponse toResponse(UserSession userSession) {
        return StoreUserResponse.builder()
            .user(
                StoreUserResponse.UserResponse.builder()
                        .id(userSession.getUserId())
                        .email(userSession.getEmail())
                        .status(userSession.getStatus())
                        .role(userSession.getRole())
                        .registeredAt(userSession.getRegisteredAt())
                        .unregisteredAt(userSession.getUnregisteredAt())
                        .lastLoginAt(userSession.getLastLoginAt())
                        .build()
            )
            .store(
                StoreUserResponse.StoreResponse.builder()
                        .id(userSession.getStoreId())
                        .name(userSession.getStoreName())
                        .build()
            )
            .build();
    }
}
