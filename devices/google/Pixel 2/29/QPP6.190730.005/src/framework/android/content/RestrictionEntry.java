/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

public class RestrictionEntry
implements Parcelable {
    public static final Parcelable.Creator<RestrictionEntry> CREATOR = new Parcelable.Creator<RestrictionEntry>(){

        @Override
        public RestrictionEntry createFromParcel(Parcel parcel) {
            return new RestrictionEntry(parcel);
        }

        public RestrictionEntry[] newArray(int n) {
            return new RestrictionEntry[n];
        }
    };
    public static final int TYPE_BOOLEAN = 1;
    public static final int TYPE_BUNDLE = 7;
    public static final int TYPE_BUNDLE_ARRAY = 8;
    public static final int TYPE_CHOICE = 2;
    public static final int TYPE_CHOICE_LEVEL = 3;
    public static final int TYPE_INTEGER = 5;
    public static final int TYPE_MULTI_SELECT = 4;
    public static final int TYPE_NULL = 0;
    public static final int TYPE_STRING = 6;
    private String[] mChoiceEntries;
    private String[] mChoiceValues;
    private String mCurrentValue;
    private String[] mCurrentValues;
    private String mDescription;
    private String mKey;
    private RestrictionEntry[] mRestrictions;
    private String mTitle;
    private int mType;

    public RestrictionEntry(int n, String string2) {
        this.mType = n;
        this.mKey = string2;
    }

    public RestrictionEntry(Parcel arrparcelable) {
        this.mType = arrparcelable.readInt();
        this.mKey = arrparcelable.readString();
        this.mTitle = arrparcelable.readString();
        this.mDescription = arrparcelable.readString();
        this.mChoiceEntries = arrparcelable.readStringArray();
        this.mChoiceValues = arrparcelable.readStringArray();
        this.mCurrentValue = arrparcelable.readString();
        this.mCurrentValues = arrparcelable.readStringArray();
        arrparcelable = arrparcelable.readParcelableArray(null);
        if (arrparcelable != null) {
            this.mRestrictions = new RestrictionEntry[arrparcelable.length];
            for (int i = 0; i < arrparcelable.length; ++i) {
                this.mRestrictions[i] = (RestrictionEntry)arrparcelable[i];
            }
        }
    }

    public RestrictionEntry(String string2, int n) {
        this.mKey = string2;
        this.mType = 5;
        this.setIntValue(n);
    }

    public RestrictionEntry(String string2, String string3) {
        this.mKey = string2;
        this.mType = 2;
        this.mCurrentValue = string3;
    }

    public RestrictionEntry(String string2, boolean bl) {
        this.mKey = string2;
        this.mType = 1;
        this.setSelectedState(bl);
    }

    private RestrictionEntry(String string2, RestrictionEntry[] arrrestrictionEntry, boolean bl) {
        this.mKey = string2;
        if (bl) {
            this.mType = 8;
            if (arrrestrictionEntry != null) {
                int n = arrrestrictionEntry.length;
                for (int i = 0; i < n; ++i) {
                    if (arrrestrictionEntry[i].getType() == 7) {
                        continue;
                    }
                    throw new IllegalArgumentException("bundle_array restriction can only have nested restriction entries of type bundle");
                }
            }
        } else {
            this.mType = 7;
        }
        this.setRestrictions(arrrestrictionEntry);
    }

    public RestrictionEntry(String string2, String[] arrstring) {
        this.mKey = string2;
        this.mType = 4;
        this.mCurrentValues = arrstring;
    }

    public static RestrictionEntry createBundleArrayEntry(String string2, RestrictionEntry[] arrrestrictionEntry) {
        return new RestrictionEntry(string2, arrrestrictionEntry, true);
    }

    public static RestrictionEntry createBundleEntry(String string2, RestrictionEntry[] arrrestrictionEntry) {
        return new RestrictionEntry(string2, arrrestrictionEntry, false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RestrictionEntry)) {
            return false;
        }
        RestrictionEntry restrictionEntry = (RestrictionEntry)object;
        if (this.mType == restrictionEntry.mType && this.mKey.equals(restrictionEntry.mKey)) {
            if (this.mCurrentValues == null && restrictionEntry.mCurrentValues == null && this.mRestrictions == null && restrictionEntry.mRestrictions == null && Objects.equals(this.mCurrentValue, restrictionEntry.mCurrentValue)) {
                return true;
            }
            if (this.mCurrentValue == null && restrictionEntry.mCurrentValue == null && this.mRestrictions == null && restrictionEntry.mRestrictions == null && Arrays.equals(this.mCurrentValues, restrictionEntry.mCurrentValues)) {
                return true;
            }
            String string2 = this.mCurrentValue;
            return string2 == null && (object = restrictionEntry.mCurrentValue) == null && string2 == null && object == null && Arrays.equals(this.mRestrictions, restrictionEntry.mRestrictions);
        }
        return false;
    }

    public String[] getAllSelectedStrings() {
        return this.mCurrentValues;
    }

    public String[] getChoiceEntries() {
        return this.mChoiceEntries;
    }

    public String[] getChoiceValues() {
        return this.mChoiceValues;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getIntValue() {
        return Integer.parseInt(this.mCurrentValue);
    }

    public String getKey() {
        return this.mKey;
    }

    public RestrictionEntry[] getRestrictions() {
        return this.mRestrictions;
    }

    public boolean getSelectedState() {
        return Boolean.parseBoolean(this.mCurrentValue);
    }

    public String getSelectedString() {
        return this.mCurrentValue;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public int getType() {
        return this.mType;
    }

    public int hashCode() {
        int n;
        int n2 = 17 * 31 + this.mKey.hashCode();
        Object[] arrobject = this.mCurrentValue;
        if (arrobject != null) {
            n = n2 * 31 + arrobject.hashCode();
        } else {
            arrobject = this.mCurrentValues;
            if (arrobject != null) {
                int n3 = arrobject.length;
                int n4 = 0;
                do {
                    n = n2;
                    if (n4 < n3) {
                        Object object = arrobject[n4];
                        n = n2;
                        if (object != null) {
                            n = n2 * 31 + ((String)object).hashCode();
                        }
                        ++n4;
                        n2 = n;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                arrobject = this.mRestrictions;
                n = n2;
                if (arrobject != null) {
                    n = n2 * 31 + Arrays.hashCode(arrobject);
                }
            }
        }
        return n;
    }

    public void setAllSelectedStrings(String[] arrstring) {
        this.mCurrentValues = arrstring;
    }

    public void setChoiceEntries(Context context, int n) {
        this.mChoiceEntries = context.getResources().getStringArray(n);
    }

    public void setChoiceEntries(String[] arrstring) {
        this.mChoiceEntries = arrstring;
    }

    public void setChoiceValues(Context context, int n) {
        this.mChoiceValues = context.getResources().getStringArray(n);
    }

    public void setChoiceValues(String[] arrstring) {
        this.mChoiceValues = arrstring;
    }

    public void setDescription(String string2) {
        this.mDescription = string2;
    }

    public void setIntValue(int n) {
        this.mCurrentValue = Integer.toString(n);
    }

    public void setRestrictions(RestrictionEntry[] arrrestrictionEntry) {
        this.mRestrictions = arrrestrictionEntry;
    }

    public void setSelectedState(boolean bl) {
        this.mCurrentValue = Boolean.toString(bl);
    }

    public void setSelectedString(String string2) {
        this.mCurrentValue = string2;
    }

    public void setTitle(String string2) {
        this.mTitle = string2;
    }

    public void setType(int n) {
        this.mType = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RestrictionEntry{mType=");
        stringBuilder.append(this.mType);
        stringBuilder.append(", mKey='");
        stringBuilder.append(this.mKey);
        stringBuilder.append('\'');
        stringBuilder.append(", mTitle='");
        stringBuilder.append(this.mTitle);
        stringBuilder.append('\'');
        stringBuilder.append(", mDescription='");
        stringBuilder.append(this.mDescription);
        stringBuilder.append('\'');
        stringBuilder.append(", mChoiceEntries=");
        stringBuilder.append(Arrays.toString(this.mChoiceEntries));
        stringBuilder.append(", mChoiceValues=");
        stringBuilder.append(Arrays.toString(this.mChoiceValues));
        stringBuilder.append(", mCurrentValue='");
        stringBuilder.append(this.mCurrentValue);
        stringBuilder.append('\'');
        stringBuilder.append(", mCurrentValues=");
        stringBuilder.append(Arrays.toString(this.mCurrentValues));
        stringBuilder.append(", mRestrictions=");
        stringBuilder.append(Arrays.toString(this.mRestrictions));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeString(this.mKey);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mDescription);
        parcel.writeStringArray(this.mChoiceEntries);
        parcel.writeStringArray(this.mChoiceValues);
        parcel.writeString(this.mCurrentValue);
        parcel.writeStringArray(this.mCurrentValues);
        parcel.writeParcelableArray((Parcelable[])this.mRestrictions, 0);
    }

}

