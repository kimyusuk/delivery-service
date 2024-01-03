package org.delivery.api.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // io swagger v3 jackson 이것을 사용 해야한다.
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) { // 우리가 만들어줬던 ObjectMapper Bean 타입을 Spring 쪽에서 자동으로 찾아내 여기다가 주입 시켜준다.
        // 그리고 이 objectMapper는 당연히 snakeCase를 가진 objMapper 겠죠?
        return new ModelResolver(objectMapper);
    }
}
