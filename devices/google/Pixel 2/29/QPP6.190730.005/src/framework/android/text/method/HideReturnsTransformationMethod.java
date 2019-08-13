/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.annotation.UnsupportedAppUsage;
import android.text.method.ReplacementTransformationMethod;

public class HideReturnsTransformationMethod
extends ReplacementTransformationMethod {
    private static char[] ORIGINAL = new char[]{'\r'};
    private static char[] REPLACEMENT = new char[]{'\ufeff'};
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static HideReturnsTransformationMethod sInstance;

    public static HideReturnsTransformationMethod getInstance() {
        HideReturnsTransformationMethod hideReturnsTransformationMethod = sInstance;
        if (hideReturnsTransformationMethod != null) {
            return hideReturnsTransformationMethod;
        }
        sInstance = new HideReturnsTransformationMethod();
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

