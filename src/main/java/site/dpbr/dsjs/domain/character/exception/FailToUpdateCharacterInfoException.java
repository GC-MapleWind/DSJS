package site.dpbr.dsjs.domain.character.exception;

import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class FailToUpdateCharacterInfoException extends ServiceException {
    public FailToUpdateCharacterInfoException() {
        super(ErrorCode.FAIL_TO_UPDATE_CHARACTER_INFO);
    }
}
