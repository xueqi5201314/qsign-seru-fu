package com.lingchen.framework.crypto

/* loaded from: classes.dex */
object Base64Helper {
    private val encodingTable = byteArrayOf(
        65,
        66,
        67,
        68,
        69,
        70,
        71,
        72,
        73,
        74,
        75,
        76,
        77,
        78,
        79,
        80,
        81,
        82,
        83,
        84,
        85,
        86,
        87,
        88,
        89,
        90,
        97,
        98,
        99,
        100,
        101,
        102,
        103,
        104,
        105,
        106,
        107,
        108,
        109,
        110,
        111,
        112,
        113,
        114,
        115,
        116,
        117,
        118,
        119,
        120,
        121,
        122,
        48,
        49,
        50,
        51,
        52,
        53,
        54,
        55,
        56,
        57,
        43,
        47
    )
    private val decodingTable = ByteArray(128)

    init {
        for (i in 0..127) {
            decodingTable[i] = -1
        }
        for (i2 in 65..90) {
            decodingTable[i2] = (i2 - 65).toByte()
        }
        for (i3 in 97..122) {
            decodingTable[i3] = (i3 - 97 + 26).toByte()
        }
        for (i4 in 48..57) {
            decodingTable[i4] = (i4 - 48 + 52).toByte()
        }
        val bArr = decodingTable
        bArr[43] = 62
        bArr[47] = 63
    }

    fun encode(data: ByteArray, offset: Int): ByteArray {
        val bytes: ByteArray
        val realCount = data.size - offset
        val modulus = realCount % 3
        bytes = if (modulus == 0) {
            ByteArray(realCount * 4 / 3)
        } else {
            ByteArray((realCount / 3 + 1) * 4)
        }
        val dataLength = data.size - modulus
        var i = offset
        var j = 0
        while (i < dataLength) {
            val a1 = data[i].toInt() and 255
            val a2 = data[i + 1].toInt() and 255
            val a3 = data[i + 2].toInt() and 255
            val bArr = encodingTable
            bytes[j] = bArr[a1 ushr 2 and 63]
            bytes[j + 1] = bArr[a1 shl 4 or (a2 ushr 4) and 63]
            bytes[j + 2] = bArr[a2 shl 2 or (a3 ushr 6) and 63]
            bytes[j + 3] = bArr[a3 and 63]
            i += 3
            j += 4
        }
        when (modulus) {
            1 -> {
                val d1 = data[data.size - 1].toInt() and 255
                val b1 = d1 ushr 2 and 63
                val b2 = d1 shl 4 and 63
                val bArr2 = encodingTable
                bytes[bytes.size - 4] = bArr2[b1]
                bytes[bytes.size - 3] = bArr2[b2]
                bytes[bytes.size - 2] = 61
                bytes[bytes.size - 1] = 61
            }

            2 -> {
                val d12 = data[data.size - 2].toInt() and 255
                val d2 = data[data.size - 1].toInt() and 255
                val b12 = d12 ushr 2 and 63
                val b22 = d12 shl 4 or (d2 ushr 4) and 63
                val b3 = d2 shl 2 and 63
                val bArr3 = encodingTable
                bytes[bytes.size - 4] = bArr3[b12]
                bytes[bytes.size - 3] = bArr3[b22]
                bytes[bytes.size - 2] = bArr3[b3]
                bytes[bytes.size - 1] = 61
            }
        }
        return bytes
    }

    fun decode(data: ByteArray): ByteArray {
        val bytes: ByteArray
        val data2 = discardNonBase64Bytes(data)
        bytes = if (data2[data2.size - 2].toInt() == 61) {
            ByteArray((data2.size / 4 - 1) * 3 + 1)
        } else if (data2[data2.size - 1].toInt() == 61) {
            ByteArray((data2.size / 4 - 1) * 3 + 2)
        } else {
            ByteArray(data2.size / 4 * 3)
        }
        var i = 0
        var j = 0
        while (i < data2.size - 4) {
            val bArr = decodingTable
            val b1 = bArr[data2[i].toInt()]
            val b2 = bArr[data2[i + 1].toInt()]
            val b3 = bArr[data2[i + 2].toInt()]
            val b4 = bArr[data2[i + 3].toInt()]
            bytes[j] = (b1.toInt() shl 2 or (b2.toInt() shr 4)).toByte()
            bytes[j + 1] = (b2.toInt() shl 4 or (b3.toInt() shr 2)).toByte()
            bytes[j + 2] = (b3.toInt() shl 6 or b4.toInt()).toByte()
            i += 4
            j += 3
        }
        val i2 = data2.size
        if (data2[i2 - 2].toInt() == 61) {
            val bArr2 = decodingTable
            val b12 = bArr2[data2[data2.size - 4].toInt()]
            bytes[bytes.size - 1] = (b12.toInt() shl 2 or (bArr2[data2[data2.size - 3].toInt()].toInt() shr 4)).toByte()
        } else if (data2[data2.size - 1].toInt() == 61) {
            val bArr3 = decodingTable
            val b13 = bArr3[data2[data2.size - 4].toInt()]
            val b22 = bArr3[data2[data2.size - 3].toInt()]
            val b32 = bArr3[data2[data2.size - 2].toInt()]
            bytes[bytes.size - 2] = (b13.toInt() shl 2 or (b22.toInt() shr 4)).toByte()
            bytes[bytes.size - 1] = (b22.toInt() shl 4 or (b32.toInt() shr 2)).toByte()
        } else {
            val bArr4 = decodingTable
            val b14 = bArr4[data2[data2.size - 4].toInt()]
            val b23 = bArr4[data2[data2.size - 3].toInt()]
            val b33 = bArr4[data2[data2.size - 2].toInt()]
            val b42 = bArr4[data2[data2.size - 1].toInt()]
            bytes[bytes.size - 3] = (b14.toInt() shl 2 or (b23.toInt() shr 4)).toByte()
            bytes[bytes.size - 2] = (b23.toInt() shl 4 or (b33.toInt() shr 2)).toByte()
            bytes[bytes.size - 1] = (b33.toInt() shl 6 or b42.toInt()).toByte()
        }
        return bytes
    }

    fun decode(data: String): ByteArray {
        val bytes: ByteArray
        val data2 = discardNonBase64Chars(data)
        bytes = if (data2[data2.length - 2] == '=') {
            ByteArray((data2.length / 4 - 1) * 3 + 1)
        } else if (data2[data2.length - 1] == '=') {
            ByteArray((data2.length / 4 - 1) * 3 + 2)
        } else {
            ByteArray(data2.length / 4 * 3)
        }
        var i = 0
        var j = 0
        while (i < data2.length - 4) {
            val bArr = decodingTable
            val b1 = bArr[data2[i].code]
            val b2 = bArr[data2[i + 1].code]
            val b3 = bArr[data2[i + 2].code]
            val b4 = bArr[data2[i + 3].code]
            bytes[j] = (b1.toInt() shl 2 or (b2.toInt() shr 4)).toByte()
            bytes[j + 1] = (b2.toInt() shl 4 or (b3.toInt() shr 2)).toByte()
            bytes[j + 2] = (b3.toInt() shl 6 or b4.toInt()).toByte()
            i += 4
            j += 3
        }
        val i2 = data2.length
        if (data2[i2 - 2] == '=') {
            val bArr2 = decodingTable
            val b12 = bArr2[data2[data2.length - 4].code]
            bytes[bytes.size - 1] = (b12.toInt() shl 2 or (bArr2[data2[data2.length - 3].code].toInt() shr 4)).toByte()
        } else if (data2[data2.length - 1] == '=') {
            val bArr3 = decodingTable
            val b13 = bArr3[data2[data2.length - 4].code]
            val b22 = bArr3[data2[data2.length - 3].code]
            val b32 = bArr3[data2[data2.length - 2].code]
            bytes[bytes.size - 2] = (b13.toInt() shl 2 or (b22.toInt() shr 4)).toByte()
            bytes[bytes.size - 1] = (b22.toInt() shl 4 or (b32.toInt() shr 2)).toByte()
        } else {
            val bArr4 = decodingTable
            val b14 = bArr4[data2[data2.length - 4].code]
            val b23 = bArr4[data2[data2.length - 3].code]
            val b33 = bArr4[data2[data2.length - 2].code]
            val b42 = bArr4[data2[data2.length - 1].code]
            bytes[bytes.size - 3] = (b14.toInt() shl 2 or (b23.toInt() shr 4)).toByte()
            bytes[bytes.size - 2] = (b23.toInt() shl 4 or (b33.toInt() shr 2)).toByte()
            bytes[bytes.size - 1] = (b33.toInt() shl 6 or b42.toInt()).toByte()
        }
        for (i3 in bytes.indices) {
            val printStream = System.out
            printStream.println("," + bytes[i3].toInt())
        }
        return bytes
    }

    private fun discardNonBase64Bytes(data: ByteArray): ByteArray {
        val temp = ByteArray(data.size)
        var bytesCopied = 0
        for (i in data.indices) {
            if (isValidBase64Byte(data[i])) {
                temp[bytesCopied] = data[i]
                bytesCopied++
            }
        }
        val newData = ByteArray(bytesCopied)
        System.arraycopy(temp, 0, newData, 0, bytesCopied)
        return newData
    }

    private fun discardNonBase64Chars(data: String): String {
        val sb = StringBuffer()
        val length = data.length
        for (i in 0 until length) {
            if (isValidBase64Byte(data[i].code.toByte())) {
                sb.append(data[i])
            }
        }
        return sb.toString()
    }

    private fun isValidBase64Byte(b: Byte): Boolean {
        return if (b.toInt() == 61) {
            true
        } else b in 0..127 && decodingTable[b.toInt()].toInt() != -1
    }

    @Throws(Exception::class)
    fun encode(data: String?, charset: String?): String? {
        if (data.isNullOrEmpty()) {
            return data
        }
        val result = encode(data.toByteArray(charset(charset!!)), 0)
        val sb = StringBuffer(result.size)
        for (b in result) {
            sb.append(Char(b.toUShort()))
        }
        return sb.toString()
    }

    @Throws(Exception::class)
    fun decode(data: String?, charset: String?): String? {
        return if (data == null || data.length == 0) {
            data
        } else String(decode(data), charset(charset!!))
    }
}
