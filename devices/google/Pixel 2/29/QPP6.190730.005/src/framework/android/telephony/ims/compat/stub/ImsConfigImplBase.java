/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.compat.stub;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import com.android.ims.ImsConfigListener;
import com.android.ims.internal.IImsConfig;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ImsConfigImplBase {
    private static final String TAG = "ImsConfigImplBase";
    ImsConfigStub mImsConfigStub;

    @UnsupportedAppUsage
    public ImsConfigImplBase(Context context) {
        this.mImsConfigStub = new ImsConfigStub(this, context);
    }

    public void getFeatureValue(int n, int n2, ImsConfigListener imsConfigListener) throws RemoteException {
    }

    @UnsupportedAppUsage
    public IImsConfig getIImsConfig() {
        return this.mImsConfigStub;
    }

    public String getProvisionedStringValue(int n) throws RemoteException {
        return null;
    }

    public int getProvisionedValue(int n) throws RemoteException {
        return -1;
    }

    public void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException {
    }

    public boolean getVolteProvisioned() throws RemoteException {
        return false;
    }

    public final void notifyProvisionedValueChanged(int n, int n2) {
        this.mImsConfigStub.updateCachedValue(n, n2, true);
    }

    public final void notifyProvisionedValueChanged(int n, String string2) {
        this.mImsConfigStub.updateCachedValue(n, string2, true);
    }

    public void setFeatureValue(int n, int n2, int n3, ImsConfigListener imsConfigListener) throws RemoteException {
    }

    public int setProvisionedStringValue(int n, String string2) throws RemoteException {
        return 1;
    }

    public int setProvisionedValue(int n, int n2) throws RemoteException {
        return 1;
    }

    public void setVideoQuality(int n, ImsConfigListener imsConfigListener) throws RemoteException {
    }

    @VisibleForTesting
    public static class ImsConfigStub
    extends IImsConfig.Stub {
        Context mContext;
        WeakReference<ImsConfigImplBase> mImsConfigImplBaseWeakReference;
        private HashMap<Integer, Integer> mProvisionedIntValue = new HashMap();
        private HashMap<Integer, String> mProvisionedStringValue = new HashMap();

        @VisibleForTesting
        public ImsConfigStub(ImsConfigImplBase imsConfigImplBase, Context context) {
            this.mContext = context;
            this.mImsConfigImplBaseWeakReference = new WeakReference<ImsConfigImplBase>(imsConfigImplBase);
        }

        private ImsConfigImplBase getImsConfigImpl() throws RemoteException {
            ImsConfigImplBase imsConfigImplBase = (ImsConfigImplBase)this.mImsConfigImplBaseWeakReference.get();
            if (imsConfigImplBase != null) {
                return imsConfigImplBase;
            }
            throw new RemoteException("Fail to get ImsConfigImpl");
        }

        private void sendImsConfigChangedIntent(int n, int n2) {
            this.sendImsConfigChangedIntent(n, Integer.toString(n2));
        }

        private void sendImsConfigChangedIntent(int n, String object) {
            Intent intent = new Intent("com.android.intent.action.IMS_CONFIG_CHANGED");
            intent.putExtra("item", n);
            intent.putExtra("value", (String)object);
            object = this.mContext;
            if (object != null) {
                ((Context)object).sendBroadcast(intent);
            }
        }

        @Override
        public void getFeatureValue(int n, int n2, ImsConfigListener imsConfigListener) throws RemoteException {
            this.getImsConfigImpl().getFeatureValue(n, n2, imsConfigListener);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String getProvisionedStringValue(int n) throws RemoteException {
            synchronized (this) {
                block5 : {
                    if (!this.mProvisionedIntValue.containsKey(n)) break block5;
                    return this.mProvisionedStringValue.get(n);
                }
                String string2 = this.getImsConfigImpl().getProvisionedStringValue(n);
                if (string2 == null) return string2;
                this.updateCachedValue(n, string2, false);
                return string2;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int getProvisionedValue(int n) throws RemoteException {
            synchronized (this) {
                block5 : {
                    if (!this.mProvisionedIntValue.containsKey(n)) break block5;
                    return this.mProvisionedIntValue.get(n);
                }
                int n2 = this.getImsConfigImpl().getProvisionedValue(n);
                if (n2 == -1) return n2;
                this.updateCachedValue(n, n2, false);
                return n2;
            }
        }

        @Override
        public void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException {
            this.getImsConfigImpl().getVideoQuality(imsConfigListener);
        }

        @Override
        public boolean getVolteProvisioned() throws RemoteException {
            return this.getImsConfigImpl().getVolteProvisioned();
        }

        @Override
        public void setFeatureValue(int n, int n2, int n3, ImsConfigListener imsConfigListener) throws RemoteException {
            this.getImsConfigImpl().setFeatureValue(n, n2, n3, imsConfigListener);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int setProvisionedStringValue(int n, String string2) throws RemoteException {
            synchronized (this) {
                this.mProvisionedStringValue.remove(n);
                int n2 = this.getImsConfigImpl().setProvisionedStringValue(n, string2);
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
        @Override
        public int setProvisionedValue(int n, int n2) throws RemoteException {
            synchronized (this) {
                this.mProvisionedIntValue.remove(n);
                int n3 = this.getImsConfigImpl().setProvisionedValue(n, n2);
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

        @Override
        public void setVideoQuality(int n, ImsConfigListener imsConfigListener) throws RemoteException {
            this.getImsConfigImpl().setVideoQuality(n, imsConfigListener);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void updateCachedValue(int n, int n2, boolean bl) {
            synchronized (this) {
                this.mProvisionedIntValue.put(n, n2);
                if (bl) {
                    this.sendImsConfigChangedIntent(n, n2);
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
        protected void updateCachedValue(int n, String string2, boolean bl) {
            synchronized (this) {
                void var3_3;
                this.mProvisionedStringValue.put(n, string2);
                if (var3_3 != false) {
                    this.sendImsConfigChangedIntent(n, string2);
                }
                return;
            }
        }
    }

}

