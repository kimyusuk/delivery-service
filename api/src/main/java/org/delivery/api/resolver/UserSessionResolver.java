package org.delivery.api.resolver;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.domain.user.model.UserSessionDto;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {
    // 실행 시키려면 webConfig 등록해줘야 한다.
//    private final UserBusiness userBusiness;
    private final UserService userService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크
        // 1. annotation 있는지 체크
        var annotation = parameter.hasParameterAnnotation(UserSession.class);
        // 2. parameter 타입 체크
        var parameterType = parameter.getParameterType().equals(UserSessionDto.class);
        return (annotation && parameterType); // 얘네 둘다 true 일때만.
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 서포트 파라미터를 통과하면(true반환시) 여기로 들어오게 된다.
        // request context holder에서 찾아오기.
        var requestContext = RequestContextHolder.getRequestAttributes();
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var userEntity = userService.getUserStrategyId(Long.parseLong(userId.toString()));
//        var userEntity2 = userBusiness.me(Long.parseLong(userId.toString())); // 위에거랑 같은 것이기 때문에 뭘 쓰든 상관 없다.

        // 사용자 정보 세팅.
        return UserSessionDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .status(userEntity.getStatus())
                .address(userEntity.getAddress())
                .registeredAt(userEntity.getRegisteredAt())
                .unregisteredAt(userEntity.getUnregisteredAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();

    }
}
