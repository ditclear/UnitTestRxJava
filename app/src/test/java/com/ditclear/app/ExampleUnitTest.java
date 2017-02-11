package com.ditclear.app;

import com.ditclear.app.test.ErrorRule;
import com.ditclear.app.test.annotation.JSpec;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Rule
    public ErrorRule mRule=new ErrorRule();

    @Test
    @JSpec(desc = "加法")
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 3);
    }

    @Test
    @JSpec(desc = "减法")
    public void subtraction_isCorrect() throws Exception {
        assertEquals(4, 6 - 2);
    }
}