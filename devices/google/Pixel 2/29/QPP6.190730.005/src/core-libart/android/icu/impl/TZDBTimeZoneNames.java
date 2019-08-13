/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.TextTrieMap;
import android.icu.impl.TimeZoneNamesImpl;
import android.icu.text.TimeZoneNames;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TZDBTimeZoneNames
extends TimeZoneNames {
    private static final ConcurrentHashMap<String, TZDBNames> TZDB_NAMES_MAP = new ConcurrentHashMap();
    private static volatile TextTrieMap<TZDBNameInfo> TZDB_NAMES_TRIE = null;
    private static final ICUResourceBundle ZONESTRINGS = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/zone", "tzdbNames").get("zoneStrings");
    private static final long serialVersionUID = 1L;
    private ULocale _locale;
    private volatile transient String _region;

    public TZDBTimeZoneNames(ULocale uLocale) {
        this._locale = uLocale;
    }

    private static TZDBNames getMetaZoneNames(String object) {
        Object object2;
        Object object3 = object2 = TZDB_NAMES_MAP.get(object);
        if (object2 == null) {
            object2 = ZONESTRINGS;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("meta:");
            ((StringBuilder)object3).append((String)object);
            object3 = TZDBNames.getInstance((ICUResourceBundle)object2, ((StringBuilder)object3).toString());
            object = ((String)object).intern();
            object = TZDB_NAMES_MAP.putIfAbsent((String)object, (TZDBNames)object3);
            if (object == null) {
                object = object3;
            }
            object3 = object;
        }
        return object3;
    }

    private String getTargetRegion() {
        if (this._region == null) {
            String string;
            String string2 = string = this._locale.getCountry();
            if (string.length() == 0) {
                string2 = string = ULocale.addLikelySubtags(this._locale).getCountry();
                if (string.length() == 0) {
                    string2 = "001";
                }
            }
            this._region = string2;
        }
        return this._region;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void prepareFind() {
        if (TZDB_NAMES_TRIE != null) return;
        synchronized (TZDBTimeZoneNames.class) {
            if (TZDB_NAMES_TRIE != null) return;
            TextTrieMap<Object> textTrieMap = new TextTrieMap<Object>(true);
            Iterator<String> iterator = TimeZoneNamesImpl._getAvailableMetaZoneIDs().iterator();
            do {
                if (!iterator.hasNext()) {
                    TZDB_NAMES_TRIE = textTrieMap;
                    return;
                }
                String string = iterator.next();
                String[] arrstring = TZDBTimeZoneNames.getMetaZoneNames(string);
                Object object = arrstring.getName(TimeZoneNames.NameType.SHORT_STANDARD);
                String string2 = arrstring.getName(TimeZoneNames.NameType.SHORT_DAYLIGHT);
                if (object == null && string2 == null) continue;
                arrstring = arrstring.getParseRegions();
                string = string.intern();
                boolean bl = object != null && string2 != null && ((String)object).equals(string2);
                if (object != null) {
                    TZDBNameInfo tZDBNameInfo = new TZDBNameInfo(string, TimeZoneNames.NameType.SHORT_STANDARD, bl, arrstring);
                    textTrieMap.put((CharSequence)object, tZDBNameInfo);
                }
                if (string2 == null) continue;
                object = new TZDBNameInfo(string, TimeZoneNames.NameType.SHORT_DAYLIGHT, bl, arrstring);
                textTrieMap.put(string2, object);
            } while (true);
        }
    }

    @Override
    public Collection<TimeZoneNames.MatchInfo> find(CharSequence charSequence, int n, EnumSet<TimeZoneNames.NameType> object) {
        if (charSequence != null && charSequence.length() != 0 && n >= 0 && n < charSequence.length()) {
            TZDBTimeZoneNames.prepareFind();
            object = new TZDBNameSearchHandler((EnumSet<TimeZoneNames.NameType>)object, this.getTargetRegion());
            TZDB_NAMES_TRIE.find(charSequence, n, (TextTrieMap.ResultHandler<TZDBNameInfo>)object);
            return ((TZDBNameSearchHandler)object).getMatches();
        }
        throw new IllegalArgumentException("bad input text or range");
    }

    @Override
    public Set<String> getAvailableMetaZoneIDs() {
        return TimeZoneNamesImpl._getAvailableMetaZoneIDs();
    }

    @Override
    public Set<String> getAvailableMetaZoneIDs(String string) {
        return TimeZoneNamesImpl._getAvailableMetaZoneIDs(string);
    }

    @Override
    public String getMetaZoneDisplayName(String string, TimeZoneNames.NameType nameType) {
        if (string != null && string.length() != 0 && (nameType == TimeZoneNames.NameType.SHORT_STANDARD || nameType == TimeZoneNames.NameType.SHORT_DAYLIGHT)) {
            return TZDBTimeZoneNames.getMetaZoneNames(string).getName(nameType);
        }
        return null;
    }

    @Override
    public String getMetaZoneID(String string, long l) {
        return TimeZoneNamesImpl._getMetaZoneID(string, l);
    }

    @Override
    public String getReferenceZoneID(String string, String string2) {
        return TimeZoneNamesImpl._getReferenceZoneID(string, string2);
    }

    @Override
    public String getTimeZoneDisplayName(String string, TimeZoneNames.NameType nameType) {
        return null;
    }

    private static class TZDBNameInfo {
        final boolean ambiguousType;
        final String mzID;
        final String[] parseRegions;
        final TimeZoneNames.NameType type;

        TZDBNameInfo(String string, TimeZoneNames.NameType nameType, boolean bl, String[] arrstring) {
            this.mzID = string;
            this.type = nameType;
            this.ambiguousType = bl;
            this.parseRegions = arrstring;
        }
    }

    private static class TZDBNameSearchHandler
    implements TextTrieMap.ResultHandler<TZDBNameInfo> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Collection<TimeZoneNames.MatchInfo> _matches;
        private EnumSet<TimeZoneNames.NameType> _nameTypes;
        private String _region;

        TZDBNameSearchHandler(EnumSet<TimeZoneNames.NameType> enumSet, String string) {
            this._nameTypes = enumSet;
            this._region = string;
        }

        public Collection<TimeZoneNames.MatchInfo> getMatches() {
            Collection<TimeZoneNames.MatchInfo> collection = this._matches;
            if (collection == null) {
                return Collections.emptyList();
            }
            return collection;
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<TZDBNameInfo> object) {
            block14 : {
                Object object2;
                block15 : {
                    Object object3;
                    block16 : {
                        Object object4 = null;
                        String[] arrstring = null;
                        do {
                            Object object5;
                            object2 = object4;
                            if (!object.hasNext()) break;
                            object3 = object.next();
                            object2 = this._nameTypes;
                            if (object2 != null && !((AbstractCollection)object2).contains((Object)object3.type)) continue;
                            if (object3.parseRegions == null) {
                                object5 = arrstring;
                                if (arrstring == null) {
                                    object5 = object3;
                                    object4 = object3;
                                }
                            } else {
                                boolean bl;
                                boolean bl2 = false;
                                object5 = object3.parseRegions;
                                int n2 = ((String[])object5).length;
                                int n3 = 0;
                                do {
                                    object2 = object4;
                                    bl = bl2;
                                    if (n3 >= n2) break;
                                    object2 = object5[n3];
                                    if (this._region.equals(object2)) {
                                        object2 = object3;
                                        bl = true;
                                        break;
                                    }
                                    ++n3;
                                } while (true);
                                if (bl) break;
                                object4 = object2;
                                object5 = arrstring;
                                if (object2 == null) {
                                    object5 = arrstring;
                                    object4 = object3;
                                }
                            }
                            arrstring = object5;
                        } while (true);
                        if (object2 == null) break block14;
                        object3 = ((TZDBNameInfo)object2).type;
                        object = object3;
                        if (!((TZDBNameInfo)object2).ambiguousType) break block15;
                        if (object3 == TimeZoneNames.NameType.SHORT_STANDARD) break block16;
                        object = object3;
                        if (object3 != TimeZoneNames.NameType.SHORT_DAYLIGHT) break block15;
                    }
                    object = object3;
                    if (this._nameTypes.contains((Object)TimeZoneNames.NameType.SHORT_STANDARD)) {
                        object = object3;
                        if (this._nameTypes.contains((Object)TimeZoneNames.NameType.SHORT_DAYLIGHT)) {
                            object = TimeZoneNames.NameType.SHORT_GENERIC;
                        }
                    }
                }
                object = new TimeZoneNames.MatchInfo((TimeZoneNames.NameType)((Object)object), null, ((TZDBNameInfo)object2).mzID, n);
                if (this._matches == null) {
                    this._matches = new LinkedList<TimeZoneNames.MatchInfo>();
                }
                this._matches.add((TimeZoneNames.MatchInfo)object);
            }
            return true;
        }
    }

    private static class TZDBNames {
        public static final TZDBNames EMPTY_TZDBNAMES = new TZDBNames(null, null);
        private static final String[] KEYS = new String[]{"ss", "sd"};
        private String[] _names;
        private String[] _parseRegions;

        private TZDBNames(String[] arrstring, String[] arrstring2) {
            this._names = arrstring;
            this._parseRegions = arrstring2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static TZDBNames getInstance(ICUResourceBundle arrstring, String arrstring2) {
            ICUResourceBundle iCUResourceBundle;
            boolean bl;
            if (arrstring == null) return EMPTY_TZDBNAMES;
            if (arrstring2 == null) return EMPTY_TZDBNAMES;
            if (arrstring2.length() == 0) {
                return EMPTY_TZDBNAMES;
            }
            try {
                iCUResourceBundle = (ICUResourceBundle)arrstring.get((String)arrstring2);
                bl = true;
            }
            catch (MissingResourceException missingResourceException) {
                return EMPTY_TZDBNAMES;
            }
            String[] arrstring3 = new String[KEYS.length];
            for (int i = 0; i < arrstring3.length; ++i) {
                try {
                    arrstring3[i] = iCUResourceBundle.getString(KEYS[i]);
                    bl = false;
                    continue;
                }
                catch (MissingResourceException missingResourceException) {
                    arrstring3[i] = null;
                }
            }
            if (bl) {
                return EMPTY_TZDBNAMES;
            }
            Object var6_9 = null;
            arrstring = null;
            arrstring2 = var6_9;
            try {
                iCUResourceBundle = (ICUResourceBundle)iCUResourceBundle.get("parseRegions");
                arrstring2 = var6_9;
                if (iCUResourceBundle.getType() == 0) {
                    arrstring2 = var6_9;
                    arrstring2 = arrstring = new String[1];
                    arrstring[0] = iCUResourceBundle.getString();
                    return new TZDBNames(arrstring3, arrstring);
                }
                arrstring2 = var6_9;
                if (iCUResourceBundle.getType() != 8) return new TZDBNames(arrstring3, arrstring);
                arrstring2 = var6_9;
                arrstring = iCUResourceBundle.getStringArray();
                return new TZDBNames(arrstring3, arrstring);
            }
            catch (MissingResourceException missingResourceException) {
                arrstring = arrstring2;
            }
            return new TZDBNames(arrstring3, arrstring);
        }

        String getName(TimeZoneNames.NameType object) {
            if (this._names == null) {
                return null;
            }
            Object var2_2 = null;
            int n = 1.$SwitchMap$android$icu$text$TimeZoneNames$NameType[object.ordinal()];
            object = n != 1 ? (n != 2 ? var2_2 : this._names[1]) : this._names[0];
            return object;
        }

        String[] getParseRegions() {
            return this._parseRegions;
        }
    }

}

