/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class StatusHints
implements Parcelable {
    public static final Parcelable.Creator<StatusHints> CREATOR = new Parcelable.Creator<StatusHints>(){

        @Override
        public StatusHints createFromParcel(Parcel parcel) {
            return new StatusHints(parcel);
        }

        public StatusHints[] newArray(int n) {
            return new StatusHints[n];
        }
    };
    private final Bundle mExtras;
    private final Icon mIcon;
    private final CharSequence mLabel;

    @SystemApi
    @Deprecated
    public StatusHints(ComponentName parcelable, CharSequence charSequence, int n, Bundle bundle) {
        parcelable = n == 0 ? null : Icon.createWithResource(parcelable.getPackageName(), n);
        this(charSequence, (Icon)parcelable, bundle);
    }

    private StatusHints(Parcel parcel) {
        this.mLabel = parcel.readCharSequence();
        this.mIcon = (Icon)parcel.readParcelable(this.getClass().getClassLoader());
        this.mExtras = (Bundle)parcel.readParcelable(this.getClass().getClassLoader());
    }

    public StatusHints(CharSequence charSequence, Icon icon, Bundle bundle) {
        this.mLabel = charSequence;
        this.mIcon = icon;
        this.mExtras = bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof StatusHints) {
            if (Objects.equals(((StatusHints)(object = (StatusHints)object)).getLabel(), this.getLabel()) && Objects.equals(((StatusHints)object).getIcon(), this.getIcon()) && Objects.equals(((StatusHints)object).getExtras(), this.getExtras())) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @SystemApi
    @Deprecated
    public Drawable getIcon(Context context) {
        return this.mIcon.loadDrawable(context);
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    @SystemApi
    @Deprecated
    public int getIconResId() {
        return 0;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    @SystemApi
    @Deprecated
    public ComponentName getPackageName() {
        return new ComponentName("", "");
    }

    public int hashCode() {
        return Objects.hashCode(this.mLabel) + Objects.hashCode(this.mIcon) + Objects.hashCode(this.mExtras);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeParcelable(this.mIcon, 0);
        parcel.writeParcelable(this.mExtras, 0);
    }

}

