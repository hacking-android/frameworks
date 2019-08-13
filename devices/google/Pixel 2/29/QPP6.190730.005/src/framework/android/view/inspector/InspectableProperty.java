/*
 * Decompiled with CFR 0.145.
 */
package android.view.inspector;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.METHOD, ElementType.FIELD})
public @interface InspectableProperty {
    public int attributeId() default 0;

    public EnumEntry[] enumMapping() default {};

    public FlagEntry[] flagMapping() default {};

    public boolean hasAttributeId() default true;

    public String name() default "";

    public ValueType valueType() default ValueType.INFERRED;

    @Retention(value=RetentionPolicy.SOURCE)
    @Target(value={ElementType.TYPE})
    public static @interface EnumEntry {
        public String name();

        public int value();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @Target(value={ElementType.TYPE})
    public static @interface FlagEntry {
        public int mask() default 0;

        public String name();

        public int target();
    }

    public static enum ValueType {
        NONE,
        INFERRED,
        INT_ENUM,
        INT_FLAG,
        COLOR,
        GRAVITY,
        RESOURCE_ID;
        
    }

}

