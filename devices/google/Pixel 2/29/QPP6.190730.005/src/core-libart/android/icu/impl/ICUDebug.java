/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.util.VersionInfo;
import java.io.PrintStream;

public final class ICUDebug {
    private static boolean debug;
    private static boolean help;
    public static final boolean isJDK14OrHigher;
    public static final VersionInfo javaVersion;
    public static final String javaVersionString;
    private static String params;

    static {
        try {
            params = System.getProperty("ICUDebug");
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        Object object = params;
        boolean bl = true;
        boolean bl2 = object != null;
        debug = bl2;
        bl2 = debug && (params.equals("") || params.indexOf("help") != -1);
        help = bl2;
        if (debug) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nICUDebug=");
            stringBuilder.append(params);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        bl2 = (javaVersion = ICUDebug.getInstanceLenient(javaVersionString = System.getProperty("java.version", "0"))).compareTo((VersionInfo)(object = VersionInfo.getInstance("1.4.0"))) >= 0 ? bl : false;
        isJDK14OrHigher = bl2;
    }

    public static boolean enabled() {
        return debug;
    }

    public static boolean enabled(String string) {
        boolean bl = debug;
        boolean bl2 = false;
        if (bl) {
            if (params.indexOf(string) != -1) {
                bl2 = true;
            }
            if (help) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\nICUDebug.enabled(");
                stringBuilder.append(string);
                stringBuilder.append(") = ");
                stringBuilder.append(bl2);
                printStream.println(stringBuilder.toString());
            }
            return bl2;
        }
        return false;
    }

    public static VersionInfo getInstanceLenient(String string) {
        int[] arrn = new int[4];
        boolean bl = false;
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            boolean bl2;
            int n2 = string.charAt(i);
            if (n2 >= 48 && n2 <= 57) {
                if (bl) {
                    arrn[n] = arrn[n] * 10 + (n2 - 48);
                    bl2 = bl;
                    n2 = n;
                    if (arrn[n] > 255) {
                        arrn[n] = 0;
                        break;
                    }
                } else {
                    bl2 = true;
                    arrn[n] = n2 - 48;
                    n2 = n;
                }
            } else {
                bl2 = bl;
                n2 = n;
                if (bl) {
                    if (n == 3) break;
                    bl2 = false;
                    n2 = n + '\u0001';
                }
            }
            bl = bl2;
            n = n2;
        }
        return VersionInfo.getInstance(arrn[0], arrn[1], arrn[2], arrn[3]);
    }

    public static String value(String string) {
        String string2 = "false";
        Object object = string2;
        if (debug) {
            int n = params.indexOf(string);
            if (n != -1) {
                if (params.length() > (n += string.length()) && params.charAt(n) == '=') {
                    int n2 = n + 1;
                    n = params.indexOf(",", n2);
                    string2 = params;
                    if (n == -1) {
                        n = string2.length();
                    }
                    string2 = string2.substring(n2, n);
                } else {
                    string2 = "true";
                }
            }
            object = string2;
            if (help) {
                object = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\nICUDebug.value(");
                stringBuilder.append(string);
                stringBuilder.append(") = ");
                stringBuilder.append(string2);
                ((PrintStream)object).println(stringBuilder.toString());
                object = string2;
            }
        }
        return object;
    }
}

