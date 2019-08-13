/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.os.IInterface;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.feature.CapabilityChangeRequest;
import android.telephony.ims.feature.ImsFeature;

@SystemApi
public class RcsFeature
extends ImsFeature {
    private final IImsRcsFeature mImsRcsBinder = new IImsRcsFeature.Stub(){};

    @Override
    public void changeEnabledCapabilities(CapabilityChangeRequest capabilityChangeRequest, ImsFeature.CapabilityCallbackProxy capabilityCallbackProxy) {
    }

    @Override
    public final IImsRcsFeature getBinder() {
        return this.mImsRcsBinder;
    }

    @Override
    public void onFeatureReady() {
    }

    @Override
    public void onFeatureRemoved() {
    }

}

