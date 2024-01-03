package org.delivery.api.common.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {
    private final RabbitTemplate rabbitTemplate; // config에서 만들어진 rabbitTemplate를 가져오는 것.

    public void producer(String exchange, String routeKey, Object object) {
        rabbitTemplate.convertAndSend(exchange, routeKey, object);
    } // 1차 producer 설정 끝. config에 테스트 controller로 잘 작동되는지 실행 테스트.
}
