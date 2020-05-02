package com.issell.benefits.login.naver

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

object NaverAuthDeleteResponse {
    /*
        access_token	String	Y	삭제처리된 접근토큰
        result      	String	Y	처리결과 (success)
     */
    @SerializedName("access_token")
    @Expose
    var accessToken:String? = null

    @SerializedName("result")
    @Expose
    var result:String? = null
}