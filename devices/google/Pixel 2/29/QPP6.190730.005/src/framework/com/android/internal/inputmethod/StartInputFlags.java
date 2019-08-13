/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.SOURCE)
public @interface StartInputFlags {
    public static final int FIRST_WINDOW_FOCUS_GAIN = 4;
    public static final int INITIAL_CONNECTION = 8;
    public static final int IS_TEXT_EDITOR = 2;
    public static final int VIEW_HAS_FOCUS = 1;
}

