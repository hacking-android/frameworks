/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothUuid;
import android.bluetooth.le.BluetoothLeUtils;
import android.os.ParcelUuid;
import android.util.SparseArray;
import java.util.List;
import java.util.Map;

public final class ScanRecord {
    private static final int DATA_TYPE_FLAGS = 1;
    private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 9;
    private static final int DATA_TYPE_LOCAL_NAME_SHORT = 8;
    private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 255;
    private static final int DATA_TYPE_SERVICE_DATA_128_BIT = 33;
    private static final int DATA_TYPE_SERVICE_DATA_16_BIT = 22;
    private static final int DATA_TYPE_SERVICE_DATA_32_BIT = 32;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_128_BIT = 21;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_16_BIT = 20;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_32_BIT = 31;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 7;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 6;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 3;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 2;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 5;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 4;
    private static final int DATA_TYPE_TX_POWER_LEVEL = 10;
    private static final String TAG = "ScanRecord";
    private final int mAdvertiseFlags;
    private final byte[] mBytes;
    private final String mDeviceName;
    private final SparseArray<byte[]> mManufacturerSpecificData;
    private final Map<ParcelUuid, byte[]> mServiceData;
    private final List<ParcelUuid> mServiceSolicitationUuids;
    private final List<ParcelUuid> mServiceUuids;
    private final int mTxPowerLevel;

    private ScanRecord(List<ParcelUuid> list, List<ParcelUuid> list2, SparseArray<byte[]> sparseArray, Map<ParcelUuid, byte[]> map, int n, int n2, String string2, byte[] arrby) {
        this.mServiceSolicitationUuids = list2;
        this.mServiceUuids = list;
        this.mManufacturerSpecificData = sparseArray;
        this.mServiceData = map;
        this.mDeviceName = string2;
        this.mAdvertiseFlags = n;
        this.mTxPowerLevel = n2;
        this.mBytes = arrby;
    }

    private static byte[] extractBytes(byte[] arrby, int n, int n2) {
        byte[] arrby2 = new byte[n2];
        System.arraycopy(arrby, n, arrby2, 0, n2);
        return arrby2;
    }

    /*
     * Exception decompiling
     */
    @UnsupportedAppUsage
    public static ScanRecord parseFromBytes(byte[] var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [21[CASE]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static int parseServiceSolicitationUuid(byte[] arrby, int n, int n2, int n3, List<ParcelUuid> list) {
        while (n2 > 0) {
            list.add(BluetoothUuid.parseUuidFrom(ScanRecord.extractBytes(arrby, n, n3)));
            n2 -= n3;
            n += n3;
        }
        return n;
    }

    private static int parseServiceUuid(byte[] arrby, int n, int n2, int n3, List<ParcelUuid> list) {
        while (n2 > 0) {
            list.add(BluetoothUuid.parseUuidFrom(ScanRecord.extractBytes(arrby, n, n3)));
            n2 -= n3;
            n += n3;
        }
        return n;
    }

    public int getAdvertiseFlags() {
        return this.mAdvertiseFlags;
    }

    public byte[] getBytes() {
        return this.mBytes;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public SparseArray<byte[]> getManufacturerSpecificData() {
        return this.mManufacturerSpecificData;
    }

    public byte[] getManufacturerSpecificData(int n) {
        SparseArray<byte[]> sparseArray = this.mManufacturerSpecificData;
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(n);
    }

    public Map<ParcelUuid, byte[]> getServiceData() {
        return this.mServiceData;
    }

    public byte[] getServiceData(ParcelUuid parcelUuid) {
        Map<ParcelUuid, byte[]> map;
        if (parcelUuid != null && (map = this.mServiceData) != null) {
            return map.get(parcelUuid);
        }
        return null;
    }

    public List<ParcelUuid> getServiceSolicitationUuids() {
        return this.mServiceSolicitationUuids;
    }

    public List<ParcelUuid> getServiceUuids() {
        return this.mServiceUuids;
    }

    public int getTxPowerLevel() {
        return this.mTxPowerLevel;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScanRecord [mAdvertiseFlags=");
        stringBuilder.append(this.mAdvertiseFlags);
        stringBuilder.append(", mServiceUuids=");
        stringBuilder.append(this.mServiceUuids);
        stringBuilder.append(", mServiceSolicitationUuids=");
        stringBuilder.append(this.mServiceSolicitationUuids);
        stringBuilder.append(", mManufacturerSpecificData=");
        stringBuilder.append(BluetoothLeUtils.toString(this.mManufacturerSpecificData));
        stringBuilder.append(", mServiceData=");
        stringBuilder.append(BluetoothLeUtils.toString(this.mServiceData));
        stringBuilder.append(", mTxPowerLevel=");
        stringBuilder.append(this.mTxPowerLevel);
        stringBuilder.append(", mDeviceName=");
        stringBuilder.append(this.mDeviceName);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

