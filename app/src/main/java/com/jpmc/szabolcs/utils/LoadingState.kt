package com.jpmc.szabolcs.utils

/**
 * The [LoadingState] data class is called
 * from the main fragment when observing the
 * loading state of the ViewModel in a lambda function
 */
@Suppress("DataClassPrivateConstructor")
data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    /**
     * The [Status] class enumerates the
     * 3 possible statuses that I am observing
     * in the [MainFragment] class
     */
    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}