package org.delivery.db.storemenu;

import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {
    // 유효한 메뉴 체크 = (다 registered야 그럼 뭘로 찾아야해 id로 찾아야지. 근데 삭제된 메뉴도 찾을까? 따라서 unregistered는 걸러야지?)
    // select * from store_menu where id = ? and status = ? order by id desc limit 1.  limit 1 = find first랑 같다.
    Optional<StoreMenu> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreMenuStatus storeMenuStatus);

    // 특정 가게의 메뉴 가져오기
    // select * from store_menu where store_id = ? and status = ? order by sequence desc;
    List<StoreMenu> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId, StoreMenuStatus storeMenuStatus);


}
