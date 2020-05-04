package com.issell.benefits.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.issell.benefits.R
import com.issell.benefits.main.MainActivity
import com.issell.benefits.main.MainContract
import com.issell.benefits.util.ActivityUtils
import com.issell.progressbar.SimpleProgressBar
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton
import kotlinx.android.synthetic.main.fragment_login.view.*


object LoginFragment : Fragment(), LoginContract.View {
    private var p: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = LoginPresenter(context!!, this)
        if (p != null) {
            // View 에 Presenter 주입
            if (p is LoginPresenter) {
                setPresenter(p!! as LoginPresenter)
                p!!.start()
            } else
                throw ClassCastException("The p on LoginView is must the type of LoginPresenter.")
        }

    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        p = presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        // remember_me 스위치 글자 색 수정/
        v.remember_sw.setOnCheckedChangeListener { _, isChecked ->
            view!!.remember_sw.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    if (isChecked)
                        R.color.milkyWhite
                    else
                        android.R.color.darker_gray
                )
            )
        }


        v.email_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider
        v.password_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider

        v.go_signup.setOnClickListener {
            startSignupFragment()
        }
        return v
    }


    override fun showNaverLoginButton(handler: OAuthLoginHandler) {
        if (null != view)
            view!!.findViewById<OAuthLoginButton>(R.id.naver_login_button).setOAuthLoginHandler(
                handler
            )
    }

    override fun createProgressbar(): SimpleProgressBar {
        val iActivity = activity as MainActivity
        return SimpleProgressBar(
            iActivity,
            R.drawable.rotate_progress, /*width:*/350, /*height:*/350
        )
    }

    override fun showLoginErrorDialog(@StringRes id: Int) {
        ActivityUtils.showErrorDialog(
            activity!!,
            R.string.error_internet_title,
            id,
            R.string.error_internet_button_text
        )
    }

    override fun showLoginErrorToast(@StringRes id: Int) {
        Toast.makeText(
            context,
            getString(id),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onResume() {
        super.onResume()
        p!!.start()
    }


    override fun finish() {
        if (activity == null) {
            return
        }
        activity!!.finish()
    }

    override fun isAutoLoginChecked(): Boolean {
        if (view == null)
            return false
        return view!!.remember_sw.isChecked
    }


    override fun startBenefitsFragment() {
        (activity!! as MainContract.View).startBenefitsFragment()
    }

    override fun startSignupFragment() {
        (activity!! as MainContract.View).startSignupFragment()
    }
}
