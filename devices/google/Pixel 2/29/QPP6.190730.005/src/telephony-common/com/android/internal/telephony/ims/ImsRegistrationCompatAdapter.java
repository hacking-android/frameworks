/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.RemoteException
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.stub.ImsRegistrationImplBase
 *  android.util.ArrayMap
 *  com.android.ims.internal.IImsRegistrationListener
 *  com.android.ims.internal.IImsRegistrationListener$Stub
 */
package com.android.internal.telephony.ims;

import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import android.util.ArrayMap;
import com.android.ims.internal.IImsRegistrationListener;
import java.util.Map;

public class ImsRegistrationCompatAdapter
extends ImsRegistrationImplBase {
    private static final Map<Integer, Integer> RADIO_TECH_MAPPER = new ArrayMap(2);
    private final IImsRegistrationListener mListener = new IImsRegistrationListener.Stub(){

        public void registrationAssociatedUriChanged(Uri[] arruri) throws RemoteException {
            ImsRegistrationCompatAdapter.this.onSubscriberAssociatedUriChanged(arruri);
        }

        public void registrationChangeFailed(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
            ImsRegistrationCompatAdapter.this.onTechnologyChangeFailed(RADIO_TECH_MAPPER.getOrDefault(n, -1).intValue(), imsReasonInfo);
        }

        public void registrationConnected() throws RemoteException {
            ImsRegistrationCompatAdapter.this.onRegistered(-1);
        }

        public void registrationConnectedWithRadioTech(int n) throws RemoteException {
            ImsRegistrationCompatAdapter.this.onRegistered(RADIO_TECH_MAPPER.getOrDefault(n, -1).intValue());
        }

        public void registrationDisconnected(ImsReasonInfo imsReasonInfo) throws RemoteException {
            ImsRegistrationCompatAdapter.this.onDeregistered(imsReasonInfo);
        }

        public void registrationFeatureCapabilityChanged(int n, int[] arrn, int[] arrn2) throws RemoteException {
        }

        public void registrationProgressing() throws RemoteException {
            ImsRegistrationCompatAdapter.this.onRegistering(-1);
        }

        public void registrationProgressingWithRadioTech(int n) throws RemoteException {
            ImsRegistrationCompatAdapter.this.onRegistering(RADIO_TECH_MAPPER.getOrDefault(n, -1).intValue());
        }

        public void registrationResumed() throws RemoteException {
        }

        public void registrationServiceCapabilityChanged(int n, int n2) throws RemoteException {
        }

        public void registrationSuspended() throws RemoteException {
        }

        public void voiceMessageCountUpdate(int n) throws RemoteException {
        }
    };

    static {
        RADIO_TECH_MAPPER.put(14, 0);
        RADIO_TECH_MAPPER.put(18, 1);
    }

    public IImsRegistrationListener getRegistrationListener() {
        return this.mListener;
    }

}

