/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CacheBase;
import android.icu.impl.ClassLoaderUtil;
import android.icu.impl.ICUBinary;
import android.icu.impl.ICUConfig;
import android.icu.impl.ICUData;
import android.icu.impl.ICUDebug;
import android.icu.impl.ICUResourceBundleImpl;
import android.icu.impl.ICUResourceBundleReader;
import android.icu.impl.SoftCache;
import android.icu.impl.URLHandler;
import android.icu.impl.UResource;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import android.icu.util.UResourceTypeMismatchException;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class ICUResourceBundle
extends UResourceBundle {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ALIAS = 3;
    public static final int ARRAY16 = 9;
    private static CacheBase<String, ICUResourceBundle, Loader> BUNDLE_CACHE;
    private static final boolean DEBUG;
    private static final String DEFAULT_TAG = "default";
    private static final String FULL_LOCALE_NAMES_LIST = "fullLocaleNames.lst";
    private static CacheBase<String, AvailEntry, ClassLoader> GET_AVAILABLE_CACHE;
    private static final char HYPHEN = '-';
    private static final String ICUDATA = "ICUDATA";
    public static final ClassLoader ICU_DATA_CLASS_LOADER;
    private static final String ICU_RESOURCE_INDEX = "res_index";
    protected static final String INSTALLED_LOCALES = "InstalledLocales";
    private static final String LOCALE = "LOCALE";
    public static final String NO_INHERITANCE_MARKER = "\u2205\u2205\u2205";
    public static final int RES_BOGUS = -1;
    private static final char RES_PATH_SEP_CHAR = '/';
    private static final String RES_PATH_SEP_STR = "/";
    public static final int STRING_V2 = 6;
    public static final int TABLE16 = 5;
    public static final int TABLE32 = 4;
    private ICUResourceBundle container;
    protected String key;
    WholeBundle wholeBundle;

    static {
        ICU_DATA_CLASS_LOADER = ClassLoaderUtil.getClassLoader(ICUData.class);
        BUNDLE_CACHE = new SoftCache<String, ICUResourceBundle, Loader>(){

            @Override
            protected ICUResourceBundle createInstance(String string, Loader loader) {
                return loader.load();
            }
        };
        DEBUG = ICUDebug.enabled("localedata");
        GET_AVAILABLE_CACHE = new SoftCache<String, AvailEntry, ClassLoader>(){

            @Override
            protected AvailEntry createInstance(String string, ClassLoader classLoader) {
                return new AvailEntry(string, classLoader);
            }
        };
    }

    protected ICUResourceBundle(WholeBundle wholeBundle) {
        this.wholeBundle = wholeBundle;
    }

    protected ICUResourceBundle(ICUResourceBundle iCUResourceBundle, String string) {
        this.key = string;
        this.wholeBundle = iCUResourceBundle.wholeBundle;
        this.container = iCUResourceBundle;
        this.parent = iCUResourceBundle.parent;
    }

    private static final void addBundleBaseNamesFromClassLoader(final String string, final ClassLoader classLoader, final Set<String> set) {
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Void run() {
                try {
                    Enumeration<URL> enumeration = classLoader.getResources(string);
                    if (enumeration == null) {
                        return null;
                    }
                    URLHandler.URLVisitor uRLVisitor = new URLHandler.URLVisitor(){

                        @Override
                        public void visit(String string) {
                            if (string.endsWith(".res")) {
                                string = string.substring(0, string.length() - 4);
                                set.add(string);
                            }
                        }
                    };
                    while (enumeration.hasMoreElements()) {
                        URL uRL = enumeration.nextElement();
                        Object object = URLHandler.get(uRL);
                        if (object != null) {
                            ((URLHandler)object).guide(uRLVisitor, false);
                            continue;
                        }
                        if (!DEBUG) continue;
                        PrintStream printStream = System.out;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("handler for ");
                        ((StringBuilder)object).append(uRL);
                        ((StringBuilder)object).append(" is null");
                        printStream.println(((StringBuilder)object).toString());
                    }
                    return null;
                }
                catch (IOException iOException) {
                    if (!DEBUG) return null;
                    PrintStream printStream = System.out;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("ouch: ");
                    stringBuilder.append(iOException.getMessage());
                    printStream.println(stringBuilder.toString());
                }
                return null;
            }

        });
    }

    private static final void addLocaleIDsFromIndexBundle(String object, ClassLoader object2, Set<String> object3) {
        try {
            object2 = (ICUResourceBundle)((ICUResourceBundle)UResourceBundle.instantiateBundle((String)object, ICU_RESOURCE_INDEX, (ClassLoader)object2, true)).get(INSTALLED_LOCALES);
        }
        catch (MissingResourceException missingResourceException) {
            if (DEBUG) {
                PrintStream printStream = System.out;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("couldn't find ");
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append('/');
                ((StringBuilder)object3).append(ICU_RESOURCE_INDEX);
                ((StringBuilder)object3).append(".res");
                printStream.println(((StringBuilder)object3).toString());
                Thread.dumpStack();
            }
            return;
        }
        object = ((UResourceBundle)object2).getIterator();
        ((UResourceBundleIterator)object).reset();
        while (((UResourceBundleIterator)object).hasNext()) {
            object3.add(((UResourceBundleIterator)object).next().getKey());
        }
        return;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void addLocaleIDsFromListFile(String object, ClassLoader object2, Set<String> set) {
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append((String)object);
        ((StringBuilder)object3).append(FULL_LOCALE_NAMES_LIST);
        object3 = ((ClassLoader)object2).getResourceAsStream(((StringBuilder)object3).toString());
        if (object3 == null) return;
        object2 = new InputStreamReader((InputStream)object3, "ASCII");
        object = new BufferedReader((Reader)object2);
        while ((object2 = ((BufferedReader)object).readLine()) != null) {
            if (((String)object2).length() == 0 || ((String)object2).startsWith("#")) continue;
            set.add((String)object2);
        }
        {
            catch (Throwable throwable) {
                ((BufferedReader)object).close();
                throw throwable;
            }
        }
        try {
            ((BufferedReader)object).close();
            return;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private static int countPathKeys(String string) {
        if (string.isEmpty()) {
            return 0;
        }
        int n = 1;
        for (int i = 0; i < string.length(); ++i) {
            int n2 = n;
            if (string.charAt(i) == '/') {
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    public static ICUResourceBundle createBundle(String string, String string2, ClassLoader classLoader) {
        ICUResourceBundleReader iCUResourceBundleReader = ICUResourceBundleReader.getReader(string, string2, classLoader);
        if (iCUResourceBundleReader == null) {
            return null;
        }
        return ICUResourceBundle.getBundle(iCUResourceBundleReader, string, string2, classLoader);
    }

    private static Set<String> createFullLocaleNameSet(String string, ClassLoader classLoader) {
        String string2;
        Object object;
        Object object2;
        if (string.endsWith(RES_PATH_SEP_STR)) {
            string2 = string;
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string);
            ((StringBuilder)object2).append(RES_PATH_SEP_STR);
            string2 = ((StringBuilder)object2).toString();
        }
        HashSet<String> hashSet = new HashSet<String>();
        if (!ICUConfig.get("android.icu.impl.ICUResourceBundle.skipRuntimeLocaleResourceScan", "false").equalsIgnoreCase("true")) {
            ICUResourceBundle.addBundleBaseNamesFromClassLoader(string2, classLoader, hashSet);
            if (string.startsWith("android/icu/impl/data/icudt63b") && (object2 = string.length() == "android/icu/impl/data/icudt63b".length() ? "" : (string.charAt("android/icu/impl/data/icudt63b".length()) == '/' ? string.substring("android/icu/impl/data/icudt63b".length() + 1) : null)) != null) {
                ICUBinary.addBaseNamesInFileFolder((String)object2, ".res", hashSet);
            }
            hashSet.remove(ICU_RESOURCE_INDEX);
            object = hashSet.iterator();
            while (object.hasNext()) {
                object2 = (String)object.next();
                if (((String)object2).length() != 1 && ((String)object2).length() <= 3 || ((String)object2).indexOf(95) >= 0) continue;
                object.remove();
            }
        }
        if (hashSet.isEmpty()) {
            if (DEBUG) {
                object2 = System.out;
                object = new StringBuilder();
                ((StringBuilder)object).append("unable to enumerate data files in ");
                ((StringBuilder)object).append(string);
                ((PrintStream)object2).println(((StringBuilder)object).toString());
            }
            ICUResourceBundle.addLocaleIDsFromListFile(string2, classLoader, hashSet);
        }
        if (hashSet.isEmpty()) {
            ICUResourceBundle.addLocaleIDsFromIndexBundle(string, classLoader, hashSet);
        }
        hashSet.remove("root");
        hashSet.add(ULocale.ROOT.toString());
        return Collections.unmodifiableSet(hashSet);
    }

    private static Set<String> createLocaleNameSet(String string, ClassLoader classLoader) {
        HashSet<String> hashSet = new HashSet<String>();
        ICUResourceBundle.addLocaleIDsFromIndexBundle(string, classLoader, hashSet);
        return Collections.unmodifiableSet(hashSet);
    }

    private static final ULocale[] createULocaleList(String arruLocale, ClassLoader object) {
        object = (ICUResourceBundle)((ICUResourceBundle)UResourceBundle.instantiateBundle((String)arruLocale, ICU_RESOURCE_INDEX, (ClassLoader)object, true)).get(INSTALLED_LOCALES);
        int n = ((UResourceBundle)object).getSize();
        int n2 = 0;
        arruLocale = new ULocale[n];
        UResourceBundleIterator uResourceBundleIterator = ((UResourceBundle)object).getIterator();
        uResourceBundleIterator.reset();
        while (uResourceBundleIterator.hasNext()) {
            object = uResourceBundleIterator.next().getKey();
            if (((String)object).equals("root")) {
                arruLocale[n2] = ULocale.ROOT;
                ++n2;
                continue;
            }
            arruLocale[n2] = new ULocale((String)object);
            ++n2;
        }
        return arruLocale;
    }

    private static final ICUResourceBundle findResourceWithFallback(String string, UResourceBundle uResourceBundle, UResourceBundle uResourceBundle2) {
        if (string.length() == 0) {
            return null;
        }
        uResourceBundle = (ICUResourceBundle)uResourceBundle;
        int n = ICUResourceBundle.super.getResDepth();
        int n2 = ICUResourceBundle.countPathKeys(string);
        String[] arrstring = new String[n + n2];
        ICUResourceBundle.getResPathKeys(string, n2, arrstring, n);
        return ICUResourceBundle.findResourceWithFallback(arrstring, n, (ICUResourceBundle)uResourceBundle, uResourceBundle2);
    }

    private static final ICUResourceBundle findResourceWithFallback(String[] object, int n, ICUResourceBundle object2, UResourceBundle uResourceBundle) {
        Object object3 = object;
        int n2 = n;
        Object object4 = object2;
        UResourceBundle uResourceBundle2 = uResourceBundle;
        if (uResourceBundle == null) {
            uResourceBundle2 = object2;
            object4 = object2;
            n2 = n;
            object3 = object;
        }
        do {
            n = n2 + 1;
            object = (ICUResourceBundle)((UResourceBundle)object4).handleGet(object3[n2], null, uResourceBundle2);
            if (object == null) {
                --n;
                object2 = ((ICUResourceBundle)object4).getParent();
                if (object2 == null) {
                    return null;
                }
                n2 = ICUResourceBundle.super.getResDepth();
                object = object3;
                if (n != n2) {
                    object = new String[((String[])object3).length - n + n2];
                    System.arraycopy(object3, n, object, n2, ((String[])object3).length - n);
                }
                ICUResourceBundle.super.getResPathKeys((String[])object, n2);
                object4 = object2;
                n2 = 0;
                object3 = object;
                continue;
            }
            if (n == ((String[])object3).length) {
                return object;
            }
            object4 = object;
            n2 = n;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static final String findStringWithFallback(String var0, UResourceBundle var1_1, UResourceBundle var2_2) {
        if (var0.length() == 0) {
            return null;
        }
        if (!(var1_1 /* !! */  instanceof ICUResourceBundleImpl.ResourceContainer)) {
            return null;
        }
        var3_10 /* !! */  = var2_9 == null ? var1_1 /* !! */  : var2_9;
        var2_9 = (ICUResourceBundle)var1_1 /* !! */ ;
        var1_2 = var2_9.wholeBundle.reader;
        var4_11 = -1;
        var6_13 = var5_12 = var2_9.getResDepth();
        var7_14 = ICUResourceBundle.countPathKeys((String)var0);
        var8_15 = new String[var6_13 + var7_14];
        ICUResourceBundle.getResPathKeys((String)var0, var7_14, var8_15, var6_13);
        var0 = var2_9;
        do {
            block16 : {
                block17 : {
                    block15 : {
                        if (var4_11 != -1) break block15;
                        var7_14 = var0.getType();
                        if (var7_14 != 2 && var7_14 != 8) break block16;
                        var2_9 = ((ICUResourceBundleImpl.ResourceContainer)var0).value;
                        ** GOTO lbl28
                    }
                    var7_14 = ICUResourceBundleReader.RES_GET_TYPE(var4_11);
                    if (!ICUResourceBundleReader.URES_IS_TABLE(var7_14)) break block17;
                    var2_9 = var1_3.getTable(var4_11);
                    ** GOTO lbl28
                }
                if (ICUResourceBundleReader.URES_IS_ARRAY(var7_14)) {
                    var2_9 = var1_3.getArray(var4_11);
lbl28: // 3 sources:
                    var7_14 = var6_13 + 1;
                    var9_16 = var8_15[var6_13];
                    var4_11 = var2_9.getResource((ICUResourceBundleReader)var1_3, var9_16);
                    if (var4_11 != -1) {
                        if (ICUResourceBundleReader.RES_GET_TYPE(var4_11) == 3) {
                            ICUResourceBundle.super.getResPathKeys(var8_15, var5_12);
                            var2_9 = ICUResourceBundle.getAliasedResource((ICUResourceBundle)var0, var8_15, var7_14, var9_16, var4_11, null, (UResourceBundle)var3_10 /* !! */ );
                        } else {
                            var2_9 = null;
                        }
                        var6_13 = var7_14;
                        if (var6_13 == var8_15.length) {
                            if (var2_9 != null) {
                                return var2_9.getString();
                            }
                            var0 = var1_3.getString(var4_11);
                            if (var0 == null) throw new UResourceTypeMismatchException("");
                            return var0;
                        }
                        if (var2_9 != null) {
                            var0 = var2_9.wholeBundle.reader;
                            var5_12 = ICUResourceBundle.super.getResDepth();
                            if (var6_13 != var5_12) {
                                var1_4 = new String[var8_15.length - var6_13 + var5_12];
                                System.arraycopy(var8_15, var6_13, var1_4, var5_12, var8_15.length - var6_13);
                                var8_15 = var1_4;
                                var4_11 = var5_12;
                                var1_5 = var0;
                                var7_14 = var5_12;
                                var6_13 = -1;
                                var5_12 = var4_11;
                                var0 = var2_9;
                            } else {
                                var4_11 = var6_13;
                                var1_6 = var0;
                                var7_14 = var5_12;
                                var6_13 = -1;
                                var5_12 = var4_11;
                                var0 = var2_9;
                            }
                        } else {
                            var10_17 = var6_13;
                            var6_13 = var4_11;
                            var7_14 = var5_12;
                            var5_12 = var10_17;
                        }
                        var4_11 = var6_13;
                        var6_13 = var5_12;
                        var5_12 = var7_14;
                        continue;
                    }
                } else {
                    var4_11 = -1;
                }
            }
            if ((var1_7 = var0.getParent()) == null) {
                return null;
            }
            ICUResourceBundle.super.getResPathKeys(var8_15, var5_12);
            var0 = var1_7;
            var1_8 = var0.wholeBundle.reader;
            var5_12 = 0;
            var6_13 = 0;
        } while (true);
    }

    protected static ICUResourceBundle getAliasedResource(ICUResourceBundle object, String[] object2, int n, String string, int n2, HashMap<String, String> object3, UResourceBundle uResourceBundle) {
        String string2;
        WholeBundle wholeBundle = ((ICUResourceBundle)object).wholeBundle;
        Object object4 = wholeBundle.loader;
        Object object5 = null;
        Object object6 = null;
        Object object7 = object3 == null ? new HashMap() : object3;
        if (((HashMap)object7).get(string2 = wholeBundle.reader.getAlias(n2)) == null) {
            Object object8;
            ((HashMap)object7).put((String)string2, (String)"");
            if (string2.indexOf(47) == 0) {
                int n3 = string2.indexOf(47, 1);
                n2 = string2.indexOf(47, n3 + 1);
                object3 = string2.substring(1, n3);
                if (n2 < 0) {
                    object5 = string2.substring(n3 + 1);
                } else {
                    object5 = string2.substring(n3 + 1, n2);
                    object6 = string2.substring(n2 + 1, string2.length());
                }
                if (((String)object3).equals(ICUDATA)) {
                    object3 = "android/icu/impl/data/icudt63b";
                    object8 = ICU_DATA_CLASS_LOADER;
                } else if (((String)object3).indexOf(ICUDATA) > -1 && (n2 = ((String)object3).indexOf(45)) > -1) {
                    object8 = new StringBuilder();
                    ((StringBuilder)object8).append("android/icu/impl/data/icudt63b/");
                    ((StringBuilder)object8).append(((String)object3).substring(n2 + 1, ((String)object3).length()));
                    object3 = ((StringBuilder)object8).toString();
                    object8 = ICU_DATA_CLASS_LOADER;
                } else {
                    object8 = object4;
                }
                object4 = object3;
            } else {
                n2 = string2.indexOf(47);
                if (n2 != -1) {
                    object8 = string2.substring(0, n2);
                    object3 = string2.substring(n2 + 1);
                } else {
                    object8 = string2;
                    object3 = object5;
                }
                object6 = wholeBundle.baseName;
                ClassLoader classLoader = object4;
                object5 = object8;
                object4 = object6;
                object6 = object3;
                object8 = classLoader;
            }
            object3 = null;
            if (((String)object4).equals(LOCALE)) {
                object = wholeBundle.baseName;
                object2 = string2.substring(LOCALE.length() + 2, string2.length());
                object = (ICUResourceBundle)uResourceBundle;
                while (((ICUResourceBundle)object).container != null) {
                    object = ((ICUResourceBundle)object).container;
                }
                object2 = ICUResourceBundle.findResourceWithFallback((String)object2, (UResourceBundle)object, null);
            } else {
                object5 = ICUResourceBundle.getBundleInstance((String)object4, (String)object5, (ClassLoader)object8, false);
                if (object6 != null) {
                    n = ICUResourceBundle.countPathKeys((String)object6);
                    if (n > 0) {
                        object = new String[n];
                        ICUResourceBundle.getResPathKeys((String)object6, n, (String[])object, 0);
                    } else {
                        object = object2;
                    }
                } else if (object2 != null) {
                    object = object2;
                } else {
                    n2 = ICUResourceBundle.super.getResDepth();
                    n = n2 + 1;
                    object2 = new String[n];
                    ICUResourceBundle.super.getResPathKeys((String[])object2, n2);
                    object2[n2] = string;
                    object = object2;
                }
                if (n > 0) {
                    object2 = object5;
                    for (n2 = 0; object2 != null && n2 < n; object2 = object2.get((String)object[n2], object7, (UResourceBundle)uResourceBundle), ++n2) {
                    }
                } else {
                    object2 = object3;
                }
            }
            if (object2 != null) {
                return object2;
            }
            throw new MissingResourceException(wholeBundle.localeID, wholeBundle.baseName, string);
        }
        throw new IllegalArgumentException("Circular references in the resource bundles");
    }

    private void getAllItemsWithFallback(UResource.Key key, ICUResourceBundleReader.ReaderValue readerValue, UResource.Sink sink) {
        Object object = (ICUResourceBundleImpl)this;
        readerValue.reader = object.wholeBundle.reader;
        readerValue.res = ((ICUResourceBundleImpl)object).getResource();
        object = this.key;
        if (object == null) {
            object = "";
        }
        key.setString((String)object);
        boolean bl = this.parent == null;
        sink.put(key, readerValue, bl);
        if (this.parent != null) {
            object = (ICUResourceBundle)this.parent;
            int n = this.getResDepth();
            if (n != 0) {
                String[] arrstring = new String[n];
                this.getResPathKeys(arrstring, n);
                object = ICUResourceBundle.findResourceWithFallback(arrstring, 0, (ICUResourceBundle)object, null);
            }
            if (object != null) {
                ICUResourceBundle.super.getAllItemsWithFallback(key, readerValue, sink);
            }
        }
    }

    private static AvailEntry getAvailEntry(String string, ClassLoader classLoader) {
        return GET_AVAILABLE_CACHE.getInstance(string, classLoader);
    }

    public static Set<String> getAvailableLocaleNameSet() {
        return ICUResourceBundle.getAvailableLocaleNameSet("android/icu/impl/data/icudt63b", ICU_DATA_CLASS_LOADER);
    }

    public static Set<String> getAvailableLocaleNameSet(String string, ClassLoader classLoader) {
        return ICUResourceBundle.getAvailEntry(string, classLoader).getLocaleNameSet();
    }

    public static final Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailEntry("android/icu/impl/data/icudt63b", ICU_DATA_CLASS_LOADER).getLocaleList();
    }

    public static final Locale[] getAvailableLocales(String string, ClassLoader classLoader) {
        return ICUResourceBundle.getAvailEntry(string, classLoader).getLocaleList();
    }

    public static final ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales("android/icu/impl/data/icudt63b", ICU_DATA_CLASS_LOADER);
    }

    public static final ULocale[] getAvailableULocales(String string, ClassLoader classLoader) {
        return ICUResourceBundle.getAvailEntry(string, classLoader).getULocaleList();
    }

    private static ICUResourceBundle getBundle(ICUResourceBundleReader object, String string, String string2, ClassLoader classLoader) {
        int n = ((ICUResourceBundleReader)object).getRootResource();
        if (ICUResourceBundleReader.URES_IS_TABLE(ICUResourceBundleReader.RES_GET_TYPE(n))) {
            object = new ICUResourceBundleImpl.ResourceTable(new WholeBundle(string, string2, classLoader, (ICUResourceBundleReader)object), n);
            string2 = ((ICUResourceBundleImpl.ResourceTable)object).findString("%%ALIAS");
            if (string2 != null) {
                return (ICUResourceBundle)UResourceBundle.getBundleInstance(string, string2);
            }
            return object;
        }
        throw new IllegalStateException("Invalid format error");
    }

    public static ICUResourceBundle getBundleInstance(String string, ULocale uLocale, OpenType openType) {
        ULocale uLocale2 = uLocale;
        if (uLocale == null) {
            uLocale2 = ULocale.getDefault();
        }
        return ICUResourceBundle.getBundleInstance(string, uLocale2.getBaseName(), ICU_DATA_CLASS_LOADER, openType);
    }

    public static ICUResourceBundle getBundleInstance(String object, String string, ClassLoader classLoader, OpenType openType) {
        String string2 = object;
        if (object == null) {
            string2 = "android/icu/impl/data/icudt63b";
        }
        string = ULocale.getBaseName(string);
        object = openType == OpenType.LOCALE_DEFAULT_ROOT ? ICUResourceBundle.instantiateBundle(string2, string, ULocale.getDefault().getBaseName(), classLoader, openType) : ICUResourceBundle.instantiateBundle(string2, string, null, classLoader, openType);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not find the bundle ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(RES_PATH_SEP_STR);
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(".res");
        throw new MissingResourceException(((StringBuilder)object).toString(), "", "");
    }

    public static ICUResourceBundle getBundleInstance(String string, String string2, ClassLoader classLoader, boolean bl) {
        OpenType openType = bl ? OpenType.DIRECT : OpenType.LOCALE_DEFAULT_ROOT;
        return ICUResourceBundle.getBundleInstance(string, string2, classLoader, openType);
    }

    public static Set<String> getFullLocaleNameSet() {
        return ICUResourceBundle.getFullLocaleNameSet("android/icu/impl/data/icudt63b", ICU_DATA_CLASS_LOADER);
    }

    public static Set<String> getFullLocaleNameSet(String string, ClassLoader classLoader) {
        return ICUResourceBundle.getAvailEntry(string, classLoader).getFullLocaleNameSet();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static final ULocale getFunctionalEquivalent(String charSequence, ClassLoader object, String arrbl, String string, ULocale object2, boolean[] object3, boolean bl) {
        Object object5;
        Object object4;
        int n;
        Object object6;
        int n2;
        block37 : {
            int n4;
            Object object8;
            Object object7;
            Object object9;
            String string3;
            String string2;
            int n3;
            block36 : {
                int n5;
                int n6;
                ULocale uLocale;
                block35 : {
                    block34 : {
                        object6 = arrbl;
                        object4 = ((ULocale)object2).getKeywordValue(string);
                        string2 = ((ULocale)object2).getBaseName();
                        string3 = null;
                        uLocale = new ULocale(string2);
                        object9 = null;
                        n4 = 0;
                        object5 = null;
                        n6 = 0;
                        n5 = 0;
                        if (object4 == null || ((String)object4).length() == 0) break block34;
                        object2 = object4;
                        if (!((String)object4).equals(DEFAULT_TAG)) break block35;
                    }
                    object2 = "";
                    n4 = 1;
                }
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance((String)charSequence, uLocale);
                if (object3 == null) {
                    object = object2;
                    object4 = iCUResourceBundle;
                    n2 = n6;
                    n = n4;
                    object8 = object9;
                    object2 = string3;
                } else {
                    object3[0] = false;
                    ULocale[] arruLocale = ICUResourceBundle.getAvailEntry((String)charSequence, (ClassLoader)object).getULocaleList();
                    object7 = object2;
                    n3 = 0;
                    do {
                        object2 = string3;
                        object8 = object9;
                        n = n4;
                        n2 = n6;
                        object4 = iCUResourceBundle;
                        object = object7;
                        if (n3 >= arruLocale.length) break;
                        if (uLocale.equals(arruLocale[n3])) {
                            object3[0] = true;
                            object2 = string3;
                            object8 = object9;
                            n = n4;
                            n2 = n6;
                            object4 = iCUResourceBundle;
                            object = object7;
                            break;
                        }
                        ++n3;
                    } while (true);
                }
                do {
                    block32 : {
                        block33 : {
                            block31 : {
                                object2 = object3 = ((ICUResourceBundle)((UResourceBundle)object4).get((String)object6)).getString(DEFAULT_TAG);
                                n4 = n;
                                if (n != true) break block31;
                                object = object2;
                                n4 = 0;
                            }
                            try {
                                object8 = object3 = ((ICUResourceBundle)object4).getULocale();
                                object3 = object;
                                object = object2;
                                n = n4;
                                break block32;
                            }
                            catch (MissingResourceException missingResourceException) {
                                n = n4;
                                break block33;
                            }
                            catch (MissingResourceException missingResourceException) {
                                // empty catch block
                            }
                        }
                        object3 = object;
                        object = object2;
                    }
                    n3 = n2;
                    object7 = object4;
                    if (object8 == null) {
                        object7 = ((ICUResourceBundle)object4).getParent();
                        n3 = n2 + 1;
                    }
                    if (object7 == null || object8 != null) break;
                    object2 = object;
                    n2 = n3;
                    object4 = object7;
                    object = object3;
                } while (true);
                object7 = (ICUResourceBundle)UResourceBundle.getBundleInstance((String)charSequence, new ULocale(string2));
                n4 = n5;
                object8 = object5;
                object4 = object;
                do {
                    object = object4;
                    object2 = object8;
                    try {
                        object5 = (ICUResourceBundle)((UResourceBundle)object7).get((String)object6);
                        object = object4;
                        object2 = object8;
                        ((UResourceBundle)object5).get((String)object3);
                        object = object4;
                        object2 = object8;
                        object8 = ((ICUResourceBundle)object5).getULocale();
                        object = object4;
                        n = n3;
                        if (object8 != null) {
                            object = object4;
                            n = n3;
                            if (n4 > n3) {
                                object = object4;
                                object2 = object8;
                                object = object4 = ((ResourceBundle)object5).getString(DEFAULT_TAG);
                                object2 = object8;
                                ((ICUResourceBundle)object7).getULocale();
                                n = n4;
                                object = object4;
                            }
                        }
                        object2 = object8;
                    }
                    catch (MissingResourceException missingResourceException) {
                        n = n3;
                    }
                    object4 = object7;
                    n2 = n4;
                    if (object2 == null) {
                        object4 = ((ICUResourceBundle)object7).getParent();
                        n2 = n4 + 1;
                    }
                    if (object4 == null) break;
                    object7 = object4;
                    object4 = object;
                    object8 = object2;
                    n3 = n;
                    n4 = n2;
                } while (object2 == null);
                if (object2 == null && object != null && !((String)object).equals(object3)) break block36;
                object5 = object;
                object4 = object2;
                object6 = object3;
                break block37;
            }
            object8 = object;
            object7 = new ULocale(string2);
            object6 = (ICUResourceBundle)UResourceBundle.getBundleInstance((String)charSequence, (ULocale)object7);
            n3 = 0;
            n4 = n;
            object4 = object2;
            n = n3;
            do {
                block38 : {
                    object3 = object4;
                    object5 = (ICUResourceBundle)((UResourceBundle)object6).get((String)arrbl);
                    object3 = object4;
                    object2 = (ICUResourceBundle)((UResourceBundle)object5).get((String)object8);
                    object3 = object4;
                    object3 = object4 = ((ICUResourceBundle)object6).getULocale();
                    string3 = ((ULocale)object4).getBaseName();
                    object3 = object4;
                    object9 = ((ICUResourceBundle)object2).getULocale();
                    object2 = object4;
                    object3 = object;
                    try {
                        if (!string3.equals(((ULocale)object9).getBaseName())) {
                            object2 = null;
                        }
                        n3 = n4;
                        object3 = object;
                        if (object2 != null) {
                            n3 = n4;
                            object3 = object;
                            if (n > n4) {
                                object4 = object2;
                                object3 = object;
                                object = ((ResourceBundle)object5).getString(DEFAULT_TAG);
                                object4 = object2;
                                object3 = object;
                                ((ICUResourceBundle)object6).getULocale();
                                n3 = n;
                                object3 = object;
                            }
                        }
                        object = object2;
                        n4 = n3;
                        object2 = object3;
                    }
                    catch (MissingResourceException missingResourceException) {
                        object = object4;
                        object2 = object3;
                    }
                    break block38;
                    catch (MissingResourceException missingResourceException) {
                        object2 = object;
                        object = object3;
                    }
                }
                object3 = object6;
                n3 = n;
                if (object == null) {
                    object3 = ((ICUResourceBundle)object6).getParent();
                    n3 = n + 1;
                }
                object6 = object8;
                n2 = n3;
                object4 = object;
                n = n4;
                object5 = object2;
                if (object3 == null) break;
                if (object != null) {
                    object6 = object8;
                    n2 = n3;
                    object4 = object;
                    n = n4;
                    object5 = object2;
                    break;
                }
                object6 = object3;
                n = n3;
                object4 = object;
                object = object2;
            } while (true);
        }
        if (object4 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("=");
            ((StringBuilder)object).append((String)object6);
            throw new MissingResourceException("Could not find locale containing requested or default keyword.", (String)charSequence, ((StringBuilder)object).toString());
        }
        if (bl && ((String)object5).equals(object6) && n2 <= n) {
            return object4;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(((ULocale)object4).getBaseName());
        ((StringBuilder)charSequence).append("@");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append("=");
        ((StringBuilder)charSequence).append((String)object6);
        return new ULocale(((StringBuilder)charSequence).toString());
    }

    public static final String[] getKeywordValues(String string, String string2) {
        HashSet<String> hashSet = new HashSet<String>();
        ULocale[] arruLocale = ICUResourceBundle.getAvailEntry(string, ICU_DATA_CLASS_LOADER).getULocaleList();
        for (int i = 0; i < arruLocale.length; ++i) {
            try {
                Enumeration<String> enumeration = ((ICUResourceBundle)UResourceBundle.getBundleInstance(string, arruLocale[i]).getObject(string2)).getKeys();
                while (enumeration.hasMoreElements()) {
                    String string3 = enumeration.nextElement();
                    if (DEFAULT_TAG.equals(string3) || string3.startsWith("private-")) continue;
                    hashSet.add(string3);
                }
                continue;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return hashSet.toArray(new String[0]);
    }

    public static final Locale[] getLocaleList(ULocale[] arruLocale) {
        ArrayList<Locale> arrayList = new ArrayList<Locale>(arruLocale.length);
        HashSet<Locale> hashSet = new HashSet<Locale>();
        for (int i = 0; i < arruLocale.length; ++i) {
            Locale locale = arruLocale[i].toLocale();
            if (hashSet.contains(locale)) continue;
            arrayList.add(locale);
            hashSet.add(locale);
        }
        return arrayList.toArray(new Locale[arrayList.size()]);
    }

    private boolean getNoFallback() {
        return this.wholeBundle.reader.getNoFallback();
    }

    private int getResDepth() {
        ICUResourceBundle iCUResourceBundle = this.container;
        int n = iCUResourceBundle == null ? 0 : iCUResourceBundle.getResDepth() + 1;
        return n;
    }

    private static void getResPathKeys(String string, int n, String[] arrstring, int n2) {
        if (n == 0) {
            return;
        }
        if (n == 1) {
            arrstring[n2] = string;
            return;
        }
        int n3 = 0;
        int n4 = n2;
        n2 = n3;
        do {
            int n5 = string.indexOf(47, n2);
            n3 = n4 + 1;
            arrstring[n4] = string.substring(n2, n5);
            if (n == 2) {
                arrstring[n3] = string.substring(n5 + 1);
                return;
            }
            n2 = n5 + 1;
            --n;
            n4 = n3;
        } while (true);
    }

    private void getResPathKeys(String[] arrstring, int n) {
        ICUResourceBundle iCUResourceBundle = this;
        while (n > 0) {
            arrstring[--n] = iCUResourceBundle.key;
            iCUResourceBundle = iCUResourceBundle.container;
        }
    }

    private static ICUResourceBundle instantiateBundle(final String string, final String string2, final String string3, final ClassLoader classLoader, final OpenType openType) {
        CharSequence charSequence;
        final String string4 = ICUResourceBundleReader.getFullName(string, string2);
        char c = (char)(openType.ordinal() + 48);
        if (openType != OpenType.LOCALE_DEFAULT_ROOT) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string4);
            ((StringBuilder)charSequence).append('#');
            ((StringBuilder)charSequence).append(c);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string4);
            ((StringBuilder)charSequence).append('#');
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append('#');
            ((StringBuilder)charSequence).append(string3);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return BUNDLE_CACHE.getInstance((String)charSequence, new Loader(){

            @Override
            public ICUResourceBundle load() {
                Object object;
                CharSequence charSequence;
                Object object2;
                Object object3;
                if (DEBUG) {
                    object2 = System.out;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Creating ");
                    ((StringBuilder)object3).append(string4);
                    ((PrintStream)object2).println(((StringBuilder)object3).toString());
                }
                object2 = string.indexOf(46) == -1 ? "root" : "";
                object3 = string2.isEmpty() ? object2 : string2;
                ICUResourceBundle iCUResourceBundle = ICUResourceBundle.createBundle(string, (String)object3, classLoader);
                if (DEBUG) {
                    object = System.out;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("The bundle created is: ");
                    ((StringBuilder)charSequence).append(iCUResourceBundle);
                    ((StringBuilder)charSequence).append(" and openType=");
                    ((StringBuilder)charSequence).append((Object)openType);
                    ((StringBuilder)charSequence).append(" and bundle.getNoFallback=");
                    boolean bl = iCUResourceBundle != null && iCUResourceBundle.getNoFallback();
                    ((StringBuilder)charSequence).append(bl);
                    ((PrintStream)object).println(((StringBuilder)charSequence).toString());
                }
                if (!(openType == OpenType.DIRECT || iCUResourceBundle != null && iCUResourceBundle.getNoFallback())) {
                    if (iCUResourceBundle == null) {
                        int n = ((String)object3).lastIndexOf(95);
                        if (n != -1) {
                            object3 = ((String)object3).substring(0, n);
                            object3 = ICUResourceBundle.instantiateBundle(string, (String)object3, string3, classLoader, openType);
                        } else if (openType == OpenType.LOCALE_DEFAULT_ROOT && !ICUResourceBundle.localeIDStartsWithLangSubtag(string3, (String)object3)) {
                            object3 = string;
                            object2 = string3;
                            object3 = ICUResourceBundle.instantiateBundle((String)object3, (String)object2, (String)object2, classLoader, openType);
                        } else {
                            object3 = iCUResourceBundle;
                            if (openType != OpenType.LOCALE_ONLY) {
                                object3 = iCUResourceBundle;
                                if (!((String)object2).isEmpty()) {
                                    object3 = ICUResourceBundle.createBundle(string, (String)object2, classLoader);
                                }
                            }
                        }
                        object2 = object3;
                    } else {
                        object3 = null;
                        object = iCUResourceBundle.getLocaleID();
                        int n = ((String)object).lastIndexOf(95);
                        charSequence = ((ICUResourceBundleImpl.ResourceTable)iCUResourceBundle).findString("%%Parent");
                        if (charSequence != null) {
                            object3 = ICUResourceBundle.instantiateBundle(string, (String)charSequence, string3, classLoader, openType);
                        } else if (n != -1) {
                            object3 = ICUResourceBundle.instantiateBundle(string, ((String)object).substring(0, n), string3, classLoader, openType);
                        } else if (!((String)object).equals(object2)) {
                            object3 = ICUResourceBundle.instantiateBundle(string, (String)object2, string3, classLoader, openType);
                        }
                        object2 = iCUResourceBundle;
                        if (!iCUResourceBundle.equals(object3)) {
                            iCUResourceBundle.setParent((ResourceBundle)object3);
                            object2 = iCUResourceBundle;
                        }
                    }
                    return object2;
                }
                return iCUResourceBundle;
            }
        });
    }

    private static boolean localeIDStartsWithLangSubtag(String string, String string2) {
        boolean bl = string.startsWith(string2) && (string.length() == string2.length() || string.charAt(string2.length()) == '_');
        return bl;
    }

    public ICUResourceBundle at(int n) {
        return (ICUResourceBundle)this.handleGet(n, null, (UResourceBundle)this);
    }

    public ICUResourceBundle at(String string) {
        if (this instanceof ICUResourceBundleImpl.ResourceTable) {
            return (ICUResourceBundle)this.handleGet(string, null, (UResourceBundle)this);
        }
        return null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof ICUResourceBundle) {
            object = (ICUResourceBundle)object;
            if (this.getBaseName().equals(((ICUResourceBundle)object).getBaseName()) && this.getLocaleID().equals(((ICUResourceBundle)object).getLocaleID())) {
                return true;
            }
        }
        return false;
    }

    public String findStringWithFallback(String string) {
        return ICUResourceBundle.findStringWithFallback(string, this, null);
    }

    @Override
    public ICUResourceBundle findTopLevel(int n) {
        return (ICUResourceBundle)super.findTopLevel(n);
    }

    @Override
    public ICUResourceBundle findTopLevel(String string) {
        return (ICUResourceBundle)super.findTopLevel(string);
    }

    public ICUResourceBundle findWithFallback(String string) {
        return ICUResourceBundle.findResourceWithFallback(string, this, null);
    }

    ICUResourceBundle get(String string, HashMap<String, String> object, UResourceBundle object2) {
        ICUResourceBundle iCUResourceBundle;
        ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle = (ICUResourceBundle)this.handleGet(string, (HashMap<String, String>)object, (UResourceBundle)object2);
        if (iCUResourceBundle == null) {
            iCUResourceBundle2 = iCUResourceBundle = this.getParent();
            if (iCUResourceBundle != null) {
                iCUResourceBundle2 = iCUResourceBundle.get(string, (HashMap<String, String>)object, (UResourceBundle)object2);
            }
            if (iCUResourceBundle2 == null) {
                object = ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID());
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Can't find resource for bundle ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(", key ");
                ((StringBuilder)object2).append(string);
                throw new MissingResourceException(((StringBuilder)object2).toString(), this.getClass().getName(), string);
            }
        }
        return iCUResourceBundle2;
    }

    public void getAllItemsWithFallback(String object, UResource.Sink object2) throws MissingResourceException {
        block4 : {
            block3 : {
                int n;
                block2 : {
                    n = ICUResourceBundle.countPathKeys((String)object);
                    if (n != 0) break block2;
                    object = this;
                    break block3;
                }
                int n2 = this.getResDepth();
                Object object3 = new String[n2 + n];
                ICUResourceBundle.getResPathKeys((String)object, n, object3, n2);
                object3 = ICUResourceBundle.findResourceWithFallback(object3, n2, this, null);
                if (object3 == null) break block4;
                object = object3;
            }
            ICUResourceBundle.super.getAllItemsWithFallback(new UResource.Key(), new ICUResourceBundleReader.ReaderValue(), (UResource.Sink)object2);
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Can't find resource for bundle ");
        ((StringBuilder)object2).append(this.getClass().getName());
        ((StringBuilder)object2).append(", key ");
        ((StringBuilder)object2).append(this.getType());
        throw new MissingResourceException(((StringBuilder)object2).toString(), (String)object, this.getKey());
    }

    public void getAllItemsWithFallbackNoFail(String string, UResource.Sink sink) {
        try {
            this.getAllItemsWithFallback(string, sink);
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
    }

    @Override
    protected String getBaseName() {
        return this.wholeBundle.baseName;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Locale getLocale() {
        return this.getULocale().toLocale();
    }

    @Override
    protected String getLocaleID() {
        return this.wholeBundle.localeID;
    }

    @Override
    public ICUResourceBundle getParent() {
        return (ICUResourceBundle)this.parent;
    }

    public String getStringWithFallback(String string) throws MissingResourceException {
        CharSequence charSequence = ICUResourceBundle.findStringWithFallback(string, this, null);
        if (charSequence != null) {
            if (!((String)charSequence).equals(NO_INHERITANCE_MARKER)) {
                return charSequence;
            }
            throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", string, this.getKey());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Can't find resource for bundle ");
        ((StringBuilder)charSequence).append(this.getClass().getName());
        ((StringBuilder)charSequence).append(", key ");
        ((StringBuilder)charSequence).append(this.getType());
        throw new MissingResourceException(((StringBuilder)charSequence).toString(), string, this.getKey());
    }

    @Deprecated
    public final Set<String> getTopLevelKeySet() {
        return this.wholeBundle.topLevelKeys;
    }

    @UnsupportedAppUsage
    @Override
    public ULocale getULocale() {
        return this.wholeBundle.ulocale;
    }

    @UnsupportedAppUsage
    public ICUResourceBundle getWithFallback(String string) throws MissingResourceException {
        Object object = ICUResourceBundle.findResourceWithFallback(string, this, null);
        if (object != null) {
            if (((UResourceBundle)object).getType() == 0 && ((UResourceBundle)object).getString().equals(NO_INHERITANCE_MARKER)) {
                throw new MissingResourceException("Encountered NO_INHERITANCE_MARKER", string, this.getKey());
            }
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Can't find resource for bundle ");
        ((StringBuilder)object).append(this.getClass().getName());
        ((StringBuilder)object).append(", key ");
        ((StringBuilder)object).append(this.getType());
        throw new MissingResourceException(((StringBuilder)object).toString(), string, this.getKey());
    }

    @Override
    protected Enumeration<String> handleGetKeys() {
        return Collections.enumeration(this.handleKeySet());
    }

    public int hashCode() {
        return 42;
    }

    public boolean isRoot() {
        boolean bl = this.wholeBundle.localeID.isEmpty() || this.wholeBundle.localeID.equals("root");
        return bl;
    }

    @Override
    protected boolean isTopLevelResource() {
        boolean bl = this.container == null;
        return bl;
    }

    @Override
    protected void setParent(ResourceBundle resourceBundle) {
        this.parent = resourceBundle;
    }

    @Deprecated
    public final void setTopLevelKeySet(Set<String> set) {
        this.wholeBundle.topLevelKeys = set;
    }

    private static final class AvailEntry {
        private volatile Set<String> fullNameSet;
        private ClassLoader loader;
        private volatile Locale[] locales;
        private volatile Set<String> nameSet;
        private String prefix;
        private volatile ULocale[] ulocales;

        AvailEntry(String string, ClassLoader classLoader) {
            this.prefix = string;
            this.loader = classLoader;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        Set<String> getFullLocaleNameSet() {
            if (this.fullNameSet != null) return this.fullNameSet;
            synchronized (this) {
                if (this.fullNameSet != null) return this.fullNameSet;
                this.fullNameSet = ICUResourceBundle.createFullLocaleNameSet(this.prefix, this.loader);
                return this.fullNameSet;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        Locale[] getLocaleList() {
            if (this.locales != null) return this.locales;
            this.getULocaleList();
            synchronized (this) {
                if (this.locales != null) return this.locales;
                this.locales = ICUResourceBundle.getLocaleList(this.ulocales);
                return this.locales;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        Set<String> getLocaleNameSet() {
            if (this.nameSet != null) return this.nameSet;
            synchronized (this) {
                if (this.nameSet != null) return this.nameSet;
                this.nameSet = ICUResourceBundle.createLocaleNameSet(this.prefix, this.loader);
                return this.nameSet;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        ULocale[] getULocaleList() {
            if (this.ulocales != null) return this.ulocales;
            synchronized (this) {
                if (this.ulocales != null) return this.ulocales;
                this.ulocales = ICUResourceBundle.createULocaleList(this.prefix, this.loader);
                return this.ulocales;
            }
        }
    }

    private static abstract class Loader {
        private Loader() {
        }

        abstract ICUResourceBundle load();
    }

    public static enum OpenType {
        LOCALE_DEFAULT_ROOT,
        LOCALE_ROOT,
        LOCALE_ONLY,
        DIRECT;
        
    }

    protected static final class WholeBundle {
        String baseName;
        ClassLoader loader;
        String localeID;
        ICUResourceBundleReader reader;
        Set<String> topLevelKeys;
        ULocale ulocale;

        WholeBundle(String string, String string2, ClassLoader classLoader, ICUResourceBundleReader iCUResourceBundleReader) {
            this.baseName = string;
            this.localeID = string2;
            this.ulocale = new ULocale(string2);
            this.loader = classLoader;
            this.reader = iCUResourceBundleReader;
        }
    }

}

