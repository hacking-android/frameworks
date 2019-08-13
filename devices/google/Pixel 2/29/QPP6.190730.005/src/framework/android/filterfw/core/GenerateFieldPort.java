/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface GenerateFieldPort {
    public boolean hasDefault() default false;

    public String name() default "";
}

