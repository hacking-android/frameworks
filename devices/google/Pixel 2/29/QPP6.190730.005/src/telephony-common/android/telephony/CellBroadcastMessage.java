/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.telephony.SmsCbCmasInfo
 *  android.telephony.SmsCbEtwsInfo
 *  android.telephony.SmsCbLocation
 *  android.telephony.SmsCbMessage
 *  android.text.format.DateUtils
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SmsCbCmasInfo;
import android.telephony.SmsCbEtwsInfo;
import android.telephony.SmsCbLocation;
import android.telephony.SmsCbMessage;
import android.text.format.DateUtils;

public class CellBroadcastMessage
implements Parcelable {
    public static final Parcelable.Creator<CellBroadcastMessage> CREATOR = new Parcelable.Creator<CellBroadcastMessage>(){

        public CellBroadcastMessage createFromParcel(Parcel parcel) {
            return new CellBroadcastMessage(parcel);
        }

        public CellBroadcastMessage[] newArray(int n) {
            return new CellBroadcastMessage[n];
        }
    };
    public static final String SMS_CB_MESSAGE_EXTRA = "com.android.cellbroadcastreceiver.SMS_CB_MESSAGE";
    private final long mDeliveryTime;
    private boolean mIsRead;
    private final SmsCbMessage mSmsCbMessage;
    private int mSubId;

    private CellBroadcastMessage(Parcel parcel) {
        boolean bl = false;
        this.mSubId = 0;
        this.mSmsCbMessage = new SmsCbMessage(parcel);
        this.mDeliveryTime = parcel.readLong();
        if (parcel.readInt() != 0) {
            bl = true;
        }
        this.mIsRead = bl;
        this.mSubId = parcel.readInt();
    }

    @UnsupportedAppUsage
    public CellBroadcastMessage(SmsCbMessage smsCbMessage) {
        this.mSubId = 0;
        this.mSmsCbMessage = smsCbMessage;
        this.mDeliveryTime = System.currentTimeMillis();
        this.mIsRead = false;
    }

    private CellBroadcastMessage(SmsCbMessage smsCbMessage, long l, boolean bl) {
        this.mSubId = 0;
        this.mSmsCbMessage = smsCbMessage;
        this.mDeliveryTime = l;
        this.mIsRead = bl;
    }

    @UnsupportedAppUsage
    public static CellBroadcastMessage createFromCursor(Cursor cursor) {
        SmsCbCmasInfo smsCbCmasInfo;
        int n = cursor.getInt(cursor.getColumnIndexOrThrow("geo_scope"));
        int n2 = cursor.getInt(cursor.getColumnIndexOrThrow("serial_number"));
        int n3 = cursor.getInt(cursor.getColumnIndexOrThrow("service_category"));
        String string = cursor.getString(cursor.getColumnIndexOrThrow("language"));
        String string2 = cursor.getString(cursor.getColumnIndexOrThrow("body"));
        int n4 = cursor.getInt(cursor.getColumnIndexOrThrow("format"));
        int n5 = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        int n6 = cursor.getColumnIndex("plmn");
        String string3 = n6 != -1 && !cursor.isNull(n6) ? cursor.getString(n6) : null;
        n6 = cursor.getColumnIndex("lac");
        n6 = n6 != -1 && !cursor.isNull(n6) ? cursor.getInt(n6) : -1;
        int n7 = cursor.getColumnIndex("cid");
        n7 = n7 != -1 && !cursor.isNull(n7) ? cursor.getInt(n7) : -1;
        SmsCbLocation smsCbLocation = new SmsCbLocation(string3, n6, n7);
        n6 = cursor.getColumnIndex("etws_warning_type");
        string3 = n6 != -1 && !cursor.isNull(n6) ? new SmsCbEtwsInfo(cursor.getInt(n6), false, false, false, null) : null;
        n6 = cursor.getColumnIndex("cmas_message_class");
        if (n6 != -1 && !cursor.isNull(n6)) {
            int n8 = cursor.getInt(n6);
            n6 = cursor.getColumnIndex("cmas_category");
            n6 = n6 != -1 && !cursor.isNull(n6) ? cursor.getInt(n6) : -1;
            n7 = cursor.getColumnIndex("cmas_response_type");
            n7 = n7 != -1 && !cursor.isNull(n7) ? cursor.getInt(n7) : -1;
            int n9 = cursor.getColumnIndex("cmas_severity");
            n9 = n9 != -1 && !cursor.isNull(n9) ? cursor.getInt(n9) : -1;
            int n10 = cursor.getColumnIndex("cmas_urgency");
            n10 = n10 != -1 && !cursor.isNull(n10) ? cursor.getInt(n10) : -1;
            int n11 = cursor.getColumnIndex("cmas_certainty");
            n11 = n11 != -1 && !cursor.isNull(n11) ? cursor.getInt(n11) : -1;
            smsCbCmasInfo = new SmsCbCmasInfo(n8, n6, n7, n9, n10, n11);
        } else {
            smsCbCmasInfo = null;
        }
        string3 = new SmsCbMessage(n4, n, n2, smsCbLocation, n3, string, string2, n5, (SmsCbEtwsInfo)string3, smsCbCmasInfo);
        long l = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
        boolean bl = cursor.getInt(cursor.getColumnIndexOrThrow("read")) != 0;
        return new CellBroadcastMessage((SmsCbMessage)string3, l, bl);
    }

    public int describeContents() {
        return 0;
    }

    public int getCmasMessageClass() {
        if (this.mSmsCbMessage.isCmasMessage()) {
            return this.mSmsCbMessage.getCmasWarningInfo().getMessageClass();
        }
        return -1;
    }

    public SmsCbCmasInfo getCmasWarningInfo() {
        return this.mSmsCbMessage.getCmasWarningInfo();
    }

    @UnsupportedAppUsage
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues(16);
        SmsCbMessage smsCbMessage = this.mSmsCbMessage;
        contentValues.put("geo_scope", Integer.valueOf(smsCbMessage.getGeographicalScope()));
        SmsCbLocation smsCbLocation = smsCbMessage.getLocation();
        if (smsCbLocation.getPlmn() != null) {
            contentValues.put("plmn", smsCbLocation.getPlmn());
        }
        if (smsCbLocation.getLac() != -1) {
            contentValues.put("lac", Integer.valueOf(smsCbLocation.getLac()));
        }
        if (smsCbLocation.getCid() != -1) {
            contentValues.put("cid", Integer.valueOf(smsCbLocation.getCid()));
        }
        contentValues.put("serial_number", Integer.valueOf(smsCbMessage.getSerialNumber()));
        contentValues.put("service_category", Integer.valueOf(smsCbMessage.getServiceCategory()));
        contentValues.put("language", smsCbMessage.getLanguageCode());
        contentValues.put("body", smsCbMessage.getMessageBody());
        contentValues.put("date", Long.valueOf(this.mDeliveryTime));
        contentValues.put("read", Boolean.valueOf(this.mIsRead));
        contentValues.put("format", Integer.valueOf(smsCbMessage.getMessageFormat()));
        contentValues.put("priority", Integer.valueOf(smsCbMessage.getMessagePriority()));
        smsCbLocation = this.mSmsCbMessage.getEtwsWarningInfo();
        if (smsCbLocation != null) {
            contentValues.put("etws_warning_type", Integer.valueOf(smsCbLocation.getWarningType()));
        }
        if ((smsCbLocation = this.mSmsCbMessage.getCmasWarningInfo()) != null) {
            contentValues.put("cmas_message_class", Integer.valueOf(smsCbLocation.getMessageClass()));
            contentValues.put("cmas_category", Integer.valueOf(smsCbLocation.getCategory()));
            contentValues.put("cmas_response_type", Integer.valueOf(smsCbLocation.getResponseType()));
            contentValues.put("cmas_severity", Integer.valueOf(smsCbLocation.getSeverity()));
            contentValues.put("cmas_urgency", Integer.valueOf(smsCbLocation.getUrgency()));
            contentValues.put("cmas_certainty", Integer.valueOf(smsCbLocation.getCertainty()));
        }
        return contentValues;
    }

    public String getDateString(Context context) {
        return DateUtils.formatDateTime((Context)context, (long)this.mDeliveryTime, (int)527121);
    }

    @UnsupportedAppUsage
    public long getDeliveryTime() {
        return this.mDeliveryTime;
    }

    @UnsupportedAppUsage
    public SmsCbEtwsInfo getEtwsWarningInfo() {
        return this.mSmsCbMessage.getEtwsWarningInfo();
    }

    @UnsupportedAppUsage
    public String getLanguageCode() {
        return this.mSmsCbMessage.getLanguageCode();
    }

    @UnsupportedAppUsage
    public String getMessageBody() {
        return this.mSmsCbMessage.getMessageBody();
    }

    @UnsupportedAppUsage
    public int getSerialNumber() {
        return this.mSmsCbMessage.getSerialNumber();
    }

    @UnsupportedAppUsage
    public int getServiceCategory() {
        return this.mSmsCbMessage.getServiceCategory();
    }

    @UnsupportedAppUsage
    public String getSpokenDateString(Context context) {
        return DateUtils.formatDateTime((Context)context, (long)this.mDeliveryTime, (int)17);
    }

    public int getSubId() {
        return this.mSubId;
    }

    @UnsupportedAppUsage
    public boolean isCmasMessage() {
        return this.mSmsCbMessage.isCmasMessage();
    }

    @UnsupportedAppUsage
    public boolean isEmergencyAlertMessage() {
        return this.mSmsCbMessage.isEmergencyMessage();
    }

    public boolean isEtwsEmergencyUserAlert() {
        SmsCbEtwsInfo smsCbEtwsInfo = this.mSmsCbMessage.getEtwsWarningInfo();
        boolean bl = smsCbEtwsInfo != null && smsCbEtwsInfo.isEmergencyUserAlert();
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isEtwsMessage() {
        return this.mSmsCbMessage.isEtwsMessage();
    }

    public boolean isEtwsPopupAlert() {
        SmsCbEtwsInfo smsCbEtwsInfo = this.mSmsCbMessage.getEtwsWarningInfo();
        boolean bl = smsCbEtwsInfo != null && smsCbEtwsInfo.isPopupAlert();
        return bl;
    }

    public boolean isEtwsTestMessage() {
        SmsCbEtwsInfo smsCbEtwsInfo = this.mSmsCbMessage.getEtwsWarningInfo();
        boolean bl = smsCbEtwsInfo != null && smsCbEtwsInfo.getWarningType() == 3;
        return bl;
    }

    public boolean isPublicAlertMessage() {
        return this.mSmsCbMessage.isEmergencyMessage();
    }

    @UnsupportedAppUsage
    public boolean isRead() {
        return this.mIsRead;
    }

    public void setIsRead(boolean bl) {
        this.mIsRead = bl;
    }

    public void setSubId(int n) {
        this.mSubId = n;
    }

    public void writeToParcel(Parcel parcel, int n) {
        this.mSmsCbMessage.writeToParcel(parcel, n);
        parcel.writeLong(this.mDeliveryTime);
        parcel.writeInt((int)this.mIsRead);
        parcel.writeInt(this.mSubId);
    }

}

