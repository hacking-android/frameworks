/*
 * Decompiled with CFR 0.145.
 */
package android.print.pdf;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.print.PrintAttributes;

public class PrintedPdfDocument
extends PdfDocument {
    private static final int MILS_PER_INCH = 1000;
    private static final int POINTS_IN_INCH = 72;
    private final Rect mContentRect;
    private final int mPageHeight;
    private final int mPageWidth;

    public PrintedPdfDocument(Context object, PrintAttributes printAttributes) {
        object = printAttributes.getMediaSize();
        this.mPageWidth = (int)((float)((PrintAttributes.MediaSize)object).getWidthMils() / 1000.0f * 72.0f);
        this.mPageHeight = (int)((float)((PrintAttributes.MediaSize)object).getHeightMils() / 1000.0f * 72.0f);
        object = printAttributes.getMinMargins();
        int n = (int)((float)((PrintAttributes.Margins)object).getLeftMils() / 1000.0f * 72.0f);
        int n2 = (int)((float)((PrintAttributes.Margins)object).getTopMils() / 1000.0f * 72.0f);
        int n3 = (int)((float)((PrintAttributes.Margins)object).getRightMils() / 1000.0f * 72.0f);
        int n4 = (int)((float)((PrintAttributes.Margins)object).getBottomMils() / 1000.0f * 72.0f);
        this.mContentRect = new Rect(n, n2, this.mPageWidth - n3, this.mPageHeight - n4);
    }

    public Rect getPageContentRect() {
        return this.mContentRect;
    }

    public int getPageHeight() {
        return this.mPageHeight;
    }

    public int getPageWidth() {
        return this.mPageWidth;
    }

    public PdfDocument.Page startPage(int n) {
        return this.startPage(new PdfDocument.PageInfo.Builder(this.mPageWidth, this.mPageHeight, n).setContentRect(this.mContentRect).create());
    }
}

