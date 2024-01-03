package org.delivery.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.delivery.db") // 이하위 밑에 엔티티는 다 불러올거야. 라는 뜻이 된다.
@EnableJpaRepositories(basePackages = "org.delivery.db") // 이하위 밑에 있는 레퍼지토리 다 사용 할거야.
public class JpaConfig {

}
