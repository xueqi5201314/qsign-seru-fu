package moe.fuqiuluo.unidbg.session

import com.lingchen.core.GlobalConfig
import com.tencent.mobileqq.channel.SsoPacket
import com.tencent.mobileqq.fe.FEKit
import kotlinx.coroutines.sync.Mutex
import com.lingchen.models.EnvData
import io.ktor.util.date.*
import moe.fuqiuluo.unidbg.QSecVM

class Session(envData: EnvData) {
    internal val vm: QSecVM =
        QSecVM(envData.appInfo.workPath!!, envData, GlobalConfig.unidbg.dynarmic, GlobalConfig.unidbg.unicorn)
    internal val mutex = Mutex()
    internal val startTime: Long = getTimeMillis() / 1000

    init {
        vm.global["PACKET"] = arrayListOf<SsoPacket>()
        vm.global["mutex"] = Mutex(true)
        vm.global["qimei36"] = envData.qimei36.lowercase()
        vm.global["guid"] = envData.guid.lowercase()
        vm.init()
        FEKit.init(vm, envData.uin.toString())
    }
}