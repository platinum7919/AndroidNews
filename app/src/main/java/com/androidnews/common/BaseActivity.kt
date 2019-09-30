package com.androidnews.common


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {

    fun putFragmentInLayout(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(containerId, fragment)
        }.commit()
    }
}