package org.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.model.TokenResponse;
import org.delivery.api.domain.token.converter.TokenConverter;
import org.delivery.api.domain.token.service.TokenService;
import org.delivery.db.user.User;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;
    /*
      1. userEntity에서 user에서 뽑고 싶은 정보를 추출.
      2. service에서 tokenDto 발행 메소드를 만들어준다. 이때 user에서 뽑은 정보를 data.put을 통해 넣어준다.
      3. service 메소드를 이용해서 (access, refresh) tokenDto를 발행.
      4. converter로 tokenDto에서 -> tokenResponse로 변환.
      5. tokenBusiness 클래스 작성 완료되면 UserBusiness로 이동
    * */
    public TokenResponse issueToken(User user) {
        return Optional.ofNullable(user)
                .map(it -> it.getId())
                .map(userId -> {
                    var access = tokenService.issueAccessToken(userId); // access token 발행.
                    var refresh = tokenService.issueRefreshToken(userId);
                    return tokenConverter.tokenDtoToTokenResponse(access, refresh);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public Long validationAccessToken(String accessToken) {
        var userId = tokenService.validationToken(accessToken);
        return userId;
    }
}
