/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.telephony.ims.ImsMmTelManager
 *  android.telephony.ims.ImsMmTelManager$RegistrationCallback
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.feature.MmTelFeature
 *  android.telephony.ims.feature.MmTelFeature$MmTelCapabilities
 */
package com.android.ims;

import android.net.Uri;
import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.feature.MmTelFeature;
import java.util.Arrays;

public class ImsConnectionStateListener
extends ImsMmTelManager.RegistrationCallback {
    public void onFeatureCapabilityChanged(int n, int[] arrn, int[] arrn2) {
    }

    public void onFeatureCapabilityChangedAdapter(int n, MmTelFeature.MmTelCapabilities mmTelCapabilities) {
        int[] arrn = new int[6];
        Arrays.fill(arrn, -1);
        int[] arrn2 = new int[6];
        Arrays.fill(arrn2, -1);
        if (n != 0) {
            if (n == 1) {
                if (mmTelCapabilities.isCapable(1)) {
                    arrn[2] = 2;
                }
                if (mmTelCapabilities.isCapable(2)) {
                    arrn[3] = 3;
                }
                if (mmTelCapabilities.isCapable(4)) {
                    arrn[5] = 5;
                }
            }
        } else {
            if (mmTelCapabilities.isCapable(1)) {
                arrn[0] = 0;
            }
            if (mmTelCapabilities.isCapable(2)) {
                arrn[1] = 1;
            }
            if (mmTelCapabilities.isCapable(4)) {
                arrn[4] = 4;
            }
        }
        for (n = 0; n < arrn.length; ++n) {
            if (arrn[n] == n) continue;
            arrn2[n] = n;
        }
        this.onFeatureCapabilityChanged(1, arrn, arrn2);
    }

    public void onImsConnected(int n) {
    }

    public void onImsDisconnected(ImsReasonInfo imsReasonInfo) {
    }

    public void onImsProgressing(int n) {
    }

    public void onImsResumed() {
    }

    public void onImsSuspended() {
    }

    public final void onRegistered(int n) {
        this.onImsConnected(n);
    }

    public final void onRegistering(int n) {
        this.onImsProgressing(n);
    }

    public void onRegistrationChangeFailed(int n, ImsReasonInfo imsReasonInfo) {
    }

    public void onSubscriberAssociatedUriChanged(Uri[] arruri) {
        this.registrationAssociatedUriChanged(arruri);
    }

    public final void onTechnologyChangeFailed(int n, ImsReasonInfo imsReasonInfo) {
        this.onRegistrationChangeFailed(n, imsReasonInfo);
    }

    public final void onUnregistered(ImsReasonInfo imsReasonInfo) {
        this.onImsDisconnected(imsReasonInfo);
    }

    public void onVoiceMessageCountChanged(int n) {
    }

    public void registrationAssociatedUriChanged(Uri[] arruri) {
    }
}

