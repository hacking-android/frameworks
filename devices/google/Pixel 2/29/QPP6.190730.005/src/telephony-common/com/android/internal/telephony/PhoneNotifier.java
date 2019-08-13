/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.CallQuality
 *  android.telephony.CellInfo
 *  android.telephony.CellLocation
 *  android.telephony.PhoneCapability
 *  android.telephony.PhysicalChannelConfig
 *  android.telephony.ims.ImsReasonInfo
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$DataState
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.telephony.CallQuality;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.ims.ImsReasonInfo;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import java.util.List;

public interface PhoneNotifier {
    public void notifyCallForwardingChanged(Phone var1);

    public void notifyCallQualityChanged(Phone var1, CallQuality var2, int var3);

    public void notifyCellInfo(Phone var1, List<CellInfo> var2);

    public void notifyCellLocation(Phone var1, CellLocation var2);

    public void notifyDataActivationStateChanged(Phone var1, int var2);

    public void notifyDataActivity(Phone var1);

    public void notifyDataConnection(Phone var1, String var2, PhoneConstants.DataState var3);

    public void notifyDataConnectionFailed(Phone var1, String var2);

    public void notifyDisconnectCause(Phone var1, int var2, int var3);

    public void notifyEmergencyNumberList(Phone var1);

    public void notifyImsDisconnectCause(Phone var1, ImsReasonInfo var2);

    @UnsupportedAppUsage
    public void notifyMessageWaitingChanged(Phone var1);

    public void notifyOemHookRawEventForSubscriber(Phone var1, byte[] var2);

    public void notifyOtaspChanged(Phone var1, int var2);

    public void notifyPhoneCapabilityChanged(PhoneCapability var1);

    public void notifyPhoneState(Phone var1);

    public void notifyPhysicalChannelConfiguration(Phone var1, List<PhysicalChannelConfig> var2);

    public void notifyPreciseCallState(Phone var1);

    public void notifyPreciseDataConnectionFailed(Phone var1, String var2, String var3, int var4);

    public void notifyRadioPowerStateChanged(Phone var1, int var2);

    public void notifyServiceState(Phone var1);

    @UnsupportedAppUsage
    public void notifySignalStrength(Phone var1);

    public void notifySrvccStateChanged(Phone var1, int var2);

    public void notifyUserMobileDataStateChanged(Phone var1, boolean var2);

    public void notifyVoiceActivationStateChanged(Phone var1, int var2);
}

