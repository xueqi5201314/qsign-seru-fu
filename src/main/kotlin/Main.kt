import com.lingchen.core.GlobalConfig
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import moe.fuqiuluo.api.*
import moe.fuqiuluo.comm.invoke
import java.io.File

//lateinit var CONFIG: QSignConfig
//lateinit var BASE_PATH: File

private val API_LIST = arrayOf(
    Routing::index,
    Routing::sign,
    Routing::energy,
    Routing::submit,
    Routing::requestToken,
    Routing::register
)

fun main(args: Array<String>) {
    args().also {
        val baseDir = File(it["basePath", "Lack of basePath."])
        GlobalConfig.loadConfig(baseDir)
    }
    GlobalConfig.server.also {
        it?.let { it1 ->
            embeddedServer(Netty, host = it1.host, port = it.port, module = Application::init)
                .start(wait = true)
        }
    }

}

fun Application.init() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if (GlobalConfig.unidbg.debug) {
                cause.printStackTrace()
            }
            call.respond(APIResult(1, cause.message ?: cause.javaClass.name, call.request.uri))
        }
    }
    routing {
        API_LIST.forEach { it(this) }
    }
}
