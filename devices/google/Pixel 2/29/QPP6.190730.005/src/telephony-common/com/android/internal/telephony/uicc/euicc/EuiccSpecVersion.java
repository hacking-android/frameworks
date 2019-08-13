/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 *  com.android.internal.telephony.uicc.asn1.Asn1Decoder
 *  com.android.internal.telephony.uicc.asn1.Asn1Node
 *  com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException
 *  com.android.internal.telephony.uicc.asn1.TagNotFoundException
 */
package com.android.internal.telephony.uicc.euicc;

import android.telephony.Rlog;
import com.android.internal.telephony.uicc.asn1.Asn1Decoder;
import com.android.internal.telephony.uicc.asn1.Asn1Node;
import com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException;
import com.android.internal.telephony.uicc.asn1.TagNotFoundException;
import java.util.Arrays;

public final class EuiccSpecVersion
implements Comparable<EuiccSpecVersion> {
    private static final String LOG_TAG = "EuiccSpecVer";
    private static final int TAG_ISD_R_APP_TEMPLATE = 224;
    private static final int TAG_VERSION = 130;
    private final int[] mVersionValues;

    public EuiccSpecVersion(int n, int n2, int n3) {
        int[] arrn = this.mVersionValues = new int[3];
        arrn[0] = n;
        arrn[1] = n2;
        arrn[2] = n3;
    }

    public EuiccSpecVersion(byte[] arrby) {
        int[] arrn = this.mVersionValues = new int[3];
        arrn[0] = arrby[0] & 255;
        arrn[1] = arrby[1] & 255;
        arrn[2] = arrby[2] & 255;
    }

    public static EuiccSpecVersion fromOpenChannelResponse(byte[] object) {
        Asn1Decoder asn1Decoder;
        block6 : {
            try {
                asn1Decoder = new Asn1Decoder((byte[])object);
                if (asn1Decoder.hasNextNode()) break block6;
                return null;
            }
            catch (InvalidAsn1DataException invalidAsn1DataException) {
                Rlog.e((String)LOG_TAG, (String)"Cannot parse the select response of ISD-R.", (Throwable)invalidAsn1DataException);
                return null;
            }
        }
        asn1Decoder = asn1Decoder.nextNode();
        try {
            object = asn1Decoder.getTag() == 224 ? asn1Decoder.getChild(130, new int[0]).asBytes() : asn1Decoder.getChild(224, new int[]{130}).asBytes();
            if (((byte[])object).length == 3) {
                return new EuiccSpecVersion((byte[])object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot parse select response of ISD-R: ");
            ((StringBuilder)object).append(asn1Decoder.toHex());
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
        catch (InvalidAsn1DataException | TagNotFoundException throwable) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot parse select response of ISD-R: ");
            stringBuilder.append(asn1Decoder.toHex());
            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
        }
        return null;
    }

    @Override
    public int compareTo(EuiccSpecVersion euiccSpecVersion) {
        if (this.getMajor() > euiccSpecVersion.getMajor()) {
            return 1;
        }
        if (this.getMajor() < euiccSpecVersion.getMajor()) {
            return -1;
        }
        if (this.getMinor() > euiccSpecVersion.getMinor()) {
            return 1;
        }
        if (this.getMinor() < euiccSpecVersion.getMinor()) {
            return -1;
        }
        if (this.getRevision() > euiccSpecVersion.getRevision()) {
            return 1;
        }
        if (this.getRevision() < euiccSpecVersion.getRevision()) {
            return -1;
        }
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            return Arrays.equals(this.mVersionValues, ((EuiccSpecVersion)object).mVersionValues);
        }
        return false;
    }

    public int getMajor() {
        return this.mVersionValues[0];
    }

    public int getMinor() {
        return this.mVersionValues[1];
    }

    public int getRevision() {
        return this.mVersionValues[2];
    }

    public int hashCode() {
        return Arrays.hashCode(this.mVersionValues);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mVersionValues[0]);
        stringBuilder.append(".");
        stringBuilder.append(this.mVersionValues[1]);
        stringBuilder.append(".");
        stringBuilder.append(this.mVersionValues[2]);
        return stringBuilder.toString();
    }
}

