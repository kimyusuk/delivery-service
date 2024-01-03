package org.delivery.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.helper.JwtTokenHelper;
import org.delivery.api.domain.token.model.TokenDto;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenHelperIfs tokenHelperIfs;
    private final JwtTokenHelper jwtTokenHelper;

    public TokenDto issueAccessToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public Long validationToken(String token) {
        var map = jwtTokenHelper.validationTokenWithThrow(token); // (String)token으로 Map<String, Object> 안에 데이터들을 조회. 얘는 key : value 형태의 데이터 값을 갖고 있는 리스트형식인 얘다.
        var userId = map.get("userId"); // map에 데이터가 있다면 userId라는 키값으로 value 값이 있는지 체크 ->
        Objects.requireNonNull(userId, () -> {throw new ApiException(ErrorCode.NULL_POINT);}); // key:value중 userId라는 value 값이 없다면 없다 라는 오류를 보냄.
        return Long.parseLong(userId.toString());
    }
}
