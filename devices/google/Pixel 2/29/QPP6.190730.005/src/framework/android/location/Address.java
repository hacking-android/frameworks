/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Address
implements Parcelable {
    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>(){

        @Override
        public Address createFromParcel(Parcel parcel) {
            int n;
            String string2 = parcel.readString();
            Object object = parcel.readString();
            object = ((String)object).length() > 0 ? new Locale(string2, (String)object) : new Locale(string2);
            object = new Address((Locale)object);
            int n2 = parcel.readInt();
            if (n2 > 0) {
                ((Address)object).mAddressLines = new HashMap(n2);
                for (n = 0; n < n2; ++n) {
                    int n3 = parcel.readInt();
                    string2 = parcel.readString();
                    ((Address)object).mAddressLines.put(n3, string2);
                    ((Address)object).mMaxAddressLineIndex = Math.max(((Address)object).mMaxAddressLineIndex, n3);
                }
            } else {
                ((Address)object).mAddressLines = null;
                ((Address)object).mMaxAddressLineIndex = -1;
            }
            ((Address)object).mFeatureName = parcel.readString();
            ((Address)object).mAdminArea = parcel.readString();
            ((Address)object).mSubAdminArea = parcel.readString();
            ((Address)object).mLocality = parcel.readString();
            ((Address)object).mSubLocality = parcel.readString();
            ((Address)object).mThoroughfare = parcel.readString();
            ((Address)object).mSubThoroughfare = parcel.readString();
            ((Address)object).mPremises = parcel.readString();
            ((Address)object).mPostalCode = parcel.readString();
            ((Address)object).mCountryCode = parcel.readString();
            ((Address)object).mCountryName = parcel.readString();
            n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n != 0;
            ((Address)object).mHasLatitude = bl2;
            if (((Address)object).mHasLatitude) {
                ((Address)object).mLatitude = parcel.readDouble();
            }
            bl2 = parcel.readInt() == 0 ? bl : true;
            ((Address)object).mHasLongitude = bl2;
            if (((Address)object).mHasLongitude) {
                ((Address)object).mLongitude = parcel.readDouble();
            }
            ((Address)object).mPhone = parcel.readString();
            ((Address)object).mUrl = parcel.readString();
            ((Address)object).mExtras = parcel.readBundle();
            return object;
        }

        public Address[] newArray(int n) {
            return new Address[n];
        }
    };
    private HashMap<Integer, String> mAddressLines;
    private String mAdminArea;
    private String mCountryCode;
    private String mCountryName;
    private Bundle mExtras = null;
    private String mFeatureName;
    private boolean mHasLatitude = false;
    private boolean mHasLongitude = false;
    private double mLatitude;
    private Locale mLocale;
    private String mLocality;
    private double mLongitude;
    private int mMaxAddressLineIndex = -1;
    private String mPhone;
    private String mPostalCode;
    private String mPremises;
    private String mSubAdminArea;
    private String mSubLocality;
    private String mSubThoroughfare;
    private String mThoroughfare;
    private String mUrl;

    public Address(Locale locale) {
        this.mLocale = locale;
    }

    public void clearLatitude() {
        this.mHasLatitude = false;
    }

    public void clearLongitude() {
        this.mHasLongitude = false;
    }

    @Override
    public int describeContents() {
        Bundle bundle = this.mExtras;
        int n = bundle != null ? bundle.describeContents() : 0;
        return n;
    }

    public String getAddressLine(int n) {
        if (n >= 0) {
            HashMap<Integer, String> hashMap = this.mAddressLines;
            hashMap = hashMap == null ? null : hashMap.get(n);
            return hashMap;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index = ");
        stringBuilder.append(n);
        stringBuilder.append(" < 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String getAdminArea() {
        return this.mAdminArea;
    }

    public String getCountryCode() {
        return this.mCountryCode;
    }

    public String getCountryName() {
        return this.mCountryName;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getFeatureName() {
        return this.mFeatureName;
    }

    public double getLatitude() {
        if (this.mHasLatitude) {
            return this.mLatitude;
        }
        throw new IllegalStateException();
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public String getLocality() {
        return this.mLocality;
    }

    public double getLongitude() {
        if (this.mHasLongitude) {
            return this.mLongitude;
        }
        throw new IllegalStateException();
    }

    public int getMaxAddressLineIndex() {
        return this.mMaxAddressLineIndex;
    }

    public String getPhone() {
        return this.mPhone;
    }

    public String getPostalCode() {
        return this.mPostalCode;
    }

    public String getPremises() {
        return this.mPremises;
    }

    public String getSubAdminArea() {
        return this.mSubAdminArea;
    }

    public String getSubLocality() {
        return this.mSubLocality;
    }

    public String getSubThoroughfare() {
        return this.mSubThoroughfare;
    }

    public String getThoroughfare() {
        return this.mThoroughfare;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean hasLatitude() {
        return this.mHasLatitude;
    }

    public boolean hasLongitude() {
        return this.mHasLongitude;
    }

    public void setAddressLine(int n, String object2) {
        if (n >= 0) {
            if (this.mAddressLines == null) {
                this.mAddressLines = new HashMap();
            }
            this.mAddressLines.put(n, (String)object2);
            if (object2 == null) {
                this.mMaxAddressLineIndex = -1;
                for (Integer n2 : this.mAddressLines.keySet()) {
                    this.mMaxAddressLineIndex = Math.max(this.mMaxAddressLineIndex, n2);
                }
            } else {
                this.mMaxAddressLineIndex = Math.max(this.mMaxAddressLineIndex, n);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index = ");
        stringBuilder.append(n);
        stringBuilder.append(" < 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setAdminArea(String string2) {
        this.mAdminArea = string2;
    }

    public void setCountryCode(String string2) {
        this.mCountryCode = string2;
    }

    public void setCountryName(String string2) {
        this.mCountryName = string2;
    }

    public void setExtras(Bundle bundle) {
        bundle = bundle == null ? null : new Bundle(bundle);
        this.mExtras = bundle;
    }

    public void setFeatureName(String string2) {
        this.mFeatureName = string2;
    }

    public void setLatitude(double d) {
        this.mLatitude = d;
        this.mHasLatitude = true;
    }

    public void setLocality(String string2) {
        this.mLocality = string2;
    }

    public void setLongitude(double d) {
        this.mLongitude = d;
        this.mHasLongitude = true;
    }

    public void setPhone(String string2) {
        this.mPhone = string2;
    }

    public void setPostalCode(String string2) {
        this.mPostalCode = string2;
    }

    public void setPremises(String string2) {
        this.mPremises = string2;
    }

    public void setSubAdminArea(String string2) {
        this.mSubAdminArea = string2;
    }

    public void setSubLocality(String string2) {
        this.mSubLocality = string2;
    }

    public void setSubThoroughfare(String string2) {
        this.mSubThoroughfare = string2;
    }

    public void setThoroughfare(String string2) {
        this.mThoroughfare = string2;
    }

    public void setUrl(String string2) {
        this.mUrl = string2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Address[addressLines=[");
        for (int i = 0; i <= this.mMaxAddressLineIndex; ++i) {
            if (i > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(i);
            stringBuilder.append(':');
            String string2 = this.mAddressLines.get(i);
            if (string2 == null) {
                stringBuilder.append("null");
                continue;
            }
            stringBuilder.append('\"');
            stringBuilder.append(string2);
            stringBuilder.append('\"');
        }
        stringBuilder.append(']');
        stringBuilder.append(",feature=");
        stringBuilder.append(this.mFeatureName);
        stringBuilder.append(",admin=");
        stringBuilder.append(this.mAdminArea);
        stringBuilder.append(",sub-admin=");
        stringBuilder.append(this.mSubAdminArea);
        stringBuilder.append(",locality=");
        stringBuilder.append(this.mLocality);
        stringBuilder.append(",thoroughfare=");
        stringBuilder.append(this.mThoroughfare);
        stringBuilder.append(",postalCode=");
        stringBuilder.append(this.mPostalCode);
        stringBuilder.append(",countryCode=");
        stringBuilder.append(this.mCountryCode);
        stringBuilder.append(",countryName=");
        stringBuilder.append(this.mCountryName);
        stringBuilder.append(",hasLatitude=");
        stringBuilder.append(this.mHasLatitude);
        stringBuilder.append(",latitude=");
        stringBuilder.append(this.mLatitude);
        stringBuilder.append(",hasLongitude=");
        stringBuilder.append(this.mHasLongitude);
        stringBuilder.append(",longitude=");
        stringBuilder.append(this.mLongitude);
        stringBuilder.append(",phone=");
        stringBuilder.append(this.mPhone);
        stringBuilder.append(",url=");
        stringBuilder.append(this.mUrl);
        stringBuilder.append(",extras=");
        stringBuilder.append(this.mExtras);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mLocale.getLanguage());
        parcel.writeString(this.mLocale.getCountry());
        Object object = this.mAddressLines;
        if (object == null) {
            parcel.writeInt(0);
        } else {
            object = ((HashMap)object).entrySet();
            parcel.writeInt(object.size());
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                object = (Map.Entry)iterator.next();
                parcel.writeInt((Integer)object.getKey());
                parcel.writeString((String)object.getValue());
            }
        }
        parcel.writeString(this.mFeatureName);
        parcel.writeString(this.mAdminArea);
        parcel.writeString(this.mSubAdminArea);
        parcel.writeString(this.mLocality);
        parcel.writeString(this.mSubLocality);
        parcel.writeString(this.mThoroughfare);
        parcel.writeString(this.mSubThoroughfare);
        parcel.writeString(this.mPremises);
        parcel.writeString(this.mPostalCode);
        parcel.writeString(this.mCountryCode);
        parcel.writeString(this.mCountryName);
        parcel.writeInt((int)this.mHasLatitude);
        if (this.mHasLatitude) {
            parcel.writeDouble(this.mLatitude);
        }
        parcel.writeInt((int)this.mHasLongitude);
        if (this.mHasLongitude) {
            parcel.writeDouble(this.mLongitude);
        }
        parcel.writeString(this.mPhone);
        parcel.writeString(this.mUrl);
        parcel.writeBundle(this.mExtras);
    }

}

