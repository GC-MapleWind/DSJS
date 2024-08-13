package site.dpbr.dsjs.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FAIL_TO_CONNECT_NEXON_OPEN_API(400, "FAILED_TO_CONNECT", "NEXON OPEN API에 연결을 실패했습니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
