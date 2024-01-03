package org.delivery.api.config.web;

import lombok.RequiredArgsConstructor;
import org.delivery.api.interceptor.AuthorizationInterceptor;
import org.delivery.api.resolver.UserSessionResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthorizationInterceptor authorizationInterceptor;
    private final UserSessionResolver userSessionResolver;
    // open api 빼고 나머지는 다 검증하기로 룰을 정하자.
    private List<String> OPEN_API = List.of(
            "/open-api/**"
    ); // open api 하위에 있는 모든 주소들 리스트 형식으로 묶은 것.
    private List<String> DEFAULT_EXCLUDE = List.of(
            "/", //root 주소를 의미함.
            "favicon.ico", // 파비콘 아이콘
            "/error" // 에러주소
    );
    private List<String> SWAGGER = List.of( // 이거 작성 잘못하면 swagger 못띄움 ;;
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Override

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(OPEN_API)
                .excludePathPatterns(DEFAULT_EXCLUDE)
                .excludePathPatterns(SWAGGER);
        // 예시로 이런식으로 제외 시킬 수 있다. 근데 이런식으로 하면 계속 exclude를 추가해야해서 번거롭다.
//                .excludePathPatterns("/api/user/register")
//                .excludePathPatterns("/api/term/**")
//                .excludePathPatterns();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionResolver); // resolver 등록.
    }
}
