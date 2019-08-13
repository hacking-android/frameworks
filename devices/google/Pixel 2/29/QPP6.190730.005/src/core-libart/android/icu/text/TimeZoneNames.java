/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUConfig;
import android.icu.impl.SoftCache;
import android.icu.impl.TZDBTimeZoneNames;
import android.icu.impl.TimeZoneNamesImpl;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

public abstract class TimeZoneNames
implements Serializable {
    private static final String DEFAULT_FACTORY_CLASS = "android.icu.impl.TimeZoneNamesFactoryImpl";
    private static final String FACTORY_NAME_PROP = "android.icu.text.TimeZoneNames.Factory.impl";
    private static Cache TZNAMES_CACHE = new Cache();
    private static final Factory TZNAMES_FACTORY;
    private static final long serialVersionUID = -9180227029248969153L;

    static {
        Object object = null;
        Object object2 = ICUConfig.get(FACTORY_NAME_PROP, DEFAULT_FACTORY_CLASS);
        do {
            block10 : {
                block9 : {
                    try {
                        Factory factory = (Factory)Class.forName((String)object2).newInstance();
                        object2 = factory;
                        break block9;
                    }
                    catch (InstantiationException instantiationException) {
                    }
                    catch (IllegalAccessException illegalAccessException) {
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        // empty catch block
                    }
                    if (!((String)object2).equals(DEFAULT_FACTORY_CLASS)) break block10;
                    object2 = object;
                }
                object = object2;
                if (object2 == null) {
                    object = new DefaultTimeZoneNames.FactoryImpl();
                }
                TZNAMES_FACTORY = object;
                return;
            }
            object2 = DEFAULT_FACTORY_CLASS;
        } while (true);
    }

    protected TimeZoneNames() {
    }

    public static TimeZoneNames getInstance(ULocale uLocale) {
        String string = uLocale.getBaseName();
        return (TimeZoneNames)TZNAMES_CACHE.getInstance(string, uLocale);
    }

    public static TimeZoneNames getInstance(Locale locale) {
        return TimeZoneNames.getInstance(ULocale.forLocale(locale));
    }

    public static TimeZoneNames getTZDBInstance(ULocale uLocale) {
        return new TZDBTimeZoneNames(uLocale);
    }

    public Collection<MatchInfo> find(CharSequence charSequence, int n, EnumSet<NameType> enumSet) {
        throw new UnsupportedOperationException("The method is not implemented in TimeZoneNames base class.");
    }

    public abstract Set<String> getAvailableMetaZoneIDs();

    public abstract Set<String> getAvailableMetaZoneIDs(String var1);

    public final String getDisplayName(String string, NameType nameType, long l) {
        String string2;
        String string3 = string2 = this.getTimeZoneDisplayName(string, nameType);
        if (string2 == null) {
            string3 = this.getMetaZoneDisplayName(this.getMetaZoneID(string, l), nameType);
        }
        return string3;
    }

    @Deprecated
    public void getDisplayNames(String string, NameType[] arrnameType, long l, String[] arrstring, int n) {
        if (string != null && string.length() != 0) {
            String string2 = null;
            for (int i = 0; i < arrnameType.length; ++i) {
                NameType nameType = arrnameType[i];
                String string3 = this.getTimeZoneDisplayName(string, nameType);
                String string4 = string2;
                String string5 = string3;
                if (string3 == null) {
                    string4 = string2;
                    if (string2 == null) {
                        string4 = this.getMetaZoneID(string, l);
                    }
                    string5 = this.getMetaZoneDisplayName(string4, nameType);
                }
                arrstring[n + i] = string5;
                string2 = string4;
            }
            return;
        }
    }

    public String getExemplarLocationName(String string) {
        return TimeZoneNamesImpl.getDefaultExemplarLocationName(string);
    }

    public abstract String getMetaZoneDisplayName(String var1, NameType var2);

    public abstract String getMetaZoneID(String var1, long var2);

    public abstract String getReferenceZoneID(String var1, String var2);

    public abstract String getTimeZoneDisplayName(String var1, NameType var2);

    @Deprecated
    public void loadAllDisplayNames() {
    }

    private static class Cache
    extends SoftCache<String, TimeZoneNames, ULocale> {
        private Cache() {
        }

        @Override
        protected TimeZoneNames createInstance(String string, ULocale uLocale) {
            return TZNAMES_FACTORY.getTimeZoneNames(uLocale);
        }
    }

    private static class DefaultTimeZoneNames
    extends TimeZoneNames {
        public static final DefaultTimeZoneNames INSTANCE = new DefaultTimeZoneNames();
        private static final long serialVersionUID = -995672072494349071L;

        private DefaultTimeZoneNames() {
        }

        @Override
        public Collection<MatchInfo> find(CharSequence charSequence, int n, EnumSet<NameType> enumSet) {
            return Collections.emptyList();
        }

        @Override
        public Set<String> getAvailableMetaZoneIDs() {
            return Collections.emptySet();
        }

        @Override
        public Set<String> getAvailableMetaZoneIDs(String string) {
            return Collections.emptySet();
        }

        @Override
        public String getMetaZoneDisplayName(String string, NameType nameType) {
            return null;
        }

        @Override
        public String getMetaZoneID(String string, long l) {
            return null;
        }

        @Override
        public String getReferenceZoneID(String string, String string2) {
            return null;
        }

        @Override
        public String getTimeZoneDisplayName(String string, NameType nameType) {
            return null;
        }

        public static class FactoryImpl
        extends Factory {
            @Override
            public TimeZoneNames getTimeZoneNames(ULocale uLocale) {
                return INSTANCE;
            }
        }

    }

    @Deprecated
    public static abstract class Factory {
        @Deprecated
        protected Factory() {
        }

        @Deprecated
        public abstract TimeZoneNames getTimeZoneNames(ULocale var1);
    }

    public static class MatchInfo {
        private int _matchLength;
        private String _mzID;
        private NameType _nameType;
        private String _tzID;

        public MatchInfo(NameType nameType, String string, String string2, int n) {
            if (nameType != null) {
                if (string == null && string2 == null) {
                    throw new IllegalArgumentException("Either tzID or mzID must be available");
                }
                if (n > 0) {
                    this._nameType = nameType;
                    this._tzID = string;
                    this._mzID = string2;
                    this._matchLength = n;
                    return;
                }
                throw new IllegalArgumentException("matchLength must be positive value");
            }
            throw new IllegalArgumentException("nameType is null");
        }

        public int matchLength() {
            return this._matchLength;
        }

        public String mzID() {
            return this._mzID;
        }

        public NameType nameType() {
            return this._nameType;
        }

        public String tzID() {
            return this._tzID;
        }
    }

    public static enum NameType {
        LONG_GENERIC,
        LONG_STANDARD,
        LONG_DAYLIGHT,
        SHORT_GENERIC,
        SHORT_STANDARD,
        SHORT_DAYLIGHT,
        EXEMPLAR_LOCATION;
        
    }

}

