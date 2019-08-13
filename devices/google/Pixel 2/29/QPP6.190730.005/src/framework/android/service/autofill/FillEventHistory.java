/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.FieldClassification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FillEventHistory
implements Parcelable {
    public static final Parcelable.Creator<FillEventHistory> CREATOR = new Parcelable.Creator<FillEventHistory>(){

        @Override
        public FillEventHistory createFromParcel(Parcel arrfieldClassification) {
            FillEventHistory fillEventHistory = new FillEventHistory(0, arrfieldClassification.readBundle());
            int n = arrfieldClassification.readInt();
            int n2 = 0;
            do {
                ArrayList<ArrayList<String>> arrayList;
                AutofillId[] arrautofillId;
                Object object = arrfieldClassification;
                if (n2 >= n) break;
                int n3 = arrfieldClassification.readInt();
                String string2 = arrfieldClassification.readString();
                Bundle bundle = arrfieldClassification.readBundle();
                ArrayList<String> arrayList2 = arrfieldClassification.createStringArrayList();
                ArraySet<? extends Object> arraySet = object.readArraySet(null);
                ArrayList<AutofillId> arrayList3 = object.createTypedArrayList(AutofillId.CREATOR);
                ArrayList<String> arrayList4 = arrfieldClassification.createStringArrayList();
                ArrayList<AutofillId> arrayList5 = object.createTypedArrayList(AutofillId.CREATOR);
                if (arrayList5 != null) {
                    int n4 = arrayList5.size();
                    arrayList = new ArrayList<ArrayList<String>>(n4);
                    for (int i = 0; i < n4; ++i) {
                        arrayList.add(arrfieldClassification.createStringArrayList());
                    }
                } else {
                    arrayList = null;
                }
                object = (arrautofillId = (AutofillId[])object.readParcelableArray(null, AutofillId.class)) != null ? FieldClassification.readArrayFromParcel((Parcel)arrfieldClassification) : null;
                fillEventHistory.addEvent(new Event(n3, string2, bundle, (List<String>)arrayList2, (ArraySet<String>)arraySet, arrayList3, arrayList4, arrayList5, arrayList, arrautofillId, (FieldClassification[])object));
                ++n2;
            } while (true);
            return fillEventHistory;
        }

        public FillEventHistory[] newArray(int n) {
            return new FillEventHistory[n];
        }
    };
    private static final String TAG = "FillEventHistory";
    private final Bundle mClientState;
    List<Event> mEvents;
    private final int mSessionId;

    public FillEventHistory(int n, Bundle bundle) {
        this.mClientState = bundle;
        this.mSessionId = n;
    }

    public void addEvent(Event event) {
        if (this.mEvents == null) {
            this.mEvents = new ArrayList<Event>(1);
        }
        this.mEvents.add(event);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public Bundle getClientState() {
        return this.mClientState;
    }

    public List<Event> getEvents() {
        return this.mEvents;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public String toString() {
        List<Event> list = this.mEvents;
        list = list == null ? "no events" : list.toString();
        return list;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.mClientState);
        Parcelable[] arrparcelable = this.mEvents;
        if (arrparcelable == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(arrparcelable.size());
            int n2 = this.mEvents.size();
            for (int i = 0; i < n2; ++i) {
                Event event = this.mEvents.get(i);
                parcel.writeInt(event.mEventType);
                parcel.writeString(event.mDatasetId);
                parcel.writeBundle(event.mClientState);
                parcel.writeStringList(event.mSelectedDatasetIds);
                parcel.writeArraySet(event.mIgnoredDatasetIds);
                parcel.writeTypedList(event.mChangedFieldIds);
                parcel.writeStringList(event.mChangedDatasetIds);
                parcel.writeTypedList(event.mManuallyFilledFieldIds);
                if (event.mManuallyFilledFieldIds != null) {
                    int n3 = event.mManuallyFilledFieldIds.size();
                    for (int j = 0; j < n3; ++j) {
                        parcel.writeStringList((List)event.mManuallyFilledDatasetIds.get(j));
                    }
                }
                arrparcelable = event.mDetectedFieldIds;
                parcel.writeParcelableArray(arrparcelable, n);
                if (arrparcelable == null) continue;
                FieldClassification.writeArrayToParcel(parcel, event.mDetectedFieldClassifications);
            }
        }
    }

    public static final class Event {
        public static final int TYPE_AUTHENTICATION_SELECTED = 2;
        public static final int TYPE_CONTEXT_COMMITTED = 4;
        public static final int TYPE_DATASET_AUTHENTICATION_SELECTED = 1;
        public static final int TYPE_DATASET_SELECTED = 0;
        public static final int TYPE_SAVE_SHOWN = 3;
        private final ArrayList<String> mChangedDatasetIds;
        private final ArrayList<AutofillId> mChangedFieldIds;
        private final Bundle mClientState;
        private final String mDatasetId;
        private final FieldClassification[] mDetectedFieldClassifications;
        private final AutofillId[] mDetectedFieldIds;
        private final int mEventType;
        private final ArraySet<String> mIgnoredDatasetIds;
        private final ArrayList<ArrayList<String>> mManuallyFilledDatasetIds;
        private final ArrayList<AutofillId> mManuallyFilledFieldIds;
        private final List<String> mSelectedDatasetIds;

        public Event(int n, String string2, Bundle bundle, List<String> list, ArraySet<String> arraySet, ArrayList<AutofillId> arrayList, ArrayList<String> arrayList2, ArrayList<AutofillId> arrayList3, ArrayList<ArrayList<String>> arrayList4, AutofillId[] arrautofillId, FieldClassification[] arrfieldClassification) {
            boolean bl;
            boolean bl2 = false;
            this.mEventType = Preconditions.checkArgumentInRange(n, 0, 4, "eventType");
            this.mDatasetId = string2;
            this.mClientState = bundle;
            this.mSelectedDatasetIds = list;
            this.mIgnoredDatasetIds = arraySet;
            if (arrayList != null) {
                bl = !ArrayUtils.isEmpty(arrayList) && arrayList2 != null && arrayList.size() == arrayList2.size();
                Preconditions.checkArgument(bl, "changed ids must have same length and not be empty");
            }
            this.mChangedFieldIds = arrayList;
            this.mChangedDatasetIds = arrayList2;
            if (arrayList3 != null) {
                bl = !ArrayUtils.isEmpty(arrayList3) && arrayList4 != null && arrayList3.size() == arrayList4.size() ? true : bl2;
                Preconditions.checkArgument(bl, "manually filled ids must have same length and not be empty");
            }
            this.mManuallyFilledFieldIds = arrayList3;
            this.mManuallyFilledDatasetIds = arrayList4;
            this.mDetectedFieldIds = arrautofillId;
            this.mDetectedFieldClassifications = arrfieldClassification;
        }

        public Map<AutofillId, String> getChangedFields() {
            ArrayList<AutofillId> arrayList = this.mChangedFieldIds;
            if (arrayList != null && this.mChangedDatasetIds != null) {
                int n = arrayList.size();
                arrayList = new ArrayMap(n);
                for (int i = 0; i < n; ++i) {
                    ((ArrayMap)((Object)arrayList)).put(this.mChangedFieldIds.get(i), this.mChangedDatasetIds.get(i));
                }
                return arrayList;
            }
            return Collections.emptyMap();
        }

        public Bundle getClientState() {
            return this.mClientState;
        }

        public String getDatasetId() {
            return this.mDatasetId;
        }

        public Map<AutofillId, FieldClassification> getFieldsClassification() {
            Object object = this.mDetectedFieldIds;
            if (object == null) {
                return Collections.emptyMap();
            }
            int n = ((AutofillId[])object).length;
            object = new ArrayMap(n);
            for (int i = 0; i < n; ++i) {
                AutofillId autofillId = this.mDetectedFieldIds[i];
                FieldClassification fieldClassification = this.mDetectedFieldClassifications[i];
                if (Helper.sVerbose) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getFieldsClassification[");
                    stringBuilder.append(i);
                    stringBuilder.append("]: id=");
                    stringBuilder.append(autofillId);
                    stringBuilder.append(", fc=");
                    stringBuilder.append(fieldClassification);
                    Log.v(FillEventHistory.TAG, stringBuilder.toString());
                }
                ((ArrayMap)object).put(autofillId, fieldClassification);
            }
            return object;
        }

        public Set<String> getIgnoredDatasetIds() {
            ArraySet<String> arraySet;
            Set<String> set = arraySet = this.mIgnoredDatasetIds;
            if (arraySet == null) {
                set = Collections.emptySet();
            }
            return set;
        }

        public Map<AutofillId, Set<String>> getManuallyEnteredField() {
            ArrayList<AutofillId> arrayList = this.mManuallyFilledFieldIds;
            if (arrayList != null && this.mManuallyFilledDatasetIds != null) {
                int n = arrayList.size();
                arrayList = new ArrayMap(n);
                for (int i = 0; i < n; ++i) {
                    arrayList.put(this.mManuallyFilledFieldIds.get(i), new ArraySet<String>(this.mManuallyFilledDatasetIds.get(i)));
                }
                return arrayList;
            }
            return Collections.emptyMap();
        }

        public Set<String> getSelectedDatasetIds() {
            Collection<String> collection = this.mSelectedDatasetIds;
            collection = collection == null ? Collections.emptySet() : new ArraySet<String>(collection);
            return collection;
        }

        public int getType() {
            return this.mEventType;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FillEvent [datasetId=");
            stringBuilder.append(this.mDatasetId);
            stringBuilder.append(", type=");
            stringBuilder.append(this.mEventType);
            stringBuilder.append(", selectedDatasets=");
            stringBuilder.append(this.mSelectedDatasetIds);
            stringBuilder.append(", ignoredDatasetIds=");
            stringBuilder.append(this.mIgnoredDatasetIds);
            stringBuilder.append(", changedFieldIds=");
            stringBuilder.append(this.mChangedFieldIds);
            stringBuilder.append(", changedDatasetsIds=");
            stringBuilder.append(this.mChangedDatasetIds);
            stringBuilder.append(", manuallyFilledFieldIds=");
            stringBuilder.append(this.mManuallyFilledFieldIds);
            stringBuilder.append(", manuallyFilledDatasetIds=");
            stringBuilder.append(this.mManuallyFilledDatasetIds);
            stringBuilder.append(", detectedFieldIds=");
            stringBuilder.append(Arrays.toString(this.mDetectedFieldIds));
            stringBuilder.append(", detectedFieldClassifications =");
            stringBuilder.append(Arrays.toString(this.mDetectedFieldClassifications));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Retention(value=RetentionPolicy.SOURCE)
        static @interface EventIds {
        }

    }

}

