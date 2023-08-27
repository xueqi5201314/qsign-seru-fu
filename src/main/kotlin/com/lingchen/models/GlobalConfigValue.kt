package com.lingchen.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import moe.fuqiuluo.comm.Server
import moe.fuqiuluo.comm.UnidbgConfig

@Serializable
data class GlobalConfigValue(
    var server: Server,
    var key: String,
    @JsonNames("autoRegister", "auto_register")
    var autoRegister: Boolean,
    var unidbg: UnidbgConfig,
)

fun GlobalConfigValue.checkIllegal() {
    require(server.port in 1..65535) { "Port is out of range." }
    //require(reloadInterval in 20 .. 50) { "ReloadInterval is out of range." }
}