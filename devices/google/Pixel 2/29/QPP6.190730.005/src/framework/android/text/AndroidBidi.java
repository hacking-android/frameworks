/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.lang.UCharacter
 *  android.icu.text.Bidi
 *  android.icu.text.BidiClassifier
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.icu.lang.UCharacter;
import android.icu.text.Bidi;
import android.icu.text.BidiClassifier;
import android.text.Emoji;
import android.text.Layout;
import com.android.internal.annotations.VisibleForTesting;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public class AndroidBidi {
    private static final EmojiBidiOverride sEmojiBidiOverride = new EmojiBidiOverride();

    @UnsupportedAppUsage
    public static int bidi(int n, char[] arrc, byte[] arrby) {
        if (arrc != null && arrby != null) {
            int n2 = arrc.length;
            if (arrby.length >= n2) {
                byte by;
                int n3 = -1;
                if (n != -2) {
                    if (n != -1) {
                        if (n != 1) {
                            if (n != 2) {
                                n = 0;
                                by = n;
                            } else {
                                n = 126;
                                by = n;
                            }
                        } else {
                            n = 0;
                            by = n;
                        }
                    } else {
                        n = 1;
                        by = n;
                    }
                } else {
                    n = 127;
                    by = n;
                }
                Bidi bidi = new Bidi(n2, 0);
                bidi.setCustomClassifier((BidiClassifier)sEmojiBidiOverride);
                bidi.setPara(arrc, by, null);
                for (n = 0; n < n2; ++n) {
                    arrby[n] = bidi.getLevelAt(n);
                }
                n = n3;
                if ((bidi.getParaLevel() & 1) == 0) {
                    n = 1;
                }
                return n;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }

    public static Layout.Directions directions(int n, byte[] arrby, int n2, char[] arrc, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        if (n4 == 0) {
            return Layout.DIRS_ALL_LEFT_TO_RIGHT;
        }
        int n10 = n == 1 ? 0 : 1;
        n = n8 = arrby[n2];
        int n11 = 1;
        for (n6 = n2 + 1; n6 < n2 + n4; ++n6) {
            n9 = arrby[n6];
            n7 = n8;
            n5 = n11;
            if (n9 != n8) {
                n7 = n9;
                n5 = n11 + 1;
            }
            n8 = n7;
            n11 = n5;
        }
        n6 = n4;
        n9 = n11;
        n7 = n6;
        if ((n8 & 1) != (n10 & true)) {
            block22 : {
                do {
                    n8 = --n6;
                    if (n6 < 0) break block22;
                    n8 = arrc[n3 + n6];
                    if (n8 != 10) continue;
                    n8 = n6 - 1;
                    break block22;
                } while (n8 == 32 || n8 == 9);
                n8 = n6;
            }
            n3 = n8 + 1;
            n9 = n11;
            n7 = n3;
            if (n3 != n4) {
                n9 = n11 + 1;
                n7 = n3;
            }
        }
        if (n9 == 1 && n == n10) {
            if ((n & 1) != 0) {
                return Layout.DIRS_ALL_RIGHT_TO_LEFT;
            }
            return Layout.DIRS_ALL_LEFT_TO_RIGHT;
        }
        arrc = new int[n9 * 2];
        n3 = n;
        int n12 = n << 26;
        int n13 = 1;
        int n14 = n2;
        int n15 = n;
        for (n11 = n2; n11 < n2 + n7; ++n11) {
            n5 = arrby[n11];
            int n16 = n15;
            n6 = n;
            n8 = n3;
            int n17 = n12;
            int n18 = n13;
            int n19 = n14;
            if (n5 != n15) {
                n15 = n5;
                if (n5 > n3) {
                    n8 = n5;
                    n6 = n;
                } else {
                    n6 = n;
                    n8 = n3;
                    if (n5 < n) {
                        n6 = n5;
                        n8 = n3;
                    }
                }
                n = n13 + 1;
                arrc[n13] = n11 - n14 | n12;
                n18 = n + 1;
                arrc[n] = n11 - n2;
                n17 = n15 << 26;
                n19 = n11;
                n16 = n15;
            }
            n15 = n16;
            n = n6;
            n3 = n8;
            n12 = n17;
            n13 = n18;
            n14 = n19;
        }
        arrc[n13] = n2 + n7 - n14 | n12;
        if (n7 < n4) {
            n2 = n13 + 1;
            arrc[n2] = n7;
            arrc[n2 + 1] = n4 - n7 | n10 << 26;
        }
        if ((n & 1) == n10) {
            n2 = n + 1;
            n = n3 > n2 ? 1 : 0;
        } else {
            n2 = n9 > 1 ? 1 : 0;
            n4 = n2;
            n2 = n;
            n = n4;
        }
        if (n != 0) {
            --n3;
            while (n3 >= n2) {
                n = 0;
                while (n < arrc.length) {
                    n4 = n;
                    if (arrby[arrc[n]] >= n3) {
                        for (n4 = n + 2; n4 < arrc.length && arrby[arrc[n4]] >= n3; n4 += 2) {
                        }
                        n11 = n;
                        for (n = n4 - 2; n11 < n; n11 += 2, n -= 2) {
                            n6 = arrc[n11];
                            arrc[n11] = arrc[n];
                            arrc[n] = n6;
                            n6 = arrc[n11 + 1];
                            arrc[n11 + 1] = arrc[n + 1];
                            arrc[n + 1] = n6;
                        }
                        n4 += 2;
                    }
                    n = n4 + 2;
                }
                --n3;
            }
        }
        return new Layout.Directions(arrc);
    }

    public static class EmojiBidiOverride
    extends BidiClassifier {
        private static final int NO_OVERRIDE = UCharacter.getIntPropertyMaxValue((int)4096) + 1;

        public EmojiBidiOverride() {
            super(null);
        }

        public int classify(int n) {
            if (Emoji.isNewEmoji(n)) {
                return 10;
            }
            return NO_OVERRIDE;
        }
    }

}

