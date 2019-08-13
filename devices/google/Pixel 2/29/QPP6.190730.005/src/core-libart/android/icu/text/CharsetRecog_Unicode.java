/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.CharsetDetector;
import android.icu.text.CharsetMatch;
import android.icu.text.CharsetRecognizer;

abstract class CharsetRecog_Unicode
extends CharsetRecognizer {
    CharsetRecog_Unicode() {
    }

    static int adjustConfidence(int n, int n2) {
        int n3;
        block7 : {
            block8 : {
                block6 : {
                    if (n != 0) break block6;
                    n3 = n2 - 10;
                    break block7;
                }
                if (n >= 32 && n <= 255) break block8;
                n3 = n2;
                if (n != 10) break block7;
            }
            n3 = n2 + 10;
        }
        if (n3 < 0) {
            n = 0;
        } else {
            n = n3;
            if (n3 > 100) {
                n = 100;
            }
        }
        return n;
    }

    static int codeUnit16FromBytes(byte by, byte by2) {
        return (by & 255) << 8 | by2 & 255;
    }

    @Override
    abstract String getName();

    @Override
    abstract CharsetMatch match(CharsetDetector var1);

    static class CharsetRecog_UTF_16_BE
    extends CharsetRecog_Unicode {
        CharsetRecog_UTF_16_BE() {
        }

        @Override
        String getName() {
            return "UTF-16BE";
        }

        @Override
        CharsetMatch match(CharsetDetector charsetDetector) {
            int n;
            byte[] arrby = charsetDetector.fRawInput;
            int n2 = 10;
            int n3 = Math.min(arrby.length, 30);
            int n4 = 0;
            do {
                n = n2;
                if (n4 >= n3 - 1) break;
                n = CharsetRecog_UTF_16_BE.codeUnit16FromBytes(arrby[n4], arrby[n4 + 1]);
                if (n4 == 0 && n == 65279) {
                    n = 100;
                    break;
                }
                n = n2 = CharsetRecog_UTF_16_BE.adjustConfidence(n, n2);
                if (n2 == 0) break;
                if (n2 == 100) {
                    n = n2;
                    break;
                }
                n4 += 2;
            } while (true);
            n2 = n;
            if (n3 < 4) {
                n2 = n;
                if (n < 100) {
                    n2 = 0;
                }
            }
            if (n2 > 0) {
                return new CharsetMatch(charsetDetector, this, n2);
            }
            return null;
        }
    }

    static class CharsetRecog_UTF_16_LE
    extends CharsetRecog_Unicode {
        CharsetRecog_UTF_16_LE() {
        }

        @Override
        String getName() {
            return "UTF-16LE";
        }

        @Override
        CharsetMatch match(CharsetDetector charsetDetector) {
            int n;
            byte[] arrby = charsetDetector.fRawInput;
            int n2 = 10;
            int n3 = Math.min(arrby.length, 30);
            int n4 = 0;
            do {
                n = n2;
                if (n4 >= n3 - 1) break;
                n = CharsetRecog_UTF_16_LE.codeUnit16FromBytes(arrby[n4 + 1], arrby[n4]);
                if (n4 == 0 && n == 65279) {
                    n = 100;
                    break;
                }
                n = n2 = CharsetRecog_UTF_16_LE.adjustConfidence(n, n2);
                if (n2 == 0) break;
                if (n2 == 100) {
                    n = n2;
                    break;
                }
                n4 += 2;
            } while (true);
            n2 = n;
            if (n3 < 4) {
                n2 = n;
                if (n < 100) {
                    n2 = 0;
                }
            }
            if (n2 > 0) {
                return new CharsetMatch(charsetDetector, this, n2);
            }
            return null;
        }
    }

    static abstract class CharsetRecog_UTF_32
    extends CharsetRecog_Unicode {
        CharsetRecog_UTF_32() {
        }

        abstract int getChar(byte[] var1, int var2);

        @Override
        abstract String getName();

        @Override
        CharsetMatch match(CharsetDetector object) {
            byte[] arrby = ((CharsetDetector)object).fRawInput;
            int n = ((CharsetDetector)object).fRawLength / 4 * 4;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            Object var8_8 = null;
            if (n == 0) {
                return null;
            }
            if (this.getChar(arrby, 0) == 65279) {
                n4 = 1;
            }
            for (int i = 0; i < n; i += 4) {
                int n6 = this.getChar(arrby, i);
                if (n6 >= 0 && n6 < 1114111 && (n6 < 55296 || n6 > 57343)) {
                    ++n2;
                    continue;
                }
                ++n3;
            }
            if (n4 != 0 && n3 == 0) {
                n4 = 100;
            } else if (n4 != 0 && n2 > n3 * 10) {
                n4 = 80;
            } else if (n2 > 3 && n3 == 0) {
                n4 = 100;
            } else if (n2 > 0 && n3 == 0) {
                n4 = 80;
            } else {
                n4 = n5;
                if (n2 > n3 * 10) {
                    n4 = 25;
                }
            }
            object = n4 == 0 ? var8_8 : new CharsetMatch((CharsetDetector)object, this, n4);
            return object;
        }
    }

    static class CharsetRecog_UTF_32_BE
    extends CharsetRecog_UTF_32 {
        CharsetRecog_UTF_32_BE() {
        }

        @Override
        int getChar(byte[] arrby, int n) {
            return (arrby[n + 0] & 255) << 24 | (arrby[n + 1] & 255) << 16 | (arrby[n + 2] & 255) << 8 | arrby[n + 3] & 255;
        }

        @Override
        String getName() {
            return "UTF-32BE";
        }
    }

    static class CharsetRecog_UTF_32_LE
    extends CharsetRecog_UTF_32 {
        CharsetRecog_UTF_32_LE() {
        }

        @Override
        int getChar(byte[] arrby, int n) {
            return (arrby[n + 3] & 255) << 24 | (arrby[n + 2] & 255) << 16 | (arrby[n + 1] & 255) << 8 | arrby[n + 0] & 255;
        }

        @Override
        String getName() {
            return "UTF-32LE";
        }
    }

}

