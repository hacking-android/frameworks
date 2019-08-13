/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class ImsException
extends Exception {
    public static final int CODE_ERROR_SERVICE_UNAVAILABLE = 1;
    public static final int CODE_ERROR_UNSPECIFIED = 0;
    public static final int CODE_ERROR_UNSUPPORTED_OPERATION = 2;
    private int mCode = 0;

    public ImsException(String string2) {
        super(ImsException.getMessage(string2, 0));
    }

    public ImsException(String string2, int n) {
        super(ImsException.getMessage(string2, n));
        this.mCode = n;
    }

    public ImsException(String string2, int n, Throwable throwable) {
        super(ImsException.getMessage(string2, n), throwable);
        this.mCode = n;
    }

    private static String getMessage(String charSequence, int n) {
        if (!TextUtils.isEmpty(charSequence)) {
            charSequence = new StringBuilder((String)charSequence);
            ((StringBuilder)charSequence).append(" (code: ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(")");
            return ((StringBuilder)charSequence).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("code: ");
        ((StringBuilder)charSequence).append(n);
        return ((StringBuilder)charSequence).toString();
    }

    public int getCode() {
        return this.mCode;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ImsErrorCode {
    }

}

