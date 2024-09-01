package com.floward.flowardtask.common.base

interface Mapper<F, T> {

    fun mapFrom(from: F): T

}