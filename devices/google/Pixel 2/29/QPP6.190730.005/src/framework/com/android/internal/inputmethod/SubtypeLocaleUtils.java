/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import android.text.TextUtils;
import java.util.Locale;

public class SubtypeLocaleUtils {
    public static Locale constructLocaleFromString(String arrstring) {
        if (TextUtils.isEmpty((CharSequence)arrstring)) {
            return null;
        }
        if ((arrstring = arrstring.split("_", 3)).length >= 1 && "tl".equals(arrstring[0])) {
            arrstring[0] = "fil";
        }
        if (arrstring.length == 1) {
            return new Locale(arrstring[0]);
        }
        if (arrstring.length == 2) {
            return new Locale(arrstring[0], arrstring[1]);
        }
        if (arrstring.length == 3) {
            return new Locale(arrstring[0], arrstring[1], arrstring[2]);
        }
        return null;
    }
}

