/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.util.ICUException;
import android.icu.util.ICUUncheckedIOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XCldrStub {
    public static <T> String join(Iterable<T> iterable2, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = true;
        for (Iterable<T> iterable2 : iterable2) {
            if (!bl) {
                stringBuilder.append(string);
            } else {
                bl = false;
            }
            stringBuilder.append(iterable2.toString());
        }
        return stringBuilder.toString();
    }

    public static <T> String join(T[] arrT, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrT.length; ++i) {
            if (i != 0) {
                stringBuilder.append(string);
            }
            stringBuilder.append(arrT[i]);
        }
        return stringBuilder.toString();
    }

    public static class CollectionUtilities {
        public static <T, U extends Iterable<T>> String join(U u, String string) {
            return XCldrStub.join(u, string);
        }
    }

    public static class FileUtilities {
        public static final Charset UTF8 = Charset.forName("utf-8");

        public static String getRelativeFileName(Class<?> serializable, String string) {
            serializable = serializable == null ? FileUtilities.class.getResource(string) : ((Class)serializable).getResource(string);
            string = ((URL)serializable).toString();
            if (string.startsWith("file:")) {
                return string.substring(5);
            }
            if (string.startsWith("jar:file:")) {
                return string.substring(9);
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("File not found: ");
            ((StringBuilder)serializable).append(string);
            throw new ICUUncheckedIOException(((StringBuilder)serializable).toString());
        }

        public static BufferedReader openFile(Class<?> class_, String string) {
            return FileUtilities.openFile(class_, string, UTF8);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static BufferedReader openFile(Class<?> object, String string, Charset object2) {
            try {
                InputStream inputStream = ((Class)object).getResourceAsStream(string);
                Object object3 = object2;
                if (object2 == null) {
                    object3 = UTF8;
                }
                object2 = new InputStreamReader(inputStream, (Charset)object3);
                return new BufferedReader((Reader)object2, 65536);
            }
            catch (Exception exception) {
                CharSequence charSequence;
                object2 = object == null ? null : ((Class)object).getCanonicalName();
                try {
                    charSequence = FileUtilities.getRelativeFileName(object, "../util/");
                    object = new File((String)charSequence);
                    object = ((File)object).getCanonicalPath();
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Couldn't open file ");
                }
                catch (Exception exception2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't open file: ");
                    stringBuilder.append(string);
                    stringBuilder.append("; relative to class: ");
                    stringBuilder.append((String)object2);
                    throw new ICUUncheckedIOException(stringBuilder.toString(), exception);
                }
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append("; in path ");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append("; relative to class: ");
                ((StringBuilder)charSequence).append((String)object2);
                throw new ICUUncheckedIOException(((StringBuilder)charSequence).toString(), exception);
            }
        }
    }

    public static class HashMultimap<K, V>
    extends Multimap<K, V> {
        private HashMultimap() {
            super(new HashMap(), HashSet.class);
        }

        public static <K, V> HashMultimap<K, V> create() {
            return new HashMultimap<K, V>();
        }
    }

    public static class ImmutableMap {
        public static <K, V> Map<K, V> copyOf(Map<K, V> map) {
            return Collections.unmodifiableMap(new LinkedHashMap<K, V>(map));
        }
    }

    public static class ImmutableMultimap {
        public static <K, V> Multimap<K, V> copyOf(Multimap<K, V> set) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<K, Set<V>> entry : ((Multimap)((Object)set)).asMap().entrySet()) {
                set = entry.getValue();
                entry = entry.getKey();
                set = set.size() == 1 ? Collections.singleton(set.iterator().next()) : Collections.unmodifiableSet(new LinkedHashSet(set));
                linkedHashMap.put(entry, set);
            }
            return new Multimap(Collections.unmodifiableMap(linkedHashMap), null);
        }
    }

    public static class ImmutableSet {
        public static <T> Set<T> copyOf(Set<T> set) {
            return Collections.unmodifiableSet(new LinkedHashSet<T>(set));
        }
    }

    public static class Joiner {
        private final String separator;

        private Joiner(String string) {
            this.separator = string;
        }

        public static final Joiner on(String string) {
            return new Joiner(string);
        }

        public <T> String join(Iterable<T> iterable) {
            return XCldrStub.join(iterable, this.separator);
        }

        public <T> String join(T[] arrT) {
            return XCldrStub.join(arrT, this.separator);
        }
    }

    public static class LinkedHashMultimap<K, V>
    extends Multimap<K, V> {
        private LinkedHashMultimap() {
            super(new LinkedHashMap(), LinkedHashSet.class);
        }

        public static <K, V> LinkedHashMultimap<K, V> create() {
            return new LinkedHashMultimap<K, V>();
        }
    }

    public static class Multimap<K, V> {
        private final Map<K, Set<V>> map;
        private final Class<Set<V>> setClass;

        private Multimap(Map<K, Set<V>> map, Class<?> class_) {
            this.map = map;
            if (class_ == null) {
                class_ = HashSet.class;
            }
            this.setClass = class_;
        }

        private Set<V> createSetIfMissing(K k) {
            Set<V> set;
            Set<V> set2 = set = this.map.get(k);
            if (set == null) {
                Map<K, Set<Set<V>>> map = this.map;
                set2 = set = this.getInstance();
                map.put(k, set);
            }
            return set2;
        }

        private Set<V> getInstance() {
            try {
                Set<V> set = this.setClass.newInstance();
                return set;
            }
            catch (Exception exception) {
                throw new ICUException(exception);
            }
        }

        public Map<K, Set<V>> asMap() {
            return this.map;
        }

        public Iterable<Map.Entry<K, V>> entries() {
            return new MultimapIterator(this.map);
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object != null && object.getClass() == this.getClass() && this.map.equals(((Multimap)object).map);
            return bl;
        }

        public Set<V> get(K k) {
            return this.map.get(k);
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        public Set<K> keySet() {
            return this.map.keySet();
        }

        public void put(K k, V v) {
            this.createSetIfMissing(k).add(v);
        }

        @SafeVarargs
        public final Multimap<K, V> putAll(K k, V ... arrV) {
            if (arrV.length != 0) {
                this.createSetIfMissing(k).addAll(Arrays.asList(arrV));
            }
            return this;
        }

        public void putAll(Multimap<K, V> object) {
            for (Map.Entry<K, Set<V>> entry : ((Multimap)object).map.entrySet()) {
                this.putAll(entry.getKey(), (Collection)entry.getValue());
            }
        }

        public void putAll(K k, Collection<V> collection) {
            if (!collection.isEmpty()) {
                this.createSetIfMissing(k).addAll(collection);
            }
        }

        public void putAll(Collection<K> object, V v) {
            object = object.iterator();
            while (object.hasNext()) {
                this.put(object.next(), v);
            }
        }

        public int size() {
            return this.map.size();
        }

        public Set<V> values() {
            Collection<Set<V>> collection = this.map.values();
            if (collection.size() == 0) {
                return Collections.emptySet();
            }
            Set<V> set = this.getInstance();
            collection = collection.iterator();
            while (collection.hasNext()) {
                set.addAll((Set)collection.next());
            }
            return set;
        }
    }

    private static class MultimapIterator<K, V>
    implements Iterator<Map.Entry<K, V>>,
    Iterable<Map.Entry<K, V>> {
        private final ReusableEntry<K, V> entry = new ReusableEntry();
        private final Iterator<Map.Entry<K, Set<V>>> it1;
        private Iterator<V> it2 = null;

        private MultimapIterator(Map<K, Set<V>> map) {
            this.it1 = map.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            Iterator<V> iterator;
            boolean bl = this.it1.hasNext() || (iterator = this.it2) != null && iterator.hasNext();
            return bl;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return this;
        }

        @Override
        public Map.Entry<K, V> next() {
            Iterator<V> iterator = this.it2;
            if (iterator != null && iterator.hasNext()) {
                this.entry.value = this.it2.next();
            } else {
                iterator = this.it1.next();
                this.entry.key = iterator.getKey();
                this.it2 = ((Set)iterator.getValue()).iterator();
            }
            return this.entry;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Multimaps {
        public static <K, V> Map<K, V> forMap(Map<K, V> map) {
            return map;
        }

        public static <K, V, R extends Multimap<K, V>> R invertFrom(Multimap<V, K> object, R r) {
            for (Map.Entry entry : ((Multimap)((Object)object)).asMap().entrySet()) {
                ((Multimap)r).putAll(entry.getValue(), entry.getKey());
            }
            return r;
        }

        /*
         * WARNING - void declaration
         */
        public static <K, V, R extends Multimap<K, V>> R invertFrom(Map<V, K> object2, R r) {
            void var1_3;
            for (Map.Entry entry : object2.entrySet()) {
                var1_3.put(entry.getValue(), entry.getKey());
            }
            return var1_3;
        }
    }

    public static interface Predicate<T> {
        public boolean test(T var1);
    }

    public static class RegexUtilities {
        public static int findMismatch(Matcher matcher, CharSequence charSequence) {
            int n;
            for (n = 1; n < charSequence.length() && (matcher.reset(charSequence.subSequence(0, n)).matches() || matcher.hitEnd()); ++n) {
            }
            return n - 1;
        }

        public static String showMismatch(Matcher object, CharSequence charSequence) {
            int n = RegexUtilities.findMismatch((Matcher)object, charSequence);
            object = new StringBuilder();
            ((StringBuilder)object).append((Object)charSequence.subSequence(0, n));
            ((StringBuilder)object).append("\u2639");
            ((StringBuilder)object).append((Object)charSequence.subSequence(n, charSequence.length()));
            return ((StringBuilder)object).toString();
        }
    }

    private static class ReusableEntry<K, V>
    implements Map.Entry<K, V> {
        K key;
        V value;

        private ReusableEntry() {
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }
    }

    public static class Splitter {
        Pattern pattern;
        boolean trimResults;

        public Splitter(char c) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\\Q");
            stringBuilder.append(c);
            stringBuilder.append("\\E");
            this(Pattern.compile(stringBuilder.toString()));
        }

        public Splitter(Pattern pattern) {
            this.trimResults = false;
            this.pattern = pattern;
        }

        public static Splitter on(char c) {
            return new Splitter(c);
        }

        public static Splitter on(Pattern pattern) {
            return new Splitter(pattern);
        }

        public Iterable<String> split(String string) {
            return this.splitToList(string);
        }

        public List<String> splitToList(String arrstring) {
            arrstring = this.pattern.split((CharSequence)arrstring);
            if (this.trimResults) {
                for (int i = 0; i < arrstring.length; ++i) {
                    arrstring[i] = arrstring[i].trim();
                }
            }
            return Arrays.asList(arrstring);
        }

        public Splitter trimResults() {
            this.trimResults = true;
            return this;
        }
    }

    public static class TreeMultimap<K, V>
    extends Multimap<K, V> {
        private TreeMultimap() {
            super(new TreeMap(), TreeSet.class);
        }

        public static <K, V> TreeMultimap<K, V> create() {
            return new TreeMultimap<K, V>();
        }
    }

}

