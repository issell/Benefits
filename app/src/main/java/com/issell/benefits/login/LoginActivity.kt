package com.issell.benefits.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.issell.benefits.R
import com.issell.benefits.util.ActivityUtils

class LoginActivity : AppCompatActivity(){
    private var presenter:LoginPresenter? = null

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


        presenter = LoginPresenter(this, loginFragment)
        if(presenter != null) {
            // View 에 Presenter 주입
            loginFragment.setPresenter(presenter!!)

        }

    }
}

