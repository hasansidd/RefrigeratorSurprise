package com.siddapps.android.refrigeratorsurprise.customviews

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.siddapps.android.refrigeratorsurprise.R
import kotlinx.android.synthetic.main.custom_view_toolbar.view.*

public class Toolbar : LinearLayout {
    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)
    lateinit var listener: OnNavigationButtonClickListener

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_view_toolbar, this, true)
        navButton.setOnClickListener{
            listener.onNavigationClick()
        }
    }

    fun setNavigationButtonClickListener(listener: OnNavigationButtonClickListener) {
        this.listener = listener
    }

    interface OnNavigationButtonClickListener {
        fun onNavigationClick()
    }
}
