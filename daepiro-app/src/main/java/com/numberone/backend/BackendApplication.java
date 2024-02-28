package com.numberone.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@ServletComponentScan
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})//https://stackoverflow.com/questions/74593433/consider-defining-a-bean-of-type-org-springframework-cloud-openfeign-feignconte
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
