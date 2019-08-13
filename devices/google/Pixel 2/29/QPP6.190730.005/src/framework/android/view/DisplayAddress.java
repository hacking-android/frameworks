/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public abstract class DisplayAddress
implements Parcelable {
    public static Network fromMacAddress(String string2) {
        return new Network(string2);
    }

    public static Physical fromPhysicalDisplayId(long l) {
        return new Physical(l);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final class Network
    extends DisplayAddress {
        public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>(){

            @Override
            public Network createFromParcel(Parcel parcel) {
                return new Network(parcel.readString());
            }

            public Network[] newArray(int n) {
                return new Network[n];
            }
        };
        private final String mMacAddress;

        private Network(String string2) {
            this.mMacAddress = string2;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Network && this.mMacAddress.equals(((Network)object).mMacAddress);
            return bl;
        }

        public int hashCode() {
            return this.mMacAddress.hashCode();
        }

        public String toString() {
            return this.mMacAddress;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mMacAddress);
        }

    }

    public static final class Physical
    extends DisplayAddress {
        public static final Parcelable.Creator<Physical> CREATOR = new Parcelable.Creator<Physical>(){

            @Override
            public Physical createFromParcel(Parcel parcel) {
                return new Physical(parcel.readLong());
            }

            public Physical[] newArray(int n) {
                return new Physical[n];
            }
        };
        private static final int MODEL_SHIFT = 8;
        private static final int PORT_MASK = 255;
        private static final long UNKNOWN_MODEL = 0L;
        private final long mPhysicalDisplayId;

        private Physical(long l) {
            this.mPhysicalDisplayId = l;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Physical && this.mPhysicalDisplayId == ((Physical)object).mPhysicalDisplayId;
            return bl;
        }

        public Long getModel() {
            long l = this.mPhysicalDisplayId >>> 8;
            Long l2 = l == 0L ? null : Long.valueOf(l);
            return l2;
        }

        public byte getPort() {
            return (byte)this.mPhysicalDisplayId;
        }

        public int hashCode() {
            return Long.hashCode(this.mPhysicalDisplayId);
        }

        public String toString() {
            Serializable serializable = new StringBuilder("{");
            ((StringBuilder)serializable).append("port=");
            StringBuilder stringBuilder = ((StringBuilder)serializable).append(this.getPort() & 255);
            serializable = this.getModel();
            if (serializable != null) {
                stringBuilder.append(", model=0x");
                stringBuilder.append(Long.toHexString((Long)serializable));
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.mPhysicalDisplayId);
        }

    }

}

