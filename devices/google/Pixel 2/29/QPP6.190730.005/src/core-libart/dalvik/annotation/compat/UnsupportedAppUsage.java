/*
 * Decompiled with CFR 0.145.
 */
package dalvik.annotation.compat;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(value=Container.class)
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
public @interface UnsupportedAppUsage {
    public String expectedSignature() default "";

    public String implicitMember() default "";

    public int maxTargetSdk() default Integer.MAX_VALUE;

    public long trackingBug() default 0L;

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.TYPE})
    public static @interface Container {
        public UnsupportedAppUsage[] value();
    }

}

