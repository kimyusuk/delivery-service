package org.delivery.api.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.user.enums.UserStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSessionDto {
    private Long id; // 얘만 추가.
    private String name;
    private String email;
    private String password;
    private UserStatus status; // status 같은 경우는 enum을 사용할 것. 따라서 String status가 아니라 UserStatus enum클래스로 만든 객체를 쓰게 된다.
    private String address;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime lastLoginAt;
}
