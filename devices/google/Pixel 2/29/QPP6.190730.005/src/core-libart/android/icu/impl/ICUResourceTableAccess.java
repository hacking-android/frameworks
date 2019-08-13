/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.LocaleIDs;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;

public class ICUResourceTableAccess {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getTableString(ICUResourceBundle object, String string, String string2, String string3, String string4) {
        Object object2 = null;
        Object iCUResourceBundle = object;
        do {
            object = object2;
            try {
                ICUResourceBundle iCUResourceBundle2 = ((ICUResourceBundle)iCUResourceBundle).findWithFallback(string);
                if (iCUResourceBundle2 == null) {
                    return string4;
                }
                Object object3 = iCUResourceBundle2;
                if (string2 != null) {
                    object = object2;
                    object3 = iCUResourceBundle2.findWithFallback(string2);
                }
                Object object4 = object2;
                if (object3 != null) {
                    object = object2;
                    object4 = object2 = ((ICUResourceBundle)object3).findStringWithFallback(string3);
                    if (object2 != null) {
                        object = object2;
                        break;
                    }
                }
                object2 = object4;
                if (string2 == null) {
                    object3 = null;
                    object = object4;
                    if (string.equals("Countries")) {
                        object = object4;
                        object3 = LocaleIDs.getCurrentCountryID(string3);
                    } else {
                        object = object4;
                        if (string.equals("Languages")) {
                            object = object4;
                            object3 = LocaleIDs.getCurrentLanguageID(string3);
                        }
                    }
                    object2 = object4;
                    if (object3 != null) {
                        object = object4;
                        object2 = object4 = iCUResourceBundle2.findStringWithFallback((String)object3);
                        if (object4 != null) {
                            object = object4;
                            break;
                        }
                    }
                }
                object = object2;
                object3 = iCUResourceBundle2.findStringWithFallback("Fallback");
                if (object3 == null) {
                    return string4;
                }
                object4 = object3;
                object = object2;
                if (((String)object3).length() == 0) {
                    object4 = "root";
                }
                object = object2;
                if (((String)object4).equals(iCUResourceBundle2.getULocale().getName())) {
                    return string4;
                }
                object = object2;
                iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(((ICUResourceBundle)iCUResourceBundle).getBaseName(), (String)object4);
            }
            catch (Exception exception) {
                // empty catch block
                break;
            }
        } while (true);
        if (object == null) return string4;
        if (((String)object).length() <= 0) return string4;
        return object;
    }

    public static String getTableString(String string, ULocale uLocale, String string2, String string3, String string4) {
        return ICUResourceTableAccess.getTableString((ICUResourceBundle)UResourceBundle.getBundleInstance(string, uLocale.getBaseName()), string2, null, string3, string4);
    }
}

