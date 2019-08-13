/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.telephony.Rlog;
import android.telephony.UiccAccessRule;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SubscriptionInfo
implements Parcelable {
    public static final Parcelable.Creator<SubscriptionInfo> CREATOR = new Parcelable.Creator<SubscriptionInfo>(){

        @Override
        public SubscriptionInfo createFromParcel(Parcel object) {
            int n = ((Parcel)object).readInt();
            String string2 = ((Parcel)object).readString();
            int n2 = ((Parcel)object).readInt();
            CharSequence charSequence = ((Parcel)object).readCharSequence();
            CharSequence charSequence2 = ((Parcel)object).readCharSequence();
            int n3 = ((Parcel)object).readInt();
            int n4 = ((Parcel)object).readInt();
            String string3 = ((Parcel)object).readString();
            int n5 = ((Parcel)object).readInt();
            String string4 = ((Parcel)object).readString();
            String string5 = ((Parcel)object).readString();
            String string6 = ((Parcel)object).readString();
            Bitmap bitmap = (Bitmap)((Parcel)object).readParcelable(Bitmap.class.getClassLoader());
            boolean bl = ((Parcel)object).readBoolean();
            UiccAccessRule[] arruiccAccessRule = ((Parcel)object).createTypedArray(UiccAccessRule.CREATOR);
            String string7 = ((Parcel)object).readString();
            int n6 = ((Parcel)object).readInt();
            boolean bl2 = ((Parcel)object).readBoolean();
            String string8 = ((Parcel)object).readString();
            boolean bl3 = ((Parcel)object).readBoolean();
            int n7 = ((Parcel)object).readInt();
            int n8 = ((Parcel)object).readInt();
            int n9 = ((Parcel)object).readInt();
            String[] arrstring = ((Parcel)object).readStringArray();
            String[] arrstring2 = ((Parcel)object).readStringArray();
            object = new SubscriptionInfo(n, string2, n2, charSequence, charSequence2, n3, n4, string3, n5, bitmap, string4, string5, string6, bl, arruiccAccessRule, string7, n6, bl2, string8, bl3, n7, n8, n9, ((Parcel)object).readString());
            ((SubscriptionInfo)object).setAssociatedPlmns(arrstring, arrstring2);
            return object;
        }

        public SubscriptionInfo[] newArray(int n) {
            return new SubscriptionInfo[n];
        }
    };
    private static final int TEXT_SIZE = 16;
    private UiccAccessRule[] mAccessRules;
    private int mCardId;
    private String mCardString;
    private int mCarrierId;
    private CharSequence mCarrierName;
    private String mCountryIso;
    private int mDataRoaming;
    private CharSequence mDisplayName;
    private String[] mEhplmns;
    private String mGroupOwner;
    private ParcelUuid mGroupUUID;
    private String[] mHplmns;
    private String mIccId;
    private Bitmap mIconBitmap;
    private int mIconTint;
    private int mId;
    private boolean mIsEmbedded;
    private boolean mIsGroupDisabled = false;
    private boolean mIsOpportunistic;
    private String mMcc;
    private String mMnc;
    private int mNameSource;
    private String mNumber;
    private int mProfileClass;
    private int mSimSlotIndex;
    private int mSubscriptionType;

    public SubscriptionInfo(int n, String string2, int n2, CharSequence charSequence, CharSequence charSequence2, int n3, int n4, String string3, int n5, Bitmap bitmap, String string4, String string5, String string6, boolean bl, UiccAccessRule[] arruiccAccessRule, String string7) {
        this(n, string2, n2, charSequence, charSequence2, n3, n4, string3, n5, bitmap, string4, string5, string6, bl, arruiccAccessRule, string7, -1, false, null, false, -1, -1, 0, null);
    }

    public SubscriptionInfo(int n, String object, int n2, CharSequence charSequence, CharSequence charSequence2, int n3, int n4, String string2, int n5, Bitmap bitmap, String string3, String string4, String string5, boolean bl, UiccAccessRule[] arruiccAccessRule, String string6, int n6, boolean bl2, String string7, boolean bl3, int n7, int n8, int n9, String string8) {
        this.mId = n;
        this.mIccId = object;
        this.mSimSlotIndex = n2;
        this.mDisplayName = charSequence;
        this.mCarrierName = charSequence2;
        this.mNameSource = n3;
        this.mIconTint = n4;
        this.mNumber = string2;
        this.mDataRoaming = n5;
        this.mIconBitmap = bitmap;
        this.mMcc = string3;
        this.mMnc = string4;
        this.mCountryIso = string5;
        this.mIsEmbedded = bl;
        this.mAccessRules = arruiccAccessRule;
        this.mCardString = string6;
        this.mCardId = n6;
        this.mIsOpportunistic = bl2;
        object = string7 == null ? null : ParcelUuid.fromString(string7);
        this.mGroupUUID = object;
        this.mIsGroupDisabled = bl3;
        this.mCarrierId = n7;
        this.mProfileClass = n8;
        this.mSubscriptionType = n9;
        this.mGroupOwner = string8;
    }

    public SubscriptionInfo(int n, String string2, int n2, CharSequence charSequence, CharSequence charSequence2, int n3, int n4, String string3, int n5, Bitmap bitmap, String string4, String string5, String string6, boolean bl, UiccAccessRule[] arruiccAccessRule, String string7, boolean bl2, String string8, int n6, int n7) {
        this(n, string2, n2, charSequence, charSequence2, n3, n4, string3, n5, bitmap, string4, string5, string6, bl, arruiccAccessRule, string7, -1, bl2, string8, false, n6, n7, 0, null);
    }

    public static String givePrintableIccid(String string2) {
        CharSequence charSequence = null;
        if (string2 != null) {
            if (string2.length() > 9 && !Build.IS_DEBUGGABLE) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2.substring(0, 9));
                ((StringBuilder)charSequence).append(Rlog.pii(false, (Object)string2.substring(9)));
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = string2;
            }
        }
        return charSequence;
    }

    @Deprecated
    public boolean canManageSubscription(Context context) {
        return this.canManageSubscription(context, context.getPackageName());
    }

    @Deprecated
    public boolean canManageSubscription(Context object, String arruiccAccessRule) {
        if (this.isEmbedded()) {
            if (this.mAccessRules == null) {
                return false;
            }
            object = ((Context)object).getPackageManager();
            try {
                object = ((PackageManager)object).getPackageInfo((String)arruiccAccessRule, 64);
                arruiccAccessRule = this.mAccessRules;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown package: ");
                ((StringBuilder)object).append((String)arruiccAccessRule);
                throw new IllegalArgumentException(((StringBuilder)object).toString(), nameNotFoundException);
            }
            int n = arruiccAccessRule.length;
            for (int i = 0; i < n; ++i) {
                if (arruiccAccessRule[i].getCarrierPrivilegeStatus((PackageInfo)object) != 1) continue;
                return true;
            }
            return false;
        }
        throw new UnsupportedOperationException("Not an embedded subscription");
    }

    public Bitmap createIconBitmap(Context object) {
        int n = this.mIconBitmap.getWidth();
        int n2 = this.mIconBitmap.getHeight();
        Object object2 = ((Context)object).getResources().getDisplayMetrics();
        Bitmap bitmap = Bitmap.createBitmap((DisplayMetrics)object2, n, n2, this.mIconBitmap.getConfig());
        object = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(this.mIconTint, PorterDuff.Mode.SRC_ATOP));
        ((Canvas)object).drawBitmap(this.mIconBitmap, 0.0f, 0.0f, paint);
        paint.setColorFilter(null);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create("sans-serif", 0));
        paint.setColor(-1);
        paint.setTextSize(((DisplayMetrics)object2).density * 16.0f);
        object2 = String.format("%d", this.mSimSlotIndex + 1);
        Rect rect = new Rect();
        paint.getTextBounds((String)object2, 0, 1, rect);
        ((Canvas)object).drawText((String)object2, (float)n / 2.0f - (float)rect.centerX(), (float)n2 / 2.0f - (float)rect.centerY(), paint);
        return bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        try {
            object = (SubscriptionInfo)object;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
        if (this.mId == ((SubscriptionInfo)object).mId && this.mSimSlotIndex == ((SubscriptionInfo)object).mSimSlotIndex && this.mNameSource == ((SubscriptionInfo)object).mNameSource && this.mIconTint == ((SubscriptionInfo)object).mIconTint && this.mDataRoaming == ((SubscriptionInfo)object).mDataRoaming && this.mIsEmbedded == ((SubscriptionInfo)object).mIsEmbedded && this.mIsOpportunistic == ((SubscriptionInfo)object).mIsOpportunistic && this.mIsGroupDisabled == ((SubscriptionInfo)object).mIsGroupDisabled && this.mCarrierId == ((SubscriptionInfo)object).mCarrierId && Objects.equals(this.mGroupUUID, ((SubscriptionInfo)object).mGroupUUID) && Objects.equals(this.mIccId, ((SubscriptionInfo)object).mIccId) && Objects.equals(this.mNumber, ((SubscriptionInfo)object).mNumber) && Objects.equals(this.mMcc, ((SubscriptionInfo)object).mMcc) && Objects.equals(this.mMnc, ((SubscriptionInfo)object).mMnc) && Objects.equals(this.mCountryIso, ((SubscriptionInfo)object).mCountryIso) && Objects.equals(this.mCardString, ((SubscriptionInfo)object).mCardString) && Objects.equals(this.mCardId, ((SubscriptionInfo)object).mCardId) && Objects.equals(this.mGroupOwner, ((SubscriptionInfo)object).mGroupOwner) && TextUtils.equals(this.mDisplayName, ((SubscriptionInfo)object).mDisplayName) && TextUtils.equals(this.mCarrierName, ((SubscriptionInfo)object).mCarrierName) && Arrays.equals(this.mAccessRules, ((SubscriptionInfo)object).mAccessRules) && this.mProfileClass == ((SubscriptionInfo)object).mProfileClass && Arrays.equals(this.mEhplmns, ((SubscriptionInfo)object).mEhplmns) && Arrays.equals(this.mHplmns, ((SubscriptionInfo)object).mHplmns)) {
            bl = true;
        }
        return bl;
    }

    @SystemApi
    public List<UiccAccessRule> getAccessRules() {
        if (this.isEmbedded()) {
            UiccAccessRule[] arruiccAccessRule = this.mAccessRules;
            if (arruiccAccessRule == null) {
                return null;
            }
            return Arrays.asList(arruiccAccessRule);
        }
        throw new UnsupportedOperationException("Not an embedded subscription");
    }

    public int getCardId() {
        return this.mCardId;
    }

    public String getCardString() {
        return this.mCardString;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public CharSequence getCarrierName() {
        return this.mCarrierName;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }

    public int getDataRoaming() {
        return this.mDataRoaming;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public List<String> getEhplmns() {
        Object object = this.mEhplmns;
        object = object == null ? Collections.emptyList() : Arrays.asList(object);
        return object;
    }

    public String getGroupOwner() {
        return this.mGroupOwner;
    }

    public ParcelUuid getGroupUuid() {
        return this.mGroupUUID;
    }

    public List<String> getHplmns() {
        Object object = this.mHplmns;
        object = object == null ? Collections.emptyList() : Arrays.asList(object);
        return object;
    }

    public String getIccId() {
        return this.mIccId;
    }

    public int getIconTint() {
        return this.mIconTint;
    }

    @Deprecated
    public int getMcc() {
        int n = 0;
        try {
            if (this.mMcc != null) {
                n = Integer.valueOf(this.mMcc);
            }
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            Log.w(SubscriptionInfo.class.getSimpleName(), "MCC string is not a number");
            return 0;
        }
    }

    public String getMccString() {
        return this.mMcc;
    }

    @Deprecated
    public int getMnc() {
        int n = 0;
        try {
            if (this.mMnc != null) {
                n = Integer.valueOf(this.mMnc);
            }
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            Log.w(SubscriptionInfo.class.getSimpleName(), "MNC string is not a number");
            return 0;
        }
    }

    public String getMncString() {
        return this.mMnc;
    }

    @UnsupportedAppUsage
    public int getNameSource() {
        return this.mNameSource;
    }

    public String getNumber() {
        return this.mNumber;
    }

    @SystemApi
    public int getProfileClass() {
        return this.mProfileClass;
    }

    public int getSimSlotIndex() {
        return this.mSimSlotIndex;
    }

    public int getSubscriptionId() {
        return this.mId;
    }

    public int getSubscriptionType() {
        return this.mSubscriptionType;
    }

    public int hashCode() {
        return Objects.hash(this.mId, this.mSimSlotIndex, this.mNameSource, this.mIconTint, this.mDataRoaming, this.mIsEmbedded, this.mIsOpportunistic, this.mGroupUUID, this.mIccId, this.mNumber, this.mMcc, this.mMnc, this.mCountryIso, this.mCardString, this.mCardId, this.mDisplayName, this.mCarrierName, this.mAccessRules, this.mIsGroupDisabled, this.mCarrierId, this.mProfileClass, this.mGroupOwner);
    }

    public boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public boolean isGroupDisabled() {
        return this.mIsGroupDisabled;
    }

    public boolean isOpportunistic() {
        return this.mIsOpportunistic;
    }

    public void setAssociatedPlmns(String[] arrstring, String[] arrstring2) {
        this.mEhplmns = arrstring;
        this.mHplmns = arrstring2;
    }

    public void setCarrierName(CharSequence charSequence) {
        this.mCarrierName = charSequence;
    }

    @UnsupportedAppUsage
    public void setDisplayName(CharSequence charSequence) {
        this.mDisplayName = charSequence;
    }

    public void setGroupDisabled(boolean bl) {
        this.mIsGroupDisabled = bl;
    }

    @UnsupportedAppUsage
    public void setIconTint(int n) {
        this.mIconTint = n;
    }

    public String toString() {
        String string2 = SubscriptionInfo.givePrintableIccid(this.mIccId);
        String string3 = SubscriptionInfo.givePrintableIccid(this.mCardString);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", iccId=");
        stringBuilder.append(string2);
        stringBuilder.append(" simSlotIndex=");
        stringBuilder.append(this.mSimSlotIndex);
        stringBuilder.append(" carrierId=");
        stringBuilder.append(this.mCarrierId);
        stringBuilder.append(" displayName=");
        stringBuilder.append((Object)this.mDisplayName);
        stringBuilder.append(" carrierName=");
        stringBuilder.append((Object)this.mCarrierName);
        stringBuilder.append(" nameSource=");
        stringBuilder.append(this.mNameSource);
        stringBuilder.append(" iconTint=");
        stringBuilder.append(this.mIconTint);
        stringBuilder.append(" mNumber=");
        stringBuilder.append(Rlog.pii(Build.IS_DEBUGGABLE, (Object)this.mNumber));
        stringBuilder.append(" dataRoaming=");
        stringBuilder.append(this.mDataRoaming);
        stringBuilder.append(" iconBitmap=");
        stringBuilder.append(this.mIconBitmap);
        stringBuilder.append(" mcc ");
        stringBuilder.append(this.mMcc);
        stringBuilder.append(" mnc ");
        stringBuilder.append(this.mMnc);
        stringBuilder.append("mCountryIso=");
        stringBuilder.append(this.mCountryIso);
        stringBuilder.append(" isEmbedded ");
        stringBuilder.append(this.mIsEmbedded);
        stringBuilder.append(" accessRules ");
        stringBuilder.append(Arrays.toString(this.mAccessRules));
        stringBuilder.append(" cardString=");
        stringBuilder.append(string3);
        stringBuilder.append(" cardId=");
        stringBuilder.append(this.mCardId);
        stringBuilder.append(" isOpportunistic ");
        stringBuilder.append(this.mIsOpportunistic);
        stringBuilder.append(" mGroupUUID=");
        stringBuilder.append(this.mGroupUUID);
        stringBuilder.append(" mIsGroupDisabled=");
        stringBuilder.append(this.mIsGroupDisabled);
        stringBuilder.append(" profileClass=");
        stringBuilder.append(this.mProfileClass);
        stringBuilder.append(" ehplmns = ");
        stringBuilder.append(Arrays.toString(this.mEhplmns));
        stringBuilder.append(" hplmns = ");
        stringBuilder.append(Arrays.toString(this.mHplmns));
        stringBuilder.append(" subscriptionType=");
        stringBuilder.append(this.mSubscriptionType);
        stringBuilder.append(" mGroupOwner=");
        stringBuilder.append(this.mGroupOwner);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mIccId);
        parcel.writeInt(this.mSimSlotIndex);
        parcel.writeCharSequence(this.mDisplayName);
        parcel.writeCharSequence(this.mCarrierName);
        parcel.writeInt(this.mNameSource);
        parcel.writeInt(this.mIconTint);
        parcel.writeString(this.mNumber);
        parcel.writeInt(this.mDataRoaming);
        parcel.writeString(this.mMcc);
        parcel.writeString(this.mMnc);
        parcel.writeString(this.mCountryIso);
        parcel.writeParcelable(this.mIconBitmap, n);
        parcel.writeBoolean(this.mIsEmbedded);
        parcel.writeTypedArray((Parcelable[])this.mAccessRules, n);
        parcel.writeString(this.mCardString);
        parcel.writeInt(this.mCardId);
        parcel.writeBoolean(this.mIsOpportunistic);
        Object object = this.mGroupUUID;
        object = object == null ? null : ((ParcelUuid)object).toString();
        parcel.writeString((String)object);
        parcel.writeBoolean(this.mIsGroupDisabled);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mProfileClass);
        parcel.writeInt(this.mSubscriptionType);
        parcel.writeStringArray(this.mEhplmns);
        parcel.writeStringArray(this.mHplmns);
        parcel.writeString(this.mGroupOwner);
    }

}

