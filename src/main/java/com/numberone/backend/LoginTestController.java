package com.numberone.backend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "login_test", description = "토큰 인증 테스트 관련 API")
@RestController
public class LoginTestController {
    @Operation(summary = "토큰 인증 테스트하기", description =
            """
                    서버에서 발급받은 액세스 토큰을 "Bearer [발급받은 액세스토큰]" 형태로 http 헤더의 Authorization에 넣어서 요청해주세요.
                                
                    유효한 토큰이라면 사용자의 이메일을 반환합니다.
                                
                    앞으로 서버에 api 요청을 날릴때 이렇게 Authorization 헤더에 발급받은 액세스 토큰을 담아 전달해주세요.
                    """)
    @GetMapping("/api/logintest")
    public String test(Authentication authentication) {
        return "Hello " + authentication.getName();
    }
}
