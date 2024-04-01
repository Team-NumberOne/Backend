package com.numberone.backend.swagger;


import com.numberone.backend.swagger.dto.request.RefreshRequestDummy;
import com.numberone.backend.swagger.dto.request.LoginRequestDummy;
import com.numberone.backend.swagger.dto.response.RefreshResponseDummy;
import com.numberone.backend.swagger.dto.response.LoginResponseDummy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "login", description = "인증 관련 API")
@RestController
@RequestMapping("/token")
public class SwaggerDocumentController {// 필터에서 처리하는 api의 경우 swagger에 등록되지 않아서 오로지 swagger 문서화를 위한 컨트롤러입니다.

    @Operation(summary = "카카오 토큰을 이용하여 서버 JWT 토큰 발급받기", description =
            """
                    카카오 토큰을 body 에 담아서 post 요청 해주세요.
                                
                    앞으로 서버 요청 시에 사용할 수 있는 JWT 토큰이 발급됩니다.
                                
                    이후 서버에 API 요청시 이 JWT 토큰을 같이 담아서 요청해야 정상적으로 API가 호출 됩니다.  
                    """)
    @PostMapping("/kakao")
    public LoginResponseDummy loginKakao(@RequestBody LoginRequestDummy loginRequestDummy) {
        return null;
    }

    @Operation(summary = "네이버 토큰을 이용하여 서버 JWT 토큰 발급받기", description =
            """
                    네이버 토큰을 body 에 담아서 post 요청 해주세요.
                                
                    앞으로 서버 요청 시에 사용할 수 있는 JWT 토큰이 발급됩니다.
                                
                    이후 서버에 API 요청시 이 JWT 토큰을 같이 담아서 요청해야 정상적으로 API가 호출 됩니다.  
                    """)
    @PostMapping("/naver")
    public LoginResponseDummy loginNaver(@RequestBody LoginRequestDummy loginRequestDummy) {
        return null;
    }

    @Operation(summary = "만료된 JWT 토큰 갱신하기", description =
            """
                    만료된 JWT 토큰을 body 에 담아서 post 요청 해주세요.
                                
                    새로 사용할 수 있는 JWT 토큰이 발급됩니다.
                    """)
    @PostMapping("/refresh")
    public RefreshResponseDummy refresh(@RequestBody RefreshRequestDummy refreshRequestDummy) {
        return null;
    }
}
