package com.androidnews.utils

import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilsTest {


    @Test
    fun testStringMD5() {
        Assert.assertEquals("C2A98EACBAA79E60EA2A440648506CAD","TransferWise".toMD5String().toUpperCase())

        Assert.assertEquals("78C8988994702995D3F1B424B52FE5E9","TransferWise2".toMD5String().toUpperCase())

    }



}
