package com.issell.benefits.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.issell.benefits.R
import com.issell.benefits.benefits.MainFragment
import com.issell.benefits.join.SignUpFragment
import com.issell.benefits.login.LoginFragment
import com.issell.benefits.util.ActivityUtils

// NOTE THAT Whenever released-version of APK are newly compiled (not debug),
//  recreate the hash key and reedit it to https://developers.kakao.com/console/app/423852/config/platform

class MainActivity : AppCompatActivity(), MainContract.View {
    private var p: MainContract.Presenter? = null

    val keyboardHider = View.OnFocusChangeListener { vw, hasFocus ->
        if (!hasFocus)
            ActivityUtils.hideKeyboard(this, vw)
    }

    companion object {
        const val FRAGMENT_FLAG = "state"
        const val FRAGMENT_FLAG_LOGIN = 0
        const val FRAGMENT_FLAG_SIGN_IN = 1
        const val FRAGMENT_FLAG_MAIN = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        p = MainPresenter(this, this)
        if (p != null) {
            // View 에 Presenter 주입
            if (p is MainPresenter) {
                setPresenter(p!! as MainPresenter)
                p!!.start()
            } else
                throw ClassCastException("The p on LoginView is must the type of LoginPresenter.")
        }

        val state = intent.getIntExtra(
            FRAGMENT_FLAG,
            FRAGMENT_FLAG_LOGIN
        )
        var frag = LoginFragment as Fragment
        when (state) {
            FRAGMENT_FLAG_LOGIN -> frag = LoginFragment
            FRAGMENT_FLAG_MAIN -> frag = MainFragment
            FRAGMENT_FLAG_SIGN_IN -> frag = SignUpFragment
            // ...
        }
        inflateFrag(frag)
    }


    private fun inflateFrag(childFrag: Fragment) {
        var fragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout)
        if (fragment != null) return
        fragment = childFrag
        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.frame_layout
        )
    }

    override fun onBackPressed() {

        val currentFrag = supportFragmentManager.findFragmentByTag(LoginFragment.javaClass.name)

        // 현재 Fragment 가 Login 인 경우 (앱종료)
        if (currentFrag != null && currentFrag.isVisible)
            finish()
        else
            startLoginFragment()
    }

    override fun startLoginFragment() {
        var fragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout)

        checkNotNull(fragment)
        fragment = LoginFragment
        ActivityUtils.replaceFragmentToActivity(
            supportFragmentManager, fragment, R.id.frame_layout
        )

    }

    override fun startBenefitsFragment() {
        var fragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout)

        checkNotNull(fragment)
        fragment = MainFragment
        ActivityUtils.replaceFragmentToActivity(
            supportFragmentManager, fragment, R.id.frame_layout
        )

    }

    override fun startSignupFragment() {
        var fragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout)

        checkNotNull(fragment)
        fragment = SignUpFragment
        ActivityUtils.replaceFragmentToActivity(
            supportFragmentManager, fragment, R.id.frame_layout
        )
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        p = presenter
    }
}

