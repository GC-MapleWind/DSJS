package site.dpbr.dsjs.domain.admin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID adminId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String adminPassword;

    @Column
    private String refreshToken;

    public static Admin create(String username, String adminPassword) {
        return Admin.builder()
                .username(username)
                .adminPassword(adminPassword)
                .build();
    }

    @Builder
    public Admin(String username, String adminPassword) {
        this.username = username;
        this.adminPassword = adminPassword;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
