/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.TelephonyManager
 *  android.util.Log
 */
package com.android.internal.telephony.uicc;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;

public class UiccStateChangedLauncher
extends Handler {
    private static final int EVENT_ICC_CHANGED = 1;
    private static final String TAG = UiccStateChangedLauncher.class.getName();
    private static String sDeviceProvisioningPackage = null;
    private Context mContext;
    private boolean[] mIsRestricted = null;
    private UiccController mUiccController;

    public UiccStateChangedLauncher(Context context, UiccController uiccController) {
        String string = sDeviceProvisioningPackage = context.getResources().getString(17039717);
        if (string != null && !string.isEmpty()) {
            this.mContext = context;
            this.mUiccController = uiccController;
            this.mUiccController.registerForIccChanged(this, 1, null);
        }
    }

    private void notifyStateChanged() {
        Intent intent = new Intent("android.intent.action.SIM_STATE_CHANGED");
        intent.setPackage(sDeviceProvisioningPackage);
        try {
            this.mContext.sendBroadcast(intent);
        }
        catch (Exception exception) {
            Log.e((String)TAG, (String)exception.toString());
        }
    }

    public void handleMessage(Message object) {
        if (((Message)object).what == 1) {
            boolean bl = false;
            if (this.mIsRestricted == null) {
                this.mIsRestricted = new boolean[TelephonyManager.getDefault().getPhoneCount()];
                bl = true;
            }
            for (int i = 0; i < this.mIsRestricted.length; ++i) {
                object = this.mUiccController.getUiccCardForPhone(i);
                boolean bl2 = object == null || ((UiccCard)object).getCardState() != IccCardStatus.CardState.CARDSTATE_RESTRICTED;
                object = this.mIsRestricted;
                if (bl2 == object[i]) continue;
                object[i] = object[i] ^ true;
                bl = true;
            }
            if (bl) {
                this.notifyStateChanged();
            }
            return;
        }
        throw new RuntimeException("unexpected event not handled");
    }
}

