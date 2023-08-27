package com.lingchen.framework.crypto

import com.lingchen.framework.utils.Util
import java.io.ByteArrayOutputStream
import java.util.*

/* loaded from: classes.dex */
class TeaCrypter {
    private var contextStart = 0
    private var crypt = 0
    private var key: ByteArray=null!!
    private var out: ByteArray=null!!
    private var padding = 0
    private var plain: ByteArray=null!!
    private var pos = 0
    private var preCrypt = 0
    private var prePlain: ByteArray?=null!!
    private var header = true
    private val baos = ByteArrayOutputStream(8)
    fun decrypt(`in`: ByteArray, offset: Int, len: Int, k: ByteArray?): ByteArray? {
        if (k == null) {
            return null
        }
        preCrypt = 0
        crypt = 0
        key = k
        var m = ByteArray(offset + 8)
        if (len % 8 != 0 || len < 16) {
            return null
        }
        val decipher = decipher(`in`, offset)
        prePlain = decipher
        val i = decipher[0].toInt() and 7
        pos = i
        var count = len - i - 10
        if (count < 0) {
            return null
        }
        for (i2 in offset until m.size) {
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
                        out[i5] = (m[preCrypt + offset + i6].toInt() xor prePlain!![i6].toInt()).toByte()
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
                        if (m[preCrypt + offset + i7].toInt() xor prePlain!![i7].toInt() != 0) {
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

    fun decrypt(`in`: ByteArray, k: ByteArray?): ByteArray? {
        return decrypt(`in`, 0, `in`.size, k)
    }

    fun encrypt(`in`: ByteArray, offset: Int, len: Int, k: ByteArray?): ByteArray {
        var len = len
        var i: Int
        if (k == null) {
            return `in`
        }
        val bArr = ByteArray(8)
        plain = bArr
        prePlain = ByteArray(8)
        pos = 1
        padding = 0
        preCrypt = 0
        crypt = 0
        key = k
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

    fun encrypt(`in`: ByteArray, k: ByteArray?): ByteArray {
        return encrypt(`in`, 0, `in`.size, k)
    }

    private fun encipher(`in`: ByteArray): ByteArray {
        var loop = 16
        var y = Util.getUnsignedInt(`in`, 0, 4)
        var z = Util.getUnsignedInt(`in`, 4, 4)
        val a = Util.getUnsignedInt(key, 0, 4)
        val b = Util.getUnsignedInt(key, 4, 4)
        val c = Util.getUnsignedInt(key, 8, 4)
        val d = Util.getUnsignedInt(key, 12, 4)
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
                baos.reset()
                writeInt(y.toInt())
                writeInt(z.toInt())
                return baos.toByteArray()
            }
        }
    }

    private fun decipher(`in`: ByteArray, offset: Int = 0): ByteArray {
        var loop = 16
        var y = Util.getUnsignedInt(`in`, offset, 4)
        var z = Util.getUnsignedInt(`in`, offset + 4, 4)
        val a = Util.getUnsignedInt(key, 0, 4)
        val b = Util.getUnsignedInt(key, 4, 4)
        val c = Util.getUnsignedInt(key, 8, 4)
        val d = Util.getUnsignedInt(key, 12, 4)
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
                baos.reset()
                writeInt(y.toInt())
                writeInt(z.toInt())
                return baos.toByteArray()
            }
        }
    }

    private fun writeInt(t: Int) {
        baos.write(t ushr 24)
        baos.write(t ushr 16)
        baos.write(t ushr 8)
        baos.write(t)
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

    private fun decrypt8Bytes(`in`: ByteArray, offset: Int, len: Int): Boolean {
        pos = 0
        while (true) {
            val i = pos
            if (i < 8) {
                if (contextStart + i >= len) {
                    return true
                }
                val bArr = prePlain
                bArr!![i] = (bArr[i].toInt() xor `in`[crypt + offset + i].toInt()).toByte()
                pos = i + 1
            } else {
                val decipher = decipher(prePlain!!)
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
    }
}
