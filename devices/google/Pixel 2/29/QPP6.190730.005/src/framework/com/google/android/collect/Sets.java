/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.collect;

import android.annotation.UnsupportedAppUsage;
import android.util.ArraySet;
import java.util.Collections;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class Sets {
    @UnsupportedAppUsage
    public static <E> ArraySet<E> newArraySet() {
        return new ArraySet();
    }

    @UnsupportedAppUsage
    public static <E> ArraySet<E> newArraySet(E ... arrE) {
        ArraySet arraySet = new ArraySet(arrE.length * 4 / 3 + 1);
        Collections.addAll(arraySet, arrE);
        return arraySet;
    }

    @UnsupportedAppUsage
    public static <K> HashSet<K> newHashSet() {
        return new HashSet();
    }

    @UnsupportedAppUsage
    public static <E> HashSet<E> newHashSet(E ... arrE) {
        HashSet hashSet = new HashSet(arrE.length * 4 / 3 + 1);
        Collections.addAll(hashSet, arrE);
        return hashSet;
    }

    @UnsupportedAppUsage
    public static <E> SortedSet<E> newSortedSet() {
        return new TreeSet();
    }

    public static <E> SortedSet<E> newSortedSet(E ... arrE) {
        TreeSet treeSet = new TreeSet();
        Collections.addAll(treeSet, arrE);
        return treeSet;
    }
}

