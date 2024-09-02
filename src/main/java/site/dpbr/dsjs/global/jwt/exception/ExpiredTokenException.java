package site.dpbr.dsjs.global.jwt.exception;


import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class ExpiredTokenException extends ServiceException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
