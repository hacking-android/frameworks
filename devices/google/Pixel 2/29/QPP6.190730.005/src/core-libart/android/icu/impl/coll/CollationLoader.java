/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.coll.CollationDataReader;
import android.icu.impl.coll.CollationRoot;
import android.icu.impl.coll.CollationSettings;
import android.icu.impl.coll.CollationTailoring;
import android.icu.impl.coll.SharedObject;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.Output;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class CollationLoader {
    private static volatile String rootRules = null;

    private CollationLoader() {
    }

    private static final UResourceBundle findWithFallback(UResourceBundle uResourceBundle, String string) {
        return ((ICUResourceBundle)uResourceBundle).findWithFallback(string);
    }

    public static String getRootRules() {
        CollationLoader.loadRootRules();
        return rootRules;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void loadRootRules() {
        if (rootRules != null) {
            return;
        }
        synchronized (CollationLoader.class) {
            if (rootRules == null) {
                rootRules = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/coll", ULocale.ROOT).getString("UCARules");
            }
            return;
        }
    }

    static String loadRules(ULocale object, String string) {
        object = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/coll", (ULocale)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("collations/");
        stringBuilder.append(ASCII.toLowerCase(string));
        return ((ICUResourceBundle)object).getWithFallback(stringBuilder.toString()).getString("Sequence");
    }

    public static CollationTailoring loadTailoring(ULocale object, Output<ULocale> object2) {
        CollationTailoring collationTailoring = CollationRoot.getRoot();
        Object object3 = ((ULocale)object).getName();
        if (((String)object3).length() != 0 && !((String)object3).equals("root")) {
            Object object4;
            ULocale uLocale;
            Object object5;
            block25 : {
                try {
                    object3 = ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/coll", (ULocale)object, ICUResourceBundle.OpenType.LOCALE_ROOT);
                }
                catch (MissingResourceException missingResourceException) {
                    ((Output)object2).value = ULocale.ROOT;
                    return collationTailoring;
                }
                uLocale = ((UResourceBundle)object3).getULocale();
                object4 = uLocale.getName();
                if (((String)object4).length() == 0 || ((String)object4).equals("root")) {
                    uLocale = ULocale.ROOT;
                }
                ((Output)object2).value = uLocale;
                try {
                    object5 = ((UResourceBundle)object3).get("collations");
                    if (object5 != null) break block25;
                    return collationTailoring;
                }
                catch (MissingResourceException missingResourceException) {
                    return collationTailoring;
                }
            }
            object4 = ((ULocale)object).getKeywordValue("collation");
            object = "standard";
            object3 = ((ICUResourceBundle)object5).findStringWithFallback("default");
            if (object3 != null) {
                object = object3;
            }
            object4 = object4 != null && !((String)object4).equals("default") ? ASCII.toLowerCase((String)object4) : object;
            Object object6 = CollationLoader.findWithFallback((UResourceBundle)object5, (String)object4);
            Object object7 = object4;
            object3 = object6;
            if (object6 == null) {
                object7 = object4;
                object3 = object6;
                if (((String)object4).length() > 6) {
                    object7 = object4;
                    object3 = object6;
                    if (((String)object4).startsWith("search")) {
                        object7 = "search";
                        object3 = CollationLoader.findWithFallback((UResourceBundle)object5, "search");
                    }
                }
            }
            object6 = object7;
            object4 = object3;
            if (object3 == null) {
                object6 = object7;
                object4 = object3;
                if (!((String)object7).equals(object)) {
                    object6 = object;
                    object4 = CollationLoader.findWithFallback((UResourceBundle)object5, (String)object6);
                }
            }
            object3 = object6;
            object7 = object4;
            if (object4 == null) {
                object3 = object6;
                object7 = object4;
                if (!((String)object6).equals("standard")) {
                    object3 = "standard";
                    object7 = CollationLoader.findWithFallback((UResourceBundle)object5, "standard");
                }
            }
            if (object7 == null) {
                return collationTailoring;
            }
            object4 = ((UResourceBundle)object7).getULocale();
            object6 = ((ULocale)object4).getName();
            if (((String)object6).length() == 0 || ((String)object6).equals("root")) {
                object4 = ULocale.ROOT;
                if (((String)object3).equals("standard")) {
                    return collationTailoring;
                }
            }
            object6 = new CollationTailoring(collationTailoring.settings);
            ((CollationTailoring)object6).actualLocale = object4;
            object5 = ((UResourceBundle)object7).get("%%CollationBin").getBinary();
            try {
                CollationDataReader.read(collationTailoring, (ByteBuffer)object5, (CollationTailoring)object6);
            }
            catch (IOException iOException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Failed to load collation tailoring data for locale:");
                ((StringBuilder)object2).append(object4);
                ((StringBuilder)object2).append(" type:");
                ((StringBuilder)object2).append((String)object3);
                throw new ICUUncheckedIOException(((StringBuilder)object2).toString(), iOException);
            }
            try {
                ((CollationTailoring)object6).setRulesResource(((UResourceBundle)object7).get("Sequence"));
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            if (!((String)object3).equals(object)) {
                ((Output)object2).value = uLocale.setKeywordValue("collation", (String)object3);
            }
            object2 = object;
            if (!((ULocale)object4).equals(uLocale)) {
                object4 = ((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/coll", (ULocale)object4)).findStringWithFallback("collations/default");
                object2 = object;
                if (object4 != null) {
                    object2 = object4;
                }
            }
            if (!((String)object3).equals(object2)) {
                ((CollationTailoring)object6).actualLocale = ((CollationTailoring)object6).actualLocale.setKeywordValue("collation", (String)object3);
            }
            return object6;
        }
        ((Output)object2).value = ULocale.ROOT;
        return collationTailoring;
    }

    private static final class ASCII {
        private ASCII() {
        }

        static String toLowerCase(String string) {
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                if ('A' > c || c > 'Z') continue;
                StringBuilder stringBuilder = new StringBuilder(string.length());
                stringBuilder.append(string, 0, i);
                stringBuilder.append((char)(c + 32));
                while (++i < string.length()) {
                    char c2 = c = string.charAt(i);
                    if ('A' <= c) {
                        c2 = c;
                        if (c <= 'Z') {
                            c2 = c = (char)(c + 32);
                        }
                    }
                    stringBuilder.append(c2);
                }
                return stringBuilder.toString();
            }
            return string;
        }
    }

}

