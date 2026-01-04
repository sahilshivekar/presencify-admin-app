package edu.watumull.presencify.core.presentation.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.openFileSaver
import io.github.vinceglb.filekit.saveImageToGallery
import io.github.vinceglb.filekit.write

/**
 * JVM-specific implementation of [ShareUtils].
 *
 * This object simulates file sharing by prompting the user with a "Save As" dialog
 * using [FileKit.openFileSaver]. It allows the user to save the content locally,
 * which is the most suitable alternative to "sharing" in desktop environments.
 */
actual object ShareUtils {

    /**
     * Prompts the user to save the given text content as a file on their system.
     *
     * This method uses [FileKit.openFileSaver] to open a native "Save As" dialog,
     * and writes the provided text to the selected file location.
     *
     * @param text The plain text content the user will save to disk.
     */
    actual suspend fun shareText(text: String) {
        val newFile = FileKit.openFileSaver(
            suggestedName = "text.txt",
        )
        newFile?.write(text.encodeToByteArray())
    }

    /**
     * Prompts the user to save a binary file (e.g., image, PDF) to their local system.
     *
     * This is used as a desktop-friendly alternative to file sharing, using
     * [FileKit.openFileSaver] to let the user choose the destination file path.
     *
     * @param file The file to be "shared", including its filename and byte content.
     */
    actual suspend fun shareFile(file: ShareFileModel) {
        val newFile = FileKit.openFileSaver(
            suggestedName = file.fileName,
        )
        newFile?.write(file.bytes)
    }

    /**
     * Shares an ImageBitmap by saving it to the system's image gallery.
     *
     * @param title The title to use when sharing the image
     * @param image The ImageBitmap to be shared
     */
    actual suspend fun shareImage(title: String, image: ImageBitmap) {
        image.asSkiaBitmap().readPixels()?.let {
            FileKit.saveImageToGallery(
                bytes = it,
                filename = "$title.png",
            )
        }
    }

    /**
     * Shares an image by saving it to the system's image gallery.
     *
     * @param title The title to use when sharing the image
     * @param byte The raw image data as ByteArray
     */
    actual suspend fun shareImage(title: String, byte: ByteArray) {
        FileKit.saveImageToGallery(
            bytes = byte,
            filename = "$title.png",
        )
    }
}