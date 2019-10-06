package com.androidnews.common

import androidx.test.runner.AndroidJUnit4
import com.androidnews.AndroidTest
import com.androidnews.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.net.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ThrowableTest : AndroidTest() {
    @Test
    fun testgetUserMessage() {
        assertEquals(ctx.getString(R.string.error_connection_error), SocketException().getUserMessage(ctx))
        assertEquals(ctx.getString(R.string.error_connection_error), SocketTimeoutException().getUserMessage(ctx))
        assertEquals(ctx.getString(R.string.error_connection_error), NoRouteToHostException().getUserMessage(ctx))
        assertEquals(ctx.getString(R.string.error_connection_error), ConnectException().getUserMessage(ctx))
        assertEquals(ctx.getString(R.string.error_connection_error), UnknownHostException().getUserMessage(ctx))
        assertNotEquals(ctx.getString(R.string.error_connection_error), Exception().getUserMessage(ctx))
        assertNotEquals(ctx.getString(R.string.error_connection_error), IOException().getUserMessage(ctx))
    }
}
