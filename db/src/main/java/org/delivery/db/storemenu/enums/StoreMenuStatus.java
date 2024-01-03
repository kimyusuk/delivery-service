package org.delivery.db.storemenu.enums;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
public enum StoreMenuStatus { // 등록 해지 뿐만 아니라, 일시 정지, 대기중, 이런 것들 도 추가해 줄 수 있다.
    REGISTERED("등록"),
    UNREGISTERED("해지"),;

    private String description;
}
