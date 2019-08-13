/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.app.ActivityThread;
import android.app.Application;
import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.service.autofill.FieldClassificationUserData;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public final class UserData
implements FieldClassificationUserData,
Parcelable {
    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>(){

        @Override
        public UserData createFromParcel(Parcel object) {
            int n;
            String string2 = ((Parcel)object).readString();
            Object object2 = ((Parcel)object).readStringArray();
            String[] arrstring = ((Parcel)object).readStringArray();
            String string3 = ((Parcel)object).readString();
            Bundle bundle = ((Parcel)object).readBundle();
            ArrayMap arrayMap = new ArrayMap();
            ((Parcel)object).readMap(arrayMap, String.class.getClassLoader());
            ArrayMap arrayMap2 = new ArrayMap();
            ((Parcel)object).readMap(arrayMap2, Bundle.class.getClassLoader());
            object = new Builder(string2, arrstring[0], object2[0]).setFieldClassificationAlgorithm(string3, bundle);
            for (n = 1; n < ((String[])object2).length; ++n) {
                string3 = object2[n];
                ((Builder)object).add(arrstring[n], string3);
            }
            int n2 = arrayMap.size();
            if (n2 > 0) {
                for (n = 0; n < n2; ++n) {
                    object2 = (String)arrayMap.keyAt(n);
                    ((Builder)object).setFieldClassificationAlgorithmForCategory((String)object2, (String)arrayMap.valueAt(n), (Bundle)arrayMap2.get(object2));
                }
            }
            return ((Builder)object).build();
        }

        public UserData[] newArray(int n) {
            return new UserData[n];
        }
    };
    private static final int DEFAULT_MAX_CATEGORY_COUNT = 10;
    private static final int DEFAULT_MAX_FIELD_CLASSIFICATION_IDS_SIZE = 10;
    private static final int DEFAULT_MAX_USER_DATA_SIZE = 50;
    private static final int DEFAULT_MAX_VALUE_LENGTH = 100;
    private static final int DEFAULT_MIN_VALUE_LENGTH = 3;
    private static final String TAG = "UserData";
    private final ArrayMap<String, String> mCategoryAlgorithms;
    private final ArrayMap<String, Bundle> mCategoryArgs;
    private final String[] mCategoryIds;
    private final String mDefaultAlgorithm;
    private final Bundle mDefaultArgs;
    private final String mId;
    private final String[] mValues;

    private UserData(Builder builder) {
        this.mId = builder.mId;
        this.mCategoryIds = new String[builder.mCategoryIds.size()];
        builder.mCategoryIds.toArray(this.mCategoryIds);
        this.mValues = new String[builder.mValues.size()];
        builder.mValues.toArray(this.mValues);
        builder.mValues.toArray(this.mValues);
        this.mDefaultAlgorithm = builder.mDefaultAlgorithm;
        this.mDefaultArgs = builder.mDefaultArgs;
        this.mCategoryAlgorithms = builder.mCategoryAlgorithms;
        this.mCategoryArgs = builder.mCategoryArgs;
    }

    public static void dumpConstraints(String string2, PrintWriter printWriter) {
        printWriter.print(string2);
        printWriter.print("maxUserDataSize: ");
        printWriter.println(UserData.getMaxUserDataSize());
        printWriter.print(string2);
        printWriter.print("maxFieldClassificationIdsSize: ");
        printWriter.println(UserData.getMaxFieldClassificationIdsSize());
        printWriter.print(string2);
        printWriter.print("maxCategoryCount: ");
        printWriter.println(UserData.getMaxCategoryCount());
        printWriter.print(string2);
        printWriter.print("minValueLength: ");
        printWriter.println(UserData.getMinValueLength());
        printWriter.print(string2);
        printWriter.print("maxValueLength: ");
        printWriter.println(UserData.getMaxValueLength());
    }

    private static int getInt(String string2, int n) {
        Object object = null;
        ActivityThread activityThread = ActivityThread.currentActivityThread();
        if (activityThread != null) {
            object = activityThread.getApplication().getContentResolver();
        }
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not read from ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("; hardcoding ");
            ((StringBuilder)object).append(n);
            Log.w(TAG, ((StringBuilder)object).toString());
            return n;
        }
        return Settings.Secure.getInt((ContentResolver)object, string2, n);
    }

    public static int getMaxCategoryCount() {
        return UserData.getInt("autofill_user_data_max_category_count", 10);
    }

    public static int getMaxFieldClassificationIdsSize() {
        return UserData.getInt("autofill_user_data_max_field_classification_size", 10);
    }

    public static int getMaxUserDataSize() {
        return UserData.getInt("autofill_user_data_max_user_data_size", 50);
    }

    public static int getMaxValueLength() {
        return UserData.getInt("autofill_user_data_max_value_length", 100);
    }

    public static int getMinValueLength() {
        return UserData.getInt("autofill_user_data_min_value_length", 3);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(String string2, PrintWriter printWriter) {
        int n;
        printWriter.print(string2);
        printWriter.print("id: ");
        printWriter.print(this.mId);
        printWriter.print(string2);
        printWriter.print("Default Algorithm: ");
        printWriter.print(this.mDefaultAlgorithm);
        printWriter.print(string2);
        printWriter.print("Default Args");
        printWriter.print(this.mDefaultArgs);
        ArrayMap<String, String> arrayMap = this.mCategoryAlgorithms;
        if (arrayMap != null && arrayMap.size() > 0) {
            printWriter.print(string2);
            printWriter.print("Algorithms per category: ");
            for (n = 0; n < this.mCategoryAlgorithms.size(); ++n) {
                printWriter.print(string2);
                printWriter.print(string2);
                printWriter.print(this.mCategoryAlgorithms.keyAt(n));
                printWriter.print(": ");
                printWriter.println(Helper.getRedacted(this.mCategoryAlgorithms.valueAt(n)));
                printWriter.print("args=");
                printWriter.print(this.mCategoryArgs.get(this.mCategoryAlgorithms.keyAt(n)));
            }
        }
        printWriter.print(string2);
        printWriter.print("Field ids size: ");
        printWriter.println(this.mCategoryIds.length);
        for (n = 0; n < this.mCategoryIds.length; ++n) {
            printWriter.print(string2);
            printWriter.print(string2);
            printWriter.print(n);
            printWriter.print(": ");
            printWriter.println(Helper.getRedacted(this.mCategoryIds[n]));
        }
        printWriter.print(string2);
        printWriter.print("Values size: ");
        printWriter.println(this.mValues.length);
        for (n = 0; n < this.mValues.length; ++n) {
            printWriter.print(string2);
            printWriter.print(string2);
            printWriter.print(n);
            printWriter.print(": ");
            printWriter.println(Helper.getRedacted(this.mValues[n]));
        }
    }

    @Override
    public String[] getCategoryIds() {
        return this.mCategoryIds;
    }

    @Override
    public Bundle getDefaultFieldClassificationArgs() {
        return this.mDefaultArgs;
    }

    @Override
    public String getFieldClassificationAlgorithm() {
        return this.mDefaultAlgorithm;
    }

    @Override
    public String getFieldClassificationAlgorithmForCategory(String string2) {
        Preconditions.checkNotNull(string2);
        ArrayMap<String, String> arrayMap = this.mCategoryAlgorithms;
        if (arrayMap != null && arrayMap.containsKey(string2)) {
            return this.mCategoryAlgorithms.get(string2);
        }
        return null;
    }

    @Override
    public ArrayMap<String, String> getFieldClassificationAlgorithms() {
        return this.mCategoryAlgorithms;
    }

    @Override
    public ArrayMap<String, Bundle> getFieldClassificationArgs() {
        return this.mCategoryArgs;
    }

    public String getId() {
        return this.mId;
    }

    @Override
    public String[] getValues() {
        return this.mValues;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("UserData: [id=").append(this.mId);
        stringBuilder.append(", categoryIds=");
        Helper.appendRedacted(stringBuilder, this.mCategoryIds);
        stringBuilder.append(", values=");
        Helper.appendRedacted(stringBuilder, this.mValues);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        parcel.writeStringArray(this.mCategoryIds);
        parcel.writeStringArray(this.mValues);
        parcel.writeString(this.mDefaultAlgorithm);
        parcel.writeBundle(this.mDefaultArgs);
        parcel.writeMap(this.mCategoryAlgorithms);
        parcel.writeMap(this.mCategoryArgs);
    }

    public static final class Builder {
        private ArrayMap<String, String> mCategoryAlgorithms;
        private ArrayMap<String, Bundle> mCategoryArgs;
        private final ArrayList<String> mCategoryIds;
        private String mDefaultAlgorithm;
        private Bundle mDefaultArgs;
        private boolean mDestroyed;
        private final String mId;
        private final ArraySet<String> mUniqueCategoryIds;
        private final ArraySet<String> mUniqueValueCategoryPairs;
        private final ArrayList<String> mValues;

        public Builder(String string2, String string3, String string4) {
            this.mId = this.checkNotEmpty("id", string2);
            this.checkNotEmpty("categoryId", string4);
            this.checkValidValue(string3);
            int n = UserData.getMaxUserDataSize();
            this.mCategoryIds = new ArrayList(n);
            this.mValues = new ArrayList(n);
            this.mUniqueValueCategoryPairs = new ArraySet(n);
            this.mUniqueCategoryIds = new ArraySet(UserData.getMaxCategoryCount());
            this.addMapping(string3, string4);
        }

        private void addMapping(String string2, String string3) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string2);
            charSequence.append(":");
            charSequence.append(string3);
            charSequence = charSequence.toString();
            if (this.mUniqueValueCategoryPairs.contains(charSequence)) {
                Log.w(UserData.TAG, "Ignoring entry with same value / category");
                return;
            }
            this.mCategoryIds.add(string3);
            this.mValues.add(string2);
            this.mUniqueCategoryIds.add(string3);
            this.mUniqueValueCategoryPairs.add((String)charSequence);
        }

        private String checkNotEmpty(String string2, String string3) {
            Preconditions.checkNotNull(string3);
            Preconditions.checkArgument(TextUtils.isEmpty(string3) ^ true, "%s cannot be empty", string2);
            return string3;
        }

        private void checkValidValue(String charSequence) {
            Preconditions.checkNotNull(charSequence);
            int n = ((String)charSequence).length();
            int n2 = UserData.getMinValueLength();
            int n3 = UserData.getMaxValueLength();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("value length (");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(")");
            Preconditions.checkArgumentInRange(n, n2, n3, ((StringBuilder)charSequence).toString());
        }

        private void throwIfDestroyed() {
            if (!this.mDestroyed) {
                return;
            }
            throw new IllegalStateException("Already called #build()");
        }

        public Builder add(String string2, String string3) {
            StringBuilder stringBuilder;
            this.throwIfDestroyed();
            this.checkNotEmpty("categoryId", string3);
            this.checkValidValue(string2);
            boolean bl = this.mUniqueCategoryIds.contains(string3);
            boolean bl2 = true;
            if (!bl) {
                bl = this.mUniqueCategoryIds.size() < UserData.getMaxCategoryCount();
                stringBuilder = new StringBuilder();
                stringBuilder.append("already added ");
                stringBuilder.append(this.mUniqueCategoryIds.size());
                stringBuilder.append(" unique category ids");
                Preconditions.checkState(bl, stringBuilder.toString());
            }
            bl = this.mValues.size() < UserData.getMaxUserDataSize() ? bl2 : false;
            stringBuilder = new StringBuilder();
            stringBuilder.append("already added ");
            stringBuilder.append(this.mValues.size());
            stringBuilder.append(" elements");
            Preconditions.checkState(bl, stringBuilder.toString());
            this.addMapping(string2, string3);
            return this;
        }

        public UserData build() {
            this.throwIfDestroyed();
            this.mDestroyed = true;
            return new UserData(this);
        }

        public Builder setFieldClassificationAlgorithm(String string2, Bundle bundle) {
            this.throwIfDestroyed();
            this.mDefaultAlgorithm = string2;
            this.mDefaultArgs = bundle;
            return this;
        }

        public Builder setFieldClassificationAlgorithmForCategory(String string2, String string3, Bundle bundle) {
            this.throwIfDestroyed();
            Preconditions.checkNotNull(string2);
            if (this.mCategoryAlgorithms == null) {
                this.mCategoryAlgorithms = new ArrayMap(UserData.getMaxCategoryCount());
            }
            if (this.mCategoryArgs == null) {
                this.mCategoryArgs = new ArrayMap(UserData.getMaxCategoryCount());
            }
            this.mCategoryAlgorithms.put(string2, string3);
            this.mCategoryArgs.put(string2, bundle);
            return this;
        }
    }

}

