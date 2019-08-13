/*
 * Decompiled with CFR 0.145.
 */
package android.printservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.printservice.IPrintService;
import android.printservice.IPrintServiceClient;
import android.printservice.PrintJob;
import android.printservice.PrinterDiscoverySession;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PrintService
extends Service {
    private static final boolean DEBUG = false;
    public static final String EXTRA_CAN_SELECT_PRINTER = "android.printservice.extra.CAN_SELECT_PRINTER";
    public static final String EXTRA_PRINTER_INFO = "android.intent.extra.print.EXTRA_PRINTER_INFO";
    public static final String EXTRA_PRINT_DOCUMENT_INFO = "android.printservice.extra.PRINT_DOCUMENT_INFO";
    public static final String EXTRA_PRINT_JOB_INFO = "android.intent.extra.print.PRINT_JOB_INFO";
    public static final String EXTRA_SELECT_PRINTER = "android.printservice.extra.SELECT_PRINTER";
    private static final String LOG_TAG = "PrintService";
    public static final String SERVICE_INTERFACE = "android.printservice.PrintService";
    public static final String SERVICE_META_DATA = "android.printservice";
    private IPrintServiceClient mClient;
    private PrinterDiscoverySession mDiscoverySession;
    private Handler mHandler;
    private int mLastSessionId = -1;

    static void throwIfNotCalledOnMainThread() {
        if (Looper.getMainLooper().isCurrentThread()) {
            return;
        }
        throw new IllegalAccessError("must be called from the main thread");
    }

    @Override
    protected final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new ServiceHandler(context.getMainLooper());
    }

    public final PrinterId generatePrinterId(String string2) {
        PrintService.throwIfNotCalledOnMainThread();
        string2 = Preconditions.checkNotNull(string2, "localId cannot be null");
        return new PrinterId(new ComponentName(this.getPackageName(), this.getClass().getName()), string2);
    }

    public final List<PrintJob> getActivePrintJobs() {
        Object object;
        block7 : {
            int n;
            int n2;
            PrintService.throwIfNotCalledOnMainThread();
            ArrayList<Object> arrayList = this.mClient;
            if (arrayList == null) {
                return Collections.emptyList();
            }
            object = null;
            List<PrintJobInfo> list = arrayList.getPrintJobInfos();
            if (list == null) break block7;
            try {
                n2 = list.size();
                arrayList = new ArrayList<Object>(n2);
                n = 0;
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error calling getPrintJobs()", remoteException);
            }
            do {
                object = arrayList;
                if (n >= n2) break;
                object = new PrintJob(this, list.get(n), this.mClient);
                arrayList.add(object);
                ++n;
                continue;
                break;
            } while (true);
        }
        if (object != null) {
            return object;
        }
        return Collections.emptyList();
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new IPrintService.Stub(){

            @Override
            public void createPrinterDiscoverySession() {
                PrintService.this.mHandler.sendEmptyMessage(1);
            }

            @Override
            public void destroyPrinterDiscoverySession() {
                PrintService.this.mHandler.sendEmptyMessage(2);
            }

            @Override
            public void onPrintJobQueued(PrintJobInfo printJobInfo) {
                PrintService.this.mHandler.obtainMessage(9, printJobInfo).sendToTarget();
            }

            @Override
            public void requestCancelPrintJob(PrintJobInfo printJobInfo) {
                PrintService.this.mHandler.obtainMessage(10, printJobInfo).sendToTarget();
            }

            @Override
            public void requestCustomPrinterIcon(PrinterId printerId) {
                PrintService.this.mHandler.obtainMessage(7, printerId).sendToTarget();
            }

            @Override
            public void setClient(IPrintServiceClient iPrintServiceClient) {
                PrintService.this.mHandler.obtainMessage(11, iPrintServiceClient).sendToTarget();
            }

            @Override
            public void startPrinterDiscovery(List<PrinterId> list) {
                PrintService.this.mHandler.obtainMessage(3, list).sendToTarget();
            }

            @Override
            public void startPrinterStateTracking(PrinterId printerId) {
                PrintService.this.mHandler.obtainMessage(6, printerId).sendToTarget();
            }

            @Override
            public void stopPrinterDiscovery() {
                PrintService.this.mHandler.sendEmptyMessage(4);
            }

            @Override
            public void stopPrinterStateTracking(PrinterId printerId) {
                PrintService.this.mHandler.obtainMessage(8, printerId).sendToTarget();
            }

            @Override
            public void validatePrinters(List<PrinterId> list) {
                PrintService.this.mHandler.obtainMessage(5, list).sendToTarget();
            }
        };
    }

    protected void onConnected() {
    }

    protected abstract PrinterDiscoverySession onCreatePrinterDiscoverySession();

    protected void onDisconnected() {
    }

    protected abstract void onPrintJobQueued(PrintJob var1);

    protected abstract void onRequestCancelPrintJob(PrintJob var1);

    private final class ServiceHandler
    extends Handler {
        public static final int MSG_CREATE_PRINTER_DISCOVERY_SESSION = 1;
        public static final int MSG_DESTROY_PRINTER_DISCOVERY_SESSION = 2;
        public static final int MSG_ON_PRINTJOB_QUEUED = 9;
        public static final int MSG_ON_REQUEST_CANCEL_PRINTJOB = 10;
        public static final int MSG_REQUEST_CUSTOM_PRINTER_ICON = 7;
        public static final int MSG_SET_CLIENT = 11;
        public static final int MSG_START_PRINTER_DISCOVERY = 3;
        public static final int MSG_START_PRINTER_STATE_TRACKING = 6;
        public static final int MSG_STOP_PRINTER_DISCOVERY = 4;
        public static final int MSG_STOP_PRINTER_STATE_TRACKING = 8;
        public static final int MSG_VALIDATE_PRINTERS = 5;

        public ServiceHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override
        public void handleMessage(Message object) {
            block14 : {
                block15 : {
                    int n = ((Message)object).what;
                    switch (n) {
                        default: {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unknown message: ");
                            ((StringBuilder)object).append(n);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        case 11: {
                            PrintService.this.mClient = (IPrintServiceClient)((Message)object).obj;
                            if (PrintService.this.mClient != null) {
                                PrintService.this.onConnected();
                                break;
                            }
                            PrintService.this.onDisconnected();
                            break;
                        }
                        case 10: {
                            PrintJobInfo printJobInfo = (PrintJobInfo)((Message)object).obj;
                            object = PrintService.this;
                            ((PrintService)object).onRequestCancelPrintJob(new PrintJob((Context)object, printJobInfo, ((PrintService)object).mClient));
                            break;
                        }
                        case 9: {
                            object = (PrintJobInfo)((Message)object).obj;
                            PrintService printService = PrintService.this;
                            printService.onPrintJobQueued(new PrintJob(printService, (PrintJobInfo)object, printService.mClient));
                            break;
                        }
                        case 8: {
                            if (PrintService.this.mDiscoverySession == null) break;
                            object = (PrinterId)((Message)object).obj;
                            PrintService.this.mDiscoverySession.stopPrinterStateTracking((PrinterId)object);
                            break;
                        }
                        case 7: {
                            if (PrintService.this.mDiscoverySession == null) break;
                            object = (PrinterId)((Message)object).obj;
                            PrintService.this.mDiscoverySession.requestCustomPrinterIcon((PrinterId)object);
                            break;
                        }
                        case 6: {
                            if (PrintService.this.mDiscoverySession == null) break;
                            object = (PrinterId)((Message)object).obj;
                            PrintService.this.mDiscoverySession.startPrinterStateTracking((PrinterId)object);
                            break;
                        }
                        case 5: {
                            if (PrintService.this.mDiscoverySession == null) break;
                            object = (List)((Message)object).obj;
                            PrintService.this.mDiscoverySession.validatePrinters((List<PrinterId>)object);
                            break;
                        }
                        case 4: {
                            if (PrintService.this.mDiscoverySession == null) break;
                            PrintService.this.mDiscoverySession.stopPrinterDiscovery();
                            break;
                        }
                        case 3: {
                            if (PrintService.this.mDiscoverySession == null) break;
                            object = (ArrayList)((Message)object).obj;
                            PrintService.this.mDiscoverySession.startPrinterDiscovery((List<PrinterId>)object);
                            break;
                        }
                        case 2: {
                            if (PrintService.this.mDiscoverySession == null) break;
                            PrintService.this.mDiscoverySession.destroy();
                            PrintService.this.mDiscoverySession = null;
                            break;
                        }
                        case 1: {
                            object = PrintService.this.onCreatePrinterDiscoverySession();
                            if (object == null) break block14;
                            if (((PrinterDiscoverySession)object).getId() == PrintService.this.mLastSessionId) break block15;
                            PrintService.this.mDiscoverySession = (PrinterDiscoverySession)object;
                            PrintService.this.mLastSessionId = ((PrinterDiscoverySession)object).getId();
                            ((PrinterDiscoverySession)object).setObserver(PrintService.this.mClient);
                        }
                    }
                    return;
                }
                throw new IllegalStateException("cannot reuse session instances");
            }
            throw new NullPointerException("session cannot be null");
        }
    }

}

