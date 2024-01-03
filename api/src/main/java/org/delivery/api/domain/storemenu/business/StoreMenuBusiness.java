package org.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.service.StoreMenuService;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    public StoreMenuResponse register(StoreMenuRegisterRequest storeMenuRegisterRequest) {
        var entity = storeMenuConverter.toEntity(storeMenuRegisterRequest);
        var savedEntity = storeMenuService.register(entity);
        var response = storeMenuConverter.toResponse(savedEntity);
        return response;
    }

    public List<StoreMenuResponse> getAllStoreMenuStrategyStoreIdStatus(Long storeId) {
        return storeMenuService.getAllStoreMenuStrategyStoreIdStatus(storeId)
                .stream()
                .map(it->storeMenuConverter.toResponse(it))
                .collect(Collectors.toList());
    }

}
