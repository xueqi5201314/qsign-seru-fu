package com.lingchen.framework.crypto

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.*

/* loaded from: classes.dex */
class TEACoding(k: ByteArray?) {
    private var contextStart = 0
    private var crypt = 0
    private var header: Boolean
    private val key: ByteArray
    private var out: ByteArray=null!!
    private var padding = 0
    private var plain: ByteArray=null!!
    private var pos = 0
    private var preCrypt = 0
    private var prePlain: ByteArray?=null

    init {
        require(!(k == null || k.size != 16)) { "Key length must be 16!" }
        header = true
        key = k
    }

    fun encode(`in`: ByteArray): ByteArray {
        return encode(`in`, 0, `in`.size)
    }

    fun encode2HexStr(`in`: ByteArray): String? {
        return HexUtil.bytes2HexStr(encode(`in`))
    }

    private fun encode(`in`: ByteArray, offset: Int, len: Int): ByteArray {
        var len = len
        var i: Int
        val bArr = ByteArray(8)
        plain = bArr
        prePlain = ByteArray(8)
        pos = 1
        padding = 0
        preCrypt = 0
        crypt = 0
        header = true
        val i2 = (len + 10) % 8
        pos = i2
        if (i2 != 0) {
            pos = 8 - i2
        }
        out = ByteArray(pos + len + 10)
        bArr[0] = (rand() and 248 or pos).toByte()
        var i3 = 1
        while (true) {
            i = pos
            if (i3 > i) {
                break
            }
            plain[i3] = (rand() and 255).toByte()
            i3++
        }
        pos = i + 1
        for (i4 in 0..7) {
            prePlain!![i4] = 0
        }
        padding = 1
        while (padding <= 2) {
            val i5 = pos
            if (i5 < 8) {
                val bArr2 = plain
                pos = i5 + 1
                bArr2[i5] = (rand() and 255).toByte()
                padding++
            }
            if (pos == 8) {
                encrypt8Bytes()
            }
        }
        var i6 = offset
        while (len > 0) {
            val i7 = pos
            if (i7 < 8) {
                val bArr3 = plain
                pos = i7 + 1
                bArr3[i7] = `in`[i6]
                len--
                i6++
            }
            if (pos == 8) {
                encrypt8Bytes()
            }
        }
        padding = 1
        while (true) {
            val i8 = padding
            if (i8 <= 7) {
                val i9 = pos
                if (i9 < 8) {
                    val bArr4 = plain
                    pos = i9 + 1
                    bArr4[i9] = 0
                    padding = i8 + 1
                }
                if (pos == 8) {
                    encrypt8Bytes()
                }
            } else {
                return out
            }
        }
    }

    fun decode(code: ByteArray?): ByteArray? {
        return decode(code, 0, code!!.size)
    }

    fun decodeFromHexStr(code: String?): ByteArray? {
        return decode(HexUtil.hexStr2Bytes(code))
    }

    private fun decode(`in`: ByteArray?, offset: Int, len: Int): ByteArray? {
        preCrypt = 0
        crypt = 0
        var m: ByteArray? = ByteArray(offset + 8)
        if (len % 8 != 0 || len < 16) {
            println(len % 8)
            return null
        }
        val decipher = decipher(`in`, offset)
        prePlain = decipher
        val i = decipher!![0].toInt() and 7
        pos = i
        var count = len - i - 10
        if (count < 0) {
            return null
        }
        for (i2 in offset until m!!.size) {
            m[i2] = 0
        }
        out = ByteArray(count)
        preCrypt = 0
        crypt = 8
        contextStart = 8
        pos++
        padding = 1
        while (true) {
            val i3 = padding
            if (i3 <= 2) {
                val i4 = pos
                if (i4 < 8) {
                    pos = i4 + 1
                    padding = i3 + 1
                }
                if (pos == 8) {
                    m = `in`
                    if (!decrypt8Bytes(`in`, offset, len)) {
                        return null
                    }
                }
            } else {
                var i5 = 0
                while (count != 0) {
                    val i6 = pos
                    if (i6 < 8) {
                        out[i5] = (m!![preCrypt + offset + i6].toInt() xor prePlain!![i6].toInt()).toByte()
                        i5++
                        count--
                        pos = i6 + 1
                    }
                    if (pos == 8) {
                        m = `in`
                        preCrypt = crypt - 8
                        if (!decrypt8Bytes(`in`, offset, len)) {
                            return null
                        }
                    }
                }
                padding = 1
                while (padding < 8) {
                    val i7 = pos
                    if (i7 < 8) {
                        if (m!![preCrypt + offset + i7].toInt() xor prePlain!![i7].toInt() != 0) {
                            return null
                        }
                        pos = i7 + 1
                    }
                    if (pos == 8) {
                        m = `in`
                        preCrypt = crypt
                        if (!decrypt8Bytes(`in`, offset, len)) {
                            return null
                        }
                    }
                    padding++
                }
                return out
            }
        }
    }

    private fun encipher(`in`: ByteArray): ByteArray? {
        var loop = 16
        try {
            var y = getUnsignedInt(`in`, 0, 4)
            var z = getUnsignedInt(`in`, 4, 4)
            val a = getUnsignedInt(key, 0, 4)
            val b = getUnsignedInt(key, 4, 4)
            val c = getUnsignedInt(key, 8, 4)
            val d = getUnsignedInt(key, 12, 4)
            var sum: Long = 0
            val delta = (-1640531527).toLong() and 4294967295L
            while (true) {
                val loop2 = loop - 1
                if (loop > 0) {
                    sum = sum + delta and 4294967295L
                    y = y + ((z shl 4) + a xor z + sum xor (z ushr 5) + b) and 4294967295L
                    z = z + ((y shl 4) + c xor y + sum xor (y ushr 5) + d) and 4294967295L
                    loop = loop2
                } else {
                    val baos = ByteArrayOutputStream(8)
                    val dos = DataOutputStream(baos)
                    dos.writeInt(y.toInt())
                    dos.writeInt(z.toInt())
                    dos.close()
                    return baos.toByteArray()
                }
            }
        } catch (e: IOException) {
            return null
        }
    }

    private fun decipher(`in`: ByteArray?, offset: Int = 0): ByteArray? {
        var loop = 16
        try {
            var y = getUnsignedInt(`in`, offset, 4)
            var z = getUnsignedInt(`in`, offset + 4, 4)
            val a = getUnsignedInt(key, 0, 4)
            val b = getUnsignedInt(key, 4, 4)
            val c = getUnsignedInt(key, 8, 4)
            val d = getUnsignedInt(key, 12, 4)
            var sum = (-478700656).toLong() and 4294967295L
            val delta = (-1640531527).toLong() and 4294967295L
            while (true) {
                val loop2 = loop - 1
                if (loop > 0) {
                    z = z - ((y shl 4) + c xor y + sum xor (y ushr 5) + d) and 4294967295L
                    y = y - ((z shl 4) + a xor z + sum xor (z ushr 5) + b) and 4294967295L
                    sum = sum - delta and 4294967295L
                    loop = loop2
                } else {
                    val baos = ByteArrayOutputStream(8)
                    val dos = DataOutputStream(baos)
                    dos.writeInt(y.toInt())
                    dos.writeInt(z.toInt())
                    dos.close()
                    return baos.toByteArray()
                }
            }
        } catch (e: IOException) {
            return null
        }
    }

    private fun encrypt8Bytes() {
        pos = 0
        while (true) {
            val i = pos
            if (i >= 8) {
                break
            }
            if (header) {
                val bArr = plain
                bArr[i] = (bArr[i].toInt() xor prePlain!![i].toInt()).toByte()
            } else {
                val bArr2 = plain
                bArr2[i] = (bArr2[i].toInt() xor out[preCrypt + i].toInt()).toByte()
            }
            pos = i + 1
        }
        val crypted = encipher(plain)
        System.arraycopy(crypted, 0, out, crypt, 8)
        pos = 0
        while (true) {
            val i2 = pos
            if (i2 >= 8) {
                System.arraycopy(plain, 0, prePlain, 0, 8)
                val i3 = crypt
                preCrypt = i3
                crypt = i3 + 8
                pos = 0
                header = false
                return
            }
            val bArr3 = out
            val i4 = crypt + i2
            bArr3[i4] = (bArr3[i4].toInt() xor prePlain!![i2].toInt()).toByte()
            pos = i2 + 1
        }
    }

    private fun decrypt8Bytes(`in`: ByteArray?, offset: Int, len: Int): Boolean {
        pos = 0
        while (true) {
            val i = pos
            if (i < 8) {
                if (contextStart + i >= len) {
                    return true
                }
                val bArr = prePlain
                bArr!![i] = (bArr[i].toInt() xor `in`!![crypt + offset + i].toInt()).toByte()
                pos = i + 1
            } else {
                val decipher = decipher(prePlain)
                prePlain = decipher
                if (decipher == null) {
                    return false
                }
                contextStart += 8
                crypt += 8
                pos = 0
                return true
            }
        }
    }

    private fun rand(): Int {
        return random.nextInt()
    }

    companion object {
        private val random = Random()
        fun getUnsignedInt(`in`: ByteArray?, offset: Int, len: Int): Long {
            val end: Int
            var ret: Long = 0
            end = if (len > 8) {
                offset + 8
            } else {
                offset + len
            }
            for (i in offset until end) {
                ret = ret shl 8 or (`in`!![i].toInt() and 255).toLong()
            }
            return 4294967295L and ret or (ret ushr 32)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val tea = TEACoding("Bt4e2%Y#X@~g.+F<".toByteArray())
            val code = tea.encode2HexStr("123456".toByteArray())
            println(code)
            println(String(tea.decodeFromHexStr(code)!!))
        }
    }
}
