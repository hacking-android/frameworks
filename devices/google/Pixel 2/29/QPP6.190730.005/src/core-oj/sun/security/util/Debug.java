/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Debug {
    private static String args;
    private static final char[] hexDigits;
    private String prefix;

    static {
        hexDigits = "0123456789abcdef".toCharArray();
    }

    public static Debug getInstance(String string) {
        return Debug.getInstance(string, string);
    }

    public static Debug getInstance(String object, String string) {
        if (Debug.isOn((String)object)) {
            object = new Debug();
            ((Debug)object).prefix = string;
            return object;
        }
        return null;
    }

    public static boolean isOn(String string) {
        String string2 = args;
        boolean bl = false;
        if (string2 == null) {
            return false;
        }
        if (string2.indexOf("all") != -1) {
            return true;
        }
        if (args.indexOf(string) != -1) {
            bl = true;
        }
        return bl;
    }

    private static String marshal(String object) {
        if (object != null) {
            StringBuffer stringBuffer = new StringBuffer();
            object = new StringBuffer((String)object);
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("[Pp][Ee][Rr][Mm][Ii][Ss][Ss][Ii][Oo][Nn]=");
            ((StringBuilder)object2).append("[a-zA-Z_$][a-zA-Z0-9_$]*([.][a-zA-Z_$][a-zA-Z0-9_$]*)*");
            object2 = Pattern.compile(((StringBuilder)object2).toString()).matcher((CharSequence)object);
            object = new StringBuffer();
            while (((Matcher)object2).find()) {
                stringBuffer.append(((Matcher)object2).group().replaceFirst("[Pp][Ee][Rr][Mm][Ii][Ss][Ss][Ii][Oo][Nn]=", "permission="));
                stringBuffer.append("  ");
                ((Matcher)object2).appendReplacement((StringBuffer)object, "");
            }
            ((Matcher)object2).appendTail((StringBuffer)object);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("[Cc][Oo][Dd][Ee][Bb][Aa][Ss][Ee]=");
            ((StringBuilder)object2).append("[^, ;]*");
            object = Pattern.compile(((StringBuilder)object2).toString()).matcher((CharSequence)object);
            object2 = new StringBuffer();
            while (((Matcher)object).find()) {
                stringBuffer.append(((Matcher)object).group().replaceFirst("[Cc][Oo][Dd][Ee][Bb][Aa][Ss][Ee]=", "codebase="));
                stringBuffer.append("  ");
                ((Matcher)object).appendReplacement((StringBuffer)object2, "");
            }
            ((Matcher)object).appendTail((StringBuffer)object2);
            stringBuffer.append(((StringBuffer)object2).toString().toLowerCase(Locale.ENGLISH));
            return stringBuffer.toString();
        }
        return null;
    }

    public static String toHexString(BigInteger object) {
        object = ((BigInteger)object).toString(16);
        StringBuffer stringBuffer = new StringBuffer(((String)object).length() * 2);
        if (((String)object).startsWith("-")) {
            stringBuffer.append("   -");
            object = ((String)object).substring(1);
        } else {
            stringBuffer.append("    ");
        }
        Object object2 = object;
        if (((String)object).length() % 2 != 0) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("0");
            ((StringBuilder)object2).append((String)object);
            object2 = ((StringBuilder)object2).toString();
        }
        int n = 0;
        while (n < ((String)object2).length()) {
            int n2;
            stringBuffer.append(((String)object2).substring(n, n + 2));
            n = n2 = n + 2;
            if (n2 == ((String)object2).length()) continue;
            if (n2 % 64 == 0) {
                stringBuffer.append("\n    ");
                n = n2;
                continue;
            }
            n = n2;
            if (n2 % 8 != 0) continue;
            stringBuffer.append(" ");
            n = n2;
        }
        return stringBuffer.toString();
    }

    public static String toString(byte[] arrby) {
        if (arrby == null) {
            return "(null)";
        }
        StringBuilder stringBuilder = new StringBuilder(arrby.length * 3);
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 255;
            if (i != 0) {
                stringBuilder.append(':');
            }
            stringBuilder.append(hexDigits[n >>> 4]);
            stringBuilder.append(hexDigits[n & 15]);
        }
        return stringBuilder.toString();
    }

    public void println() {
        PrintStream printStream = System.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.prefix);
        stringBuilder.append(":");
        printStream.println(stringBuilder.toString());
    }

    public void println(String string) {
        PrintStream printStream = System.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.prefix);
        stringBuilder.append(": ");
        stringBuilder.append(string);
        printStream.println(stringBuilder.toString());
    }
}

