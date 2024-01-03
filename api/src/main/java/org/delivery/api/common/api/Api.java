package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCodeIfs;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Api<Dto> {
    /* Api = {
        result : {}
     (Dto)body : {}
     } */
    // 이런 형태인데, Ok method가 실행 될때 저 형태를 띄워라. 어떤형태를?
    // new Api <Dto>를 띄우는데, 이때 
    // 매개변수로는 Dto(body 값) 을 받아주고,  바디 값을 api.body에 넣어주고. 다시 return 시켜줘라.
    private Result result;
    @Valid
    private Dto body;

    // static으로 Api 객체가 담고있는 이 OK 메소드를 쉽게 불러오고 싶으면
    // static 뒤에 <> 제너릭을 써줘야 한다. 만약 매개변수로 제너릭 타입을 쓰고 있으면은.
    // 왜 why? Api가 body를 내려줄때 어떤 객체를 내려주는데, 이때 static도 어떤 객체를 써야하는지 알아야한다.
    public static <Dto>Api OK(Dto data) {
//        var api = new Api<Dto>();
//        api.result = Result.OK();
//        api.body = data;
        Api<Dto> api = new Api(Result.OK(),data);
        return api;
    }
    public static Api ERROR(Result result) {
        var api = new Api();
        api.result = result;
        return api;
    }
    public static Api ERROR(ErrorCodeIfs errorCodeIfs) {
        var api = new Api();
        api.result = Result.ERROR(errorCodeIfs);
        return api;
    }
    public static Api ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        var api = new Api();
        api.result = Result.ERROR(errorCodeIfs, tx);
        return api;
    }
    public static Api ERROR(ErrorCodeIfs errorCodeIfs, String description) {
        var api = new Api();
        api.result = Result.ERROR(errorCodeIfs, description);
        return api;
    }
}
