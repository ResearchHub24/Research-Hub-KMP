package com.atech.research.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Data loader interface
 * This interface is used to load data when the lifecycle is in a specific state
 * @see DataLoaderImpl
 */
interface DataLoader {
    /**
     * Register lifecycle owner
     * This function is used to register the lifecycle owner
     * @param owner [LifecycleOwner]
     */
    fun registerLifecycleOwner(owner: LifecycleOwner)

    /**
     * Set task
     * This function is used to set the task to be executed when the lifecycle is in a specific state
     * @param task [() -> Unit]
     */
    fun setTask(task: () -> Unit)
}

/**
 * Data loader implementation
 * This class is used to load data when the lifecycle is in a specific state
 * @see DataLoader
 */
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