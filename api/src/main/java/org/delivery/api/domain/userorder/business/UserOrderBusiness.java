package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.UserSessionDto;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.model.UserOrderRequest;
import org.delivery.api.domain.userorder.model.UserOrderResponse;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {
    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;

    // 사용자 주문 (RequestModel 필요)
    // 1. 사용자, 메뉴id
    // 2. userOrder 생성
    // 3. userOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(UserSessionDto userSessionDto, UserOrderRequest userOrderRequest) {
        var storeMenuEntityList = userOrderRequest.getStoreMenuIdList()
                .stream()
                .map(it -> storeMenuService.getStoreMenuStrategyIdStatus(it))
                .collect(Collectors.toList());
        var userOrderEntity = userOrderConverter.toEntity(userSessionDto, storeMenuEntityList);

        // 주문
        var savedUserOrderEntity = userOrderService.order(userOrderEntity);

        // 맵핑
        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> {
                    // menu + userOrder
                    var userOrderMenuEntity = userOrderMenuConverter.toEntity(savedUserOrderEntity, it);
                    return userOrderMenuEntity;
                })
                .collect(Collectors.toList());

        // 비동기로 가맹점에 주문 알리기. => 비동기로 사용자의 주문이 날아가게 된다.
        userOrderProducer.sendOrder(savedUserOrderEntity);

        // 주문 내역 기록 남기기.
        userOrderMenuEntityList.forEach(it->{
            userOrderMenuService.order(it);
        });

        return userOrderConverter.toResponse(savedUserOrderEntity);

    }


    public List<UserOrderDetailResponse> current(UserSessionDto userSessionDto) { // N건을 가져오느냐
        var userOrderEntityList = userOrderService.currentOrder(userSessionDto.getId());
        // 사용자가 주문한 메뉴
        var userOrderDetailResponsesList = userOrderEntityList.stream()
                .map(it -> {
                    var userOrderMenuList = userOrderMenuService.getUserOrderMenu(it.getId());
                    var storeMenuList = userOrderMenuList.stream().map(userOrderMenuEntity -> {
                        var storeMenu = storeMenuService.getStoreMenuStrategyIdStatus(userOrderMenuEntity.getStoreMenuId());
                        return storeMenu;
                    }).collect(Collectors.toList());
                    // 사용자가 주문한 스토어
                    var store = storeService.getStoreStrategyIdStatus(storeMenuList.stream().findFirst().get().getStoreId());
                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuList))
                            .storeResponse(storeConverter.toResponse(store))
                            .build();
                }).collect(Collectors.toList());

        return userOrderDetailResponsesList;
    }

    public List<UserOrderDetailResponse> history(UserSessionDto userSessionDto) {
        var userOrderEntityList = userOrderService.pastOrderHistory(userSessionDto.getId());
        // 사용자가 주문한 메뉴
        var userOrderDetailResponsesList = userOrderEntityList.stream()
                .map(it -> {
                    var userOrderMenuList = userOrderMenuService.getUserOrderMenu(it.getId());
                    var storeMenuList = userOrderMenuList.stream().map(userOrderMenuEntity -> {
                        var storeMenu = storeMenuService.getStoreMenuStrategyIdStatus(userOrderMenuEntity.getStoreMenuId());
                        return storeMenu;
                    }).collect(Collectors.toList());
                    var store = storeService.getStoreStrategyIdStatus(storeMenuList.stream().findFirst().get().getStoreId());
                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuList))
                            .storeResponse(storeConverter.toResponse(store))
                            .build();
                }).collect(Collectors.toList());
            return userOrderDetailResponsesList;
    }

    public UserOrderDetailResponse read(UserSessionDto userSessionDto, Long orderId) { // 한건을 가져오느냐
        var userOrder = userOrderService.getUserOrderWithOutStatusWithThrow(orderId, userSessionDto.getId());
        // 사용자가 주문한 메뉴
            var userOrderMenuList = userOrderMenuService.getUserOrderMenu(userOrder.getId());
            var storeMenuList = userOrderMenuList.stream().map(userOrderMenuEntity -> {
                var storeMenu = storeMenuService.getStoreMenuStrategyIdStatus(userOrderMenuEntity.getStoreMenuId());
                return storeMenu;
            }).collect(Collectors.toList());
        var store = storeService.getStoreStrategyIdStatus(storeMenuList.stream().findFirst().get().getStoreId());
        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrder))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuList))
                .storeResponse(storeConverter.toResponse(store))
                .build();
    }
}
