/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.content.Context;
import android.content.Loader;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.print.PrintManager;
import android.printservice.PrintServiceInfo;
import com.android.internal.util.Preconditions;
import java.util.List;

public class PrintServicesLoader
extends Loader<List<PrintServiceInfo>> {
    private final Handler mHandler = new MyHandler();
    private PrintManager.PrintServicesChangeListener mListener;
    private final PrintManager mPrintManager;
    private final int mSelectionFlags;

    public PrintServicesLoader(PrintManager printManager, Context context, int n) {
        super(Preconditions.checkNotNull(context));
        this.mPrintManager = Preconditions.checkNotNull(printManager);
        this.mSelectionFlags = Preconditions.checkFlagsArgument(n, 3);
    }

    private void queueNewResult() {
        Message message = this.mHandler.obtainMessage(0);
        message.obj = this.mPrintManager.getPrintServices(this.mSelectionFlags);
        this.mHandler.sendMessage(message);
    }

    @Override
    protected void onForceLoad() {
        this.queueNewResult();
    }

    @Override
    protected void onReset() {
        this.onStopLoading();
    }

    @Override
    protected void onStartLoading() {
        this.mListener = new PrintManager.PrintServicesChangeListener(){

            @Override
            public void onPrintServicesChanged() {
                PrintServicesLoader.this.queueNewResult();
            }
        };
        this.mPrintManager.addPrintServicesChangeListener(this.mListener, null);
        this.deliverResult(this.mPrintManager.getPrintServices(this.mSelectionFlags));
    }

    @Override
    protected void onStopLoading() {
        PrintManager.PrintServicesChangeListener printServicesChangeListener = this.mListener;
        if (printServicesChangeListener != null) {
            this.mPrintManager.removePrintServicesChangeListener(printServicesChangeListener);
            this.mListener = null;
        }
        this.mHandler.removeMessages(0);
    }

    private class MyHandler
    extends Handler {
        public MyHandler() {
            super(PrintServicesLoader.this.getContext().getMainLooper());
        }

        @Override
        public void handleMessage(Message message) {
            if (PrintServicesLoader.this.isStarted()) {
                PrintServicesLoader.this.deliverResult((List)message.obj);
            }
        }
    }

}

