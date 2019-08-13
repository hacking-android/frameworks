/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.RemoteException
 *  android.telephony.ims.aidl.IImsConfig
 *  android.telephony.ims.aidl.IImsMmTelFeature
 *  android.telephony.ims.aidl.IImsRcsFeature
 *  android.telephony.ims.aidl.IImsRegistration
 *  android.util.Log
 *  android.util.SparseArray
 *  com.android.ims.internal.IImsConfig
 *  com.android.ims.internal.IImsFeatureStatusCallback
 *  com.android.ims.internal.IImsMMTelFeature
 *  com.android.ims.internal.IImsServiceController
 *  com.android.ims.internal.IImsServiceController$Stub
 */
package com.android.internal.telephony.ims;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.util.Log;
import android.util.SparseArray;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.ims.internal.IImsMMTelFeature;
import com.android.ims.internal.IImsServiceController;
import com.android.internal.telephony.ims.ImsConfigCompatAdapter;
import com.android.internal.telephony.ims.ImsRegistrationCompatAdapter;
import com.android.internal.telephony.ims.ImsServiceController;
import com.android.internal.telephony.ims.MmTelFeatureCompatAdapter;
import com.android.internal.telephony.ims.MmTelInterfaceAdapter;

public class ImsServiceControllerCompat
extends ImsServiceController {
    private static final String TAG = "ImsSCCompat";
    private final SparseArray<ImsConfigCompatAdapter> mConfigCompatAdapters = new SparseArray();
    private final SparseArray<MmTelFeatureCompatAdapter> mMmTelCompatAdapters = new SparseArray();
    private final SparseArray<ImsRegistrationCompatAdapter> mRegCompatAdapters = new SparseArray();
    private IImsServiceController mServiceController;

    public ImsServiceControllerCompat(Context context, ComponentName componentName, ImsServiceController.ImsServiceControllerCallbacks imsServiceControllerCallbacks) {
        super(context, componentName, imsServiceControllerCallbacks);
    }

    private IImsMmTelFeature createMMTelCompat(int n, IImsFeatureStatusCallback object) throws RemoteException {
        object = this.getInterface(n, (IImsFeatureStatusCallback)object);
        object = new MmTelFeatureCompatAdapter(this.mContext, n, (MmTelInterfaceAdapter)object);
        this.mMmTelCompatAdapters.put(n, object);
        ImsRegistrationCompatAdapter imsRegistrationCompatAdapter = new ImsRegistrationCompatAdapter();
        ((MmTelFeatureCompatAdapter)((Object)object)).addRegistrationAdapter(imsRegistrationCompatAdapter);
        this.mRegCompatAdapters.put(n, (Object)imsRegistrationCompatAdapter);
        this.mConfigCompatAdapters.put(n, (Object)new ImsConfigCompatAdapter(((MmTelFeatureCompatAdapter)((Object)object)).getOldConfigInterface()));
        return object.getBinder();
    }

    private IImsRcsFeature createRcsFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        return null;
    }

    @Override
    protected final IInterface createImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        if (n2 != 1) {
            if (n2 != 2) {
                return null;
            }
            return this.createRcsFeature(n, iImsFeatureStatusCallback);
        }
        return this.createMMTelCompat(n, iImsFeatureStatusCallback);
    }

    @Override
    public final void disableIms(int n) {
        Object object = (MmTelFeatureCompatAdapter)((Object)this.mMmTelCompatAdapters.get(n));
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("enableIms: adapter null for slot :");
            ((StringBuilder)object).append(n);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return;
        }
        try {
            object.disableIms();
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Couldn't enable IMS: ");
            ((StringBuilder)object).append(remoteException.getMessage());
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
        }
    }

    @Override
    public final void enableIms(int n) {
        Object object = (MmTelFeatureCompatAdapter)((Object)this.mMmTelCompatAdapters.get(n));
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("enableIms: adapter null for slot :");
            ((StringBuilder)object).append(n);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return;
        }
        try {
            object.enableIms();
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Couldn't enable IMS: ");
            ((StringBuilder)object).append(remoteException.getMessage());
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
        }
    }

    @Override
    public final IImsConfig getConfig(int n) {
        Object object = (ImsConfigCompatAdapter)((Object)this.mConfigCompatAdapters.get(n));
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("getConfig: Config does not exist for slot ");
            ((StringBuilder)object).append(n);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return null;
        }
        return object.getIImsConfig();
    }

    protected MmTelInterfaceAdapter getInterface(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        if ((iImsFeatureStatusCallback = this.mServiceController.createMMTelFeature(n, iImsFeatureStatusCallback)) == null) {
            Log.w((String)TAG, (String)"createMMTelCompat: createMMTelFeature returned null.");
            return null;
        }
        return new MmTelInterfaceAdapter(n, iImsFeatureStatusCallback.asBinder());
    }

    @Override
    public final IImsRegistration getRegistration(int n) {
        Object object = (ImsRegistrationCompatAdapter)((Object)this.mRegCompatAdapters.get(n));
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("getRegistration: Registration does not exist for slot ");
            ((StringBuilder)object).append(n);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return null;
        }
        return object.getBinder();
    }

    @Override
    protected final String getServiceInterface() {
        return "android.telephony.ims.compat.ImsService";
    }

    @Override
    protected boolean isServiceControllerAvailable() {
        boolean bl = this.mServiceController != null;
        return bl;
    }

    @Override
    protected final void notifyImsServiceReady() {
        Log.d((String)TAG, (String)"notifyImsServiceReady");
    }

    @Override
    protected final void removeImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        IImsServiceController iImsServiceController;
        if (n2 == 1) {
            this.mMmTelCompatAdapters.remove(n);
            this.mRegCompatAdapters.remove(n);
            this.mConfigCompatAdapters.remove(n);
        }
        if ((iImsServiceController = this.mServiceController) != null) {
            iImsServiceController.removeImsFeature(n, n2, iImsFeatureStatusCallback);
        }
    }

    @Override
    protected void setServiceController(IBinder iBinder) {
        this.mServiceController = IImsServiceController.Stub.asInterface((IBinder)iBinder);
    }
}

