package org.delivery.db.user;

import org.delivery.db.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // select * from user where id = ? and status = ? order by desc limit 1
    Optional<User> findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus status); // 사용자를 찾을 때 쓰는 쿼리 메소드다.

    // select * from user where email = ? and password = ? and status = ? order by desc limit 1
    Optional<User> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password, UserStatus status); // 로그인할 때 쓰이는 쿼리 메소드다.
}
