/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb.gadget.V1_0;

import java.util.ArrayList;

public final class GadgetFunction {
    public static final long ACCESSORY = 2L;
    public static final long ADB = 1L;
    public static final long AUDIO_SOURCE = 64L;
    public static final long MIDI = 8L;
    public static final long MTP = 4L;
    public static final long NONE = 0L;
    public static final long PTP = 16L;
    public static final long RNDIS = 32L;

    public static final String dumpBitfield(long l) {
        ArrayList<String> arrayList = new ArrayList<String>();
        long l2 = 0L;
        arrayList.add("NONE");
        if ((l & 1L) == 1L) {
            arrayList.add("ADB");
            l2 = 0L | 1L;
        }
        long l3 = l2;
        if ((l & 2L) == 2L) {
            arrayList.add("ACCESSORY");
            l3 = l2 | 2L;
        }
        l2 = l3;
        if ((l & 4L) == 4L) {
            arrayList.add("MTP");
            l2 = l3 | 4L;
        }
        l3 = l2;
        if ((l & 8L) == 8L) {
            arrayList.add("MIDI");
            l3 = l2 | 8L;
        }
        long l4 = l3;
        if ((l & 16L) == 16L) {
            arrayList.add("PTP");
            l4 = l3 | 16L;
        }
        l2 = l4;
        if ((l & 32L) == 32L) {
            arrayList.add("RNDIS");
            l2 = l4 | 32L;
        }
        l3 = l2;
        if ((l & 64L) == 64L) {
            arrayList.add("AUDIO_SOURCE");
            l3 = l2 | 64L;
        }
        if (l != l3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Long.toHexString(l3 & l));
            arrayList.add(stringBuilder.toString());
        }
        return String.join((CharSequence)" | ", arrayList);
    }

    public static final String toString(long l) {
        if (l == 0L) {
            return "NONE";
        }
        if (l == 1L) {
            return "ADB";
        }
        if (l == 2L) {
            return "ACCESSORY";
        }
        if (l == 4L) {
            return "MTP";
        }
        if (l == 8L) {
            return "MIDI";
        }
        if (l == 16L) {
            return "PTP";
        }
        if (l == 32L) {
            return "RNDIS";
        }
        if (l == 64L) {
            return "AUDIO_SOURCE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Long.toHexString(l));
        return stringBuilder.toString();
    }
}

