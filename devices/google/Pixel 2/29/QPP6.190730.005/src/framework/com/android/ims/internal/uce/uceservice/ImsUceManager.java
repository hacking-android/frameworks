/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.uceservice;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.ims.internal.uce.uceservice.IUceService;
import java.util.HashMap;

public class ImsUceManager {
    public static final String ACTION_UCE_SERVICE_DOWN = "com.android.ims.internal.uce.UCE_SERVICE_DOWN";
    public static final String ACTION_UCE_SERVICE_UP = "com.android.ims.internal.uce.UCE_SERVICE_UP";
    public static final String EXTRA_PHONE_ID = "android:phone_id";
    private static final String LOG_TAG = "ImsUceManager";
    private static final String UCE_SERVICE = "uce";
    public static final int UCE_SERVICE_STATUS_CLOSED = 2;
    public static final int UCE_SERVICE_STATUS_FAILURE = 0;
    public static final int UCE_SERVICE_STATUS_ON = 1;
    public static final int UCE_SERVICE_STATUS_READY = 3;
    private static HashMap<Integer, ImsUceManager> sUceManagerInstances = new HashMap();
    private Context mContext;
    private UceServiceDeathRecipient mDeathReceipient = new UceServiceDeathRecipient();
    private int mPhoneId;
    private IUceService mUceService = null;

    private ImsUceManager(Context context, int n) {
        this.mContext = context;
        this.mPhoneId = n;
        this.createUceService(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ImsUceManager getInstance(Context object, int n) {
        HashMap<Integer, ImsUceManager> hashMap = sUceManagerInstances;
        synchronized (hashMap) {
            if (sUceManagerInstances.containsKey(n)) {
                return sUceManagerInstances.get(n);
            }
            ImsUceManager imsUceManager = new ImsUceManager((Context)object, n);
            sUceManagerInstances.put(n, imsUceManager);
            return imsUceManager;
        }
    }

    private String getUceServiceName(int n) {
        return UCE_SERVICE;
    }

    public void createUceService(boolean bl) {
        if (bl && ServiceManager.checkService(this.getUceServiceName(this.mPhoneId)) == null) {
            return;
        }
        IBinder iBinder = ServiceManager.getService(this.getUceServiceName(this.mPhoneId));
        if (iBinder != null) {
            try {
                iBinder.linkToDeath(this.mDeathReceipient, 0);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        this.mUceService = IUceService.Stub.asInterface(iBinder);
    }

    public IUceService getUceServiceInstance() {
        return this.mUceService;
    }

    private class UceServiceDeathRecipient
    implements IBinder.DeathRecipient {
        private UceServiceDeathRecipient() {
        }

        @Override
        public void binderDied() {
            ImsUceManager.this.mUceService = null;
            if (ImsUceManager.this.mContext != null) {
                Intent intent = new Intent(ImsUceManager.ACTION_UCE_SERVICE_DOWN);
                intent.putExtra(ImsUceManager.EXTRA_PHONE_ID, ImsUceManager.this.mPhoneId);
                ImsUceManager.this.mContext.sendBroadcast(new Intent(intent));
            }
        }
    }

}

