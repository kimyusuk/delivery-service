package org.delivery.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트가 동작하기 위해서 달아주는 어노테이션.
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
// 스프링 부트 터미널 설정 !!!
// terminal -> dir -> ./gradlew build -> build failed 나옴. 이유 우리가 root build gradle 에게는 bootjar, jar 둘다 false를 줬기 때문이다.
// 아무튼 이후 빌드 실패해도, build packages들이 많이 많들어짐. build를 살펴보면
// libs에 jar가 => 부트 jar이고,
// plain.jar가 => 그냥 jar이다.
// 아무튼 우리는 부트 jar가 필요한거지 그냥 jar는 필요가 없음 그냥 자르는 외부 라이브러리만 끄집어 패키징 해주는 것 뿐. 부트 자르가 스프링 어플리케이션을 실행시키는 본체임.

// 따라서 api에 있는 build.gradle에다가 작성해준다. 아까 부모 그래들 하고는 다르게
// 부트 자르 = 트루, 그냥 자르 = 펄스를 준다.

// db는 entity만 정의 하는 곳 이기 때문에 스프링 어플리케이션이 실행될 이유가 없다.
// 따라서 db에 있는 build.gradle에다가는
// 부트자르는 false, 그냥자르는 true를 준다.

// gui gradle에서 -> tasks -> build -> clean으로 빌드패키지스 다시 새롭게 만들 수 있다.
// 터미널 방법은 ./gradlew clean 해주면 위에 방법과 동일한 효과를 준다.
// 다시 빌드 ./gradlew build 해주면 여전히 build successful 값이 나온다.

//"registeredAt":"2023-11-11T04:24:13.2702195" = 표준화 기구 ios에서  이러한 형태를 8601 이라고 정의를 내려
// ios8601형태라고 한다. 구분자는 yyyy-MM-ddThh:mm:ss 다

// object mapper = json과 객체를 형변환 시켜주는 애다.
// json <==> Object 이때 형변환을 Serialize 역형변환을 deSerialize라고 한다.
// 일단 기본적으로 object Mapper는 카멜케이스가 기본이다.
// 근데 회사마다 규율이 다르고 매 java파일 만들때마다 JsonNaming(value = propertyStrategies.snakeStrategies.class) 이런 작업을 모든 파일에 해주기 번거롭다.
// 따라서 이것도 config로 등록 시켜버린다.

// mavenRepository google 검색해서, springdoc 검색 = 여기서 org.spring.doc => springdoc-openapi-ui 클릭.  1.7.0 버전 사용. 이것이 스웨거라고 한다. 스웨거는 탈렌드 api와 비슷한 것.
// gradle니까 gradle 찍고 복사. dependency 추가해주고 어플 실행.
// 실행 후. http://localhost:8080/swagger-ui/index.html 이렇게 검색.
// 이렇게 실행하고 보면 swagger도 snakecase가 default값이 아니라 camelcase가 default값이라, config로 설정 해줘야 한다.

// token으로 검증하기 위해서는 크롬스토어에 mod-header를 추가? 구독 하고 = 안에다가 request분문에 {header에 : "authorization-token"}, {value에 : token(얻은 토큰값)} 값을 적어주면 된다.

// 1:N 관계는 N 쪽에서 1의 아이디를 갖는 것이다.
// 예를 들어 가게는 여러개의 메뉴를 갖고있다. 이때 가게는 1이고 메뉴는 n이다. 이때 메뉴는 가게의 id를 갖을 수 있다. 이게 바로 1:N관계다.
// workbench index 잡는법. => constraint와 index 밑에 구문 다 삭제해주시고, 실행 => 이후 새로만들어진 table에 설정 바꾸기 클릭(스패너 모양 있음)
// 여기서 index 클릭. primary 밑에 컬럼에 idx_1:N관계가 되는형을 집어넣는다. => idx_store_id 이런식으로.
// 이후 클릭후 mapping 시켜준다 일치하는 항목과 store_id에 체크.

// yaml 파일에 ddl = validator에서 자체적으로 db안에 테이블명과 칼럼명들과 우리가 작성한 table의 칼럼명들이 다 맞는지 대조해서 검증 해 준다.

// userId = 1번은 N개의 주문 내역을 가질 수 있다. ex) (storeId = 1번 starBucks(americano, cafeLatte)), (storeId = 2번 BBq(rice, chicken))
// 이때 한 user는 N개의 가게의 주문을 가질수 있기 때문에 = user_order와 1:N관계 이고
// 이때 한 주문내역 안에서는 여러개의 메뉴를 시킬 수 있기 때문에 이때도 역시 = user_order와 user_order_menu도 1:N 관계이어야 한다.
// 왜 그럼 user_order 와 store_menu로 1:N관계를 안시키냐?
// 이유는 store가 메뉴를 등록하고 조회하는 기능이지, 주문하는 기능은 아니기 때문이다.
// 따라서 store_menu는 그대로 두고 그 주문 기능을 추가 시켜줄 order_menu로 mapping을 하는 개념이다.
// userId 1번(김유석)이 user_oderId 1번(주문)을 통해 주문을 했을시 ->
// user_order_menuId 1번(스타벅스)에서 store_menuId 1번(아메리카노)과 2번(카페라때)을 주문했다. 라는 식으로 erd를 짜줄 수 있다.
// 따라서 store_menu와 user_order_menu도 1:N관계가 된다. 한 가게의 메뉴는 N번의 주문 기능을 갖기 때문이다.

// order_menu가 갖는 기능은
// 주문자가 총 구매한 금액(amount) +
// 주문한 시간(orderedAt)(datetime) +
// 가맹점에서 수락한 시간(acceptedAt)(datetime) +
// 요리 시작시간(cookingStartedAt) +
// 배달 픽업 시간(deliveryStartedAt) +
// 받은 시간(receivedAt)
