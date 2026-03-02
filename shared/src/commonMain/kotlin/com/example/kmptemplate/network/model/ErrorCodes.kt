package com.example.kmptemplate.network.model

object ErrorCodes {
    const val CONNECTION = -1

    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val TOO_MANY_REQUESTS = 429

    val RANGE_5XX = 500..599
}

