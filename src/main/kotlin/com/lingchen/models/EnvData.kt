package com.lingchen.models

import java.util.ArrayList

data class EnvData(
    var uin: Long,
    var androidId: String,
    var guid: String,
    var qimei36: String,
    var appInfo: AppInfo,
) {
    var qua: String = appInfo.Qua
    var version: String = appInfo.ClientPubVer
    var code: String = appInfo.versionCode.toString()
    var packageName: String = appInfo.AppName
    var device: MobileDevice= MobileDevice(uin)
    var blackList: List<Long> = ArrayList()
    init {
        if (androidId.isNotEmpty()) device.AndroidId = androidId
        if (guid.isNotEmpty()) device.Wtsdk_Guid = guid
        if (qimei36.isNotEmpty()) device.QImei = qimei36
    }
}
