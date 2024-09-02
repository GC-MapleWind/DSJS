package site.dpbr.dsjs.domain.shared.exception;


import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class PasswordNotMatchException extends ServiceException {
    public PasswordNotMatchException() {
        super(ErrorCode.PASSWORD_NOT_MATCH);
    }
}
