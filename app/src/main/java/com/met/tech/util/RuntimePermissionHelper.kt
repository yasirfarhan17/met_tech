package com.met.tech.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.met.tech.R

class RuntimePermissionHelper private constructor(private var activity: Activity) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var runtimePermissionHelper: RuntimePermissionHelper? = null
        const val PERMISSION_REQUEST_CODE = 1

        @Synchronized
        fun getInstance(activity: Activity): RuntimePermissionHelper {
            runtimePermissionHelper?.let {
                return it
            } ?: return RuntimePermissionHelper(activity).also { runtimePermissionHelper ->
                this.runtimePermissionHelper = runtimePermissionHelper
            }
        }
    }

    fun requestPermissionsIfDenied(permission: String) {
        if (canShowPermissionRationaleDialog(permission)) {
            showMessageOKCancel("Permission required") {
                askPermission(
                    permission
                )
            }
            return
        }
        askPermission(permission)
    }

    fun setActivity(activity: Activity) {
        this.activity = activity
    }

    fun canShowPermissionRationaleDialog(permission: String): Boolean {
        var shouldShowRationale = false
        val shouldShow =
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        if (shouldShow) {
            shouldShowRationale = true
        }
        return shouldShowRationale
    }

    private fun askPermission(permission: String) {
        ActivityCompat.requestPermissions(
            activity, arrayOf(permission),
            PERMISSION_REQUEST_CODE
        )
    }

    fun requestPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(
            activity, permissions,
            PERMISSION_REQUEST_CODE
        )
    }

    private fun showMessageOKCancel(
        message: String,
        funcOnOkClick: () -> Unit
    ) {
        AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton(R.string.app_name) { _, _ -> funcOnOkClick.invoke() }
            .setNegativeButton(R.string.app_name) { _, _ -> activity.finish() }
            .create()
            .show()
    }

    fun isPermissionAvailable(permission: String): Boolean {
        var isPermissionAvailable = true
        if (ContextCompat.checkSelfPermission(
                activity, permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            isPermissionAvailable = false
        }
        return isPermissionAvailable
    }

    fun isPermissionsAvailable(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

}