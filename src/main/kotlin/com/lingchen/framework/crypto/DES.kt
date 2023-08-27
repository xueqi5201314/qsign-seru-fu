package com.lingchen.framework.crypto

import com.lingchen.framework.utils.Util
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/* loaded from: classes.dex */
object DES {
    @JvmStatic
    fun main(args: Array<String>) {
        val result = encrypt(
            "xxxxxx".toByteArray(),
            "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456"
        )
        val printStream = System.out
        printStream.println("cccccccc" + String(result!!))
        try {
            val decryResult = decrypt(
                Util.hexString2Buf("F409A12ED30EE76AD717955B3DC3E42E81AB09D8A3C03AFB7F554379B1A2CA43C243FB21BEAC01188CB8EBB8E3CA523B15ECDFBD7869B7FCF7F1C696419E350F5F09F86E50AA2BEB91D382A7A9CDFE8DC1E866BA33B2C0737415EE6D338C1FDDA50E4BC050E024321688541F0C8BBA2FBBA9DD065CD548F4BE1CB871192C909412F91199549270338BA89B58FDF38313"),
                "65dRa93L"
            )
            Util.buf2hexString(decryResult)
            val printStream2 = System.out
            printStream2.println("ghfggh" + String(decryResult))
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    fun encrypt(datasource: ByteArray?, key: ByteArray?): ByteArray? {
        return try {
            val random = SecureRandom()
            val desKey = DESKeySpec(key)
            val keyFactory = SecretKeyFactory.getInstance("DES")
            val securekey = keyFactory.generateSecret(desKey)
            val cipher = Cipher.getInstance("DES")
            cipher.init(1, securekey, random)
            cipher.doFinal(datasource)
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    fun encrypt(datasource: ByteArray?, key: String): ByteArray? {
        return encrypt(datasource, key.toByteArray())
    }

    @Throws(Exception::class)
    fun decrypt(src: ByteArray?, key: ByteArray?): ByteArray {
        val random = SecureRandom()
        val desKey = DESKeySpec(key)
        val keyFactory = SecretKeyFactory.getInstance("DES")
        val securekey = keyFactory.generateSecret(desKey)
        val cipher = Cipher.getInstance("DES")
        cipher.init(2, securekey, random)
        return cipher.doFinal(src)
    }

    @Throws(Exception::class)
    fun decrypt(src: ByteArray?, key: String): ByteArray {
        return decrypt(src, key.toByteArray())
    }
}
