package site.dpbr.dsjs.domain.admin.presentation.dto.request;

import site.dpbr.dsjs.domain.shared.Role;

public record AdminRegisterRequest(String username,
                                   String password,
                                   Role role) {
}
