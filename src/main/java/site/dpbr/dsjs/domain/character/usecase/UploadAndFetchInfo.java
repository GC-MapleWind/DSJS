package site.dpbr.dsjs.domain.character.usecase;

import java.io.IOException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterInfoResponse;
import site.dpbr.dsjs.global.success.SuccessCode;

@Service
@RequiredArgsConstructor
public class UploadAndFetchInfo {

	private final CharacterRepository characterRepository;
	private final FetchCharacterInfo fetchCharacterInfo;
	private final FetchOcid fetchOcid;

	public String execute(String date, String characterName) throws IOException {
		String ocid = fetchOcid.execute(characterName);
		CharacterInfoResponse characterInfoResponse = fetchCharacterInfo.execute(ocid, date);
		characterRepository.save(Character.create(ocid, characterName, characterInfoResponse));

		return SuccessCode.UPLOAD_AND_FETCH_SUCCESS.getMessage();
	}
}
