package com.numberone.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = @Server(url = "/", description = "${host.url}"),
        info = @Info(
                title = "🚀 대피로 백엔드 API 명세서",
                description = """
                        spring docs 를 이용한 API 명세서 입니다.😊
                        """,
                version = "1.0",
                contact = @Contact(
                        name = "springdoc 공식문서",
                        url = "https://springdoc.org/"
                )
        )
)
@Configuration
public class SwaggerConfig {
}
