package com.issell.benefits.join.network

import com.google.gson.annotations.SerializedName

data class SignUp constructor(
    @SerializedName("email")
    var email:String,

    @SerializedName("password")
    var password:String
)