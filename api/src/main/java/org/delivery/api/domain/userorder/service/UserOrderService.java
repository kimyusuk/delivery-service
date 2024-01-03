package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.User;
import org.delivery.db.userorder.UserOrder;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {
     // user_id를 갖고오는 메소드가 필요하겠죠?
    private final UserOrderRepository userOrderRepository;

    public UserOrder getUserOrderWithOutStatusWithThrow(
            Long orderId,
            Long userId
    ){
        return userOrderRepository.findAllByIdAndUserId(orderId, userId)
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    // 특정 사용자의 모든 주문,
    public List<UserOrder> getUserOrderStrategyUserId(Long userId) {
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }

    // 특정 주문
    public UserOrder getUserOrderStrategyIdUserId(Long orderId, Long userId) {
        return userOrderRepository.findAllByIdAndStatusAndUserId(orderId, UserOrderStatus.REGISTERED, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 위에는 매개변수로 status 안받는데 여기는 왜 매개 변수 status인가? 이유는 사용자나 고용주로 부터 status를 request 요청 상태로 받아야 하기 때문에
    public List<UserOrder> getUserOrderStrategyUserIdInStatus(Long userId, List<UserOrderStatus> statuses) {
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statuses);
    }

    // 주문에 대한 상태 변경 메소드
    public UserOrder userOrderSetStatus(UserOrder userOrder, UserOrderStatus userOrderStatus) {
        userOrder.setStatus(userOrderStatus);
        return userOrderRepository.save(userOrder);
    }

    // UserOrderStatus Enum 클래스로 상태 기능을 또 만들어준다.
    // 주문 ( create )
    public UserOrder order(UserOrder userOrder) {
        return Optional.ofNullable(userOrder)// null 값이 올수 있기 때문에 Optional 체크 해줘야한다.
                .map(it -> {
                    it.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 주문 확인
    public UserOrder orderAccepted(UserOrder userOrder) {
        userOrder.setAcceptedAt(LocalDateTime.now());
        return userOrderSetStatus(userOrder, UserOrderStatus.ACCEPT);
    }

    // 조리 시작
    public UserOrder cookedTime(UserOrder userOrder) {
        userOrder.setCookingStartedAt(LocalDateTime.now());
        return userOrderSetStatus(userOrder, UserOrderStatus.COOKING);
    }

    // 배달 시작
    public UserOrder deliveryStarted(UserOrder userOrder) {
        userOrder.setDeliveryStartedAt(LocalDateTime.now());
        return userOrderSetStatus(userOrder, UserOrderStatus.DELIVERY);
    }

    // 배달 완료
    public UserOrder receivedTime(UserOrder userOrder) {
        userOrder.setReceivedAt(LocalDateTime.now());
        return userOrderSetStatus(userOrder, UserOrderStatus.RECEIVE);
    }

    // 현재 주문 내역 메소드와, 과거 주문 내역 메소드를 만들어주기.
    public List<UserOrder> currentOrder(Long userId) {
        return getUserOrderStrategyUserIdInStatus(
                userId,
                List.of( // 이 4개의 상태는 현재 진행 중이라는 것을 알려주는 상태다.
                    UserOrderStatus.ORDER, // 주문
                    UserOrderStatus.ACCEPT, // 주문 확인
                    UserOrderStatus.COOKING, // 요리 중
                    UserOrderStatus.DELIVERY // 배달 중
                )
        );
    }

    public List<UserOrder> pastOrderHistory(Long userId) {
        return getUserOrderStrategyUserIdInStatus(
                userId,
                List.of( // 이 1개의 상태는 모든 진행이 다 끝났다는 것을 알려주는 상태다.
                        UserOrderStatus.RECEIVE // 배달 완료
                )
        );
    }
}
