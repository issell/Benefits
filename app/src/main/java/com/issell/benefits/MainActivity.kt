package com.issell.benefits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.issell.benefits.naverlogin.MyOAuthLoginHandler
import com.issell.benefits.utils.getApplicationName
import com.nhn.android.naverlogin.OAuthLogin
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val oauthClientId = BuildConfig.CLIENT_ID
        val oauthClientSecret = BuildConfig.CLIENT_SECRET
        val oauthClientName = getApplicationName(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            this
            , oauthClientId
            , oauthClientSecret
            , oauthClientName
        )
        val mOAuthLoginHandler = MyOAuthLoginHandler(mOAuthLoginModule, this)
        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)
//        mOAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler)
    }
}

