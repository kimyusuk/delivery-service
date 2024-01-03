package org.delivery.storeadmin.domain.authorization.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {
    // implements UserDetailsService는 localhost메인 페이지에서 default로 제공해주는 spring의 security 로그인창을 우리가 가입시킨 가맹점 직원들을 로그인 할수 있게 처리 해주는 작업을 한다.

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인창에 입력한 id값이
        // 바로 (String username) 값으로 들어오게 된다. 이때 우리는 return값에 "어 정상적으로 존재하는 가입자야" 라고 승인 해주는 코드를 작성해주면 된다.
        // 정상적으로 가입한 사용자가 있는지 확인하기 위해서 일단 getRegisterUser라는 서비스 메소드로 불러오자. => 정상적인 사용자 = storeUser
        // user는 springSecurity에 UserDetails에서 제공해주는 User를 사용해주자. builder를통해 세팅 작업에 들어가준다.
        // 정상적일때는 email password 넘겨줘서, User빌드를 통해 password를 대조 후 사용자의 정보 값을 리턴 시켜주고, 정상적이지가 않을 때는 exception을 터트릴 것이다.
        // 빌드할때는 role은 필수 값이다.
        // build gradle에 thymeleaf 추가해 로그인 이후에도 웹 페이지로 볼수 있게 만들어주자. 이때 thymeleaf는 컨트롤러가 있으면 리소스 templates 하위에 있는 최상위 html에다가 localhost:default 값을 부여해준다.

        var storeUser = storeUserService.getRegisterUser(username); // 이때 여기서 return 값은 Optional로 감싸진 형태이기 때문에 없는 사용자일때는 UsernameNotFoundException을 터트려 줘야한다.
        var store = storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeUser.get().getStoreId(), StoreStatus.REGISTERED); // 가게 주인의 id를 가지고 어떤 가게인지를 또 찾아낼수 있다.

        return storeUser.map(it -> {
            var userSession = UserSession.builder()
                    .userId(it.getId())
                    .email(it.getEmail())
                    .password(it.getPassword())
                    .status(it.getStatus())
                    .role(it.getRole())
                    .registeredAt(it.getRegisteredAt())
                    .unregisteredAt(it.getUnregisteredAt())
                    .lastLoginAt(it.getLastLoginAt())
                    .storeId(store.get().getId()) // optional로 감싸져서 get을 한번더 써준다.
                    .storeName(store.get().getName())
                    .build();
            return userSession;

//            var user = User.builder()
//                    .username(it.getEmail()) // storeUser가 갖고있는 email
//                    .password(it.getPassword()) // 그리고 password를 넘겨준다.
//                    .roles(it.getRole().toString())
//                    .build();
//            return user; // 가맹점 사용자를 리턴 시켜주는 대신. UserSession 객체를 리턴 시켜주도록 해보자. 이 위에 코드랑 다른 점은 더 많은 정보를 내려줄 수 있다는 것이다.
        }).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
