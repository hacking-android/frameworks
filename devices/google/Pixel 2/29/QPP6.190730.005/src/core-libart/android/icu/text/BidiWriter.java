/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.UCharacter;
import android.icu.text.Bidi;
import android.icu.text.BidiRun;
import android.icu.text.UTF16;

final class BidiWriter {
    static final char LRM_CHAR = '\u200e';
    static final int MASK_R_AL = 8194;
    static final char RLM_CHAR = '\u200f';

    BidiWriter() {
    }

    private static boolean IsCombining(int n) {
        boolean bl = true;
        if ((1 << n & 448) == 0) {
            bl = false;
        }
        return bl;
    }

    private static String doWriteForward(String string, int n) {
        if ((n &= 10) != 0) {
            int n2;
            if (n != 2) {
                if (n != 8) {
                    int n3;
                    StringBuffer stringBuffer = new StringBuffer(string.length());
                    n = 0;
                    do {
                        int n4 = UTF16.charAt(string, n);
                        n3 = n + UTF16.getCharCount(n4);
                        if (!Bidi.IsBidiControlChar(n4)) {
                            UTF16.append(stringBuffer, UCharacter.getMirror(n4));
                        }
                        n = n3;
                    } while (n3 < string.length());
                    return stringBuffer.toString();
                }
                StringBuilder stringBuilder = new StringBuilder(string.length());
                n = 0;
                do {
                    int n5 = n + 1;
                    char c = string.charAt(n);
                    if (!Bidi.IsBidiControlChar(c)) {
                        stringBuilder.append(c);
                    }
                    if (n5 >= string.length()) {
                        return stringBuilder.toString();
                    }
                    n = n5;
                } while (true);
            }
            StringBuffer stringBuffer = new StringBuffer(string.length());
            n = 0;
            do {
                int n6 = UTF16.charAt(string, n);
                n2 = n + UTF16.getCharCount(n6);
                UTF16.append(stringBuffer, UCharacter.getMirror(n6));
                n = n2;
            } while (n2 < string.length());
            return stringBuffer.toString();
        }
        return string;
    }

    private static String doWriteForward(char[] arrc, int n, int n2, int n3) {
        return BidiWriter.doWriteForward(new String(arrc, n, n2 - n), n3);
    }

    static String doWriteReverse(char[] arrc, int n, int n2, int n3) {
        return BidiWriter.writeReverse(new String(arrc, n, n2 - n), n3);
    }

    static String writeReordered(Bidi bidi, int n) {
        char[] arrc = bidi.text;
        int n2 = bidi.countRuns();
        int n3 = n;
        if ((bidi.reorderingOptions & 1) != 0) {
            n3 = (n | 4) & -9;
        }
        n = n3;
        if ((bidi.reorderingOptions & 2) != 0) {
            n = (n3 | 8) & -5;
        }
        int n4 = n;
        if (bidi.reorderingMode != 4) {
            n4 = n;
            if (bidi.reorderingMode != 5) {
                n4 = n;
                if (bidi.reorderingMode != 6) {
                    n4 = n;
                    if (bidi.reorderingMode != 3) {
                        n4 = n & -5;
                    }
                }
            }
        }
        n = (n4 & 4) != 0 ? bidi.length * 2 : bidi.length;
        StringBuilder stringBuilder = new StringBuilder(n);
        if ((n4 & 16) == 0) {
            if ((n4 & 4) == 0) {
                for (n = 0; n < n2; ++n) {
                    BidiRun bidiRun = bidi.getVisualRun(n);
                    if (bidiRun.isEvenRun()) {
                        stringBuilder.append(BidiWriter.doWriteForward(arrc, bidiRun.start, bidiRun.limit, n4 & -3));
                        continue;
                    }
                    stringBuilder.append(BidiWriter.doWriteReverse(arrc, bidiRun.start, bidiRun.limit, n4));
                }
            } else {
                byte[] arrby = bidi.dirProps;
                for (int i = 0; i < n2; ++i) {
                    char c;
                    BidiRun bidiRun = bidi.getVisualRun(i);
                    n = n3 = bidi.runs[i].insertRemove;
                    if (n3 < 0) {
                        n = 0;
                    }
                    if (bidiRun.isEvenRun()) {
                        n3 = n;
                        if (bidi.isInverse()) {
                            n3 = n;
                            if (arrby[bidiRun.start] != 0) {
                                n3 = n | 1;
                            }
                        }
                        if ((n3 & 1) != 0) {
                            n = 8206;
                            c = n;
                        } else if ((n3 & 4) != 0) {
                            n = 8207;
                            c = n;
                        } else {
                            n = 0;
                            c = n;
                        }
                        if (c != '\u0000') {
                            stringBuilder.append(c);
                        }
                        stringBuilder.append(BidiWriter.doWriteForward(arrc, bidiRun.start, bidiRun.limit, n4 & -3));
                        n = n3;
                        if (bidi.isInverse()) {
                            n = n3;
                            if (arrby[bidiRun.limit - 1] != 0) {
                                n = n3 | 2;
                            }
                        }
                        if ((n & 2) != 0) {
                            n = 8206;
                            c = n;
                        } else if ((n & 8) != 0) {
                            n = 8207;
                            c = n;
                        } else {
                            n = 0;
                            c = n;
                        }
                        if (c == '\u0000') continue;
                        stringBuilder.append(c);
                        continue;
                    }
                    n3 = n;
                    if (bidi.isInverse()) {
                        n3 = n;
                        if (!bidi.testDirPropFlagAt(8194, bidiRun.limit - 1)) {
                            n3 = n | 4;
                        }
                    }
                    if ((n3 & 1) != 0) {
                        n = 8206;
                        c = n;
                    } else if ((n3 & 4) != 0) {
                        n = 8207;
                        c = n;
                    } else {
                        n = 0;
                        c = n;
                    }
                    if (c != '\u0000') {
                        stringBuilder.append(c);
                    }
                    stringBuilder.append(BidiWriter.doWriteReverse(arrc, bidiRun.start, bidiRun.limit, n4));
                    n = n3;
                    if (bidi.isInverse()) {
                        n = n3;
                        if ((Bidi.DirPropFlag(arrby[bidiRun.start]) & 8194) == 0) {
                            n = n3 | 8;
                        }
                    }
                    if ((n & 2) != 0) {
                        n = 8206;
                        c = n;
                    } else if ((n & 8) != 0) {
                        n = 8207;
                        c = n;
                    } else {
                        n = 0;
                        c = n;
                    }
                    if (c == '\u0000') continue;
                    stringBuilder.append(c);
                }
            }
        } else if ((n4 & 4) == 0) {
            n = n2;
            while (--n >= 0) {
                BidiRun bidiRun = bidi.getVisualRun(n);
                if (bidiRun.isEvenRun()) {
                    stringBuilder.append(BidiWriter.doWriteReverse(arrc, bidiRun.start, bidiRun.limit, n4 & -3));
                    continue;
                }
                stringBuilder.append(BidiWriter.doWriteForward(arrc, bidiRun.start, bidiRun.limit, n4));
            }
        } else {
            byte[] arrby = bidi.dirProps;
            n = n2;
            while (--n >= 0) {
                BidiRun bidiRun = bidi.getVisualRun(n);
                if (bidiRun.isEvenRun()) {
                    if (arrby[bidiRun.limit - 1] != 0) {
                        stringBuilder.append('\u200e');
                    }
                    stringBuilder.append(BidiWriter.doWriteReverse(arrc, bidiRun.start, bidiRun.limit, n4 & -3));
                    if (arrby[bidiRun.start] == 0) continue;
                    stringBuilder.append('\u200e');
                    continue;
                }
                if ((Bidi.DirPropFlag(arrby[bidiRun.start]) & 8194) == 0) {
                    stringBuilder.append('\u200f');
                }
                stringBuilder.append(BidiWriter.doWriteForward(arrc, bidiRun.start, bidiRun.limit, n4));
                if ((Bidi.DirPropFlag(arrby[bidiRun.limit - 1]) & 8194) != 0) continue;
                stringBuilder.append('\u200f');
            }
        }
        return stringBuilder.toString();
    }

    static String writeReverse(String string, int n) {
        StringBuffer stringBuffer = new StringBuffer(string.length());
        int n2 = n & 11;
        if (n2 != 0) {
            if (n2 != 1) {
                int n3 = string.length();
                do {
                    int n4 = n3;
                    int n5 = UTF16.charAt(string, n4 - 1);
                    n2 = n3 = n4 - UTF16.getCharCount(n5);
                    int n6 = n5;
                    if ((n & 1) != 0) {
                        do {
                            n2 = n3;
                            n6 = n5;
                            if (n3 <= 0) break;
                            n2 = n3;
                            n6 = n5;
                            if (!BidiWriter.IsCombining(UCharacter.getType(n5))) break;
                            n5 = UTF16.charAt(string, n3 - 1);
                            n3 -= UTF16.getCharCount(n5);
                        } while (true);
                    }
                    if ((n & 8) == 0 || !Bidi.IsBidiControlChar(n6)) {
                        n5 = n3 = n2;
                        if ((n & 2) != 0) {
                            n5 = UCharacter.getMirror(n6);
                            UTF16.append(stringBuffer, n5);
                            n5 = n3 + UTF16.getCharCount(n5);
                        }
                        stringBuffer.append(string.substring(n5, n4));
                    }
                    n3 = n2;
                } while (n2 > 0);
            } else {
                n = string.length();
                do {
                    int n7;
                    int n8;
                    n = n8 = n;
                    while ((n2 = n - UTF16.getCharCount(n7 = UTF16.charAt(string, n - 1))) > 0) {
                        n = n2;
                        if (BidiWriter.IsCombining(UCharacter.getType(n7))) continue;
                    }
                    stringBuffer.append(string.substring(n2, n8));
                    n = n2;
                } while (n2 > 0);
            }
        } else {
            n = string.length();
            do {
                n2 = n;
                n = n2 - UTF16.getCharCount(UTF16.charAt(string, n2 - 1));
                stringBuffer.append(string.substring(n, n2));
            } while (n > 0);
        }
        return stringBuffer.toString();
    }
}

