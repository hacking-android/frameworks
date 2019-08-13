/*
 * Decompiled with CFR 0.145.
 */
package android.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.FIELD})
public @interface BroadcastBehavior {
    public boolean explicitOnly() default false;

    public boolean includeBackground() default false;

    public boolean protectedBroadcast() default false;

    public boolean registeredOnly() default false;
}

