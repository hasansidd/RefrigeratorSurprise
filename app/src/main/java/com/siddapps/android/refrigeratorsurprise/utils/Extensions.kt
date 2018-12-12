package com.siddapps.android.refrigeratorsurprise.utils

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
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