package com.lingchen.framework.crypto

import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/* loaded from: classes.dex */
object HmacSHA1 {
    fun compute(inData: String, key: String): String {
        return try {
            val v2 =
                SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "HmacSHA1")
            val v1_1 = Mac.getInstance("HmacSHA1")
            v1_1.init(v2)
            a(v1_1.doFinal(inData.toByteArray()))
        } catch (e: Exception) {
            ""
        }
    }

    private fun a(arg7: ByteArray): String {
        var v2 = ""
        for (v3 in arg7) {
            val v21 = StringBuilder().append(v2)
            val v4 = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
            val ss= v3.toInt()
            v21.append(String(charArrayOf(v4[ss ushr 4 and 15], v4[ss and 15])))
            v2 = v21.toString()
        }
        return v2
    }
}
