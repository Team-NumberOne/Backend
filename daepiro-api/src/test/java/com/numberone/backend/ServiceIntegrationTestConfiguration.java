package com.numberone.backend;

import com.numberone.backend.config.FirebaseConfig;
import com.numberone.backend.config.S3Config;
import com.numberone.backend.provider.s3.S3Provider;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@TestConfiguration
@SpringBootApplication
public class ServiceIntegrationTestConfiguration {

    @MockBean
    private S3Config s3Config;

    @MockBean
    private S3Provider s3Provider;

    @MockBean
    private FirebaseConfig firebaseConfig;

}
