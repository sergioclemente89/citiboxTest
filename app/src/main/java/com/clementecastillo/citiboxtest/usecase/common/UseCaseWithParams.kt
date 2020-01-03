package com.clementecastillo.citiboxtest.usecase.common

interface UseCaseWithParams<out T, in P> {

    fun bind(params: P): T
}