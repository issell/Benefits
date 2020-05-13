package com.issell.benefits.customview.dialog

import androidx.annotation.StringRes
import com.issell.benefits.R

class DialogFactory private constructor() {
    companion object {
        fun makeSuccessDialog(
            @StringRes titleId: Int,
            @StringRes messageId: Int,
            @StringRes buttonTextId: Int,
            action: MyDialog.ButtonDialogAction
        ): MyDialog {
            return MyDialog.newInstance(
                titleId,
                messageId,
                buttonTextId,
                R.drawable.ic_checked,
                R.color.green_500,
                action
            )
        } // makeSuccessDialog()

        fun makeSuccessDialog(
            title: CharSequence,
            message: CharSequence,
            buttonText: CharSequence,
            action: MyDialog.ButtonDialogAction
        ): MyDialog {
            return MyDialog.newInstance(
                title,
                message,
                buttonText,
                R.drawable.ic_checked,
                R.color.green_500,
                action
            )
        } // makeSuccessDialog()

        fun makeErrorDialog(
            @StringRes titleId: Int,
            @StringRes messageId: Int,
            @StringRes buttonTextId: Int,
            action: MyDialog.ButtonDialogAction
        ): MyDialog {
            return MyDialog.newInstance(
                titleId,
                messageId,
                buttonTextId,
                R.drawable.ic_close,
                R.color.red_500,
                action
            )
        } // makeErrorDialog()

        fun makeErrorDialog(
            title: CharSequence,
            message: CharSequence,
            buttonText: CharSequence,
            action: MyDialog.ButtonDialogAction
        ): MyDialog {
            return MyDialog.newInstance(
                title,
                message,
                buttonText,
                R.drawable.ic_close,
                R.color.red_500,
                action
            )
        } // makeErrorDialog()
    }
}