/*
 * Decompiled with CFR 0.145.
 */
package java.lang.annotation;

public interface Annotation {
    public Class<? extends Annotation> annotationType();

    public boolean equals(Object var1);

    public int hashCode();

    public String toString();
}

