package org.delivery.storeadmin.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreUserResponse {
    private UserResponse user;
    private StoreResponse store;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse { // user는 가게 사용자의 정보를 넣어주는 것.
        // 이렇게 생긴 inner class 접근 방법은 StoreUserResponse 뒤에 .으로 불러낼수 있다.
        // public이면 바깥쪽에서 접근 가능하고 static이면 반대다.
        private Long id;
        private String email;
        private String password;
        private StoreUserStatus status;
        private StoreUserRole role; // 권한
        private LocalDateTime registeredAt;
        private LocalDateTime unregisteredAt;
        private LocalDateTime lastLoginAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreResponse { // store는 가맹점의 정보를 넣어주는 것.
        private Long id;
        private String name;

    }
}
