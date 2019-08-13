/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.ContentValues
 *  android.database.Cursor
 *  android.util.Pair
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.util.HexDump
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.HexDump;
import java.util.Arrays;
import java.util.Date;

public class InboundSmsTracker {
    private static final int DEST_PORT_FLAG_3GPP = 131072;
    @VisibleForTesting
    public static final int DEST_PORT_FLAG_3GPP2 = 262144;
    @VisibleForTesting
    public static final int DEST_PORT_FLAG_3GPP2_WAP_PDU = 524288;
    @VisibleForTesting
    public static final int DEST_PORT_FLAG_NO_PORT = 65536;
    private static final int DEST_PORT_MASK = 65535;
    @VisibleForTesting
    public static final String SELECT_BY_REFERENCE = "address=? AND reference_number=? AND count=? AND (destination_port & 524288=0) AND deleted=0";
    @VisibleForTesting
    public static final String SELECT_BY_REFERENCE_3GPP2WAP = "address=? AND reference_number=? AND count=? AND (destination_port & 524288=524288) AND deleted=0";
    private final String mAddress;
    private String mDeleteWhere;
    private String[] mDeleteWhereArgs;
    private final int mDestPort;
    private final String mDisplayAddress;
    private final boolean mIs3gpp2;
    private final boolean mIs3gpp2WapPdu;
    private final boolean mIsClass0;
    private final String mMessageBody;
    private final int mMessageCount;
    private final byte[] mPdu;
    private final int mReferenceNumber;
    private final int mSequenceNumber;
    private final long mTimestamp;

    public InboundSmsTracker(Cursor object, boolean bl) {
        block7 : {
            block6 : {
                int n;
                block5 : {
                    this.mPdu = HexDump.hexStringToByteArray((String)object.getString(0));
                    this.mIsClass0 = false;
                    if (object.isNull(2)) {
                        this.mDestPort = -1;
                        this.mIs3gpp2 = bl;
                        this.mIs3gpp2WapPdu = false;
                    } else {
                        n = object.getInt(2);
                        this.mIs3gpp2 = (131072 & n) != 0 ? false : ((262144 & n) != 0 ? true : bl);
                        bl = (524288 & n) != 0;
                        this.mIs3gpp2WapPdu = bl;
                        this.mDestPort = InboundSmsTracker.getRealDestPort(n);
                    }
                    this.mTimestamp = object.getLong(3);
                    this.mAddress = object.getString(6);
                    this.mDisplayAddress = object.getString(9);
                    if (object.getInt(5) != 1) break block5;
                    long l = object.getLong(7);
                    this.mReferenceNumber = -1;
                    this.mSequenceNumber = this.getIndexOffset();
                    this.mMessageCount = 1;
                    this.mDeleteWhere = "_id=?";
                    this.mDeleteWhereArgs = new String[]{Long.toString(l)};
                    break block6;
                }
                this.mReferenceNumber = object.getInt(4);
                this.mMessageCount = object.getInt(5);
                this.mSequenceNumber = object.getInt(1);
                n = this.mSequenceNumber - this.getIndexOffset();
                if (n < 0 || n >= this.mMessageCount) break block7;
                this.mDeleteWhere = this.getQueryForSegments();
                this.mDeleteWhereArgs = new String[]{this.mAddress, Integer.toString(this.mReferenceNumber), Integer.toString(this.mMessageCount)};
            }
            this.mMessageBody = object.getString(8);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("invalid PDU sequence ");
        ((StringBuilder)object).append(this.mSequenceNumber);
        ((StringBuilder)object).append(" of ");
        ((StringBuilder)object).append(this.mMessageCount);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public InboundSmsTracker(byte[] arrby, long l, int n, boolean bl, String string, String string2, int n2, int n3, int n4, boolean bl2, String string3, boolean bl3) {
        this.mPdu = arrby;
        this.mTimestamp = l;
        this.mDestPort = n;
        this.mIs3gpp2 = bl;
        this.mIs3gpp2WapPdu = bl2;
        this.mMessageBody = string3;
        this.mIsClass0 = bl3;
        this.mDisplayAddress = string2;
        this.mAddress = string;
        this.mReferenceNumber = n2;
        this.mSequenceNumber = n3;
        this.mMessageCount = n4;
    }

    public InboundSmsTracker(byte[] arrby, long l, int n, boolean bl, boolean bl2, String string, String string2, String string3, boolean bl3) {
        this.mPdu = arrby;
        this.mTimestamp = l;
        this.mDestPort = n;
        this.mIs3gpp2 = bl;
        this.mIs3gpp2WapPdu = bl2;
        this.mMessageBody = string3;
        this.mAddress = string;
        this.mDisplayAddress = string2;
        this.mIsClass0 = bl3;
        this.mReferenceNumber = -1;
        this.mSequenceNumber = this.getIndexOffset();
        this.mMessageCount = 1;
    }

    private String addDestPortQuery(String string) {
        String string2 = this.mIs3gpp2WapPdu ? "destination_port & 524288=524288" : "destination_port & 524288=0";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" AND (");
        stringBuilder.append(string2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static int getRealDestPort(int n) {
        if ((65536 & n) != 0) {
            return -1;
        }
        return 65535 & n;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("pdu", HexDump.toHexString((byte[])this.mPdu));
        contentValues.put("date", Long.valueOf(this.mTimestamp));
        int n = this.mDestPort;
        n = n == -1 ? 65536 : (n &= 65535);
        n = this.mIs3gpp2 ? (n |= 262144) : (n |= 131072);
        int n2 = n;
        if (this.mIs3gpp2WapPdu) {
            n2 = n | 524288;
        }
        contentValues.put("destination_port", Integer.valueOf(n2));
        String string = this.mAddress;
        if (string != null) {
            contentValues.put("address", string);
            contentValues.put("display_originating_addr", this.mDisplayAddress);
            contentValues.put("reference_number", Integer.valueOf(this.mReferenceNumber));
            contentValues.put("sequence", Integer.valueOf(this.mSequenceNumber));
        }
        contentValues.put("count", Integer.valueOf(this.mMessageCount));
        contentValues.put("message_body", this.mMessageBody);
        return contentValues;
    }

    public String getDeleteWhere() {
        return this.mDeleteWhere;
    }

    public String[] getDeleteWhereArgs() {
        return this.mDeleteWhereArgs;
    }

    public int getDestPort() {
        return this.mDestPort;
    }

    public String getDisplayAddress() {
        return this.mDisplayAddress;
    }

    public Pair<String, String[]> getExactMatchDupDetectQuery() {
        String string = this.getAddress();
        String string2 = Integer.toString(this.getReferenceNumber());
        String string3 = Integer.toString(this.getMessageCount());
        String string4 = Integer.toString(this.getSequenceNumber());
        String string5 = Long.toString(this.getTimestamp());
        String string6 = this.getMessageBody();
        return new Pair((Object)this.addDestPortQuery("address=? AND reference_number=? AND count=? AND sequence=? AND date=? AND message_body=?"), (Object)new String[]{string, string2, string3, string4, string5, string6});
    }

    @UnsupportedAppUsage
    public String getFormat() {
        String string = this.mIs3gpp2 ? "3gpp2" : "3gpp";
        return string;
    }

    @UnsupportedAppUsage
    public int getIndexOffset() {
        int n = this.mIs3gpp2 && this.mIs3gpp2WapPdu ? 0 : 1;
        return n;
    }

    public Pair<String, String[]> getInexactMatchDupDetectQuery() {
        if (this.getMessageCount() == 1) {
            return null;
        }
        String string = this.getAddress();
        String string2 = Integer.toString(this.getReferenceNumber());
        String string3 = Integer.toString(this.getMessageCount());
        String string4 = Integer.toString(this.getSequenceNumber());
        return new Pair((Object)this.addDestPortQuery("address=? AND reference_number=? AND count=? AND sequence=? AND deleted=0"), (Object)new String[]{string, string2, string3, string4});
    }

    public String getMessageBody() {
        return this.mMessageBody;
    }

    public int getMessageCount() {
        return this.mMessageCount;
    }

    public byte[] getPdu() {
        return this.mPdu;
    }

    public String getQueryForSegments() {
        String string = this.mIs3gpp2WapPdu ? SELECT_BY_REFERENCE_3GPP2WAP : SELECT_BY_REFERENCE;
        return string;
    }

    public int getReferenceNumber() {
        return this.mReferenceNumber;
    }

    public int getSequenceNumber() {
        return this.mSequenceNumber;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public boolean is3gpp2() {
        return this.mIs3gpp2;
    }

    public boolean isClass0() {
        return this.mIsClass0;
    }

    public void setDeleteWhere(String string, String[] arrstring) {
        this.mDeleteWhere = string;
        this.mDeleteWhereArgs = arrstring;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("SmsTracker{timestamp=");
        stringBuilder.append(new Date(this.mTimestamp));
        stringBuilder.append(" destPort=");
        stringBuilder.append(this.mDestPort);
        stringBuilder.append(" is3gpp2=");
        stringBuilder.append(this.mIs3gpp2);
        stringBuilder.append(" display_originating_addr=");
        stringBuilder.append(this.mDisplayAddress);
        stringBuilder.append(" refNumber=");
        stringBuilder.append(this.mReferenceNumber);
        stringBuilder.append(" seqNumber=");
        stringBuilder.append(this.mSequenceNumber);
        stringBuilder.append(" msgCount=");
        stringBuilder.append(this.mMessageCount);
        if (this.mDeleteWhere != null) {
            stringBuilder.append(" deleteWhere(");
            stringBuilder.append(this.mDeleteWhere);
            stringBuilder.append(") deleteArgs=(");
            stringBuilder.append(Arrays.toString(this.mDeleteWhereArgs));
            stringBuilder.append(')');
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

