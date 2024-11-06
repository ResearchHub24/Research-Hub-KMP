package com.atech.research.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

interface DataLoader {
    fun registerLifecycleOwner(owner: LifecycleOwner)
    fun setTask(task: () -> Unit)
}

class DataLoaderImpl : DataLoader, LifecycleEventObserver {

    operator fun getValue(thisRef: Any?, property: Any?): DataLoader {
        return this
    }

    private var task: (() -> Unit)? = null
    override fun registerLifecycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun setTask(task: () -> Unit) {
        this.task = task
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> task?.invoke()
            else -> {}
        }
    }
}