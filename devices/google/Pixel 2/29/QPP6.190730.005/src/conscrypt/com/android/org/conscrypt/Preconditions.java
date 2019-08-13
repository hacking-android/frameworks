/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

final class Preconditions {
    private Preconditions() {
    }

    private static String badPositionIndex(int n, int n2, String charSequence) {
        if (n < 0) {
            return String.format("%s (%s) must not be negative", charSequence, n);
        }
        if (n2 >= 0) {
            return String.format("%s (%s) must not be greater than size (%s)", charSequence, n, n2);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("negative size: ");
        ((StringBuilder)charSequence).append(n2);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static String badPositionIndexes(int n, int n2, int n3) {
        if (n >= 0 && n <= n3) {
            if (n2 >= 0 && n2 <= n3) {
                return String.format("end index (%s) must not be less than start index (%s)", n2, n);
            }
            return Preconditions.badPositionIndex(n2, n3, "end index");
        }
        return Preconditions.badPositionIndex(n, n3, "start index");
    }

    static void checkArgument(boolean bl, String string) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException(string);
    }

    static void checkArgument(boolean bl, String string, Object object) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException(String.format(string, object));
    }

    static <T> T checkNotNull(T t, String string) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(string);
    }

    static void checkPositionIndexes(int n, int n2, int n3) {
        if (n >= 0 && n2 >= n && n2 <= n3) {
            return;
        }
        throw new IndexOutOfBoundsException(Preconditions.badPositionIndexes(n, n2, n3));
    }
}

