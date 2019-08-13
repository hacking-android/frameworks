/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.UCharacter;
import android.icu.text.Collator;
import android.icu.text.Normalizer2;
import android.icu.text.RuleBasedCollator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.util.LocaleData;
import android.icu.util.ULocale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class AlphabeticIndex<V>
implements Iterable<Bucket<V>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String BASE = "\ufdd0";
    private static final char CGJ = '\u034f';
    private static final int GC_CN_MASK = 1;
    private static final int GC_LL_MASK = 4;
    private static final int GC_LM_MASK = 16;
    private static final int GC_LO_MASK = 32;
    private static final int GC_LT_MASK = 8;
    private static final int GC_LU_MASK = 2;
    private static final int GC_L_MASK = 62;
    private static final Comparator<String> binaryCmp = new UTF16.StringComparator(true, false, 0);
    private BucketList<V> buckets;
    private RuleBasedCollator collatorExternal;
    private final RuleBasedCollator collatorOriginal;
    private final RuleBasedCollator collatorPrimaryOnly;
    private final List<String> firstCharsInScripts;
    private String inflowLabel = "\u2026";
    private final UnicodeSet initialLabels = new UnicodeSet();
    private List<Record<V>> inputList;
    private int maxLabelCount = 99;
    private String overflowLabel = "\u2026";
    private final Comparator<Record<V>> recordComparator = new Comparator<Record<V>>(){

        @Override
        public int compare(Record<V> record, Record<V> record2) {
            return AlphabeticIndex.this.collatorOriginal.compare(record.name, record2.name);
        }
    };
    private String underflowLabel = "\u2026";

    public AlphabeticIndex(RuleBasedCollator ruleBasedCollator) {
        this(null, ruleBasedCollator);
    }

    public AlphabeticIndex(ULocale uLocale) {
        this(uLocale, null);
    }

    private AlphabeticIndex(ULocale uLocale, RuleBasedCollator ruleBasedCollator) {
        if (ruleBasedCollator == null) {
            ruleBasedCollator = (RuleBasedCollator)Collator.getInstance(uLocale);
        }
        this.collatorOriginal = ruleBasedCollator;
        try {
            this.collatorPrimaryOnly = this.collatorOriginal.cloneAsThawed();
            this.collatorPrimaryOnly.setStrength(0);
            this.collatorPrimaryOnly.freeze();
        }
        catch (Exception exception) {
            throw new IllegalStateException("Collator cannot be cloned", exception);
        }
        this.firstCharsInScripts = this.getFirstCharactersInScripts();
        Collections.sort(this.firstCharsInScripts, this.collatorPrimaryOnly);
        while (!this.firstCharsInScripts.isEmpty()) {
            if (this.collatorPrimaryOnly.compare(this.firstCharsInScripts.get(0), "") == 0) {
                this.firstCharsInScripts.remove(0);
                continue;
            }
            if (!this.addChineseIndexCharacters() && uLocale != null) {
                this.addIndexExemplars(uLocale);
            }
            return;
        }
        throw new IllegalArgumentException("AlphabeticIndex requires some non-ignorable script boundary strings");
    }

    public AlphabeticIndex(Locale locale) {
        this(ULocale.forLocale(locale), null);
    }

    private boolean addChineseIndexCharacters() {
        Object object = new UnicodeSet();
        try {
            this.collatorPrimaryOnly.internalAddContractions(BASE.charAt(0), (UnicodeSet)object);
            if (((UnicodeSet)object).isEmpty()) {
                return false;
            }
            this.initialLabels.addAll((UnicodeSet)object);
        }
        catch (Exception exception) {
            return false;
        }
        Iterator<String> iterator = ((UnicodeSet)object).iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            char c = ((String)object).charAt(((String)object).length() - 1);
            if ('A' > c || c > 'Z') continue;
            this.initialLabels.add(65, 90);
            break;
        }
        return true;
    }

    private void addIndexExemplars(ULocale uLocale) {
        Object object;
        Object object2 = LocaleData.getExemplarSet(uLocale, 0, 2);
        if (object2 != null && !((UnicodeSet)object2).isEmpty()) {
            this.initialLabels.addAll((UnicodeSet)object2);
            return;
        }
        object2 = LocaleData.getExemplarSet(uLocale, 0, 0).cloneAsThawed();
        if (((UnicodeSet)object2).containsSome(97, 122) || ((UnicodeSet)object2).isEmpty()) {
            ((UnicodeSet)object2).addAll(97, 122);
        }
        if (((UnicodeSet)object2).containsSome(44032, 55203)) {
            ((UnicodeSet)object2).remove(44032, 55203).add(44032).add(45208).add(45796).add(46972).add(47560).add(48148).add(49324).add(50500).add(51088).add(52264).add(52852).add(53440).add(54028).add(54616);
        }
        if (((UnicodeSet)object2).containsSome(4608, 4991)) {
            object = new UnicodeSet("[\u1200\u1208\u1210\u1218\u1220\u1228\u1230\u1238\u1240\u1248\u1250\u1258\u1260\u1268\u1270\u1278\u1280\u1288\u1290\u1298\u12a0\u12a8\u12b0\u12b8\u12c0\u12c8\u12d0\u12d8\u12e0\u12e8\u12f0\u12f8\u1300\u1308\u1310\u1318\u1320\u1328\u1330\u1338\u1340\u1348\u1350\u1358]");
            ((UnicodeSet)object).retainAll((UnicodeSet)object2);
            ((UnicodeSet)object2).remove(4608, 4991).addAll((UnicodeSet)object);
        }
        object2 = ((UnicodeSet)object2).iterator();
        while (object2.hasNext()) {
            object = (String)object2.next();
            this.initialLabels.add(UCharacter.toUpperCase(uLocale, (String)object));
        }
    }

    private BucketList<V> createBucketList() {
        Iterable<Bucket> iterable = this;
        Iterable<Bucket> iterable2 = this.initLabels();
        long l = ((AlphabeticIndex)iterable).collatorPrimaryOnly.isAlternateHandlingShifted() ? (long)((AlphabeticIndex)iterable).collatorPrimaryOnly.getVariableTop() & 0xFFFFFFFFL : 0L;
        int n = 0;
        Bucket[] arrbucket = new Bucket[26];
        Bucket[] arrbucket2 = new Bucket[26];
        int n2 = 0;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(new Bucket(this.getUnderflowLabel(), "", Bucket.LabelType.UNDERFLOW));
        int n3 = -1;
        iterable = "";
        Iterator<String> iterator = iterable2.iterator();
        do {
            int n4;
            Object object;
            AlphabeticIndex alphabeticIndex = this;
            if (!iterator.hasNext()) break;
            String string = iterator.next();
            if (alphabeticIndex.collatorPrimaryOnly.compare(string, (String)((Object)iterable)) >= 0) {
                n4 = 0;
                do {
                    object = alphabeticIndex.firstCharsInScripts;
                    if (alphabeticIndex.collatorPrimaryOnly.compare(string, (String)(object = object.get(++n3))) < 0) {
                        if (n4 != 0 && arrayList.size() > 1) {
                            arrayList.add(new Bucket(this.getInflowLabel(), (String)((Object)iterable), Bucket.LabelType.INFLOW));
                        }
                        iterable = object;
                        break;
                    }
                    n4 = 1;
                } while (true);
            }
            object = new Bucket(AlphabeticIndex.fixLabel(string), string, Bucket.LabelType.NORMAL);
            arrayList.add(object);
            if (string.length() == 1 && 65 <= (n4 = (int)string.charAt(0)) && n4 <= 90) {
                arrbucket[n4 - 65] = object;
                n4 = n2;
            } else {
                n4 = n2;
                if (string.length() == BASE.length() + 1) {
                    n4 = n2;
                    if (string.startsWith(BASE)) {
                        char c = string.charAt(BASE.length());
                        n4 = n2;
                        if ('A' <= c) {
                            n4 = n2;
                            if (c <= 'Z') {
                                arrbucket2[c - 65] = object;
                                n4 = 1;
                            }
                        }
                    }
                }
            }
            if (!string.startsWith(BASE) && AlphabeticIndex.hasMultiplePrimaryWeights(alphabeticIndex.collatorPrimaryOnly, l, string) && !string.endsWith("\uffff")) {
                n2 = arrayList.size() - 2;
                Object object2 = object;
                while (((Bucket)(object = (Bucket)arrayList.get(n2))).labelType == Bucket.LabelType.NORMAL) {
                    if (((Bucket)object).displayBucket == null && !AlphabeticIndex.hasMultiplePrimaryWeights(this.collatorPrimaryOnly, l, ((Bucket)object).lowerBoundary)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(string);
                        stringBuilder.append("\uffff");
                        Bucket bucket = new Bucket("", stringBuilder.toString(), Bucket.LabelType.NORMAL);
                        bucket.displayBucket = (Bucket)object;
                        arrayList.add(bucket);
                        n = 1;
                        break;
                    }
                    --n2;
                }
            }
            n2 = n4;
        } while (true);
        if (arrayList.size() == 1) {
            return new BucketList(arrayList, arrayList);
        }
        arrayList.add(new Bucket(this.getOverflowLabel(), (String)((Object)iterable), Bucket.LabelType.OVERFLOW));
        if (n2 != 0) {
            iterable2 = null;
            n3 = 0;
            do {
                n2 = n;
                if (n3 < 26) {
                    if (arrbucket[n3] != null) {
                        iterable2 = arrbucket[n3];
                    }
                    n2 = n;
                    if (arrbucket2[n3] != null) {
                        n2 = n;
                        if (iterable2 != null) {
                            arrbucket2[n3].displayBucket = (Bucket)iterable2;
                            n2 = 1;
                        }
                    }
                    ++n3;
                    n = n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            n2 = n;
        }
        if (n2 == 0) {
            return new BucketList(arrayList, arrayList);
        }
        n = arrayList.size() - 1;
        iterable2 = (Bucket)arrayList.get(n);
        while (--n > 0) {
            iterable = (Bucket)arrayList.get(n);
            if (iterable.displayBucket != null) continue;
            if (iterable.labelType == Bucket.LabelType.INFLOW && ((Bucket)iterable2).labelType != Bucket.LabelType.NORMAL) {
                iterable.displayBucket = iterable2;
                continue;
            }
            iterable2 = iterable;
        }
        iterable = new ArrayList();
        for (Bucket bucket : arrayList) {
            if (bucket.displayBucket != null) continue;
            ((ArrayList)iterable).add(bucket);
        }
        return new BucketList(arrayList, (ArrayList)iterable);
    }

    private static String fixLabel(String charSequence) {
        if (!((String)charSequence).startsWith(BASE)) {
            return charSequence;
        }
        char c = ((String)charSequence).charAt(BASE.length());
        if ('\u2800' < c && c <= '\u28ff') {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(c - 10240);
            ((StringBuilder)charSequence).append("\u5283");
            return ((StringBuilder)charSequence).toString();
        }
        return ((String)charSequence).substring(BASE.length());
    }

    private static boolean hasMultiplePrimaryWeights(RuleBasedCollator arrl, long l, String string) {
        arrl = arrl.internalGetCEs(string);
        boolean bl = false;
        for (int i = 0; i < arrl.length; ++i) {
            boolean bl2 = bl;
            if (arrl[i] >>> 32 > l) {
                if (bl) {
                    return true;
                }
                bl2 = true;
            }
            bl = bl2;
        }
        return false;
    }

    private void initBuckets() {
        if (this.buckets != null) {
            return;
        }
        this.buckets = this.createBucketList();
        Iterable<Record<Object>> iterable = this.inputList;
        if (iterable != null && !iterable.isEmpty()) {
            Iterable<Record<Object>> iterable2;
            Collections.sort(this.inputList, this.recordComparator);
            Iterator iterator = this.buckets.fullIterator();
            Object object = (Bucket)iterator.next();
            if (iterator.hasNext()) {
                iterable = (Bucket)iterator.next();
                iterable2 = iterable.lowerBoundary;
            } else {
                iterable = null;
                iterable2 = null;
            }
            Iterator<Record<V>> iterator2 = this.inputList.iterator();
            String string = iterable2;
            iterable2 = object;
            while (iterator2.hasNext()) {
                Record<V> record = iterator2.next();
                while (string != null && this.collatorPrimaryOnly.compare(record.name, (Object)string) >= 0) {
                    iterable2 = iterable;
                    if (iterator.hasNext()) {
                        iterable = (Bucket)iterator.next();
                        string = iterable.lowerBoundary;
                        continue;
                    }
                    string = null;
                }
                Iterable<Record<Object>> iterable3 = iterable2;
                object = iterable3;
                if (((Bucket)iterable3).displayBucket != null) {
                    object = iterable3.displayBucket;
                }
                if (((Bucket)object).records == null) {
                    ((Bucket)object).records = new ArrayList();
                }
                ((Bucket)object).records.add(record);
            }
            return;
        }
    }

    private List<String> initLabels() {
        int n;
        Normalizer2 normalizer2 = Normalizer2.getNFKDInstance();
        ArrayList<String> arrayList = new ArrayList<String>();
        String string = this.firstCharsInScripts.get(0);
        Object object2 = this.firstCharsInScripts;
        String string2 = object2.get(object2.size() - 1);
        for (Object object2 : this.initialLabels) {
            if (!UTF16.hasMoreCodePointsThan((String)object2, 1)) {
                n = 0;
            } else if (((String)object2).charAt(((String)object2).length() - 1) == '*' && ((String)object2).charAt(((String)object2).length() - 2) != '*') {
                object2 = ((String)object2).substring(0, ((String)object2).length() - 1);
                n = 0;
            } else {
                n = 1;
            }
            if (this.collatorPrimaryOnly.compare((String)object2, string) < 0 || this.collatorPrimaryOnly.compare((String)object2, string2) >= 0 || n != 0 && this.collatorPrimaryOnly.compare((String)object2, this.separated((String)object2)) == 0) continue;
            n = Collections.binarySearch(arrayList, object2, this.collatorPrimaryOnly);
            if (n < 0) {
                arrayList.add(n, (String)object2);
                continue;
            }
            if (!AlphabeticIndex.isOneLabelBetterThanOther(normalizer2, (String)object2, (String)arrayList.get(n))) continue;
            arrayList.set(n, (String)object2);
        }
        int n2 = arrayList.size() - 1;
        if (n2 > this.maxLabelCount) {
            int n3 = 0;
            n = -1;
            object2 = arrayList.iterator();
            while (object2.hasNext()) {
                object2.next();
                int n4 = this.maxLabelCount * ++n3 / n2;
                if (n4 == n) {
                    object2.remove();
                    continue;
                }
                n = n4;
            }
        }
        return arrayList;
    }

    private static boolean isOneLabelBetterThanOther(Normalizer2 object, String string, String string2) {
        String string3 = ((Normalizer2)object).normalize(string);
        object = ((Normalizer2)object).normalize(string2);
        int n = string3.length();
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if ((n = string3.codePointCount(0, n) - ((String)object).codePointCount(0, ((String)object).length())) != 0) {
            if (n < 0) {
                bl3 = true;
            }
            return bl3;
        }
        n = binaryCmp.compare(string3, (String)object);
        if (n != 0) {
            bl3 = bl;
            if (n < 0) {
                bl3 = true;
            }
            return bl3;
        }
        bl3 = bl2;
        if (binaryCmp.compare(string, string2) < 0) {
            bl3 = true;
        }
        return bl3;
    }

    private String separated(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        char c = string.charAt(0);
        stringBuilder.append(c);
        for (int i = 1; i < string.length(); ++i) {
            char c2 = string.charAt(i);
            if (!UCharacter.isHighSurrogate(c) || !UCharacter.isLowSurrogate(c2)) {
                stringBuilder.append('\u034f');
            }
            stringBuilder.append(c2);
            char c3 = c2;
            c = c3;
        }
        return stringBuilder.toString();
    }

    public AlphabeticIndex<V> addLabels(UnicodeSet unicodeSet) {
        this.initialLabels.addAll(unicodeSet);
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> addLabels(ULocale ... arruLocale) {
        int n = arruLocale.length;
        for (int i = 0; i < n; ++i) {
            this.addIndexExemplars(arruLocale[i]);
        }
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> addLabels(Locale ... arrlocale) {
        int n = arrlocale.length;
        for (int i = 0; i < n; ++i) {
            this.addIndexExemplars(ULocale.forLocale(arrlocale[i]));
        }
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> addRecord(CharSequence charSequence, V v) {
        this.buckets = null;
        if (this.inputList == null) {
            this.inputList = new ArrayList<Record<V>>();
        }
        this.inputList.add(new Record(charSequence, v));
        return this;
    }

    public ImmutableIndex<V> buildImmutableIndex() {
        Iterable<Record<V>> iterable = this.inputList;
        if (iterable != null && !iterable.isEmpty()) {
            iterable = this.createBucketList();
        } else {
            if (this.buckets == null) {
                this.buckets = this.createBucketList();
            }
            iterable = this.buckets;
        }
        return new ImmutableIndex((BucketList)iterable, this.collatorPrimaryOnly);
    }

    public AlphabeticIndex<V> clearRecords() {
        List<Record<V>> list = this.inputList;
        if (list != null && !list.isEmpty()) {
            this.inputList.clear();
            this.buckets = null;
        }
        return this;
    }

    public int getBucketCount() {
        this.initBuckets();
        return this.buckets.getBucketCount();
    }

    public int getBucketIndex(CharSequence charSequence) {
        this.initBuckets();
        return this.buckets.getBucketIndex(charSequence, this.collatorPrimaryOnly);
    }

    public List<String> getBucketLabels() {
        this.initBuckets();
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<Bucket<V>> iterator = this.buckets.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getLabel());
        }
        return arrayList;
    }

    public RuleBasedCollator getCollator() {
        if (this.collatorExternal == null) {
            try {
                this.collatorExternal = (RuleBasedCollator)this.collatorOriginal.clone();
            }
            catch (Exception exception) {
                throw new IllegalStateException("Collator cannot be cloned", exception);
            }
        }
        return this.collatorExternal;
    }

    @Deprecated
    public List<String> getFirstCharactersInScripts() {
        ArrayList<String> arrayList = new ArrayList<String>(200);
        Object object = new UnicodeSet();
        this.collatorPrimaryOnly.internalAddContractions(64977, (UnicodeSet)object);
        if (!((UnicodeSet)object).isEmpty()) {
            object = ((UnicodeSet)object).iterator();
            while (object.hasNext()) {
                String string = (String)object.next();
                if ((1 << UCharacter.getType(string.codePointAt(1)) & 63) == 0) continue;
                arrayList.add(string);
            }
            return arrayList;
        }
        throw new UnsupportedOperationException("AlphabeticIndex requires script-first-primary contractions");
    }

    public String getInflowLabel() {
        return this.inflowLabel;
    }

    public int getMaxLabelCount() {
        return this.maxLabelCount;
    }

    public String getOverflowLabel() {
        return this.overflowLabel;
    }

    public int getRecordCount() {
        List<Record<V>> list = this.inputList;
        int n = list != null ? list.size() : 0;
        return n;
    }

    public String getUnderflowLabel() {
        return this.underflowLabel;
    }

    @Override
    public Iterator<Bucket<V>> iterator() {
        this.initBuckets();
        return this.buckets.iterator();
    }

    public AlphabeticIndex<V> setInflowLabel(String string) {
        this.inflowLabel = string;
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> setMaxLabelCount(int n) {
        this.maxLabelCount = n;
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> setOverflowLabel(String string) {
        this.overflowLabel = string;
        this.buckets = null;
        return this;
    }

    public AlphabeticIndex<V> setUnderflowLabel(String string) {
        this.underflowLabel = string;
        this.buckets = null;
        return this;
    }

    public static class Bucket<V>
    implements Iterable<Record<V>> {
        private Bucket<V> displayBucket;
        private int displayIndex;
        private final String label;
        private final LabelType labelType;
        private final String lowerBoundary;
        private List<Record<V>> records;

        private Bucket(String string, String string2, LabelType labelType) {
            this.label = string;
            this.lowerBoundary = string2;
            this.labelType = labelType;
        }

        public String getLabel() {
            return this.label;
        }

        public LabelType getLabelType() {
            return this.labelType;
        }

        @Override
        public Iterator<Record<V>> iterator() {
            List<Record<V>> list = this.records;
            if (list == null) {
                return Collections.emptyList().iterator();
            }
            return list.iterator();
        }

        public int size() {
            List<Record<V>> list = this.records;
            int n = list == null ? 0 : list.size();
            return n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{labelType=");
            stringBuilder.append((Object)this.labelType);
            stringBuilder.append(", lowerBoundary=");
            stringBuilder.append(this.lowerBoundary);
            stringBuilder.append(", label=");
            stringBuilder.append(this.label);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public static enum LabelType {
            NORMAL,
            UNDERFLOW,
            INFLOW,
            OVERFLOW;
            
        }

    }

    private static class BucketList<V>
    implements Iterable<Bucket<V>> {
        private final ArrayList<Bucket<V>> bucketList;
        private final List<Bucket<V>> immutableVisibleList;

        private BucketList(ArrayList<Bucket<V>> object, ArrayList<Bucket<V>> arrayList) {
            this.bucketList = object;
            int n = 0;
            object = arrayList.iterator();
            while (object.hasNext()) {
                ((Bucket)object.next()).displayIndex = n;
                ++n;
            }
            this.immutableVisibleList = Collections.unmodifiableList(arrayList);
        }

        private Iterator<Bucket<V>> fullIterator() {
            return this.bucketList.iterator();
        }

        private int getBucketCount() {
            return this.immutableVisibleList.size();
        }

        private int getBucketIndex(CharSequence object, Collator object2) {
            int n = 0;
            int n2 = this.bucketList.size();
            while (n + 1 < n2) {
                int n3 = (n + n2) / 2;
                if (((Collator)object2).compare(object, (Object)this.bucketList.get(n3).lowerBoundary) < 0) {
                    n2 = n3;
                    continue;
                }
                n = n3;
            }
            object = object2 = this.bucketList.get(n);
            if (((Bucket)object2).displayBucket != null) {
                object = ((Bucket)object2).displayBucket;
            }
            return ((Bucket)object).displayIndex;
        }

        @Override
        public Iterator<Bucket<V>> iterator() {
            return this.immutableVisibleList.iterator();
        }
    }

    public static final class ImmutableIndex<V>
    implements Iterable<Bucket<V>> {
        private final BucketList<V> buckets;
        private final Collator collatorPrimaryOnly;

        private ImmutableIndex(BucketList<V> bucketList, Collator collator) {
            this.buckets = bucketList;
            this.collatorPrimaryOnly = collator;
        }

        public Bucket<V> getBucket(int n) {
            if (n >= 0 && n < this.buckets.getBucketCount()) {
                return (Bucket)this.buckets.immutableVisibleList.get(n);
            }
            return null;
        }

        public int getBucketCount() {
            return this.buckets.getBucketCount();
        }

        public int getBucketIndex(CharSequence charSequence) {
            return this.buckets.getBucketIndex(charSequence, this.collatorPrimaryOnly);
        }

        @Override
        public Iterator<Bucket<V>> iterator() {
            return this.buckets.iterator();
        }
    }

    public static class Record<V> {
        private final V data;
        private final CharSequence name;

        private Record(CharSequence charSequence, V v) {
            this.name = charSequence;
            this.data = v;
        }

        public V getData() {
            return this.data;
        }

        public CharSequence getName() {
            return this.name;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((Object)this.name);
            stringBuilder.append("=");
            stringBuilder.append(this.data);
            return stringBuilder.toString();
        }
    }

}

