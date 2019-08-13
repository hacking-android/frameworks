/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 *  java.nio.NioUtils
 */
package android.media;

import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Size;
import android.view.Surface;
import dalvik.system.VMRuntime;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ImageWriter
implements AutoCloseable {
    private List<Image> mDequeuedImages = new CopyOnWriteArrayList<Image>();
    private int mEstimatedNativeAllocBytes;
    private OnImageReleasedListener mListener;
    private ListenerHandler mListenerHandler;
    private final Object mListenerLock = new Object();
    private final int mMaxImages;
    private long mNativeContext;
    private int mWriterFormat;

    static {
        System.loadLibrary("media_jni");
        ImageWriter.nativeClassInit();
    }

    protected ImageWriter(Surface object, int n, int n2) {
        if (object != null && n >= 1) {
            this.mMaxImages = n;
            int n3 = n2;
            if (n2 == 0) {
                n3 = SurfaceUtils.getSurfaceFormat((Surface)object);
            }
            this.mNativeContext = this.nativeInit(new WeakReference<ImageWriter>(this), (Surface)object, n, n3);
            object = SurfaceUtils.getSurfaceSize((Surface)object);
            this.mEstimatedNativeAllocBytes = ImageUtils.getEstimatedNativeAllocBytes(((Size)object).getWidth(), ((Size)object).getHeight(), n3, 1);
            VMRuntime.getRuntime().registerNativeAllocation(this.mEstimatedNativeAllocBytes);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal input argument: surface ");
        stringBuilder.append(object);
        stringBuilder.append(", maxImages: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void abortImage(Image image) {
        if (image != null) {
            if (this.mDequeuedImages.contains(image)) {
                WriterSurfaceImage writerSurfaceImage = (WriterSurfaceImage)image;
                if (!writerSurfaceImage.mIsImageValid) {
                    return;
                }
                this.cancelImage(this.mNativeContext, image);
                this.mDequeuedImages.remove(image);
                writerSurfaceImage.clearSurfacePlanes();
                writerSurfaceImage.mIsImageValid = false;
                return;
            }
            throw new IllegalStateException("It is illegal to abort some image that is not dequeued yet");
        }
        throw new IllegalArgumentException("image shouldn't be null");
    }

    private void attachAndQueueInputImage(Image image) {
        if (image != null) {
            if (!this.isImageOwnedByMe(image)) {
                if (image.isAttachable()) {
                    Rect rect = image.getCropRect();
                    this.nativeAttachAndQueueImage(this.mNativeContext, image.getNativeContext(), image.getFormat(), image.getTimestamp(), rect.left, rect.top, rect.right, rect.bottom, image.getTransform(), image.getScalingMode());
                    return;
                }
                throw new IllegalStateException("Image was not detached from last owner, or image  is not detachable");
            }
            throw new IllegalArgumentException("Can not attach an image that is owned ImageWriter already");
        }
        throw new IllegalArgumentException("image shouldn't be null");
    }

    private synchronized native void cancelImage(long var1, Image var3);

    private boolean isImageOwnedByMe(Image image) {
        if (!(image instanceof WriterSurfaceImage)) {
            return false;
        }
        return ((WriterSurfaceImage)image).getOwner() == this;
    }

    private synchronized native int nativeAttachAndQueueImage(long var1, long var3, int var5, long var6, int var8, int var9, int var10, int var11, int var12, int var13);

    private static native void nativeClassInit();

    private synchronized native void nativeClose(long var1);

    private synchronized native void nativeDequeueInputImage(long var1, Image var3);

    private synchronized native long nativeInit(Object var1, Surface var2, int var3, int var4);

    private synchronized native void nativeQueueInputImage(long var1, Image var3, long var4, int var6, int var7, int var8, int var9, int var10, int var11);

    public static ImageWriter newInstance(Surface surface, int n) {
        return new ImageWriter(surface, n, 0);
    }

    public static ImageWriter newInstance(Surface object, int n, int n2) {
        if (!ImageFormat.isPublicFormat(n2) && !PixelFormat.isPublicFormat(n2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid format is specified: ");
            ((StringBuilder)object).append(n2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return new ImageWriter((Surface)object, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void postEventFromNative(Object object) {
        Object object2 = (ImageWriter)((WeakReference)object).get();
        if (object2 == null) {
            return;
        }
        object = ((ImageWriter)object2).mListenerLock;
        // MONITORENTER : object
        object2 = ((ImageWriter)object2).mListenerHandler;
        // MONITOREXIT : object
        if (object2 == null) return;
        ((Handler)object2).sendEmptyMessage(0);
    }

    @Override
    public void close() {
        this.setOnImageReleasedListener(null, null);
        Iterator<Image> iterator = this.mDequeuedImages.iterator();
        while (iterator.hasNext()) {
            iterator.next().close();
        }
        this.mDequeuedImages.clear();
        this.nativeClose(this.mNativeContext);
        this.mNativeContext = 0L;
        if (this.mEstimatedNativeAllocBytes > 0) {
            VMRuntime.getRuntime().registerNativeFree(this.mEstimatedNativeAllocBytes);
            this.mEstimatedNativeAllocBytes = 0;
        }
    }

    public Image dequeueInputImage() {
        if (this.mDequeuedImages.size() < this.mMaxImages) {
            WriterSurfaceImage writerSurfaceImage = new WriterSurfaceImage(this);
            this.nativeDequeueInputImage(this.mNativeContext, writerSurfaceImage);
            this.mDequeuedImages.add(writerSurfaceImage);
            writerSurfaceImage.mIsImageValid = true;
            return writerSurfaceImage;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Already dequeued max number of Images ");
        stringBuilder.append(this.mMaxImages);
        throw new IllegalStateException(stringBuilder.toString());
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

    public int getFormat() {
        return this.mWriterFormat;
    }

    public int getMaxImages() {
        return this.mMaxImages;
    }

    public void queueInputImage(Image image) {
        if (image != null) {
            boolean bl = this.isImageOwnedByMe(image);
            if (bl && !((WriterSurfaceImage)image).mIsImageValid) {
                throw new IllegalStateException("Image from ImageWriter is invalid");
            }
            if (!bl) {
                if (image.getOwner() instanceof ImageReader) {
                    ((ImageReader)image.getOwner()).detachImage(image);
                    this.attachAndQueueInputImage(image);
                    image.close();
                    return;
                }
                throw new IllegalArgumentException("Only images from ImageReader can be queued to ImageWriter, other image source is not supported yet!");
            }
            Rect rect = image.getCropRect();
            this.nativeQueueInputImage(this.mNativeContext, image, image.getTimestamp(), rect.left, rect.top, rect.right, rect.bottom, image.getTransform(), image.getScalingMode());
            if (bl) {
                this.mDequeuedImages.remove(image);
                image = (WriterSurfaceImage)image;
                ((WriterSurfaceImage)image).clearSurfacePlanes();
                ((WriterSurfaceImage)image).mIsImageValid = false;
            }
            return;
        }
        throw new IllegalArgumentException("image shouldn't be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnImageReleasedListener(OnImageReleasedListener object, Handler object2) {
        Object object3 = this.mListenerLock;
        synchronized (object3) {
            if (object != null) {
                if ((object2 = object2 != null ? ((Handler)object2).getLooper() : Looper.myLooper()) == null) {
                    object = new IllegalArgumentException("handler is null but the current thread is not a looper");
                    throw object;
                }
                if (this.mListenerHandler == null || this.mListenerHandler.getLooper() != object2) {
                    ListenerHandler listenerHandler;
                    this.mListenerHandler = listenerHandler = new ListenerHandler((Looper)object2);
                }
                this.mListener = object;
            } else {
                this.mListener = null;
                this.mListenerHandler = null;
            }
            return;
        }
    }

    private final class ListenerHandler
    extends Handler {
        public ListenerHandler(Looper looper) {
            super(looper, null, true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void handleMessage(Message object) {
            object = ImageWriter.this.mListenerLock;
            // MONITORENTER : object
            OnImageReleasedListener onImageReleasedListener = ImageWriter.this.mListener;
            // MONITOREXIT : object
            if (onImageReleasedListener == null) return;
            onImageReleasedListener.onImageReleased(ImageWriter.this);
        }
    }

    public static interface OnImageReleasedListener {
        public void onImageReleased(ImageWriter var1);
    }

    private static class WriterSurfaceImage
    extends Image {
        private final long DEFAULT_TIMESTAMP;
        private int mFormat = -1;
        private int mHeight = -1;
        private long mNativeBuffer;
        private int mNativeFenceFd = -1;
        private ImageWriter mOwner;
        private SurfacePlane[] mPlanes;
        private int mScalingMode = 0;
        private long mTimestamp = Long.MIN_VALUE;
        private int mTransform = 0;
        private int mWidth = -1;

        public WriterSurfaceImage(ImageWriter imageWriter) {
            this.DEFAULT_TIMESTAMP = Long.MIN_VALUE;
            this.mOwner = imageWriter;
        }

        private void clearSurfacePlanes() {
            if (this.mIsImageValid && this.mPlanes != null) {
                SurfacePlane[] arrsurfacePlane;
                for (int i = 0; i < (arrsurfacePlane = this.mPlanes).length; ++i) {
                    if (arrsurfacePlane[i] == null) continue;
                    arrsurfacePlane[i].clearBuffer();
                    this.mPlanes[i] = null;
                }
            }
        }

        private synchronized native SurfacePlane[] nativeCreatePlanes(int var1, int var2);

        private synchronized native int nativeGetFormat();

        private synchronized native HardwareBuffer nativeGetHardwareBuffer();

        private synchronized native int nativeGetHeight();

        private synchronized native int nativeGetWidth();

        @Override
        public void close() {
            if (this.mIsImageValid) {
                this.getOwner().abortImage(this);
            }
        }

        protected final void finalize() throws Throwable {
            try {
                this.close();
                return;
            }
            finally {
                Object.super.finalize();
            }
        }

        @Override
        public int getFormat() {
            this.throwISEIfImageIsInvalid();
            if (this.mFormat == -1) {
                this.mFormat = this.nativeGetFormat();
            }
            return this.mFormat;
        }

        @Override
        public HardwareBuffer getHardwareBuffer() {
            this.throwISEIfImageIsInvalid();
            return this.nativeGetHardwareBuffer();
        }

        @Override
        public int getHeight() {
            this.throwISEIfImageIsInvalid();
            if (this.mHeight == -1) {
                this.mHeight = this.nativeGetHeight();
            }
            return this.mHeight;
        }

        @Override
        long getNativeContext() {
            this.throwISEIfImageIsInvalid();
            return this.mNativeBuffer;
        }

        @Override
        ImageWriter getOwner() {
            this.throwISEIfImageIsInvalid();
            return this.mOwner;
        }

        @Override
        public Image.Plane[] getPlanes() {
            this.throwISEIfImageIsInvalid();
            if (this.mPlanes == null) {
                this.mPlanes = this.nativeCreatePlanes(ImageUtils.getNumPlanesForFormat(this.getFormat()), this.getOwner().getFormat());
            }
            return (Image.Plane[])this.mPlanes.clone();
        }

        @Override
        public int getScalingMode() {
            this.throwISEIfImageIsInvalid();
            return this.mScalingMode;
        }

        @Override
        public long getTimestamp() {
            this.throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override
        public int getTransform() {
            this.throwISEIfImageIsInvalid();
            return this.mTransform;
        }

        @Override
        public int getWidth() {
            this.throwISEIfImageIsInvalid();
            if (this.mWidth == -1) {
                this.mWidth = this.nativeGetWidth();
            }
            return this.mWidth;
        }

        @Override
        boolean isAttachable() {
            this.throwISEIfImageIsInvalid();
            return false;
        }

        @Override
        public void setTimestamp(long l) {
            this.throwISEIfImageIsInvalid();
            this.mTimestamp = l;
        }

        private class SurfacePlane
        extends Image.Plane {
            private ByteBuffer mBuffer;
            private final int mPixelStride;
            private final int mRowStride;

            private SurfacePlane(int n, int n2, ByteBuffer byteBuffer) {
                this.mRowStride = n;
                this.mPixelStride = n2;
                this.mBuffer = byteBuffer;
                this.mBuffer.order(ByteOrder.nativeOrder());
            }

            private void clearBuffer() {
                ByteBuffer byteBuffer = this.mBuffer;
                if (byteBuffer == null) {
                    return;
                }
                if (byteBuffer.isDirect()) {
                    NioUtils.freeDirectBuffer((ByteBuffer)this.mBuffer);
                }
                this.mBuffer = null;
            }

            @Override
            public ByteBuffer getBuffer() {
                WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mBuffer;
            }

            @Override
            public int getPixelStride() {
                WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mPixelStride;
            }

            @Override
            public int getRowStride() {
                WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mRowStride;
            }
        }

    }

}

