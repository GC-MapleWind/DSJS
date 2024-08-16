package site.dpbr.dsjs.global.openAPI.exception;

import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class FailToConnectNexonOpenAPIException extends ServiceException {
    public FailToConnectNexonOpenAPIException() {
        super(ErrorCode.FAIL_TO_CONNECT_NEXON_OPEN_API);
    }
}
