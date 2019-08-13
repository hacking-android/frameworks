/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUResourceBundleReader;
import android.icu.impl.ResourceBundleWrapper;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundleIterator;
import android.icu.util.UResourceTypeMismatchException;
import android.icu.util.VersionInfo;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public abstract class UResourceBundle
extends ResourceBundle {
    public static final int ARRAY = 8;
    public static final int BINARY = 1;
    public static final int INT = 7;
    public static final int INT_VECTOR = 14;
    public static final int NONE = -1;
    private static Map<String, RootType> ROOT_CACHE = new ConcurrentHashMap<String, RootType>();
    public static final int STRING = 0;
    public static final int TABLE = 2;

    public static UResourceBundle getBundleInstance(ULocale uLocale) {
        ULocale uLocale2 = uLocale;
        if (uLocale == null) {
            uLocale2 = ULocale.getDefault();
        }
        return UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale2.getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String string) {
        String string2 = string;
        if (string == null) {
            string2 = "android/icu/impl/data/icudt63b";
        }
        return UResourceBundle.getBundleInstance(string2, ULocale.getDefault().getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    @UnsupportedAppUsage
    public static UResourceBundle getBundleInstance(String object, ULocale uLocale) {
        String string = object;
        if (object == null) {
            string = "android/icu/impl/data/icudt63b";
        }
        object = uLocale;
        if (uLocale == null) {
            object = ULocale.getDefault();
        }
        return UResourceBundle.getBundleInstance(string, ((ULocale)object).getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String object, ULocale uLocale, ClassLoader classLoader) {
        String string = object;
        if (object == null) {
            string = "android/icu/impl/data/icudt63b";
        }
        object = uLocale;
        if (uLocale == null) {
            object = ULocale.getDefault();
        }
        return UResourceBundle.getBundleInstance(string, ((ULocale)object).getBaseName(), classLoader, false);
    }

    public static UResourceBundle getBundleInstance(String string, String string2) {
        return UResourceBundle.getBundleInstance(string, string2, ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String string, String string2, ClassLoader classLoader) {
        return UResourceBundle.getBundleInstance(string, string2, classLoader, false);
    }

    protected static UResourceBundle getBundleInstance(String string, String string2, ClassLoader classLoader, boolean bl) {
        return UResourceBundle.instantiateBundle(string, string2, classLoader, bl);
    }

    public static UResourceBundle getBundleInstance(String object, Locale locale) {
        String string = object;
        if (object == null) {
            string = "android/icu/impl/data/icudt63b";
        }
        object = locale == null ? ULocale.getDefault() : ULocale.forLocale(locale);
        return UResourceBundle.getBundleInstance(string, ((ULocale)object).getBaseName(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
    }

    public static UResourceBundle getBundleInstance(String object, Locale locale, ClassLoader classLoader) {
        String string = object;
        if (object == null) {
            string = "android/icu/impl/data/icudt63b";
        }
        object = locale == null ? ULocale.getDefault() : ULocale.forLocale(locale);
        return UResourceBundle.getBundleInstance(string, ((ULocale)object).getBaseName(), classLoader, false);
    }

    private static RootType getRootType(String string, ClassLoader object) {
        RootType rootType;
        Object object2 = rootType = ROOT_CACHE.get(string);
        if (rootType == null) {
            object2 = string.indexOf(46) == -1 ? "root" : "";
            try {
                ICUResourceBundle.getBundleInstance(string, (String)object2, object, true);
                rootType = RootType.ICU;
                object = rootType;
            }
            catch (MissingResourceException missingResourceException) {
                try {
                    ResourceBundleWrapper.getBundleInstance(string, (String)object2, object, true);
                    object = RootType.JAVA;
                }
                catch (MissingResourceException missingResourceException2) {
                    object = RootType.MISSING;
                }
            }
            ROOT_CACHE.put(string, (RootType)((Object)object));
            object2 = object;
        }
        return object2;
    }

    private Object handleGetObjectImpl(String string, UResourceBundle object) {
        Object object2;
        Object object3 = object2 = this.resolveObject(string, (UResourceBundle)object);
        if (object2 == null) {
            object3 = this.getParent();
            if (object3 != null) {
                object2 = UResourceBundle.super.handleGetObjectImpl(string, (UResourceBundle)object);
            }
            if (object2 != null) {
                object3 = object2;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Can't find resource for bundle ");
                ((StringBuilder)object).append(this.getClass().getName());
                ((StringBuilder)object).append(", key ");
                ((StringBuilder)object).append(string);
                throw new MissingResourceException(((StringBuilder)object).toString(), this.getClass().getName(), string);
            }
        }
        return object3;
    }

    protected static UResourceBundle instantiateBundle(String object, String object2, ClassLoader classLoader, boolean bl) {
        Object object3 = UResourceBundle.getRootType(object, classLoader);
        int n = 1.$SwitchMap$android$icu$util$UResourceBundle$RootType[object3.ordinal()];
        if (n != 1) {
            if (n != 2) {
                try {
                    object3 = ICUResourceBundle.getBundleInstance(object, (String)object2, classLoader, bl);
                    UResourceBundle.setRootType(object, RootType.ICU);
                    object = object3;
                }
                catch (MissingResourceException missingResourceException) {
                    object2 = ResourceBundleWrapper.getBundleInstance(object, (String)object2, classLoader, bl);
                    UResourceBundle.setRootType(object, RootType.JAVA);
                    object = object2;
                }
                return object;
            }
            return ResourceBundleWrapper.getBundleInstance(object, (String)object2, classLoader, bl);
        }
        return ICUResourceBundle.getBundleInstance(object, (String)object2, classLoader, bl);
    }

    private Object resolveObject(String object, UResourceBundle arrstring) {
        if (this.getType() == 0) {
            return this.getString();
        }
        if ((object = this.handleGet((String)object, null, (UResourceBundle)arrstring)) != null) {
            if (((UResourceBundle)object).getType() == 0) {
                return ((UResourceBundle)object).getString();
            }
            try {
                if (((UResourceBundle)object).getType() == 8) {
                    arrstring = ((UResourceBundle)object).handleGetStringArray();
                    return arrstring;
                }
            }
            catch (UResourceTypeMismatchException uResourceTypeMismatchException) {
                return object;
            }
        }
        return object;
    }

    private static void setRootType(String string, RootType rootType) {
        ROOT_CACHE.put(string, rootType);
    }

    @Deprecated
    protected UResourceBundle findTopLevel(int n) {
        for (UResourceBundle uResourceBundle = this; uResourceBundle != null; uResourceBundle = uResourceBundle.getParent()) {
            UResourceBundle uResourceBundle2 = uResourceBundle.handleGet(n, null, this);
            if (uResourceBundle2 == null) continue;
            return uResourceBundle2;
        }
        return null;
    }

    @Deprecated
    protected UResourceBundle findTopLevel(String string) {
        for (UResourceBundle uResourceBundle = this; uResourceBundle != null; uResourceBundle = uResourceBundle.getParent()) {
            UResourceBundle uResourceBundle2 = uResourceBundle.handleGet(string, null, this);
            if (uResourceBundle2 == null) continue;
            return uResourceBundle2;
        }
        return null;
    }

    public UResourceBundle get(int n) {
        UResourceBundle uResourceBundle = this.handleGet(n, null, this);
        Object object = uResourceBundle;
        if (uResourceBundle == null) {
            uResourceBundle = this.getParent();
            object = uResourceBundle;
            if (uResourceBundle != null) {
                object = uResourceBundle.get(n);
            }
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Can't find resource for bundle ");
                ((StringBuilder)object).append(this.getClass().getName());
                ((StringBuilder)object).append(", key ");
                ((StringBuilder)object).append(this.getKey());
                throw new MissingResourceException(((StringBuilder)object).toString(), this.getClass().getName(), this.getKey());
            }
        }
        return object;
    }

    public UResourceBundle get(String string) {
        Object object = this.findTopLevel(string);
        if (object != null) {
            return object;
        }
        object = ICUResourceBundleReader.getFullName(this.getBaseName(), this.getLocaleID());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't find resource for bundle ");
        stringBuilder.append((String)object);
        stringBuilder.append(", key ");
        stringBuilder.append(string);
        throw new MissingResourceException(stringBuilder.toString(), this.getClass().getName(), string);
    }

    protected abstract String getBaseName();

    public ByteBuffer getBinary() {
        throw new UResourceTypeMismatchException("");
    }

    public byte[] getBinary(byte[] arrby) {
        throw new UResourceTypeMismatchException("");
    }

    public int getInt() {
        throw new UResourceTypeMismatchException("");
    }

    public int[] getIntVector() {
        throw new UResourceTypeMismatchException("");
    }

    public UResourceBundleIterator getIterator() {
        return new UResourceBundleIterator(this);
    }

    @UnsupportedAppUsage
    public String getKey() {
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(this.keySet());
    }

    @Override
    public Locale getLocale() {
        return this.getULocale().toLocale();
    }

    protected abstract String getLocaleID();

    protected abstract UResourceBundle getParent();

    public int getSize() {
        return 1;
    }

    @UnsupportedAppUsage
    public String getString() {
        throw new UResourceTypeMismatchException("");
    }

    public String getString(int n) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)this.get(n);
        if (iCUResourceBundle.getType() == 0) {
            return iCUResourceBundle.getString();
        }
        throw new UResourceTypeMismatchException("");
    }

    public String[] getStringArray() {
        throw new UResourceTypeMismatchException("");
    }

    @UnsupportedAppUsage
    public int getType() {
        return -1;
    }

    public int getUInt() {
        throw new UResourceTypeMismatchException("");
    }

    public abstract ULocale getULocale();

    public VersionInfo getVersion() {
        return null;
    }

    protected UResourceBundle handleGet(int n, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
        return null;
    }

    protected UResourceBundle handleGet(String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
        return null;
    }

    protected Enumeration<String> handleGetKeys() {
        return null;
    }

    @Override
    protected Object handleGetObject(String string) {
        return this.handleGetObjectImpl(string, this);
    }

    protected String[] handleGetStringArray() {
        return null;
    }

    @Deprecated
    @Override
    protected Set<String> handleKeySet() {
        return Collections.emptySet();
    }

    @Deprecated
    protected boolean isTopLevelResource() {
        return true;
    }

    @Deprecated
    @Override
    public Set<String> keySet() {
        Object object = null;
        Set<String> set = null;
        Set<String> set2 = object;
        ICUResourceBundle iCUResourceBundle = set;
        if (this.isTopLevelResource()) {
            set2 = object;
            iCUResourceBundle = set;
            if (this instanceof ICUResourceBundle) {
                iCUResourceBundle = (ICUResourceBundle)this;
                set2 = iCUResourceBundle.getTopLevelKeySet();
            }
        }
        set = set2;
        if (set2 == null) {
            if (this.isTopLevelResource()) {
                if (this.parent == null) {
                    set2 = new TreeSet<String>();
                } else if (this.parent instanceof UResourceBundle) {
                    set2 = new TreeSet<String>(((UResourceBundle)this.parent).keySet());
                } else {
                    set = new TreeSet<E>();
                    object = this.parent.getKeys();
                    do {
                        set2 = set;
                        if (!object.hasMoreElements()) break;
                        ((TreeSet)set).add((String)object.nextElement());
                    } while (true);
                }
                set2.addAll(this.handleKeySet());
                set = Collections.unmodifiableSet(set2);
                if (iCUResourceBundle != null) {
                    iCUResourceBundle.setTopLevelKeySet(set);
                }
            } else {
                return this.handleKeySet();
            }
        }
        return set;
    }

    private static enum RootType {
        MISSING,
        ICU,
        JAVA;
        
    }

}

