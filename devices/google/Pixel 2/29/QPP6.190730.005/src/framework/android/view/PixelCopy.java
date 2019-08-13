/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowManager;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PixelCopy {
    public static final int ERROR_DESTINATION_INVALID = 5;
    public static final int ERROR_SOURCE_INVALID = 4;
    public static final int ERROR_SOURCE_NO_DATA = 3;
    public static final int ERROR_TIMEOUT = 2;
    public static final int ERROR_UNKNOWN = 1;
    public static final int SUCCESS = 0;

    private PixelCopy() {
    }

    public static void request(Surface surface, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        PixelCopy.request(surface, null, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(Surface surface, Rect rect, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        PixelCopy.validateBitmapDest(bitmap);
        if (surface.isValid()) {
            if (rect != null && rect.isEmpty()) {
                throw new IllegalArgumentException("sourceRect is empty");
            }
            handler.post(new Runnable(ThreadedRenderer.copySurfaceInto(surface, rect, bitmap)){
                final /* synthetic */ int val$result;
                {
                    this.val$result = n;
                }

                @Override
                public void run() {
                    OnPixelCopyFinishedListener.this.onPixelCopyFinished(this.val$result);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Surface isn't valid, source.isValid() == false");
    }

    public static void request(SurfaceView surfaceView, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        PixelCopy.request(surfaceView.getHolder().getSurface(), bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(SurfaceView surfaceView, Rect rect, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        PixelCopy.request(surfaceView.getHolder().getSurface(), rect, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(Window window, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        PixelCopy.request(window, null, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(Window object, Rect rect, Bitmap bitmap, OnPixelCopyFinishedListener onPixelCopyFinishedListener, Handler handler) {
        PixelCopy.validateBitmapDest(bitmap);
        if (object != null) {
            if (((Window)object).peekDecorView() != null) {
                Surface surface = null;
                ViewRootImpl viewRootImpl = ((Window)object).peekDecorView().getViewRootImpl();
                object = rect;
                if (viewRootImpl != null) {
                    surface = viewRootImpl.mSurface;
                    object = viewRootImpl.mWindowAttributes.surfaceInsets;
                    if (rect == null) {
                        object = new Rect(((Rect)object).left, ((Rect)object).top, viewRootImpl.mWidth + ((Rect)object).left, viewRootImpl.mHeight + ((Rect)object).top);
                    } else {
                        rect.offset(((Rect)object).left, ((Rect)object).top);
                        object = rect;
                    }
                }
                if (surface != null && surface.isValid()) {
                    PixelCopy.request(surface, (Rect)object, bitmap, onPixelCopyFinishedListener, handler);
                    return;
                }
                throw new IllegalArgumentException("Window doesn't have a backing surface!");
            }
            throw new IllegalArgumentException("Only able to copy windows with decor views");
        }
        throw new IllegalArgumentException("source is null");
    }

    private static void validateBitmapDest(Bitmap bitmap) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                if (bitmap.isMutable()) {
                    return;
                }
                throw new IllegalArgumentException("Bitmap is immutable");
            }
            throw new IllegalArgumentException("Bitmap is recycled");
        }
        throw new IllegalArgumentException("Bitmap cannot be null");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CopyResultStatus {
    }

    public static interface OnPixelCopyFinishedListener {
        public void onPixelCopyFinished(int var1);
    }

}

