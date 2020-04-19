package com.issell.benefits.customview

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.issell.benefits.R
import kotlinx.android.synthetic.main.dialog_warning.*

class MyDialog:DialogFragment() {
    private var buttonDialogAction: ButtonDialogAction? = null
    private fun getContentView(): Int = R.layout.dialog_warning

    companion object {
        const val TAG = "OneButtonDialogTag"
        const val ARG_BUTTON_TEXT = "ARG_BUTTON_TEXT"
        const val ARG_COLOR_RESOURCE_ID = "ARG_COLOR_RESOURCE_ID"
        const val ARG_TITLE = "ARG_TITLE"
        const val ARG_MESSAGE = "ARG_MESSAGE"
        const val ARG_IMAGE_RESOURCE_ID = "ARG_IMAGE_RESOURCE_ID"
        private const val DIALOG_WINDOW_WIDTH = 0.85

        fun newInstance(
            @StringRes titleRes: Int,
            @StringRes messageRes: Int,
            @StringRes buttonTextRes: Int,
            @DrawableRes imageResId: Int,
            @ColorRes colorResId: Int,
            buttonDialogAction: ButtonDialogAction
        ): MyDialog {
            val oneButtonDialog = MyDialog()
            oneButtonDialog.buttonDialogAction = buttonDialogAction

            val args = Bundle()
            args.putInt(ARG_TITLE, titleRes)
            args.putInt(ARG_MESSAGE, messageRes)
            args.putInt(ARG_BUTTON_TEXT, buttonTextRes)
            args.putInt(ARG_IMAGE_RESOURCE_ID, imageResId)
            args.putInt(ARG_COLOR_RESOURCE_ID, colorResId)
            oneButtonDialog.setArguments(args)
            return oneButtonDialog
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val window = dialog!!.window
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.attributes!!.windowAnimations = R.style.DialogAnimation
        val view = inflater.inflate(getContentView(), container, false)
        // ButterKnife.bind(this, view)

        dialog!!.setCanceledOnTouchOutside(false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleRes = arguments!!.getInt(ARG_TITLE)
        val messageRes = arguments!!.getInt(ARG_MESSAGE)
        val buttonTextRes = arguments!!.getInt(ARG_BUTTON_TEXT)
        val image = arguments!!.getInt(ARG_IMAGE_RESOURCE_ID)
        val color = arguments!!.getInt(ARG_COLOR_RESOURCE_ID)

        tvTitle.setText(titleRes)
        tvTitle.setTextColor(ContextCompat.getColor(view.context, color))
        tvMessage.setText(messageRes)
        btnNeutral.setText(buttonTextRes)
        btnNeutral.setOnClickListener {
            closeDialog()
            if (buttonDialogAction != null) {
                buttonDialogAction!!.onButtonClicked()
            }
        }
        ivDialogIcon.setImageResource(image)
    }

    override fun onStart() {
        super.onStart()
        setDialogWindowWidth(DIALOG_WINDOW_WIDTH)
    }

    private fun setDialogWindowWidth(width: Double) {
        val window = dialog!!.window
        val size = Point()
        val display: Display
        if (window != null) {
            display = window.windowManager.defaultDisplay
            display.getSize(size)
            val maxWidth = size.x
            window.setLayout((maxWidth * width).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)
        }
    }

    fun closeDialog() {
        if (dialog!!.isShowing) {
            closeKeyboard()
            dialog!!.dismiss()
        }
    }

    protected fun closeKeyboard() {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            activity!!.findViewById<View>(android.R.id.content).windowToken, 0
        )
    }

    interface ButtonDialogAction {
        fun onButtonClicked()
    }
}
