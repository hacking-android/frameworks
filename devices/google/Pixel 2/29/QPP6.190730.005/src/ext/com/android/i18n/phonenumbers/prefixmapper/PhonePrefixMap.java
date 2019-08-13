/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.prefixmapper;

import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.prefixmapper.DefaultMapStorage;
import com.android.i18n.phonenumbers.prefixmapper.FlyweightMapStorage;
import com.android.i18n.phonenumbers.prefixmapper.PhonePrefixMapStorageStrategy;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

public class PhonePrefixMap
implements Externalizable {
    private static final Logger logger = Logger.getLogger(PhonePrefixMap.class.getName());
    private PhonePrefixMapStorageStrategy phonePrefixMapStorage;
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    private int binarySearch(int n, int n2, long l) {
        int n3 = 0;
        int n4 = n2;
        n2 = n;
        n = n3;
        while (n2 <= n4) {
            n = n2 + n4 >>> 1;
            n3 = this.phonePrefixMapStorage.getPrefix(n);
            if ((long)n3 == l) {
                return n;
            }
            if ((long)n3 > l) {
                n4 = --n;
                continue;
            }
            n2 = n + 1;
        }
        return n;
    }

    private PhonePrefixMapStorageStrategy createDefaultMapStorage() {
        return new DefaultMapStorage();
    }

    private PhonePrefixMapStorageStrategy createFlyweightMapStorage() {
        return new FlyweightMapStorage();
    }

    private static int getSizeOfPhonePrefixMapStorage(PhonePrefixMapStorageStrategy phonePrefixMapStorageStrategy, SortedMap<Integer, String> object) throws IOException {
        phonePrefixMapStorageStrategy.readFromSortedMap((SortedMap<Integer, String>)object);
        object = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream((OutputStream)object);
        phonePrefixMapStorageStrategy.writeExternal(objectOutputStream);
        objectOutputStream.flush();
        int n = ((ByteArrayOutputStream)object).size();
        objectOutputStream.close();
        return n;
    }

    PhonePrefixMapStorageStrategy getPhonePrefixMapStorage() {
        return this.phonePrefixMapStorage;
    }

    PhonePrefixMapStorageStrategy getSmallerMapStorage(SortedMap<Integer, String> object) {
        try {
            PhonePrefixMapStorageStrategy phonePrefixMapStorageStrategy = this.createFlyweightMapStorage();
            int n = PhonePrefixMap.getSizeOfPhonePrefixMapStorage(phonePrefixMapStorageStrategy, object);
            PhonePrefixMapStorageStrategy phonePrefixMapStorageStrategy2 = this.createDefaultMapStorage();
            int n2 = PhonePrefixMap.getSizeOfPhonePrefixMapStorage(phonePrefixMapStorageStrategy2, object);
            object = n < n2 ? phonePrefixMapStorageStrategy : phonePrefixMapStorageStrategy2;
            return object;
        }
        catch (IOException iOException) {
            logger.severe(iOException.getMessage());
            return this.createFlyweightMapStorage();
        }
    }

    String lookup(long l) {
        int n = this.phonePrefixMapStorage.getNumOfEntries();
        if (n == 0) {
            return null;
        }
        --n;
        SortedSet<Integer> sortedSet = this.phonePrefixMapStorage.getPossibleLengths();
        while (sortedSet.size() > 0) {
            Integer n2 = (Integer)sortedSet.last();
            String string = String.valueOf(l);
            if (string.length() > n2) {
                l = Long.parseLong(string.substring(0, n2));
            }
            if ((n = this.binarySearch(0, n, l)) < 0) {
                return null;
            }
            if (l == (long)this.phonePrefixMapStorage.getPrefix(n)) {
                return this.phonePrefixMapStorage.getDescription(n);
            }
            sortedSet = sortedSet.headSet(n2);
        }
        return null;
    }

    public String lookup(Phonenumber.PhoneNumber phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(phoneNumber.getCountryCode());
        stringBuilder.append(this.phoneUtil.getNationalSignificantNumber(phoneNumber));
        return this.lookup(Long.parseLong(stringBuilder.toString()));
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        this.phonePrefixMapStorage = objectInput.readBoolean() ? new FlyweightMapStorage() : new DefaultMapStorage();
        this.phonePrefixMapStorage.readExternal(objectInput);
    }

    public void readPhonePrefixMap(SortedMap<Integer, String> sortedMap) {
        this.phonePrefixMapStorage = this.getSmallerMapStorage(sortedMap);
    }

    public String toString() {
        return this.phonePrefixMapStorage.toString();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeBoolean(this.phonePrefixMapStorage instanceof FlyweightMapStorage);
        this.phonePrefixMapStorage.writeExternal(objectOutput);
    }
}

