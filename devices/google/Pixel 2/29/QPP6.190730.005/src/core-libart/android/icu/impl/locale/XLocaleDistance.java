/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.Row;
import android.icu.impl.locale.XCldrStub;
import android.icu.impl.locale.XLikelySubtags;
import android.icu.text.LocaleDisplayNames;
import android.icu.util.LocaleMatcher;
import android.icu.util.Output;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class XLocaleDistance {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ABOVE_THRESHOLD = 100;
    private static final Set<String> ALL_FINAL_REGIONS;
    @Deprecated
    public static final String ANY = "\ufffd";
    static final XCldrStub.Multimap<String, String> CONTAINER_TO_CONTAINED;
    static final XCldrStub.Multimap<String, String> CONTAINER_TO_CONTAINED_FINAL;
    private static final XLocaleDistance DEFAULT;
    static final boolean PRINT_OVERRIDES = false;
    private static final boolean TRACE_DISTANCE = false;
    static final LocaleDisplayNames english;
    private final int defaultLanguageDistance;
    private final int defaultRegionDistance;
    private final int defaultScriptDistance;
    private final DistanceTable languageDesired2Supported;
    private final RegionMapper regionMapper;

    static {
        Collection<String> collection;
        Object object;
        int n;
        int n2;
        Object object2;
        english = LocaleDisplayNames.getInstance(ULocale.ENGLISH);
        CONTAINER_TO_CONTAINED = XLocaleDistance.xGetContainment();
        Object object5 = XCldrStub.TreeMultimap.create();
        for (Map.Entry<String, Set<String>> entry : CONTAINER_TO_CONTAINED.asMap().entrySet()) {
            object2 = entry.getKey();
            for (String string : entry.getValue()) {
                if (CONTAINER_TO_CONTAINED.get(string) != null) continue;
                ((XCldrStub.Multimap)object5).put(object2, string);
            }
        }
        CONTAINER_TO_CONTAINED_FINAL = XCldrStub.ImmutableMultimap.copyOf(object5);
        ALL_FINAL_REGIONS = XCldrStub.ImmutableSet.copyOf(CONTAINER_TO_CONTAINED_FINAL.get("001"));
        object5 = new String[][]{{"$enUS", "AS+GU+MH+MP+PR+UM+US+VI"}, {"$cnsar", "HK+MO"}, {"$americas", "019"}, {"$maghreb", "MA+DZ+TN+LY+MR+EH"}};
        object2 = new String[]{"en", "en-GB", "es", "es-419", "pt-BR", "pt-PT"};
        Object object6 = new String[]{"ar_*_$maghreb", "ar_*_$maghreb", "96"};
        String[] arrstring = new String[]{"ar_*_*", "ar_*_*", "95"};
        Object object3 = new String[]{"en_*_$!enUS", "en_*_$!enUS", "96"};
        Object object4 = new String[]{"es_*_$!americas", "es_*_$!americas", "96"};
        Object object7 = new String[]{"es_*_*", "es_*_*", "95"};
        object6 = new String[][]{object6, {"ar_*_$!maghreb", "ar_*_$!maghreb", "96"}, arrstring, {"en_*_$enUS", "en_*_$enUS", "96"}, object3, {"en_*_*", "en_*_*", "95"}, {"es_*_$americas", "es_*_$americas", "96"}, object4, object7, {"pt_*_$americas", "pt_*_$americas", "96"}, {"pt_*_$!americas", "pt_*_$!americas", "96"}, {"pt_*_*", "pt_*_*", "95"}, {"zh_Hant_$cnsar", "zh_Hant_$cnsar", "96"}, {"zh_Hant_$!cnsar", "zh_Hant_$!cnsar", "96"}, {"zh_Hant_*", "zh_Hant_*", "95"}, {"*_*_*", "*_*_*", "96"}};
        object3 = new RegionMapper.Builder().addParadigms((String[])object2);
        for (String[] arrstring2 : object5) {
            ((RegionMapper.Builder)object3).add(arrstring2[0], arrstring2[1]);
        }
        StringDistanceTable stringDistanceTable = new StringDistanceTable();
        object3 = ((RegionMapper.Builder)object3).build();
        object4 = XCldrStub.Splitter.on('_');
        object7 = new ArrayList[]{new ArrayList(), new ArrayList(), new ArrayList()};
        for (Row.R4<String, String, Integer, Boolean> r4 : XLocaleDistance.xGetLanguageMatcherData()) {
            object = (String)r4.get0();
            Object object8 = (String)r4.get1();
            List<String> list = ((XCldrStub.Splitter)object4).splitToList((String)object);
            List<String> list2 = ((XCldrStub.Splitter)object4).splitToList((String)object8);
            object8 = (Boolean)r4.get3();
            n = ((String)object).equals("*_*") ? 50 : (Integer)r4.get2();
            n2 = list.size();
            if (n2 == 3) continue;
            object7[n2 - 1].add(Row.of(list, list2, n, object8));
        }
        n2 = ((Object[])object7).length;
        for (n = 0; n < n2; ++n) {
            collection = object7[n].iterator();
            while (collection.hasNext()) {
                Row.R4<String, String, Integer, Boolean> r4 = collection.next();
                object2 = (List)r4.get0();
                object5 = (List)r4.get1();
                object = (Integer)r4.get2();
                Boolean bl = (Boolean)r4.get3();
                XLocaleDistance.add(stringDistanceTable, (List<String>)object2, (List<String>)object5, (Integer)object);
                if (bl != Boolean.TRUE && !object2.equals(object5)) {
                    XLocaleDistance.add(stringDistanceTable, (List<String>)object5, (List<String>)object2, (Integer)object);
                }
                XLocaleDistance.printMatchXml((List<String>)object2, (List<String>)object5, (Integer)object, bl);
            }
        }
        n2 = ((String[])object6).length;
        object2 = object6;
        for (n = 0; n < n2; ++n) {
            object5 = object2[n];
            object7 = new ArrayList<String>(((XCldrStub.Splitter)object4).splitToList((String)object5[0]));
            object6 = new ArrayList<String>(((XCldrStub.Splitter)object4).splitToList((String)object5[1]));
            object = 100 - Integer.parseInt((String)object5[2]);
            XLocaleDistance.printMatchXml((List<String>)object7, (List<String>)object6, (Integer)object, false);
            object5 = ((RegionMapper)object3).getIdsFromVariable((String)object7.get(2));
            if (!object5.isEmpty()) {
                collection = ((RegionMapper)object3).getIdsFromVariable((String)object6.get(2));
                if (!collection.isEmpty()) {
                    Iterator iterator = object5.iterator();
                    while (iterator.hasNext()) {
                        object7.set(2, ((String)iterator.next()).toString());
                        object5 = collection.iterator();
                        while (object5.hasNext()) {
                            object6.set(2, ((String)object5.next()).toString());
                            XLocaleDistance.add(stringDistanceTable, (List<String>)object7, (List<String>)object6, (Integer)object);
                            XLocaleDistance.add(stringDistanceTable, (List<String>)object6, (List<String>)object7, (Integer)object);
                        }
                    }
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Bad region variable: ");
                ((StringBuilder)object2).append((String)object6.get(2));
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Bad region variable: ");
            ((StringBuilder)object2).append((String)object7.get(2));
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        object2 = Arrays.asList("*", "*", "*");
        object5 = Arrays.asList("XA", "XB", "XC").iterator();
        while (object5.hasNext()) {
            object6 = Arrays.asList("*", "*", (String)object5.next());
            XLocaleDistance.add(stringDistanceTable, (List<String>)object6, (List<String>)object2, 100);
            XLocaleDistance.add(stringDistanceTable, (List<String>)object2, (List<String>)object6, 100);
        }
        for (n = 1; n <= 8; ++n) {
            object5 = new StringBuilder();
            ((StringBuilder)object5).append("x");
            ((StringBuilder)object5).append(String.valueOf(n));
            object5 = Arrays.asList(((StringBuilder)object5).toString(), "*", "*");
            XLocaleDistance.add(stringDistanceTable, (List<String>)object5, (List<String>)object2, 100);
            XLocaleDistance.add(stringDistanceTable, (List<String>)object2, (List<String>)object5, 100);
        }
        DEFAULT = new XLocaleDistance(stringDistanceTable.compact(), (RegionMapper)object3);
    }

    public XLocaleDistance(DistanceTable object, RegionMapper regionMapper) {
        this.languageDesired2Supported = object;
        this.regionMapper = regionMapper;
        object = (StringDistanceNode)((StringDistanceTable)this.languageDesired2Supported).subtables.get(ANY).get(ANY);
        this.defaultLanguageDistance = ((StringDistanceNode)object).distance;
        object = (StringDistanceNode)((StringDistanceTable)object.distanceTable).subtables.get(ANY).get(ANY);
        this.defaultScriptDistance = ((StringDistanceNode)object).distance;
        this.defaultRegionDistance = ((StringDistanceTable)object.distanceTable).subtables.get((Object)"\ufffd").get((Object)"\ufffd").distance;
    }

    public static void add(StringDistanceTable stringDistanceTable, List<String> list, List<String> list2, int n) {
        int n2 = list.size();
        if (n2 == list2.size() && n2 >= 1 && n2 <= 3) {
            String string = XLocaleDistance.fixAny(list.get(0));
            String string2 = XLocaleDistance.fixAny(list2.get(0));
            if (n2 == 1) {
                stringDistanceTable.addSubtable(string, string2, n);
            } else {
                String string3 = XLocaleDistance.fixAny(list.get(1));
                String string4 = XLocaleDistance.fixAny(list2.get(1));
                if (n2 == 2) {
                    stringDistanceTable.addSubtables(string, string2, string3, string4, n);
                } else {
                    stringDistanceTable.addSubtables(string, string2, string3, string4, XLocaleDistance.fixAny(list.get(2)), XLocaleDistance.fixAny(list2.get(2)), n);
                }
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    private static Set<String> fill(String string, XCldrStub.TreeMultimap<String, String> treeMultimap, XCldrStub.Multimap<String, String> multimap) {
        Set set = treeMultimap.get(string);
        if (set == null) {
            return Collections.emptySet();
        }
        multimap.putAll(string, set);
        set = set.iterator();
        while (set.hasNext()) {
            multimap.putAll(string, (Collection<String>)XLocaleDistance.fill((String)set.next(), treeMultimap, multimap));
        }
        return multimap.get(string);
    }

    private static String fixAny(String string) {
        block0 : {
            if (!"*".equals(string)) break block0;
            string = ANY;
        }
        return string;
    }

    private static String fixedName(List<String> object) {
        String string;
        ArrayList<String> arrayList = new ArrayList<String>((Collection<String>)object);
        int n = arrayList.size();
        object = new StringBuilder();
        if (n >= 3) {
            string = (String)arrayList.get(2);
            if (!string.equals("*") && !string.startsWith("$")) {
                ((StringBuilder)object).append(english.regionDisplayName(string));
            } else {
                ((StringBuilder)object).append(string);
            }
        }
        if (n >= 2) {
            string = (String)arrayList.get(1);
            if (string.equals("*")) {
                ((StringBuilder)object).insert(0, string);
            } else {
                ((StringBuilder)object).insert(0, english.scriptDisplayName(string));
            }
        }
        if (n >= 1) {
            string = (String)arrayList.get(0);
            if (string.equals("*")) {
                ((StringBuilder)object).insert(0, string);
            } else {
                ((StringBuilder)object).insert(0, english.languageDisplayName(string));
            }
        }
        return XCldrStub.CollectionUtilities.join(arrayList, "; ");
    }

    static Set<String> getContainingMacrosFor(Collection<String> collection, Set<String> set) {
        set.clear();
        for (Map.Entry<String, Set<String>> entry : CONTAINER_TO_CONTAINED.asMap().entrySet()) {
            if (!collection.containsAll((Collection)entry.getValue())) continue;
            set.add(entry.getKey());
        }
        return set;
    }

    public static XLocaleDistance getDefault() {
        return DEFAULT;
    }

    public static <K, V> XCldrStub.Multimap<K, V> invertMap(Map<V, K> map) {
        return XCldrStub.Multimaps.invertFrom(XCldrStub.Multimaps.forMap(map), XCldrStub.LinkedHashMultimap.create());
    }

    public static void main(String[] object) {
        object = XLocaleDistance.getDefault().languageDesired2Supported;
        if (object.equals(((DistanceTable)object).compact())) {
            return;
        }
        throw new IllegalArgumentException("Compaction isn't equal");
    }

    private static Map newMap() {
        return new TreeMap();
    }

    private static void printMatchXml(List<String> list, List<String> list2, Integer n, Boolean bl) {
    }

    private static XCldrStub.Multimap<String, String> xGetContainment() {
        XCldrStub.TreeMultimap<String, String> treeMultimap = XCldrStub.TreeMultimap.create();
        treeMultimap.putAll("001", "019", "002", "150", "142", "009").putAll("011", "BF", "BJ", "CI", "CV", "GH", "GM", "GN", "GW", "LR", "ML", "MR", "NE", "NG", "SH", "SL", "SN", "TG").putAll("013", "BZ", "CR", "GT", "HN", "MX", "NI", "PA", "SV").putAll("014", "BI", "DJ", "ER", "ET", "KE", "KM", "MG", "MU", "MW", "MZ", "RE", "RW", "SC", "SO", "SS", "TZ", "UG", "YT", "ZM", "ZW").putAll("142", "145", "143", "030", "034", "035").putAll("143", "TM", "TJ", "KG", "KZ", "UZ").putAll("145", "AE", "AM", "AZ", "BH", "CY", "GE", "IL", "IQ", "JO", "KW", "LB", "OM", "PS", "QA", "SA", "SY", "TR", "YE", "NT", "YD").putAll("015", "DZ", "EG", "EH", "LY", "MA", "SD", "TN", "EA", "IC").putAll("150", "154", "155", "151", "039").putAll("151", "BG", "BY", "CZ", "HU", "MD", "PL", "RO", "RU", "SK", "UA", "SU").putAll("154", "GG", "IM", "JE", "AX", "DK", "EE", "FI", "FO", "GB", "IE", "IS", "LT", "LV", "NO", "SE", "SJ").putAll("155", "AT", "BE", "CH", "DE", "FR", "LI", "LU", "MC", "NL", "DD", "FX").putAll("017", "AO", "CD", "CF", "CG", "CM", "GA", "GQ", "ST", "TD", "ZR").putAll("018", "BW", "LS", "NA", "SZ", "ZA").putAll("019", "021", "013", "029", "005", "003", "419").putAll("002", "015", "011", "017", "014", "018").putAll("021", "BM", "CA", "GL", "PM", "US").putAll("029", "AG", "AI", "AW", "BB", "BL", "BQ", "BS", "CU", "CW", "DM", "DO", "GD", "GP", "HT", "JM", "KN", "KY", "LC", "MF", "MQ", "MS", "PR", "SX", "TC", "TT", "VC", "VG", "VI", "AN").putAll("003", "021", "013", "029").putAll("030", "CN", "HK", "JP", "KP", "KR", "MN", "MO", "TW").putAll("035", "BN", "ID", "KH", "LA", "MM", "MY", "PH", "SG", "TH", "TL", "VN", "BU", "TP").putAll("039", "AD", "AL", "BA", "ES", "GI", "GR", "HR", "IT", "ME", "MK", "MT", "RS", "PT", "SI", "SM", "VA", "XK", "CS", "YU").putAll("419", "013", "029", "005").putAll("005", "AR", "BO", "BR", "CL", "CO", "EC", "FK", "GF", "GY", "PE", "PY", "SR", "UY", "VE").putAll("053", "AU", "NF", "NZ").putAll("054", "FJ", "NC", "PG", "SB", "VU").putAll("057", "FM", "GU", "KI", "MH", "MP", "NR", "PW").putAll("061", "AS", "CK", "NU", "PF", "PN", "TK", "TO", "TV", "WF", "WS").putAll("034", "AF", "BD", "BT", "IN", "IR", "LK", "MV", "NP", "PK").putAll("009", "053", "054", "057", "061", "QO").putAll("QO", "AQ", "BV", "CC", "CX", "GS", "HM", "IO", "TF", "UM", "AC", "CP", "DG", "TA");
        XCldrStub.TreeMultimap<String, String> treeMultimap2 = XCldrStub.TreeMultimap.create();
        XLocaleDistance.fill("001", treeMultimap, treeMultimap2);
        return XCldrStub.ImmutableMultimap.copyOf(treeMultimap2);
    }

    private static List<Row.R4<String, String, Integer, Boolean>> xGetLanguageMatcherData() {
        ArrayList<Row.R4> arrayList = new ArrayList<Row.R4>();
        UResourceBundleIterator uResourceBundleIterator = ((ICUResourceBundle)LocaleMatcher.getICUSupplementalData().findTopLevel("languageMatchingNew").get("written")).getIterator();
        while (uResourceBundleIterator.hasNext()) {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)uResourceBundleIterator.next();
            boolean bl = iCUResourceBundle.getSize() > 3 && "1".equals(iCUResourceBundle.getString(3));
            arrayList.add((Row.R4)Row.of(iCUResourceBundle.getString(0), iCUResourceBundle.getString(1), Integer.parseInt(iCUResourceBundle.getString(2)), bl).freeze());
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static Map<String, String> xGetMatchVariables() {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)LocaleMatcher.getICUSupplementalData().findTopLevel("languageMatchingInfo").get("written").get("matchVariable");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Enumeration<String> enumeration = iCUResourceBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            hashMap.put(string, iCUResourceBundle.getString(string));
        }
        return Collections.unmodifiableMap(hashMap);
    }

    private static Set<String> xGetParadigmLocales() {
        return Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(((ICUResourceBundle)LocaleMatcher.getICUSupplementalData().findTopLevel("languageMatchingInfo").get("written").get("paradigmLocales")).getStringArray())));
    }

    public int distance(ULocale uLocale, ULocale object, int n, DistanceOption distanceOption) {
        object = XLikelySubtags.LSR.fromMaximalized((ULocale)object);
        return this.distanceRaw(XLikelySubtags.LSR.fromMaximalized(uLocale), (XLikelySubtags.LSR)object, n, distanceOption);
    }

    public int distanceRaw(XLikelySubtags.LSR lSR, XLikelySubtags.LSR lSR2, int n, DistanceOption distanceOption) {
        return this.distanceRaw(lSR.language, lSR2.language, lSR.script, lSR2.script, lSR.region, lSR2.region, n, distanceOption);
    }

    public int distanceRaw(String set, String set2, String object, String object2, String object3, String string, int n, DistanceOption object4) {
        int n2;
        Output<DistanceTable> output = new Output<DistanceTable>();
        int n3 = this.languageDesired2Supported.getDistance((String)((Object)set), (String)((Object)set2), output, true);
        int n4 = object4 == DistanceOption.SCRIPT_FIRST ? 1 : 0;
        int n5 = n3;
        if (n4 != 0) {
            n5 = n3 >> 2;
        }
        if (n5 < 0) {
            n3 = 0;
        } else {
            n3 = n5;
            if (n5 >= n) {
                return 100;
            }
        }
        n5 = n2 = ((DistanceTable)output.value).getDistance((String)object, (String)object2, output, true);
        if (n4 != 0) {
            n5 = n2 >> 1;
        }
        if ((n2 = n3 + n5) >= n) {
            return 100;
        }
        if (((String)object3).equals(string)) {
            return n2;
        }
        object4 = this.regionMapper.toId((String)object3);
        object2 = this.regionMapper.toId(string);
        set = ((String)object4).isEmpty() ? this.regionMapper.macroToPartitions.get((String)object3) : null;
        set2 = ((String)object2).isEmpty() ? this.regionMapper.macroToPartitions.get(string) : null;
        if (set == null && set2 == null) {
            n5 = ((DistanceTable)output.value).getDistance((String)object4, (String)object2, null, false);
        } else {
            n5 = 0;
            object = set;
            if (set == null) {
                object = Collections.singleton(object4);
            }
            set = set2;
            if (set2 == null) {
                set = Collections.singleton(object2);
            }
            object = object.iterator();
            set2 = output;
            object2 = set;
            while (object.hasNext()) {
                string = (String)object.next();
                object3 = object2.iterator();
                set = set2;
                set2 = object;
                object = object2;
                while (object3.hasNext()) {
                    object2 = (String)object3.next();
                    n3 = ((DistanceTable)((Output)set).value).getDistance(string, (String)object2, null, false);
                    n4 = n5;
                    if (n5 < n3) {
                        n4 = n3;
                    }
                    n5 = n4;
                }
                object2 = object;
                object = set2;
                set2 = set;
            }
        }
        n5 = n2 + n5;
        n = n5 >= n ? 100 : n5;
        return n;
    }

    public int getDefaultLanguageDistance() {
        return this.defaultLanguageDistance;
    }

    public int getDefaultRegionDistance() {
        return this.defaultRegionDistance;
    }

    public int getDefaultScriptDistance() {
        return this.defaultScriptDistance;
    }

    public Set<ULocale> getParadigms() {
        return this.regionMapper.paradigms;
    }

    @Deprecated
    public StringDistanceTable internalGetDistanceTable() {
        return (StringDistanceTable)this.languageDesired2Supported;
    }

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.regionMapper);
        stringBuilder.append("\n");
        stringBuilder.append(this.languageDesired2Supported.toString(bl));
        return stringBuilder.toString();
    }

    static class AddSub
    implements XCldrStub.Predicate<DistanceNode> {
        private final String desiredSub;
        private final CopyIfEmpty r;
        private final String supportedSub;

        AddSub(String string, String string2, StringDistanceTable stringDistanceTable) {
            this.r = new CopyIfEmpty(stringDistanceTable);
            this.desiredSub = string;
            this.supportedSub = string2;
        }

        @Override
        public boolean test(DistanceNode distanceNode) {
            if (distanceNode != null) {
                ((StringDistanceNode)distanceNode).addSubtables(this.desiredSub, this.supportedSub, this.r);
                return true;
            }
            throw new IllegalArgumentException("bad structure");
        }
    }

    static class CompactAndImmutablizer
    extends IdMakerFull<Object> {
        CompactAndImmutablizer() {
        }

        DistanceNode compact(DistanceNode distanceNode) {
            if (this.toId(distanceNode) != null) {
                return this.intern(distanceNode);
            }
            DistanceTable distanceTable = distanceNode.getDistanceTable();
            if (distanceTable != null && !distanceTable.isEmpty()) {
                return new StringDistanceNode(distanceNode.distance, this.compact((StringDistanceTable)((StringDistanceNode)distanceNode).distanceTable));
            }
            return new DistanceNode(distanceNode.distance);
        }

        StringDistanceTable compact(StringDistanceTable stringDistanceTable) {
            if (this.toId(stringDistanceTable) != null) {
                return this.intern(stringDistanceTable);
            }
            return new StringDistanceTable(this.compact(stringDistanceTable.subtables, 0));
        }

        <K, T> Map<K, T> compact(Map<K, T> map, int n) {
            if (this.toId(map) != null) {
                return this.intern(map);
            }
            LinkedHashMap<K, Object> linkedHashMap = new LinkedHashMap<K, Object>();
            for (Map.Entry<K, T> entry : map.entrySet()) {
                map = entry.getValue();
                if (map instanceof Map) {
                    linkedHashMap.put(entry.getKey(), this.compact(map, n + 1));
                    continue;
                }
                linkedHashMap.put(entry.getKey(), this.compact((DistanceNode)((Object)map)));
            }
            return XCldrStub.ImmutableMap.copyOf(linkedHashMap);
        }
    }

    static class CopyIfEmpty
    implements XCldrStub.Predicate<DistanceNode> {
        private final StringDistanceTable toCopy;

        CopyIfEmpty(StringDistanceTable stringDistanceTable) {
            this.toCopy = stringDistanceTable;
        }

        @Override
        public boolean test(DistanceNode object) {
            object = (StringDistanceTable)((DistanceNode)object).getDistanceTable();
            if (((StringDistanceTable)object).subtables.isEmpty()) {
                ((StringDistanceTable)object).copy(this.toCopy);
            }
            return true;
        }
    }

    @Deprecated
    public static class DistanceNode {
        final int distance;

        public DistanceNode(int n) {
            this.distance = n;
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object != null && object.getClass() == this.getClass() && this.distance == ((DistanceNode)object).distance;
            return bl;
        }

        public DistanceTable getDistanceTable() {
            return null;
        }

        public int hashCode() {
            return this.distance;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\ndistance: ");
            stringBuilder.append(this.distance);
            return stringBuilder.toString();
        }
    }

    public static enum DistanceOption {
        REGION_FIRST,
        SCRIPT_FIRST;
        
    }

    @Deprecated
    public static abstract class DistanceTable {
        public DistanceTable compact() {
            return this;
        }

        abstract Set<String> getCloser(int var1);

        abstract int getDistance(String var1, String var2, Output<DistanceTable> var3, boolean var4);

        public Map<String, Set<String>> getInternalMatches() {
            return null;
        }

        public DistanceNode getInternalNode(String string, String string2) {
            return null;
        }

        public boolean isEmpty() {
            return true;
        }

        abstract String toString(boolean var1);
    }

    static class IdMakerFull<T>
    implements IdMapper<T, Integer> {
        private final List<T> intToObject = new ArrayList<T>();
        final String name;
        private final Map<T, Integer> objectToInt = new HashMap<T, Integer>();

        IdMakerFull() {
            this("unnamed");
        }

        IdMakerFull(String string) {
            this.name = string;
        }

        IdMakerFull(String string, T t) {
            this(string);
            this.add(t);
        }

        public Integer add(T t) {
            Integer n = this.objectToInt.get(t);
            if (n == null) {
                n = this.intToObject.size();
                this.objectToInt.put(t, n);
                this.intToObject.add(t);
                return n;
            }
            return n;
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object != null && object.getClass() == this.getClass() && this.intToObject.equals(((IdMakerFull)object).intToObject);
            return bl;
        }

        public T fromId(int n) {
            return this.intToObject.get(n);
        }

        public Integer getOldAndAdd(T t) {
            Integer n = this.objectToInt.get(t);
            if (n == null) {
                int n2 = this.intToObject.size();
                this.objectToInt.put(t, n2);
                this.intToObject.add(t);
            }
            return n;
        }

        public int hashCode() {
            return this.intToObject.hashCode();
        }

        public T intern(T t) {
            return this.fromId(this.add(t));
        }

        public int size() {
            return this.intToObject.size();
        }

        @Override
        public Integer toId(T t) {
            return this.objectToInt.get(t);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.size());
            stringBuilder.append(": ");
            stringBuilder.append(this.intToObject);
            return stringBuilder.toString();
        }
    }

    private static interface IdMapper<K, V> {
        public V toId(K var1);
    }

    static class RegionMapper
    implements IdMapper<String, String> {
        final XCldrStub.Multimap<String, String> macroToPartitions;
        final Set<ULocale> paradigms;
        final Map<String, String> regionToPartition;
        final XCldrStub.Multimap<String, String> variableToPartition;

        private RegionMapper(XCldrStub.Multimap<String, String> multimap, Map<String, String> map, XCldrStub.Multimap<String, String> multimap2, Set<ULocale> set) {
            this.variableToPartition = XCldrStub.ImmutableMultimap.copyOf(multimap);
            this.regionToPartition = XCldrStub.ImmutableMap.copyOf(map);
            this.macroToPartitions = XCldrStub.ImmutableMultimap.copyOf(multimap2);
            this.paradigms = XCldrStub.ImmutableSet.copyOf(set);
        }

        public Collection<String> getIdsFromVariable(String string) {
            if (string.equals("*")) {
                return Collections.singleton("*");
            }
            Set<String> set = this.variableToPartition.get(string);
            if (set != null && !set.isEmpty()) {
                return set;
            }
            set = new StringBuilder();
            ((StringBuilder)((Object)set)).append("Variable not defined: ");
            ((StringBuilder)((Object)set)).append(string);
            throw new IllegalArgumentException(((StringBuilder)((Object)set)).toString());
        }

        public Set<String> regions() {
            return this.regionToPartition.keySet();
        }

        @Override
        public String toId(String string) {
            block0 : {
                if ((string = this.regionToPartition.get(string)) != null) break block0;
                string = "";
            }
            return string;
        }

        public String toString() {
            Map.Entry<String, Set<String>> entry22 = XCldrStub.Multimaps.invertFrom(this.variableToPartition, XCldrStub.TreeMultimap.create());
            Object object2 = XCldrStub.TreeMultimap.create();
            for (Map.Entry<String, String> entry : this.regionToPartition.entrySet()) {
                ((XCldrStub.Multimap)object2).put(entry.getValue(), entry.getKey());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Partition \u27a0 Variables \u27a0 Regions (final)");
            for (Map.Entry entry : ((XCldrStub.Multimap)((Object)entry22)).asMap().entrySet()) {
                stringBuilder.append('\n');
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append((String)entry.getKey());
                stringBuilder2.append("\t");
                stringBuilder2.append(entry.getValue());
                stringBuilder2.append("\t");
                stringBuilder2.append(((XCldrStub.Multimap)object2).get((String)entry.getKey()));
                stringBuilder.append(stringBuilder2.toString());
            }
            stringBuilder.append("\nMacro \u27a0 Partitions");
            for (Map.Entry<String, Set<String>> entry22 : this.macroToPartitions.asMap().entrySet()) {
                stringBuilder.append('\n');
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(entry22.getKey());
                ((StringBuilder)object2).append("\t");
                ((StringBuilder)object2).append(entry22.getValue());
                stringBuilder.append(((StringBuilder)object2).toString());
            }
            return stringBuilder.toString();
        }

        public Set<String> variables() {
            return this.variableToPartition.keySet();
        }

        static class Builder {
            private final Set<ULocale> paradigms = new LinkedHashSet<ULocale>();
            private final RegionSet regionSet = new RegionSet();
            private final XCldrStub.Multimap<String, String> regionToRawPartition = XCldrStub.TreeMultimap.create();

            Builder() {
            }

            void add(String string, String iterator) {
                CharSequence charSequence2;
                for (CharSequence charSequence2 : this.regionSet.parseSet((String)((Object)iterator))) {
                    this.regionToRawPartition.put((String)charSequence2, string);
                }
                iterator = this.regionSet.inverse();
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("$!");
                ((StringBuilder)charSequence2).append(string.substring(1));
                string = ((StringBuilder)charSequence2).toString();
                iterator = iterator.iterator();
                while (iterator.hasNext()) {
                    charSequence2 = (String)iterator.next();
                    this.regionToRawPartition.put((String)charSequence2, string);
                }
            }

            public Builder addParadigms(String ... arrstring) {
                for (String string : arrstring) {
                    this.paradigms.add(new ULocale(string));
                }
                return this;
            }

            RegionMapper build() {
                Object object;
                Object object2 = new IdMakerFull("partition");
                XCldrStub.TreeMultimap<String, String> treeMultimap = XCldrStub.TreeMultimap.create();
                TreeMap<String, String> treeMap = new TreeMap<String, String>();
                XCldrStub.TreeMultimap<String, String> treeMultimap2 = XCldrStub.TreeMultimap.create();
                for (Map.Entry<String, Set<String>> entry : this.regionToRawPartition.asMap().entrySet()) {
                    object = entry.getKey();
                    Collection collection = entry.getValue();
                    String object3 = String.valueOf((char)(((IdMakerFull)object2).add(collection) + 945));
                    treeMap.put((String)object, object3);
                    treeMultimap2.put(object3, (String)object);
                    object = collection.iterator();
                    while (object.hasNext()) {
                        treeMultimap.put((String)object.next(), object3);
                    }
                }
                object2 = XCldrStub.TreeMultimap.create();
                for (Map.Entry<String, Set<String>> entry : CONTAINER_TO_CONTAINED.asMap().entrySet()) {
                    object = entry.getKey();
                    for (Map.Entry entry2 : treeMultimap2.asMap().entrySet()) {
                        String string = (String)entry2.getKey();
                        if (Collections.disjoint((Collection)entry.getValue(), entry2.getValue())) continue;
                        ((XCldrStub.Multimap)object2).put(object, string);
                    }
                }
                return new RegionMapper(treeMultimap, treeMap, (XCldrStub.Multimap)object2, this.paradigms);
            }
        }

    }

    private static class RegionSet {
        private Operation operation = null;
        private final Set<String> tempRegions = new TreeSet<String>();

        private RegionSet() {
        }

        private void add(String string, int n, int n2) {
            if (n2 > n) {
                string = string.substring(n, n2);
                this.changeSet(this.operation, string);
            }
        }

        private void changeSet(Operation operation, String string) {
            Set<String> set = CONTAINER_TO_CONTAINED_FINAL.get(string);
            if (set != null && !set.isEmpty()) {
                if (Operation.add == operation) {
                    this.tempRegions.addAll(set);
                } else {
                    this.tempRegions.removeAll(set);
                }
            } else if (Operation.add == operation) {
                this.tempRegions.add(string);
            } else {
                this.tempRegions.remove(string);
            }
        }

        private Set<String> inverse() {
            TreeSet<String> treeSet = new TreeSet<String>(ALL_FINAL_REGIONS);
            treeSet.removeAll(this.tempRegions);
            return treeSet;
        }

        private Set<String> parseSet(String string) {
            int n;
            this.operation = Operation.add;
            int n2 = 0;
            this.tempRegions.clear();
            for (n = 0; n < string.length(); ++n) {
                char c = string.charAt(n);
                if (c != '+') {
                    if (c != '-') continue;
                    this.add(string, n2, n);
                    n2 = n + 1;
                    this.operation = Operation.remove;
                    continue;
                }
                this.add(string, n2, n);
                n2 = n + 1;
                this.operation = Operation.add;
            }
            this.add(string, n2, n);
            return this.tempRegions;
        }

        private static enum Operation {
            add,
            remove;
            
        }

    }

    static class StringDistanceNode
    extends DistanceNode {
        final DistanceTable distanceTable;

        StringDistanceNode(int n) {
            this(n, new StringDistanceTable());
        }

        public StringDistanceNode(int n, DistanceTable distanceTable) {
            super(n);
            this.distanceTable = distanceTable;
        }

        public void addSubtables(String string, String string2, CopyIfEmpty copyIfEmpty) {
            ((StringDistanceTable)this.distanceTable).addSubtables(string, string2, copyIfEmpty);
        }

        public void copyTables(StringDistanceTable stringDistanceTable) {
            if (stringDistanceTable != null) {
                ((StringDistanceTable)this.distanceTable).copy(stringDistanceTable);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null) return false;
            if (object.getClass() != this.getClass()) return false;
            int n = this.distance;
            object = (StringDistanceNode)object;
            if (n != ((StringDistanceNode)object).distance) return false;
            if (!Objects.equals(this.distanceTable, ((StringDistanceNode)object).distanceTable)) return false;
            if (!super.equals(object)) return false;
            return true;
        }

        @Override
        public DistanceTable getDistanceTable() {
            return this.distanceTable;
        }

        @Override
        public int hashCode() {
            return this.distance ^ Objects.hashCode(this.distanceTable);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("distance: ");
            stringBuilder.append(this.distance);
            stringBuilder.append("\n");
            stringBuilder.append(this.distanceTable);
            return stringBuilder.toString();
        }
    }

    @Deprecated
    public static class StringDistanceTable
    extends DistanceTable {
        final Map<String, Map<String, DistanceNode>> subtables;

        StringDistanceTable() {
            this(XLocaleDistance.newMap());
        }

        StringDistanceTable(Map<String, Map<String, DistanceNode>> map) {
            this.subtables = map;
        }

        private DistanceNode getNode(String object, String string) {
            if ((object = this.subtables.get(object)) == null) {
                return null;
            }
            return (DistanceNode)object.get(string);
        }

        DistanceNode addSubtable(String object, String string, int n) {
            Map map;
            Map map2 = map = this.subtables.get(object);
            if (map == null) {
                Map<String, Map<String, DistanceNode>> map3 = this.subtables;
                map2 = map = XLocaleDistance.newMap();
                map3.put((String)object, map);
            }
            if ((object = map2.get(string)) != null) {
                return object;
            }
            object = new StringDistanceNode(n);
            map2.put(string, object);
            return object;
        }

        public void addSubtables(String object, String string, XCldrStub.Predicate<DistanceNode> predicate) {
            Object object2;
            Object object3 = object2 = this.getNode((String)object, string);
            if (object2 == null) {
                object2 = new Output();
                object3 = object = this.addSubtable((String)object, string, this.getDistance((String)object, string, (Output<DistanceTable>)object2, true));
                if (((Output)object2).value != null) {
                    ((StringDistanceNode)object).copyTables((StringDistanceTable)((Output)object2).value);
                    object3 = object;
                }
            }
            predicate.test((DistanceNode)object3);
        }

        public void addSubtables(String string, String string2, String string3, String string4, int n) {
            boolean bl = false;
            for (Map.Entry<String, Map<String, DistanceNode>> entry : this.subtables.entrySet()) {
                boolean bl2;
                block5 : {
                    boolean bl3;
                    block4 : {
                        bl3 = string.equals(entry.getKey());
                        if (bl3) break block4;
                        bl2 = bl;
                        if (!string.equals(XLocaleDistance.ANY)) break block5;
                    }
                    entry = entry.getValue().entrySet().iterator();
                    do {
                        bl2 = bl;
                        if (!entry.hasNext()) break;
                        Map.Entry entry2 = (Map.Entry)entry.next();
                        boolean bl4 = string2.equals((String)entry2.getKey());
                        bl2 = bl3 && bl4;
                        bl |= bl2;
                        if (!bl4 && !string2.equals(XLocaleDistance.ANY)) continue;
                        ((StringDistanceTable)((DistanceNode)entry2.getValue()).getDistanceTable()).addSubtable(string3, string4, n);
                    } while (true);
                }
                bl = bl2;
            }
            StringDistanceTable stringDistanceTable = new StringDistanceTable();
            stringDistanceTable.addSubtable(string3, string4, n);
            this.addSubtables(string, string2, new CopyIfEmpty(stringDistanceTable));
        }

        public void addSubtables(String string, String string2, String string3, String string4, String string5, String string6, int n) {
            boolean bl = false;
            for (Map.Entry<String, Map<String, DistanceNode>> entry : this.subtables.entrySet()) {
                boolean bl2;
                block5 : {
                    boolean bl3;
                    block4 : {
                        bl3 = string.equals(entry.getKey());
                        if (bl3) break block4;
                        bl2 = bl;
                        if (!string.equals(XLocaleDistance.ANY)) break block5;
                    }
                    Iterator<Map.Entry<String, DistanceNode>> iterator = entry.getValue().entrySet().iterator();
                    do {
                        bl2 = bl;
                        if (!iterator.hasNext()) break;
                        Map.Entry<String, DistanceNode> entry2 = iterator.next();
                        boolean bl4 = string2.equals(entry2.getKey());
                        bl2 = bl3 && bl4;
                        bl |= bl2;
                        if (!bl4 && !string2.equals(XLocaleDistance.ANY)) continue;
                        ((StringDistanceTable)((StringDistanceNode)entry2.getValue()).distanceTable).addSubtables(string3, string4, string5, string6, n);
                    } while (true);
                }
                bl = bl2;
            }
            StringDistanceTable stringDistanceTable = new StringDistanceTable();
            stringDistanceTable.addSubtable(string5, string6, n);
            this.addSubtables(string, string2, new AddSub(string3, string4, stringDistanceTable));
        }

        @Override
        public StringDistanceTable compact() {
            return new CompactAndImmutablizer().compact(this);
        }

        public void copy(StringDistanceTable object) {
            for (Map.Entry<String, Map<String, DistanceNode>> entry : ((StringDistanceTable)object).subtables.entrySet()) {
                for (Map.Entry<String, DistanceNode> entry2 : entry.getValue().entrySet()) {
                    DistanceNode distanceNode = entry2.getValue();
                    this.addSubtable(entry.getKey(), entry2.getKey(), distanceNode.distance);
                }
            }
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object != null && object.getClass() == this.getClass() && this.subtables.equals(((StringDistanceTable)object).subtables);
            return bl;
        }

        @Override
        public Set<String> getCloser(int n) {
            HashSet<String> hashSet = new HashSet<String>();
            block0 : for (Map.Entry<String, Map<String, DistanceNode>> entry : this.subtables.entrySet()) {
                String string = entry.getKey();
                Iterator<Map.Entry<String, DistanceNode>> object = entry.getValue().entrySet().iterator();
                while (object.hasNext()) {
                    if (object.next().getValue().distance >= n) continue;
                    hashSet.add(string);
                    continue block0;
                }
            }
            return hashSet;
        }

        @Override
        public int getDistance(String string, String string2, Output<DistanceTable> output, boolean bl) {
            Object object;
            int n = 0;
            Object object2 = object = this.subtables.get(string);
            if (object == null) {
                object2 = this.subtables.get(XLocaleDistance.ANY);
                n = 1;
            }
            Object object3 = object2.get(string2);
            int n2 = n;
            object = object3;
            if (object3 == null) {
                object2 = object = object2.get(XLocaleDistance.ANY);
                if (object == null) {
                    object2 = object;
                    if (n == 0) {
                        object3 = this.subtables.get(XLocaleDistance.ANY);
                        object2 = object = (DistanceNode)object3.get(string2);
                        if (object == null) {
                            object2 = (DistanceNode)object3.get(XLocaleDistance.ANY);
                        }
                    }
                }
                n2 = 1;
                object = object2;
            }
            if (output != null) {
                output.value = ((StringDistanceNode)object).distanceTable;
            }
            n = bl && n2 != 0 && string.equals(string2) ? 0 : ((DistanceNode)object).distance;
            return n;
        }

        public Integer getInternalDistance(String object, String object2) {
            Map<String, DistanceNode> map = this.subtables.get(object);
            object = null;
            if (map == null) {
                return null;
            }
            if ((object2 = map.get(object2)) != null) {
                object = ((DistanceNode)object2).distance;
            }
            return object;
        }

        @Override
        public Map<String, Set<String>> getInternalMatches() {
            LinkedHashMap<String, Set<String>> linkedHashMap = new LinkedHashMap<String, Set<String>>();
            for (Map.Entry<String, Map<String, DistanceNode>> entry : this.subtables.entrySet()) {
                linkedHashMap.put(entry.getKey(), new LinkedHashSet<String>(entry.getValue().keySet()));
            }
            return linkedHashMap;
        }

        @Override
        public DistanceNode getInternalNode(String object, String string) {
            if ((object = this.subtables.get(object)) == null) {
                return null;
            }
            return (DistanceNode)object.get(string);
        }

        public int hashCode() {
            return this.subtables.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.subtables.isEmpty();
        }

        public String toString() {
            return this.toString(false);
        }

        @Override
        public String toString(boolean bl) {
            return this.toString(bl, "", new IdMakerFull<Object>("interner"), new StringBuilder()).toString();
        }

        public StringBuilder toString(boolean bl, String string, IdMakerFull<Object> idMakerFull, StringBuilder stringBuilder) {
            String string2 = string.isEmpty() ? "" : "\t";
            Object object = bl ? idMakerFull.getOldAndAdd(this.subtables) : null;
            int n = 10;
            if (object != null) {
                stringBuilder.append(string2);
                stringBuilder.append('#');
                stringBuilder.append(object);
                stringBuilder.append('\n');
            } else {
                Iterator<Map.Entry<String, Map<String, DistanceNode>>> iterator = this.subtables.entrySet().iterator();
                int n2 = n;
                while (iterator.hasNext()) {
                    object = iterator.next();
                    Object object2 = (Map)object.getValue();
                    stringBuilder.append(string2);
                    stringBuilder.append((String)object.getKey());
                    Object object3 = "\t";
                    object = bl ? idMakerFull.getOldAndAdd(object2) : null;
                    if (object != null) {
                        stringBuilder.append("\t");
                        stringBuilder.append('#');
                        stringBuilder.append(object);
                        stringBuilder.append((char)n2);
                        n = n2;
                    } else {
                        object2 = object2.entrySet().iterator();
                        object = object3;
                        n = n2;
                        while (object2.hasNext()) {
                            Map.Entry entry = (Map.Entry)object2.next();
                            object3 = (DistanceNode)entry.getValue();
                            stringBuilder.append((String)object);
                            stringBuilder.append((String)entry.getKey());
                            object = bl ? idMakerFull.getOldAndAdd(object3) : null;
                            if (object != null) {
                                stringBuilder.append('\t');
                                stringBuilder.append('#');
                                stringBuilder.append(object);
                                stringBuilder.append('\n');
                            } else {
                                stringBuilder.append('\t');
                                stringBuilder.append(((DistanceNode)object3).distance);
                                object3 = ((DistanceNode)object3).getDistanceTable();
                                if (object3 != null) {
                                    object = bl ? idMakerFull.getOldAndAdd(object3) : null;
                                    if (object != null) {
                                        stringBuilder.append('\t');
                                        stringBuilder.append('#');
                                        stringBuilder.append(object);
                                        stringBuilder.append('\n');
                                    } else {
                                        object3 = (StringDistanceTable)object3;
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append(string);
                                        ((StringBuilder)object).append("\t\t\t");
                                        ((StringDistanceTable)object3).toString(bl, ((StringBuilder)object).toString(), idMakerFull, stringBuilder);
                                        stringBuilder.append('\n');
                                    }
                                } else {
                                    stringBuilder.append('\n');
                                }
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append(string);
                            ((StringBuilder)object).append('\t');
                            object = ((StringBuilder)object).toString();
                            n = 10;
                        }
                    }
                    string2 = string;
                    n2 = n;
                }
            }
            return stringBuilder;
        }
    }

}

