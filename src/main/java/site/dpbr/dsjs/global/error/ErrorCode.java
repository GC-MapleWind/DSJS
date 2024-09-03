package site.dpbr.dsjs.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMPTY_FILE(400, "EMPTY_FILE", "빈 파일 입니다."),
    FAIL_TO_CONNECT_NEXON_OPEN_API(400, "FAILED_TO_CONNECT", "NEXON OPEN API에 연결을 실패했습니다."),
    FAIL_TO_UPDATE_CHARACTER_INFO(400, "FAIL_TO_UPDATE_CHARACTER_INFO", "캐릭터 정보를 업데이트하는데 실패했습니다."),
    FAIL_TO_SAVE_CHARACTER(400, "FAIL_TO_SAVE_CHARACTER", "캐릭터 정보를 DB에 저장하는데 실패했습니다."),
    IMAGE_DOWNLOAD_FAIL(400, "IMAGE_DOWNLOAD_FAIL", "이미지 다운로드에 실패했습니다."),
    INVALID_TOKEN(401, "INVALID_TOKEN", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(403, "EXPIRED_TOKEN", "만료된 토큰입니다."),
    PASSWORD_NOT_MATCH(403, "PASSWORD_NOT_MATCH", "비밀번호가 일치하지 않습니다."),
    ADMIN_NOT_FOUND(404, "ADMIN_NOT_FOUND", "존재하지 않는 관리자입니다."),
    CHARACTER_NOT_FOUND(404, "CHARACTER_NOT_FOUND", "캐릭터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
