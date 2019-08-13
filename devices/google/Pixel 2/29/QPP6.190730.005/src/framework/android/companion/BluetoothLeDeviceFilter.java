/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.companion.BluetoothDeviceFilterUtils;
import android.companion.DeviceFilter;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;
import android.text.TextUtils;
import com.android.internal.util.BitUtils;
import com.android.internal.util.ObjectUtils;
import com.android.internal.util.Preconditions;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public final class BluetoothLeDeviceFilter
implements DeviceFilter<ScanResult> {
    public static final Parcelable.Creator<BluetoothLeDeviceFilter> CREATOR = new Parcelable.Creator<BluetoothLeDeviceFilter>(){

        @Override
        public BluetoothLeDeviceFilter createFromParcel(Parcel object) {
            Builder builder = new Builder().setNamePattern(BluetoothDeviceFilterUtils.patternFromString(((Parcel)object).readString())).setScanFilter((ScanFilter)((Parcel)object).readParcelable(null));
            Object object2 = ((Parcel)object).createByteArray();
            Object object3 = ((Parcel)object).createByteArray();
            if (object2 != null) {
                builder.setRawDataFilter((byte[])object2, (byte[])object3);
            }
            object3 = ((Parcel)object).readString();
            object2 = ((Parcel)object).readString();
            int n = ((Parcel)object).readInt();
            int n2 = ((Parcel)object).readInt();
            int n3 = ((Parcel)object).readInt();
            int n4 = ((Parcel)object).readInt();
            boolean bl = ((Parcel)object).readBoolean();
            if (object3 != null) {
                if (n >= 0) {
                    object = bl ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
                    builder.setRenameFromBytes((String)object3, (String)object2, n, n2, (ByteOrder)object);
                } else {
                    builder.setRenameFromName((String)object3, (String)object2, n3, n4);
                }
            }
            return builder.build();
        }

        public BluetoothLeDeviceFilter[] newArray(int n) {
            return new BluetoothLeDeviceFilter[n];
        }
    };
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "BluetoothLeDeviceFilter";
    private static final int RENAME_PREFIX_LENGTH_LIMIT = 10;
    private final Pattern mNamePattern;
    private final byte[] mRawDataFilter;
    private final byte[] mRawDataFilterMask;
    private final int mRenameBytesFrom;
    private final int mRenameBytesLength;
    private final boolean mRenameBytesReverseOrder;
    private final int mRenameNameFrom;
    private final int mRenameNameLength;
    private final String mRenamePrefix;
    private final String mRenameSuffix;
    private final ScanFilter mScanFilter;

    private BluetoothLeDeviceFilter(Pattern pattern, ScanFilter scanFilter, byte[] arrby, byte[] arrby2, String string2, String string3, int n, int n2, int n3, int n4, boolean bl) {
        this.mNamePattern = pattern;
        this.mScanFilter = ObjectUtils.firstNotNull(scanFilter, ScanFilter.EMPTY);
        this.mRawDataFilter = arrby;
        this.mRawDataFilterMask = arrby2;
        this.mRenamePrefix = string2;
        this.mRenameSuffix = string3;
        this.mRenameBytesFrom = n;
        this.mRenameBytesLength = n2;
        this.mRenameNameFrom = n3;
        this.mRenameNameLength = n4;
        this.mRenameBytesReverseOrder = bl;
    }

    public static int getRenamePrefixLengthLimit() {
        return 10;
    }

    @Override
    private boolean matches(BluetoothDevice bluetoothDevice) {
        boolean bl = BluetoothDeviceFilterUtils.matches(this.getScanFilter(), bluetoothDevice) && BluetoothDeviceFilterUtils.matchesName(this.getNamePattern(), bluetoothDevice);
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (BluetoothLeDeviceFilter)object;
            if (!(this.mRenameBytesFrom == ((BluetoothLeDeviceFilter)object).mRenameBytesFrom && this.mRenameBytesLength == ((BluetoothLeDeviceFilter)object).mRenameBytesLength && this.mRenameNameFrom == ((BluetoothLeDeviceFilter)object).mRenameNameFrom && this.mRenameNameLength == ((BluetoothLeDeviceFilter)object).mRenameNameLength && this.mRenameBytesReverseOrder == ((BluetoothLeDeviceFilter)object).mRenameBytesReverseOrder && Objects.equals(this.mNamePattern, ((BluetoothLeDeviceFilter)object).mNamePattern) && Objects.equals(this.mScanFilter, ((BluetoothLeDeviceFilter)object).mScanFilter) && Arrays.equals(this.mRawDataFilter, ((BluetoothLeDeviceFilter)object).mRawDataFilter) && Arrays.equals(this.mRawDataFilterMask, ((BluetoothLeDeviceFilter)object).mRawDataFilterMask) && Objects.equals(this.mRenamePrefix, ((BluetoothLeDeviceFilter)object).mRenamePrefix) && Objects.equals(this.mRenameSuffix, ((BluetoothLeDeviceFilter)object).mRenameSuffix))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public String getDeviceDisplayName(ScanResult object) {
        if (this.mRenameBytesFrom < 0 && this.mRenameNameFrom < 0) {
            return BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(((ScanResult)object).getDevice());
        }
        StringBuilder stringBuilder = new StringBuilder(TextUtils.emptyIfNull(this.mRenamePrefix));
        if (this.mRenameBytesFrom >= 0) {
            object = ((ScanResult)object).getScanRecord().getBytes();
            int n = this.mRenameBytesFrom;
            int n2 = this.mRenameBytesFrom + this.mRenameBytesLength - 1;
            int n3 = this.mRenameBytesReverseOrder ? -1 : 1;
            for (int i = this.mRenameBytesReverseOrder != false ? n2 : n; n <= i && i <= n2; i += n3) {
                stringBuilder.append(Byte.toHexString((byte)object[i], (boolean)true));
            }
        } else {
            object = BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(((ScanResult)object).getDevice());
            int n = this.mRenameNameFrom;
            stringBuilder.append(((String)object).substring(n, this.mRenameNameLength + n));
        }
        stringBuilder.append(TextUtils.emptyIfNull(this.mRenameSuffix));
        return stringBuilder.toString();
    }

    @Override
    public int getMediumType() {
        return 1;
    }

    public Pattern getNamePattern() {
        return this.mNamePattern;
    }

    public byte[] getRawDataFilter() {
        return this.mRawDataFilter;
    }

    public byte[] getRawDataFilterMask() {
        return this.mRawDataFilterMask;
    }

    public int getRenameBytesFrom() {
        return this.mRenameBytesFrom;
    }

    public int getRenameBytesLength() {
        return this.mRenameBytesLength;
    }

    public String getRenamePrefix() {
        return this.mRenamePrefix;
    }

    public String getRenameSuffix() {
        return this.mRenameSuffix;
    }

    @UnsupportedAppUsage
    public ScanFilter getScanFilter() {
        return this.mScanFilter;
    }

    public int hashCode() {
        return Objects.hash(this.mNamePattern, this.mScanFilter, this.mRawDataFilter, this.mRawDataFilterMask, this.mRenamePrefix, this.mRenameSuffix, this.mRenameBytesFrom, this.mRenameBytesLength, this.mRenameNameFrom, this.mRenameNameLength, this.mRenameBytesReverseOrder);
    }

    public boolean isRenameBytesReverseOrder() {
        return this.mRenameBytesReverseOrder;
    }

    @Override
    public boolean matches(ScanResult scanResult) {
        boolean bl = this.matches(scanResult.getDevice()) && (this.mRawDataFilter == null || BitUtils.maskedEquals(scanResult.getScanRecord().getBytes(), this.mRawDataFilter, this.mRawDataFilterMask));
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BluetoothLEDeviceFilter{mNamePattern=");
        stringBuilder.append(this.mNamePattern);
        stringBuilder.append(", mScanFilter=");
        stringBuilder.append(this.mScanFilter);
        stringBuilder.append(", mRawDataFilter=");
        stringBuilder.append(Arrays.toString(this.mRawDataFilter));
        stringBuilder.append(", mRawDataFilterMask=");
        stringBuilder.append(Arrays.toString(this.mRawDataFilterMask));
        stringBuilder.append(", mRenamePrefix='");
        stringBuilder.append(this.mRenamePrefix);
        stringBuilder.append('\'');
        stringBuilder.append(", mRenameSuffix='");
        stringBuilder.append(this.mRenameSuffix);
        stringBuilder.append('\'');
        stringBuilder.append(", mRenameBytesFrom=");
        stringBuilder.append(this.mRenameBytesFrom);
        stringBuilder.append(", mRenameBytesLength=");
        stringBuilder.append(this.mRenameBytesLength);
        stringBuilder.append(", mRenameNameFrom=");
        stringBuilder.append(this.mRenameNameFrom);
        stringBuilder.append(", mRenameNameLength=");
        stringBuilder.append(this.mRenameNameLength);
        stringBuilder.append(", mRenameBytesReverseOrder=");
        stringBuilder.append(this.mRenameBytesReverseOrder);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(BluetoothDeviceFilterUtils.patternToString(this.getNamePattern()));
        parcel.writeParcelable(this.mScanFilter, n);
        parcel.writeByteArray(this.mRawDataFilter);
        parcel.writeByteArray(this.mRawDataFilterMask);
        parcel.writeString(this.mRenamePrefix);
        parcel.writeString(this.mRenameSuffix);
        parcel.writeInt(this.mRenameBytesFrom);
        parcel.writeInt(this.mRenameBytesLength);
        parcel.writeInt(this.mRenameNameFrom);
        parcel.writeInt(this.mRenameNameLength);
        parcel.writeBoolean(this.mRenameBytesReverseOrder);
    }

    public static final class Builder
    extends OneTimeUseBuilder<BluetoothLeDeviceFilter> {
        private Pattern mNamePattern;
        private byte[] mRawDataFilter;
        private byte[] mRawDataFilterMask;
        private int mRenameBytesFrom = -1;
        private int mRenameBytesLength;
        private boolean mRenameBytesReverseOrder = false;
        private int mRenameNameFrom = -1;
        private int mRenameNameLength;
        private String mRenamePrefix;
        private String mRenameSuffix;
        private ScanFilter mScanFilter;

        private void checkRangeNotEmpty(int n) {
            boolean bl = n > 0;
            Preconditions.checkArgument(bl, "Range must be non-empty");
        }

        private void checkRenameNotSet() {
            boolean bl = this.mRenamePrefix == null;
            Preconditions.checkState(bl, "Renaming rule can only be set once");
        }

        private Builder setRename(String string2, String string3) {
            this.checkNotUsed();
            boolean bl = TextUtils.length(string2) <= BluetoothLeDeviceFilter.getRenamePrefixLengthLimit();
            Preconditions.checkArgument(bl, "Prefix is too long");
            this.mRenamePrefix = string2;
            this.mRenameSuffix = string3;
            return this;
        }

        @Override
        public BluetoothLeDeviceFilter build() {
            this.markUsed();
            return new BluetoothLeDeviceFilter(this.mNamePattern, this.mScanFilter, this.mRawDataFilter, this.mRawDataFilterMask, this.mRenamePrefix, this.mRenameSuffix, this.mRenameBytesFrom, this.mRenameBytesLength, this.mRenameNameFrom, this.mRenameNameLength, this.mRenameBytesReverseOrder);
        }

        public Builder setNamePattern(Pattern pattern) {
            this.checkNotUsed();
            this.mNamePattern = pattern;
            return this;
        }

        public Builder setRawDataFilter(byte[] arrby, byte[] arrby2) {
            this.checkNotUsed();
            Preconditions.checkNotNull(arrby);
            boolean bl = arrby2 == null || arrby.length == arrby2.length;
            Preconditions.checkArgument(bl, "Mask and filter should be the same length");
            this.mRawDataFilter = arrby;
            this.mRawDataFilterMask = arrby2;
            return this;
        }

        public Builder setRenameFromBytes(String string2, String string3, int n, int n2, ByteOrder byteOrder) {
            this.checkRenameNotSet();
            this.checkRangeNotEmpty(n2);
            this.mRenameBytesFrom = n;
            this.mRenameBytesLength = n2;
            boolean bl = byteOrder == ByteOrder.LITTLE_ENDIAN;
            this.mRenameBytesReverseOrder = bl;
            return this.setRename(string2, string3);
        }

        public Builder setRenameFromName(String string2, String string3, int n, int n2) {
            this.checkRenameNotSet();
            this.checkRangeNotEmpty(n2);
            this.mRenameNameFrom = n;
            this.mRenameNameLength = n2;
            this.mRenameBytesReverseOrder = false;
            return this.setRename(string2, string3);
        }

        public Builder setScanFilter(ScanFilter scanFilter) {
            this.checkNotUsed();
            this.mScanFilter = scanFilter;
            return this;
        }
    }

}

