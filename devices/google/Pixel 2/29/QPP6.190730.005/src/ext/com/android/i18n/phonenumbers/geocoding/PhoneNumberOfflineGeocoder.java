/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.i18n.phonenumbers.geocoding;

import com.android.i18n.phonenumbers.NumberParseException;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.prefixmapper.PrefixFileReader;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.List;
import java.util.Locale;

public class PhoneNumberOfflineGeocoder {
    private static final String MAPPING_DATA_DIRECTORY = "/com/android/i18n/phonenumbers/geocoding/data/";
    private static PhoneNumberOfflineGeocoder instance = null;
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private PrefixFileReader prefixFileReader = null;

    PhoneNumberOfflineGeocoder(String string) {
        this.prefixFileReader = new PrefixFileReader(string);
    }

    private String getCountryNameForNumber(Phonenumber.PhoneNumber phoneNumber, Locale locale) {
        List<String> list = this.phoneUtil.getRegionCodesForCountryCode(phoneNumber.getCountryCode());
        if (list.size() == 1) {
            return this.getRegionDisplayName(list.get(0), locale);
        }
        Object object = "ZZ";
        for (String string : list) {
            list = object;
            if (this.phoneUtil.isValidNumberForRegion(phoneNumber, string)) {
                if (!((String)object).equals("ZZ")) {
                    return "";
                }
                list = string;
            }
            object = list;
        }
        return this.getRegionDisplayName((String)object, locale);
    }

    @UnsupportedAppUsage
    public static PhoneNumberOfflineGeocoder getInstance() {
        synchronized (PhoneNumberOfflineGeocoder.class) {
            PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder;
            if (instance == null) {
                instance = phoneNumberOfflineGeocoder = new PhoneNumberOfflineGeocoder(MAPPING_DATA_DIRECTORY);
            }
            phoneNumberOfflineGeocoder = instance;
            return phoneNumberOfflineGeocoder;
        }
    }

    private String getRegionDisplayName(String string, Locale locale) {
        String string2 = "";
        string = string != null && !string.equals("ZZ") && !string.equals("001") ? new Locale("", string).getDisplayCountry(locale) : string2;
        return string;
    }

    @UnsupportedAppUsage
    public String getDescriptionForNumber(Phonenumber.PhoneNumber phoneNumber, Locale locale) {
        PhoneNumberUtil.PhoneNumberType phoneNumberType = this.phoneUtil.getNumberType(phoneNumber);
        if (phoneNumberType == PhoneNumberUtil.PhoneNumberType.UNKNOWN) {
            return "";
        }
        if (!this.phoneUtil.isNumberGeographical(phoneNumberType, phoneNumber.getCountryCode())) {
            return this.getCountryNameForNumber(phoneNumber, locale);
        }
        return this.getDescriptionForValidNumber(phoneNumber, locale);
    }

    public String getDescriptionForNumber(Phonenumber.PhoneNumber phoneNumber, Locale locale, String string) {
        PhoneNumberUtil.PhoneNumberType phoneNumberType = this.phoneUtil.getNumberType(phoneNumber);
        if (phoneNumberType == PhoneNumberUtil.PhoneNumberType.UNKNOWN) {
            return "";
        }
        if (!this.phoneUtil.isNumberGeographical(phoneNumberType, phoneNumber.getCountryCode())) {
            return this.getCountryNameForNumber(phoneNumber, locale);
        }
        return this.getDescriptionForValidNumber(phoneNumber, locale, string);
    }

    public String getDescriptionForValidNumber(Phonenumber.PhoneNumber object, Locale locale) {
        String string = locale.getLanguage();
        String string2 = locale.getCountry();
        String string3 = PhoneNumberUtil.getCountryMobileToken(((Phonenumber.PhoneNumber)object).getCountryCode());
        Object object2 = this.phoneUtil.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
        if (!string3.equals("") && ((String)object2).startsWith(string3)) {
            string3 = ((String)object2).substring(string3.length());
            object2 = this.phoneUtil.getRegionCodeForCountryCode(((Phonenumber.PhoneNumber)object).getCountryCode());
            try {
                object2 = this.phoneUtil.parse(string3, (String)object2);
            }
            catch (NumberParseException numberParseException) {
                object2 = object;
            }
            object2 = this.prefixFileReader.getDescriptionForNumber((Phonenumber.PhoneNumber)object2, string, "", string2);
        } else {
            object2 = this.prefixFileReader.getDescriptionForNumber((Phonenumber.PhoneNumber)object, string, "", string2);
        }
        object = ((String)object2).length() > 0 ? object2 : this.getCountryNameForNumber((Phonenumber.PhoneNumber)object, locale);
        return object;
    }

    public String getDescriptionForValidNumber(Phonenumber.PhoneNumber phoneNumber, Locale locale, String string) {
        String string2 = this.phoneUtil.getRegionCodeForNumber(phoneNumber);
        if (string.equals(string2)) {
            return this.getDescriptionForValidNumber(phoneNumber, locale);
        }
        return this.getRegionDisplayName(string2, locale);
    }
}

