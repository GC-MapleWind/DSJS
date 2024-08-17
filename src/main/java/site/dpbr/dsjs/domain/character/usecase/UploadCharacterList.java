package site.dpbr.dsjs.domain.character.usecase;

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
import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;
import site.dpbr.dsjs.global.success.SuccessCode;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
public class UploadCharacterList {

    private final FetchOcid fetchOcid;
    private final CharacterRepository characterRepository;

    public String execute(MultipartFile file) {
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
                    if (cnt == 4) {
                        sleep(1000);
                        cnt = 0;
                    }

                    String characterName = cell.getStringCellValue();
                    String ocid = fetchOcid.execute(characterName);

                    characterRepository.save(Character.create(ocid, characterName));

                    cnt++;
                }
            }

            workbook.close();
        } catch (IOException | InterruptedException e) {
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return SuccessCode.UPLOAD_FILE_SUCCESS.getMessage();
    }
}
