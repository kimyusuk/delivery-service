package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    Optional<UserOrder> findAllByIdAndUserId(Long id, Long userId);

    // 특정 유저의 모든 주문,
    // select * from user_order where user_id = ? and status = ? order by id desc
    List<UserOrder> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    // select * from user_order where user_id = ? and status in (?,?,) order by id desc
    List<UserOrder> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);

    // 특정 주문
    Optional<UserOrder> findAllByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);


    
}
