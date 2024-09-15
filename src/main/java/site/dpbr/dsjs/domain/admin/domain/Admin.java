package site.dpbr.dsjs.domain.admin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dpbr.dsjs.domain.shared.Role;

import java.util.UUID;

@Entity(name = "admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID adminId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String adminPassword;

    @Column
    private String refreshToken;

    public static Admin create(String username, String adminPassword, Role role) {
        return Admin.builder()
                .username(username)
                .adminPassword(adminPassword)
                .role(role)
                .build();
    }

    @Builder
    public Admin(String username, String adminPassword, Role role) {
        this.username = username;
        this.adminPassword = adminPassword;
        this.role = role;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
