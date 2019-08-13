/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.FileUtils;
import android.os.OperationCanceledException;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public class PrintFileDocumentAdapter
extends PrintDocumentAdapter {
    private static final String LOG_TAG = "PrintedFileDocAdapter";
    private final Context mContext;
    private final PrintDocumentInfo mDocumentInfo;
    private final File mFile;
    private WriteFileAsyncTask mWriteFileAsyncTask;

    public PrintFileDocumentAdapter(Context context, File file, PrintDocumentInfo printDocumentInfo) {
        if (file != null) {
            if (printDocumentInfo != null) {
                this.mContext = context;
                this.mFile = file;
                this.mDocumentInfo = printDocumentInfo;
                return;
            }
            throw new IllegalArgumentException("documentInfo cannot be null!");
        }
        throw new IllegalArgumentException("File cannot be null!");
    }

    @Override
    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
        layoutResultCallback.onLayoutFinished(this.mDocumentInfo, false);
    }

    @Override
    public void onWrite(PageRange[] arrpageRange, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        this.mWriteFileAsyncTask = new WriteFileAsyncTask(parcelFileDescriptor, cancellationSignal, writeResultCallback);
        this.mWriteFileAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    private final class WriteFileAsyncTask
    extends AsyncTask<Void, Void, Void> {
        private final CancellationSignal mCancellationSignal;
        private final ParcelFileDescriptor mDestination;
        private final PrintDocumentAdapter.WriteResultCallback mResultCallback;

        private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
            if (throwable != null) {
                try {
                    autoCloseable.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                autoCloseable.close();
            }
        }

        public WriteFileAsyncTask(ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            this.mDestination = parcelFileDescriptor;
            this.mResultCallback = writeResultCallback;
            this.mCancellationSignal = cancellationSignal;
            this.mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){

                @Override
                public void onCancel() {
                    WriteFileAsyncTask.this.cancel(true);
                }
            });
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        protected Void doInBackground(Void ... object) {
            object = new FileInputStream(PrintFileDocumentAdapter.this.mFile);
            FileOutputStream fileOutputStream = new FileOutputStream(this.mDestination.getFileDescriptor());
            FileUtils.copy((InputStream)object, fileOutputStream, this.mCancellationSignal, null, null);
            WriteFileAsyncTask.$closeResource(null, fileOutputStream);
            WriteFileAsyncTask.$closeResource(null, (AutoCloseable)object);
            return null;
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        WriteFileAsyncTask.$closeResource(throwable, fileOutputStream);
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        try {
                            throw throwable3;
                        }
                        catch (Throwable throwable4) {
                            try {
                                WriteFileAsyncTask.$closeResource(throwable3, (AutoCloseable)object);
                                throw throwable4;
                            }
                            catch (IOException iOException) {
                                Log.e(PrintFileDocumentAdapter.LOG_TAG, "Error writing data!", iOException);
                                this.mResultCallback.onWriteFailed(PrintFileDocumentAdapter.this.mContext.getString(17041320));
                                return null;
                            }
                            catch (OperationCanceledException operationCanceledException) {
                                // empty catch block
                            }
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onCancelled(Void void_) {
            this.mResultCallback.onWriteFailed(PrintFileDocumentAdapter.this.mContext.getString(17041319));
        }

        @Override
        protected void onPostExecute(Void void_) {
            this.mResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        }

    }

}

