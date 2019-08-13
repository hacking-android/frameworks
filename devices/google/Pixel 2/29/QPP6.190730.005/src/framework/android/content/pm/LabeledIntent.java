/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class LabeledIntent
extends Intent {
    public static final Parcelable.Creator<LabeledIntent> CREATOR = new Parcelable.Creator<LabeledIntent>(){

        @Override
        public LabeledIntent createFromParcel(Parcel parcel) {
            return new LabeledIntent(parcel);
        }

        public LabeledIntent[] newArray(int n) {
            return new LabeledIntent[n];
        }
    };
    private int mIcon;
    private int mLabelRes;
    private CharSequence mNonLocalizedLabel;
    private String mSourcePackage;

    public LabeledIntent(Intent intent, String string2, int n, int n2) {
        super(intent);
        this.mSourcePackage = string2;
        this.mLabelRes = n;
        this.mNonLocalizedLabel = null;
        this.mIcon = n2;
    }

    public LabeledIntent(Intent intent, String string2, CharSequence charSequence, int n) {
        super(intent);
        this.mSourcePackage = string2;
        this.mLabelRes = 0;
        this.mNonLocalizedLabel = charSequence;
        this.mIcon = n;
    }

    protected LabeledIntent(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public LabeledIntent(String string2, int n, int n2) {
        this.mSourcePackage = string2;
        this.mLabelRes = n;
        this.mNonLocalizedLabel = null;
        this.mIcon = n2;
    }

    public LabeledIntent(String string2, CharSequence charSequence, int n) {
        this.mSourcePackage = string2;
        this.mLabelRes = 0;
        this.mNonLocalizedLabel = charSequence;
        this.mIcon = n;
    }

    public int getIconResource() {
        return this.mIcon;
    }

    public int getLabelResource() {
        return this.mLabelRes;
    }

    public CharSequence getNonLocalizedLabel() {
        return this.mNonLocalizedLabel;
    }

    public String getSourcePackage() {
        return this.mSourcePackage;
    }

    public Drawable loadIcon(PackageManager object) {
        String string2;
        int n = this.mIcon;
        if (n != 0 && (string2 = this.mSourcePackage) != null && (object = ((PackageManager)object).getDrawable(string2, n, null)) != null) {
            return object;
        }
        return null;
    }

    public CharSequence loadLabel(PackageManager object) {
        CharSequence charSequence = this.mNonLocalizedLabel;
        if (charSequence != null) {
            return charSequence;
        }
        int n = this.mLabelRes;
        if (n != 0 && (charSequence = this.mSourcePackage) != null && (object = ((PackageManager)object).getText((String)charSequence, n, null)) != null) {
            return object;
        }
        return null;
    }

    @Override
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.mSourcePackage = parcel.readString();
        this.mLabelRes = parcel.readInt();
        this.mNonLocalizedLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.mSourcePackage);
        parcel.writeInt(this.mLabelRes);
        TextUtils.writeToParcel(this.mNonLocalizedLabel, parcel, n);
        parcel.writeInt(this.mIcon);
    }

}

