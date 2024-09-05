package site.dpbr.dsjs.domain.admin.exception;


import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class AdminDeduplicateException extends ServiceException {
    public AdminDeduplicateException() {
        super(ErrorCode.ADMIN_DUPLICATE);
    }
}
