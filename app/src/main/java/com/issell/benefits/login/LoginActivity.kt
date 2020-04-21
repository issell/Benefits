package com.issell.benefits.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.issell.benefits.R
import com.issell.benefits.util.ActivityUtils

// NOTE Whenever released-version of APK are newly compiled (not debug),
//  it requires to reedit the hash key via https://developers.kakao.com/console/app/423852/config/platform

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로그인 Fragment 셋
        var loginFragment:LoginFragment? =
            supportFragmentManager.findFragmentById(R.id.frame_layout) as LoginFragment?
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager, loginFragment, R.id.frame_layout
            )
        }
    }
}

