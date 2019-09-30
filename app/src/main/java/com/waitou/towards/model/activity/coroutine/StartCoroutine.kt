package com.waitou.towards.model.activity.coroutine

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.waitou.net_library.http.AsyncOkHttpClient
import okhttp3.Request
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * auth aboom
 * date 2019-09-25
 */
fun startCoroutine(block: suspend () -> Unit) {
    block.startCoroutine(BaseContinuation())
}

private val hanlder by lazy { Handler(Looper.getMainLooper()) }

suspend fun startLogoPic(url: String) = suspendCoroutine<ByteArray> { conitnuation ->
    Log.e("aa", "异步任务开始前")
    AsyncTask {
        try {
            Log.e("aa", "耗时处理...")
            val request = Request.Builder().url(url).build()
            val response = AsyncOkHttpClient.getOkHttpClient().newCall(request).execute()
            if (response.isSuccessful) {
                response.body()?.byteStream()?.readBytes()?.let { byte ->
                    hanlder.post {
                        conitnuation.resume(byte)
                    }
                }
            } else {
                hanlder.post {
                    conitnuation.resumeWithException(Throwable(response.message()))
                }
            }
        } catch (e: Exception) {
            conitnuation.resumeWithException(e)
        }

    }.execute()
}