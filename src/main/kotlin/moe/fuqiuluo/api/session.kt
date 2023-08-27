package moe.fuqiuluo.api

import com.lingchen.core.GlobalConfig
import io.ktor.util.date.*
import kotlinx.coroutines.sync.Mutex
import moe.fuqiuluo.unidbg.session.Session
import moe.fuqiuluo.unidbg.session.SessionManager
import kotlin.concurrent.timer
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun initSession(uin: Long): Session? {
    val session = SessionManager[uin]
    if (session != null) {
        if (getTimeMillis() / 1000 - session.startTime > 5 * 60) {
            return null
        }
    }
    return session ?: if (!GlobalConfig.autoRegister) {
        throw SessionNotFoundError
    } else {
        null
    }
}

fun findSession(uin: Long): Session {
    return SessionManager[uin] ?: throw SessionNotFoundError
}

internal suspend inline fun <T> Session.withLock(action: () -> T): T {
    return mutex.withLockAndTimeout(5000, action)
}

@OptIn(ExperimentalContracts::class)
private suspend inline fun <T> Mutex.withLockAndTimeout(timeout: Long, action: () -> T): T {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    lock()
    val job = timer(initialDelay = timeout, period = timeout) {
        if (isLocked)
            unlock()
    }
    try {
        return action().also {
            job.cancel()
        }
    } finally {
        try {
            unlock()
        } catch (e: java.lang.Exception) {
        }
    }
}