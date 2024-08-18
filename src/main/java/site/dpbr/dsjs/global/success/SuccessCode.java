package site.dpbr.dsjs.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    UPLOAD_FILE_SUCCESS(201, "UPLOAD_FILE_SUCCESS", "엑셀 파일에 있는 캐릭터들의 정보를 가져오는데 성공했습니다."),
    FETCH_BASIC_INFO_SUCCESS(200, "FETCH_BASIC_INFO_SUCCESS", "캐릭터의 기본 정보를 가져오는데 성공했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
