package org.delivery.api.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    커스텀 어노테이션으로
    어노테이션들을 분리 하는 이유

    1. 서비스 로직.
    2. 유저 데이터 관리. + 도메인.
    3. 외부 연결 관리. + 비지니스 로직.

    * */
@Target(ElementType.TYPE) // 어노테이션으로 만들기 위해서.
@Retention(RetentionPolicy.RUNTIME)
@Service // Business 어노테이션이 빈으로 등록 될 수 있게 해준다.
public @interface Business {
    @AliasFor(annotation = Service.class)
    String value() default "";
}
