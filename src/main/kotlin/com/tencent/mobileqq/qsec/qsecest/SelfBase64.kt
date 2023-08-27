package com.tencent.mobileqq.qsec.qsecest

import java.io.OutputStream
import java.nio.ByteBuffer
import kotlin.math.min

class SelfBase64 {
    class Encoder private constructor(
        private val isURL: Boolean,
        private val newline: ByteArray?,
        private val linemax: Int,
        private val doPadding: Boolean
    ) {
        private fun outLength(i: Int): Int {
            val i2: Int
            i2 = if (doPadding) {
                (i + 2) / 3 * 4
            } else {
                val i3 = i % 3
                (if (i3 == 0) 0 else i3 + 1) + i / 3 * 4
            }
            return if (linemax > 0) {
                i2 + (i2 - 1) / linemax * newline!!.size
            } else i2
        }

        fun encode(bArr: ByteArray): ByteArray? {
            return try {
                val bArr2 = ByteArray(outLength(bArr.size))
                val encode0 = encode0(bArr, 0, bArr.size, bArr2)
                if (encode0 != bArr2.size) {
                    bArr2.copyOf(encode0)
                } else bArr2
            } catch (th: Throwable) {
                th.printStackTrace()
                null
            }
        }

        fun encode(byteBuffer: ByteBuffer): ByteBuffer {
            val encode0: Int
            val bArr = ByteArray(outLength(byteBuffer.remaining()))
            if (byteBuffer.hasArray()) {
                encode0 = encode0(
                    byteBuffer.array(),
                    byteBuffer.arrayOffset() + byteBuffer.position(),
                    byteBuffer.arrayOffset() + byteBuffer.limit(),
                    bArr
                )
                byteBuffer.position(byteBuffer.limit())
            } else {
                val bArr2 = ByteArray(byteBuffer.remaining())
                byteBuffer[bArr2]
                encode0 = encode0(bArr2, 0, bArr2.size, bArr)
            }
            return ByteBuffer.wrap(if (encode0 != bArr.size) bArr.copyOf(encode0) else bArr)
        }

        fun encode(bArr: ByteArray, bArr2: ByteArray): Int {
            require(bArr2.size >= outLength(bArr.size)) { "Output byte array is too small for encoding all input bytes" }
            return encode0(bArr, 0, bArr.size, bArr2)
        }

        fun encodeToString(bArr: ByteArray): String? {
            return try {
                val encode = encode(bArr)
                String(encode!!, 0,   encode.size)
            } catch (th: Throwable) {
                null
            }
        }

        fun wrap(outputStream: OutputStream?): OutputStream? {
            //Objects.requireNonNull(outputStream);
            //return new SelfBase64.EncOutputStream(outputStream, this.isURL ? toBase64URL : toBase64, this.newline, this.linemax, this.doPadding);
            return null
        }

        fun withoutPadding(): Encoder {
            return if (!doPadding) this else Encoder(isURL, newline, linemax, false)
        }

        private fun encode0(bArr: ByteArray, i: Int, i2: Int, bArr2: ByteArray): Int {
            val i3: Int
            val cArr = if (isURL) toBase64URL else toBase64
            var i4 = (i2 - i) / 3 * 3
            val i5 = i + i4
            if (linemax > 0 && i4 > linemax / 4 * 3) {
                i4 = linemax / 4 * 3
            }
            var i6 = 0
            var i7 = i
            while (i7 < i5) {
                val min = min((i7 + i4).toDouble(), i5.toDouble()).toInt()
                var i8 = i6
                var i9 = i7
                while (i9 < min) {
                    val i10 = i9 + 1
                    val i11 = i10 + 1
                    val i12 = bArr[i10].toInt() and 255 shl 8 or (bArr[i9].toInt() and 255 shl 16)
                    i9 = i11 + 1
                    val i13 = i12 or (bArr[i11].toInt() and 255)
                    val i14 = i8 + 1
                    bArr2[i8] = cArr[i13 ushr 18 and 63].code.toByte()
                    val i15 = i14 + 1
                    bArr2[i14] = cArr[i13 ushr 12 and 63].code.toByte()
                    val i16 = i15 + 1
                    bArr2[i15] = cArr[i13 ushr 6 and 63].code.toByte()
                    i8 = i16 + 1
                    bArr2[i16] = cArr[i13 and 63].code.toByte()
                }
                val i17 = (min - i7) / 3 * 4
                i6 += i17
                if (i17 == linemax && min < i2) {
                    val bArr3 = newline
                    val length = bArr3!!.size
                    var i18 = 0
                    while (i18 < length) {
                        bArr2[i6] = bArr3[i18]
                        i18++
                        i6++
                    }
                }
                i7 = min
            }
            if (i7 < i2) {
                val i19 = i7 + 1
                val i20 = bArr[i7].toInt() and 255
                val i21 = i6 + 1
                bArr2[i6] = cArr[i20 shr 2].code.toByte()
                if (i19 == i2) {
                    i3 = i21 + 1
                    bArr2[i21] = cArr[i20 shl 4 and 63].code.toByte()
                    if (doPadding) {
                        val i22 = i3 + 1
                        bArr2[i3] = 61
                        val i23 = i22 + 1
                        bArr2[i22] = 61
                        return i23
                    }
                } else {
                    val i24 = i19 + 1
                    val i25 = bArr[i19].toInt() and 255
                    val i26 = i21 + 1
                    bArr2[i21] = cArr[i20 shl 4 and 63 or (i25 shr 4)].code.toByte()
                    i3 = i26 + 1
                    bArr2[i26] = cArr[i25 shl 2 and 63].code.toByte()
                    if (doPadding) {
                        val i27 = i3 + 1
                        bArr2[i3] = 61
                        return i27
                    }
                }
                return i3
            }
            return i6
        }

        companion object {
            private const val base64chars = "EBnuvwxCD+FGHIopqrstJKLRSTUlmyz012VWXYZaMNOPQbcdefghijk3456789A/"
            private val toBase64 = base64chars.toCharArray()
            private val toBase64URL = charArrayOf(
                'A',
                'B',
                'C',
                'D',
                'E',
                'F',
                'G',
                'H',
                'I',
                'J',
                'K',
                'L',
                'M',
                'N',
                'O',
                'P',
                'Q',
                'R',
                'S',
                'T',
                'U',
                'V',
                'W',
                'X',
                'Y',
                'Z',
                'a',
                'b',
                'c',
                'd',
                'e',
                'f',
                'g',
                'h',
                'i',
                'j',
                'k',
                'l',
                'm',
                'n',
                'o',
                'p',
                'q',
                'r',
                's',
                't',
                'u',
                'v',
                'w',
                'x',
                'y',
                'z',
                '0',
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                '7',
                '8',
                '9',
                '-',
                '_'
            )
            private val CRLF = byteArrayOf(13, 10)
            val RFC4648 = Encoder(false, null, -1, true)
            val RFC4648_URLSAFE = Encoder(true, null, -1, true)
            private const val MIMELINEMAX = 76
            val RFC2045 = Encoder(false, CRLF, MIMELINEMAX, true)
        }
    }
}
