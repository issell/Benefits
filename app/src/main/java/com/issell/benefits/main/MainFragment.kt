package com.issell.benefits.main

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.issell.benefits.R
import com.issell.benefits.customview.dialog.MyDialog
import com.issell.benefits.login.LoginActivity
import com.issell.benefits.util.ActivityUtils
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.lang.NullPointerException

object MainFragment : Fragment(), MainContract.View {
    private var p: MainContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = MainPresenter(context!!, this)
        if (p != null) {
            // View 에 Presenter 주입
            if (p is MainPresenter) {
                setPresenter(p!! as MainPresenter)
                p!!.start()
            } else
                throw ClassCastException("The Presenter on MainView must be type of MainPresenter.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_main, container, false)

        v.logout_bt.setOnClickListener{
            if(p == null)
                throw NullPointerException("Presenter is null.")
            p!!.logout()
        }
        return v
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        p = presenter
    }

    override fun startLoginActivity() {
        startActivity(Intent(context, LoginActivity::class.java))
    }

    override fun showLogoutSuccessDialog() {
        ActivityUtils.showSuccessDialog(
            activity!!,
            R.string.main_logout_success_title,
            R.string.main_logout_success_message,
            R.string.main_logout_success_button_text,
            object : MyDialog.ButtonDialogAction {
                override fun onButtonClicked() {
                    startLoginActivity()
                }
            }
        )
    }
}
