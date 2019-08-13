/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.text.method.ReplacementTransformationMethod;

public class SingleLineTransformationMethod
extends ReplacementTransformationMethod {
    private static char[] ORIGINAL = new char[]{'\n', '\r'};
    private static char[] REPLACEMENT = new char[]{' ', '\ufffffeff'};
    private static SingleLineTransformationMethod sInstance;

    public static SingleLineTransformationMethod getInstance() {
        SingleLineTransformationMethod singleLineTransformationMethod = sInstance;
        if (singleLineTransformationMethod != null) {
            return singleLineTransformationMethod;
        }
        sInstance = new SingleLineTransformationMethod();
        return sInstance;
    }

    @Override
    protected char[] getOriginal() {
        return ORIGINAL;
    }

    @Override
    protected char[] getReplacement() {
        return REPLACEMENT;
    }
}

