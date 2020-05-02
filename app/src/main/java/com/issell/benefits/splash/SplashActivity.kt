package com.issell.benefits.splash

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity
import com.issell.benefits.R
import com.issell.benefits.login.LoginActivity
import com.issell.benefits.main.MainActivity
import com.issell.benefits.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_splash.*

const val MILLI_DELAYED: Long = 1300

// 스플래시 액티비티
class SplashActivity : FragmentActivity(), SplashContract.View {
    private var p: SplashContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val topAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_top_anim)
        val bottomAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_anim)
        imageView.animation = topAnim
        imageView2.animation = bottomAnim



    }

    override fun onResume() {
        super.onResume()
        // p 주입
        p = SplashPresenter(this, this)
        if (p != null) {
            // View 에 Presenter 주입
            if (p is SplashPresenter) {
                setPresenter(p!! as SplashPresenter)
                p!!.start()
            }
            else
                throw ClassCastException("The presenter in SplashVIew must be type of SplashPresenter.")
        }
    }

    override fun finish() {
        super.finish()
        // 메인 (뉴스 리스트) 액티비티 전환 시 페이드 인/아웃 처리
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("email", "") // TODO
        startActivity(intent)
        finish()
    }

    override fun setPresenter(presenter: SplashContract.Presenter) {
        this.p = presenter
    }

    override fun showAutoLoginErrorDialog() {
        ActivityUtils.showErrorDialog(
            this,
            R.string.auto_login_error_title,
            R.string.auto_login_error_auth_expired,
            R.string.auto_login_error_button_text
        )
    }
}
