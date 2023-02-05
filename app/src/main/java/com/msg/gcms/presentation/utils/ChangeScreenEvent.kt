package com.msg.gcms.presentation.utils

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.msg.gcms.R

fun enterActivity(activity: FragmentActivity, destination: Activity) {
    activity.startActivity(Intent(activity, destination::class.java))
    activity.overridePendingTransition(R.anim.enter_anim, R.anim.no_anim)
}

fun exitActivity(activity: FragmentActivity) {
    activity.finish()
    activity.overridePendingTransition(R.anim.no_anim, R.anim.exit_anim)
}

fun enterFragment(activity: FragmentActivity, fragmentContainer: Int, destination: Fragment) {
    activity.supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.enter_anim, R.anim.no_anim)
        .replace(fragmentContainer, destination).commit()
}

fun exitFragment(activity: FragmentActivity, fragmentContainer: Int, destination: Fragment) {
    activity.supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.no_anim, R.anim.exit_anim)
        .replace(fragmentContainer, destination).commit()
}