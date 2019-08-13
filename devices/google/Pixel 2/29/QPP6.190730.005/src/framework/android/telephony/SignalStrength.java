/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.hardware.radio.V1_0.CdmaSignalStrength;
import android.hardware.radio.V1_0.EvdoSignalStrength;
import android.hardware.radio.V1_0.GsmSignalStrength;
import android.hardware.radio.V1_0.LteSignalStrength;
import android.hardware.radio.V1_0.TdScdmaSignalStrength;
import android.hardware.radio.V1_2.TdscdmaSignalStrength;
import android.hardware.radio.V1_2.WcdmaSignalStrength;
import android.hardware.radio.V1_4.NrSignalStrength;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.CellSignalStrengthTdscdma;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SignalStrength
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<SignalStrength> CREATOR;
    private static final boolean DBG = false;
    public static final int INVALID = Integer.MAX_VALUE;
    private static final String LOG_TAG = "SignalStrength";
    private static final int LTE_RSRP_THRESHOLDS_NUM = 4;
    private static final String MEASUREMENT_TYPE_RSCP = "rscp";
    @UnsupportedAppUsage
    public static final int NUM_SIGNAL_STRENGTH_BINS = 5;
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static final int SIGNAL_STRENGTH_GOOD = 3;
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static final int SIGNAL_STRENGTH_GREAT = 4;
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static final int SIGNAL_STRENGTH_MODERATE = 2;
    public static final String[] SIGNAL_STRENGTH_NAMES;
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static final int SIGNAL_STRENGTH_POOR = 1;
    private static final int WCDMA_RSCP_THRESHOLDS_NUM = 4;
    CellSignalStrengthCdma mCdma;
    CellSignalStrengthGsm mGsm;
    CellSignalStrengthLte mLte;
    CellSignalStrengthNr mNr;
    CellSignalStrengthTdscdma mTdscdma;
    CellSignalStrengthWcdma mWcdma;

    static {
        SIGNAL_STRENGTH_NAMES = new String[]{"none", "poor", "moderate", "good", "great"};
        CREATOR = new Parcelable.Creator(){

            public SignalStrength createFromParcel(Parcel parcel) {
                return new SignalStrength(parcel);
            }

            public SignalStrength[] newArray(int n) {
                return new SignalStrength[n];
            }
        };
    }

    @UnsupportedAppUsage
    public SignalStrength() {
        this(new CellSignalStrengthCdma(), new CellSignalStrengthGsm(), new CellSignalStrengthWcdma(), new CellSignalStrengthTdscdma(), new CellSignalStrengthLte(), new CellSignalStrengthNr());
    }

    public SignalStrength(android.hardware.radio.V1_0.SignalStrength signalStrength) {
        this(new CellSignalStrengthCdma(signalStrength.cdma, signalStrength.evdo), new CellSignalStrengthGsm(signalStrength.gw), new CellSignalStrengthWcdma(), new CellSignalStrengthTdscdma(signalStrength.tdScdma), new CellSignalStrengthLte(signalStrength.lte), new CellSignalStrengthNr());
    }

    public SignalStrength(android.hardware.radio.V1_2.SignalStrength signalStrength) {
        this(new CellSignalStrengthCdma(signalStrength.cdma, signalStrength.evdo), new CellSignalStrengthGsm(signalStrength.gsm), new CellSignalStrengthWcdma(signalStrength.wcdma), new CellSignalStrengthTdscdma(signalStrength.tdScdma), new CellSignalStrengthLte(signalStrength.lte), new CellSignalStrengthNr());
    }

    public SignalStrength(android.hardware.radio.V1_4.SignalStrength signalStrength) {
        this(new CellSignalStrengthCdma(signalStrength.cdma, signalStrength.evdo), new CellSignalStrengthGsm(signalStrength.gsm), new CellSignalStrengthWcdma(signalStrength.wcdma), new CellSignalStrengthTdscdma(signalStrength.tdscdma), new CellSignalStrengthLte(signalStrength.lte), new CellSignalStrengthNr(signalStrength.nr));
    }

    @UnsupportedAppUsage
    public SignalStrength(Parcel parcel) {
        this.mCdma = (CellSignalStrengthCdma)parcel.readParcelable(CellSignalStrengthCdma.class.getClassLoader());
        this.mGsm = (CellSignalStrengthGsm)parcel.readParcelable(CellSignalStrengthGsm.class.getClassLoader());
        this.mWcdma = (CellSignalStrengthWcdma)parcel.readParcelable(CellSignalStrengthWcdma.class.getClassLoader());
        this.mTdscdma = (CellSignalStrengthTdscdma)parcel.readParcelable(CellSignalStrengthTdscdma.class.getClassLoader());
        this.mLte = (CellSignalStrengthLte)parcel.readParcelable(CellSignalStrengthLte.class.getClassLoader());
        this.mNr = (CellSignalStrengthNr)parcel.readParcelable(CellSignalStrengthLte.class.getClassLoader());
    }

    public SignalStrength(CellSignalStrengthCdma cellSignalStrengthCdma, CellSignalStrengthGsm cellSignalStrengthGsm, CellSignalStrengthWcdma cellSignalStrengthWcdma, CellSignalStrengthTdscdma cellSignalStrengthTdscdma, CellSignalStrengthLte cellSignalStrengthLte, CellSignalStrengthNr cellSignalStrengthNr) {
        this.mCdma = cellSignalStrengthCdma;
        this.mGsm = cellSignalStrengthGsm;
        this.mWcdma = cellSignalStrengthWcdma;
        this.mTdscdma = cellSignalStrengthTdscdma;
        this.mLte = cellSignalStrengthLte;
        this.mNr = cellSignalStrengthNr;
    }

    @UnsupportedAppUsage
    public SignalStrength(SignalStrength signalStrength) {
        this.copyFrom(signalStrength);
    }

    private CellSignalStrength getPrimary() {
        if (this.mLte.isValid()) {
            return this.mLte;
        }
        if (this.mCdma.isValid()) {
            return this.mCdma;
        }
        if (this.mTdscdma.isValid()) {
            return this.mTdscdma;
        }
        if (this.mWcdma.isValid()) {
            return this.mWcdma;
        }
        if (this.mGsm.isValid()) {
            return this.mGsm;
        }
        if (this.mNr.isValid()) {
            return this.mNr;
        }
        return this.mLte;
    }

    private static void log(String string2) {
        Rlog.w(LOG_TAG, string2);
    }

    private static void loge(String string2) {
        Rlog.e(LOG_TAG, string2);
    }

    @UnsupportedAppUsage
    public static SignalStrength newFromBundle(Bundle bundle) {
        SignalStrength signalStrength = new SignalStrength();
        signalStrength.setFromNotifierBundle(bundle);
        return signalStrength;
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    private void setFromNotifierBundle(Bundle bundle) {
        this.mCdma = (CellSignalStrengthCdma)bundle.getParcelable("Cdma");
        this.mGsm = (CellSignalStrengthGsm)bundle.getParcelable("Gsm");
        this.mWcdma = (CellSignalStrengthWcdma)bundle.getParcelable("Wcdma");
        this.mTdscdma = (CellSignalStrengthTdscdma)bundle.getParcelable("Tdscdma");
        this.mLte = (CellSignalStrengthLte)bundle.getParcelable("Lte");
        this.mNr = (CellSignalStrengthNr)bundle.getParcelable("Nr");
    }

    @UnsupportedAppUsage
    protected void copyFrom(SignalStrength signalStrength) {
        this.mCdma = new CellSignalStrengthCdma(signalStrength.mCdma);
        this.mGsm = new CellSignalStrengthGsm(signalStrength.mGsm);
        this.mWcdma = new CellSignalStrengthWcdma(signalStrength.mWcdma);
        this.mTdscdma = new CellSignalStrengthTdscdma(signalStrength.mTdscdma);
        this.mLte = new CellSignalStrengthLte(signalStrength.mLte);
        this.mNr = new CellSignalStrengthNr(signalStrength.mNr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof SignalStrength;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (SignalStrength)object;
            if (!this.mCdma.equals(((SignalStrength)object).mCdma) || !this.mGsm.equals(((SignalStrength)object).mGsm) || !this.mWcdma.equals(((SignalStrength)object).mWcdma) || !this.mTdscdma.equals(((SignalStrength)object).mTdscdma) || !this.mLte.equals(((SignalStrength)object).mLte) || !this.mNr.equals(((SignalStrength)object).mNr)) break block1;
            bl = true;
        }
        return bl;
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public void fillInNotifierBundle(Bundle bundle) {
        bundle.putParcelable("Cdma", this.mCdma);
        bundle.putParcelable("Gsm", this.mGsm);
        bundle.putParcelable("Wcdma", this.mWcdma);
        bundle.putParcelable("Tdscdma", this.mTdscdma);
        bundle.putParcelable("Lte", this.mLte);
        bundle.putParcelable("Nr", this.mNr);
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getAsuLevel() {
        return this.getPrimary().getAsuLevel();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getCdmaAsuLevel() {
        return this.mCdma.getAsuLevel();
    }

    @Deprecated
    public int getCdmaDbm() {
        return this.mCdma.getCdmaDbm();
    }

    @Deprecated
    public int getCdmaEcio() {
        return this.mCdma.getCdmaEcio();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getCdmaLevel() {
        return this.mCdma.getLevel();
    }

    public List<CellSignalStrength> getCellSignalStrengths() {
        return this.getCellSignalStrengths(CellSignalStrength.class);
    }

    public <T extends CellSignalStrength> List<T> getCellSignalStrengths(Class<T> class_) {
        ArrayList<CellSignalStrength> arrayList = new ArrayList<CellSignalStrength>(2);
        if (this.mLte.isValid() && class_.isAssignableFrom(CellSignalStrengthLte.class)) {
            arrayList.add(this.mLte);
        }
        if (this.mCdma.isValid() && class_.isAssignableFrom(CellSignalStrengthCdma.class)) {
            arrayList.add(this.mCdma);
        }
        if (this.mTdscdma.isValid() && class_.isAssignableFrom(CellSignalStrengthTdscdma.class)) {
            arrayList.add(this.mTdscdma);
        }
        if (this.mWcdma.isValid() && class_.isAssignableFrom(CellSignalStrengthWcdma.class)) {
            arrayList.add(this.mWcdma);
        }
        if (this.mGsm.isValid() && class_.isAssignableFrom(CellSignalStrengthGsm.class)) {
            arrayList.add(this.mGsm);
        }
        if (this.mNr.isValid() && class_.isAssignableFrom(CellSignalStrengthNr.class)) {
            arrayList.add(this.mNr);
        }
        return arrayList;
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getDbm() {
        return this.getPrimary().getDbm();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getEvdoAsuLevel() {
        return this.mCdma.getEvdoAsuLevel();
    }

    @Deprecated
    public int getEvdoDbm() {
        return this.mCdma.getEvdoDbm();
    }

    @Deprecated
    public int getEvdoEcio() {
        return this.mCdma.getEvdoEcio();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getEvdoLevel() {
        return this.mCdma.getEvdoLevel();
    }

    @Deprecated
    public int getEvdoSnr() {
        return this.mCdma.getEvdoSnr();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getGsmAsuLevel() {
        return this.mGsm.getAsuLevel();
    }

    @Deprecated
    public int getGsmBitErrorRate() {
        return this.mGsm.getBitErrorRate();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getGsmDbm() {
        return this.mGsm.getDbm();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getGsmLevel() {
        return this.mGsm.getLevel();
    }

    @Deprecated
    public int getGsmSignalStrength() {
        return this.mGsm.getAsuLevel();
    }

    public int getLevel() {
        int n = this.getPrimary().getLevel();
        if (n >= 0 && n <= 4) {
            return this.getPrimary().getLevel();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Level ");
        stringBuilder.append(n);
        stringBuilder.append(", this=");
        stringBuilder.append(this);
        SignalStrength.loge(stringBuilder.toString());
        return 0;
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getLteAsuLevel() {
        return this.mLte.getAsuLevel();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getLteCqi() {
        return this.mLte.getCqi();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getLteDbm() {
        return this.mLte.getRsrp();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getLteLevel() {
        return this.mLte.getLevel();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getLteRsrp() {
        return this.mLte.getRsrp();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getLteRsrq() {
        return this.mLte.getRsrq();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getLteRssnr() {
        return this.mLte.getRssnr();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getLteSignalStrength() {
        return this.mLte.getRssi();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getTdScdmaAsuLevel() {
        return this.mTdscdma.getAsuLevel();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getTdScdmaDbm() {
        return this.mTdscdma.getRscp();
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getTdScdmaLevel() {
        return this.mTdscdma.getLevel();
    }

    @Deprecated
    public int getWcdmaAsuLevel() {
        return this.mWcdma.getAsuLevel();
    }

    @Deprecated
    public int getWcdmaDbm() {
        return this.mWcdma.getDbm();
    }

    @Deprecated
    public int getWcdmaLevel() {
        return this.mWcdma.getLevel();
    }

    @Deprecated
    public int getWcdmaRscp() {
        return this.mWcdma.getRscp();
    }

    public int hashCode() {
        return Objects.hash(this.mCdma, this.mGsm, this.mWcdma, this.mTdscdma, this.mLte, this.mNr);
    }

    @Deprecated
    public boolean isGsm() {
        boolean bl = !(this.getPrimary() instanceof CellSignalStrengthCdma);
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SignalStrength:{");
        stringBuilder.append("mCdma=");
        stringBuilder.append(this.mCdma);
        stringBuilder.append(",mGsm=");
        stringBuilder.append(this.mGsm);
        stringBuilder.append(",mWcdma=");
        stringBuilder.append(this.mWcdma);
        stringBuilder.append(",mTdscdma=");
        stringBuilder.append(this.mTdscdma);
        stringBuilder.append(",mLte=");
        stringBuilder.append(this.mLte);
        stringBuilder.append(",mNr=");
        stringBuilder.append(this.mNr);
        stringBuilder.append(",primary=");
        stringBuilder.append(this.getPrimary().getClass().getSimpleName());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        this.mCdma.updateLevel(persistableBundle, serviceState);
        this.mGsm.updateLevel(persistableBundle, serviceState);
        this.mWcdma.updateLevel(persistableBundle, serviceState);
        this.mTdscdma.updateLevel(persistableBundle, serviceState);
        this.mLte.updateLevel(persistableBundle, serviceState);
        this.mNr.updateLevel(persistableBundle, serviceState);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mCdma, n);
        parcel.writeParcelable(this.mGsm, n);
        parcel.writeParcelable(this.mWcdma, n);
        parcel.writeParcelable(this.mTdscdma, n);
        parcel.writeParcelable(this.mLte, n);
        parcel.writeParcelable(this.mNr, n);
    }

}

