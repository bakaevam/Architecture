package com.example.architecturebase.mvvm.model

interface Subscribable<T> {
    fun notifyChanges()
    fun subscribe(subscriber: (T) -> (Unit))
}