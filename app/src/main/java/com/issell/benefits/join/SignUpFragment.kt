package com.issell.benefits.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.issell.benefits.R
import com.issell.benefits.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.view.email_et
import kotlinx.android.synthetic.main.fragment_login.view.password_et
import kotlinx.android.synthetic.main.fragment_signup.view.*

object SignUpFragment : Fragment(), SignUpContract.View {

    private var p:SignUpContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = SignUpPresenter(this)
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

        // email textWatcher
        v.email_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider
        v.password_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider
        v.password2_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider

        v.email_et.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    v.email_et.error = "Characters more than 20"
                } else {
                    //v.email_et.isErrorEnabled = false
                }
            }
        })
        return v
    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
        p = presenter
    }
}

