/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ComponentCallbacks;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ComponentCallbacks2
extends ComponentCallbacks {
    public static final int TRIM_MEMORY_BACKGROUND = 40;
    public static final int TRIM_MEMORY_COMPLETE = 80;
    public static final int TRIM_MEMORY_MODERATE = 60;
    public static final int TRIM_MEMORY_RUNNING_CRITICAL = 15;
    public static final int TRIM_MEMORY_RUNNING_LOW = 10;
    public static final int TRIM_MEMORY_RUNNING_MODERATE = 5;
    public static final int TRIM_MEMORY_UI_HIDDEN = 20;

    public void onTrimMemory(int var1);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TrimMemoryLevel {
    }

}

