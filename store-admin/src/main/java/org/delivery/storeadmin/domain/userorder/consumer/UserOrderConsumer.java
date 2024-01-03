package org.delivery.storeadmin.domain.userorder.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.message.model.UserOrderMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserOrderConsumer {

    // 어떤 queue로 부터 받아올 것이냐? queues = "" 형태로 적어주면 된다.
    @RabbitListener(queues = "delivery.queue")
    public void userOrderConsumer(UserOrderMessage userOrderMessage) {
        // 괄호안에 받을 매개변수로는 객체로받아도 되고, 스트링 형태로도 된다.
        log.info("message queue >> {}", userOrderMessage);
    } // 이렇게만 작성해주면 컨슘 작성은 끝이 난다.

}
