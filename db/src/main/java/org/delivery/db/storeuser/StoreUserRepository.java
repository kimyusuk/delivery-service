package org.delivery.db.storeuser;

import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreUserRepository extends JpaRepository<StoreUser, Long> {
    // select * from store_user where email = ? and status = ? order by id desc limit 1
    Optional<StoreUser> findFirstByEmailAndStatusOrderByIdDesc(String email, StoreUserStatus storeUserStatus);

}
