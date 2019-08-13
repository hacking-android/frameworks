/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.CLASS)
public @interface VisibleForTesting {
    public Visibility visibility() default Visibility.PRIVATE;

    public static enum Visibility {
        PROTECTED,
        PACKAGE,
        PRIVATE;
        
    }

}

