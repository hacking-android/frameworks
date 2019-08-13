/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.RbnfLenientScanner;
import android.icu.util.ULocale;

@Deprecated
public interface RbnfLenientScannerProvider {
    @Deprecated
    public RbnfLenientScanner get(ULocale var1, String var2);
}

