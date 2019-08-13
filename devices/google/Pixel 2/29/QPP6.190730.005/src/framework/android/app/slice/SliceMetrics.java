/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.content.Context;
import android.metrics.LogMaker;
import android.net.Uri;
import com.android.internal.logging.MetricsLogger;

public class SliceMetrics {
    private static final String TAG = "SliceMetrics";
    private LogMaker mLogMaker = new LogMaker(0);
    private MetricsLogger mMetricsLogger = new MetricsLogger();

    public SliceMetrics(Context context, Uri uri) {
        this.mLogMaker.addTaggedData(1402, uri.getAuthority());
        this.mLogMaker.addTaggedData(1403, uri.getPath());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void logHidden() {
        LogMaker logMaker = this.mLogMaker;
        synchronized (logMaker) {
            this.mLogMaker.setCategory(1401).setType(2);
            this.mMetricsLogger.write(this.mLogMaker);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void logTouch(int n, Uri uri) {
        LogMaker logMaker = this.mLogMaker;
        synchronized (logMaker) {
            this.mLogMaker.setCategory(1401).setType(4).addTaggedData(1404, uri.getAuthority()).addTaggedData(1405, uri.getPath());
            this.mMetricsLogger.write(this.mLogMaker);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void logVisible() {
        LogMaker logMaker = this.mLogMaker;
        synchronized (logMaker) {
            this.mLogMaker.setCategory(1401).setType(1);
            this.mMetricsLogger.write(this.mLogMaker);
            return;
        }
    }
}

