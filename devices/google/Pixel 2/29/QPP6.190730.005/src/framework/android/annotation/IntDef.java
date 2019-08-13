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
@Target(value={ElementType.ANNOTATION_TYPE})
public @interface IntDef {
    public boolean flag() default false;

    public String[] prefix() default {};

    public String[] suffix() default {};

    public int[] value() default {};
}

