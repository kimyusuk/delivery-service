package org.delivery.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass // Base = 맵핑하지 않는다, 그저 상속을 줄때만 사용하겠다.
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Base { // 이게 실체니까 굳이 entity라는 용어를 붙히지 않아도 이게 entity 입니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
