package com.met.tech.base

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Navigator(var context: AppCompatActivity) {

    fun startActivity(
        activityClass: Class<out Activity>,
        noTransitionAnimation: Boolean = false
    ) {
        val (activity, intent) = getActivityIntent(activityClass)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q || noTransitionAnimation) {
            activity.startActivity(intent)
        } else {
            activity.startActivity(
                intent, ActivityOptions.makeSceneTransitionAnimation(activity)
                    .toBundle()
            )
        }
    }

    fun startActivityWithData(
        activityClass: Class<out Activity>,
        bundle: Bundle,
        noTransitionAnimation: Boolean = false
    ) {
        val (activity, intent) = getActivityIntent(activityClass)
        intent.putExtras(bundle)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q || noTransitionAnimation) {
            activity.startActivity(intent)
        } else {
            activity.startActivity(
                intent, ActivityOptions.makeSceneTransitionAnimation(activity)
                    .toBundle()
            )
        }
    }


    fun startActivityClearStack(activityClass: Class<out Activity>) {
        val (activity, intent) = getActivityIntent(activityClass)
        intent.flags =
            (Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP)
        activity.startActivity(intent)
        activity.finishAffinity()
    }

    fun finishActivity() {
        context.finish()
    }

    fun startActivityClearCurrent(
        activityClass: Class<out Activity>,
        noTransitionAnimation: Boolean = false
    ) {
        val (activity, intent) = getActivityIntent(activityClass)
        intent.flags =
            (Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q || noTransitionAnimation) {
            activity.startActivity(intent)
        } else {
            activity.startActivity(
                intent, ActivityOptions.makeSceneTransitionAnimation(activity)
                    .toBundle()
            )
        }
        activity.finish()
    }


    private fun getActivityIntent(activityClass: Class<out Activity>): Pair<AppCompatActivity, Intent> {
        val activity = context
        val intent = Intent(activity, activityClass)
        return Pair(activity, intent)
    }


    fun openUrlInBrowser(url: String) {
        val i = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        context.startActivity(i)
    }


}