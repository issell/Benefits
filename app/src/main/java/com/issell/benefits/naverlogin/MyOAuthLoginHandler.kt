package com.issell.benefits.naverlogin

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

class MyOAuthLoginHandler (
    private val mOAuthLoginModule: OAuthLogin,
    private val mContext: Context
) : OAuthLoginHandler() {
    override fun run(success: Boolean) {
        if (success) {
            val accessToken = mOAuthLoginModule.getAccessToken(mContext)
            val refreshToken = mOAuthLoginModule.getRefreshToken(mContext)
            val expiresAt = mOAuthLoginModule.getExpiresAt(mContext)
            val tokenType = mOAuthLoginModule.getTokenType(mContext)
            Log.d("aa", "accessToken : $accessToken\nrefreshToken : $refreshToken \nexpiresAt : $expiresAt \ntokenType : $tokenType")
//            mOauthAT.setText(accessToken)
//            mOauthRT.setText(refreshToken)
//            mOauthExpires.setText(expiresAt.toString())
//            mOauthTokenType.setText(tokenType)
//            mOAuthState.setText(mOAuthLoginModule.getState(mContext).toString())
        } else {
            val errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode()
            val errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext)
            Toast.makeText(
                mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
            ).show()
        }
    }
}
