/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import java.nio.ByteBuffer;

public abstract class Image
implements AutoCloseable {
    private Rect mCropRect;
    protected boolean mIsImageValid = false;

    @UnsupportedAppUsage
    protected Image() {
    }

    @Override
    public abstract void close();

    public Rect getCropRect() {
        this.throwISEIfImageIsInvalid();
        Rect rect = this.mCropRect;
        if (rect == null) {
            return new Rect(0, 0, this.getWidth(), this.getHeight());
        }
        return new Rect(rect);
    }

    public abstract int getFormat();

    public HardwareBuffer getHardwareBuffer() {
        this.throwISEIfImageIsInvalid();
        return null;
    }

    public abstract int getHeight();

    long getNativeContext() {
        this.throwISEIfImageIsInvalid();
        return 0L;
    }

    Object getOwner() {
        this.throwISEIfImageIsInvalid();
        return null;
    }

    public abstract Plane[] getPlanes();

    public abstract int getScalingMode();

    public abstract long getTimestamp();

    public abstract int getTransform();

    public abstract int getWidth();

    boolean isAttachable() {
        this.throwISEIfImageIsInvalid();
        return false;
    }

    public void setCropRect(Rect rect) {
        this.throwISEIfImageIsInvalid();
        Rect rect2 = rect;
        if (rect != null) {
            rect2 = rect = new Rect(rect);
            if (!rect.intersect(0, 0, this.getWidth(), this.getHeight())) {
                rect.setEmpty();
                rect2 = rect;
            }
        }
        this.mCropRect = rect2;
    }

    public void setTimestamp(long l) {
        this.throwISEIfImageIsInvalid();
    }

    protected void throwISEIfImageIsInvalid() {
        if (this.mIsImageValid) {
            return;
        }
        throw new IllegalStateException("Image is already closed");
    }

    public static abstract class Plane {
        @UnsupportedAppUsage
        protected Plane() {
        }

        public abstract ByteBuffer getBuffer();

        public abstract int getPixelStride();

        public abstract int getRowStride();
    }

}

