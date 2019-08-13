/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.CountryCodeToRegionCodeMap;
import com.android.i18n.phonenumbers.MetadataManager;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonemetadata;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.internal.MatcherApi;
import com.android.i18n.phonenumbers.internal.RegexBasedMatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortNumberInfo {
    private static final ShortNumberInfo INSTANCE;
    private static final Set<String> REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT;
    private static final Logger logger;
    private final Map<Integer, List<String>> countryCallingCodeToRegionCodeMap;
    private final MatcherApi matcherApi;

    static {
        logger = Logger.getLogger(ShortNumberInfo.class.getName());
        INSTANCE = new ShortNumberInfo(RegexBasedMatcher.create());
        REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT = new HashSet<String>();
        REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.add("BR");
        REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.add("CL");
        REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.add("NI");
    }

    ShortNumberInfo(MatcherApi matcherApi) {
        this.matcherApi = matcherApi;
        this.countryCallingCodeToRegionCodeMap = CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap();
    }

    public static ShortNumberInfo getInstance() {
        return INSTANCE;
    }

    private static String getNationalSignificantNumber(Phonenumber.PhoneNumber phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        if (phoneNumber.isItalianLeadingZero()) {
            char[] arrc = new char[phoneNumber.getNumberOfLeadingZeros()];
            Arrays.fill(arrc, '0');
            stringBuilder.append(new String(arrc));
        }
        stringBuilder.append(phoneNumber.getNationalNumber());
        return stringBuilder.toString();
    }

    private String getRegionCodeForShortNumberFromRegionList(Phonenumber.PhoneNumber object, List<String> object2) {
        if (object2.size() == 0) {
            return null;
        }
        if (object2.size() == 1) {
            return object2.get(0);
        }
        object = ShortNumberInfo.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
        Iterator<String> iterator = object2.iterator();
        while (iterator.hasNext()) {
            object2 = iterator.next();
            Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion((String)object2);
            if (phoneMetadata == null || !this.matchesPossibleNumberAndNationalNumber((String)object, phoneMetadata.getShortCode())) continue;
            return object2;
        }
        return null;
    }

    private List<String> getRegionCodesForCountryCode(int n) {
        List<String> list;
        block0 : {
            list = this.countryCallingCodeToRegionCodeMap.get(n);
            if (list != null) break block0;
            list = new ArrayList<String>(0);
        }
        return Collections.unmodifiableList(list);
    }

    private boolean matchesEmergencyNumberHelper(CharSequence object, String string, boolean bl) {
        CharSequence charSequence = PhoneNumberUtil.extractPossibleNumber((CharSequence)object);
        boolean bl2 = PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(charSequence).lookingAt();
        boolean bl3 = false;
        if (bl2) {
            return false;
        }
        object = MetadataManager.getShortNumberMetadataForRegion(string);
        if (object != null && ((Phonemetadata.PhoneMetadata)object).hasEmergency()) {
            charSequence = PhoneNumberUtil.normalizeDigitsOnly(charSequence);
            bl = bl && !REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.contains(string) ? true : bl3;
            return this.matcherApi.matchNationalNumber(charSequence, ((Phonemetadata.PhoneMetadata)object).getEmergency(), bl);
        }
        return false;
    }

    private boolean matchesPossibleNumberAndNationalNumber(String string, Phonemetadata.PhoneNumberDesc phoneNumberDesc) {
        if (phoneNumberDesc.getPossibleLengthCount() > 0 && !phoneNumberDesc.getPossibleLengthList().contains(string.length())) {
            return false;
        }
        return this.matcherApi.matchNationalNumber(string, phoneNumberDesc, false);
    }

    private boolean regionDialingFromMatchesNumber(Phonenumber.PhoneNumber phoneNumber, String string) {
        return this.getRegionCodesForCountryCode(phoneNumber.getCountryCode()).contains(string);
    }

    public boolean connectsToEmergencyNumber(String string, String string2) {
        return this.matchesEmergencyNumberHelper(string, string2, true);
    }

    String getExampleShortNumber(String object) {
        if ((object = MetadataManager.getShortNumberMetadataForRegion((String)object)) == null) {
            return "";
        }
        if (((Phonemetadata.PhoneNumberDesc)(object = ((Phonemetadata.PhoneMetadata)object).getShortCode())).hasExampleNumber()) {
            return ((Phonemetadata.PhoneNumberDesc)object).getExampleNumber();
        }
        return "";
    }

    String getExampleShortNumberForCost(String object, ShortNumberCost shortNumberCost) {
        Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion((String)object);
        if (phoneMetadata == null) {
            return "";
        }
        object = null;
        int n = 1.$SwitchMap$com$android$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost[shortNumberCost.ordinal()];
        if (n != 1) {
            if (n != 3) {
                if (n == 4) {
                    object = phoneMetadata.getTollFree();
                }
            } else {
                object = phoneMetadata.getStandardRate();
            }
        } else {
            object = phoneMetadata.getPremiumRate();
        }
        if (object != null && ((Phonemetadata.PhoneNumberDesc)object).hasExampleNumber()) {
            return ((Phonemetadata.PhoneNumberDesc)object).getExampleNumber();
        }
        return "";
    }

    public ShortNumberCost getExpectedCost(Phonenumber.PhoneNumber phoneNumber) {
        Object object = this.getRegionCodesForCountryCode(phoneNumber.getCountryCode());
        if (object.size() == 0) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        if (object.size() == 1) {
            return this.getExpectedCostForRegion(phoneNumber, object.get(0));
        }
        Object object2 = ShortNumberCost.TOLL_FREE;
        Iterator<String> iterator = object.iterator();
        while (iterator.hasNext()) {
            ShortNumberCost shortNumberCost = this.getExpectedCostForRegion(phoneNumber, iterator.next());
            int n = 1.$SwitchMap$com$android$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost[shortNumberCost.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            object = logger;
                            Level level = Level.SEVERE;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unrecognised cost for region: ");
                            stringBuilder.append((Object)shortNumberCost);
                            ((Logger)object).log(level, stringBuilder.toString());
                            object = object2;
                        } else {
                            object = object2;
                        }
                    } else {
                        object = object2;
                        if (object2 != ShortNumberCost.UNKNOWN_COST) {
                            object = ShortNumberCost.STANDARD_RATE;
                        }
                    }
                } else {
                    object = ShortNumberCost.UNKNOWN_COST;
                }
                object2 = object;
                continue;
            }
            return ShortNumberCost.PREMIUM_RATE;
        }
        return object2;
    }

    public ShortNumberCost getExpectedCostForRegion(Phonenumber.PhoneNumber object, String string) {
        if (!this.regionDialingFromMatchesNumber((Phonenumber.PhoneNumber)object, string)) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        Phonemetadata.PhoneMetadata phoneMetadata = MetadataManager.getShortNumberMetadataForRegion(string);
        if (phoneMetadata == null) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        object = ShortNumberInfo.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
        if (!phoneMetadata.getGeneralDesc().getPossibleLengthList().contains(((String)object).length())) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        if (this.matchesPossibleNumberAndNationalNumber((String)object, phoneMetadata.getPremiumRate())) {
            return ShortNumberCost.PREMIUM_RATE;
        }
        if (this.matchesPossibleNumberAndNationalNumber((String)object, phoneMetadata.getStandardRate())) {
            return ShortNumberCost.STANDARD_RATE;
        }
        if (this.matchesPossibleNumberAndNationalNumber((String)object, phoneMetadata.getTollFree())) {
            return ShortNumberCost.TOLL_FREE;
        }
        if (this.isEmergencyNumber((CharSequence)object, string)) {
            return ShortNumberCost.TOLL_FREE;
        }
        return ShortNumberCost.UNKNOWN_COST;
    }

    Set<String> getSupportedRegions() {
        return MetadataManager.getSupportedShortNumberRegions();
    }

    public boolean isCarrierSpecific(Phonenumber.PhoneNumber object) {
        Object object2 = this.getRegionCodeForShortNumberFromRegionList((Phonenumber.PhoneNumber)object, this.getRegionCodesForCountryCode(((Phonenumber.PhoneNumber)object).getCountryCode()));
        object = ShortNumberInfo.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
        boolean bl = (object2 = MetadataManager.getShortNumberMetadataForRegion((String)object2)) != null && this.matchesPossibleNumberAndNationalNumber((String)object, ((Phonemetadata.PhoneMetadata)object2).getCarrierSpecific());
        return bl;
    }

    public boolean isCarrierSpecificForRegion(Phonenumber.PhoneNumber object, String object2) {
        boolean bl;
        block1 : {
            boolean bl2 = this.regionDialingFromMatchesNumber((Phonenumber.PhoneNumber)object, (String)object2);
            bl = false;
            if (!bl2) {
                return false;
            }
            object = ShortNumberInfo.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
            if ((object2 = MetadataManager.getShortNumberMetadataForRegion((String)object2)) == null || !this.matchesPossibleNumberAndNationalNumber((String)object, ((Phonemetadata.PhoneMetadata)object2).getCarrierSpecific())) break block1;
            bl = true;
        }
        return bl;
    }

    public boolean isEmergencyNumber(CharSequence charSequence, String string) {
        return this.matchesEmergencyNumberHelper(charSequence, string, false);
    }

    public boolean isPossibleShortNumber(Phonenumber.PhoneNumber object) {
        Object object2 = this.getRegionCodesForCountryCode(((Phonenumber.PhoneNumber)object).getCountryCode());
        int n = ShortNumberInfo.getNationalSignificantNumber((Phonenumber.PhoneNumber)object).length();
        object = object2.iterator();
        while (object.hasNext()) {
            object2 = MetadataManager.getShortNumberMetadataForRegion((String)object.next());
            if (object2 == null || !((Phonemetadata.PhoneMetadata)object2).getGeneralDesc().getPossibleLengthList().contains(n)) continue;
            return true;
        }
        return false;
    }

    public boolean isPossibleShortNumberForRegion(Phonenumber.PhoneNumber phoneNumber, String object) {
        if (!this.regionDialingFromMatchesNumber(phoneNumber, (String)object)) {
            return false;
        }
        if ((object = MetadataManager.getShortNumberMetadataForRegion((String)object)) == null) {
            return false;
        }
        int n = ShortNumberInfo.getNationalSignificantNumber(phoneNumber).length();
        return ((Phonemetadata.PhoneMetadata)object).getGeneralDesc().getPossibleLengthList().contains(n);
    }

    public boolean isSmsServiceForRegion(Phonenumber.PhoneNumber phoneNumber, String object) {
        boolean bl;
        block1 : {
            boolean bl2 = this.regionDialingFromMatchesNumber(phoneNumber, (String)object);
            bl = false;
            if (!bl2) {
                return false;
            }
            if ((object = MetadataManager.getShortNumberMetadataForRegion((String)object)) == null || !this.matchesPossibleNumberAndNationalNumber(ShortNumberInfo.getNationalSignificantNumber(phoneNumber), ((Phonemetadata.PhoneMetadata)object).getSmsServices())) break block1;
            bl = true;
        }
        return bl;
    }

    public boolean isValidShortNumber(Phonenumber.PhoneNumber phoneNumber) {
        List<String> list = this.getRegionCodesForCountryCode(phoneNumber.getCountryCode());
        String string = this.getRegionCodeForShortNumberFromRegionList(phoneNumber, list);
        if (list.size() > 1 && string != null) {
            return true;
        }
        return this.isValidShortNumberForRegion(phoneNumber, string);
    }

    public boolean isValidShortNumberForRegion(Phonenumber.PhoneNumber object, String object2) {
        if (!this.regionDialingFromMatchesNumber((Phonenumber.PhoneNumber)object, (String)object2)) {
            return false;
        }
        if ((object2 = MetadataManager.getShortNumberMetadataForRegion((String)object2)) == null) {
            return false;
        }
        if (!this.matchesPossibleNumberAndNationalNumber((String)(object = ShortNumberInfo.getNationalSignificantNumber((Phonenumber.PhoneNumber)object)), ((Phonemetadata.PhoneMetadata)object2).getGeneralDesc())) {
            return false;
        }
        return this.matchesPossibleNumberAndNationalNumber((String)object, ((Phonemetadata.PhoneMetadata)object2).getShortCode());
    }

    public static enum ShortNumberCost {
        TOLL_FREE,
        STANDARD_RATE,
        PREMIUM_RATE,
        UNKNOWN_COST;
        
    }

}

