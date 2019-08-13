/*
 * Decompiled with CFR 0.145.
 */
package android.text;

public class Hyphenator {
    private Hyphenator() {
    }

    public static void init() {
        Hyphenator.nInit();
    }

    private static native void nInit();
}

