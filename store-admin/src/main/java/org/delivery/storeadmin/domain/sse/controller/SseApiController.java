package org.delivery.storeadmin.domain.sse.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {
    private static final Map<String, SseEmitter> userConnection = new ConcurrentHashMap<>(); // var emitter를 userConnection에 저장한다.
    // 3개의 thread 발송 보낸 전송 이던가? 아무튼 충돌을 막기 위해서 콘코런트해쉬맵을 쓴다는데 솔직히 무슨 말인지 1도 모르겠다.
    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // produces에서 mediaType을 text event stream으로 설정.
    public ResponseBodyEmitter connect(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession) { // ResponseBodyEmitter로 타입 받아주기.
        log.info("login user {}", userSession);
        var emitter = new SseEmitter(1000L * 60); // 이때 sse 연결이 성립이 된다. 여기에 요청사항이 없을시 자동 재접을 밀리 세컨드 단위로 조정이 가능하다.
        // default 값으로는 30초 동안 요청 사항 없을시 자동 재접이 된다.
        userConnection.put(userSession.getUserId().toString(), emitter);

        emitter.onTimeout(() -> {
            log.info("onTimeout");
            // 클라이언트와 타임아웃이 일어났을때.
            emitter.complete(); // 연결 종료.
        });

        emitter.onCompletion(() -> {
            log.info("onCompletion");
            // 클라이언트와 연결이 종료 되었을 때.
            userConnection.remove(userSession.getUserId().toString()); // 사용자를 지워줘야 한다.
        });

        // 최초 연결시 응답 전송.
        var event = SseEmitter
                .event()
                .name("onopen")
//                .data("connect") 최초 연결시 어차피 데이터를 받지 못한다.
                ;

        try { // emitter.send시 try catch문 감싸 줘야 한다.
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    @GetMapping("/push-event")
    public void pushEvent(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession) {
        // 기존에 연결된 유저 찾기.
        var emitter = userConnection.get(userSession.getUserId().toString()); // 연결된 세션쪽에서 찾아온다는 뜻.
        var event = SseEmitter
                .event()
                .data("hello") // on message에 전송이 된다.
                ;
        try {
            emitter.send(event);
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

    }
}
