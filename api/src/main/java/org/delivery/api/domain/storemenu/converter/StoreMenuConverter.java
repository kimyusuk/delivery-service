package org.delivery.api.domain.storemenu.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.storemenu.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.model.StoreMenuResponse;
import org.delivery.db.storemenu.StoreMenu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class StoreMenuConverter {
    public StoreMenu toEntity(StoreMenuRegisterRequest storeMenuRegisterRequest) {
        return Optional.ofNullable(storeMenuRegisterRequest)
                .map(it -> { // 빌드 가능 한수는 storeMenuRegisterRequest가 갖고 있는 필드값 만큼만 빌드 할수 있겠죠.
                    return StoreMenu.builder()
                            .storeId(it.getStoreId())
                            .name(it.getName())
                            .amount(it.getAmount())
                            .thumbnailUrl(it.getThumbnailUrl())
                            .build();
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreMenuResponse toResponse(StoreMenu storeMenu) {
        return Optional
                .ofNullable(storeMenu)
                .map(it -> {
                    return StoreMenuResponse.builder()
                            .id(storeMenu.getId()) // 희안 하게 전부다 it으로 가져와지더만, 얘만 it으로 가져와지질 않네?
                            .storeId(it.getStoreId())
                            .name(it.getName())
                            .amount(it.getAmount())
                            .thumbnailUrl(it.getThumbnailUrl())
                            .storeMenuStatus(it.getStatus())
                            .likeCount(it.getLikeCount())
                            .sequence(it.getSequence())
                            .build();
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenu> storeMenuList) {
        return storeMenuList.stream()
                .map(it -> {
                    return toResponse(it);
                }).collect(Collectors.toList());
    }
}
