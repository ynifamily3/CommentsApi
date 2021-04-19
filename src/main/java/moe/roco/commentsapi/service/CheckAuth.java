package moe.roco.commentsapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckAuth {
    @Value("${SESSION_SECRET}")
    private String SESSION_SECRET;

    private final NaverAuthService naverAuthService;

    public boolean isLogin(String authType, String authorization) {
        if (authorization == null || authType == null) {
            return false;
        }
        switch (authType) {
            case "naver":
                try {
                    var re = naverAuthService.getUserProfile(authorization).getMessage();
                    return re.equals("success");
                } catch (Exception e) {
                    return false;
                }
            default:
                return false;
        }
    }

    public boolean isLogin(String authType, String authorization, String providedUserId) {
        if (authorization == null || authType == null || providedUserId == null) {
            return false;
        }
        switch (authType) {
            case "twitter":
                try {
                    Algorithm algorithm = Algorithm.HMAC256(SESSION_SECRET);
                    JWTVerifier verifier = JWT.require(algorithm)
                            .build();
                    DecodedJWT jwt = verifier.verify(authorization);
                    return true;
                } catch (JWTVerificationException exception) {
                    log.info("토큰 유효하지 않음.");
                    log.warn(exception.getMessage());
                } catch (Exception e) {
                    return false;
                }
            default:
                return false;
        }
    }
}
