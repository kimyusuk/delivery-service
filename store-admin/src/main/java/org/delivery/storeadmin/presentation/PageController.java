package org.delivery.storeadmin.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController { // ModelAndView는 security Spring이 session값을 통해 정상적인 인증을 하게 되면 localhost:default 값으로 보내준다.
    // 이때 thymeleaf가 있으면 localhost:default 값에 리소스 templates 하위에 최상위 html을 찾아갈 것.
    // 그리고 thymeleaf의 장점은 html에서 thymeleaf문법을 사용하면 어떤 사용자가 로그인 했는데 추적할 수 있게 해준다.
    // 이런 문법을 사용하기 위해서는 Thymeleaf Extras Springsecurity5 디펜던시를 추가해줘야한다.

    @RequestMapping(path = {"","/main"}) // mapping 주소가 ("")이 뜻은 = localhost:8081이다. 그리고 주소에다가 /alpha 이렇게 넣어주면 이뜻은 localhost:8081/alpha가 되는 것이다.
    public ModelAndView main() {
        return new ModelAndView("main"); // + .html 돼서 templates 하위에 있는 html 값을 찾아준다. /templates/main이라고 따로 적지 않아도 되는 이유는 자동으로 templates 폴더를 찾아주기 때문.
    }

    @RequestMapping(path = "/order") // localhost:8081/order
    public ModelAndView order() {
        return new ModelAndView("order/order"); // 이때는 templates 하위에 order 폴더 아래에 order.html 이 있다. 이렇게 해석 하면 된다.
    }
}
