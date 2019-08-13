/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.CharsetDetector;
import android.icu.text.CharsetMatch;
import android.icu.text.CharsetRecognizer;

class CharsetRecog_UTF8
extends CharsetRecognizer {
    CharsetRecog_UTF8() {
    }

    @Override
    String getName() {
        return "UTF-8";
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    CharsetMatch match(CharsetDetector object) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        byte[] arrby = ((CharsetDetector)object).fRawInput;
        int n4 = n;
        if (((CharsetDetector)object).fRawLength >= 3) {
            n4 = n;
            if ((arrby[0] & 255) == 239) {
                n4 = n;
                if ((arrby[1] & 255) == 187) {
                    n4 = n;
                    if ((arrby[2] & 255) == 191) {
                        n4 = 1;
                    }
                }
            }
        }
        n = 0;
        do {
            int n5;
            block19 : {
                int n6;
                int n7;
                block23 : {
                    block20 : {
                        block25 : {
                            block24 : {
                                block22 : {
                                    block21 : {
                                        if (n >= ((CharsetDetector)object).fRawLength) break block20;
                                        n5 = arrby[n];
                                        if ((n5 & 128) != 0) break block21;
                                        n5 = n3;
                                        break block19;
                                    }
                                    if ((n5 & 224) != 192) break block22;
                                    n5 = 1;
                                    n6 = n;
                                    break block23;
                                }
                                if ((n5 & 240) != 224) break block24;
                                n5 = 2;
                                n6 = n;
                                break block23;
                            }
                            if ((n5 & 248) != 240) break block25;
                            n5 = 3;
                            n6 = n;
                            break block23;
                        }
                        n5 = n3 + 1;
                        break block19;
                    }
                    n = 0;
                    if (n4 != 0 && n3 == 0) {
                        n = 100;
                    } else if (n4 != 0 && n2 > n3 * 10) {
                        n = 80;
                    } else if (n2 > 3 && n3 == 0) {
                        n = 100;
                    } else if (n2 > 0 && n3 == 0) {
                        n = 80;
                    } else if (n2 == 0 && n3 == 0) {
                        n = 15;
                    } else if (n2 > n3 * 10) {
                        n = 25;
                    }
                    if (n != 0) return new CharsetMatch((CharsetDetector)object, this, n);
                    return null;
                }
                do {
                    if ((n = n6 + 1) >= ((CharsetDetector)object).fRawLength) {
                        n5 = n3;
                        break block19;
                    }
                    if ((arrby[n] & 192) != 128) {
                        n5 = n3 + 1;
                        break block19;
                    }
                    n5 = n7 = n5 - 1;
                    n6 = n;
                } while (n7 != 0);
                ++n2;
                n5 = n3;
            }
            ++n;
            n3 = n5;
        } while (true);
    }
}

