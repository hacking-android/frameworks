/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class NvItem {
    public static final int CDMA_1X_ADVANCED_ENABLED = 57;
    public static final int CDMA_ACCOLC = 4;
    public static final int CDMA_BC10 = 52;
    public static final int CDMA_BC14 = 53;
    public static final int CDMA_EHRPD_ENABLED = 58;
    public static final int CDMA_EHRPD_FORCED = 59;
    public static final int CDMA_MDN = 3;
    public static final int CDMA_MEID = 1;
    public static final int CDMA_MIN = 2;
    public static final int CDMA_PRL_VERSION = 51;
    public static final int CDMA_SO68 = 54;
    public static final int CDMA_SO73_COP0 = 55;
    public static final int CDMA_SO73_COP1TO7 = 56;
    public static final int DEVICE_MSL = 11;
    public static final int LTE_BAND_ENABLE_25 = 71;
    public static final int LTE_BAND_ENABLE_26 = 72;
    public static final int LTE_BAND_ENABLE_41 = 73;
    public static final int LTE_HIDDEN_BAND_PRIORITY_25 = 77;
    public static final int LTE_HIDDEN_BAND_PRIORITY_26 = 78;
    public static final int LTE_HIDDEN_BAND_PRIORITY_41 = 79;
    public static final int LTE_SCAN_PRIORITY_25 = 74;
    public static final int LTE_SCAN_PRIORITY_26 = 75;
    public static final int LTE_SCAN_PRIORITY_41 = 76;
    public static final int MIP_PROFILE_AAA_AUTH = 33;
    public static final int MIP_PROFILE_AAA_SPI = 39;
    public static final int MIP_PROFILE_HA_AUTH = 34;
    public static final int MIP_PROFILE_HA_SPI = 38;
    public static final int MIP_PROFILE_HOME_ADDRESS = 32;
    public static final int MIP_PROFILE_MN_AAA_SS = 41;
    public static final int MIP_PROFILE_MN_HA_SS = 40;
    public static final int MIP_PROFILE_NAI = 31;
    public static final int MIP_PROFILE_PRI_HA_ADDR = 35;
    public static final int MIP_PROFILE_REV_TUN_PREF = 37;
    public static final int MIP_PROFILE_SEC_HA_ADDR = 36;
    public static final int OMADM_HFA_LEVEL = 18;
    public static final int RTN_ACTIVATION_DATE = 13;
    public static final int RTN_LIFE_CALLS = 15;
    public static final int RTN_LIFE_DATA_RX = 17;
    public static final int RTN_LIFE_DATA_TX = 16;
    public static final int RTN_LIFE_TIMER = 14;
    public static final int RTN_RECONDITIONED_STATUS = 12;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("CDMA_MEID");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("CDMA_MIN");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("CDMA_MDN");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CDMA_ACCOLC");
            n3 = n2 | 4;
        }
        int n4 = n3;
        if ((n & 11) == 11) {
            arrayList.add("DEVICE_MSL");
            n4 = n3 | 11;
        }
        n2 = n4;
        if ((n & 12) == 12) {
            arrayList.add("RTN_RECONDITIONED_STATUS");
            n2 = n4 | 12;
        }
        n3 = n2;
        if ((n & 13) == 13) {
            arrayList.add("RTN_ACTIVATION_DATE");
            n3 = n2 | 13;
        }
        n2 = n3;
        if ((n & 14) == 14) {
            arrayList.add("RTN_LIFE_TIMER");
            n2 = n3 | 14;
        }
        n3 = n2;
        if ((n & 15) == 15) {
            arrayList.add("RTN_LIFE_CALLS");
            n3 = n2 | 15;
        }
        n2 = n3;
        if ((n & 16) == 16) {
            arrayList.add("RTN_LIFE_DATA_TX");
            n2 = n3 | 16;
        }
        n3 = n2;
        if ((n & 17) == 17) {
            arrayList.add("RTN_LIFE_DATA_RX");
            n3 = n2 | 17;
        }
        n4 = n3;
        if ((n & 18) == 18) {
            arrayList.add("OMADM_HFA_LEVEL");
            n4 = n3 | 18;
        }
        n2 = n4;
        if ((n & 31) == 31) {
            arrayList.add("MIP_PROFILE_NAI");
            n2 = n4 | 31;
        }
        n3 = n2;
        if ((n & 32) == 32) {
            arrayList.add("MIP_PROFILE_HOME_ADDRESS");
            n3 = n2 | 32;
        }
        n2 = n3;
        if ((n & 33) == 33) {
            arrayList.add("MIP_PROFILE_AAA_AUTH");
            n2 = n3 | 33;
        }
        n3 = n2;
        if ((n & 34) == 34) {
            arrayList.add("MIP_PROFILE_HA_AUTH");
            n3 = n2 | 34;
        }
        n4 = n3;
        if ((n & 35) == 35) {
            arrayList.add("MIP_PROFILE_PRI_HA_ADDR");
            n4 = n3 | 35;
        }
        n2 = n4;
        if ((n & 36) == 36) {
            arrayList.add("MIP_PROFILE_SEC_HA_ADDR");
            n2 = n4 | 36;
        }
        n3 = n2;
        if ((n & 37) == 37) {
            arrayList.add("MIP_PROFILE_REV_TUN_PREF");
            n3 = n2 | 37;
        }
        n2 = n3;
        if ((n & 38) == 38) {
            arrayList.add("MIP_PROFILE_HA_SPI");
            n2 = n3 | 38;
        }
        n3 = n2;
        if ((n & 39) == 39) {
            arrayList.add("MIP_PROFILE_AAA_SPI");
            n3 = n2 | 39;
        }
        n2 = n3;
        if ((n & 40) == 40) {
            arrayList.add("MIP_PROFILE_MN_HA_SS");
            n2 = n3 | 40;
        }
        n3 = n2;
        if ((n & 41) == 41) {
            arrayList.add("MIP_PROFILE_MN_AAA_SS");
            n3 = n2 | 41;
        }
        n2 = n3;
        if ((n & 51) == 51) {
            arrayList.add("CDMA_PRL_VERSION");
            n2 = n3 | 51;
        }
        n3 = n2;
        if ((n & 52) == 52) {
            arrayList.add("CDMA_BC10");
            n3 = n2 | 52;
        }
        n2 = n3;
        if ((n & 53) == 53) {
            arrayList.add("CDMA_BC14");
            n2 = n3 | 53;
        }
        n3 = n2;
        if ((n & 54) == 54) {
            arrayList.add("CDMA_SO68");
            n3 = n2 | 54;
        }
        n2 = n3;
        if ((n & 55) == 55) {
            arrayList.add("CDMA_SO73_COP0");
            n2 = n3 | 55;
        }
        n3 = n2;
        if ((n & 56) == 56) {
            arrayList.add("CDMA_SO73_COP1TO7");
            n3 = n2 | 56;
        }
        n4 = n3;
        if ((n & 57) == 57) {
            arrayList.add("CDMA_1X_ADVANCED_ENABLED");
            n4 = n3 | 57;
        }
        n2 = n4;
        if ((n & 58) == 58) {
            arrayList.add("CDMA_EHRPD_ENABLED");
            n2 = n4 | 58;
        }
        n3 = n2;
        if ((n & 59) == 59) {
            arrayList.add("CDMA_EHRPD_FORCED");
            n3 = n2 | 59;
        }
        n4 = n3;
        if ((n & 71) == 71) {
            arrayList.add("LTE_BAND_ENABLE_25");
            n4 = n3 | 71;
        }
        n2 = n4;
        if ((n & 72) == 72) {
            arrayList.add("LTE_BAND_ENABLE_26");
            n2 = n4 | 72;
        }
        n3 = n2;
        if ((n & 73) == 73) {
            arrayList.add("LTE_BAND_ENABLE_41");
            n3 = n2 | 73;
        }
        n4 = n3;
        if ((n & 74) == 74) {
            arrayList.add("LTE_SCAN_PRIORITY_25");
            n4 = n3 | 74;
        }
        n2 = n4;
        if ((n & 75) == 75) {
            arrayList.add("LTE_SCAN_PRIORITY_26");
            n2 = n4 | 75;
        }
        n3 = n2;
        if ((n & 76) == 76) {
            arrayList.add("LTE_SCAN_PRIORITY_41");
            n3 = n2 | 76;
        }
        n4 = n3;
        if ((n & 77) == 77) {
            arrayList.add("LTE_HIDDEN_BAND_PRIORITY_25");
            n4 = n3 | 77;
        }
        n2 = n4;
        if ((n & 78) == 78) {
            arrayList.add("LTE_HIDDEN_BAND_PRIORITY_26");
            n2 = n4 | 78;
        }
        n3 = n2;
        if ((n & 79) == 79) {
            arrayList.add("LTE_HIDDEN_BAND_PRIORITY_41");
            n3 = n2 | 79;
        }
        if (n != n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(n3 & n));
            arrayList.add(stringBuilder.toString());
        }
        return String.join((CharSequence)" | ", arrayList);
    }

    public static final String toString(int n) {
        if (n == 1) {
            return "CDMA_MEID";
        }
        if (n == 2) {
            return "CDMA_MIN";
        }
        if (n == 3) {
            return "CDMA_MDN";
        }
        if (n == 4) {
            return "CDMA_ACCOLC";
        }
        if (n == 11) {
            return "DEVICE_MSL";
        }
        if (n == 12) {
            return "RTN_RECONDITIONED_STATUS";
        }
        if (n == 13) {
            return "RTN_ACTIVATION_DATE";
        }
        if (n == 14) {
            return "RTN_LIFE_TIMER";
        }
        if (n == 15) {
            return "RTN_LIFE_CALLS";
        }
        if (n == 16) {
            return "RTN_LIFE_DATA_TX";
        }
        if (n == 17) {
            return "RTN_LIFE_DATA_RX";
        }
        if (n == 18) {
            return "OMADM_HFA_LEVEL";
        }
        if (n == 31) {
            return "MIP_PROFILE_NAI";
        }
        if (n == 32) {
            return "MIP_PROFILE_HOME_ADDRESS";
        }
        if (n == 33) {
            return "MIP_PROFILE_AAA_AUTH";
        }
        if (n == 34) {
            return "MIP_PROFILE_HA_AUTH";
        }
        if (n == 35) {
            return "MIP_PROFILE_PRI_HA_ADDR";
        }
        if (n == 36) {
            return "MIP_PROFILE_SEC_HA_ADDR";
        }
        if (n == 37) {
            return "MIP_PROFILE_REV_TUN_PREF";
        }
        if (n == 38) {
            return "MIP_PROFILE_HA_SPI";
        }
        if (n == 39) {
            return "MIP_PROFILE_AAA_SPI";
        }
        if (n == 40) {
            return "MIP_PROFILE_MN_HA_SS";
        }
        if (n == 41) {
            return "MIP_PROFILE_MN_AAA_SS";
        }
        if (n == 51) {
            return "CDMA_PRL_VERSION";
        }
        if (n == 52) {
            return "CDMA_BC10";
        }
        if (n == 53) {
            return "CDMA_BC14";
        }
        if (n == 54) {
            return "CDMA_SO68";
        }
        if (n == 55) {
            return "CDMA_SO73_COP0";
        }
        if (n == 56) {
            return "CDMA_SO73_COP1TO7";
        }
        if (n == 57) {
            return "CDMA_1X_ADVANCED_ENABLED";
        }
        if (n == 58) {
            return "CDMA_EHRPD_ENABLED";
        }
        if (n == 59) {
            return "CDMA_EHRPD_FORCED";
        }
        if (n == 71) {
            return "LTE_BAND_ENABLE_25";
        }
        if (n == 72) {
            return "LTE_BAND_ENABLE_26";
        }
        if (n == 73) {
            return "LTE_BAND_ENABLE_41";
        }
        if (n == 74) {
            return "LTE_SCAN_PRIORITY_25";
        }
        if (n == 75) {
            return "LTE_SCAN_PRIORITY_26";
        }
        if (n == 76) {
            return "LTE_SCAN_PRIORITY_41";
        }
        if (n == 77) {
            return "LTE_HIDDEN_BAND_PRIORITY_25";
        }
        if (n == 78) {
            return "LTE_HIDDEN_BAND_PRIORITY_26";
        }
        if (n == 79) {
            return "LTE_HIDDEN_BAND_PRIORITY_41";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

