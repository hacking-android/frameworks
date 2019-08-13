/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

class MidiPortImpl {
    private static final int DATA_PACKET_OVERHEAD = 9;
    public static final int MAX_PACKET_DATA_SIZE = 1015;
    public static final int MAX_PACKET_SIZE = 1024;
    public static final int PACKET_TYPE_DATA = 1;
    public static final int PACKET_TYPE_FLUSH = 2;
    private static final String TAG = "MidiPort";
    private static final int TIMESTAMP_SIZE = 8;

    MidiPortImpl() {
    }

    public static int getDataOffset(byte[] arrby, int n) {
        return 1;
    }

    public static int getDataSize(byte[] arrby, int n) {
        return n - 9;
    }

    public static long getPacketTimestamp(byte[] arrby, int n) {
        int n2 = n;
        long l = 0L;
        for (n = 0; n < 8; ++n) {
            l = l << 8 | (long)(arrby[--n2] & 255);
        }
        return l;
    }

    public static int getPacketType(byte[] arrby, int n) {
        return arrby[0];
    }

    public static int packData(byte[] arrby, int n, int n2, long l, byte[] arrby2) {
        int n3 = n2;
        if (n2 > 1015) {
            n3 = 1015;
        }
        n2 = 0 + 1;
        arrby2[0] = (byte)(true ? 1 : 0);
        System.arraycopy(arrby, n, arrby2, n2, n3);
        n = n2 + n3;
        n2 = 0;
        while (n2 < 8) {
            arrby2[n] = (byte)l;
            l >>= 8;
            ++n2;
            ++n;
        }
        return n;
    }

    public static int packFlush(byte[] arrby) {
        arrby[0] = (byte)2;
        return 1;
    }
}

