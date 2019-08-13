/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class VisualVoicemailSmsFilterSettings
implements Parcelable {
    public static final Parcelable.Creator<VisualVoicemailSmsFilterSettings> CREATOR;
    public static final String DEFAULT_CLIENT_PREFIX = "//VVM";
    public static final int DEFAULT_DESTINATION_PORT = -1;
    public static final List<String> DEFAULT_ORIGINATING_NUMBERS;
    public static final int DESTINATION_PORT_ANY = -1;
    public static final int DESTINATION_PORT_DATA_SMS = -2;
    public final String clientPrefix;
    public final int destinationPort;
    public final List<String> originatingNumbers;
    public final String packageName;

    static {
        DEFAULT_ORIGINATING_NUMBERS = Collections.emptyList();
        CREATOR = new Parcelable.Creator<VisualVoicemailSmsFilterSettings>(){

            @Override
            public VisualVoicemailSmsFilterSettings createFromParcel(Parcel parcel) {
                Builder builder = new Builder();
                builder.setClientPrefix(parcel.readString());
                builder.setOriginatingNumbers(parcel.createStringArrayList());
                builder.setDestinationPort(parcel.readInt());
                builder.setPackageName(parcel.readString());
                return builder.build();
            }

            public VisualVoicemailSmsFilterSettings[] newArray(int n) {
                return new VisualVoicemailSmsFilterSettings[n];
            }
        };
    }

    private VisualVoicemailSmsFilterSettings(Builder builder) {
        this.clientPrefix = builder.mClientPrefix;
        this.originatingNumbers = builder.mOriginatingNumbers;
        this.destinationPort = builder.mDestinationPort;
        this.packageName = builder.mPackageName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[VisualVoicemailSmsFilterSettings clientPrefix=");
        stringBuilder.append(this.clientPrefix);
        stringBuilder.append(", originatingNumbers=");
        stringBuilder.append(this.originatingNumbers);
        stringBuilder.append(", destinationPort=");
        stringBuilder.append(this.destinationPort);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.clientPrefix);
        parcel.writeStringList(this.originatingNumbers);
        parcel.writeInt(this.destinationPort);
        parcel.writeString(this.packageName);
    }

    public static class Builder {
        private String mClientPrefix = "//VVM";
        private int mDestinationPort = -1;
        private List<String> mOriginatingNumbers = DEFAULT_ORIGINATING_NUMBERS;
        private String mPackageName;

        public VisualVoicemailSmsFilterSettings build() {
            return new VisualVoicemailSmsFilterSettings(this);
        }

        public Builder setClientPrefix(String string2) {
            if (string2 != null) {
                this.mClientPrefix = string2;
                return this;
            }
            throw new IllegalArgumentException("Client prefix cannot be null");
        }

        public Builder setDestinationPort(int n) {
            this.mDestinationPort = n;
            return this;
        }

        public Builder setOriginatingNumbers(List<String> list) {
            if (list != null) {
                this.mOriginatingNumbers = list;
                return this;
            }
            throw new IllegalArgumentException("Originating numbers cannot be null");
        }

        public Builder setPackageName(String string2) {
            this.mPackageName = string2;
            return this;
        }
    }

}

