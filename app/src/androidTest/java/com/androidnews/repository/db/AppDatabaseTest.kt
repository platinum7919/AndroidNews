package com.androidnews.repository.db

import androidx.test.runner.AndroidJUnit4
import com.androidnews.AndroidTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : AndroidTest() {
    @Test
    fun testDateConverter() {

        val epochMs = 1570361266000
        val dateString = "Sun, 06 Oct 2019 11:27:46 +0000"
        val rfc2822 = "EEE, dd MMM yyyy HH:mm:ss Z"
        val dc = DateConverter()

        // test fromDate
        Assert.assertEquals(epochMs, dc.fromDate(SimpleDateFormat(rfc2822).parse(dateString)))

        // test toDate
        Assert.assertEquals(dateString, SimpleDateFormat(rfc2822).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }.format(dc.toDate(epochMs)))
    }
}
