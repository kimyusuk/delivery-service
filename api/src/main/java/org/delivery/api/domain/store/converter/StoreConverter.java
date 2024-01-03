package org.delivery.api.domain.store.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.model.StoreRegisterRequest;
import org.delivery.api.domain.store.model.StoreResponse;
import org.delivery.db.store.Store;
import org.delivery.db.store.enums.StoreStatus;

import java.util.Optional;

@Converter
public class StoreConverter {
    public Store toEntity(StoreRegisterRequest storeRegisterRequest) {
        return Optional.ofNullable(storeRegisterRequest)
                .map(it-> Store.builder()
                            .name(storeRegisterRequest.getName())
                            .address(storeRegisterRequest.getAddress())
                            .status(StoreStatus.REGISTERED)
                            .category(storeRegisterRequest.getStoreCategory())
                            .thumbnailUrl(storeRegisterRequest.getThumbnailUrl())
                            .minimumAmount(storeRegisterRequest.getMinimumAmount())
                            .minimumDeliveryAmount(storeRegisterRequest.getMinimumDeliveryAmount())
                            .phoneNumber(storeRegisterRequest.getPhoneNumber())
                            .build())
        .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "storeRegisterRequest Null"));
    }
    public StoreResponse toResponse(Store store) {
        return Optional.ofNullable(store)
                .map(it -> StoreResponse
                        .builder()
                        .id(store.getId())
                        .name(store.getName())
                        .address(store.getAddress())
                        .status(store.getStatus())
                        .storeCategory(store.getCategory())
                        .star(store.getStar())
                        .thumbnailUrl(store.getThumbnailUrl())
                        .minimumAmount(store.getMinimumAmount())
                        .minimumDeliveryAmount(store.getMinimumDeliveryAmount())
                        .phoneNumber(store.getPhoneNumber())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "StoreEntity is Null"));
    }
}
