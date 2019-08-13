/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.CharsetDetector;
import android.icu.text.CharsetMatch;
import android.icu.text.CharsetRecognizer;

abstract class CharsetRecog_2022
extends CharsetRecognizer {
    CharsetRecog_2022() {
    }

    int match(byte[] arrby, int n, byte[][] arrby2) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n5 < n) {
            int n6;
            int n7;
            int n8;
            block11 : {
                int n9;
                block12 : {
                    n9 = n3;
                    if (arrby[n5] == 27) {
                        block1 : for (n9 = 0; n9 < arrby2.length; ++n9) {
                            byte[] arrby3 = arrby2[n9];
                            if (n - n5 < arrby3.length) continue;
                            for (n7 = 1; n7 < arrby3.length; ++n7) {
                                if (arrby3[n7] == arrby[n5 + n7]) continue;
                                continue block1;
                            }
                            n8 = n2 + 1;
                            n6 = n5 + (arrby3.length - 1);
                            n7 = n4;
                            break block11;
                        }
                        n9 = n3 + 1;
                    }
                    if (arrby[n5] == 14) break block12;
                    n8 = n2;
                    n3 = n9;
                    n7 = n4;
                    n6 = n5;
                    if (arrby[n5] != 15) break block11;
                }
                n7 = n4 + 1;
                n6 = n5;
                n3 = n9;
                n8 = n2;
            }
            n5 = n6 + 1;
            n2 = n8;
            n4 = n7;
        }
        if (n2 == 0) {
            return 0;
        }
        n = n5 = (n2 * 100 - n3 * 100) / (n2 + n3);
        if (n2 + n4 < 5) {
            n = n5 - (5 - (n2 + n4)) * 10;
        }
        n5 = n;
        if (n < 0) {
            n5 = 0;
        }
        return n5;
    }

    static class CharsetRecog_2022CN
    extends CharsetRecog_2022 {
        private byte[][] escapeSequences;

        CharsetRecog_2022CN() {
            byte[] arrby = new byte[]{27, 36, 41, 65};
            byte[] arrby2 = new byte[]{27, 36, 42, 72};
            byte[] arrby3 = new byte[]{27, 36, 41, 69};
            byte[] arrby4 = new byte[]{27, 36, 43, 73};
            byte[] arrby5 = new byte[]{27, 36, 43, 74};
            byte[] arrby6 = new byte[]{27, 36, 43, 75};
            byte[] arrby7 = new byte[]{27, 36, 43, 76};
            byte[] arrby8 = new byte[]{27, 36, 43, 77};
            byte[] arrby9 = new byte[]{27, 78};
            byte[] arrby10 = new byte[]{27, 79};
            this.escapeSequences = new byte[][]{arrby, {27, 36, 41, 71}, arrby2, arrby3, arrby4, arrby5, arrby6, arrby7, arrby8, arrby9, arrby10};
        }

        @Override
        String getName() {
            return "ISO-2022-CN";
        }

        @Override
        CharsetMatch match(CharsetDetector object) {
            int n = this.match(((CharsetDetector)object).fInputBytes, ((CharsetDetector)object).fInputLen, this.escapeSequences);
            object = n == 0 ? null : new CharsetMatch((CharsetDetector)object, this, n);
            return object;
        }
    }

    static class CharsetRecog_2022JP
    extends CharsetRecog_2022 {
        private byte[][] escapeSequences = new byte[][]{{27, 36, 40, 67}, {27, 36, 40, 68}, {27, 36, 64}, {27, 36, 65}, {27, 36, 66}, {27, 38, 64}, {27, 40, 66}, {27, 40, 72}, {27, 40, 73}, {27, 40, 74}, {27, 46, 65}, {27, 46, 70}};

        CharsetRecog_2022JP() {
        }

        @Override
        String getName() {
            return "ISO-2022-JP";
        }

        @Override
        CharsetMatch match(CharsetDetector object) {
            int n = this.match(((CharsetDetector)object).fInputBytes, ((CharsetDetector)object).fInputLen, this.escapeSequences);
            object = n == 0 ? null : new CharsetMatch((CharsetDetector)object, this, n);
            return object;
        }
    }

    static class CharsetRecog_2022KR
    extends CharsetRecog_2022 {
        private byte[][] escapeSequences = new byte[][]{{27, 36, 41, 67}};

        CharsetRecog_2022KR() {
        }

        @Override
        String getName() {
            return "ISO-2022-KR";
        }

        @Override
        CharsetMatch match(CharsetDetector object) {
            int n = this.match(((CharsetDetector)object).fInputBytes, ((CharsetDetector)object).fInputLen, this.escapeSequences);
            object = n == 0 ? null : new CharsetMatch((CharsetDetector)object, this, n);
            return object;
        }
    }

}

