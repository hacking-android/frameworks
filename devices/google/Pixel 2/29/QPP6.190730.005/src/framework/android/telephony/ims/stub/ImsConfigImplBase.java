/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.stub._$$Lambda$ImsConfigImplBase$GAuYvQ8qBc7KgCJhNp4Pt4j5t_0;
import android.telephony.ims.stub._$$Lambda$ImsConfigImplBase$yL4863k_FoQyqg_FX2mWsLMqbyA;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.function.Consumer;

@SystemApi
public class ImsConfigImplBase {
    public static final int CONFIG_RESULT_FAILED = 1;
    public static final int CONFIG_RESULT_SUCCESS = 0;
    public static final int CONFIG_RESULT_UNKNOWN = -1;
    private static final String TAG = "ImsConfigImplBase";
    private final RemoteCallbackList<IImsConfigCallback> mCallbacks = new RemoteCallbackList();
    ImsConfigStub mImsConfigStub = new ImsConfigStub(this);

    public ImsConfigImplBase() {
    }

    public ImsConfigImplBase(Context context) {
    }

    private void addImsConfigCallback(IImsConfigCallback iImsConfigCallback) {
        this.mCallbacks.register(iImsConfigCallback);
    }

    static /* synthetic */ void lambda$notifyConfigChanged$0(int n, int n2, IImsConfigCallback iImsConfigCallback) {
        try {
            iImsConfigCallback.onIntConfigChanged(n, n2);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "notifyConfigChanged(int): dead binder in notify, skipping.");
        }
    }

    static /* synthetic */ void lambda$notifyConfigChanged$1(int n, String string2, IImsConfigCallback iImsConfigCallback) {
        try {
            iImsConfigCallback.onStringConfigChanged(n, string2);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "notifyConfigChanged(string): dead binder in notify, skipping.");
        }
    }

    private final void notifyConfigChanged(int n, int n2) {
        RemoteCallbackList<IImsConfigCallback> remoteCallbackList = this.mCallbacks;
        if (remoteCallbackList == null) {
            return;
        }
        remoteCallbackList.broadcast(new _$$Lambda$ImsConfigImplBase$yL4863k_FoQyqg_FX2mWsLMqbyA(n, n2));
    }

    private void notifyConfigChanged(int n, String string2) {
        RemoteCallbackList<IImsConfigCallback> remoteCallbackList = this.mCallbacks;
        if (remoteCallbackList == null) {
            return;
        }
        remoteCallbackList.broadcast(new _$$Lambda$ImsConfigImplBase$GAuYvQ8qBc7KgCJhNp4Pt4j5t_0(n, string2));
    }

    private void removeImsConfigCallback(IImsConfigCallback iImsConfigCallback) {
        this.mCallbacks.unregister(iImsConfigCallback);
    }

    public int getConfigInt(int n) {
        return -1;
    }

    public String getConfigString(int n) {
        return null;
    }

    public IImsConfig getIImsConfig() {
        return this.mImsConfigStub;
    }

    public final void notifyProvisionedValueChanged(int n, int n2) {
        try {
            this.mImsConfigStub.updateCachedValue(n, n2, true);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "notifyProvisionedValueChanged(int): Framework connection is dead.");
        }
    }

    public final void notifyProvisionedValueChanged(int n, String string2) {
        try {
            this.mImsConfigStub.updateCachedValue(n, string2, true);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "notifyProvisionedValueChanged(string): Framework connection is dead.");
        }
    }

    public int setConfig(int n, int n2) {
        return 1;
    }

    public int setConfig(int n, String string2) {
        return 1;
    }

    @VisibleForTesting
    public static class ImsConfigStub
    extends IImsConfig.Stub {
        WeakReference<ImsConfigImplBase> mImsConfigImplBaseWeakReference;
        private HashMap<Integer, Integer> mProvisionedIntValue = new HashMap();
        private HashMap<Integer, String> mProvisionedStringValue = new HashMap();

        @VisibleForTesting
        public ImsConfigStub(ImsConfigImplBase imsConfigImplBase) {
            this.mImsConfigImplBaseWeakReference = new WeakReference<ImsConfigImplBase>(imsConfigImplBase);
        }

        private ImsConfigImplBase getImsConfigImpl() throws RemoteException {
            ImsConfigImplBase imsConfigImplBase = (ImsConfigImplBase)this.mImsConfigImplBaseWeakReference.get();
            if (imsConfigImplBase != null) {
                return imsConfigImplBase;
            }
            throw new RemoteException("Fail to get ImsConfigImpl");
        }

        private void notifyImsConfigChanged(int n, int n2) throws RemoteException {
            this.getImsConfigImpl().notifyConfigChanged(n, n2);
        }

        private void notifyImsConfigChanged(int n, String string2) throws RemoteException {
            this.getImsConfigImpl().notifyConfigChanged(n, string2);
        }

        @Override
        public void addImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException {
            this.getImsConfigImpl().addImsConfigCallback(iImsConfigCallback);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int getConfigInt(int n) throws RemoteException {
            synchronized (this) {
                block5 : {
                    if (!this.mProvisionedIntValue.containsKey(n)) break block5;
                    return this.mProvisionedIntValue.get(n);
                }
                int n2 = this.getImsConfigImpl().getConfigInt(n);
                if (n2 == -1) return n2;
                this.updateCachedValue(n, n2, false);
                return n2;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String getConfigString(int n) throws RemoteException {
            synchronized (this) {
                block5 : {
                    if (!this.mProvisionedIntValue.containsKey(n)) break block5;
                    return this.mProvisionedStringValue.get(n);
                }
                String string2 = this.getImsConfigImpl().getConfigString(n);
                if (string2 == null) return string2;
                this.updateCachedValue(n, string2, false);
                return string2;
            }
        }

        @Override
        public void removeImsConfigCallback(IImsConfigCallback iImsConfigCallback) throws RemoteException {
            this.getImsConfigImpl().removeImsConfigCallback(iImsConfigCallback);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int setConfigInt(int n, int n2) throws RemoteException {
            synchronized (this) {
                this.mProvisionedIntValue.remove(n);
                int n3 = this.getImsConfigImpl().setConfig(n, n2);
                if (n3 == 0) {
                    this.updateCachedValue(n, n2, true);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Set provision value of ");
                    stringBuilder.append(n);
                    stringBuilder.append(" to ");
                    stringBuilder.append(n2);
                    stringBuilder.append(" failed with error code ");
                    stringBuilder.append(n3);
                    Log.d(ImsConfigImplBase.TAG, stringBuilder.toString());
                }
                return n3;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int setConfigString(int n, String string2) throws RemoteException {
            synchronized (this) {
                this.mProvisionedStringValue.remove(n);
                int n2 = this.getImsConfigImpl().setConfig(n, string2);
                if (n2 == 0) {
                    this.updateCachedValue(n, string2, true);
                }
                return n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void updateCachedValue(int n, int n2, boolean bl) throws RemoteException {
            synchronized (this) {
                this.mProvisionedIntValue.put(n, n2);
                if (bl) {
                    this.notifyImsConfigChanged(n, n2);
                }
                return;
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void updateCachedValue(int n, String string2, boolean bl) throws RemoteException {
            synchronized (this) {
                void var3_3;
                this.mProvisionedStringValue.put(n, string2);
                if (var3_3 != false) {
                    this.notifyImsConfigChanged(n, string2);
                }
                return;
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SetConfigResult {
    }

}

