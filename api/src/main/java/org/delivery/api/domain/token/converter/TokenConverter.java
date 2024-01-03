package org.delivery.api.domain.token.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.model.TokenDto;
import org.delivery.api.domain.token.model.TokenResponse;

import java.util.Objects;

@Converter
@RequiredArgsConstructor
public class TokenConverter {
    public TokenResponse tokenDtoToTokenResponse(TokenDto access, TokenDto refresh) {
        // null 값이 올수도 있다.
        Objects.requireNonNull(access, () -> {throw new ApiException(ErrorCode.NULL_POINT);}); // nonNullelseget은 항상실행 nonnull은 null일때 예외 보냄
        Objects.requireNonNull(refresh, () -> {throw new ApiException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
                .accessToken(access.getToken())
                .accessTokenExpiredAt(access.getExpiredAt())
                .refreshToken(refresh.getToken())
                .refreshTokenExpiredAt(refresh.getExpiredAt())
                .build();

    }

}
