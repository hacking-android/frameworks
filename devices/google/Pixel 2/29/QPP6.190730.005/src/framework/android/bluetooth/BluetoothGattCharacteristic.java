/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BluetoothGattCharacteristic
implements Parcelable {
    public static final Parcelable.Creator<BluetoothGattCharacteristic> CREATOR = new Parcelable.Creator<BluetoothGattCharacteristic>(){

        @Override
        public BluetoothGattCharacteristic createFromParcel(Parcel parcel) {
            return new BluetoothGattCharacteristic(parcel);
        }

        public BluetoothGattCharacteristic[] newArray(int n) {
            return new BluetoothGattCharacteristic[n];
        }
    };
    public static final int FORMAT_FLOAT = 52;
    public static final int FORMAT_SFLOAT = 50;
    public static final int FORMAT_SINT16 = 34;
    public static final int FORMAT_SINT32 = 36;
    public static final int FORMAT_SINT8 = 33;
    public static final int FORMAT_UINT16 = 18;
    public static final int FORMAT_UINT32 = 20;
    public static final int FORMAT_UINT8 = 17;
    public static final int PERMISSION_READ = 1;
    public static final int PERMISSION_READ_ENCRYPTED = 2;
    public static final int PERMISSION_READ_ENCRYPTED_MITM = 4;
    public static final int PERMISSION_WRITE = 16;
    public static final int PERMISSION_WRITE_ENCRYPTED = 32;
    public static final int PERMISSION_WRITE_ENCRYPTED_MITM = 64;
    public static final int PERMISSION_WRITE_SIGNED = 128;
    public static final int PERMISSION_WRITE_SIGNED_MITM = 256;
    public static final int PROPERTY_BROADCAST = 1;
    public static final int PROPERTY_EXTENDED_PROPS = 128;
    public static final int PROPERTY_INDICATE = 32;
    public static final int PROPERTY_NOTIFY = 16;
    public static final int PROPERTY_READ = 2;
    public static final int PROPERTY_SIGNED_WRITE = 64;
    public static final int PROPERTY_WRITE = 8;
    public static final int PROPERTY_WRITE_NO_RESPONSE = 4;
    public static final int WRITE_TYPE_DEFAULT = 2;
    public static final int WRITE_TYPE_NO_RESPONSE = 1;
    public static final int WRITE_TYPE_SIGNED = 4;
    protected List<BluetoothGattDescriptor> mDescriptors;
    @UnsupportedAppUsage
    protected int mInstance;
    protected int mKeySize = 16;
    protected int mPermissions;
    protected int mProperties;
    @UnsupportedAppUsage
    protected BluetoothGattService mService;
    protected UUID mUuid;
    protected byte[] mValue;
    protected int mWriteType;

    BluetoothGattCharacteristic(BluetoothGattService bluetoothGattService, UUID uUID, int n, int n2, int n3) {
        this.initCharacteristic(bluetoothGattService, uUID, n, n2, n3);
    }

    private BluetoothGattCharacteristic(Parcel object) {
        this.mUuid = ((ParcelUuid)((Parcel)object).readParcelable(null)).getUuid();
        this.mInstance = ((Parcel)object).readInt();
        this.mProperties = ((Parcel)object).readInt();
        this.mPermissions = ((Parcel)object).readInt();
        this.mKeySize = ((Parcel)object).readInt();
        this.mWriteType = ((Parcel)object).readInt();
        this.mDescriptors = new ArrayList<BluetoothGattDescriptor>();
        object = ((Parcel)object).createTypedArrayList(BluetoothGattDescriptor.CREATOR);
        if (object != null) {
            Iterator iterator = ((ArrayList)object).iterator();
            while (iterator.hasNext()) {
                object = (BluetoothGattDescriptor)iterator.next();
                ((BluetoothGattDescriptor)object).setCharacteristic(this);
                this.mDescriptors.add((BluetoothGattDescriptor)object);
            }
        }
    }

    public BluetoothGattCharacteristic(UUID uUID, int n, int n2) {
        this.initCharacteristic(null, uUID, 0, n, n2);
    }

    public BluetoothGattCharacteristic(UUID uUID, int n, int n2, int n3) {
        this.initCharacteristic(null, uUID, n, n2, n3);
    }

    private float bytesToFloat(byte by, byte by2) {
        int n = this.unsignedToSigned(this.unsignedByteToInt(by) + ((this.unsignedByteToInt(by2) & 15) << 8), 12);
        int n2 = this.unsignedToSigned(this.unsignedByteToInt(by2) >> 4, 4);
        return (float)((double)n * Math.pow(10.0, n2));
    }

    private float bytesToFloat(byte by, byte by2, byte by3, byte by4) {
        return (float)((double)this.unsignedToSigned(this.unsignedByteToInt(by) + (this.unsignedByteToInt(by2) << 8) + (this.unsignedByteToInt(by3) << 16), 24) * Math.pow(10.0, by4));
    }

    private int getTypeLen(int n) {
        return n & 15;
    }

    private void initCharacteristic(BluetoothGattService bluetoothGattService, UUID uUID, int n, int n2, int n3) {
        this.mUuid = uUID;
        this.mInstance = n;
        this.mProperties = n2;
        this.mPermissions = n3;
        this.mService = bluetoothGattService;
        this.mValue = null;
        this.mDescriptors = new ArrayList<BluetoothGattDescriptor>();
        this.mWriteType = (this.mProperties & 4) != 0 ? 1 : 2;
    }

    private int intToSignedBits(int n, int n2) {
        int n3 = n;
        if (n < 0) {
            n3 = (1 << n2 - 1) + (n & (1 << n2 - 1) - 1);
        }
        return n3;
    }

    private int unsignedByteToInt(byte by) {
        return by & 255;
    }

    private int unsignedBytesToInt(byte by, byte by2) {
        return this.unsignedByteToInt(by) + (this.unsignedByteToInt(by2) << 8);
    }

    private int unsignedBytesToInt(byte by, byte by2, byte by3, byte by4) {
        return this.unsignedByteToInt(by) + (this.unsignedByteToInt(by2) << 8) + (this.unsignedByteToInt(by3) << 16) + (this.unsignedByteToInt(by4) << 24);
    }

    private int unsignedToSigned(int n, int n2) {
        int n3 = n;
        if ((1 << n2 - 1 & n) != 0) {
            n3 = ((1 << n2 - 1) - (n & (1 << n2 - 1) - 1)) * -1;
        }
        return n3;
    }

    public boolean addDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        this.mDescriptors.add(bluetoothGattDescriptor);
        bluetoothGattDescriptor.setCharacteristic(this);
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public BluetoothGattDescriptor getDescriptor(UUID uUID) {
        for (BluetoothGattDescriptor bluetoothGattDescriptor : this.mDescriptors) {
            if (!bluetoothGattDescriptor.getUuid().equals(uUID)) continue;
            return bluetoothGattDescriptor;
        }
        return null;
    }

    BluetoothGattDescriptor getDescriptor(UUID uUID, int n) {
        for (BluetoothGattDescriptor bluetoothGattDescriptor : this.mDescriptors) {
            if (!bluetoothGattDescriptor.getUuid().equals(uUID) || bluetoothGattDescriptor.getInstanceId() != n) continue;
            return bluetoothGattDescriptor;
        }
        return null;
    }

    public List<BluetoothGattDescriptor> getDescriptors() {
        return this.mDescriptors;
    }

    public Float getFloatValue(int n, int n2) {
        byte[] arrby;
        int n3 = this.getTypeLen(n);
        if (n3 + n2 > (arrby = this.mValue).length) {
            return null;
        }
        if (n != 50) {
            if (n != 52) {
                return null;
            }
            return Float.valueOf(this.bytesToFloat(arrby[n2], arrby[n2 + 1], arrby[n2 + 2], arrby[n2 + 3]));
        }
        return Float.valueOf(this.bytesToFloat(arrby[n2], arrby[n2 + 1]));
    }

    public int getInstanceId() {
        return this.mInstance;
    }

    public Integer getIntValue(int n, int n2) {
        byte[] arrby;
        int n3 = this.getTypeLen(n);
        if (n3 + n2 > (arrby = this.mValue).length) {
            return null;
        }
        if (n != 17) {
            if (n != 18) {
                if (n != 20) {
                    if (n != 36) {
                        if (n != 33) {
                            if (n != 34) {
                                return null;
                            }
                            return this.unsignedToSigned(this.unsignedBytesToInt(arrby[n2], arrby[n2 + 1]), 16);
                        }
                        return this.unsignedToSigned(this.unsignedByteToInt(arrby[n2]), 8);
                    }
                    return this.unsignedToSigned(this.unsignedBytesToInt(arrby[n2], arrby[n2 + 1], arrby[n2 + 2], arrby[n2 + 3]), 32);
                }
                return this.unsignedBytesToInt(arrby[n2], arrby[n2 + 1], arrby[n2 + 2], arrby[n2 + 3]);
            }
            return this.unsignedBytesToInt(arrby[n2], arrby[n2 + 1]);
        }
        return this.unsignedByteToInt(arrby[n2]);
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public int getPermissions() {
        return this.mPermissions;
    }

    public int getProperties() {
        return this.mProperties;
    }

    public BluetoothGattService getService() {
        return this.mService;
    }

    public String getStringValue(int n) {
        byte[] arrby = this.mValue;
        if (arrby != null && n <= arrby.length) {
            byte[] arrby2 = new byte[arrby.length - n];
            for (int i = 0; i != (arrby = this.mValue).length - n; ++i) {
                arrby2[i] = arrby[n + i];
            }
            return new String(arrby2);
        }
        return null;
    }

    public UUID getUuid() {
        return this.mUuid;
    }

    public byte[] getValue() {
        return this.mValue;
    }

    public int getWriteType() {
        return this.mWriteType;
    }

    public void setInstanceId(int n) {
        this.mInstance = n;
    }

    @UnsupportedAppUsage
    public void setKeySize(int n) {
        this.mKeySize = n;
    }

    @UnsupportedAppUsage
    void setService(BluetoothGattService bluetoothGattService) {
        this.mService = bluetoothGattService;
    }

    public boolean setValue(int n, int n2, int n3) {
        block8 : {
            int n4;
            block3 : {
                block4 : {
                    block5 : {
                        block6 : {
                            block7 : {
                                n4 = this.getTypeLen(n2) + n3;
                                if (this.mValue == null) {
                                    this.mValue = new byte[n4];
                                }
                                if (n4 > this.mValue.length) {
                                    return false;
                                }
                                n4 = n;
                                if (n2 == 17) break block3;
                                n4 = n;
                                if (n2 == 18) break block4;
                                n4 = n;
                                if (n2 == 20) break block5;
                                if (n2 == 36) break block6;
                                if (n2 == 33) break block7;
                                if (n2 != 34) {
                                    return false;
                                }
                                n4 = this.intToSignedBits(n, 16);
                                break block4;
                            }
                            n4 = this.intToSignedBits(n, 8);
                            break block3;
                        }
                        n4 = this.intToSignedBits(n, 32);
                    }
                    byte[] arrby = this.mValue;
                    n = n3 + 1;
                    arrby[n3] = (byte)(n4 & 255);
                    n2 = n + 1;
                    arrby[n] = (byte)(n4 >> 8 & 255);
                    arrby[n2] = (byte)(n4 >> 16 & 255);
                    arrby[n2 + 1] = (byte)(n4 >> 24 & 255);
                    break block8;
                }
                byte[] arrby = this.mValue;
                arrby[n3] = (byte)(n4 & 255);
                arrby[n3 + 1] = (byte)(n4 >> 8 & 255);
                break block8;
            }
            this.mValue[n3] = (byte)(n4 & 255);
        }
        return true;
    }

    public boolean setValue(int n, int n2, int n3, int n4) {
        int n5 = this.getTypeLen(n3) + n4;
        if (this.mValue == null) {
            this.mValue = new byte[n5];
        }
        if (n5 > this.mValue.length) {
            return false;
        }
        if (n3 != 50) {
            if (n3 != 52) {
                return false;
            }
            n = this.intToSignedBits(n, 24);
            n2 = this.intToSignedBits(n2, 8);
            byte[] arrby = this.mValue;
            n3 = n4 + 1;
            arrby[n4] = (byte)(n & 255);
            n4 = n3 + 1;
            arrby[n3] = (byte)(n >> 8 & 255);
            n3 = n4 + 1;
            arrby[n4] = (byte)(n >> 16 & 255);
            arrby[n3] = (byte)(arrby[n3] + (byte)(n2 & 255));
        } else {
            n = this.intToSignedBits(n, 12);
            n2 = this.intToSignedBits(n2, 4);
            byte[] arrby = this.mValue;
            n3 = n4 + 1;
            arrby[n4] = (byte)(n & 255);
            arrby[n3] = (byte)(n >> 8 & 15);
            arrby[n3] = (byte)(arrby[n3] + (byte)((n2 & 15) << 4));
        }
        return true;
    }

    public boolean setValue(String string2) {
        this.mValue = string2.getBytes();
        return true;
    }

    public boolean setValue(byte[] arrby) {
        this.mValue = arrby;
        return true;
    }

    public void setWriteType(int n) {
        this.mWriteType = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(new ParcelUuid(this.mUuid), 0);
        parcel.writeInt(this.mInstance);
        parcel.writeInt(this.mProperties);
        parcel.writeInt(this.mPermissions);
        parcel.writeInt(this.mKeySize);
        parcel.writeInt(this.mWriteType);
        parcel.writeTypedList(this.mDescriptors);
    }

}

