/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import java.util.ArrayList;

public final class MimeTypeFilter {
    private MimeTypeFilter() {
    }

    public static String matches(String string22, String[] arrstring) {
        if (string22 == null) {
            return null;
        }
        String[] arrstring2 = string22.split("/");
        for (String string22 : arrstring) {
            if (!MimeTypeFilter.mimeTypeAgainstFilter(arrstring2, string22.split("/"))) continue;
            return string22;
        }
        return null;
    }

    public static String matches(String[] arrstring, String string22) {
        if (arrstring == null) {
            return null;
        }
        String[] arrstring2 = string22.split("/");
        for (String string22 : arrstring) {
            if (!MimeTypeFilter.mimeTypeAgainstFilter(string22.split("/"), arrstring2)) continue;
            return string22;
        }
        return null;
    }

    public static boolean matches(String string2, String string3) {
        if (string2 == null) {
            return false;
        }
        return MimeTypeFilter.mimeTypeAgainstFilter(string2.split("/"), string3.split("/"));
    }

    public static String[] matchesMany(String[] arrstring, String arrstring2) {
        if (arrstring == null) {
            return new String[0];
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrstring2 = arrstring2.split("/");
        for (String string2 : arrstring) {
            if (!MimeTypeFilter.mimeTypeAgainstFilter(string2.split("/"), arrstring2)) continue;
            arrayList.add(string2);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private static boolean mimeTypeAgainstFilter(String[] arrstring, String[] arrstring2) {
        if (arrstring2.length == 2) {
            if (!arrstring2[0].isEmpty() && !arrstring2[1].isEmpty()) {
                if (arrstring.length != 2) {
                    return false;
                }
                if (!"*".equals(arrstring2[0]) && !arrstring2[0].equals(arrstring[0])) {
                    return false;
                }
                return "*".equals(arrstring2[1]) || arrstring2[1].equals(arrstring[1]);
            }
            throw new IllegalArgumentException("Ill-formatted MIME type filter. Type or subtype empty.");
        }
        throw new IllegalArgumentException("Ill-formatted MIME type filter. Must be type/subtype.");
    }
}

