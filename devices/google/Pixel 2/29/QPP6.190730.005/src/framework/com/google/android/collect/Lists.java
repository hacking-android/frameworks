/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.collect;

import android.annotation.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.Collections;

public class Lists {
    @UnsupportedAppUsage
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    @UnsupportedAppUsage
    public static <E> ArrayList<E> newArrayList(E ... arrE) {
        ArrayList arrayList = new ArrayList(arrE.length * 110 / 100 + 5);
        Collections.addAll(arrayList, arrE);
        return arrayList;
    }
}

