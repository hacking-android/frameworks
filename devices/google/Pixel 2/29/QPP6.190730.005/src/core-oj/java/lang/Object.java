/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 */
package java.lang;

import dalvik.annotation.optimization.FastNative;

public class Object {
    private transient Class<?> shadow$_klass_;
    private transient int shadow$_monitor_;

    static int identityHashCode(Object object) {
        int n = object.shadow$_monitor_;
        if ((-1073741824 & n) == Integer.MIN_VALUE) {
            return 268435455 & n;
        }
        return Object.identityHashCodeNative(object);
    }

    @FastNative
    private static native int identityHashCodeNative(Object var0);

    @FastNative
    private native Object internalClone();

    protected Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return this.internalClone();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Class ");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" doesn't implement Cloneable");
        throw new CloneNotSupportedException(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        boolean bl = this == object;
        return bl;
    }

    protected void finalize() throws Throwable {
    }

    public final Class<?> getClass() {
        return this.shadow$_klass_;
    }

    public int hashCode() {
        return Object.identityHashCode(this);
    }

    @FastNative
    public final native void notify();

    @FastNative
    public final native void notifyAll();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        return stringBuilder.toString();
    }

    public final void wait() throws InterruptedException {
        this.wait(0L);
    }

    public final void wait(long l) throws InterruptedException {
        this.wait(l, 0);
    }

    @FastNative
    public final native void wait(long var1, int var3) throws InterruptedException;
}

