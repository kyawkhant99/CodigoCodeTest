package com.kkh.core.extensions

fun String?.unknownErrorOnNull() = if (this.isNullOrEmpty()) "Something went wrong!" else this