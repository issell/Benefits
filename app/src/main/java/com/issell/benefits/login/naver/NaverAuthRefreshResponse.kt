package com.issell.benefits.login.naver

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

object NaverAuthRefreshResponse {
    /*
    access_token	String	Y	재발급 받은 접근토큰
    token_type  	String	Y	토큰 타입 (bearer)
    expires_in	    String	Y	접근토큰 유효성 체크 결과 메시지
     */
    @SerializedName("access_token")
    @Expose
    var accessToken:String? = null

    @SerializedName("token_type")
    @Expose
    var tokenType:String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn:String? = null


}