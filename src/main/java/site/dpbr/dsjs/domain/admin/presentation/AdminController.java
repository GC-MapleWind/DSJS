package site.dpbr.dsjs.domain.admin.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dpbr.dsjs.domain.admin.presentation.dto.request.AdminLoginRequest;
import site.dpbr.dsjs.domain.admin.presentation.dto.request.AdminRegisterRequest;
import site.dpbr.dsjs.domain.admin.presentation.dto.response.AdminLoginResponse;
import site.dpbr.dsjs.domain.admin.usecase.AdminLogin;
import site.dpbr.dsjs.domain.admin.usecase.AdminRegister;
import site.dpbr.dsjs.domain.shared.dto.request.RefreshRequest;
import site.dpbr.dsjs.domain.shared.dto.response.RegisterResponse;
import site.dpbr.dsjs.global.error.ErrorResponse;
import site.dpbr.dsjs.global.jwt.JwtProvider;
import site.dpbr.dsjs.global.jwt.dto.JwtResponse;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminRegister adminRegister;
    private final AdminLogin adminLogin;
    private final JwtProvider jwtProvider;

    @Operation(summary = "관리자 테스트 회원가입", description = "테스트를 위한 관리자 회원가입 기능입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/test-register")
    public ResponseEntity<RegisterResponse> register(@RequestBody AdminRegisterRequest request) {
        return ResponseEntity.ok(adminRegister.execute(request.username(), request.password()));
    }

    @Operation(summary = "관리자 로그인", description = "관리자 계정으로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        AdminLoginResponse response = adminLogin.execute(request.id(), request.password());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "(관리자) 관리자 토큰 갱신", description = "관리자 계정의 액세스토큰을 갱신합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(jwtProvider.refreshAccessToken(request.refreshToken()));
    }
}
