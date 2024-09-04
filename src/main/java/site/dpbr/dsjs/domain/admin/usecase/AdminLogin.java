package site.dpbr.dsjs.domain.admin.usecase;


import site.dpbr.dsjs.domain.admin.presentation.dto.response.AdminLoginResponse;

public interface AdminLogin {
    AdminLoginResponse execute(String id, String password);
}
