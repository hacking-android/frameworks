/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

import com.android.org.bouncycastle.util.StringList;
import com.android.org.bouncycastle.util.encoders.UTF8;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Vector;

public final class Strings {
    private static String LINE_SEPARATOR;

    static {
        try {
            PrivilegedAction<String> privilegedAction = new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return System.getProperty("line.separator");
                }
            };
            LINE_SEPARATOR = AccessController.doPrivileged(privilegedAction);
        }
        catch (Exception exception) {
            try {
                LINE_SEPARATOR = String.format("%n", new Object[0]);
            }
            catch (Exception exception2) {
                LINE_SEPARATOR = "\n";
            }
        }
    }

    public static char[] asCharArray(byte[] arrby) {
        char[] arrc = new char[arrby.length];
        for (int i = 0; i != arrc.length; ++i) {
            arrc[i] = (char)(arrby[i] & 255);
        }
        return arrc;
    }

    public static String fromByteArray(byte[] arrby) {
        return new String(Strings.asCharArray(arrby));
    }

    public static String fromUTF8ByteArray(byte[] arrby) {
        char[] arrc = new char[arrby.length];
        int n = UTF8.transcodeToUTF16(arrby, arrc);
        if (n >= 0) {
            return new String(arrc, 0, n);
        }
        throw new IllegalArgumentException("Invalid UTF-8 input");
    }

    public static String lineSeparator() {
        return LINE_SEPARATOR;
    }

    public static StringList newList() {
        return new StringListImpl();
    }

    public static String[] split(String object, char c) {
        Vector<String> vector = new Vector<String>();
        boolean bl = true;
        while (bl) {
            int n = object.indexOf(c);
            if (n > 0) {
                vector.addElement(object.substring(0, n));
                object = object.substring(n + 1);
                continue;
            }
            bl = false;
            vector.addElement((String)object);
        }
        object = new String[vector.size()];
        for (c = '\u0000'; c != ((String[])object).length; c = (char)(c + 1)) {
            object[c] = (String)vector.elementAt(c);
        }
        return object;
    }

    public static int toByteArray(String string, byte[] arrby, int n) {
        int n2 = string.length();
        for (int i = 0; i < n2; ++i) {
            arrby[n + i] = (byte)string.charAt(i);
        }
        return n2;
    }

    public static byte[] toByteArray(String string) {
        byte[] arrby = new byte[string.length()];
        for (int i = 0; i != arrby.length; ++i) {
            arrby[i] = (byte)string.charAt(i);
        }
        return arrby;
    }

    public static byte[] toByteArray(char[] arrc) {
        byte[] arrby = new byte[arrc.length];
        for (int i = 0; i != arrby.length; ++i) {
            arrby[i] = (byte)arrc[i];
        }
        return arrby;
    }

    public static String toLowerCase(String string) {
        boolean bl = false;
        char[] arrc = string.toCharArray();
        for (int i = 0; i != arrc.length; ++i) {
            char c = arrc[i];
            boolean bl2 = bl;
            if ('A' <= c) {
                bl2 = bl;
                if ('Z' >= c) {
                    bl2 = true;
                    arrc[i] = (char)(c - 65 + 97);
                }
            }
            bl = bl2;
        }
        if (bl) {
            return new String(arrc);
        }
        return string;
    }

    public static void toUTF8ByteArray(char[] arrc, OutputStream outputStream) throws IOException {
        for (int i = 0; i < arrc.length; ++i) {
            int n = arrc[i];
            if (n < 128) {
                outputStream.write(n);
                continue;
            }
            if (n < 2048) {
                outputStream.write(n >> 6 | 192);
                outputStream.write(128 | n & 63);
                continue;
            }
            if (n >= 55296 && n <= 57343) {
                if (i + 1 < arrc.length) {
                    char c = arrc[++i];
                    if (n <= 56319) {
                        n = ((n & 1023) << 10 | c & 1023) + 65536;
                        outputStream.write(n >> 18 | 240);
                        outputStream.write(n >> 12 & 63 | 128);
                        outputStream.write(n >> 6 & 63 | 128);
                        outputStream.write(128 | n & 63);
                        continue;
                    }
                    throw new IllegalStateException("invalid UTF-16 codepoint");
                }
                throw new IllegalStateException("invalid UTF-16 codepoint");
            }
            outputStream.write(n >> 12 | 224);
            outputStream.write(n >> 6 & 63 | 128);
            outputStream.write(128 | n & 63);
        }
    }

    public static byte[] toUTF8ByteArray(String string) {
        return Strings.toUTF8ByteArray(string.toCharArray());
    }

    public static byte[] toUTF8ByteArray(char[] arrc) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Strings.toUTF8ByteArray(arrc, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            throw new IllegalStateException("cannot encode string to byte array!");
        }
    }

    public static String toUpperCase(String string) {
        boolean bl = false;
        char[] arrc = string.toCharArray();
        for (int i = 0; i != arrc.length; ++i) {
            char c = arrc[i];
            boolean bl2 = bl;
            if ('a' <= c) {
                bl2 = bl;
                if ('z' >= c) {
                    bl2 = true;
                    arrc[i] = (char)(c - 97 + 65);
                }
            }
            bl = bl2;
        }
        if (bl) {
            return new String(arrc);
        }
        return string;
    }

    private static class StringListImpl
    extends ArrayList<String>
    implements StringList {
        private StringListImpl() {
        }

        @Override
        public void add(int n, String string) {
            super.add(n, string);
        }

        @Override
        public boolean add(String string) {
            return super.add(string);
        }

        @Override
        public String set(int n, String string) {
            return super.set(n, string);
        }

        @Override
        public String[] toStringArray() {
            String[] arrstring = new String[this.size()];
            for (int i = 0; i != arrstring.length; ++i) {
                arrstring[i] = (String)this.get(i);
            }
            return arrstring;
        }

        @Override
        public String[] toStringArray(int n, int n2) {
            String[] arrstring = new String[n2 - n];
            for (int i = n; i != this.size() && i != n2; ++i) {
                arrstring[i - n] = (String)this.get(i);
            }
            return arrstring;
        }
    }

}

