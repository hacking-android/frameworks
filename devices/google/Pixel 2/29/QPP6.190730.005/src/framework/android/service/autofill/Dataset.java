/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class Dataset
implements Parcelable {
    public static final Parcelable.Creator<Dataset> CREATOR = new Parcelable.Creator<Dataset>(){

        @Override
        public Dataset createFromParcel(Parcel parcel) {
            Object object = (RemoteViews)parcel.readParcelable(null);
            object = object == null ? new Builder() : new Builder((RemoteViews)object);
            ArrayList<AutofillId> arrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
            ArrayList<AutofillValue> arrayList2 = parcel.createTypedArrayList(AutofillValue.CREATOR);
            ArrayList<RemoteViews> arrayList3 = parcel.createTypedArrayList(RemoteViews.CREATOR);
            ArrayList<DatasetFieldFilter> arrayList4 = parcel.createTypedArrayList(DatasetFieldFilter.CREATOR);
            for (int i = 0; i < arrayList.size(); ++i) {
                ((Builder)object).setLifeTheUniverseAndEverything(arrayList.get(i), arrayList2.get(i), arrayList3.get(i), arrayList4.get(i));
            }
            ((Builder)object).setAuthentication((IntentSender)parcel.readParcelable(null));
            ((Builder)object).setId(parcel.readString());
            return ((Builder)object).build();
        }

        public Dataset[] newArray(int n) {
            return new Dataset[n];
        }
    };
    private final IntentSender mAuthentication;
    private final ArrayList<DatasetFieldFilter> mFieldFilters;
    private final ArrayList<AutofillId> mFieldIds;
    private final ArrayList<RemoteViews> mFieldPresentations;
    private final ArrayList<AutofillValue> mFieldValues;
    String mId;
    private final RemoteViews mPresentation;

    private Dataset(Builder builder) {
        this.mFieldIds = builder.mFieldIds;
        this.mFieldValues = builder.mFieldValues;
        this.mFieldPresentations = builder.mFieldPresentations;
        this.mFieldFilters = builder.mFieldFilters;
        this.mPresentation = builder.mPresentation;
        this.mAuthentication = builder.mAuthentication;
        this.mId = builder.mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public IntentSender getAuthentication() {
        return this.mAuthentication;
    }

    public ArrayList<AutofillId> getFieldIds() {
        return this.mFieldIds;
    }

    public RemoteViews getFieldPresentation(int n) {
        RemoteViews remoteViews = this.mFieldPresentations.get(n);
        if (remoteViews == null) {
            remoteViews = this.mPresentation;
        }
        return remoteViews;
    }

    public ArrayList<AutofillValue> getFieldValues() {
        return this.mFieldValues;
    }

    public DatasetFieldFilter getFilter(int n) {
        return this.mFieldFilters.get(n);
    }

    public String getId() {
        return this.mId;
    }

    public boolean isEmpty() {
        ArrayList<AutofillId> arrayList = this.mFieldIds;
        boolean bl = arrayList == null || arrayList.isEmpty();
        return bl;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("Dataset[");
        if (this.mId == null) {
            stringBuilder.append("noId");
        } else {
            stringBuilder.append("id=");
            stringBuilder.append(this.mId.length());
            stringBuilder.append("_chars");
        }
        if (this.mFieldIds != null) {
            stringBuilder.append(", fieldIds=");
            stringBuilder.append(this.mFieldIds);
        }
        if (this.mFieldValues != null) {
            stringBuilder.append(", fieldValues=");
            stringBuilder.append(this.mFieldValues);
        }
        if (this.mFieldPresentations != null) {
            stringBuilder.append(", fieldPresentations=");
            stringBuilder.append(this.mFieldPresentations.size());
        }
        if (this.mFieldFilters != null) {
            stringBuilder.append(", fieldFilters=");
            stringBuilder.append(this.mFieldFilters.size());
        }
        if (this.mPresentation != null) {
            stringBuilder.append(", hasPresentation");
        }
        if (this.mAuthentication != null) {
            stringBuilder.append(", hasAuthentication");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mPresentation, n);
        parcel.writeTypedList(this.mFieldIds, n);
        parcel.writeTypedList(this.mFieldValues, n);
        parcel.writeTypedList(this.mFieldPresentations, n);
        parcel.writeTypedList(this.mFieldFilters, n);
        parcel.writeParcelable(this.mAuthentication, n);
        parcel.writeString(this.mId);
    }

    public static final class Builder {
        private IntentSender mAuthentication;
        private boolean mDestroyed;
        private ArrayList<DatasetFieldFilter> mFieldFilters;
        private ArrayList<AutofillId> mFieldIds;
        private ArrayList<RemoteViews> mFieldPresentations;
        private ArrayList<AutofillValue> mFieldValues;
        private String mId;
        private RemoteViews mPresentation;

        public Builder() {
        }

        public Builder(RemoteViews remoteViews) {
            Preconditions.checkNotNull(remoteViews, "presentation must be non-null");
            this.mPresentation = remoteViews;
        }

        private void setLifeTheUniverseAndEverything(AutofillId autofillId, AutofillValue autofillValue, RemoteViews remoteViews, DatasetFieldFilter datasetFieldFilter) {
            Preconditions.checkNotNull(autofillId, "id cannot be null");
            ArrayList<AutofillId> arrayList = this.mFieldIds;
            if (arrayList != null) {
                int n = arrayList.indexOf(autofillId);
                if (n >= 0) {
                    this.mFieldValues.set(n, autofillValue);
                    this.mFieldPresentations.set(n, remoteViews);
                    this.mFieldFilters.set(n, datasetFieldFilter);
                    return;
                }
            } else {
                this.mFieldIds = new ArrayList();
                this.mFieldValues = new ArrayList();
                this.mFieldPresentations = new ArrayList();
                this.mFieldFilters = new ArrayList();
            }
            this.mFieldIds.add(autofillId);
            this.mFieldValues.add(autofillValue);
            this.mFieldPresentations.add(remoteViews);
            this.mFieldFilters.add(datasetFieldFilter);
        }

        private void throwIfDestroyed() {
            if (!this.mDestroyed) {
                return;
            }
            throw new IllegalStateException("Already called #build()");
        }

        public Dataset build() {
            this.throwIfDestroyed();
            this.mDestroyed = true;
            if (this.mFieldIds != null) {
                return new Dataset(this);
            }
            throw new IllegalStateException("at least one value must be set");
        }

        public Builder setAuthentication(IntentSender intentSender) {
            this.throwIfDestroyed();
            this.mAuthentication = intentSender;
            return this;
        }

        public Builder setId(String string2) {
            this.throwIfDestroyed();
            this.mId = string2;
            return this;
        }

        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue) {
            this.throwIfDestroyed();
            this.setLifeTheUniverseAndEverything(autofillId, autofillValue, null, null);
            return this;
        }

        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, RemoteViews remoteViews) {
            this.throwIfDestroyed();
            Preconditions.checkNotNull(remoteViews, "presentation cannot be null");
            this.setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, null);
            return this;
        }

        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, Pattern pattern) {
            this.throwIfDestroyed();
            boolean bl = this.mPresentation != null;
            Preconditions.checkState(bl, "Dataset presentation not set on constructor");
            this.setLifeTheUniverseAndEverything(autofillId, autofillValue, null, new DatasetFieldFilter(pattern));
            return this;
        }

        public Builder setValue(AutofillId autofillId, AutofillValue autofillValue, Pattern pattern, RemoteViews remoteViews) {
            this.throwIfDestroyed();
            Preconditions.checkNotNull(remoteViews, "presentation cannot be null");
            this.setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, new DatasetFieldFilter(pattern));
            return this;
        }
    }

    public static final class DatasetFieldFilter
    implements Parcelable {
        public static final Parcelable.Creator<DatasetFieldFilter> CREATOR = new Parcelable.Creator<DatasetFieldFilter>(){

            @Override
            public DatasetFieldFilter createFromParcel(Parcel parcel) {
                return new DatasetFieldFilter((Pattern)parcel.readSerializable());
            }

            public DatasetFieldFilter[] newArray(int n) {
                return new DatasetFieldFilter[n];
            }
        };
        public final Pattern pattern;

        private DatasetFieldFilter(Pattern pattern) {
            this.pattern = pattern;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            CharSequence charSequence;
            if (!Helper.sDebug) {
                return super.toString();
            }
            if (this.pattern == null) {
                charSequence = "null";
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.pattern.pattern().length());
                ((StringBuilder)charSequence).append("_chars");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            return charSequence;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeSerializable(this.pattern);
        }

    }

}

