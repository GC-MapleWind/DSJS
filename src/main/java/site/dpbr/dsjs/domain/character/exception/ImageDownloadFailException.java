package site.dpbr.dsjs.domain.character.exception;

import site.dpbr.dsjs.global.error.ErrorCode;
import site.dpbr.dsjs.global.error.exception.ServiceException;

public class ImageDownloadFailException extends ServiceException {
    public ImageDownloadFailException() {
        super(ErrorCode.IMAGE_DOWNLOAD_FAIL);
    }
}
