/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.SubscriptionManager;
import android.telephony.mbms.IMbmsStreamingSessionCallback;
import android.telephony.mbms.IStreamingServiceCallback;
import android.telephony.mbms.InternalStreamingServiceCallback;
import android.telephony.mbms.InternalStreamingSessionCallback;
import android.telephony.mbms.MbmsStreamingSessionCallback;
import android.telephony.mbms.MbmsUtils;
import android.telephony.mbms.ServiceInfo;
import android.telephony.mbms.StreamingService;
import android.telephony.mbms.StreamingServiceCallback;
import android.telephony.mbms.StreamingServiceInfo;
import android.telephony.mbms.vendor.IMbmsStreamingService;
import android.util.ArraySet;
import android.util.Log;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MbmsStreamingSession
implements AutoCloseable {
    private static final String LOG_TAG = "MbmsStreamingSession";
    @SystemApi
    public static final String MBMS_STREAMING_SERVICE_ACTION = "android.telephony.action.EmbmsStreaming";
    public static final String MBMS_STREAMING_SERVICE_OVERRIDE_METADATA = "mbms-streaming-service-override";
    private static AtomicBoolean sIsInitialized = new AtomicBoolean(false);
    private final Context mContext;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        @Override
        public void binderDied() {
            sIsInitialized.set(false);
            MbmsStreamingSession.this.sendErrorToApp(3, "Received death notification");
        }
    };
    private InternalStreamingSessionCallback mInternalCallback;
    private Set<StreamingService> mKnownActiveStreamingServices = new ArraySet<StreamingService>();
    private AtomicReference<IMbmsStreamingService> mService = new AtomicReference<Object>(null);
    private int mSubscriptionId = -1;

    private MbmsStreamingSession(Context context, Executor executor, int n, MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        this.mContext = context;
        this.mSubscriptionId = n;
        this.mInternalCallback = new InternalStreamingSessionCallback(mbmsStreamingSessionCallback, executor);
    }

    private int bindAndInitialize() {
        return MbmsUtils.startBinding(this.mContext, MBMS_STREAMING_SERVICE_ACTION, new ServiceConnection(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onServiceConnected(ComponentName object, IBinder iBinder) {
                block5 : {
                    block6 : {
                        object = IMbmsStreamingService.Stub.asInterface(iBinder);
                        int n = object.initialize(MbmsStreamingSession.this.mInternalCallback, MbmsStreamingSession.this.mSubscriptionId);
                        if (n == -1) break block5;
                        if (n == 0) break block6;
                        MbmsStreamingSession.this.sendErrorToApp(n, "Error returned during initialization");
                        sIsInitialized.set(false);
                        return;
                    }
                    try {
                        object.asBinder().linkToDeath(MbmsStreamingSession.this.mDeathRecipient, 0);
                        MbmsStreamingSession.this.mService.set(object);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        MbmsStreamingSession.this.sendErrorToApp(3, "Middleware lost during initialization");
                        sIsInitialized.set(false);
                        return;
                    }
                }
                MbmsStreamingSession.this.close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
                catch (RuntimeException runtimeException) {
                    Log.e(MbmsStreamingSession.LOG_TAG, "Runtime exception during initialization");
                    MbmsStreamingSession.this.sendErrorToApp(103, runtimeException.toString());
                    sIsInitialized.set(false);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e(MbmsStreamingSession.LOG_TAG, "Service died before initialization");
                    MbmsStreamingSession.this.sendErrorToApp(103, remoteException.toString());
                    sIsInitialized.set(false);
                    return;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                sIsInitialized.set(false);
                MbmsStreamingSession.this.mService.set(null);
            }
        });
    }

    public static MbmsStreamingSession create(Context object, Executor executor, final int n, MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        if (sIsInitialized.compareAndSet(false, true)) {
            object = new MbmsStreamingSession((Context)object, executor, n, mbmsStreamingSessionCallback);
            n = MbmsStreamingSession.super.bindAndInitialize();
            if (n != 0) {
                sIsInitialized.set(false);
                executor.execute(new Runnable(){

                    @Override
                    public void run() {
                        MbmsStreamingSessionCallback.this.onError(n, null);
                    }
                });
                return null;
            }
            return object;
        }
        throw new IllegalStateException("Cannot create two instances of MbmsStreamingSession");
    }

    public static MbmsStreamingSession create(Context context, Executor executor, MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        return MbmsStreamingSession.create(context, executor, SubscriptionManager.getDefaultSubscriptionId(), mbmsStreamingSessionCallback);
    }

    private void sendErrorToApp(int n, String string2) {
        try {
            this.mInternalCallback.onError(n, string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void close() {
        Object object;
        block5 : {
            object = this.mService.get();
            if (object != null) break block5;
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mInternalCallback.stop();
            return;
        }
        try {
            object.dispose(this.mSubscriptionId);
            object = this.mKnownActiveStreamingServices.iterator();
            while (object.hasNext()) {
                ((StreamingService)object.next()).getCallback().stop();
            }
            this.mKnownActiveStreamingServices.clear();
        }
        catch (Throwable throwable) {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mInternalCallback.stop();
            throw throwable;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        this.mService.set(null);
        sIsInitialized.set(false);
        this.mInternalCallback.stop();
    }

    public void onStreamingServiceStopped(StreamingService streamingService) {
        this.mKnownActiveStreamingServices.remove(streamingService);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestUpdateStreamingServices(List<String> object) {
        IMbmsStreamingService iMbmsStreamingService = this.mService.get();
        if (iMbmsStreamingService == null) throw new IllegalStateException("Middleware not yet bound");
        try {
            int n = iMbmsStreamingService.requestUpdateStreamingServices(this.mSubscriptionId, (List<String>)object);
            if (n != -1) {
                if (n == 0) return;
                this.sendErrorToApp(n, null);
                return;
            }
            this.close();
            object = new IllegalStateException("Middleware must not return an unknown error code");
            throw object;
        }
        catch (RemoteException remoteException) {
            Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            this.sendErrorToApp(3, null);
        }
    }

    public StreamingService startStreaming(StreamingServiceInfo object, Executor object2, StreamingServiceCallback object3) {
        IMbmsStreamingService iMbmsStreamingService = this.mService.get();
        if (iMbmsStreamingService != null) {
            block5 : {
                block6 : {
                    int n;
                    object3 = new InternalStreamingServiceCallback((StreamingServiceCallback)object3, (Executor)object2);
                    object2 = new StreamingService(this.mSubscriptionId, iMbmsStreamingService, this, (StreamingServiceInfo)object, (InternalStreamingServiceCallback)object3);
                    this.mKnownActiveStreamingServices.add((StreamingService)object2);
                    try {
                        n = iMbmsStreamingService.startStreaming(this.mSubscriptionId, ((ServiceInfo)object).getServiceId(), (IStreamingServiceCallback)object3);
                        if (n == -1) break block5;
                        if (n == 0) break block6;
                    }
                    catch (RemoteException remoteException) {
                        Log.w(LOG_TAG, "Remote process died");
                        this.mService.set(null);
                        sIsInitialized.set(false);
                        this.sendErrorToApp(3, null);
                        return null;
                    }
                    this.sendErrorToApp(n, null);
                    return null;
                }
                return object2;
            }
            this.close();
            object = new IllegalStateException("Middleware must not return an unknown error code");
            throw object;
        }
        throw new IllegalStateException("Middleware not yet bound");
    }

}

