package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
    user의 경우 1000번대 에러 코드 사용
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs{ // enum 클래스는 코드의 실수가 없어야 할 때 쓰는 클래스라고 한다.

    USER_NOT_FOUND(400, 1404, "사용자를 찾을 수 없음."); // http, 내부, 설명

    private final Integer httpStatusCode; // 여기 internal에 상응하는 코드
    private final Integer errorCode; // internal 내부의 라는 뜻.
    private final String description;


}
