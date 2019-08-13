/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.FieldClassificationUserData;
import android.service.autofill.UserData;
import android.util.ArrayMap;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;

public final class CompositeUserData
implements FieldClassificationUserData,
Parcelable {
    public static final Parcelable.Creator<CompositeUserData> CREATOR = new Parcelable.Creator<CompositeUserData>(){

        @Override
        public CompositeUserData createFromParcel(Parcel parcel) {
            return new CompositeUserData((UserData)parcel.readParcelable(null), (UserData)parcel.readParcelable(null));
        }

        public CompositeUserData[] newArray(int n) {
            return new CompositeUserData[n];
        }
    };
    private final String[] mCategories;
    private final UserData mGenericUserData;
    private final UserData mPackageUserData;
    private final String[] mValues;

    public CompositeUserData(UserData object, UserData object2) {
        this.mGenericUserData = object;
        this.mPackageUserData = object2;
        String[] arrstring = this.mPackageUserData.getCategoryIds();
        Object object3 = this.mPackageUserData.getValues();
        object2 = new ArrayList(arrstring.length);
        object = new ArrayList(((String[])object3).length);
        Collections.addAll(object2, arrstring);
        Collections.addAll(object, object3);
        object3 = this.mGenericUserData;
        if (object3 != null) {
            object3 = ((UserData)object3).getCategoryIds();
            arrstring = this.mGenericUserData.getValues();
            int n = this.mGenericUserData.getCategoryIds().length;
            for (int i = 0; i < n; ++i) {
                if (((ArrayList)object2).contains(object3[i])) continue;
                ((ArrayList)object2).add(object3[i]);
                ((ArrayList)object).add(arrstring[i]);
            }
        }
        this.mCategories = new String[((ArrayList)object2).size()];
        ((ArrayList)object2).toArray(this.mCategories);
        this.mValues = new String[((ArrayList)object).size()];
        ((ArrayList)object).toArray(this.mValues);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String[] getCategoryIds() {
        return this.mCategories;
    }

    @Override
    public Bundle getDefaultFieldClassificationArgs() {
        Parcelable parcelable = this.mPackageUserData.getDefaultFieldClassificationArgs();
        if (parcelable != null) {
            return parcelable;
        }
        parcelable = this.mGenericUserData;
        parcelable = parcelable == null ? null : ((UserData)parcelable).getDefaultFieldClassificationArgs();
        return parcelable;
    }

    @Override
    public String getFieldClassificationAlgorithm() {
        Object object = this.mPackageUserData.getFieldClassificationAlgorithm();
        if (object != null) {
            return object;
        }
        object = this.mGenericUserData;
        object = object == null ? null : ((UserData)object).getFieldClassificationAlgorithm();
        return object;
    }

    @Override
    public String getFieldClassificationAlgorithmForCategory(String string2) {
        Preconditions.checkNotNull(string2);
        ArrayMap<String, String> arrayMap = this.getFieldClassificationAlgorithms();
        if (arrayMap != null && arrayMap.containsKey(string2)) {
            return arrayMap.get(string2);
        }
        return null;
    }

    @Override
    public ArrayMap<String, String> getFieldClassificationAlgorithms() {
        ArrayMap<String, String> arrayMap = this.mPackageUserData.getFieldClassificationAlgorithms();
        Object object = this.mGenericUserData;
        object = object == null ? null : ((UserData)object).getFieldClassificationAlgorithms();
        ArrayMap<String, String> arrayMap2 = null;
        if (arrayMap != null || object != null) {
            ArrayMap<String, String> arrayMap3 = new ArrayMap<String, String>();
            if (object != null) {
                arrayMap3.putAll((ArrayMap<String, String>)object);
            }
            arrayMap2 = arrayMap3;
            if (arrayMap != null) {
                arrayMap3.putAll(arrayMap);
                arrayMap2 = arrayMap3;
            }
        }
        return arrayMap2;
    }

    @Override
    public ArrayMap<String, Bundle> getFieldClassificationArgs() {
        ArrayMap<String, Bundle> arrayMap = this.mPackageUserData.getFieldClassificationArgs();
        Object object = this.mGenericUserData;
        object = object == null ? null : ((UserData)object).getFieldClassificationArgs();
        ArrayMap<String, Bundle> arrayMap2 = null;
        if (arrayMap != null || object != null) {
            ArrayMap<String, Bundle> arrayMap3 = new ArrayMap<String, Bundle>();
            if (object != null) {
                arrayMap3.putAll((ArrayMap<String, Bundle>)object);
            }
            arrayMap2 = arrayMap3;
            if (arrayMap != null) {
                arrayMap3.putAll(arrayMap);
                arrayMap2 = arrayMap3;
            }
        }
        return arrayMap2;
    }

    @Override
    public String[] getValues() {
        return this.mValues;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("genericUserData=");
        stringBuilder.append(this.mGenericUserData);
        stringBuilder.append(", packageUserData=");
        return stringBuilder.append(this.mPackageUserData).toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mGenericUserData, 0);
        parcel.writeParcelable(this.mPackageUserData, 0);
    }

}

