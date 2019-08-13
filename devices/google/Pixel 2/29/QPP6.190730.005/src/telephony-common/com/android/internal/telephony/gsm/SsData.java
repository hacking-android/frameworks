/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.gsm;

import android.telephony.Rlog;
import com.android.internal.telephony.CallForwardInfo;

public class SsData {
    public CallForwardInfo[] cfInfo;
    public RequestType requestType;
    public int result;
    public int serviceClass;
    public ServiceType serviceType;
    public int[] ssInfo;
    public TeleserviceType teleserviceType;

    public RequestType RequestTypeFromRILInt(int n) {
        try {
            RequestType requestType = RequestType.values()[n];
            return requestType;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            Rlog.e((String)"GsmCdmaPhone", (String)"Invalid Request type");
            return null;
        }
    }

    public ServiceType ServiceTypeFromRILInt(int n) {
        try {
            ServiceType serviceType = ServiceType.values()[n];
            return serviceType;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            Rlog.e((String)"GsmCdmaPhone", (String)"Invalid Service type");
            return null;
        }
    }

    public TeleserviceType TeleserviceTypeFromRILInt(int n) {
        try {
            TeleserviceType teleserviceType = TeleserviceType.values()[n];
            return teleserviceType;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            Rlog.e((String)"GsmCdmaPhone", (String)"Invalid Teleservice type");
            return null;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[SsData] ServiceType: ");
        stringBuilder.append((Object)this.serviceType);
        stringBuilder.append(" RequestType: ");
        stringBuilder.append((Object)this.requestType);
        stringBuilder.append(" TeleserviceType: ");
        stringBuilder.append((Object)this.teleserviceType);
        stringBuilder.append(" ServiceClass: ");
        stringBuilder.append(this.serviceClass);
        stringBuilder.append(" Result: ");
        stringBuilder.append(this.result);
        stringBuilder.append(" Is Service Type CF: ");
        stringBuilder.append(this.serviceType.isTypeCF());
        return stringBuilder.toString();
    }

    public static enum RequestType {
        SS_ACTIVATION,
        SS_DEACTIVATION,
        SS_INTERROGATION,
        SS_REGISTRATION,
        SS_ERASURE;
        

        public boolean isTypeInterrogation() {
            boolean bl = this == SS_INTERROGATION;
            return bl;
        }
    }

    public static enum ServiceType {
        SS_CFU,
        SS_CF_BUSY,
        SS_CF_NO_REPLY,
        SS_CF_NOT_REACHABLE,
        SS_CF_ALL,
        SS_CF_ALL_CONDITIONAL,
        SS_CLIP,
        SS_CLIR,
        SS_COLP,
        SS_COLR,
        SS_WAIT,
        SS_BAOC,
        SS_BAOIC,
        SS_BAOIC_EXC_HOME,
        SS_BAIC,
        SS_BAIC_ROAMING,
        SS_ALL_BARRING,
        SS_OUTGOING_BARRING,
        SS_INCOMING_BARRING;
        

        public boolean isTypeBarring() {
            boolean bl = this == SS_BAOC || this == SS_BAOIC || this == SS_BAOIC_EXC_HOME || this == SS_BAIC || this == SS_BAIC_ROAMING || this == SS_ALL_BARRING || this == SS_OUTGOING_BARRING || this == SS_INCOMING_BARRING;
            return bl;
        }

        public boolean isTypeCF() {
            boolean bl = this == SS_CFU || this == SS_CF_BUSY || this == SS_CF_NO_REPLY || this == SS_CF_NOT_REACHABLE || this == SS_CF_ALL || this == SS_CF_ALL_CONDITIONAL;
            return bl;
        }

        public boolean isTypeCW() {
            boolean bl = this == SS_WAIT;
            return bl;
        }

        public boolean isTypeClip() {
            boolean bl = this == SS_CLIP;
            return bl;
        }

        public boolean isTypeClir() {
            boolean bl = this == SS_CLIR;
            return bl;
        }

        public boolean isTypeUnConditional() {
            boolean bl = this == SS_CFU || this == SS_CF_ALL;
            return bl;
        }
    }

    public static enum TeleserviceType {
        SS_ALL_TELE_AND_BEARER_SERVICES,
        SS_ALL_TELESEVICES,
        SS_TELEPHONY,
        SS_ALL_DATA_TELESERVICES,
        SS_SMS_SERVICES,
        SS_ALL_TELESERVICES_EXCEPT_SMS;
        
    }

}

