/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.graphics.pdf;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import dalvik.system.CloseGuard;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PdfDocument {
    private final byte[] mChunk = new byte[4096];
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private Page mCurrentPage;
    private long mNativeDocument = this.nativeCreateDocument();
    private final List<PageInfo> mPages = new ArrayList<PageInfo>();

    public PdfDocument() {
        this.mCloseGuard.open("close");
    }

    private void dispose() {
        long l = this.mNativeDocument;
        if (l != 0L) {
            this.nativeClose(l);
            this.mCloseGuard.close();
            this.mNativeDocument = 0L;
        }
    }

    private native void nativeClose(long var1);

    private native long nativeCreateDocument();

    private native void nativeFinishPage(long var1);

    private static native long nativeStartPage(long var0, int var2, int var3, int var4, int var5, int var6, int var7);

    private native void nativeWriteTo(long var1, OutputStream var3, byte[] var4);

    private void throwIfClosed() {
        if (this.mNativeDocument != 0L) {
            return;
        }
        throw new IllegalStateException("document is closed!");
    }

    private void throwIfCurrentPageNotFinished() {
        if (this.mCurrentPage == null) {
            return;
        }
        throw new IllegalStateException("Current page not finished!");
    }

    public void close() {
        this.throwIfCurrentPageNotFinished();
        this.dispose();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.dispose();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public void finishPage(Page page) {
        this.throwIfClosed();
        if (page != null) {
            if (page == this.mCurrentPage) {
                if (!page.isFinished()) {
                    this.mPages.add(page.getInfo());
                    this.mCurrentPage = null;
                    this.nativeFinishPage(this.mNativeDocument);
                    page.finish();
                    return;
                }
                throw new IllegalStateException("page already finished");
            }
            throw new IllegalStateException("invalid page");
        }
        throw new IllegalArgumentException("page cannot be null");
    }

    public List<PageInfo> getPages() {
        return Collections.unmodifiableList(this.mPages);
    }

    public Page startPage(PageInfo pageInfo) {
        this.throwIfClosed();
        this.throwIfCurrentPageNotFinished();
        if (pageInfo != null) {
            this.mCurrentPage = new Page(new PdfCanvas(PdfDocument.nativeStartPage(this.mNativeDocument, pageInfo.mPageWidth, pageInfo.mPageHeight, PageInfo.access$200((PageInfo)pageInfo).left, PageInfo.access$200((PageInfo)pageInfo).top, PageInfo.access$200((PageInfo)pageInfo).right, PageInfo.access$200((PageInfo)pageInfo).bottom)), pageInfo);
            return this.mCurrentPage;
        }
        throw new IllegalArgumentException("page cannot be null");
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.throwIfClosed();
        this.throwIfCurrentPageNotFinished();
        if (outputStream != null) {
            this.nativeWriteTo(this.mNativeDocument, outputStream, this.mChunk);
            return;
        }
        throw new IllegalArgumentException("out cannot be null!");
    }

    public static final class Page {
        private Canvas mCanvas;
        private final PageInfo mPageInfo;

        private Page(Canvas canvas, PageInfo pageInfo) {
            this.mCanvas = canvas;
            this.mPageInfo = pageInfo;
        }

        private void finish() {
            Canvas canvas = this.mCanvas;
            if (canvas != null) {
                canvas.release();
                this.mCanvas = null;
            }
        }

        public Canvas getCanvas() {
            return this.mCanvas;
        }

        public PageInfo getInfo() {
            return this.mPageInfo;
        }

        boolean isFinished() {
            boolean bl = this.mCanvas == null;
            return bl;
        }
    }

    public static final class PageInfo {
        private Rect mContentRect;
        private int mPageHeight;
        private int mPageNumber;
        private int mPageWidth;

        private PageInfo() {
        }

        public Rect getContentRect() {
            return this.mContentRect;
        }

        public int getPageHeight() {
            return this.mPageHeight;
        }

        public int getPageNumber() {
            return this.mPageNumber;
        }

        public int getPageWidth() {
            return this.mPageWidth;
        }

        public static final class Builder {
            private final PageInfo mPageInfo = new PageInfo();

            public Builder(int n, int n2, int n3) {
                if (n > 0) {
                    if (n2 > 0) {
                        if (n3 >= 0) {
                            this.mPageInfo.mPageWidth = n;
                            this.mPageInfo.mPageHeight = n2;
                            this.mPageInfo.mPageNumber = n3;
                            return;
                        }
                        throw new IllegalArgumentException("pageNumber must be non negative");
                    }
                    throw new IllegalArgumentException("page width must be positive");
                }
                throw new IllegalArgumentException("page width must be positive");
            }

            public PageInfo create() {
                if (this.mPageInfo.mContentRect == null) {
                    PageInfo pageInfo = this.mPageInfo;
                    pageInfo.mContentRect = new Rect(0, 0, pageInfo.mPageWidth, this.mPageInfo.mPageHeight);
                }
                return this.mPageInfo;
            }

            public Builder setContentRect(Rect rect) {
                if (rect != null && (rect.left < 0 || rect.top < 0 || rect.right > this.mPageInfo.mPageWidth || rect.bottom > this.mPageInfo.mPageHeight)) {
                    throw new IllegalArgumentException("contentRect does not fit the page");
                }
                this.mPageInfo.mContentRect = rect;
                return this;
            }
        }

    }

    private final class PdfCanvas
    extends Canvas {
        public PdfCanvas(long l) {
            super(l);
        }

        @Override
        public void setBitmap(Bitmap bitmap) {
            throw new UnsupportedOperationException();
        }
    }

}

