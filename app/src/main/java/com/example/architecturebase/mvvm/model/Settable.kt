package com.example.architecturebase.mvvm.model

interface Settable<T> {
    fun setData(data: T)
}