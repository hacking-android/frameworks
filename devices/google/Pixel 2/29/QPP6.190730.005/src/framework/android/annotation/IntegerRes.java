/*
 * Decompiled with CFR 0.145.
 */
package android.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
public @interface IntegerRes {
}

