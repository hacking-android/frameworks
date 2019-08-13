/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.PrintStream;

public class MessageUtils {
    public static void err(String string) {
        System.err.println(string);
    }

    public static void out(String string) {
        System.out.println(string);
    }

    public static String subst(String string, String string2) {
        return MessageUtils.subst(string, new String[]{string2});
    }

    public static String subst(String string, String string2, String string3) {
        return MessageUtils.subst(string, new String[]{string2, string3});
    }

    public static String subst(String string, String string2, String string3, String string4) {
        return MessageUtils.subst(string, new String[]{string2, string3, string4});
    }

    public static String subst(String string, String[] arrstring) {
        StringBuffer stringBuffer = new StringBuffer();
        int n = string.length();
        int n2 = 0;
        while (n2 >= 0 && n2 < n) {
            int n3;
            char c = string.charAt(n2);
            if (c == '%') {
                n3 = n2;
                if (n2 != n) {
                    int n4 = Character.digit(string.charAt(n2 + 1), 10);
                    if (n4 == -1) {
                        stringBuffer.append(string.charAt(n2 + 1));
                        n3 = n2 + 1;
                    } else {
                        n3 = n2;
                        if (n4 < arrstring.length) {
                            stringBuffer.append(arrstring[n4]);
                            n3 = n2 + 1;
                        }
                    }
                }
            } else {
                stringBuffer.append(c);
                n3 = n2;
            }
            n2 = n3 + 1;
        }
        return stringBuffer.toString();
    }

    public static String substProp(String string, String string2) {
        return MessageUtils.subst(System.getProperty(string), string2);
    }

    public static String substProp(String string, String string2, String string3) {
        return MessageUtils.subst(System.getProperty(string), string2, string3);
    }

    public static String substProp(String string, String string2, String string3, String string4) {
        return MessageUtils.subst(System.getProperty(string), string2, string3, string4);
    }

    public static void where() {
        StackTraceElement[] arrstackTraceElement = new Throwable().getStackTrace();
        for (int i = 1; i < arrstackTraceElement.length; ++i) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\t");
            stringBuilder.append(arrstackTraceElement[i].toString());
            printStream.println(stringBuilder.toString());
        }
    }
}

