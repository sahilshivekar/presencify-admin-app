package edu.watumull.presencify.core.presentation.utils

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Platform-specific utilities for sharing content such as text, files, and images.
 *
 * This expect declaration should be implemented for each platform to handle
 * the specifics of sharing functionality.
 */
expect object ShareUtils {

    /**
     * Shares plain text content using the platform's native sharing mechanism.
     *
     * @param text The text content to be shared.
     */
    suspend fun shareText(text: String)

    /**
     * Shares a file using the platform's native sharing mechanism.
     *
     * @param file A [ShareFileModel] containing the file's metadata and content.
     */
    suspend fun shareFile(file: ShareFileModel)

    /**
     * Shares an image with other applications.
     *
     * @param title The title to use when sharing the image
     * @param image The ImageBitmap to be shared
     */
    suspend fun shareImage(title: String, image: ImageBitmap)

    /**
     * Shares an image with other applications using raw byte data.
     *
     * @param title The title to use when sharing the image
     * @param byte The raw image data as ByteArray
     */
    suspend fun shareImage(title: String, byte: ByteArray)
}

/**
 * Represents supported MIME types for file sharing.
 */
enum class MimeType {
    PDF,
    TEXT,
    IMAGE,
}

/**
 * Model representing a file to be shared.
 *
 * @property mime The MIME type of the file. Defaults to [MimeType.PDF].
 * @property fileName The name of the file, including its extension.
 * @property bytes The binary content of the file.
 */
data class ShareFileModel(
    val mime: MimeType = MimeType.PDF,
    val fileName: String,
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ShareFileModel

        if (mime != other.mime) return false
        if (fileName != other.fileName) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mime.hashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}