/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

public final class DalvikLogging {
    private DalvikLogging() {
    }

    public static String loggerNameToTag(String string) {
        if (string == null) {
            return "null";
        }
        int n = string.length();
        if (n <= 23) {
            return string;
        }
        int n2 = string.lastIndexOf(".");
        string = n - (n2 + 1) <= 23 ? string.substring(n2 + 1) : string.substring(string.length() - 23);
        return string;
    }
}

