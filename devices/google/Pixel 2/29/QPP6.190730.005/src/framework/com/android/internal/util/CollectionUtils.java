/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.util.ArraySet;
import android.util.ExceptionUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> List<T> add(List<T> list, T t) {
        List<T> list2;
        block3 : {
            block2 : {
                if (list == null) break block2;
                list2 = list;
                if (list != Collections.emptyList()) break block3;
            }
            list2 = new ArrayList<T>();
        }
        list2.add(t);
        return list2;
    }

    public static <T> Set<T> add(Set<T> set, T t) {
        Set<T> set2;
        block3 : {
            block2 : {
                if (set == null) break block2;
                set2 = set;
                if (set != Collections.emptySet()) break block3;
            }
            set2 = new ArraySet<T>();
        }
        set2.add(t);
        return set2;
    }

    public static <T> void addIf(List<T> list, Collection<? super T> collection, Predicate<? super T> predicate) {
        for (int i = 0; i < CollectionUtils.size(list); ++i) {
            T t = list.get(i);
            if (!predicate.test(t)) continue;
            collection.add(t);
        }
    }

    public static <T> boolean any(List<T> list, Predicate<T> predicate) {
        boolean bl = CollectionUtils.find(list, predicate) != null;
        return bl;
    }

    public static <T> List<T> copyOf(List<T> list) {
        list = CollectionUtils.isEmpty(list) ? Collections.emptyList() : new ArrayList<T>(list);
        return list;
    }

    public static <T> Set<T> copyOf(Set<T> set) {
        set = CollectionUtils.isEmpty(set) ? Collections.emptySet() : new ArraySet<T>(set);
        return set;
    }

    public static <T> List<T> emptyIfNull(List<T> list) {
        block0 : {
            if (list != null) break block0;
            list = Collections.emptyList();
        }
        return list;
    }

    public static <T> Set<T> emptyIfNull(Set<T> set) {
        block0 : {
            if (set != null) break block0;
            set = Collections.emptySet();
        }
        return set;
    }

    public static <T> List<T> filter(List<?> list, Class<T> class_) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        ArrayList<?> arrayList = null;
        for (int i = 0; i < list.size(); ++i) {
            Object obj = list.get(i);
            ArrayList<?> arrayList2 = arrayList;
            if (class_.isInstance(obj)) {
                arrayList2 = ArrayUtils.add(arrayList, obj);
            }
            arrayList = arrayList2;
        }
        return CollectionUtils.emptyIfNull(arrayList);
    }

    public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        ArrayList<T> arrayList = null;
        for (int i = 0; i < CollectionUtils.size(list); ++i) {
            T t = list.get(i);
            ArrayList<T> arrayList2 = arrayList;
            if (predicate.test(t)) {
                arrayList2 = ArrayUtils.add(arrayList, t);
            }
            arrayList = arrayList2;
        }
        return CollectionUtils.emptyIfNull(arrayList);
    }

    public static <T> Set<T> filter(Set<T> arraySet, Predicate<? super T> predicate) {
        if (arraySet != null && arraySet.size() != 0) {
            ArraySet<Object> arraySet2 = null;
            ArraySet<Object> arraySet3 = null;
            if (arraySet instanceof ArraySet) {
                arraySet2 = arraySet;
                int n = arraySet2.size();
                arraySet = arraySet3;
                for (int i = 0; i < n; ++i) {
                    Object object = arraySet2.valueAt(i);
                    arraySet3 = arraySet;
                    if (predicate.test(object)) {
                        arraySet3 = ArrayUtils.add(arraySet, object);
                    }
                    arraySet = arraySet3;
                }
                arraySet3 = arraySet;
            } else {
                Iterator<T> iterator = arraySet.iterator();
                arraySet = arraySet2;
                do {
                    arraySet3 = arraySet;
                    if (!iterator.hasNext()) break;
                    arraySet2 = iterator.next();
                    arraySet3 = arraySet;
                    if (predicate.test(arraySet2)) {
                        arraySet3 = ArrayUtils.add(arraySet, arraySet2);
                    }
                    arraySet = arraySet3;
                } while (true);
            }
            return CollectionUtils.emptyIfNull(arraySet3);
        }
        return Collections.emptySet();
    }

    public static <T> T find(List<T> list, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        for (int i = 0; i < list.size(); ++i) {
            T t = list.get(i);
            if (!predicate.test(t)) continue;
            return t;
        }
        return null;
    }

    public static <T> T firstOrNull(Collection<T> collection) {
        collection = CollectionUtils.isEmpty(collection) ? null : collection.iterator().next();
        return (T)collection;
    }

    public static <T> T firstOrNull(List<T> list) {
        list = CollectionUtils.isEmpty(list) ? null : list.get(0);
        return (T)list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> void forEach(Set<T> iterator, FunctionalUtils.ThrowingConsumer<T> throwingConsumer) {
        block7 : {
            if (iterator == null || throwingConsumer == null) return;
            int n = iterator.size();
            if (n == 0) {
                return;
            }
            if (!(iterator instanceof ArraySet)) break block7;
            iterator = (ArraySet)((Object)iterator);
            for (int i = 0; i < n; ++i) {
                throwingConsumer.acceptOrThrow(((ArraySet)((Object)iterator)).valueAt(i));
            }
            return;
        }
        try {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                throwingConsumer.acceptOrThrow(iterator.next());
            }
            return;
        }
        catch (Exception exception) {
            throw ExceptionUtils.propagate(exception);
        }
    }

    public static boolean isEmpty(Collection<?> collection) {
        boolean bl = CollectionUtils.size(collection) == 0;
        return bl;
    }

    public static <I, O> List<O> map(List<I> list, Function<? super I, ? extends O> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        ArrayList<O> arrayList = new ArrayList<O>();
        for (int i = 0; i < list.size(); ++i) {
            arrayList.add(function.apply(list.get(i)));
        }
        return arrayList;
    }

    public static <I, O> Set<O> map(Set<I> object, Function<? super I, ? extends O> function) {
        if (CollectionUtils.isEmpty(object)) {
            return Collections.emptySet();
        }
        ArraySet<O> arraySet = new ArraySet<O>();
        if (object instanceof ArraySet) {
            object = (ArraySet)object;
            int n = ((ArraySet)object).size();
            for (int i = 0; i < n; ++i) {
                arraySet.add(function.apply(((ArraySet)object).valueAt(i)));
            }
        } else {
            object = object.iterator();
            while (object.hasNext()) {
                arraySet.add(function.apply(object.next()));
            }
        }
        return arraySet;
    }

    public static <I, O> List<O> mapNotNull(List<I> list, Function<? super I, ? extends O> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<O> list2 = null;
        for (int i = 0; i < list.size(); ++i) {
            O o = function.apply(list.get(i));
            List<O> list3 = list2;
            if (o != null) {
                list3 = CollectionUtils.add(list2, o);
            }
            list2 = list3;
        }
        return CollectionUtils.emptyIfNull(list2);
    }

    public static <T> List<T> remove(List<T> list, T t) {
        if (CollectionUtils.isEmpty(list)) {
            return CollectionUtils.emptyIfNull(list);
        }
        list.remove(t);
        return list;
    }

    public static <T> Set<T> remove(Set<T> set, T t) {
        if (CollectionUtils.isEmpty(set)) {
            return CollectionUtils.emptyIfNull(set);
        }
        set.remove(t);
        return set;
    }

    public static <T> List<T> singletonOrEmpty(T object) {
        object = object == null ? Collections.emptyList() : Collections.singletonList(object);
        return object;
    }

    public static int size(Collection<?> collection) {
        int n = collection != null ? collection.size() : 0;
        return n;
    }

    public static int size(Map<?, ?> map) {
        int n = map != null ? map.size() : 0;
        return n;
    }
}

