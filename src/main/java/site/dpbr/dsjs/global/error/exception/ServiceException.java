package site.dpbr.dsjs.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.dpbr.dsjs.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;
}
