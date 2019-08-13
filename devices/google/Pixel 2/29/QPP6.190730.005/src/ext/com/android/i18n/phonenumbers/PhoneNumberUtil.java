/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.AsYouTypeFormatter;
import com.android.i18n.phonenumbers.CountryCodeToRegionCodeMap;
import com.android.i18n.phonenumbers.MetadataLoader;
import com.android.i18n.phonenumbers.MetadataManager;
import com.android.i18n.phonenumbers.MetadataSource;
import com.android.i18n.phonenumbers.MultiFileMetadataSourceImpl;
import com.android.i18n.phonenumbers.NumberParseException;
import com.android.i18n.phonenumbers.PhoneNumberMatch;
import com.android.i18n.phonenumbers.PhoneNumberMatcher;
import com.android.i18n.phonenumbers.Phonemetadata;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.internal.MatcherApi;
import com.android.i18n.phonenumbers.internal.RegexBasedMatcher;
import com.android.i18n.phonenumbers.internal.RegexCache;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Serializable;
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
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberUtil {
    private static final Map<Character, Character> ALL_PLUS_NUMBER_GROUPING_SYMBOLS;
    private static final Map<Character, Character> ALPHA_MAPPINGS;
    private static final Map<Character, Character> ALPHA_PHONE_MAPPINGS;
    private static final Pattern CAPTURING_DIGIT_PATTERN;
    private static final String CAPTURING_EXTN_DIGITS = "(\\p{Nd}{1,7})";
    private static final String CC_STRING = "$CC";
    private static final String COLOMBIA_MOBILE_TO_FIXED_LINE_PREFIX = "3";
    private static final String DEFAULT_EXTN_PREFIX = " ext. ";
    private static final Map<Character, Character> DIALLABLE_CHAR_MAPPINGS;
    private static final String DIGITS = "\\p{Nd}";
    private static final Pattern EXTN_PATTERN;
    static final String EXTN_PATTERNS_FOR_MATCHING;
    private static final String EXTN_PATTERNS_FOR_PARSING;
    private static final String FG_STRING = "$FG";
    private static final Pattern FIRST_GROUP_ONLY_PREFIX_PATTERN;
    private static final Pattern FIRST_GROUP_PATTERN;
    private static final Set<Integer> GEO_MOBILE_COUNTRIES;
    private static final Set<Integer> GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES;
    private static final int MAX_INPUT_STRING_LENGTH = 250;
    static final int MAX_LENGTH_COUNTRY_CODE = 3;
    static final int MAX_LENGTH_FOR_NSN = 17;
    private static final int MIN_LENGTH_FOR_NSN = 2;
    private static final Map<Integer, String> MOBILE_TOKEN_MAPPINGS;
    private static final int NANPA_COUNTRY_CODE = 1;
    static final Pattern NON_DIGITS_PATTERN;
    private static final String NP_STRING = "$NP";
    static final String PLUS_CHARS = "+\uff0b";
    static final Pattern PLUS_CHARS_PATTERN;
    static final char PLUS_SIGN = '+';
    static final int REGEX_FLAGS = 66;
    public static final String REGION_CODE_FOR_NON_GEO_ENTITY = "001";
    private static final String RFC3966_EXTN_PREFIX = ";ext=";
    private static final String RFC3966_ISDN_SUBADDRESS = ";isub=";
    private static final String RFC3966_PHONE_CONTEXT = ";phone-context=";
    private static final String RFC3966_PREFIX = "tel:";
    private static final String SECOND_NUMBER_START = "[\\\\/] *x";
    static final Pattern SECOND_NUMBER_START_PATTERN;
    private static final Pattern SEPARATOR_PATTERN;
    private static final Pattern SINGLE_INTERNATIONAL_PREFIX;
    private static final char STAR_SIGN = '*';
    private static final String UNKNOWN_REGION = "ZZ";
    private static final String UNWANTED_END_CHARS = "[[\\P{N}&&\\P{L}]&&[^#]]+$";
    static final Pattern UNWANTED_END_CHAR_PATTERN;
    private static final String VALID_ALPHA;
    private static final Pattern VALID_ALPHA_PHONE_PATTERN;
    private static final String VALID_PHONE_NUMBER;
    private static final Pattern VALID_PHONE_NUMBER_PATTERN;
    static final String VALID_PUNCTUATION = "-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f \u00a0\u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e";
    private static final String VALID_START_CHAR = "[+\uff0b\\p{Nd}]";
    private static final Pattern VALID_START_CHAR_PATTERN;
    private static PhoneNumberUtil instance;
    private static final Logger logger;
    private final Map<Integer, List<String>> countryCallingCodeToRegionCodeMap;
    private final Set<Integer> countryCodesForNonGeographicalRegion = new HashSet<Integer>();
    private final MatcherApi matcherApi = RegexBasedMatcher.create();
    private final MetadataSource metadataSource;
    private final Set<String> nanpaRegions = new HashSet<String>(35);
    private final RegexCache regexCache = new RegexCache(100);
    private final Set<String> supportedRegions = new HashSet<String>(320);

    static {
        logger = Logger.getLogger(PhoneNumberUtil.class.getName());
        Serializable serializable = new HashMap<Integer, String>();
        Object object = Character.valueOf('4');
        ((HashMap)serializable).put(52, "1");
        Serializable serializable2 = Character.valueOf('6');
        ((HashMap)serializable).put(54, "9");
        MOBILE_TOKEN_MAPPINGS = Collections.unmodifiableMap(serializable);
        serializable = new HashSet();
        ((HashSet)serializable).add(86);
        GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES = Collections.unmodifiableSet(serializable);
        Serializable serializable3 = new HashSet<Integer>();
        serializable3.add(52);
        serializable3.add(54);
        Character c = Character.valueOf('7');
        serializable3.add(55);
        serializable3.add(62);
        serializable3.addAll((Collection<Integer>)((Object)serializable));
        GEO_MOBILE_COUNTRIES = Collections.unmodifiableSet(serializable3);
        serializable = new HashMap();
        ((HashMap)serializable).put(Character.valueOf('0'), Character.valueOf('0'));
        ((HashMap)serializable).put(Character.valueOf('1'), Character.valueOf('1'));
        Character c2 = Character.valueOf('2');
        ((HashMap)serializable).put(c2, c2);
        serializable3 = Character.valueOf('3');
        ((HashMap)serializable).put(serializable3, serializable3);
        ((HashMap)serializable).put(object, object);
        Character c3 = Character.valueOf('5');
        ((HashMap)serializable).put(c3, c3);
        ((HashMap)serializable).put(serializable2, serializable2);
        ((HashMap)serializable).put(c, c);
        ((HashMap)serializable).put(Character.valueOf('8'), Character.valueOf('8'));
        Character c4 = Character.valueOf('9');
        ((HashMap)serializable).put(c4, c4);
        HashMap<Character, Serializable> hashMap = new HashMap<Character, Serializable>(40);
        hashMap.put(Character.valueOf('A'), c2);
        hashMap.put(Character.valueOf('B'), c2);
        hashMap.put(Character.valueOf('C'), c2);
        hashMap.put(Character.valueOf('D'), serializable3);
        hashMap.put(Character.valueOf('E'), serializable3);
        hashMap.put(Character.valueOf('F'), serializable3);
        hashMap.put(Character.valueOf('G'), (Serializable)object);
        hashMap.put(Character.valueOf('H'), (Serializable)object);
        hashMap.put(Character.valueOf('I'), (Serializable)object);
        hashMap.put(Character.valueOf('J'), c3);
        hashMap.put(Character.valueOf('K'), c3);
        hashMap.put(Character.valueOf('L'), c3);
        hashMap.put(Character.valueOf('M'), serializable2);
        hashMap.put(Character.valueOf('N'), serializable2);
        hashMap.put(Character.valueOf('O'), serializable2);
        hashMap.put(Character.valueOf('P'), c);
        hashMap.put(Character.valueOf('Q'), c);
        hashMap.put(Character.valueOf('R'), c);
        hashMap.put(Character.valueOf('S'), c);
        hashMap.put(Character.valueOf('T'), Character.valueOf('8'));
        hashMap.put(Character.valueOf('U'), Character.valueOf('8'));
        hashMap.put(Character.valueOf('V'), Character.valueOf('8'));
        hashMap.put(Character.valueOf('W'), c4);
        hashMap.put(Character.valueOf('X'), c4);
        hashMap.put(Character.valueOf('Y'), c4);
        hashMap.put(Character.valueOf('Z'), c4);
        ALPHA_MAPPINGS = Collections.unmodifiableMap(hashMap);
        object = new HashMap(100);
        ((HashMap)object).putAll(ALPHA_MAPPINGS);
        ((HashMap)object).putAll(serializable);
        ALPHA_PHONE_MAPPINGS = Collections.unmodifiableMap(object);
        object = new HashMap();
        ((HashMap)object).putAll(serializable);
        ((HashMap)object).put(Character.valueOf('+'), Character.valueOf('+'));
        ((HashMap)object).put(Character.valueOf('*'), Character.valueOf('*'));
        ((HashMap)object).put(Character.valueOf('#'), Character.valueOf('#'));
        DIALLABLE_CHAR_MAPPINGS = Collections.unmodifiableMap(object);
        serializable2 = new HashMap();
        object = ALPHA_MAPPINGS.keySet().iterator();
        while (object.hasNext()) {
            char c5 = ((Character)object.next()).charValue();
            ((HashMap)serializable2).put(Character.valueOf(Character.toLowerCase(c5)), Character.valueOf(c5));
            ((HashMap)serializable2).put(Character.valueOf(c5), Character.valueOf(c5));
        }
        ((HashMap)serializable2).putAll(serializable);
        ((HashMap)serializable2).put(Character.valueOf('-'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('\uff0d'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('\u2010'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('\u2011'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('\u2012'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('\u2013'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('\u2014'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('\u2015'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('\u2212'), Character.valueOf('-'));
        ((HashMap)serializable2).put(Character.valueOf('/'), Character.valueOf('/'));
        ((HashMap)serializable2).put(Character.valueOf('\uff0f'), Character.valueOf('/'));
        ((HashMap)serializable2).put(Character.valueOf(' '), Character.valueOf(' '));
        ((HashMap)serializable2).put(Character.valueOf('\u3000'), Character.valueOf(' '));
        ((HashMap)serializable2).put(Character.valueOf('\u2060'), Character.valueOf(' '));
        ((HashMap)serializable2).put(Character.valueOf('.'), Character.valueOf('.'));
        ((HashMap)serializable2).put(Character.valueOf('\uff0e'), Character.valueOf('.'));
        ALL_PLUS_NUMBER_GROUPING_SYMBOLS = Collections.unmodifiableMap(serializable2);
        SINGLE_INTERNATIONAL_PREFIX = Pattern.compile("[\\d]+(?:[~\u2053\u223c\uff5e][\\d]+)?");
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(Arrays.toString(ALPHA_MAPPINGS.keySet().toArray()).replaceAll("[, \\[\\]]", ""));
        ((StringBuilder)serializable).append(Arrays.toString(ALPHA_MAPPINGS.keySet().toArray()).toLowerCase().replaceAll("[, \\[\\]]", ""));
        VALID_ALPHA = ((StringBuilder)serializable).toString();
        PLUS_CHARS_PATTERN = Pattern.compile("[+\uff0b]+");
        SEPARATOR_PATTERN = Pattern.compile("[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f \u00a0\u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e]+");
        CAPTURING_DIGIT_PATTERN = Pattern.compile("(\\p{Nd})");
        VALID_START_CHAR_PATTERN = Pattern.compile(VALID_START_CHAR);
        SECOND_NUMBER_START_PATTERN = Pattern.compile(SECOND_NUMBER_START);
        UNWANTED_END_CHAR_PATTERN = Pattern.compile(UNWANTED_END_CHARS);
        VALID_ALPHA_PHONE_PATTERN = Pattern.compile("(?:.*?[A-Za-z]){3}.*");
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\\p{Nd}{2}|[+\uff0b]*+(?:[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f \u00a0\u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e*]*\\p{Nd}){3,}[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f \u00a0\u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e*");
        ((StringBuilder)serializable).append(VALID_ALPHA);
        ((StringBuilder)serializable).append(DIGITS);
        ((StringBuilder)serializable).append("]*");
        VALID_PHONE_NUMBER = ((StringBuilder)serializable).toString();
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(",;");
        ((StringBuilder)serializable).append("x\uff58#\uff03~\uff5e");
        EXTN_PATTERNS_FOR_PARSING = PhoneNumberUtil.createExtnPattern(((StringBuilder)serializable).toString());
        EXTN_PATTERNS_FOR_MATCHING = PhoneNumberUtil.createExtnPattern("x\uff58#\uff03~\uff5e");
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("(?:");
        ((StringBuilder)serializable).append(EXTN_PATTERNS_FOR_PARSING);
        ((StringBuilder)serializable).append(")$");
        EXTN_PATTERN = Pattern.compile(((StringBuilder)serializable).toString(), 66);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(VALID_PHONE_NUMBER);
        ((StringBuilder)serializable).append("(?:");
        ((StringBuilder)serializable).append(EXTN_PATTERNS_FOR_PARSING);
        ((StringBuilder)serializable).append(")?");
        VALID_PHONE_NUMBER_PATTERN = Pattern.compile(((StringBuilder)serializable).toString(), 66);
        NON_DIGITS_PATTERN = Pattern.compile("(\\D+)");
        FIRST_GROUP_PATTERN = Pattern.compile("(\\$\\d)");
        FIRST_GROUP_ONLY_PREFIX_PATTERN = Pattern.compile("\\(?\\$1\\)?");
        instance = null;
    }

    PhoneNumberUtil(MetadataSource list, Map<Integer, List<String>> map) {
        this.metadataSource = list;
        this.countryCallingCodeToRegionCodeMap = map;
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            list = entry.getValue();
            if (list.size() == 1 && REGION_CODE_FOR_NON_GEO_ENTITY.equals(list.get(0))) {
                this.countryCodesForNonGeographicalRegion.add(entry.getKey());
                continue;
            }
            this.supportedRegions.addAll((Collection<String>)list);
        }
        if (this.supportedRegions.remove(REGION_CODE_FOR_NON_GEO_ENTITY)) {
            logger.log(Level.WARNING, "invalid metadata (country calling code was mapped to the non-geo entity as well as specific region(s))");
        }
        this.nanpaRegions.addAll((Collection<String>)map.get(1));
    }

    private void buildNationalNumberForParsing(String string, StringBuilder stringBuilder) {
        int n;
        int n2 = string.indexOf(RFC3966_PHONE_CONTEXT);
        if (n2 >= 0) {
            int n3 = RFC3966_PHONE_CONTEXT.length() + n2;
            if (n3 < string.length() - 1 && string.charAt(n3) == '+') {
                n = string.indexOf(59, n3);
                if (n > 0) {
                    stringBuilder.append(string.substring(n3, n));
                } else {
                    stringBuilder.append(string.substring(n3));
                }
            }
            n = (n = string.indexOf(RFC3966_PREFIX)) >= 0 ? RFC3966_PREFIX.length() + n : 0;
            stringBuilder.append(string.substring(n, n2));
        } else {
            stringBuilder.append(PhoneNumberUtil.extractPossibleNumber(string));
        }
        n = stringBuilder.indexOf(RFC3966_ISDN_SUBADDRESS);
        if (n > 0) {
            stringBuilder.delete(n, stringBuilder.length());
        }
    }

    private boolean checkRegionForParsing(CharSequence charSequence, String string) {
        return this.isValidRegionCode(string) || charSequence != null && charSequence.length() != 0 && PLUS_CHARS_PATTERN.matcher(charSequence).lookingAt();
    }

    public static String convertAlphaCharactersInNumber(CharSequence charSequence) {
        return PhoneNumberUtil.normalizeHelper(charSequence, ALPHA_PHONE_MAPPINGS, false);
    }

    private static Phonenumber.PhoneNumber copyCoreFieldsOnly(Phonenumber.PhoneNumber phoneNumber) {
        Phonenumber.PhoneNumber phoneNumber2 = new Phonenumber.PhoneNumber();
        phoneNumber2.setCountryCode(phoneNumber.getCountryCode());
        phoneNumber2.setNationalNumber(phoneNumber.getNationalNumber());
        if (phoneNumber.getExtension().length() > 0) {
            phoneNumber2.setExtension(phoneNumber.getExtension());
        }
        if (phoneNumber.isItalianLeadingZero()) {
            phoneNumber2.setItalianLeadingZero(true);
            phoneNumber2.setNumberOfLeadingZeros(phoneNumber.getNumberOfLeadingZeros());
        }
        return phoneNumber2;
    }

    private static String createExtnPattern(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(";ext=(\\p{Nd}{1,7})|[ \u00a0\\t,]*(?:e?xt(?:ensi(?:o\u0301?|\u00f3))?n?|\uff45?\uff58\uff54\uff4e?|\u0434\u043e\u0431|[");
        stringBuilder.append(string);
        stringBuilder.append("]|int|anexo|\uff49\uff4e\uff54)[:\\.\uff0e]?[ \u00a0\\t,-]*");
        stringBuilder.append(CAPTURING_EXTN_DIGITS);
        stringBuilder.append("#?|[- ]+(");
        stringBuilder.append(DIGITS);
        stringBuilder.append("{1,5})#");
        return stringBuilder.toString();
    }

    public static PhoneNumberUtil createInstance(MetadataLoader metadataLoader) {
        if (metadataLoader != null) {
            return PhoneNumberUtil.createInstance(new MultiFileMetadataSourceImpl(metadataLoader));
        }
        throw new IllegalArgumentException("metadataLoader could not be null.");
    }

    private static PhoneNumberUtil createInstance(MetadataSource metadataSource) {
        if (metadataSource != null) {
            return new PhoneNumberUtil(metadataSource, CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap());
        }
        throw new IllegalArgumentException("metadataSource could not be null.");
    }

    private static boolean descHasData(Phonemetadata.PhoneNumberDesc phoneNumberDesc) {
        boolean bl = phoneNumberDesc.hasExampleNumber() || PhoneNumberUtil.descHasPossibleNumberData(phoneNumberDesc) || phoneNumberDesc.hasNationalNumberPattern();
        return bl;
    }

    private static boolean descHasPossibleNumberData(Phonemetadata.PhoneNumberDesc phoneNumberDesc) {
        int n = phoneNumberDesc.getPossibleLengthCount();
        boolean bl = false;
        if (n != 1 || phoneNumberDesc.getPossibleLength(0) != -1) {
            bl = true;
        }
        return bl;
    }

    static CharSequence extractPossibleNumber(CharSequence object) {
        Object object2 = VALID_START_CHAR_PATTERN.matcher((CharSequence)object);
        if (((Matcher)object2).find()) {
            object2 = object.subSequence(((Matcher)object2).start(), object.length());
            Matcher matcher = UNWANTED_END_CHAR_PATTERN.matcher((CharSequence)object2);
            object = object2;
            if (matcher.find()) {
                object = object2.subSequence(0, matcher.start());
            }
            matcher = SECOND_NUMBER_START_PATTERN.matcher((CharSequence)object);
            object2 = object;
            if (matcher.find()) {
                object2 = object.subSequence(0, matcher.start());
            }
            return object2;
        }
        return "";
    }

    private String formatNsn(String string, Phonemetadata.PhoneMetadata phoneMetadata, PhoneNumberFormat phoneNumberFormat) {
        return this.formatNsn(string, phoneMetadata, phoneNumberFormat, null);
    }

    private String formatNsn(String string, Phonemetadata.PhoneMetadata list, PhoneNumberFormat phoneNumberFormat, CharSequence charSequence) {
        list = ((Phonemetadata.PhoneMetadata)((Object)list)).intlNumberFormats().size() != 0 && phoneNumberFormat != PhoneNumberFormat.NATIONAL ? ((Phonemetadata.PhoneMetadata)((Object)list)).intlNumberFormats() : ((Phonemetadata.PhoneMetadata)((Object)list)).numberFormats();
        list = this.chooseFormattingPatternForNumber(list, string);
        if (list != null) {
            string = this.formatNsnUsingPattern(string, (Phonemetadata.NumberFormat)((Object)list), phoneNumberFormat, charSequence);
        }
        return string;
    }

    private String formatNsnUsingPattern(String object, Phonemetadata.NumberFormat object2, PhoneNumberFormat phoneNumberFormat, CharSequence object3) {
        String string = ((Phonemetadata.NumberFormat)object2).getFormat();
        object = this.regexCache.getPatternForRegex(((Phonemetadata.NumberFormat)object2).getPattern()).matcher((CharSequence)object);
        if (phoneNumberFormat == PhoneNumberFormat.NATIONAL && object3 != null && object3.length() > 0 && ((Phonemetadata.NumberFormat)object2).getDomesticCarrierCodeFormattingRule().length() > 0) {
            object2 = ((Phonemetadata.NumberFormat)object2).getDomesticCarrierCodeFormattingRule().replace(CC_STRING, (CharSequence)object3);
            object = ((Matcher)object).replaceAll(FIRST_GROUP_PATTERN.matcher(string).replaceFirst((String)object2));
        } else {
            object2 = ((Phonemetadata.NumberFormat)object2).getNationalPrefixFormattingRule();
            if (phoneNumberFormat == PhoneNumberFormat.NATIONAL && object2 != null && ((String)object2).length() > 0) {
                object3 = FIRST_GROUP_PATTERN.matcher(string);
                object = ((Matcher)object).replaceAll(((Matcher)object3).replaceFirst((String)object2));
            } else {
                object = ((Matcher)object).replaceAll(string);
            }
        }
        object2 = object;
        if (phoneNumberFormat == PhoneNumberFormat.RFC3966) {
            object2 = SEPARATOR_PATTERN.matcher((CharSequence)object);
            if (((Matcher)object2).lookingAt()) {
                object = ((Matcher)object2).replaceFirst("");
            }
            object2 = ((Matcher)object2).reset((CharSequence)object).replaceAll("-");
        }
        return object2;
    }

    static boolean formattingRuleHasFirstGroupOnly(String string) {
        boolean bl = string.length() == 0 || FIRST_GROUP_ONLY_PREFIX_PATTERN.matcher(string).matches();
        return bl;
    }

    private int getCountryCodeForValidRegion(String string) {
        Serializable serializable = this.getMetadataForRegion(string);
        if (serializable != null) {
            return ((Phonemetadata.PhoneMetadata)serializable).getCountryCode();
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Invalid region code: ");
        ((StringBuilder)serializable).append(string);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public static String getCountryMobileToken(int n) {
        if (MOBILE_TOKEN_MAPPINGS.containsKey(n)) {
            return MOBILE_TOKEN_MAPPINGS.get(n);
        }
        return "";
    }

    @UnsupportedAppUsage
    public static PhoneNumberUtil getInstance() {
        synchronized (PhoneNumberUtil.class) {
            if (instance == null) {
                PhoneNumberUtil.setInstance(PhoneNumberUtil.createInstance(MetadataManager.DEFAULT_METADATA_LOADER));
            }
            PhoneNumberUtil phoneNumberUtil = instance;
            return phoneNumberUtil;
        }
    }

    private Phonemetadata.PhoneMetadata getMetadataForRegionOrCallingCode(int n, String object) {
        object = REGION_CODE_FOR_NON_GEO_ENTITY.equals(object) ? this.getMetadataForNonGeographicalRegion(n) : this.getMetadataForRegion((String)object);
        return object;
    }

    private PhoneNumberType getNumberTypeHelper(String string, Phonemetadata.PhoneMetadata phoneMetadata) {
        if (!this.isNumberMatchingDesc(string, phoneMetadata.getGeneralDesc())) {
            return PhoneNumberType.UNKNOWN;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getPremiumRate())) {
            return PhoneNumberType.PREMIUM_RATE;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getTollFree())) {
            return PhoneNumberType.TOLL_FREE;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getSharedCost())) {
            return PhoneNumberType.SHARED_COST;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getVoip())) {
            return PhoneNumberType.VOIP;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getPersonalNumber())) {
            return PhoneNumberType.PERSONAL_NUMBER;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getPager())) {
            return PhoneNumberType.PAGER;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getUan())) {
            return PhoneNumberType.UAN;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getVoicemail())) {
            return PhoneNumberType.VOICEMAIL;
        }
        if (this.isNumberMatchingDesc(string, phoneMetadata.getFixedLine())) {
            if (phoneMetadata.getSameMobileAndFixedLinePattern()) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            if (this.isNumberMatchingDesc(string, phoneMetadata.getMobile())) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            return PhoneNumberType.FIXED_LINE;
        }
        if (!phoneMetadata.getSameMobileAndFixedLinePattern() && this.isNumberMatchingDesc(string, phoneMetadata.getMobile())) {
            return PhoneNumberType.MOBILE;
        }
        return PhoneNumberType.UNKNOWN;
    }

    private String getRegionCodeForNumberFromRegionList(Phonenumber.PhoneNumber object, List<String> object2) {
        object = this.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
        object2 = object2.iterator();
        while (object2.hasNext()) {
            String string = (String)object2.next();
            Phonemetadata.PhoneMetadata phoneMetadata = this.getMetadataForRegion(string);
            if (!(phoneMetadata.hasLeadingDigits() ? this.regexCache.getPatternForRegex(phoneMetadata.getLeadingDigits()).matcher((CharSequence)object).lookingAt() : this.getNumberTypeHelper((String)object, phoneMetadata) != PhoneNumberType.UNKNOWN)) continue;
            return string;
        }
        return null;
    }

    private Set<PhoneNumberType> getSupportedTypesForMetadata(Phonemetadata.PhoneMetadata phoneMetadata) {
        TreeSet<PhoneNumberType> treeSet = new TreeSet<PhoneNumberType>();
        for (PhoneNumberType phoneNumberType : PhoneNumberType.values()) {
            if (phoneNumberType == PhoneNumberType.FIXED_LINE_OR_MOBILE || phoneNumberType == PhoneNumberType.UNKNOWN || !PhoneNumberUtil.descHasData(this.getNumberDescByType(phoneMetadata, phoneNumberType))) continue;
            treeSet.add(phoneNumberType);
        }
        return Collections.unmodifiableSet(treeSet);
    }

    private boolean hasFormattingPatternForNumber(Phonenumber.PhoneNumber object) {
        int n = ((Phonenumber.PhoneNumber)object).getCountryCode();
        Object object2 = this.getRegionCodeForCountryCode(n);
        object2 = this.getMetadataForRegionOrCallingCode(n, (String)object2);
        boolean bl = false;
        if (object2 == null) {
            return false;
        }
        object = this.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
        if (this.chooseFormattingPatternForNumber(((Phonemetadata.PhoneMetadata)object2).numberFormats(), (String)object) != null) {
            bl = true;
        }
        return bl;
    }

    private boolean hasValidCountryCallingCode(int n) {
        return this.countryCallingCodeToRegionCodeMap.containsKey(n);
    }

    private boolean isNationalNumberSuffixOfTheOther(Phonenumber.PhoneNumber object, Phonenumber.PhoneNumber object2) {
        boolean bl = ((String)(object = String.valueOf(((Phonenumber.PhoneNumber)object).getNationalNumber()))).endsWith((String)(object2 = String.valueOf(((Phonenumber.PhoneNumber)object2).getNationalNumber()))) || ((String)object2).endsWith((String)object);
        return bl;
    }

    private boolean isValidRegionCode(String string) {
        boolean bl = string != null && this.supportedRegions.contains(string);
        return bl;
    }

    static boolean isViablePhoneNumber(CharSequence charSequence) {
        if (charSequence.length() < 2) {
            return false;
        }
        return VALID_PHONE_NUMBER_PATTERN.matcher(charSequence).matches();
    }

    private void maybeAppendFormattedExtension(Phonenumber.PhoneNumber phoneNumber, Phonemetadata.PhoneMetadata phoneMetadata, PhoneNumberFormat phoneNumberFormat, StringBuilder stringBuilder) {
        if (phoneNumber.hasExtension() && phoneNumber.getExtension().length() > 0) {
            if (phoneNumberFormat == PhoneNumberFormat.RFC3966) {
                stringBuilder.append(RFC3966_EXTN_PREFIX);
                stringBuilder.append(phoneNumber.getExtension());
            } else if (phoneMetadata.hasPreferredExtnPrefix()) {
                stringBuilder.append(phoneMetadata.getPreferredExtnPrefix());
                stringBuilder.append(phoneNumber.getExtension());
            } else {
                stringBuilder.append(DEFAULT_EXTN_PREFIX);
                stringBuilder.append(phoneNumber.getExtension());
            }
        }
    }

    static StringBuilder normalize(StringBuilder stringBuilder) {
        if (VALID_ALPHA_PHONE_PATTERN.matcher(stringBuilder).matches()) {
            stringBuilder.replace(0, stringBuilder.length(), PhoneNumberUtil.normalizeHelper(stringBuilder, ALPHA_PHONE_MAPPINGS, true));
        } else {
            stringBuilder.replace(0, stringBuilder.length(), PhoneNumberUtil.normalizeDigitsOnly(stringBuilder));
        }
        return stringBuilder;
    }

    public static String normalizeDiallableCharsOnly(CharSequence charSequence) {
        return PhoneNumberUtil.normalizeHelper(charSequence, DIALLABLE_CHAR_MAPPINGS, true);
    }

    static StringBuilder normalizeDigits(CharSequence charSequence, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder(charSequence.length());
        for (int i = 0; i < charSequence.length(); ++i) {
            char c = charSequence.charAt(i);
            int n = Character.digit(c, 10);
            if (n != -1) {
                stringBuilder.append(n);
                continue;
            }
            if (!bl) continue;
            stringBuilder.append(c);
        }
        return stringBuilder;
    }

    public static String normalizeDigitsOnly(CharSequence charSequence) {
        return PhoneNumberUtil.normalizeDigits(charSequence, false).toString();
    }

    private static String normalizeHelper(CharSequence charSequence, Map<Character, Character> map, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder(charSequence.length());
        for (int i = 0; i < charSequence.length(); ++i) {
            char c = charSequence.charAt(i);
            Character c2 = map.get(Character.valueOf(Character.toUpperCase(c)));
            if (c2 != null) {
                stringBuilder.append(c2);
                continue;
            }
            if (bl) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void parseHelper(CharSequence object, String object2, boolean bl, boolean bl2, Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        if (object != null) {
            if (object.length() <= 250) {
                CharSequence charSequence = new StringBuilder();
                object = object.toString();
                this.buildNationalNumberForParsing((String)object, (StringBuilder)charSequence);
                if (PhoneNumberUtil.isViablePhoneNumber(charSequence)) {
                    NumberParseException numberParseException2;
                    block23 : {
                        block24 : {
                            Object object3;
                            int n;
                            if (bl2 && !this.checkRegionForParsing(charSequence, (String)object2)) {
                                throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Missing or invalid default region.");
                            }
                            if (bl) {
                                phoneNumber.setRawInput((String)object);
                            }
                            if (((String)(object = this.maybeStripExtension((StringBuilder)charSequence))).length() > 0) {
                                phoneNumber.setExtension((String)object);
                            }
                            object = this.getMetadataForRegion((String)object2);
                            StringBuilder stringBuilder = new StringBuilder();
                            try {
                                n = this.maybeExtractCountryCode(charSequence, (Phonemetadata.PhoneMetadata)object, stringBuilder, bl, phoneNumber);
                            }
                            catch (NumberParseException numberParseException2) {
                                object3 = PLUS_CHARS_PATTERN.matcher(charSequence);
                                if (numberParseException2.getErrorType() != NumberParseException.ErrorType.INVALID_COUNTRY_CODE || !((Matcher)object3).lookingAt()) break block23;
                                n = this.maybeExtractCountryCode(((StringBuilder)charSequence).substring(((Matcher)object3).end()), (Phonemetadata.PhoneMetadata)object, stringBuilder, bl, phoneNumber);
                                if (n == 0) break block24;
                            }
                            if (n != 0) {
                                charSequence = this.getRegionCodeForCountryCode(n);
                                if (!((String)charSequence).equals(object2)) {
                                    object = this.getMetadataForRegionOrCallingCode(n, (String)charSequence);
                                }
                                object2 = object;
                            } else {
                                stringBuilder.append(PhoneNumberUtil.normalize((StringBuilder)charSequence));
                                if (object2 != null) {
                                    phoneNumber.setCountryCode(((Phonemetadata.PhoneMetadata)object).getCountryCode());
                                    object2 = object;
                                } else {
                                    object2 = object;
                                    if (bl) {
                                        phoneNumber.clearCountryCodeSource();
                                        object2 = object;
                                    }
                                }
                            }
                            if (stringBuilder.length() >= 2) {
                                object = stringBuilder;
                                if (object2 != null) {
                                    object3 = new StringBuilder();
                                    charSequence = new StringBuilder(stringBuilder);
                                    this.maybeStripNationalPrefixAndCarrierCode((StringBuilder)charSequence, (Phonemetadata.PhoneMetadata)object2, (StringBuilder)object3);
                                    object2 = this.testNumberLength(charSequence, (Phonemetadata.PhoneMetadata)object2);
                                    object = stringBuilder;
                                    if (object2 != ValidationResult.TOO_SHORT) {
                                        object = stringBuilder;
                                        if (object2 != ValidationResult.IS_POSSIBLE_LOCAL_ONLY) {
                                            object = stringBuilder;
                                            if (object2 != ValidationResult.INVALID_LENGTH) {
                                                object = object2 = charSequence;
                                                if (bl) {
                                                    object = object2;
                                                    if (((StringBuilder)object3).length() > 0) {
                                                        phoneNumber.setPreferredDomesticCarrierCode(((StringBuilder)object3).toString());
                                                        object = object2;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if ((n = ((StringBuilder)object).length()) >= 2) {
                                    if (n <= 17) {
                                        PhoneNumberUtil.setItalianLeadingZerosForPhoneNumber((CharSequence)object, phoneNumber);
                                        phoneNumber.setNationalNumber(Long.parseLong(((StringBuilder)object).toString()));
                                        return;
                                    }
                                    throw new NumberParseException(NumberParseException.ErrorType.TOO_LONG, "The string supplied is too long to be a phone number.");
                                }
                                throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_NSN, "The string supplied is too short to be a phone number.");
                            }
                            throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_NSN, "The string supplied is too short to be a phone number.");
                        }
                        throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Could not interpret numbers after plus-sign.");
                    }
                    throw new NumberParseException(numberParseException2.getErrorType(), numberParseException2.getMessage());
                }
                throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "The string supplied did not seem to be a phone number.");
            }
            throw new NumberParseException(NumberParseException.ErrorType.TOO_LONG, "The string supplied was too long to parse.");
        }
        throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "The phone number supplied was null.");
    }

    private boolean parsePrefixAsIdd(Pattern object, StringBuilder stringBuilder) {
        if (((Matcher)(object = ((Pattern)object).matcher(stringBuilder))).lookingAt()) {
            int n = ((Matcher)object).end();
            object = CAPTURING_DIGIT_PATTERN.matcher(stringBuilder.substring(n));
            if (((Matcher)object).find() && PhoneNumberUtil.normalizeDigitsOnly(((Matcher)object).group(1)).equals("0")) {
                return false;
            }
            stringBuilder.delete(0, n);
            return true;
        }
        return false;
    }

    private void prefixNumberWithCountryCallingCode(int n, PhoneNumberFormat phoneNumberFormat, StringBuilder stringBuilder) {
        int n2 = 2.$SwitchMap$com$android$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberFormat[phoneNumberFormat.ordinal()];
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    return;
                }
                stringBuilder.insert(0, "-").insert(0, n).insert(0, '+').insert(0, RFC3966_PREFIX);
                return;
            }
            stringBuilder.insert(0, " ").insert(0, n).insert(0, '+');
            return;
        }
        stringBuilder.insert(0, n).insert(0, '+');
    }

    private boolean rawInputContainsNationalPrefix(String string, String string2, String string3) {
        if ((string = PhoneNumberUtil.normalizeDigitsOnly(string)).startsWith(string2)) {
            try {
                boolean bl = this.isValidNumber(this.parse(string.substring(string2.length()), string3));
                return bl;
            }
            catch (NumberParseException numberParseException) {
                return false;
            }
        }
        return false;
    }

    static void setInstance(PhoneNumberUtil phoneNumberUtil) {
        synchronized (PhoneNumberUtil.class) {
            instance = phoneNumberUtil;
            return;
        }
    }

    static void setItalianLeadingZerosForPhoneNumber(CharSequence charSequence, Phonenumber.PhoneNumber phoneNumber) {
        if (charSequence.length() > 1 && charSequence.charAt(0) == '0') {
            int n;
            phoneNumber.setItalianLeadingZero(true);
            for (n = 1; n < charSequence.length() - 1 && charSequence.charAt(n) == '0'; ++n) {
            }
            if (n != 1) {
                phoneNumber.setNumberOfLeadingZeros(n);
            }
        }
    }

    private ValidationResult testNumberLength(CharSequence charSequence, Phonemetadata.PhoneMetadata phoneMetadata) {
        return this.testNumberLength(charSequence, phoneMetadata, PhoneNumberType.UNKNOWN);
    }

    private ValidationResult testNumberLength(CharSequence object, Phonemetadata.PhoneMetadata list, PhoneNumberType object2) {
        Object object3 = this.getNumberDescByType((Phonemetadata.PhoneMetadata)((Object)list), (PhoneNumberType)((Object)object2));
        List<Integer> list2 = ((Phonemetadata.PhoneNumberDesc)object3).getPossibleLengthList().isEmpty() ? ((Phonemetadata.PhoneMetadata)((Object)list)).getGeneralDesc().getPossibleLengthList() : ((Phonemetadata.PhoneNumberDesc)object3).getPossibleLengthList();
        List<Integer> list3 = ((Phonemetadata.PhoneNumberDesc)object3).getPossibleLengthLocalOnlyList();
        List<Integer> list4 = list2;
        object3 = list3;
        if (object2 == PhoneNumberType.FIXED_LINE_OR_MOBILE) {
            if (!PhoneNumberUtil.descHasPossibleNumberData(this.getNumberDescByType((Phonemetadata.PhoneMetadata)((Object)list), PhoneNumberType.FIXED_LINE))) {
                return this.testNumberLength((CharSequence)object, (Phonemetadata.PhoneMetadata)((Object)list), PhoneNumberType.MOBILE);
            }
            object2 = this.getNumberDescByType((Phonemetadata.PhoneMetadata)((Object)list), PhoneNumberType.MOBILE);
            list4 = list2;
            object3 = list3;
            if (PhoneNumberUtil.descHasPossibleNumberData((Phonemetadata.PhoneNumberDesc)object2)) {
                list4 = new ArrayList<Integer>(list2);
                list = ((Phonemetadata.PhoneNumberDesc)object2).getPossibleLengthList().size() == 0 ? ((Phonemetadata.PhoneMetadata)((Object)list)).getGeneralDesc().getPossibleLengthList() : ((Phonemetadata.PhoneNumberDesc)object2).getPossibleLengthList();
                list4.addAll(list);
                Collections.sort(list4);
                if (list3.isEmpty()) {
                    object3 = ((Phonemetadata.PhoneNumberDesc)object2).getPossibleLengthLocalOnlyList();
                } else {
                    object3 = new ArrayList<Integer>(list3);
                    object3.addAll(((Phonemetadata.PhoneNumberDesc)object2).getPossibleLengthLocalOnlyList());
                    Collections.sort(object3);
                }
            }
        }
        if (list4.get(0) == -1) {
            return ValidationResult.INVALID_LENGTH;
        }
        int n = object.length();
        if (object3.contains(n)) {
            return ValidationResult.IS_POSSIBLE_LOCAL_ONLY;
        }
        int n2 = list4.get(0);
        if (n2 == n) {
            return ValidationResult.IS_POSSIBLE;
        }
        if (n2 > n) {
            return ValidationResult.TOO_SHORT;
        }
        if (list4.get(list4.size() - 1) < n) {
            return ValidationResult.TOO_LONG;
        }
        object = list4.subList(1, list4.size()).contains(n) ? ValidationResult.IS_POSSIBLE : ValidationResult.INVALID_LENGTH;
        return object;
    }

    public boolean canBeInternationallyDialled(Phonenumber.PhoneNumber phoneNumber) {
        Phonemetadata.PhoneMetadata phoneMetadata = this.getMetadataForRegion(this.getRegionCodeForNumber(phoneNumber));
        if (phoneMetadata == null) {
            return true;
        }
        return true ^ this.isNumberMatchingDesc(this.getNationalSignificantNumber(phoneNumber), phoneMetadata.getNoInternationalDialling());
    }

    Phonemetadata.NumberFormat chooseFormattingPatternForNumber(List<Phonemetadata.NumberFormat> object, String string) {
        object = object.iterator();
        while (object.hasNext()) {
            Phonemetadata.NumberFormat numberFormat = (Phonemetadata.NumberFormat)object.next();
            int n = numberFormat.leadingDigitsPatternSize();
            if (n != 0 && !this.regexCache.getPatternForRegex(numberFormat.getLeadingDigitsPattern(n - 1)).matcher(string).lookingAt() || !this.regexCache.getPatternForRegex(numberFormat.getPattern()).matcher(string).matches()) continue;
            return numberFormat;
        }
        return null;
    }

    int extractCountryCode(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        if (stringBuilder.length() != 0 && stringBuilder.charAt(0) != '0') {
            int n = stringBuilder.length();
            for (int i = 1; i <= 3 && i <= n; ++i) {
                int n2 = Integer.parseInt(stringBuilder.substring(0, i));
                if (!this.countryCallingCodeToRegionCodeMap.containsKey(n2)) continue;
                stringBuilder2.append(stringBuilder.substring(i));
                return n2;
            }
            return 0;
        }
        return 0;
    }

    public Iterable<PhoneNumberMatch> findNumbers(CharSequence charSequence, String string) {
        return this.findNumbers(charSequence, string, Leniency.VALID, Long.MAX_VALUE);
    }

    @UnsupportedAppUsage
    public Iterable<PhoneNumberMatch> findNumbers(final CharSequence charSequence, final String string, final Leniency leniency, final long l) {
        return new Iterable<PhoneNumberMatch>(){

            @Override
            public Iterator<PhoneNumberMatch> iterator() {
                return new PhoneNumberMatcher(PhoneNumberUtil.this, charSequence, string, leniency, l);
            }
        };
    }

    @UnsupportedAppUsage
    public String format(Phonenumber.PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat) {
        CharSequence charSequence;
        if (phoneNumber.getNationalNumber() == 0L && phoneNumber.hasRawInput() && ((String)(charSequence = phoneNumber.getRawInput())).length() > 0) {
            return charSequence;
        }
        charSequence = new StringBuilder(20);
        this.format(phoneNumber, phoneNumberFormat, (StringBuilder)charSequence);
        return ((StringBuilder)charSequence).toString();
    }

    public void format(Phonenumber.PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        int n = phoneNumber.getCountryCode();
        String string = this.getNationalSignificantNumber(phoneNumber);
        if (phoneNumberFormat == PhoneNumberFormat.E164) {
            stringBuilder.append(string);
            this.prefixNumberWithCountryCallingCode(n, PhoneNumberFormat.E164, stringBuilder);
            return;
        }
        if (!this.hasValidCountryCallingCode(n)) {
            stringBuilder.append(string);
            return;
        }
        Object object = this.getRegionCodeForCountryCode(n);
        object = this.getMetadataForRegionOrCallingCode(n, (String)object);
        stringBuilder.append(this.formatNsn(string, (Phonemetadata.PhoneMetadata)object, phoneNumberFormat));
        this.maybeAppendFormattedExtension(phoneNumber, (Phonemetadata.PhoneMetadata)object, phoneNumberFormat, stringBuilder);
        this.prefixNumberWithCountryCallingCode(n, phoneNumberFormat, stringBuilder);
    }

    public String formatByPattern(Phonenumber.PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat, List<Phonemetadata.NumberFormat> object) {
        int n = phoneNumber.getCountryCode();
        String string = this.getNationalSignificantNumber(phoneNumber);
        if (!this.hasValidCountryCallingCode(n)) {
            return string;
        }
        Object object2 = this.getRegionCodeForCountryCode(n);
        object2 = this.getMetadataForRegionOrCallingCode(n, (String)object2);
        StringBuilder stringBuilder = new StringBuilder(20);
        Object object3 = this.chooseFormattingPatternForNumber((List<Phonemetadata.NumberFormat>)object, string);
        if (object3 == null) {
            stringBuilder.append(string);
        } else {
            object = Phonemetadata.NumberFormat.newBuilder();
            ((Phonemetadata.NumberFormat.Builder)object).mergeFrom((Phonemetadata.NumberFormat)object3);
            object3 = ((Phonemetadata.NumberFormat)object3).getNationalPrefixFormattingRule();
            if (((String)object3).length() > 0) {
                String string2 = ((Phonemetadata.PhoneMetadata)object2).getNationalPrefix();
                if (string2.length() > 0) {
                    ((Phonemetadata.NumberFormat)object).setNationalPrefixFormattingRule(((String)object3).replace(NP_STRING, string2).replace(FG_STRING, "$1"));
                } else {
                    ((Phonemetadata.NumberFormat)object).clearNationalPrefixFormattingRule();
                }
            }
            stringBuilder.append(this.formatNsnUsingPattern(string, (Phonemetadata.NumberFormat)object, phoneNumberFormat));
        }
        this.maybeAppendFormattedExtension(phoneNumber, (Phonemetadata.PhoneMetadata)object2, phoneNumberFormat, stringBuilder);
        this.prefixNumberWithCountryCallingCode(n, phoneNumberFormat, stringBuilder);
        return stringBuilder.toString();
    }

    @UnsupportedAppUsage
    public String formatInOriginalFormat(Phonenumber.PhoneNumber object, String object2) {
        Serializable serializable;
        if (((Phonenumber.PhoneNumber)object).hasRawInput() && !this.hasFormattingPatternForNumber((Phonenumber.PhoneNumber)object)) {
            return ((Phonenumber.PhoneNumber)object).getRawInput();
        }
        if (!((Phonenumber.PhoneNumber)object).hasCountryCodeSource()) {
            return this.format((Phonenumber.PhoneNumber)object, PhoneNumberFormat.NATIONAL);
        }
        int n = 2.$SwitchMap$com$android$i18n$phonenumbers$Phonenumber$PhoneNumber$CountryCodeSource[((Phonenumber.PhoneNumber)object).getCountryCodeSource().ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    Object object3 = this.getRegionCodeForCountryCode(((Phonenumber.PhoneNumber)object).getCountryCode());
                    serializable = this.getNddPrefixForRegion((String)object3, true);
                    object2 = this.format((Phonenumber.PhoneNumber)object, PhoneNumberFormat.NATIONAL);
                    if (serializable != null && ((String)((Object)serializable)).length() != 0 && !this.rawInputContainsNationalPrefix(((Phonenumber.PhoneNumber)object).getRawInput(), (String)((Object)serializable), (String)object3)) {
                        object3 = this.getMetadataForRegion((String)object3);
                        serializable = this.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
                        serializable = this.chooseFormattingPatternForNumber(((Phonemetadata.PhoneMetadata)object3).numberFormats(), (String)((Object)serializable));
                        if (serializable != null && (n = ((String)(object3 = ((Phonemetadata.NumberFormat)serializable).getNationalPrefixFormattingRule())).indexOf("$1")) > 0 && PhoneNumberUtil.normalizeDigitsOnly(((String)object3).substring(0, n)).length() != 0) {
                            object2 = Phonemetadata.NumberFormat.newBuilder();
                            ((Phonemetadata.NumberFormat.Builder)object2).mergeFrom((Phonemetadata.NumberFormat)serializable);
                            ((Phonemetadata.NumberFormat)object2).clearNationalPrefixFormattingRule();
                            serializable = new ArrayList<Phonemetadata.NumberFormat>(1);
                            serializable.add((Phonemetadata.NumberFormat)object2);
                            object2 = this.formatByPattern((Phonenumber.PhoneNumber)object, PhoneNumberFormat.NATIONAL, (List<Phonemetadata.NumberFormat>)((Object)serializable));
                        }
                    }
                } else {
                    object2 = this.format((Phonenumber.PhoneNumber)object, PhoneNumberFormat.INTERNATIONAL).substring(1);
                }
            } else {
                object2 = this.formatOutOfCountryCallingNumber((Phonenumber.PhoneNumber)object, (String)object2);
            }
        } else {
            object2 = this.format((Phonenumber.PhoneNumber)object, PhoneNumberFormat.INTERNATIONAL);
        }
        serializable = ((Phonenumber.PhoneNumber)object).getRawInput();
        object = object2;
        if (object2 != null) {
            object = object2;
            if (((String)((Object)serializable)).length() > 0) {
                object = object2;
                if (!PhoneNumberUtil.normalizeDiallableCharsOnly((CharSequence)object2).equals(PhoneNumberUtil.normalizeDiallableCharsOnly((CharSequence)((Object)serializable)))) {
                    object = serializable;
                }
            }
        }
        return object;
    }

    public String formatNationalNumberWithCarrierCode(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence) {
        int n = phoneNumber.getCountryCode();
        String string = this.getNationalSignificantNumber(phoneNumber);
        if (!this.hasValidCountryCallingCode(n)) {
            return string;
        }
        Phonemetadata.PhoneMetadata phoneMetadata = this.getMetadataForRegionOrCallingCode(n, this.getRegionCodeForCountryCode(n));
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append(this.formatNsn(string, phoneMetadata, PhoneNumberFormat.NATIONAL, charSequence));
        this.maybeAppendFormattedExtension(phoneNumber, phoneMetadata, PhoneNumberFormat.NATIONAL, stringBuilder);
        this.prefixNumberWithCountryCallingCode(n, PhoneNumberFormat.NATIONAL, stringBuilder);
        return stringBuilder.toString();
    }

    public String formatNationalNumberWithPreferredCarrierCode(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence) {
        block0 : {
            if (phoneNumber.getPreferredDomesticCarrierCode().length() <= 0) break block0;
            charSequence = phoneNumber.getPreferredDomesticCarrierCode();
        }
        return this.formatNationalNumberWithCarrierCode(phoneNumber, charSequence);
    }

    String formatNsnUsingPattern(String string, Phonemetadata.NumberFormat numberFormat, PhoneNumberFormat phoneNumberFormat) {
        return this.formatNsnUsingPattern(string, numberFormat, phoneNumberFormat, null);
    }

    public String formatNumberForMobileDialing(Phonenumber.PhoneNumber object, String charSequence, boolean bl) {
        int n = ((Phonenumber.PhoneNumber)object).getCountryCode();
        boolean bl2 = this.hasValidCountryCallingCode(n);
        String string = "";
        if (!bl2) {
            if (((Phonenumber.PhoneNumber)object).hasRawInput()) {
                string = ((Phonenumber.PhoneNumber)object).getRawInput();
            }
            return string;
        }
        String string2 = "";
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber().mergeFrom((Phonenumber.PhoneNumber)object).clearExtension();
        object = this.getRegionCodeForCountryCode(n);
        PhoneNumberType phoneNumberType = this.getNumberType(phoneNumber);
        PhoneNumberType phoneNumberType2 = PhoneNumberType.UNKNOWN;
        boolean bl3 = false;
        boolean bl4 = phoneNumberType != phoneNumberType2;
        if (((String)charSequence).equals(object)) {
            if (phoneNumberType == PhoneNumberType.FIXED_LINE || phoneNumberType == PhoneNumberType.MOBILE || phoneNumberType == PhoneNumberType.FIXED_LINE_OR_MOBILE) {
                bl3 = true;
            }
            if (((String)object).equals("CO") && phoneNumberType == PhoneNumberType.FIXED_LINE) {
                object = this.formatNationalNumberWithCarrierCode(phoneNumber, COLOMBIA_MOBILE_TO_FIXED_LINE_PREFIX);
            } else if (((String)object).equals("BR") && bl3) {
                object = phoneNumber.getPreferredDomesticCarrierCode().length() > 0 ? this.formatNationalNumberWithPreferredCarrierCode(phoneNumber, "") : string;
            } else if (bl4 && ((String)object).equals("HU")) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.getNddPrefixForRegion((String)object, true));
                ((StringBuilder)charSequence).append(" ");
                ((StringBuilder)charSequence).append(this.format(phoneNumber, PhoneNumberFormat.NATIONAL));
                object = ((StringBuilder)charSequence).toString();
            } else if (n == 1) {
                object = this.getMetadataForRegion((String)charSequence);
                object = this.canBeInternationallyDialled(phoneNumber) && this.testNumberLength(this.getNationalSignificantNumber(phoneNumber), (Phonemetadata.PhoneMetadata)object) != ValidationResult.TOO_SHORT ? this.format(phoneNumber, PhoneNumberFormat.INTERNATIONAL) : this.format(phoneNumber, PhoneNumberFormat.NATIONAL);
            } else {
                object = (((String)object).equals(REGION_CODE_FOR_NON_GEO_ENTITY) || (((String)object).equals("MX") || ((String)object).equals("CL") || ((String)object).equals("UZ")) && bl3) && this.canBeInternationallyDialled(phoneNumber) ? this.format(phoneNumber, PhoneNumberFormat.INTERNATIONAL) : this.format(phoneNumber, PhoneNumberFormat.NATIONAL);
            }
        } else {
            object = string2;
            if (bl4) {
                object = string2;
                if (this.canBeInternationallyDialled(phoneNumber)) {
                    object = bl ? this.format(phoneNumber, PhoneNumberFormat.INTERNATIONAL) : this.format(phoneNumber, PhoneNumberFormat.E164);
                    return object;
                }
            }
        }
        if (!bl) {
            object = PhoneNumberUtil.normalizeDiallableCharsOnly((CharSequence)object);
        }
        return object;
    }

    public String formatOutOfCountryCallingNumber(Phonenumber.PhoneNumber phoneNumber, String charSequence) {
        if (!this.isValidRegionCode((String)charSequence)) {
            Logger logger = PhoneNumberUtil.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Trying to format number from invalid region ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(". International formatting applied.");
            logger.log(level, stringBuilder.toString());
            return this.format(phoneNumber, PhoneNumberFormat.INTERNATIONAL);
        }
        int n = phoneNumber.getCountryCode();
        CharSequence charSequence2 = this.getNationalSignificantNumber(phoneNumber);
        if (!this.hasValidCountryCallingCode(n)) {
            return charSequence2;
        }
        if (n == 1) {
            if (this.isNANPACountry((String)charSequence)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(" ");
                ((StringBuilder)charSequence).append(this.format(phoneNumber, PhoneNumberFormat.NATIONAL));
                return ((StringBuilder)charSequence).toString();
            }
        } else if (n == this.getCountryCodeForValidRegion((String)charSequence)) {
            return this.format(phoneNumber, PhoneNumberFormat.NATIONAL);
        }
        Phonemetadata.PhoneMetadata phoneMetadata = this.getMetadataForRegion((String)charSequence);
        Object object = phoneMetadata.getInternationalPrefix();
        charSequence = "";
        if (SINGLE_INTERNATIONAL_PREFIX.matcher((CharSequence)object).matches()) {
            charSequence = object;
        } else if (phoneMetadata.hasPreferredInternationalPrefix()) {
            charSequence = phoneMetadata.getPreferredInternationalPrefix();
        }
        object = this.getRegionCodeForCountryCode(n);
        object = this.getMetadataForRegionOrCallingCode(n, (String)object);
        charSequence2 = new StringBuilder(this.formatNsn((String)charSequence2, (Phonemetadata.PhoneMetadata)object, PhoneNumberFormat.INTERNATIONAL));
        this.maybeAppendFormattedExtension(phoneNumber, (Phonemetadata.PhoneMetadata)object, PhoneNumberFormat.INTERNATIONAL, (StringBuilder)charSequence2);
        if (((String)charSequence).length() > 0) {
            ((StringBuilder)charSequence2).insert(0, " ").insert(0, n).insert(0, " ").insert(0, (String)charSequence);
        } else {
            this.prefixNumberWithCountryCallingCode(n, PhoneNumberFormat.INTERNATIONAL, (StringBuilder)charSequence2);
        }
        return ((StringBuilder)charSequence2).toString();
    }

    public String formatOutOfCountryKeepingAlphaChars(Phonenumber.PhoneNumber object, String object2) {
        Object object3 = ((Phonenumber.PhoneNumber)object).getRawInput();
        if (((String)object3).length() == 0) {
            return this.formatOutOfCountryCallingNumber((Phonenumber.PhoneNumber)object, (String)object2);
        }
        int n = ((Phonenumber.PhoneNumber)object).getCountryCode();
        if (!this.hasValidCountryCallingCode(n)) {
            return object3;
        }
        object3 = PhoneNumberUtil.normalizeHelper((CharSequence)object3, ALL_PLUS_NUMBER_GROUPING_SYMBOLS, true);
        String string = this.getNationalSignificantNumber((Phonenumber.PhoneNumber)object);
        CharSequence charSequence = object3;
        if (string.length() > 3) {
            int n2 = ((String)object3).indexOf(string.substring(0, 3));
            charSequence = object3;
            if (n2 != -1) {
                charSequence = ((String)object3).substring(n2);
            }
        }
        Serializable serializable = this.getMetadataForRegion((String)object2);
        if (n == 1) {
            if (this.isNANPACountry((String)object2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append((String)charSequence);
                return ((StringBuilder)object).toString();
            }
        } else if (serializable != null && n == this.getCountryCodeForValidRegion((String)object2)) {
            object = this.chooseFormattingPatternForNumber(((Phonemetadata.PhoneMetadata)serializable).numberFormats(), string);
            if (object == null) {
                return charSequence;
            }
            object2 = Phonemetadata.NumberFormat.newBuilder();
            ((Phonemetadata.NumberFormat.Builder)object2).mergeFrom((Phonemetadata.NumberFormat)object);
            ((Phonemetadata.NumberFormat)object2).setPattern("(\\d+)(.*)");
            ((Phonemetadata.NumberFormat)object2).setFormat("$1$2");
            return this.formatNsnUsingPattern((String)charSequence, (Phonemetadata.NumberFormat)object2, PhoneNumberFormat.NATIONAL);
        }
        object3 = "";
        if (serializable != null && !SINGLE_INTERNATIONAL_PREFIX.matcher((CharSequence)(object3 = ((Phonemetadata.PhoneMetadata)serializable).getInternationalPrefix())).matches()) {
            object3 = ((Phonemetadata.PhoneMetadata)serializable).getPreferredInternationalPrefix();
        }
        charSequence = new StringBuilder((String)charSequence);
        this.maybeAppendFormattedExtension((Phonenumber.PhoneNumber)object, this.getMetadataForRegionOrCallingCode(n, this.getRegionCodeForCountryCode(n)), PhoneNumberFormat.INTERNATIONAL, (StringBuilder)charSequence);
        if (((String)object3).length() > 0) {
            ((StringBuilder)charSequence).insert(0, " ").insert(0, n).insert(0, " ").insert(0, (String)object3);
        } else {
            if (!this.isValidRegionCode((String)object2)) {
                object = logger;
                object3 = Level.WARNING;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Trying to format number from invalid region ");
                ((StringBuilder)serializable).append((String)object2);
                ((StringBuilder)serializable).append(". International formatting applied.");
                ((Logger)object).log((Level)object3, ((StringBuilder)serializable).toString());
            }
            this.prefixNumberWithCountryCallingCode(n, PhoneNumberFormat.INTERNATIONAL, (StringBuilder)charSequence);
        }
        return ((StringBuilder)charSequence).toString();
    }

    @UnsupportedAppUsage
    public AsYouTypeFormatter getAsYouTypeFormatter(String string) {
        return new AsYouTypeFormatter(string);
    }

    @UnsupportedAppUsage
    public int getCountryCodeForRegion(String string) {
        if (!this.isValidRegionCode(string)) {
            Logger logger = PhoneNumberUtil.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid or missing region code (");
            if (string == null) {
                string = "null";
            }
            stringBuilder.append(string);
            stringBuilder.append(") provided.");
            logger.log(level, stringBuilder.toString());
            return 0;
        }
        return this.getCountryCodeForValidRegion(string);
    }

    public Phonenumber.PhoneNumber getExampleNumber(String string) {
        return this.getExampleNumberForType(string, PhoneNumberType.FIXED_LINE);
    }

    public Phonenumber.PhoneNumber getExampleNumberForNonGeoEntity(int n) {
        Object object = this.getMetadataForNonGeographicalRegion(n);
        if (object != null) {
            for (Serializable serializable : Arrays.asList(((Phonemetadata.PhoneMetadata)object).getMobile(), ((Phonemetadata.PhoneMetadata)object).getTollFree(), ((Phonemetadata.PhoneMetadata)object).getSharedCost(), ((Phonemetadata.PhoneMetadata)object).getVoip(), ((Phonemetadata.PhoneMetadata)object).getVoicemail(), ((Phonemetadata.PhoneMetadata)object).getUan(), ((Phonemetadata.PhoneMetadata)object).getPremiumRate())) {
                if (serializable == null) continue;
                try {
                    if (!serializable.hasExampleNumber()) continue;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("+");
                    stringBuilder.append(n);
                    stringBuilder.append(serializable.getExampleNumber());
                    serializable = this.parse(stringBuilder.toString(), UNKNOWN_REGION);
                    return serializable;
                }
                catch (NumberParseException numberParseException) {
                    logger.log(Level.SEVERE, numberParseException.toString());
                }
            }
        } else {
            Logger logger = PhoneNumberUtil.logger;
            object = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid or unknown country calling code provided: ");
            stringBuilder.append(n);
            logger.log((Level)object, stringBuilder.toString());
        }
        return null;
    }

    public Phonenumber.PhoneNumber getExampleNumberForType(PhoneNumberType phoneNumberType) {
        Object object;
        Object object2 = this.getSupportedRegions().iterator();
        while (object2.hasNext()) {
            object = this.getExampleNumberForType(object2.next(), phoneNumberType);
            if (object == null) continue;
            return object;
        }
        object = this.getSupportedGlobalNetworkCallingCodes().iterator();
        while (object.hasNext()) {
            int n = (Integer)object.next();
            object2 = this.getNumberDescByType(this.getMetadataForNonGeographicalRegion(n), phoneNumberType);
            try {
                if (!((Phonemetadata.PhoneNumberDesc)object2).hasExampleNumber()) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("+");
                stringBuilder.append(n);
                stringBuilder.append(((Phonemetadata.PhoneNumberDesc)object2).getExampleNumber());
                object2 = this.parse(stringBuilder.toString(), UNKNOWN_REGION);
                return object2;
            }
            catch (NumberParseException numberParseException) {
                logger.log(Level.SEVERE, numberParseException.toString());
            }
        }
        return null;
    }

    public Phonenumber.PhoneNumber getExampleNumberForType(String object, PhoneNumberType object2) {
        if (!this.isValidRegionCode((String)object)) {
            Logger logger = PhoneNumberUtil.logger;
            Level level = Level.WARNING;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Invalid or unknown region code provided: ");
            ((StringBuilder)object2).append((String)object);
            logger.log(level, ((StringBuilder)object2).toString());
            return null;
        }
        object2 = this.getNumberDescByType(this.getMetadataForRegion((String)object), (PhoneNumberType)((Object)object2));
        try {
            if (((Phonemetadata.PhoneNumberDesc)object2).hasExampleNumber()) {
                object = this.parse(((Phonemetadata.PhoneNumberDesc)object2).getExampleNumber(), (String)object);
                return object;
            }
        }
        catch (NumberParseException numberParseException) {
            logger.log(Level.SEVERE, numberParseException.toString());
        }
        return null;
    }

    public Phonenumber.PhoneNumber getInvalidExampleNumber(String string) {
        if (!this.isValidRegionCode(string)) {
            Logger logger = PhoneNumberUtil.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid or unknown region code provided: ");
            stringBuilder.append(string);
            logger.log(level, stringBuilder.toString());
            return null;
        }
        Object object = this.getNumberDescByType(this.getMetadataForRegion(string), PhoneNumberType.FIXED_LINE);
        if (!((Phonemetadata.PhoneNumberDesc)object).hasExampleNumber()) {
            return null;
        }
        object = ((Phonemetadata.PhoneNumberDesc)object).getExampleNumber();
        for (int i = object.length() - 1; i >= 2; --i) {
            Object object2 = ((String)object).substring(0, i);
            try {
                object2 = this.parse((CharSequence)object2, string);
                boolean bl = this.isValidNumber((Phonenumber.PhoneNumber)object2);
                if (bl) continue;
                return object2;
            }
            catch (NumberParseException numberParseException) {
                // empty catch block
            }
        }
        return null;
    }

    public int getLengthOfGeographicalAreaCode(Phonenumber.PhoneNumber phoneNumber) {
        Object object = this.getMetadataForRegion(this.getRegionCodeForNumber(phoneNumber));
        if (object == null) {
            return 0;
        }
        if (!object.hasNationalPrefix() && !phoneNumber.isItalianLeadingZero()) {
            return 0;
        }
        object = this.getNumberType(phoneNumber);
        int n = phoneNumber.getCountryCode();
        if (object == PhoneNumberType.MOBILE && GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES.contains(n)) {
            return 0;
        }
        if (!this.isNumberGeographical((PhoneNumberType)((Object)object), n)) {
            return 0;
        }
        return this.getLengthOfNationalDestinationCode(phoneNumber);
    }

    public int getLengthOfNationalDestinationCode(Phonenumber.PhoneNumber phoneNumber) {
        String[] arrstring;
        if (phoneNumber.hasExtension()) {
            arrstring = new Phonenumber.PhoneNumber();
            arrstring.mergeFrom(phoneNumber);
            arrstring.clearExtension();
        } else {
            arrstring = phoneNumber;
        }
        arrstring = this.format((Phonenumber.PhoneNumber)arrstring, PhoneNumberFormat.INTERNATIONAL);
        arrstring = NON_DIGITS_PATTERN.split((CharSequence)arrstring);
        if (arrstring.length <= 3) {
            return 0;
        }
        if (this.getNumberType(phoneNumber) == PhoneNumberType.MOBILE && !PhoneNumberUtil.getCountryMobileToken(phoneNumber.getCountryCode()).equals("")) {
            return arrstring[2].length() + arrstring[3].length();
        }
        return arrstring[2].length();
    }

    Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(int n) {
        if (!this.countryCallingCodeToRegionCodeMap.containsKey(n)) {
            return null;
        }
        return this.metadataSource.getMetadataForNonGeographicalRegion(n);
    }

    Phonemetadata.PhoneMetadata getMetadataForRegion(String string) {
        if (!this.isValidRegionCode(string)) {
            return null;
        }
        return this.metadataSource.getMetadataForRegion(string);
    }

    @UnsupportedAppUsage
    public String getNationalSignificantNumber(Phonenumber.PhoneNumber phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        if (phoneNumber.isItalianLeadingZero() && phoneNumber.getNumberOfLeadingZeros() > 0) {
            char[] arrc = new char[phoneNumber.getNumberOfLeadingZeros()];
            Arrays.fill(arrc, '0');
            stringBuilder.append(new String(arrc));
        }
        stringBuilder.append(phoneNumber.getNationalNumber());
        return stringBuilder.toString();
    }

    public String getNddPrefixForRegion(String object, boolean bl) {
        Object object2 = this.getMetadataForRegion((String)object);
        if (object2 == null) {
            Logger logger = PhoneNumberUtil.logger;
            object2 = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid or missing region code (");
            if (object == null) {
                object = "null";
            }
            stringBuilder.append((String)object);
            stringBuilder.append(") provided.");
            logger.log((Level)object2, stringBuilder.toString());
            return null;
        }
        if (((String)(object2 = ((Phonemetadata.PhoneMetadata)object2).getNationalPrefix())).length() == 0) {
            return null;
        }
        object = object2;
        if (bl) {
            object = ((String)object2).replace("~", "");
        }
        return object;
    }

    Phonemetadata.PhoneNumberDesc getNumberDescByType(Phonemetadata.PhoneMetadata phoneMetadata, PhoneNumberType phoneNumberType) {
        switch (phoneNumberType) {
            default: {
                return phoneMetadata.getGeneralDesc();
            }
            case VOICEMAIL: {
                return phoneMetadata.getVoicemail();
            }
            case UAN: {
                return phoneMetadata.getUan();
            }
            case PAGER: {
                return phoneMetadata.getPager();
            }
            case PERSONAL_NUMBER: {
                return phoneMetadata.getPersonalNumber();
            }
            case VOIP: {
                return phoneMetadata.getVoip();
            }
            case SHARED_COST: {
                return phoneMetadata.getSharedCost();
            }
            case FIXED_LINE: 
            case FIXED_LINE_OR_MOBILE: {
                return phoneMetadata.getFixedLine();
            }
            case MOBILE: {
                return phoneMetadata.getMobile();
            }
            case TOLL_FREE: {
                return phoneMetadata.getTollFree();
            }
            case PREMIUM_RATE: 
        }
        return phoneMetadata.getPremiumRate();
    }

    @UnsupportedAppUsage
    public PhoneNumberType getNumberType(Phonenumber.PhoneNumber phoneNumber) {
        Object object = this.getRegionCodeForNumber(phoneNumber);
        object = this.getMetadataForRegionOrCallingCode(phoneNumber.getCountryCode(), (String)object);
        if (object == null) {
            return PhoneNumberType.UNKNOWN;
        }
        return this.getNumberTypeHelper(this.getNationalSignificantNumber(phoneNumber), (Phonemetadata.PhoneMetadata)object);
    }

    public String getRegionCodeForCountryCode(int n) {
        List<String> list = this.countryCallingCodeToRegionCodeMap.get(n);
        list = list == null ? UNKNOWN_REGION : list.get(0);
        return list;
    }

    @UnsupportedAppUsage
    public String getRegionCodeForNumber(Phonenumber.PhoneNumber object) {
        int n = ((Phonenumber.PhoneNumber)object).getCountryCode();
        List<String> list = this.countryCallingCodeToRegionCodeMap.get(n);
        if (list == null) {
            object = logger;
            list = Level.INFO;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Missing/invalid country_code (");
            stringBuilder.append(n);
            stringBuilder.append(")");
            ((Logger)object).log((Level)((Object)list), stringBuilder.toString());
            return null;
        }
        if (list.size() == 1) {
            return (String)list.get(0);
        }
        return this.getRegionCodeForNumberFromRegionList((Phonenumber.PhoneNumber)object, list);
    }

    public List<String> getRegionCodesForCountryCode(int n) {
        List<String> list;
        block0 : {
            list = this.countryCallingCodeToRegionCodeMap.get(n);
            if (list != null) break block0;
            list = new ArrayList<String>(0);
        }
        return Collections.unmodifiableList(list);
    }

    public Set<Integer> getSupportedCallingCodes() {
        return Collections.unmodifiableSet(this.countryCallingCodeToRegionCodeMap.keySet());
    }

    public Set<Integer> getSupportedGlobalNetworkCallingCodes() {
        return Collections.unmodifiableSet(this.countryCodesForNonGeographicalRegion);
    }

    public Set<String> getSupportedRegions() {
        return Collections.unmodifiableSet(this.supportedRegions);
    }

    public Set<PhoneNumberType> getSupportedTypesForNonGeoEntity(int n) {
        Object object = this.getMetadataForNonGeographicalRegion(n);
        if (object == null) {
            object = logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown country calling code for a non-geographical entity provided: ");
            stringBuilder.append(n);
            ((Logger)object).log(level, stringBuilder.toString());
            return Collections.unmodifiableSet(new TreeSet());
        }
        return this.getSupportedTypesForMetadata((Phonemetadata.PhoneMetadata)object);
    }

    public Set<PhoneNumberType> getSupportedTypesForRegion(String string) {
        if (!this.isValidRegionCode(string)) {
            Logger logger = PhoneNumberUtil.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid or unknown region code provided: ");
            stringBuilder.append(string);
            logger.log(level, stringBuilder.toString());
            return Collections.unmodifiableSet(new TreeSet());
        }
        return this.getSupportedTypesForMetadata(this.getMetadataForRegion(string));
    }

    public boolean isAlphaNumber(CharSequence charSequence) {
        if (!PhoneNumberUtil.isViablePhoneNumber(charSequence)) {
            return false;
        }
        charSequence = new StringBuilder(charSequence);
        this.maybeStripExtension((StringBuilder)charSequence);
        return VALID_ALPHA_PHONE_PATTERN.matcher(charSequence).matches();
    }

    public boolean isMobileNumberPortableRegion(String string) {
        Serializable serializable = this.getMetadataForRegion(string);
        if (serializable == null) {
            Logger logger = PhoneNumberUtil.logger;
            Level level = Level.WARNING;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Invalid or unknown region code provided: ");
            ((StringBuilder)serializable).append(string);
            logger.log(level, ((StringBuilder)serializable).toString());
            return false;
        }
        return ((Phonemetadata.PhoneMetadata)serializable).isMobileNumberPortableRegion();
    }

    public boolean isNANPACountry(String string) {
        return this.nanpaRegions.contains(string);
    }

    public boolean isNumberGeographical(PhoneNumberType phoneNumberType, int n) {
        boolean bl = phoneNumberType == PhoneNumberType.FIXED_LINE || phoneNumberType == PhoneNumberType.FIXED_LINE_OR_MOBILE || GEO_MOBILE_COUNTRIES.contains(n) && phoneNumberType == PhoneNumberType.MOBILE;
        return bl;
    }

    public boolean isNumberGeographical(Phonenumber.PhoneNumber phoneNumber) {
        return this.isNumberGeographical(this.getNumberType(phoneNumber), phoneNumber.getCountryCode());
    }

    @UnsupportedAppUsage
    public MatchType isNumberMatch(Phonenumber.PhoneNumber phoneNumber, Phonenumber.PhoneNumber phoneNumber2) {
        phoneNumber = PhoneNumberUtil.copyCoreFieldsOnly(phoneNumber);
        phoneNumber2 = PhoneNumberUtil.copyCoreFieldsOnly(phoneNumber2);
        if (phoneNumber.hasExtension() && phoneNumber2.hasExtension() && !phoneNumber.getExtension().equals(phoneNumber2.getExtension())) {
            return MatchType.NO_MATCH;
        }
        int n = phoneNumber.getCountryCode();
        int n2 = phoneNumber2.getCountryCode();
        if (n != 0 && n2 != 0) {
            if (phoneNumber.exactlySameAs(phoneNumber2)) {
                return MatchType.EXACT_MATCH;
            }
            if (n == n2 && this.isNationalNumberSuffixOfTheOther(phoneNumber, phoneNumber2)) {
                return MatchType.SHORT_NSN_MATCH;
            }
            return MatchType.NO_MATCH;
        }
        phoneNumber.setCountryCode(n2);
        if (phoneNumber.exactlySameAs(phoneNumber2)) {
            return MatchType.NSN_MATCH;
        }
        if (this.isNationalNumberSuffixOfTheOther(phoneNumber, phoneNumber2)) {
            return MatchType.SHORT_NSN_MATCH;
        }
        return MatchType.NO_MATCH;
    }

    public MatchType isNumberMatch(Phonenumber.PhoneNumber object, CharSequence charSequence) {
        try {
            MatchType matchType = this.isNumberMatch((Phonenumber.PhoneNumber)object, this.parse(charSequence, UNKNOWN_REGION));
            return matchType;
        }
        catch (NumberParseException numberParseException) {
            if (numberParseException.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE) {
                Object object2;
                block7 : {
                    object2 = this.getRegionCodeForCountryCode(object.getCountryCode());
                    if (((String)object2).equals(UNKNOWN_REGION)) break block7;
                    if ((object = this.isNumberMatch((Phonenumber.PhoneNumber)object, this.parse(charSequence, (String)object2))) == MatchType.EXACT_MATCH) {
                        return MatchType.NSN_MATCH;
                    }
                    return object;
                }
                try {
                    object2 = new Phonenumber.PhoneNumber();
                    this.parseHelper(charSequence, null, false, false, (Phonenumber.PhoneNumber)object2);
                    object = this.isNumberMatch((Phonenumber.PhoneNumber)object, (Phonenumber.PhoneNumber)object2);
                    return object;
                }
                catch (NumberParseException numberParseException2) {
                    // empty catch block
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }

    public MatchType isNumberMatch(CharSequence object, CharSequence charSequence) {
        try {
            MatchType matchType = this.isNumberMatch(this.parse((CharSequence)object, UNKNOWN_REGION), charSequence);
            return matchType;
        }
        catch (NumberParseException numberParseException) {
            block7 : {
                if (numberParseException.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE) {
                    try {
                        MatchType matchType = this.isNumberMatch(this.parse(charSequence, UNKNOWN_REGION), (CharSequence)object);
                        return matchType;
                    }
                    catch (NumberParseException numberParseException2) {
                        if (numberParseException2.getErrorType() != NumberParseException.ErrorType.INVALID_COUNTRY_CODE) break block7;
                        try {
                            Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
                            Phonenumber.PhoneNumber phoneNumber2 = new Phonenumber.PhoneNumber();
                            this.parseHelper((CharSequence)object, null, false, false, phoneNumber);
                            this.parseHelper(charSequence, null, false, false, phoneNumber2);
                            object = this.isNumberMatch(phoneNumber, phoneNumber2);
                            return object;
                        }
                        catch (NumberParseException numberParseException3) {
                            // empty catch block
                        }
                    }
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }

    boolean isNumberMatchingDesc(String string, Phonemetadata.PhoneNumberDesc phoneNumberDesc) {
        int n = string.length();
        List<Integer> list = phoneNumberDesc.getPossibleLengthList();
        if (list.size() > 0 && !list.contains(n)) {
            return false;
        }
        return this.matcherApi.matchNationalNumber(string, phoneNumberDesc, false);
    }

    @UnsupportedAppUsage
    public boolean isPossibleNumber(Phonenumber.PhoneNumber object) {
        boolean bl = (object = this.isPossibleNumberWithReason((Phonenumber.PhoneNumber)object)) == ValidationResult.IS_POSSIBLE || object == ValidationResult.IS_POSSIBLE_LOCAL_ONLY;
        return bl;
    }

    public boolean isPossibleNumber(CharSequence charSequence, String string) {
        try {
            boolean bl = this.isPossibleNumber(this.parse(charSequence, string));
            return bl;
        }
        catch (NumberParseException numberParseException) {
            return false;
        }
    }

    public boolean isPossibleNumberForType(Phonenumber.PhoneNumber object, PhoneNumberType phoneNumberType) {
        boolean bl = (object = this.isPossibleNumberForTypeWithReason((Phonenumber.PhoneNumber)object, phoneNumberType)) == ValidationResult.IS_POSSIBLE || object == ValidationResult.IS_POSSIBLE_LOCAL_ONLY;
        return bl;
    }

    public ValidationResult isPossibleNumberForTypeWithReason(Phonenumber.PhoneNumber phoneNumber, PhoneNumberType phoneNumberType) {
        String string = this.getNationalSignificantNumber(phoneNumber);
        int n = phoneNumber.getCountryCode();
        if (!this.hasValidCountryCallingCode(n)) {
            return ValidationResult.INVALID_COUNTRY_CODE;
        }
        return this.testNumberLength(string, this.getMetadataForRegionOrCallingCode(n, this.getRegionCodeForCountryCode(n)), phoneNumberType);
    }

    @UnsupportedAppUsage
    public ValidationResult isPossibleNumberWithReason(Phonenumber.PhoneNumber phoneNumber) {
        return this.isPossibleNumberForTypeWithReason(phoneNumber, PhoneNumberType.UNKNOWN);
    }

    @UnsupportedAppUsage
    public boolean isValidNumber(Phonenumber.PhoneNumber phoneNumber) {
        return this.isValidNumberForRegion(phoneNumber, this.getRegionCodeForNumber(phoneNumber));
    }

    public boolean isValidNumberForRegion(Phonenumber.PhoneNumber phoneNumber, String string) {
        int n = phoneNumber.getCountryCode();
        Phonemetadata.PhoneMetadata phoneMetadata = this.getMetadataForRegionOrCallingCode(n, string);
        boolean bl = false;
        if (phoneMetadata != null && (REGION_CODE_FOR_NON_GEO_ENTITY.equals(string) || n == this.getCountryCodeForValidRegion(string))) {
            if (this.getNumberTypeHelper(this.getNationalSignificantNumber(phoneNumber), phoneMetadata) != PhoneNumberType.UNKNOWN) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    int maybeExtractCountryCode(CharSequence object, Phonemetadata.PhoneMetadata phoneMetadata, StringBuilder stringBuilder, boolean bl, Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        if (object.length() == 0) {
            return 0;
        }
        StringBuilder stringBuilder2 = new StringBuilder((CharSequence)object);
        object = "NonMatch";
        if (phoneMetadata != null) {
            object = phoneMetadata.getInternationalPrefix();
        }
        object = this.maybeStripInternationalPrefixAndNormalize(stringBuilder2, (String)object);
        if (bl) {
            phoneNumber.setCountryCodeSource((Phonenumber.PhoneNumber.CountryCodeSource)((Object)object));
        }
        if (object != Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            if (stringBuilder2.length() > 2) {
                int n = this.extractCountryCode(stringBuilder2, stringBuilder);
                if (n != 0) {
                    phoneNumber.setCountryCode(n);
                    return n;
                }
                throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Country calling code supplied was not recognised.");
            }
            throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_AFTER_IDD, "Phone number had an IDD, but after this was not long enough to be a viable phone number.");
        }
        if (phoneMetadata != null) {
            int n = phoneMetadata.getCountryCode();
            object = String.valueOf(n);
            Object object2 = stringBuilder2.toString();
            if (((String)object2).startsWith((String)object)) {
                object = new StringBuilder(((String)object2).substring(((String)object).length()));
                object2 = phoneMetadata.getGeneralDesc();
                this.maybeStripNationalPrefixAndCarrierCode((StringBuilder)object, phoneMetadata, null);
                if (!this.matcherApi.matchNationalNumber(stringBuilder2, (Phonemetadata.PhoneNumberDesc)object2, false) && this.matcherApi.matchNationalNumber((CharSequence)object, (Phonemetadata.PhoneNumberDesc)object2, false) || this.testNumberLength(stringBuilder2, phoneMetadata) == ValidationResult.TOO_LONG) {
                    stringBuilder.append((CharSequence)object);
                    if (bl) {
                        phoneNumber.setCountryCodeSource(Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN);
                    }
                    phoneNumber.setCountryCode(n);
                    return n;
                }
            }
        }
        phoneNumber.setCountryCode(0);
        return 0;
    }

    String maybeStripExtension(StringBuilder stringBuilder) {
        Matcher matcher = EXTN_PATTERN.matcher(stringBuilder);
        if (matcher.find() && PhoneNumberUtil.isViablePhoneNumber(stringBuilder.substring(0, matcher.start()))) {
            int n = matcher.groupCount();
            for (int i = 1; i <= n; ++i) {
                if (matcher.group(i) == null) continue;
                String string = matcher.group(i);
                stringBuilder.delete(matcher.start(), stringBuilder.length());
                return string;
            }
        }
        return "";
    }

    Phonenumber.PhoneNumber.CountryCodeSource maybeStripInternationalPrefixAndNormalize(StringBuilder object, String object2) {
        if (object.length() == 0) {
            return Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY;
        }
        Matcher matcher = PLUS_CHARS_PATTERN.matcher((CharSequence)object);
        if (matcher.lookingAt()) {
            object.delete(0, matcher.end());
            PhoneNumberUtil.normalize(object);
            return Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
        }
        object2 = this.regexCache.getPatternForRegex((String)object2);
        PhoneNumberUtil.normalize(object);
        object = this.parsePrefixAsIdd((Pattern)object2, (StringBuilder)object) ? Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_IDD : Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY;
        return object;
    }

    boolean maybeStripNationalPrefixAndCarrierCode(StringBuilder stringBuilder, Phonemetadata.PhoneMetadata object, StringBuilder stringBuilder2) {
        int n = stringBuilder.length();
        Object object2 = ((Phonemetadata.PhoneMetadata)object).getNationalPrefixForParsing();
        if (n != 0 && ((String)object2).length() != 0) {
            if (((Matcher)(object2 = this.regexCache.getPatternForRegex((String)object2).matcher(stringBuilder))).lookingAt()) {
                Phonemetadata.PhoneNumberDesc phoneNumberDesc = ((Phonemetadata.PhoneMetadata)object).getGeneralDesc();
                boolean bl = this.matcherApi.matchNationalNumber(stringBuilder, phoneNumberDesc, false);
                int n2 = ((Matcher)object2).groupCount();
                if ((object = ((Phonemetadata.PhoneMetadata)object).getNationalPrefixTransformRule()) != null && ((String)object).length() != 0 && ((Matcher)object2).group(n2) != null) {
                    StringBuilder stringBuilder3 = new StringBuilder(stringBuilder);
                    stringBuilder3.replace(0, n, ((Matcher)object2).replaceFirst((String)object));
                    if (bl && !this.matcherApi.matchNationalNumber(stringBuilder3.toString(), phoneNumberDesc, false)) {
                        return false;
                    }
                    if (stringBuilder2 != null && n2 > 1) {
                        stringBuilder2.append(((Matcher)object2).group(1));
                    }
                    stringBuilder.replace(0, stringBuilder.length(), stringBuilder3.toString());
                    return true;
                }
                if (bl && !this.matcherApi.matchNationalNumber(stringBuilder.substring(((Matcher)object2).end()), phoneNumberDesc, false)) {
                    return false;
                }
                if (stringBuilder2 != null && n2 > 0 && ((Matcher)object2).group(n2) != null) {
                    stringBuilder2.append(((Matcher)object2).group(1));
                }
                stringBuilder.delete(0, ((Matcher)object2).end());
                return true;
            }
            return false;
        }
        return false;
    }

    public Phonenumber.PhoneNumber parse(CharSequence charSequence, String string) throws NumberParseException {
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        this.parse(charSequence, string, phoneNumber);
        return phoneNumber;
    }

    public void parse(CharSequence charSequence, String string, Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        this.parseHelper(charSequence, string, false, true, phoneNumber);
    }

    public Phonenumber.PhoneNumber parseAndKeepRawInput(CharSequence charSequence, String string) throws NumberParseException {
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        this.parseAndKeepRawInput(charSequence, string, phoneNumber);
        return phoneNumber;
    }

    public void parseAndKeepRawInput(CharSequence charSequence, String string, Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        this.parseHelper(charSequence, string, true, true, phoneNumber);
    }

    public boolean truncateTooLongNumber(Phonenumber.PhoneNumber phoneNumber) {
        block2 : {
            long l;
            if (this.isValidNumber(phoneNumber)) {
                return true;
            }
            Phonenumber.PhoneNumber phoneNumber2 = new Phonenumber.PhoneNumber();
            phoneNumber2.mergeFrom(phoneNumber);
            long l2 = phoneNumber.getNationalNumber();
            do {
                l = l2 / 10L;
                phoneNumber2.setNationalNumber(l);
                if (this.isPossibleNumberWithReason(phoneNumber2) == ValidationResult.TOO_SHORT || l == 0L) break block2;
                l2 = l;
            } while (!this.isValidNumber(phoneNumber2));
            phoneNumber.setNationalNumber(l);
            return true;
        }
        return false;
    }

    public static enum Leniency {
        POSSIBLE{

            @Override
            boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil) {
                return phoneNumberUtil.isPossibleNumber(phoneNumber);
            }
        }
        ,
        VALID{

            @Override
            boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil) {
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, charSequence.toString(), phoneNumberUtil)) {
                    return PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil);
                }
                return false;
            }
        }
        ,
        STRICT_GROUPING{

            @Override
            boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil) {
                String string = charSequence.toString();
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, string, phoneNumberUtil) && !PhoneNumberMatcher.containsMoreThanOneSlashInNationalNumber(phoneNumber, string) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil)) {
                    return PhoneNumberMatcher.checkNumberGroupingIsValid(phoneNumber, charSequence, phoneNumberUtil, new PhoneNumberMatcher.NumberGroupingChecker(){

                        @Override
                        public boolean checkGroups(PhoneNumberUtil phoneNumberUtil, Phonenumber.PhoneNumber phoneNumber, StringBuilder stringBuilder, String[] arrstring) {
                            return PhoneNumberMatcher.allNumberGroupsRemainGrouped(phoneNumberUtil, phoneNumber, stringBuilder, arrstring);
                        }
                    });
                }
                return false;
            }

        }
        ,
        EXACT_GROUPING{

            @Override
            boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil) {
                String string = charSequence.toString();
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, string, phoneNumberUtil) && !PhoneNumberMatcher.containsMoreThanOneSlashInNationalNumber(phoneNumber, string) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil)) {
                    return PhoneNumberMatcher.checkNumberGroupingIsValid(phoneNumber, charSequence, phoneNumberUtil, new PhoneNumberMatcher.NumberGroupingChecker(){

                        @Override
                        public boolean checkGroups(PhoneNumberUtil phoneNumberUtil, Phonenumber.PhoneNumber phoneNumber, StringBuilder stringBuilder, String[] arrstring) {
                            return PhoneNumberMatcher.allNumberGroupsAreExactlyPresent(phoneNumberUtil, phoneNumber, stringBuilder, arrstring);
                        }
                    });
                }
                return false;
            }

        };
        

        abstract boolean verify(Phonenumber.PhoneNumber var1, CharSequence var2, PhoneNumberUtil var3);

    }

    @UnsupportedAppUsage(implicitMember="values()[Lcom/android/i18n/phonenumbers/PhoneNumberUtil$MatchType;")
    public static enum MatchType {
        NOT_A_NUMBER,
        NO_MATCH,
        SHORT_NSN_MATCH,
        NSN_MATCH,
        EXACT_MATCH;
        
    }

    @UnsupportedAppUsage(implicitMember="values()[Lcom/android/i18n/phonenumbers/PhoneNumberUtil$PhoneNumberFormat;")
    public static enum PhoneNumberFormat {
        E164,
        INTERNATIONAL,
        NATIONAL,
        RFC3966;
        
    }

    @UnsupportedAppUsage(implicitMember="values()[Lcom/android/i18n/phonenumbers/PhoneNumberUtil$PhoneNumberType;")
    public static enum PhoneNumberType {
        FIXED_LINE,
        MOBILE,
        FIXED_LINE_OR_MOBILE,
        TOLL_FREE,
        PREMIUM_RATE,
        SHARED_COST,
        VOIP,
        PERSONAL_NUMBER,
        PAGER,
        UAN,
        VOICEMAIL,
        UNKNOWN;
        
    }

    public static enum ValidationResult {
        IS_POSSIBLE,
        IS_POSSIBLE_LOCAL_ONLY,
        INVALID_COUNTRY_CODE,
        TOO_SHORT,
        INVALID_LENGTH,
        TOO_LONG;
        
    }

}

