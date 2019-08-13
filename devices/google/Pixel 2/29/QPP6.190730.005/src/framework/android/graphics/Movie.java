/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
public class Movie {
    @UnsupportedAppUsage
    private long mNativeMovie;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Movie(long l) {
        if (l != 0L) {
            this.mNativeMovie = l;
            return;
        }
        throw new RuntimeException("native movie creation failed");
    }

    public static native Movie decodeByteArray(byte[] var0, int var1, int var2);

    public static Movie decodeFile(String object) {
        try {
            object = new FileInputStream((String)object);
        }
        catch (FileNotFoundException fileNotFoundException) {
            return null;
        }
        return Movie.decodeTempStream((InputStream)object);
    }

    public static Movie decodeStream(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        if (inputStream instanceof AssetManager.AssetInputStream) {
            return Movie.nativeDecodeAsset(((AssetManager.AssetInputStream)inputStream).getNativeAsset());
        }
        return Movie.nativeDecodeStream(inputStream);
    }

    private static Movie decodeTempStream(InputStream inputStream) {
        Movie movie;
        Movie movie2 = null;
        movie2 = movie = Movie.decodeStream(inputStream);
        try {
            inputStream.close();
            movie2 = movie;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return movie2;
    }

    private native void nDraw(long var1, float var3, float var4, long var5);

    private static native Movie nativeDecodeAsset(long var0);

    private static native Movie nativeDecodeStream(InputStream var0);

    private static native void nativeDestructor(long var0);

    public void draw(Canvas canvas, float f, float f2) {
        this.nDraw(canvas.getNativeCanvasWrapper(), f, f2, 0L);
    }

    public void draw(Canvas canvas, float f, float f2, Paint paint) {
        long l = canvas.getNativeCanvasWrapper();
        long l2 = paint != null ? paint.getNativeInstance() : 0L;
        this.nDraw(l, f, f2, l2);
    }

    public native int duration();

    protected void finalize() throws Throwable {
        try {
            Movie.nativeDestructor(this.mNativeMovie);
            this.mNativeMovie = 0L;
            return;
        }
        finally {
            super.finalize();
        }
    }

    public native int height();

    public native boolean isOpaque();

    public native boolean setTime(int var1);

    public native int width();
}

