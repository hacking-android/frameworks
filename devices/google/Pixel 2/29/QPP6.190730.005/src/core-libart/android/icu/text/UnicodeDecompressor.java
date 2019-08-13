/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.SCSU;

public final class UnicodeDecompressor
implements SCSU {
    private static final int BUFSIZE = 3;
    private byte[] fBuffer = new byte[3];
    private int fBufferLength = 0;
    private int fCurrentWindow = 0;
    private int fMode = 0;
    private int[] fOffsets = new int[8];

    public UnicodeDecompressor() {
        this.reset();
    }

    public static String decompress(byte[] arrby) {
        return new String(UnicodeDecompressor.decompress(arrby, 0, arrby.length));
    }

    public static char[] decompress(byte[] arrobject, int n, int n2) {
        UnicodeDecompressor unicodeDecompressor = new UnicodeDecompressor();
        int n3 = Math.max(2, (n2 - n) * 2);
        char[] arrc = new char[n3];
        n = unicodeDecompressor.decompress((byte[])arrobject, n, n2, null, arrc, 0, n3);
        arrobject = new char[n];
        System.arraycopy(arrc, 0, arrobject, 0, n);
        return arrobject;
    }

    public int decompress(byte[] arrby, int n, int n2, int[] arrn, char[] arrc, int n3, int n4) {
        block37 : {
            int n5;
            block36 : {
                byte[] arrby2;
                int n6 = n;
                n5 = n3;
                int n7 = 0;
                if (arrc.length < 2 || n4 - n3 < 2) break block37;
                int n8 = this.fBufferLength;
                if (n8 > 0) {
                    if (n8 != 3) {
                        if (n2 - n < (n8 = this.fBuffer.length - n8)) {
                            n8 = n2 - n;
                        }
                        System.arraycopy((byte[])arrby, (int)n, (byte[])this.fBuffer, (int)this.fBufferLength, (int)n8);
                    } else {
                        n8 = 0;
                    }
                    this.fBufferLength = 0;
                    arrby2 = this.fBuffer;
                    n5 += this.decompress(arrby2, 0, arrby2.length, null, arrc, n3, n4);
                    n8 = n6 + n8;
                } else {
                    n8 = n6;
                }
                block16 : while (n8 < n2 && n5 < n4) {
                    int n9 = this.fMode;
                    int n10 = n8;
                    n6 = n5;
                    int n11 = n7;
                    if (n9 != 0) {
                        n10 = n8;
                        n6 = n5;
                        n11 = n7;
                        if (n9 != 1) continue;
                        block17 : do {
                            n8 = n10;
                            n5 = n6;
                            n7 = n11;
                            if (n10 >= n2) continue block16;
                            n8 = n10;
                            n5 = n6;
                            n7 = n11;
                            if (n6 >= n4) continue block16;
                            n5 = n10 + 1;
                            n8 = arrby[n10] & 255;
                            switch (n8) {
                                default: {
                                    if (n5 < n2) break;
                                    n4 = n5 - 1;
                                    System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                    this.fBufferLength = n2 - n4;
                                    n2 = n4 + this.fBufferLength;
                                    n5 = n6;
                                    break block36;
                                }
                                case 241: {
                                    if (n5 + 1 >= n2) {
                                        n4 = n5 - 1;
                                        System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                        this.fBufferLength = n2 - n4;
                                        n2 = n4 + this.fBufferLength;
                                        n5 = n6;
                                        break block36;
                                    }
                                    n8 = n5 + 1;
                                    n7 = arrby[n5] & 255;
                                    this.fCurrentWindow = (n7 & 224) >> 5;
                                    this.fOffsets[this.fCurrentWindow] = (arrby[n8] & 255 | (n7 & 31) << 8) * 128 + 65536;
                                    this.fMode = 0;
                                    ++n8;
                                    n5 = n6;
                                    continue block16;
                                }
                                case 240: {
                                    if (n5 >= n2 - 1) {
                                        n4 = n5 - 1;
                                        System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                        this.fBufferLength = n2 - n4;
                                        n2 = n4 + this.fBufferLength;
                                        n5 = n6;
                                        break block36;
                                    }
                                    n8 = n5 + 1;
                                    n11 = arrby[n5];
                                    n10 = n8 + 1;
                                    arrc[n6] = (char)(n11 << 8 | arrby[n8] & 255);
                                    ++n6;
                                    continue block17;
                                }
                                case 232: 
                                case 233: 
                                case 234: 
                                case 235: 
                                case 236: 
                                case 237: 
                                case 238: 
                                case 239: {
                                    if (n5 >= n2) {
                                        n4 = n5 - 1;
                                        System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                        this.fBufferLength = n2 - n4;
                                        n2 = n4 + this.fBufferLength;
                                        n5 = n6;
                                        break block36;
                                    }
                                    this.fCurrentWindow = n8 - 232;
                                    this.fOffsets[this.fCurrentWindow] = sOffsetTable[arrby[n5] & 255];
                                    this.fMode = 0;
                                    n7 = n8;
                                    n8 = n5 + 1;
                                    n5 = n6;
                                    continue block16;
                                }
                                case 224: 
                                case 225: 
                                case 226: 
                                case 227: 
                                case 228: 
                                case 229: 
                                case 230: 
                                case 231: {
                                    this.fCurrentWindow = n8 - 224;
                                    this.fMode = 0;
                                    n7 = n8;
                                    n8 = n5;
                                    n5 = n6;
                                    continue block16;
                                }
                            }
                            n10 = n5 + 1;
                            arrc[n6] = (char)(arrby[n5] & 255 | n8 << 8);
                            ++n6;
                            n11 = n8;
                        } while (true);
                    }
                    do {
                        block38 : {
                            n8 = n10;
                            n5 = n6;
                            n7 = n11;
                            if (n10 >= n2) continue block16;
                            n8 = n10;
                            n5 = n6;
                            n7 = n11;
                            if (n6 >= n4) continue block16;
                            n7 = n10 + 1;
                            n8 = arrby[n10] & 255;
                            switch (n8) {
                                default: {
                                    break;
                                }
                                case 128: 
                                case 129: 
                                case 130: 
                                case 131: 
                                case 132: 
                                case 133: 
                                case 134: 
                                case 135: 
                                case 136: 
                                case 137: 
                                case 138: 
                                case 139: 
                                case 140: 
                                case 141: 
                                case 142: 
                                case 143: 
                                case 144: 
                                case 145: 
                                case 146: 
                                case 147: 
                                case 148: 
                                case 149: 
                                case 150: 
                                case 151: 
                                case 152: 
                                case 153: 
                                case 154: 
                                case 155: 
                                case 156: 
                                case 157: 
                                case 158: 
                                case 159: 
                                case 160: 
                                case 161: 
                                case 162: 
                                case 163: 
                                case 164: 
                                case 165: 
                                case 166: 
                                case 167: 
                                case 168: 
                                case 169: 
                                case 170: 
                                case 171: 
                                case 172: 
                                case 173: 
                                case 174: 
                                case 175: 
                                case 176: 
                                case 177: 
                                case 178: 
                                case 179: 
                                case 180: 
                                case 181: 
                                case 182: 
                                case 183: 
                                case 184: 
                                case 185: 
                                case 186: 
                                case 187: 
                                case 188: 
                                case 189: 
                                case 190: 
                                case 191: 
                                case 192: 
                                case 193: 
                                case 194: 
                                case 195: 
                                case 196: 
                                case 197: 
                                case 198: 
                                case 199: 
                                case 200: 
                                case 201: 
                                case 202: 
                                case 203: 
                                case 204: 
                                case 205: 
                                case 206: 
                                case 207: 
                                case 208: 
                                case 209: 
                                case 210: 
                                case 211: 
                                case 212: 
                                case 213: 
                                case 214: 
                                case 215: 
                                case 216: 
                                case 217: 
                                case 218: 
                                case 219: 
                                case 220: 
                                case 221: 
                                case 222: 
                                case 223: 
                                case 224: 
                                case 225: 
                                case 226: 
                                case 227: 
                                case 228: 
                                case 229: 
                                case 230: 
                                case 231: 
                                case 232: 
                                case 233: 
                                case 234: 
                                case 235: 
                                case 236: 
                                case 237: 
                                case 238: 
                                case 239: 
                                case 240: 
                                case 241: 
                                case 242: 
                                case 243: 
                                case 244: 
                                case 245: 
                                case 246: 
                                case 247: 
                                case 248: 
                                case 249: 
                                case 250: 
                                case 251: 
                                case 252: 
                                case 253: 
                                case 254: 
                                case 255: {
                                    arrby2 = this.fOffsets;
                                    n5 = this.fCurrentWindow;
                                    if (arrby2[n5] <= 65535) {
                                        arrc[n6] = (char)(arrby2[n5] + n8 - 128);
                                        n5 = n7;
                                        n7 = n8;
                                        n8 = n5;
                                        n5 = ++n6;
                                    } else {
                                        if (n6 + 1 >= n4) {
                                            n4 = n7 - 1;
                                            System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                            this.fBufferLength = n2 - n4;
                                            n2 = n4 + this.fBufferLength;
                                            n5 = n6;
                                            break block36;
                                        }
                                        n5 = arrby2[n5] - 65536;
                                        n10 = n6 + 1;
                                        arrc[n6] = (char)((n5 >> 10) + 55296);
                                        arrc[n10] = (char)((n5 & 1023) + 56320 + (n8 & 127));
                                        n5 = n7;
                                        n7 = n8;
                                        n6 = n10 + 1;
                                        n8 = n5;
                                        n5 = n6;
                                    }
                                    break block38;
                                }
                                case 24: 
                                case 25: 
                                case 26: 
                                case 27: 
                                case 28: 
                                case 29: 
                                case 30: 
                                case 31: {
                                    if (n7 >= n2) {
                                        n4 = n7 - 1;
                                        System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                        this.fBufferLength = n2 - n4;
                                        n2 = n4 + this.fBufferLength;
                                        n5 = n6;
                                        break block36;
                                    }
                                    this.fCurrentWindow = n8 - 24;
                                    arrby2 = this.fOffsets;
                                    n10 = this.fCurrentWindow;
                                    int[] arrn2 = sOffsetTable;
                                    n5 = n7 + 1;
                                    arrby2[n10] = arrn2[arrby[n7] & 255];
                                    n7 = n8;
                                    n8 = n5;
                                    n5 = n6;
                                    break block38;
                                }
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                                case 20: 
                                case 21: 
                                case 22: 
                                case 23: {
                                    this.fCurrentWindow = n8 - 16;
                                    break;
                                }
                                case 15: {
                                    this.fMode = 1;
                                    n5 = n7;
                                    n7 = n8;
                                    n8 = n5;
                                    n5 = n6;
                                    continue block16;
                                }
                                case 14: {
                                    if (n7 + 1 >= n2) {
                                        n4 = n7 - 1;
                                        System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                        this.fBufferLength = n2 - n4;
                                        n2 = n4 + this.fBufferLength;
                                        n5 = n6;
                                        break block36;
                                    }
                                    n8 = n7 + 1;
                                    n7 = arrby[n7];
                                    arrc[n6] = (char)(arrby[n8] & 255 | n7 << 8);
                                    n5 = n6 + 1;
                                    break block38;
                                }
                                case 11: {
                                    if (n7 + 1 >= n2) {
                                        n4 = n7 - 1;
                                        System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                        this.fBufferLength = n2 - n4;
                                        n2 = n4 + this.fBufferLength;
                                        n5 = n6;
                                        break block36;
                                    }
                                    n5 = n7 + 1;
                                    n7 = arrby[n7] & 255;
                                    this.fCurrentWindow = (n7 & 224) >> 5;
                                    arrby2 = this.fOffsets;
                                    n10 = this.fCurrentWindow;
                                    n8 = n5 + 1;
                                    arrby2[n10] = (arrby[n5] & 255 | (n7 & 31) << 8) * 128 + 65536;
                                    n5 = n6;
                                    break block38;
                                }
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: {
                                    if (n7 >= n2) {
                                        n4 = n7 - 1;
                                        System.arraycopy((byte[])arrby, (int)n4, (byte[])this.fBuffer, (int)0, (int)(n2 - n4));
                                        this.fBufferLength = n2 - n4;
                                        n2 = n4 + this.fBufferLength;
                                        n5 = n6;
                                        break block36;
                                    }
                                    n10 = arrby[n7] & 255;
                                    n5 = n10 >= 0 && n10 < 128 ? sOffsets[n8 - 1] : this.fOffsets[n8 - 1] - 128;
                                    arrc[n6] = (char)(n5 + n10);
                                    n10 = n8;
                                    n8 = n7 + 1;
                                    n5 = n6 + 1;
                                    n7 = n10;
                                    break block38;
                                }
                                case 0: 
                                case 9: 
                                case 10: 
                                case 13: 
                                case 32: 
                                case 33: 
                                case 34: 
                                case 35: 
                                case 36: 
                                case 37: 
                                case 38: 
                                case 39: 
                                case 40: 
                                case 41: 
                                case 42: 
                                case 43: 
                                case 44: 
                                case 45: 
                                case 46: 
                                case 47: 
                                case 48: 
                                case 49: 
                                case 50: 
                                case 51: 
                                case 52: 
                                case 53: 
                                case 54: 
                                case 55: 
                                case 56: 
                                case 57: 
                                case 58: 
                                case 59: 
                                case 60: 
                                case 61: 
                                case 62: 
                                case 63: 
                                case 64: 
                                case 65: 
                                case 66: 
                                case 67: 
                                case 68: 
                                case 69: 
                                case 70: 
                                case 71: 
                                case 72: 
                                case 73: 
                                case 74: 
                                case 75: 
                                case 76: 
                                case 77: 
                                case 78: 
                                case 79: 
                                case 80: 
                                case 81: 
                                case 82: 
                                case 83: 
                                case 84: 
                                case 85: 
                                case 86: 
                                case 87: 
                                case 88: 
                                case 89: 
                                case 90: 
                                case 91: 
                                case 92: 
                                case 93: 
                                case 94: 
                                case 95: 
                                case 96: 
                                case 97: 
                                case 98: 
                                case 99: 
                                case 100: 
                                case 101: 
                                case 102: 
                                case 103: 
                                case 104: 
                                case 105: 
                                case 106: 
                                case 107: 
                                case 108: 
                                case 109: 
                                case 110: 
                                case 111: 
                                case 112: 
                                case 113: 
                                case 114: 
                                case 115: 
                                case 116: 
                                case 117: 
                                case 118: 
                                case 119: 
                                case 120: 
                                case 121: 
                                case 122: 
                                case 123: 
                                case 124: 
                                case 125: 
                                case 126: 
                                case 127: {
                                    arrc[n6] = (char)n8;
                                    n5 = n7;
                                    n7 = n8;
                                    n8 = n5;
                                    n5 = ++n6;
                                    break block38;
                                }
                            }
                            n10 = n7;
                            n7 = n8;
                            n5 = n6;
                            n8 = n10;
                        }
                        n10 = ++n8;
                        n6 = n5;
                        n11 = n7;
                    } while (true);
                }
                n2 = n8;
            }
            if (arrn != null) {
                arrn[0] = n2 - n;
            }
            return n5 - n3;
        }
        throw new IllegalArgumentException("charBuffer.length < 2");
    }

    public void reset() {
        int[] arrn = this.fOffsets;
        arrn[0] = 128;
        arrn[1] = 192;
        arrn[2] = 1024;
        arrn[3] = 1536;
        arrn[4] = 2304;
        arrn[5] = 12352;
        arrn[6] = 12448;
        arrn[7] = 65280;
        this.fCurrentWindow = 0;
        this.fMode = 0;
        this.fBufferLength = 0;
    }
}

