/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public final class MidiDeviceInfo
implements Parcelable {
    public static final Parcelable.Creator<MidiDeviceInfo> CREATOR = new Parcelable.Creator<MidiDeviceInfo>(){

        @Override
        public MidiDeviceInfo createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            String[] arrstring = parcel.createStringArray();
            String[] arrstring2 = parcel.createStringArray();
            boolean bl = parcel.readInt() == 1;
            parcel.readBundle();
            return new MidiDeviceInfo(n, n2, n3, n4, arrstring, arrstring2, parcel.readBundle(), bl);
        }

        public MidiDeviceInfo[] newArray(int n) {
            return new MidiDeviceInfo[n];
        }
    };
    public static final String PROPERTY_ALSA_CARD = "alsa_card";
    public static final String PROPERTY_ALSA_DEVICE = "alsa_device";
    public static final String PROPERTY_BLUETOOTH_DEVICE = "bluetooth_device";
    public static final String PROPERTY_MANUFACTURER = "manufacturer";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_PRODUCT = "product";
    public static final String PROPERTY_SERIAL_NUMBER = "serial_number";
    public static final String PROPERTY_SERVICE_INFO = "service_info";
    public static final String PROPERTY_USB_DEVICE = "usb_device";
    public static final String PROPERTY_VERSION = "version";
    private static final String TAG = "MidiDeviceInfo";
    public static final int TYPE_BLUETOOTH = 3;
    public static final int TYPE_USB = 1;
    public static final int TYPE_VIRTUAL = 2;
    private final int mId;
    private final int mInputPortCount;
    private final String[] mInputPortNames;
    private final boolean mIsPrivate;
    private final int mOutputPortCount;
    private final String[] mOutputPortNames;
    private final Bundle mProperties;
    private final int mType;

    public MidiDeviceInfo(int n, int n2, int n3, int n4, String[] arrstring, String[] arrstring2, Bundle bundle, boolean bl) {
        this.mType = n;
        this.mId = n2;
        this.mInputPortCount = n3;
        this.mOutputPortCount = n4;
        this.mInputPortNames = arrstring == null ? new String[n3] : arrstring;
        this.mOutputPortNames = arrstring2 == null ? new String[n4] : arrstring2;
        this.mProperties = bundle;
        this.mIsPrivate = bl;
    }

    private Bundle getBasicProperties(String[] arrstring) {
        Bundle bundle = new Bundle();
        for (String string2 : arrstring) {
            Object object = this.mProperties.get(string2);
            if (object == null) continue;
            if (object instanceof String) {
                bundle.putString(string2, (String)object);
                continue;
            }
            if (object instanceof Integer) {
                bundle.putInt(string2, (Integer)object);
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported property type: ");
            stringBuilder.append(object.getClass().getName());
            Log.w(TAG, stringBuilder.toString());
        }
        return bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof MidiDeviceInfo;
        boolean bl2 = false;
        if (bl) {
            if (((MidiDeviceInfo)object).mId == this.mId) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int getId() {
        return this.mId;
    }

    public int getInputPortCount() {
        return this.mInputPortCount;
    }

    public int getOutputPortCount() {
        return this.mOutputPortCount;
    }

    public PortInfo[] getPorts() {
        PortInfo[] arrportInfo = new PortInfo[this.mInputPortCount + this.mOutputPortCount];
        int n = 0;
        int n2 = 0;
        while (n2 < this.mInputPortCount) {
            arrportInfo[n] = new PortInfo(1, n2, this.mInputPortNames[n2]);
            ++n2;
            ++n;
        }
        n2 = 0;
        while (n2 < this.mOutputPortCount) {
            arrportInfo[n] = new PortInfo(2, n2, this.mOutputPortNames[n2]);
            ++n2;
            ++n;
        }
        return arrportInfo;
    }

    public Bundle getProperties() {
        return this.mProperties;
    }

    public int getType() {
        return this.mType;
    }

    public int hashCode() {
        return this.mId;
    }

    public boolean isPrivate() {
        return this.mIsPrivate;
    }

    public String toString() {
        this.mProperties.getString(PROPERTY_NAME);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MidiDeviceInfo[mType=");
        stringBuilder.append(this.mType);
        stringBuilder.append(",mInputPortCount=");
        stringBuilder.append(this.mInputPortCount);
        stringBuilder.append(",mOutputPortCount=");
        stringBuilder.append(this.mOutputPortCount);
        stringBuilder.append(",mProperties=");
        stringBuilder.append(this.mProperties);
        stringBuilder.append(",mIsPrivate=");
        stringBuilder.append(this.mIsPrivate);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mInputPortCount);
        parcel.writeInt(this.mOutputPortCount);
        parcel.writeStringArray(this.mInputPortNames);
        parcel.writeStringArray(this.mOutputPortNames);
        parcel.writeInt((int)this.mIsPrivate);
        parcel.writeBundle(this.getBasicProperties(new String[]{PROPERTY_NAME, PROPERTY_MANUFACTURER, PROPERTY_PRODUCT, PROPERTY_VERSION, PROPERTY_SERIAL_NUMBER, PROPERTY_ALSA_CARD, PROPERTY_ALSA_DEVICE}));
        parcel.writeBundle(this.mProperties);
    }

    public static final class PortInfo {
        public static final int TYPE_INPUT = 1;
        public static final int TYPE_OUTPUT = 2;
        private final String mName;
        private final int mPortNumber;
        private final int mPortType;

        PortInfo(int n, int n2, String string2) {
            this.mPortType = n;
            this.mPortNumber = n2;
            if (string2 == null) {
                string2 = "";
            }
            this.mName = string2;
        }

        public String getName() {
            return this.mName;
        }

        public int getPortNumber() {
            return this.mPortNumber;
        }

        public int getType() {
            return this.mPortType;
        }
    }

}

