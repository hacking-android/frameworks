/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Frame;

public class NativeBuffer {
    private Frame mAttachedFrame;
    private long mDataPointer = 0L;
    private boolean mOwnsData = false;
    private int mRefCount = 1;
    private int mSize = 0;

    static {
        System.loadLibrary("filterfw");
    }

    public NativeBuffer() {
    }

    public NativeBuffer(int n) {
        this.allocate(this.getElementSize() * n);
        this.mOwnsData = true;
    }

    private native boolean allocate(int var1);

    private native boolean deallocate(boolean var1);

    private native boolean nativeCopyTo(NativeBuffer var1);

    protected void assertReadable() {
        Frame frame;
        if (this.mDataPointer != 0L && this.mSize != 0 && ((frame = this.mAttachedFrame) == null || frame.hasNativeAllocation())) {
            return;
        }
        throw new NullPointerException("Attempting to read from null data frame!");
    }

    protected void assertWritable() {
        if (!this.isReadOnly()) {
            return;
        }
        throw new RuntimeException("Attempting to modify read-only native (structured) data!");
    }

    void attachToFrame(Frame frame) {
        this.mAttachedFrame = frame;
    }

    public int count() {
        int n = this.mDataPointer != 0L ? this.mSize / this.getElementSize() : 0;
        return n;
    }

    public int getElementSize() {
        return 1;
    }

    public boolean isReadOnly() {
        Frame frame = this.mAttachedFrame;
        boolean bl = frame != null ? frame.isReadOnly() : false;
        return bl;
    }

    public NativeBuffer mutableCopy() {
        NativeBuffer nativeBuffer;
        block2 : {
            try {
                nativeBuffer = (NativeBuffer)this.getClass().newInstance();
                if (this.mSize <= 0 || this.nativeCopyTo(nativeBuffer)) break block2;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to allocate a copy of ");
                stringBuilder.append(this.getClass());
                stringBuilder.append("! Make sure the class has a default constructor!");
                throw new RuntimeException(stringBuilder.toString());
            }
            throw new RuntimeException("Failed to copy NativeBuffer to mutable instance!");
        }
        return nativeBuffer;
    }

    public NativeBuffer release() {
        boolean bl = false;
        Frame frame = this.mAttachedFrame;
        boolean bl2 = false;
        boolean bl3 = false;
        if (frame != null) {
            if (frame.release() == null) {
                bl3 = true;
            }
        } else {
            bl3 = bl;
            if (this.mOwnsData) {
                --this.mRefCount;
                bl3 = bl2;
                if (this.mRefCount == 0) {
                    bl3 = true;
                }
            }
        }
        if (bl3) {
            this.deallocate(this.mOwnsData);
            return null;
        }
        return this;
    }

    public NativeBuffer retain() {
        Frame frame = this.mAttachedFrame;
        if (frame != null) {
            frame.retain();
        } else if (this.mOwnsData) {
            ++this.mRefCount;
        }
        return this;
    }

    public int size() {
        return this.mSize;
    }
}

