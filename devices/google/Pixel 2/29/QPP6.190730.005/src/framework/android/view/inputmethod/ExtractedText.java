/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class ExtractedText
implements Parcelable {
    public static final Parcelable.Creator<ExtractedText> CREATOR = new Parcelable.Creator<ExtractedText>(){

        @Override
        public ExtractedText createFromParcel(Parcel parcel) {
            ExtractedText extractedText = new ExtractedText();
            extractedText.text = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            extractedText.startOffset = parcel.readInt();
            extractedText.partialStartOffset = parcel.readInt();
            extractedText.partialEndOffset = parcel.readInt();
            extractedText.selectionStart = parcel.readInt();
            extractedText.selectionEnd = parcel.readInt();
            extractedText.flags = parcel.readInt();
            extractedText.hint = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            return extractedText;
        }

        public ExtractedText[] newArray(int n) {
            return new ExtractedText[n];
        }
    };
    public static final int FLAG_SELECTING = 2;
    public static final int FLAG_SINGLE_LINE = 1;
    public int flags;
    public CharSequence hint;
    public int partialEndOffset;
    public int partialStartOffset;
    public int selectionEnd;
    public int selectionStart;
    public int startOffset;
    public CharSequence text;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        TextUtils.writeToParcel(this.text, parcel, n);
        parcel.writeInt(this.startOffset);
        parcel.writeInt(this.partialStartOffset);
        parcel.writeInt(this.partialEndOffset);
        parcel.writeInt(this.selectionStart);
        parcel.writeInt(this.selectionEnd);
        parcel.writeInt(this.flags);
        TextUtils.writeToParcel(this.hint, parcel, n);
    }

}

