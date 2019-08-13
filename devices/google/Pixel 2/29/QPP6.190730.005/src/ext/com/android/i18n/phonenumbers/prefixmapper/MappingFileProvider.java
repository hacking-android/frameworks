/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.prefixmapper;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;

public class MappingFileProvider
implements Externalizable {
    private static final Map<String, String> LOCALE_NORMALIZATION_MAP;
    private List<Set<String>> availableLanguages;
    private int[] countryCallingCodes;
    private int numOfEntries = 0;

    static {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("zh_TW", "zh_Hant");
        hashMap.put("zh_HK", "zh_Hant");
        hashMap.put("zh_MO", "zh_Hant");
        LOCALE_NORMALIZATION_MAP = Collections.unmodifiableMap(hashMap);
    }

    private void appendSubsequentLocalePart(String string, StringBuilder stringBuilder) {
        if (string.length() > 0) {
            stringBuilder.append('_');
            stringBuilder.append(string);
        }
    }

    private StringBuilder constructFullLocale(String charSequence, String string, String string2) {
        charSequence = new StringBuilder((String)charSequence);
        this.appendSubsequentLocalePart(string, (StringBuilder)charSequence);
        this.appendSubsequentLocalePart(string2, (StringBuilder)charSequence);
        return charSequence;
    }

    private String findBestMatchingLanguageCode(Set<String> set, String string, String charSequence, String string2) {
        String string3 = this.constructFullLocale(string, (String)charSequence, string2).toString();
        CharSequence charSequence2 = LOCALE_NORMALIZATION_MAP.get(string3);
        if (charSequence2 != null && set.contains(charSequence2)) {
            return charSequence2;
        }
        if (set.contains(string3)) {
            return string3;
        }
        if (this.onlyOneOfScriptOrRegionIsEmpty((String)charSequence, string2)) {
            if (set.contains(string)) {
                return string;
            }
        } else if (((String)charSequence).length() > 0 && string2.length() > 0) {
            charSequence2 = new StringBuilder(string);
            ((StringBuilder)charSequence2).append('_');
            charSequence = ((StringBuilder)charSequence2).append((String)charSequence).toString();
            if (set.contains(charSequence)) {
                return charSequence;
            }
            charSequence = new StringBuilder(string);
            ((StringBuilder)charSequence).append('_');
            charSequence = ((StringBuilder)charSequence).append(string2).toString();
            if (set.contains(charSequence)) {
                return charSequence;
            }
            if (set.contains(string)) {
                return string;
            }
        }
        return "";
    }

    private boolean onlyOneOfScriptOrRegionIsEmpty(String string, String string2) {
        boolean bl = string.length() == 0 && string2.length() > 0 || string2.length() == 0 && string.length() > 0;
        return bl;
    }

    String getFileName(int n, String string, String charSequence, String string2) {
        if (string.length() == 0) {
            return "";
        }
        int n2 = Arrays.binarySearch(this.countryCallingCodes, n);
        if (n2 < 0) {
            return "";
        }
        Set<String> set = this.availableLanguages.get(n2);
        if (set.size() > 0 && (string = this.findBestMatchingLanguageCode(set, string, (String)charSequence, string2)).length() > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append('_');
            ((StringBuilder)charSequence).append(string);
            return ((StringBuilder)charSequence).toString();
        }
        return "";
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        this.numOfEntries = objectInput.readInt();
        Object object = this.countryCallingCodes;
        if (object == null || ((int[])object).length < this.numOfEntries) {
            this.countryCallingCodes = new int[this.numOfEntries];
        }
        if (this.availableLanguages == null) {
            this.availableLanguages = new ArrayList<Set<String>>();
        }
        for (int i = 0; i < this.numOfEntries; ++i) {
            this.countryCallingCodes[i] = objectInput.readInt();
            int n = objectInput.readInt();
            object = new HashSet();
            for (int j = 0; j < n; ++j) {
                object.add(objectInput.readUTF());
            }
            this.availableLanguages.add((Set<String>)object);
        }
    }

    public void readFileConfigs(SortedMap<Integer, Set<String>> sortedMap) {
        int n = this.numOfEntries = sortedMap.size();
        this.countryCallingCodes = new int[n];
        this.availableLanguages = new ArrayList<Set<String>>(n);
        n = 0;
        Iterator<Integer> iterator = sortedMap.keySet().iterator();
        while (iterator.hasNext()) {
            int n2;
            this.countryCallingCodes[n] = n2 = iterator.next().intValue();
            this.availableLanguages.add(new HashSet((Collection)sortedMap.get(n2)));
            ++n;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.numOfEntries; ++i) {
            stringBuilder.append(this.countryCallingCodes[i]);
            stringBuilder.append('|');
            Iterator iterator = new TreeSet(this.availableLanguages.get(i)).iterator();
            while (iterator.hasNext()) {
                stringBuilder.append((String)iterator.next());
                stringBuilder.append(',');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeInt(this.numOfEntries);
        for (int i = 0; i < this.numOfEntries; ++i) {
            objectOutput.writeInt(this.countryCallingCodes[i]);
            Object object = this.availableLanguages.get(i);
            objectOutput.writeInt(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                objectOutput.writeUTF((String)object.next());
            }
        }
    }
}

