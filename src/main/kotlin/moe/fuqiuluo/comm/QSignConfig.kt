@file:OptIn(ExperimentalSerializationApi::class)
package moe.fuqiuluo.comm

import com.lingchen.models.MobileDevice
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Server(
    var host: String,
    var port: Int
)


@Serializable
data class Protocol(
    var qua: String,
    var version: String,
    var code: String,
    @SerialName("package_name")
    var packageName: String? = null,
)
@Serializable
data class UnidbgConfig(
    var dynarmic: Boolean,
    var unicorn: Boolean,
    var debug: Boolean,
)

@Serializable
data class QSignConfig(
    var server: Server,
    var key: String,
    @JsonNames("autoRegister", "auto_register")
    var autoRegister:Boolean,
    //@JsonNames("reloadInterval", "reload_interval")
    //var reloadInterval: Int,
    var protocol: Protocol,
    var unidbg: UnidbgConfig,
    @JsonNames("blackList", "black_list")
    var blackList: List<Long> = listOf()
)

fun QSignConfig.checkIllegal() {
    require(server.port in 1 .. 65535) { "Port is out of range." }
}