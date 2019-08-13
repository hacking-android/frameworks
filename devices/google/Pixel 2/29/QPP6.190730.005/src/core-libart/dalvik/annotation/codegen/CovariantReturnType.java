/*
 * Decompiled with CFR 0.145.
 */
package dalvik.annotation.codegen;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(value=CovariantReturnTypes.class)
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.METHOD})
public @interface CovariantReturnType {
    public int presentAfter();

    public Class<?> returnType();

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.METHOD})
    public static @interface CovariantReturnTypes {
        public CovariantReturnType[] value();
    }

}

