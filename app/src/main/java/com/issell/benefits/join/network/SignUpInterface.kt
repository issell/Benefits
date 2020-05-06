package com.issell.benefits.join.network

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpInterface {
    @Headers("Content-Type: application/json")
    @POST("/signup")
    fun doSignUp(@Body param: SignUp): Observable<SignUpResult>
}