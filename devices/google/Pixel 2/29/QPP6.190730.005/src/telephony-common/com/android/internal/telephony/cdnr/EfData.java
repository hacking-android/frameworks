/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdnr;

import com.android.internal.telephony.uicc.IccRecords;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public interface EfData {
    public static final int EF_SOURCE_CARRIER_API = 2;
    public static final int EF_SOURCE_CARRIER_CONFIG = 1;
    public static final int EF_SOURCE_CSIM = 5;
    public static final int EF_SOURCE_DATA_OPERATOR_SIGNALLING = 8;
    public static final int EF_SOURCE_ERI = 10;
    public static final int EF_SOURCE_MODEM_CONFIG = 9;
    public static final int EF_SOURCE_RUIM = 6;
    public static final int EF_SOURCE_SIM = 4;
    public static final int EF_SOURCE_USIM = 3;
    public static final int EF_SOURCE_VOICE_OPERATOR_SIGNALLING = 7;

    default public List<String> getEhplmnList() {
        return null;
    }

    default public List<IccRecords.OperatorPlmnInfo> getOperatorPlmnList() {
        return null;
    }

    default public List<IccRecords.PlmnNetworkName> getPlmnNetworkNameList() {
        return null;
    }

    default public List<String> getServiceProviderDisplayInformation() {
        return null;
    }

    default public String getServiceProviderName() {
        return null;
    }

    default public int getServiceProviderNameDisplayCondition() {
        return -1;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EFSource {
    }

}

