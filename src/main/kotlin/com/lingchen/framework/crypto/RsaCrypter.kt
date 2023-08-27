package com.lingchen.framework.crypto

import com.lingchen.framework.utils.Util
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.*
import javax.crypto.Cipher

/* loaded from: classes.dex */
class RsaCrypter {
    var _privateKey: ByteArray?=null
    var _publicKey: ByteArray?=null
    fun getPublicKey(base64: String): PublicKey? {
        return getPublicKey(Base64Helper.decode(base64))
    }

    fun getPrivateKey(base64: String): PrivateKey? {
        return getPrivateKey(Base64Helper.decode(base64))
    }

    @JvmOverloads
    fun genkeyPairs(keySize: Int = 1024): Boolean {
        return try {
            val keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM)
            keyPairGen.initialize(keySize)
            when (keySize) {
                516 -> MAX_ENCRYPT_LENGTH = 64
                1024 -> MAX_ENCRYPT_LENGTH = 128
                2048 -> MAX_ENCRYPT_LENGTH = 256
            }
            val keyPair = keyPairGen.generateKeyPair()
            val publicKey = keyPair.public as RSAPublicKey
            val privateKey = keyPair.private as RSAPrivateKey
            _publicKey = publicKey.encoded
            val printStream = System.out
            printStream.println("cli_module:" + publicKey.modulus.toString(16))
            val printStream2 = System.out
            printStream2.println("publickey:" + Util.buf2hexString(_publicKey!!))
            _privateKey = privateKey.encoded
            val printStream3 = System.out
            printStream3.println("privatekey:" + Util.buf2hexString(_privateKey!!))
            true
        } catch (e: Exception) {
            false
        }
    }

    fun decryptByPublicKey(`in`: ByteArray): ByteArray? {
        val publicKey = getPublicKey(_publicKey)
        return decryptByPublicKey(publicKey, `in`)
    }

    @Throws(Throwable::class)
    fun encryptByPublicKey(pubkey: ByteArray?, paramByteOfArray: ByteArray?): ByteArray? {
        val publicKey = getPublicKey(pubkey)
        return encryptByPublicKey(publicKey, paramByteOfArray)
    }

    fun decryptByPublicKey(pubkey: ByteArray?, paramByteOfArray: ByteArray): ByteArray? {
        val publicKey = getPublicKey(pubkey)
        return decryptByPublicKey(publicKey, paramByteOfArray)
    }

    @Throws(Throwable::class)
    fun encryptByPublicKey(pubkeyBase64: String, paramByteOfArray: ByteArray?): ByteArray? {
        val publicKey = getPublicKey(pubkeyBase64)
        return encryptByPublicKey(publicKey, paramByteOfArray)
    }

    fun decryptByPublicKey(pubkeyBase64: String, paramByteOfArray: ByteArray): ByteArray? {
        val publicKey = getPublicKey(pubkeyBase64)
        return decryptByPublicKey(publicKey, paramByteOfArray)
    }

    private fun decryptByPublicKey(publicKey: PublicKey?, paramArrayOfByte: ByteArray): ByteArray? {
        var i = 0
        try {
            val localCipher = Cipher.getInstance(padding)
            localCipher.init(2, publicKey)
            when (publicKey!!.encoded.size) {
                516 -> MAX_ENCRYPT_LENGTH = 64
                1024 -> MAX_ENCRYPT_LENGTH = 128
                2048 -> MAX_ENCRYPT_LENGTH = 256
            }
            val j = paramArrayOfByte.size
            val arrayOfByte1 = ByteArray(j)
            if (j % MAX_ENCRYPT_LENGTH != 0) {
                return null
            }
            var k = 0
            while (true) {
                val i2 = MAX_ENCRYPT_LENGTH
                if (i < j / i2) {
                    val arrayOfByte3 = ByteArray(i2)
                    System.arraycopy(paramArrayOfByte, i * i2, arrayOfByte3, 0, i2)
                    val arrayOfByte4 = localCipher.doFinal(arrayOfByte3)
                    System.arraycopy(arrayOfByte4, 0, arrayOfByte1, k, arrayOfByte4.size)
                    k += arrayOfByte4.size
                    i++
                } else {
                    val arrayOfByte2 = ByteArray(k)
                    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, k)
                    return arrayOfByte2
                }
            }
        } catch (e: Exception) {
            return null
        }
    }

    @Throws(Throwable::class)
    fun encryptByPublicKey(publicKey: PublicKey?, plainText: ByteArray?): ByteArray? {
        val pieceCount: Int
        if (plainText == null || publicKey == null) {
            return null
        }
        val localCipher = Cipher.getInstance(padding)
        localCipher.init(1, publicKey)
        when (publicKey.encoded.size) {
            516 -> MAX_ENCRYPT_LENGTH = 64
            1024 -> MAX_ENCRYPT_LENGTH = 128
            2048 -> MAX_ENCRYPT_LENGTH = 256
        }
        val arrLen = plainText.size
        var max_enc_block_length = 117
        if (arrLen >= 117) {
            val d = (arrLen / 117).toDouble()
            java.lang.Double.isNaN(d)
            pieceCount = Math.round(d + 0.5).toInt()
        } else {
            max_enc_block_length = arrLen
            pieceCount = 1
        }
        val out = ByteArrayOutputStream()
        for (i in 0 until pieceCount) {
            val cache = ByteArray(max_enc_block_length)
            System.arraycopy(plainText, i * 117, cache, 0, max_enc_block_length)
            val partialResult = localCipher.doFinal(cache)
            out.write(partialResult, 0, partialResult.size)
        }
        return out.toByteArray()
    }

    @Throws(Throwable::class)
    fun encryptByPublicKey(`in`: ByteArray?): ByteArray? {
        val bArr = _publicKey
        if (bArr == null || bArr.size <= 0) {
            _publicKey = Util.hexString2Buf(DEFAULT_PUB_KEY)
        }
        val pubkey = getPublicKey(_publicKey)
        return encryptByPublicKey(pubkey, `in`)
    }

    fun encryptByPrivateKey(`in`: ByteArray?): ByteArray? {
        val bArr = _privateKey
        if (bArr == null || bArr.size <= 0) {
            _privateKey = Util.hexString2Buf(DEFAULT_PRIV_KEY)
        }
        val privateKey = getPrivateKey(_privateKey)
        return encryptByPrivateKey(privateKey, `in`)
    }

    fun encryptByPrivateKey(privateKeyArr: ByteArray?, paramByteOfArray: ByteArray?): ByteArray? {
        val privateKey = getPrivateKey(privateKeyArr)
        return encryptByPrivateKey(privateKey, paramByteOfArray)
    }

    @Throws(Throwable::class)
    fun decryptByPrivateKey(privateKeyArr: ByteArray?, paramByteOfArray: ByteArray): ByteArray {
        val privateKey = getPrivateKey(privateKeyArr)
        return decryptByPrivateKey(privateKey, paramByteOfArray)
    }

    fun encryptByPrivateKey(privateBase64: String, paramByteOfArray: ByteArray?): ByteArray? {
        val privateKey = getPrivateKey(privateBase64)
        return encryptByPrivateKey(privateKey, paramByteOfArray)
    }

    fun decryptByPrivateKey(pubkeyBase64: String, paramByteOfArray: ByteArray): ByteArray? {
        val publicKey = getPublicKey(pubkeyBase64)
        return decryptByPublicKey(publicKey, paramByteOfArray)
    }

    @Throws(Throwable::class)
    fun decryptByPrivateKey(privateKey: PrivateKey?, encryptedData: ByteArray): ByteArray {
        var cache: ByteArray
        var offSet = 0
        var i = 0
        val localCipher = Cipher.getInstance(padding)
        localCipher.init(2, privateKey)
        val out = ByteArrayOutputStream()
        val inputLen = encryptedData.size
        val keySize = privateKey!!.encoded.size
        when (keySize) {
            347 -> MAX_ENCRYPT_LENGTH = 64
            636 -> MAX_ENCRYPT_LENGTH = 128
            1218 -> MAX_ENCRYPT_LENGTH = 256
        }
        if (inputLen % MAX_ENCRYPT_LENGTH == 0) {
            while (inputLen - offSet > 0) {
                val i2 = inputLen - offSet
                val i3 = MAX_ENCRYPT_LENGTH
                cache = if (i2 >= i3) {
                    localCipher.doFinal(encryptedData, offSet, i3)
                } else {
                    localCipher.doFinal(encryptedData, offSet, inputLen - offSet)
                }
                out.write(cache, 0, cache.size)
                i++
                offSet = i * MAX_ENCRYPT_LENGTH
            }
        }
        out.toByteArray()
        out.close()
        return out.toByteArray()
    }

    private fun encryptByPrivateKey(privateKey: PrivateKey?, paramArrayOfByte: ByteArray?): ByteArray? {
        val pieceCount: Int
        var byteArrayResult: ByteArray? = null
        if (paramArrayOfByte == null || privateKey == null) {
            return null
        }
        try {
            val localCipher = Cipher.getInstance(padding)
            localCipher.init(1, privateKey)
            val keySize = privateKey.encoded.size
            when (keySize) {
                347 -> MAX_ENCRYPT_LENGTH = 64
                636 -> MAX_ENCRYPT_LENGTH = 128
                1218 -> MAX_ENCRYPT_LENGTH = 256
            }
            val arrLen = paramArrayOfByte.size
            var max_enc_block_length = 117
            if (arrLen >= 117) {
                val d = (arrLen / 117).toDouble()
                java.lang.Double.isNaN(d)
                pieceCount = Math.round(d + 0.5).toInt()
            } else {
                max_enc_block_length = arrLen
                pieceCount = 1
            }
            byteArrayResult = ByteArray(pieceCount * 128)
            for (k in 0 until pieceCount) {
                val arrayOfByte2 = ByteArray(max_enc_block_length)
                System.arraycopy(paramArrayOfByte, k * 117, arrayOfByte2, 0, max_enc_block_length)
                val doFinal = localCipher.doFinal(arrayOfByte2)
                val i = MAX_ENCRYPT_LENGTH
                System.arraycopy(doFinal, 0, byteArrayResult, i * k, i)
            }
        } catch (e: Exception) {
        }
        return byteArrayResult
    }

    fun setPublicKey(publicKey: ByteArray) {
        _publicKey = publicKey
    }

    companion object {
        const val DEFAULT_PRIV_KEY =
            "3082025e02010002818100daaa2a418b271f3dfcf8f0a9120326d47f07618593d8d71d61a4fe987cc47740e491105bf8e68bd479bf51dfe19d3b06e12017df6d87a0f43bb82b57f59bd4220f2a3d8d68904a6ddb51197989e6e82512d8d8fa6c41b755a8ca962595d3e1e1be7ea01677249be4794cd7c6682d611c1bd81f0a16231fb83517515b94d13e5d02030100010281806bbfca4ebde92b45fa7018f6d6ff6294f77b859cb2fbf9146b5748758f95a845fbdb57ba5a6e109d44d8f7d9606d7ff6a5dc90a6f26c10ee08b779f43ffce78c6fc0feb8a063885e1b9ee6f3615b8b850e6b89365fe7037de6928e3ca2b93c55f60fff2873ce9a88254c4c553aece69c311ddd37bb6dfc8c45399144a59f25e9024100f12a24798dfc2d56e719df7a8f9f870037007ac187c1a76a88e4749347cbc270ea54491b27309d02d0d0e1bb566a3f4972c286193e34b3863962a103ab2e9063024100e81db1b9e333baa72636599b792f7ae2fc06593a94851bd15c5d209c5d5d2836ecf2309c52426ca297475bfd8920e5fade8765afd9f6822ee4b7e333d234523f024100e356ead37bb981b42e5f0180b3eb9a83e5559a62ddeafc3b3d98bf1c27ce3919e08c5bee30df6ee3bc9d6c6e01645f0c8a163dfb85dc806fc3a0ea505f0aa229024100dee10c73f2bf0c1e4de9e8370ab155ad38d49bbf4d375713bc3dcbff7902e7877e13bc2b8e2d2c051f7faccc116d5e877a3fc69b898e5348d5e3e0ad34cd7a9f024100ede9b6081428b058d2db5c7ccbef7a178d9003c547319d177a5d1d219e9727f18dbe41008198af9a01fb684b6c96c536c8fbb98532b908028c2d4dce7281aff9"
        const val DEFAULT_PUB_KEY =
            "30818902818100daaa2a418b271f3dfcf8f0a9120326d47f07618593d8d71d61a4fe987cc47740e491105bf8e68bd479bf51dfe19d3b06e12017df6d87a0f43bb82b57f59bd4220f2a3d8d68904a6ddb51197989e6e82512d8d8fa6c41b755a8ca962595d3e1e1be7ea01677249be4794cd7c6682d611c1bd81f0a16231fb83517515b94d13e5d0203010001"
        var padding = "RSA/ECB/PKCS1Padding"
        var KEY_ALGORITHM = "RSA"
        var MAX_ENCRYPT_LENGTH = 128
        fun getPublicKey(keyBytes: ByteArray?): PublicKey? {
            val x509EncodedKeySpec = X509EncodedKeySpec(keyBytes)
            return try {
                val factory =
                    KeyFactory.getInstance(KEY_ALGORITHM)
                factory.generatePublic(x509EncodedKeySpec)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                null
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace()
                null
            }
        }

        fun getPublicKey(modulus: String?, exponent: String?): RSAPublicKey? {
            return try {
                val b1 = BigInteger(modulus, 16)
                val b2 = BigInteger(exponent, 16)
                val keyFactory = KeyFactory.getInstance("RSA")
                val keySpec = RSAPublicKeySpec(b1, b2)
                keyFactory.generatePublic(keySpec) as RSAPublicKey
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun getPrivateKey(modulus: String?, exponent: String?): RSAPrivateKey? {
            return try {
                val b1 = BigInteger(modulus)
                val b2 = BigInteger(exponent)
                val keyFactory = KeyFactory.getInstance("RSA")
                val keySpec = RSAPrivateKeySpec(b1, b2)
                keyFactory.generatePrivate(keySpec) as RSAPrivateKey
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun getPrivateKey(keyBytes: ByteArray?): PrivateKey? {
            val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(keyBytes)
            return try {
                val factory =
                    KeyFactory.getInstance(KEY_ALGORITHM)
                factory.generatePrivate(pkcs8EncodedKeySpec)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                null
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace()
                null
            }
        }
    }
}
