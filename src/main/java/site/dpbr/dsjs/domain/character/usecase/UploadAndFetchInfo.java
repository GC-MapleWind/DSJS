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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class UploadAndFetchInfo {

    private final FetchOcid fetchOcid;
    private final ObjectMapper objectMapper;
    private final Connection connection;
    private final CharacterRepository characterRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public String execute(String date, MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyFileException();
        }

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // 비동기 작업을 담을 CompletableFuture 리스트
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            rowIterator.forEachRemaining(row -> {
                Iterator<Cell> cellIterator = row.iterator();
                if (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String characterName = cell.getStringCellValue();

                    if (!characterName.isEmpty()) {
                        // 비동기 작업을 CompletableFuture로 감싸고, 스레드 풀을 사용하여 병렬 처리
                        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                            try {
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
                            } catch (IOException e) {
                                throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
                            }
                        }, executorService);

                        futures.add(future); // 작업 리스트에 추가
                    }
                }
            });

            // 모든 비동기 작업이 완료될 때까지 대기
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        } catch (IOException e) {
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return SuccessCode.UPLOAD_FILE_SUCCESS.getMessage();
    }
}