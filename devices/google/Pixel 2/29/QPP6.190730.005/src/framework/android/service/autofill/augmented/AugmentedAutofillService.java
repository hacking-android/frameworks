/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.augmented.-$
 *  android.service.autofill.augmented.-$$Lambda
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService$1
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService$1$4dXh5Zwc8KxDD9bV1LFhgo3zrgk
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService$1$D2Ct4Bd0D1M8vONZTBmU9zstEFI
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService$1$LSvI4QN2NxJLegcZI0BFIvKwp6o
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService$1$mgzh8N5GuvmPXfqMBgjw-Q27Ij0
 *  android.service.autofill.augmented.-$$Lambda$AugmentedAutofillService$zZAmNDLQX4rUV_yTGug25y4E6gA
 */
package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.autofill.augmented.-$;
import android.service.autofill.augmented.FillCallback;
import android.service.autofill.augmented.FillController;
import android.service.autofill.augmented.FillRequest;
import android.service.autofill.augmented.FillWindow;
import android.service.autofill.augmented.Helper;
import android.service.autofill.augmented.IAugmentedAutofillService;
import android.service.autofill.augmented.IFillCallback;
import android.service.autofill.augmented.PresentationParams;
import android.service.autofill.augmented._$$Lambda$AugmentedAutofillService$1$4dXh5Zwc8KxDD9bV1LFhgo3zrgk;
import android.service.autofill.augmented._$$Lambda$AugmentedAutofillService$1$D2Ct4Bd0D1M8vONZTBmU9zstEFI;
import android.service.autofill.augmented._$$Lambda$AugmentedAutofillService$1$LSvI4QN2NxJLegcZI0BFIvKwp6o;
import android.service.autofill.augmented._$$Lambda$AugmentedAutofillService$1$mgzh8N5GuvmPXfqMBgjw_Q27Ij0;
import android.service.autofill.augmented._$$Lambda$AugmentedAutofillService$zZAmNDLQX4rUV_yTGug25y4E6gA;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAugmentedAutofillManagerClient;
import android.view.autofill.IAutofillWindowPresenter;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public abstract class AugmentedAutofillService
extends Service {
    public static final String SERVICE_INTERFACE = "android.service.autofill.augmented.AugmentedAutofillService";
    private static final String TAG = AugmentedAutofillService.class.getSimpleName();
    static boolean sDebug = Build.IS_USER ^ true;
    static boolean sVerbose = false;
    private SparseArray<AutofillProxy> mAutofillProxies;
    private Handler mHandler;
    private final IAugmentedAutofillService mInterface = new IAugmentedAutofillService.Stub(){

        static /* synthetic */ void lambda$onConnected$0(Object object, boolean bl, boolean bl2) {
            ((AugmentedAutofillService)object).handleOnConnected(bl, bl2);
        }

        static /* synthetic */ void lambda$onDestroyAllFillWindowsRequest$3(Object object) {
            ((AugmentedAutofillService)object).handleOnDestroyAllFillWindowsRequest();
        }

        static /* synthetic */ void lambda$onDisconnected$1(Object object) {
            ((AugmentedAutofillService)object).handleOnDisconnected();
        }

        static /* synthetic */ void lambda$onFillRequest$2(Object object, int n, IBinder iBinder, int n2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long l, IFillCallback iFillCallback) {
            ((AugmentedAutofillService)object).handleOnFillRequest(n, iBinder, n2, componentName, autofillId, autofillValue, l, iFillCallback);
        }

        @Override
        public void onConnected(boolean bl, boolean bl2) {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AugmentedAutofillService$1$4dXh5Zwc8KxDD9bV1LFhgo3zrgk.INSTANCE, AugmentedAutofillService.this, bl, bl2));
        }

        @Override
        public void onDestroyAllFillWindowsRequest() {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AugmentedAutofillService$1$LSvI4QN2NxJLegcZI0BFIvKwp6o.INSTANCE, AugmentedAutofillService.this));
        }

        @Override
        public void onDisconnected() {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AugmentedAutofillService$1$D2Ct4Bd0D1M8vONZTBmU9zstEFI.INSTANCE, AugmentedAutofillService.this));
        }

        @Override
        public void onFillRequest(int n, IBinder iBinder, int n2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long l, IFillCallback iFillCallback) {
            AugmentedAutofillService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AugmentedAutofillService$1$mgzh8N5GuvmPXfqMBgjw_Q27Ij0.INSTANCE, AugmentedAutofillService.this, n, iBinder, n2, componentName, autofillId, autofillValue, l, iFillCallback));
        }
    };
    private ComponentName mServiceComponentName;

    private void handleOnConnected(boolean bl, boolean bl2) {
        if (sDebug || bl) {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleOnConnected(): debug=");
            stringBuilder.append(bl);
            stringBuilder.append(", verbose=");
            stringBuilder.append(bl2);
            Log.d(string2, stringBuilder.toString());
        }
        sDebug = bl;
        sVerbose = bl2;
        this.onConnected();
    }

    private void handleOnDestroyAllFillWindowsRequest() {
        Object object = this.mAutofillProxies;
        if (object != null) {
            int n = ((SparseArray)object).size();
            for (int i = 0; i < n; ++i) {
                int n2 = this.mAutofillProxies.keyAt(i);
                object = this.mAutofillProxies.valueAt(i);
                if (object == null) {
                    String string2 = TAG;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("No proxy for session ");
                    ((StringBuilder)object).append(n2);
                    Log.w(string2, ((StringBuilder)object).toString());
                    return;
                }
                if (((AutofillProxy)object).mCallback != null) {
                    try {
                        if (!((AutofillProxy)object).mCallback.isCompleted()) {
                            ((AutofillProxy)object).mCallback.cancel();
                        }
                    }
                    catch (Exception exception) {
                        Log.e(TAG, "failed to check current pending request status", exception);
                    }
                }
                ((AutofillProxy)object).destroy();
            }
            this.mAutofillProxies.clear();
        }
    }

    private void handleOnDisconnected() {
        this.onDisconnected();
    }

    private void handleOnFillRequest(int n, IBinder object, int n2, ComponentName object2, AutofillId autofillId, AutofillValue autofillValue, long l, IFillCallback iFillCallback) {
        if (this.mAutofillProxies == null) {
            this.mAutofillProxies = new SparseArray();
        }
        ICancellationSignal iCancellationSignal = CancellationSignal.createTransport();
        CancellationSignal cancellationSignal = CancellationSignal.fromTransport(iCancellationSignal);
        AutofillProxy autofillProxy = this.mAutofillProxies.get(n);
        if (autofillProxy == null) {
            object = new AutofillProxy(n, (IBinder)object, n2, this.mServiceComponentName, (ComponentName)object2, autofillId, autofillValue, l, iFillCallback, cancellationSignal);
            this.mAutofillProxies.put(n, (AutofillProxy)object);
        } else {
            if (sDebug) {
                object = TAG;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Reusing proxy for session ");
                ((StringBuilder)object2).append(n);
                Log.d((String)object, ((StringBuilder)object2).toString());
            }
            autofillProxy.update(autofillId, autofillValue, iFillCallback, cancellationSignal);
            object = autofillProxy;
        }
        try {
            iFillCallback.onCancellable(iCancellationSignal);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
        this.onFillRequest(new FillRequest((AutofillProxy)object), cancellationSignal, new FillController((AutofillProxy)object), new FillCallback((AutofillProxy)object));
    }

    private void handleOnUnbind() {
        StringBuilder stringBuilder;
        SparseArray<AutofillProxy> sparseArray = this.mAutofillProxies;
        if (sparseArray == null) {
            if (sDebug) {
                Log.d(TAG, "onUnbind(): no proxy to destroy");
            }
            return;
        }
        int n = sparseArray.size();
        if (sDebug) {
            sparseArray = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onUnbind(): destroying ");
            stringBuilder.append(n);
            stringBuilder.append(" proxies");
            Log.d((String)((Object)sparseArray), stringBuilder.toString());
        }
        for (int i = 0; i < n; ++i) {
            sparseArray = this.mAutofillProxies.valueAt(i);
            try {
                ((AutofillProxy)((Object)sparseArray)).destroy();
                continue;
            }
            catch (Exception exception) {
                String string2 = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("error destroying ");
                stringBuilder.append(sparseArray);
                Log.w(string2, stringBuilder.toString());
            }
        }
        this.mAutofillProxies = null;
    }

    public static /* synthetic */ void lambda$zZAmNDLQX4rUV_yTGug25y4E6gA(AugmentedAutofillService augmentedAutofillService) {
        augmentedAutofillService.handleOnUnbind();
    }

    @Override
    protected final void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        printWriter.print("Service component: ");
        printWriter.println(ComponentName.flattenToShortString(this.mServiceComponentName));
        object = this.mAutofillProxies;
        if (object != null) {
            int n = ((SparseArray)object).size();
            printWriter.print("Number proxies: ");
            printWriter.println(n);
            for (int i = 0; i < n; ++i) {
                int n2 = this.mAutofillProxies.keyAt(i);
                object = this.mAutofillProxies.valueAt(i);
                printWriter.print(i);
                printWriter.print(") SessionId=");
                printWriter.print(n2);
                printWriter.println(":");
                ((AutofillProxy)object).dump("  ", printWriter);
            }
        }
        this.dump(printWriter, arrstring);
    }

    protected void dump(PrintWriter printWriter, String[] arrstring) {
        printWriter.print(this.getClass().getName());
        printWriter.println(": nothing to dump");
    }

    @Override
    public final IBinder onBind(Intent intent) {
        this.mServiceComponentName = intent.getComponent();
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tried to bind to wrong intent (should be android.service.autofill.augmented.AugmentedAutofillService: ");
        stringBuilder.append(intent);
        Log.w(string2, stringBuilder.toString());
        return null;
    }

    public void onConnected() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    public void onDisconnected() {
    }

    public void onFillRequest(FillRequest fillRequest, CancellationSignal cancellationSignal, FillController fillController, FillCallback fillCallback) {
    }

    @Override
    public boolean onUnbind(Intent intent) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AugmentedAutofillService$zZAmNDLQX4rUV_yTGug25y4E6gA.INSTANCE, this));
        return false;
    }

    static final class AutofillProxy {
        static final int REPORT_EVENT_NO_RESPONSE = 1;
        static final int REPORT_EVENT_UI_DESTROYED = 3;
        static final int REPORT_EVENT_UI_SHOWN = 2;
        public final ComponentName componentName;
        @GuardedBy(value={"mLock"})
        private IFillCallback mCallback;
        private CancellationSignal mCancellationSignal;
        private final IAugmentedAutofillManagerClient mClient;
        @GuardedBy(value={"mLock"})
        private FillWindow mFillWindow;
        private long mFirstOnSuccessTime;
        private final long mFirstRequestTime;
        @GuardedBy(value={"mLock"})
        private AutofillId mFocusedId;
        @GuardedBy(value={"mLock"})
        private AutofillValue mFocusedValue;
        @GuardedBy(value={"mLock"})
        private AutofillId mLastShownId;
        private final Object mLock = new Object();
        private String mServicePackageName;
        private final int mSessionId;
        @GuardedBy(value={"mLock"})
        private PresentationParams.SystemPopupPresentationParams mSmartSuggestion;
        private long mUiFirstDestroyedTime;
        private long mUiFirstShownTime;
        public final int taskId;

        private AutofillProxy(int n, IBinder iBinder, int n2, ComponentName componentName, ComponentName componentName2, AutofillId autofillId, AutofillValue autofillValue, long l, IFillCallback iFillCallback, CancellationSignal cancellationSignal) {
            this.mSessionId = n;
            this.mClient = IAugmentedAutofillManagerClient.Stub.asInterface(iBinder);
            this.mCallback = iFillCallback;
            this.taskId = n2;
            this.componentName = componentName2;
            this.mServicePackageName = componentName.getPackageName();
            this.mFocusedId = autofillId;
            this.mFocusedValue = autofillValue;
            this.mFirstRequestTime = l;
            this.mCancellationSignal = cancellationSignal;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void destroy() {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mFillWindow != null) {
                    if (sDebug) {
                        Log.d(TAG, "destroying window");
                    }
                    this.mFillWindow.destroy();
                    this.mFillWindow = null;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void update(AutofillId object, AutofillValue autofillValue, IFillCallback iFillCallback, CancellationSignal cancellationSignal) {
            Object object2 = this.mLock;
            synchronized (object2) {
                this.mFocusedId = object;
                this.mFocusedValue = autofillValue;
                object = this.mCallback;
                if (object != null) {
                    try {
                        if (!this.mCallback.isCompleted()) {
                            this.mCallback.cancel();
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "failed to check current pending request status", remoteException);
                    }
                    Log.d(TAG, "mCallback is updated.");
                }
                this.mCallback = iFillCallback;
                this.mCancellationSignal = cancellationSignal;
                return;
            }
        }

        public void autofill(List<Pair<AutofillId, AutofillValue>> list) throws RemoteException {
            int n = list.size();
            ArrayList<AutofillId> arrayList = new ArrayList<AutofillId>(n);
            ArrayList<AutofillValue> arrayList2 = new ArrayList<AutofillValue>(n);
            for (int i = 0; i < n; ++i) {
                Pair<AutofillId, AutofillValue> pair = list.get(i);
                arrayList.add((AutofillId)pair.first);
                arrayList2.add((AutofillValue)pair.second);
            }
            this.mClient.autofill(this.mSessionId, arrayList, arrayList2);
        }

        public void dump(String string2, PrintWriter printWriter) {
            long l;
            long l2;
            printWriter.print(string2);
            printWriter.print("sessionId: ");
            printWriter.println(this.mSessionId);
            printWriter.print(string2);
            printWriter.print("taskId: ");
            printWriter.println(this.taskId);
            printWriter.print(string2);
            printWriter.print("component: ");
            printWriter.println(this.componentName.flattenToShortString());
            printWriter.print(string2);
            printWriter.print("focusedId: ");
            printWriter.println(this.mFocusedId);
            if (this.mFocusedValue != null) {
                printWriter.print(string2);
                printWriter.print("focusedValue: ");
                printWriter.println(this.mFocusedValue);
            }
            if (this.mLastShownId != null) {
                printWriter.print(string2);
                printWriter.print("lastShownId: ");
                printWriter.println(this.mLastShownId);
            }
            printWriter.print(string2);
            printWriter.print("client: ");
            printWriter.println(this.mClient);
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string2);
            charSequence.append("  ");
            charSequence = charSequence.toString();
            if (this.mFillWindow != null) {
                printWriter.print(string2);
                printWriter.println("window:");
                this.mFillWindow.dump((String)charSequence, printWriter);
            }
            if (this.mSmartSuggestion != null) {
                printWriter.print(string2);
                printWriter.println("smartSuggestion:");
                this.mSmartSuggestion.dump((String)charSequence, printWriter);
            }
            if ((l2 = this.mFirstOnSuccessTime) > 0L) {
                l = this.mFirstRequestTime;
                printWriter.print(string2);
                printWriter.print("response time: ");
                TimeUtils.formatDuration(l2 - l, printWriter);
                printWriter.println();
            }
            if ((l = this.mUiFirstShownTime) > 0L) {
                l2 = this.mFirstRequestTime;
                printWriter.print(string2);
                printWriter.print("UI rendering time: ");
                TimeUtils.formatDuration(l - l2, printWriter);
                printWriter.println();
            }
            if ((l2 = this.mUiFirstDestroyedTime) > 0L) {
                l = this.mFirstRequestTime;
                printWriter.print(string2);
                printWriter.print("UI life time: ");
                TimeUtils.formatDuration(l2 - l, printWriter);
                printWriter.println();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public FillWindow getFillWindow() {
            Object object = this.mLock;
            synchronized (object) {
                return this.mFillWindow;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public AutofillId getFocusedId() {
            Object object = this.mLock;
            synchronized (object) {
                return this.mFocusedId;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public AutofillValue getFocusedValue() {
            Object object = this.mLock;
            synchronized (object) {
                return this.mFocusedValue;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public PresentationParams.SystemPopupPresentationParams getSmartSuggestionParams() {
            Object object = this.mLock;
            synchronized (object) {
                Object object2;
                PresentationParams.SystemPopupPresentationParams systemPopupPresentationParams;
                block5 : {
                    if (this.mSmartSuggestion != null && this.mFocusedId.equals(this.mLastShownId)) {
                        return this.mSmartSuggestion;
                    }
                    try {
                        object2 = this.mClient.getViewCoordinates(this.mFocusedId);
                        if (object2 != null) break block5;
                        if (!sDebug) return null;
                    }
                    catch (RemoteException remoteException) {
                        String string2 = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Could not get coordinates for ");
                        stringBuilder.append(this.mFocusedId);
                        Log.w(string2, stringBuilder.toString());
                        return null;
                    }
                    String string3 = TAG;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("getViewCoordinates(");
                    ((StringBuilder)object2).append(this.mFocusedId);
                    ((StringBuilder)object2).append(") returned null");
                    Log.d(string3, ((StringBuilder)object2).toString());
                    return null;
                }
                this.mSmartSuggestion = systemPopupPresentationParams = new PresentationParams.SystemPopupPresentationParams(this, (Rect)object2);
                this.mLastShownId = this.mFocusedId;
                return this.mSmartSuggestion;
            }
        }

        public void report(int n) {
            CharSequence charSequence;
            CharSequence charSequence2;
            if (sVerbose) {
                charSequence = TAG;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("report(): ");
                ((StringBuilder)charSequence2).append(n);
                Log.v((String)charSequence, ((StringBuilder)charSequence2).toString());
            }
            long l = -1L;
            int n2 = 0;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        charSequence = TAG;
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append("invalid event reported: ");
                        ((StringBuilder)charSequence2).append(n);
                        Log.w((String)charSequence, ((StringBuilder)charSequence2).toString());
                        n = n2;
                    } else {
                        n = n2 = 2;
                        if (this.mUiFirstDestroyedTime == 0L) {
                            long l2;
                            this.mUiFirstDestroyedTime = SystemClock.elapsedRealtime();
                            l = l2 = this.mUiFirstDestroyedTime - this.mFirstRequestTime;
                            n = n2;
                            if (sDebug) {
                                charSequence = TAG;
                                charSequence2 = new StringBuilder();
                                ((StringBuilder)charSequence2).append("UI destroyed in ");
                                ((StringBuilder)charSequence2).append(TimeUtils.formatDuration(l2));
                                Log.d((String)charSequence, ((StringBuilder)charSequence2).toString());
                                l = l2;
                                n = n2;
                            }
                        }
                    }
                } else {
                    n = n2 = 1;
                    if (this.mUiFirstShownTime == 0L) {
                        long l3;
                        this.mUiFirstShownTime = SystemClock.elapsedRealtime();
                        l = l3 = this.mUiFirstShownTime - this.mFirstRequestTime;
                        n = n2;
                        if (sDebug) {
                            charSequence2 = TAG;
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("UI shown in ");
                            ((StringBuilder)charSequence).append(TimeUtils.formatDuration(l3));
                            Log.d((String)charSequence2, ((StringBuilder)charSequence).toString());
                            l = l3;
                            n = n2;
                        }
                    }
                }
            } else {
                n = 10;
                if (this.mFirstOnSuccessTime == 0L) {
                    long l4;
                    this.mFirstOnSuccessTime = SystemClock.elapsedRealtime();
                    l = l4 = this.mFirstOnSuccessTime - this.mFirstRequestTime;
                    if (sDebug) {
                        charSequence = TAG;
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append("Service responded nothing in ");
                        ((StringBuilder)charSequence2).append(TimeUtils.formatDuration(l4));
                        Log.d((String)charSequence, ((StringBuilder)charSequence2).toString());
                        l = l4;
                    }
                }
                try {
                    this.mCallback.onSuccess();
                }
                catch (RemoteException remoteException) {
                    String string2 = TAG;
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("Error reporting success: ");
                    ((StringBuilder)charSequence2).append(remoteException);
                    Log.e(string2, ((StringBuilder)charSequence2).toString());
                }
            }
            Helper.logResponse(n, this.mServicePackageName, this.componentName, this.mSessionId, l);
        }

        public void requestHideFillUi() throws RemoteException {
            this.mClient.requestHideFillUi(this.mSessionId, this.mFocusedId);
        }

        public void requestShowFillUi(int n, int n2, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
            if (this.mCancellationSignal.isCanceled()) {
                if (sVerbose) {
                    Log.v(TAG, "requestShowFillUi() not showing because request is cancelled");
                }
                return;
            }
            this.mClient.requestShowFillUi(this.mSessionId, this.mFocusedId, n, n2, rect, iAutofillWindowPresenter);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setFillWindow(FillWindow fillWindow) {
            Object object = this.mLock;
            synchronized (object) {
                this.mFillWindow = fillWindow;
                return;
            }
        }

        @Retention(value=RetentionPolicy.SOURCE)
        static @interface ReportEvent {
        }

    }

}

