/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.vibrator.V1_3;

import java.util.ArrayList;

public final class Effect {
    public static final int CLICK = 0;
    public static final int DOUBLE_CLICK = 1;
    public static final int HEAVY_CLICK = 5;
    public static final int POP = 4;
    public static final int RINGTONE_1 = 6;
    public static final int RINGTONE_10 = 15;
    public static final int RINGTONE_11 = 16;
    public static final int RINGTONE_12 = 17;
    public static final int RINGTONE_13 = 18;
    public static final int RINGTONE_14 = 19;
    public static final int RINGTONE_15 = 20;
    public static final int RINGTONE_2 = 7;
    public static final int RINGTONE_3 = 8;
    public static final int RINGTONE_4 = 9;
    public static final int RINGTONE_5 = 10;
    public static final int RINGTONE_6 = 11;
    public static final int RINGTONE_7 = 12;
    public static final int RINGTONE_8 = 13;
    public static final int RINGTONE_9 = 14;
    public static final int TEXTURE_TICK = 21;
    public static final int THUD = 3;
    public static final int TICK = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("CLICK");
        if ((n & 1) == 1) {
            arrayList.add("DOUBLE_CLICK");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("TICK");
            n3 = n2 | 2;
        }
        int n4 = n3;
        if ((n & 3) == 3) {
            arrayList.add("THUD");
            n4 = n3 | 3;
        }
        n2 = n4;
        if ((n & 4) == 4) {
            arrayList.add("POP");
            n2 = n4 | 4;
        }
        n3 = n2;
        if ((n & 5) == 5) {
            arrayList.add("HEAVY_CLICK");
            n3 = n2 | 5;
        }
        n2 = n3;
        if ((n & 6) == 6) {
            arrayList.add("RINGTONE_1");
            n2 = n3 | 6;
        }
        n4 = n2;
        if ((n & 7) == 7) {
            arrayList.add("RINGTONE_2");
            n4 = n2 | 7;
        }
        n3 = n4;
        if ((n & 8) == 8) {
            arrayList.add("RINGTONE_3");
            n3 = n4 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("RINGTONE_4");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("RINGTONE_5");
            n3 = n2 | 10;
        }
        n2 = n3;
        if ((n & 11) == 11) {
            arrayList.add("RINGTONE_6");
            n2 = n3 | 11;
        }
        n3 = n2;
        if ((n & 12) == 12) {
            arrayList.add("RINGTONE_7");
            n3 = n2 | 12;
        }
        n2 = n3;
        if ((n & 13) == 13) {
            arrayList.add("RINGTONE_8");
            n2 = n3 | 13;
        }
        n3 = n2;
        if ((n & 14) == 14) {
            arrayList.add("RINGTONE_9");
            n3 = n2 | 14;
        }
        n4 = n3;
        if ((n & 15) == 15) {
            arrayList.add("RINGTONE_10");
            n4 = n3 | 15;
        }
        n2 = n4;
        if ((n & 16) == 16) {
            arrayList.add("RINGTONE_11");
            n2 = n4 | 16;
        }
        n3 = n2;
        if ((n & 17) == 17) {
            arrayList.add("RINGTONE_12");
            n3 = n2 | 17;
        }
        n2 = n3;
        if ((n & 18) == 18) {
            arrayList.add("RINGTONE_13");
            n2 = n3 | 18;
        }
        n4 = n2;
        if ((n & 19) == 19) {
            arrayList.add("RINGTONE_14");
            n4 = n2 | 19;
        }
        n3 = n4;
        if ((n & 20) == 20) {
            arrayList.add("RINGTONE_15");
            n3 = n4 | 20;
        }
        n2 = n3;
        if ((n & 21) == 21) {
            arrayList.add("TEXTURE_TICK");
            n2 = n3 | 21;
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
            return "CLICK";
        }
        if (n == 1) {
            return "DOUBLE_CLICK";
        }
        if (n == 2) {
            return "TICK";
        }
        if (n == 3) {
            return "THUD";
        }
        if (n == 4) {
            return "POP";
        }
        if (n == 5) {
            return "HEAVY_CLICK";
        }
        if (n == 6) {
            return "RINGTONE_1";
        }
        if (n == 7) {
            return "RINGTONE_2";
        }
        if (n == 8) {
            return "RINGTONE_3";
        }
        if (n == 9) {
            return "RINGTONE_4";
        }
        if (n == 10) {
            return "RINGTONE_5";
        }
        if (n == 11) {
            return "RINGTONE_6";
        }
        if (n == 12) {
            return "RINGTONE_7";
        }
        if (n == 13) {
            return "RINGTONE_8";
        }
        if (n == 14) {
            return "RINGTONE_9";
        }
        if (n == 15) {
            return "RINGTONE_10";
        }
        if (n == 16) {
            return "RINGTONE_11";
        }
        if (n == 17) {
            return "RINGTONE_12";
        }
        if (n == 18) {
            return "RINGTONE_13";
        }
        if (n == 19) {
            return "RINGTONE_14";
        }
        if (n == 20) {
            return "RINGTONE_15";
        }
        if (n == 21) {
            return "TEXTURE_TICK";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

