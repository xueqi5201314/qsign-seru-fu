package moe.fuqiuluo.api

import com.lingchen.core.GlobalConfig
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.lingchen.models.EnvData
import moe.fuqiuluo.ext.failure
import moe.fuqiuluo.ext.fetchGet
import moe.fuqiuluo.unidbg.session.SessionManager

fun Routing.register() {
    get("/register") {
        val uin = fetchGet("uin")!!.toLong()
        val androidId = fetchGet("android_id")!!
        val guid = fetchGet("guid")!!.lowercase()
        val qimei36 = fetchGet("qimei36")!!.lowercase()

        val key = fetchGet("key")!!

        val qua = fetchGet("qua")!!
        //val version = fetchGet("version")
        //val code = fetchGet("code")

        val apkVersion:String=  qua.split('_')[3]
        if (!GlobalConfig.AppInfoMap.containsKey(apkVersion)){
            throw MissingQQVersionError
        }
        val app = GlobalConfig.AppInfoMap[apkVersion]!!
        val hasRegister = uin in SessionManager
        if (key == GlobalConfig.key) {
            SessionManager.register(EnvData(uin, androidId, guid, qimei36, app))
            if (hasRegister) {
                call.respond(
                    APIResult(
                        0,
                        "The QQ has already loaded an instance, so this time it is deleting the existing instance and creating a new one.",
                        ""
                    )
                )
            } else {
                call.respond(APIResult(0, "Instance loaded successfully.", ""))
            }
        } else {
            throw WrongKeyError
        }
    }

    get("/destroy") {
        val uin = fetchGet("uin")!!.toLong()
        val key = fetchGet("key")!!

        if (key == GlobalConfig.key) {
           if(uin in SessionManager){
                SessionManager.close(uin)
                call.respond(APIResult(0,  "Instance destroyed successfully.", ""))
              } else {
                  failure(1, "Instance does not exist.")
           }
        } else {
            throw WrongKeyError
        }
    }
}