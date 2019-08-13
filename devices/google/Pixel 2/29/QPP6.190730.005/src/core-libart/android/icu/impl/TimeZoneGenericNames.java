/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SoftCache;
import android.icu.impl.TextTrieMap;
import android.icu.impl.ZoneMeta;
import android.icu.text.LocaleDisplayNames;
import android.icu.text.TimeZoneFormat;
import android.icu.text.TimeZoneNames;
import android.icu.util.BasicTimeZone;
import android.icu.util.Freezable;
import android.icu.util.Output;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.TimeZoneTransition;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TimeZoneGenericNames
implements Serializable,
Freezable<TimeZoneGenericNames> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long DST_CHECK_RANGE = 15897600000L;
    private static Cache GENERIC_NAMES_CACHE = new Cache();
    private static final TimeZoneNames.NameType[] GENERIC_NON_LOCATION_TYPES = new TimeZoneNames.NameType[]{TimeZoneNames.NameType.LONG_GENERIC, TimeZoneNames.NameType.SHORT_GENERIC};
    private static final long serialVersionUID = 2729910342063468417L;
    private volatile transient boolean _frozen;
    private transient ConcurrentHashMap<String, String> _genericLocationNamesMap;
    private transient ConcurrentHashMap<String, String> _genericPartialLocationNamesMap;
    private transient TextTrieMap<NameInfo> _gnamesTrie;
    private transient boolean _gnamesTrieFullyLoaded;
    private final ULocale _locale;
    private transient WeakReference<LocaleDisplayNames> _localeDisplayNamesRef;
    private transient MessageFormat[] _patternFormatters;
    private transient String _region;
    private TimeZoneNames _tznames;

    private TimeZoneGenericNames(ULocale uLocale) {
        this(uLocale, (TimeZoneNames)null);
    }

    public TimeZoneGenericNames(ULocale uLocale, TimeZoneNames timeZoneNames) {
        this._locale = uLocale;
        this._tznames = timeZoneNames;
        this.init();
    }

    /*
     * Enabled aggressive block sorting
     */
    private GenericMatchInfo createGenericMatchInfo(TimeZoneNames.MatchInfo matchInfo) {
        GenericNameType genericNameType;
        TimeZoneFormat.TimeType timeType = TimeZoneFormat.TimeType.UNKNOWN;
        int n = 1.$SwitchMap$android$icu$text$TimeZoneNames$NameType[matchInfo.nameType().ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected MatchInfo name type - ");
                        stringBuilder.append((Object)matchInfo.nameType());
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    genericNameType = GenericNameType.SHORT;
                } else {
                    genericNameType = GenericNameType.SHORT;
                    timeType = TimeZoneFormat.TimeType.STANDARD;
                }
            } else {
                genericNameType = GenericNameType.LONG;
            }
        } else {
            genericNameType = GenericNameType.LONG;
            timeType = TimeZoneFormat.TimeType.STANDARD;
        }
        String string = matchInfo.tzID();
        if (string == null) {
            string = matchInfo.mzID();
            string = this._tznames.getReferenceZoneID(string, this.getTargetRegion());
        }
        return new GenericMatchInfo(genericNameType, string, matchInfo.matchLength(), timeType);
    }

    private Collection<GenericMatchInfo> findLocal(String collection, int n, EnumSet<GenericNameType> object) {
        synchronized (this) {
            GenericNameSearchHandler genericNameSearchHandler;
            block5 : {
                genericNameSearchHandler = new GenericNameSearchHandler((EnumSet<GenericNameType>)object);
                this._gnamesTrie.find((CharSequence)((Object)collection), n, genericNameSearchHandler);
                if (genericNameSearchHandler.getMaxMatchLen() == ((String)((Object)collection)).length() - n || this._gnamesTrieFullyLoaded) break block5;
                object = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null).iterator();
                while (object.hasNext()) {
                    this.loadStrings((String)object.next());
                }
                this._gnamesTrieFullyLoaded = true;
                genericNameSearchHandler.resetResults();
                this._gnamesTrie.find((CharSequence)((Object)collection), n, genericNameSearchHandler);
                collection = genericNameSearchHandler.getMatches();
                return collection;
            }
            collection = genericNameSearchHandler.getMatches();
            return collection;
        }
    }

    private Collection<TimeZoneNames.MatchInfo> findTimeZoneNames(String string, int n, EnumSet<GenericNameType> collection) {
        Object var4_4 = null;
        EnumSet<TimeZoneNames.NameType> enumSet = EnumSet.noneOf(TimeZoneNames.NameType.class);
        if (collection.contains((Object)GenericNameType.LONG)) {
            enumSet.add(TimeZoneNames.NameType.LONG_GENERIC);
            enumSet.add(TimeZoneNames.NameType.LONG_STANDARD);
        }
        if (collection.contains((Object)GenericNameType.SHORT)) {
            enumSet.add(TimeZoneNames.NameType.SHORT_GENERIC);
            enumSet.add(TimeZoneNames.NameType.SHORT_STANDARD);
        }
        collection = var4_4;
        if (!enumSet.isEmpty()) {
            collection = this._tznames.find(string, n, enumSet);
        }
        return collection;
    }

    private String formatGenericNonLocationName(TimeZone object, GenericNameType object2, long l) {
        String string = ZoneMeta.getCanonicalCLDRID((TimeZone)object);
        if (string == null) {
            return null;
        }
        TimeZoneNames.NameType nameType = object2 == GenericNameType.LONG ? TimeZoneNames.NameType.LONG_GENERIC : TimeZoneNames.NameType.SHORT_GENERIC;
        String string2 = this._tznames.getTimeZoneDisplayName(string, nameType);
        if (string2 != null) {
            return string2;
        }
        String string3 = this._tznames.getMetaZoneID(string, l);
        object2 = string2;
        if (string3 != null) {
            int[] arrn;
            boolean bl = false;
            int[] arrn2 = arrn = new int[2];
            arrn2[0] = 0;
            arrn2[1] = 0;
            ((TimeZone)object).getOffset(l, false, arrn);
            if (arrn[1] == 0) {
                bl = true;
                boolean bl2 = true;
                if (object instanceof BasicTimeZone) {
                    object2 = ((BasicTimeZone)(object = (BasicTimeZone)object)).getPreviousTransition(l, true);
                    if (object2 != null && l - ((TimeZoneTransition)object2).getTime() < 15897600000L && ((TimeZoneTransition)object2).getFrom().getDSTSavings() != 0) {
                        bl = false;
                    } else {
                        object = ((BasicTimeZone)object).getNextTransition(l, false);
                        bl = bl2;
                        if (object != null) {
                            bl = bl2;
                            if (((TimeZoneTransition)object).getTime() - l < 15897600000L) {
                                bl = bl2;
                                if (((TimeZoneTransition)object).getTo().getDSTSavings() != 0) {
                                    bl = false;
                                }
                            }
                        }
                    }
                } else {
                    object2 = new int[2];
                    ((TimeZone)object).getOffset(l - 15897600000L, false, (int[])object2);
                    if (object2[1] != false) {
                        bl = false;
                    } else {
                        ((TimeZone)object).getOffset(l + 15897600000L, false, (int[])object2);
                        if (object2[1] != false) {
                            bl = false;
                        }
                    }
                }
            }
            object = string2;
            if (bl) {
                object = nameType == TimeZoneNames.NameType.LONG_GENERIC ? TimeZoneNames.NameType.LONG_STANDARD : TimeZoneNames.NameType.SHORT_STANDARD;
                object2 = this._tznames.getDisplayName(string, (TimeZoneNames.NameType)((Object)object), l);
                object = string2;
                if (object2 != null) {
                    object = object2;
                    if (((String)object2).equalsIgnoreCase(this._tznames.getMetaZoneDisplayName(string3, nameType))) {
                        object = null;
                    }
                }
            }
            object2 = object;
            if (object == null) {
                string2 = this._tznames.getMetaZoneDisplayName(string3, nameType);
                object2 = object;
                if (string2 != null) {
                    object = this._tznames.getReferenceZoneID(string3, this.getTargetRegion());
                    if (object != null && !((String)object).equals(string)) {
                        object = TimeZone.getFrozenTimeZone((String)object);
                        Object object3 = object2 = new int[2];
                        object3[0] = false;
                        object3[1] = false;
                        long l2 = arrn[0];
                        boolean bl3 = true;
                        ((TimeZone)object).getOffset(l2 + l + (long)arrn[1], true, (int[])object2);
                        if (arrn[0] == object2[0] && arrn[1] == object2[1]) {
                            object2 = string2;
                        } else {
                            if (nameType != TimeZoneNames.NameType.LONG_GENERIC) {
                                bl3 = false;
                            }
                            object2 = this.getPartialLocationName(string, string3, bl3, string2);
                        }
                    } else {
                        object2 = string2;
                    }
                }
            }
        }
        return object2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String formatPattern(Pattern object, String ... arrstring) {
        synchronized (this) {
            Object object2;
            MessageFormat[] arrmessageFormat;
            void var2_2;
            int n;
            if (this._patternFormatters == null) {
                this._patternFormatters = new MessageFormat[Pattern.values().length];
            }
            if ((object2 = this._patternFormatters[n = ((Enum)object).ordinal()]) != null) return this._patternFormatters[n].format(var2_2);
            try {
                object2 = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/zone", this._locale);
                arrmessageFormat = new StringBuilder();
                arrmessageFormat.append("zoneStrings/");
                arrmessageFormat.append(((Pattern)((Object)object)).key());
                object = object2 = ((ICUResourceBundle)object2).getStringWithFallback(arrmessageFormat.toString());
            }
            catch (MissingResourceException missingResourceException) {
                object = ((Pattern)((Object)object)).defaultValue();
            }
            arrmessageFormat = this._patternFormatters;
            arrmessageFormat[n] = object2 = new MessageFormat((String)object);
            return this._patternFormatters[n].format(var2_2);
        }
    }

    public static TimeZoneGenericNames getInstance(ULocale uLocale) {
        String string = uLocale.getBaseName();
        return (TimeZoneGenericNames)GENERIC_NAMES_CACHE.getInstance(string, uLocale);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private LocaleDisplayNames getLocaleDisplayNames() {
        synchronized (this) {
            WeakReference<LocaleDisplayNames> weakReference = null;
            if (this._localeDisplayNamesRef != null) {
                weakReference = (LocaleDisplayNames)this._localeDisplayNamesRef.get();
            }
            LocaleDisplayNames localeDisplayNames = weakReference;
            if (weakReference == null) {
                localeDisplayNames = LocaleDisplayNames.getInstance(this._locale);
                weakReference = new WeakReference<LocaleDisplayNames>(localeDisplayNames);
                this._localeDisplayNamesRef = weakReference;
            }
            return localeDisplayNames;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getPartialLocationName(String object, String string, boolean bl, String object2) {
        String string2 = bl ? "L" : "S";
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("&");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append("#");
        ((StringBuilder)charSequence).append(string2);
        charSequence = ((StringBuilder)charSequence).toString();
        string2 = this._genericPartialLocationNamesMap.get(charSequence);
        if (string2 != null) {
            return string2;
        }
        string2 = ZoneMeta.getCanonicalCountry(object);
        if (string2 != null) {
            string = object.equals(this._tznames.getReferenceZoneID(string, string2)) ? this.getLocaleDisplayNames().regionDisplayName(string2) : this._tznames.getExemplarLocationName((String)object);
        } else {
            string = string2 = this._tznames.getExemplarLocationName((String)object);
            if (string2 == null) {
                string = object;
            }
        }
        string = this.formatPattern(Pattern.FALLBACK_FORMAT, new String[]{string, object2});
        synchronized (this) {
            object2 = this._genericPartialLocationNamesMap.putIfAbsent(((String)charSequence).intern(), string.intern());
            if (object2 != null) return object2;
            string2 = object.intern();
            object = bl ? GenericNameType.LONG : GenericNameType.SHORT;
            object2 = new NameInfo(string2, (GenericNameType)((Object)object));
            this._gnamesTrie.put(string, (NameInfo)object2);
            return string;
        }
    }

    private String getTargetRegion() {
        synchronized (this) {
            if (this._region == null) {
                this._region = this._locale.getCountry();
                if (this._region.length() == 0) {
                    this._region = ULocale.addLikelySubtags(this._locale).getCountry();
                    if (this._region.length() == 0) {
                        this._region = "001";
                    }
                }
            }
            String string = this._region;
            return string;
        }
    }

    private void init() {
        if (this._tznames == null) {
            this._tznames = TimeZoneNames.getInstance(this._locale);
        }
        this._genericLocationNamesMap = new ConcurrentHashMap();
        this._genericPartialLocationNamesMap = new ConcurrentHashMap();
        this._gnamesTrie = new TextTrieMap(true);
        this._gnamesTrieFullyLoaded = false;
        String string = ZoneMeta.getCanonicalCLDRID(TimeZone.getDefault());
        if (string != null) {
            this.loadStrings(string);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void loadStrings(String var1_1) {
        // MONITORENTER : this
        if (var1_1 == null) {
            // MONITOREXIT : this
            return;
        }
        if (var1_1.length() == 0) {
            return;
        }
        this.getGenericLocationName(var1_1);
        var2_2 = this._tznames.getAvailableMetaZoneIDs(var1_1).iterator();
        block2 : do lbl-1000: // 3 sources:
        {
            if (!var2_2.hasNext()) {
                // MONITOREXIT : this
                return;
            }
            var3_3 = var2_2.next();
            if (var1_1.equals(this._tznames.getReferenceZoneID(var3_3, this.getTargetRegion()))) ** GOTO lbl-1000
            var4_4 = TimeZoneGenericNames.GENERIC_NON_LOCATION_TYPES;
            var5_5 = var4_4.length;
            var6_6 = 0;
            do {
                if (var6_6 >= var5_5) continue block2;
                var7_7 = var4_4[var6_6];
                var8_8 = this._tznames.getMetaZoneDisplayName(var3_3, var7_7);
                if (var8_8 != null) {
                    var9_9 = var7_7 == TimeZoneNames.NameType.LONG_GENERIC;
                    this.getPartialLocationName(var1_1, var3_3, var9_9, var8_8);
                }
                ++var6_6;
            } while (true);
            break;
        } while (true);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init();
    }

    @Override
    public TimeZoneGenericNames cloneAsThawed() {
        TimeZoneGenericNames timeZoneGenericNames;
        TimeZoneGenericNames timeZoneGenericNames2 = null;
        timeZoneGenericNames2 = timeZoneGenericNames = (TimeZoneGenericNames)super.clone();
        try {
            timeZoneGenericNames._frozen = false;
            timeZoneGenericNames2 = timeZoneGenericNames;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return timeZoneGenericNames2;
    }

    public Collection<GenericMatchInfo> find(String collection, int n, EnumSet<GenericNameType> collection2) {
        if (collection != null && ((String)((Object)collection)).length() != 0 && n >= 0 && n < ((String)((Object)collection)).length()) {
            Object object = this.findLocal((String)((Object)collection), n, (EnumSet<GenericNameType>)collection2);
            collection = this.findTimeZoneNames((String)((Object)collection), n, (EnumSet<GenericNameType>)collection2);
            collection2 = object;
            if (collection != null) {
                Iterator iterator = collection.iterator();
                collection = object;
                do {
                    collection2 = collection;
                    if (!iterator.hasNext()) break;
                    object = (TimeZoneNames.MatchInfo)iterator.next();
                    collection2 = collection;
                    if (collection == null) {
                        collection2 = new LinkedList();
                    }
                    collection2.add((Object)((GenericNameType)((Object)this.createGenericMatchInfo((TimeZoneNames.MatchInfo)object))));
                    collection = collection2;
                } while (true);
            }
            return collection2;
        }
        throw new IllegalArgumentException("bad input text or range");
    }

    public GenericMatchInfo findBestMatch(String object, int n, EnumSet<GenericNameType> object2) {
        block11 : {
            block15 : {
                Object object3;
                Object object4;
                block12 : {
                    if (object == null || ((String)object).length() == 0 || n < 0 || n >= ((String)object).length()) break block11;
                    Iterator<Object> iterator = null;
                    Object object5 = this.findTimeZoneNames((String)object, n, (EnumSet<GenericNameType>)object2);
                    object4 = iterator;
                    if (object5 == null) break block12;
                    object3 = null;
                    Iterator<TimeZoneNames.MatchInfo> iterator2 = object5.iterator();
                    while (iterator2.hasNext()) {
                        block14 : {
                            block13 : {
                                object5 = iterator2.next();
                                if (object3 == null) break block13;
                                object4 = object3;
                                if (((TimeZoneNames.MatchInfo)object5).matchLength() <= ((TimeZoneNames.MatchInfo)object3).matchLength()) break block14;
                            }
                            object4 = object5;
                        }
                        object3 = object4;
                    }
                    object4 = iterator;
                    if (object3 != null) {
                        object4 = object3 = this.createGenericMatchInfo((TimeZoneNames.MatchInfo)object3);
                        if (((GenericMatchInfo)object3).matchLength() == ((String)object).length() - n) {
                            object4 = object3;
                            if (((GenericMatchInfo)object3).timeType != TimeZoneFormat.TimeType.STANDARD) {
                                return object3;
                            }
                        }
                    }
                }
                object2 = this.findLocal((String)object, n, (EnumSet<GenericNameType>)object2);
                object = object4;
                if (object2 == null) break block15;
                object3 = object2.iterator();
                do {
                    block17 : {
                        block16 : {
                            object = object4;
                            if (!object3.hasNext()) break;
                            object2 = (GenericMatchInfo)object3.next();
                            if (object4 == null) break block16;
                            object = object4;
                            if (((GenericMatchInfo)object2).matchLength() < ((GenericMatchInfo)object4).matchLength()) break block17;
                        }
                        object = object2;
                    }
                    object4 = object;
                } while (true);
            }
            return object;
        }
        throw new IllegalArgumentException("bad input text or range");
    }

    @Override
    public TimeZoneGenericNames freeze() {
        this._frozen = true;
        return this;
    }

    public String getDisplayName(TimeZone object, GenericNameType object2, long l) {
        String string = null;
        int n = 1.$SwitchMap$android$icu$impl$TimeZoneGenericNames$GenericNameType[object2.ordinal()];
        if (n != 1) {
            if (n != 2 && n != 3) {
                object2 = string;
            } else {
                string = this.formatGenericNonLocationName((TimeZone)object, (GenericNameType)((Object)object2), l);
                object2 = string;
                if (string == null) {
                    object = ZoneMeta.getCanonicalCLDRID((TimeZone)object);
                    object2 = string;
                    if (object != null) {
                        object2 = this.getGenericLocationName((String)object);
                    }
                }
            }
        } else {
            object = ZoneMeta.getCanonicalCLDRID((TimeZone)object);
            object2 = string;
            if (object != null) {
                object2 = this.getGenericLocationName((String)object);
            }
        }
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getGenericLocationName(String object) {
        if (object == null) return null;
        if (((String)object).length() == 0) {
            return null;
        }
        String string = this._genericLocationNamesMap.get(object);
        if (string != null) {
            if (string.length() != 0) return string;
            return null;
        }
        Output<Boolean> output = new Output<Boolean>();
        String string2 = ZoneMeta.getCanonicalCountry((String)object, output);
        if (string2 != null) {
            if (((Boolean)output.value).booleanValue()) {
                string = this.getLocaleDisplayNames().regionDisplayName(string2);
                string = this.formatPattern(Pattern.REGION_FORMAT, string);
            } else {
                string = this._tznames.getExemplarLocationName((String)object);
                string = this.formatPattern(Pattern.REGION_FORMAT, string);
            }
        }
        if (string == null) {
            this._genericLocationNamesMap.putIfAbsent(((String)object).intern(), "");
            return string;
        }
        synchronized (this) {
            string2 = ((String)object).intern();
            object = this._genericLocationNamesMap.putIfAbsent(string2, string.intern());
            if (object != null) return object;
            object = new NameInfo(string2, GenericNameType.LOCATION);
            this._gnamesTrie.put(string, (NameInfo)object);
            return string;
        }
    }

    @Override
    public boolean isFrozen() {
        return this._frozen;
    }

    public TimeZoneGenericNames setFormatPattern(Pattern pattern, String string) {
        if (!this.isFrozen()) {
            if (!this._genericLocationNamesMap.isEmpty()) {
                this._genericLocationNamesMap = new ConcurrentHashMap();
            }
            if (!this._genericPartialLocationNamesMap.isEmpty()) {
                this._genericPartialLocationNamesMap = new ConcurrentHashMap();
            }
            this._gnamesTrie = null;
            this._gnamesTrieFullyLoaded = false;
            if (this._patternFormatters == null) {
                this._patternFormatters = new MessageFormat[Pattern.values().length];
            }
            this._patternFormatters[pattern.ordinal()] = new MessageFormat(string);
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    private static class Cache
    extends SoftCache<String, TimeZoneGenericNames, ULocale> {
        private Cache() {
        }

        @Override
        protected TimeZoneGenericNames createInstance(String string, ULocale uLocale) {
            return new TimeZoneGenericNames(uLocale).freeze();
        }
    }

    public static class GenericMatchInfo {
        final int matchLength;
        final GenericNameType nameType;
        final TimeZoneFormat.TimeType timeType;
        final String tzID;

        private GenericMatchInfo(GenericNameType genericNameType, String string, int n) {
            this(genericNameType, string, n, TimeZoneFormat.TimeType.UNKNOWN);
        }

        private GenericMatchInfo(GenericNameType genericNameType, String string, int n, TimeZoneFormat.TimeType timeType) {
            this.nameType = genericNameType;
            this.tzID = string;
            this.matchLength = n;
            this.timeType = timeType;
        }

        public int matchLength() {
            return this.matchLength;
        }

        public GenericNameType nameType() {
            return this.nameType;
        }

        public TimeZoneFormat.TimeType timeType() {
            return this.timeType;
        }

        public String tzID() {
            return this.tzID;
        }
    }

    private static class GenericNameSearchHandler
    implements TextTrieMap.ResultHandler<NameInfo> {
        private Collection<GenericMatchInfo> _matches;
        private int _maxMatchLen;
        private EnumSet<GenericNameType> _types;

        GenericNameSearchHandler(EnumSet<GenericNameType> enumSet) {
            this._types = enumSet;
        }

        public Collection<GenericMatchInfo> getMatches() {
            return this._matches;
        }

        public int getMaxMatchLen() {
            return this._maxMatchLen;
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<NameInfo> iterator) {
            while (iterator.hasNext()) {
                NameInfo nameInfo = iterator.next();
                EnumSet<GenericNameType> enumSet = this._types;
                if (enumSet != null && !enumSet.contains((Object)nameInfo.type)) continue;
                enumSet = new GenericMatchInfo(nameInfo.type, nameInfo.tzID, n);
                if (this._matches == null) {
                    this._matches = new LinkedList<GenericMatchInfo>();
                }
                this._matches.add((GenericMatchInfo)((Object)enumSet));
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

    public static enum GenericNameType {
        LOCATION("LONG", "SHORT"),
        LONG(new String[0]),
        SHORT(new String[0]);
        
        String[] _fallbackTypeOf;

        private GenericNameType(String ... arrstring) {
            this._fallbackTypeOf = arrstring;
        }

        public boolean isFallbackTypeOf(GenericNameType arrstring) {
            String string = arrstring.toString();
            arrstring = this._fallbackTypeOf;
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                if (!arrstring[i].equals(string)) continue;
                return true;
            }
            return false;
        }
    }

    private static class NameInfo {
        final GenericNameType type;
        final String tzID;

        NameInfo(String string, GenericNameType genericNameType) {
            this.tzID = string;
            this.type = genericNameType;
        }
    }

    public static enum Pattern {
        REGION_FORMAT("regionFormat", "({0})"),
        FALLBACK_FORMAT("fallbackFormat", "{1} ({0})");
        
        String _defaultVal;
        String _key;

        private Pattern(String string2, String string3) {
            this._key = string2;
            this._defaultVal = string3;
        }

        String defaultValue() {
            return this._defaultVal;
        }

        String key() {
            return this._key;
        }
    }

}

