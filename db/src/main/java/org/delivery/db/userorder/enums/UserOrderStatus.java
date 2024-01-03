package org.delivery.db.userorder.enums;
public enum UserOrderStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),

    // 주문
    ORDER("주문"),

    // 주문 확인
    ACCEPT("확인"),

    // 요리중
    COOKING("요리중"),

    // 배달중
    DELIVERY("배달중"),

    // 배달 완료
    RECEIVE("배달 완료"),
    ;
    UserOrderStatus(String description) {
        this.description = description;
    }
    private String description;

}
