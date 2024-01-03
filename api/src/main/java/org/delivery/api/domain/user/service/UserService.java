package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.User;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User register(User user) {
        return Optional.ofNullable(user)
                .map(it -> {
                    it.setStatus(UserStatus.REGISTERED);
                    it.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(it);})
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity isNull"));
    }

    public User login(String email, String password) { // 서비스 로직.
        var entity = getUserStrategyEmailPw(email, password);
        return entity;
    }

    public User getUserStrategyEmailPw(String email, String password) { // 이메일과 패스워드를 통해서 사용자 정보를 가져와야한다.
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(email, password, UserStatus.REGISTERED)
                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public User getUserStrategyId(Long userId) { // 이메일과 패스워드를 통해서 사용자 정보를 가져와야한다.
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(userId, UserStatus.REGISTERED)
                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
