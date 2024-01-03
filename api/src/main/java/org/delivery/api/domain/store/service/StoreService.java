package org.delivery.api.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.Store;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    // 스토어 등록.
    public Store register(Store store) {
        return Optional.ofNullable(store)
                .map(it -> {
                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);
                    return storeRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 아이디와 상태로 유효한 가게인지 확인하는 메소드.
    public Store getStoreStrategyIdStatus(Long id) { // return을 써서 한줄로 작성하는 것의 단점은 debugging이 어렵다. 따라서 풀어 쓰는 것이 좋다.
        var storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        return storeEntity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 스토어의 카테고리와 상태로 전체 가게 리스트 반환.
    public List<Store> getStoreListStrategyCategory(StoreCategory storeCategory) {
        var storeList = storeRepository.findAllByStatusAndCategoryOrderByStarDesc(StoreStatus.REGISTERED, storeCategory);
        return storeList;
    }

    // 스토어의 상태로 전체 가게 리시트 반환
    public List<Store> getStoreListStrategyStatus() {
        var storeList = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
        return storeList;
    }


}
