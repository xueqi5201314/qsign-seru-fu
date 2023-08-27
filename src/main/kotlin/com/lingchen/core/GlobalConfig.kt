package com.lingchen.core

import com.lingchen.models.AppInfo
import com.lingchen.models.GlobalConfigValue
import com.lingchen.models.checkIllegal
import kotlinx.serialization.json.Json
import moe.fuqiuluo.comm.QSignConfig
import moe.fuqiuluo.comm.Server
import moe.fuqiuluo.comm.UnidbgConfig
import java.io.File

object GlobalConfig {
    @JvmField
    val AppInfoMap = HashMap<String, AppInfo>()
    var server: Server? = null
    lateinit var unidbg: UnidbgConfig
    var key: String? = null
    var autoRegister = false
    var basePath: String? = ""
    fun loadConfig(baseDir: File) {
        basePath = baseDir.path
        val json = Json { ignoreUnknownKeys = true }
        val globalConfigJson =
            json.decodeFromString<GlobalConfigValue>(baseDir.resolve("global_config.json").readText())
                .apply { checkIllegal() }
        server = globalConfigJson.server
        unidbg = globalConfigJson.unidbg
        key = globalConfigJson.key
        autoRegister = globalConfigJson.autoRegister
        val appArr = VersionControl.getAppList(baseDir)
        if (appArr.isNotEmpty()) {
            for (app in appArr) {
                val appFile = File(baseDir.path + "\\" + app)
                if (!appFile.exists() ||
                    !appFile.isDirectory ||
                    !appFile.resolve("libfekit.so").exists() ||
                    !appFile.resolve("libQSec.so").exists() ||
                    !appFile.resolve("config.json").exists()
                    || !appFile.resolve("dtconfig.json").exists()
                ) {
                    continue
                } else {
                    var versionInfo = AppInfo.parse(app)
                    if (versionInfo == null) {
                        versionInfo = AppInfo()
                    }
                    AppInfoMap[app] = versionInfo
                    versionInfo.feBound.initAssertConfig(appFile)

                    val qSignConfig = json.decodeFromString<QSignConfig>(appFile.resolve("config.json").readText())
                    versionInfo.Apk_V = qSignConfig.protocol.version
                    versionInfo.Qua = qSignConfig.protocol.qua
                    versionInfo.versionCode = Integer.parseInt(qSignConfig.protocol.code)
                    versionInfo.blackList = qSignConfig.blackList
                    versionInfo.workPath=appFile
                    println("Loaded version ${versionInfo.Apk_V},FEBond sum = ${versionInfo.feBound.checkCurrent()}")
                }
            }
        }

    }
}
