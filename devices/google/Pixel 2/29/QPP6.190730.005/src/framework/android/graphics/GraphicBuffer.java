/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

public class GraphicBuffer
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<GraphicBuffer> CREATOR = new Parcelable.Creator<GraphicBuffer>(){

        @Override
        public GraphicBuffer createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            long l = GraphicBuffer.nReadGraphicBufferFromParcel(parcel);
            if (l != 0L) {
                return new GraphicBuffer(n, n2, n3, n4, l);
            }
            return null;
        }

        public GraphicBuffer[] newArray(int n) {
            return new GraphicBuffer[n];
        }
    };
    public static final int USAGE_HW_2D = 1024;
    public static final int USAGE_HW_COMPOSER = 2048;
    public static final int USAGE_HW_MASK = 466688;
    public static final int USAGE_HW_RENDER = 512;
    public static final int USAGE_HW_TEXTURE = 256;
    public static final int USAGE_HW_VIDEO_ENCODER = 65536;
    public static final int USAGE_PROTECTED = 16384;
    public static final int USAGE_SOFTWARE_MASK = 255;
    public static final int USAGE_SW_READ_MASK = 15;
    public static final int USAGE_SW_READ_NEVER = 0;
    public static final int USAGE_SW_READ_OFTEN = 3;
    public static final int USAGE_SW_READ_RARELY = 2;
    public static final int USAGE_SW_WRITE_MASK = 240;
    public static final int USAGE_SW_WRITE_NEVER = 0;
    public static final int USAGE_SW_WRITE_OFTEN = 48;
    public static final int USAGE_SW_WRITE_RARELY = 32;
    private Canvas mCanvas;
    private boolean mDestroyed;
    private final int mFormat;
    private final int mHeight;
    @UnsupportedAppUsage
    private final long mNativeObject;
    private int mSaveCount;
    private final int mUsage;
    private final int mWidth;

    @UnsupportedAppUsage
    private GraphicBuffer(int n, int n2, int n3, int n4, long l) {
        this.mWidth = n;
        this.mHeight = n2;
        this.mFormat = n3;
        this.mUsage = n4;
        this.mNativeObject = l;
    }

    public static GraphicBuffer create(int n, int n2, int n3, int n4) {
        long l = GraphicBuffer.nCreateGraphicBuffer(n, n2, n3, n4);
        if (l != 0L) {
            return new GraphicBuffer(n, n2, n3, n4, l);
        }
        return null;
    }

    @UnsupportedAppUsage
    public static GraphicBuffer createFromExisting(int n, int n2, int n3, int n4, long l) {
        if ((l = GraphicBuffer.nWrapGraphicBuffer(l)) != 0L) {
            return new GraphicBuffer(n, n2, n3, n4, l);
        }
        return null;
    }

    private static native long nCreateGraphicBuffer(int var0, int var1, int var2, int var3);

    private static native void nDestroyGraphicBuffer(long var0);

    private static native boolean nLockCanvas(long var0, Canvas var2, Rect var3);

    private static native long nReadGraphicBufferFromParcel(Parcel var0);

    private static native boolean nUnlockCanvasAndPost(long var0, Canvas var2);

    private static native long nWrapGraphicBuffer(long var0);

    private static native void nWriteGraphicBufferToParcel(long var0, Parcel var2);

    @Override
    public int describeContents() {
        return 0;
    }

    public void destroy() {
        if (!this.mDestroyed) {
            this.mDestroyed = true;
            GraphicBuffer.nDestroyGraphicBuffer(this.mNativeObject);
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (!this.mDestroyed) {
                GraphicBuffer.nDestroyGraphicBuffer(this.mNativeObject);
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getUsage() {
        return this.mUsage;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public Canvas lockCanvas() {
        return this.lockCanvas(null);
    }

    public Canvas lockCanvas(Rect rect) {
        if (this.mDestroyed) {
            return null;
        }
        if (this.mCanvas == null) {
            this.mCanvas = new Canvas();
        }
        if (GraphicBuffer.nLockCanvas(this.mNativeObject, this.mCanvas, rect)) {
            this.mSaveCount = this.mCanvas.save();
            return this.mCanvas;
        }
        return null;
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        Canvas canvas2;
        if (!this.mDestroyed && (canvas2 = this.mCanvas) != null && canvas == canvas2) {
            canvas.restoreToCount(this.mSaveCount);
            this.mSaveCount = 0;
            GraphicBuffer.nUnlockCanvasAndPost(this.mNativeObject, this.mCanvas);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (!this.mDestroyed) {
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
            parcel.writeInt(this.mFormat);
            parcel.writeInt(this.mUsage);
            GraphicBuffer.nWriteGraphicBufferToParcel(this.mNativeObject, parcel);
            return;
        }
        throw new IllegalStateException("This GraphicBuffer has been destroyed and cannot be written to a parcel.");
    }

}

