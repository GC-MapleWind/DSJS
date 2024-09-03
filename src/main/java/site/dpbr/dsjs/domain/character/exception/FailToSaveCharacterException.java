package site.dpbr.dsjs.domain.character.exception;

import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class FailToSaveCharacterException extends ServiceException {
    public FailToSaveCharacterException() {
        super(ErrorCode.FAIL_TO_SAVE_CHARACTER);
    }
}
