package com.lingchen.core

import com.lingchen.models.AppInfo
import java.io.File
import java.text.ParseException

object VersionControl {
    fun getAppList(baseDir: File): Array<out String> {
        return if (baseDir.exists()) {
            baseDir.list()!!
        } else arrayOf()
    }

    @Throws(ParseException::class)
    fun initAppInfo(baseDir: String): Int {
        val versionDir = File(baseDir)
        val files = getAppList(versionDir)
        for (version in files) {
            val versionInfo = AppInfo.parse(version)
            if (versionInfo != null) GlobalConfig.AppInfoMap[version] = versionInfo
        }
        if (GlobalConfig.AppInfoMap.isEmpty()) {
            println("缺少QQ版本")
            return 0
        }
        return GlobalConfig.AppInfoMap.size
    }
}
