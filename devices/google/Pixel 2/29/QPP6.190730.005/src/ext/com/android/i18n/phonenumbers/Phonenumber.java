/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.i18n.phonenumbers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Serializable;

public final class Phonenumber {
    private Phonenumber() {
    }

    public static class PhoneNumber
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private CountryCodeSource countryCodeSource_ = CountryCodeSource.UNSPECIFIED;
        private int countryCode_ = 0;
        private String extension_ = "";
        private boolean hasCountryCode;
        private boolean hasCountryCodeSource;
        private boolean hasExtension;
        private boolean hasItalianLeadingZero;
        private boolean hasNationalNumber;
        private boolean hasNumberOfLeadingZeros;
        private boolean hasPreferredDomesticCarrierCode;
        private boolean hasRawInput;
        private boolean italianLeadingZero_ = false;
        private long nationalNumber_ = 0L;
        private int numberOfLeadingZeros_ = 1;
        private String preferredDomesticCarrierCode_ = "";
        private String rawInput_ = "";

        public final PhoneNumber clear() {
            this.clearCountryCode();
            this.clearNationalNumber();
            this.clearExtension();
            this.clearItalianLeadingZero();
            this.clearNumberOfLeadingZeros();
            this.clearRawInput();
            this.clearCountryCodeSource();
            this.clearPreferredDomesticCarrierCode();
            return this;
        }

        @UnsupportedAppUsage
        public PhoneNumber clearCountryCode() {
            this.hasCountryCode = false;
            this.countryCode_ = 0;
            return this;
        }

        public PhoneNumber clearCountryCodeSource() {
            this.hasCountryCodeSource = false;
            this.countryCodeSource_ = CountryCodeSource.UNSPECIFIED;
            return this;
        }

        public PhoneNumber clearExtension() {
            this.hasExtension = false;
            this.extension_ = "";
            return this;
        }

        public PhoneNumber clearItalianLeadingZero() {
            this.hasItalianLeadingZero = false;
            this.italianLeadingZero_ = false;
            return this;
        }

        public PhoneNumber clearNationalNumber() {
            this.hasNationalNumber = false;
            this.nationalNumber_ = 0L;
            return this;
        }

        public PhoneNumber clearNumberOfLeadingZeros() {
            this.hasNumberOfLeadingZeros = false;
            this.numberOfLeadingZeros_ = 1;
            return this;
        }

        public PhoneNumber clearPreferredDomesticCarrierCode() {
            this.hasPreferredDomesticCarrierCode = false;
            this.preferredDomesticCarrierCode_ = "";
            return this;
        }

        public PhoneNumber clearRawInput() {
            this.hasRawInput = false;
            this.rawInput_ = "";
            return this;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof PhoneNumber && this.exactlySameAs((PhoneNumber)object);
            return bl;
        }

        public boolean exactlySameAs(PhoneNumber phoneNumber) {
            boolean bl;
            block2 : {
                bl = false;
                if (phoneNumber == null) {
                    return false;
                }
                if (this == phoneNumber) {
                    return true;
                }
                if (this.countryCode_ != phoneNumber.countryCode_ || this.nationalNumber_ != phoneNumber.nationalNumber_ || !this.extension_.equals(phoneNumber.extension_) || this.italianLeadingZero_ != phoneNumber.italianLeadingZero_ || this.numberOfLeadingZeros_ != phoneNumber.numberOfLeadingZeros_ || !this.rawInput_.equals(phoneNumber.rawInput_) || this.countryCodeSource_ != phoneNumber.countryCodeSource_ || !this.preferredDomesticCarrierCode_.equals(phoneNumber.preferredDomesticCarrierCode_) || this.hasPreferredDomesticCarrierCode() != phoneNumber.hasPreferredDomesticCarrierCode()) break block2;
                bl = true;
            }
            return bl;
        }

        @UnsupportedAppUsage
        public int getCountryCode() {
            return this.countryCode_;
        }

        @UnsupportedAppUsage
        public CountryCodeSource getCountryCodeSource() {
            return this.countryCodeSource_;
        }

        @UnsupportedAppUsage
        public String getExtension() {
            return this.extension_;
        }

        @UnsupportedAppUsage
        public long getNationalNumber() {
            return this.nationalNumber_;
        }

        public int getNumberOfLeadingZeros() {
            return this.numberOfLeadingZeros_;
        }

        public String getPreferredDomesticCarrierCode() {
            return this.preferredDomesticCarrierCode_;
        }

        public String getRawInput() {
            return this.rawInput_;
        }

        @UnsupportedAppUsage
        public boolean hasCountryCode() {
            return this.hasCountryCode;
        }

        public boolean hasCountryCodeSource() {
            return this.hasCountryCodeSource;
        }

        @UnsupportedAppUsage
        public boolean hasExtension() {
            return this.hasExtension;
        }

        public boolean hasItalianLeadingZero() {
            return this.hasItalianLeadingZero;
        }

        public boolean hasNationalNumber() {
            return this.hasNationalNumber;
        }

        public boolean hasNumberOfLeadingZeros() {
            return this.hasNumberOfLeadingZeros;
        }

        public boolean hasPreferredDomesticCarrierCode() {
            return this.hasPreferredDomesticCarrierCode;
        }

        public boolean hasRawInput() {
            return this.hasRawInput;
        }

        public int hashCode() {
            int n = this.getCountryCode();
            int n2 = Long.valueOf(this.getNationalNumber()).hashCode();
            int n3 = this.getExtension().hashCode();
            boolean bl = this.isItalianLeadingZero();
            int n4 = 1231;
            int n5 = bl ? 1231 : 1237;
            int n6 = this.getNumberOfLeadingZeros();
            int n7 = this.getRawInput().hashCode();
            int n8 = this.getCountryCodeSource().hashCode();
            int n9 = this.getPreferredDomesticCarrierCode().hashCode();
            if (!this.hasPreferredDomesticCarrierCode()) {
                n4 = 1237;
            }
            return ((((((((41 * 53 + n) * 53 + n2) * 53 + n3) * 53 + n5) * 53 + n6) * 53 + n7) * 53 + n8) * 53 + n9) * 53 + n4;
        }

        public boolean isItalianLeadingZero() {
            return this.italianLeadingZero_;
        }

        public PhoneNumber mergeFrom(PhoneNumber phoneNumber) {
            if (phoneNumber.hasCountryCode()) {
                this.setCountryCode(phoneNumber.getCountryCode());
            }
            if (phoneNumber.hasNationalNumber()) {
                this.setNationalNumber(phoneNumber.getNationalNumber());
            }
            if (phoneNumber.hasExtension()) {
                this.setExtension(phoneNumber.getExtension());
            }
            if (phoneNumber.hasItalianLeadingZero()) {
                this.setItalianLeadingZero(phoneNumber.isItalianLeadingZero());
            }
            if (phoneNumber.hasNumberOfLeadingZeros()) {
                this.setNumberOfLeadingZeros(phoneNumber.getNumberOfLeadingZeros());
            }
            if (phoneNumber.hasRawInput()) {
                this.setRawInput(phoneNumber.getRawInput());
            }
            if (phoneNumber.hasCountryCodeSource()) {
                this.setCountryCodeSource(phoneNumber.getCountryCodeSource());
            }
            if (phoneNumber.hasPreferredDomesticCarrierCode()) {
                this.setPreferredDomesticCarrierCode(phoneNumber.getPreferredDomesticCarrierCode());
            }
            return this;
        }

        public PhoneNumber setCountryCode(int n) {
            this.hasCountryCode = true;
            this.countryCode_ = n;
            return this;
        }

        public PhoneNumber setCountryCodeSource(CountryCodeSource countryCodeSource) {
            if (countryCodeSource != null) {
                this.hasCountryCodeSource = true;
                this.countryCodeSource_ = countryCodeSource;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneNumber setExtension(String string) {
            if (string != null) {
                this.hasExtension = true;
                this.extension_ = string;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneNumber setItalianLeadingZero(boolean bl) {
            this.hasItalianLeadingZero = true;
            this.italianLeadingZero_ = bl;
            return this;
        }

        public PhoneNumber setNationalNumber(long l) {
            this.hasNationalNumber = true;
            this.nationalNumber_ = l;
            return this;
        }

        public PhoneNumber setNumberOfLeadingZeros(int n) {
            this.hasNumberOfLeadingZeros = true;
            this.numberOfLeadingZeros_ = n;
            return this;
        }

        public PhoneNumber setPreferredDomesticCarrierCode(String string) {
            if (string != null) {
                this.hasPreferredDomesticCarrierCode = true;
                this.preferredDomesticCarrierCode_ = string;
                return this;
            }
            throw new NullPointerException();
        }

        public PhoneNumber setRawInput(String string) {
            if (string != null) {
                this.hasRawInput = true;
                this.rawInput_ = string;
                return this;
            }
            throw new NullPointerException();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Country Code: ");
            stringBuilder.append(this.countryCode_);
            stringBuilder.append(" National Number: ");
            stringBuilder.append(this.nationalNumber_);
            if (this.hasItalianLeadingZero() && this.isItalianLeadingZero()) {
                stringBuilder.append(" Leading Zero(s): true");
            }
            if (this.hasNumberOfLeadingZeros()) {
                stringBuilder.append(" Number of leading zeros: ");
                stringBuilder.append(this.numberOfLeadingZeros_);
            }
            if (this.hasExtension()) {
                stringBuilder.append(" Extension: ");
                stringBuilder.append(this.extension_);
            }
            if (this.hasCountryCodeSource()) {
                stringBuilder.append(" Country Code Source: ");
                stringBuilder.append((Object)this.countryCodeSource_);
            }
            if (this.hasPreferredDomesticCarrierCode()) {
                stringBuilder.append(" Preferred Domestic Carrier Code: ");
                stringBuilder.append(this.preferredDomesticCarrierCode_);
            }
            return stringBuilder.toString();
        }

        @UnsupportedAppUsage(implicitMember="values()[Lcom/android/i18n/phonenumbers/Phonenumber$PhoneNumber$CountryCodeSource;")
        public static enum CountryCodeSource {
            FROM_NUMBER_WITH_PLUS_SIGN,
            FROM_NUMBER_WITH_IDD,
            FROM_NUMBER_WITHOUT_PLUS_SIGN,
            FROM_DEFAULT_COUNTRY,
            UNSPECIFIED;
            
        }

    }

}

