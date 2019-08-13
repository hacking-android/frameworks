/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Locale;

public final class TextClassificationContext
implements Parcelable {
    public static final Parcelable.Creator<TextClassificationContext> CREATOR = new Parcelable.Creator<TextClassificationContext>(){

        @Override
        public TextClassificationContext createFromParcel(Parcel parcel) {
            return new TextClassificationContext(parcel);
        }

        public TextClassificationContext[] newArray(int n) {
            return new TextClassificationContext[n];
        }
    };
    private final String mPackageName;
    private final String mWidgetType;
    private final String mWidgetVersion;

    private TextClassificationContext(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mWidgetType = parcel.readString();
        this.mWidgetVersion = parcel.readString();
    }

    private TextClassificationContext(String string2, String string3, String string4) {
        this.mPackageName = Preconditions.checkNotNull(string2);
        this.mWidgetType = Preconditions.checkNotNull(string3);
        this.mWidgetVersion = string4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getWidgetType() {
        return this.mWidgetType;
    }

    public String getWidgetVersion() {
        return this.mWidgetVersion;
    }

    public String toString() {
        return String.format(Locale.US, "TextClassificationContext{packageName=%s, widgetType=%s, widgetVersion=%s}", this.mPackageName, this.mWidgetType, this.mWidgetVersion);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mWidgetType);
        parcel.writeString(this.mWidgetVersion);
    }

    public static final class Builder {
        private final String mPackageName;
        private final String mWidgetType;
        private String mWidgetVersion;

        public Builder(String string2, String string3) {
            this.mPackageName = Preconditions.checkNotNull(string2);
            this.mWidgetType = Preconditions.checkNotNull(string3);
        }

        public TextClassificationContext build() {
            return new TextClassificationContext(this.mPackageName, this.mWidgetType, this.mWidgetVersion);
        }

        public Builder setWidgetVersion(String string2) {
            this.mWidgetVersion = string2;
            return this;
        }
    }

}

