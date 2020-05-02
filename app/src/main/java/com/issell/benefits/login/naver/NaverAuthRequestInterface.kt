package com.issell.benefits.login.naver


import retrofit2.Call
import retrofit2.http.*

interface NaverAuthRequestInterface {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("oauth2.0/token")
    @FormUrlEncoded
    fun requestToRefresh(
        @FieldMap
        map:HashMap<String, String>
    ) : Call<NaverAuthRefreshResponse>



    @Headers("accept: application/json",
        "content-type: application/json")
    @FormUrlEncoded
    @POST("oauth2.0/token")
    fun requestToDelete(
        @FieldMap
        map:HashMap<String, String>
    ) : Call<NaverAuthDeleteResponse>
}