package com.lingchen.framework.utils

import java.io.*
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors

/* loaded from: classes.dex */
object Util {
    private var random: Random? = null
    private val sb = StringBuilder()
    private val hex = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    const val ACTION_POINTER_INDEX_MASK = 0x0000ff00
    fun buf_to_int16(buf: ByteArray, pos: Int): Int {
        return (buf[pos].toInt() shl 8 and ACTION_POINTER_INDEX_MASK) + (buf[pos + 1].toInt() and 255)
    }

    //    public static int buf_to_int32(byte[] buf, int pos) {
    //        return ((buf[pos] << 24) & ViewCompat.MEASURED_STATE_MASK) + ((buf[pos + 1] << 16) & 16711680) + ((buf[pos + 2] << 8) & ACTION_POINTER_INDEX_MASK) + ((buf[pos + 3] << 0) & 255);
    //    }
    fun buf_to_int64(buf: ByteArray, pos: Int): Long {
        return (buf[pos].toLong() shl 56 and -72057594037927936L) + (buf[pos + 1].toLong() shl 48 and 71776119061217280L) + (buf[pos + 2].toLong() shl 40 and 280375465082880L) + (buf[pos + 3].toLong() shl 32 and 1095216660480L) + ((buf[pos + 4].toInt() shl 24).toLong() and 4278190080L) + (buf[pos + 5].toInt() shl 16 and 16711680) + (buf[pos + 6].toInt() shl 8 and 65280) + (buf[pos + 7].toInt() and 255)
    }

    fun buf_to_int8(buf: ByteArray, pos: Int): Int {
        return buf[pos].toInt() and 255
    }

    fun buf2hexString(bs: ByteArray): String {
        val r = StringBuilder()
        for (b in bs) {
            var hex2 = Integer.toHexString(b.toInt() and 255)
            if (hex2.length == 1) {
                hex2 = "0$hex2"
            }
            r.append(hex2.uppercase(Locale.getDefault()))
        }
        return r.toString()
    }

    @JvmStatic
    fun hexString2Buf(hexString: String): ByteArray {
        if (hexString == "") {
            return null!!
        }
        val hexString2 = hexString.uppercase(Locale.getDefault())
        val length = hexString2.length / 2
        val hexChars = hexString2.toCharArray()
        val d = ByteArray(length)
        for (i in 0 until length) {
            val pos = i * 2
            d[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(
                hexChars[pos + 1]
            ).toInt()).toByte()
        }
        return d
    }

    private fun charToByte(c: Char): Byte {
        return "0123456789ABCDEF".indexOf(c).toByte()
    }

    fun getUnsignedInt(`in`: ByteArray, offset: Int, len: Int): Long {
        val end: Int
        var ret: Long = 0
        end = if (len > 8) {
            offset + 8
        } else {
            offset + len
        }
        for (i in offset until end) {
            ret = ret shl 8 or (`in`[i].toInt() and 255).toLong()
        }
        return 4294967295L and ret or (ret ushr 32)
    }

    fun isByteArrayEqual(b1: ByteArray, b2: ByteArray): Boolean {
        if (b1.size != b2.size) {
            return false
        }
        for (i in b1.indices) {
            if (b1[i] != b2[i]) {
                return false
            }
        }
        return true
    }

    fun isIpZero(ip: ByteArray): Boolean {
        for (b in ip) {
            if (b.toInt() != 0) {
                return false
            }
        }
        return true
    }

    fun compareMD5(m1: ByteArray?, m2: ByteArray?): Boolean {
        if (m1 == null || m2 == null) {
            return true
        }
        for (i in 0..15) {
            if (m1[i] != m2[i]) {
                return false
            }
        }
        return true
    }

    fun getBytes(s: String, encoding: String?): ByteArray {
        return try {
            s.toByteArray(charset(encoding!!))
        } catch (e: UnsupportedEncodingException) {
            s.toByteArray()
        }
    }

    fun getBytes(s: String): ByteArray {
        return getBytes(s, "utf-8")
    }

    fun getString(s: String, srcEncoding: String?, destEncoding: String?): String {
        return try {
            String(s.toByteArray(charset(srcEncoding!!)), charset(destEncoding!!))
        } catch (e: UnsupportedEncodingException) {
            s
        }
    }

    fun getString(b: ByteArray?, encoding: String?): String {
        return try {
            String(b!!, charset(encoding!!))
        } catch (e: UnsupportedEncodingException) {
            String(b!!)
        }
    }

    fun getString(b: ByteArray?, offset: Int, len: Int, encoding: String?): String {
        return try {
            String(b!!, offset, len, charset(encoding!!))
        } catch (e: UnsupportedEncodingException) {
            String(b!!, offset, len)
        }
    }

    fun getInt(s: String, faultValue: Int): Int {
        return try {
            s.toInt()
        } catch (e: NumberFormatException) {
            faultValue
        }
    }

    fun getLong(s: String, radix: Int, faultValue: Long): Long {
        return try {
            s.toLong(radix)
        } catch (e: NumberFormatException) {
            faultValue
        }
    }

    fun getInt(s: String, radix: Int, faultValue: Int): Int {
        return try {
            s.toInt(radix)
        } catch (e: NumberFormatException) {
            faultValue
        }
    }

    fun isInt(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun getByte(s: String, faultValue: Int): Byte {
        return (getInt(s, faultValue) and 255).toByte()
    }

    fun getIpStringFromBytes(ip: ByteArray): String {
        val sb2 = sb
        sb2.delete(0, sb2.length)
        sb2.append(ip[0].toInt() and 255)
        sb2.append('.')
        sb2.append(ip[1].toInt() and 255)
        sb2.append('.')
        sb2.append(ip[2].toInt() and 255)
        sb2.append('.')
        sb2.append(ip[3].toInt() and 255)
        return sb2.toString()
    }

    fun getIpByteArrayFromString(ip: String?): ByteArray {
        val ret = ByteArray(4)
        val st = StringTokenizer(ip, ".")
        try {
            ret[0] = (st.nextToken().toInt() and 255).toByte()
            ret[1] = (st.nextToken().toInt() and 255).toByte()
            ret[2] = (st.nextToken().toInt() and 255).toByte()
            ret[3] = (st.nextToken().toInt() and 255).toByte()
        } catch (e: Exception) {
        }
        return ret
    }

    fun isIpEquals(ip1: ByteArray, ip2: ByteArray): Boolean {
        return ip1[0] == ip2[0] && ip1[1] == ip2[1] && ip1[2] == ip2[2] && ip1[3] == ip2[3]
    }

    fun indexOf(buf: ByteArray, begin: Int, b: Byte): Int {
        for (i in begin until buf.size) {
            if (buf[i] == b) {
                return i
            }
        }
        return -1
    }

    fun indexOf(buf: ByteArray, begin: Int, b: ByteArray): Int {
        for (i in begin until buf.size) {
            for (b2 in b) {
                if (buf[i] == b2) {
                    return i
                }
            }
        }
        return -1
    }

    fun random(): Random? {
        if (random == null) {
            random = Random()
        }
        return random
    }

    fun randomKey(): ByteArray {
        val key = ByteArray(16)
        random()!!.nextBytes(key)
        return key
    }

    fun parseInt(content: ByteArray, offset: Int): Int {
        val offset2 = offset + 1
        val offset3 = offset2 + 1
        val i = content[offset].toInt() and 255 shl 24 or (content[offset2].toInt() and 255 shl 16)
        val offset4 = offset3 + 1
        val i2 = i or (content[offset3].toInt() and 255 shl 8)
        return i2 or (content[offset4].toInt() and 255)
    }

    fun parseChar(content: ByteArray, offset: Int): Char {
        val offset2 = offset + 1
        return (content[offset].toInt() and 255 shl 8 or (content[offset2].toInt() and 255)).toChar()
    }

    fun convertByteToHexString(b: ByteArray?): String {
        return if (b == null) {
            "null"
        } else convertByteToHexString(b, 0, b.size)
    }

    fun convertByteToHexString(b: ByteArray?, offset: Int, len: Int): String {
        if (b == null) {
            return "null"
        }
        var end = offset + len
        if (end > b.size) {
            end = b.size
        }
        val sb2 = sb
        sb2.delete(0, sb2.length)
        for (i in offset until end) {
            val sb3 = sb
            val cArr = hex
            sb3.append(cArr[b[i].toInt() and 240 ushr 4])
            sb3.append(cArr[b[i].toInt() and 15])
            sb3.append(' ')
        }
        val sb4 = sb
        if (sb4.length > 0) {
            sb4.deleteCharAt(sb4.length - 1)
        }
        return sb4.toString()
    }

    fun convertByteToHexStringWithoutSpace(b: ByteArray?): String {
        return if (b == null) {
            "null"
        } else convertByteToHexStringWithoutSpace(b, 0, b.size)
    }

    fun convertByteToHexStringWithoutSpace(b: ByteArray?, offset: Int, len: Int): String {
        if (b == null) {
            return "null"
        }
        var end = offset + len
        if (end > b.size) {
            end = b.size
        }
        val sb2 = sb
        sb2.delete(0, sb2.length)
        for (i in offset until end) {
            val sb3 = sb
            val cArr = hex
            sb3.append(cArr[b[i].toInt() and 240 ushr 4])
            sb3.append(cArr[b[i].toInt() and 15])
        }
        return sb.toString()
    }

    fun convertHexStringToByte(s: String): ByteArray? {
        return try {
            val st = StringTokenizer(s.trim { it <= ' ' }, " ")
            val ret = ByteArray(st.countTokens())
            var i = 0
            while (st.hasMoreTokens()) {
                val byteString = st.nextToken()
                if (byteString.length > 2) {
                    return null
                }
                ret[i] = (byteString.toInt(16) and 255).toByte()
                i++
            }
            ret
        } catch (e: Exception) {
            null
        }
    }

    fun convertHexStringToByteNoSpace(s: String): ByteArray {
        val len = s.length
        val ret = ByteArray(len ushr 1)
        var i = 0
        while (i <= len - 2) {
            ret[i ushr 1] = (s.substring(i, i + 2).trim { it <= ' ' }.toInt(16) and 255).toByte()
            i += 2
        }
        return ret
    }

    fun convertIpToString(ip: ByteArray): String {
        val sb2 = sb
        sb2.delete(0, sb2.length)
        for (b in ip) {
            val sb3 = sb
            sb3.append(b.toInt() and 255)
            sb3.append('.')
        }
        val sb4 = sb
        sb4.deleteCharAt(sb4.length - 1)
        return sb4.toString()
    }

    @JvmOverloads
    fun findByteOffset(ar: ByteArray, b: Byte, from: Int = 0): Int {
        for (i in from until ar.size) {
            if (ar[i] == b) {
                return i
            }
        }
        return -1
    }

    fun findByteOffset(ar: ByteArray, b: Byte, from: Int, occurs: Int): Int {
        var j = 0
        for (i in from until ar.size) {
            if (ar[i] == b && j + 1.also { j = it } == occurs) {
                return i
            }
        }
        return -1
    }

    fun CharToByteArray(c: Char): ByteArray {
        return byteArrayOf((65280 and c.code ushr 8).toByte(), (c.code and 255).toByte())
    }

    fun CharArrayToByteArray(chars: CharArray): ByteArray {
        val cs: Charset
        cs = StandardCharsets.UTF_8
        val cb = CharBuffer.allocate(chars.size)
        cb.put(chars)
        cb.flip()
        val bb = cs.encode(cb)
        return bb.array()
    }

    fun getIntFromBytes(b: ByteArray, offset: Int, len: Int): Int {
        var len = len
        if (len > 4) {
            len = 4
        }
        var ret = 0
        val end = offset + len
        for (i in offset until end) {
            ret = ret or (b[i].toInt() and 255)
            if (i < end - 1) {
                ret = ret shl 8
            }
        }
        return ret
    }

    fun getSubBytes(b: ByteArray?, offset: Int, len: Int): ByteArray {
        val ret = ByteArray(len)
        System.arraycopy(b, offset, ret, 0, len)
        return ret
    }

    fun InputStream2ByteArray(inStream: InputStream): ByteArray {
        val swapStream = ByteArrayOutputStream()
        val buff = ByteArray(100)
        while (true) {
            try {
                val rc = inStream.read(buff, 0, 100)
                if (rc <= 0) {
                    break
                }
                swapStream.write(buff, 0, rc)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return swapStream.toByteArray()
    }

    fun ByteArray2InputStream(inBytes: ByteArray?): InputStream {
        return ByteArrayInputStream(inBytes)
    }

    @JvmOverloads
    fun SendBroadMsg(msgBytes: ByteArray, port: Int = 10086) {
        try {
            val udp = MulticastSocket(0)
            val remoteAddress = InetAddress.getByName("255.255.255.255")
            val dp = DatagramPacket(msgBytes, msgBytes.size, remoteAddress, port)
            udp.send(dp)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun SendBroadMsg(msg: String) {
        val msgBytes: ByteArray
        msgBytes = msg.toByteArray(StandardCharsets.UTF_8)
        SendBroadMsg(msgBytes)
    }

    fun randomMacAddress(seed: Long): String {
        val random = Random(seed)
        val sb = StringBuilder()
        for (i in 0..5) {
            if (sb.length > 0) {
                sb.append(":")
            }
            val `val` = random.nextInt(i + 1)
            val element = Integer.toHexString(`val`)
            sb.append(0)
            sb.append(element)
        }
        return sb.toString()
    }

    @JvmStatic
    fun padLeft(src: String, len: Int, ch: Char): String {
        val diff = len - src.length
        if (diff <= 0) {
            return src
        }
        val charr = CharArray(len)
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length)
        for (i in src.length until len) {
            charr[i] = ch
        }
        return String(charr)
    }

    /**
     * @作者 尧
     * @功能 String右对齐
     */
    fun padRight(src: String, len: Int, ch: Char): String {
        val diff = len - src.length
        if (diff <= 0) {
            return src
        }
        val charr = CharArray(len)
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length)
        for (i in 0 until diff) {
            charr[i] = ch
        }
        return String(charr)
    }

    fun readText(file: String?): String {
        return readText(File(file))
    }

    fun readText(file: File?): String {
        val result = StringBuilder()
        try {
            // 构造一个BufferedReader类来读取文件
            val br = BufferedReader(FileReader(file))
            var s: String?
            // 使用readLine方法，一次读一行
            while (br.readLine().also { s = it } != null) {
                result.append(System.lineSeparator()).append(s)
            }
            br.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result.toString()
    }

    @Throws(IOException::class)
    fun readLines(file: String?): List<String> {
        return Files.lines(Paths.get(file)).collect(Collectors.toList())
    }

    @Throws(IOException::class)
    fun readAllLines(file: String?, encoding: Charset?): List<String> {
        // 转换成List<String>, 要注意java.lang.OutOfMemoryError: Java heap space
        return Files.readAllLines(
            Paths.get(file),
            encoding
        )
    }

    @Throws(IOException::class)
    fun readAllBytes(file: File): ByteArray {
        return readAllBytes(file.path)
    }

    @Throws(IOException::class)
    fun readAllBytes(file: String?): ByteArray {
        //如果是JDK11用上面的方法，如果不是用这个方法也很容易
        return Files.readAllBytes(Paths.get(file))
    }

    @Throws(IOException::class)
    fun writeAllText(filepath: String?, content: String?) {
        BufferedWriter(FileWriter(filepath)).use { bufferedWriter -> bufferedWriter.write(content) }
    }

    @Throws(IOException::class)
    fun writeAllText(filepath: File?, content: String?) {
        BufferedWriter(FileWriter(filepath)).use { bufferedWriter -> bufferedWriter.write(content) }
    }

    @Throws(IOException::class)
    fun appendAllText(filepath: File?, content: String?) {
        FileWriter(filepath).use { fileWriter -> fileWriter.append(content) }
    }

    @Throws(IOException::class)
    fun writeAllBytes(filepath: File?, content: ByteArray?) {
        FileOutputStream(filepath).use { fileOutputStream -> fileOutputStream.write(content) }
    }

    fun replaceAll(str: String?, regex: String?, replace: String?): String? {
        return if (str != null && "" != str) {
            val pattern = Pattern.compile(regex) //去掉空格符合换行符
            val matcher = pattern.matcher(str)
            matcher.replaceAll(replace)
        } else {
            str
        }
    }
}
