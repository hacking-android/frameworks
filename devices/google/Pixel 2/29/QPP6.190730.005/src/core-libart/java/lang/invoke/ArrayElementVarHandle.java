/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package java.lang.invoke;

import java.lang.invoke.VarHandle;

final class ArrayElementVarHandle
extends VarHandle {
    private ArrayElementVarHandle(Class<?> class_) {
        super(class_.getComponentType(), class_, false, class_, Integer.TYPE);
    }

    static ArrayElementVarHandle create(Class<?> class_) {
        return new ArrayElementVarHandle(class_);
    }
}

