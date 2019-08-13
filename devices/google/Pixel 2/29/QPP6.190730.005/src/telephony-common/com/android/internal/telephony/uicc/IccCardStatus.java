/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.SubscriptionInfo
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.telephony.SubscriptionInfo;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;

public class IccCardStatus {
    public static final int CARD_MAX_APPS = 8;
    public String atr;
    public String eid;
    public String iccid;
    @UnsupportedAppUsage
    public IccCardApplicationStatus[] mApplications;
    @UnsupportedAppUsage
    public CardState mCardState;
    @UnsupportedAppUsage
    public int mCdmaSubscriptionAppIndex;
    @UnsupportedAppUsage
    public int mGsmUmtsSubscriptionAppIndex;
    @UnsupportedAppUsage
    public int mImsSubscriptionAppIndex;
    @UnsupportedAppUsage
    public PinState mUniversalPinState;
    public int physicalSlotIndex = -1;

    /*
     * Enabled aggressive block sorting
     */
    public void setCardState(int n) {
        if (n == 0) {
            this.mCardState = CardState.CARDSTATE_ABSENT;
            return;
        }
        if (n == 1) {
            this.mCardState = CardState.CARDSTATE_PRESENT;
            return;
        }
        if (n == 2) {
            this.mCardState = CardState.CARDSTATE_ERROR;
            return;
        }
        if (n == 3) {
            this.mCardState = CardState.CARDSTATE_RESTRICTED;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized RIL_CardState: ");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setUniversalPinState(int n) {
        if (n == 0) {
            this.mUniversalPinState = PinState.PINSTATE_UNKNOWN;
            return;
        }
        if (n == 1) {
            this.mUniversalPinState = PinState.PINSTATE_ENABLED_NOT_VERIFIED;
            return;
        }
        if (n == 2) {
            this.mUniversalPinState = PinState.PINSTATE_ENABLED_VERIFIED;
            return;
        }
        if (n == 3) {
            this.mUniversalPinState = PinState.PINSTATE_DISABLED;
            return;
        }
        if (n == 4) {
            this.mUniversalPinState = PinState.PINSTATE_ENABLED_BLOCKED;
            return;
        }
        if (n == 5) {
            this.mUniversalPinState = PinState.PINSTATE_ENABLED_PERM_BLOCKED;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized RIL_PinState: ");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IccCardState {");
        stringBuilder.append((Object)this.mCardState);
        stringBuilder.append(",");
        stringBuilder.append((Object)this.mUniversalPinState);
        if (this.mApplications != null) {
            stringBuilder.append(",num_apps=");
            stringBuilder.append(this.mApplications.length);
        } else {
            stringBuilder.append(",mApplications=null");
        }
        stringBuilder.append(",gsm_id=");
        stringBuilder.append(this.mGsmUmtsSubscriptionAppIndex);
        Object object = this.mApplications;
        String string = "null";
        if (object != null && (n = this.mGsmUmtsSubscriptionAppIndex) >= 0 && n < ((IccCardApplicationStatus[])object).length) {
            if ((object = object[n]) == null) {
                object = "null";
            }
            stringBuilder.append(object);
        }
        stringBuilder.append(",cdma_id=");
        stringBuilder.append(this.mCdmaSubscriptionAppIndex);
        object = this.mApplications;
        if (object != null && (n = this.mCdmaSubscriptionAppIndex) >= 0 && n < ((IccCardApplicationStatus[])object).length) {
            if ((object = object[n]) == null) {
                object = "null";
            }
            stringBuilder.append(object);
        }
        stringBuilder.append(",ims_id=");
        stringBuilder.append(this.mImsSubscriptionAppIndex);
        object = this.mApplications;
        if (object != null && (n = this.mImsSubscriptionAppIndex) >= 0 && n < ((IccCardApplicationStatus[])object).length) {
            if ((object = object[n]) == null) {
                object = string;
            }
            stringBuilder.append(object);
        }
        stringBuilder.append(",physical_slot_id=");
        stringBuilder.append(this.physicalSlotIndex);
        stringBuilder.append(",atr=");
        stringBuilder.append(this.atr);
        stringBuilder.append(",iccid=");
        stringBuilder.append(SubscriptionInfo.givePrintableIccid((String)this.iccid));
        stringBuilder.append(",eid=");
        stringBuilder.append(this.eid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static enum CardState {
        CARDSTATE_ABSENT,
        CARDSTATE_PRESENT,
        CARDSTATE_ERROR,
        CARDSTATE_RESTRICTED;
        

        @UnsupportedAppUsage
        public boolean isCardPresent() {
            boolean bl = this == CARDSTATE_PRESENT || this == CARDSTATE_RESTRICTED;
            return bl;
        }
    }

    public static enum PinState {
        PINSTATE_UNKNOWN,
        PINSTATE_ENABLED_NOT_VERIFIED,
        PINSTATE_ENABLED_VERIFIED,
        PINSTATE_DISABLED,
        PINSTATE_ENABLED_BLOCKED,
        PINSTATE_ENABLED_PERM_BLOCKED;
        

        boolean isPermBlocked() {
            boolean bl = this == PINSTATE_ENABLED_PERM_BLOCKED;
            return bl;
        }

        boolean isPinRequired() {
            boolean bl = this == PINSTATE_ENABLED_NOT_VERIFIED;
            return bl;
        }

        boolean isPukRequired() {
            boolean bl = this == PINSTATE_ENABLED_BLOCKED;
            return bl;
        }
    }

}

