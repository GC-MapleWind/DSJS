package site.dpbr.dsjs.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
	UPLOAD_AND_FETCH_SUCCESS(201, "UPLOAD_AND_FETCH_SUCCESS", "캐릭터 정보를 불러오고 DB에 저장하는데 성공했습니다."),
	UPLOAD_AND_FETCH_ALL_CHARACTER_SUCCESS(201, "UPLOAD_AND_FETCH_ALL_CHARACTER_SUCCESS",
		"엑셀 파일에 있는 모든 캐릭터의 정보를 불러오고 DB에 저장하는데 성공했습니다."),
	UPDATE_SUCCESS(201, "UPDATE_SUCCESS", "모든 캐릭터의 정보를 업데이트하는데 성공했습니다.");

	private final int httpStatus;
	private final String code;
	private final String message;
}
