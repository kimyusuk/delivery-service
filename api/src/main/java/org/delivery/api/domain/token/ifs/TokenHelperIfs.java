package org.delivery.api.domain.token.ifs;

import org.delivery.api.domain.token.model.TokenDto;

import java.util.Map;

public interface TokenHelperIfs {
    TokenDto issueAccessToken(Map<String, Object> data); // data => AccessToken

    TokenDto issueRefreshToken(Map<String, Object> data); // data => RefreshToken

    Map<String, Object> validationTokenWithThrow(String token); // (Access or Refresh)Token => Token이 변조된 Token이 아닌지에 대한 검증.
}
