/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import android.icu.text.Transliterator;
import java.util.Locale;
import libcore.icu.ICU;

class CaseMapper {
    private static final ThreadLocal<Transliterator> EL_UPPER;
    private static final char GREEK_CAPITAL_SIGMA = '\u03a3';
    private static final char GREEK_SMALL_FINAL_SIGMA = '\u03c2';
    private static final char LATIN_CAPITAL_I_WITH_DOT = '\u0130';
    private static final char[] upperValues;
    private static final char[] upperValues2;

    static {
        upperValues = "SS\u0000\u02bcN\u0000J\u030c\u0000\u0399\u0308\u0301\u03a5\u0308\u0301\u0535\u0552\u0000H\u0331\u0000T\u0308\u0000W\u030a\u0000Y\u030a\u0000A\u02be\u0000\u03a5\u0313\u0000\u03a5\u0313\u0300\u03a5\u0313\u0301\u03a5\u0313\u0342\u1f08\u0399\u0000\u1f09\u0399\u0000\u1f0a\u0399\u0000\u1f0b\u0399\u0000\u1f0c\u0399\u0000\u1f0d\u0399\u0000\u1f0e\u0399\u0000\u1f0f\u0399\u0000\u1f08\u0399\u0000\u1f09\u0399\u0000\u1f0a\u0399\u0000\u1f0b\u0399\u0000\u1f0c\u0399\u0000\u1f0d\u0399\u0000\u1f0e\u0399\u0000\u1f0f\u0399\u0000\u1f28\u0399\u0000\u1f29\u0399\u0000\u1f2a\u0399\u0000\u1f2b\u0399\u0000\u1f2c\u0399\u0000\u1f2d\u0399\u0000\u1f2e\u0399\u0000\u1f2f\u0399\u0000\u1f28\u0399\u0000\u1f29\u0399\u0000\u1f2a\u0399\u0000\u1f2b\u0399\u0000\u1f2c\u0399\u0000\u1f2d\u0399\u0000\u1f2e\u0399\u0000\u1f2f\u0399\u0000\u1f68\u0399\u0000\u1f69\u0399\u0000\u1f6a\u0399\u0000\u1f6b\u0399\u0000\u1f6c\u0399\u0000\u1f6d\u0399\u0000\u1f6e\u0399\u0000\u1f6f\u0399\u0000\u1f68\u0399\u0000\u1f69\u0399\u0000\u1f6a\u0399\u0000\u1f6b\u0399\u0000\u1f6c\u0399\u0000\u1f6d\u0399\u0000\u1f6e\u0399\u0000\u1f6f\u0399\u0000\u1fba\u0399\u0000\u0391\u0399\u0000\u0386\u0399\u0000\u0391\u0342\u0000\u0391\u0342\u0399\u0391\u0399\u0000\u1fca\u0399\u0000\u0397\u0399\u0000\u0389\u0399\u0000\u0397\u0342\u0000\u0397\u0342\u0399\u0397\u0399\u0000\u0399\u0308\u0300\u0399\u0308\u0301\u0399\u0342\u0000\u0399\u0308\u0342\u03a5\u0308\u0300\u03a5\u0308\u0301\u03a1\u0313\u0000\u03a5\u0342\u0000\u03a5\u0308\u0342\u1ffa\u0399\u0000\u03a9\u0399\u0000\u038f\u0399\u0000\u03a9\u0342\u0000\u03a9\u0342\u0399\u03a9\u0399\u0000FF\u0000FI\u0000FL\u0000FFIFFLST\u0000ST\u0000\u0544\u0546\u0000\u0544\u0535\u0000\u0544\u053b\u0000\u054e\u0546\u0000\u0544\u053d\u0000".toCharArray();
        upperValues2 = "\u000b\u0000\f\u0000\r\u0000\u000e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>\u0000\u0000?@A\u0000BC\u0000\u0000\u0000\u0000D\u0000\u0000\u0000\u0000\u0000EFG\u0000HI\u0000\u0000\u0000\u0000J\u0000\u0000\u0000\u0000\u0000KL\u0000\u0000MN\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000OPQ\u0000RS\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000TUV\u0000WX\u0000\u0000\u0000\u0000Y".toCharArray();
        EL_UPPER = new ThreadLocal<Transliterator>(){

            @Override
            protected Transliterator initialValue() {
                return Transliterator.getInstance("el-Upper");
            }
        };
    }

    private CaseMapper() {
    }

    private static boolean isFinalSigma(String string, int n) {
        if (n <= 0) {
            return false;
        }
        char c = string.charAt(n - 1);
        if (!(Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isTitleCase(c))) {
            return false;
        }
        if (n + 1 >= string.length()) {
            return true;
        }
        c = string.charAt(n + 1);
        return !(Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isTitleCase(c));
        {
        }
    }

    public static String toLowerCase(Locale locale, String string) {
        char[] arrc = locale.getLanguage();
        if (!(arrc.equals("tr") || arrc.equals("az") || arrc.equals("lt"))) {
            arrc = null;
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                if (c != '\u0130' && !Character.isHighSurrogate(c)) {
                    char c2 = c == '\u03a3' && CaseMapper.isFinalSigma(string, i) ? (char)'\u03c2' : Character.toLowerCase(c);
                    char[] arrc2 = arrc;
                    if (c != c2) {
                        arrc2 = arrc;
                        if (arrc == null) {
                            arrc2 = new char[n];
                            string.getCharsNoCheck(0, n, arrc2, 0);
                        }
                        arrc2[i] = c2;
                    }
                    arrc = arrc2;
                    continue;
                }
                return ICU.toLowerCase(string, locale);
            }
            if (arrc != null) {
                string = new String(arrc);
            }
            return string;
        }
        return ICU.toLowerCase(string, locale);
    }

    public static String toUpperCase(Locale object, String string, int n) {
        char[] arrc = ((Locale)object).getLanguage();
        if (!(arrc.equals("tr") || arrc.equals("az") || arrc.equals("lt"))) {
            if (arrc.equals("el")) {
                return EL_UPPER.get().transliterate(string);
            }
            arrc = null;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                char[] arrc2;
                int n3 = string.charAt(i);
                if (Character.isHighSurrogate((char)n3)) {
                    return ICU.toUpperCase(string, (Locale)object);
                }
                int n4 = CaseMapper.upperIndex(n3);
                int n5 = 2;
                if (n4 == -1) {
                    arrc2 = arrc;
                    if (arrc != null) {
                        arrc2 = arrc;
                        if (n2 >= arrc.length) {
                            arrc2 = new char[arrc.length + n / 6 + 2];
                            System.arraycopy(arrc, 0, arrc2, 0, arrc.length);
                        }
                    }
                    n5 = Character.toUpperCase((char)n3);
                    if (arrc2 != null) {
                        n4 = n2 + 1;
                        arrc2[n2] = (char)n5;
                        arrc = arrc2;
                        n2 = n4;
                        continue;
                    }
                    if (n3 != n5) {
                        arrc = new char[n];
                        string.getCharsNoCheck(0, i, arrc, 0);
                        n2 = i + 1;
                        arrc[i] = (char)n5;
                        continue;
                    }
                    arrc = arrc2;
                    continue;
                }
                int n6 = n4 * 3;
                char c = upperValues[n6 + 2];
                if (arrc == null) {
                    arrc2 = new char[n / 6 + n + 2];
                    n4 = i;
                    string.getCharsNoCheck(0, n4, arrc2, 0);
                } else {
                    if (c == '\u0000') {
                        n5 = 1;
                    }
                    arrc2 = arrc;
                    n4 = n2;
                    if (n5 + n2 >= arrc.length) {
                        arrc2 = new char[arrc.length + n / 6 + 3];
                        System.arraycopy(arrc, 0, arrc2, 0, arrc.length);
                        n4 = n2;
                    }
                }
                arrc = upperValues;
                n2 = arrc[n6];
                n5 = n4 + 1;
                arrc2[n4] = (char)n2;
                n4 = arrc[n6 + 1];
                n2 = n5 + 1;
                arrc2[n5] = (char)n4;
                if (c != '\u0000') {
                    arrc2[n2] = c;
                    ++n2;
                    arrc = arrc2;
                    continue;
                }
                arrc = arrc2;
            }
            if (arrc == null) {
                return string;
            }
            object = arrc.length != n2 && arrc.length - n2 >= 8 ? new String(arrc, 0, n2) : new String(0, n2, arrc);
            return object;
        }
        return ICU.toUpperCase(string, (Locale)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int upperIndex(int n) {
        int n2;
        int n3 = n2 = -1;
        if (n < 223) return n3;
        if (n <= 1415) {
            if (n == 223) return 0;
            if (n == 329) return 1;
            if (n == 496) return 2;
            if (n == 912) return 3;
            if (n == 944) return 4;
            if (n == 1415) return 5;
            return n2;
        }
        n3 = n2;
        if (n < 7830) return n3;
        if (n <= 7834) {
            return n + 6 - 7830;
        }
        if (n >= 8016 && n <= 8188) {
            n3 = n = upperValues2[n - 8016];
            if (n != 0) return n3;
            return -1;
        }
        n3 = n2;
        if (n < 64256) return n3;
        if (n <= 64262) {
            return n + 90 - 64256;
        }
        n3 = n2;
        if (n < 64275) return n3;
        n3 = n2;
        if (n > 64279) return n3;
        return n + 97 - 64275;
    }

}

