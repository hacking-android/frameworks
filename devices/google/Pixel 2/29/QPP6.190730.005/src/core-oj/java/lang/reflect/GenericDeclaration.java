/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.TypeVariable;

public interface GenericDeclaration
extends AnnotatedElement {
    public TypeVariable<?>[] getTypeParameters();
}

