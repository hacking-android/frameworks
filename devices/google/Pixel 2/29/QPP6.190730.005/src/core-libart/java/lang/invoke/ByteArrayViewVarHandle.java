/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package java.lang.invoke;

import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

final class ByteArrayViewVarHandle
extends VarHandle {
    private boolean nativeByteOrder;

    private ByteArrayViewVarHandle(Class<?> class_, ByteOrder byteOrder) {
        super(class_.getComponentType(), byte[].class, false, byte[].class, Integer.TYPE);
        this.nativeByteOrder = byteOrder.equals(ByteOrder.nativeOrder());
    }

    static ByteArrayViewVarHandle create(Class<?> class_, ByteOrder byteOrder) {
        return new ByteArrayViewVarHandle(class_, byteOrder);
    }
}

