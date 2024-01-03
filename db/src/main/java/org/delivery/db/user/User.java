package org.delivery.db.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.delivery.db.Base;
import org.delivery.db.user.enums.UserStatus;
import org.hibernate.type.StringType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = true) // 부모 클래스의 필드를 가져다 쓸 경우에는, true 값을 준다.
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends Base {
    // NOTNULL 일때 nullable = false를 주는 것이다.

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status; // status 같은 경우는 enum을 사용할 것. 따라서 String status가 아니라 UserStatus enum클래스로 만든 객체를 쓰게 된다.

    @Column(length = 150, nullable = false)
    private String address;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime lastLoginAt;
}
