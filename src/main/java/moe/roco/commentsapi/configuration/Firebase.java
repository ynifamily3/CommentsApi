package moe.roco.commentsapi.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class Firebase {
    @Bean
    public void initializeFirebaseApp() throws IOException {
        log.info("Firebase 설정 중...");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();
        FirebaseApp.initializeApp(options);
    }
}
