/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.telephony.ims.aidl.IImsServiceController
 *  android.telephony.ims.aidl.IImsServiceController$Stub
 *  android.telephony.ims.stub.ImsFeatureConfiguration
 *  android.telephony.ims.stub.ImsFeatureConfiguration$FeatureSlotPair
 *  android.util.Log
 */
package com.android.internal.telephony.ims;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.telephony.ims.aidl.IImsServiceController;
import android.telephony.ims.stub.ImsFeatureConfiguration;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImsServiceFeatureQueryManager {
    private final Map<ComponentName, ImsServiceFeatureQuery> mActiveQueries = new HashMap<ComponentName, ImsServiceFeatureQuery>();
    private final Context mContext;
    private final Listener mListener;
    private final Object mLock = new Object();

    public ImsServiceFeatureQueryManager(Context context, Listener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isQueryInProgress() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mActiveQueries.isEmpty()) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean startQuery(ComponentName componentName, String string) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mActiveQueries.containsKey((Object)componentName)) {
                return true;
            }
            ImsServiceFeatureQuery imsServiceFeatureQuery = new ImsServiceFeatureQuery(componentName, string);
            this.mActiveQueries.put(componentName, imsServiceFeatureQuery);
            return imsServiceFeatureQuery.start();
        }
    }

    private final class ImsServiceFeatureQuery
    implements ServiceConnection {
        private static final String LOG_TAG = "ImsServiceFeatureQuery";
        private final String mIntentFilter;
        private final ComponentName mName;

        ImsServiceFeatureQuery(ComponentName componentName, String string) {
            this.mName = componentName;
            this.mIntentFilter = string;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void cleanup() {
            ImsServiceFeatureQueryManager.this.mContext.unbindService((ServiceConnection)this);
            Object object = ImsServiceFeatureQueryManager.this.mLock;
            synchronized (object) {
                ImsServiceFeatureQueryManager.this.mActiveQueries.remove((Object)this.mName);
                return;
            }
        }

        private void queryImsFeatures(IImsServiceController object) {
            try {
                object = object.querySupportedImsFeatures();
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("queryImsFeatures - error: ");
                stringBuilder.append(exception);
                Log.w((String)LOG_TAG, (String)stringBuilder.toString());
                this.cleanup();
                ImsServiceFeatureQueryManager.this.mListener.onError(this.mName);
                return;
            }
            object = object.getServiceFeatures();
            this.cleanup();
            ImsServiceFeatureQueryManager.this.mListener.onComplete(this.mName, (Set<ImsFeatureConfiguration.FeatureSlotPair>)object);
        }

        public void onServiceConnected(ComponentName componentName, IBinder object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onServiceConnected for component: ");
            stringBuilder.append((Object)componentName);
            Log.i((String)LOG_TAG, (String)stringBuilder.toString());
            if (object != null) {
                this.queryImsFeatures(IImsServiceController.Stub.asInterface((IBinder)object));
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("onServiceConnected: ");
                ((StringBuilder)object).append((Object)componentName);
                ((StringBuilder)object).append(" binder null, cleaning up.");
                Log.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
                this.cleanup();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onServiceDisconnected for component: ");
            stringBuilder.append((Object)componentName);
            Log.w((String)LOG_TAG, (String)stringBuilder.toString());
        }

        public boolean start() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("start: intent filter=");
            stringBuilder.append(this.mIntentFilter);
            stringBuilder.append(", name=");
            stringBuilder.append((Object)this.mName);
            Log.d((String)LOG_TAG, (String)stringBuilder.toString());
            stringBuilder = new Intent(this.mIntentFilter).setComponent(this.mName);
            boolean bl = ImsServiceFeatureQueryManager.this.mContext.bindService((Intent)stringBuilder, (ServiceConnection)this, 67108929);
            if (!bl) {
                this.cleanup();
            }
            return bl;
        }
    }

    public static interface Listener {
        public void onComplete(ComponentName var1, Set<ImsFeatureConfiguration.FeatureSlotPair> var2);

        public void onError(ComponentName var1);
    }

}

