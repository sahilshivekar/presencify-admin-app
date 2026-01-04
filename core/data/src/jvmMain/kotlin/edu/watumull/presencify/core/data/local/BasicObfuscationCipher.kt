package edu.watumull.presencify.core.data.local

import java.util.Base64

/**
 * Very simple obfuscation: XOR with a derived key, then Base64.
 * This is NOT strong encryption; it only deters casual inspection.
 */
class BasicObfuscationCipher : StringCipher {
    private val keyBytes: ByteArray =
        (System.getProperty("user.name")?.reversed() ?: "presencify").toByteArray()

    override fun encrypt(input: String): String {
        val inputBytes = input.toByteArray()
        val out = ByteArray(inputBytes.size)
        for (i in inputBytes.indices) {
            out[i] = (inputBytes[i].toInt() xor keyBytes[i % keyBytes.size].toInt()).toByte()
        }
        return Base64.getEncoder().encodeToString(out)
    }

    override fun decrypt(input: String): String {
        val decoded = Base64.getDecoder().decode(input)
        val out = ByteArray(decoded.size)
        for (i in decoded.indices) {
            out[i] = (decoded[i].toInt() xor keyBytes[i % keyBytes.size].toInt()).toByte()
        }
        return String(out)
    }
}
