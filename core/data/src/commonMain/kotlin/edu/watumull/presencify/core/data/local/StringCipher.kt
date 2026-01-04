package edu.watumull.presencify.core.data.local

/**
 * Pluggable string cipher to optionally obfuscate values before saving.
 * Mobile platforms can bind a no-op, while desktop can provide a basic obfuscation.
 */
interface StringCipher {
    fun encrypt(input: String): String
    fun decrypt(input: String): String
}

object NoOpStringCipher : StringCipher {
    override fun encrypt(input: String): String = input
    override fun decrypt(input: String): String = input
}
