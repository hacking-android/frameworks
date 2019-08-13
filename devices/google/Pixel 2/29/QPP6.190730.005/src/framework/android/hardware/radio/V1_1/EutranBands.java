/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import java.util.ArrayList;

public final class EutranBands {
    public static final int BAND_1 = 1;
    public static final int BAND_10 = 10;
    public static final int BAND_11 = 11;
    public static final int BAND_12 = 12;
    public static final int BAND_13 = 13;
    public static final int BAND_14 = 14;
    public static final int BAND_17 = 17;
    public static final int BAND_18 = 18;
    public static final int BAND_19 = 19;
    public static final int BAND_2 = 2;
    public static final int BAND_20 = 20;
    public static final int BAND_21 = 21;
    public static final int BAND_22 = 22;
    public static final int BAND_23 = 23;
    public static final int BAND_24 = 24;
    public static final int BAND_25 = 25;
    public static final int BAND_26 = 26;
    public static final int BAND_27 = 27;
    public static final int BAND_28 = 28;
    public static final int BAND_3 = 3;
    public static final int BAND_30 = 30;
    public static final int BAND_31 = 31;
    public static final int BAND_33 = 33;
    public static final int BAND_34 = 34;
    public static final int BAND_35 = 35;
    public static final int BAND_36 = 36;
    public static final int BAND_37 = 37;
    public static final int BAND_38 = 38;
    public static final int BAND_39 = 39;
    public static final int BAND_4 = 4;
    public static final int BAND_40 = 40;
    public static final int BAND_41 = 41;
    public static final int BAND_42 = 42;
    public static final int BAND_43 = 43;
    public static final int BAND_44 = 44;
    public static final int BAND_45 = 45;
    public static final int BAND_46 = 46;
    public static final int BAND_47 = 47;
    public static final int BAND_48 = 48;
    public static final int BAND_5 = 5;
    public static final int BAND_6 = 6;
    public static final int BAND_65 = 65;
    public static final int BAND_66 = 66;
    public static final int BAND_68 = 68;
    public static final int BAND_7 = 7;
    public static final int BAND_70 = 70;
    public static final int BAND_8 = 8;
    public static final int BAND_9 = 9;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("BAND_1");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("BAND_2");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("BAND_3");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("BAND_4");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("BAND_5");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("BAND_6");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("BAND_7");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("BAND_8");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("BAND_9");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("BAND_10");
            n3 = n2 | 10;
        }
        int n4 = n3;
        if ((n & 11) == 11) {
            arrayList.add("BAND_11");
            n4 = n3 | 11;
        }
        n2 = n4;
        if ((n & 12) == 12) {
            arrayList.add("BAND_12");
            n2 = n4 | 12;
        }
        n3 = n2;
        if ((n & 13) == 13) {
            arrayList.add("BAND_13");
            n3 = n2 | 13;
        }
        n2 = n3;
        if ((n & 14) == 14) {
            arrayList.add("BAND_14");
            n2 = n3 | 14;
        }
        n3 = n2;
        if ((n & 17) == 17) {
            arrayList.add("BAND_17");
            n3 = n2 | 17;
        }
        n2 = n3;
        if ((n & 18) == 18) {
            arrayList.add("BAND_18");
            n2 = n3 | 18;
        }
        n3 = n2;
        if ((n & 19) == 19) {
            arrayList.add("BAND_19");
            n3 = n2 | 19;
        }
        n2 = n3;
        if ((n & 20) == 20) {
            arrayList.add("BAND_20");
            n2 = n3 | 20;
        }
        n3 = n2;
        if ((n & 21) == 21) {
            arrayList.add("BAND_21");
            n3 = n2 | 21;
        }
        n2 = n3;
        if ((n & 22) == 22) {
            arrayList.add("BAND_22");
            n2 = n3 | 22;
        }
        n3 = n2;
        if ((n & 23) == 23) {
            arrayList.add("BAND_23");
            n3 = n2 | 23;
        }
        n2 = n3;
        if ((n & 24) == 24) {
            arrayList.add("BAND_24");
            n2 = n3 | 24;
        }
        n3 = n2;
        if ((n & 25) == 25) {
            arrayList.add("BAND_25");
            n3 = n2 | 25;
        }
        n2 = n3;
        if ((n & 26) == 26) {
            arrayList.add("BAND_26");
            n2 = n3 | 26;
        }
        n3 = n2;
        if ((n & 27) == 27) {
            arrayList.add("BAND_27");
            n3 = n2 | 27;
        }
        n2 = n3;
        if ((n & 28) == 28) {
            arrayList.add("BAND_28");
            n2 = n3 | 28;
        }
        n3 = n2;
        if ((n & 30) == 30) {
            arrayList.add("BAND_30");
            n3 = n2 | 30;
        }
        n2 = n3;
        if ((n & 31) == 31) {
            arrayList.add("BAND_31");
            n2 = n3 | 31;
        }
        n3 = n2;
        if ((n & 33) == 33) {
            arrayList.add("BAND_33");
            n3 = n2 | 33;
        }
        n2 = n3;
        if ((n & 34) == 34) {
            arrayList.add("BAND_34");
            n2 = n3 | 34;
        }
        n3 = n2;
        if ((n & 35) == 35) {
            arrayList.add("BAND_35");
            n3 = n2 | 35;
        }
        n2 = n3;
        if ((n & 36) == 36) {
            arrayList.add("BAND_36");
            n2 = n3 | 36;
        }
        n3 = n2;
        if ((n & 37) == 37) {
            arrayList.add("BAND_37");
            n3 = n2 | 37;
        }
        n2 = n3;
        if ((n & 38) == 38) {
            arrayList.add("BAND_38");
            n2 = n3 | 38;
        }
        n4 = n2;
        if ((n & 39) == 39) {
            arrayList.add("BAND_39");
            n4 = n2 | 39;
        }
        n3 = n4;
        if ((n & 40) == 40) {
            arrayList.add("BAND_40");
            n3 = n4 | 40;
        }
        n2 = n3;
        if ((n & 41) == 41) {
            arrayList.add("BAND_41");
            n2 = n3 | 41;
        }
        n3 = n2;
        if ((n & 42) == 42) {
            arrayList.add("BAND_42");
            n3 = n2 | 42;
        }
        n2 = n3;
        if ((n & 43) == 43) {
            arrayList.add("BAND_43");
            n2 = n3 | 43;
        }
        n3 = n2;
        if ((n & 44) == 44) {
            arrayList.add("BAND_44");
            n3 = n2 | 44;
        }
        n2 = n3;
        if ((n & 45) == 45) {
            arrayList.add("BAND_45");
            n2 = n3 | 45;
        }
        n4 = n2;
        if ((n & 46) == 46) {
            arrayList.add("BAND_46");
            n4 = n2 | 46;
        }
        n3 = n4;
        if ((n & 47) == 47) {
            arrayList.add("BAND_47");
            n3 = n4 | 47;
        }
        n2 = n3;
        if ((n & 48) == 48) {
            arrayList.add("BAND_48");
            n2 = n3 | 48;
        }
        n3 = n2;
        if ((n & 65) == 65) {
            arrayList.add("BAND_65");
            n3 = n2 | 65;
        }
        n2 = n3;
        if ((n & 66) == 66) {
            arrayList.add("BAND_66");
            n2 = n3 | 66;
        }
        n3 = n2;
        if ((n & 68) == 68) {
            arrayList.add("BAND_68");
            n3 = n2 | 68;
        }
        n2 = n3;
        if ((n & 70) == 70) {
            arrayList.add("BAND_70");
            n2 = n3 | 70;
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
        if (n == 1) {
            return "BAND_1";
        }
        if (n == 2) {
            return "BAND_2";
        }
        if (n == 3) {
            return "BAND_3";
        }
        if (n == 4) {
            return "BAND_4";
        }
        if (n == 5) {
            return "BAND_5";
        }
        if (n == 6) {
            return "BAND_6";
        }
        if (n == 7) {
            return "BAND_7";
        }
        if (n == 8) {
            return "BAND_8";
        }
        if (n == 9) {
            return "BAND_9";
        }
        if (n == 10) {
            return "BAND_10";
        }
        if (n == 11) {
            return "BAND_11";
        }
        if (n == 12) {
            return "BAND_12";
        }
        if (n == 13) {
            return "BAND_13";
        }
        if (n == 14) {
            return "BAND_14";
        }
        if (n == 17) {
            return "BAND_17";
        }
        if (n == 18) {
            return "BAND_18";
        }
        if (n == 19) {
            return "BAND_19";
        }
        if (n == 20) {
            return "BAND_20";
        }
        if (n == 21) {
            return "BAND_21";
        }
        if (n == 22) {
            return "BAND_22";
        }
        if (n == 23) {
            return "BAND_23";
        }
        if (n == 24) {
            return "BAND_24";
        }
        if (n == 25) {
            return "BAND_25";
        }
        if (n == 26) {
            return "BAND_26";
        }
        if (n == 27) {
            return "BAND_27";
        }
        if (n == 28) {
            return "BAND_28";
        }
        if (n == 30) {
            return "BAND_30";
        }
        if (n == 31) {
            return "BAND_31";
        }
        if (n == 33) {
            return "BAND_33";
        }
        if (n == 34) {
            return "BAND_34";
        }
        if (n == 35) {
            return "BAND_35";
        }
        if (n == 36) {
            return "BAND_36";
        }
        if (n == 37) {
            return "BAND_37";
        }
        if (n == 38) {
            return "BAND_38";
        }
        if (n == 39) {
            return "BAND_39";
        }
        if (n == 40) {
            return "BAND_40";
        }
        if (n == 41) {
            return "BAND_41";
        }
        if (n == 42) {
            return "BAND_42";
        }
        if (n == 43) {
            return "BAND_43";
        }
        if (n == 44) {
            return "BAND_44";
        }
        if (n == 45) {
            return "BAND_45";
        }
        if (n == 46) {
            return "BAND_46";
        }
        if (n == 47) {
            return "BAND_47";
        }
        if (n == 48) {
            return "BAND_48";
        }
        if (n == 65) {
            return "BAND_65";
        }
        if (n == 66) {
            return "BAND_66";
        }
        if (n == 68) {
            return "BAND_68";
        }
        if (n == 70) {
            return "BAND_70";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

