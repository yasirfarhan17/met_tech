package com.met.tech.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.met.tech.base.BaseActivity

class UiUtil(private val context: Context) {

    private lateinit var snackbar: Snackbar
    private lateinit var progressDialog:ProgressDialog

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            AppCompatActivity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }

    fun showMessage(
        message: String,
        length: Int = Snackbar.LENGTH_SHORT
    ) {
        showSnackBar(message, length)
    }

    private fun showSnackBar(
        message: String,
        snackBarLength: Int = Snackbar.LENGTH_LONG
    ) {
        if (!::snackbar.isInitialized) {
            (context as BaseActivity<*, *>).getLayoutBinding()
                .root.let {
                    snackbar = Snackbar.make(it, message, snackBarLength)
                }
        }
        snackbar.setText(message)
        snackbar.show()
    }


    fun showToast(
        message: String,
        length: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(context, message, length)
            .show()
    }

    fun showProgress() {
        if (!::progressDialog.isInitialized) {
            progressDialog = ProgressDialog(context)
        }
        progressDialog.show()
    }

    fun hideProgress() {
        if (!::progressDialog.isInitialized) {
            progressDialog = ProgressDialog(context)
            return
        }
        progressDialog.dismiss()
    }

}

