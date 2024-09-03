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
import site.dpbr.dsjs.domain.character.exception.FailToSaveCharacterException;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterInfoResponse;
import site.dpbr.dsjs.global.success.SuccessCode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadAndFetchInfo {

    private final CharacterRepository characterRepository;
    private final FetchCharacterInfo fetchCharacterInfo;
    private final FetchOcid fetchOcid;

    public String execute(String date, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new EmptyFileException();
        }

        List<String> characterNames = extractCharacterNamesFromExcel(file.getInputStream());

        characterNames.parallelStream()
                .filter(characterName -> !characterName.isEmpty())
                .forEach(characterName -> {
                    try {
                        String ocid = fetchOcid.execute(characterName);
                        CharacterInfoResponse characterInfoResponse = fetchCharacterInfo.execute(ocid, date);
                        Character character = Character.create(ocid, characterName, characterInfoResponse);
                        synchronized (this) {
                            characterRepository.save(character);
                        }
                    } catch (IOException e) {
                        throw new FailToSaveCharacterException();
                    }
                });

        return SuccessCode.UPLOAD_AND_FETCH_SUCCESS.getMessage();
    }

    private List<String> extractCharacterNamesFromExcel(InputStream inputStream) throws IOException {
        List<String> characterNames = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell cell = row.getCell(0);

            if (cell != null) characterNames.add(cell.getStringCellValue());
        }

        return characterNames;
    }
}
