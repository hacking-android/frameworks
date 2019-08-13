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
import android.printservice.recommendation.RecommendationInfo;
import com.android.internal.util.Preconditions;
import java.util.List;

public class PrintServiceRecommendationsLoader
extends Loader<List<RecommendationInfo>> {
    private final Handler mHandler = new MyHandler();
    private PrintManager.PrintServiceRecommendationsChangeListener mListener;
    private final PrintManager mPrintManager;

    public PrintServiceRecommendationsLoader(PrintManager printManager, Context context) {
        super(Preconditions.checkNotNull(context));
        this.mPrintManager = Preconditions.checkNotNull(printManager);
    }

    private void queueNewResult() {
        Message message = this.mHandler.obtainMessage(0);
        message.obj = this.mPrintManager.getPrintServiceRecommendations();
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
        this.mListener = new PrintManager.PrintServiceRecommendationsChangeListener(){

            @Override
            public void onPrintServiceRecommendationsChanged() {
                PrintServiceRecommendationsLoader.this.queueNewResult();
            }
        };
        this.mPrintManager.addPrintServiceRecommendationsChangeListener(this.mListener, null);
        this.deliverResult(this.mPrintManager.getPrintServiceRecommendations());
    }

    @Override
    protected void onStopLoading() {
        PrintManager.PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener = this.mListener;
        if (printServiceRecommendationsChangeListener != null) {
            this.mPrintManager.removePrintServiceRecommendationsChangeListener(printServiceRecommendationsChangeListener);
            this.mListener = null;
        }
        this.mHandler.removeMessages(0);
    }

    private class MyHandler
    extends Handler {
        public MyHandler() {
            super(PrintServiceRecommendationsLoader.this.getContext().getMainLooper());
        }

        @Override
        public void handleMessage(Message message) {
            if (PrintServiceRecommendationsLoader.this.isStarted()) {
                PrintServiceRecommendationsLoader.this.deliverResult((List)message.obj);
            }
        }
    }

}

