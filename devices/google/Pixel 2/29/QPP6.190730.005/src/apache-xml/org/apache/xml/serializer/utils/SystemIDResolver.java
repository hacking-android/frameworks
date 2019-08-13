/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.io.File;
import javax.xml.transform.TransformerException;
import org.apache.xml.serializer.utils.URI;

public final class SystemIDResolver {
    private static String getAbsolutePathFromRelativePath(String string) {
        return new File(string).getAbsolutePath();
    }

    public static String getAbsoluteURI(String string) {
        block7 : {
            block8 : {
                block9 : {
                    String string2;
                    block11 : {
                        CharSequence charSequence;
                        block10 : {
                            charSequence = string;
                            if (!SystemIDResolver.isAbsoluteURI(string)) break block7;
                            if (!string.startsWith("file:")) break block8;
                            String string3 = string.substring(5);
                            if (string3 == null || !string3.startsWith("/")) break block9;
                            if (string3.startsWith("///")) break block10;
                            string2 = charSequence;
                            if (string3.startsWith("//")) break block11;
                        }
                        int n = string.indexOf(58, 5);
                        string2 = charSequence;
                        if (n > 0) {
                            string2 = string.substring(n - 1);
                            try {
                                if (!SystemIDResolver.isAbsolutePath(string2)) {
                                    charSequence = new StringBuilder();
                                    ((StringBuilder)charSequence).append(string.substring(0, n - 1));
                                    ((StringBuilder)charSequence).append(SystemIDResolver.getAbsolutePathFromRelativePath(string2));
                                    charSequence = ((StringBuilder)charSequence).toString();
                                }
                                string2 = charSequence;
                            }
                            catch (SecurityException securityException) {
                                return string;
                            }
                        }
                    }
                    return SystemIDResolver.replaceChars(string2);
                }
                return SystemIDResolver.getAbsoluteURIFromRelative(string.substring(5));
            }
            return string;
        }
        return SystemIDResolver.getAbsoluteURIFromRelative(string);
    }

    public static String getAbsoluteURI(String object, String string) throws TransformerException {
        if (string == null) {
            return SystemIDResolver.getAbsoluteURI((String)object);
        }
        string = SystemIDResolver.getAbsoluteURI(string);
        try {
            URI uRI = new URI(string);
            object = new URI(uRI, (String)object);
        }
        catch (URI.MalformedURIException malformedURIException) {
            throw new TransformerException(malformedURIException);
        }
        return SystemIDResolver.replaceChars(((URI)object).toString());
    }

    public static String getAbsoluteURIFromRelative(String charSequence) {
        if (charSequence != null && ((String)charSequence).length() != 0) {
            CharSequence charSequence2 = charSequence;
            if (!SystemIDResolver.isAbsolutePath((String)charSequence)) {
                try {
                    charSequence2 = SystemIDResolver.getAbsolutePathFromRelativePath((String)charSequence);
                }
                catch (SecurityException securityException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("file:");
                    stringBuilder.append((String)charSequence);
                    return stringBuilder.toString();
                }
            }
            if (charSequence2 != null) {
                if (((String)charSequence2).startsWith(File.separator)) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("file://");
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    charSequence = ((StringBuilder)charSequence).toString();
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("file:///");
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            } else {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("file:");
                ((StringBuilder)charSequence2).append((String)charSequence);
                charSequence = ((StringBuilder)charSequence2).toString();
            }
            return SystemIDResolver.replaceChars((String)charSequence);
        }
        return "";
    }

    public static boolean isAbsolutePath(String string) {
        if (string == null) {
            return false;
        }
        return new File(string).isAbsolute();
    }

    public static boolean isAbsoluteURI(String string) {
        boolean bl = SystemIDResolver.isWindowsAbsolutePath(string);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        int n = string.indexOf(35);
        int n2 = string.indexOf(63);
        int n3 = string.indexOf(47);
        int n4 = string.indexOf(58);
        int n5 = string.length() - 1;
        if (n > 0) {
            n5 = n;
        }
        n = n5;
        if (n2 > 0) {
            n = n5;
            if (n2 < n5) {
                n = n2;
            }
        }
        n5 = n;
        if (n3 > 0) {
            n5 = n;
            if (n3 < n) {
                n5 = n3;
            }
        }
        bl = bl2;
        if (n4 > 0) {
            bl = bl2;
            if (n4 < n5) {
                bl = true;
            }
        }
        return bl;
    }

    private static boolean isWindowsAbsolutePath(String string) {
        if (!SystemIDResolver.isAbsolutePath(string)) {
            return false;
        }
        return string.length() > 2 && string.charAt(1) == ':' && Character.isLetter(string.charAt(0)) && (string.charAt(2) == '\\' || string.charAt(2) == '/');
    }

    private static String replaceChars(String charSequence) {
        charSequence = new StringBuffer((String)charSequence);
        int n = ((StringBuffer)charSequence).length();
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4;
            char c = ((StringBuffer)charSequence).charAt(n2);
            if (c == ' ') {
                ((StringBuffer)charSequence).setCharAt(n2, '%');
                ((StringBuffer)charSequence).insert(n2 + 1, "20");
                n4 = n + 2;
                n3 = n2 + 2;
            } else {
                n4 = n;
                n3 = n2;
                if (c == '\\') {
                    ((StringBuffer)charSequence).setCharAt(n2, '/');
                    n3 = n2;
                    n4 = n;
                }
            }
            n2 = n3 + 1;
            n = n4;
        }
        return ((StringBuffer)charSequence).toString();
    }
}

