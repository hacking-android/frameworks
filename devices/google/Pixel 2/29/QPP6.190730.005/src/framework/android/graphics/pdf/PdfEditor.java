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

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import libcore.io.IoUtils;

public final class PdfEditor {
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private ParcelFileDescriptor mInput;
    private long mNativeDocument;
    private int mPageCount;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public PdfEditor(ParcelFileDescriptor object) throws IOException {
        long l;
        if (object == null) throw new NullPointerException("input cannot be null");
        try {
            Os.lseek((FileDescriptor)((ParcelFileDescriptor)object).getFileDescriptor(), (long)0L, (int)OsConstants.SEEK_SET);
            l = Os.fstat((FileDescriptor)object.getFileDescriptor()).st_size;
            this.mInput = object;
            object = PdfRenderer.sPdfiumLock;
        }
        catch (ErrnoException errnoException) {
            throw new IllegalArgumentException("file descriptor not seekable");
        }
        this.mNativeDocument = PdfEditor.nativeOpen(this.mInput.getFd(), l);
        this.mPageCount = PdfEditor.nativeGetPageCount(this.mNativeDocument);
        // MONITOREXIT : object
        catch (Throwable throwable) {
            PdfEditor.nativeClose(this.mNativeDocument);
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
    private void doClose() {
        ParcelFileDescriptor parcelFileDescriptor;
        if (this.mNativeDocument != 0L) {
            Object object = PdfRenderer.sPdfiumLock;
            synchronized (object) {
                PdfEditor.nativeClose(this.mNativeDocument);
            }
            this.mNativeDocument = 0L;
        }
        if ((parcelFileDescriptor = this.mInput) != null) {
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            this.mInput = null;
        }
        this.mCloseGuard.close();
    }

    private static native void nativeClose(long var0);

    private static native int nativeGetPageCount(long var0);

    private static native boolean nativeGetPageCropBox(long var0, int var2, Rect var3);

    private static native boolean nativeGetPageMediaBox(long var0, int var2, Rect var3);

    private static native void nativeGetPageSize(long var0, int var2, Point var3);

    private static native long nativeOpen(int var0, long var1);

    private static native int nativeRemovePage(long var0, int var2);

    private static native boolean nativeScaleForPrinting(long var0);

    private static native void nativeSetPageCropBox(long var0, int var2, Rect var3);

    private static native void nativeSetPageMediaBox(long var0, int var2, Rect var3);

    private static native void nativeSetTransformAndClip(long var0, int var2, long var3, int var5, int var6, int var7, int var8);

    private static native void nativeWrite(long var0, int var2);

    private void throwIfClosed() {
        if (this.mInput != null) {
            return;
        }
        throw new IllegalStateException("Already closed");
    }

    private void throwIfCropBoxNull(Rect rect) {
        if (rect != null) {
            return;
        }
        throw new NullPointerException("cropBox cannot be null");
    }

    private void throwIfMediaBoxNull(Rect rect) {
        if (rect != null) {
            return;
        }
        throw new NullPointerException("mediaBox cannot be null");
    }

    private void throwIfNotNullAndNotAfine(Matrix matrix) {
        if (matrix != null && !matrix.isAffine()) {
            throw new IllegalStateException("Matrix must be afine");
        }
    }

    private void throwIfOutCropBoxNull(Rect rect) {
        if (rect != null) {
            return;
        }
        throw new NullPointerException("outCropBox cannot be null");
    }

    private void throwIfOutMediaBoxNull(Rect rect) {
        if (rect != null) {
            return;
        }
        throw new NullPointerException("outMediaBox cannot be null");
    }

    private void throwIfOutSizeNull(Point point) {
        if (point != null) {
            return;
        }
        throw new NullPointerException("outSize cannot be null");
    }

    private void throwIfPageNotInDocument(int n) {
        if (n >= 0 && n < this.mPageCount) {
            return;
        }
        throw new IllegalArgumentException("Invalid page index");
    }

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

    public int getPageCount() {
        this.throwIfClosed();
        return this.mPageCount;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getPageCropBox(int n, Rect rect) {
        this.throwIfClosed();
        this.throwIfOutCropBoxNull(rect);
        this.throwIfPageNotInDocument(n);
        Object object = PdfRenderer.sPdfiumLock;
        synchronized (object) {
            return PdfEditor.nativeGetPageCropBox(this.mNativeDocument, n, rect);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getPageMediaBox(int n, Rect rect) {
        this.throwIfClosed();
        this.throwIfOutMediaBoxNull(rect);
        this.throwIfPageNotInDocument(n);
        Object object = PdfRenderer.sPdfiumLock;
        synchronized (object) {
            return PdfEditor.nativeGetPageMediaBox(this.mNativeDocument, n, rect);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getPageSize(int n, Point point) {
        this.throwIfClosed();
        this.throwIfOutSizeNull(point);
        this.throwIfPageNotInDocument(n);
        Object object = PdfRenderer.sPdfiumLock;
        synchronized (object) {
            PdfEditor.nativeGetPageSize(this.mNativeDocument, n, point);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removePage(int n) {
        this.throwIfClosed();
        this.throwIfPageNotInDocument(n);
        Object object = PdfRenderer.sPdfiumLock;
        synchronized (object) {
            this.mPageCount = PdfEditor.nativeRemovePage(this.mNativeDocument, n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPageCropBox(int n, Rect rect) {
        this.throwIfClosed();
        this.throwIfCropBoxNull(rect);
        this.throwIfPageNotInDocument(n);
        Object object = PdfRenderer.sPdfiumLock;
        synchronized (object) {
            PdfEditor.nativeSetPageCropBox(this.mNativeDocument, n, rect);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPageMediaBox(int n, Rect rect) {
        this.throwIfClosed();
        this.throwIfMediaBoxNull(rect);
        this.throwIfPageNotInDocument(n);
        Object object = PdfRenderer.sPdfiumLock;
        synchronized (object) {
            PdfEditor.nativeSetPageMediaBox(this.mNativeDocument, n, rect);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTransformAndClip(int n, Matrix object, Rect parcelable) {
        this.throwIfClosed();
        this.throwIfPageNotInDocument(n);
        this.throwIfNotNullAndNotAfine((Matrix)object);
        Matrix matrix = object;
        if (object == null) {
            matrix = Matrix.IDENTITY_MATRIX;
        }
        if (parcelable == null) {
            parcelable = new Point();
            this.getPageSize(n, (Point)parcelable);
            object = PdfRenderer.sPdfiumLock;
            synchronized (object) {
                PdfEditor.nativeSetTransformAndClip(this.mNativeDocument, n, matrix.native_instance, 0, 0, ((Point)parcelable).x, ((Point)parcelable).y);
                return;
            }
        }
        object = PdfRenderer.sPdfiumLock;
        synchronized (object) {
            PdfEditor.nativeSetTransformAndClip(this.mNativeDocument, n, matrix.native_instance, parcelable.left, parcelable.top, parcelable.right, parcelable.bottom);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean shouldScaleForPrinting() {
        this.throwIfClosed();
        Object object = PdfRenderer.sPdfiumLock;
        synchronized (object) {
            return PdfEditor.nativeScaleForPrinting(this.mNativeDocument);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void write(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        try {
            this.throwIfClosed();
            Object object = PdfRenderer.sPdfiumLock;
            // MONITORENTER : object
        }
        catch (Throwable throwable) {
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            throw throwable;
        }
        PdfEditor.nativeWrite(this.mNativeDocument, parcelFileDescriptor.getFd());
        // MONITOREXIT : object
        IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
    }
}

