package com.clementecastillo.citiboxtest.usecase.common

interface UseCase<out T> {

    fun bind(): T
}