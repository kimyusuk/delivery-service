package org.delivery.storeadmin.domain.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSession implements UserDetails {
    // user
    private Long userId;
    private String email;
    private String password;
    private StoreUserStatus status;
    private StoreUserRole role; // 역할(마스터, 관리자, 직원)
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime lastLoginAt;

    // store
    private Long storeId;
    private String storeName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 여기에 list.of를 사용해서 만들어준다. simpleGrantedAuthority에 this.role.toString을 해준다.
        return List.of(new SimpleGrantedAuthority(this.role.toString())); // 문자로 권한을 넘겨준다.
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() { // 만료가 되었는가를 물어보는것. user status가  REGISTERED면 만료가 안됐다. 라는 값으로 리턴 시켜줄 수 있는 것이다.
        return this.status == StoreUserStatus.REGISTERED;
    }

    @Override
    public boolean isAccountNonLocked() { // 잠긴 상태가 아닌가를 물어보는 것인데, 어떤 경우에 잠기는 것인지 잘 모르겠지만 위에랑 동일하다. 반대로 잠긴 상태다 라는 것을 보내주려면 UNREGISTERED 하면 될것 같다.
        return this.status == StoreUserStatus.REGISTERED;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 사용자의 자격 증명(암호가) 만료되었는가를 물어보는 것이다.
        return this.status == StoreUserStatus.REGISTERED;
    }

    @Override
    public boolean isEnabled() { // 사용자의 사용 가능 여부를 묻는 것이다. 항상 true 값을 준다.
        return true;
    }
}
