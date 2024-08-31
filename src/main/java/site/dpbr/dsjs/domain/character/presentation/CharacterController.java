package site.dpbr.dsjs.domain.character.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dpbr.dsjs.domain.character.presentation.dto.request.CharacterOcidRequest;
import site.dpbr.dsjs.domain.character.usecase.*;
import site.dpbr.dsjs.global.error.ErrorResponse;

import java.io.IOException;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {

    private final FetchOcid fetchOcid;
    private final UploadAndFetchInfo uploadAndFetchInfo;
    private final ExportCharacterListToExcel exportCharacterListToExcel;
    private final ExportCharacterImages exportCharacterImages;
    private final FetchBasicInfoFromCharacterList fetchBasicInfoFromCharacterList;
    private final FetchUnionInfoFromCharacterList fetchUnionInfoFromCharacterList;
    private final FetchStatInfoFromCharacterList fetchStatInfoFromCharacterList;
    private final FetchMuLungInfoFromCharacterList fetchMuLungInfoFromCharacterList;

    @Operation(summary = "캐릭터 목록 엑셀 파일 업로드 및 전체 정보 로드", description = "캐릭터 목록 엑셀 파일을 DB에 저장하고, Nexon Open API를 통해 필요한 모든 정보를 호출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/uploadAndFetch")
    public ResponseEntity<String> uploadFile(@RequestParam("date") String date, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(uploadAndFetchInfo.execute(date, file));
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
    @PostMapping("/export-characters/image")
    public ResponseEntity<byte[]> exportCharacterImages(@RequestPart("file") MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=character_images.zip");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(exportCharacterImages.execute(file));
    }

    @Operation(summary = "캐릭터의 ocid 호출", description = "Nexon Open API를 통해 캐릭터의 ocid를 호출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/ocid")
    public ResponseEntity<String> fetchOcid(CharacterOcidRequest request) throws IOException {
        String characterName = request.characterName();
        return ResponseEntity.ok(fetchOcid.execute(characterName));
    }

    @Operation(summary = "캐릭터의 기본 정보 호출", description = "Nexon Open API를 통해 캐릭터의 기본 정보를 호출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/basic")
    public ResponseEntity<String> fetchBasicInfo() throws IOException {
        return ResponseEntity.ok(fetchBasicInfoFromCharacterList.execute());
    }

    @Operation(summary = "캐릭터의 스텟 정보 호출", description = "Nexon Open API를 통해 캐릭터의 스텟 정보를 호출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/stat")
    public ResponseEntity<String> fetchStatInfo() throws IOException {
        return ResponseEntity.ok(fetchStatInfoFromCharacterList.execute());
    }

    @Operation(summary = "캐릭터의 유니온 정보 호출", description = "Nexon Open API를 통해 캐릭터의 유니온 정보를 호출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/union")
    public ResponseEntity<String> fetchUnionInfo() throws IOException {
        return ResponseEntity.ok(fetchUnionInfoFromCharacterList.execute());
    }

    @Operation(summary = "캐릭터의 무릉도장 정보 호출", description = "Nexon Open API를 통해 캐릭터의 무릉도장 정보를 호출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/mulung")
    public ResponseEntity<String> fetchMuLungInfo() throws IOException {
        return ResponseEntity.ok(fetchMuLungInfoFromCharacterList.execute());
    }
}
