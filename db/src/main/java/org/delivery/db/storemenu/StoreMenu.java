package org.delivery.db.storemenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.Base;
import org.delivery.db.storemenu.enums.StoreMenuStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "store_menu")
public class StoreMenu extends Base {
    @Column(nullable = false)
    private Long storeId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(precision = 11,  scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING) // 기본적으로 숫자이기 때문에 enum이 예를들어 REGISTERED가 1번, UNREGISTERED가 2번으로 저장되는데, 이를 "REGISTERED" 이런식으로 맵핑 시켜준 것이다.
    private StoreMenuStatus status;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    private int likeCount; // null 허용일경우 = Integer로
    // 좋아요 수 얘는 like를 사용할 수 없어서.

    private int sequence;
    // 정렬, 순서라는 뜻으로 사용되는데, sort를 사용할수 없어서.
}
