package com.icapps.template.model

import java.util.concurrent.TimeUnit

@Suppress("MagicNumber")
object Constants {
    val DEFAULT_CACHE_VALIDITY = TimeUnit.MINUTES.toMillis(5)

    const val THREAD_POOL_MAX_THREAD_COUNT = 10
    const val THREAD_POOL_KEEP_ALIVE_SECONDS = 5L
}
