/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.prefixmapper;

import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.prefixmapper.PhonePrefixMap;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.StringTokenizer;

public class PrefixTimeZonesMap
implements Externalizable {
    private static final String RAW_STRING_TIMEZONES_SEPARATOR = "&";
    private final PhonePrefixMap phonePrefixMap = new PhonePrefixMap();

    private List<String> lookupTimeZonesForNumber(long l) {
        String string = this.phonePrefixMap.lookup(l);
        if (string == null) {
            return new LinkedList<String>();
        }
        return this.tokenizeRawOutputString(string);
    }

    private List<String> tokenizeRawOutputString(String object) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)object, RAW_STRING_TIMEZONES_SEPARATOR);
        object = new LinkedList();
        while (stringTokenizer.hasMoreTokens()) {
            ((LinkedList)object).add(stringTokenizer.nextToken());
        }
        return object;
    }

    public List<String> lookupCountryLevelTimeZonesForNumber(Phonenumber.PhoneNumber phoneNumber) {
        return this.lookupTimeZonesForNumber(phoneNumber.getCountryCode());
    }

    public List<String> lookupTimeZonesForNumber(Phonenumber.PhoneNumber phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(phoneNumber.getCountryCode());
        stringBuilder.append(PhoneNumberUtil.getInstance().getNationalSignificantNumber(phoneNumber));
        return this.lookupTimeZonesForNumber(Long.parseLong(stringBuilder.toString()));
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        this.phonePrefixMap.readExternal(objectInput);
    }

    public void readPrefixTimeZonesMap(SortedMap<Integer, String> sortedMap) {
        this.phonePrefixMap.readPhonePrefixMap(sortedMap);
    }

    public String toString() {
        return this.phonePrefixMap.toString();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        this.phonePrefixMap.writeExternal(objectOutput);
    }
}

