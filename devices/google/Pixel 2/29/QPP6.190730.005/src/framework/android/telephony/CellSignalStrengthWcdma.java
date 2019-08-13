/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_0.WcdmaSignalStrength;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.telephony.CellSignalStrength;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class CellSignalStrengthWcdma
extends CellSignalStrength
implements Parcelable {
    public static final Parcelable.Creator<CellSignalStrengthWcdma> CREATOR;
    private static final boolean DBG = false;
    private static final String DEFAULT_LEVEL_CALCULATION_METHOD = "rssi";
    public static final String LEVEL_CALCULATION_METHOD_RSCP = "rscp";
    public static final String LEVEL_CALCULATION_METHOD_RSSI = "rssi";
    private static final String LOG_TAG = "CellSignalStrengthWcdma";
    private static final int WCDMA_RSCP_GOOD = -95;
    private static final int WCDMA_RSCP_GREAT = -85;
    private static final int WCDMA_RSCP_MAX = -24;
    private static final int WCDMA_RSCP_MIN = -120;
    private static final int WCDMA_RSCP_MODERATE = -105;
    private static final int WCDMA_RSCP_POOR = -115;
    private static final int WCDMA_RSSI_GOOD = -87;
    private static final int WCDMA_RSSI_GREAT = -77;
    private static final int WCDMA_RSSI_MAX = -51;
    private static final int WCDMA_RSSI_MIN = -113;
    private static final int WCDMA_RSSI_MODERATE = -97;
    private static final int WCDMA_RSSI_POOR = -107;
    private static final CellSignalStrengthWcdma sInvalid;
    private static final int[] sRscpThresholds;
    private static final int[] sRssiThresholds;
    @UnsupportedAppUsage
    private int mBitErrorRate;
    private int mEcNo;
    private int mLevel;
    private int mRscp;
    private int mRssi;

    static {
        sRssiThresholds = new int[]{-107, -97, -87, -77};
        sRscpThresholds = new int[]{-115, -105, -95, -85};
        sInvalid = new CellSignalStrengthWcdma();
        CREATOR = new Parcelable.Creator<CellSignalStrengthWcdma>(){

            @Override
            public CellSignalStrengthWcdma createFromParcel(Parcel parcel) {
                return new CellSignalStrengthWcdma(parcel);
            }

            public CellSignalStrengthWcdma[] newArray(int n) {
                return new CellSignalStrengthWcdma[n];
            }
        };
    }

    public CellSignalStrengthWcdma() {
        this.setDefaultValues();
    }

    public CellSignalStrengthWcdma(int n, int n2, int n3, int n4) {
        this.mRssi = CellSignalStrengthWcdma.inRangeOrUnavailable(n, -113, -51);
        this.mBitErrorRate = CellSignalStrengthWcdma.inRangeOrUnavailable(n2, 0, 7, 99);
        this.mRscp = CellSignalStrengthWcdma.inRangeOrUnavailable(n3, -120, -24);
        this.mEcNo = CellSignalStrengthWcdma.inRangeOrUnavailable(n4, -24, 1);
        this.updateLevel(null, null);
    }

    public CellSignalStrengthWcdma(WcdmaSignalStrength wcdmaSignalStrength) {
        this(CellSignalStrengthWcdma.getRssiDbmFromAsu(wcdmaSignalStrength.signalStrength), wcdmaSignalStrength.bitErrorRate, Integer.MAX_VALUE, Integer.MAX_VALUE);
        if (this.mRssi == Integer.MAX_VALUE && this.mRscp == Integer.MAX_VALUE) {
            this.setDefaultValues();
        }
    }

    public CellSignalStrengthWcdma(android.hardware.radio.V1_2.WcdmaSignalStrength wcdmaSignalStrength) {
        this(CellSignalStrengthWcdma.getRssiDbmFromAsu(wcdmaSignalStrength.base.signalStrength), wcdmaSignalStrength.base.bitErrorRate, CellSignalStrengthWcdma.getRscpDbmFromAsu(wcdmaSignalStrength.rscp), CellSignalStrengthWcdma.getEcNoDbFromAsu(wcdmaSignalStrength.ecno));
        if (this.mRssi == Integer.MAX_VALUE && this.mRscp == Integer.MAX_VALUE) {
            this.setDefaultValues();
        }
    }

    private CellSignalStrengthWcdma(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mRscp = parcel.readInt();
        this.mEcNo = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    public CellSignalStrengthWcdma(CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        this.copyFrom(cellSignalStrengthWcdma);
    }

    private static void log(String string2) {
        Rlog.w(LOG_TAG, string2);
    }

    private static void loge(String string2) {
        Rlog.e(LOG_TAG, string2);
    }

    @Override
    public CellSignalStrengthWcdma copy() {
        return new CellSignalStrengthWcdma(this);
    }

    protected void copyFrom(CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        this.mRssi = cellSignalStrengthWcdma.mRssi;
        this.mBitErrorRate = cellSignalStrengthWcdma.mBitErrorRate;
        this.mRscp = cellSignalStrengthWcdma.mRscp;
        this.mEcNo = cellSignalStrengthWcdma.mEcNo;
        this.mLevel = cellSignalStrengthWcdma.mLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CellSignalStrengthWcdma;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (CellSignalStrengthWcdma)object;
        bl = bl2;
        if (this.mRssi == ((CellSignalStrengthWcdma)object).mRssi) {
            bl = bl2;
            if (this.mBitErrorRate == ((CellSignalStrengthWcdma)object).mBitErrorRate) {
                bl = bl2;
                if (this.mRscp == ((CellSignalStrengthWcdma)object).mRscp) {
                    bl = bl2;
                    if (this.mEcNo == ((CellSignalStrengthWcdma)object).mEcNo) {
                        bl = bl2;
                        if (this.mLevel == ((CellSignalStrengthWcdma)object).mLevel) {
                            bl = true;
                        }
                    }
                }
            }
        }
        return bl;
    }

    @Override
    public int getAsuLevel() {
        int n = this.mRscp;
        if (n != Integer.MAX_VALUE) {
            return CellSignalStrengthWcdma.getAsuFromRscpDbm(n);
        }
        n = this.mRssi;
        if (n != Integer.MAX_VALUE) {
            return CellSignalStrengthWcdma.getAsuFromRssiDbm(n);
        }
        return CellSignalStrengthWcdma.getAsuFromRscpDbm(Integer.MAX_VALUE);
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override
    public int getDbm() {
        int n = this.mRscp;
        if (n != Integer.MAX_VALUE) {
            return n;
        }
        return this.mRssi;
    }

    public int getEcNo() {
        return this.mEcNo;
    }

    @Override
    public int getLevel() {
        return this.mLevel;
    }

    public int getRscp() {
        return this.mRscp;
    }

    public int getRssi() {
        return this.mRssi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mRssi, this.mBitErrorRate, this.mRscp, this.mEcNo, this.mLevel);
    }

    @Override
    public boolean isValid() {
        return this.equals(sInvalid) ^ true;
    }

    @Override
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mRscp = Integer.MAX_VALUE;
        this.mEcNo = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CellSignalStrengthWcdma: ss=");
        stringBuilder.append(this.mRssi);
        stringBuilder.append(" ber=");
        stringBuilder.append(this.mBitErrorRate);
        stringBuilder.append(" rscp=");
        stringBuilder.append(this.mRscp);
        stringBuilder.append(" ecno=");
        stringBuilder.append(this.mEcNo);
        stringBuilder.append(" level=");
        stringBuilder.append(this.mLevel);
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void updateLevel(PersistableBundle var1_1, ServiceState var2_2) {
        block17 : {
            block16 : {
                block14 : {
                    block15 : {
                        block13 : {
                            if (var1_1 != null) break block13;
                            var3_3 = "rssi";
                            var1_1 = CellSignalStrengthWcdma.sRscpThresholds;
                            break block14;
                        }
                        var2_2 = var3_3 = var1_1.getString("wcdma_default_signal_strength_measurement_string", "rssi");
                        if (TextUtils.isEmpty((CharSequence)var3_3)) {
                            var2_2 = "rssi";
                        }
                        if ((var4_4 = var1_1.getIntArray("wcdma_rscp_thresholds_int_array")) == null) break block15;
                        var3_3 = var2_2;
                        var1_1 = var4_4;
                        if (var4_4.length == 4) break block14;
                    }
                    var1_1 = CellSignalStrengthWcdma.sRscpThresholds;
                    var3_3 = var2_2;
                }
                var5_5 = 4;
                var6_6 = var3_3.hashCode();
                if (var6_6 == 3509870) break block16;
                if (var6_6 != 3510359 || !var3_3.equals("rssi")) ** GOTO lbl-1000
                var6_6 = 2;
                break block17;
            }
            if (var3_3.equals("rscp")) {
                var6_6 = 0;
            } else lbl-1000: // 2 sources:
            {
                var6_6 = -1;
            }
        }
        if (var6_6 != 0) {
            if (var6_6 != 2) {
                var1_1 = new StringBuilder();
                var1_1.append("Invalid Level Calculation Method for CellSignalStrengthWcdma = ");
                var1_1.append((String)var3_3);
                CellSignalStrengthWcdma.loge(var1_1.toString());
            }
            if ((var6_6 = this.mRssi) >= -113 && var6_6 <= -51) {
                while (var5_5 > 0 && this.mRssi < CellSignalStrengthWcdma.sRssiThresholds[var5_5 - 1]) {
                    --var5_5;
                }
                this.mLevel = var5_5;
                return;
            }
            this.mLevel = 0;
            return;
        }
        var6_6 = this.mRscp;
        if (var6_6 >= -120 && var6_6 <= -24) {
            while (var5_5 > 0 && this.mRscp < var1_1[var5_5 - 1]) {
                --var5_5;
            }
            this.mLevel = var5_5;
            return;
        }
        this.mLevel = 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mRscp);
        parcel.writeInt(this.mEcNo);
        parcel.writeInt(this.mLevel);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LevelCalculationMethod {
    }

}

