package org.delivery.storeadmin.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.common.annotation.Business;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.service.StoreUserService;

import javax.validation.Valid;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {
    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository; // todo service로 변경해주기.

    public StoreUserResponse register(StoreUserRegisterRequest storeUserRegisterRequest) {
        var store = storeRepository.findFirstByNameAndStatusOrderByIdDesc(storeUserRegisterRequest.getName(), StoreStatus.REGISTERED);
        var storeUser = storeUserConverter.toEntity(storeUserRegisterRequest, store.get());
        var savedStoreUser= storeUserService.register(storeUser);
        var response = storeUserConverter.toResponse(savedStoreUser, store.get());
        return response;
    }
}
