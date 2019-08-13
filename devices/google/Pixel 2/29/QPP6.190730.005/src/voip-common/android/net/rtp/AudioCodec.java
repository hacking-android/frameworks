/*
 * Decompiled with CFR 0.145.
 */
package android.net.rtp;

import java.util.Arrays;

public class AudioCodec {
    public static final AudioCodec AMR;
    public static final AudioCodec GSM;
    public static final AudioCodec GSM_EFR;
    public static final AudioCodec PCMA;
    public static final AudioCodec PCMU;
    private static final AudioCodec[] sCodecs;
    public final String fmtp;
    public final String rtpmap;
    public final int type;

    static {
        PCMU = new AudioCodec(0, "PCMU/8000", null);
        PCMA = new AudioCodec(8, "PCMA/8000", null);
        GSM = new AudioCodec(3, "GSM/8000", null);
        GSM_EFR = new AudioCodec(96, "GSM-EFR/8000", null);
        AMR = new AudioCodec(97, "AMR/8000", null);
        sCodecs = new AudioCodec[]{GSM_EFR, AMR, GSM, PCMU, PCMA};
    }

    private AudioCodec(int n, String string, String string2) {
        this.type = n;
        this.rtpmap = string;
        this.fmtp = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static AudioCodec getCodec(int n, String string, String string2) {
        Object object;
        Object object2;
        block10 : {
            int n2;
            int n3;
            AudioCodec audioCodec;
            block9 : {
                block8 : {
                    block7 : {
                        if (n < 0) return null;
                        if (n > 127) {
                            return null;
                        }
                        audioCodec = null;
                        object = null;
                        n3 = 0;
                        n2 = 0;
                        if (string != null) break block7;
                        object2 = object;
                        if (n >= 96) break block8;
                        break block9;
                    }
                    String string3 = string.trim().toUpperCase();
                    Object object3 = sCodecs;
                    n3 = ((AudioCodec[])object3).length;
                    do {
                        object2 = object;
                        if (n2 >= n3) break;
                        audioCodec = object3[n2];
                        if (string3.startsWith(audioCodec.rtpmap)) {
                            object3 = string3.substring(audioCodec.rtpmap.length());
                            if (((String)object3).length() != 0) {
                                object2 = object;
                                if (!((String)object3).equals("/1")) break;
                            }
                            object2 = audioCodec;
                            break;
                        }
                        ++n2;
                    } while (true);
                }
                object = string;
                break block10;
            }
            AudioCodec[] arraudioCodec = sCodecs;
            int n4 = arraudioCodec.length;
            n2 = n3;
            do {
                object2 = audioCodec;
                object = string;
                if (n2 >= n4) break;
                object = arraudioCodec[n2];
                if (n == ((AudioCodec)object).type) {
                    object2 = object;
                    object = ((AudioCodec)object).rtpmap;
                    break;
                }
                ++n2;
            } while (true);
        }
        if (object2 == null) {
            return null;
        }
        if (object2 != AMR) return new AudioCodec(n, (String)object, string2);
        if (string2 == null) return new AudioCodec(n, (String)object, string2);
        string = string2.toLowerCase();
        if (string.contains("crc=1")) return null;
        if (string.contains("robust-sorting=1")) return null;
        if (!string.contains("interleaving=")) return new AudioCodec(n, (String)object, string2);
        return null;
    }

    public static AudioCodec[] getCodecs() {
        AudioCodec[] arraudioCodec = sCodecs;
        return Arrays.copyOf(arraudioCodec, arraudioCodec.length);
    }
}

