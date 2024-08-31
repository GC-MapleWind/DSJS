package site.dpbr.dsjs.domain.character.exception;

import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class CharacterNotFoundException extends ServiceException {
    public CharacterNotFoundException() {
        super(ErrorCode.CHARACTER_NOT_FOUND);
    }
}
