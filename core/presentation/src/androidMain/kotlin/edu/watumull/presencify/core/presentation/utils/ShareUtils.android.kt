package edu.watumull.presencify.core.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import edu.watumull.presencify.core.presentation.utils.ShareUtils.shareFile
import edu.watumull.presencify.core.presentation.utils.ShareUtils.shareText
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.ImageFormat
import io.github.vinceglb.filekit.compressImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import java.io.File
import java.io.FileOutputStream


/**
 * Android-specific implementation of [edu.watumull.presencify.core.presentation.ShareUtils].
 *
 * This utility enables sharing of text and files (PDF, text, image) through Android's
 * native `Intent`-based sharing system.
 */
actual object ShareUtils {

    /**
     * Provider function to retrieve the current [Activity].
     * This must be set before using [shareText] or [shareFile].
     */
    private var activityProvider: () -> Activity = {
        throw IllegalArgumentException(
            "You need to implement the 'activityProvider' to provide the required Activity. " +
                    "Just make sure to set a valid activity using " +
                    "the 'setActivityProvider()' method.",
        )
    }

    /**
     * Sets the activity provider function to be used internally for context retrieval.
     *
     * This is required to initialize before calling any sharing methods.
     *
     * @param provider A lambda that returns the current [Activity].
     */
    fun setActivityProvider(provider: () -> Activity) {
        activityProvider = provider
    }

    /**
     * Shares plain text content using an Android share sheet (`Intent.ACTION_SEND`).
     *
     * @param text The text content to share.
     */
    actual suspend fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val intentChooser = Intent.createChooser(intent, null)
        activityProvider.invoke().startActivity(intentChooser)
    }

    /**
     * Shares a file (e.g. PDF, text, image) using Android's file sharing mechanism.
     *
     * If the file is an image, it is compressed before sharing.
     * The file is temporarily saved to internal cache and shared using a `FileProvider`.
     *
     * @param file A [ShareFileModel] containing file metadata and binary content.
     */
    @OptIn(ExperimentalResourceApi::class)
    actual suspend fun shareFile(file: ShareFileModel) {
        val context = activityProvider.invoke().application.baseContext

        try {
            withContext(Dispatchers.IO) {
                val compressedBytes = if (file.mime == MimeType.IMAGE) {
                    FileKit.compressImage(
                        file.bytes,
                        imageFormat = ImageFormat.PNG,
                        quality = 100,
                        maxWidth = 1024,
                        maxHeight = 1024,
                    )
                } else {
                    file.bytes
                }

                val savedFile = saveFile(file.fileName, compressedBytes, context = context)
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    savedFile,
                )

                withContext(Dispatchers.Main) {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_STREAM, uri)
                        flags += Intent.FLAG_ACTIVITY_NEW_TASK
                        flags += Intent.FLAG_GRANT_READ_URI_PERMISSION
                        setDataAndType(uri, file.mime.toAndroidMimeType())
                    }
                    val chooser = Intent.createChooser(intent, null)
                    activityProvider.invoke().startActivity(chooser)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Shares an ImageBitmap with other applications.
     *
     * @param title The title to be used in the share dialog
     * @param image The ImageBitmap to be shared
     */
    actual suspend fun shareImage(title: String, image: ImageBitmap) {
        val context = activityProvider.invoke().application.baseContext

        val uri = saveImage(image.asAndroidBitmap(), context)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            setDataAndType(uri, "image/png")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val shareIntent = Intent.createChooser(sendIntent, title)
        activityProvider.invoke().startActivity(shareIntent)
    }

    /**
     * Shares an image with other applications using raw byte data.
     *
     * @param title The title to use when sharing the image
     * @param byte The raw image data as ByteArray
     */
    @OptIn(ExperimentalResourceApi::class)
    actual suspend fun shareImage(title: String, byte: ByteArray) {
        val context = activityProvider.invoke().application.baseContext
        val imageBitmap = byte.decodeToImageBitmap()

        val uri = saveImage(imageBitmap.asAndroidBitmap(), context)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            setDataAndType(uri, "image/png")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val shareIntent = Intent.createChooser(sendIntent, title)
        activityProvider.invoke().startActivity(shareIntent)
    }

    /**
     * Saves the provided byte array as a temporary file in the internal cache directory.
     *
     * @param name The name of the file to be saved.
     * @param data Byte array representing the file content.
     * @param context Android [Context] used to access the cache directory.
     * @return The saved [File] object.
     */
    private fun saveFile(name: String, data: ByteArray, context: Context): File {
        val cache = context.cacheDir
        val savedFile = File(cache, name)
        savedFile.writeBytes(data)
        return savedFile
    }

    /**
     * Maps [MimeType] to a corresponding Android MIME type string.
     *
     * @return Android-compatible MIME type string.
     */
    private fun MimeType.toAndroidMimeType(): String = when (this) {
        MimeType.PDF -> "application/pdf"
        MimeType.TEXT -> "text/plain"
        MimeType.IMAGE -> "image/*"
    }

    /**
     * Saves a Bitmap to a temporary file in the application's cache directory.
     *
     * @param image The Android Bitmap to save
     * @param context The Android context
     * @return A content URI for the saved image, or null if saving failed
     */
    private suspend fun saveImage(image: Bitmap, context: Context): Uri? {
        return withContext(Dispatchers.IO) {
            try {
                val imagesFolder = File(context.cacheDir, "images")
                imagesFolder.mkdirs()
                val file = File(imagesFolder, "shared_image.png")

                val stream = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()

                FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}