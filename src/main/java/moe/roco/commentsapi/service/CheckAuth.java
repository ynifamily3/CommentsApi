package moe.roco.commentsapi.service;

import java.util.Base64;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

    public String getValidUser(String authType, String authorization) {
        Base64.Decoder decoder = Base64.getDecoder();
        if (authorization == null || authType == null) {
            return null;
        }
        switch (authType) {
            case "twitter":
            case "kakao":
                try {
                    Algorithm algorithm = Algorithm.HMAC256(SESSION_SECRET);
                    JWTVerifier verifier = JWT.require(algorithm)
                            .build();
                    DecodedJWT jwt = verifier.verify(authorization);
                    String json = new String(decoder.decode(jwt.getPayload()));
                    JSONParser parser = new JSONParser();
                    JSONObject object = (JSONObject) parser.parse(json);
                    String id = String.valueOf(object.get("id"));
                    long expires = (long) object.get("expires");
                    var date = new Date(expires);
                    var currentDate = new Date();
                    if (date.getTime() - currentDate.getTime() < 0) {
                        log.warn("세션 만료..");
                        return null;
                    }
                    return id;
                } catch (JWTVerificationException exception) {
                    log.info("토큰 유효하지 않음.");
                    log.warn(exception.getMessage());
                    return null;
                } catch (ParseException e) {
                    log.warn("JSON 파싱 실패");
                    return null;
                } catch (Exception e) {
                    log.warn("기타: " + e.getMessage());
                    return null;
                }
            default:
                log.warn("디폴트");
                return null;
        }
    }

    public boolean isLogin(String authType, String authorization, String providedUserId) {
        if (authorization == null || authType == null || providedUserId == null) {
            return false;
        }
        switch (authType) {
            case "twitter":
            case "kakao":
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
