package com.waitou.towards.model.activity.coroutine

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

/**
 * auth aboom
 * date 2019-09-25
 */
class BaseContinuation() :Continuation<Unit> {
    override val context: CoroutineContext
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun resumeWith(result: Result<Unit>) {

    }


}