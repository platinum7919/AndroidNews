package com.androidnews.common


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection

open class BaseActivity : AppCompatActivity() {

    fun putFragmentInLayout(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(containerId, fragment)
        }.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }
}