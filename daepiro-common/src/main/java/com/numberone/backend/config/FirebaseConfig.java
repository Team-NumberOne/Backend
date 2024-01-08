package com.numberone.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.numberone.backend.exception.conflict.FirebaseInitializationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

    @Value("${fcm.key.scope}")
    private String fireBaseScope;

    @PostConstruct
    public void firebaseInitialization() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                                    .createScoped(List.of(fireBaseScope))
                    ).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application initialization is successfully completed!");
            }
        } catch (Exception e) {
            log.error("firebaseInitialization error {}", e.getMessage());
            throw new FirebaseInitializationException();
        }
    }
}
