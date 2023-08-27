package com.lingchen.models

import com.lingchen.framework.utils.Util.hexString2Buf
import com.tencent.mobileqq.dt.model.FEBound
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AppInfo {

    var FEKitVersion: String = ""
    var ClientPubTime: String = ""
    var ClientPubVer: String = ""
    var AppName: String = ""
    var Appid = 0
    var Apk_V: String? = null
    var Apk_Sig: ByteArray? = null
    var DA2 = 0
    var SubSigMap = 0
    var MiscBitmap = 0

    /// <summary>
    /// 修订版本
    /// </summary>
    var Revision: String? = null
    var QQType: QQOSType? = null
    var ProtocolFinger: String? = null
    var ServcieType = 0
    var Applist = 0
    var Appid2 = 0
    var TbsSdkVersion = 0
    var Apk_MD5: ByteArray? = null
    var RSA_MD5: ByteArray? = null
    var Txsfcfgk_MD5: ByteArray? = null
    var QQBuild: String? = null
    var Libhobi_so_MD5: ByteArray? = null
    var Apk_FileLen = 0
    var SSOVersion = 0
    var SVN_VER = 0
    var Qua: String = ""
    var RqdServerSDKVer: String? = null
    var AppBuild: String? = null
    var AppType: String? = null
    val feBound = FEBound()
    var versionCode = 0
    var blackList: List<Long> = ArrayList()
    var workPath: File? = null

    companion object {
        @Throws(ParseException::class)
        fun parse(version: String): AppInfo? {
            val appInfo = AppInfo()
            appInfo.Apk_V = version.replace("_TEST".toRegex(), "")
            appInfo.AppName = "com.tencent.mobileqq"
            appInfo.AppType = "安卓QQ"
            appInfo.QQType = QQOSType.Android
            appInfo.SubSigMap = 66560
            appInfo.Applist = 1600000226
            appInfo.Qua = "V1_AND_SQ_8.4.8_1492_YYB_D"
            appInfo.SSOVersion = 5

            when (version) {
                "3.5.2" -> {
                    appInfo.AppName = "com.tencent.tim"
                    appInfo.Apk_Sig = hexString2Buf("775e696d09856872fdd8ab4f3f06b1e0")
                    appInfo.AppType = "安卓TIM"
                    appInfo.FEKitVersion = "2.2.183"
                    appInfo.SSOVersion = 0x12 //0x13
                    // MobileDevice.NetType = 2;
                    appInfo.Revision = String.format("%s.3f4af297", appInfo.Apk_V) //"{appInfo.Apk_V}.4fff3385";
                    appInfo.QQBuild = String.format("%s.3178", appInfo.Apk_V) //"{appInfo.Apk_V}.11050";//QQSubVersion
                    appInfo.ClientPubTime = "2021/8/27 19:02:56"
                    appInfo.ClientPubVer = "6.0.0.2484"
                    //APPVER_DENGTA:8.9.53.3964
                    appInfo.Qua = String.format(
                        "V1_AND_SQ_%s_352_TIM_D",
                        appInfo.Apk_V
                    ) //  "V1_AND_SQ_{appInfo.Apk_V}_4058_HDBM_T"; //app_bld 3898
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.TbsSdkVersion = 44281
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("30FBDBF8CFC56513E946B4535A4A32D2")
                    appInfo.Apk_MD5 = hexString2Buf("8F27F4C32B4B0EDE24174C306663DE35")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("FC372463A59F41F0E4C350D2D4A6A446")
                    appInfo.Apk_FileLen = 127450177
                    //mobileDevice.GuidType = MobileDevice.DeviceGuid.Md5_AndroidId_Mac;
                    val time = SimpleDateFormat("yyyy-MM-dd").format(Date(appInfo.ClientPubTime))
                    //Date time1= DateFormat.getInstance().parse(appInfo.ClientPubTime);
                    appInfo.ProtocolFinger = String.format("%s.%s.%s.GuanWang",
                        appInfo.QQBuild, time, appInfo.Revision!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[3]
                    )
                }

                "8.9.76" -> {

                    //appInfo.SupportGetSign = true;
                    appInfo.SSOVersion = 0x14 //0x13
                    //MobileDevice.NetType = 2;
                    appInfo.Revision = appInfo.Apk_V + ".cefd59d7"
                    appInfo.QQBuild = appInfo.Apk_V + ".12115" //QQSubVersion
                    appInfo.ClientPubTime = "2023/8/9 15:26:18"
                    appInfo.ClientPubVer = "6.0.0.2554"
                    appInfo.versionCode = 4484

                    appInfo.FEKitVersion = "7.0.344"
                    appInfo.TbsSdkVersion = 44332

                    appInfo.RSA_MD5 = hexString2Buf("4199BED3B5950A8B515E3BEF50E14137")
                    appInfo.Apk_MD5 = hexString2Buf("BCDCB19382B375C8B934C47A402D10F7")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("4A64B428C654FA48AAE171FAB5E24910")
                    appInfo.Apk_FileLen = 324932595

                    val time = SimpleDateFormat("yyyy-MM-dd").format(Date(appInfo.ClientPubTime))
                    appInfo.ProtocolFinger = String.format("%s.%s.%s.GuanWang",
                        appInfo.QQBuild, time, appInfo.Revision!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[3]
                    )
                    appInfo.Qua =
                        "V1_AND_SQ_" + appInfo.Apk_V + "_" + appInfo.versionCode + "_YYB_D" //app_bld 3898 V1_AND_SQ_8.9.68_4264_YYB_D
                    appInfo.DA2 = 0x21410E0
                    appInfo.MiscBitmap = 0xAF7FF7C
                }

                "8.9.71" -> {

                    //appInfo.SupportGetSign = true;
                    appInfo.SSOVersion = 0x14 //0x13
                    //MobileDevice.NetType = 2;
                    appInfo.Revision = appInfo.Apk_V + ".9fd08ae5"
                    appInfo.QQBuild = appInfo.Apk_V + ".11735" //QQSubVersion
                    appInfo.ClientPubTime = "2023/7/7 16:54:42"
                    appInfo.ClientPubVer = "6.0.0.2551"
                    appInfo.versionCode = 4332
                    appInfo.Qua =
                        "V1_AND_SQ_" + appInfo.Apk_V + "_" + appInfo.versionCode + "_YYB_D" //app_bld 3898 V1_AND_SQ_8.9.68_4264_YYB_D
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.FEKitVersion = "7.0.326"
                    appInfo.TbsSdkVersion = 44317
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("FFB887D0FD95978EC38088002666B0B1")
                    appInfo.Apk_MD5 = hexString2Buf("674C57FA39F09466650319F8109F4F11")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("893E2348B46CBCE763353BF29450C14A")
                    appInfo.Apk_FileLen = 313777361
                    val time = SimpleDateFormat("yyyy-MM-dd").format(Date(appInfo.ClientPubTime))
                    appInfo.ProtocolFinger = String.format("%s.%s.%s.GuanWang",
                        appInfo.QQBuild, time, appInfo.Revision!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[3]
                    )
                }

                "8.9.68" -> {

                    //appInfo.SupportGetSign = true;
                    appInfo.SSOVersion = 0x14 //0x13
                    //MobileDevice.NetType = 2;
                    appInfo.Revision = appInfo.Apk_V + ".e757227e"
                    appInfo.QQBuild = appInfo.Apk_V + ".11565" //QQSubVersion
                    appInfo.ClientPubTime = "2023/6/20 17:40:22"
                    appInfo.ClientPubVer = "6.0.0.2549"
                    appInfo.versionCode = 4264
                    appInfo.Qua =
                        "V1_AND_SQ_" + appInfo.Apk_V + "_" + appInfo.versionCode + "_YYB_D" //app_bld 3898 V1_AND_SQ_8.9.68_4264_YYB_D
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.FEKitVersion = "7.0.300"
                    appInfo.TbsSdkVersion = 44281
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("717CC1F0D952D66D1A8D5DBC51FC7C4F")
                    appInfo.Apk_MD5 = hexString2Buf("7772804F3CB4961F57CB764FBE4973E6")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("F9C98A808CABA9602431AEF572F345AE")
                    appInfo.Apk_FileLen = 312549162
                    val time = SimpleDateFormat("yyyy-MM-dd").format(Date(appInfo.ClientPubTime))
                    appInfo.ProtocolFinger = String.format("%s.%s.%s.GuanWang",
                        appInfo.QQBuild, time, appInfo.Revision!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[3]
                    )
                }

                "8.9.58" -> {
                    appInfo.Apk_Sig = hexString2Buf("a6b745bf24a2c277527716f6f36eb68d")
                    appInfo.FEKitVersion = "6.2.221"
                    appInfo.SSOVersion = 0x13 //0x13
                    // MobileDevice.NetType = 2;
                    appInfo.Revision = String.format("%s.b5517ef7", appInfo.Apk_V) //"{appInfo.Apk_V}.4fff3385";
                    appInfo.QQBuild = String.format("%s.11170", appInfo.Apk_V) //"{appInfo.Apk_V}.11050";//QQSubVersion
                    appInfo.ClientPubTime = "2023/5/19 11:35:00"
                    appInfo.ClientPubVer = "6.0.0.2545"
                    appInfo.versionCode = 4106
                    appInfo.Qua =
                        "V1_AND_SQ_" + appInfo.Apk_V + "_" + appInfo.versionCode + "_YYB_D" //app_bld 3898 V1_AND_SQ_8.9.58_4106_YYB_D
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.TbsSdkVersion = 44281
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("8CB6B873AC1C34A482DA5E47A312957A")
                    appInfo.Apk_MD5 = hexString2Buf("ECF90B228C023AB2364C6F3DE65EEA92")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("84562198BEB9241613FF1F22D23FC5C7")
                    appInfo.Apk_FileLen = 314178955
                    //mobileDevice.GuidType = MobileDevice.DeviceGuid.Md5_AndroidId_Mac;
                    val time = SimpleDateFormat("yyyy-MM-dd").format(Date(appInfo.ClientPubTime))
                    appInfo.ProtocolFinger = String.format("%s.%s.%s.GuanWang",
                        appInfo.QQBuild, time, appInfo.Revision!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[3]
                    )
                }

                "8.9.58_TEST" -> {
                    appInfo.Apk_Sig = hexString2Buf("a6b745bf24a2c277527716f6f36eb68d")
                    appInfo.FEKitVersion = "2.2.183"
                    appInfo.SSOVersion = 0x13 //0x13
                    // MobileDevice.NetType = 2;
                    appInfo.Revision = String.format("%s.4fff3385", appInfo.Apk_V) //"{appInfo.Apk_V}.4fff3385";
                    appInfo.QQBuild = String.format("%s.11050", appInfo.Apk_V) //"{appInfo.Apk_V}.11050";//QQSubVersion
                    appInfo.ClientPubTime = "2023/4/19 18:53:11"
                    appInfo.ClientPubVer = "6.0.0.2538"
                    //APPVER_DENGTA:8.9.53.3964
                    appInfo.versionCode = 4058
                    appInfo.Qua = String.format(
                        "V1_AND_SQ_%s_%d_HDBM_T",
                        appInfo.Apk_V,
                        appInfo.versionCode
                    ) //  "V1_AND_SQ_{appInfo.Apk_V}_4058_HDBM_T"; //app_bld 3898
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.TbsSdkVersion = 44281
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("4E0FF179B6104E3B66011562A7BE8A22")
                    appInfo.Apk_MD5 = hexString2Buf("F4E6C2D5CADC489FBFFF30C3E0717601")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("AEFCB690B0E827D71D1EA3A6DE56910A")
                    appInfo.Apk_FileLen = 337222478
                    //mobileDevice.GuidType = MobileDevice.DeviceGuid.Md5_AndroidId_Mac;
                    val time = SimpleDateFormat("yyyy-MM-dd").format(Date(appInfo.ClientPubTime))
                    appInfo.ProtocolFinger = String.format("%s.%s.%s.GuanWang",
                        appInfo.QQBuild, time, appInfo.Revision!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[3]
                    )
                }

                "8.9.55" -> {
                    appInfo.Apk_Sig = hexString2Buf("a6b745bf24a2c277527716f6f36eb68d")
                    appInfo.SSOVersion = 0x13 //0x13
                    //mobileDevice.NetType = 2;
                    appInfo.FEKitVersion = "2.2.196"
                    appInfo.Revision = String.format("%s.c1304831", appInfo.Apk_V)
                    appInfo.QQBuild = String.format("%s.10980", appInfo.Apk_V) //QQSubVersion
                    appInfo.ClientPubTime = "2023/4/19 18:53:11"
                    appInfo.ClientPubVer = "6.0.0.2538"
                    appInfo.versionCode = 4030
                    appInfo.Qua =
                        String.format("V1_AND_SQ_%s_%d_YYB_D", appInfo.Apk_V, appInfo.versionCode) //app_bld 3898
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.TbsSdkVersion = 44281
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("30FBDBF8CFC56513E946B4535A4A32D2")
                    appInfo.Apk_MD5 = hexString2Buf("2CD5F9392AF5422439BA3DCFC66EFEA5")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("FC372463A59F41F0E4C350D2D4A6A446")
                    appInfo.Apk_FileLen = 314060689
                    //mobileDevice.GuidType = //mobileDevice.DeviceGuid.Md5_AndroidId_Mac;
                    val time = SimpleDateFormat("yyyy-MM-dd").format(Date(appInfo.ClientPubTime))
                    appInfo.ProtocolFinger = String.format("%s.%s.%s.GuanWang",
                        appInfo.QQBuild, time, appInfo.Revision!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[3]
                    )
                }

                "8.9.53" -> {
                    appInfo.Apk_Sig = hexString2Buf("a6b745bf24a2c277527716f6f36eb68d")
                    appInfo.SSOVersion = 0x13 //0x13
                    //mobileDevice.NetType = 2;
                    appInfo.FEKitVersion = "2.2.187"
                    appInfo.Revision = String.format("%s.f4b355cc", appInfo.Apk_V)
                    appInfo.QQBuild = String.format("%s.10815", appInfo.Apk_V) //QQSubVersion
                    appInfo.ClientPubTime = "2023/04/19 18:53:11"
                    appInfo.ClientPubVer = "6.0.0.2538"
                    appInfo.versionCode = 3964
                    appInfo.Qua = String.format("V1_AND_SQ_%s_%d_YYB_D", appInfo.Apk_V, appInfo.versionCode)
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.TbsSdkVersion = 44281
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("F40FBEB13AA756DED02FA6B656750354")
                    appInfo.Apk_MD5 = hexString2Buf("D257B80833A5FF6AD02C3DF4EE770EF4")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("E0BFC74764198EB1A51834C8CA78708A")
                    appInfo.Apk_FileLen = 313324664
                    //mobileDevice.GuidType = //mobileDevice.DeviceGuid.Md5_AndroidId_Mac;
                    val time = SimpleDateFormat("yyyy-MM-dd").format(Date(appInfo.ClientPubTime))
                    appInfo.ProtocolFinger = String.format("%s.%s.%s.GuanWang",
                        appInfo.QQBuild, time, appInfo.Revision!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[3]
                    )
                }

                "8.9.50" -> {
                    appInfo.Apk_Sig = hexString2Buf("a6b745bf24a2c277527716f6f36eb68d")
                    appInfo.FEKitVersion = "2.2.166"
                    appInfo.SSOVersion = 0x13 //0x13
                    //mobileDevice.NetType = 2;
                    appInfo.Revision = "8.9.50.f5a7d351"
                    appInfo.ClientPubTime = "2023/02/16 15:10:14"
                    appInfo.ProtocolFinger = "8.9.50.10650.2023-03-17.f5a7d351.GuanWang"
                    appInfo.ClientPubVer = "6.0.0.2535"
                    appInfo.QQBuild = "8.9.50.10650" //QQSubVersion
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.versionCode = 3898
                    appInfo.Qua = String.format("V1_AND_SQ_%s_%d_YYB_D", appInfo.Apk_V, appInfo.versionCode)
                    //app_bld 3898
                    appInfo.TbsSdkVersion = 44281
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("B07EF4CE7F62A65032F8D23C0151086B")
                    appInfo.Apk_MD5 = hexString2Buf("B78CC5F76CB91D7E5E67A628CB8DD8A0")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("426167B358EFE0207C123F8C033A3367")
                    appInfo.Apk_FileLen = 311952142
                }

                "8.9.35" -> {
                    appInfo.Apk_Sig = hexString2Buf("a6b745bf24a2c277527716f6f36eb68d")
                    appInfo.FEKitVersion = "2.2.148"
                    appInfo.SSOVersion = 0x13 //0x13
                    //mobileDevice.NetType = 2;
                    appInfo.Revision = "8.9.35.a53df25c"
                    appInfo.ClientPubTime = "2023/02/16 15:10:14"
                    appInfo.ProtocolFinger = "8.9.35.10440.2023-03-17.a53df25c.GuanWang"
                    appInfo.ClientPubVer = "6.0.0.2535"
                    appInfo.QQBuild = "8.9.35.10440"
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.versionCode = 3814
                    appInfo.Qua = String.format("V1_AND_SQ_%s_%d_YYB_D", appInfo.Apk_V, appInfo.versionCode)
                    appInfo.TbsSdkVersion = 44281
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("F86B816E9298C4B41E8DE010A024FE7A")
                    appInfo.Apk_MD5 = hexString2Buf("24952983CFF67EFF5B824A45ED7C4497")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("B1755290F51E6569C49305D812548BE0")
                    appInfo.Apk_FileLen = 308140568
                }

                "8.9.33" -> {
                    appInfo.Apk_Sig = hexString2Buf("a6b745bf24a2c277527716f6f36eb68d")
                    appInfo.FEKitVersion = "2.2.130"
                    appInfo.SSOVersion = 0x13 //0x13
                    //mobileDevice.NetType = 2;
                    appInfo.Revision = "8.9.33.2045045f"
                    appInfo.ClientPubTime = "2023/01/13 16:51:38"
                    appInfo.ProtocolFinger = "8.9.33.10335.2023-01-13.2045045f."
                    appInfo.ClientPubVer = "6.0.0.2534"
                    appInfo.QQBuild = "8.9.33.10335"
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.versionCode = 3772
                    appInfo.Qua = String.format("V1_AND_SQ_%s_%d_YYB_D", appInfo.Apk_V, appInfo.versionCode)
                    appInfo.TbsSdkVersion = 44196
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("F86B816E9298C4B41E8DE010A024FE7A")
                    appInfo.Apk_MD5 = hexString2Buf("415AEAF7811C0512A1B8E4ABA1F7D496")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("B1755290F51E6569C49305D812548BE0")
                    appInfo.Apk_FileLen = 308576099
                }

                "8.9.30" -> {
                    appInfo.Apk_Sig = hexString2Buf("a6b745bf24a2c277527716f6f36eb68d")
                    appInfo.FEKitVersion = "2.2.122"
                    appInfo.SSOVersion = 0x13 //0x13
                    //mobileDevice.NetType = 2;
                    appInfo.Revision = "8.9.30.7dbbbadc"
                    appInfo.ClientPubTime = "2023/01/13 16:51:38"
                    appInfo.ProtocolFinger = "8.9.30.10230.2023-01-13.7dbbbadc.GuanWang"
                    appInfo.ClientPubVer = "6.0.0.2534"
                    appInfo.QQBuild = "8.9.30.10230"
                    appInfo.DA2 = 0x21410E0
                    //appInfo.DA2 = 0x20410C0;
                    appInfo.versionCode = 3730
                    appInfo.Qua = String.format("V1_AND_SQ_%s_%d_YYB_D", appInfo.Apk_V, appInfo.versionCode)
                    appInfo.TbsSdkVersion = 44196
                    appInfo.MiscBitmap = 0xAF7FF7C
                    appInfo.RSA_MD5 = hexString2Buf("08AD22E8720051427F424569642F457A")
                    appInfo.Apk_MD5 = hexString2Buf("415AEAF7811C0512A1B8E4ABA1F7D496")
                    appInfo.Libhobi_so_MD5 = hexString2Buf("140A15D382421306EF7BCCBA735FD2FA")
                    appInfo.Txsfcfgk_MD5 = hexString2Buf("81EEEF0F100DDD10221B1ECBC765F475")
                    appInfo.Apk_FileLen = 307719513
                }

                else -> return null
            }
            return appInfo
        }
    }

}
