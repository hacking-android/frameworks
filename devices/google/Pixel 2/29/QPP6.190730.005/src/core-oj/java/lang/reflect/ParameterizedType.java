/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

import java.lang.reflect.Type;

public interface ParameterizedType
extends Type {
    public Type[] getActualTypeArguments();

    public Type getOwnerType();

    public Type getRawType();
}

