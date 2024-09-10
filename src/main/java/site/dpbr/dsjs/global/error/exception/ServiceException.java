package site.dpbr.dsjs.global.error.exception;

import lombok.Getter;
import site.dpbr.dsjs.global.error.ErrorCode;

@Getter
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;

     public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
