package com.waitou.towards.model.activity.coroutine

import java.util.concurrent.Executors

/**
 * auth aboom
 * date 2019-09-25
 */
private val pool by lazy {
    Executors.newCachedThreadPool()
}

class AsyncTask(private val block: () -> Unit) {
    fun execute() = pool.execute(block)
}
