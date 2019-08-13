/*
 * Decompiled with CFR 0.145.
 */
package android.view.textservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.SpellCheckSpan;

public final class TextInfo
implements Parcelable {
    public static final Parcelable.Creator<TextInfo> CREATOR = new Parcelable.Creator<TextInfo>(){

        @Override
        public TextInfo createFromParcel(Parcel parcel) {
            return new TextInfo(parcel);
        }

        public TextInfo[] newArray(int n) {
            return new TextInfo[n];
        }
    };
    private static final int DEFAULT_COOKIE = 0;
    private static final int DEFAULT_SEQUENCE_NUMBER = 0;
    private final CharSequence mCharSequence;
    private final int mCookie;
    private final int mSequenceNumber;

    public TextInfo(Parcel parcel) {
        this.mCharSequence = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mCookie = parcel.readInt();
        this.mSequenceNumber = parcel.readInt();
    }

    public TextInfo(CharSequence charSequence, int n, int n2, int n3, int n4) {
        if (!TextUtils.isEmpty(charSequence)) {
            charSequence = new SpannableStringBuilder(charSequence, n, n2);
            SpellCheckSpan[] arrspellCheckSpan = ((SpannableStringBuilder)charSequence).getSpans(0, ((SpannableStringBuilder)charSequence).length(), SpellCheckSpan.class);
            for (n = 0; n < arrspellCheckSpan.length; ++n) {
                ((SpannableStringBuilder)charSequence).removeSpan(arrspellCheckSpan[n]);
            }
            this.mCharSequence = charSequence;
            this.mCookie = n3;
            this.mSequenceNumber = n4;
            return;
        }
        throw new IllegalArgumentException("charSequence is empty");
    }

    public TextInfo(String string2) {
        this(string2, 0, TextInfo.getStringLengthOrZero(string2), 0, 0);
    }

    public TextInfo(String string2, int n, int n2) {
        this(string2, 0, TextInfo.getStringLengthOrZero(string2), n, n2);
    }

    private static int getStringLengthOrZero(String string2) {
        int n = TextUtils.isEmpty(string2) ? 0 : string2.length();
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CharSequence getCharSequence() {
        return this.mCharSequence;
    }

    public int getCookie() {
        return this.mCookie;
    }

    public int getSequence() {
        return this.mSequenceNumber;
    }

    public String getText() {
        CharSequence charSequence = this.mCharSequence;
        if (charSequence == null) {
            return null;
        }
        return charSequence.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        TextUtils.writeToParcel(this.mCharSequence, parcel, n);
        parcel.writeInt(this.mCookie);
        parcel.writeInt(this.mSequenceNumber);
    }

}

