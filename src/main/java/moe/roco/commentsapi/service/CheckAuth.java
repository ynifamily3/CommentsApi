package moe.roco.commentsapi.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckAuth {

    private final NaverAuthService naverAuthService;
    private final FirebaseAuthService firebaseAuthService;

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
                    return firebaseAuthService.verifyIdToken(authorization, providedUserId);
                } catch (Exception e) {
                    return false;
                }
            default:
                return false;
        }
    }
}
