/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentInfo;

public abstract class PrintDocumentAdapter {
    public static final String EXTRA_PRINT_PREVIEW = "EXTRA_PRINT_PREVIEW";

    public void onFinish() {
    }

    public abstract void onLayout(PrintAttributes var1, PrintAttributes var2, CancellationSignal var3, LayoutResultCallback var4, Bundle var5);

    public void onStart() {
    }

    public abstract void onWrite(PageRange[] var1, ParcelFileDescriptor var2, CancellationSignal var3, WriteResultCallback var4);

    public static abstract class LayoutResultCallback {
        public void onLayoutCancelled() {
        }

        public void onLayoutFailed(CharSequence charSequence) {
        }

        public void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean bl) {
        }
    }

    public static abstract class WriteResultCallback {
        public void onWriteCancelled() {
        }

        public void onWriteFailed(CharSequence charSequence) {
        }

        public void onWriteFinished(PageRange[] arrpageRange) {
        }
    }

}

