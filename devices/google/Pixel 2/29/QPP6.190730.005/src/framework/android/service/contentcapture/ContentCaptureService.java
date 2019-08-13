/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.contentcapture.-$
 *  android.service.contentcapture.-$$Lambda
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$1
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$1$NhSHlL57JqxWNJ8QcsuGxEhxv1Y
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$1$PaMsQkJwdUJ1lCgOOaLG9Bm09t8
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$1$V1mxGgTDjVVHroIjJrHvYfUHCKE
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$1$iP7RXM_Va9lafd6bT9eXRx_D47Q
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$1$jkZQ77YuBlPDClOdklQb8tj8Kpw
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$1$sJuAS4AaQcXaSFkQpSEmVLBqyvw
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$1$wPMOb7AM5r-kHmuyl3SBSylaH1A
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$2
 *  android.service.contentcapture.-$$Lambda$ContentCaptureService$2$nqaNcni5MOtmyGkMJfxu_qUHOk4
 */
package android.service.contentcapture;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.contentcapture.-$;
import android.service.contentcapture.ActivityEvent;
import android.service.contentcapture.FlushMetrics;
import android.service.contentcapture.IContentCaptureService;
import android.service.contentcapture.IContentCaptureServiceCallback;
import android.service.contentcapture.SnapshotData;
import android.service.contentcapture._$$Lambda$ContentCaptureService$1$NhSHlL57JqxWNJ8QcsuGxEhxv1Y;
import android.service.contentcapture._$$Lambda$ContentCaptureService$1$PaMsQkJwdUJ1lCgOOaLG9Bm09t8;
import android.service.contentcapture._$$Lambda$ContentCaptureService$1$V1mxGgTDjVVHroIjJrHvYfUHCKE;
import android.service.contentcapture._$$Lambda$ContentCaptureService$1$iP7RXM_Va9lafd6bT9eXRx_D47Q;
import android.service.contentcapture._$$Lambda$ContentCaptureService$1$jkZQ77YuBlPDClOdklQb8tj8Kpw;
import android.service.contentcapture._$$Lambda$ContentCaptureService$1$sJuAS4AaQcXaSFkQpSEmVLBqyvw;
import android.service.contentcapture._$$Lambda$ContentCaptureService$1$wPMOb7AM5r_kHmuyl3SBSylaH1A;
import android.service.contentcapture._$$Lambda$ContentCaptureService$2$nqaNcni5MOtmyGkMJfxu_qUHOk4;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.StatsLog;
import android.view.contentcapture.ContentCaptureCondition;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureEvent;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.ContentCaptureSessionId;
import android.view.contentcapture.DataRemovalRequest;
import android.view.contentcapture.IContentCaptureDirectManager;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@SystemApi
public abstract class ContentCaptureService
extends Service {
    public static final String SERVICE_INTERFACE = "android.service.contentcapture.ContentCaptureService";
    public static final String SERVICE_META_DATA = "android.content_capture";
    private static final String TAG = ContentCaptureService.class.getSimpleName();
    private IContentCaptureServiceCallback mCallback;
    private long mCallerMismatchTimeout = 1000L;
    private final IContentCaptureDirectManager mClientInterface = new IContentCaptureDirectManager.Stub(){

        static /* synthetic */ void lambda$sendEvents$0(Object object, int n, ParceledListSlice parceledListSlice, int n2, ContentCaptureOptions contentCaptureOptions) {
            ((ContentCaptureService)object).handleSendEvents(n, parceledListSlice, n2, contentCaptureOptions);
        }

        @Override
        public void sendEvents(ParceledListSlice parceledListSlice, int n, ContentCaptureOptions contentCaptureOptions) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ContentCaptureService$2$nqaNcni5MOtmyGkMJfxu_qUHOk4.INSTANCE, ContentCaptureService.this, Binder.getCallingUid(), parceledListSlice, n, contentCaptureOptions));
        }
    };
    private Handler mHandler;
    private long mLastCallerMismatchLog;
    private final IContentCaptureService mServerInterface = new IContentCaptureService.Stub(){

        static /* synthetic */ void lambda$onActivityEvent$6(Object object, ActivityEvent activityEvent) {
            ((ContentCaptureService)object).handleOnActivityEvent(activityEvent);
        }

        static /* synthetic */ void lambda$onActivitySnapshot$3(Object object, int n, SnapshotData snapshotData) {
            ((ContentCaptureService)object).handleOnActivitySnapshot(n, snapshotData);
        }

        static /* synthetic */ void lambda$onConnected$0(Object object, IBinder iBinder) {
            ((ContentCaptureService)object).handleOnConnected(iBinder);
        }

        static /* synthetic */ void lambda$onDataRemovalRequest$5(Object object, DataRemovalRequest dataRemovalRequest) {
            ((ContentCaptureService)object).handleOnDataRemovalRequest(dataRemovalRequest);
        }

        static /* synthetic */ void lambda$onDisconnected$1(Object object) {
            ((ContentCaptureService)object).handleOnDisconnected();
        }

        static /* synthetic */ void lambda$onSessionFinished$4(Object object, int n) {
            ((ContentCaptureService)object).handleFinishSession(n);
        }

        static /* synthetic */ void lambda$onSessionStarted$2(Object object, ContentCaptureContext contentCaptureContext, int n, int n2, IResultReceiver iResultReceiver, int n3) {
            ((ContentCaptureService)object).handleOnCreateSession(contentCaptureContext, n, n2, iResultReceiver, n3);
        }

        @Override
        public void onActivityEvent(ActivityEvent activityEvent) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ContentCaptureService$1$V1mxGgTDjVVHroIjJrHvYfUHCKE.INSTANCE, ContentCaptureService.this, activityEvent));
        }

        @Override
        public void onActivitySnapshot(int n, SnapshotData snapshotData) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ContentCaptureService$1$NhSHlL57JqxWNJ8QcsuGxEhxv1Y.INSTANCE, ContentCaptureService.this, n, snapshotData));
        }

        @Override
        public void onConnected(IBinder iBinder, boolean bl, boolean bl2) {
            ContentCaptureHelper.sVerbose = bl;
            ContentCaptureHelper.sDebug = bl2;
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ContentCaptureService$1$iP7RXM_Va9lafd6bT9eXRx_D47Q.INSTANCE, ContentCaptureService.this, iBinder));
        }

        @Override
        public void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ContentCaptureService$1$sJuAS4AaQcXaSFkQpSEmVLBqyvw.INSTANCE, ContentCaptureService.this, dataRemovalRequest));
        }

        @Override
        public void onDisconnected() {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ContentCaptureService$1$wPMOb7AM5r_kHmuyl3SBSylaH1A.INSTANCE, ContentCaptureService.this));
        }

        @Override
        public void onSessionFinished(int n) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ContentCaptureService$1$jkZQ77YuBlPDClOdklQb8tj8Kpw.INSTANCE, ContentCaptureService.this, n));
        }

        @Override
        public void onSessionStarted(ContentCaptureContext contentCaptureContext, int n, int n2, IResultReceiver iResultReceiver, int n3) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ContentCaptureService$1$PaMsQkJwdUJ1lCgOOaLG9Bm09t8.INSTANCE, ContentCaptureService.this, contentCaptureContext, n, n2, iResultReceiver, n3));
        }
    };
    private final SparseIntArray mSessionUids = new SparseIntArray();

    private void handleFinishSession(int n) {
        this.mSessionUids.delete(n);
        this.onDestroyContentCaptureSession(new ContentCaptureSessionId(n));
    }

    private boolean handleIsRightCallerFor(ContentCaptureEvent object, int n) {
        int n2 = ((ContentCaptureEvent)object).getType();
        n2 = n2 != -2 && n2 != -1 ? ((ContentCaptureEvent)object).getSessionId() : ((ContentCaptureEvent)object).getParentSessionId();
        if (this.mSessionUids.indexOfKey(n2) < 0) {
            if (ContentCaptureHelper.sVerbose) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleIsRightCallerFor(");
                stringBuilder.append(object);
                stringBuilder.append("): no session for ");
                stringBuilder.append(n2);
                stringBuilder.append(": ");
                stringBuilder.append(this.mSessionUids);
                Log.v(string2, stringBuilder.toString());
            }
            return false;
        }
        int n3 = this.mSessionUids.get(n2);
        if (n3 != n) {
            String string3 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("invalid call from UID ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(": session ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" belongs to ");
            ((StringBuilder)object).append(n3);
            Log.e(string3, ((StringBuilder)object).toString());
            long l = System.currentTimeMillis();
            if (l - this.mLastCallerMismatchLog > this.mCallerMismatchTimeout) {
                StatsLog.write(206, this.getPackageManager().getNameForUid(n3), this.getPackageManager().getNameForUid(n));
                this.mLastCallerMismatchLog = l;
            }
            return false;
        }
        return true;
    }

    private void handleOnActivityEvent(ActivityEvent activityEvent) {
        this.onActivityEvent(activityEvent);
    }

    private void handleOnActivitySnapshot(int n, SnapshotData snapshotData) {
        this.onActivitySnapshot(new ContentCaptureSessionId(n), snapshotData);
    }

    private void handleOnConnected(IBinder iBinder) {
        this.mCallback = IContentCaptureServiceCallback.Stub.asInterface(iBinder);
        this.onConnected();
    }

    private void handleOnCreateSession(ContentCaptureContext contentCaptureContext, int n, int n2, IResultReceiver iResultReceiver, int n3) {
        this.mSessionUids.put(n, n2);
        this.onCreateContentCaptureSession(contentCaptureContext, new ContentCaptureSessionId(n));
        int n4 = contentCaptureContext.getFlags();
        n = 0;
        if ((n4 & 2) != 0) {
            n = 0 | 32;
        }
        n2 = n;
        if ((n4 & 1) != 0) {
            n2 = n | 64;
        }
        if (n2 != 0) {
            n3 = n2 | 4;
        }
        ContentCaptureService.setClientState(iResultReceiver, n3, this.mClientInterface.asBinder());
    }

    private void handleOnDataRemovalRequest(DataRemovalRequest dataRemovalRequest) {
        this.onDataRemovalRequest(dataRemovalRequest);
    }

    private void handleOnDisconnected() {
        this.onDisconnected();
        this.mCallback = null;
    }

    private void handleSendEvents(int n, ParceledListSlice<ContentCaptureEvent> parceledListSlice, int n2, ContentCaptureOptions contentCaptureOptions) {
        List list = parceledListSlice.getList();
        if (list.isEmpty()) {
            Log.w(TAG, "handleSendEvents() received empty list of events");
            return;
        }
        FlushMetrics flushMetrics = new FlushMetrics();
        parceledListSlice = null;
        int n3 = 0;
        ContentCaptureSessionId contentCaptureSessionId = null;
        for (int i = 0; i < list.size(); ++i) {
            ContentCaptureEvent contentCaptureEvent = (ContentCaptureEvent)list.get(i);
            if (!this.handleIsRightCallerFor(contentCaptureEvent, n)) continue;
            int n4 = contentCaptureEvent.getSessionId();
            int n5 = n3;
            if (n4 != n3) {
                contentCaptureSessionId = new ContentCaptureSessionId(n4);
                if (i != 0) {
                    this.writeFlushMetrics(n4, (ComponentName)((Object)parceledListSlice), flushMetrics, contentCaptureOptions, n2);
                    flushMetrics.reset();
                }
                n5 = n4;
            }
            ContentCaptureContext contentCaptureContext = contentCaptureEvent.getContentCaptureContext();
            Parcelable parcelable = parceledListSlice;
            if (parceledListSlice == null) {
                parcelable = parceledListSlice;
                if (contentCaptureContext != null) {
                    parcelable = contentCaptureContext.getActivityComponent();
                }
            }
            if ((n3 = contentCaptureEvent.getType()) != -2) {
                if (n3 != -1) {
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 3) {
                                this.onContentCaptureEvent(contentCaptureSessionId, contentCaptureEvent);
                                n3 = n5;
                                parceledListSlice = parcelable;
                                continue;
                            }
                            this.onContentCaptureEvent(contentCaptureSessionId, contentCaptureEvent);
                            ++flushMetrics.viewTextChangedCount;
                            n3 = n5;
                            parceledListSlice = parcelable;
                            continue;
                        }
                        this.onContentCaptureEvent(contentCaptureSessionId, contentCaptureEvent);
                        ++flushMetrics.viewDisappearedCount;
                        n3 = n5;
                        parceledListSlice = parcelable;
                        continue;
                    }
                    this.onContentCaptureEvent(contentCaptureSessionId, contentCaptureEvent);
                    ++flushMetrics.viewAppearedCount;
                    n3 = n5;
                    parceledListSlice = parcelable;
                    continue;
                }
                contentCaptureContext.setParentSessionId(contentCaptureEvent.getParentSessionId());
                this.mSessionUids.put(n4, n);
                this.onCreateContentCaptureSession(contentCaptureContext, contentCaptureSessionId);
                ++flushMetrics.sessionStarted;
                n3 = n5;
                parceledListSlice = parcelable;
                continue;
            }
            this.mSessionUids.delete(n4);
            this.onDestroyContentCaptureSession(contentCaptureSessionId);
            ++flushMetrics.sessionFinished;
            parceledListSlice = parcelable;
            n3 = n5;
        }
        this.writeFlushMetrics(n3, (ComponentName)((Object)parceledListSlice), flushMetrics, contentCaptureOptions, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setClientState(IResultReceiver object, int n, IBinder object2) {
        RemoteException remoteException2;
        block4 : {
            block3 : {
                if (object2 != null) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putBinder("binder", (IBinder)object2);
                        object2 = bundle;
                        break block3;
                    }
                    catch (RemoteException remoteException2) {
                        break block4;
                    }
                }
                object2 = null;
            }
            object.send(n, (Bundle)object2);
            return;
        }
        object2 = TAG;
        object = new StringBuilder();
        ((StringBuilder)object).append("Error async reporting result to client: ");
        ((StringBuilder)object).append(remoteException2);
        Slog.w((String)object2, ((StringBuilder)object).toString());
    }

    private void writeFlushMetrics(int n, ComponentName object, FlushMetrics object2, ContentCaptureOptions contentCaptureOptions, int n2) {
        IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mCallback;
        if (iContentCaptureServiceCallback == null) {
            Log.w(TAG, "writeSessionFlush(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.writeSessionFlush(n, (ComponentName)object, (FlushMetrics)object2, contentCaptureOptions, n2);
        }
        catch (RemoteException remoteException) {
            object = TAG;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("failed to write flush metrics: ");
            ((StringBuilder)object2).append(remoteException);
            Log.e((String)object, ((StringBuilder)object2).toString());
        }
    }

    public final void disableSelf() {
        IContentCaptureServiceCallback iContentCaptureServiceCallback;
        if (ContentCaptureHelper.sDebug) {
            Log.d(TAG, "disableSelf()");
        }
        if ((iContentCaptureServiceCallback = this.mCallback) == null) {
            Log.w(TAG, "disableSelf(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.disableSelf();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.print("Debug: ");
        printWriter.print(ContentCaptureHelper.sDebug);
        printWriter.print(" Verbose: ");
        printWriter.println(ContentCaptureHelper.sVerbose);
        int n = this.mSessionUids.size();
        printWriter.print("Number sessions: ");
        printWriter.println(n);
        if (n > 0) {
            for (int i = 0; i < n; ++i) {
                printWriter.print("  ");
                printWriter.print(this.mSessionUids.keyAt(i));
                printWriter.print(": uid=");
                printWriter.println(this.mSessionUids.valueAt(i));
            }
        }
    }

    public void onActivityEvent(ActivityEvent activityEvent) {
        if (ContentCaptureHelper.sVerbose) {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onActivityEvent(): ");
            stringBuilder.append(activityEvent);
            Log.v(string2, stringBuilder.toString());
        }
    }

    public void onActivitySnapshot(ContentCaptureSessionId contentCaptureSessionId, SnapshotData object) {
        if (ContentCaptureHelper.sVerbose) {
            String string2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("onActivitySnapshot(id=");
            ((StringBuilder)object).append(contentCaptureSessionId);
            ((StringBuilder)object).append(")");
            Log.v(string2, ((StringBuilder)object).toString());
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mServerInterface.asBinder();
        }
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tried to bind to wrong intent (should be android.service.contentcapture.ContentCaptureService: ");
        stringBuilder.append(intent);
        Log.w(string2, stringBuilder.toString());
        return null;
    }

    public void onConnected() {
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bound to ");
        stringBuilder.append(this.getClass().getName());
        Slog.i(string2, stringBuilder.toString());
    }

    public void onContentCaptureEvent(ContentCaptureSessionId contentCaptureSessionId, ContentCaptureEvent object) {
        if (ContentCaptureHelper.sVerbose) {
            object = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onContentCaptureEventsRequest(id=");
            stringBuilder.append(contentCaptureSessionId);
            stringBuilder.append(")");
            Log.v((String)object, stringBuilder.toString());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    public void onCreateContentCaptureSession(ContentCaptureContext contentCaptureContext, ContentCaptureSessionId contentCaptureSessionId) {
        if (ContentCaptureHelper.sVerbose) {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateContentCaptureSession(id=");
            stringBuilder.append(contentCaptureSessionId);
            stringBuilder.append(", ctx=");
            stringBuilder.append(contentCaptureContext);
            stringBuilder.append(")");
            Log.v(string2, stringBuilder.toString());
        }
    }

    public void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onDataRemovalRequest()");
        }
    }

    public void onDestroyContentCaptureSession(ContentCaptureSessionId contentCaptureSessionId) {
        if (ContentCaptureHelper.sVerbose) {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDestroyContentCaptureSession(id=");
            stringBuilder.append(contentCaptureSessionId);
            stringBuilder.append(")");
            Log.v(string2, stringBuilder.toString());
        }
    }

    public void onDisconnected() {
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unbinding from ");
        stringBuilder.append(this.getClass().getName());
        Slog.i(string2, stringBuilder.toString());
    }

    public final void setContentCaptureConditions(String string2, Set<ContentCaptureCondition> set) {
        IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mCallback;
        if (iContentCaptureServiceCallback == null) {
            Log.w(TAG, "setContentCaptureConditions(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.setContentCaptureConditions(string2, ContentCaptureHelper.toList(set));
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public final void setContentCaptureWhitelist(Set<String> set, Set<ComponentName> set2) {
        IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mCallback;
        if (iContentCaptureServiceCallback == null) {
            Log.w(TAG, "setContentCaptureWhitelist(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.setContentCaptureWhitelist(ContentCaptureHelper.toList(set), ContentCaptureHelper.toList(set2));
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

}

