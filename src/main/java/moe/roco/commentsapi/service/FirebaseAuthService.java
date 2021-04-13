package moe.roco.commentsapi.service;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseAuthService {

    public boolean verifyIdToken(String idToken, String providedId) {
        try {
            var s = FirebaseAuth.getInstance().verifyIdToken(idToken, true);
            String uid = s.getUid();
            return providedId.equals(uid);
        } catch (FirebaseAuthException fe) {
            return false;
        } catch (Exception e) {
            log.error("예외: ");
            log.error(e.getLocalizedMessage());
            return false;
        }
    }
}


