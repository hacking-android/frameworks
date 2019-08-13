/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE_USE})
public @interface NullFromTypeParam {
    public int from() default Integer.MIN_VALUE;

    public int to() default Integer.MAX_VALUE;
}

