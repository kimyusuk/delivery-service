package org.delivery.storeadmin.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    // 여기서는 rabbitTemplate(cf,msgcv)는 따로 만들지 않는다.
    // yaml 파일에 있는 정보로 자동으로 cf(connectionfactory)를 만들어준다.
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        // 우리가 만든 objmapper형태가 작동이 된다.
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    // 이제 도메인에 컨슘을 만들어주면 된다.
}
