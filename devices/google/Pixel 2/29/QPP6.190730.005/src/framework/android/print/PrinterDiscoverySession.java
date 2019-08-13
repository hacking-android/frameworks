/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.print.IPrintManager;
import android.print.IPrinterDiscoveryObserver;
import android.print.PrinterId;
import android.print.PrinterInfo;
import android.util.ArrayMap;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PrinterDiscoverySession {
    private static final String LOG_TAG = "PrinterDiscoverySession";
    private static final int MSG_PRINTERS_ADDED = 1;
    private static final int MSG_PRINTERS_REMOVED = 2;
    private final Handler mHandler;
    private boolean mIsPrinterDiscoveryStarted;
    private OnPrintersChangeListener mListener;
    private IPrinterDiscoveryObserver mObserver;
    private final IPrintManager mPrintManager;
    private final LinkedHashMap<PrinterId, PrinterInfo> mPrinters = new LinkedHashMap();
    private final int mUserId;

    PrinterDiscoverySession(IPrintManager iPrintManager, Context context, int n) {
        this.mPrintManager = iPrintManager;
        this.mUserId = n;
        this.mHandler = new SessionHandler(context.getMainLooper());
        this.mObserver = new PrinterDiscoveryObserver(this);
        try {
            this.mPrintManager.createPrinterDiscoverySession(this.mObserver, this.mUserId);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error creating printer discovery session", remoteException);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void destroyNoCheck() {
        this.stopPrinterDiscovery();
        this.mPrintManager.destroyPrinterDiscoverySession(this.mObserver, this.mUserId);
        this.mObserver = null;
        this.mPrinters.clear();
        return;
        {
            catch (RemoteException var1_2) {}
            {
                Log.e("PrinterDiscoverySession", "Error destroying printer discovery session", var1_2);
            }
        }
        ** finally { 
lbl11: // 1 sources:
        this.mObserver = null;
        this.mPrinters.clear();
        throw var1_1;
    }

    private void handlePrintersAdded(List<PrinterInfo> object) {
        if (this.isDestroyed()) {
            return;
        }
        if (this.mPrinters.isEmpty()) {
            int n = object.size();
            for (int i = 0; i < n; ++i) {
                PrinterInfo printerInfo = (PrinterInfo)object.get(i);
                this.mPrinters.put(printerInfo.getId(), printerInfo);
            }
            this.notifyOnPrintersChanged();
            return;
        }
        ArrayMap<PrinterId, PrinterInfo> arrayMap = new ArrayMap<PrinterId, PrinterInfo>();
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            PrinterInfo parcelable = (PrinterInfo)object.get(i);
            arrayMap.put(parcelable.getId(), parcelable);
        }
        for (PrinterId printerId : this.mPrinters.keySet()) {
            PrinterInfo printerInfo = (PrinterInfo)arrayMap.remove(printerId);
            if (printerInfo == null) continue;
            this.mPrinters.put(printerId, printerInfo);
        }
        this.mPrinters.putAll(arrayMap);
        this.notifyOnPrintersChanged();
    }

    private void handlePrintersRemoved(List<PrinterId> list) {
        if (this.isDestroyed()) {
            return;
        }
        boolean bl = false;
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            PrinterId printerId = list.get(i);
            if (this.mPrinters.remove(printerId) == null) continue;
            bl = true;
        }
        if (bl) {
            this.notifyOnPrintersChanged();
        }
    }

    private boolean isDestroyedNoCheck() {
        boolean bl = this.mObserver == null;
        return bl;
    }

    private void notifyOnPrintersChanged() {
        OnPrintersChangeListener onPrintersChangeListener = this.mListener;
        if (onPrintersChangeListener != null) {
            onPrintersChangeListener.onPrintersChanged();
        }
    }

    private static void throwIfNotCalledOnMainThread() {
        if (Looper.getMainLooper().isCurrentThread()) {
            return;
        }
        throw new IllegalAccessError("must be called from the main thread");
    }

    public final void destroy() {
        if (this.isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring destroy - session destroyed");
        }
        this.destroyNoCheck();
    }

    protected final void finalize() throws Throwable {
        if (!this.isDestroyedNoCheck()) {
            Log.e(LOG_TAG, "Destroying leaked printer discovery session");
            this.destroyNoCheck();
        }
        super.finalize();
    }

    public final List<PrinterInfo> getPrinters() {
        if (this.isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring get printers - session destroyed");
            return Collections.emptyList();
        }
        return new ArrayList<PrinterInfo>(this.mPrinters.values());
    }

    public final boolean isDestroyed() {
        PrinterDiscoverySession.throwIfNotCalledOnMainThread();
        return this.isDestroyedNoCheck();
    }

    public final boolean isPrinterDiscoveryStarted() {
        PrinterDiscoverySession.throwIfNotCalledOnMainThread();
        return this.mIsPrinterDiscoveryStarted;
    }

    public final void setOnPrintersChangeListener(OnPrintersChangeListener onPrintersChangeListener) {
        PrinterDiscoverySession.throwIfNotCalledOnMainThread();
        this.mListener = onPrintersChangeListener;
    }

    public final void startPrinterDiscovery(List<PrinterId> list) {
        if (this.isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring start printers discovery - session destroyed");
            return;
        }
        if (!this.mIsPrinterDiscoveryStarted) {
            this.mIsPrinterDiscoveryStarted = true;
            try {
                this.mPrintManager.startPrinterDiscovery(this.mObserver, list, this.mUserId);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error starting printer discovery", remoteException);
            }
        }
    }

    public final void startPrinterStateTracking(PrinterId printerId) {
        if (this.isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring start printer state tracking - session destroyed");
            return;
        }
        try {
            this.mPrintManager.startPrinterStateTracking(printerId, this.mUserId);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error starting printer state tracking", remoteException);
        }
    }

    public final void stopPrinterDiscovery() {
        if (this.isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring stop printers discovery - session destroyed");
            return;
        }
        if (this.mIsPrinterDiscoveryStarted) {
            this.mIsPrinterDiscoveryStarted = false;
            try {
                this.mPrintManager.stopPrinterDiscovery(this.mObserver, this.mUserId);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error stopping printer discovery", remoteException);
            }
        }
    }

    public final void stopPrinterStateTracking(PrinterId printerId) {
        if (this.isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring stop printer state tracking - session destroyed");
            return;
        }
        try {
            this.mPrintManager.stopPrinterStateTracking(printerId, this.mUserId);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error stopping printer state tracking", remoteException);
        }
    }

    public final void validatePrinters(List<PrinterId> list) {
        if (this.isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring validate printers - session destroyed");
            return;
        }
        try {
            this.mPrintManager.validatePrinters(list, this.mUserId);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error validating printers", remoteException);
        }
    }

    public static interface OnPrintersChangeListener {
        public void onPrintersChanged();
    }

    public static final class PrinterDiscoveryObserver
    extends IPrinterDiscoveryObserver.Stub {
        private final WeakReference<PrinterDiscoverySession> mWeakSession;

        public PrinterDiscoveryObserver(PrinterDiscoverySession printerDiscoverySession) {
            this.mWeakSession = new WeakReference<PrinterDiscoverySession>(printerDiscoverySession);
        }

        @Override
        public void onPrintersAdded(ParceledListSlice parceledListSlice) {
            PrinterDiscoverySession printerDiscoverySession = (PrinterDiscoverySession)this.mWeakSession.get();
            if (printerDiscoverySession != null) {
                printerDiscoverySession.mHandler.obtainMessage(1, parceledListSlice.getList()).sendToTarget();
            }
        }

        @Override
        public void onPrintersRemoved(ParceledListSlice parceledListSlice) {
            PrinterDiscoverySession printerDiscoverySession = (PrinterDiscoverySession)this.mWeakSession.get();
            if (printerDiscoverySession != null) {
                printerDiscoverySession.mHandler.obtainMessage(2, parceledListSlice.getList()).sendToTarget();
            }
        }
    }

    private final class SessionHandler
    extends Handler {
        public SessionHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 1) {
                if (n == 2) {
                    object = (List)((Message)object).obj;
                    PrinterDiscoverySession.this.handlePrintersRemoved((List)object);
                }
            } else {
                object = (List)((Message)object).obj;
                PrinterDiscoverySession.this.handlePrintersAdded((List)object);
            }
        }
    }

}

