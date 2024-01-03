package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final TokenBusiness tokenBusiness;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url: {}",request.getRequestURI());

        // web, chrome 처리 get, post 이전에 options라고 해당 api를 지원하는지 체크를 해주는 mapping이 있다. options = pass
        if(HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // 받는 요청이 일어날때의 처리. js, html, png, resource를 요청할 경우 검문없이 통과.
        if(handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        //return true; // 일단 통과처리.
        // TODO header 검증
//         header로 들어온 authorization-token 값이 우리가 발행한 토큰 값과 맞는지 매칭 시켜줘야 한다.
            var accessToken = request.getHeader("authorization-token"); // header 값을 일단 꺼내와야 한다.
            if (accessToken == null) { // 토큰 값이 없으면 = 인증 헤더에 토큰 값이 없다는 오류 메세지를 보내준다.
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }
        var userId = tokenBusiness.validationAccessToken(accessToken);
        if (userId != null) {
            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()); // null 값일 경우에는 requireNonNull에서 잡아줄거임.
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST); // requestContext에다가 저장한다. "userId" : userId를 . 얼마만큼? 이번 요청 한해서만.
            return true;
        }
        throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
    }
}
