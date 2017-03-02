package com.nu.micar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation activity_micar_test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MicarExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under activity_micar_test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.nu.micar.micar", appContext.getPackageName());
    }
}
