package info.hellovass.gallerydownloader.library

import java.security.MessageDigest


object DigestUtils {

    private const val HEX_CHARS = "0123456789ABCDEF"

    fun sha512(input: String) = hashString("SHA-512", input)

    fun sha256(input: String) = hashString("SHA-256", input)

    fun sha1(input: String) = hashString("SHA-1", input)

    fun md5(input: String) = hashString("MD5", input)

    private fun hashString(type: String, input: String): String {

        val bytes = MessageDigest.getInstance(type).digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }
}
