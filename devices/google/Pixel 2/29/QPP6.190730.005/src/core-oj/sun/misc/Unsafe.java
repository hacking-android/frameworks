/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 */
package sun.misc;

import dalvik.annotation.optimization.FastNative;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import sun.reflect.Reflection;

public final class Unsafe {
    public static final int INVALID_FIELD_OFFSET = -1;
    private static final Unsafe THE_ONE;
    private static final Unsafe theUnsafe;

    static {
        theUnsafe = THE_ONE = new Unsafe();
    }

    private Unsafe() {
    }

    @FastNative
    private static native int getArrayBaseOffsetForComponentType(Class var0);

    @FastNative
    private static native int getArrayIndexScaleForComponentType(Class var0);

    public static Unsafe getUnsafe() {
        Class<?> class_ = Reflection.getCallerClass();
        class_ = class_ == null ? null : class_.getClassLoader();
        if (class_ != null && class_ != Unsafe.class.getClassLoader()) {
            throw new SecurityException("Unsafe access denied");
        }
        return THE_ONE;
    }

    @FastNative
    public native int addressSize();

    public native Object allocateInstance(Class<?> var1);

    @FastNative
    public native long allocateMemory(long var1);

    public int arrayBaseOffset(Class class_) {
        Serializable serializable = class_.getComponentType();
        if (serializable != null) {
            return Unsafe.getArrayBaseOffsetForComponentType(serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Valid for array classes only: ");
        ((StringBuilder)serializable).append(class_);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public int arrayIndexScale(Class class_) {
        Serializable serializable = class_.getComponentType();
        if (serializable != null) {
            return Unsafe.getArrayIndexScaleForComponentType(serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Valid for array classes only: ");
        ((StringBuilder)serializable).append(class_);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    @FastNative
    public native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);

    @FastNative
    public native boolean compareAndSwapLong(Object var1, long var2, long var4, long var6);

    @FastNative
    public native boolean compareAndSwapObject(Object var1, long var2, Object var4, Object var5);

    @FastNative
    public native void copyMemory(long var1, long var3, long var5);

    @FastNative
    public native void copyMemoryFromPrimitiveArray(Object var1, long var2, long var4, long var6);

    @FastNative
    public native void copyMemoryToPrimitiveArray(long var1, Object var3, long var4, long var6);

    @FastNative
    public native void freeMemory(long var1);

    @FastNative
    public native void fullFence();

    public final int getAndAddInt(Object object, long l, int n) {
        int n2;
        while (!this.compareAndSwapInt(object, l, n2 = this.getIntVolatile(object, l), n2 + n)) {
        }
        return n2;
    }

    public final long getAndAddLong(Object object, long l, long l2) {
        long l3;
        while (!this.compareAndSwapLong(object, l, l3 = this.getLongVolatile(object, l), l3 + l2)) {
        }
        return l3;
    }

    public final int getAndSetInt(Object object, long l, int n) {
        int n2;
        while (!this.compareAndSwapInt(object, l, n2 = this.getIntVolatile(object, l), n)) {
        }
        return n2;
    }

    public final long getAndSetLong(Object object, long l, long l2) {
        long l3;
        while (!this.compareAndSwapLong(object, l, l3 = this.getLongVolatile(object, l), l2)) {
        }
        return l3;
    }

    public final Object getAndSetObject(Object object, long l, Object object2) {
        Object object3;
        while (!this.compareAndSwapObject(object, l, object3 = this.getObjectVolatile(object, l), object2)) {
        }
        return object3;
    }

    @FastNative
    public native boolean getBoolean(Object var1, long var2);

    @FastNative
    public native byte getByte(long var1);

    @FastNative
    public native byte getByte(Object var1, long var2);

    @FastNative
    public native char getChar(long var1);

    @FastNative
    public native char getChar(Object var1, long var2);

    @FastNative
    public native double getDouble(long var1);

    @FastNative
    public native double getDouble(Object var1, long var2);

    @FastNative
    public native float getFloat(long var1);

    @FastNative
    public native float getFloat(Object var1, long var2);

    @FastNative
    public native int getInt(long var1);

    @FastNative
    public native int getInt(Object var1, long var2);

    @FastNative
    public native int getIntVolatile(Object var1, long var2);

    @FastNative
    public native long getLong(long var1);

    @FastNative
    public native long getLong(Object var1, long var2);

    @FastNative
    public native long getLongVolatile(Object var1, long var2);

    @FastNative
    public native Object getObject(Object var1, long var2);

    @FastNative
    public native Object getObjectVolatile(Object var1, long var2);

    @FastNative
    public native short getShort(long var1);

    @FastNative
    public native short getShort(Object var1, long var2);

    @FastNative
    public native void loadFence();

    public long objectFieldOffset(Field field) {
        if (!Modifier.isStatic(field.getModifiers())) {
            return field.getOffset();
        }
        throw new IllegalArgumentException("valid for instance fields only");
    }

    @FastNative
    public native int pageSize();

    public native void park(boolean var1, long var2);

    @FastNative
    public native void putBoolean(Object var1, long var2, boolean var4);

    @FastNative
    public native void putByte(long var1, byte var3);

    @FastNative
    public native void putByte(Object var1, long var2, byte var4);

    @FastNative
    public native void putChar(long var1, char var3);

    @FastNative
    public native void putChar(Object var1, long var2, char var4);

    @FastNative
    public native void putDouble(long var1, double var3);

    @FastNative
    public native void putDouble(Object var1, long var2, double var4);

    @FastNative
    public native void putFloat(long var1, float var3);

    @FastNative
    public native void putFloat(Object var1, long var2, float var4);

    @FastNative
    public native void putInt(long var1, int var3);

    @FastNative
    public native void putInt(Object var1, long var2, int var4);

    @FastNative
    public native void putIntVolatile(Object var1, long var2, int var4);

    @FastNative
    public native void putLong(long var1, long var3);

    @FastNative
    public native void putLong(Object var1, long var2, long var4);

    @FastNative
    public native void putLongVolatile(Object var1, long var2, long var4);

    @FastNative
    public native void putObject(Object var1, long var2, Object var4);

    @FastNative
    public native void putObjectVolatile(Object var1, long var2, Object var4);

    @FastNative
    public native void putOrderedInt(Object var1, long var2, int var4);

    @FastNative
    public native void putOrderedLong(Object var1, long var2, long var4);

    @FastNative
    public native void putOrderedObject(Object var1, long var2, Object var4);

    @FastNative
    public native void putShort(long var1, short var3);

    @FastNative
    public native void putShort(Object var1, long var2, short var4);

    @FastNative
    public native void setMemory(long var1, long var3, byte var5);

    @FastNative
    public native void storeFence();

    @FastNative
    public native void unpark(Object var1);
}

