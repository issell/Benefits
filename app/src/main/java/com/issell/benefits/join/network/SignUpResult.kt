package com.issell.benefits.join.network

import retrofit2.http.Field

data class SignUpResult (
    @Field("result")
    val result:Boolean,

    @Field("message")
    val message:String
)