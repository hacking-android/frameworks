/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.text.UnicodeSet;
import android.icu.util.OutputInt;

public final class BMPSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static int U16_SURROGATE_OFFSET = 56613888;
    private int[] bmpBlockBits;
    private boolean[] latin1Contains;
    private final int[] list;
    private int[] list4kStarts;
    private final int listLength;
    private int[] table7FF;

    public BMPSet(BMPSet bMPSet, int[] arrn, int n) {
        this.list = arrn;
        this.listLength = n;
        this.latin1Contains = (boolean[])bMPSet.latin1Contains.clone();
        this.table7FF = (int[])bMPSet.table7FF.clone();
        this.bmpBlockBits = (int[])bMPSet.bmpBlockBits.clone();
        this.list4kStarts = (int[])bMPSet.list4kStarts.clone();
    }

    public BMPSet(int[] arrn, int n) {
        this.list = arrn;
        this.listLength = n;
        this.latin1Contains = new boolean[256];
        this.table7FF = new int[64];
        this.bmpBlockBits = new int[64];
        this.list4kStarts = new int[18];
        this.list4kStarts[0] = this.findCodePoint(2048, 0, this.listLength - 1);
        for (n = 1; n <= 16; ++n) {
            arrn = this.list4kStarts;
            arrn[n] = this.findCodePoint(n << 12, arrn[n - 1], this.listLength - 1);
        }
        this.list4kStarts[17] = this.listLength - 1;
        this.initBits();
    }

    private final boolean containsSlow(int n, int n2, int n3) {
        n = this.findCodePoint(n, n2, n3);
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    private int findCodePoint(int n, int n2, int n3) {
        int[] arrn = this.list;
        if (n < arrn[n2]) {
            return n2;
        }
        if (n2 < n3) {
            int n4 = n3;
            if (n < arrn[n3 - 1]) {
                do {
                    if ((n3 = n2 + n4 >>> 1) == n2) {
                        return n4;
                    }
                    if (n < this.list[n3]) {
                        n4 = n3;
                        continue;
                    }
                    n2 = n3;
                } while (true);
            }
        }
        return n3;
    }

    private void initBits() {
        int n = 0;
        do {
            int n2;
            block20 : {
                int n3;
                int n4;
                Object[] arrobject;
                int n5;
                block19 : {
                    arrobject = this.list;
                    n2 = n + 1;
                    n3 = arrobject[n];
                    if (n2 < this.listLength) {
                        n = arrobject[n2];
                        ++n2;
                    } else {
                        n = 1114112;
                    }
                    n5 = n3;
                    if (n3 >= 256) break block19;
                    do {
                        arrobject = this.latin1Contains;
                        n3 = n5 + 1;
                        arrobject[n5] = 1;
                        if (n3 >= n || n3 >= 256) break;
                        n5 = n3;
                    } while (true);
                    if (n <= 256) break block20;
                }
                do {
                    n4 = n3;
                    if (n3 >= 2048) break;
                    arrobject = this.table7FF;
                    n5 = n <= 2048 ? n : 2048;
                    BMPSet.set32x64Bits(arrobject, n3, n5);
                    if (n > 2048) {
                        n4 = 2048;
                        break;
                    }
                    arrobject = this.list;
                    n5 = n2 + 1;
                    n3 = arrobject[n2];
                    if (n5 < this.listLength) {
                        n2 = n5 + 1;
                        n = arrobject[n5];
                        continue;
                    }
                    n = 1114112;
                    n2 = n5;
                } while (true);
                n3 = 2048;
                while (n4 < 65536) {
                    int n6 = n;
                    if (n > 65536) {
                        n6 = 65536;
                    }
                    n = n4;
                    if (n4 < n3) {
                        n = n3;
                    }
                    n4 = n6;
                    n5 = n3;
                    if (n < n6) {
                        int n7 = n;
                        if ((n & 63) != 0) {
                            n3 = n >> 6;
                            arrobject = this.bmpBlockBits;
                            n = n3 & 63;
                            arrobject[n] = arrobject[n] | 65537 << (n3 >> 6);
                            n3 = n7 = n3 + 1 << 6;
                        }
                        n4 = n6;
                        n5 = n3;
                        if (n7 < n6) {
                            if (n7 < (n6 & -64)) {
                                BMPSet.set32x64Bits(this.bmpBlockBits, n7 >> 6, n6 >> 6);
                            }
                            n4 = n6;
                            n5 = n3;
                            if ((n6 & 63) != 0) {
                                n3 = n6 >> 6;
                                arrobject = this.bmpBlockBits;
                                n = n3 & 63;
                                arrobject[n] = 65537 << (n3 >> 6) | arrobject[n];
                                n5 = n4 = n3 + 1 << 6;
                            }
                        }
                    }
                    if (n4 == 65536) break;
                    arrobject = this.list;
                    n3 = n2 + 1;
                    n4 = arrobject[n2];
                    if (n3 < this.listLength) {
                        n2 = n3 + 1;
                        n = arrobject[n3];
                        n3 = n5;
                        continue;
                    }
                    n = 1114112;
                    n2 = n3;
                    n3 = n5;
                }
                return;
            }
            n = n2;
        } while (true);
    }

    private static void set32x64Bits(int[] arrn, int n, int n2) {
        int n3;
        int n4 = n >> 6;
        int n5 = 1 << n4;
        if (n + 1 == n2) {
            arrn[n3] = arrn[n3] | n5;
            return;
        }
        int n6 = n2 >> 6;
        int n7 = n2 & 63;
        if (n4 == n6) {
            for (n3 = n & 63; n3 < n7; ++n3) {
                arrn[n3] = arrn[n3] | n5;
            }
        } else {
            n = n4;
            if (n3 > 0) {
                do {
                    n = n3 + 1;
                    arrn[n3] = arrn[n3] | n5;
                    if (n >= 64) {
                        n = n4 + 1;
                        break;
                    }
                    n3 = n;
                } while (true);
            }
            if (n < n6) {
                n = n2 = (1 << n) - 1;
                if (n6 < 32) {
                    n = n2 & (1 << n6) - 1;
                }
                for (n2 = 0; n2 < 64; ++n2) {
                    arrn[n2] = arrn[n2] | n;
                }
            }
            for (n = 0; n < n7; ++n) {
                arrn[n] = arrn[n] | 1 << n6;
            }
        }
    }

    public boolean contains(int n) {
        if (n <= 255) {
            return this.latin1Contains[n];
        }
        boolean bl = false;
        boolean bl2 = false;
        if (n <= 2047) {
            if ((this.table7FF[n & 63] & 1 << (n >> 6)) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        if (n >= 55296 && (n < 57344 || n > 65535)) {
            if (n <= 1114111) {
                int[] arrn = this.list4kStarts;
                return this.containsSlow(n, arrn[13], arrn[17]);
            }
            return false;
        }
        int n2 = n >> 12;
        int n3 = this.bmpBlockBits[n >> 6 & 63] >> n2 & 65537;
        if (n3 <= 1) {
            bl2 = bl;
            if (n3 != 0) {
                bl2 = true;
            }
            return bl2;
        }
        int[] arrn = this.list4kStarts;
        return this.containsSlow(n, arrn[n2], arrn[n2 + 1]);
    }

    public final int span(CharSequence charSequence, int n, UnicodeSet.SpanCondition arrn, OutputInt outputInt) {
        int n2;
        int n3;
        block22 : {
            n3 = n;
            int n4 = charSequence.length();
            int n5 = 0;
            n2 = 0;
            int n6 = n3;
            if (UnicodeSet.SpanCondition.NOT_CONTAINED != arrn) {
                n5 = n2;
                n6 = n3;
                do {
                    char c;
                    n2 = n6;
                    n3 = n5;
                    if (n6 >= n4) break block22;
                    char c2 = charSequence.charAt(n6);
                    if (c2 <= '\u00ff') {
                        n2 = n6;
                        n3 = n5;
                        if (!this.latin1Contains[c2]) {
                            n2 = n6;
                            n3 = n5;
                            break block22;
                        }
                    } else if (c2 <= '\u07ff') {
                        n2 = n6;
                        n3 = n5;
                        if ((this.table7FF[c2 & 63] & 1 << (c2 >> 6)) == 0) {
                            n2 = n6;
                            n3 = n5;
                            break block22;
                        }
                    } else if (c2 >= '\ud800' && c2 < '\udc00' && n6 + 1 != n4 && (c = charSequence.charAt(n6 + 1)) >= '\udc00' && c < '\ue000') {
                        n3 = Character.toCodePoint(c2, c);
                        if (!this.containsSlow(n3, (arrn = this.list4kStarts)[16], arrn[17])) {
                            n2 = n6;
                            n3 = n5;
                            break block22;
                        }
                        n3 = n5 + 1;
                        n2 = n6 + 1;
                    } else {
                        n3 = c2 >> 12;
                        n2 = this.bmpBlockBits[c2 >> 6 & 63] >> n3 & 65537;
                        if (n2 <= 1) {
                            if (n2 == 0) {
                                n2 = n6;
                                n3 = n5;
                                break block22;
                            }
                        } else {
                            arrn = this.list4kStarts;
                            if (!this.containsSlow(c2, arrn[n3], arrn[n3 + 1])) {
                                n2 = n6;
                                n3 = n5;
                                break block22;
                            }
                        }
                        n3 = n5;
                        n2 = n6;
                    }
                    n6 = n2 + 1;
                    n5 = n3;
                } while (true);
            }
            do {
                char c;
                n2 = ++n6;
                n3 = n5;
                if (n6 >= n4) break;
                char c3 = charSequence.charAt(n6);
                if (c3 <= '\u00ff') {
                    if (!this.latin1Contains[c3]) continue;
                    n2 = n6;
                    n3 = n5;
                    break;
                }
                if (c3 <= '\u07ff') {
                    if ((this.table7FF[c3 & 63] & 1 << (c3 >> 6)) == 0) continue;
                    n2 = n6;
                    n3 = n5;
                    break;
                }
                if (c3 >= '\ud800' && c3 < '\udc00' && n6 + 1 != n4 && (c = charSequence.charAt(n6 + 1)) >= '\udc00' && c < '\ue000') {
                    n3 = Character.toCodePoint(c3, c);
                    if (this.containsSlow(n3, (arrn = this.list4kStarts)[16], arrn[17])) {
                        n2 = n6;
                        n3 = n5;
                        break;
                    }
                    ++n5;
                    ++n6;
                    continue;
                }
                n3 = c3 >> 12;
                n2 = this.bmpBlockBits[c3 >> 6 & 63] >> n3 & 65537;
                if (n2 <= 1) {
                    if (n2 == 0) continue;
                    n2 = n6;
                    n3 = n5;
                    break;
                }
                arrn = this.list4kStarts;
                if (!this.containsSlow(c3, arrn[n3], arrn[n3 + 1])) continue;
                n2 = n6;
                n3 = n5;
                break;
            } while (true);
        }
        if (outputInt != null) {
            outputInt.value = n2 - n - n3;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int spanBack(CharSequence charSequence, int n, UnicodeSet.SpanCondition arrn) {
        if (UnicodeSet.SpanCondition.NOT_CONTAINED != arrn) {
            do {
                char c;
                int n2;
                char c2;
                if ((c2 = charSequence.charAt(n2 = n - 1)) <= '\u00ff') {
                    n = n2;
                    if (this.latin1Contains[c2]) continue;
                    n = n2;
                    return n + 1;
                }
                if (c2 <= '\u07ff') {
                    n = n2;
                    if ((this.table7FF[c2 & 63] & 1 << (c2 >> 6)) != 0) continue;
                    n = n2;
                    return n + 1;
                }
                if (c2 >= '\ud800' && c2 >= '\udc00' && n2 != 0 && (c = charSequence.charAt(n2 - 1)) >= '\ud800' && c < '\udc00') {
                    n = Character.toCodePoint(c, c2);
                    if (!this.containsSlow(n, (arrn = this.list4kStarts)[16], arrn[17])) {
                        n = n2;
                        return n + 1;
                    }
                    n = n2 - 1;
                    continue;
                }
                n = c2 >> 12;
                int n3 = this.bmpBlockBits[c2 >> 6 & 63] >> n & 65537;
                if (n3 <= 1) {
                    if (n3 == 0) {
                        n = n2;
                        return n + 1;
                    }
                } else {
                    arrn = this.list4kStarts;
                    if (!this.containsSlow(c2, arrn[n], arrn[n + 1])) {
                        n = n2;
                        return n + 1;
                    }
                }
                n = n2;
            } while (n != 0);
            return 0;
        }
        do {
            char c;
            if ((c = charSequence.charAt(--n)) <= '\u00ff') {
                if (!this.latin1Contains[c]) continue;
                return n + 1;
            } else if (c <= '\u07ff') {
                if ((this.table7FF[c & 63] & 1 << (c >> 6)) == 0) continue;
                return n + 1;
            } else {
                char c3;
                int n4;
                if (c >= '\ud800' && c >= '\udc00' && n != 0 && (c3 = charSequence.charAt(n - 1)) >= '\ud800' && c3 < '\udc00') {
                    n4 = Character.toCodePoint(c3, c);
                    if (this.containsSlow(n4, (arrn = this.list4kStarts)[16], arrn[17])) return n + 1;
                    --n;
                    continue;
                }
                int n5 = c >> 12;
                n4 = this.bmpBlockBits[c >> 6 & 63] >> n5 & 65537;
                if (!(n4 <= 1 ? n4 != 0 : this.containsSlow(c, (arrn = this.list4kStarts)[n5], arrn[n5 + 1]))) continue;
            }
            return n + 1;
        } while (n != 0);
        return 0;
    }
}

