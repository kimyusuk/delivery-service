package org.delivery.api.domain.token.helper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtTokenHelper implements TokenHelperIfs {
    // jwt token을 편리하기 위해서 yaml 파일에 token 기능을 추가해주도록 하는데, 이건 해도 되고 안해도 되는 작업.
    // 이곳으로 yaml을 불러 오려면, token-yaml파일의 기능을 받아줄 변수를 생성해준다. secret-key, access-token 1 hour, refresh-token 12 hour
    // 어노테이션 value를 사용해준다. 이때 롬복꺼 말고, spring거로 해줘야한다. 이후 따옴표 달러 대괄호 이후. yaml파일에 접근해준다.
    @Value("${token.secret.key}")
    private String secretKey;
    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusOneHour;
    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusTwelveHour;
    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        // 만료시간 먼저 정해야겠죠.
        var expired = LocalDateTime.now().plusHours(accessTokenPlusOneHour); // 만료시간 설정.
        var expiredAt = Date.from(expired.atZone(ZoneId.systemDefault()).toInstant()); // 만료시간 LocalDate 버전을 = Date 버전으로 바꾼거.
        // key 생성
        var key = Keys.hmacShaKeyFor(secretKey.getBytes()); // key 생성.
        // token 생성
        var jwtToken = Jwts.builder() // jwts(토큰빌드), signWith(서명키, 인코딩방식), setClaims(어떤 데이터를 숨길 것이냐),setExpiration((date버전)만료시간), compact() =  build(); = 발행;
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expired) // 여기는 localDate 버전을 넣어줘야 한다.
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        var expired = LocalDateTime.now().plusHours(refreshTokenPlusTwelveHour);
        var expiredAt = Date.from(expired.atZone(ZoneId.systemDefault()).toInstant());
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt) // date
                .compact();
        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expired) // localDate
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        // 검증 코드죠.
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var parser = Jwts.parserBuilder() // parser(key) = 해체작업 준비.
                .setSigningKey(key)
                .build();
        try {
            var result = parser.parseClaimsJws(token); // parser(key)-> (발행된 token) 해체 완료  = 결과물(에러일수도, 아닐수도).
            return new HashMap<String, Object>(result.getBody());
        } catch (Exception e) {
            if (e instanceof SignatureException) {
                // 토큰이 유효하지 않을 때
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
            } else if (e instanceof ExpiredJwtException) {
                // 만료시간이 다 됐을 때
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            } else {
                // 그외 에러.
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }
}
