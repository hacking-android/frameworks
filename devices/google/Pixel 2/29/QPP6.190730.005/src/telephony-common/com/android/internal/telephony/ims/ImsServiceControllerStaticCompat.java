/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.util.Log
 *  com.android.ims.internal.IImsFeatureStatusCallback
 *  com.android.ims.internal.IImsService
 *  com.android.ims.internal.IImsService$Stub
 */
package com.android.internal.telephony.ims;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.ims.internal.IImsService;
import com.android.internal.telephony.ims.ImsServiceController;
import com.android.internal.telephony.ims.ImsServiceControllerCompat;
import com.android.internal.telephony.ims.ImsServiceInterfaceAdapter;
import com.android.internal.telephony.ims.MmTelInterfaceAdapter;

public class ImsServiceControllerStaticCompat
extends ImsServiceControllerCompat {
    private static final String IMS_SERVICE_NAME = "ims";
    private static final String TAG = "ImsSCStaticCompat";
    private ImsDeathRecipient mImsDeathRecipient = null;
    private IImsService mImsServiceCompat = null;

    public ImsServiceControllerStaticCompat(Context context, ComponentName componentName, ImsServiceController.ImsServiceControllerCallbacks imsServiceControllerCallbacks) {
        super(context, componentName, imsServiceControllerCallbacks);
    }

    @Override
    protected MmTelInterfaceAdapter getInterface(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        iImsFeatureStatusCallback = this.mImsServiceCompat;
        if (iImsFeatureStatusCallback == null) {
            Log.w((String)TAG, (String)"getInterface: IImsService returned null.");
            return null;
        }
        return new ImsServiceInterfaceAdapter(n, iImsFeatureStatusCallback.asBinder());
    }

    @Override
    protected boolean isServiceControllerAvailable() {
        boolean bl = this.mImsServiceCompat != null;
        return bl;
    }

    @Override
    protected void setServiceController(IBinder iBinder) {
        if (iBinder == null) {
            IImsService iImsService = this.mImsServiceCompat;
            if (iImsService != null) {
                iImsService.asBinder().unlinkToDeath((IBinder.DeathRecipient)this.mImsDeathRecipient, 0);
            }
            this.mImsDeathRecipient = null;
        }
        this.mImsServiceCompat = IImsService.Stub.asInterface((IBinder)iBinder);
    }

    @Override
    public boolean startBindToService(Intent intent, ImsServiceController.ImsServiceConnection imsServiceConnection, int n) {
        IBinder iBinder = ServiceManager.checkService((String)IMS_SERVICE_NAME);
        if (iBinder == null) {
            return false;
        }
        intent = new ComponentName(this.mContext, ImsServiceControllerStaticCompat.class);
        imsServiceConnection.onServiceConnected((ComponentName)intent, iBinder);
        try {
            ImsDeathRecipient imsDeathRecipient;
            this.mImsDeathRecipient = imsDeathRecipient = new ImsDeathRecipient((ComponentName)intent, imsServiceConnection);
            iBinder.linkToDeath((IBinder.DeathRecipient)this.mImsDeathRecipient, 0);
        }
        catch (RemoteException remoteException) {
            this.mImsDeathRecipient.binderDied();
            this.mImsDeathRecipient = null;
        }
        return true;
    }

    private class ImsDeathRecipient
    implements IBinder.DeathRecipient {
        private ComponentName mComponentName;
        private ServiceConnection mServiceConnection;

        ImsDeathRecipient(ComponentName componentName, ServiceConnection serviceConnection) {
            this.mComponentName = componentName;
            this.mServiceConnection = serviceConnection;
        }

        public void binderDied() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ImsService(");
            stringBuilder.append((Object)this.mComponentName);
            stringBuilder.append(") died. Restarting...");
            Log.e((String)ImsServiceControllerStaticCompat.TAG, (String)stringBuilder.toString());
            this.mServiceConnection.onBindingDied(this.mComponentName);
        }
    }

}

