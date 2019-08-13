/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.io.InputStream;
import java.io.OutputStream;

public class Picture {
    private static final int WORKING_STREAM_STORAGE = 16384;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private long mNativePicture;
    private PictureCanvas mRecordingCanvas;
    private boolean mRequiresHwAcceleration;

    public Picture() {
        this(Picture.nativeConstructor(0L));
    }

    public Picture(long l) {
        if (l != 0L) {
            this.mNativePicture = l;
            return;
        }
        throw new IllegalArgumentException();
    }

    public Picture(Picture picture) {
        long l = picture != null ? picture.mNativePicture : 0L;
        this(Picture.nativeConstructor(l));
    }

    @Deprecated
    public static Picture createFromStream(InputStream inputStream) {
        return new Picture(Picture.nativeCreateFromStream(inputStream, new byte[16384]));
    }

    private static native long nativeBeginRecording(long var0, int var2, int var3);

    private static native long nativeConstructor(long var0);

    private static native long nativeCreateFromStream(InputStream var0, byte[] var1);

    private static native void nativeDestructor(long var0);

    private static native void nativeDraw(long var0, long var2);

    private static native void nativeEndRecording(long var0);

    private static native int nativeGetHeight(long var0);

    private static native int nativeGetWidth(long var0);

    private static native boolean nativeWriteToStream(long var0, OutputStream var2, byte[] var3);

    private void verifyValid() {
        if (this.mNativePicture != 0L) {
            return;
        }
        throw new IllegalStateException("Picture is destroyed");
    }

    public Canvas beginRecording(int n, int n2) {
        this.verifyValid();
        if (this.mRecordingCanvas == null) {
            this.mRecordingCanvas = new PictureCanvas(this, Picture.nativeBeginRecording(this.mNativePicture, n, n2));
            this.mRequiresHwAcceleration = false;
            return this.mRecordingCanvas;
        }
        throw new IllegalStateException("Picture already recording, must call #endRecording()");
    }

    public void close() {
        long l = this.mNativePicture;
        if (l != 0L) {
            Picture.nativeDestructor(l);
            this.mNativePicture = 0L;
        }
    }

    public void draw(Canvas canvas) {
        this.verifyValid();
        if (this.mRecordingCanvas != null) {
            this.endRecording();
        }
        if (this.mRequiresHwAcceleration && !canvas.isHardwareAccelerated()) {
            canvas.onHwBitmapInSwMode();
        }
        Picture.nativeDraw(canvas.getNativeCanvasWrapper(), this.mNativePicture);
    }

    public void endRecording() {
        this.verifyValid();
        PictureCanvas pictureCanvas = this.mRecordingCanvas;
        if (pictureCanvas != null) {
            this.mRequiresHwAcceleration = pictureCanvas.mHoldsHwBitmap;
            this.mRecordingCanvas = null;
            Picture.nativeEndRecording(this.mNativePicture);
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getHeight() {
        this.verifyValid();
        return Picture.nativeGetHeight(this.mNativePicture);
    }

    public int getWidth() {
        this.verifyValid();
        return Picture.nativeGetWidth(this.mNativePicture);
    }

    public boolean requiresHardwareAcceleration() {
        this.verifyValid();
        return this.mRequiresHwAcceleration;
    }

    @Deprecated
    public void writeToStream(OutputStream outputStream) {
        this.verifyValid();
        if (outputStream != null) {
            if (Picture.nativeWriteToStream(this.mNativePicture, outputStream, new byte[16384])) {
                return;
            }
            throw new RuntimeException();
        }
        throw new IllegalArgumentException("stream cannot be null");
    }

    private static class PictureCanvas
    extends Canvas {
        boolean mHoldsHwBitmap;
        private final Picture mPicture;

        public PictureCanvas(Picture picture, long l) {
            super(l);
            this.mPicture = picture;
            this.mDensity = 0;
        }

        @Override
        public void drawPicture(Picture picture) {
            if (this.mPicture != picture) {
                super.drawPicture(picture);
                return;
            }
            throw new RuntimeException("Cannot draw a picture into its recording canvas");
        }

        @Override
        protected void onHwBitmapInSwMode() {
            this.mHoldsHwBitmap = true;
        }

        @Override
        public void setBitmap(Bitmap bitmap) {
            throw new RuntimeException("Cannot call setBitmap on a picture canvas");
        }
    }

}

