/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

public interface UForwardCharacterIterator {
    public static final int DONE = -1;

    public int next();

    public int nextCodePoint();
}

