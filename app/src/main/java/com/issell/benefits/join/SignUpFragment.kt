package com.issell.benefits.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.issell.benefits.R
import com.issell.benefits.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.view.email_et
import kotlinx.android.synthetic.main.fragment_login.view.password_et
import kotlinx.android.synthetic.main.fragment_signup.view.*

object SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_signup, container, false)
        v.email_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider
        v.password_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider
        v.password2_et.onFocusChangeListener = (activity!! as MainActivity).keyboardHider
        return v
    }

}
