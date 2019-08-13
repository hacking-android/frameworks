/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;

@SystemApi
public class ServiceSpecificException
extends RuntimeException {
    public final int errorCode;

    public ServiceSpecificException(int n) {
        this.errorCode = n;
    }

    public ServiceSpecificException(int n, String string2) {
        super(string2);
        this.errorCode = n;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" (code ");
        stringBuilder.append(this.errorCode);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

