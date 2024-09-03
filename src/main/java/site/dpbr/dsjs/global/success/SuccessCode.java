package site.dpbr.dsjs.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    UPLOAD_AND_FETCH_SUCCESS(201, "UPLOAD_AND_FETCH_SUCCESS", "엑셀 파일에 있는 캐릭터들의 정보를 불러오고 DB에 저장하는데 성공했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
