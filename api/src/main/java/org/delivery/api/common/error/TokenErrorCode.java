package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
  token error code인 경우에는 2000번대.
* */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs{
    INVALID_TOKEN(400, 2000, "유효하지 않는 토큰"), // http, 내부, 설명
    EXPIRED_TOKEN(400, 2001,"만료된 토큰"), // http, 내부, 설명
    TOKEN_EXCEPTION(400, 2002, "알수 없는 토큰 에러"), // http, 내부, 설명
    AUTHORIZATION_TOKEN_NOT_FOUND(400, 2003, "인증 헤더 토큰 없음"),
    ;
    private final Integer httpStatusCode; // 여기 internal에 상응하는 코드
    private final Integer errorCode; // internal 내부의 라는 뜻.
    private final String description;
    @Override
    public Integer getHttpStatusCode() {
        return null;
    }

    @Override
    public Integer getErrorCode() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
