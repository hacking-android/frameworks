/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.i18n.phonenumbers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public final class Phonemetadata {
    private Phonemetadata() {
    }

    public static class NumberFormat
    implements Externalizable {
        private static final long serialVersionUID = 1L;
        private String domesticCarrierCodeFormattingRule_ = "";
        private String format_ = "";
        private boolean hasDomesticCarrierCodeFormattingRule;
        private boolean hasFormat;
        private boolean hasNationalPrefixFormattingRule;
        private boolean hasNationalPrefixOptionalWhenFormatting;
        private boolean hasPattern;
        private List<String> leadingDigitsPattern_ = new ArrayList<String>();
        private String nationalPrefixFormattingRule_ = "";
        private boolean nationalPrefixOptionalWhenFormatting_ = false;
        private String pattern_ = "";

        public static Builder newBuilder() {
            return new Builder();
        }

        public NumberFormat addLeadingDigitsPattern(String string) {
            if (string != null) {
                this.leadingDigitsPattern_.add(string);
                return this;
            }
            throw new NullPointerException();
        }

        public NumberFormat clearNationalPrefixFormattingRule() {
            this.hasNationalPrefixFormattingRule = false;
            this.nationalPrefixFormattingRule_ = "";
            return this;
        }

        @UnsupportedAppUsage
        public String getDomesticCarrierCodeFormattingRule() {
            return this.domesticCarrierCodeFormattingRule_;
        }

        @UnsupportedAppUsage
        public String getFormat() {
            return this.format_;
        }

        @UnsupportedAppUsage
        public String getLeadingDigitsPattern(int n) {
            return this.leadingDigitsPattern_.get(n);
        }

        @UnsupportedAppUsage
        public String getNationalPrefixFormattingRule() {
            return this.nationalPrefixFormattingRule_;
        }

        public boolean getNationalPrefixOptionalWhenFormatting() {
            return this.nationalPrefixOptionalWhenFormatting_;
        }

        @UnsupportedAppUsage
        public String getPattern() {
            return this.pattern_;
        }

        public boolean hasDomesticCarrierCodeFormattingRule() {
            return this.hasDomesticCarrierCodeFormattingRule;
        }

        public boolean hasFormat() {
            return this.hasFormat;
        }

        public boolean hasNationalPrefixFormattingRule() {
            return this.hasNationalPrefixFormattingRule;
        }

        public boolean hasNationalPrefixOptionalWhenFormatting() {
            return this.hasNationalPrefixOptionalWhenFormatting;
        }

        public boolean hasPattern() {
            return this.hasPattern;
        }

        public List<String> leadingDigitPatterns() {
            return this.leadingDigitsPattern_;
        }

        @UnsupportedAppUsage
        public int leadingDigitsPatternSize() {
            return this.leadingDigitsPattern_.size();
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException {
            this.setPattern(objectInput.readUTF());
            this.setFormat(objectInput.readUTF());
            int n = objectInput.readInt();
            for (int i = 0; i < n; ++i) {
                this.leadingDigitsPattern_.add(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setNationalPrefixFormattingRule(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setDomesticCarrierCodeFormattingRule(objectInput.readUTF());
            }
            this.setNationalPrefixOptionalWhenFormatting(objectInput.readBoolean());
        }

        public NumberFormat setDomesticCarrierCodeFormattingRule(String string) {
            this.hasDomesticCarrierCodeFormattingRule = true;
            this.domesticCarrierCodeFormattingRule_ = string;
            return this;
        }

        public NumberFormat setFormat(String string) {
            this.hasFormat = true;
            this.format_ = string;
            return this;
        }

        public NumberFormat setNationalPrefixFormattingRule(String string) {
            this.hasNationalPrefixFormattingRule = true;
            this.nationalPrefixFormattingRule_ = string;
            return this;
        }

        public NumberFormat setNationalPrefixOptionalWhenFormatting(boolean bl) {
            this.hasNationalPrefixOptionalWhenFormatting = true;
            this.nationalPrefixOptionalWhenFormatting_ = bl;
            return this;
        }

        public NumberFormat setPattern(String string) {
            this.hasPattern = true;
            this.pattern_ = string;
            return this;
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeUTF(this.pattern_);
            objectOutput.writeUTF(this.format_);
            int n = this.leadingDigitsPatternSize();
            objectOutput.writeInt(n);
            for (int i = 0; i < n; ++i) {
                objectOutput.writeUTF(this.leadingDigitsPattern_.get(i));
            }
            objectOutput.writeBoolean(this.hasNationalPrefixFormattingRule);
            if (this.hasNationalPrefixFormattingRule) {
                objectOutput.writeUTF(this.nationalPrefixFormattingRule_);
            }
            objectOutput.writeBoolean(this.hasDomesticCarrierCodeFormattingRule);
            if (this.hasDomesticCarrierCodeFormattingRule) {
                objectOutput.writeUTF(this.domesticCarrierCodeFormattingRule_);
            }
            objectOutput.writeBoolean(this.nationalPrefixOptionalWhenFormatting_);
        }

        public static final class Builder
        extends NumberFormat {
            public NumberFormat build() {
                return this;
            }

            public Builder mergeFrom(NumberFormat numberFormat) {
                if (numberFormat.hasPattern()) {
                    this.setPattern(numberFormat.getPattern());
                }
                if (numberFormat.hasFormat()) {
                    this.setFormat(numberFormat.getFormat());
                }
                for (int i = 0; i < numberFormat.leadingDigitsPatternSize(); ++i) {
                    this.addLeadingDigitsPattern(numberFormat.getLeadingDigitsPattern(i));
                }
                if (numberFormat.hasNationalPrefixFormattingRule()) {
                    this.setNationalPrefixFormattingRule(numberFormat.getNationalPrefixFormattingRule());
                }
                if (numberFormat.hasDomesticCarrierCodeFormattingRule()) {
                    this.setDomesticCarrierCodeFormattingRule(numberFormat.getDomesticCarrierCodeFormattingRule());
                }
                if (numberFormat.hasNationalPrefixOptionalWhenFormatting()) {
                    this.setNationalPrefixOptionalWhenFormatting(numberFormat.getNationalPrefixOptionalWhenFormatting());
                }
                return this;
            }
        }

    }

    public static class PhoneMetadata
    implements Externalizable {
        private static final long serialVersionUID = 1L;
        private PhoneNumberDesc carrierSpecific_ = null;
        private int countryCode_ = 0;
        private PhoneNumberDesc emergency_ = null;
        private PhoneNumberDesc fixedLine_ = null;
        private PhoneNumberDesc generalDesc_ = null;
        private boolean hasCarrierSpecific;
        private boolean hasCountryCode;
        private boolean hasEmergency;
        private boolean hasFixedLine;
        private boolean hasGeneralDesc;
        private boolean hasId;
        private boolean hasInternationalPrefix;
        private boolean hasLeadingDigits;
        private boolean hasLeadingZeroPossible;
        private boolean hasMainCountryForCode;
        private boolean hasMobile;
        private boolean hasMobileNumberPortableRegion;
        private boolean hasNationalPrefix;
        private boolean hasNationalPrefixForParsing;
        private boolean hasNationalPrefixTransformRule;
        private boolean hasNoInternationalDialling;
        private boolean hasPager;
        private boolean hasPersonalNumber;
        private boolean hasPreferredExtnPrefix;
        private boolean hasPreferredInternationalPrefix;
        private boolean hasPremiumRate;
        private boolean hasSameMobileAndFixedLinePattern;
        private boolean hasSharedCost;
        private boolean hasShortCode;
        private boolean hasSmsServices;
        private boolean hasStandardRate;
        private boolean hasTollFree;
        private boolean hasUan;
        private boolean hasVoicemail;
        private boolean hasVoip;
        private String id_ = "";
        private String internationalPrefix_ = "";
        private List<NumberFormat> intlNumberFormat_ = new ArrayList<NumberFormat>();
        private String leadingDigits_ = "";
        private boolean leadingZeroPossible_ = false;
        private boolean mainCountryForCode_ = false;
        private boolean mobileNumberPortableRegion_ = false;
        private PhoneNumberDesc mobile_ = null;
        private String nationalPrefixForParsing_ = "";
        private String nationalPrefixTransformRule_ = "";
        private String nationalPrefix_ = "";
        private PhoneNumberDesc noInternationalDialling_ = null;
        private List<NumberFormat> numberFormat_ = new ArrayList<NumberFormat>();
        private PhoneNumberDesc pager_ = null;
        private PhoneNumberDesc personalNumber_ = null;
        private String preferredExtnPrefix_ = "";
        private String preferredInternationalPrefix_ = "";
        private PhoneNumberDesc premiumRate_ = null;
        private boolean sameMobileAndFixedLinePattern_ = false;
        private PhoneNumberDesc sharedCost_ = null;
        private PhoneNumberDesc shortCode_ = null;
        private PhoneNumberDesc smsServices_ = null;
        private PhoneNumberDesc standardRate_ = null;
        private PhoneNumberDesc tollFree_ = null;
        private PhoneNumberDesc uan_ = null;
        private PhoneNumberDesc voicemail_ = null;
        private PhoneNumberDesc voip_ = null;

        public static Builder newBuilder() {
            return new Builder();
        }

        public PhoneMetadata addIntlNumberFormat(NumberFormat numberFormat) {
            if (numberFormat != null) {
                this.intlNumberFormat_.add(numberFormat);
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata addNumberFormat(NumberFormat numberFormat) {
            if (numberFormat != null) {
                this.numberFormat_.add(numberFormat);
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata clearIntlNumberFormat() {
            this.intlNumberFormat_.clear();
            return this;
        }

        public PhoneMetadata clearLeadingZeroPossible() {
            this.hasLeadingZeroPossible = false;
            this.leadingZeroPossible_ = false;
            return this;
        }

        public PhoneMetadata clearMainCountryForCode() {
            this.hasMainCountryForCode = false;
            this.mainCountryForCode_ = false;
            return this;
        }

        public PhoneMetadata clearMobileNumberPortableRegion() {
            this.hasMobileNumberPortableRegion = false;
            this.mobileNumberPortableRegion_ = false;
            return this;
        }

        public PhoneMetadata clearNationalPrefix() {
            this.hasNationalPrefix = false;
            this.nationalPrefix_ = "";
            return this;
        }

        public PhoneMetadata clearNationalPrefixTransformRule() {
            this.hasNationalPrefixTransformRule = false;
            this.nationalPrefixTransformRule_ = "";
            return this;
        }

        public PhoneMetadata clearPreferredExtnPrefix() {
            this.hasPreferredExtnPrefix = false;
            this.preferredExtnPrefix_ = "";
            return this;
        }

        public PhoneMetadata clearPreferredInternationalPrefix() {
            this.hasPreferredInternationalPrefix = false;
            this.preferredInternationalPrefix_ = "";
            return this;
        }

        public PhoneMetadata clearSameMobileAndFixedLinePattern() {
            this.hasSameMobileAndFixedLinePattern = false;
            this.sameMobileAndFixedLinePattern_ = false;
            return this;
        }

        public PhoneNumberDesc getCarrierSpecific() {
            return this.carrierSpecific_;
        }

        @UnsupportedAppUsage
        public int getCountryCode() {
            return this.countryCode_;
        }

        public PhoneNumberDesc getEmergency() {
            return this.emergency_;
        }

        public PhoneNumberDesc getFixedLine() {
            return this.fixedLine_;
        }

        @UnsupportedAppUsage
        public PhoneNumberDesc getGeneralDesc() {
            return this.generalDesc_;
        }

        public String getId() {
            return this.id_;
        }

        public String getInternationalPrefix() {
            return this.internationalPrefix_;
        }

        public NumberFormat getIntlNumberFormat(int n) {
            return this.intlNumberFormat_.get(n);
        }

        public String getLeadingDigits() {
            return this.leadingDigits_;
        }

        public boolean getMainCountryForCode() {
            return this.mainCountryForCode_;
        }

        public PhoneNumberDesc getMobile() {
            return this.mobile_;
        }

        public String getNationalPrefix() {
            return this.nationalPrefix_;
        }

        @UnsupportedAppUsage
        public String getNationalPrefixForParsing() {
            return this.nationalPrefixForParsing_;
        }

        @UnsupportedAppUsage
        public String getNationalPrefixTransformRule() {
            return this.nationalPrefixTransformRule_;
        }

        public PhoneNumberDesc getNoInternationalDialling() {
            return this.noInternationalDialling_;
        }

        public NumberFormat getNumberFormat(int n) {
            return this.numberFormat_.get(n);
        }

        public PhoneNumberDesc getPager() {
            return this.pager_;
        }

        public PhoneNumberDesc getPersonalNumber() {
            return this.personalNumber_;
        }

        @UnsupportedAppUsage
        public String getPreferredExtnPrefix() {
            return this.preferredExtnPrefix_;
        }

        public String getPreferredInternationalPrefix() {
            return this.preferredInternationalPrefix_;
        }

        public PhoneNumberDesc getPremiumRate() {
            return this.premiumRate_;
        }

        public boolean getSameMobileAndFixedLinePattern() {
            return this.sameMobileAndFixedLinePattern_;
        }

        public PhoneNumberDesc getSharedCost() {
            return this.sharedCost_;
        }

        public PhoneNumberDesc getShortCode() {
            return this.shortCode_;
        }

        public PhoneNumberDesc getSmsServices() {
            return this.smsServices_;
        }

        public PhoneNumberDesc getStandardRate() {
            return this.standardRate_;
        }

        public PhoneNumberDesc getTollFree() {
            return this.tollFree_;
        }

        public PhoneNumberDesc getUan() {
            return this.uan_;
        }

        public PhoneNumberDesc getVoicemail() {
            return this.voicemail_;
        }

        public PhoneNumberDesc getVoip() {
            return this.voip_;
        }

        public boolean hasCarrierSpecific() {
            return this.hasCarrierSpecific;
        }

        public boolean hasCountryCode() {
            return this.hasCountryCode;
        }

        public boolean hasEmergency() {
            return this.hasEmergency;
        }

        public boolean hasFixedLine() {
            return this.hasFixedLine;
        }

        public boolean hasGeneralDesc() {
            return this.hasGeneralDesc;
        }

        public boolean hasId() {
            return this.hasId;
        }

        public boolean hasInternationalPrefix() {
            return this.hasInternationalPrefix;
        }

        public boolean hasLeadingDigits() {
            return this.hasLeadingDigits;
        }

        public boolean hasLeadingZeroPossible() {
            return this.hasLeadingZeroPossible;
        }

        public boolean hasMainCountryForCode() {
            return this.hasMainCountryForCode;
        }

        public boolean hasMobile() {
            return this.hasMobile;
        }

        public boolean hasMobileNumberPortableRegion() {
            return this.hasMobileNumberPortableRegion;
        }

        @UnsupportedAppUsage
        public boolean hasNationalPrefix() {
            return this.hasNationalPrefix;
        }

        public boolean hasNationalPrefixForParsing() {
            return this.hasNationalPrefixForParsing;
        }

        public boolean hasNationalPrefixTransformRule() {
            return this.hasNationalPrefixTransformRule;
        }

        public boolean hasNoInternationalDialling() {
            return this.hasNoInternationalDialling;
        }

        public boolean hasPager() {
            return this.hasPager;
        }

        public boolean hasPersonalNumber() {
            return this.hasPersonalNumber;
        }

        @UnsupportedAppUsage
        public boolean hasPreferredExtnPrefix() {
            return this.hasPreferredExtnPrefix;
        }

        public boolean hasPreferredInternationalPrefix() {
            return this.hasPreferredInternationalPrefix;
        }

        public boolean hasPremiumRate() {
            return this.hasPremiumRate;
        }

        public boolean hasSameMobileAndFixedLinePattern() {
            return this.hasSameMobileAndFixedLinePattern;
        }

        public boolean hasSharedCost() {
            return this.hasSharedCost;
        }

        public boolean hasShortCode() {
            return this.hasShortCode;
        }

        public boolean hasSmsServices() {
            return this.hasSmsServices;
        }

        public boolean hasStandardRate() {
            return this.hasStandardRate;
        }

        public boolean hasTollFree() {
            return this.hasTollFree;
        }

        public boolean hasUan() {
            return this.hasUan;
        }

        public boolean hasVoicemail() {
            return this.hasVoicemail;
        }

        public boolean hasVoip() {
            return this.hasVoip;
        }

        public int intlNumberFormatSize() {
            return this.intlNumberFormat_.size();
        }

        @UnsupportedAppUsage
        public List<NumberFormat> intlNumberFormats() {
            return this.intlNumberFormat_;
        }

        public boolean isLeadingZeroPossible() {
            return this.leadingZeroPossible_;
        }

        public boolean isMainCountryForCode() {
            return this.mainCountryForCode_;
        }

        public boolean isMobileNumberPortableRegion() {
            return this.mobileNumberPortableRegion_;
        }

        public int numberFormatSize() {
            return this.numberFormat_.size();
        }

        @UnsupportedAppUsage
        public List<NumberFormat> numberFormats() {
            return this.numberFormat_;
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException {
            Externalizable externalizable;
            int n;
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setGeneralDesc((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setFixedLine((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setMobile((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setTollFree((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setPremiumRate((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setSharedCost((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setPersonalNumber((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setVoip((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setPager((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setUan((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setEmergency((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setVoicemail((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setShortCode((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setStandardRate((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setCarrierSpecific((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setSmsServices((PhoneNumberDesc)externalizable);
            }
            if (objectInput.readBoolean()) {
                externalizable = new PhoneNumberDesc();
                ((PhoneNumberDesc)externalizable).readExternal(objectInput);
                this.setNoInternationalDialling((PhoneNumberDesc)externalizable);
            }
            this.setId(objectInput.readUTF());
            this.setCountryCode(objectInput.readInt());
            this.setInternationalPrefix(objectInput.readUTF());
            if (objectInput.readBoolean()) {
                this.setPreferredInternationalPrefix(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setNationalPrefix(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setPreferredExtnPrefix(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setNationalPrefixForParsing(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setNationalPrefixTransformRule(objectInput.readUTF());
            }
            this.setSameMobileAndFixedLinePattern(objectInput.readBoolean());
            int n2 = objectInput.readInt();
            for (n = 0; n < n2; ++n) {
                externalizable = new NumberFormat();
                ((NumberFormat)externalizable).readExternal(objectInput);
                this.numberFormat_.add((NumberFormat)externalizable);
            }
            n2 = objectInput.readInt();
            for (n = 0; n < n2; ++n) {
                externalizable = new NumberFormat();
                ((NumberFormat)externalizable).readExternal(objectInput);
                this.intlNumberFormat_.add((NumberFormat)externalizable);
            }
            this.setMainCountryForCode(objectInput.readBoolean());
            if (objectInput.readBoolean()) {
                this.setLeadingDigits(objectInput.readUTF());
            }
            this.setLeadingZeroPossible(objectInput.readBoolean());
            this.setMobileNumberPortableRegion(objectInput.readBoolean());
        }

        public PhoneMetadata setCarrierSpecific(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasCarrierSpecific = true;
                this.carrierSpecific_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setCountryCode(int n) {
            this.hasCountryCode = true;
            this.countryCode_ = n;
            return this;
        }

        public PhoneMetadata setEmergency(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasEmergency = true;
                this.emergency_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setFixedLine(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasFixedLine = true;
                this.fixedLine_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setGeneralDesc(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasGeneralDesc = true;
                this.generalDesc_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setId(String string) {
            this.hasId = true;
            this.id_ = string;
            return this;
        }

        public PhoneMetadata setInternationalPrefix(String string) {
            this.hasInternationalPrefix = true;
            this.internationalPrefix_ = string;
            return this;
        }

        public PhoneMetadata setLeadingDigits(String string) {
            this.hasLeadingDigits = true;
            this.leadingDigits_ = string;
            return this;
        }

        public PhoneMetadata setLeadingZeroPossible(boolean bl) {
            this.hasLeadingZeroPossible = true;
            this.leadingZeroPossible_ = bl;
            return this;
        }

        public PhoneMetadata setMainCountryForCode(boolean bl) {
            this.hasMainCountryForCode = true;
            this.mainCountryForCode_ = bl;
            return this;
        }

        public PhoneMetadata setMobile(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasMobile = true;
                this.mobile_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setMobileNumberPortableRegion(boolean bl) {
            this.hasMobileNumberPortableRegion = true;
            this.mobileNumberPortableRegion_ = bl;
            return this;
        }

        public PhoneMetadata setNationalPrefix(String string) {
            this.hasNationalPrefix = true;
            this.nationalPrefix_ = string;
            return this;
        }

        public PhoneMetadata setNationalPrefixForParsing(String string) {
            this.hasNationalPrefixForParsing = true;
            this.nationalPrefixForParsing_ = string;
            return this;
        }

        public PhoneMetadata setNationalPrefixTransformRule(String string) {
            this.hasNationalPrefixTransformRule = true;
            this.nationalPrefixTransformRule_ = string;
            return this;
        }

        public PhoneMetadata setNoInternationalDialling(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasNoInternationalDialling = true;
                this.noInternationalDialling_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setPager(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasPager = true;
                this.pager_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setPersonalNumber(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasPersonalNumber = true;
                this.personalNumber_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setPreferredExtnPrefix(String string) {
            this.hasPreferredExtnPrefix = true;
            this.preferredExtnPrefix_ = string;
            return this;
        }

        public PhoneMetadata setPreferredInternationalPrefix(String string) {
            this.hasPreferredInternationalPrefix = true;
            this.preferredInternationalPrefix_ = string;
            return this;
        }

        public PhoneMetadata setPremiumRate(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasPremiumRate = true;
                this.premiumRate_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setSameMobileAndFixedLinePattern(boolean bl) {
            this.hasSameMobileAndFixedLinePattern = true;
            this.sameMobileAndFixedLinePattern_ = bl;
            return this;
        }

        public PhoneMetadata setSharedCost(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasSharedCost = true;
                this.sharedCost_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setShortCode(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasShortCode = true;
                this.shortCode_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setSmsServices(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasSmsServices = true;
                this.smsServices_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setStandardRate(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasStandardRate = true;
                this.standardRate_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setTollFree(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasTollFree = true;
                this.tollFree_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setUan(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasUan = true;
                this.uan_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setVoicemail(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasVoicemail = true;
                this.voicemail_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadata setVoip(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc != null) {
                this.hasVoip = true;
                this.voip_ = phoneNumberDesc;
                return this;
            }
            throw new NullPointerException();
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            int n;
            objectOutput.writeBoolean(this.hasGeneralDesc);
            if (this.hasGeneralDesc) {
                this.generalDesc_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasFixedLine);
            if (this.hasFixedLine) {
                this.fixedLine_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasMobile);
            if (this.hasMobile) {
                this.mobile_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasTollFree);
            if (this.hasTollFree) {
                this.tollFree_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPremiumRate);
            if (this.hasPremiumRate) {
                this.premiumRate_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasSharedCost);
            if (this.hasSharedCost) {
                this.sharedCost_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPersonalNumber);
            if (this.hasPersonalNumber) {
                this.personalNumber_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasVoip);
            if (this.hasVoip) {
                this.voip_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPager);
            if (this.hasPager) {
                this.pager_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasUan);
            if (this.hasUan) {
                this.uan_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasEmergency);
            if (this.hasEmergency) {
                this.emergency_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasVoicemail);
            if (this.hasVoicemail) {
                this.voicemail_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasShortCode);
            if (this.hasShortCode) {
                this.shortCode_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasStandardRate);
            if (this.hasStandardRate) {
                this.standardRate_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasCarrierSpecific);
            if (this.hasCarrierSpecific) {
                this.carrierSpecific_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasSmsServices);
            if (this.hasSmsServices) {
                this.smsServices_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasNoInternationalDialling);
            if (this.hasNoInternationalDialling) {
                this.noInternationalDialling_.writeExternal(objectOutput);
            }
            objectOutput.writeUTF(this.id_);
            objectOutput.writeInt(this.countryCode_);
            objectOutput.writeUTF(this.internationalPrefix_);
            objectOutput.writeBoolean(this.hasPreferredInternationalPrefix);
            if (this.hasPreferredInternationalPrefix) {
                objectOutput.writeUTF(this.preferredInternationalPrefix_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefix);
            if (this.hasNationalPrefix) {
                objectOutput.writeUTF(this.nationalPrefix_);
            }
            objectOutput.writeBoolean(this.hasPreferredExtnPrefix);
            if (this.hasPreferredExtnPrefix) {
                objectOutput.writeUTF(this.preferredExtnPrefix_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefixForParsing);
            if (this.hasNationalPrefixForParsing) {
                objectOutput.writeUTF(this.nationalPrefixForParsing_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefixTransformRule);
            if (this.hasNationalPrefixTransformRule) {
                objectOutput.writeUTF(this.nationalPrefixTransformRule_);
            }
            objectOutput.writeBoolean(this.sameMobileAndFixedLinePattern_);
            int n2 = this.numberFormatSize();
            objectOutput.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                this.numberFormat_.get(n).writeExternal(objectOutput);
            }
            n2 = this.intlNumberFormatSize();
            objectOutput.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                this.intlNumberFormat_.get(n).writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.mainCountryForCode_);
            objectOutput.writeBoolean(this.hasLeadingDigits);
            if (this.hasLeadingDigits) {
                objectOutput.writeUTF(this.leadingDigits_);
            }
            objectOutput.writeBoolean(this.leadingZeroPossible_);
            objectOutput.writeBoolean(this.mobileNumberPortableRegion_);
        }

        public static final class Builder
        extends PhoneMetadata {
            public PhoneMetadata build() {
                return this;
            }
        }

    }

    public static class PhoneMetadataCollection
    implements Externalizable {
        private static final long serialVersionUID = 1L;
        private List<PhoneMetadata> metadata_ = new ArrayList<PhoneMetadata>();

        public static Builder newBuilder() {
            return new Builder();
        }

        public PhoneMetadataCollection addMetadata(PhoneMetadata phoneMetadata) {
            if (phoneMetadata != null) {
                this.metadata_.add(phoneMetadata);
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneMetadataCollection clear() {
            this.metadata_.clear();
            return this;
        }

        public int getMetadataCount() {
            return this.metadata_.size();
        }

        @UnsupportedAppUsage
        public List<PhoneMetadata> getMetadataList() {
            return this.metadata_;
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException {
            int n = objectInput.readInt();
            for (int i = 0; i < n; ++i) {
                PhoneMetadata phoneMetadata = new PhoneMetadata();
                phoneMetadata.readExternal(objectInput);
                this.metadata_.add(phoneMetadata);
            }
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            int n = this.getMetadataCount();
            objectOutput.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.metadata_.get(i).writeExternal(objectOutput);
            }
        }

        public static final class Builder
        extends PhoneMetadataCollection {
            public PhoneMetadataCollection build() {
                return this;
            }
        }

    }

    public static class PhoneNumberDesc
    implements Externalizable {
        private static final long serialVersionUID = 1L;
        private String exampleNumber_ = "";
        private boolean hasExampleNumber;
        private boolean hasNationalNumberPattern;
        private String nationalNumberPattern_ = "";
        private List<Integer> possibleLengthLocalOnly_ = new ArrayList<Integer>();
        private List<Integer> possibleLength_ = new ArrayList<Integer>();

        public static Builder newBuilder() {
            return new Builder();
        }

        public PhoneNumberDesc addPossibleLength(int n) {
            this.possibleLength_.add(n);
            return this;
        }

        public PhoneNumberDesc addPossibleLengthLocalOnly(int n) {
            this.possibleLengthLocalOnly_.add(n);
            return this;
        }

        public PhoneNumberDesc clearExampleNumber() {
            this.hasExampleNumber = false;
            this.exampleNumber_ = "";
            return this;
        }

        public PhoneNumberDesc clearNationalNumberPattern() {
            this.hasNationalNumberPattern = false;
            this.nationalNumberPattern_ = "";
            return this;
        }

        public PhoneNumberDesc clearPossibleLength() {
            this.possibleLength_.clear();
            return this;
        }

        public PhoneNumberDesc clearPossibleLengthLocalOnly() {
            this.possibleLengthLocalOnly_.clear();
            return this;
        }

        public boolean exactlySameAs(PhoneNumberDesc phoneNumberDesc) {
            boolean bl = this.nationalNumberPattern_.equals(phoneNumberDesc.nationalNumberPattern_) && this.possibleLength_.equals(phoneNumberDesc.possibleLength_) && this.possibleLengthLocalOnly_.equals(phoneNumberDesc.possibleLengthLocalOnly_) && this.exampleNumber_.equals(phoneNumberDesc.exampleNumber_);
            return bl;
        }

        public String getExampleNumber() {
            return this.exampleNumber_;
        }

        @UnsupportedAppUsage
        public String getNationalNumberPattern() {
            return this.nationalNumberPattern_;
        }

        public int getPossibleLength(int n) {
            return this.possibleLength_.get(n);
        }

        public int getPossibleLengthCount() {
            return this.possibleLength_.size();
        }

        public List<Integer> getPossibleLengthList() {
            return this.possibleLength_;
        }

        public int getPossibleLengthLocalOnly(int n) {
            return this.possibleLengthLocalOnly_.get(n);
        }

        public int getPossibleLengthLocalOnlyCount() {
            return this.possibleLengthLocalOnly_.size();
        }

        public List<Integer> getPossibleLengthLocalOnlyList() {
            return this.possibleLengthLocalOnly_;
        }

        public boolean hasExampleNumber() {
            return this.hasExampleNumber;
        }

        public boolean hasNationalNumberPattern() {
            return this.hasNationalNumberPattern;
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException {
            int n;
            if (objectInput.readBoolean()) {
                this.setNationalNumberPattern(objectInput.readUTF());
            }
            int n2 = objectInput.readInt();
            for (n = 0; n < n2; ++n) {
                this.possibleLength_.add(objectInput.readInt());
            }
            n2 = objectInput.readInt();
            for (n = 0; n < n2; ++n) {
                this.possibleLengthLocalOnly_.add(objectInput.readInt());
            }
            if (objectInput.readBoolean()) {
                this.setExampleNumber(objectInput.readUTF());
            }
        }

        public PhoneNumberDesc setExampleNumber(String string) {
            this.hasExampleNumber = true;
            this.exampleNumber_ = string;
            return this;
        }

        public PhoneNumberDesc setNationalNumberPattern(String string) {
            this.hasNationalNumberPattern = true;
            this.nationalNumberPattern_ = string;
            return this;
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            int n;
            objectOutput.writeBoolean(this.hasNationalNumberPattern);
            if (this.hasNationalNumberPattern) {
                objectOutput.writeUTF(this.nationalNumberPattern_);
            }
            int n2 = this.getPossibleLengthCount();
            objectOutput.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                objectOutput.writeInt(this.possibleLength_.get(n));
            }
            n2 = this.getPossibleLengthLocalOnlyCount();
            objectOutput.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                objectOutput.writeInt(this.possibleLengthLocalOnly_.get(n));
            }
            objectOutput.writeBoolean(this.hasExampleNumber);
            if (this.hasExampleNumber) {
                objectOutput.writeUTF(this.exampleNumber_);
            }
        }

        public static final class Builder
        extends PhoneNumberDesc {
            public PhoneNumberDesc build() {
                return this;
            }

            public Builder mergeFrom(PhoneNumberDesc phoneNumberDesc) {
                int n;
                if (phoneNumberDesc.hasNationalNumberPattern()) {
                    this.setNationalNumberPattern(phoneNumberDesc.getNationalNumberPattern());
                }
                for (n = 0; n < phoneNumberDesc.getPossibleLengthCount(); ++n) {
                    this.addPossibleLength(phoneNumberDesc.getPossibleLength(n));
                }
                for (n = 0; n < phoneNumberDesc.getPossibleLengthLocalOnlyCount(); ++n) {
                    this.addPossibleLengthLocalOnly(phoneNumberDesc.getPossibleLengthLocalOnly(n));
                }
                if (phoneNumberDesc.hasExampleNumber()) {
                    this.setExampleNumber(phoneNumberDesc.getExampleNumber());
                }
                return this;
            }
        }

    }

}

