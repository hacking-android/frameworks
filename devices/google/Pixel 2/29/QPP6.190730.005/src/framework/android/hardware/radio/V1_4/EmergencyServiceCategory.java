/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import java.util.ArrayList;

public final class EmergencyServiceCategory {
    public static final int AIEC = 64;
    public static final int AMBULANCE = 2;
    public static final int FIRE_BRIGADE = 4;
    public static final int MARINE_GUARD = 8;
    public static final int MIEC = 32;
    public static final int MOUNTAIN_RESCUE = 16;
    public static final int POLICE = 1;
    public static final int UNSPECIFIED = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNSPECIFIED");
        if ((n & 1) == 1) {
            arrayList.add("POLICE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("AMBULANCE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("FIRE_BRIGADE");
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("MARINE_GUARD");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 16) == 16) {
            arrayList.add("MOUNTAIN_RESCUE");
            n2 = n3 | 16;
        }
        n3 = n2;
        if ((n & 32) == 32) {
            arrayList.add("MIEC");
            n3 = n2 | 32;
        }
        n2 = n3;
        if ((n & 64) == 64) {
            arrayList.add("AIEC");
            n2 = n3 | 64;
        }
        if (n != n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(n2 & n));
            arrayList.add(stringBuilder.toString());
        }
        return String.join((CharSequence)" | ", arrayList);
    }

    public static final String toString(int n) {
        if (n == 0) {
            return "UNSPECIFIED";
        }
        if (n == 1) {
            return "POLICE";
        }
        if (n == 2) {
            return "AMBULANCE";
        }
        if (n == 4) {
            return "FIRE_BRIGADE";
        }
        if (n == 8) {
            return "MARINE_GUARD";
        }
        if (n == 16) {
            return "MOUNTAIN_RESCUE";
        }
        if (n == 32) {
            return "MIEC";
        }
        if (n == 64) {
            return "AIEC";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

