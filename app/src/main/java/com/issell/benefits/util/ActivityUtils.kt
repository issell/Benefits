package com.issell.benefits.util

import android.app.Activity
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.common.base.Preconditions.checkNotNull
import com.issell.benefits.customview.dialog.DialogFactory
import com.issell.benefits.customview.dialog.MyDialog


object ActivityUtils {


    fun addFragmentToActivity(
        @NonNull fragmentManager: FragmentManager,
        @NonNull fragment: Fragment,
        frameId: Int
    ) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment, fragment.javaClass.name)
        transaction.commit()
    }

    fun replaceFragmentToActivity(
        @NonNull fragmentManager: FragmentManager,
        @NonNull fragment: Fragment,
        frameId: Int
    ) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, fragment.javaClass.name)
        transaction.commit()
    }

    private inline fun showDialog(
        activity: FragmentActivity,
        f: () -> MyDialog
    ) {
        f().show(activity.supportFragmentManager, MyDialog.TAG)
    }

    fun showErrorDialog(
        activity: FragmentActivity,
        @StringRes titleId: Int,
        @StringRes messageId: Int,
        @StringRes buttonId: Int,
        action: MyDialog.ButtonDialogAction? = null
    ) {
        showDialog(activity) {
            DialogFactory.makeErrorDialog(
                titleId,
                messageId,
                buttonId,
                if (action == null) object : MyDialog.ButtonDialogAction {
                    override fun onButtonClicked() {
                        SystemClock.sleep(400)
                        activity.finish()
                    }
                } else action
            )

        }
    }

    fun showErrorDialog(
        activity: FragmentActivity,
        titleId: CharSequence,
        messageId: CharSequence,
        buttonId: CharSequence,
        action: MyDialog.ButtonDialogAction? = null
    ) {
        showDialog(activity) {
            DialogFactory.makeErrorDialog(
                titleId,
                messageId,
                buttonId,
                if (action == null)
                    object : MyDialog.ButtonDialogAction {
                        override fun onButtonClicked() {
                            SystemClock.sleep(400)
                            activity.finish()
                        }
                    } else action
            )

        }
    }


    fun showSuccessDialog(
        activity: FragmentActivity,
        @StringRes titleId: Int,
        @StringRes messageId: Int,
        @StringRes buttonId: Int,
        action: MyDialog.ButtonDialogAction
    ) {
        showDialog(activity) {
            DialogFactory.makeSuccessDialog(
                titleId,
                messageId,
                buttonId,
                action
            )
        }
    }

    fun showSuccessDialog(
        activity: FragmentActivity,
        title: CharSequence,
        message: CharSequence,
        button: CharSequence,
        action: MyDialog.ButtonDialogAction
    ) {
        showDialog(activity) {
            DialogFactory.makeSuccessDialog(
                title,
                message,
                button,
                action
            )
        }
    }

    fun hideKeyboard(@NonNull activity: Activity, view: View) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
