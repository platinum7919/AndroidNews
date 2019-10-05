package com.androidnews.common


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidnews.utils.Json
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var json: Json


    fun putFragmentInLayout(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(containerId, fragment)
        }.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    fun showSnackBar(message : String){
        Timber.v(message);
    }
}