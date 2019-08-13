/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.text.TextUtils
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.text.TextUtils;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.Arrays;

public class AdnRecord
implements Parcelable {
    static final int ADN_BCD_NUMBER_LENGTH = 0;
    static final int ADN_CAPABILITY_ID = 12;
    static final int ADN_DIALING_NUMBER_END = 11;
    static final int ADN_DIALING_NUMBER_START = 2;
    static final int ADN_EXTENSION_ID = 13;
    static final int ADN_TON_AND_NPI = 1;
    @UnsupportedAppUsage
    public static final Parcelable.Creator<AdnRecord> CREATOR = new Parcelable.Creator<AdnRecord>(){

        public AdnRecord createFromParcel(Parcel parcel) {
            return new AdnRecord(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readStringArray());
        }

        public AdnRecord[] newArray(int n) {
            return new AdnRecord[n];
        }
    };
    static final int EXT_RECORD_LENGTH_BYTES = 13;
    static final int EXT_RECORD_TYPE_ADDITIONAL_DATA = 2;
    static final int EXT_RECORD_TYPE_MASK = 3;
    static final int FOOTER_SIZE_BYTES = 14;
    static final String LOG_TAG = "AdnRecord";
    static final int MAX_EXT_CALLED_PARTY_LENGTH = 10;
    static final int MAX_NUMBER_SIZE_BYTES = 11;
    @UnsupportedAppUsage
    String mAlphaTag = null;
    @UnsupportedAppUsage
    int mEfid;
    @UnsupportedAppUsage
    String[] mEmails;
    @UnsupportedAppUsage
    int mExtRecord = 255;
    @UnsupportedAppUsage
    String mNumber = null;
    @UnsupportedAppUsage
    int mRecordNumber;

    @UnsupportedAppUsage
    public AdnRecord(int n, int n2, String string, String string2) {
        this.mEfid = n;
        this.mRecordNumber = n2;
        this.mAlphaTag = string;
        this.mNumber = string2;
        this.mEmails = null;
    }

    @UnsupportedAppUsage
    public AdnRecord(int n, int n2, String string, String string2, String[] arrstring) {
        this.mEfid = n;
        this.mRecordNumber = n2;
        this.mAlphaTag = string;
        this.mNumber = string2;
        this.mEmails = arrstring;
    }

    @UnsupportedAppUsage
    public AdnRecord(int n, int n2, byte[] arrby) {
        this.mEfid = n;
        this.mRecordNumber = n2;
        this.parseRecord(arrby);
    }

    @UnsupportedAppUsage
    public AdnRecord(String string, String string2) {
        this(0, 0, string, string2);
    }

    @UnsupportedAppUsage
    public AdnRecord(String string, String string2, String[] arrstring) {
        this(0, 0, string, string2, arrstring);
    }

    @UnsupportedAppUsage
    public AdnRecord(byte[] arrby) {
        this(0, 0, arrby);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void parseRecord(byte[] var1_1) {
        this.mAlphaTag = IccUtils.adnStringFieldToString((byte[])var1_1, (int)0, (int)(var1_1.length - 14));
        var2_3 = var1_1.length - 14;
        var3_4 = var1_1[var2_3] & 255;
        if (var3_4 <= 11) ** GOTO lbl9
        try {
            this.mNumber = "";
            return;
lbl9: // 1 sources:
            this.mNumber = PhoneNumberUtils.calledPartyBCDToString((byte[])var1_1, (int)(var2_3 + 1), (int)var3_4, (int)1);
            this.mExtRecord = var1_1[var1_1.length - 1] & 255;
            this.mEmails = null;
            return;
        }
        catch (RuntimeException var1_2) {
            Rlog.w((String)"AdnRecord", (String)"Error parsing AdnRecord", (Throwable)var1_2);
            this.mNumber = "";
            this.mAlphaTag = "";
            this.mEmails = null;
        }
    }

    private static boolean stringCompareNullEqualsEmpty(String string, String string2) {
        if (string == string2) {
            return true;
        }
        String string3 = string;
        if (string == null) {
            string3 = "";
        }
        string = string2;
        if (string2 == null) {
            string = "";
        }
        return string3.equals(string);
    }

    public void appendExtRecord(byte[] arrby) {
        block5 : {
            if (arrby.length == 13) break block5;
            return;
        }
        if ((arrby[0] & 3) != 2) {
            return;
        }
        if ((arrby[1] & 255) > 10) {
            return;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mNumber);
            stringBuilder.append(PhoneNumberUtils.calledPartyBCDFragmentToString((byte[])arrby, (int)2, (int)(arrby[1] & 255), (int)1));
            this.mNumber = stringBuilder.toString();
        }
        catch (RuntimeException runtimeException) {
            Rlog.w((String)LOG_TAG, (String)"Error parsing AdnRecord ext record", (Throwable)runtimeException);
        }
    }

    @UnsupportedAppUsage
    public byte[] buildAdnString(int n) {
        int n2 = n - 14;
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)-1;
        }
        if (TextUtils.isEmpty((CharSequence)this.mNumber)) {
            Rlog.w((String)LOG_TAG, (String)"[buildAdnString] Empty dialing number");
            return arrby;
        }
        if (this.mNumber.length() > 20) {
            Rlog.w((String)LOG_TAG, (String)"[buildAdnString] Max length of dialing number is 20");
            return null;
        }
        Object object = !TextUtils.isEmpty((CharSequence)this.mAlphaTag) ? GsmAlphabet.stringToGsm8BitPacked((String)this.mAlphaTag) : new byte[0];
        if (((byte[])object).length > n2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[buildAdnString] Max length of tag is ");
            ((StringBuilder)object).append(n2);
            Rlog.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return null;
        }
        byte[] arrby2 = PhoneNumberUtils.numberToCalledPartyBCD((String)this.mNumber, (int)1);
        System.arraycopy(arrby2, 0, arrby, n2 + 1, arrby2.length);
        arrby[n2 + 0] = (byte)arrby2.length;
        arrby[n2 + 12] = (byte)-1;
        arrby[n2 + 13] = (byte)-1;
        if (((Object)object).length > 0) {
            System.arraycopy(object, 0, arrby, 0, ((Object)object).length);
        }
        return arrby;
    }

    public int describeContents() {
        return 0;
    }

    public String getAlphaTag() {
        return this.mAlphaTag;
    }

    public int getEfid() {
        return this.mEfid;
    }

    @UnsupportedAppUsage
    public String[] getEmails() {
        return this.mEmails;
    }

    @UnsupportedAppUsage
    public String getNumber() {
        return this.mNumber;
    }

    public int getRecId() {
        return this.mRecordNumber;
    }

    public boolean hasExtendedRecord() {
        int n = this.mExtRecord;
        boolean bl = n != 0 && n != 255;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isEmpty() {
        boolean bl = TextUtils.isEmpty((CharSequence)this.mAlphaTag) && TextUtils.isEmpty((CharSequence)this.mNumber) && this.mEmails == null;
        return bl;
    }

    public boolean isEqual(AdnRecord adnRecord) {
        boolean bl = AdnRecord.stringCompareNullEqualsEmpty(this.mAlphaTag, adnRecord.mAlphaTag) && AdnRecord.stringCompareNullEqualsEmpty(this.mNumber, adnRecord.mNumber) && Arrays.equals(this.mEmails, adnRecord.mEmails);
        return bl;
    }

    @UnsupportedAppUsage
    public void setEmails(String[] arrstring) {
        this.mEmails = arrstring;
    }

    public void setNumber(String string) {
        this.mNumber = string;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ADN Record '");
        stringBuilder.append(this.mAlphaTag);
        stringBuilder.append("' '");
        stringBuilder.append(Rlog.pii((String)LOG_TAG, (Object)this.mNumber));
        stringBuilder.append(" ");
        stringBuilder.append(Rlog.pii((String)LOG_TAG, (Object)this.mEmails));
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mEfid);
        parcel.writeInt(this.mRecordNumber);
        parcel.writeString(this.mAlphaTag);
        parcel.writeString(this.mNumber);
        parcel.writeStringArray(this.mEmails);
    }

}

