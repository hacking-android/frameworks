/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.asn1.Asn1Node
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.asn1.Asn1Node;
import com.android.internal.telephony.uicc.euicc.EuiccCardException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EuiccCardErrorException
extends EuiccCardException {
    public static final int OPERATION_AUTHENTICATE_SERVER = 3;
    public static final int OPERATION_CANCEL_SESSION = 4;
    public static final int OPERATION_DELETE_PROFILE = 12;
    public static final int OPERATION_DISABLE_PROFILE = 11;
    public static final int OPERATION_GET_PROFILE = 1;
    public static final int OPERATION_LIST_NOTIFICATIONS = 6;
    public static final int OPERATION_LOAD_BOUND_PROFILE_PACKAGE = 5;
    public static final int OPERATION_PREPARE_DOWNLOAD = 2;
    public static final int OPERATION_REMOVE_NOTIFICATION_FROM_LIST = 9;
    public static final int OPERATION_RESET_MEMORY = 13;
    public static final int OPERATION_RETRIEVE_NOTIFICATION = 8;
    public static final int OPERATION_SET_DEFAULT_SMDP_ADDRESS = 14;
    public static final int OPERATION_SET_NICKNAME = 7;
    public static final int OPERATION_SWITCH_TO_PROFILE = 10;
    public static final int OPERATION_UNKNOWN = 0;
    private final int mErrorCode;
    private final Asn1Node mErrorDetails;
    private final int mOperationCode;

    public EuiccCardErrorException(int n, int n2) {
        this.mOperationCode = n;
        this.mErrorCode = n2;
        this.mErrorDetails = null;
    }

    public EuiccCardErrorException(int n, int n2, Asn1Node asn1Node) {
        this.mOperationCode = n;
        this.mErrorCode = n2;
        this.mErrorDetails = asn1Node;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public Asn1Node getErrorDetails() {
        return this.mErrorDetails;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EuiccCardError: mOperatorCode=");
        stringBuilder.append(this.mOperationCode);
        stringBuilder.append(", mErrorCode=");
        stringBuilder.append(this.mErrorCode);
        stringBuilder.append(", errorDetails=");
        Object object = this.mErrorDetails;
        object = object == null ? "null" : object.toHex();
        stringBuilder.append((String)object);
        return stringBuilder.toString();
    }

    public int getOperationCode() {
        return this.mOperationCode;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OperationCode {
    }

}

