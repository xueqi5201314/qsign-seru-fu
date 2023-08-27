package com.lingchen.framework.crypto

/* loaded from: classes.dex */
object HexUtil {
    private val digits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    private val emptyBytes = ByteArray(0)
    fun byte2HexStr(b: Byte): String {
        val cArr = digits
        val buf = charArrayOf(cArr[(b.toInt() ushr 4).toByte().toInt() and 15], cArr[b.toInt() and 15])
        return String(buf)
    }

    fun bytes2HexStr(bytes: ByteArray?): String? {
        if (bytes == null || bytes.isEmpty()) {
            return null
        }
        val buf = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val b = bytes[i]
            val cArr = digits
            buf[i * 2 + 1] = cArr[b.toInt() and 15]
            buf[i * 2 + 0] = cArr[(b.toInt() ushr 4).toByte().toInt() and 15]
        }
        return String(buf)
    }

    fun hexStr2Byte(str: String?): Byte {
        return if (str == null || str.length != 1) {
            0.toByte()
        } else char2Byte(str[0])
    }

    fun char2Byte(ch: Char): Byte {
        if (ch >= '0' && ch <= '9') {
            return (ch.code - '0'.code).toByte()
        }
        if (ch >= 'a' && ch <= 'f') {
            return (ch.code - 'a'.code + 10).toByte()
        }
        return if (ch >= 'A' && ch <= 'F') {
            (ch.code - 'A'.code + 10).toByte()
        } else 0.toByte()
    }

    fun hexStr2Bytes(str: String?): ByteArray {
        if (str == null || str == "") {
            return emptyBytes
        }
        val bytes = ByteArray(str.length / 2)
        for (i in bytes.indices) {
            val high = str[i * 2]
            val low = str[i * 2 + 1]
            bytes[i] = (char2Byte(high) * 16 + char2Byte(low)).toByte()
        }
        return bytes
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val begin = System.currentTimeMillis()
        for (i in 0..999999) {
            val input = "234$i"
            val inputBytes = input.toByteArray()
            val encode = bytes2HexStr(inputBytes)
            val bytes = hexStr2Bytes(encode)
            val result = String(bytes)
            if (result != input) {
                println("error:$input")
            }
        }
        println("use:" + (System.currentTimeMillis() - begin))
    }
}
