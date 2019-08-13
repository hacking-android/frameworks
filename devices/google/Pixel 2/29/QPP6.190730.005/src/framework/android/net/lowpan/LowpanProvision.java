/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanCredential;
import android.net.lowpan.LowpanIdentity;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class LowpanProvision
implements Parcelable {
    public static final Parcelable.Creator<LowpanProvision> CREATOR = new Parcelable.Creator<LowpanProvision>(){

        @Override
        public LowpanProvision createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            builder.setLowpanIdentity(LowpanIdentity.CREATOR.createFromParcel(parcel));
            if (parcel.readBoolean()) {
                builder.setLowpanCredential(LowpanCredential.CREATOR.createFromParcel(parcel));
            }
            return builder.build();
        }

        public LowpanProvision[] newArray(int n) {
            return new LowpanProvision[n];
        }
    };
    private LowpanCredential mCredential = null;
    private LowpanIdentity mIdentity = new LowpanIdentity();

    private LowpanProvision() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof LowpanProvision)) {
            return false;
        }
        object = (LowpanProvision)object;
        if (!this.mIdentity.equals(((LowpanProvision)object).mIdentity)) {
            return false;
        }
        return Objects.equals(this.mCredential, ((LowpanProvision)object).mCredential);
    }

    public LowpanCredential getLowpanCredential() {
        return this.mCredential;
    }

    public LowpanIdentity getLowpanIdentity() {
        return this.mIdentity;
    }

    public int hashCode() {
        return Objects.hash(this.mIdentity, this.mCredential);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("LowpanProvision { identity => ");
        stringBuffer.append(this.mIdentity.toString());
        if (this.mCredential != null) {
            stringBuffer.append(", credential => ");
            stringBuffer.append(this.mCredential.toString());
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mIdentity.writeToParcel(parcel, n);
        if (this.mCredential == null) {
            parcel.writeBoolean(false);
        } else {
            parcel.writeBoolean(true);
            this.mCredential.writeToParcel(parcel, n);
        }
    }

    public static class Builder {
        private final LowpanProvision provision = new LowpanProvision();

        public LowpanProvision build() {
            return this.provision;
        }

        public Builder setLowpanCredential(LowpanCredential lowpanCredential) {
            this.provision.mCredential = lowpanCredential;
            return this;
        }

        public Builder setLowpanIdentity(LowpanIdentity lowpanIdentity) {
            this.provision.mIdentity = lowpanIdentity;
            return this;
        }
    }

}

