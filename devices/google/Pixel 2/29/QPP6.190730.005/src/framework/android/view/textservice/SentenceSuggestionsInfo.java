/*
 * Decompiled with CFR 0.145.
 */
package android.view.textservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.textservice.SuggestionsInfo;
import java.util.Arrays;

public final class SentenceSuggestionsInfo
implements Parcelable {
    public static final Parcelable.Creator<SentenceSuggestionsInfo> CREATOR = new Parcelable.Creator<SentenceSuggestionsInfo>(){

        @Override
        public SentenceSuggestionsInfo createFromParcel(Parcel parcel) {
            return new SentenceSuggestionsInfo(parcel);
        }

        public SentenceSuggestionsInfo[] newArray(int n) {
            return new SentenceSuggestionsInfo[n];
        }
    };
    private final int[] mLengths;
    private final int[] mOffsets;
    private final SuggestionsInfo[] mSuggestionsInfos;

    public SentenceSuggestionsInfo(Parcel parcel) {
        this.mSuggestionsInfos = new SuggestionsInfo[parcel.readInt()];
        parcel.readTypedArray(this.mSuggestionsInfos, SuggestionsInfo.CREATOR);
        this.mOffsets = new int[this.mSuggestionsInfos.length];
        parcel.readIntArray(this.mOffsets);
        this.mLengths = new int[this.mSuggestionsInfos.length];
        parcel.readIntArray(this.mLengths);
    }

    public SentenceSuggestionsInfo(SuggestionsInfo[] arrsuggestionsInfo, int[] arrn, int[] arrn2) {
        if (arrsuggestionsInfo != null && arrn != null && arrn2 != null) {
            if (arrsuggestionsInfo.length == arrn.length && arrn.length == arrn2.length) {
                int n = arrsuggestionsInfo.length;
                this.mSuggestionsInfos = Arrays.copyOf(arrsuggestionsInfo, n);
                this.mOffsets = Arrays.copyOf(arrn, n);
                this.mLengths = Arrays.copyOf(arrn2, n);
                return;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getLengthAt(int n) {
        int[] arrn;
        if (n >= 0 && n < (arrn = this.mLengths).length) {
            return arrn[n];
        }
        return -1;
    }

    public int getOffsetAt(int n) {
        int[] arrn;
        if (n >= 0 && n < (arrn = this.mOffsets).length) {
            return arrn[n];
        }
        return -1;
    }

    public int getSuggestionsCount() {
        return this.mSuggestionsInfos.length;
    }

    public SuggestionsInfo getSuggestionsInfoAt(int n) {
        SuggestionsInfo[] arrsuggestionsInfo;
        if (n >= 0 && n < (arrsuggestionsInfo = this.mSuggestionsInfos).length) {
            return arrsuggestionsInfo[n];
        }
        return null;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSuggestionsInfos.length);
        parcel.writeTypedArray((Parcelable[])this.mSuggestionsInfos, 0);
        parcel.writeIntArray(this.mOffsets);
        parcel.writeIntArray(this.mLengths);
    }

}

