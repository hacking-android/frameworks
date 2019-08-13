/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

public interface Type {
    default public String getTypeName() {
        return this.toString();
    }
}

