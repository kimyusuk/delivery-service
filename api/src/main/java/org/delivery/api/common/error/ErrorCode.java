package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{ // enum 클래스는 코드의 실수가 없어야 할 때 쓰는 클래스라고 한다.
    OK(200, 200, "성공"), // http, 내부, 설명
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null point");
//    ErrorCode(Integer httpStatusCode, Integer errorCode, String description) {
//        this.httpStatusCode = httpStatusCode;
//        this.errorCode = errorCode;
//        this.description = description;
//    }

    private final Integer httpStatusCode; // 여기 internal에 상응하는 코드
    private final Integer errorCode; // internal 내부의 라는 뜻.
    private final String description;

//    @Getter 붙히는순간 이렇게 수기로 작성 안해도 getter code를 만들어준다. 오버라이드가 자동으로 된다. imple만 해준다면,
//    @Override
//    public Integer getHttpStatusCode() {
//        return this.httpStatusCode;
//    }
//
//    @Override
//    public Integer getErrorCode() {
//        return this.errorCode;
//    }
//
//    @Override
//    public String getDescription() {
//        return this.description;
//    }
}
