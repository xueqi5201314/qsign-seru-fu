package moe.fuqiuluo.api

import com.lingchen.core.GlobalConfig
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import moe.fuqiuluo.comm.Protocol

@Serializable
data class APIResult<T>(
    val code: Int,
    val msg: String = "",
    @Contextual
    val data: T? = null
)

@Serializable
data class APIInfo(
    val version: String,
    val protocols: ArrayList<Protocol>
)
fun Routing.index() {
    val protocols = arrayListOf<Protocol>()
    for (app in GlobalConfig.AppInfoMap) {
        val protocol = Protocol(app.value.Qua, app.value.Apk_V!!, app.value.versionCode.toString())
        protocols.add(protocol)
    }
    get("/") {
        call.respond(
            APIResult(
                0, "IAA 云天明 章北海", APIInfo(
                    version = BuildConfig.version,
                    protocols = ArrayList(protocols)
                )
            )
        )
    }
}
