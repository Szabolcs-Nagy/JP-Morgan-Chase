package com.jpmc.szabolcs

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class LiveDataTestUtil<T> {
    @Throws(InterruptedException::class)
    fun getValue(liveData: LiveData<T>): T? {
        val data: MutableList<T> = ArrayList()

        // declaring a latch for blocking thread util data
        val latch = CountDownLatch(1)
        val observer: Observer<T> = object : Observer<T> {
            override fun onChanged(t: T) {
                data.add(t)
                latch.countDown() // releasing the latch
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        try {
            latch.await(2, TimeUnit.SECONDS) // wait for onChanged to fire and set data
        } catch (e: InterruptedException) {
            throw InterruptedException("Latch failure")
        }
        return if (data.size > 0) {
            data[0]
        } else null
    }
}