package org.delivery.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity // security 활성화
public class SecurityConfig {
    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf() // default 값이 csrf 활성화 이기 때문에
                .disable() // 이런식으로 csrf 공격에 대한 방어를 비활성화를 시켜준다. 이유는 모르겠다. 공격 예방하면 좋은거 아닌가? 아웅..
                .authorizeHttpRequests(it -> {
                    it.requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations()
                            ).permitAll() // resource에 대해서는 모든 요청을 허용한다.
                            .mvcMatchers(
                                    SWAGGER.toArray(new String[0])
                            ).permitAll() // swagger는 인증 절차 없이 통과.
                            .mvcMatchers(
                                    "/open-api/**"
                            ).permitAll() // open-api의 하위 주소들은 전부다 통과.
                            .anyRequest().authenticated(); // 그 외 모든 요청은 인증을 받겠다.
                }).formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // hash 방식의 암호화를 한다.
    }

}
