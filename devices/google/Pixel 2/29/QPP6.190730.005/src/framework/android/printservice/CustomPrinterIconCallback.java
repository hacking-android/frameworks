/*
 * Decompiled with CFR 0.145.
 */
package android.printservice;

import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.print.PrinterId;
import android.printservice.IPrintServiceClient;
import android.util.Log;

public final class CustomPrinterIconCallback {
    private static final String LOG_TAG = "CustomPrinterIconCB";
    private final IPrintServiceClient mObserver;
    private final PrinterId mPrinterId;

    CustomPrinterIconCallback(PrinterId printerId, IPrintServiceClient iPrintServiceClient) {
        this.mPrinterId = printerId;
        this.mObserver = iPrintServiceClient;
    }

    public boolean onCustomPrinterIconLoaded(Icon icon) {
        try {
            this.mObserver.onCustomPrinterIconLoaded(this.mPrinterId, icon);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Could not update icon", remoteException);
            return false;
        }
    }
}

