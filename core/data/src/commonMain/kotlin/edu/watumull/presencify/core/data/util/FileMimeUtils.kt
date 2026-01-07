package edu.watumull.presencify.core.data.util

object FileMimeUtils {

    fun getMimeType(bytes: ByteArray): String {
        if (bytes.size < 4) return "image/jpeg" // Fallback

        val hex = bytes.take(4).joinToString("") { it.toUByte().toString(16).uppercase() }

        return when {
            hex.startsWith("89504E47") -> "image/png"
            hex.startsWith("FFD8FF") -> "image/jpeg"
            hex.startsWith("47494638") -> "image/gif"
            else -> "image/jpeg" // Default fallback
        }
    }

    fun getExtensionFromMime(mimeType: String): String {
        return when (mimeType) {
            "image/png" -> "png"
            "image/gif" -> "gif"
            "application/pdf" -> "pdf"
            else -> "jpg"
        }
    }
}