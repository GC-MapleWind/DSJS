package site.dpbr.dsjs.domain.admin.exception;


import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class AdminNotFoundException extends ServiceException {
    public AdminNotFoundException() {
        super(ErrorCode.ADMIN_NOT_FOUND);
    }
}
