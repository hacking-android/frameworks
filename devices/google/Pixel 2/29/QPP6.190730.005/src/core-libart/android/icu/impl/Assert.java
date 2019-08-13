/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public class Assert {
    public static void assrt(String string, boolean bl) {
        if (bl) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("assert '");
        stringBuilder.append(string);
        stringBuilder.append("' failed");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public static void assrt(boolean bl) {
        if (bl) {
            return;
        }
        throw new IllegalStateException("assert failed");
    }

    public static void fail(Exception exception) {
        Assert.fail(exception.toString());
    }

    public static void fail(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("failure '");
        stringBuilder.append(string);
        stringBuilder.append("'");
        throw new IllegalStateException(stringBuilder.toString());
    }
}

