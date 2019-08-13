/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;

public class JsResult {
    @UnsupportedAppUsage
    private final ResultReceiver mReceiver;
    private boolean mResult;

    @SystemApi
    public JsResult(ResultReceiver resultReceiver) {
        this.mReceiver = resultReceiver;
    }

    private final void wakeUp() {
        this.mReceiver.onJsResultComplete(this);
    }

    public final void cancel() {
        this.mResult = false;
        this.wakeUp();
    }

    public final void confirm() {
        this.mResult = true;
        this.wakeUp();
    }

    @SystemApi
    public final boolean getResult() {
        return this.mResult;
    }

    @SystemApi
    public static interface ResultReceiver {
        public void onJsResultComplete(JsResult var1);
    }

}

