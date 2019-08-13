/*
 * Decompiled with CFR 0.145.
 */
package dalvik.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Deprecated
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE})
public @interface TestTarget {
    public String conceptName() default "";

    public Class<?>[] methodArgs() default {};

    public String methodName() default "";
}

