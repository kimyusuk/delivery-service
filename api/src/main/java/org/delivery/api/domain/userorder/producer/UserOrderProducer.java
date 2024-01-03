package org.delivery.api.domain.userorder.producer;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.rabbitmq.Producer;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userorder.UserOrder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderProducer {
    private final Producer producer;
    private static final String Exchange = "delivery.exchange";
    private static final String ROUTE_KEY = "delivery.key";
    public void sendOrder(UserOrder userOrder) {
        sendOrder(userOrder.getUserId());
    } // 객체를 집어 넣어도 => sendOrder에는 무조건 적으로 UserOrderId로 전환 돼서 입력 된다.

    public void sendOrder(Long userOrderId) {
        var message = UserOrderMessage.builder()
                .userOrderId(userOrderId)
                .build();
        // 이제 메세지를 프류듀스에 담아서 보내면 되겠죠.
        producer.producer(Exchange, ROUTE_KEY, message);
    }

    // 이렇게 까지 작성되면 userOrderBusiness에 비동기식으로 가맹점에게 알리는 기능을 추가해주자.
    // business에서 비동기식 처리 메소드를 작성 완료 했으면, 이후부터는 가맹점쪽도 똑같이 rabbitmq 설정들을 마무리 해주면 된다.
}
