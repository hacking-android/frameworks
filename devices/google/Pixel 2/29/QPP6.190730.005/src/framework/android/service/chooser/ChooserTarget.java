/*
 * Decompiled with CFR 0.145.
 */
package android.service.chooser;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public final class ChooserTarget
implements Parcelable {
    public static final Parcelable.Creator<ChooserTarget> CREATOR = new Parcelable.Creator<ChooserTarget>(){

        @Override
        public ChooserTarget createFromParcel(Parcel parcel) {
            return new ChooserTarget(parcel);
        }

        public ChooserTarget[] newArray(int n) {
            return new ChooserTarget[n];
        }
    };
    private static final String TAG = "ChooserTarget";
    private ComponentName mComponentName;
    private Icon mIcon;
    private Bundle mIntentExtras;
    private float mScore;
    private CharSequence mTitle;

    ChooserTarget(Parcel parcel) {
        this.mTitle = parcel.readCharSequence();
        this.mIcon = parcel.readInt() != 0 ? Icon.CREATOR.createFromParcel(parcel) : null;
        this.mScore = parcel.readFloat();
        this.mComponentName = ComponentName.readFromParcel(parcel);
        this.mIntentExtras = parcel.readBundle();
    }

    public ChooserTarget(CharSequence charSequence, Icon icon, float f, ComponentName componentName, Bundle bundle) {
        this.mTitle = charSequence;
        this.mIcon = icon;
        if (!(f > 1.0f) && !(f < 0.0f)) {
            this.mScore = f;
            this.mComponentName = componentName;
            this.mIntentExtras = bundle;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Score ");
        ((StringBuilder)charSequence).append(f);
        ((StringBuilder)charSequence).append(" out of range; must be between 0.0f and 1.0f");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public Bundle getIntentExtras() {
        return this.mIntentExtras;
    }

    public float getScore() {
        return this.mScore;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ChooserTarget{");
        stringBuilder.append(this.mComponentName);
        stringBuilder.append(", ");
        stringBuilder.append(this.mIntentExtras);
        stringBuilder.append(", '");
        stringBuilder.append((Object)this.mTitle);
        stringBuilder.append("', ");
        stringBuilder.append(this.mScore);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeCharSequence(this.mTitle);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeFloat(this.mScore);
        ComponentName.writeToParcel(this.mComponentName, parcel);
        parcel.writeBundle(this.mIntentExtras);
    }

}

