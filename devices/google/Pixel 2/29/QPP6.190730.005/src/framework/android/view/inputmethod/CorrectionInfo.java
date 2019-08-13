/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public final class CorrectionInfo
implements Parcelable {
    public static final Parcelable.Creator<CorrectionInfo> CREATOR = new Parcelable.Creator<CorrectionInfo>(){

        @Override
        public CorrectionInfo createFromParcel(Parcel parcel) {
            return new CorrectionInfo(parcel);
        }

        public CorrectionInfo[] newArray(int n) {
            return new CorrectionInfo[n];
        }
    };
    private final CharSequence mNewText;
    private final int mOffset;
    private final CharSequence mOldText;

    public CorrectionInfo(int n, CharSequence charSequence, CharSequence charSequence2) {
        this.mOffset = n;
        this.mOldText = charSequence;
        this.mNewText = charSequence2;
    }

    private CorrectionInfo(Parcel parcel) {
        this.mOffset = parcel.readInt();
        this.mOldText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mNewText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CharSequence getNewText() {
        return this.mNewText;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public CharSequence getOldText() {
        return this.mOldText;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CorrectionInfo{#");
        stringBuilder.append(this.mOffset);
        stringBuilder.append(" \"");
        stringBuilder.append((Object)this.mOldText);
        stringBuilder.append("\" -> \"");
        stringBuilder.append((Object)this.mNewText);
        stringBuilder.append("\"}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mOffset);
        TextUtils.writeToParcel(this.mOldText, parcel, n);
        TextUtils.writeToParcel(this.mNewText, parcel, n);
    }

}

