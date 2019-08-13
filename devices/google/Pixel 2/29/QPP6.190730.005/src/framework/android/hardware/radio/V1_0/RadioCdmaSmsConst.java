/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RadioCdmaSmsConst {
    public static final int ADDRESS_MAX = 36;
    public static final int BEARER_DATA_MAX = 255;
    public static final int IP_ADDRESS_SIZE = 4;
    public static final int MAX_UD_HEADERS = 7;
    public static final int SUBADDRESS_MAX = 36;
    public static final int UDH_ANIM_NUM_BITMAPS = 4;
    public static final int UDH_EO_DATA_SEGMENT_MAX = 131;
    public static final int UDH_LARGE_BITMAP_SIZE = 32;
    public static final int UDH_LARGE_PIC_SIZE = 128;
    public static final int UDH_MAX_SND_SIZE = 128;
    public static final int UDH_OTHER_SIZE = 226;
    public static final int UDH_SMALL_BITMAP_SIZE = 8;
    public static final int UDH_SMALL_PIC_SIZE = 32;
    public static final int UDH_VAR_PIC_SIZE = 134;
    public static final int USER_DATA_MAX = 229;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 36) == 36) {
            arrayList.add("ADDRESS_MAX");
            n2 = 0 | 36;
        }
        int n3 = n2;
        if ((n & 36) == 36) {
            arrayList.add("SUBADDRESS_MAX");
            n3 = n2 | 36;
        }
        n2 = n3;
        if ((n & 255) == 255) {
            arrayList.add("BEARER_DATA_MAX");
            n2 = n3 | 255;
        }
        n3 = n2;
        if ((n & 128) == 128) {
            arrayList.add("UDH_MAX_SND_SIZE");
            n3 = n2 | 128;
        }
        n2 = n3;
        if ((n & 131) == 131) {
            arrayList.add("UDH_EO_DATA_SEGMENT_MAX");
            n2 = n3 | 131;
        }
        n3 = n2;
        if ((n & 7) == 7) {
            arrayList.add("MAX_UD_HEADERS");
            n3 = n2 | 7;
        }
        int n4 = n3;
        if ((n & 229) == 229) {
            arrayList.add("USER_DATA_MAX");
            n4 = n3 | 229;
        }
        n2 = n4;
        if ((n & 128) == 128) {
            arrayList.add("UDH_LARGE_PIC_SIZE");
            n2 = n4 | 128;
        }
        n3 = n2;
        if ((n & 32) == 32) {
            arrayList.add("UDH_SMALL_PIC_SIZE");
            n3 = n2 | 32;
        }
        n2 = n3;
        if ((n & 134) == 134) {
            arrayList.add("UDH_VAR_PIC_SIZE");
            n2 = n3 | 134;
        }
        n4 = n2;
        if ((n & 4) == 4) {
            arrayList.add("UDH_ANIM_NUM_BITMAPS");
            n4 = n2 | 4;
        }
        n3 = n4;
        if ((n & 32) == 32) {
            arrayList.add("UDH_LARGE_BITMAP_SIZE");
            n3 = n4 | 32;
        }
        n2 = n3;
        if ((n & 8) == 8) {
            arrayList.add("UDH_SMALL_BITMAP_SIZE");
            n2 = n3 | 8;
        }
        n3 = n2;
        if ((n & 226) == 226) {
            arrayList.add("UDH_OTHER_SIZE");
            n3 = n2 | 226;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("IP_ADDRESS_SIZE");
            n2 = n3 | 4;
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
        if (n == 36) {
            return "ADDRESS_MAX";
        }
        if (n == 36) {
            return "SUBADDRESS_MAX";
        }
        if (n == 255) {
            return "BEARER_DATA_MAX";
        }
        if (n == 128) {
            return "UDH_MAX_SND_SIZE";
        }
        if (n == 131) {
            return "UDH_EO_DATA_SEGMENT_MAX";
        }
        if (n == 7) {
            return "MAX_UD_HEADERS";
        }
        if (n == 229) {
            return "USER_DATA_MAX";
        }
        if (n == 128) {
            return "UDH_LARGE_PIC_SIZE";
        }
        if (n == 32) {
            return "UDH_SMALL_PIC_SIZE";
        }
        if (n == 134) {
            return "UDH_VAR_PIC_SIZE";
        }
        if (n == 4) {
            return "UDH_ANIM_NUM_BITMAPS";
        }
        if (n == 32) {
            return "UDH_LARGE_BITMAP_SIZE";
        }
        if (n == 8) {
            return "UDH_SMALL_BITMAP_SIZE";
        }
        if (n == 226) {
            return "UDH_OTHER_SIZE";
        }
        if (n == 4) {
            return "IP_ADDRESS_SIZE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

