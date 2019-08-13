/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.util.UResourceBundle;
import android.icu.util.VersionInfo;
import java.util.MissingResourceException;

public final class ICUDataVersion {
    private static final String U_ICU_DATA_KEY = "DataVersion";
    private static final String U_ICU_VERSION_BUNDLE = "icuver";

    public static VersionInfo getDataVersion() {
        UResourceBundle uResourceBundle;
        try {
            uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", U_ICU_VERSION_BUNDLE, ICUResourceBundle.ICU_DATA_CLASS_LOADER).get(U_ICU_DATA_KEY);
        }
        catch (MissingResourceException missingResourceException) {
            return null;
        }
        return VersionInfo.getInstance(uResourceBundle.getString());
    }
}

