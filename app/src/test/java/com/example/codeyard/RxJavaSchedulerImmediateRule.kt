package com.example.codeyard

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class RxJavaSchedulerImmediateRule : TestWatcher() {

    private val immediate: Scheduler = object : Scheduler() {

        override fun scheduleDirect(run: Runnable): Disposable =
            super.scheduleDirect(run, 0, TimeUnit.MILLISECONDS)

        override fun createWorker(): Worker =
            ExecutorScheduler.ExecutorWorker(object : ScheduledThreadPoolExecutor(1) {
                override fun execute(command: Runnable) = command.run()
            }, true, true)
    }

    override fun starting(description: Description) {
        super.starting(description)

        RxJavaPlugins.setIoSchedulerHandler { immediate }
        RxJavaPlugins.setComputationSchedulerHandler { immediate }
        RxJavaPlugins.setNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setMainThreadSchedulerHandler { immediate }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}