package site.dpbr.dsjs.domain.character.presentation;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import site.dpbr.dsjs.domain.character.presentation.dto.request.CharacterFetchRequest;
import site.dpbr.dsjs.domain.character.presentation.dto.response.SearchCharacterInfoResponse;
import site.dpbr.dsjs.domain.character.usecase.ExportCharacterImages;
import site.dpbr.dsjs.domain.character.usecase.ExportCharacterListToExcel;
import site.dpbr.dsjs.domain.character.usecase.FindAllCharactersInfo;
import site.dpbr.dsjs.domain.character.usecase.SearchCharacterInfo;
import site.dpbr.dsjs.domain.character.usecase.UpdateAllCharacterInfo;
import site.dpbr.dsjs.domain.character.usecase.UploadAndFetchAllCharacterInfo;
import site.dpbr.dsjs.domain.character.usecase.UploadAndFetchInfo;
import site.dpbr.dsjs.global.error.ErrorResponse;

@RestController
@RequestMapping("v1/character")
@RequiredArgsConstructor
public class CharacterController {

	private final UploadAndFetchInfo uploadAndFetchInfo;
	private final UploadAndFetchAllCharacterInfo uploadAndFetchAllCharacterInfo;
	private final ExportCharacterListToExcel exportCharacterListToExcel;
	private final ExportCharacterImages exportCharacterImages;
	private final FindAllCharactersInfo findAllCharactersInfo;
	private final SearchCharacterInfo searchCharacterInfo;
	private final UpdateAllCharacterInfo updateAllCharacterInfo;


	@Operation(summary = "캐릭터 정보 API 불러오기", description = "케릭터의 정보를, Nexon Open API를 통해 필요한 모든 정보를 호출하고 DB에 저장합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201"),
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "/uploadAndFetch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadAndFetchInfo(CharacterFetchRequest characterFetchRequest) throws IOException {
		return ResponseEntity.ok(uploadAndFetchInfo.execute(characterFetchRequest.date(), characterFetchRequest.characterName()));
	}

	@Operation(summary = "캐릭터 목록 엑셀 파일 업로드 및 전체 정보 로드", description = "캐릭터 목록 엑셀 파일을 DB에 저장하고, Nexon Open API를 통해 필요한 모든 정보를 호출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "/uploadAndFetchAllCharacter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadAndFetchAllCharacterInfo(@RequestParam("date") String date,
		@RequestPart("file") MultipartFile file) throws IOException {
		return ResponseEntity.ok(uploadAndFetchAllCharacterInfo.execute(date, file));
	}

	@Operation(summary = "캐릭터 정보 엑셀파일 추출", description = "DB에 저장된 캐릭터 목록을 엑셀 파일로 추출합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/export-characters/excel")
	public ResponseEntity<byte[]> exportCharacterListToExcel() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=단생조사.xlsx");

		return ResponseEntity.ok()
			.headers(headers)
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.body(exportCharacterListToExcel.execute());
	}

	@Operation(summary = "캐릭터 이미지 파일 추출", description = "DB에 저장된 캐릭터 이미지를 압축하여 다운로드합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping(value = "/export-characters/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<byte[]> exportCharacterImages(@RequestPart("file") MultipartFile file, String date) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=character_images.zip");

		return ResponseEntity.ok()
			.headers(headers)
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.body(exportCharacterImages.execute(file, date));
	}

	@Operation(summary = "캐릭터 정보 검색", description = "DB에 저장된 캐릭터 정보를 검색하여 DB에서 불러옵니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/search")
	public ResponseEntity<SearchCharacterInfoResponse> searchCharacterInfo(@RequestParam String characterName) {
		return ResponseEntity.ok(searchCharacterInfo.execute(characterName));
	}

	@Operation(summary = "캐릭터 정보 업데이트", description = "DB에 저장된 모든 캐릭터의 정보를 업데이트 합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PatchMapping(value = "/update")
	public ResponseEntity<String> updateAllCharacterInfo() {
		return ResponseEntity.ok(updateAllCharacterInfo.execute());
	}

	@Operation(summary = "모든 캐릭터 정보 탐색", description = "DB에 저장된 모든 캐릭터 정보를 탐색합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping(value = "/find-all")
	public ResponseEntity<List<SearchCharacterInfoResponse>> findAllCharactersInfo() {
		return ResponseEntity.ok(findAllCharactersInfo.execute());
	}
}
