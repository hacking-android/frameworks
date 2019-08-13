/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.compat.feature;

import android.os.IInterface;
import android.telephony.ims.compat.feature.ImsFeature;
import com.android.ims.internal.IImsRcsFeature;

public class RcsFeature
extends ImsFeature {
    private final IImsRcsFeature mImsRcsBinder = new IImsRcsFeature.Stub(){};

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

