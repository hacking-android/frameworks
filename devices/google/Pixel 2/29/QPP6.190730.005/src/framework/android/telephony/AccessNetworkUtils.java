/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

public class AccessNetworkUtils {
    public static final int INVALID_BAND = -1;

    private AccessNetworkUtils() {
    }

    public static int getDuplexModeForEutranBand(int n) {
        if (n == -1) {
            return 0;
        }
        if (n >= 68) {
            return 0;
        }
        if (n >= 65) {
            return 1;
        }
        if (n >= 47) {
            return 0;
        }
        if (n >= 33) {
            return 2;
        }
        return n >= 1;
    }

    public static int getOperatingBandForEarfcn(int n) {
        if (n > 67535) {
            return -1;
        }
        if (n >= 67366) {
            return -1;
        }
        if (n >= 66436) {
            return 66;
        }
        if (n >= 65536) {
            return 65;
        }
        if (n > 54339) {
            return -1;
        }
        if (n >= 46790) {
            return 46;
        }
        if (n >= 46590) {
            return 45;
        }
        if (n >= 45590) {
            return 44;
        }
        if (n >= 43590) {
            return 43;
        }
        if (n >= 41590) {
            return 42;
        }
        if (n >= 39650) {
            return 41;
        }
        if (n >= 38650) {
            return 40;
        }
        if (n >= 38250) {
            return 39;
        }
        if (n >= 37750) {
            return 38;
        }
        if (n >= 37550) {
            return 37;
        }
        if (n >= 36950) {
            return 36;
        }
        if (n >= 36350) {
            return 35;
        }
        if (n >= 36200) {
            return 34;
        }
        if (n >= 36000) {
            return 33;
        }
        if (n > 10359) {
            return -1;
        }
        if (n >= 9920) {
            return -1;
        }
        if (n >= 9870) {
            return 31;
        }
        if (n >= 9770) {
            return 30;
        }
        if (n >= 9660) {
            return -1;
        }
        if (n >= 9210) {
            return 28;
        }
        if (n >= 9040) {
            return 27;
        }
        if (n >= 8690) {
            return 26;
        }
        if (n >= 8040) {
            return 25;
        }
        if (n >= 7700) {
            return 24;
        }
        if (n >= 7500) {
            return 23;
        }
        if (n >= 6600) {
            return 22;
        }
        if (n >= 6450) {
            return 21;
        }
        if (n >= 6150) {
            return 20;
        }
        if (n >= 6000) {
            return 19;
        }
        if (n >= 5850) {
            return 18;
        }
        if (n >= 5730) {
            return 17;
        }
        if (n > 5379) {
            return -1;
        }
        if (n >= 5280) {
            return 14;
        }
        if (n >= 5180) {
            return 13;
        }
        if (n >= 5010) {
            return 12;
        }
        if (n >= 4750) {
            return 11;
        }
        if (n >= 4150) {
            return 10;
        }
        if (n >= 3800) {
            return 9;
        }
        if (n >= 3450) {
            return 8;
        }
        if (n >= 2750) {
            return 7;
        }
        if (n >= 2650) {
            return 6;
        }
        if (n >= 2400) {
            return 5;
        }
        if (n >= 1950) {
            return 4;
        }
        if (n >= 1200) {
            return 3;
        }
        if (n >= 600) {
            return 2;
        }
        if (n >= 0) {
            return 1;
        }
        return -1;
    }
}

