package com.der3318.forumsurf;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ForumSurfInstrumentedTest {

    @Test
    public void appContextAndConfigurations() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.der3318.forumsurf", appContext.getPackageName());
        assertTrue(Integer.parseInt(appContext.getString(R.string.picasso_cache_bytes)) > 0);
        assertTrue(Pattern.compile(appContext.getString(R.string.image_links_regex)).matcher("https://i.imgur.com/sample.jpeg").find());
        assertTrue(Pattern.compile(appContext.getString(R.string.image_links_regex)).matcher("https://imgs.com/sample.png").find());
        assertTrue(Pattern.compile(appContext.getString(R.string.image_links_regex)).matcher("https://pictures.com/sample.jpg").find());
    }

}