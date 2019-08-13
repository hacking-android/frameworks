/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

import java.lang.reflect.Type;

public interface WildcardType
extends Type {
    public Type[] getLowerBounds();

    public Type[] getUpperBounds();
}

