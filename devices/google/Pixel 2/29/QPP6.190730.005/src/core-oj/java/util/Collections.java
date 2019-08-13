/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package java.util;

import dalvik.system.VMRuntime;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util._$$Lambda$Collections$CheckedList$gXIP1Db1_l1aVeW3UfOh4dLyESo;
import java.util._$$Lambda$Collections$CheckedMap$5MCuh91_pd5SsNapFva5jp8gVs8;
import java.util._$$Lambda$Collections$CheckedMap$UHd_dm3NjSdMnE1bZpQe0MXp_Gk;
import java.util._$$Lambda$Collections$CheckedMap$hvicTzt8soLX23klS8sBMiCuEvk;
import java.util._$$Lambda$Collections$CopiesList$JkPuGMNhrKbnEHjebm8AvHc2xHw;
import java.util._$$Lambda$Collections$CopiesList$uHL7LkfEBowvpXOMQGFkZqUGxm4;
import java.util._$$Lambda$Collections$UnmodifiableMap$UnmodifiableEntrySet$W5VhpDb0JlKqrRuOSf_2RiCnSgo;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Collections {
    private static final int BINARYSEARCH_THRESHOLD = 5000;
    private static final int COPY_THRESHOLD = 10;
    public static final List EMPTY_LIST;
    public static final Map EMPTY_MAP;
    public static final Set EMPTY_SET;
    private static final int FILL_THRESHOLD = 25;
    private static final int INDEXOFSUBLIST_THRESHOLD = 35;
    private static final int REPLACEALL_THRESHOLD = 11;
    private static final int REVERSE_THRESHOLD = 18;
    private static final int ROTATE_THRESHOLD = 100;
    private static final int SHUFFLE_THRESHOLD = 5;
    private static Random r;

    static {
        EMPTY_SET = new EmptySet();
        EMPTY_LIST = new EmptyList();
        EMPTY_MAP = new EmptyMap();
    }

    private Collections() {
    }

    @SafeVarargs
    public static <T> boolean addAll(Collection<? super T> collection, T ... arrT) {
        boolean bl = false;
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            bl |= collection.add(arrT[i]);
        }
        return bl;
    }

    public static <T> Queue<T> asLifoQueue(Deque<T> deque) {
        return new AsLIFOQueue<T>(deque);
    }

    public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T t) {
        if (!(list instanceof RandomAccess) && list.size() >= 5000) {
            return Collections.iteratorBinarySearch(list, t);
        }
        return Collections.indexedBinarySearch(list, t);
    }

    public static <T> int binarySearch(List<? extends T> list, T t, Comparator<? super T> comparator) {
        if (comparator == null) {
            return Collections.binarySearch(list, t);
        }
        if (!(list instanceof RandomAccess) && list.size() >= 5000) {
            return Collections.iteratorBinarySearch(list, t, comparator);
        }
        return Collections.indexedBinarySearch(list, t, comparator);
    }

    public static <E> Collection<E> checkedCollection(Collection<E> collection, Class<E> class_) {
        return new CheckedCollection<E>(collection, class_);
    }

    public static <E> List<E> checkedList(List<E> checkedList, Class<E> class_) {
        checkedList = checkedList instanceof RandomAccess ? new CheckedRandomAccessList<E>(checkedList, class_) : new CheckedList<E>(checkedList, class_);
        return checkedList;
    }

    public static <K, V> Map<K, V> checkedMap(Map<K, V> map, Class<K> class_, Class<V> class_2) {
        return new CheckedMap<K, V>(map, class_, class_2);
    }

    public static <K, V> NavigableMap<K, V> checkedNavigableMap(NavigableMap<K, V> navigableMap, Class<K> class_, Class<V> class_2) {
        return new CheckedNavigableMap<K, V>(navigableMap, class_, class_2);
    }

    public static <E> NavigableSet<E> checkedNavigableSet(NavigableSet<E> navigableSet, Class<E> class_) {
        return new CheckedNavigableSet<E>(navigableSet, class_);
    }

    public static <E> Queue<E> checkedQueue(Queue<E> queue, Class<E> class_) {
        return new CheckedQueue<E>(queue, class_);
    }

    public static <E> Set<E> checkedSet(Set<E> set, Class<E> class_) {
        return new CheckedSet<E>(set, class_);
    }

    public static <K, V> SortedMap<K, V> checkedSortedMap(SortedMap<K, V> sortedMap, Class<K> class_, Class<V> class_2) {
        return new CheckedSortedMap<K, V>(sortedMap, class_, class_2);
    }

    public static <E> SortedSet<E> checkedSortedSet(SortedSet<E> sortedSet, Class<E> class_) {
        return new CheckedSortedSet<E>(sortedSet, class_);
    }

    public static <T> void copy(List<? super T> object, List<? extends T> object2) {
        int n = object2.size();
        if (n <= object.size()) {
            if (!(n < 10 || object2 instanceof RandomAccess && object instanceof RandomAccess)) {
                object = object.listIterator();
                object2 = object2.listIterator();
                for (int i = 0; i < n; ++i) {
                    object.next();
                    object.set(object2.next());
                }
            } else {
                for (int i = 0; i < n; ++i) {
                    object.set(i, object2.get(i));
                }
            }
            return;
        }
        throw new IndexOutOfBoundsException("Source does not fit in dest");
    }

    public static boolean disjoint(Collection<?> object, Collection<?> collection) {
        Object object2;
        Collection<?> collection2;
        Collection<?> collection3 = collection;
        Collection<?> collection4 = object;
        if (object instanceof Set) {
            object2 = object;
            collection2 = collection;
        } else {
            object2 = collection3;
            collection2 = collection4;
            if (!(collection instanceof Set)) {
                int n = object.size();
                int n2 = collection.size();
                if (n != 0 && n2 != 0) {
                    object2 = collection3;
                    collection2 = collection4;
                    if (n > n2) {
                        object2 = object;
                        collection2 = collection;
                    }
                } else {
                    return true;
                }
            }
        }
        object = collection2.iterator();
        while (object.hasNext()) {
            if (!object2.contains(object.next())) continue;
            return false;
        }
        return true;
    }

    public static <T> Enumeration<T> emptyEnumeration() {
        return EmptyEnumeration.EMPTY_ENUMERATION;
    }

    public static <T> Iterator<T> emptyIterator() {
        return EmptyIterator.EMPTY_ITERATOR;
    }

    public static final <T> List<T> emptyList() {
        return EMPTY_LIST;
    }

    public static <T> ListIterator<T> emptyListIterator() {
        return EmptyListIterator.EMPTY_ITERATOR;
    }

    public static final <K, V> Map<K, V> emptyMap() {
        return EMPTY_MAP;
    }

    public static final <K, V> NavigableMap<K, V> emptyNavigableMap() {
        return UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
    }

    public static <E> NavigableSet<E> emptyNavigableSet() {
        return UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
    }

    public static final <T> Set<T> emptySet() {
        return EMPTY_SET;
    }

    public static final <K, V> SortedMap<K, V> emptySortedMap() {
        return UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
    }

    public static <E> SortedSet<E> emptySortedSet() {
        return UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
    }

    public static <T> Enumeration<T> enumeration(final Collection<T> collection) {
        return new Enumeration<T>(){
            private final Iterator<T> i;
            {
                this.i = collection.iterator();
            }

            @Override
            public boolean hasMoreElements() {
                return this.i.hasNext();
            }

            @Override
            public T nextElement() {
                return this.i.next();
            }
        };
    }

    static boolean eq(Object object, Object object2) {
        boolean bl = object == null ? object2 == null : object.equals(object2);
        return bl;
    }

    public static <T> void fill(List<? super T> object, T t) {
        int n = object.size();
        if (n >= 25 && !(object instanceof RandomAccess)) {
            object = object.listIterator();
            for (int i = 0; i < n; ++i) {
                object.next();
                object.set(t);
            }
        } else {
            for (int i = 0; i < n; ++i) {
                object.set(i, t);
            }
        }
    }

    public static int frequency(Collection<?> iterator, Object object) {
        int n = 0;
        int n2 = 0;
        if (object == null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                n = n2;
                if (iterator.next() == null) {
                    n = n2 + 1;
                }
                n2 = n;
            }
            n = n2;
        } else {
            iterator = iterator.iterator();
            n2 = n;
            do {
                n = n2;
                if (!iterator.hasNext()) break;
                n = n2;
                if (object.equals(iterator.next())) {
                    n = n2 + 1;
                }
                n2 = n;
            } while (true);
        }
        return n;
    }

    private static <T> T get(ListIterator<? extends T> listIterator, int n) {
        block4 : {
            T t;
            int n2;
            int n3 = n2 = listIterator.nextIndex();
            if (n2 <= n) {
                do {
                    T t2 = listIterator.next();
                    if (n2 >= n) {
                        listIterator = t2;
                        break block4;
                    }
                    ++n2;
                } while (true);
            }
            do {
                t = listIterator.previous();
                n3 = n2 = n3 - 1;
            } while (n2 > n);
            listIterator = t;
        }
        return (T)listIterator;
    }

    public static int indexOfSubList(List<?> object, List<?> list) {
        int n = object.size();
        int n2 = list.size();
        int n3 = n - n2;
        if (!(n < 35 || object instanceof RandomAccess && list instanceof RandomAccess)) {
            object = object.listIterator();
            block0 : for (n = 0; n <= n3; ++n) {
                ListIterator<?> listIterator = list.listIterator();
                for (int i = 0; i < n2; ++i) {
                    if (Collections.eq(listIterator.next(), object.next())) continue;
                    for (int j = 0; j < i; ++j) {
                        object.previous();
                    }
                    continue block0;
                }
                return n;
            }
        } else {
            block3 : for (n = 0; n <= n3; ++n) {
                int n4 = 0;
                int n5 = n;
                while (n4 < n2) {
                    if (!Collections.eq(list.get(n4), object.get(n5))) {
                        continue block3;
                    }
                    ++n4;
                    ++n5;
                }
                return n;
            }
        }
        return -1;
    }

    private static <T> int indexedBinarySearch(List<? extends Comparable<? super T>> list, T t) {
        int n = 0;
        int n2 = list.size() - 1;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = list.get(n3).compareTo(t);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 > 0) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    private static <T> int indexedBinarySearch(List<? extends T> list, T t, Comparator<? super T> comparator) {
        int n = 0;
        int n2 = list.size() - 1;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = comparator.compare(list.get(n3), t);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 > 0) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    private static <T> int iteratorBinarySearch(List<? extends Comparable<? super T>> object, T t) {
        int n = 0;
        int n2 = object.size() - 1;
        object = object.listIterator();
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = ((Comparable)Collections.get(object, n3)).compareTo(t);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 > 0) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    private static <T> int iteratorBinarySearch(List<? extends T> object, T t, Comparator<? super T> comparator) {
        int n = 0;
        int n2 = object.size() - 1;
        object = object.listIterator();
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = comparator.compare(Collections.get(object, n3), t);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 > 0) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int lastIndexOfSubList(List<?> object, List<?> list) {
        int n;
        int n2 = object.size();
        int n3 = list.size();
        if (n2 >= 35 && !(object instanceof RandomAccess)) {
            if (n < 0) {
                return -1;
            }
            object = object.listIterator(n);
            block0 : for (n = n2 - n3; n >= 0; --n) {
                ListIterator<?> listIterator = list.listIterator();
                for (n2 = 0; n2 < n3; ++n2) {
                    if (Collections.eq(listIterator.next(), object.next())) continue;
                    if (n == 0) continue block0;
                    for (int i = 0; i <= n2 + 1; ++i) {
                        object.previous();
                    }
                    continue block0;
                }
                return n;
            }
        } else {
            block3 : while (n >= 0) {
                int n4 = 0;
                n2 = n;
                while (n4 < n3) {
                    if (!Collections.eq(list.get(n4), object.get(n2))) {
                        --n;
                        continue block3;
                    }
                    ++n4;
                    ++n2;
                }
                return n;
            }
        }
        return -1;
    }

    public static <T> ArrayList<T> list(Enumeration<T> enumeration) {
        ArrayList<T> arrayList = new ArrayList<T>();
        while (enumeration.hasMoreElements()) {
            arrayList.add(enumeration.nextElement());
        }
        return arrayList;
    }

    public static <T> T max(Collection<? extends T> collection) {
        Iterator<T> iterator = collection.iterator();
        Object object = iterator.next();
        while (iterator.hasNext()) {
            T t = iterator.next();
            collection = object;
            if (((Comparable)t).compareTo(object) > 0) {
                collection = t;
            }
            object = collection;
        }
        return object;
    }

    public static <T> T max(Collection<? extends T> collection, Comparator<? super T> comparator) {
        if (comparator == null) {
            return Collections.max(collection);
        }
        Iterator<T> iterator = collection.iterator();
        Object object = iterator.next();
        while (iterator.hasNext()) {
            T t = iterator.next();
            collection = object;
            if (comparator.compare(t, object) > 0) {
                collection = t;
            }
            object = collection;
        }
        return object;
    }

    public static <T> T min(Collection<? extends T> collection) {
        Iterator<T> iterator = collection.iterator();
        collection = iterator.next();
        while (iterator.hasNext()) {
            T t = iterator.next();
            Collection<T> collection2 = collection;
            if (((Comparable)t).compareTo(collection) < 0) {
                collection2 = t;
            }
            collection = collection2;
        }
        return (T)collection;
    }

    public static <T> T min(Collection<? extends T> collection, Comparator<? super T> comparator) {
        if (comparator == null) {
            return Collections.min(collection);
        }
        Iterator<T> iterator = collection.iterator();
        collection = iterator.next();
        while (iterator.hasNext()) {
            T t = iterator.next();
            Collection<T> collection2 = collection;
            if (comparator.compare((Collection<T>)t, collection) < 0) {
                collection2 = t;
            }
            collection = collection2;
        }
        return (T)collection;
    }

    public static <T> List<T> nCopies(int n, T object) {
        if (n >= 0) {
            return new CopiesList<T>(n, object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("List length = ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return new SetFromMap<E>(map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T> boolean replaceAll(List<T> listIterator, T t, T t2) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        int n = listIterator.size();
        if (n >= 11 && !(listIterator instanceof RandomAccess)) {
            listIterator = listIterator.listIterator();
            if (t == null) {
                for (int i = 0; i < n; ++i) {
                    if (listIterator.next() != null) continue;
                    listIterator.set(t2);
                    bl4 = true;
                }
                return bl4;
            }
            int n2 = 0;
            do {
                bl4 = bl;
                if (n2 >= n) return bl4;
                {
                    if (t.equals(listIterator.next())) {
                        listIterator.set(t2);
                        bl = true;
                    }
                    ++n2;
                    continue;
                }
                break;
            } while (true);
        }
        if (t == null) {
            bl4 = bl2;
            for (int i = 0; i < n; ++i) {
                if (listIterator.get(i) != null) continue;
                listIterator.set(i, t2);
                bl4 = true;
            }
            return bl4;
        }
        int n3 = 0;
        bl = bl3;
        do {
            bl4 = bl;
            if (n3 >= n) return bl4;
            if (t.equals(listIterator.get(n3))) {
                listIterator.set(n3, t2);
                bl = true;
            }
            ++n3;
        } while (true);
    }

    public static void reverse(List<?> list) {
        int n = list.size();
        if (n >= 18 && !(list instanceof RandomAccess)) {
            ListIterator<?> listIterator = list.listIterator();
            ListIterator<?> listIterator2 = list.listIterator(n);
            int n2 = list.size();
            for (int i = 0; i < n2 >> 1; ++i) {
                list = listIterator.next();
                listIterator.set(listIterator2.previous());
                listIterator2.set(list);
            }
        } else {
            int n3 = 0;
            int n4 = n - 1;
            while (n3 < n >> 1) {
                Collections.swap(list, n3, n4);
                ++n3;
                --n4;
            }
        }
    }

    public static <T> Comparator<T> reverseOrder() {
        return ReverseComparator.REVERSE_ORDER;
    }

    public static <T> Comparator<T> reverseOrder(Comparator<T> comparator) {
        if (comparator == null) {
            return Collections.reverseOrder();
        }
        if (comparator instanceof ReverseComparator2) {
            return ((ReverseComparator2)comparator).cmp;
        }
        return new ReverseComparator2<T>(comparator);
    }

    public static void rotate(List<?> list, int n) {
        if (!(list instanceof RandomAccess) && list.size() >= 100) {
            Collections.rotate2(list, n);
        } else {
            Collections.rotate1(list, n);
        }
    }

    private static <T> void rotate1(List<T> list, int n) {
        int n2 = list.size();
        if (n2 == 0) {
            return;
        }
        int n3 = n %= n2;
        if (n < 0) {
            n3 = n + n2;
        }
        if (n3 == 0) {
            return;
        }
        n = 0;
        int n4 = 0;
        while (n4 != n2) {
            int n5;
            T t = list.get(n);
            int n6 = n;
            int n7 = n4;
            do {
                n4 = n6 += n3;
                if (n6 >= n2) {
                    n4 = n6 - n2;
                }
                t = list.set(n4, t);
                n7 = n5 = n7 + 1;
                n6 = n4;
            } while (n4 != n);
            ++n;
            n4 = n5;
        }
    }

    private static void rotate2(List<?> list, int n) {
        int n2;
        int n3 = list.size();
        if (n3 == 0) {
            return;
        }
        n = n2 = -n % n3;
        if (n2 < 0) {
            n = n2 + n3;
        }
        if (n == 0) {
            return;
        }
        Collections.reverse(list.subList(0, n));
        Collections.reverse(list.subList(n, n3));
        Collections.reverse(list);
    }

    public static void shuffle(List<?> list) {
        Random random;
        Random random2 = random = r;
        if (random == null) {
            random2 = random = new Random();
            r = random;
        }
        Collections.shuffle(list, random2);
    }

    public static void shuffle(List<?> object, Random random) {
        int n = object.size();
        if (n >= 5 && !(object instanceof RandomAccess)) {
            Object[] arrobject = object.toArray();
            while (n > 1) {
                Collections.swap(arrobject, n - 1, random.nextInt(n));
                --n;
            }
            object = object.listIterator();
            for (n = 0; n < arrobject.length; ++n) {
                object.next();
                object.set(arrobject[n]);
            }
        } else {
            while (n > 1) {
                Collections.swap(object, n - 1, random.nextInt(n));
                --n;
            }
        }
    }

    public static <T> Set<T> singleton(T t) {
        return new SingletonSet<T>(t);
    }

    static <E> Iterator<E> singletonIterator(final E e) {
        return new Iterator<E>(){
            private boolean hasNext = true;

            @Override
            public void forEachRemaining(Consumer<? super E> consumer) {
                Objects.requireNonNull(consumer);
                if (this.hasNext) {
                    consumer.accept(e);
                    this.hasNext = false;
                }
            }

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public E next() {
                if (this.hasNext) {
                    this.hasNext = false;
                    return (E)e;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <T> List<T> singletonList(T t) {
        return new SingletonList<T>(t);
    }

    public static <K, V> Map<K, V> singletonMap(K k, V v) {
        return new SingletonMap<K, V>(k, v);
    }

    static <T> Spliterator<T> singletonSpliterator(final T t) {
        return new Spliterator<T>(){
            long est = 1L;

            @Override
            public int characteristics() {
                int n = t != null ? 256 : 0;
                return n | 64 | 16384 | 1024 | 1 | 16;
            }

            @Override
            public long estimateSize() {
                return this.est;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                this.tryAdvance(consumer);
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                long l = this.est;
                if (l > 0L) {
                    this.est = l - 1L;
                    consumer.accept(t);
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }
        };
    }

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        Collections.sort(list, null);
    }

    public static <T> void sort(List<T> object, Comparator<? super T> comparator) {
        if (VMRuntime.getRuntime().getTargetSdkVersion() > 25) {
            object.sort(comparator);
        } else {
            if (object.getClass() == ArrayList.class) {
                Arrays.sort(((ArrayList)object).elementData, 0, object.size(), comparator);
                return;
            }
            Object[] arrobject = object.toArray();
            Arrays.sort(arrobject, comparator);
            object = object.listIterator();
            for (int i = 0; i < arrobject.length; ++i) {
                object.next();
                object.set(arrobject[i]);
            }
        }
    }

    public static void swap(List<?> list, int n, int n2) {
        list.set(n, list.set(n2, list.get(n)));
    }

    private static void swap(Object[] arrobject, int n, int n2) {
        Object object = arrobject[n];
        arrobject[n] = arrobject[n2];
        arrobject[n2] = object;
    }

    public static <T> Collection<T> synchronizedCollection(Collection<T> collection) {
        return new SynchronizedCollection<T>(collection);
    }

    static <T> Collection<T> synchronizedCollection(Collection<T> collection, Object object) {
        return new SynchronizedCollection<T>(collection, object);
    }

    public static <T> List<T> synchronizedList(List<T> synchronizedList) {
        synchronizedList = synchronizedList instanceof RandomAccess ? new SynchronizedRandomAccessList<T>(synchronizedList) : new SynchronizedList<T>(synchronizedList);
        return synchronizedList;
    }

    static <T> List<T> synchronizedList(List<T> synchronizedList, Object object) {
        synchronizedList = synchronizedList instanceof RandomAccess ? new SynchronizedRandomAccessList<T>(synchronizedList, object) : new SynchronizedList<T>(synchronizedList, object);
        return synchronizedList;
    }

    public static <K, V> Map<K, V> synchronizedMap(Map<K, V> map) {
        return new SynchronizedMap<K, V>(map);
    }

    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
        return new SynchronizedNavigableMap<K, V>(navigableMap);
    }

    public static <T> NavigableSet<T> synchronizedNavigableSet(NavigableSet<T> navigableSet) {
        return new SynchronizedNavigableSet<T>(navigableSet);
    }

    public static <T> Set<T> synchronizedSet(Set<T> set) {
        return new SynchronizedSet<T>(set);
    }

    static <T> Set<T> synchronizedSet(Set<T> set, Object object) {
        return new SynchronizedSet<T>(set, object);
    }

    public static <K, V> SortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> sortedMap) {
        return new SynchronizedSortedMap<K, V>(sortedMap);
    }

    public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> sortedSet) {
        return new SynchronizedSortedSet<T>(sortedSet);
    }

    public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> collection) {
        return new UnmodifiableCollection<T>(collection);
    }

    public static <T> List<T> unmodifiableList(List<? extends T> unmodifiableList) {
        unmodifiableList = unmodifiableList instanceof RandomAccess ? new UnmodifiableRandomAccessList<T>(unmodifiableList) : new UnmodifiableList<T>(unmodifiableList);
        return unmodifiableList;
    }

    public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> map) {
        return new UnmodifiableMap<K, V>(map);
    }

    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
        return new UnmodifiableNavigableMap<K, V>(navigableMap);
    }

    public static <T> NavigableSet<T> unmodifiableNavigableSet(NavigableSet<T> navigableSet) {
        return new UnmodifiableNavigableSet<T>(navigableSet);
    }

    public static <T> Set<T> unmodifiableSet(Set<? extends T> set) {
        return new UnmodifiableSet<T>(set);
    }

    public static <K, V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> sortedMap) {
        return new UnmodifiableSortedMap<K, V>(sortedMap);
    }

    public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> sortedSet) {
        return new UnmodifiableSortedSet<T>(sortedSet);
    }

    static <T> T[] zeroLengthArray(Class<T> class_) {
        return (Object[])Array.newInstance(class_, 0);
    }

    static class AsLIFOQueue<E>
    extends AbstractQueue<E>
    implements Queue<E>,
    Serializable {
        private static final long serialVersionUID = 1802017725587941708L;
        private final Deque<E> q;

        AsLIFOQueue(Deque<E> deque) {
            this.q = deque;
        }

        @Override
        public boolean add(E e) {
            this.q.addFirst(e);
            return true;
        }

        @Override
        public void clear() {
            this.q.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.q.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.q.containsAll(collection);
        }

        @Override
        public E element() {
            return this.q.getFirst();
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            this.q.forEach(consumer);
        }

        @Override
        public boolean isEmpty() {
            return this.q.isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            return this.q.iterator();
        }

        @Override
        public boolean offer(E e) {
            return this.q.offerFirst(e);
        }

        @Override
        public Stream<E> parallelStream() {
            return this.q.parallelStream();
        }

        @Override
        public E peek() {
            return this.q.peekFirst();
        }

        @Override
        public E poll() {
            return this.q.pollFirst();
        }

        @Override
        public E remove() {
            return this.q.removeFirst();
        }

        @Override
        public boolean remove(Object object) {
            return this.q.remove(object);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.q.removeAll(collection);
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            return this.q.removeIf(predicate);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.q.retainAll(collection);
        }

        @Override
        public int size() {
            return this.q.size();
        }

        @Override
        public Spliterator<E> spliterator() {
            return this.q.spliterator();
        }

        @Override
        public Stream<E> stream() {
            return this.q.stream();
        }

        @Override
        public Object[] toArray() {
            return this.q.toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.q.toArray(arrT);
        }

        @Override
        public String toString() {
            return this.q.toString();
        }
    }

    static class CheckedCollection<E>
    implements Collection<E>,
    Serializable {
        private static final long serialVersionUID = 1578914078182001775L;
        final Collection<E> c;
        final Class<E> type;
        private E[] zeroLengthElementArray;

        CheckedCollection(Collection<E> collection, Class<E> class_) {
            this.c = Objects.requireNonNull(collection, "c");
            this.type = Objects.requireNonNull(class_, "type");
        }

        private String badElementMsg(Object object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempt to insert ");
            stringBuilder.append(object.getClass());
            stringBuilder.append(" element into collection with element type ");
            stringBuilder.append(this.type);
            return stringBuilder.toString();
        }

        private E[] zeroLengthElementArray() {
            E[] arrE = this.zeroLengthElementArray;
            if (arrE == null) {
                this.zeroLengthElementArray = arrE = Collections.zeroLengthArray(this.type);
            }
            return arrE;
        }

        @Override
        public boolean add(E e) {
            return this.c.add(this.typeCheck(e));
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            return this.c.addAll(this.checkedCopyOf(collection));
        }

        Collection<E> checkedCopyOf(Collection<? extends E> arrobject) {
            E[] arrE;
            E[] arrE2 = this.zeroLengthElementArray();
            Object[] arrobject2 = arrE = arrobject.toArray(arrE2);
            try {
                if (arrE.getClass() != arrE2.getClass()) {
                    arrobject2 = Arrays.copyOf(arrE, arrE.length, arrE2.getClass());
                }
            }
            catch (ArrayStoreException arrayStoreException) {
                arrobject = (Object[])arrobject.toArray().clone();
                int n = arrobject.length;
                int n2 = 0;
                do {
                    arrobject2 = arrobject;
                    if (n2 >= n) break;
                    this.typeCheck(arrobject[n2]);
                    ++n2;
                } while (true);
            }
            return Arrays.asList(arrobject2);
        }

        @Override
        public void clear() {
            this.c.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.c.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.c.containsAll(collection);
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            this.c.forEach(consumer);
        }

        @Override
        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>(this.c.iterator()){
                final /* synthetic */ Iterator val$it;
                {
                    this.val$it = iterator;
                }

                @Override
                public boolean hasNext() {
                    return this.val$it.hasNext();
                }

                @Override
                public E next() {
                    return this.val$it.next();
                }

                @Override
                public void remove() {
                    this.val$it.remove();
                }
            };
        }

        @Override
        public Stream<E> parallelStream() {
            return this.c.parallelStream();
        }

        @Override
        public boolean remove(Object object) {
            return this.c.remove(object);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.c.removeAll(collection);
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            return this.c.removeIf(predicate);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.c.retainAll(collection);
        }

        @Override
        public int size() {
            return this.c.size();
        }

        @Override
        public Spliterator<E> spliterator() {
            return this.c.spliterator();
        }

        @Override
        public Stream<E> stream() {
            return this.c.stream();
        }

        @Override
        public Object[] toArray() {
            return this.c.toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.c.toArray(arrT);
        }

        public String toString() {
            return this.c.toString();
        }

        E typeCheck(Object object) {
            if (object != null && !this.type.isInstance(object)) {
                throw new ClassCastException(this.badElementMsg(object));
            }
            return (E)object;
        }

    }

    static class CheckedList<E>
    extends CheckedCollection<E>
    implements List<E> {
        private static final long serialVersionUID = 65247728283967356L;
        final List<E> list;

        CheckedList(List<E> list, Class<E> class_) {
            super(list, class_);
            this.list = list;
        }

        @Override
        public void add(int n, E e) {
            this.list.add(n, this.typeCheck(e));
        }

        @Override
        public boolean addAll(int n, Collection<? extends E> collection) {
            return this.list.addAll(n, this.checkedCopyOf(collection));
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || this.list.equals(object);
            return bl;
        }

        @Override
        public E get(int n) {
            return this.list.get(n);
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public int indexOf(Object object) {
            return this.list.indexOf(object);
        }

        public /* synthetic */ Object lambda$replaceAll$0$Collections$CheckedList(UnaryOperator unaryOperator, Object object) {
            return this.typeCheck(unaryOperator.apply(object));
        }

        @Override
        public int lastIndexOf(Object object) {
            return this.list.lastIndexOf(object);
        }

        @Override
        public ListIterator<E> listIterator() {
            return this.listIterator(0);
        }

        @Override
        public ListIterator<E> listIterator(int n) {
            return new ListIterator<E>(this.list.listIterator(n)){
                final /* synthetic */ ListIterator val$i;
                {
                    this.val$i = listIterator;
                }

                @Override
                public void add(E e) {
                    this.val$i.add(this.typeCheck(e));
                }

                @Override
                public void forEachRemaining(Consumer<? super E> consumer) {
                    this.val$i.forEachRemaining(consumer);
                }

                @Override
                public boolean hasNext() {
                    return this.val$i.hasNext();
                }

                @Override
                public boolean hasPrevious() {
                    return this.val$i.hasPrevious();
                }

                @Override
                public E next() {
                    return this.val$i.next();
                }

                @Override
                public int nextIndex() {
                    return this.val$i.nextIndex();
                }

                @Override
                public E previous() {
                    return this.val$i.previous();
                }

                @Override
                public int previousIndex() {
                    return this.val$i.previousIndex();
                }

                @Override
                public void remove() {
                    this.val$i.remove();
                }

                @Override
                public void set(E e) {
                    this.val$i.set(this.typeCheck(e));
                }
            };
        }

        @Override
        public E remove(int n) {
            return this.list.remove(n);
        }

        @Override
        public void replaceAll(UnaryOperator<E> unaryOperator) {
            Objects.requireNonNull(unaryOperator);
            this.list.replaceAll(new _$$Lambda$Collections$CheckedList$gXIP1Db1_l1aVeW3UfOh4dLyESo(this, unaryOperator));
        }

        @Override
        public E set(int n, E e) {
            return this.list.set(n, this.typeCheck(e));
        }

        @Override
        public void sort(Comparator<? super E> comparator) {
            this.list.sort(comparator);
        }

        @Override
        public List<E> subList(int n, int n2) {
            return new CheckedList<E>(this.list.subList(n, n2), this.type);
        }

    }

    private static class CheckedMap<K, V>
    implements Map<K, V>,
    Serializable {
        private static final long serialVersionUID = 5742860141034234728L;
        private transient Set<Map.Entry<K, V>> entrySet;
        final Class<K> keyType;
        private final Map<K, V> m;
        final Class<V> valueType;

        CheckedMap(Map<K, V> map, Class<K> class_, Class<V> class_2) {
            this.m = Objects.requireNonNull(map);
            this.keyType = Objects.requireNonNull(class_);
            this.valueType = Objects.requireNonNull(class_2);
        }

        private String badKeyMsg(Object object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempt to insert ");
            stringBuilder.append(object.getClass());
            stringBuilder.append(" key into map with key type ");
            stringBuilder.append(this.keyType);
            return stringBuilder.toString();
        }

        private String badValueMsg(Object object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempt to insert ");
            stringBuilder.append(object.getClass());
            stringBuilder.append(" value into map with value type ");
            stringBuilder.append(this.valueType);
            return stringBuilder.toString();
        }

        private BiFunction<? super K, ? super V, ? extends V> typeCheck(BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Objects.requireNonNull(biFunction);
            return new _$$Lambda$Collections$CheckedMap$5MCuh91_pd5SsNapFva5jp8gVs8(this, biFunction);
        }

        private void typeCheck(Object object, Object object2) {
            if (object != null && !this.keyType.isInstance(object)) {
                throw new ClassCastException(this.badKeyMsg(object));
            }
            if (object2 != null && !this.valueType.isInstance(object2)) {
                throw new ClassCastException(this.badValueMsg(object2));
            }
        }

        @Override
        public void clear() {
            this.m.clear();
        }

        @Override
        public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            return this.m.compute((K)k, this.typeCheck(biFunction));
        }

        @Override
        public V computeIfAbsent(K k, Function<? super K, ? extends V> function) {
            Objects.requireNonNull(function);
            return this.m.computeIfAbsent(k, new _$$Lambda$Collections$CheckedMap$hvicTzt8soLX23klS8sBMiCuEvk(this, function));
        }

        @Override
        public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            return this.m.computeIfPresent((K)k, this.typeCheck(biFunction));
        }

        @Override
        public boolean containsKey(Object object) {
            return this.m.containsKey(object);
        }

        @Override
        public boolean containsValue(Object object) {
            return this.m.containsValue(object);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = new CheckedEntrySet<K, V>(this.m.entrySet(), this.valueType);
            }
            return this.entrySet;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || this.m.equals(object);
            return bl;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
            this.m.forEach(biConsumer);
        }

        @Override
        public V get(Object object) {
            return this.m.get(object);
        }

        @Override
        public int hashCode() {
            return this.m.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public Set<K> keySet() {
            return this.m.keySet();
        }

        public /* synthetic */ Object lambda$computeIfAbsent$1$Collections$CheckedMap(Function function, Object object) {
            function = function.apply(object);
            this.typeCheck(object, function);
            return function;
        }

        public /* synthetic */ Object lambda$merge$2$Collections$CheckedMap(BiFunction biFunction, Object object, Object object2) {
            biFunction = biFunction.apply(object, object2);
            this.typeCheck(null, biFunction);
            return biFunction;
        }

        public /* synthetic */ Object lambda$typeCheck$0$Collections$CheckedMap(BiFunction biFunction, Object object, Object object2) {
            biFunction = biFunction.apply(object, object2);
            this.typeCheck(object, biFunction);
            return biFunction;
        }

        @Override
        public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Objects.requireNonNull(biFunction);
            return this.m.merge(k, v, new _$$Lambda$Collections$CheckedMap$UHd_dm3NjSdMnE1bZpQe0MXp_Gk(this, biFunction));
        }

        @Override
        public V put(K k, V v) {
            this.typeCheck(k, v);
            return this.m.put(k, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> object) {
            object = object.entrySet().toArray();
            ArrayList arrayList = new ArrayList(((Object)object).length);
            int n = ((Object)object).length;
            for (int i = 0; i < n; ++i) {
                Map.Entry entry = (Map.Entry)object[i];
                Object object2 = entry.getKey();
                entry = entry.getValue();
                this.typeCheck(object2, entry);
                arrayList.add(new AbstractMap.SimpleImmutableEntry(object2, entry));
            }
            for (Map.Entry entry : arrayList) {
                this.m.put(entry.getKey(), entry.getValue());
            }
        }

        @Override
        public V putIfAbsent(K k, V v) {
            this.typeCheck(k, v);
            return this.m.putIfAbsent(k, v);
        }

        @Override
        public V remove(Object object) {
            return this.m.remove(object);
        }

        @Override
        public boolean remove(Object object, Object object2) {
            return this.m.remove(object, object2);
        }

        @Override
        public V replace(K k, V v) {
            this.typeCheck(k, v);
            return this.m.replace(k, v);
        }

        @Override
        public boolean replace(K k, V v, V v2) {
            this.typeCheck(k, v2);
            return this.m.replace(k, v, v2);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
            this.m.replaceAll(this.typeCheck(biFunction));
        }

        @Override
        public int size() {
            return this.m.size();
        }

        public String toString() {
            return this.m.toString();
        }

        @Override
        public Collection<V> values() {
            return this.m.values();
        }

        static class CheckedEntrySet<K, V>
        implements Set<Map.Entry<K, V>> {
            private final Set<Map.Entry<K, V>> s;
            private final Class<V> valueType;

            CheckedEntrySet(Set<Map.Entry<K, V>> set, Class<V> class_) {
                this.s = set;
                this.valueType = class_;
            }

            private boolean batchRemove(Collection<?> collection, boolean bl) {
                Objects.requireNonNull(collection);
                boolean bl2 = false;
                Iterator<Map.Entry<K, V>> iterator = this.iterator();
                while (iterator.hasNext()) {
                    if (collection.contains(iterator.next()) == bl) continue;
                    iterator.remove();
                    bl2 = true;
                }
                return bl2;
            }

            static <K, V, T> CheckedEntry<K, V, T> checkedEntry(Map.Entry<K, V> entry, Class<T> class_) {
                return new CheckedEntry<K, V, T>(entry, class_);
            }

            @Override
            public boolean add(Map.Entry<K, V> entry) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void clear() {
                this.s.clear();
            }

            @Override
            public boolean contains(Object checkedEntry) {
                if (!(checkedEntry instanceof Map.Entry)) {
                    return false;
                }
                checkedEntry = checkedEntry;
                Set<Map.Entry<K, V>> set = this.s;
                if (!(checkedEntry instanceof CheckedEntry)) {
                    checkedEntry = CheckedEntrySet.checkedEntry(checkedEntry, this.valueType);
                }
                return set.contains(checkedEntry);
            }

            @Override
            public boolean containsAll(Collection<?> object) {
                object = object.iterator();
                while (object.hasNext()) {
                    if (this.contains(object.next())) continue;
                    return false;
                }
                return true;
            }

            @Override
            public boolean equals(Object object) {
                boolean bl = true;
                if (object == this) {
                    return true;
                }
                if (!(object instanceof Set)) {
                    return false;
                }
                if ((object = (Set)object).size() != this.s.size() || !this.containsAll((Collection<?>)object)) {
                    bl = false;
                }
                return bl;
            }

            @Override
            public int hashCode() {
                return this.s.hashCode();
            }

            @Override
            public boolean isEmpty() {
                return this.s.isEmpty();
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new Iterator<Map.Entry<K, V>>(this.s.iterator(), this.valueType){
                    final /* synthetic */ Iterator val$i;
                    final /* synthetic */ Class val$valueType;
                    {
                        this.val$i = iterator;
                        this.val$valueType = class_;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.val$i.hasNext();
                    }

                    @Override
                    public Map.Entry<K, V> next() {
                        return CheckedEntrySet.checkedEntry((Map.Entry)this.val$i.next(), this.val$valueType);
                    }

                    @Override
                    public void remove() {
                        this.val$i.remove();
                    }
                };
            }

            @Override
            public boolean remove(Object object) {
                if (!(object instanceof Map.Entry)) {
                    return false;
                }
                return this.s.remove(new AbstractMap.SimpleImmutableEntry((Map.Entry)object));
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return this.batchRemove(collection, false);
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return this.batchRemove(collection, true);
            }

            @Override
            public int size() {
                return this.s.size();
            }

            @Override
            public Object[] toArray() {
                Object[] arrobject = this.s.toArray();
                Object[] arrobject2 = CheckedEntry.class.isInstance(arrobject.getClass().getComponentType()) ? arrobject : new Object[arrobject.length];
                for (int i = 0; i < arrobject.length; ++i) {
                    arrobject2[i] = CheckedEntrySet.checkedEntry((Map.Entry)arrobject[i], this.valueType);
                }
                return arrobject2;
            }

            @Override
            public <T> T[] toArray(T[] arrT) {
                Set<Map.Entry<K, V>> set = this.s;
                T[] arrT2 = arrT.length == 0 ? arrT : Arrays.copyOf(arrT, 0);
                arrT2 = set.toArray(arrT2);
                for (int i = 0; i < arrT2.length; ++i) {
                    arrT2[i] = CheckedEntrySet.checkedEntry((Map.Entry)arrT2[i], this.valueType);
                }
                if (arrT2.length > arrT.length) {
                    return arrT2;
                }
                System.arraycopy(arrT2, 0, arrT, 0, arrT2.length);
                if (arrT.length > arrT2.length) {
                    arrT[arrT2.length] = null;
                }
                return arrT;
            }

            public String toString() {
                return this.s.toString();
            }

            private static class CheckedEntry<K, V, T>
            implements Map.Entry<K, V> {
                private final Map.Entry<K, V> e;
                private final Class<T> valueType;

                CheckedEntry(Map.Entry<K, V> entry, Class<T> class_) {
                    this.e = Objects.requireNonNull(entry);
                    this.valueType = Objects.requireNonNull(class_);
                }

                private String badValueMsg(Object object) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Attempt to insert ");
                    stringBuilder.append(object.getClass());
                    stringBuilder.append(" value into map with value type ");
                    stringBuilder.append(this.valueType);
                    return stringBuilder.toString();
                }

                @Override
                public boolean equals(Object object) {
                    if (object == this) {
                        return true;
                    }
                    if (!(object instanceof Map.Entry)) {
                        return false;
                    }
                    return this.e.equals(new AbstractMap.SimpleImmutableEntry((Map.Entry)object));
                }

                @Override
                public K getKey() {
                    return this.e.getKey();
                }

                @Override
                public V getValue() {
                    return this.e.getValue();
                }

                @Override
                public int hashCode() {
                    return this.e.hashCode();
                }

                @Override
                public V setValue(V v) {
                    if (v != null && !this.valueType.isInstance(v)) {
                        throw new ClassCastException(this.badValueMsg(v));
                    }
                    return this.e.setValue(v);
                }

                public String toString() {
                    return this.e.toString();
                }
            }

        }

    }

    static class CheckedNavigableMap<K, V>
    extends CheckedSortedMap<K, V>
    implements NavigableMap<K, V>,
    Serializable {
        private static final long serialVersionUID = -4852462692372534096L;
        private final NavigableMap<K, V> nm;

        CheckedNavigableMap(NavigableMap<K, V> navigableMap, Class<K> class_, Class<V> class_2) {
            super(navigableMap, class_, class_2);
            this.nm = navigableMap;
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K object) {
            object = (object = this.nm.ceilingEntry(object)) != null ? new CheckedMap.CheckedEntrySet.CheckedEntry(object, this.valueType) : null;
            return object;
        }

        @Override
        public K ceilingKey(K k) {
            return this.nm.ceilingKey(k);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.nm.comparator();
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return Collections.checkedNavigableSet(this.nm.descendingKeySet(), this.keyType);
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return Collections.checkedNavigableMap(this.nm.descendingMap(), this.keyType, this.valueType);
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            Map.Entry<K, V> entry = this.nm.firstEntry();
            entry = entry != null ? new CheckedMap.CheckedEntrySet.CheckedEntry(entry, this.valueType) : null;
            return entry;
        }

        @Override
        public K firstKey() {
            return this.nm.firstKey();
        }

        @Override
        public Map.Entry<K, V> floorEntry(K object) {
            object = (object = this.nm.floorEntry(object)) != null ? new CheckedMap.CheckedEntrySet.CheckedEntry(object, this.valueType) : null;
            return object;
        }

        @Override
        public K floorKey(K k) {
            return this.nm.floorKey(k);
        }

        @Override
        public NavigableMap<K, V> headMap(K k) {
            return Collections.checkedNavigableMap(this.nm.headMap(k, false), this.keyType, this.valueType);
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return Collections.checkedNavigableMap(this.nm.headMap(k, bl), this.keyType, this.valueType);
        }

        @Override
        public Map.Entry<K, V> higherEntry(K object) {
            object = (object = this.nm.higherEntry(object)) != null ? new CheckedMap.CheckedEntrySet.CheckedEntry(object, this.valueType) : null;
            return object;
        }

        @Override
        public K higherKey(K k) {
            return this.nm.higherKey(k);
        }

        @Override
        public NavigableSet<K> keySet() {
            return this.navigableKeySet();
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            Map.Entry<K, V> entry = this.nm.lastEntry();
            entry = entry != null ? new CheckedMap.CheckedEntrySet.CheckedEntry(entry, this.valueType) : null;
            return entry;
        }

        @Override
        public K lastKey() {
            return this.nm.lastKey();
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K object) {
            object = (object = this.nm.lowerEntry(object)) != null ? new CheckedMap.CheckedEntrySet.CheckedEntry(object, this.valueType) : null;
            return object;
        }

        @Override
        public K lowerKey(K k) {
            return this.nm.lowerKey(k);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return Collections.checkedNavigableSet(this.nm.navigableKeySet(), this.keyType);
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            Map.Entry<K, V> entry = this.nm.pollFirstEntry();
            entry = entry == null ? null : new CheckedMap.CheckedEntrySet.CheckedEntry(entry, this.valueType);
            return entry;
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            Map.Entry<K, V> entry = this.nm.pollLastEntry();
            entry = entry == null ? null : new CheckedMap.CheckedEntrySet.CheckedEntry(entry, this.valueType);
            return entry;
        }

        @Override
        public NavigableMap<K, V> subMap(K k, K k2) {
            return Collections.checkedNavigableMap(this.nm.subMap(k, true, k2, false), this.keyType, this.valueType);
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Collections.checkedNavigableMap(this.nm.subMap(k, bl, k2, bl2), this.keyType, this.valueType);
        }

        @Override
        public NavigableMap<K, V> tailMap(K k) {
            return Collections.checkedNavigableMap(this.nm.tailMap(k, true), this.keyType, this.valueType);
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return Collections.checkedNavigableMap(this.nm.tailMap(k, bl), this.keyType, this.valueType);
        }
    }

    static class CheckedNavigableSet<E>
    extends CheckedSortedSet<E>
    implements NavigableSet<E>,
    Serializable {
        private static final long serialVersionUID = -5429120189805438922L;
        private final NavigableSet<E> ns;

        CheckedNavigableSet(NavigableSet<E> navigableSet, Class<E> class_) {
            super(navigableSet, class_);
            this.ns = navigableSet;
        }

        @Override
        public E ceiling(E e) {
            return this.ns.ceiling(e);
        }

        @Override
        public Iterator<E> descendingIterator() {
            return Collections.checkedNavigableSet(this.ns.descendingSet(), this.type).iterator();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return Collections.checkedNavigableSet(this.ns.descendingSet(), this.type);
        }

        @Override
        public E floor(E e) {
            return this.ns.floor(e);
        }

        @Override
        public NavigableSet<E> headSet(E e) {
            return Collections.checkedNavigableSet(this.ns.headSet(e, false), this.type);
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return Collections.checkedNavigableSet(this.ns.headSet(e, bl), this.type);
        }

        @Override
        public E higher(E e) {
            return this.ns.higher(e);
        }

        @Override
        public E lower(E e) {
            return this.ns.lower(e);
        }

        @Override
        public E pollFirst() {
            return this.ns.pollFirst();
        }

        @Override
        public E pollLast() {
            return this.ns.pollLast();
        }

        @Override
        public NavigableSet<E> subSet(E e, E e2) {
            return Collections.checkedNavigableSet(this.ns.subSet(e, true, e2, false), this.type);
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return Collections.checkedNavigableSet(this.ns.subSet(e, bl, e2, bl2), this.type);
        }

        @Override
        public NavigableSet<E> tailSet(E e) {
            return Collections.checkedNavigableSet(this.ns.tailSet(e, true), this.type);
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return Collections.checkedNavigableSet(this.ns.tailSet(e, bl), this.type);
        }
    }

    static class CheckedQueue<E>
    extends CheckedCollection<E>
    implements Queue<E>,
    Serializable {
        private static final long serialVersionUID = 1433151992604707767L;
        final Queue<E> queue;

        CheckedQueue(Queue<E> queue, Class<E> class_) {
            super(queue, class_);
            this.queue = queue;
        }

        @Override
        public E element() {
            return this.queue.element();
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || this.c.equals(object);
            return bl;
        }

        @Override
        public int hashCode() {
            return this.c.hashCode();
        }

        @Override
        public boolean offer(E e) {
            return this.queue.offer(this.typeCheck(e));
        }

        @Override
        public E peek() {
            return this.queue.peek();
        }

        @Override
        public E poll() {
            return this.queue.poll();
        }

        @Override
        public E remove() {
            return this.queue.remove();
        }
    }

    static class CheckedRandomAccessList<E>
    extends CheckedList<E>
    implements RandomAccess {
        private static final long serialVersionUID = 1638200125423088369L;

        CheckedRandomAccessList(List<E> list, Class<E> class_) {
            super(list, class_);
        }

        @Override
        public List<E> subList(int n, int n2) {
            return new CheckedRandomAccessList(this.list.subList(n, n2), this.type);
        }
    }

    static class CheckedSet<E>
    extends CheckedCollection<E>
    implements Set<E>,
    Serializable {
        private static final long serialVersionUID = 4694047833775013803L;

        CheckedSet(Set<E> set, Class<E> class_) {
            super(set, class_);
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || this.c.equals(object);
            return bl;
        }

        @Override
        public int hashCode() {
            return this.c.hashCode();
        }
    }

    static class CheckedSortedMap<K, V>
    extends CheckedMap<K, V>
    implements SortedMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 1599671320688067438L;
        private final SortedMap<K, V> sm;

        CheckedSortedMap(SortedMap<K, V> sortedMap, Class<K> class_, Class<V> class_2) {
            super(sortedMap, class_, class_2);
            this.sm = sortedMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sm.comparator();
        }

        @Override
        public K firstKey() {
            return this.sm.firstKey();
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return Collections.checkedSortedMap(this.sm.headMap(k), this.keyType, this.valueType);
        }

        @Override
        public K lastKey() {
            return this.sm.lastKey();
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return Collections.checkedSortedMap(this.sm.subMap(k, k2), this.keyType, this.valueType);
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return Collections.checkedSortedMap(this.sm.tailMap(k), this.keyType, this.valueType);
        }
    }

    static class CheckedSortedSet<E>
    extends CheckedSet<E>
    implements SortedSet<E>,
    Serializable {
        private static final long serialVersionUID = 1599911165492914959L;
        private final SortedSet<E> ss;

        CheckedSortedSet(SortedSet<E> sortedSet, Class<E> class_) {
            super(sortedSet, class_);
            this.ss = sortedSet;
        }

        @Override
        public Comparator<? super E> comparator() {
            return this.ss.comparator();
        }

        @Override
        public E first() {
            return this.ss.first();
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return Collections.checkedSortedSet(this.ss.headSet(e), this.type);
        }

        @Override
        public E last() {
            return this.ss.last();
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return Collections.checkedSortedSet(this.ss.subSet(e, e2), this.type);
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return Collections.checkedSortedSet(this.ss.tailSet(e), this.type);
        }
    }

    private static class CopiesList<E>
    extends AbstractList<E>
    implements RandomAccess,
    Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = 2739099268398711800L;
        final E element;
        final int n;

        CopiesList(int n, E e) {
            this.n = n;
            this.element = e;
        }

        @Override
        public boolean contains(Object object) {
            boolean bl = this.n != 0 && Collections.eq(object, this.element);
            return bl;
        }

        @Override
        public E get(int n) {
            if (n >= 0 && n < this.n) {
                return this.element;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            stringBuilder.append(", Size: ");
            stringBuilder.append(this.n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        @Override
        public int indexOf(Object object) {
            int n = this.contains(object) ? 0 : -1;
            return n;
        }

        public /* synthetic */ Object lambda$parallelStream$1$Collections$CopiesList(int n) {
            return this.element;
        }

        public /* synthetic */ Object lambda$stream$0$Collections$CopiesList(int n) {
            return this.element;
        }

        @Override
        public int lastIndexOf(Object object) {
            int n = this.contains(object) ? this.n - 1 : -1;
            return n;
        }

        @Override
        public Stream<E> parallelStream() {
            return IntStream.range(0, this.n).parallel().mapToObj(new _$$Lambda$Collections$CopiesList$JkPuGMNhrKbnEHjebm8AvHc2xHw(this));
        }

        @Override
        public int size() {
            return this.n;
        }

        @Override
        public Spliterator<E> spliterator() {
            return this.stream().spliterator();
        }

        @Override
        public Stream<E> stream() {
            return IntStream.range(0, this.n).mapToObj(new _$$Lambda$Collections$CopiesList$uHL7LkfEBowvpXOMQGFkZqUGxm4(this));
        }

        @Override
        public List<E> subList(int n, int n2) {
            if (n >= 0) {
                if (n2 <= this.n) {
                    if (n <= n2) {
                        return new CopiesList<E>(n2 - n, this.element);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("fromIndex(");
                    stringBuilder.append(n);
                    stringBuilder.append(") > toIndex(");
                    stringBuilder.append(n2);
                    stringBuilder.append(")");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("toIndex = ");
                stringBuilder.append(n2);
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fromIndex = ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        @Override
        public Object[] toArray() {
            int n = this.n;
            Object[] arrobject = new Object[n];
            E e = this.element;
            if (e != null) {
                Arrays.fill(arrobject, 0, n, e);
            }
            return arrobject;
        }

        @Override
        public <T> T[] toArray(T[] arrobject) {
            Object[] arrobject2;
            int n = this.n;
            if (arrobject.length < n) {
                arrobject = (Object[])Array.newInstance(arrobject.getClass().getComponentType(), n);
                E e = this.element;
                arrobject2 = arrobject;
                if (e != null) {
                    Arrays.fill(arrobject, 0, n, e);
                    arrobject2 = arrobject;
                }
            } else {
                Arrays.fill(arrobject, 0, n, this.element);
                arrobject2 = arrobject;
                if (arrobject.length > n) {
                    arrobject[n] = null;
                    arrobject2 = arrobject;
                }
            }
            return arrobject2;
        }
    }

    private static class EmptyEnumeration<E>
    implements Enumeration<E> {
        static final EmptyEnumeration<Object> EMPTY_ENUMERATION = new EmptyEnumeration<E>();

        private EmptyEnumeration() {
        }

        @Override
        public boolean hasMoreElements() {
            return false;
        }

        @Override
        public E nextElement() {
            throw new NoSuchElementException();
        }
    }

    private static class EmptyIterator<E>
    implements Iterator<E> {
        static final EmptyIterator<Object> EMPTY_ITERATOR = new EmptyIterator<E>();

        private EmptyIterator() {
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new IllegalStateException();
        }
    }

    private static class EmptyList<E>
    extends AbstractList<E>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 8842843931221139166L;

        private EmptyList() {
        }

        private Object readResolve() {
            return EMPTY_LIST;
        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof List && ((List)object).isEmpty();
            return bl;
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
        }

        @Override
        public E get(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<E> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public ListIterator<E> listIterator() {
            return Collections.emptyListIterator();
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }

        @Override
        public void replaceAll(UnaryOperator<E> unaryOperator) {
            Objects.requireNonNull(unaryOperator);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void sort(Comparator<? super E> comparator) {
        }

        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.emptySpliterator();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            if (arrT.length > 0) {
                arrT[0] = null;
            }
            return arrT;
        }
    }

    private static class EmptyListIterator<E>
    extends EmptyIterator<E>
    implements ListIterator<E> {
        static final EmptyListIterator<Object> EMPTY_ITERATOR = new EmptyListIterator<E>();

        private EmptyListIterator() {
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public E previous() {
            throw new NoSuchElementException();
        }

        @Override
        public int previousIndex() {
            return -1;
        }

        @Override
        public void set(E e) {
            throw new IllegalStateException();
        }
    }

    private static class EmptyMap<K, V>
    extends AbstractMap<K, V>
    implements Serializable {
        private static final long serialVersionUID = 6428348081105594320L;

        private EmptyMap() {
        }

        private Object readResolve() {
            return EMPTY_MAP;
        }

        @Override
        public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(K k, Function<? super K, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object object) {
            return false;
        }

        @Override
        public boolean containsValue(Object object) {
            return false;
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return Collections.emptySet();
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof Map && ((Map)object).isEmpty();
            return bl;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
            Objects.requireNonNull(biConsumer);
        }

        @Override
        public V get(Object object) {
            return null;
        }

        @Override
        public V getOrDefault(Object object, V v) {
            return v;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Set<K> keySet() {
            return Collections.emptySet();
        }

        @Override
        public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Objects.requireNonNull(biFunction);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Collection<V> values() {
            return Collections.emptySet();
        }
    }

    private static class EmptySet<E>
    extends AbstractSet<E>
    implements Serializable {
        private static final long serialVersionUID = 1582296315990362920L;

        private EmptySet() {
        }

        private Object readResolve() {
            return EMPTY_SET;
        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<E> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.emptySpliterator();
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            if (arrT.length > 0) {
                arrT[0] = null;
            }
            return arrT;
        }
    }

    private static class ReverseComparator
    implements Comparator<Comparable<Object>>,
    Serializable {
        static final ReverseComparator REVERSE_ORDER = new ReverseComparator();
        private static final long serialVersionUID = 7207038068494060240L;

        private ReverseComparator() {
        }

        private Object readResolve() {
            return Collections.reverseOrder();
        }

        @Override
        public int compare(Comparable<Object> comparable, Comparable<Object> comparable2) {
            return comparable2.compareTo(comparable);
        }

        @Override
        public Comparator<Comparable<Object>> reversed() {
            return Comparator.naturalOrder();
        }
    }

    private static class ReverseComparator2<T>
    implements Comparator<T>,
    Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = 4374092139857L;
        final Comparator<T> cmp;

        ReverseComparator2(Comparator<T> comparator) {
            this.cmp = comparator;
        }

        @Override
        public int compare(T t, T t2) {
            return this.cmp.compare(t2, t);
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || object instanceof ReverseComparator2 && this.cmp.equals(((ReverseComparator2)object).cmp);
            return bl;
        }

        public int hashCode() {
            return this.cmp.hashCode() ^ Integer.MIN_VALUE;
        }

        @Override
        public Comparator<T> reversed() {
            return this.cmp;
        }
    }

    private static class SetFromMap<E>
    extends AbstractSet<E>
    implements Set<E>,
    Serializable {
        private static final long serialVersionUID = 2454657854757543876L;
        private final Map<E, Boolean> m;
        private transient Set<E> s;

        SetFromMap(Map<E, Boolean> map) {
            if (map.isEmpty()) {
                this.m = map;
                this.s = map.keySet();
                return;
            }
            throw new IllegalArgumentException("Map is non-empty");
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.s = this.m.keySet();
        }

        @Override
        public boolean add(E e) {
            boolean bl = this.m.put(e, Boolean.TRUE) == null;
            return bl;
        }

        @Override
        public void clear() {
            this.m.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.m.containsKey(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.s.containsAll(collection);
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || this.s.equals(object);
            return bl;
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            this.s.forEach(consumer);
        }

        @Override
        public int hashCode() {
            return this.s.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            return this.s.iterator();
        }

        @Override
        public Stream<E> parallelStream() {
            return this.s.parallelStream();
        }

        @Override
        public boolean remove(Object object) {
            boolean bl = this.m.remove(object) != null;
            return bl;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.s.removeAll(collection);
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            return this.s.removeIf(predicate);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.s.retainAll(collection);
        }

        @Override
        public int size() {
            return this.m.size();
        }

        @Override
        public Spliterator<E> spliterator() {
            return this.s.spliterator();
        }

        @Override
        public Stream<E> stream() {
            return this.s.stream();
        }

        @Override
        public Object[] toArray() {
            return this.s.toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.s.toArray(arrT);
        }

        @Override
        public String toString() {
            return this.s.toString();
        }
    }

    private static class SingletonList<E>
    extends AbstractList<E>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 3093736618740652951L;
        private final E element;

        SingletonList(E e) {
            this.element = e;
        }

        @Override
        public boolean contains(Object object) {
            return Collections.eq(object, this.element);
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            consumer.accept(this.element);
        }

        @Override
        public E get(int n) {
            if (n == 0) {
                return this.element;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            stringBuilder.append(", Size: 1");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        @Override
        public Iterator<E> iterator() {
            return Collections.singletonIterator(this.element);
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(UnaryOperator<E> unaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public void sort(Comparator<? super E> comparator) {
        }

        @Override
        public Spliterator<E> spliterator() {
            return Collections.singletonSpliterator(this.element);
        }
    }

    private static class SingletonMap<K, V>
    extends AbstractMap<K, V>
    implements Serializable {
        private static final long serialVersionUID = -6979724477215052911L;
        private transient Set<Map.Entry<K, V>> entrySet;
        private final K k;
        private transient Set<K> keySet;
        private final V v;
        private transient Collection<V> values;

        SingletonMap(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(K k, Function<? super K, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object object) {
            return Collections.eq(object, this.k);
        }

        @Override
        public boolean containsValue(Object object) {
            return Collections.eq(object, this.v);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = Collections.singleton(new AbstractMap.SimpleImmutableEntry<K, V>(this.k, this.v));
            }
            return this.entrySet;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
            biConsumer.accept(this.k, this.v);
        }

        @Override
        public V get(Object object) {
            object = Collections.eq(object, this.k) ? this.v : null;
            return (V)object;
        }

        @Override
        public V getOrDefault(Object object, V v) {
            block0 : {
                if (!Collections.eq(object, this.k)) break block0;
                v = this.v;
            }
            return v;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Set<K> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.singleton(this.k);
            }
            return this.keySet;
        }

        @Override
        public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public Collection<V> values() {
            if (this.values == null) {
                this.values = Collections.singleton(this.v);
            }
            return this.values;
        }
    }

    private static class SingletonSet<E>
    extends AbstractSet<E>
    implements Serializable {
        private static final long serialVersionUID = 3193687207550431679L;
        private final E element;

        SingletonSet(E e) {
            this.element = e;
        }

        @Override
        public boolean contains(Object object) {
            return Collections.eq(object, this.element);
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            consumer.accept(this.element);
        }

        @Override
        public Iterator<E> iterator() {
            return Collections.singletonIterator(this.element);
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public Spliterator<E> spliterator() {
            return Collections.singletonSpliterator(this.element);
        }
    }

    static class SynchronizedCollection<E>
    implements Collection<E>,
    Serializable {
        private static final long serialVersionUID = 3053995032091335093L;
        final Collection<E> c;
        final Object mutex;

        SynchronizedCollection(Collection<E> collection) {
            this.c = Objects.requireNonNull(collection);
            this.mutex = this;
        }

        SynchronizedCollection(Collection<E> collection, Object object) {
            this.c = Objects.requireNonNull(collection);
            this.mutex = Objects.requireNonNull(object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.mutex;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean add(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.add(e);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean addAll(Collection<? extends E> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.addAll(collection);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.c.clear();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean contains(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.c.contains(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean containsAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.containsAll(collection);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void forEach(Consumer<? super E> consumer) {
            Object object = this.mutex;
            synchronized (object) {
                this.c.forEach(consumer);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.isEmpty();
            }
        }

        @Override
        public Iterator<E> iterator() {
            return this.c.iterator();
        }

        @Override
        public Stream<E> parallelStream() {
            return this.c.parallelStream();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean remove(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.c.remove(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean removeAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.removeAll(collection);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.removeIf(predicate);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean retainAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.retainAll(collection);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.size();
            }
        }

        @Override
        public Spliterator<E> spliterator() {
            return this.c.spliterator();
        }

        @Override
        public Stream<E> stream() {
            return this.c.stream();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Object[] toArray() {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.toArray();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public <T> T[] toArray(T[] arrT) {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.toArray(arrT);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String toString() {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.toString();
            }
        }
    }

    static class SynchronizedList<E>
    extends SynchronizedCollection<E>
    implements List<E> {
        private static final long serialVersionUID = -7754090372962971524L;
        final List<E> list;

        SynchronizedList(List<E> list) {
            super(list);
            this.list = list;
        }

        SynchronizedList(List<E> list, Object object) {
            super(list, object);
            this.list = list;
        }

        private Object readResolve() {
            SynchronizedRandomAccessList synchronizedRandomAccessList = this.list;
            synchronizedRandomAccessList = synchronizedRandomAccessList instanceof RandomAccess ? new SynchronizedRandomAccessList(synchronizedRandomAccessList) : this;
            return synchronizedRandomAccessList;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void add(int n, E e) {
            Object object = this.mutex;
            synchronized (object) {
                this.list.add(n, e);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean addAll(int n, Collection<? extends E> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.list.addAll(n, collection);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.list.equals(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E get(int n) {
            Object object = this.mutex;
            synchronized (object) {
                E e = this.list.get(n);
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.list.hashCode();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int indexOf(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.list.indexOf(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int lastIndexOf(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.list.lastIndexOf(object);
            }
        }

        @Override
        public ListIterator<E> listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ListIterator<E> listIterator(int n) {
            return this.list.listIterator(n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E remove(int n) {
            Object object = this.mutex;
            synchronized (object) {
                E e = this.list.remove(n);
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void replaceAll(UnaryOperator<E> unaryOperator) {
            Object object = this.mutex;
            synchronized (object) {
                this.list.replaceAll(unaryOperator);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E set(int n, E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.list.set(n, e);
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void sort(Comparator<? super E> comparator) {
            Object object = this.mutex;
            synchronized (object) {
                this.list.sort(comparator);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public List<E> subList(int n, int n2) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedList<E>(this.list.subList(n, n2), this.mutex);
            }
        }
    }

    private static class SynchronizedMap<K, V>
    implements Map<K, V>,
    Serializable {
        private static final long serialVersionUID = 1978198479659022715L;
        private transient Set<Map.Entry<K, V>> entrySet;
        private transient Set<K> keySet;
        private final Map<K, V> m;
        final Object mutex;
        private transient Collection<V> values;

        SynchronizedMap(Map<K, V> map) {
            this.m = Objects.requireNonNull(map);
            this.mutex = this;
        }

        SynchronizedMap(Map<K, V> map, Object object) {
            this.m = map;
            this.mutex = object;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.mutex;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.m.clear();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V compute(K object, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.compute((K)object, (BiFunction<? super K, ? extends V, ? extends V>)biFunction);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V computeIfAbsent(K object, Function<? super K, ? extends V> function) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.computeIfAbsent((K)object, function);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V computeIfPresent(K object, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.computeIfPresent((K)object, (BiFunction<? super K, ? extends V, ? extends V>)biFunction);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean containsKey(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.m.containsKey(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean containsValue(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.m.containsValue(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entrySet != null) return this.entrySet;
                Set<Map.Entry<K, V>> set = new Set<Map.Entry<K, V>>(this.m.entrySet(), this.mutex);
                this.entrySet = set;
                return this.entrySet;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.m.equals(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
            Object object = this.mutex;
            synchronized (object) {
                this.m.forEach(biConsumer);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V get(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.get(object);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V getOrDefault(Object object, V v) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.getOrDefault(object, v);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.m.hashCode();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.m.isEmpty();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Set<K> keySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.keySet != null) return this.keySet;
                Set<K> set = new Set<K>(this.m.keySet(), this.mutex);
                this.keySet = set;
                return this.keySet;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V merge(K object, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.merge(object, (V)v, (BiFunction<? extends V, ? extends V, ? extends V>)biFunction);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V put(K object, V v) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.put(object, v);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            Object object = this.mutex;
            synchronized (object) {
                this.m.putAll(map);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V putIfAbsent(K object, V v) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.putIfAbsent(object, v);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V remove(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.remove(object);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean remove(Object object, Object object2) {
            Object object3 = this.mutex;
            synchronized (object3) {
                return this.m.remove(object, object2);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V replace(K object, V v) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.m.replace(object, v);
                return (V)object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean replace(K k, V v, V v2) {
            Object object = this.mutex;
            synchronized (object) {
                return this.m.replace(k, v, v2);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
            Object object = this.mutex;
            synchronized (object) {
                this.m.replaceAll(biFunction);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.m.size();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String toString() {
            Object object = this.mutex;
            synchronized (object) {
                return this.m.toString();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Collection<V> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.values != null) return this.values;
                Collection<V> collection = new Collection<V>(this.m.values(), this.mutex);
                this.values = collection;
                return this.values;
            }
        }
    }

    static class SynchronizedNavigableMap<K, V>
    extends SynchronizedSortedMap<K, V>
    implements NavigableMap<K, V> {
        private static final long serialVersionUID = 699392247599746807L;
        private final NavigableMap<K, V> nm;

        SynchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
            super(navigableMap);
            this.nm = navigableMap;
        }

        SynchronizedNavigableMap(NavigableMap<K, V> navigableMap, Object object) {
            super(navigableMap, object);
            this.nm = navigableMap;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map.Entry<K, V> ceilingEntry(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.nm.ceilingEntry(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public K ceilingKey(K k) {
            Object object = this.mutex;
            synchronized (object) {
                k = this.nm.ceilingKey(k);
                return k;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<K> descendingKeySet() {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<K>(this.nm.descendingKeySet(), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableMap<K, V> descendingMap() {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableMap<K, V>(this.nm.descendingMap(), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map.Entry<K, V> firstEntry() {
            Object object = this.mutex;
            synchronized (object) {
                return this.nm.firstEntry();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map.Entry<K, V> floorEntry(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.nm.floorEntry(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public K floorKey(K k) {
            Object object = this.mutex;
            synchronized (object) {
                k = this.nm.floorKey(k);
                return k;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableMap<K, V>(this.nm.headMap(k, bl), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedMap<K, V> headMap(K k) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableMap<K, V>(this.nm.headMap(k, false), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map.Entry<K, V> higherEntry(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.nm.higherEntry(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public K higherKey(K k) {
            Object object = this.mutex;
            synchronized (object) {
                k = this.nm.higherKey(k);
                return k;
            }
        }

        @Override
        public NavigableSet<K> keySet() {
            return this.navigableKeySet();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map.Entry<K, V> lastEntry() {
            Object object = this.mutex;
            synchronized (object) {
                return this.nm.lastEntry();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map.Entry<K, V> lowerEntry(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.nm.lowerEntry(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public K lowerKey(K k) {
            Object object = this.mutex;
            synchronized (object) {
                k = this.nm.lowerKey(k);
                return k;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<K> navigableKeySet() {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<K>(this.nm.navigableKeySet(), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            Object object = this.mutex;
            synchronized (object) {
                return this.nm.pollFirstEntry();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Map.Entry<K, V> pollLastEntry() {
            Object object = this.mutex;
            synchronized (object) {
                return this.nm.pollLastEntry();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableMap<K, V>(this.nm.subMap(k, bl, k2, bl2), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableMap<K, V>(this.nm.subMap(k, true, k2, false), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableMap<K, V>(this.nm.tailMap(k, bl), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedMap<K, V> tailMap(K k) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableMap<K, V>(this.nm.tailMap(k, true), this.mutex);
            }
        }
    }

    static class SynchronizedNavigableSet<E>
    extends SynchronizedSortedSet<E>
    implements NavigableSet<E> {
        private static final long serialVersionUID = -5505529816273629798L;
        private final NavigableSet<E> ns;

        SynchronizedNavigableSet(NavigableSet<E> navigableSet) {
            super(navigableSet);
            this.ns = navigableSet;
        }

        SynchronizedNavigableSet(NavigableSet<E> navigableSet, Object object) {
            super(navigableSet, object);
            this.ns = navigableSet;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E ceiling(E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.ns.ceiling(e);
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Iterator<E> descendingIterator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.descendingSet().iterator();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<E> descendingSet() {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<E>(this.ns.descendingSet(), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E floor(E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.ns.floor(e);
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<E> headSet(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<E>(this.ns.headSet(e, false), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<E>(this.ns.headSet(e, bl), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E higher(E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.ns.higher(e);
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E lower(E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.ns.lower(e);
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E pollFirst() {
            Object object = this.mutex;
            synchronized (object) {
                E e = this.ns.pollFirst();
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E pollLast() {
            Object object = this.mutex;
            synchronized (object) {
                E e = this.ns.pollLast();
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<E> subSet(E e, E e2) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<E>(this.ns.subSet(e, true, e2, false), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<E>(this.ns.subSet(e, bl, e2, bl2), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<E> tailSet(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<E>(this.ns.tailSet(e, true), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedNavigableSet<E>(this.ns.tailSet(e, bl), this.mutex);
            }
        }
    }

    static class SynchronizedRandomAccessList<E>
    extends SynchronizedList<E>
    implements RandomAccess {
        private static final long serialVersionUID = 1530674583602358482L;

        SynchronizedRandomAccessList(List<E> list) {
            super(list);
        }

        SynchronizedRandomAccessList(List<E> list, Object object) {
            super(list, object);
        }

        private Object writeReplace() {
            return new SynchronizedList(this.list);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public List<E> subList(int n, int n2) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedRandomAccessList(this.list.subList(n, n2), this.mutex);
            }
        }
    }

    static class SynchronizedSet<E>
    extends SynchronizedCollection<E>
    implements Set<E> {
        private static final long serialVersionUID = 487447009682186044L;

        SynchronizedSet(Set<E> set) {
            super(set);
        }

        SynchronizedSet(Set<E> set, Object object) {
            super(set, object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.c.equals(object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.c.hashCode();
            }
        }
    }

    static class SynchronizedSortedMap<K, V>
    extends SynchronizedMap<K, V>
    implements SortedMap<K, V> {
        private static final long serialVersionUID = -8798146769416483793L;
        private final SortedMap<K, V> sm;

        SynchronizedSortedMap(SortedMap<K, V> sortedMap) {
            super(sortedMap);
            this.sm = sortedMap;
        }

        SynchronizedSortedMap(SortedMap<K, V> sortedMap, Object object) {
            super(sortedMap, object);
            this.sm = sortedMap;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Comparator<? super K> comparator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.sm.comparator();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public K firstKey() {
            Object object = this.mutex;
            synchronized (object) {
                K k = this.sm.firstKey();
                return k;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedMap<K, V> headMap(K k) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedSortedMap<K, V>(this.sm.headMap(k), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public K lastKey() {
            Object object = this.mutex;
            synchronized (object) {
                K k = this.sm.lastKey();
                return k;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedSortedMap<K, V>(this.sm.subMap(k, k2), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedMap<K, V> tailMap(K k) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedSortedMap<K, V>(this.sm.tailMap(k), this.mutex);
            }
        }
    }

    static class SynchronizedSortedSet<E>
    extends SynchronizedSet<E>
    implements SortedSet<E> {
        private static final long serialVersionUID = 8695801310862127406L;
        private final SortedSet<E> ss;

        SynchronizedSortedSet(SortedSet<E> sortedSet) {
            super(sortedSet);
            this.ss = sortedSet;
        }

        SynchronizedSortedSet(SortedSet<E> sortedSet, Object object) {
            super(sortedSet, object);
            this.ss = sortedSet;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Comparator<? super E> comparator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.ss.comparator();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E first() {
            Object object = this.mutex;
            synchronized (object) {
                E e = this.ss.first();
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedSet<E> headSet(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedSortedSet<E>(this.ss.headSet(e), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E last() {
            Object object = this.mutex;
            synchronized (object) {
                E e = this.ss.last();
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedSet<E> subSet(E e, E e2) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedSortedSet<E>(this.ss.subSet(e, e2), this.mutex);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SortedSet<E> tailSet(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return new SynchronizedSortedSet<E>(this.ss.tailSet(e), this.mutex);
            }
        }
    }

    static class UnmodifiableCollection<E>
    implements Collection<E>,
    Serializable {
        private static final long serialVersionUID = 1820017752578914078L;
        final Collection<? extends E> c;

        UnmodifiableCollection(Collection<? extends E> collection) {
            if (collection != null) {
                this.c = collection;
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object object) {
            return this.c.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.c.containsAll(collection);
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            this.c.forEach(consumer);
        }

        @Override
        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>(){
                private final Iterator<? extends E> i;
                {
                    this.i = c.iterator();
                }

                @Override
                public void forEachRemaining(Consumer<? super E> consumer) {
                    this.i.forEachRemaining(consumer);
                }

                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override
                public E next() {
                    return this.i.next();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public Stream<E> parallelStream() {
            return this.c.parallelStream();
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeIf(Predicate<? super E> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return this.c.size();
        }

        @Override
        public Spliterator<E> spliterator() {
            return this.c.spliterator();
        }

        @Override
        public Stream<E> stream() {
            return this.c.stream();
        }

        @Override
        public Object[] toArray() {
            return this.c.toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.c.toArray(arrT);
        }

        public String toString() {
            return this.c.toString();
        }

    }

    static class UnmodifiableList<E>
    extends UnmodifiableCollection<E>
    implements List<E> {
        private static final long serialVersionUID = -283967356065247728L;
        final List<? extends E> list;

        UnmodifiableList(List<? extends E> list) {
            super(list);
            this.list = list;
        }

        private Object readResolve() {
            UnmodifiableRandomAccessList unmodifiableRandomAccessList = this.list;
            unmodifiableRandomAccessList = unmodifiableRandomAccessList instanceof RandomAccess ? new UnmodifiableRandomAccessList(unmodifiableRandomAccessList) : this;
            return unmodifiableRandomAccessList;
        }

        @Override
        public void add(int n, E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || this.list.equals(object);
            return bl;
        }

        @Override
        public E get(int n) {
            return this.list.get(n);
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public int indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        public int lastIndexOf(Object object) {
            return this.list.lastIndexOf(object);
        }

        @Override
        public ListIterator<E> listIterator() {
            return this.listIterator(0);
        }

        @Override
        public ListIterator<E> listIterator(final int n) {
            return new ListIterator<E>(){
                private final ListIterator<? extends E> i;
                {
                    this.i = list.listIterator(n);
                }

                @Override
                public void add(E e) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void forEachRemaining(Consumer<? super E> consumer) {
                    this.i.forEachRemaining(consumer);
                }

                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override
                public boolean hasPrevious() {
                    return this.i.hasPrevious();
                }

                @Override
                public E next() {
                    return this.i.next();
                }

                @Override
                public int nextIndex() {
                    return this.i.nextIndex();
                }

                @Override
                public E previous() {
                    return this.i.previous();
                }

                @Override
                public int previousIndex() {
                    return this.i.previousIndex();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void set(E e) {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public E remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(UnaryOperator<E> unaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public E set(int n, E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void sort(Comparator<? super E> comparator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<E> subList(int n, int n2) {
            return new UnmodifiableList<E>(this.list.subList(n, n2));
        }

    }

    private static class UnmodifiableMap<K, V>
    implements Map<K, V>,
    Serializable {
        private static final long serialVersionUID = -1034234728574286014L;
        private transient Set<Map.Entry<K, V>> entrySet;
        private transient Set<K> keySet;
        private final Map<? extends K, ? extends V> m;
        private transient Collection<V> values;

        UnmodifiableMap(Map<? extends K, ? extends V> map) {
            if (map != null) {
                this.m = map;
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(K k, Function<? super K, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.m.containsKey(object);
        }

        @Override
        public boolean containsValue(Object object) {
            return this.m.containsValue(object);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = new UnmodifiableEntrySet<K, V>(this.m.entrySet());
            }
            return this.entrySet;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || this.m.equals(object);
            return bl;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
            this.m.forEach(biConsumer);
        }

        @Override
        public V get(Object object) {
            return this.m.get(object);
        }

        @Override
        public V getOrDefault(Object object, V v) {
            return this.m.getOrDefault(object, v);
        }

        @Override
        public int hashCode() {
            return this.m.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public Set<K> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.m.keySet());
            }
            return this.keySet;
        }

        @Override
        public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return this.m.size();
        }

        public String toString() {
            return this.m.toString();
        }

        @Override
        public Collection<V> values() {
            if (this.values == null) {
                this.values = Collections.unmodifiableCollection(this.m.values());
            }
            return this.values;
        }

        static class UnmodifiableEntrySet<K, V>
        extends UnmodifiableSet<Map.Entry<K, V>> {
            private static final long serialVersionUID = 7854390611657943733L;

            UnmodifiableEntrySet(Set<? extends Map.Entry<? extends K, ? extends V>> set) {
                super(set);
            }

            static <K, V> Consumer<Map.Entry<K, V>> entryConsumer(Consumer<? super Map.Entry<K, V>> consumer) {
                return new _$$Lambda$Collections$UnmodifiableMap$UnmodifiableEntrySet$W5VhpDb0JlKqrRuOSf_2RiCnSgo(consumer);
            }

            static /* synthetic */ void lambda$entryConsumer$0(Consumer consumer, Map.Entry entry) {
                consumer.accept(new UnmodifiableEntry(entry));
            }

            @Override
            public boolean contains(Object object) {
                if (!(object instanceof Map.Entry)) {
                    return false;
                }
                return this.c.contains(new UnmodifiableEntry((Map.Entry)object));
            }

            @Override
            public boolean containsAll(Collection<?> object) {
                object = object.iterator();
                while (object.hasNext()) {
                    if (this.contains(object.next())) continue;
                    return false;
                }
                return true;
            }

            @Override
            public boolean equals(Object object) {
                if (object == this) {
                    return true;
                }
                if (!(object instanceof Set)) {
                    return false;
                }
                if ((object = (Set)object).size() != this.c.size()) {
                    return false;
                }
                return this.containsAll((Collection<?>)object);
            }

            @Override
            public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
                Objects.requireNonNull(consumer);
                this.c.forEach(UnmodifiableEntrySet.entryConsumer(consumer));
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new Iterator<Map.Entry<K, V>>(){
                    private final Iterator<? extends Map.Entry<? extends K, ? extends V>> i;
                    {
                        this.i = c.iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public Map.Entry<K, V> next() {
                        return new UnmodifiableEntry<K, V>(this.i.next());
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @Override
            public Stream<Map.Entry<K, V>> parallelStream() {
                return StreamSupport.stream(this.spliterator(), true);
            }

            @Override
            public Spliterator<Map.Entry<K, V>> spliterator() {
                return new UnmodifiableEntrySetSpliterator(this.c.spliterator());
            }

            @Override
            public Stream<Map.Entry<K, V>> stream() {
                return StreamSupport.stream(this.spliterator(), false);
            }

            @Override
            public Object[] toArray() {
                Object[] arrobject = this.c.toArray();
                for (int i = 0; i < arrobject.length; ++i) {
                    arrobject[i] = new UnmodifiableEntry((Map.Entry)arrobject[i]);
                }
                return arrobject;
            }

            @Override
            public <T> T[] toArray(T[] arrT) {
                Collection collection = this.c;
                T[] arrT2 = arrT.length == 0 ? arrT : Arrays.copyOf(arrT, 0);
                arrT2 = collection.toArray(arrT2);
                for (int i = 0; i < arrT2.length; ++i) {
                    arrT2[i] = new UnmodifiableEntry((Map.Entry)arrT2[i]);
                }
                if (arrT2.length > arrT.length) {
                    return arrT2;
                }
                System.arraycopy(arrT2, 0, arrT, 0, arrT2.length);
                if (arrT.length > arrT2.length) {
                    arrT[arrT2.length] = null;
                }
                return arrT;
            }

            private static class UnmodifiableEntry<K, V>
            implements Map.Entry<K, V> {
                private Map.Entry<? extends K, ? extends V> e;

                UnmodifiableEntry(Map.Entry<? extends K, ? extends V> entry) {
                    this.e = Objects.requireNonNull(entry);
                }

                @Override
                public boolean equals(Object object) {
                    boolean bl = true;
                    if (this == object) {
                        return true;
                    }
                    if (!(object instanceof Map.Entry)) {
                        return false;
                    }
                    object = (Map.Entry)object;
                    if (!Collections.eq(this.e.getKey(), object.getKey()) || !Collections.eq(this.e.getValue(), object.getValue())) {
                        bl = false;
                    }
                    return bl;
                }

                @Override
                public K getKey() {
                    return this.e.getKey();
                }

                @Override
                public V getValue() {
                    return this.e.getValue();
                }

                @Override
                public int hashCode() {
                    return this.e.hashCode();
                }

                @Override
                public V setValue(V v) {
                    throw new UnsupportedOperationException();
                }

                public String toString() {
                    return this.e.toString();
                }
            }

            static final class UnmodifiableEntrySetSpliterator<K, V>
            implements Spliterator<Map.Entry<K, V>> {
                final Spliterator<Map.Entry<K, V>> s;

                UnmodifiableEntrySetSpliterator(Spliterator<Map.Entry<K, V>> spliterator) {
                    this.s = spliterator;
                }

                @Override
                public int characteristics() {
                    return this.s.characteristics();
                }

                @Override
                public long estimateSize() {
                    return this.s.estimateSize();
                }

                @Override
                public void forEachRemaining(Consumer<? super Map.Entry<K, V>> consumer) {
                    Objects.requireNonNull(consumer);
                    this.s.forEachRemaining(UnmodifiableEntrySet.entryConsumer(consumer));
                }

                @Override
                public Comparator<? super Map.Entry<K, V>> getComparator() {
                    return this.s.getComparator();
                }

                @Override
                public long getExactSizeIfKnown() {
                    return this.s.getExactSizeIfKnown();
                }

                @Override
                public boolean hasCharacteristics(int n) {
                    return this.s.hasCharacteristics(n);
                }

                @Override
                public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
                    Objects.requireNonNull(consumer);
                    return this.s.tryAdvance(UnmodifiableEntrySet.entryConsumer(consumer));
                }

                @Override
                public Spliterator<Map.Entry<K, V>> trySplit() {
                    Spliterator<Map.Entry<K, V>> spliterator = this.s.trySplit();
                    spliterator = spliterator == null ? null : new UnmodifiableEntrySetSpliterator<K, V>(spliterator);
                    return spliterator;
                }
            }

        }

    }

    static class UnmodifiableNavigableMap<K, V>
    extends UnmodifiableSortedMap<K, V>
    implements NavigableMap<K, V>,
    Serializable {
        private static final EmptyNavigableMap<?, ?> EMPTY_NAVIGABLE_MAP = new EmptyNavigableMap();
        private static final long serialVersionUID = -4858195264774772197L;
        private final NavigableMap<K, ? extends V> nm;

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
            super(navigableMap);
            this.nm = navigableMap;
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K object) {
            object = (object = this.nm.ceilingEntry(object)) != null ? new UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry(object) : null;
            return object;
        }

        @Override
        public K ceilingKey(K k) {
            return this.nm.ceilingKey(k);
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return Collections.unmodifiableNavigableSet(this.nm.descendingKeySet());
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return Collections.unmodifiableNavigableMap(this.nm.descendingMap());
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            Map.Entry<K, ? extends V> entry = this.nm.firstEntry();
            entry = entry != null ? new UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<K, V>(entry) : null;
            return entry;
        }

        @Override
        public Map.Entry<K, V> floorEntry(K object) {
            object = (object = this.nm.floorEntry(object)) != null ? new UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry(object) : null;
            return object;
        }

        @Override
        public K floorKey(K k) {
            return this.nm.floorKey(k);
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return Collections.unmodifiableNavigableMap(this.nm.headMap(k, bl));
        }

        @Override
        public Map.Entry<K, V> higherEntry(K object) {
            object = (object = this.nm.higherEntry(object)) != null ? new UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry(object) : null;
            return object;
        }

        @Override
        public K higherKey(K k) {
            return this.nm.higherKey(k);
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            Map.Entry<K, ? extends V> entry = this.nm.lastEntry();
            entry = entry != null ? new UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<K, V>(entry) : null;
            return entry;
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K object) {
            object = (object = this.nm.lowerEntry(object)) != null ? new UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry(object) : null;
            return object;
        }

        @Override
        public K lowerKey(K k) {
            return this.nm.lowerKey(k);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return Collections.unmodifiableNavigableSet(this.nm.navigableKeySet());
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            throw new UnsupportedOperationException();
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Collections.unmodifiableNavigableMap(this.nm.subMap(k, bl, k2, bl2));
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return Collections.unmodifiableNavigableMap(this.nm.tailMap(k, bl));
        }

        private static class EmptyNavigableMap<K, V>
        extends UnmodifiableNavigableMap<K, V>
        implements Serializable {
            private static final long serialVersionUID = -2239321462712562324L;

            EmptyNavigableMap() {
                super(new TreeMap());
            }

            private Object readResolve() {
                return EMPTY_NAVIGABLE_MAP;
            }

            @Override
            public NavigableSet<K> navigableKeySet() {
                return Collections.emptyNavigableSet();
            }
        }

    }

    static class UnmodifiableNavigableSet<E>
    extends UnmodifiableSortedSet<E>
    implements NavigableSet<E>,
    Serializable {
        private static final NavigableSet<?> EMPTY_NAVIGABLE_SET = new EmptyNavigableSet();
        private static final long serialVersionUID = -6027448201786391929L;
        private final NavigableSet<E> ns;

        UnmodifiableNavigableSet(NavigableSet<E> navigableSet) {
            super(navigableSet);
            this.ns = navigableSet;
        }

        @Override
        public E ceiling(E e) {
            return this.ns.ceiling(e);
        }

        @Override
        public Iterator<E> descendingIterator() {
            return this.descendingSet().iterator();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return new UnmodifiableNavigableSet<E>(this.ns.descendingSet());
        }

        @Override
        public E floor(E e) {
            return this.ns.floor(e);
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return new UnmodifiableNavigableSet<E>(this.ns.headSet(e, bl));
        }

        @Override
        public E higher(E e) {
            return this.ns.higher(e);
        }

        @Override
        public E lower(E e) {
            return this.ns.lower(e);
        }

        @Override
        public E pollFirst() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E pollLast() {
            throw new UnsupportedOperationException();
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return new UnmodifiableNavigableSet<E>(this.ns.subSet(e, bl, e2, bl2));
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return new UnmodifiableNavigableSet<E>(this.ns.tailSet(e, bl));
        }

        private static class EmptyNavigableSet<E>
        extends UnmodifiableNavigableSet<E>
        implements Serializable {
            private static final long serialVersionUID = -6291252904449939134L;

            public EmptyNavigableSet() {
                super(new TreeSet());
            }

            private Object readResolve() {
                return EMPTY_NAVIGABLE_SET;
            }
        }

    }

    static class UnmodifiableRandomAccessList<E>
    extends UnmodifiableList<E>
    implements RandomAccess {
        private static final long serialVersionUID = -2542308836966382001L;

        UnmodifiableRandomAccessList(List<? extends E> list) {
            super(list);
        }

        private Object writeReplace() {
            return new UnmodifiableList(this.list);
        }

        @Override
        public List<E> subList(int n, int n2) {
            return new UnmodifiableRandomAccessList(this.list.subList(n, n2));
        }
    }

    static class UnmodifiableSet<E>
    extends UnmodifiableCollection<E>
    implements Set<E>,
    Serializable {
        private static final long serialVersionUID = -9215047833775013803L;

        UnmodifiableSet(Set<? extends E> set) {
            super(set);
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object == this || this.c.equals(object);
            return bl;
        }

        @Override
        public int hashCode() {
            return this.c.hashCode();
        }
    }

    static class UnmodifiableSortedMap<K, V>
    extends UnmodifiableMap<K, V>
    implements SortedMap<K, V>,
    Serializable {
        private static final long serialVersionUID = -8806743815996713206L;
        private final SortedMap<K, ? extends V> sm;

        UnmodifiableSortedMap(SortedMap<K, ? extends V> sortedMap) {
            super(sortedMap);
            this.sm = sortedMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sm.comparator();
        }

        @Override
        public K firstKey() {
            return this.sm.firstKey();
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return new UnmodifiableSortedMap<K, V>(this.sm.headMap(k));
        }

        @Override
        public K lastKey() {
            return this.sm.lastKey();
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return new UnmodifiableSortedMap<K, V>(this.sm.subMap(k, k2));
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return new UnmodifiableSortedMap<K, V>(this.sm.tailMap(k));
        }
    }

    static class UnmodifiableSortedSet<E>
    extends UnmodifiableSet<E>
    implements SortedSet<E>,
    Serializable {
        private static final long serialVersionUID = -4929149591599911165L;
        private final SortedSet<E> ss;

        UnmodifiableSortedSet(SortedSet<E> sortedSet) {
            super(sortedSet);
            this.ss = sortedSet;
        }

        @Override
        public Comparator<? super E> comparator() {
            return this.ss.comparator();
        }

        @Override
        public E first() {
            return this.ss.first();
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return new UnmodifiableSortedSet<E>(this.ss.headSet(e));
        }

        @Override
        public E last() {
            return this.ss.last();
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return new UnmodifiableSortedSet<E>(this.ss.subSet(e, e2));
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return new UnmodifiableSortedSet<E>(this.ss.tailSet(e));
        }
    }

}

