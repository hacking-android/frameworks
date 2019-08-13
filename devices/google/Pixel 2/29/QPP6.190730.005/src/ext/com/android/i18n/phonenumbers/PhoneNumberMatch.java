/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.Phonenumber;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Arrays;

public final class PhoneNumberMatch {
    private final Phonenumber.PhoneNumber number;
    private final String rawString;
    private final int start;

    PhoneNumberMatch(int n, String string, Phonenumber.PhoneNumber phoneNumber) {
        if (n >= 0) {
            if (string != null && phoneNumber != null) {
                this.start = n;
                this.rawString = string;
                this.number = phoneNumber;
                return;
            }
            throw new NullPointerException();
        }
        throw new IllegalArgumentException("Start index must be >= 0.");
    }

    @UnsupportedAppUsage
    public int end() {
        return this.start + this.rawString.length();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof PhoneNumberMatch)) {
            return false;
        }
        object = (PhoneNumberMatch)object;
        if (!this.rawString.equals(((PhoneNumberMatch)object).rawString) || this.start != ((PhoneNumberMatch)object).start || !this.number.equals(((PhoneNumberMatch)object).number)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.start, this.rawString, this.number});
    }

    @UnsupportedAppUsage
    public Phonenumber.PhoneNumber number() {
        return this.number;
    }

    @UnsupportedAppUsage
    public String rawString() {
        return this.rawString;
    }

    @UnsupportedAppUsage
    public int start() {
        return this.start;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PhoneNumberMatch [");
        stringBuilder.append(this.start());
        stringBuilder.append(",");
        stringBuilder.append(this.end());
        stringBuilder.append(") ");
        stringBuilder.append(this.rawString);
        return stringBuilder.toString();
    }
}

