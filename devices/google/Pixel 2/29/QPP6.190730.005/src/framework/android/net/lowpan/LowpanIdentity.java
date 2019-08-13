/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.StringPrep
 *  android.icu.text.StringPrepParseException
 */
package android.net.lowpan;

import android.icu.text.StringPrep;
import android.icu.text.StringPrepParseException;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.util.HexDump;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class LowpanIdentity
implements Parcelable {
    public static final Parcelable.Creator<LowpanIdentity> CREATOR;
    private static final String TAG;
    public static final int UNSPECIFIED_CHANNEL = -1;
    public static final int UNSPECIFIED_PANID = -1;
    private int mChannel = -1;
    private boolean mIsNameValid = true;
    private String mName = "";
    private int mPanid = -1;
    private byte[] mRawName = new byte[0];
    private String mType = "";
    private byte[] mXpanid = new byte[0];

    static {
        TAG = LowpanIdentity.class.getSimpleName();
        CREATOR = new Parcelable.Creator<LowpanIdentity>(){

            @Override
            public LowpanIdentity createFromParcel(Parcel parcel) {
                Builder builder = new Builder();
                builder.setRawName(parcel.createByteArray());
                builder.setType(parcel.readString());
                builder.setXpanid(parcel.createByteArray());
                builder.setPanid(parcel.readInt());
                builder.setChannel(parcel.readInt());
                return builder.build();
            }

            public LowpanIdentity[] newArray(int n) {
                return new LowpanIdentity[n];
            }
        };
    }

    LowpanIdentity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof LowpanIdentity;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (LowpanIdentity)object;
            if (!Arrays.equals(this.mRawName, ((LowpanIdentity)object).mRawName) || !Arrays.equals(this.mXpanid, ((LowpanIdentity)object).mXpanid) || !this.mType.equals(((LowpanIdentity)object).mType) || this.mPanid != ((LowpanIdentity)object).mPanid || this.mChannel != ((LowpanIdentity)object).mChannel) break block1;
            bl = true;
        }
        return bl;
    }

    public int getChannel() {
        return this.mChannel;
    }

    public String getName() {
        return this.mName;
    }

    public int getPanid() {
        return this.mPanid;
    }

    public byte[] getRawName() {
        return (byte[])this.mRawName.clone();
    }

    public String getType() {
        return this.mType;
    }

    public byte[] getXpanid() {
        return (byte[])this.mXpanid.clone();
    }

    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.mRawName), this.mType, Arrays.hashCode(this.mXpanid), this.mPanid, this.mChannel);
    }

    public boolean isNameValid() {
        return this.mIsNameValid;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Name:");
        stringBuffer.append(this.getName());
        if (this.mType.length() > 0) {
            stringBuffer.append(", Type:");
            stringBuffer.append(this.mType);
        }
        if (this.mXpanid.length > 0) {
            stringBuffer.append(", XPANID:");
            stringBuffer.append(HexDump.toHexString(this.mXpanid));
        }
        if (this.mPanid != -1) {
            stringBuffer.append(", PANID:");
            stringBuffer.append(String.format("0x%04X", this.mPanid));
        }
        if (this.mChannel != -1) {
            stringBuffer.append(", Channel:");
            stringBuffer.append(this.mChannel);
        }
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mRawName);
        parcel.writeString(this.mType);
        parcel.writeByteArray(this.mXpanid);
        parcel.writeInt(this.mPanid);
        parcel.writeInt(this.mChannel);
    }

    public static class Builder {
        private static final StringPrep stringPrep = StringPrep.getInstance((int)8);
        final LowpanIdentity mIdentity = new LowpanIdentity();

        private static String escape(byte[] arrby) {
            StringBuffer stringBuffer = new StringBuffer();
            for (byte by : arrby) {
                if (by >= 32 && by <= 126) {
                    stringBuffer.append((char)by);
                    continue;
                }
                stringBuffer.append(String.format("\\0x%02x", by & 255));
            }
            return stringBuffer.toString();
        }

        public LowpanIdentity build() {
            return this.mIdentity;
        }

        public Builder setChannel(int n) {
            this.mIdentity.mChannel = n;
            return this;
        }

        public Builder setLowpanIdentity(LowpanIdentity lowpanIdentity) {
            Objects.requireNonNull(lowpanIdentity);
            this.setRawName(lowpanIdentity.getRawName());
            this.setXpanid(lowpanIdentity.getXpanid());
            this.setPanid(lowpanIdentity.getPanid());
            this.setChannel(lowpanIdentity.getChannel());
            this.setType(lowpanIdentity.getType());
            return this;
        }

        public Builder setName(String string2) {
            Objects.requireNonNull(string2);
            try {
                this.mIdentity.mName = Builder.stringPrep.prepare(string2, 0);
                this.mIdentity.mRawName = this.mIdentity.mName.getBytes(StandardCharsets.UTF_8);
                this.mIdentity.mIsNameValid = true;
            }
            catch (StringPrepParseException stringPrepParseException) {
                Log.w(TAG, stringPrepParseException.toString());
                this.setRawName(string2.getBytes(StandardCharsets.UTF_8));
            }
            return this;
        }

        public Builder setPanid(int n) {
            this.mIdentity.mPanid = n;
            return this;
        }

        public Builder setRawName(byte[] arrby) {
            Object object;
            Objects.requireNonNull(arrby);
            this.mIdentity.mRawName = (byte[])arrby.clone();
            this.mIdentity.mName = new String(arrby, StandardCharsets.UTF_8);
            try {
                object = stringPrep.prepare(this.mIdentity.mName, 0);
                this.mIdentity.mIsNameValid = Arrays.equals(((String)object).getBytes(StandardCharsets.UTF_8), arrby);
            }
            catch (StringPrepParseException stringPrepParseException) {
                Log.w(TAG, stringPrepParseException.toString());
                this.mIdentity.mIsNameValid = false;
            }
            if (!this.mIdentity.mIsNameValid) {
                object = this.mIdentity;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\u00ab");
                stringBuilder.append(Builder.escape(arrby));
                stringBuilder.append("\u00bb");
                ((LowpanIdentity)object).mName = stringBuilder.toString();
            }
            return this;
        }

        public Builder setType(String string2) {
            this.mIdentity.mType = string2;
            return this;
        }

        public Builder setXpanid(byte[] object) {
            LowpanIdentity lowpanIdentity = this.mIdentity;
            object = object != null ? (byte[])object.clone() : null;
            lowpanIdentity.mXpanid = object;
            return this;
        }
    }

}

