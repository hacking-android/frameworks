/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.SubscriptionInfo
 *  android.text.TextUtils
 */
package com.android.internal.telephony.uicc;

import android.telephony.SubscriptionInfo;
import android.text.TextUtils;
import com.android.internal.telephony.uicc.IccCardStatus;

public class IccSlotStatus {
    public String atr;
    public IccCardStatus.CardState cardState;
    public String eid;
    public String iccid;
    public int logicalSlotIndex;
    public SlotState slotState;

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (IccSlotStatus)object;
            if (!(this.cardState == ((IccSlotStatus)object).cardState && this.slotState == ((IccSlotStatus)object).slotState && this.logicalSlotIndex == ((IccSlotStatus)object).logicalSlotIndex && TextUtils.equals((CharSequence)this.atr, (CharSequence)((IccSlotStatus)object).atr) && TextUtils.equals((CharSequence)this.iccid, (CharSequence)((IccSlotStatus)object).iccid) && TextUtils.equals((CharSequence)this.eid, (CharSequence)((IccSlotStatus)object).eid))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCardState(int n) {
        if (n == 0) {
            this.cardState = IccCardStatus.CardState.CARDSTATE_ABSENT;
            return;
        }
        if (n == 1) {
            this.cardState = IccCardStatus.CardState.CARDSTATE_PRESENT;
            return;
        }
        if (n == 2) {
            this.cardState = IccCardStatus.CardState.CARDSTATE_ERROR;
            return;
        }
        if (n == 3) {
            this.cardState = IccCardStatus.CardState.CARDSTATE_RESTRICTED;
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
    public void setSlotState(int n) {
        if (n == 0) {
            this.slotState = SlotState.SLOTSTATE_INACTIVE;
            return;
        }
        if (n == 1) {
            this.slotState = SlotState.SLOTSTATE_ACTIVE;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized RIL_SlotState: ");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IccSlotStatus {");
        stringBuilder.append((Object)this.cardState);
        stringBuilder.append(",");
        stringBuilder.append((Object)this.slotState);
        stringBuilder.append(",");
        stringBuilder.append("logicalSlotIndex=");
        stringBuilder.append(this.logicalSlotIndex);
        stringBuilder.append(",");
        stringBuilder.append("atr=");
        stringBuilder.append(this.atr);
        stringBuilder.append(",iccid=");
        stringBuilder.append(SubscriptionInfo.givePrintableIccid((String)this.iccid));
        stringBuilder.append(",");
        stringBuilder.append("eid=");
        stringBuilder.append(this.eid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static enum SlotState {
        SLOTSTATE_INACTIVE,
        SLOTSTATE_ACTIVE;
        
    }

}

