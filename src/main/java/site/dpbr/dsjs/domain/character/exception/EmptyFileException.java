package site.dpbr.dsjs.domain.character.exception;

import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class EmptyFileException extends ServiceException {
    public EmptyFileException() {
        super(ErrorCode.FAIL_TO_CONNECT_NEXON_OPEN_API);
    }
}
