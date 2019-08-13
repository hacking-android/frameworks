/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.prefixmapper;

import com.android.i18n.phonenumbers.prefixmapper.PhonePrefixMapStorageStrategy;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

final class FlyweightMapStorage
extends PhonePrefixMapStorageStrategy {
    private static final int INT_NUM_BYTES = 4;
    private static final int SHORT_NUM_BYTES = 2;
    private int descIndexSizeInBytes;
    private ByteBuffer descriptionIndexes;
    private String[] descriptionPool;
    private ByteBuffer phoneNumberPrefixes;
    private int prefixSizeInBytes;

    FlyweightMapStorage() {
    }

    private void createDescriptionPool(SortedSet<String> object, SortedMap<Integer, String> sortedMap) {
        this.descIndexSizeInBytes = FlyweightMapStorage.getOptimalNumberOfBytesForValue(object.size() - 1);
        this.descriptionIndexes = ByteBuffer.allocate(this.numOfEntries * this.descIndexSizeInBytes);
        this.descriptionPool = new String[object.size()];
        object.toArray(this.descriptionPool);
        int n = 0;
        for (int i = 0; i < this.numOfEntries; ++i) {
            object = (String)sortedMap.get(FlyweightMapStorage.readWordFromBuffer(this.phoneNumberPrefixes, this.prefixSizeInBytes, i));
            int n2 = Arrays.binarySearch(this.descriptionPool, object);
            FlyweightMapStorage.storeWordInBuffer(this.descriptionIndexes, this.descIndexSizeInBytes, n, n2);
            ++n;
        }
    }

    private static int getOptimalNumberOfBytesForValue(int n) {
        n = n <= 32767 ? 2 : 4;
        return n;
    }

    private void readEntries(ObjectInput objectInput) throws IOException {
        this.numOfEntries = objectInput.readInt();
        ByteBuffer byteBuffer = this.phoneNumberPrefixes;
        if (byteBuffer == null || byteBuffer.capacity() < this.numOfEntries) {
            this.phoneNumberPrefixes = ByteBuffer.allocate(this.numOfEntries * this.prefixSizeInBytes);
        }
        if ((byteBuffer = this.descriptionIndexes) == null || byteBuffer.capacity() < this.numOfEntries) {
            this.descriptionIndexes = ByteBuffer.allocate(this.numOfEntries * this.descIndexSizeInBytes);
        }
        for (int i = 0; i < this.numOfEntries; ++i) {
            FlyweightMapStorage.readExternalWord(objectInput, this.prefixSizeInBytes, this.phoneNumberPrefixes, i);
            FlyweightMapStorage.readExternalWord(objectInput, this.descIndexSizeInBytes, this.descriptionIndexes, i);
        }
    }

    private static void readExternalWord(ObjectInput objectInput, int n, ByteBuffer byteBuffer, int n2) throws IOException {
        n2 *= n;
        if (n == 2) {
            byteBuffer.putShort(n2, objectInput.readShort());
        } else {
            byteBuffer.putInt(n2, objectInput.readInt());
        }
    }

    private static int readWordFromBuffer(ByteBuffer byteBuffer, int n, int n2) {
        n2 *= n;
        n = n == 2 ? (int)byteBuffer.getShort(n2) : byteBuffer.getInt(n2);
        return n;
    }

    private static void storeWordInBuffer(ByteBuffer byteBuffer, int n, int n2, int n3) {
        n2 *= n;
        if (n == 2) {
            byteBuffer.putShort(n2, (short)n3);
        } else {
            byteBuffer.putInt(n2, n3);
        }
    }

    private static void writeExternalWord(ObjectOutput objectOutput, int n, ByteBuffer byteBuffer, int n2) throws IOException {
        n2 *= n;
        if (n == 2) {
            objectOutput.writeShort(byteBuffer.getShort(n2));
        } else {
            objectOutput.writeInt(byteBuffer.getInt(n2));
        }
    }

    @Override
    public String getDescription(int n) {
        n = FlyweightMapStorage.readWordFromBuffer(this.descriptionIndexes, this.descIndexSizeInBytes, n);
        return this.descriptionPool[n];
    }

    @Override
    public int getPrefix(int n) {
        return FlyweightMapStorage.readWordFromBuffer(this.phoneNumberPrefixes, this.prefixSizeInBytes, n);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        int n;
        this.prefixSizeInBytes = objectInput.readInt();
        this.descIndexSizeInBytes = objectInput.readInt();
        int n2 = objectInput.readInt();
        this.possibleLengths.clear();
        for (n = 0; n < n2; ++n) {
            this.possibleLengths.add(objectInput.readInt());
        }
        n2 = objectInput.readInt();
        Object object = this.descriptionPool;
        if (object == null || ((String[])object).length < n2) {
            this.descriptionPool = new String[n2];
        }
        for (n = 0; n < n2; ++n) {
            this.descriptionPool[n] = object = objectInput.readUTF();
        }
        this.readEntries(objectInput);
    }

    @Override
    public void readFromSortedMap(SortedMap<Integer, String> sortedMap) {
        TreeSet<String> treeSet = new TreeSet<String>();
        this.numOfEntries = sortedMap.size();
        this.prefixSizeInBytes = FlyweightMapStorage.getOptimalNumberOfBytesForValue(sortedMap.lastKey());
        this.phoneNumberPrefixes = ByteBuffer.allocate(this.numOfEntries * this.prefixSizeInBytes);
        int n = 0;
        for (Map.Entry<Integer, String> entry : sortedMap.entrySet()) {
            int n2 = entry.getKey();
            FlyweightMapStorage.storeWordInBuffer(this.phoneNumberPrefixes, this.prefixSizeInBytes, n, n2);
            this.possibleLengths.add((int)Math.log10(n2) + 1);
            treeSet.add(entry.getValue());
            ++n;
        }
        this.createDescriptionPool(treeSet, sortedMap);
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        int n;
        objectOutput.writeInt(this.prefixSizeInBytes);
        objectOutput.writeInt(this.descIndexSizeInBytes);
        objectOutput.writeInt(this.possibleLengths.size());
        String[] arrstring = this.possibleLengths.iterator();
        while (arrstring.hasNext()) {
            objectOutput.writeInt((Integer)arrstring.next());
        }
        objectOutput.writeInt(this.descriptionPool.length);
        arrstring = this.descriptionPool;
        int n2 = arrstring.length;
        for (n = 0; n < n2; ++n) {
            objectOutput.writeUTF(arrstring[n]);
        }
        objectOutput.writeInt(this.numOfEntries);
        for (n = 0; n < this.numOfEntries; ++n) {
            FlyweightMapStorage.writeExternalWord(objectOutput, this.prefixSizeInBytes, this.phoneNumberPrefixes, n);
            FlyweightMapStorage.writeExternalWord(objectOutput, this.descIndexSizeInBytes, this.descriptionIndexes, n);
        }
    }
}

