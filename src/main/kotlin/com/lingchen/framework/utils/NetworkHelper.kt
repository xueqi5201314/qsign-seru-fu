package com.lingchen.framework.utils

import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

/* loaded from: classes.dex */
object NetworkHelper {
    val localIpAddress: String
        get() {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }

            } catch (ex: SocketException) {
                //Log.e("lingchen_get IpAddress fail", ex.getMessage());
            }
            return ""
        }
}
