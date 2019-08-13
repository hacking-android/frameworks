/*
 * Decompiled with CFR 0.145.
 */
package android.printservice;

import android.content.pm.ParceledListSlice;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.PrinterId;
import android.print.PrinterInfo;
import android.printservice.CustomPrinterIconCallback;
import android.printservice.IPrintServiceClient;
import android.printservice.PrintService;
import android.util.ArrayMap;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class PrinterDiscoverySession {
    private static final String LOG_TAG = "PrinterDiscoverySession";
    private static int sIdCounter = 0;
    private final int mId;
    private boolean mIsDestroyed;
    private boolean mIsDiscoveryStarted;
    private ArrayMap<PrinterId, PrinterInfo> mLastSentPrinters;
    private IPrintServiceClient mObserver;
    private final ArrayMap<PrinterId, PrinterInfo> mPrinters = new ArrayMap();
    private final List<PrinterId> mTrackedPrinters = new ArrayList<PrinterId>();

    public PrinterDiscoverySession() {
        int n = sIdCounter;
        sIdCounter = n + 1;
        this.mId = n;
    }

    private void sendOutOfDiscoveryPeriodPrinterChanges() {
        block14 : {
            Object object;
            ArrayList<PrinterId> arrayList;
            ArrayMap<PrinterId, PrinterInfo> arrayMap = this.mLastSentPrinters;
            if (arrayMap == null || arrayMap.isEmpty()) break block14;
            arrayMap = null;
            for (PrinterInfo printerInfo : this.mPrinters.values()) {
                block16 : {
                    block15 : {
                        PrinterInfo printerInfo2 = this.mLastSentPrinters.get(printerInfo.getId());
                        if (printerInfo2 == null) break block15;
                        arrayList = arrayMap;
                        if (printerInfo2.equals(printerInfo)) break block16;
                    }
                    arrayList = arrayMap;
                    if (arrayMap == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((PrinterId)((Object)printerInfo));
                }
                arrayMap = arrayList;
            }
            if (arrayMap != null) {
                try {
                    object = this.mObserver;
                    arrayList = new ArrayList<PrinterId>((List<PrinterId>)((Object)arrayMap));
                    object.onPrintersAdded((ParceledListSlice)((Object)arrayList));
                }
                catch (RemoteException remoteException) {
                    Log.e(LOG_TAG, "Error sending added printers", remoteException);
                }
            }
            arrayMap = null;
            for (PrinterInfo printerInfo : this.mLastSentPrinters.values()) {
                arrayList = arrayMap;
                if (!this.mPrinters.containsKey(printerInfo.getId())) {
                    arrayList = arrayMap;
                    if (arrayMap == null) {
                        arrayList = new ArrayList<PrinterId>();
                    }
                    arrayList.add(printerInfo.getId());
                }
                arrayMap = arrayList;
            }
            if (arrayMap != null) {
                try {
                    object = this.mObserver;
                    arrayList = new ArrayList<PrinterId>((List<PrinterId>)((Object)arrayMap));
                    object.onPrintersRemoved((ParceledListSlice)((Object)arrayList));
                }
                catch (RemoteException remoteException) {
                    Log.e(LOG_TAG, "Error sending removed printers", remoteException);
                }
            }
            this.mLastSentPrinters = null;
            return;
        }
        this.mLastSentPrinters = null;
    }

    public final void addPrinters(List<PrinterInfo> object) {
        block13 : {
            block10 : {
                Object object2;
                PrintService.throwIfNotCalledOnMainThread();
                if (this.mIsDestroyed) {
                    Log.w(LOG_TAG, "Not adding printers - session destroyed.");
                    return;
                }
                if (!this.mIsDiscoveryStarted) break block10;
                ParceledListSlice parceledListSlice = null;
                int n = object.size();
                for (int i = 0; i < n; ++i) {
                    block12 : {
                        PrinterInfo printerInfo;
                        block11 : {
                            printerInfo = object.get(i);
                            PrinterInfo printerInfo2 = this.mPrinters.put(printerInfo.getId(), printerInfo);
                            if (printerInfo2 == null) break block11;
                            object2 = parceledListSlice;
                            if (printerInfo2.equals(printerInfo)) break block12;
                        }
                        object2 = parceledListSlice;
                        if (parceledListSlice == null) {
                            object2 = new ArrayList();
                        }
                        object2.add((PrinterInfo)printerInfo);
                    }
                    parceledListSlice = object2;
                }
                if (parceledListSlice == null) break block13;
                try {
                    object = this.mObserver;
                    object2 = new ParceledListSlice(parceledListSlice);
                    object.onPrintersAdded((ParceledListSlice)object2);
                }
                catch (RemoteException remoteException) {
                    Log.e(LOG_TAG, "Error sending added printers", remoteException);
                }
                break block13;
            }
            if (this.mLastSentPrinters == null) {
                this.mLastSentPrinters = new ArrayMap<PrinterId, PrinterInfo>(this.mPrinters);
            }
            int n = object.size();
            for (int i = 0; i < n; ++i) {
                PrinterInfo printerInfo = object.get(i);
                if (this.mPrinters.get(printerInfo.getId()) != null) continue;
                this.mPrinters.put(printerInfo.getId(), printerInfo);
            }
        }
    }

    void destroy() {
        if (!this.mIsDestroyed) {
            this.mIsDestroyed = true;
            this.mIsDiscoveryStarted = false;
            this.mPrinters.clear();
            this.mLastSentPrinters = null;
            this.mObserver = null;
            this.onDestroy();
        }
    }

    int getId() {
        return this.mId;
    }

    public final List<PrinterInfo> getPrinters() {
        PrintService.throwIfNotCalledOnMainThread();
        if (this.mIsDestroyed) {
            return Collections.emptyList();
        }
        return new ArrayList<PrinterInfo>(this.mPrinters.values());
    }

    public final List<PrinterId> getTrackedPrinters() {
        PrintService.throwIfNotCalledOnMainThread();
        if (this.mIsDestroyed) {
            return Collections.emptyList();
        }
        return new ArrayList<PrinterId>(this.mTrackedPrinters);
    }

    public final boolean isDestroyed() {
        PrintService.throwIfNotCalledOnMainThread();
        return this.mIsDestroyed;
    }

    public final boolean isPrinterDiscoveryStarted() {
        PrintService.throwIfNotCalledOnMainThread();
        return this.mIsDiscoveryStarted;
    }

    public abstract void onDestroy();

    public void onRequestCustomPrinterIcon(PrinterId printerId, CancellationSignal cancellationSignal, CustomPrinterIconCallback customPrinterIconCallback) {
    }

    public abstract void onStartPrinterDiscovery(List<PrinterId> var1);

    public abstract void onStartPrinterStateTracking(PrinterId var1);

    public abstract void onStopPrinterDiscovery();

    public abstract void onStopPrinterStateTracking(PrinterId var1);

    public abstract void onValidatePrinters(List<PrinterId> var1);

    public final void removePrinters(List<PrinterId> object) {
        PrintService.throwIfNotCalledOnMainThread();
        if (this.mIsDestroyed) {
            Log.w("PrinterDiscoverySession", "Not removing printers - session destroyed.");
            return;
        }
        if (this.mIsDiscoveryStarted) {
            Parcelable parcelable;
            ArrayList<PrinterId> arrayList = new ArrayList<PrinterId>();
            int n = object.size();
            for (int i = 0; i < n; ++i) {
                parcelable = object.get(i);
                if (this.mPrinters.remove(parcelable) == null) continue;
                arrayList.add((PrinterId)parcelable);
            }
            if (!arrayList.isEmpty()) {
                try {
                    object = this.mObserver;
                    parcelable = new ParceledListSlice(arrayList);
                    object.onPrintersRemoved((ParceledListSlice)parcelable);
                }
                catch (RemoteException remoteException) {
                    Log.e("PrinterDiscoverySession", "Error sending removed printers", remoteException);
                }
            }
        } else {
            if (this.mLastSentPrinters == null) {
                this.mLastSentPrinters = new ArrayMap<PrinterId, PrinterInfo>(this.mPrinters);
            }
            int n = object.size();
            for (int i = 0; i < n; ++i) {
                PrinterId printerId = object.get(i);
                this.mPrinters.remove(printerId);
            }
        }
    }

    void requestCustomPrinterIcon(PrinterId printerId) {
        Object object;
        if (!this.mIsDestroyed && (object = this.mObserver) != null) {
            object = new CustomPrinterIconCallback(printerId, (IPrintServiceClient)object);
            this.onRequestCustomPrinterIcon(printerId, new CancellationSignal(), (CustomPrinterIconCallback)object);
        }
    }

    void setObserver(IPrintServiceClient iPrintServiceClient) {
        this.mObserver = iPrintServiceClient;
        if (!this.mPrinters.isEmpty()) {
            try {
                iPrintServiceClient = this.mObserver;
                ParceledListSlice<PrinterInfo> parceledListSlice = new ParceledListSlice<PrinterInfo>(this.getPrinters());
                iPrintServiceClient.onPrintersAdded(parceledListSlice);
            }
            catch (RemoteException remoteException) {
                Log.e("PrinterDiscoverySession", "Error sending added printers", remoteException);
            }
        }
    }

    void startPrinterDiscovery(List<PrinterId> list) {
        if (!this.mIsDestroyed) {
            this.mIsDiscoveryStarted = true;
            this.sendOutOfDiscoveryPeriodPrinterChanges();
            List<PrinterId> list2 = list;
            if (list == null) {
                list2 = Collections.emptyList();
            }
            this.onStartPrinterDiscovery(list2);
        }
    }

    void startPrinterStateTracking(PrinterId printerId) {
        if (!this.mIsDestroyed && this.mObserver != null && !this.mTrackedPrinters.contains(printerId)) {
            this.mTrackedPrinters.add(printerId);
            this.onStartPrinterStateTracking(printerId);
        }
    }

    void stopPrinterDiscovery() {
        if (!this.mIsDestroyed) {
            this.mIsDiscoveryStarted = false;
            this.onStopPrinterDiscovery();
        }
    }

    void stopPrinterStateTracking(PrinterId printerId) {
        if (!this.mIsDestroyed && this.mObserver != null && this.mTrackedPrinters.remove(printerId)) {
            this.onStopPrinterStateTracking(printerId);
        }
    }

    void validatePrinters(List<PrinterId> list) {
        if (!this.mIsDestroyed && this.mObserver != null) {
            this.onValidatePrinters(list);
        }
    }
}

