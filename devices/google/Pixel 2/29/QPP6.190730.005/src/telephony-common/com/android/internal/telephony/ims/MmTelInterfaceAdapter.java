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
 *  com.android.ims.internal.IImsConfig
 *  com.android.ims.internal.IImsEcbm
 *  com.android.ims.internal.IImsMMTelFeature
 *  com.android.ims.internal.IImsMMTelFeature$Stub
 *  com.android.ims.internal.IImsMultiEndpoint
 *  com.android.ims.internal.IImsRegistrationListener
 *  com.android.ims.internal.IImsUt
 */
package com.android.internal.telephony.ims;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMMTelFeature;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsUt;

public class MmTelInterfaceAdapter {
    protected IBinder mBinder;
    protected int mSlotId;

    public MmTelInterfaceAdapter(int n, IBinder iBinder) {
        this.mBinder = iBinder;
        this.mSlotId = n;
    }

    private IImsMMTelFeature getInterface() throws RemoteException {
        IImsMMTelFeature iImsMMTelFeature = IImsMMTelFeature.Stub.asInterface((IBinder)this.mBinder);
        if (iImsMMTelFeature != null) {
            return iImsMMTelFeature;
        }
        throw new RemoteException("Binder not Available");
    }

    public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        this.getInterface().addRegistrationListener(iImsRegistrationListener);
    }

    public ImsCallProfile createCallProfile(int n, int n2, int n3) throws RemoteException {
        return this.getInterface().createCallProfile(n, n2, n3);
    }

    public IImsCallSession createCallSession(int n, ImsCallProfile imsCallProfile) throws RemoteException {
        return this.getInterface().createCallSession(n, imsCallProfile);
    }

    public void endSession(int n) throws RemoteException {
        this.getInterface().endSession(n);
    }

    public IImsConfig getConfigInterface() throws RemoteException {
        return this.getInterface().getConfigInterface();
    }

    public IImsEcbm getEcbmInterface() throws RemoteException {
        return this.getInterface().getEcbmInterface();
    }

    public int getFeatureState() throws RemoteException {
        return this.getInterface().getFeatureStatus();
    }

    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
        return this.getInterface().getMultiEndpointInterface();
    }

    public IImsCallSession getPendingCallSession(int n, String string) throws RemoteException {
        return this.getInterface().getPendingCallSession(n, string);
    }

    public IImsUt getUtInterface() throws RemoteException {
        return this.getInterface().getUtInterface();
    }

    public boolean isConnected(int n, int n2) throws RemoteException {
        return this.getInterface().isConnected(n, n2);
    }

    public boolean isOpened() throws RemoteException {
        return this.getInterface().isOpened();
    }

    public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        this.getInterface().removeRegistrationListener(iImsRegistrationListener);
    }

    public void setUiTTYMode(int n, Message message) throws RemoteException {
        this.getInterface().setUiTTYMode(n, message);
    }

    public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        return this.getInterface().startSession(pendingIntent, iImsRegistrationListener);
    }

    public void turnOffIms() throws RemoteException {
        this.getInterface().turnOffIms();
    }

    public void turnOnIms() throws RemoteException {
        this.getInterface().turnOnIms();
    }
}

