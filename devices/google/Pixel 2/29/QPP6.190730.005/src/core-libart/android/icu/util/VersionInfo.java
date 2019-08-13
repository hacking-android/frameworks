/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.LocaleData;
import android.icu.util.UResourceBundle;
import java.io.PrintStream;
import java.util.concurrent.ConcurrentHashMap;

public final class VersionInfo
implements Comparable<VersionInfo> {
    @Deprecated
    public static final VersionInfo ICU_DATA_VERSION;
    @Deprecated
    public static final String ICU_DATA_VERSION_PATH = "63b";
    public static final VersionInfo ICU_VERSION;
    private static final String INVALID_VERSION_NUMBER_ = "Invalid version number: Version number may be negative or greater than 255";
    private static final int LAST_BYTE_MASK_ = 255;
    private static final ConcurrentHashMap<Integer, VersionInfo> MAP_;
    private static volatile String TZDATA_VERSION;
    public static final VersionInfo UCOL_BUILDER_VERSION;
    public static final VersionInfo UCOL_RUNTIME_VERSION;
    @Deprecated
    public static final VersionInfo UCOL_TAILORINGS_VERSION;
    public static final VersionInfo UNICODE_10_0;
    public static final VersionInfo UNICODE_11_0;
    public static final VersionInfo UNICODE_1_0;
    public static final VersionInfo UNICODE_1_0_1;
    public static final VersionInfo UNICODE_1_1_0;
    public static final VersionInfo UNICODE_1_1_5;
    public static final VersionInfo UNICODE_2_0;
    public static final VersionInfo UNICODE_2_1_2;
    public static final VersionInfo UNICODE_2_1_5;
    public static final VersionInfo UNICODE_2_1_8;
    public static final VersionInfo UNICODE_2_1_9;
    public static final VersionInfo UNICODE_3_0;
    public static final VersionInfo UNICODE_3_0_1;
    public static final VersionInfo UNICODE_3_1_0;
    public static final VersionInfo UNICODE_3_1_1;
    public static final VersionInfo UNICODE_3_2;
    public static final VersionInfo UNICODE_4_0;
    public static final VersionInfo UNICODE_4_0_1;
    public static final VersionInfo UNICODE_4_1;
    public static final VersionInfo UNICODE_5_0;
    public static final VersionInfo UNICODE_5_1;
    public static final VersionInfo UNICODE_5_2;
    public static final VersionInfo UNICODE_6_0;
    public static final VersionInfo UNICODE_6_1;
    public static final VersionInfo UNICODE_6_2;
    public static final VersionInfo UNICODE_6_3;
    public static final VersionInfo UNICODE_7_0;
    public static final VersionInfo UNICODE_8_0;
    public static final VersionInfo UNICODE_9_0;
    private static final VersionInfo UNICODE_VERSION;
    private static volatile VersionInfo javaVersion;
    private int m_version_;

    static {
        MAP_ = new ConcurrentHashMap();
        UNICODE_1_0 = VersionInfo.getInstance(1, 0, 0, 0);
        UNICODE_1_0_1 = VersionInfo.getInstance(1, 0, 1, 0);
        UNICODE_1_1_0 = VersionInfo.getInstance(1, 1, 0, 0);
        UNICODE_1_1_5 = VersionInfo.getInstance(1, 1, 5, 0);
        UNICODE_2_0 = VersionInfo.getInstance(2, 0, 0, 0);
        UNICODE_2_1_2 = VersionInfo.getInstance(2, 1, 2, 0);
        UNICODE_2_1_5 = VersionInfo.getInstance(2, 1, 5, 0);
        UNICODE_2_1_8 = VersionInfo.getInstance(2, 1, 8, 0);
        UNICODE_2_1_9 = VersionInfo.getInstance(2, 1, 9, 0);
        UNICODE_3_0 = VersionInfo.getInstance(3, 0, 0, 0);
        UNICODE_3_0_1 = VersionInfo.getInstance(3, 0, 1, 0);
        UNICODE_3_1_0 = VersionInfo.getInstance(3, 1, 0, 0);
        UNICODE_3_1_1 = VersionInfo.getInstance(3, 1, 1, 0);
        UNICODE_3_2 = VersionInfo.getInstance(3, 2, 0, 0);
        UNICODE_4_0 = VersionInfo.getInstance(4, 0, 0, 0);
        UNICODE_4_0_1 = VersionInfo.getInstance(4, 0, 1, 0);
        UNICODE_4_1 = VersionInfo.getInstance(4, 1, 0, 0);
        UNICODE_5_0 = VersionInfo.getInstance(5, 0, 0, 0);
        UNICODE_5_1 = VersionInfo.getInstance(5, 1, 0, 0);
        UNICODE_5_2 = VersionInfo.getInstance(5, 2, 0, 0);
        UNICODE_6_0 = VersionInfo.getInstance(6, 0, 0, 0);
        UNICODE_6_1 = VersionInfo.getInstance(6, 1, 0, 0);
        UNICODE_6_2 = VersionInfo.getInstance(6, 2, 0, 0);
        UNICODE_6_3 = VersionInfo.getInstance(6, 3, 0, 0);
        UNICODE_7_0 = VersionInfo.getInstance(7, 0, 0, 0);
        UNICODE_8_0 = VersionInfo.getInstance(8, 0, 0, 0);
        UNICODE_9_0 = VersionInfo.getInstance(9, 0, 0, 0);
        UNICODE_10_0 = VersionInfo.getInstance(10, 0, 0, 0);
        UNICODE_11_0 = VersionInfo.getInstance(11, 0, 0, 0);
        ICU_DATA_VERSION = ICU_VERSION = VersionInfo.getInstance(63, 2, 0, 0);
        UNICODE_VERSION = UNICODE_11_0;
        UCOL_RUNTIME_VERSION = VersionInfo.getInstance(9);
        UCOL_BUILDER_VERSION = VersionInfo.getInstance(9);
        UCOL_TAILORINGS_VERSION = VersionInfo.getInstance(1);
        TZDATA_VERSION = null;
    }

    private VersionInfo(int n) {
        this.m_version_ = n;
    }

    public static VersionInfo getInstance(int n) {
        return VersionInfo.getInstance(n, 0, 0, 0);
    }

    public static VersionInfo getInstance(int n, int n2) {
        return VersionInfo.getInstance(n, n2, 0, 0);
    }

    public static VersionInfo getInstance(int n, int n2, int n3) {
        return VersionInfo.getInstance(n, n2, n3, 0);
    }

    public static VersionInfo getInstance(int n, int n2, int n3, int n4) {
        if (n >= 0 && n <= 255 && n2 >= 0 && n2 <= 255 && n3 >= 0 && n3 <= 255 && n4 >= 0 && n4 <= 255) {
            VersionInfo versionInfo;
            n = VersionInfo.getInt(n, n2, n3, n4);
            Integer n5 = n;
            VersionInfo versionInfo2 = versionInfo = MAP_.get(n5);
            if (versionInfo == null && (versionInfo = MAP_.putIfAbsent(n5, versionInfo2 = new VersionInfo(n))) != null) {
                versionInfo2 = versionInfo;
            }
            return versionInfo2;
        }
        throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
    }

    public static VersionInfo getInstance(String string) {
        int n;
        int n2 = string.length();
        Object object = new int[4];
        int[] arrn = object;
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        arrn[3] = 0;
        int n3 = 0;
        for (n = 0; n3 < 4 && n < n2; ++n) {
            char c = string.charAt(n);
            if (c == '.') {
                ++n3;
                continue;
            }
            if ((c = (char)(c - 48)) >= '\u0000' && c <= '\t') {
                object[n3] = object[n3] * 10;
                object[n3] = object[n3] + c;
                continue;
            }
            throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
        }
        if (n == n2) {
            for (n = 0; n < 4; ++n) {
                if (object[n] >= 0 && object[n] <= 255) {
                    continue;
                }
                throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
            }
            return VersionInfo.getInstance((int)object[0], (int)object[1], (int)object[2], (int)object[3]);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid version number: String '");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("' exceeds version format");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static int getInt(int n, int n2, int n3, int n4) {
        return n << 24 | n2 << 16 | n3 << 8 | n4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String getTZDataVersion() {
        if (TZDATA_VERSION != null) return TZDATA_VERSION;
        synchronized (VersionInfo.class) {
            if (TZDATA_VERSION != null) return TZDATA_VERSION;
            TZDATA_VERSION = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "zoneinfo64").getString("TZVersion");
            return TZDATA_VERSION;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static VersionInfo javaVersion() {
        if (javaVersion != null) return javaVersion;
        synchronized (VersionInfo.class) {
            int n;
            if (javaVersion != null) return javaVersion;
            char[] arrc = System.getProperty("java.version").toCharArray();
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            do {
                int n6;
                n = n3;
                if (n2 >= arrc.length) break;
                int n7 = arrc[n2];
                if (n7 >= 48 && n7 <= 57) {
                    n = 1;
                    arrc[n3] = (char)n7;
                    n7 = n3 + 1;
                    n6 = n4;
                } else {
                    n7 = n3;
                    n6 = n4;
                    n = n5;
                    if (n5 != 0) {
                        if (n4 == 3) {
                            n = n3;
                            break;
                        }
                        n = 0;
                        arrc[n3] = (char)46;
                        n6 = n4 + 1;
                        n7 = n3 + 1;
                    }
                }
                ++n2;
                n3 = n7;
                n4 = n6;
                n5 = n;
            } while (true);
            while (n > 0 && arrc[n - 1] == '.') {
                --n;
            }
            String string = new String(arrc, 0, n);
            javaVersion = VersionInfo.getInstance(string);
            return javaVersion;
        }
    }

    public static void main(String[] object) {
        if (ICU_VERSION.getMajor() <= 4) {
            if (ICU_VERSION.getMinor() % 2 != 0) {
                int n = ICU_VERSION.getMajor();
                int n2 = ICU_VERSION.getMinor() + 1;
                int n3 = n;
                int n4 = n2;
                if (n2 >= 10) {
                    n4 = n2 - 10;
                    n3 = n + 1;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(".");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append("M");
                ((StringBuilder)object).append(ICU_VERSION.getMilli());
                object = ((StringBuilder)object).toString();
            } else {
                object = ICU_VERSION.getVersionString(2, 2);
            }
        } else if (ICU_VERSION.getMinor() == 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("");
            ((StringBuilder)object).append(ICU_VERSION.getMajor());
            ((StringBuilder)object).append("M");
            ((StringBuilder)object).append(ICU_VERSION.getMilli());
            object = ((StringBuilder)object).toString();
        } else {
            object = ICU_VERSION.getVersionString(2, 2);
        }
        Appendable appendable = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("International Components for Unicode for Java ");
        stringBuilder.append((String)object);
        ((PrintStream)appendable).println(stringBuilder.toString());
        System.out.println("");
        object = System.out;
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("Implementation Version: ");
        ((StringBuilder)appendable).append(ICU_VERSION.getVersionString(2, 4));
        ((PrintStream)object).println(((StringBuilder)appendable).toString());
        appendable = System.out;
        object = new StringBuilder();
        ((StringBuilder)object).append("Unicode Data Version:   ");
        ((StringBuilder)object).append(UNICODE_VERSION.getVersionString(2, 4));
        ((PrintStream)appendable).println(((StringBuilder)object).toString());
        appendable = System.out;
        object = new StringBuilder();
        ((StringBuilder)object).append("CLDR Data Version:      ");
        ((StringBuilder)object).append(LocaleData.getCLDRVersion().getVersionString(2, 4));
        ((PrintStream)appendable).println(((StringBuilder)object).toString());
        appendable = System.out;
        object = new StringBuilder();
        ((StringBuilder)object).append("Time Zone Data Version: ");
        ((StringBuilder)object).append(VersionInfo.getTZDataVersion());
        ((PrintStream)appendable).println(((StringBuilder)object).toString());
    }

    @Override
    public int compareTo(VersionInfo versionInfo) {
        return this.m_version_ - versionInfo.m_version_;
    }

    public boolean equals(Object object) {
        boolean bl = object == this;
        return bl;
    }

    public int getMajor() {
        return this.m_version_ >> 24 & 255;
    }

    public int getMicro() {
        return this.m_version_ & 255;
    }

    public int getMilli() {
        return this.m_version_ >> 8 & 255;
    }

    public int getMinor() {
        return this.m_version_ >> 16 & 255;
    }

    @Deprecated
    public String getVersionString(int n, int n2) {
        if (n >= 1 && n2 >= 1 && n <= 4 && n2 <= 4 && n <= n2) {
            int[] arrn = new int[]{this.getMajor(), this.getMinor(), this.getMilli(), this.getMicro()};
            while (n2 > n && arrn[n2 - 1] == 0) {
                --n2;
            }
            StringBuilder stringBuilder = new StringBuilder(7);
            stringBuilder.append(arrn[0]);
            for (n = 1; n < n2; ++n) {
                stringBuilder.append(".");
                stringBuilder.append(arrn[n]);
            }
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("Invalid min/maxDigits range");
    }

    public int hashCode() {
        return this.m_version_;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(7);
        stringBuilder.append(this.getMajor());
        stringBuilder.append('.');
        stringBuilder.append(this.getMinor());
        stringBuilder.append('.');
        stringBuilder.append(this.getMilli());
        stringBuilder.append('.');
        stringBuilder.append(this.getMicro());
        return stringBuilder.toString();
    }
}

