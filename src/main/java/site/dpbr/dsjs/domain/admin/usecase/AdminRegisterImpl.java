package site.dpbr.dsjs.domain.admin.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.admin.domain.Admin;
import site.dpbr.dsjs.domain.admin.domain.repository.AdminRepository;
import site.dpbr.dsjs.domain.admin.exception.AdminDeduplicateException;
import site.dpbr.dsjs.domain.shared.Role;
import site.dpbr.dsjs.domain.shared.dto.response.RegisterResponse;
import site.dpbr.dsjs.global.jwt.JwtProvider;
import site.dpbr.dsjs.global.jwt.exception.ExpiredTokenException;
import site.dpbr.dsjs.global.jwt.exception.InvalidTokenException;


@Service
@RequiredArgsConstructor
public class AdminRegisterImpl implements AdminRegister {
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;

    @Override
    public RegisterResponse execute(String username, String password) {
        if(adminRepository.existsByUsername(username)) throw new AdminDeduplicateException();

        Admin admin = Admin.create(username, passwordEncoder.encode(password));
        adminRepository.save(admin);
        generateRefreshToken(admin);

        return new RegisterResponse(true, "관리자 가입 성공");
    }

    public void generateRefreshToken(Admin admin) {
        String refreshToken = admin.getRefreshToken();
        if (refreshToken == null || !isValidToken(refreshToken)) {
            refreshToken = jwtProvider.generateRefreshToken(admin.getAdminId(), admin.getUsername(), Role.ROLE_ADMIN);
            updateRefreshToken(admin, refreshToken);
        }
    }

    public Boolean isValidToken(String token) {
        try {
            jwtProvider.validateToken(token);
            return true;
        } catch (InvalidTokenException | ExpiredTokenException e) {
            return false;
        }
    }

    private void updateRefreshToken(Admin admin, String refreshToken) {
        admin.updateRefreshToken(refreshToken);
        adminRepository.save(admin);
    }
}
