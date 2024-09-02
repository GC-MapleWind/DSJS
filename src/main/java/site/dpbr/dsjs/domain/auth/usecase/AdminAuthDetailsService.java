package site.dpbr.dsjs.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.admin.domain.Admin;
import site.dpbr.dsjs.domain.admin.domain.repository.AdminRepository;
import site.dpbr.dsjs.domain.admin.exception.AdminNotFoundException;
import site.dpbr.dsjs.domain.auth.domain.AuthDetails;
import site.dpbr.dsjs.domain.shared.Role;


@Service
@RequiredArgsConstructor
public class AdminAuthDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    public AuthDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(AdminNotFoundException::new);
        return new AuthDetails(admin.getAdminId(), admin.getUsername(), Role.ROLE_ADMIN);
    }
}
