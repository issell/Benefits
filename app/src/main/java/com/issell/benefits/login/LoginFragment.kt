package com.issell.benefits.login

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.issell.benefits.R
import com.issell.benefits.customview.DialogFactory
import com.issell.benefits.customview.MyDialog
import com.issell.progressbar.SimpleProgressBar
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton



object LoginFragment : Fragment(), LoginContract.View{
    private var presenter : LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(presenter != null) {
            // 네트워크 확인
            presenter!!.checkNetwork()
        }
        // TODO 자동 로그인 확인
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }



    override fun setPresenter(presenter: LoginPresenter) {
        this.presenter = presenter
    }


    override fun showNaverLoginButton(handler:OAuthLoginHandler) {
        if(null != view)
            view!!.findViewById<OAuthLoginButton>(R.id.buttonOAuthLoginImg).setOAuthLoginHandler(handler)
    }

    override fun createProgressbar(): SimpleProgressBar {
        val iActivity = activity as LoginActivity
        return SimpleProgressBar(iActivity,
            R.drawable.rotate_progress, /*width:*/350, /*height:*/350)
    }

    override fun showConnectionErrorDialog() {
        val oneButtonDialog = DialogFactory.makeErrorDialog(
            R.string.error_internet_title,
            R.string.error_internet_message,
            R.string.error_internet_button_text,
            object : MyDialog.ButtonDialogAction {
                override fun onButtonClicked() {
                    SystemClock.sleep(400)
                    if(activity != null)
                        activity!!.finish()
                }
            })
        oneButtonDialog.show(activity!!.supportFragmentManager, MyDialog.TAG)
    }

    override fun onResume() {
        super.onResume()
        presenter!!.start()
    }
}
