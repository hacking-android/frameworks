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
import android.telephony.mbms.GroupCall;
import android.telephony.mbms.GroupCallCallback;
import android.telephony.mbms.IGroupCallCallback;
import android.telephony.mbms.IMbmsGroupCallSessionCallback;
import android.telephony.mbms.InternalGroupCallCallback;
import android.telephony.mbms.InternalGroupCallSessionCallback;
import android.telephony.mbms.MbmsGroupCallSessionCallback;
import android.telephony.mbms.MbmsUtils;
import android.telephony.mbms.vendor.IMbmsGroupCallService;
import android.util.ArraySet;
import android.util.Log;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MbmsGroupCallSession
implements AutoCloseable {
    private static final String LOG_TAG = "MbmsGroupCallSession";
    @SystemApi
    public static final String MBMS_GROUP_CALL_SERVICE_ACTION = "android.telephony.action.EmbmsGroupCall";
    public static final String MBMS_GROUP_CALL_SERVICE_OVERRIDE_METADATA = "mbms-group-call-service-override";
    private static AtomicBoolean sIsInitialized = new AtomicBoolean(false);
    private final Context mContext;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        @Override
        public void binderDied() {
            sIsInitialized.set(false);
            MbmsGroupCallSession.this.mInternalCallback.onError(3, "Received death notification");
        }
    };
    private InternalGroupCallSessionCallback mInternalCallback;
    private Set<GroupCall> mKnownActiveGroupCalls = new ArraySet<GroupCall>();
    private AtomicReference<IMbmsGroupCallService> mService = new AtomicReference<Object>(null);
    private int mSubscriptionId;

    private MbmsGroupCallSession(Context context, Executor executor, int n, MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        this.mContext = context;
        this.mSubscriptionId = n;
        this.mInternalCallback = new InternalGroupCallSessionCallback(mbmsGroupCallSessionCallback, executor);
    }

    private int bindAndInitialize() {
        return MbmsUtils.startBinding(this.mContext, MBMS_GROUP_CALL_SERVICE_ACTION, new ServiceConnection(){

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
                        object = IMbmsGroupCallService.Stub.asInterface(iBinder);
                        int n = object.initialize(MbmsGroupCallSession.this.mInternalCallback, MbmsGroupCallSession.this.mSubscriptionId);
                        if (n == -1) break block5;
                        if (n == 0) break block6;
                        MbmsGroupCallSession.this.mInternalCallback.onError(n, "Error returned during initialization");
                        sIsInitialized.set(false);
                        return;
                    }
                    try {
                        object.asBinder().linkToDeath(MbmsGroupCallSession.this.mDeathRecipient, 0);
                        MbmsGroupCallSession.this.mService.set(object);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        MbmsGroupCallSession.this.mInternalCallback.onError(3, "Middleware lost during initialization");
                        sIsInitialized.set(false);
                        return;
                    }
                }
                MbmsGroupCallSession.this.close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
                catch (RuntimeException runtimeException) {
                    Log.e(MbmsGroupCallSession.LOG_TAG, "Runtime exception during initialization");
                    MbmsGroupCallSession.this.mInternalCallback.onError(103, runtimeException.toString());
                    sIsInitialized.set(false);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e(MbmsGroupCallSession.LOG_TAG, "Service died before initialization");
                    MbmsGroupCallSession.this.mInternalCallback.onError(103, remoteException.toString());
                    sIsInitialized.set(false);
                    return;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                sIsInitialized.set(false);
                MbmsGroupCallSession.this.mService.set(null);
            }
        });
    }

    public static MbmsGroupCallSession create(Context object, final int n, Executor executor, MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        if (sIsInitialized.compareAndSet(false, true)) {
            object = new MbmsGroupCallSession((Context)object, executor, n, mbmsGroupCallSessionCallback);
            n = MbmsGroupCallSession.super.bindAndInitialize();
            if (n != 0) {
                sIsInitialized.set(false);
                executor.execute(new Runnable(){

                    @Override
                    public void run() {
                        MbmsGroupCallSessionCallback.this.onError(n, null);
                    }
                });
                return null;
            }
            return object;
        }
        throw new IllegalStateException("Cannot create two instances of MbmsGroupCallSession");
    }

    public static MbmsGroupCallSession create(Context context, Executor executor, MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        return MbmsGroupCallSession.create(context, SubscriptionManager.getDefaultSubscriptionId(), executor, mbmsGroupCallSessionCallback);
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
            object = this.mKnownActiveGroupCalls.iterator();
            while (object.hasNext()) {
                ((GroupCall)object.next()).getCallback().stop();
            }
            this.mKnownActiveGroupCalls.clear();
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

    public void onGroupCallStopped(GroupCall groupCall) {
        this.mKnownActiveGroupCalls.remove(groupCall);
    }

    public GroupCall startGroupCall(long l, List<Integer> object, List<Integer> list, Executor object2, GroupCallCallback object3) {
        IMbmsGroupCallService iMbmsGroupCallService = this.mService.get();
        if (iMbmsGroupCallService != null) {
            block5 : {
                block6 : {
                    int n;
                    object2 = new InternalGroupCallCallback((GroupCallCallback)object3, (Executor)object2);
                    object3 = new GroupCall(this.mSubscriptionId, iMbmsGroupCallService, this, l, (InternalGroupCallCallback)object2);
                    this.mKnownActiveGroupCalls.add((GroupCall)object3);
                    try {
                        n = iMbmsGroupCallService.startGroupCall(this.mSubscriptionId, l, (List)object, list, (IGroupCallCallback)object2);
                        if (n == -1) break block5;
                        if (n == 0) break block6;
                    }
                    catch (RemoteException remoteException) {
                        Log.w(LOG_TAG, "Remote process died");
                        this.mService.set(null);
                        sIsInitialized.set(false);
                        this.mInternalCallback.onError(3, null);
                        return null;
                    }
                    this.mInternalCallback.onError(n, null);
                    return null;
                }
                return object3;
            }
            this.close();
            object = new IllegalStateException("Middleware must not return an unknown error code");
            throw object;
        }
        throw new IllegalStateException("Middleware not yet bound");
    }

}

