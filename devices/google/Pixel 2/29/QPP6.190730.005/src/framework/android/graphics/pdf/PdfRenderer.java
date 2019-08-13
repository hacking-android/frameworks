/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  dalvik.system.CloseGuard
 *  libcore.io.IoUtils
 */
package android.graphics.pdf;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.io.IoUtils;

public final class PdfRenderer
implements AutoCloseable {
    static final Object sPdfiumLock = new Object();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    @UnsupportedAppUsage
    private Page mCurrentPage;
    private ParcelFileDescriptor mInput;
    private long mNativeDocument;
    private final int mPageCount;
    private final Point mTempPoint = new Point();

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public PdfRenderer(ParcelFileDescriptor object) throws IOException {
        long l;
        if (object == null) throw new NullPointerException("input cannot be null");
        try {
            Os.lseek((FileDescriptor)((ParcelFileDescriptor)object).getFileDescriptor(), (long)0L, (int)OsConstants.SEEK_SET);
            l = Os.fstat((FileDescriptor)object.getFileDescriptor()).st_size;
            this.mInput = object;
            object = sPdfiumLock;
        }
        catch (ErrnoException errnoException) {
            throw new IllegalArgumentException("file descriptor not seekable");
        }
        this.mNativeDocument = PdfRenderer.nativeCreate(this.mInput.getFd(), l);
        this.mPageCount = PdfRenderer.nativeGetPageCount(this.mNativeDocument);
        // MONITOREXIT : object
        catch (Throwable throwable) {
            PdfRenderer.nativeClose(this.mNativeDocument);
            this.mNativeDocument = 0L;
            throw throwable;
        }
        this.mCloseGuard.open("close");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void doClose() {
        AutoCloseable autoCloseable = this.mCurrentPage;
        if (autoCloseable != null) {
            autoCloseable.close();
            this.mCurrentPage = null;
        }
        if (this.mNativeDocument != 0L) {
            Object object = sPdfiumLock;
            synchronized (object) {
                PdfRenderer.nativeClose(this.mNativeDocument);
            }
            this.mNativeDocument = 0L;
        }
        if ((autoCloseable = this.mInput) != null) {
            IoUtils.closeQuietly((AutoCloseable)autoCloseable);
            this.mInput = null;
        }
        this.mCloseGuard.close();
    }

    private static native void nativeClose(long var0);

    private static native void nativeClosePage(long var0);

    private static native long nativeCreate(int var0, long var1);

    private static native int nativeGetPageCount(long var0);

    private static native long nativeOpenPageAndGetSize(long var0, int var2, Point var3);

    private static native void nativeRenderPage(long var0, long var2, long var4, int var6, int var7, int var8, int var9, long var10, int var12);

    private static native boolean nativeScaleForPrinting(long var0);

    private void throwIfClosed() {
        if (this.mInput != null) {
            return;
        }
        throw new IllegalStateException("Already closed");
    }

    private void throwIfPageNotInDocument(int n) {
        if (n >= 0 && n < this.mPageCount) {
            return;
        }
        throw new IllegalArgumentException("Invalid page index");
    }

    private void throwIfPageOpened() {
        if (this.mCurrentPage == null) {
            return;
        }
        throw new IllegalStateException("Current page not closed");
    }

    @Override
    public void close() {
        this.throwIfClosed();
        this.throwIfPageOpened();
        this.doClose();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.doClose();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getPageCount() {
        this.throwIfClosed();
        return this.mPageCount;
    }

    public Page openPage(int n) {
        this.throwIfClosed();
        this.throwIfPageOpened();
        this.throwIfPageNotInDocument(n);
        this.mCurrentPage = new Page(n);
        return this.mCurrentPage;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean shouldScaleForPrinting() {
        this.throwIfClosed();
        Object object = sPdfiumLock;
        synchronized (object) {
            return PdfRenderer.nativeScaleForPrinting(this.mNativeDocument);
        }
    }

    public final class Page
    implements AutoCloseable {
        public static final int RENDER_MODE_FOR_DISPLAY = 1;
        public static final int RENDER_MODE_FOR_PRINT = 2;
        private final CloseGuard mCloseGuard = CloseGuard.get();
        private final int mHeight;
        private final int mIndex;
        private long mNativePage;
        private final int mWidth;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private Page(int n) {
            Point point = PdfRenderer.this.mTempPoint;
            Object object = sPdfiumLock;
            synchronized (object) {
                this.mNativePage = PdfRenderer.nativeOpenPageAndGetSize(PdfRenderer.this.mNativeDocument, n, point);
            }
            this.mIndex = n;
            this.mWidth = point.x;
            this.mHeight = point.y;
            this.mCloseGuard.open("close");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void doClose() {
            if (this.mNativePage != 0L) {
                Object object = sPdfiumLock;
                synchronized (object) {
                    PdfRenderer.nativeClosePage(this.mNativePage);
                }
                this.mNativePage = 0L;
            }
            this.mCloseGuard.close();
            PdfRenderer.this.mCurrentPage = null;
        }

        private void throwIfClosed() {
            if (this.mNativePage != 0L) {
                return;
            }
            throw new IllegalStateException("Already closed");
        }

        @Override
        public void close() {
            this.throwIfClosed();
            this.doClose();
        }

        protected void finalize() throws Throwable {
            try {
                if (this.mCloseGuard != null) {
                    this.mCloseGuard.warnIfOpen();
                }
                this.doClose();
                return;
            }
            finally {
                super.finalize();
            }
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public int getWidth() {
            return this.mWidth;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void render(Bitmap bitmap, Rect object, Matrix matrix, int n) {
            void var4_5;
            Object object2;
            Matrix matrix2;
            if (this.mNativePage == 0L) {
                throw new NullPointerException();
            }
            if ((bitmap = Preconditions.checkNotNull(bitmap, "bitmap null")).getConfig() != Bitmap.Config.ARGB_8888) {
                throw new IllegalArgumentException("Unsupported pixel format");
            }
            if (object2 != null && (((Rect)object2).left < 0 || ((Rect)object2).top < 0 || ((Rect)object2).right > bitmap.getWidth() || ((Rect)object2).bottom > bitmap.getHeight())) {
                throw new IllegalArgumentException("destBounds not in destination");
            }
            if (matrix2 != null && !matrix2.isAffine()) {
                throw new IllegalArgumentException("transform not affine");
            }
            if (var4_5 != 2 && var4_5 != true) {
                throw new IllegalArgumentException("Unsupported render mode");
            }
            if (var4_5 == 2 && var4_5 == true) {
                throw new IllegalArgumentException("Only single render mode supported");
            }
            int n2 = 0;
            int n3 = object2 != null ? ((Rect)object2).left : 0;
            if (object2 != null) {
                n2 = ((Rect)object2).top;
            }
            int n4 = object2 != null ? ((Rect)object2).right : bitmap.getWidth();
            int n5 = object2 != null ? ((Rect)object2).bottom : bitmap.getHeight();
            if (matrix2 == null) {
                matrix2 = new Matrix();
                matrix2.postScale((float)(n4 - n3) / (float)this.getWidth(), (float)(n5 - n2) / (float)this.getHeight());
                matrix2.postTranslate(n3, n2);
            }
            long l = matrix2.native_instance;
            object2 = sPdfiumLock;
            synchronized (object2) {
                long l2 = PdfRenderer.this.mNativeDocument;
                long l3 = this.mNativePage;
                long l4 = bitmap.getNativeInstance();
                try {
                    PdfRenderer.nativeRenderPage(l2, l3, l4, n3, n2, n4, n5, l, (int)var4_5);
                    return;
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RenderMode {
    }

}

