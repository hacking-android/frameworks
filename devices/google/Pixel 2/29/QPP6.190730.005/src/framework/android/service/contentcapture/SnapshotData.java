/*
 * Decompiled with CFR 0.145.
 */
package android.service.contentcapture;

import android.annotation.SystemApi;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class SnapshotData
implements Parcelable {
    public static final Parcelable.Creator<SnapshotData> CREATOR = new Parcelable.Creator<SnapshotData>(){

        @Override
        public SnapshotData createFromParcel(Parcel parcel) {
            return new SnapshotData(parcel);
        }

        public SnapshotData[] newArray(int n) {
            return new SnapshotData[n];
        }
    };
    private final AssistContent mAssistContent;
    private final Bundle mAssistData;
    private final AssistStructure mAssistStructure;

    public SnapshotData(Bundle bundle, AssistStructure assistStructure, AssistContent assistContent) {
        this.mAssistData = bundle;
        this.mAssistStructure = assistStructure;
        this.mAssistContent = assistContent;
    }

    SnapshotData(Parcel parcel) {
        this.mAssistData = parcel.readBundle();
        this.mAssistStructure = (AssistStructure)parcel.readParcelable(null);
        this.mAssistContent = (AssistContent)parcel.readParcelable(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public AssistContent getAssistContent() {
        return this.mAssistContent;
    }

    public Bundle getAssistData() {
        return this.mAssistData;
    }

    public AssistStructure getAssistStructure() {
        return this.mAssistStructure;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.mAssistData);
        parcel.writeParcelable(this.mAssistStructure, n);
        parcel.writeParcelable(this.mAssistContent, n);
    }

}

