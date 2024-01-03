package org.delivery.api.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean // exchange 만들기.
    public DirectExchange directExchange() {
        return new DirectExchange("delivery.exchange");
    }

    @Bean // queue 만들기.
    public Queue queue() {
        return new Queue("delivery.queue");
    }

    @Bean // exchange를 => queue로 확장하는 방법.
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("delivery.key");
    }

    @Bean
    // rabbitmq에서 제공해주는 템플릿 producer가 => exchange에 전송하기 전 템플릿을 이용해 전송한다.
    // 여기에 들어갈 messageconverter는 amqp하위에 있는 컨버터다. ConnectionFactory도 amqp 하위에 rabbitmq 컨넥션이다.
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory); // 컨넥션팩토리로 부터 = 레빗템플릿 생성.
        rabbitTemplate.setMessageConverter(messageConverter); // 레빗템플릿에 메세지 컨버터 설정.
        return rabbitTemplate; // obj <=> json 과 같이 우리가 레빗 템플릿에 obj 넣으면 자동으로 json으로 바꿔준다잉. json도 obj로 바꿔주는데 이래서 위와 같이 messageconverter를 설정한거다.
    }

    @Bean
    // messageconverter를 설정해줘야 한다.
    public MessageConverter messageConverter(ObjectMapper objectMapper) { // 이때 우리가 bean으로 건드린 objectMapper가 고스란히 여기로 들어오게 되는 겁니다.
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // connectionfactory는 api하위 application.yaml에서 설정할 것이다.
    // 야물에 connectionfactory를 작성을 다하면,
    // 야물의 정보를 가지고 connectionFactory를 채워준다.
    // rabbitTemplate를 bean으로 등록해놨다가. exchange에다가 넣는 용도로 사용할 것이다.

    // 이제 실질적으로 produce에서 exchange에 보내는 코드를 common 하위에 작성한다.
}
