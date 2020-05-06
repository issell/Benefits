package com.issell.benefits.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.issell.benefits.R
import com.issell.benefits.customview.dialog.MyDialog
import com.issell.benefits.join.network.SignUp
import com.issell.benefits.main.MainActivity
import com.issell.benefits.main.MainContract
import com.issell.benefits.util.ActivityUtils
import kotlinx.android.synthetic.main.fragment_login.view.email_et
import kotlinx.android.synthetic.main.fragment_login.view.password_et
import kotlinx.android.synthetic.main.fragment_signup.view.*

object SignUpFragment : Fragment(), SignUpContract.View {

    private var p: SignUpContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = SignUpPresenter(context!!, this)
        if (p != null) {
            // View 에 Presenter 주입
            if (p is SignUpPresenter) {
                setPresenter(p!!)
                p!!.start()
            } else
                throw ClassCastException("The type of Presenter in SignUpView is must the type of SignUpPresenter.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_signup, container, false)

        // textWatcher
        val w = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var check = true
                if (p == null) return
                if (p!!.checkEmail(v.email_et.text.toString())) {
                    v.email_l.isErrorEnabled = false
                    v.email_l.error = null
                } else {
                    check = false
                    v.email_l.isErrorEnabled = true
                    v.email_l.error =
                        resources.getString(R.string.signup_error_email_required)
                }

                if (p!!.checkPassword(v.password_et.text.toString())) {
                    v.password_l.isErrorEnabled = false
                    v.password_l.error = null

                } else {
                    check = false
                    v.password_l.isErrorEnabled = true
                    v.password_l.error =
                        resources.getString(R.string.signup_error_password_required)
                }
                if (p!!.checkEqualPasswords(
                        v.password_et.text.toString(),
                        v.password2_et.text.toString()
                    )
                ) {
                    v.password2_l.isErrorEnabled = false
                    v.password2_l.error = null
                } else {
                    check = false
                    v.password2_l.isErrorEnabled = true
                    v.password2_l.error =
                        resources.getString(R.string.signup_error_password2_required)
                }
                activateCommitButton(check)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }


        v.email_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider
        v.password_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider
        v.password2_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider

        v.email_et.addTextChangedListener(w)
        v.password_et.addTextChangedListener(w)
        v.password2_et.addTextChangedListener(w)

        v.signup_commit_btn.setOnClickListener {
            checkNotNull(p)
            p!!.sendUserInfo(
                SignUp(
                    v.email_et.text.toString(),
                    v.password_et.toString()
                )
            )
        }

        return v
    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
        p = presenter
    }

    override fun activateCommitButton(on: Boolean) {
        view!!.signup_commit_btn.isEnabled = on
    }

    override fun showSignUpSuccessDialog() {
        ActivityUtils.showSuccessDialog(
            activity!!,
            R.string.signup_success_title,
            R.string.signup_success_message,
            R.string.signup_success_button,
            object : MyDialog.ButtonDialogAction {
                override fun onButtonClicked() {
                    (activity!! as MainContract.View).startLoginFragment()
                }
            }
        )
    }
}

