/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package java.lang.invoke;

import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class ByteBufferViewVarHandle
extends VarHandle {
    private boolean nativeByteOrder;

    private ByteBufferViewVarHandle(Class<?> class_, ByteOrder byteOrder) {
        super(class_.getComponentType(), byte[].class, false, ByteBuffer.class, Integer.TYPE);
        this.nativeByteOrder = byteOrder.equals(ByteOrder.nativeOrder());
    }

    static ByteBufferViewVarHandle create(Class<?> class_, ByteOrder byteOrder) {
        return new ByteBufferViewVarHandle(class_, byteOrder);
    }
}

