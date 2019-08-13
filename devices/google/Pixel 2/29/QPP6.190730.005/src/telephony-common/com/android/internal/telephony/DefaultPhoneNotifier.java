/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.net.LinkProperties
 *  android.net.NetworkCapabilities
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.telephony.CallQuality
 *  android.telephony.CellInfo
 *  android.telephony.CellLocation
 *  android.telephony.PhoneCapability
 *  android.telephony.PhysicalChannelConfig
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.data.ApnSetting
 *  android.telephony.ims.ImsReasonInfo
 *  com.android.internal.telephony.ITelephonyRegistry
 *  com.android.internal.telephony.ITelephonyRegistry$Stub
 *  com.android.internal.telephony.PhoneConstantConversions
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.CallQuality;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.data.ApnSetting;
import android.telephony.ims.ImsReasonInfo;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstantConversions;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.PhoneNotifier;
import java.util.List;

public class DefaultPhoneNotifier
implements PhoneNotifier {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "DefaultPhoneNotifier";
    @UnsupportedAppUsage
    protected ITelephonyRegistry mRegistry = ITelephonyRegistry.Stub.asInterface((IBinder)ServiceManager.getService((String)"telephony.registry"));

    public static int convertDataActivityState(PhoneInternalInterface.DataActivityState dataActivityState) {
        int n = 1.$SwitchMap$com$android$internal$telephony$PhoneInternalInterface$DataActivityState[dataActivityState.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return 0;
                    }
                    return 4;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public static int convertPreciseCallState(Call.State state) {
        switch (state) {
            default: {
                return 0;
            }
            case DISCONNECTING: {
                return 8;
            }
            case DISCONNECTED: {
                return 7;
            }
            case WAITING: {
                return 6;
            }
            case INCOMING: {
                return 5;
            }
            case ALERTING: {
                return 4;
            }
            case DIALING: {
                return 3;
            }
            case HOLDING: {
                return 2;
            }
            case ACTIVE: 
        }
        return 1;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void doNotifyDataConnection(Phone object, String string, PhoneConstants.DataState dataState) {
        ServiceState serviceState;
        int n;
        LinkProperties linkProperties;
        boolean bl;
        int n4;
        int n2;
        boolean bl2;
        int n3;
        NetworkCapabilities networkCapabilities;
        block8 : {
            n4 = ((Phone)object).getSubId();
            n2 = ((Phone)object).getPhoneId();
            long l = SubscriptionManager.getDefaultDataSubscriptionId();
            TelephonyManager telephonyManager = TelephonyManager.getDefault();
            if (dataState == PhoneConstants.DataState.CONNECTED) {
                linkProperties = ((Phone)object).getLinkProperties(string);
                networkCapabilities = ((Phone)object).getNetworkCapabilities(string);
            } else {
                linkProperties = null;
                networkCapabilities = null;
            }
            serviceState = object.getServiceState();
            bl = serviceState != null ? serviceState.getDataRoaming() : false;
            if (this.mRegistry == null) return;
            serviceState = this.mRegistry;
            n3 = PhoneConstantConversions.convertDataState((PhoneConstants.DataState)dataState);
            bl2 = ((Phone)object).isDataAllowed(ApnSetting.getApnTypesBitmaskFromString((String)string));
            object = ((Phone)object).getActiveApnHost(string);
            if (telephonyManager == null) break block8;
            try {
                n = telephonyManager.getDataNetworkType(n4);
            }
            catch (RemoteException remoteException) {
                return;
            }
        }
        n = 0;
        try {
            serviceState.notifyDataConnectionForSubscriber(n2, n4, n3, bl2, (String)object, string, linkProperties, networkCapabilities, n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    @Override
    public void notifyCallForwardingChanged(Phone phone) {
        int n = phone.getSubId();
        try {
            if (this.mRegistry != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("notifyCallForwardingChanged: subId=");
                stringBuilder.append(n);
                stringBuilder.append(", isCFActive=");
                stringBuilder.append(phone.getCallForwardingIndicator());
                Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                this.mRegistry.notifyCallForwardingChangedForSubscriber(n, phone.getCallForwardingIndicator());
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyCallQualityChanged(Phone phone, CallQuality callQuality, int n) {
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyCallQualityChanged(callQuality, phone.getPhoneId(), phone.getSubId(), n);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyCellInfo(Phone phone, List<CellInfo> list) {
        int n = phone.getSubId();
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyCellInfoForSubscriber(n, list);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyCellLocation(Phone phone, CellLocation cellLocation) {
        int n = phone.getSubId();
        phone = new Bundle();
        cellLocation.fillInNotifierBundle((Bundle)phone);
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyCellLocationForSubscriber(n, (Bundle)phone);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyDataActivationStateChanged(Phone phone, int n) {
        try {
            this.mRegistry.notifySimActivationStateChangedForPhoneId(phone.getPhoneId(), phone.getSubId(), 1, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyDataActivity(Phone phone) {
        int n = phone.getSubId();
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyDataActivityForSubscriber(n, DefaultPhoneNotifier.convertDataActivityState(phone.getDataActivityState()));
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyDataConnection(Phone phone, String string, PhoneConstants.DataState dataState) {
        this.doNotifyDataConnection(phone, string, dataState);
    }

    @Override
    public void notifyDataConnectionFailed(Phone phone, String string) {
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyDataConnectionFailedForSubscriber(phone.getPhoneId(), phone.getSubId(), string);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyDisconnectCause(Phone phone, int n, int n2) {
        try {
            this.mRegistry.notifyDisconnectCause(phone.getPhoneId(), phone.getSubId(), n, n2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyEmergencyNumberList(Phone phone) {
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyEmergencyNumberList(phone.getPhoneId(), phone.getSubId());
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyImsDisconnectCause(Phone phone, ImsReasonInfo imsReasonInfo) {
        try {
            this.mRegistry.notifyImsDisconnectCause(phone.getSubId(), imsReasonInfo);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyMessageWaitingChanged(Phone phone) {
        int n = phone.getPhoneId();
        int n2 = phone.getSubId();
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyMessageWaitingChangedForPhoneId(n, n2, phone.getMessageWaitingIndicator());
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyOemHookRawEventForSubscriber(Phone phone, byte[] arrby) {
        try {
            this.mRegistry.notifyOemHookRawEventForSubscriber(phone.getPhoneId(), phone.getSubId(), arrby);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyOtaspChanged(Phone phone, int n) {
        int n2 = phone.getSubId();
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyOtaspChanged(n2, n);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) {
        try {
            this.mRegistry.notifyPhoneCapabilityChanged(phoneCapability);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyPhoneState(Phone phone) {
        String string;
        Call call = phone.getRingingCall();
        int n = phone.getSubId();
        int n2 = phone.getPhoneId();
        String string2 = string = "";
        if (call != null) {
            string2 = string;
            if (call.getEarliestConnection() != null) {
                string2 = call.getEarliestConnection().getAddress();
            }
        }
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyCallStateForPhoneId(n2, n, PhoneConstantConversions.convertCallState((PhoneConstants.State)phone.getState()), string2);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyPhysicalChannelConfiguration(Phone phone, List<PhysicalChannelConfig> list) {
        int n = phone.getSubId();
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyPhysicalChannelConfigurationForSubscriber(n, list);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyPreciseCallState(Phone phone) {
        Call call = phone.getRingingCall();
        Call call2 = phone.getForegroundCall();
        Call call3 = phone.getBackgroundCall();
        if (call != null && call2 != null && call3 != null) {
            try {
                this.mRegistry.notifyPreciseCallState(phone.getPhoneId(), phone.getSubId(), DefaultPhoneNotifier.convertPreciseCallState(call.getState()), DefaultPhoneNotifier.convertPreciseCallState(call2.getState()), DefaultPhoneNotifier.convertPreciseCallState(call3.getState()));
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @Override
    public void notifyPreciseDataConnectionFailed(Phone phone, String string, String string2, int n) {
        try {
            this.mRegistry.notifyPreciseDataConnectionFailed(phone.getPhoneId(), phone.getSubId(), string, string2, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyRadioPowerStateChanged(Phone phone, int n) {
        try {
            this.mRegistry.notifyRadioPowerStateChanged(phone.getPhoneId(), phone.getSubId(), n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyServiceState(Phone phone) {
        ServiceState serviceState = phone.getServiceState();
        int n = phone.getPhoneId();
        int n2 = phone.getSubId();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("nofityServiceState: mRegistry=");
        stringBuilder.append((Object)this.mRegistry);
        stringBuilder.append(" ss=");
        stringBuilder.append((Object)serviceState);
        stringBuilder.append(" sender=");
        stringBuilder.append(phone);
        stringBuilder.append(" phondId=");
        stringBuilder.append(n);
        stringBuilder.append(" subId=");
        stringBuilder.append(n2);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        phone = serviceState;
        if (serviceState == null) {
            phone = new ServiceState();
            phone.setStateOutOfService();
        }
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifyServiceStateForPhoneId(n, n2, (ServiceState)phone);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifySignalStrength(Phone phone) {
        int n = phone.getPhoneId();
        int n2 = phone.getSubId();
        try {
            if (this.mRegistry != null) {
                this.mRegistry.notifySignalStrengthForPhoneId(n, n2, phone.getSignalStrength());
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifySrvccStateChanged(Phone phone, int n) {
        try {
            this.mRegistry.notifySrvccStateChanged(phone.getSubId(), n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyUserMobileDataStateChanged(Phone phone, boolean bl) {
        try {
            this.mRegistry.notifyUserMobileDataStateChangedForPhoneId(phone.getPhoneId(), phone.getSubId(), bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void notifyVoiceActivationStateChanged(Phone phone, int n) {
        try {
            this.mRegistry.notifySimActivationStateChangedForPhoneId(phone.getPhoneId(), phone.getSubId(), 0, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

}

