/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonemetadata;
import com.android.i18n.phonenumbers.internal.RegexCache;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsYouTypeFormatter {
    private static final Pattern DIGIT_PATTERN;
    private static final String DIGIT_PLACEHOLDER = "\u2008";
    private static final Pattern ELIGIBLE_FORMAT_PATTERN;
    private static final Phonemetadata.PhoneMetadata EMPTY_METADATA;
    private static final int MIN_LEADING_DIGITS_LENGTH = 3;
    private static final Pattern NATIONAL_PREFIX_SEPARATORS_PATTERN;
    private static final char SEPARATOR_BEFORE_NATIONAL_NUMBER = ' ';
    private boolean ableToFormat = true;
    private StringBuilder accruedInput = new StringBuilder();
    private StringBuilder accruedInputWithoutFormatting = new StringBuilder();
    private String currentFormattingPattern = "";
    private Phonemetadata.PhoneMetadata currentMetadata;
    private String currentOutput = "";
    private String defaultCountry;
    private Phonemetadata.PhoneMetadata defaultMetadata;
    private String extractedNationalPrefix = "";
    private StringBuilder formattingTemplate = new StringBuilder();
    private boolean inputHasFormatting = false;
    private boolean isCompleteNumber = false;
    private boolean isExpectingCountryCallingCode = false;
    private int lastMatchPosition = 0;
    private StringBuilder nationalNumber = new StringBuilder();
    private int originalPosition = 0;
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private int positionToRemember = 0;
    private List<Phonemetadata.NumberFormat> possibleFormats = new ArrayList<Phonemetadata.NumberFormat>();
    private StringBuilder prefixBeforeNationalNumber = new StringBuilder();
    private RegexCache regexCache = new RegexCache(64);
    private boolean shouldAddSpaceAfterNationalPrefix = false;

    static {
        EMPTY_METADATA = new Phonemetadata.PhoneMetadata().setInternationalPrefix("NA");
        ELIGIBLE_FORMAT_PATTERN = Pattern.compile("[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f \u00a0\u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e]*(\\$\\d[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f \u00a0\u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e]*)+");
        NATIONAL_PREFIX_SEPARATORS_PATTERN = Pattern.compile("[- ]");
        DIGIT_PATTERN = Pattern.compile(DIGIT_PLACEHOLDER);
    }

    AsYouTypeFormatter(String string) {
        this.defaultCountry = string;
        this.defaultMetadata = this.currentMetadata = this.getMetadataForRegion(this.defaultCountry);
    }

    private boolean ableToExtractLongerNdd() {
        if (this.extractedNationalPrefix.length() > 0) {
            this.nationalNumber.insert(0, this.extractedNationalPrefix);
            int n = this.prefixBeforeNationalNumber.lastIndexOf(this.extractedNationalPrefix);
            this.prefixBeforeNationalNumber.setLength(n);
        }
        return this.extractedNationalPrefix.equals(this.removeNationalPrefixFromNationalNumber()) ^ true;
    }

    private String appendNationalNumber(String string) {
        int n = this.prefixBeforeNationalNumber.length();
        if (this.shouldAddSpaceAfterNationalPrefix && n > 0 && this.prefixBeforeNationalNumber.charAt(n - 1) != ' ') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(new String(this.prefixBeforeNationalNumber));
            stringBuilder.append(' ');
            stringBuilder.append(string);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((Object)this.prefixBeforeNationalNumber);
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private String attemptToChooseFormattingPattern() {
        if (this.nationalNumber.length() >= 3) {
            this.getAvailableFormats(this.nationalNumber.toString());
            String string = this.attemptToFormatAccruedDigits();
            if (string.length() > 0) {
                return string;
            }
            string = this.maybeCreateNewTemplate() ? this.inputAccruedNationalNumber() : this.accruedInput.toString();
            return string;
        }
        return this.appendNationalNumber(this.nationalNumber.toString());
    }

    private String attemptToChoosePatternWithPrefixExtracted() {
        this.ableToFormat = true;
        this.isExpectingCountryCallingCode = false;
        this.possibleFormats.clear();
        this.lastMatchPosition = 0;
        this.formattingTemplate.setLength(0);
        this.currentFormattingPattern = "";
        return this.attemptToChooseFormattingPattern();
    }

    private boolean attemptToExtractCountryCallingCode() {
        if (this.nationalNumber.length() == 0) {
            return false;
        }
        CharSequence charSequence = new StringBuilder();
        int n = this.phoneUtil.extractCountryCode(this.nationalNumber, (StringBuilder)charSequence);
        if (n == 0) {
            return false;
        }
        this.nationalNumber.setLength(0);
        this.nationalNumber.append(charSequence);
        charSequence = this.phoneUtil.getRegionCodeForCountryCode(n);
        if ("001".equals(charSequence)) {
            this.currentMetadata = this.phoneUtil.getMetadataForNonGeographicalRegion(n);
        } else if (!((String)charSequence).equals(this.defaultCountry)) {
            this.currentMetadata = this.getMetadataForRegion((String)charSequence);
        }
        charSequence = Integer.toString(n);
        StringBuilder stringBuilder = this.prefixBeforeNationalNumber;
        stringBuilder.append((String)charSequence);
        stringBuilder.append(' ');
        this.extractedNationalPrefix = "";
        return true;
    }

    private boolean attemptToExtractIdd() {
        RegexCache regexCache = this.regexCache;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("\\+|");
        ((StringBuilder)object).append(this.currentMetadata.getInternationalPrefix());
        object = regexCache.getPatternForRegex(((StringBuilder)object).toString()).matcher(this.accruedInputWithoutFormatting);
        if (((Matcher)object).lookingAt()) {
            this.isCompleteNumber = true;
            int n = ((Matcher)object).end();
            this.nationalNumber.setLength(0);
            this.nationalNumber.append(this.accruedInputWithoutFormatting.substring(n));
            this.prefixBeforeNationalNumber.setLength(0);
            this.prefixBeforeNationalNumber.append(this.accruedInputWithoutFormatting.substring(0, n));
            if (this.accruedInputWithoutFormatting.charAt(0) != '+') {
                this.prefixBeforeNationalNumber.append(' ');
            }
            return true;
        }
        return false;
    }

    private boolean createFormattingTemplate(Phonemetadata.NumberFormat object) {
        String string = ((Phonemetadata.NumberFormat)object).getPattern();
        this.formattingTemplate.setLength(0);
        object = this.getFormattingTemplate(string, ((Phonemetadata.NumberFormat)object).getFormat());
        if (((String)object).length() > 0) {
            this.formattingTemplate.append((String)object);
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private void getAvailableFormats(String string) {
        void var3_5;
        boolean bl = this.isCompleteNumber && this.extractedNationalPrefix.length() == 0;
        if (bl && this.currentMetadata.intlNumberFormatSize() > 0) {
            List<Phonemetadata.NumberFormat> list2 = this.currentMetadata.intlNumberFormats();
        } else {
            List<Phonemetadata.NumberFormat> list = this.currentMetadata.numberFormats();
        }
        for (Phonemetadata.NumberFormat numberFormat : var3_5) {
            if (this.extractedNationalPrefix.length() > 0 && PhoneNumberUtil.formattingRuleHasFirstGroupOnly(numberFormat.getNationalPrefixFormattingRule()) && !numberFormat.getNationalPrefixOptionalWhenFormatting() && !numberFormat.hasDomesticCarrierCodeFormattingRule() || this.extractedNationalPrefix.length() == 0 && !this.isCompleteNumber && !PhoneNumberUtil.formattingRuleHasFirstGroupOnly(numberFormat.getNationalPrefixFormattingRule()) && !numberFormat.getNationalPrefixOptionalWhenFormatting() || !ELIGIBLE_FORMAT_PATTERN.matcher(numberFormat.getFormat()).matches()) continue;
            this.possibleFormats.add(numberFormat);
        }
        this.narrowDownPossibleFormats(string);
    }

    private String getFormattingTemplate(String string, String string2) {
        Object object = this.regexCache.getPatternForRegex(string).matcher("999999999999999");
        ((Matcher)object).find();
        object = ((Matcher)object).group();
        if (((String)object).length() < this.nationalNumber.length()) {
            return "";
        }
        return ((String)object).replaceAll(string, string2).replaceAll("9", DIGIT_PLACEHOLDER);
    }

    private Phonemetadata.PhoneMetadata getMetadataForRegion(String object) {
        int n = this.phoneUtil.getCountryCodeForRegion((String)object);
        object = this.phoneUtil.getRegionCodeForCountryCode(n);
        if ((object = this.phoneUtil.getMetadataForRegion((String)object)) != null) {
            return object;
        }
        return EMPTY_METADATA;
    }

    private String inputAccruedNationalNumber() {
        int n = this.nationalNumber.length();
        if (n > 0) {
            String string = "";
            for (int i = 0; i < n; ++i) {
                string = this.inputDigitHelper(this.nationalNumber.charAt(i));
            }
            string = this.ableToFormat ? this.appendNationalNumber(string) : this.accruedInput.toString();
            return string;
        }
        return this.prefixBeforeNationalNumber.toString();
    }

    private String inputDigitHelper(char c) {
        Matcher matcher = DIGIT_PATTERN.matcher(this.formattingTemplate);
        if (matcher.find(this.lastMatchPosition)) {
            String string = matcher.replaceFirst(Character.toString(c));
            this.formattingTemplate.replace(0, string.length(), string);
            this.lastMatchPosition = matcher.start();
            return this.formattingTemplate.substring(0, this.lastMatchPosition + 1);
        }
        if (this.possibleFormats.size() == 1) {
            this.ableToFormat = false;
        }
        this.currentFormattingPattern = "";
        return this.accruedInput.toString();
    }

    private String inputDigitWithOptionToRememberPosition(char n, boolean bl) {
        block18 : {
            block20 : {
                block19 : {
                    int n2;
                    this.accruedInput.append((char)n);
                    if (bl) {
                        this.originalPosition = this.accruedInput.length();
                    }
                    if (!this.isDigitOrLeadingPlusSign((char)n)) {
                        this.ableToFormat = false;
                        this.inputHasFormatting = true;
                    } else {
                        n = n2 = this.normalizeAndAccrueDigitsAndPlusSign((char)n, bl);
                    }
                    if (!this.ableToFormat) {
                        if (this.inputHasFormatting) {
                            return this.accruedInput.toString();
                        }
                        if (this.attemptToExtractIdd()) {
                            if (this.attemptToExtractCountryCallingCode()) {
                                return this.attemptToChoosePatternWithPrefixExtracted();
                            }
                        } else if (this.ableToExtractLongerNdd()) {
                            this.prefixBeforeNationalNumber.append(' ');
                            return this.attemptToChoosePatternWithPrefixExtracted();
                        }
                        return this.accruedInput.toString();
                    }
                    n2 = this.accruedInputWithoutFormatting.length();
                    if (n2 == 0 || n2 == 1 || n2 == 2) break block18;
                    if (n2 != 3) break block19;
                    if (!this.attemptToExtractIdd()) break block20;
                    this.isExpectingCountryCallingCode = true;
                }
                if (this.isExpectingCountryCallingCode) {
                    if (this.attemptToExtractCountryCallingCode()) {
                        this.isExpectingCountryCallingCode = false;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((Object)this.prefixBeforeNationalNumber);
                    stringBuilder.append(this.nationalNumber.toString());
                    return stringBuilder.toString();
                }
                if (this.possibleFormats.size() > 0) {
                    String string = this.inputDigitHelper((char)n);
                    String string2 = this.attemptToFormatAccruedDigits();
                    if (string2.length() > 0) {
                        return string2;
                    }
                    this.narrowDownPossibleFormats(this.nationalNumber.toString());
                    if (this.maybeCreateNewTemplate()) {
                        return this.inputAccruedNationalNumber();
                    }
                    string2 = this.ableToFormat ? this.appendNationalNumber(string) : this.accruedInput.toString();
                    return string2;
                }
                return this.attemptToChooseFormattingPattern();
            }
            this.extractedNationalPrefix = this.removeNationalPrefixFromNationalNumber();
            return this.attemptToChooseFormattingPattern();
        }
        return this.accruedInput.toString();
    }

    private boolean isDigitOrLeadingPlusSign(char c) {
        boolean bl;
        block0 : {
            boolean bl2 = Character.isDigit(c);
            bl = true;
            if (bl2 || this.accruedInput.length() == 1 && PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(Character.toString(c)).matches()) break block0;
            bl = false;
        }
        return bl;
    }

    private boolean isNanpaNumberWithNationalPrefix() {
        boolean bl;
        block0 : {
            int n = this.currentMetadata.getCountryCode();
            bl = false;
            if (n != 1 || this.nationalNumber.charAt(0) != '1' || this.nationalNumber.charAt(1) == '0' || this.nationalNumber.charAt(1) == '1') break block0;
            bl = true;
        }
        return bl;
    }

    private boolean maybeCreateNewTemplate() {
        Iterator<Phonemetadata.NumberFormat> iterator = this.possibleFormats.iterator();
        while (iterator.hasNext()) {
            Phonemetadata.NumberFormat numberFormat = iterator.next();
            String string = numberFormat.getPattern();
            if (this.currentFormattingPattern.equals(string)) {
                return false;
            }
            if (this.createFormattingTemplate(numberFormat)) {
                this.currentFormattingPattern = string;
                this.shouldAddSpaceAfterNationalPrefix = NATIONAL_PREFIX_SEPARATORS_PATTERN.matcher(numberFormat.getNationalPrefixFormattingRule()).find();
                this.lastMatchPosition = 0;
                return true;
            }
            iterator.remove();
        }
        this.ableToFormat = false;
        return false;
    }

    private void narrowDownPossibleFormats(String string) {
        int n = string.length();
        Iterator<Phonemetadata.NumberFormat> iterator = this.possibleFormats.iterator();
        while (iterator.hasNext()) {
            int n2;
            Phonemetadata.NumberFormat numberFormat = iterator.next();
            if (numberFormat.leadingDigitsPatternSize() == 0 || this.regexCache.getPatternForRegex(numberFormat.getLeadingDigitsPattern(n2 = Math.min(n - 3, numberFormat.leadingDigitsPatternSize() - 1))).matcher(string).lookingAt()) continue;
            iterator.remove();
        }
    }

    private char normalizeAndAccrueDigitsAndPlusSign(char c, boolean bl) {
        if (c == '+') {
            char c2 = c;
            this.accruedInputWithoutFormatting.append(c);
            c = c2;
        } else {
            char c3;
            c = Character.forDigit(Character.digit(c, 10), 10);
            this.accruedInputWithoutFormatting.append(c);
            this.nationalNumber.append(c);
            c = c3 = c;
        }
        if (bl) {
            this.positionToRemember = this.accruedInputWithoutFormatting.length();
        }
        return c;
    }

    private String removeNationalPrefixFromNationalNumber() {
        int n;
        Object object;
        int n2 = 0;
        if (this.isNanpaNumberWithNationalPrefix()) {
            n = 1;
            object = this.prefixBeforeNationalNumber;
            ((StringBuilder)object).append('1');
            ((StringBuilder)object).append(' ');
            this.isCompleteNumber = true;
        } else {
            n = n2;
            if (this.currentMetadata.hasNationalPrefixForParsing()) {
                object = this.regexCache.getPatternForRegex(this.currentMetadata.getNationalPrefixForParsing()).matcher(this.nationalNumber);
                n = n2;
                if (((Matcher)object).lookingAt()) {
                    n = n2;
                    if (((Matcher)object).end() > 0) {
                        this.isCompleteNumber = true;
                        n = ((Matcher)object).end();
                        this.prefixBeforeNationalNumber.append(this.nationalNumber.substring(0, n));
                    }
                }
            }
        }
        object = this.nationalNumber.substring(0, n);
        this.nationalNumber.delete(0, n);
        return object;
    }

    String attemptToFormatAccruedDigits() {
        for (Phonemetadata.NumberFormat numberFormat : this.possibleFormats) {
            Matcher matcher = this.regexCache.getPatternForRegex(numberFormat.getPattern()).matcher(this.nationalNumber);
            if (!matcher.matches()) continue;
            this.shouldAddSpaceAfterNationalPrefix = NATIONAL_PREFIX_SEPARATORS_PATTERN.matcher(numberFormat.getNationalPrefixFormattingRule()).find();
            return this.appendNationalNumber(matcher.replaceAll(numberFormat.getFormat()));
        }
        return "";
    }

    @UnsupportedAppUsage
    public void clear() {
        this.currentOutput = "";
        this.accruedInput.setLength(0);
        this.accruedInputWithoutFormatting.setLength(0);
        this.formattingTemplate.setLength(0);
        this.lastMatchPosition = 0;
        this.currentFormattingPattern = "";
        this.prefixBeforeNationalNumber.setLength(0);
        this.extractedNationalPrefix = "";
        this.nationalNumber.setLength(0);
        this.ableToFormat = true;
        this.inputHasFormatting = false;
        this.positionToRemember = 0;
        this.originalPosition = 0;
        this.isCompleteNumber = false;
        this.isExpectingCountryCallingCode = false;
        this.possibleFormats.clear();
        this.shouldAddSpaceAfterNationalPrefix = false;
        if (!this.currentMetadata.equals(this.defaultMetadata)) {
            this.currentMetadata = this.getMetadataForRegion(this.defaultCountry);
        }
    }

    String getExtractedNationalPrefix() {
        return this.extractedNationalPrefix;
    }

    @UnsupportedAppUsage
    public int getRememberedPosition() {
        int n;
        if (!this.ableToFormat) {
            return this.originalPosition;
        }
        int n2 = 0;
        for (n = 0; n2 < this.positionToRemember && n < this.currentOutput.length(); ++n) {
            int n3 = n2;
            if (this.accruedInputWithoutFormatting.charAt(n2) == this.currentOutput.charAt(n)) {
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        return n;
    }

    @UnsupportedAppUsage
    public String inputDigit(char c) {
        this.currentOutput = this.inputDigitWithOptionToRememberPosition(c, false);
        return this.currentOutput;
    }

    @UnsupportedAppUsage
    public String inputDigitAndRememberPosition(char c) {
        this.currentOutput = this.inputDigitWithOptionToRememberPosition(c, true);
        return this.currentOutput;
    }
}

