/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.RemoteException
 *  android.telephony.ims.ImsCallProfile
 *  com.android.ims.internal.IImsCallSession
 *  com.android.ims.internal.IImsCallSessionListener
 *  com.android.ims.internal.IImsConfig
 *  com.android.ims.internal.IImsEcbm
 *  com.android.ims.internal.IImsMultiEndpoint
 *  com.android.ims.internal.IImsRegistrationListener
 *  com.android.ims.internal.IImsService
 *  com.android.ims.internal.IImsService$Stub
 *  com.android.ims.internal.IImsUt
 */
package com.android.internal.telephony.ims;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsService;
import com.android.ims.internal.IImsUt;
import com.android.internal.telephony.ims.MmTelInterfaceAdapter;

public class ImsServiceInterfaceAdapter
extends MmTelInterfaceAdapter {
    private static final int SERVICE_ID = 1;

    public ImsServiceInterfaceAdapter(int n, IBinder iBinder) {
        super(n, iBinder);
    }

    private IImsService getInterface() throws RemoteException {
        IImsService iImsService = IImsService.Stub.asInterface((IBinder)this.mBinder);
        if (iImsService != null) {
            return iImsService;
        }
        throw new RemoteException("Binder not Available");
    }

    @Override
    public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        this.getInterface().addRegistrationListener(this.mSlotId, 1, iImsRegistrationListener);
    }

    @Override
    public ImsCallProfile createCallProfile(int n, int n2, int n3) throws RemoteException {
        return this.getInterface().createCallProfile(n, n2, n3);
    }

    @Override
    public IImsCallSession createCallSession(int n, ImsCallProfile imsCallProfile) throws RemoteException {
        return this.getInterface().createCallSession(n, imsCallProfile, null);
    }

    @Override
    public void endSession(int n) throws RemoteException {
        this.getInterface().close(n);
    }

    @Override
    public IImsConfig getConfigInterface() throws RemoteException {
        return this.getInterface().getConfigInterface(this.mSlotId);
    }

    @Override
    public IImsEcbm getEcbmInterface() throws RemoteException {
        return this.getInterface().getEcbmInterface(1);
    }

    @Override
    public int getFeatureState() throws RemoteException {
        return 2;
    }

    @Override
    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
        return this.getInterface().getMultiEndpointInterface(1);
    }

    @Override
    public IImsCallSession getPendingCallSession(int n, String string) throws RemoteException {
        return this.getInterface().getPendingCallSession(n, string);
    }

    @Override
    public IImsUt getUtInterface() throws RemoteException {
        return this.getInterface().getUtInterface(1);
    }

    @Override
    public boolean isConnected(int n, int n2) throws RemoteException {
        return this.getInterface().isConnected(1, n, n2);
    }

    @Override
    public boolean isOpened() throws RemoteException {
        return this.getInterface().isOpened(1);
    }

    @Override
    public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
    }

    @Override
    public void setUiTTYMode(int n, Message message) throws RemoteException {
        this.getInterface().setUiTTYMode(1, n, message);
    }

    @Override
    public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        return this.getInterface().open(this.mSlotId, 1, pendingIntent, iImsRegistrationListener);
    }

    @Override
    public void turnOffIms() throws RemoteException {
        this.getInterface().turnOffIms(this.mSlotId);
    }

    @Override
    public void turnOnIms() throws RemoteException {
        this.getInterface().turnOnIms(this.mSlotId);
    }
}

