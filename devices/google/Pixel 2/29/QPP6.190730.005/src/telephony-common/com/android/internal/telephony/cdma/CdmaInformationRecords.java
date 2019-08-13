/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.android.internal.telephony.cdma;

import android.os.Parcel;

public final class CdmaInformationRecords {
    public static final int RIL_CDMA_CALLED_PARTY_NUMBER_INFO_REC = 1;
    public static final int RIL_CDMA_CALLING_PARTY_NUMBER_INFO_REC = 2;
    public static final int RIL_CDMA_CONNECTED_NUMBER_INFO_REC = 3;
    public static final int RIL_CDMA_DISPLAY_INFO_REC = 0;
    public static final int RIL_CDMA_EXTENDED_DISPLAY_INFO_REC = 7;
    public static final int RIL_CDMA_LINE_CONTROL_INFO_REC = 6;
    public static final int RIL_CDMA_REDIRECTING_NUMBER_INFO_REC = 5;
    public static final int RIL_CDMA_SIGNAL_INFO_REC = 4;
    public static final int RIL_CDMA_T53_AUDIO_CONTROL_INFO_REC = 10;
    public static final int RIL_CDMA_T53_CLIR_INFO_REC = 8;
    public static final int RIL_CDMA_T53_RELEASE_INFO_REC = 9;
    public Object record;

    public CdmaInformationRecords(Parcel object) {
        int n = object.readInt();
        switch (n) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("RIL_UNSOL_CDMA_INFO_REC: unsupported record. Got ");
                ((StringBuilder)object).append(CdmaInformationRecords.idToString(n));
                ((StringBuilder)object).append(" ");
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            case 10: {
                this.record = new CdmaT53AudioControlInfoRec(object.readInt(), object.readInt());
                break;
            }
            case 8: {
                this.record = new CdmaT53ClirInfoRec(object.readInt());
                break;
            }
            case 6: {
                this.record = new CdmaLineControlInfoRec(object.readInt(), object.readInt(), object.readInt(), object.readInt());
                break;
            }
            case 5: {
                this.record = new CdmaRedirectingNumberInfoRec(object.readString(), object.readInt(), object.readInt(), object.readInt(), object.readInt(), object.readInt());
                break;
            }
            case 4: {
                this.record = new CdmaSignalInfoRec(object.readInt(), object.readInt(), object.readInt(), object.readInt());
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                this.record = new CdmaNumberInfoRec(n, object.readString(), object.readInt(), object.readInt(), object.readInt(), object.readInt());
                break;
            }
            case 0: 
            case 7: {
                this.record = new CdmaDisplayInfoRec(n, object.readString());
            }
        }
    }

    public CdmaInformationRecords(CdmaDisplayInfoRec cdmaDisplayInfoRec) {
        this.record = cdmaDisplayInfoRec;
    }

    public CdmaInformationRecords(CdmaLineControlInfoRec cdmaLineControlInfoRec) {
        this.record = cdmaLineControlInfoRec;
    }

    public CdmaInformationRecords(CdmaNumberInfoRec cdmaNumberInfoRec) {
        this.record = cdmaNumberInfoRec;
    }

    public CdmaInformationRecords(CdmaRedirectingNumberInfoRec cdmaRedirectingNumberInfoRec) {
        this.record = cdmaRedirectingNumberInfoRec;
    }

    public CdmaInformationRecords(CdmaSignalInfoRec cdmaSignalInfoRec) {
        this.record = cdmaSignalInfoRec;
    }

    public CdmaInformationRecords(CdmaT53AudioControlInfoRec cdmaT53AudioControlInfoRec) {
        this.record = cdmaT53AudioControlInfoRec;
    }

    public CdmaInformationRecords(CdmaT53ClirInfoRec cdmaT53ClirInfoRec) {
        this.record = cdmaT53ClirInfoRec;
    }

    public static String idToString(int n) {
        switch (n) {
            default: {
                return "<unknown record>";
            }
            case 10: {
                return "RIL_CDMA_T53_AUDIO_CONTROL_INFO_REC";
            }
            case 9: {
                return "RIL_CDMA_T53_RELEASE_INFO_REC";
            }
            case 8: {
                return "RIL_CDMA_T53_CLIR_INFO_REC";
            }
            case 7: {
                return "RIL_CDMA_EXTENDED_DISPLAY_INFO_REC";
            }
            case 6: {
                return "RIL_CDMA_LINE_CONTROL_INFO_REC";
            }
            case 5: {
                return "RIL_CDMA_REDIRECTING_NUMBER_INFO_REC";
            }
            case 4: {
                return "RIL_CDMA_SIGNAL_INFO_REC";
            }
            case 3: {
                return "RIL_CDMA_CONNECTED_NUMBER_INFO_REC";
            }
            case 2: {
                return "RIL_CDMA_CALLING_PARTY_NUMBER_INFO_REC";
            }
            case 1: {
                return "RIL_CDMA_CALLED_PARTY_NUMBER_INFO_REC";
            }
            case 0: 
        }
        return "RIL_CDMA_DISPLAY_INFO_REC";
    }

    public static class CdmaDisplayInfoRec {
        public String alpha;
        public int id;

        public CdmaDisplayInfoRec(int n, String string) {
            this.id = n;
            this.alpha = string;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CdmaDisplayInfoRec: { id: ");
            stringBuilder.append(CdmaInformationRecords.idToString(this.id));
            stringBuilder.append(", alpha: ");
            stringBuilder.append(this.alpha);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class CdmaLineControlInfoRec {
        public byte lineCtrlPolarityIncluded;
        public byte lineCtrlPowerDenial;
        public byte lineCtrlReverse;
        public byte lineCtrlToggle;

        public CdmaLineControlInfoRec(int n, int n2, int n3, int n4) {
            this.lineCtrlPolarityIncluded = (byte)n;
            this.lineCtrlToggle = (byte)n2;
            this.lineCtrlReverse = (byte)n3;
            this.lineCtrlPowerDenial = (byte)n4;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CdmaLineControlInfoRec: { lineCtrlPolarityIncluded: ");
            stringBuilder.append(this.lineCtrlPolarityIncluded);
            stringBuilder.append(" lineCtrlToggle: ");
            stringBuilder.append(this.lineCtrlToggle);
            stringBuilder.append(" lineCtrlReverse: ");
            stringBuilder.append(this.lineCtrlReverse);
            stringBuilder.append(" lineCtrlPowerDenial: ");
            stringBuilder.append(this.lineCtrlPowerDenial);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class CdmaNumberInfoRec {
        public int id;
        public String number;
        public byte numberPlan;
        public byte numberType;
        public byte pi;
        public byte si;

        public CdmaNumberInfoRec(int n, String string, int n2, int n3, int n4, int n5) {
            this.number = string;
            this.numberType = (byte)n2;
            this.numberPlan = (byte)n3;
            this.pi = (byte)n4;
            this.si = (byte)n5;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CdmaNumberInfoRec: { id: ");
            stringBuilder.append(CdmaInformationRecords.idToString(this.id));
            stringBuilder.append(", number: <MASKED>, numberType: ");
            stringBuilder.append(this.numberType);
            stringBuilder.append(", numberPlan: ");
            stringBuilder.append(this.numberPlan);
            stringBuilder.append(", pi: ");
            stringBuilder.append(this.pi);
            stringBuilder.append(", si: ");
            stringBuilder.append(this.si);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class CdmaRedirectingNumberInfoRec {
        public static final int REASON_CALLED_DTE_OUT_OF_ORDER = 9;
        public static final int REASON_CALL_FORWARDING_BUSY = 1;
        public static final int REASON_CALL_FORWARDING_BY_THE_CALLED_DTE = 10;
        public static final int REASON_CALL_FORWARDING_NO_REPLY = 2;
        public static final int REASON_CALL_FORWARDING_UNCONDITIONAL = 15;
        public static final int REASON_UNKNOWN = 0;
        public CdmaNumberInfoRec numberInfoRec;
        public int redirectingReason;

        public CdmaRedirectingNumberInfoRec(String string, int n, int n2, int n3, int n4, int n5) {
            this.numberInfoRec = new CdmaNumberInfoRec(5, string, n, n2, n3, n4);
            this.redirectingReason = n5;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CdmaNumberInfoRec: { numberInfoRec: ");
            stringBuilder.append(this.numberInfoRec);
            stringBuilder.append(", redirectingReason: ");
            stringBuilder.append(this.redirectingReason);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class CdmaSignalInfoRec {
        public int alertPitch;
        public boolean isPresent;
        public int signal;
        public int signalType;

        public CdmaSignalInfoRec() {
        }

        public CdmaSignalInfoRec(int n, int n2, int n3, int n4) {
            boolean bl = n != 0;
            this.isPresent = bl;
            this.signalType = n2;
            this.alertPitch = n3;
            this.signal = n4;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CdmaSignalInfo: { isPresent: ");
            stringBuilder.append(this.isPresent);
            stringBuilder.append(", signalType: ");
            stringBuilder.append(this.signalType);
            stringBuilder.append(", alertPitch: ");
            stringBuilder.append(this.alertPitch);
            stringBuilder.append(", signal: ");
            stringBuilder.append(this.signal);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class CdmaT53AudioControlInfoRec {
        public byte downlink;
        public byte uplink;

        public CdmaT53AudioControlInfoRec(int n, int n2) {
            this.uplink = (byte)n;
            this.downlink = (byte)n2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CdmaT53AudioControlInfoRec: { uplink: ");
            stringBuilder.append(this.uplink);
            stringBuilder.append(" downlink: ");
            stringBuilder.append(this.downlink);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class CdmaT53ClirInfoRec {
        public byte cause;

        public CdmaT53ClirInfoRec(int n) {
            this.cause = (byte)n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CdmaT53ClirInfoRec: { cause: ");
            stringBuilder.append(this.cause);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

}

