package com.hnatiuk.screencapture

sealed class ScreenshotException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(throwable: Throwable) : super(throwable)

    class ScreenTakeException(throwable: Throwable) : ScreenshotException(throwable)

    object NoContextReference : ScreenshotException("Can't make a screenshot because Context was garbage collected")

    object FailedToCreateMediaProjection : ScreenshotException("Failed to create MediaProjection object")

    object FailedToAcquireImage : ScreenshotException("ImageReader::acquireLatestImage returned null")

    object NoMediaProjectionPermission : ScreenshotException("Request permission for media projection first")
}