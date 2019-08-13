/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.prefixmapper;

import com.android.i18n.phonenumbers.prefixmapper.PhonePrefixMapStorageStrategy;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;

class DefaultMapStorage
extends PhonePrefixMapStorageStrategy {
    private String[] descriptions;
    private int[] phoneNumberPrefixes;

    @Override
    public String getDescription(int n) {
        return this.descriptions[n];
    }

    @Override
    public int getPrefix(int n) {
        return this.phoneNumberPrefixes[n];
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        int n;
        this.numOfEntries = objectInput.readInt();
        Object[] arrobject = this.phoneNumberPrefixes;
        if (arrobject == null || arrobject.length < this.numOfEntries) {
            this.phoneNumberPrefixes = new int[this.numOfEntries];
        }
        if ((arrobject = this.descriptions) == null || arrobject.length < this.numOfEntries) {
            this.descriptions = new String[this.numOfEntries];
        }
        for (n = 0; n < this.numOfEntries; ++n) {
            this.phoneNumberPrefixes[n] = objectInput.readInt();
            this.descriptions[n] = objectInput.readUTF();
        }
        int n2 = objectInput.readInt();
        this.possibleLengths.clear();
        for (n = 0; n < n2; ++n) {
            this.possibleLengths.add(objectInput.readInt());
        }
    }

    @Override
    public void readFromSortedMap(SortedMap<Integer, String> sortedMap) {
        this.numOfEntries = sortedMap.size();
        this.phoneNumberPrefixes = new int[this.numOfEntries];
        this.descriptions = new String[this.numOfEntries];
        int n = 0;
        Iterator<Integer> iterator = sortedMap.keySet().iterator();
        while (iterator.hasNext()) {
            int n2;
            this.phoneNumberPrefixes[n] = n2 = iterator.next().intValue();
            this.possibleLengths.add((int)Math.log10(n2) + 1);
            ++n;
        }
        sortedMap.values().toArray(this.descriptions);
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeInt(this.numOfEntries);
        for (int i = 0; i < this.numOfEntries; ++i) {
            objectOutput.writeInt(this.phoneNumberPrefixes[i]);
            objectOutput.writeUTF(this.descriptions[i]);
        }
        objectOutput.writeInt(this.possibleLengths.size());
        Iterator iterator = this.possibleLengths.iterator();
        while (iterator.hasNext()) {
            objectOutput.writeInt((Integer)iterator.next());
        }
    }
}

