package com.lingchen.models

import com.lingchen.framework.crypto.MD5.encrypt
import com.lingchen.framework.utils.RandomGen.GenAndroidId
import com.lingchen.framework.utils.RandomGen.GenAndroidSysVersion
import com.lingchen.framework.utils.RandomGen.GenBssid
import com.lingchen.framework.utils.RandomGen.GenICCID
import com.lingchen.framework.utils.RandomGen.GenImei
import com.lingchen.framework.utils.RandomGen.GenImsi
import com.lingchen.framework.utils.RandomGen.GenMacAddress
import com.lingchen.framework.utils.RandomGen.GenMobile
import com.lingchen.framework.utils.RandomGen.GenMobileBrand
import com.lingchen.framework.utils.RandomGen.GenWifiName
import com.lingchen.framework.utils.Util.hexString2Buf
import com.lingchen.framework.utils.Util.padLeft
import java.util.*

class MobileDevice(seed: Long) {
    var RO_Build_Id: String
    var CODENAME: String
    var Incremental: String
    var DeviceId: String
    var Mobile: Long
    var ICCID: String
    var IMSI: String
    var WifiName: String
    var Sim_Operator_Name: String
    var Bssid: String
    var DeviceName: String
    var UIVersion: String
    var APN: String
    var NetType: Int
    var OS_Type: String
    var InnerVersion: String
    var BaseBand: String
    var Fingerprint: String
    var AndroidId: String
    var OSVersion: String
    var Wtsdk_Guid: String
    var Mac = "02:00:00:00:00:00"
    var QImei: String? = null
    var Manufacturer: String
    var Model: String
    var Brand: String
    var AndroidLevel: Int
    var BootId: String
    fun SDK_INT(): Int {
        return AndroidLevel
    }

    var Display: String
    var Product: String
    var Board: String
    var Bootloader: String
    var Hardware: String

    init {
//        UIName = "EMUI";
//        Build = new Build
//        {
//            TAGS = "release-keys",
//                    TYPE = "user",
//                    MANUFACTURER = "HUAWEI",
//                    DEVICE = "HWFRD",
//                    ID = "HUAWEIFRD-AL00",
//                    MODEL = "FRD-AL00",
//                    BRAND = "honor",
//                    PRODUCT = "FRD-AL00",
//                    BOARD = "FRD",
//                    HOST = "wuhjk0381cna",
//                    VERSION = new VERSION
//            {
//                INCREMENTAL = "C00B396",
//                        RELEASE = "7.0",
//                        SDK_INT = 24,
//                        CODENAME = "REL",
//                        ACTIVE_CODENAMES = new string[1] { "REL" },
//                BASE_OS = "",
//                        FIRST_SDK_INT = 0,
//                        MIN_SUPPORTED_TARGET_SDK_INT = 0,
//                        PREVIEW_SDK_INT = 0,
//                        SDK = "24",
//                        SECURITY_PATCH = "2018-01-01"
//            },
//            BOOTLOADER = "unknown",
//                    CPU_ABI = "armeabi-v7a",
//                    CPU_ABI2 = "armeabi",
//                    HARDWARE = "hi3650",
//                    IS_CONTAINER = false,
//                    IS_DEBUGGABLE = false,
//                    IS_EMULATOR = false,
//                    IS_TREBLE_ENABLED = false,
//                    PERMISSIONS_REVIEW_REQUIRED = false,
//                    RADIO = "CBP8.2,21.258.08.00.030",
//                    SERIAL = "WTK7N16A24001141",
//                    SUPPORTED_32_BIT_ABIS = new string[2] { "armeabi-v7a", "armeabi" },
//            SUPPORTED_64_BIT_ABIS = new string[1] { "arm64-v8a" },
//            SUPPORTED_ABIS = new string[3] { "arm64-v8a", "armeabi-v7a", "armeabi" },
//            TIME = 1515637388L,
//                    USER = "test"
//        };
//        TelephonyManager = new TelephonyManager
//        {
//            DeviceId = "863549011602317",
//                    SimSerialNumber = "",
//                    SubscriberId = "",
//                    LinelNumber = ""
//        };
//        Settings = new Settings
//        {
//            ANDROID_ID = "a06747b0904dd22",
//                    SerialNO = "WTK7N16A24001141"
//        };
//        WifiManager = new WifiManager
//        {
//            MacAddress = "02:00:00:00:00:00",
//                    BSSID = "b0:d5:9d:eb:46:9e",
//                    SSID = "\"360wifi\""
//        };
//        CpuInfo = new CpuInfo
//        {
//            Processor = "AArch64 Processor rev 0 (aarch64)",
//                    ProcessorId = 0,
//                    Features = "half thumb fastmult vfp edsp neon vfpv3 tls vfpv4 idiva idivt lpae evtstrm aes pmull sha1 sha2 crc32",
//                    BogoMIPS = 3.84,
//                    CPU_Implementer = "0x41",
//                    CPU_Architecture = 8,
//                    CPU_Variant = "0x0",
//                    CPU_Part = "0xd03",
//                    CPU_revision = 4
//        };
//        Version = new android.proc.Version();
//        Version.Value = "Linux version 4.14.253-android+ (cjw@mv-dev1) (gcc version 4.9 20150123 (prerelease) (GCC), GNU gold (GNU Binutils 2.25.51.20141117) 1.11) #819 SMP Fri Apr 14 09:38:13 CST 2023";
//        //Linux version 4.14.253-android+ (cjw@mv-dev1) (gcc version 4.9 20150123 (prerelease) (GCC), GNU gold (GNU Binutils 2.25.51.20141117) 1.11) #819 SMP Fri Apr 14 09:38:13 CST 2023
//        Boot_id = new Boot_id();
//        Boot_id.Value = new Guid(QQMd5.ComputeHash(seed.ToString())).ToString();
//        Locale = new Locale
//        {
//            Country = "CN",
//                    Language = "zh",
//                    Mcc = 460,
//                    Mnc = 1,
//                    Msin = 123456789,
//                    LocaleId = 2052
//        };

//        Build.MANUFACTURER = "HUAWEI";
//        Build.DEVICE = "HWFRD";
//        Build.ID = "HUAWEIFRD-AL00";
//        Build.MODEL = "FRD-AL00";
//        Build.BRAND = "honor";
//        Build.PRODUCT = "FRD-AL00";
//        Build.BOARD = "FRD";
        val androidSysVer = GenAndroidSysVersion(seed)
        val android_os_ver = androidSysVer.AndroidVersion
        val android_level = androidSysVer.AndroidLevel
        CODENAME = "REL"
        OS_Type = "android"
        OSVersion = ""
        AndroidLevel = android_level
        //VersionCode = (Build.VERSION_CODES) android_level;
        OSVersion = android_os_ver!!
        DeviceId = GenImei(seed)
        Mac = GenMacAddress(seed).uppercase(Locale.getDefault())
        IMSI = GenImsi(seed)
        ICCID = GenICCID(seed)
        APN = "wifi"
        NetType = 1
        WifiName = GenWifiName(seed)
        Mobile = GenMobile(seed)
        AndroidId = GenAndroidId(seed)
        Bssid = GenBssid(seed)
        Sim_Operator_Name = "China Mobile GSM"
        val mobileBrandInfo = GenMobileBrand(seed)
        Model = mobileBrandInfo.model.also { Product = it!! }!!
        Manufacturer = mobileBrandInfo.manufacturer!!
        Board = mobileBrandInfo.board!!
        Brand = mobileBrandInfo.brand!!
        DeviceName = mobileBrandInfo.deviceName!!
        UIVersion = "EmotionUI_5.0.1"
        APN = "3gnet"
        NetType = 0
        APN = "wifi"
        NetType = 2
        Hardware = "qcom"
        Incremental = "C" + AndroidId.substring(0, 6).uppercase(Locale.getDefault())
        BaseBand = String.format(
            "MPSS.AT.2.0.c4.7-00070-8998_GEN_PACK-2.%s.1.214666.1",
            padLeft(seed.toString(), 6, '0').substring(0, 6)
        )
        Fingerprint = String.format(
            "%s/%s/%s:7.1.2/20171130.%s:user/release-keys",
            Model, Manufacturer, Brand, Incremental
        )
        Wtsdk_Guid = encrypt(AndroidId + Mac)
        Display = String.format("%s-user %s 20171130.%s release-keys", Model, OSVersion, Incremental)
        Bootloader = "uboot"
        BootId = UUID.nameUUIDFromBytes(hexString2Buf(Wtsdk_Guid)).toString()
        InnerVersion = String.format("rel.se.infra.20230407.%s", Incremental)
        RO_Build_Id = String.format("%s%s", Manufacturer, Model)
    }
}