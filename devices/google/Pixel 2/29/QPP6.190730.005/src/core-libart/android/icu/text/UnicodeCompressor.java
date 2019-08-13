/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.SCSU;

public final class UnicodeCompressor
implements SCSU {
    private static boolean[] sSingleTagTable = new boolean[]{false, true, true, true, true, true, true, true, true, false, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static boolean[] sUnicodeTagTable = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private int fCurrentWindow = 0;
    private int[] fIndexCount = new int[256];
    private int fMode = 0;
    private int[] fOffsets = new int[8];
    private int fTimeStamp = 0;
    private int[] fTimeStamps = new int[8];

    public UnicodeCompressor() {
        this.reset();
    }

    public static byte[] compress(String string) {
        return UnicodeCompressor.compress(string.toCharArray(), 0, string.length());
    }

    public static byte[] compress(char[] arrobject, int n, int n2) {
        UnicodeCompressor unicodeCompressor = new UnicodeCompressor();
        int n3 = Math.max(4, (n2 - n) * 3 + 1);
        byte[] arrby = new byte[n3];
        n = unicodeCompressor.compress((char[])arrobject, n, n2, null, arrby, 0, n3);
        arrobject = new byte[n];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrobject, (int)0, (int)n);
        return arrobject;
    }

    private int findDynamicWindow(int n) {
        for (int i = 7; i >= 0; --i) {
            if (!this.inDynamicWindow(n, i)) continue;
            int[] arrn = this.fTimeStamps;
            arrn[i] = arrn[i] + 1;
            return i;
        }
        return -1;
    }

    private static int findStaticWindow(int n) {
        for (int i = 7; i >= 0; --i) {
            if (!UnicodeCompressor.inStaticWindow(n, i)) continue;
            return i;
        }
        return -1;
    }

    private int getLRDefinedWindow() {
        int n = Integer.MAX_VALUE;
        int n2 = -1;
        for (int i = 7; i >= 0; --i) {
            int[] arrn = this.fTimeStamps;
            int n3 = n;
            if (arrn[i] < n) {
                n3 = arrn[i];
                n2 = i;
            }
            n = n3;
        }
        return n2;
    }

    private boolean inDynamicWindow(int n, int n2) {
        int[] arrn = this.fOffsets;
        boolean bl = n >= arrn[n2] && n < arrn[n2] + 128;
        return bl;
    }

    private static boolean inStaticWindow(int n, int n2) {
        boolean bl = n >= sOffsets[n2] && n < sOffsets[n2] + 128;
        return bl;
    }

    private static boolean isCompressible(int n) {
        boolean bl = n < 13312 || n >= 57344;
        return bl;
    }

    private static int makeIndex(int n) {
        if (n >= 192 && n < 320) {
            return 249;
        }
        if (n >= 592 && n < 720) {
            return 250;
        }
        if (n >= 880 && n < 1008) {
            return 251;
        }
        if (n >= 1328 && n < 1424) {
            return 252;
        }
        if (n >= 12352 && n < 12448) {
            return 253;
        }
        if (n >= 12448 && n < 12576) {
            return 254;
        }
        if (n >= 65376 && n < 65439) {
            return 255;
        }
        if (n >= 128 && n < 13312) {
            return n / 128 & 255;
        }
        if (n >= 57344 && n <= 65535) {
            return (n - 44032) / 128 & 255;
        }
        return 0;
    }

    public int compress(char[] arrc, int n, int n2, int[] arrn, byte[] arrby, int n3, int n4) {
        int n5 = n3;
        int n6 = n;
        if (arrby.length >= 4 && n4 - n3 >= 4) {
            int n7;
            int n8;
            block0 : do {
                int[] arrn2;
                int n9;
                n7 = n5;
                n8 = n6;
                if (n6 >= n2) break;
                n7 = n5;
                n8 = n6;
                if (n5 >= n4) break;
                int n10 = this.fMode;
                n8 = n5;
                n7 = n6;
                if (n10 != 0) {
                    n8 = n5;
                    n7 = n6;
                    if (n10 != 1) continue;
                    do {
                        n5 = n8;
                        n6 = n7;
                        if (n7 >= n2) continue block0;
                        n5 = n8;
                        n6 = n7;
                        if (n8 >= n4) continue block0;
                        n5 = n7 + 1;
                        n10 = arrc[n7];
                        n7 = n5 < n2 ? arrc[n5] : -1;
                        if (UnicodeCompressor.isCompressible(n10) && (n7 == -1 || UnicodeCompressor.isCompressible(n7))) {
                            if (n10 < 128) {
                                n6 = n10 & 255;
                                if (n7 != -1 && n7 < 128 && !sSingleTagTable[n6]) {
                                    if (n8 + 1 >= n4) {
                                        n2 = n5 - 1;
                                        n7 = n8;
                                        n8 = n2;
                                        break block0;
                                    }
                                    n7 = this.fCurrentWindow;
                                    n10 = n8 + 1;
                                    arrby[n8] = (byte)(n7 + 224);
                                    n8 = n10 + 1;
                                    arrby[n10] = (byte)n6;
                                    arrn2 = this.fTimeStamps;
                                    this.fTimeStamp = n6 = this.fTimeStamp + 1;
                                    arrn2[n7] = n6;
                                    this.fMode = 0;
                                    n6 = n5;
                                    n5 = n8;
                                    continue block0;
                                }
                                if (n8 + 1 >= n4) {
                                    n2 = n5 - 1;
                                    n7 = n8;
                                    n8 = n2;
                                    break block0;
                                }
                                n7 = n8 + 1;
                                arrby[n8] = (byte)(false ? 1 : 0);
                                n8 = n7 + 1;
                                arrby[n7] = (byte)n6;
                                n7 = n5;
                                continue;
                            }
                            n6 = this.findDynamicWindow(n10);
                            if (n6 != -1) {
                                if (this.inDynamicWindow(n7, n6)) {
                                    if (n8 + 1 >= n4) {
                                        n2 = n5 - 1;
                                        n7 = n8;
                                        n8 = n2;
                                        break block0;
                                    }
                                    n7 = n8 + 1;
                                    arrby[n8] = (byte)(n6 + 224);
                                    n8 = n7 + 1;
                                    arrby[n7] = (byte)(n10 - this.fOffsets[n6] + 128);
                                    arrn2 = this.fTimeStamps;
                                    this.fTimeStamp = n7 = this.fTimeStamp + 1;
                                    arrn2[n6] = n7;
                                    this.fCurrentWindow = n6;
                                    this.fMode = 0;
                                    n6 = n5;
                                    n5 = n8;
                                    continue block0;
                                }
                                if (n8 + 2 >= n4) {
                                    n2 = n5 - 1;
                                    n7 = n8;
                                    n8 = n2;
                                    break block0;
                                }
                                n6 = n10 >>> 8;
                                n7 = n8;
                                if (sUnicodeTagTable[n6]) {
                                    arrby[n8] = (byte)-16;
                                    n7 = n8 + 1;
                                }
                                n9 = n7 + 1;
                                arrby[n7] = (byte)n6;
                                n8 = n9 + 1;
                                arrby[n9] = (byte)(n10 & 255);
                                n7 = n5;
                                continue;
                            }
                            n9 = UnicodeCompressor.makeIndex(n10);
                            arrn2 = this.fIndexCount;
                            arrn2[n9] = arrn2[n9] + 1;
                            n6 = n5 + 1 < n2 ? arrc[n5 + 1] : -1;
                            if (this.fIndexCount[n9] <= 1 && (n9 != UnicodeCompressor.makeIndex(n7) || n9 != UnicodeCompressor.makeIndex(n6))) {
                                if (n8 + 2 >= n4) {
                                    n2 = n5 - 1;
                                    n7 = n8;
                                    n8 = n2;
                                    break block0;
                                }
                                n6 = n10 >>> 8;
                                n7 = n8;
                                if (sUnicodeTagTable[n6]) {
                                    arrby[n8] = (byte)-16;
                                    n7 = n8 + 1;
                                }
                                n9 = n7 + 1;
                                arrby[n7] = (byte)n6;
                                n8 = n9 + 1;
                                arrby[n9] = (byte)(n10 & 255);
                                n7 = n5;
                                continue;
                            }
                            if (n8 + 2 >= n4) {
                                n2 = n5 - 1;
                                n7 = n8;
                                n8 = n2;
                                break block0;
                            }
                            n7 = this.getLRDefinedWindow();
                            n6 = n8 + 1;
                            arrby[n8] = (byte)(n7 + 232);
                            n8 = n6 + 1;
                            arrby[n6] = (byte)n9;
                            arrby[n8] = (byte)(n10 - sOffsetTable[n9] + 128);
                            this.fOffsets[n7] = sOffsetTable[n9];
                            this.fCurrentWindow = n7;
                            arrn2 = this.fTimeStamps;
                            this.fTimeStamp = n6 = this.fTimeStamp + 1;
                            arrn2[n7] = n6;
                            this.fMode = 0;
                            n6 = n5;
                            n5 = ++n8;
                            continue block0;
                        }
                        if (n8 + 2 >= n4) {
                            n2 = n5 - 1;
                            n7 = n8;
                            n8 = n2;
                            break block0;
                        }
                        n6 = n10 >>> 8;
                        n7 = n8;
                        if (sUnicodeTagTable[n6]) {
                            arrby[n8] = (byte)-16;
                            n7 = n8 + 1;
                        }
                        n9 = n7 + 1;
                        arrby[n7] = (byte)n6;
                        n8 = n9 + 1;
                        arrby[n9] = (byte)(n10 & 255);
                        n7 = n5;
                    } while (true);
                }
                do {
                    n5 = n8;
                    n6 = n7;
                    if (n7 >= n2) continue block0;
                    n5 = n8;
                    n6 = n7;
                    if (n8 >= n4) continue block0;
                    n5 = n7 + 1;
                    n10 = arrc[n7];
                    n7 = n5 < n2 ? arrc[n5] : -1;
                    if (n10 < 128) {
                        n6 = n10 & 255;
                        n7 = n8;
                        if (sSingleTagTable[n6]) {
                            if (n8 + 1 >= n4) {
                                n2 = n5 - 1;
                                n7 = n8;
                                n8 = n2;
                                break block0;
                            }
                            arrby[n8] = (byte)(true ? 1 : 0);
                            n7 = n8 + 1;
                        }
                        arrby[n7] = (byte)n6;
                        n8 = n7 + 1;
                        n7 = n5;
                        continue;
                    }
                    if (this.inDynamicWindow(n10, this.fCurrentWindow)) {
                        arrby[n8] = (byte)(n10 - this.fOffsets[this.fCurrentWindow] + 128);
                        ++n8;
                        n7 = n5;
                        continue;
                    }
                    if (!UnicodeCompressor.isCompressible(n10)) {
                        if (n7 != -1 && UnicodeCompressor.isCompressible(n7)) {
                            if (n8 + 2 >= n4) {
                                n2 = -1 + n5;
                                n7 = n8;
                                n8 = n2;
                                break block0;
                            }
                            n7 = n8 + 1;
                            arrby[n8] = (byte)14;
                            n8 = n7 + 1;
                            arrby[n7] = (byte)(n10 >>> 8);
                            arrby[n8] = (byte)(n10 & 255);
                            ++n8;
                            n7 = n5;
                            continue;
                        }
                        if (n8 + 3 >= n4) {
                            n2 = -1 + n5;
                            n7 = n8;
                            n8 = n2;
                            break block0;
                        }
                        n7 = n8 + 1;
                        arrby[n8] = (byte)15;
                        n6 = n10 >>> 8;
                        if (sUnicodeTagTable[n6]) {
                            n8 = n7 + 1;
                            arrby[n7] = (byte)-16;
                        } else {
                            n8 = n7;
                        }
                        n7 = n8 + 1;
                        arrby[n8] = (byte)n6;
                        arrby[n7] = (byte)(n10 & 255);
                        this.fMode = 1;
                        n6 = n5;
                        n5 = n7 + 1;
                        continue block0;
                    }
                    n9 = this.findDynamicWindow(n10);
                    if (n9 != -1) {
                        n6 = n5 + 1 < n2 ? arrc[n5 + 1] : -1;
                        if (this.inDynamicWindow(n7, n9) && this.inDynamicWindow(n6, n9)) {
                            if (n8 + 1 >= n4) {
                                n2 = -1 + n5;
                                n7 = n8;
                                n8 = n2;
                                break block0;
                            }
                            n7 = n8 + 1;
                            arrby[n8] = (byte)(n9 + 16);
                            n8 = n7 + 1;
                            arrby[n7] = (byte)(n10 - this.fOffsets[n9] + 128);
                            arrn2 = this.fTimeStamps;
                            this.fTimeStamp = n7 = this.fTimeStamp + 1;
                            arrn2[n9] = n7;
                            this.fCurrentWindow = n9;
                            n7 = n5;
                            continue;
                        }
                        if (n8 + 1 >= n4) {
                            n2 = -1 + n5;
                            n7 = n8;
                            n8 = n2;
                            break block0;
                        }
                        n7 = n8 + 1;
                        arrby[n8] = (byte)(n9 + 1);
                        n8 = n7 + 1;
                        arrby[n7] = (byte)(n10 - this.fOffsets[n9] + 128);
                        n7 = n5;
                        continue;
                    }
                    n6 = UnicodeCompressor.findStaticWindow(n10);
                    if (n6 != -1 && !UnicodeCompressor.inStaticWindow(n7, n6)) {
                        if (n8 + 1 >= n4) {
                            n2 = n5 - 1;
                            n7 = n8;
                            n8 = n2;
                            break block0;
                        }
                        n7 = n8 + 1;
                        arrby[n8] = (byte)(n6 + 1);
                        n8 = n7 + 1;
                        arrby[n7] = (byte)(n10 - sOffsets[n6]);
                        n7 = n5;
                        continue;
                    }
                    n9 = UnicodeCompressor.makeIndex(n10);
                    arrn2 = this.fIndexCount;
                    arrn2[n9] = arrn2[n9] + 1;
                    n6 = n5 + 1 < n2 ? arrc[n5 + 1] : -1;
                    if (this.fIndexCount[n9] <= 1 && (n9 != UnicodeCompressor.makeIndex(n7) || n9 != UnicodeCompressor.makeIndex(n6))) {
                        if (n8 + 3 >= n4) {
                            n2 = -1 + n5;
                            n7 = n8;
                            n8 = n2;
                            break block0;
                        }
                        n7 = n8 + 1;
                        arrby[n8] = (byte)15;
                        n6 = n10 >>> 8;
                        if (sUnicodeTagTable[n6]) {
                            n8 = n7 + 1;
                            arrby[n7] = (byte)-16;
                        } else {
                            n8 = n7;
                        }
                        n7 = n8 + 1;
                        arrby[n8] = (byte)n6;
                        arrby[n7] = (byte)(n10 & 255);
                        this.fMode = 1;
                        n6 = n5;
                        n5 = n7 + 1;
                        continue block0;
                    }
                    if (n8 + 2 >= n4) {
                        n2 = n5 - 1;
                        n7 = n8;
                        n8 = n2;
                        break block0;
                    }
                    n7 = this.getLRDefinedWindow();
                    n6 = n8 + 1;
                    arrby[n8] = (byte)(n7 + 24);
                    n8 = n6 + 1;
                    arrby[n6] = (byte)n9;
                    arrby[n8] = (byte)(n10 - sOffsetTable[n9] + 128);
                    this.fOffsets[n7] = sOffsetTable[n9];
                    this.fCurrentWindow = n7;
                    arrn2 = this.fTimeStamps;
                    this.fTimeStamp = n6 = this.fTimeStamp + 1;
                    arrn2[n7] = n6;
                    ++n8;
                    n7 = n5;
                } while (true);
                break;
            } while (true);
            if (arrn != null) {
                arrn[0] = n8 - n;
            }
            return n7 - n3;
        }
        throw new IllegalArgumentException("byteBuffer.length < 4");
    }

    public void reset() {
        int n;
        int[] arrn = this.fOffsets;
        arrn[0] = 128;
        arrn[1] = 192;
        arrn[2] = 1024;
        arrn[3] = 1536;
        arrn[4] = 2304;
        arrn[5] = 12352;
        arrn[6] = 12448;
        arrn[7] = 65280;
        for (n = 0; n < 8; ++n) {
            this.fTimeStamps[n] = 0;
        }
        for (n = 0; n <= 255; ++n) {
            this.fIndexCount[n] = 0;
        }
        this.fTimeStamp = 0;
        this.fCurrentWindow = 0;
        this.fMode = 0;
    }
}

