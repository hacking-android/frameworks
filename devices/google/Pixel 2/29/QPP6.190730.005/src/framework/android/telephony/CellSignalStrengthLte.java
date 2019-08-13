/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_0.LteSignalStrength;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.telephony.CellSignalStrength;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import java.util.Objects;

public final class CellSignalStrengthLte
extends CellSignalStrength
implements Parcelable {
    public static final Parcelable.Creator<CellSignalStrengthLte> CREATOR;
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellSignalStrengthLte";
    private static final int MAX_LTE_RSRP = -44;
    private static final int MIN_LTE_RSRP = -140;
    private static final int SIGNAL_STRENGTH_LTE_RSSI_ASU_UNKNOWN = 99;
    private static final int SIGNAL_STRENGTH_LTE_RSSI_VALID_ASU_MAX_VALUE = 31;
    private static final int SIGNAL_STRENGTH_LTE_RSSI_VALID_ASU_MIN_VALUE = 0;
    private static final CellSignalStrengthLte sInvalid;
    private static final int sRsrpBoost = 0;
    private static final int[] sThresholds;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mCqi;
    private int mLevel;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mRsrp;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mRsrq;
    private int mRssi;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mRssnr;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mSignalStrength;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mTimingAdvance;

    static {
        sThresholds = new int[]{-115, -105, -95, -85};
        sInvalid = new CellSignalStrengthLte();
        CREATOR = new Parcelable.Creator<CellSignalStrengthLte>(){

            @Override
            public CellSignalStrengthLte createFromParcel(Parcel parcel) {
                return new CellSignalStrengthLte(parcel);
            }

            public CellSignalStrengthLte[] newArray(int n) {
                return new CellSignalStrengthLte[n];
            }
        };
    }

    @UnsupportedAppUsage
    public CellSignalStrengthLte() {
        this.setDefaultValues();
    }

    public CellSignalStrengthLte(int n, int n2, int n3, int n4, int n5, int n6) {
        this.mSignalStrength = this.mRssi = CellSignalStrengthLte.inRangeOrUnavailable(n, -113, -51);
        this.mRsrp = CellSignalStrengthLte.inRangeOrUnavailable(n2, -140, -43);
        this.mRsrq = CellSignalStrengthLte.inRangeOrUnavailable(n3, -20, -3);
        this.mRssnr = CellSignalStrengthLte.inRangeOrUnavailable(n4, -200, 300);
        this.mCqi = CellSignalStrengthLte.inRangeOrUnavailable(n5, 0, 15);
        this.mTimingAdvance = CellSignalStrengthLte.inRangeOrUnavailable(n6, 0, 1282);
        this.updateLevel(null, null);
    }

    public CellSignalStrengthLte(LteSignalStrength lteSignalStrength) {
        int n = CellSignalStrengthLte.convertRssiAsuToDBm(lteSignalStrength.signalStrength);
        int n2 = lteSignalStrength.rsrp != Integer.MAX_VALUE ? -lteSignalStrength.rsrp : lteSignalStrength.rsrp;
        int n3 = lteSignalStrength.rsrq != Integer.MAX_VALUE ? -lteSignalStrength.rsrq : lteSignalStrength.rsrq;
        this(n, n2, n3, lteSignalStrength.rssnr, lteSignalStrength.cqi, lteSignalStrength.timingAdvance);
    }

    private CellSignalStrengthLte(Parcel parcel) {
        this.mSignalStrength = this.mRssi = parcel.readInt();
        this.mRsrp = parcel.readInt();
        this.mRsrq = parcel.readInt();
        this.mRssnr = parcel.readInt();
        this.mCqi = parcel.readInt();
        this.mTimingAdvance = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    public CellSignalStrengthLte(CellSignalStrengthLte cellSignalStrengthLte) {
        this.copyFrom(cellSignalStrengthLte);
    }

    private static int convertRssiAsuToDBm(int n) {
        if (n == 99) {
            return Integer.MAX_VALUE;
        }
        if (n >= 0 && n <= 31) {
            return n * 2 - 113;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("convertRssiAsuToDBm: invalid RSSI in ASU=");
        stringBuilder.append(n);
        Rlog.e(LOG_TAG, stringBuilder.toString());
        return Integer.MAX_VALUE;
    }

    private static void log(String string2) {
        Rlog.w(LOG_TAG, string2);
    }

    @Override
    public CellSignalStrengthLte copy() {
        return new CellSignalStrengthLte(this);
    }

    protected void copyFrom(CellSignalStrengthLte cellSignalStrengthLte) {
        this.mSignalStrength = cellSignalStrengthLte.mSignalStrength;
        this.mRssi = cellSignalStrengthLte.mRssi;
        this.mRsrp = cellSignalStrengthLte.mRsrp;
        this.mRsrq = cellSignalStrengthLte.mRsrq;
        this.mRssnr = cellSignalStrengthLte.mRssnr;
        this.mCqi = cellSignalStrengthLte.mCqi;
        this.mTimingAdvance = cellSignalStrengthLte.mTimingAdvance;
        this.mLevel = cellSignalStrengthLte.mLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CellSignalStrengthLte;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (CellSignalStrengthLte)object;
        bl = bl2;
        if (this.mRssi == ((CellSignalStrengthLte)object).mRssi) {
            bl = bl2;
            if (this.mRsrp == ((CellSignalStrengthLte)object).mRsrp) {
                bl = bl2;
                if (this.mRsrq == ((CellSignalStrengthLte)object).mRsrq) {
                    bl = bl2;
                    if (this.mRssnr == ((CellSignalStrengthLte)object).mRssnr) {
                        bl = bl2;
                        if (this.mCqi == ((CellSignalStrengthLte)object).mCqi) {
                            bl = bl2;
                            if (this.mTimingAdvance == ((CellSignalStrengthLte)object).mTimingAdvance) {
                                bl = bl2;
                                if (this.mLevel == ((CellSignalStrengthLte)object).mLevel) {
                                    bl = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }

    @Override
    public int getAsuLevel() {
        int n = this.mRsrp;
        n = n == Integer.MAX_VALUE ? 99 : (n <= -140 ? 0 : (n >= -43 ? 97 : (n += 140)));
        return n;
    }

    public int getCqi() {
        return this.mCqi;
    }

    @Override
    public int getDbm() {
        return this.mRsrp;
    }

    @Override
    public int getLevel() {
        return this.mLevel;
    }

    public int getRsrp() {
        return this.mRsrp;
    }

    public int getRsrq() {
        return this.mRsrq;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getRssnr() {
        return this.mRssnr;
    }

    public int getTimingAdvance() {
        return this.mTimingAdvance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mRssi, this.mRsrp, this.mRsrq, this.mRssnr, this.mCqi, this.mTimingAdvance, this.mLevel);
    }

    @Override
    public boolean isValid() {
        return this.equals(sInvalid) ^ true;
    }

    @Override
    public void setDefaultValues() {
        this.mSignalStrength = Integer.MAX_VALUE;
        this.mRssi = Integer.MAX_VALUE;
        this.mRsrp = Integer.MAX_VALUE;
        this.mRsrq = Integer.MAX_VALUE;
        this.mRssnr = Integer.MAX_VALUE;
        this.mCqi = Integer.MAX_VALUE;
        this.mTimingAdvance = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CellSignalStrengthLte: rssi=");
        stringBuilder.append(this.mRssi);
        stringBuilder.append(" rsrp=");
        stringBuilder.append(this.mRsrp);
        stringBuilder.append(" rsrq=");
        stringBuilder.append(this.mRsrq);
        stringBuilder.append(" rssnr=");
        stringBuilder.append(this.mRssnr);
        stringBuilder.append(" cqi=");
        stringBuilder.append(this.mCqi);
        stringBuilder.append(" ta=");
        stringBuilder.append(this.mTimingAdvance);
        stringBuilder.append(" level=");
        stringBuilder.append(this.mLevel);
        return stringBuilder.toString();
    }

    @Override
    public void updateLevel(PersistableBundle arrn, ServiceState serviceState) {
        int n;
        boolean bl;
        if (arrn == null) {
            arrn = sThresholds;
            bl = false;
        } else {
            boolean bl2 = arrn.getBoolean("use_only_rsrp_for_lte_signal_bar_bool", false);
            int[] arrn2 = arrn.getIntArray("lte_rsrp_thresholds_int_array");
            arrn = arrn2;
            bl = bl2;
            if (arrn2 == null) {
                arrn = sThresholds;
                bl = bl2;
            }
        }
        int n2 = 0;
        if (serviceState != null) {
            n2 = serviceState.getLteEarfcnRsrpBoost();
        }
        int n3 = -1;
        int n4 = this.mRsrp + n2;
        if (n4 >= -140 && n4 <= -44) {
            n2 = arrn.length;
            do {
                n = n2;
                if (n2 > 0) {
                    n = n2;
                    if (n4 < arrn[n2 - 1]) {
                        --n2;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            n = -1;
        }
        if (bl && n != -1) {
            this.mLevel = n;
            return;
        }
        n4 = this.mRssnr;
        if (n4 > 300) {
            n2 = -1;
        } else if (n4 >= 130) {
            n2 = 4;
        } else if (n4 >= 45) {
            n2 = 3;
        } else if (n4 >= 10) {
            n2 = 2;
        } else if (n4 >= -30) {
            n2 = 1;
        } else {
            n2 = n3;
            if (n4 >= -200) {
                n2 = 0;
            }
        }
        if (n2 != -1 && n != -1) {
            if (n < n2) {
                n2 = n;
            }
            this.mLevel = n2;
            return;
        }
        if (n2 != -1) {
            this.mLevel = n2;
            return;
        }
        if (n != -1) {
            this.mLevel = n;
            return;
        }
        n2 = this.mRssi;
        n2 = n2 > -51 ? 0 : (n2 >= -89 ? 4 : (n2 >= -97 ? 3 : (n2 >= -103 ? 2 : (n2 >= -113 ? 1 : 0))));
        this.mLevel = n2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mRsrp);
        parcel.writeInt(this.mRsrq);
        parcel.writeInt(this.mRssnr);
        parcel.writeInt(this.mCqi);
        parcel.writeInt(this.mTimingAdvance);
        parcel.writeInt(this.mLevel);
    }

}

