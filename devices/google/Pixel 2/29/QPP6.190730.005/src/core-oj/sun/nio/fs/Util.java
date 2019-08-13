/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.nio.charset.Charset;
import java.nio.file.LinkOption;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class Util {
    private static final Charset jnuEncoding = Charset.forName("UTF-8");

    private Util() {
    }

    static boolean followLinks(LinkOption ... arrlinkOption) {
        boolean bl = true;
        for (LinkOption linkOption : arrlinkOption) {
            if (linkOption == LinkOption.NOFOLLOW_LINKS) {
                bl = false;
                continue;
            }
            if (linkOption == null) {
                throw new NullPointerException();
            }
            throw new AssertionError((Object)"Should not get here");
        }
        return bl;
    }

    static Charset jnuEncoding() {
        return jnuEncoding;
    }

    @SafeVarargs
    static <E> Set<E> newSet(Set<E> set, E ... arrE) {
        set = new HashSet<E>(set);
        int n = arrE.length;
        for (int i = 0; i < n; ++i) {
            ((HashSet)set).add(arrE[i]);
        }
        return set;
    }

    @SafeVarargs
    static <E> Set<E> newSet(E ... arrE) {
        HashSet<E> hashSet = new HashSet<E>();
        int n = arrE.length;
        for (int i = 0; i < n; ++i) {
            hashSet.add(arrE[i]);
        }
        return hashSet;
    }

    static String[] split(String string, char c) {
        int n;
        int n2;
        int n3 = 0;
        for (n2 = 0; n2 < string.length(); ++n2) {
            n = n3;
            if (string.charAt(n2) == c) {
                n = n3 + 1;
            }
            n3 = n;
        }
        String[] arrstring = new String[n3 + 1];
        n = 0;
        int n4 = 0;
        for (n2 = 0; n2 < string.length(); ++n2) {
            int n5 = n;
            n3 = n4;
            if (string.charAt(n2) == c) {
                arrstring[n] = string.substring(n4, n2);
                n3 = n2 + 1;
                n5 = n + 1;
            }
            n = n5;
            n4 = n3;
        }
        arrstring[n] = string.substring(n4, string.length());
        return arrstring;
    }

    static byte[] toBytes(String string) {
        return string.getBytes(jnuEncoding);
    }

    static String toString(byte[] arrby) {
        return new String(arrby, jnuEncoding);
    }
}

