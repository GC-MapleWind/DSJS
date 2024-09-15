package site.dpbr.dsjs.domain.admin.usecase;


import site.dpbr.dsjs.domain.shared.Role;
import site.dpbr.dsjs.domain.shared.dto.response.RegisterResponse;

public interface AdminRegister {
    RegisterResponse execute(String username, String password, Role role);
}
