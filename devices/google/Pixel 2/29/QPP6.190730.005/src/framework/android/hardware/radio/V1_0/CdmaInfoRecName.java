/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaInfoRecName {
    public static final int CALLED_PARTY_NUMBER = 1;
    public static final int CALLING_PARTY_NUMBER = 2;
    public static final int CONNECTED_NUMBER = 3;
    public static final int DISPLAY = 0;
    public static final int EXTENDED_DISPLAY = 7;
    public static final int LINE_CONTROL = 6;
    public static final int REDIRECTING_NUMBER = 5;
    public static final int SIGNAL = 4;
    public static final int T53_AUDIO_CONTROL = 10;
    public static final int T53_CLIR = 8;
    public static final int T53_RELEASE = 9;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("DISPLAY");
        if ((n & 1) == 1) {
            arrayList.add("CALLED_PARTY_NUMBER");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("CALLING_PARTY_NUMBER");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("CONNECTED_NUMBER");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("SIGNAL");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("REDIRECTING_NUMBER");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("LINE_CONTROL");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("EXTENDED_DISPLAY");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("T53_CLIR");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("T53_RELEASE");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("T53_AUDIO_CONTROL");
            n3 = n2 | 10;
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
        if (n == 0) {
            return "DISPLAY";
        }
        if (n == 1) {
            return "CALLED_PARTY_NUMBER";
        }
        if (n == 2) {
            return "CALLING_PARTY_NUMBER";
        }
        if (n == 3) {
            return "CONNECTED_NUMBER";
        }
        if (n == 4) {
            return "SIGNAL";
        }
        if (n == 5) {
            return "REDIRECTING_NUMBER";
        }
        if (n == 6) {
            return "LINE_CONTROL";
        }
        if (n == 7) {
            return "EXTENDED_DISPLAY";
        }
        if (n == 8) {
            return "T53_CLIR";
        }
        if (n == 9) {
            return "T53_RELEASE";
        }
        if (n == 10) {
            return "T53_AUDIO_CONTROL";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

