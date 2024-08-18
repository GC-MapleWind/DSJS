package site.dpbr.dsjs.domain.character.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dpbr.dsjs.domain.character.presentation.dto.request.CharacterOcidRequest;
import site.dpbr.dsjs.domain.character.usecase.FetchBasicInfo;
import site.dpbr.dsjs.domain.character.usecase.FetchOcid;
import site.dpbr.dsjs.domain.character.usecase.FetchUnionInfo;
import site.dpbr.dsjs.domain.character.usecase.UploadCharacterList;
import site.dpbr.dsjs.global.error.ErrorResponse;

import java.io.IOException;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {

    private final FetchOcid fetchOcid;
    private final UploadCharacterList uploadCharacterList;
    private final FetchBasicInfo fetchBasicInfo;
    private final FetchUnionInfo fetchUnionInfo;


    @Operation(summary = "캐릭터 목록 엑셀 파일 업로드", description = "캐릭터 목록 엑셀 파일을 DB에 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadCharacterList.execute(file));
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
        return ResponseEntity.ok(fetchBasicInfo.execute());
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
        return ResponseEntity.ok(fetchUnionInfo.execute());
    }
}
