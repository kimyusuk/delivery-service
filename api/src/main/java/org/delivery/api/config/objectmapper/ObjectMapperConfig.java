package org.delivery.api.config.objectmapper;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    // ObjectMapper에 registerModule 메소드가 커스텀 할수 있게 해준다.
    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module()); // 8버전 이후의 클래스들을 처리해준다.
        objectMapper.registerModule(new JavaTimeModule()); // localdate들을 처리해준다.
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 모르는 json field에 대해서는 무시한다.
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 비어 있는 것들 생성할때 무시한다.
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);// 날짜 직렬화
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());// snake case로 쓰겠다.
        return objectMapper;
    }

}
