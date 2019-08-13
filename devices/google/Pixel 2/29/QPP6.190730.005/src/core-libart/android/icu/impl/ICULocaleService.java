/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ClassLoaderUtil;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUService;
import android.icu.impl.LocaleUtility;
import android.icu.util.ULocale;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ICULocaleService
extends ICUService {
    private ULocale fallbackLocale;
    private String fallbackLocaleName;

    public ICULocaleService() {
    }

    public ICULocaleService(String string) {
        super(string);
    }

    public ICUService.Key createKey(ULocale uLocale, int n) {
        return LocaleKey.createWithCanonical(uLocale, this.validateFallbackLocale(), n);
    }

    @Override
    public ICUService.Key createKey(String string) {
        return LocaleKey.createWithCanonicalFallback(string, this.validateFallbackLocale());
    }

    public ICUService.Key createKey(String string, int n) {
        return LocaleKey.createWithCanonicalFallback(string, this.validateFallbackLocale(), n);
    }

    public Object get(ULocale uLocale) {
        return this.get(uLocale, -1, null);
    }

    public Object get(ULocale uLocale, int n) {
        return this.get(uLocale, n, null);
    }

    public Object get(ULocale arrstring, int n, ULocale[] arruLocale) {
        Object object = this.createKey((ULocale)arrstring, n);
        if (arruLocale == null) {
            return this.getKey((ICUService.Key)object);
        }
        arrstring = new String[1];
        if ((object = this.getKey((ICUService.Key)object, arrstring)) != null) {
            n = arrstring[0].indexOf("/");
            if (n >= 0) {
                arrstring[0] = arrstring[0].substring(n + 1);
            }
            arruLocale[0] = new ULocale(arrstring[0]);
        }
        return object;
    }

    public Object get(ULocale uLocale, ULocale[] arruLocale) {
        return this.get(uLocale, -1, arruLocale);
    }

    public Locale[] getAvailableLocales() {
        Object object = this.getVisibleIDs();
        Locale[] arrlocale = new Locale[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arrlocale[n] = LocaleUtility.getLocaleFromName((String)object.next());
            ++n;
        }
        return arrlocale;
    }

    public ULocale[] getAvailableULocales() {
        Object object = this.getVisibleIDs();
        ULocale[] arruLocale = new ULocale[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arruLocale[n] = new ULocale((String)object.next());
            ++n;
        }
        return arruLocale;
    }

    public ICUService.Factory registerObject(Object object, ULocale uLocale) {
        return this.registerObject(object, uLocale, -1, true);
    }

    public ICUService.Factory registerObject(Object object, ULocale uLocale, int n) {
        return this.registerObject(object, uLocale, n, true);
    }

    public ICUService.Factory registerObject(Object object, ULocale uLocale, int n, boolean bl) {
        return this.registerFactory(new SimpleLocaleKeyFactory(object, uLocale, n, bl));
    }

    public ICUService.Factory registerObject(Object object, ULocale uLocale, boolean bl) {
        return this.registerObject(object, uLocale, -1, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String validateFallbackLocale() {
        ULocale uLocale = ULocale.getDefault();
        if (uLocale == this.fallbackLocale) return this.fallbackLocaleName;
        synchronized (this) {
            if (uLocale == this.fallbackLocale) return this.fallbackLocaleName;
            this.fallbackLocale = uLocale;
            this.fallbackLocaleName = uLocale.getBaseName();
            this.clearServiceCache();
            return this.fallbackLocaleName;
        }
    }

    public static class ICUResourceBundleFactory
    extends LocaleKeyFactory {
        protected final String bundleName;

        public ICUResourceBundleFactory() {
            this("android/icu/impl/data/icudt63b");
        }

        public ICUResourceBundleFactory(String string) {
            super(true);
            this.bundleName = string;
        }

        @Override
        protected Set<String> getSupportedIDs() {
            return ICUResourceBundle.getFullLocaleNameSet(this.bundleName, this.loader());
        }

        @Override
        protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
            return ICUResourceBundle.getBundleInstance(this.bundleName, uLocale, this.loader());
        }

        protected ClassLoader loader() {
            return ClassLoaderUtil.getClassLoader(this.getClass());
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append(", bundle: ");
            stringBuilder.append(this.bundleName);
            return stringBuilder.toString();
        }

        @Override
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            Iterator<String> iterator = ICUResourceBundle.getAvailableLocaleNameSet(this.bundleName, this.loader()).iterator();
            while (iterator.hasNext()) {
                map.put(iterator.next(), this);
            }
        }
    }

    public static class LocaleKey
    extends ICUService.Key {
        public static final int KIND_ANY = -1;
        private String currentID;
        private String fallbackID;
        private int kind;
        private String primaryID;
        private int varstart;

        protected LocaleKey(String string, String string2, String string3, int n) {
            super(string);
            this.kind = n;
            if (string2 != null && !string2.equalsIgnoreCase("root")) {
                n = string2.indexOf(64);
                if (n == 4 && string2.regionMatches(true, 0, "root", 0, 4)) {
                    this.primaryID = string2.substring(4);
                    this.varstart = 0;
                    this.fallbackID = null;
                } else {
                    this.primaryID = string2;
                    this.varstart = n;
                    this.fallbackID = string3 != null && !this.primaryID.equals(string3) ? string3 : "";
                }
            } else {
                this.primaryID = "";
                this.fallbackID = null;
            }
            n = this.varstart;
            string = n == -1 ? this.primaryID : this.primaryID.substring(0, n);
            this.currentID = string;
        }

        public static LocaleKey createWithCanonical(ULocale object, String string, int n) {
            if (object == null) {
                return null;
            }
            object = ((ULocale)object).getName();
            return new LocaleKey((String)object, (String)object, string, n);
        }

        public static LocaleKey createWithCanonicalFallback(String string, String string2) {
            return LocaleKey.createWithCanonicalFallback(string, string2, -1);
        }

        public static LocaleKey createWithCanonicalFallback(String string, String string2, int n) {
            if (string == null) {
                return null;
            }
            return new LocaleKey(string, ULocale.getName(string), string2, n);
        }

        @Override
        public String canonicalID() {
            return this.primaryID;
        }

        public ULocale canonicalLocale() {
            return new ULocale(this.primaryID);
        }

        @Override
        public String currentDescriptor() {
            String string = this.currentID();
            CharSequence charSequence = string;
            if (string != null) {
                charSequence = new StringBuilder();
                if (this.kind != -1) {
                    ((StringBuilder)charSequence).append(this.prefix());
                }
                ((StringBuilder)charSequence).append('/');
                ((StringBuilder)charSequence).append(string);
                int n = this.varstart;
                if (n != -1) {
                    string = this.primaryID;
                    ((StringBuilder)charSequence).append(string.substring(n, string.length()));
                }
                charSequence = ((StringBuilder)charSequence).toString();
            }
            return charSequence;
        }

        @Override
        public String currentID() {
            return this.currentID;
        }

        public ULocale currentLocale() {
            if (this.varstart == -1) {
                return new ULocale(this.currentID);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.currentID);
            stringBuilder.append(this.primaryID.substring(this.varstart));
            return new ULocale(stringBuilder.toString());
        }

        @Override
        public boolean fallback() {
            int n = this.currentID.lastIndexOf(95);
            if (n != -1) {
                while (--n >= 0 && this.currentID.charAt(n) == '_') {
                }
                this.currentID = this.currentID.substring(0, n + 1);
                return true;
            }
            String string = this.fallbackID;
            if (string != null) {
                this.currentID = string;
                this.fallbackID = string.length() == 0 ? null : "";
                return true;
            }
            this.currentID = null;
            return false;
        }

        @Override
        public boolean isFallbackOf(String string) {
            return LocaleUtility.isFallbackOf(this.canonicalID(), string);
        }

        public int kind() {
            return this.kind;
        }

        public String prefix() {
            String string = this.kind == -1 ? null : Integer.toString(this.kind());
            return string;
        }
    }

    public static abstract class LocaleKeyFactory
    implements ICUService.Factory {
        public static final boolean INVISIBLE = false;
        public static final boolean VISIBLE = true;
        protected final String name;
        protected final boolean visible;

        protected LocaleKeyFactory(boolean bl) {
            this.visible = bl;
            this.name = null;
        }

        protected LocaleKeyFactory(boolean bl, String string) {
            this.visible = bl;
            this.name = string;
        }

        @Override
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (this.handlesKey(key)) {
                key = (LocaleKey)key;
                int n = ((LocaleKey)key).kind();
                return this.handleCreate(((LocaleKey)key).currentLocale(), n, iCUService);
            }
            return null;
        }

        @Override
        public String getDisplayName(String string, ULocale uLocale) {
            if (uLocale == null) {
                return string;
            }
            return new ULocale(string).getDisplayName(uLocale);
        }

        protected Set<String> getSupportedIDs() {
            return Collections.emptySet();
        }

        protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
            return null;
        }

        protected boolean handlesKey(ICUService.Key object) {
            if (object != null) {
                object = ((ICUService.Key)object).currentID();
                return this.getSupportedIDs().contains(object);
            }
            return false;
        }

        protected boolean isSupportedID(String string) {
            return this.getSupportedIDs().contains(string);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(super.toString());
            if (this.name != null) {
                stringBuilder.append(", name: ");
                stringBuilder.append(this.name);
            }
            stringBuilder.append(", visible: ");
            stringBuilder.append(this.visible);
            return stringBuilder.toString();
        }

        @Override
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            for (String string : this.getSupportedIDs()) {
                if (this.visible) {
                    map.put(string, this);
                    continue;
                }
                map.remove(string);
            }
        }
    }

    public static class SimpleLocaleKeyFactory
    extends LocaleKeyFactory {
        private final String id;
        private final int kind;
        private final Object obj;

        public SimpleLocaleKeyFactory(Object object, ULocale uLocale, int n, boolean bl) {
            this(object, uLocale, n, bl, null);
        }

        public SimpleLocaleKeyFactory(Object object, ULocale uLocale, int n, boolean bl, String string) {
            super(bl, string);
            this.obj = object;
            this.id = uLocale.getBaseName();
            this.kind = n;
        }

        @Override
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (!(key instanceof LocaleKey)) {
                return null;
            }
            key = (LocaleKey)key;
            int n = this.kind;
            if (n != -1 && n != ((LocaleKey)key).kind()) {
                return null;
            }
            if (!this.id.equals(((LocaleKey)key).currentID())) {
                return null;
            }
            return this.obj;
        }

        @Override
        protected boolean isSupportedID(String string) {
            return this.id.equals(string);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(super.toString());
            stringBuilder.append(", id: ");
            stringBuilder.append(this.id);
            stringBuilder.append(", kind: ");
            stringBuilder.append(this.kind);
            return stringBuilder.toString();
        }

        @Override
        public void updateVisibleIDs(Map<String, ICUService.Factory> map) {
            if (this.visible) {
                map.put(this.id, this);
            } else {
                map.remove(this.id);
            }
        }
    }

}

