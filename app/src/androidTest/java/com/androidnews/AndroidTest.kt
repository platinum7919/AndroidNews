package com.androidnews

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class AndroidTest {
    open val ctx = InstrumentationRegistry.getTargetContext()

}
