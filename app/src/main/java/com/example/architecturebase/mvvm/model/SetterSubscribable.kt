package com.example.architecturebase.mvvm.model

class SetterSubscribable<T>: Settable<T>, Subscribable<T> {
    private var data: T? = null
    private var subscriber: (T) -> (Unit) = {}

    override fun setData(data: T) {
        this.data = data
        notifyChanges()
    }

    override fun notifyChanges() {
        data?.let { subscriber(it) }
    }

    override fun subscribe(subscriber: (T) -> Unit) {
        this.subscriber = subscriber
    }

}