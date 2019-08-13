/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.prefixmapper;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.SortedMap;
import java.util.TreeSet;

abstract class PhonePrefixMapStorageStrategy {
    protected int numOfEntries = 0;
    protected final TreeSet<Integer> possibleLengths = new TreeSet();

    PhonePrefixMapStorageStrategy() {
    }

    public abstract String getDescription(int var1);

    public int getNumOfEntries() {
        return this.numOfEntries;
    }

    public TreeSet<Integer> getPossibleLengths() {
        return this.possibleLengths;
    }

    public abstract int getPrefix(int var1);

    public abstract void readExternal(ObjectInput var1) throws IOException;

    public abstract void readFromSortedMap(SortedMap<Integer, String> var1);

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.getNumOfEntries();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(this.getPrefix(i));
            stringBuilder.append("|");
            stringBuilder.append(this.getDescription(i));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public abstract void writeExternal(ObjectOutput var1) throws IOException;
}

