/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.MutableFrameFormat;
import android.graphics.Bitmap;
import java.nio.ByteBuffer;

public abstract class Frame {
    public static final int NO_BINDING = 0;
    public static final long TIMESTAMP_NOT_SET = -2L;
    public static final long TIMESTAMP_UNKNOWN = -1L;
    private long mBindingId = 0L;
    private int mBindingType = 0;
    private FrameFormat mFormat;
    private FrameManager mFrameManager;
    private boolean mReadOnly = false;
    private int mRefCount = 1;
    private boolean mReusable = false;
    private long mTimestamp = -2L;

    Frame(FrameFormat frameFormat, FrameManager frameManager) {
        this.mFormat = frameFormat.mutableCopy();
        this.mFrameManager = frameManager;
    }

    Frame(FrameFormat frameFormat, FrameManager frameManager, int n, long l) {
        this.mFormat = frameFormat.mutableCopy();
        this.mFrameManager = frameManager;
        this.mBindingType = n;
        this.mBindingId = l;
    }

    protected static Bitmap convertBitmapToRGBA(Bitmap bitmap) {
        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888) {
            return bitmap;
        }
        if ((bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)) != null) {
            if (bitmap.getRowBytes() == bitmap.getWidth() * 4) {
                return bitmap;
            }
            throw new RuntimeException("Unsupported row byte count in bitmap!");
        }
        throw new RuntimeException("Error converting bitmap to RGBA!");
    }

    protected void assertFrameMutable() {
        if (!this.isReadOnly()) {
            return;
        }
        throw new RuntimeException("Attempting to modify read-only frame!");
    }

    final int decRefCount() {
        --this.mRefCount;
        return this.mRefCount;
    }

    public long getBindingId() {
        return this.mBindingId;
    }

    public int getBindingType() {
        return this.mBindingType;
    }

    @UnsupportedAppUsage
    public abstract Bitmap getBitmap();

    public int getCapacity() {
        return this.getFormat().getSize();
    }

    public abstract ByteBuffer getData();

    public abstract float[] getFloats();

    @UnsupportedAppUsage
    public FrameFormat getFormat() {
        return this.mFormat;
    }

    public FrameManager getFrameManager() {
        return this.mFrameManager;
    }

    public abstract int[] getInts();

    public abstract Object getObjectValue();

    public int getRefCount() {
        return this.mRefCount;
    }

    @UnsupportedAppUsage
    public long getTimestamp() {
        return this.mTimestamp;
    }

    protected abstract boolean hasNativeAllocation();

    final int incRefCount() {
        ++this.mRefCount;
        return this.mRefCount;
    }

    public boolean isReadOnly() {
        return this.mReadOnly;
    }

    final boolean isReusable() {
        return this.mReusable;
    }

    final void markReadOnly() {
        this.mReadOnly = true;
    }

    protected void onFrameFetch() {
    }

    protected void onFrameStore() {
    }

    @UnsupportedAppUsage
    public Frame release() {
        FrameManager frameManager = this.mFrameManager;
        if (frameManager != null) {
            return frameManager.releaseFrame(this);
        }
        return this;
    }

    protected abstract void releaseNativeAllocation();

    protected boolean requestResize(int[] arrn) {
        return false;
    }

    protected void reset(FrameFormat frameFormat) {
        this.mFormat = frameFormat.mutableCopy();
        this.mReadOnly = false;
        this.mRefCount = 1;
    }

    public Frame retain() {
        FrameManager frameManager = this.mFrameManager;
        if (frameManager != null) {
            return frameManager.retainFrame(this);
        }
        return this;
    }

    public abstract void setBitmap(Bitmap var1);

    public void setData(ByteBuffer byteBuffer) {
        this.setData(byteBuffer, 0, byteBuffer.limit());
    }

    public abstract void setData(ByteBuffer var1, int var2, int var3);

    public void setData(byte[] arrby, int n, int n2) {
        this.setData(ByteBuffer.wrap(arrby, n, n2));
    }

    public void setDataFromFrame(Frame frame) {
        this.setData(frame.getData());
    }

    public abstract void setFloats(float[] var1);

    protected void setFormat(FrameFormat frameFormat) {
        this.mFormat = frameFormat.mutableCopy();
    }

    protected void setGenericObjectValue(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot set object value of unsupported type: ");
        stringBuilder.append(object.getClass());
        throw new RuntimeException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public abstract void setInts(int[] var1);

    public void setObjectValue(Object object) {
        this.assertFrameMutable();
        if (object instanceof int[]) {
            this.setInts((int[])object);
        } else if (object instanceof float[]) {
            this.setFloats((float[])object);
        } else if (object instanceof ByteBuffer) {
            this.setData((ByteBuffer)object);
        } else if (object instanceof Bitmap) {
            this.setBitmap((Bitmap)object);
        } else {
            this.setGenericObjectValue(object);
        }
    }

    protected void setReusable(boolean bl) {
        this.mReusable = bl;
    }

    @UnsupportedAppUsage
    public void setTimestamp(long l) {
        this.mTimestamp = l;
    }
}

