/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;

public interface TypeVariable<D extends GenericDeclaration>
extends Type {
    public Type[] getBounds();

    public D getGenericDeclaration();

    public String getName();
}

