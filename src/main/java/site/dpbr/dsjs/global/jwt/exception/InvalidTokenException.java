package site.dpbr.dsjs.global.jwt.exception;


import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class InvalidTokenException extends ServiceException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
