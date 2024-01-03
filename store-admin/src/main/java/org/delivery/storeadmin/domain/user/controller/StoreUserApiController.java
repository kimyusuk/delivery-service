package org.delivery.storeadmin.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.model.StoreUserResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store-user")
@RequiredArgsConstructor
public class StoreUserApiController { // 로그인 이후에 나타날수 있는 페이지
    private final StoreUserConverter storeUserConverter;

    @GetMapping("/get-me") // parameter hidden으로 swagger에서 안보이도록 설정해주자.
    public StoreUserResponse getMe(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession) {
        return storeUserConverter.toResponse(userSession);
    }
}
