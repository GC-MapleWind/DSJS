package site.dpbr.dsjs.domain.character.usecase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;

@Service
@RequiredArgsConstructor
public class ExportCharacterListToExcel {

	private final CharacterRepository characterRepository;

	public byte[] execute() throws IOException {
		List<Character> characters = characterRepository.findAll();

		// Excel Workbook 생성
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("단생조사");

		// 헤더 행 생성
		Row headerRow = sheet.createRow(0);
		String[] columns = {"닉네임", "성별", "월드", "직업", "레벨", "유니온", "유니온 아티펙트", "무릉", "전투력"};
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
		}

		// 데이터 행 생성
		int rowNum = 1;
		for (Character character : characters) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(character.getName() != null ? character.getName() : "");
			row.createCell(1).setCellValue(character.getGender() != null ? character.getGender() : "");
			row.createCell(2).setCellValue(character.getWorld() != null ? character.getWorld() : "");
			row.createCell(3).setCellValue(character.getJob() != null ? character.getJob() : "");
			row.createCell(4).setCellValue(character.getLevel() != null ? String.valueOf(character.getLevel()) : "");
			row.createCell(5)
				.setCellValue(character.getUnionLevel() != null ? String.valueOf(character.getUnionLevel()) : "");
			row.createCell(6)
				.setCellValue(
					character.getUnionArtifactLevel() != null ? String.valueOf(character.getUnionArtifactLevel()) : "");
			row.createCell(7)
				.setCellValue(
					character.getMuLungFloor() != null ? String.valueOf(character.getUnionArtifactLevel()) : "");
			row.createCell(8)
				.setCellValue(character.getCombatPower() != null ? String.valueOf(character.getCombatPower()) : "");
		}

		// Excel 파일을 ByteArrayOutputStream에 쓰기
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		workbook.close();

		// 파일 데이터를 byte[]로 변환하여 반환
		return out.toByteArray();
	}
}
