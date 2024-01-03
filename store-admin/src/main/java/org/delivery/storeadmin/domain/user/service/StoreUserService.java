package org.delivery.storeadmin.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.storeuser.StoreUser;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreUserService {
    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder; // config->security->SecurityConfig.java-> Bean으로 등록했기 때문에 이렇게 interface 형식으로 불러다 가져올 수 있다.

    public StoreUser register(StoreUser storeUser) { // 외부에서 storeUser를 받는다.
        storeUser.setStatus(StoreUserStatus.REGISTERED);
        storeUser.setPassword(passwordEncoder.encode(storeUser.getPassword())); // 괄호 안 password 값을 인코딩 할 수 있게 해준다. 이후 그 값으로 세팅 해준다.
        // 즉 passwordEncoder는 디코드는 불가능하고 서로 같은 인코딩 형식을 매칭 시켜 판별 해준다.
        storeUser.setRegisteredAt(LocalDateTime.now());

        return storeUserRepository.save(storeUser); // 위 내용들을 저장.
    }

    public Optional<StoreUser> getRegisterUser(String email) {
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email, StoreUserStatus.REGISTERED);
    }

}
