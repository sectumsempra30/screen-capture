package com.hnatiuk.screenshoter

sealed class ScreenshotException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(throwable: Throwable) : super(throwable)

    class NoActivityReference :
        ScreenshotException("Can't make a screenshot because Activity was garbage collected")

    class FailedToCreateMediaProjection :
        ScreenshotException("Failed to create MediaProjection object")

    class FailedToAcquireImage :
        ScreenshotException("ImageReader::acquireLatestImage returned null")

    class ProjectionNotSupported :
        ScreenshotException("MediaProjection not supported on this API version")

    class PixelCopyNotSupported : ScreenshotException("PixelCopy not supported on this API version")

    class PixelCopyFailed(code: Int) : ScreenshotException("PixelCopy failed, result code = $code")

    class FallbackStrategiesFailed : ScreenshotException("All fallback strategies failed")

    class ScreenTakeException(throwable: Throwable) : ScreenshotException(throwable)
}