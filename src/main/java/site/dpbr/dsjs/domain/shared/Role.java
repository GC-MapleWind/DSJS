package site.dpbr.dsjs.domain.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),

    ROLE_HEAD_EVENT("ROLE_HEAD_EVENT"), // 행사팀장
    ROLE_HEAD_PERSONNEL("ROLE_HEAD_PERSONNEL"), // 인사팀장
    ROLE_HEAD_RELATIONS("ROLE_HEAD_RELATIONS"), // 홍보팀장
    ROLE_PRESIDENT("ROLE_PRESIDENT"), // 회장
    ROLE_VICE_PRESIDENT("ROLE_VICE_PRESIDENT"), // 부회장

    ROLE_EVENT("ROLE_EVENT"), // 행사팀
    ROLE_PERSONNEL("ROLE_PERSONNEL"), // 인사팀
    ROLE_RELATIONS("ROLE_RELATIONS"), // 홍보팀

    ROLE_USER("ROLE_USER"); // 회원

    private final String role;
}
