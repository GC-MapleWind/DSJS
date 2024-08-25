package site.dpbr.dsjs.domain.character.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;
import site.dpbr.dsjs.domain.character.exception.EmptyFileException;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterBasicInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterMuLungInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterStatInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterUnionInfoResponse;
import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;
import site.dpbr.dsjs.global.openAPI.Connection;
import site.dpbr.dsjs.global.success.SuccessCode;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
public class UploadAndFetchInfo {

    private final FetchOcid fetchOcid;
    private final ObjectMapper objectMapper;
    private final Connection connection;
    private final CharacterRepository characterRepository;

    public String execute(String date, MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyFileException();
        }

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 가져옵니다.

            int cnt = 0;

            // 엑셀 데이터 처리
            for (Row row : sheet) {
                for (Cell cell : row) {
                    String characterName = cell.getStringCellValue();
                    String ocid = fetchOcid.execute(characterName);

                    String path = "/character/basic?ocid=" + ocid + "&date=" + date;
                    CharacterBasicInfoResponse characterBasicInfoResponse = objectMapper.readValue(connection.execute(path),
                            CharacterBasicInfoResponse.class);

                    path = "/character/stat?ocid=" + ocid + "&date=" + date;
                    CharacterStatInfoResponse characterStatInfoResponse = objectMapper.readValue(connection.execute(path),
                            CharacterStatInfoResponse.class);

                    path = "/user/union?ocid=" + ocid + "&date=" + date;
                    CharacterUnionInfoResponse characterUnionInfoResponse = objectMapper.readValue(connection.execute(path),
                            CharacterUnionInfoResponse.class);

                    path = "/character/dojang?ocid=" + ocid + "&date=" + date;
                    CharacterMuLungInfoResponse characterMuLungInfoResponse = objectMapper.readValue(connection.execute(path),
                            CharacterMuLungInfoResponse.class);

                    Character character = Character.create(ocid, characterName);
                    character.updateBasicInfo(characterBasicInfoResponse);
                    character.updateStatInfo(characterStatInfoResponse);
                    character.updateUnionInfo(characterUnionInfoResponse);
                    character.updateMuLungInfo(characterMuLungInfoResponse);

                    characterRepository.save(character);

                    sleep(1000);
                }
            }

            workbook.close();
        } catch (IOException | InterruptedException e) {
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return SuccessCode.UPLOAD_FILE_SUCCESS.getMessage();
    }
}
