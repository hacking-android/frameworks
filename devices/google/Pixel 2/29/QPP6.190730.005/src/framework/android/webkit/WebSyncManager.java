/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Handler;
import android.webkit.WebViewDatabase;

@Deprecated
abstract class WebSyncManager
implements Runnable {
    protected static final String LOGTAG = "websync";
    protected WebViewDatabase mDataBase;
    @UnsupportedAppUsage
    protected Handler mHandler;

    protected WebSyncManager(Context context, String string2) {
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("doesn't implement Cloneable");
    }

    protected void onSyncInit() {
    }

    public void resetSync() {
    }

    @Override
    public void run() {
    }

    public void startSync() {
    }

    public void stopSync() {
    }

    public void sync() {
    }

    @UnsupportedAppUsage
    abstract void syncFromRamToFlash();
}

