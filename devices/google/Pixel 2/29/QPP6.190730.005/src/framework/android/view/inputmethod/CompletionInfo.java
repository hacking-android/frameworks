/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public final class CompletionInfo
implements Parcelable {
    public static final Parcelable.Creator<CompletionInfo> CREATOR = new Parcelable.Creator<CompletionInfo>(){

        @Override
        public CompletionInfo createFromParcel(Parcel parcel) {
            return new CompletionInfo(parcel);
        }

        public CompletionInfo[] newArray(int n) {
            return new CompletionInfo[n];
        }
    };
    private final long mId;
    private final CharSequence mLabel;
    private final int mPosition;
    private final CharSequence mText;

    public CompletionInfo(long l, int n, CharSequence charSequence) {
        this.mId = l;
        this.mPosition = n;
        this.mText = charSequence;
        this.mLabel = null;
    }

    public CompletionInfo(long l, int n, CharSequence charSequence, CharSequence charSequence2) {
        this.mId = l;
        this.mPosition = n;
        this.mText = charSequence;
        this.mLabel = charSequence2;
    }

    private CompletionInfo(Parcel parcel) {
        this.mId = parcel.readLong();
        this.mPosition = parcel.readInt();
        this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return this.mId;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CompletionInfo{#");
        stringBuilder.append(this.mPosition);
        stringBuilder.append(" \"");
        stringBuilder.append((Object)this.mText);
        stringBuilder.append("\" id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(" label=");
        stringBuilder.append((Object)this.mLabel);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mId);
        parcel.writeInt(this.mPosition);
        TextUtils.writeToParcel(this.mText, parcel, n);
        TextUtils.writeToParcel(this.mLabel, parcel, n);
    }

}

