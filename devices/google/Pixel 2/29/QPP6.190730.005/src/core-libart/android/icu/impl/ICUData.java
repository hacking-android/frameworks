/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;
import java.util.logging.Logger;

public final class ICUData {
    public static final String ICU_BASE_NAME = "android/icu/impl/data/icudt63b";
    public static final String ICU_BRKITR_BASE_NAME = "android/icu/impl/data/icudt63b/brkitr";
    public static final String ICU_BRKITR_NAME = "brkitr";
    public static final String ICU_BUNDLE = "data/icudt63b";
    public static final String ICU_COLLATION_BASE_NAME = "android/icu/impl/data/icudt63b/coll";
    public static final String ICU_CURR_BASE_NAME = "android/icu/impl/data/icudt63b/curr";
    static final String ICU_DATA_PATH = "android/icu/impl/";
    public static final String ICU_LANG_BASE_NAME = "android/icu/impl/data/icudt63b/lang";
    public static final String ICU_RBNF_BASE_NAME = "android/icu/impl/data/icudt63b/rbnf";
    public static final String ICU_REGION_BASE_NAME = "android/icu/impl/data/icudt63b/region";
    public static final String ICU_TRANSLIT_BASE_NAME = "android/icu/impl/data/icudt63b/translit";
    public static final String ICU_UNIT_BASE_NAME = "android/icu/impl/data/icudt63b/unit";
    public static final String ICU_ZONE_BASE_NAME = "android/icu/impl/data/icudt63b/zone";
    static final String PACKAGE_NAME = "icudt63b";
    private static final boolean logBinaryDataFromInputStream = false;
    private static final Logger logger = null;

    private static void checkStreamForBinaryData(InputStream inputStream, String string) {
    }

    public static boolean exists(String object) {
        object = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<URL>((String)object){
            final /* synthetic */ String val$resourceName;
            {
                this.val$resourceName = string;
            }

            @Override
            public URL run() {
                return ICUData.class.getResource(this.val$resourceName);
            }
        }) : ICUData.class.getResource((String)object);
        boolean bl = object != null;
        return bl;
    }

    public static InputStream getRequiredStream(Class<?> class_, String string) {
        return ICUData.getStream(class_, string, true);
    }

    public static InputStream getRequiredStream(ClassLoader classLoader, String string) {
        return ICUData.getStream(classLoader, string, true);
    }

    public static InputStream getRequiredStream(String string) {
        return ICUData.getStream(ICUData.class, string, true);
    }

    public static InputStream getStream(Class<?> class_, String string) {
        return ICUData.getStream(class_, string, false);
    }

    private static InputStream getStream(final Class<?> class_, final String string, boolean bl) {
        Object object = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<InputStream>(){

            @Override
            public InputStream run() {
                return class_.getResourceAsStream(string);
            }
        }) : class_.getResourceAsStream(string);
        if (object == null && bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("could not locate data ");
            ((StringBuilder)object).append(string);
            throw new MissingResourceException(((StringBuilder)object).toString(), class_.getPackage().getName(), string);
        }
        ICUData.checkStreamForBinaryData((InputStream)object, string);
        return object;
    }

    public static InputStream getStream(ClassLoader classLoader, String string) {
        return ICUData.getStream(classLoader, string, false);
    }

    static InputStream getStream(final ClassLoader classLoader, final String string, boolean bl) {
        InputStream inputStream = System.getSecurityManager() != null ? AccessController.doPrivileged(new PrivilegedAction<InputStream>(){

            @Override
            public InputStream run() {
                return classLoader.getResourceAsStream(string);
            }
        }) : classLoader.getResourceAsStream(string);
        if (inputStream == null && bl) {
            throw new MissingResourceException("could not locate data", classLoader.toString(), string);
        }
        ICUData.checkStreamForBinaryData(inputStream, string);
        return inputStream;
    }

    public static InputStream getStream(String string) {
        return ICUData.getStream(ICUData.class, string, false);
    }

}

