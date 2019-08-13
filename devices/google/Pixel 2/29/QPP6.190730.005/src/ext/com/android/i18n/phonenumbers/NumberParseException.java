/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.i18n.phonenumbers;

import dalvik.annotation.compat.UnsupportedAppUsage;

public class NumberParseException
extends Exception {
    private ErrorType errorType;
    private String message;

    public NumberParseException(ErrorType errorType, String string) {
        super(string);
        this.message = string;
        this.errorType = errorType;
    }

    @UnsupportedAppUsage
    public ErrorType getErrorType() {
        return this.errorType;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error type: ");
        stringBuilder.append((Object)this.errorType);
        stringBuilder.append(". ");
        stringBuilder.append(this.message);
        return stringBuilder.toString();
    }

    public static enum ErrorType {
        INVALID_COUNTRY_CODE,
        NOT_A_NUMBER,
        TOO_SHORT_AFTER_IDD,
        TOO_SHORT_NSN,
        TOO_LONG;
        
    }

}

