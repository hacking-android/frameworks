/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 *  java.nio.NioUtils
 */
package android.media;

import android.hardware.HardwareBuffer;
import android.media.Image;
import android.media.ImageUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import dalvik.system.VMRuntime;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageReader
implements AutoCloseable {
    private static final int ACQUIRE_MAX_IMAGES = 2;
    private static final int ACQUIRE_NO_BUFS = 1;
    private static final int ACQUIRE_SUCCESS = 0;
    private List<Image> mAcquiredImages = new CopyOnWriteArrayList<Image>();
    private final Object mCloseLock = new Object();
    private int mEstimatedNativeAllocBytes;
    private final int mFormat;
    private final int mHeight;
    private boolean mIsReaderValid = false;
    private OnImageAvailableListener mListener;
    private ListenerHandler mListenerHandler;
    private final Object mListenerLock = new Object();
    private final int mMaxImages;
    private long mNativeContext;
    private final int mNumPlanes;
    private final Surface mSurface;
    private final int mWidth;

    static {
        System.loadLibrary("media_jni");
        ImageReader.nativeClassInit();
    }

    protected ImageReader(int n, int n2, int n3, int n4, long l) {
        this.mWidth = n;
        this.mHeight = n2;
        this.mFormat = n3;
        this.mMaxImages = n4;
        if (n >= 1 && n2 >= 1) {
            if (this.mMaxImages >= 1) {
                if (n3 != 17) {
                    this.mNumPlanes = ImageUtils.getNumPlanesForFormat(this.mFormat);
                    this.nativeInit(new WeakReference<ImageReader>(this), n, n2, n3, n4, l);
                    this.mSurface = this.nativeGetSurface();
                    this.mIsReaderValid = true;
                    this.mEstimatedNativeAllocBytes = ImageUtils.getEstimatedNativeAllocBytes(n, n2, n3, 1);
                    VMRuntime.getRuntime().registerNativeAllocation(this.mEstimatedNativeAllocBytes);
                    return;
                }
                throw new IllegalArgumentException("NV21 format is not supported");
            }
            throw new IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        throw new IllegalArgumentException("The image dimensions must be positive");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int acquireNextSurfaceImage(SurfaceImage object) {
        Object object2 = this.mCloseLock;
        synchronized (object2) {
            int n = 1;
            if (this.mIsReaderValid) {
                n = this.nativeImageSetup((Image)object);
            }
            if (n != 0) {
                if (n != 1 && n != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown nativeImageSetup return code ");
                    ((StringBuilder)object).append(n);
                    AssertionError assertionError = new AssertionError((Object)((StringBuilder)object).toString());
                    throw assertionError;
                }
            } else {
                ((SurfaceImage)object).mIsImageValid = true;
            }
            if (n == 0) {
                this.mAcquiredImages.add((Image)object);
            }
            return n;
        }
    }

    private boolean isImageOwnedbyMe(Image image) {
        boolean bl = image instanceof SurfaceImage;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (((SurfaceImage)image).getReader() == this) {
            bl2 = true;
        }
        return bl2;
    }

    private static native void nativeClassInit();

    private synchronized native void nativeClose();

    private synchronized native int nativeDetachImage(Image var1);

    private synchronized native void nativeDiscardFreeBuffers();

    private synchronized native Surface nativeGetSurface();

    private synchronized native int nativeImageSetup(Image var1);

    private synchronized native void nativeInit(Object var1, int var2, int var3, int var4, int var5, long var6);

    private synchronized native void nativeReleaseImage(Image var1);

    public static ImageReader newInstance(int n, int n2, int n3, int n4) {
        long l = n3 == 34 ? 0L : 3L;
        return new ImageReader(n, n2, n3, n4, l);
    }

    public static ImageReader newInstance(int n, int n2, int n3, int n4, long l) {
        return new ImageReader(n, n2, n3, n4, l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void postEventFromNative(Object object) {
        Object object2 = (ImageReader)((WeakReference)object).get();
        if (object2 == null) {
            return;
        }
        object = ((ImageReader)object2).mListenerLock;
        // MONITORENTER : object
        object2 = ((ImageReader)object2).mListenerHandler;
        // MONITOREXIT : object
        if (object2 == null) return;
        ((Handler)object2).sendEmptyMessage(0);
    }

    private void releaseImage(Image image) {
        if (image instanceof SurfaceImage) {
            SurfaceImage surfaceImage = (SurfaceImage)image;
            if (!surfaceImage.mIsImageValid) {
                return;
            }
            if (surfaceImage.getReader() == this && this.mAcquiredImages.contains(image)) {
                surfaceImage.clearSurfacePlanes();
                this.nativeReleaseImage(image);
                surfaceImage.mIsImageValid = false;
                this.mAcquiredImages.remove(image);
                return;
            }
            throw new IllegalArgumentException("This image was not produced by this ImageReader");
        }
        throw new IllegalArgumentException("This image was not produced by an ImageReader");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Image acquireLatestImage() {
        Image image;
        Image image2 = image = this.acquireNextImage();
        if (image == null) {
            return null;
        }
        do {
            image = this.acquireNextImageNoThrowISE();
            if (image != null) break block5;
            if (!false) return image2;
            break;
        } while (true);
        catch (Throwable throwable) {
            image2.close();
            throw throwable;
        }
        {
            block5 : {
                throw new NullPointerException();
            }
            image2.close();
            image2 = image;
            continue;
        }
    }

    public Image acquireNextImage() {
        Object object = new SurfaceImage(this.mFormat);
        int n = this.acquireNextSurfaceImage((SurfaceImage)object);
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown nativeImageSetup return code ");
                    ((StringBuilder)object).append(n);
                    throw new AssertionError((Object)((StringBuilder)object).toString());
                }
                throw new IllegalStateException(String.format("maxImages (%d) has already been acquired, call #close before acquiring more.", this.mMaxImages));
            }
            return null;
        }
        return object;
    }

    public Image acquireNextImageNoThrowISE() {
        SurfaceImage surfaceImage = new SurfaceImage(this.mFormat);
        if (this.acquireNextSurfaceImage(surfaceImage) != 0) {
            surfaceImage = null;
        }
        return surfaceImage;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        this.setOnImageAvailableListener(null, null);
        Object object = this.mSurface;
        if (object != null) {
            ((Surface)object).release();
        }
        object = this.mCloseLock;
        synchronized (object) {
            this.mIsReaderValid = false;
            Iterator<Image> iterator = this.mAcquiredImages.iterator();
            while (iterator.hasNext()) {
                iterator.next().close();
            }
            this.mAcquiredImages.clear();
            this.nativeClose();
            if (this.mEstimatedNativeAllocBytes > 0) {
                VMRuntime.getRuntime().registerNativeFree(this.mEstimatedNativeAllocBytes);
                this.mEstimatedNativeAllocBytes = 0;
            }
            return;
        }
    }

    void detachImage(Image image) {
        if (image != null) {
            if (this.isImageOwnedbyMe(image)) {
                SurfaceImage surfaceImage = (SurfaceImage)image;
                surfaceImage.throwISEIfImageIsInvalid();
                if (!surfaceImage.isAttachable()) {
                    this.nativeDetachImage(image);
                    surfaceImage.clearSurfacePlanes();
                    surfaceImage.mPlanes = null;
                    surfaceImage.setDetached(true);
                    return;
                }
                throw new IllegalStateException("Image was already detached from this ImageReader");
            }
            throw new IllegalArgumentException("Trying to detach an image that is not owned by this ImageReader");
        }
        throw new IllegalArgumentException("input image must not be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void discardFreeBuffers() {
        Object object = this.mCloseLock;
        synchronized (object) {
            this.nativeDiscardFreeBuffers();
            return;
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
        return this.mHeight;
    }

    public int getImageFormat() {
        return this.mFormat;
    }

    public int getMaxImages() {
        return this.mMaxImages;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public int getWidth() {
        return this.mWidth;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnImageAvailableListener(OnImageAvailableListener object, Handler object2) {
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
            Object object2 = ImageReader.this.mListenerLock;
            // MONITORENTER : object2
            object = ImageReader.this.mListener;
            // MONITOREXIT : object2
            object2 = ImageReader.this.mCloseLock;
            // MONITORENTER : object2
            boolean bl = ImageReader.this.mIsReaderValid;
            // MONITOREXIT : object2
            if (object == null) return;
            if (!bl) return;
            object.onImageAvailable(ImageReader.this);
        }
    }

    public static interface OnImageAvailableListener {
        public void onImageAvailable(ImageReader var1);
    }

    private class SurfaceImage
    extends Image {
        private int mFormat = 0;
        private AtomicBoolean mIsDetached = new AtomicBoolean(false);
        private long mNativeBuffer;
        private SurfacePlane[] mPlanes;
        private int mScalingMode;
        private long mTimestamp;
        private int mTransform;

        public SurfaceImage(int n) {
            this.mFormat = n;
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

        private synchronized native int nativeGetFormat(int var1);

        private synchronized native HardwareBuffer nativeGetHardwareBuffer();

        private synchronized native int nativeGetHeight();

        private synchronized native int nativeGetWidth();

        private void setDetached(boolean bl) {
            this.throwISEIfImageIsInvalid();
            this.mIsDetached.getAndSet(bl);
        }

        @Override
        public void close() {
            ImageReader.this.releaseImage(this);
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
            int n = ImageReader.this.getImageFormat();
            if (n != 34) {
                n = this.nativeGetFormat(n);
            }
            this.mFormat = n;
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
            int n = this.getFormat();
            n = n != 36 && n != 1212500294 && n != 1768253795 && n != 256 && n != 257 ? this.nativeGetHeight() : ImageReader.this.getHeight();
            return n;
        }

        @Override
        long getNativeContext() {
            this.throwISEIfImageIsInvalid();
            return this.mNativeBuffer;
        }

        @Override
        ImageReader getOwner() {
            this.throwISEIfImageIsInvalid();
            return ImageReader.this;
        }

        @Override
        public Image.Plane[] getPlanes() {
            this.throwISEIfImageIsInvalid();
            if (this.mPlanes == null) {
                this.mPlanes = this.nativeCreatePlanes(ImageReader.this.mNumPlanes, ImageReader.this.mFormat);
            }
            return (Image.Plane[])this.mPlanes.clone();
        }

        public ImageReader getReader() {
            return ImageReader.this;
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
            int n = this.getFormat();
            n = n != 36 && n != 1212500294 && n != 1768253795 && n != 256 && n != 257 ? this.nativeGetWidth() : ImageReader.this.getWidth();
            return n;
        }

        @Override
        boolean isAttachable() {
            this.throwISEIfImageIsInvalid();
            return this.mIsDetached.get();
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
                SurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mBuffer;
            }

            @Override
            public int getPixelStride() {
                SurfaceImage.this.throwISEIfImageIsInvalid();
                if (ImageReader.this.mFormat != 36) {
                    return this.mPixelStride;
                }
                throw new UnsupportedOperationException("getPixelStride is not supported for RAW_PRIVATE plane");
            }

            @Override
            public int getRowStride() {
                SurfaceImage.this.throwISEIfImageIsInvalid();
                if (ImageReader.this.mFormat != 36) {
                    return this.mRowStride;
                }
                throw new UnsupportedOperationException("getRowStride is not supported for RAW_PRIVATE plane");
            }
        }

    }

}

