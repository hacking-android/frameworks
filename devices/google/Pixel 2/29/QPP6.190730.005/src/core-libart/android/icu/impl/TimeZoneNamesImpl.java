/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Grego;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SoftCache;
import android.icu.impl.TextTrieMap;
import android.icu.impl.UResource;
import android.icu.impl.Utility;
import android.icu.impl.ZoneMeta;
import android.icu.text.TimeZoneNames;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeZoneNamesImpl
extends TimeZoneNames {
    private static final Pattern LOC_EXCLUSION_PATTERN;
    private static volatile Set<String> METAZONE_IDS;
    private static final String MZ_PREFIX = "meta:";
    private static final MZ2TZsCache MZ_TO_TZS_CACHE;
    private static final TZ2MZsCache TZ_TO_MZS_CACHE;
    private static final String ZONE_STRINGS_BUNDLE = "zoneStrings";
    private static final long serialVersionUID = -2179814848495897472L;
    private transient ConcurrentHashMap<String, ZNames> _mzNamesMap;
    private transient boolean _namesFullyLoaded;
    private transient TextTrieMap<NameInfo> _namesTrie;
    private transient boolean _namesTrieFullyLoaded;
    private transient ConcurrentHashMap<String, ZNames> _tzNamesMap;
    private transient ICUResourceBundle _zoneStrings;

    static {
        TZ_TO_MZS_CACHE = new TZ2MZsCache();
        MZ_TO_TZS_CACHE = new MZ2TZsCache();
        LOC_EXCLUSION_PATTERN = Pattern.compile("Etc/.*|SystemV/.*|.*/Riyadh8[7-9]");
    }

    public TimeZoneNamesImpl(ULocale uLocale) {
        this.initialize(uLocale);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Set<String> _getAvailableMetaZoneIDs() {
        if (METAZONE_IDS != null) return METAZONE_IDS;
        synchronized (TimeZoneNamesImpl.class) {
            if (METAZONE_IDS != null) return METAZONE_IDS;
            METAZONE_IDS = Collections.unmodifiableSet(UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "metaZones").get("mapTimezones").keySet());
            return METAZONE_IDS;
        }
    }

    static Set<String> _getAvailableMetaZoneIDs(String object) {
        if (object != null && ((String)object).length() != 0) {
            Object object2 = (List)TZ_TO_MZS_CACHE.getInstance(object, object);
            if (object2.isEmpty()) {
                return Collections.emptySet();
            }
            object = new HashSet(object2.size());
            object2 = object2.iterator();
            while (object2.hasNext()) {
                object.add(((MZMapEntry)object2.next()).mzID());
            }
            return Collections.unmodifiableSet(object);
        }
        return Collections.emptySet();
    }

    static String _getMetaZoneID(String object, long l) {
        if (object != null && ((String)object).length() != 0) {
            block2 : {
                Object var3_2 = null;
                Iterator iterator = ((List)TZ_TO_MZS_CACHE.getInstance(object, object)).iterator();
                do {
                    object = var3_2;
                    if (!iterator.hasNext()) break block2;
                } while (l < ((MZMapEntry)(object = (MZMapEntry)iterator.next())).from() || l >= ((MZMapEntry)object).to());
                object = ((MZMapEntry)object).mzID();
            }
            return object;
        }
        return null;
    }

    static String _getReferenceZoneID(String string, String string2) {
        if (string != null && string.length() != 0) {
            Object var2_2 = null;
            Map map = (Map)MZ_TO_TZS_CACHE.getInstance(string, string);
            string = var2_2;
            if (!map.isEmpty()) {
                string = string2 = (String)map.get(string2);
                if (string2 == null) {
                    string = (String)map.get("001");
                }
            }
            return string;
        }
        return null;
    }

    private void addAllNamesIntoTrie() {
        for (Map.Entry<String, ZNames> entry : this._tzNamesMap.entrySet()) {
            entry.getValue().addAsTimeZoneIntoTrie(entry.getKey(), this._namesTrie);
        }
        for (Map.Entry<String, ZNames> entry : this._mzNamesMap.entrySet()) {
            entry.getValue().addAsMetaZoneIntoTrie(entry.getKey(), this._namesTrie);
        }
    }

    private Collection<TimeZoneNames.MatchInfo> doFind(NameSearchHandler nameSearchHandler, CharSequence charSequence, int n) {
        nameSearchHandler.resetResults();
        this._namesTrie.find(charSequence, n, nameSearchHandler);
        if (nameSearchHandler.getMaxMatchLen() != charSequence.length() - n && !this._namesTrieFullyLoaded) {
            return null;
        }
        return nameSearchHandler.getMatches();
    }

    public static String getDefaultExemplarLocationName(String string) {
        if (string != null && string.length() != 0 && !LOC_EXCLUSION_PATTERN.matcher(string).matches()) {
            String string2 = null;
            int n = string.lastIndexOf(47);
            String string3 = string2;
            if (n > 0) {
                string3 = string2;
                if (n + 1 < string.length()) {
                    string3 = string.substring(n + 1).replace('_', ' ');
                }
            }
            return string3;
        }
        return null;
    }

    private void initialize(ULocale object) {
        this._zoneStrings = (ICUResourceBundle)((ICUResourceBundle)ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/zone", (ULocale)object)).get(ZONE_STRINGS_BUNDLE);
        this._tzNamesMap = new ConcurrentHashMap();
        this._mzNamesMap = new ConcurrentHashMap();
        this._namesFullyLoaded = false;
        this._namesTrie = new TextTrieMap(true);
        this._namesTrieFullyLoaded = false;
        object = ZoneMeta.getCanonicalCLDRID(TimeZone.getDefault());
        if (object != null) {
            this.loadStrings((String)object);
        }
    }

    private void internalLoadAllDisplayNames() {
        if (!this._namesFullyLoaded) {
            this._namesFullyLoaded = true;
            new ZoneStringsLoader().load();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ZNames loadMetaZoneNames(String string) {
        synchronized (this) {
            ZNames zNames = this._mzNamesMap.get(string);
            Object object = zNames;
            if (zNames != null) return object;
            object = new ZNamesLoader();
            ((ZNamesLoader)object).loadMetaZone(this._zoneStrings, string);
            return ZNames.createMetaZoneAndPutInCache(this._mzNamesMap, ((ZNamesLoader)object).getNames(), string);
        }
    }

    private void loadStrings(String object) {
        synchronized (this) {
            block7 : {
                if (object != null) {
                    if (((String)object).length() == 0) {
                        break block7;
                    }
                    this.loadTimeZoneNames((String)object);
                    object = this.getAvailableMetaZoneIDs((String)object).iterator();
                    while (object.hasNext()) {
                        this.loadMetaZoneNames((String)object.next());
                    }
                    return;
                }
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ZNames loadTimeZoneNames(String string) {
        synchronized (this) {
            ZNames zNames = this._tzNamesMap.get(string);
            Object object = zNames;
            if (zNames != null) return object;
            object = new ZNamesLoader();
            ((ZNamesLoader)object).loadTimeZone(this._zoneStrings, string);
            return ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, ((ZNamesLoader)object).getNames(), string);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.initialize((ULocale)objectInputStream.readObject());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this._zoneStrings.getULocale());
    }

    @Override
    public Collection<TimeZoneNames.MatchInfo> find(CharSequence collection, int n, EnumSet<TimeZoneNames.NameType> collection2) {
        synchronized (this) {
            block9 : {
                if (collection != null) {
                    NameSearchHandler nameSearchHandler;
                    block11 : {
                        block10 : {
                            if (collection.length() == 0 || n < 0) break block9;
                            if (n >= collection.length()) break block9;
                            nameSearchHandler = new NameSearchHandler((EnumSet<TimeZoneNames.NameType>)collection2);
                            collection2 = this.doFind(nameSearchHandler, (CharSequence)((Object)collection), n);
                            if (collection2 == null) break block10;
                            return collection2;
                        }
                        this.addAllNamesIntoTrie();
                        collection2 = this.doFind(nameSearchHandler, (CharSequence)((Object)collection), n);
                        if (collection2 == null) break block11;
                        return collection2;
                    }
                    this.internalLoadAllDisplayNames();
                    for (String string : TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null)) {
                        if (this._tzNamesMap.containsKey(string)) continue;
                        ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, null, string);
                    }
                    this.addAllNamesIntoTrie();
                    this._namesTrieFullyLoaded = true;
                    collection = this.doFind(nameSearchHandler, (CharSequence)((Object)collection), n);
                    return collection;
                }
            }
            collection = new Collection<TimeZoneNames.MatchInfo>("bad input text or range");
            throw collection;
        }
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
    public void getDisplayNames(String string, TimeZoneNames.NameType[] arrnameType, long l, String[] arrstring, int n) {
        if (string != null && string.length() != 0) {
            ZNames zNames = this.loadTimeZoneNames(string);
            ZNames zNames2 = null;
            for (int i = 0; i < arrnameType.length; ++i) {
                TimeZoneNames.NameType nameType = arrnameType[i];
                String string2 = zNames.getName(nameType);
                Object object = zNames2;
                String string3 = string2;
                if (string2 == null) {
                    object = zNames2;
                    if (zNames2 == null) {
                        object = this.getMetaZoneID(string, l);
                        object = object != null && ((String)object).length() != 0 ? this.loadMetaZoneNames((String)object) : ZNames.EMPTY_ZNAMES;
                    }
                    string3 = ((ZNames)object).getName(nameType);
                }
                arrstring[n + i] = string3;
                zNames2 = object;
            }
            return;
        }
    }

    @Override
    public String getExemplarLocationName(String string) {
        if (string != null && string.length() != 0) {
            return this.loadTimeZoneNames(string).getName(TimeZoneNames.NameType.EXEMPLAR_LOCATION);
        }
        return null;
    }

    @Override
    public String getMetaZoneDisplayName(String string, TimeZoneNames.NameType nameType) {
        if (string != null && string.length() != 0) {
            return this.loadMetaZoneNames(string).getName(nameType);
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
        if (string != null && string.length() != 0) {
            return this.loadTimeZoneNames(string).getName(nameType);
        }
        return null;
    }

    @Override
    public void loadAllDisplayNames() {
        synchronized (this) {
            this.internalLoadAllDisplayNames();
            return;
        }
    }

    private static class MZ2TZsCache
    extends SoftCache<String, Map<String, String>, String> {
        private MZ2TZsCache() {
        }

        @Override
        protected Map<String, String> createInstance(String hashMap, String object) {
            object = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "metaZones").get("mapTimezones");
            try {
                object = ((UResourceBundle)object).get((String)((Object)hashMap));
                Object object2 = ((UResourceBundle)object).keySet();
                hashMap = new HashMap<String, String>(object2.size());
                Iterator<String> iterator = object2.iterator();
                while (iterator.hasNext()) {
                    object2 = iterator.next();
                    String string = ((ResourceBundle)object).getString((String)object2).intern();
                    hashMap.put(((String)object2).intern(), string);
                }
            }
            catch (MissingResourceException missingResourceException) {
                hashMap = Collections.emptyMap();
            }
            return hashMap;
        }
    }

    private static class MZMapEntry {
        private long _from;
        private String _mzID;
        private long _to;

        MZMapEntry(String string, long l, long l2) {
            this._mzID = string;
            this._from = l;
            this._to = l2;
        }

        long from() {
            return this._from;
        }

        String mzID() {
            return this._mzID;
        }

        long to() {
            return this._to;
        }
    }

    private static class NameInfo {
        String mzID;
        TimeZoneNames.NameType type;
        String tzID;

        private NameInfo() {
        }
    }

    private static class NameSearchHandler
    implements TextTrieMap.ResultHandler<NameInfo> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Collection<TimeZoneNames.MatchInfo> _matches;
        private int _maxMatchLen;
        private EnumSet<TimeZoneNames.NameType> _nameTypes;

        NameSearchHandler(EnumSet<TimeZoneNames.NameType> enumSet) {
            this._nameTypes = enumSet;
        }

        public Collection<TimeZoneNames.MatchInfo> getMatches() {
            Collection<TimeZoneNames.MatchInfo> collection = this._matches;
            if (collection == null) {
                return Collections.emptyList();
            }
            return collection;
        }

        public int getMaxMatchLen() {
            return this._maxMatchLen;
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<NameInfo> iterator) {
            while (iterator.hasNext()) {
                Object object = iterator.next();
                EnumSet<TimeZoneNames.NameType> enumSet = this._nameTypes;
                if (enumSet != null && !enumSet.contains((Object)((NameInfo)object).type)) continue;
                object = ((NameInfo)object).tzID != null ? new TimeZoneNames.MatchInfo(((NameInfo)object).type, ((NameInfo)object).tzID, null, n) : new TimeZoneNames.MatchInfo(((NameInfo)object).type, null, ((NameInfo)object).mzID, n);
                if (this._matches == null) {
                    this._matches = new LinkedList<TimeZoneNames.MatchInfo>();
                }
                this._matches.add((TimeZoneNames.MatchInfo)object);
                if (n <= this._maxMatchLen) continue;
                this._maxMatchLen = n;
            }
            return true;
        }

        public void resetResults() {
            this._matches = null;
            this._maxMatchLen = 0;
        }
    }

    private static class TZ2MZsCache
    extends SoftCache<String, List<MZMapEntry>, String> {
        private TZ2MZsCache() {
        }

        private static long parseDate(String string) {
            int n;
            int n2;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            for (n2 = 0; n2 <= 3; ++n2) {
                n = string.charAt(n2) - 48;
                if (n >= 0 && n < 10) {
                    n3 = n3 * 10 + n;
                    continue;
                }
                throw new IllegalArgumentException("Bad year");
            }
            n2 = n4;
            for (n = 5; n <= 6; ++n) {
                n4 = string.charAt(n) - 48;
                if (n4 >= 0 && n4 < 10) {
                    n2 = n2 * 10 + n4;
                    continue;
                }
                throw new IllegalArgumentException("Bad month");
            }
            n = n5;
            for (n4 = 8; n4 <= 9; ++n4) {
                n5 = string.charAt(n4) - 48;
                if (n5 >= 0 && n5 < 10) {
                    n = n * 10 + n5;
                    continue;
                }
                throw new IllegalArgumentException("Bad day");
            }
            n4 = n6;
            for (n5 = 11; n5 <= 12; ++n5) {
                n6 = string.charAt(n5) - 48;
                if (n6 >= 0 && n6 < 10) {
                    n4 = n4 * 10 + n6;
                    continue;
                }
                throw new IllegalArgumentException("Bad hour");
            }
            for (n5 = 14; n5 <= 15; ++n5) {
                n6 = string.charAt(n5) - 48;
                if (n6 >= 0 && n6 < 10) {
                    n7 = n7 * 10 + n6;
                    continue;
                }
                throw new IllegalArgumentException("Bad minute");
            }
            return Grego.fieldsToDay(n3, n2 - 1, n) * 86400000L + (long)n4 * 3600000L + (long)n7 * 60000L;
        }

        @Override
        protected List<MZMapEntry> createInstance(String arrayList, String object) {
            block6 : {
                int n;
                UResourceBundle uResourceBundle;
                arrayList = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "metaZones").get("metazoneInfo");
                object = ((String)object).replace('/', ':');
                try {
                    uResourceBundle = ((UResourceBundle)((Object)arrayList)).get((String)object);
                    arrayList = new ArrayList<Object>(uResourceBundle.getSize());
                    n = 0;
                }
                catch (MissingResourceException missingResourceException) {
                    arrayList = Collections.emptyList();
                }
                do {
                    String string;
                    Object object2;
                    block7 : {
                        if (n >= uResourceBundle.getSize()) break block6;
                        object2 = uResourceBundle.get(n);
                        string = ((UResourceBundle)object2).getString(0);
                        if (((UResourceBundle)object2).getSize() == 3) {
                            object = ((UResourceBundle)object2).getString(1);
                            String string2 = ((UResourceBundle)object2).getString(2);
                            object2 = object;
                            object = string2;
                            break block7;
                        }
                        object2 = "1970-01-01 00:00";
                        object = "9999-12-31 23:59";
                    }
                    long l = TZ2MZsCache.parseDate((String)object2);
                    long l2 = TZ2MZsCache.parseDate((String)object);
                    object = new MZMapEntry(string, l, l2);
                    arrayList.add(object);
                    ++n;
                    continue;
                    break;
                } while (true);
            }
            return arrayList;
        }
    }

    private static class ZNames {
        static final ZNames EMPTY_ZNAMES = new ZNames(null);
        private static final int EX_LOC_INDEX = NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
        public static final int NUM_NAME_TYPES = 7;
        private String[] _names;
        private boolean didAddIntoTrie;

        protected ZNames(String[] arrstring) {
            this._names = arrstring;
            boolean bl = arrstring == null;
            this.didAddIntoTrie = bl;
        }

        private void addNamesIntoTrie(String string, String string2, TextTrieMap<NameInfo> textTrieMap) {
            if (this._names != null && !this.didAddIntoTrie) {
                Object object;
                this.didAddIntoTrie = true;
                for (int i = 0; i < ((String[])(object = this._names)).length; ++i) {
                    String string3 = object[i];
                    if (string3 == null) continue;
                    object = new NameInfo();
                    object.mzID = string;
                    object.tzID = string2;
                    object.type = ZNames.getNameType(i);
                    textTrieMap.put(string3, (NameInfo)object);
                }
                return;
            }
        }

        public static ZNames createMetaZoneAndPutInCache(Map<String, ZNames> map, String[] object, String string) {
            string = string.intern();
            object = object == null ? EMPTY_ZNAMES : new ZNames((String[])object);
            map.put(string, (ZNames)object);
            return object;
        }

        public static ZNames createTimeZoneAndPutInCache(Map<String, ZNames> map, String[] object, String string) {
            int n;
            if (object == null) {
                object = new String[EX_LOC_INDEX + 1];
            }
            if (object[n = EX_LOC_INDEX] == null) {
                object[n] = TimeZoneNamesImpl.getDefaultExemplarLocationName(string);
            }
            string = string.intern();
            object = new ZNames((String[])object);
            map.put(string, (ZNames)object);
            return object;
        }

        private static TimeZoneNames.NameType getNameType(int n) {
            switch (NameTypeIndex.values[n]) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No NameType match for ");
                    stringBuilder.append(n);
                    throw new AssertionError((Object)stringBuilder.toString());
                }
                case SHORT_DAYLIGHT: {
                    return TimeZoneNames.NameType.SHORT_DAYLIGHT;
                }
                case SHORT_STANDARD: {
                    return TimeZoneNames.NameType.SHORT_STANDARD;
                }
                case SHORT_GENERIC: {
                    return TimeZoneNames.NameType.SHORT_GENERIC;
                }
                case LONG_DAYLIGHT: {
                    return TimeZoneNames.NameType.LONG_DAYLIGHT;
                }
                case LONG_STANDARD: {
                    return TimeZoneNames.NameType.LONG_STANDARD;
                }
                case LONG_GENERIC: {
                    return TimeZoneNames.NameType.LONG_GENERIC;
                }
                case EXEMPLAR_LOCATION: 
            }
            return TimeZoneNames.NameType.EXEMPLAR_LOCATION;
        }

        private static int getNameTypeIndex(TimeZoneNames.NameType nameType) {
            switch (nameType) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No NameTypeIndex match for ");
                    stringBuilder.append((Object)nameType);
                    throw new AssertionError((Object)stringBuilder.toString());
                }
                case SHORT_DAYLIGHT: {
                    return NameTypeIndex.SHORT_DAYLIGHT.ordinal();
                }
                case SHORT_STANDARD: {
                    return NameTypeIndex.SHORT_STANDARD.ordinal();
                }
                case SHORT_GENERIC: {
                    return NameTypeIndex.SHORT_GENERIC.ordinal();
                }
                case LONG_DAYLIGHT: {
                    return NameTypeIndex.LONG_DAYLIGHT.ordinal();
                }
                case LONG_STANDARD: {
                    return NameTypeIndex.LONG_STANDARD.ordinal();
                }
                case LONG_GENERIC: {
                    return NameTypeIndex.LONG_GENERIC.ordinal();
                }
                case EXEMPLAR_LOCATION: 
            }
            return NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
        }

        public void addAsMetaZoneIntoTrie(String string, TextTrieMap<NameInfo> textTrieMap) {
            this.addNamesIntoTrie(string, null, textTrieMap);
        }

        public void addAsTimeZoneIntoTrie(String string, TextTrieMap<NameInfo> textTrieMap) {
            this.addNamesIntoTrie(null, string, textTrieMap);
        }

        public String getName(TimeZoneNames.NameType arrstring) {
            int n = ZNames.getNameTypeIndex((TimeZoneNames.NameType)arrstring);
            arrstring = this._names;
            if (arrstring != null && n < arrstring.length) {
                return arrstring[n];
            }
            return null;
        }

        private static enum NameTypeIndex {
            EXEMPLAR_LOCATION,
            LONG_GENERIC,
            LONG_STANDARD,
            LONG_DAYLIGHT,
            SHORT_GENERIC,
            SHORT_STANDARD,
            SHORT_DAYLIGHT;
            
            static final NameTypeIndex[] values;

            static {
                values = NameTypeIndex.values();
            }
        }

    }

    private static final class ZNamesLoader
    extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static ZNamesLoader DUMMY_LOADER = new ZNamesLoader();
        private String[] names;

        private ZNamesLoader() {
        }

        private String[] getNames() {
            Object object;
            if (Utility.sameObjects(this.names, null)) {
                return null;
            }
            int n = 0;
            for (int i = 0; i < 7; ++i) {
                object = this.names[i];
                int n2 = n;
                if (object != null) {
                    if (object.equals("\u2205\u2205\u2205")) {
                        this.names[i] = null;
                        n2 = n;
                    } else {
                        n2 = i + 1;
                    }
                }
                n = n2;
            }
            object = n == 7 ? this.names : (n == 0 ? null : Arrays.copyOfRange(this.names, 0, n));
            return object;
        }

        private static ZNames.NameTypeIndex nameTypeIndexFromKey(UResource.Key object) {
            int n = object.length();
            Object var2_2 = null;
            Object var3_3 = null;
            if (n != 2) {
                return null;
            }
            n = object.charAt(0);
            char c = object.charAt(1);
            if (n == 108) {
                if (c == 'g') {
                    object = ZNames.NameTypeIndex.LONG_GENERIC;
                } else if (c == 's') {
                    object = ZNames.NameTypeIndex.LONG_STANDARD;
                } else {
                    object = var3_3;
                    if (c == 'd') {
                        object = ZNames.NameTypeIndex.LONG_DAYLIGHT;
                    }
                }
                return object;
            }
            if (n == 115) {
                if (c == 'g') {
                    object = ZNames.NameTypeIndex.SHORT_GENERIC;
                } else if (c == 's') {
                    object = ZNames.NameTypeIndex.SHORT_STANDARD;
                } else {
                    object = var2_2;
                    if (c == 'd') {
                        object = ZNames.NameTypeIndex.SHORT_DAYLIGHT;
                    }
                }
                return object;
            }
            if (n == 101 && c == 'c') {
                return ZNames.NameTypeIndex.EXEMPLAR_LOCATION;
            }
            return null;
        }

        private void setNameIfEmpty(UResource.Key object, UResource.Value value) {
            if (this.names == null) {
                this.names = new String[7];
            }
            if ((object = ZNamesLoader.nameTypeIndexFromKey((UResource.Key)object)) == null) {
                return;
            }
            if (this.names[((Enum)object).ordinal()] == null) {
                this.names[object.ordinal()] = value.getString();
            }
        }

        void loadMetaZone(ICUResourceBundle iCUResourceBundle, String string) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(TimeZoneNamesImpl.MZ_PREFIX);
            stringBuilder.append(string);
            this.loadNames(iCUResourceBundle, stringBuilder.toString());
        }

        void loadNames(ICUResourceBundle iCUResourceBundle, String string) {
            this.names = null;
            try {
                iCUResourceBundle.getAllItemsWithFallback(string, this);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }

        void loadTimeZone(ICUResourceBundle iCUResourceBundle, String string) {
            this.loadNames(iCUResourceBundle, string.replace('/', ':'));
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                this.setNameIfEmpty(key, value);
                ++n;
            }
        }
    }

    private final class ZoneStringsLoader
    extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int INITIAL_NUM_ZONES = 300;
        private HashMap<UResource.Key, ZNamesLoader> keyToLoader = new HashMap(300);
        private StringBuilder sb = new StringBuilder(32);

        private ZoneStringsLoader() {
        }

        private void consumeNamesTable(UResource.Key key, UResource.Value value, boolean bl) {
            Object object;
            Object object2 = object = this.keyToLoader.get(key);
            if (object == null) {
                if (this.isMetaZone(key)) {
                    object2 = this.mzIDFromKey(key);
                    object2 = TimeZoneNamesImpl.this._mzNamesMap.containsKey(object2) ? ZNamesLoader.DUMMY_LOADER : new ZNamesLoader();
                } else {
                    object2 = this.tzIDFromKey(key);
                    object2 = TimeZoneNamesImpl.this._tzNamesMap.containsKey(object2) ? ZNamesLoader.DUMMY_LOADER : new ZNamesLoader();
                }
                object = this.createKey(key);
                this.keyToLoader.put((UResource.Key)object, (ZNamesLoader)object2);
            }
            if (object2 != ZNamesLoader.DUMMY_LOADER) {
                ((ZNamesLoader)object2).put(key, value, bl);
            }
        }

        private String mzIDFromKey(UResource.Key key) {
            this.sb.setLength(0);
            for (int i = "meta:".length(); i < key.length(); ++i) {
                this.sb.append(key.charAt(i));
            }
            return this.sb.toString();
        }

        private String tzIDFromKey(UResource.Key key) {
            this.sb.setLength(0);
            for (int i = 0; i < key.length(); ++i) {
                char c = key.charAt(i);
                char c2 = c;
                if (c == ':') {
                    c = '/';
                    c2 = c;
                }
                this.sb.append(c2);
            }
            return this.sb.toString();
        }

        UResource.Key createKey(UResource.Key key) {
            return key.clone();
        }

        boolean isMetaZone(UResource.Key key) {
            return key.startsWith(TimeZoneNamesImpl.MZ_PREFIX);
        }

        void load() {
            TimeZoneNamesImpl.this._zoneStrings.getAllItemsWithFallback("", this);
            for (Map.Entry<UResource.Key, ZNamesLoader> entry : this.keyToLoader.entrySet()) {
                ZNamesLoader zNamesLoader = entry.getValue();
                if (zNamesLoader == ZNamesLoader.DUMMY_LOADER) continue;
                if (this.isMetaZone((UResource.Key)((Object)(entry = entry.getKey())))) {
                    entry = this.mzIDFromKey((UResource.Key)((Object)entry));
                    ZNames.createMetaZoneAndPutInCache(TimeZoneNamesImpl.this._mzNamesMap, zNamesLoader.getNames(), (String)((Object)entry));
                    continue;
                }
                entry = this.tzIDFromKey((UResource.Key)((Object)entry));
                ZNames.createTimeZoneAndPutInCache(TimeZoneNamesImpl.this._tzNamesMap, zNamesLoader.getNames(), (String)((Object)entry));
            }
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (value.getType() == 2) {
                    this.consumeNamesTable(key, value, bl);
                }
                ++n;
            }
        }
    }

}

