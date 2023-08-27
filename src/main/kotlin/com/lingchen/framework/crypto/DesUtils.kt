package com.lingchen.framework.crypto

/* loaded from: classes.dex */
object DesUtils {
    const val FLAG_DECRYPT = 0
    const val FLAG_ENCRYPT = 1
    private val IP = intArrayOf(
        58,
        50,
        42,
        34,
        26,
        18,
        10,
        2,
        60,
        52,
        44,
        36,
        28,
        20,
        12,
        4,
        62,
        54,
        46,
        38,
        30,
        22,
        14,
        6,
        64,
        56,
        48,
        40,
        32,
        24,
        16,
        8,
        57,
        49,
        41,
        33,
        25,
        17,
        9,
        1,
        59,
        51,
        43,
        35,
        27,
        19,
        11,
        3,
        61,
        53,
        45,
        37,
        29,
        21,
        13,
        5,
        63,
        55,
        47,
        39,
        31,
        23,
        15,
        7
    )
    private val IP_1 = intArrayOf(
        40,
        8,
        48,
        16,
        56,
        24,
        64,
        32,
        39,
        7,
        47,
        15,
        55,
        23,
        63,
        31,
        38,
        6,
        46,
        14,
        54,
        22,
        62,
        30,
        37,
        5,
        45,
        13,
        53,
        21,
        61,
        29,
        36,
        4,
        44,
        12,
        52,
        20,
        60,
        28,
        35,
        3,
        43,
        11,
        51,
        19,
        59,
        27,
        34,
        2,
        42,
        10,
        50,
        18,
        58,
        26,
        33,
        1,
        41,
        9,
        49,
        17,
        57,
        25
    )
    val KEY = byteArrayOf(-25, -101, -115, 1, 47, 7, -27, -59, 18, Byte.MIN_VALUE, 123, 79, -44, 37, 46, 115)
    val MAC_KEY = byteArrayOf(37, -110, 60, Byte.MAX_VALUE, 42, -27, -17, -110)
    val MTT_KEY = byteArrayOf(-122, -8, -23, -84, -125, 113, 84, 99)
    private val PC_1 = intArrayOf(
        57,
        49,
        41,
        33,
        25,
        17,
        9,
        1,
        58,
        50,
        42,
        34,
        26,
        18,
        10,
        2,
        59,
        51,
        43,
        35,
        27,
        19,
        11,
        3,
        60,
        52,
        44,
        36,
        63,
        55,
        47,
        39,
        31,
        23,
        15,
        7,
        62,
        54,
        46,
        38,
        30,
        22,
        14,
        6,
        61,
        53,
        45,
        37,
        29,
        21,
        13,
        5,
        28,
        20,
        12,
        4
    )
    private val PC_2 = intArrayOf(
        14,
        17,
        11,
        24,
        1,
        5,
        3,
        28,
        15,
        6,
        21,
        10,
        23,
        19,
        12,
        4,
        26,
        8,
        16,
        7,
        27,
        20,
        13,
        2,
        41,
        52,
        31,
        37,
        47,
        55,
        30,
        40,
        51,
        45,
        33,
        48,
        44,
        49,
        39,
        56,
        34,
        53,
        46,
        42,
        50,
        36,
        29,
        32
    )
    val QQMARKET_KEY = "AL!#\$AC9Ahg@KLJ1".toByteArray()
    private val E = intArrayOf(
        32,
        1,
        2,
        3,
        4,
        5,
        4,
        5,
        6,
        7,
        8,
        9,
        8,
        9,
        10,
        11,
        12,
        13,
        12,
        13,
        14,
        15,
        16,
        17,
        16,
        17,
        18,
        19,
        20,
        21,
        20,
        21,
        22,
        23,
        24,
        25,
        24,
        25,
        26,
        27,
        28,
        29,
        28,
        29,
        30,
        31,
        32,
        1
    )
    private val P = intArrayOf(
        16,
        7,
        20,
        21,
        29,
        12,
        28,
        17,
        1,
        15,
        23,
        26,
        5,
        18,
        31,
        10,
        2,
        8,
        24,
        14,
        32,
        27,
        3,
        9,
        19,
        13,
        30,
        6,
        22,
        11,
        4,
        25
    )
    private val S_Box = arrayOf(
        arrayOf(
            intArrayOf(14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7),
            intArrayOf(0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8),
            intArrayOf(4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0),
            intArrayOf(15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13)
        ),
        arrayOf(
            intArrayOf(15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10),
            intArrayOf(3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5),
            intArrayOf(0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15),
            intArrayOf(13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9)
        ),
        arrayOf(
            intArrayOf(10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8),
            intArrayOf(13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1),
            intArrayOf(13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7),
            intArrayOf(1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12)
        ),
        arrayOf(
            intArrayOf(7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15),
            intArrayOf(13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9),
            intArrayOf(10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4),
            intArrayOf(3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14)
        ),
        arrayOf(
            intArrayOf(2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9),
            intArrayOf(14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6),
            intArrayOf(4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14),
            intArrayOf(11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3)
        ),
        arrayOf(
            intArrayOf(12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11),
            intArrayOf(10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8),
            intArrayOf(9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6),
            intArrayOf(4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13)
        ),
        arrayOf(
            intArrayOf(4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1),
            intArrayOf(13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6),
            intArrayOf(1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2),
            intArrayOf(6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12)
        ),
        arrayOf(
            intArrayOf(13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7),
            intArrayOf(1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2),
            intArrayOf(7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8),
            intArrayOf(2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11)
        )
    )
    private val LeftMove = intArrayOf(1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1)
    val REPORT_KEY_TEA = byteArrayOf(98, -24, 57, -84, -115, 117, 55, 121)
    private fun ByteDataFormat(paramArrayOfByte: ByteArray): ByteArray {
        val i = paramArrayOfByte.size
        val j = 8 - i % 8
        val k = i + j
        val arrayOfByte = ByteArray(k)
        System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i)
        for (m in i until k) {
            arrayOfByte[m] = j.toByte()
        }
        return arrayOfByte
    }

    fun DesEncrypt(des_key: ByteArray?, des_data: ByteArray?, flag: Int): ByteArray? {
        if (des_data == null) {
            return null
        }
        return if (des_key == null) {
            des_data
        } else try {
            val v4 = KeyDataFormat(des_key)
            val v3 = ByteDataFormat(des_data)
            val v12 = v3.size / 8
            var v7 = ByteArray(v3.size)
            for (v5 in 0 until v12) {
                val v10 = ByteArray(8)
                val v9 = ByteArray(8)
                System.arraycopy(v4, 0, v10, 0, 8)
                System.arraycopy(v3, v5 * 8, v9, 0, 8)
                System.arraycopy(UnitDes(v10, v9, flag), 0, v7, v5 * 8, 8)
            }
            if (flag == 0) {
                val v8 = ByteArray(des_data.size)
                System.arraycopy(v7, 0, v8, 0, v8.size)
                val v6 = v8[v8.size - 1].toInt()
                if (v6 > 0 && v6 <= 8) {
                    var v1 = 1
                    var v52 = 0
                    while (true) {
                        if (v52 < v6) {
                            if (v6 != v8[v8.size - 1 - v52].toInt()) {
                                v1 = 0
                            } else {
                                v52++
                            }
                        }
                        if (v1 == 0) {
                            return v7
                        }
                        try {
                            v7 = ByteArray(v8.size - v6)
                            System.arraycopy(v8, 0, v7, 0, v7.size)
                        } catch (e: Exception) {
                            return des_data
                        }
                    }
                }
            }
            v7
        } catch (e2: Exception) {
            des_data
        }
    }

    private fun Encrypt(paramArrayOfInt: IntArray, paramInt: Int, paramArrayOfInt1: Array<IntArray>): ByteArray {
        val arrayOfByte = ByteArray(8)
        val arrayOfInt1 = IntArray(64)
        val arrayOfInt2 = IntArray(64)
        for (i in 0..63) {
            arrayOfInt1[i] = paramArrayOfInt[IP[i] - 1]
        }
        if (paramInt == 1) {
            for (m in 0..15) {
                LoopF(arrayOfInt1, m, paramInt, paramArrayOfInt1)
            }
        }
        if (paramInt == 0) {
            for (k in 15 downTo -1 + 1) {
                LoopF(arrayOfInt1, k, paramInt, paramArrayOfInt1)
            }
        }
        for (j in 0..63) {
            arrayOfInt2[j] = arrayOfInt1[IP_1[j] - 1]
        }
        GetEncryptResultOfByteArray(arrayOfInt2, arrayOfByte)
        return arrayOfByte
    }

    private fun GetEncryptResultOfByteArray(paramArrayOfInt: IntArray, paramArrayOfByte: ByteArray) {
        for (i in 0..7) {
            for (j in 0..7) {
                paramArrayOfByte[i] = (paramArrayOfByte[i] + (paramArrayOfInt[(i shl 3) + j] shl 7 - j)).toByte()
            }
        }
    }

    private fun KeyDataFormat(paramArrayOfByte: ByteArray): ByteArray {
        val arrayOfByte = ByteArray(8)
        for (i in arrayOfByte.indices) {
            arrayOfByte[i] = 0
        }
        val i2 = paramArrayOfByte.size
        if (i2 > 8) {
            System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, arrayOfByte.size)
            return arrayOfByte
        }
        System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.size)
        return arrayOfByte
    }

    private fun KeyInitialize(paramArrayOfInt: IntArray, paramArrayOfInt1: Array<IntArray>) {
        val arrayOfInt = IntArray(56)
        for (i in 0..55) {
            arrayOfInt[i] = paramArrayOfInt[PC_1[i] - 1]
        }
        for (j in 0..15) {
            LeftBitMove(arrayOfInt, LeftMove[j])
            for (k in 0..47) {
                paramArrayOfInt1[j][k] = arrayOfInt[PC_2[k] - 1]
            }
        }
    }

    private fun LeftBitMove(paramArrayOfInt: IntArray, paramInt: Int) {
        val arrayOfInt1 = IntArray(28)
        val arrayOfInt2 = IntArray(28)
        val arrayOfInt3 = IntArray(28)
        val arrayOfInt4 = IntArray(28)
        for (i in 0..27) {
            arrayOfInt1[i] = paramArrayOfInt[i]
            arrayOfInt2[i] = paramArrayOfInt[i + 28]
        }
        if (paramInt == 1) {
            for (m in 0..26) {
                arrayOfInt3[m] = arrayOfInt1[m + 1]
                arrayOfInt4[m] = arrayOfInt2[m + 1]
            }
            val m2 = arrayOfInt1[0]
            arrayOfInt3[27] = m2
            arrayOfInt4[27] = arrayOfInt2[0]
        }
        for (k in 0..27) {
            paramArrayOfInt[k] = arrayOfInt3[k]
            paramArrayOfInt[k + 28] = arrayOfInt4[k]
        }
        if (paramInt == 2) {
            for (j in 0..25) {
                arrayOfInt3[j] = arrayOfInt1[j + 2]
                arrayOfInt4[j] = arrayOfInt2[j + 2]
            }
            val j2 = arrayOfInt1[0]
            arrayOfInt3[26] = j2
            arrayOfInt4[26] = arrayOfInt2[0]
            arrayOfInt3[27] = arrayOfInt1[1]
            arrayOfInt4[27] = arrayOfInt2[1]
        }
    }

    private fun LoopF(M: IntArray, times: Int, flag: Int, keyArray: Array<IntArray>) {
        var c: Char
        val v1 = IntArray(32)
        val v3 = IntArray(32)
        val v2 = IntArray(32)
        val v4 = IntArray(32)
        val v5 = IntArray(48)
        val v7 = java.lang.reflect.Array.newInstance(Int::class.javaPrimitiveType, 8, 6) as Array<IntArray>
        val v10 = IntArray(8)
        val v11 = IntArray(32)
        val v6 = IntArray(32)
        for (v8 in 0..31) {
            v1[v8] = M[v8]
            v3[v8] = M[v8 + 32]
        }
        var v82 = 0
        while (true) {
            c = 1.toChar()
            if (v82 >= 48) {
                break
            }
            v5[v82] = v3[E[v82] - 1]
            v5[v82] = v5[v82] + keyArray[times][v82]
            if (v5[v82] == 2) {
                v5[v82] = 0
            }
            v82++
        }
        var v83 = 0
        var i = 8
        while (v83 < i) {
            for (v9 in 0..5) {
                v7[v83][v9] = v5[v83 * 6 + v9]
            }
            v10[v83] =
                S_Box[v83][(v7[v83][0] shl 1) + v7[v83][5]][(v7[v83][c.code] shl 3) + (v7[v83][2] shl 2) + (v7[v83][3] shl 1) + v7[v83][4]]
            for (v92 in 0..3) {
                v11[v83 * 4 + 3 - v92] = v10[v83] % 2
                v10[v83] = v10[v83] / 2
            }
            v83++
            c = 1.toChar()
            i = 8
        }
        for (v84 in 0..31) {
            v6[v84] = v11[P[v84] - 1]
            v2[v84] = v3[v84]
            v4[v84] = v1[v84] + v6[v84]
            if (v4[v84] == 2) {
                v4[v84] = 0
            }
            if (flag != 0 || times != 0) {
                if (flag == 1 && times == 15) {
                    M[v84] = v4[v84]
                    M[v84 + 32] = v2[v84]
                }
                M[v84] = v2[v84]
                M[v84 + 32] = v4[v84]
            } else {
                M[v84] = v4[v84]
                M[v84 + 32] = v2[v84]
                M[v84] = v2[v84]
                M[v84 + 32] = v4[v84]
            }
        }
    }

    private fun ReadDataToBirnaryIntArray(paramArrayOfByte: ByteArray): IntArray {
        val arrayOfInt1 = IntArray(8)
        for (i in 0..7) {
            arrayOfInt1[i] = paramArrayOfByte[i].toInt()
            if (arrayOfInt1[i] < 0) {
                arrayOfInt1[i] = arrayOfInt1[i] + 256
                arrayOfInt1[i] = arrayOfInt1[i] % 256
            }
        }
        val arrayOfInt2 = IntArray(64)
        for (j in 0..7) {
            for (k in 0..7) {
                arrayOfInt2[j * 8 + 7 - k] = arrayOfInt1[j] % 2
                arrayOfInt1[j] = arrayOfInt1[j] / 2
            }
        }
        return arrayOfInt2
    }

    private fun UnitDes(paramArrayOfByte1: ByteArray, paramArrayOfByte2: ByteArray, paramInt: Int): ByteArray {
        if (paramArrayOfByte1.size != 8 || paramArrayOfByte2.size != 8 || paramInt != 1 && paramInt != 0) {
            throw RuntimeException("Data Format Error !")
        }
        val arrayOfInt1 = intArrayOf(16, 48)
        val arrayOfInt = java.lang.reflect.Array.newInstance(Integer.TYPE, *arrayOfInt1) as Array<IntArray>
        val arrayOfInt2 = ReadDataToBirnaryIntArray(paramArrayOfByte1)
        val arrayOfInt3 = ReadDataToBirnaryIntArray(paramArrayOfByte2)
        KeyInitialize(arrayOfInt2, arrayOfInt)
        return Encrypt(arrayOfInt3, paramInt, arrayOfInt)
    }
}
