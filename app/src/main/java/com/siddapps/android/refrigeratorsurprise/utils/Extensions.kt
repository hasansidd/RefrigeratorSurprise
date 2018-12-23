package com.siddapps.android.refrigeratorsurprise.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

inline fun FragmentManager.add(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

inline fun FragmentManager.add(containerId: Int, fragment: Fragment) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.add(containerId, fragment, fragment::class.qualifiedName)
    fragmentTransaction.addToBackStack(fragment::class.qualifiedName)
    fragmentTransaction.commit()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.print(string: String?) {
    Log.e(this::class.java.simpleName, string)
}

fun String.httpToHttps(): String {
    return this.substring(0, 4) + "s" + this.substring(4)
}

fun View.pulse(scale: Float, duration: Long) {
    val xScale = PropertyValuesHolder.ofFloat("scaleX", scale)
    val yScale = PropertyValuesHolder.ofFloat("scaleY", scale)
    val anim = ObjectAnimator.ofPropertyValuesHolder(this, xScale, yScale)
    anim.duration = duration

    anim.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            val xScale2 = PropertyValuesHolder.ofFloat("scaleX", 1.0F)
            val yScale2 = PropertyValuesHolder.ofFloat("scaleY", 1.0F)
            val anim2 = ObjectAnimator.ofPropertyValuesHolder(this, xScale2, yScale2)
            anim2.duration = duration
            anim2.start()
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
        }

    })
    anim.start()
}