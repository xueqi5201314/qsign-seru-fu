package com.lingchen.framework.crypto

import com.lingchen.framework.utils.Util
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/* loaded from: classes.dex */
object MD5 {
    fun toMD5(arg: String): String {
        return toMD5(arg.toByteArray())
    }

    fun toMD5(hexarg: ByteArray?): String {
        return try {
            val hexDigits =
                charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
            val messageDigest = MessageDigest.getInstance("md5")
            messageDigest.update(hexarg)
            val result = messageDigest.digest()
            val j = result.size
            val str = CharArray(j * 2)
            var k = 0
            for (byte0 in result) {
                val k2 = k + 1
                str[k] = hexDigits[byte0.toInt() ushr 4 and 15]
                k = k2 + 1
                str[k2] = hexDigits[byte0.toInt() and 15]
            }
            String(str)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            ""
        }
    }

    fun compute(bytes: ByteArray): ByteArray {
        return try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update(bytes)
            digest.digest()
        } catch (e: Exception) {
            null!!
        }
    }

    fun compute(str: String): ByteArray {
        return compute(str.toByteArray())
    }

    fun encrypt(bytes: ByteArray): String {
        return try {
            Util.buf2hexString(Objects.requireNonNull(compute(bytes)))
        } catch (e: Throwable) {
            e.printStackTrace()
            ""
        }
    }

    @JvmStatic
    fun encrypt(str: String): String {
        return try {
            Util.buf2hexString(compute(str))
        } catch (e: Throwable) {
            e.printStackTrace()
            ""
        }
    }

    fun computeHash(arg: String): ByteArray {
        val hex = toMD5(arg)
        return Util.hexString2Buf(hex)
    }
}
