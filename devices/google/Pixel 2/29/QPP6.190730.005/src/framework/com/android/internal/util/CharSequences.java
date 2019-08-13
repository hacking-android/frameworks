/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;

public class CharSequences {
    @UnsupportedAppUsage
    public static int compareToIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        int n = charSequence.length();
        int n2 = charSequence2.length();
        int n3 = 0;
        int n4 = 0;
        int n5 = n < n2 ? n : n2;
        while (n3 < n5) {
            int n6 = Character.toLowerCase(charSequence.charAt(n3)) - Character.toLowerCase(charSequence2.charAt(n4));
            if (n6 != 0) {
                return n6;
            }
            ++n3;
            ++n4;
        }
        return n - n2;
    }

    @UnsupportedAppUsage
    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence.length() != charSequence2.length()) {
            return false;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
            return false;
        }
        return true;
    }

    public static CharSequence forAsciiBytes(byte[] arrby) {
        return new CharSequence(){

            @Override
            public char charAt(int n) {
                return (char)val$bytes[n];
            }

            @Override
            public int length() {
                return val$bytes.length;
            }

            @Override
            public CharSequence subSequence(int n, int n2) {
                return CharSequences.forAsciiBytes(val$bytes, n, n2);
            }

            @Override
            public String toString() {
                return new String(val$bytes);
            }
        };
    }

    public static CharSequence forAsciiBytes(byte[] arrby, final int n, final int n2) {
        CharSequences.validate(n, n2, arrby.length);
        return new CharSequence(){

            @Override
            public char charAt(int n3) {
                return (char)val$bytes[n + n3];
            }

            @Override
            public int length() {
                return n2 - n;
            }

            @Override
            public CharSequence subSequence(int n4, int n22) {
                int n3 = n;
                CharSequences.validate(n4 -= n3, n22 -= n3, this.length());
                return CharSequences.forAsciiBytes(val$bytes, n4, n22);
            }

            @Override
            public String toString() {
                return new String(val$bytes, n, this.length());
            }
        };
    }

    static void validate(int n, int n2, int n3) {
        if (n >= 0) {
            if (n2 >= 0) {
                if (n2 <= n3) {
                    if (n <= n2) {
                        return;
                    }
                    throw new IndexOutOfBoundsException();
                }
                throw new IndexOutOfBoundsException();
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IndexOutOfBoundsException();
    }

}

