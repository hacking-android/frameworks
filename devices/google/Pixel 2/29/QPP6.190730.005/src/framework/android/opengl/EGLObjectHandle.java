/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

public abstract class EGLObjectHandle {
    private final long mHandle;

    @Deprecated
    protected EGLObjectHandle(int n) {
        this.mHandle = n;
    }

    protected EGLObjectHandle(long l) {
        this.mHandle = l;
    }

    @Deprecated
    public int getHandle() {
        long l = this.mHandle;
        if ((0xFFFFFFFFL & l) == l) {
            return (int)l;
        }
        throw new UnsupportedOperationException();
    }

    public long getNativeHandle() {
        return this.mHandle;
    }

    public int hashCode() {
        long l = this.mHandle;
        return 17 * 31 + (int)(l ^ l >>> 32);
    }
}

