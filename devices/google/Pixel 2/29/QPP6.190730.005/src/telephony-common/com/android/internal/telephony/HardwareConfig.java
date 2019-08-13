/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import java.util.BitSet;

public class HardwareConfig {
    public static final int DEV_HARDWARE_STATE_DISABLED = 2;
    public static final int DEV_HARDWARE_STATE_ENABLED = 0;
    public static final int DEV_HARDWARE_STATE_STANDBY = 1;
    public static final int DEV_HARDWARE_TYPE_MODEM = 0;
    public static final int DEV_HARDWARE_TYPE_SIM = 1;
    public static final int DEV_MODEM_RIL_MODEL_MULTIPLE = 1;
    public static final int DEV_MODEM_RIL_MODEL_SINGLE = 0;
    static final String LOG_TAG = "HardwareConfig";
    public int maxActiveDataCall;
    public int maxActiveVoiceCall;
    public int maxStandby;
    public String modemUuid;
    public BitSet rat;
    public int rilModel;
    public int state;
    public int type;
    public String uuid;

    public HardwareConfig(int n) {
        this.type = n;
    }

    public HardwareConfig(String arrstring) {
        arrstring = arrstring.split(",");
        int n = this.type = Integer.parseInt(arrstring[0]);
        if (n != 0) {
            if (n == 1) {
                this.assignSim(arrstring[1].trim(), Integer.parseInt(arrstring[2]), arrstring[3].trim());
            }
        } else {
            this.assignModem(arrstring[1].trim(), Integer.parseInt(arrstring[2]), Integer.parseInt(arrstring[3]), Integer.parseInt(arrstring[4]), Integer.parseInt(arrstring[5]), Integer.parseInt(arrstring[6]), Integer.parseInt(arrstring[7]));
        }
    }

    public void assignModem(String object, int n, int n2, int n3, int n4, int n5, int n6) {
        if (this.type == 0) {
            char[] arrc = Integer.toBinaryString(n3).toCharArray();
            this.uuid = object;
            this.state = n;
            this.rilModel = n2;
            this.rat = new BitSet(arrc.length);
            for (n = 0; n < arrc.length; ++n) {
                object = this.rat;
                boolean bl = arrc[n] == '1';
                ((BitSet)object).set(n, bl);
            }
            this.maxActiveVoiceCall = n4;
            this.maxActiveDataCall = n5;
            this.maxStandby = n6;
        }
    }

    public void assignSim(String string, int n, String string2) {
        if (this.type == 1) {
            this.uuid = string;
            this.modemUuid = string2;
            this.state = n;
        }
    }

    public int compareTo(HardwareConfig hardwareConfig) {
        return this.toString().compareTo(hardwareConfig.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.type;
        if (n == 0) {
            stringBuilder.append("Modem ");
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("{ uuid=");
            stringBuilder2.append(this.uuid);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", state=");
            stringBuilder2.append(this.state);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", rilModel=");
            stringBuilder2.append(this.rilModel);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", rat=");
            stringBuilder2.append(this.rat.toString());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", maxActiveVoiceCall=");
            stringBuilder2.append(this.maxActiveVoiceCall);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", maxActiveDataCall=");
            stringBuilder2.append(this.maxActiveDataCall);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", maxStandby=");
            stringBuilder2.append(this.maxStandby);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder.append(" }");
        } else if (n == 1) {
            stringBuilder.append("Sim ");
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("{ uuid=");
            stringBuilder3.append(this.uuid);
            stringBuilder.append(stringBuilder3.toString());
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(", modemUuid=");
            stringBuilder3.append(this.modemUuid);
            stringBuilder.append(stringBuilder3.toString());
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(", state=");
            stringBuilder3.append(this.state);
            stringBuilder.append(stringBuilder3.toString());
            stringBuilder.append(" }");
        } else {
            stringBuilder.append("Invalid Configration");
        }
        return stringBuilder.toString();
    }
}

