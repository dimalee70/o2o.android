package kz.dragau.larek

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented ic_arrow_back, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under ic_arrow_back.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("kz.dragau.larek", appContext.packageName)
    }
}
