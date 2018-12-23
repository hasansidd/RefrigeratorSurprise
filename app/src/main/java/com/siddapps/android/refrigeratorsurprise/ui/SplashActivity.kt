package com.siddapps.android.refrigeratorsurprise.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
        finish()
    }
}