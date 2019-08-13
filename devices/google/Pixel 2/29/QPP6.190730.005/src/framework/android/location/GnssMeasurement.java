/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class GnssMeasurement
implements Parcelable {
    public static final int ADR_STATE_ALL = 31;
    public static final int ADR_STATE_CYCLE_SLIP = 4;
    public static final int ADR_STATE_HALF_CYCLE_REPORTED = 16;
    public static final int ADR_STATE_HALF_CYCLE_RESOLVED = 8;
    public static final int ADR_STATE_RESET = 2;
    public static final int ADR_STATE_UNKNOWN = 0;
    public static final int ADR_STATE_VALID = 1;
    public static final Parcelable.Creator<GnssMeasurement> CREATOR = new Parcelable.Creator<GnssMeasurement>(){

        @Override
        public GnssMeasurement createFromParcel(Parcel parcel) {
            GnssMeasurement gnssMeasurement = new GnssMeasurement();
            gnssMeasurement.mFlags = parcel.readInt();
            gnssMeasurement.mSvid = parcel.readInt();
            gnssMeasurement.mConstellationType = parcel.readInt();
            gnssMeasurement.mTimeOffsetNanos = parcel.readDouble();
            gnssMeasurement.mState = parcel.readInt();
            gnssMeasurement.mReceivedSvTimeNanos = parcel.readLong();
            gnssMeasurement.mReceivedSvTimeUncertaintyNanos = parcel.readLong();
            gnssMeasurement.mCn0DbHz = parcel.readDouble();
            gnssMeasurement.mPseudorangeRateMetersPerSecond = parcel.readDouble();
            gnssMeasurement.mPseudorangeRateUncertaintyMetersPerSecond = parcel.readDouble();
            gnssMeasurement.mAccumulatedDeltaRangeState = parcel.readInt();
            gnssMeasurement.mAccumulatedDeltaRangeMeters = parcel.readDouble();
            gnssMeasurement.mAccumulatedDeltaRangeUncertaintyMeters = parcel.readDouble();
            gnssMeasurement.mCarrierFrequencyHz = parcel.readFloat();
            gnssMeasurement.mCarrierCycles = parcel.readLong();
            gnssMeasurement.mCarrierPhase = parcel.readDouble();
            gnssMeasurement.mCarrierPhaseUncertainty = parcel.readDouble();
            gnssMeasurement.mMultipathIndicator = parcel.readInt();
            gnssMeasurement.mSnrInDb = parcel.readDouble();
            gnssMeasurement.mAutomaticGainControlLevelInDb = parcel.readDouble();
            gnssMeasurement.mCodeType = parcel.readString();
            return gnssMeasurement;
        }

        public GnssMeasurement[] newArray(int n) {
            return new GnssMeasurement[n];
        }
    };
    private static final int HAS_AUTOMATIC_GAIN_CONTROL = 8192;
    private static final int HAS_CARRIER_CYCLES = 1024;
    private static final int HAS_CARRIER_FREQUENCY = 512;
    private static final int HAS_CARRIER_PHASE = 2048;
    private static final int HAS_CARRIER_PHASE_UNCERTAINTY = 4096;
    private static final int HAS_CODE_TYPE = 16384;
    private static final int HAS_NO_FLAGS = 0;
    private static final int HAS_SNR = 1;
    public static final int MULTIPATH_INDICATOR_DETECTED = 1;
    public static final int MULTIPATH_INDICATOR_NOT_DETECTED = 2;
    public static final int MULTIPATH_INDICATOR_UNKNOWN = 0;
    public static final int STATE_2ND_CODE_LOCK = 65536;
    private static final int STATE_ALL = 16383;
    public static final int STATE_BDS_D2_BIT_SYNC = 256;
    public static final int STATE_BDS_D2_SUBFRAME_SYNC = 512;
    public static final int STATE_BIT_SYNC = 2;
    public static final int STATE_CODE_LOCK = 1;
    public static final int STATE_GAL_E1BC_CODE_LOCK = 1024;
    public static final int STATE_GAL_E1B_PAGE_SYNC = 4096;
    public static final int STATE_GAL_E1C_2ND_CODE_LOCK = 2048;
    public static final int STATE_GLO_STRING_SYNC = 64;
    public static final int STATE_GLO_TOD_DECODED = 128;
    public static final int STATE_GLO_TOD_KNOWN = 32768;
    public static final int STATE_MSEC_AMBIGUOUS = 16;
    public static final int STATE_SBAS_SYNC = 8192;
    public static final int STATE_SUBFRAME_SYNC = 4;
    public static final int STATE_SYMBOL_SYNC = 32;
    public static final int STATE_TOW_DECODED = 8;
    public static final int STATE_TOW_KNOWN = 16384;
    public static final int STATE_UNKNOWN = 0;
    private double mAccumulatedDeltaRangeMeters;
    private int mAccumulatedDeltaRangeState;
    private double mAccumulatedDeltaRangeUncertaintyMeters;
    private double mAutomaticGainControlLevelInDb;
    private long mCarrierCycles;
    private float mCarrierFrequencyHz;
    private double mCarrierPhase;
    private double mCarrierPhaseUncertainty;
    private double mCn0DbHz;
    private String mCodeType;
    private int mConstellationType;
    private int mFlags;
    private int mMultipathIndicator;
    private double mPseudorangeRateMetersPerSecond;
    private double mPseudorangeRateUncertaintyMetersPerSecond;
    private long mReceivedSvTimeNanos;
    private long mReceivedSvTimeUncertaintyNanos;
    private double mSnrInDb;
    private int mState;
    private int mSvid;
    private double mTimeOffsetNanos;

    public GnssMeasurement() {
        this.initialize();
    }

    private String getAccumulatedDeltaRangeStateString() {
        int n;
        if (this.mAccumulatedDeltaRangeState == 0) {
            return "Unknown";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if ((this.mAccumulatedDeltaRangeState & 1) == 1) {
            stringBuilder.append("Valid|");
        }
        if ((this.mAccumulatedDeltaRangeState & 2) == 2) {
            stringBuilder.append("Reset|");
        }
        if ((this.mAccumulatedDeltaRangeState & 4) == 4) {
            stringBuilder.append("CycleSlip|");
        }
        if ((this.mAccumulatedDeltaRangeState & 8) == 8) {
            stringBuilder.append("HalfCycleResolved|");
        }
        if ((this.mAccumulatedDeltaRangeState & 16) == 16) {
            stringBuilder.append("HalfCycleReported|");
        }
        if ((n = this.mAccumulatedDeltaRangeState & -32) > 0) {
            stringBuilder.append("Other(");
            stringBuilder.append(Integer.toBinaryString(n));
            stringBuilder.append(")|");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String getMultipathIndicatorString() {
        int n = this.mMultipathIndicator;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("<Invalid: ");
                    stringBuilder.append(this.mMultipathIndicator);
                    stringBuilder.append(">");
                    return stringBuilder.toString();
                }
                return "NotDetected";
            }
            return "Detected";
        }
        return "Unknown";
    }

    private String getStateString() {
        int n;
        if (this.mState == 0) {
            return "Unknown";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if ((this.mState & 1) != 0) {
            stringBuilder.append("CodeLock|");
        }
        if ((this.mState & 2) != 0) {
            stringBuilder.append("BitSync|");
        }
        if ((this.mState & 4) != 0) {
            stringBuilder.append("SubframeSync|");
        }
        if ((this.mState & 8) != 0) {
            stringBuilder.append("TowDecoded|");
        }
        if ((this.mState & 16384) != 0) {
            stringBuilder.append("TowKnown|");
        }
        if ((this.mState & 16) != 0) {
            stringBuilder.append("MsecAmbiguous|");
        }
        if ((this.mState & 32) != 0) {
            stringBuilder.append("SymbolSync|");
        }
        if ((this.mState & 64) != 0) {
            stringBuilder.append("GloStringSync|");
        }
        if ((this.mState & 128) != 0) {
            stringBuilder.append("GloTodDecoded|");
        }
        if ((this.mState & 32768) != 0) {
            stringBuilder.append("GloTodKnown|");
        }
        if ((this.mState & 256) != 0) {
            stringBuilder.append("BdsD2BitSync|");
        }
        if ((this.mState & 512) != 0) {
            stringBuilder.append("BdsD2SubframeSync|");
        }
        if ((this.mState & 1024) != 0) {
            stringBuilder.append("GalE1bcCodeLock|");
        }
        if ((this.mState & 2048) != 0) {
            stringBuilder.append("E1c2ndCodeLock|");
        }
        if ((this.mState & 4096) != 0) {
            stringBuilder.append("GalE1bPageSync|");
        }
        if ((this.mState & 8192) != 0) {
            stringBuilder.append("SbasSync|");
        }
        if ((this.mState & 65536) != 0) {
            stringBuilder.append("2ndCodeLock|");
        }
        if ((n = this.mState & -16384) > 0) {
            stringBuilder.append("Other(");
            stringBuilder.append(Integer.toBinaryString(n));
            stringBuilder.append(")|");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private void initialize() {
        this.mFlags = 0;
        this.setSvid(0);
        this.setTimeOffsetNanos(-9.223372036854776E18);
        this.setState(0);
        this.setReceivedSvTimeNanos(Long.MIN_VALUE);
        this.setReceivedSvTimeUncertaintyNanos(Long.MAX_VALUE);
        this.setCn0DbHz(Double.MIN_VALUE);
        this.setPseudorangeRateMetersPerSecond(Double.MIN_VALUE);
        this.setPseudorangeRateUncertaintyMetersPerSecond(Double.MIN_VALUE);
        this.setAccumulatedDeltaRangeState(0);
        this.setAccumulatedDeltaRangeMeters(Double.MIN_VALUE);
        this.setAccumulatedDeltaRangeUncertaintyMeters(Double.MIN_VALUE);
        this.resetCarrierFrequencyHz();
        this.resetCarrierCycles();
        this.resetCarrierPhase();
        this.resetCarrierPhaseUncertainty();
        this.setMultipathIndicator(0);
        this.resetSnrInDb();
        this.resetAutomaticGainControlLevel();
        this.resetCodeType();
    }

    private boolean isFlagSet(int n) {
        boolean bl = (this.mFlags & n) == n;
        return bl;
    }

    private void resetFlag(int n) {
        this.mFlags &= n;
    }

    private void setFlag(int n) {
        this.mFlags |= n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getAccumulatedDeltaRangeMeters() {
        return this.mAccumulatedDeltaRangeMeters;
    }

    public int getAccumulatedDeltaRangeState() {
        return this.mAccumulatedDeltaRangeState;
    }

    public double getAccumulatedDeltaRangeUncertaintyMeters() {
        return this.mAccumulatedDeltaRangeUncertaintyMeters;
    }

    public double getAutomaticGainControlLevelDb() {
        return this.mAutomaticGainControlLevelInDb;
    }

    @Deprecated
    public long getCarrierCycles() {
        return this.mCarrierCycles;
    }

    public float getCarrierFrequencyHz() {
        return this.mCarrierFrequencyHz;
    }

    @Deprecated
    public double getCarrierPhase() {
        return this.mCarrierPhase;
    }

    @Deprecated
    public double getCarrierPhaseUncertainty() {
        return this.mCarrierPhaseUncertainty;
    }

    public double getCn0DbHz() {
        return this.mCn0DbHz;
    }

    public String getCodeType() {
        return this.mCodeType;
    }

    public int getConstellationType() {
        return this.mConstellationType;
    }

    public int getMultipathIndicator() {
        return this.mMultipathIndicator;
    }

    public double getPseudorangeRateMetersPerSecond() {
        return this.mPseudorangeRateMetersPerSecond;
    }

    public double getPseudorangeRateUncertaintyMetersPerSecond() {
        return this.mPseudorangeRateUncertaintyMetersPerSecond;
    }

    public long getReceivedSvTimeNanos() {
        return this.mReceivedSvTimeNanos;
    }

    public long getReceivedSvTimeUncertaintyNanos() {
        return this.mReceivedSvTimeUncertaintyNanos;
    }

    public double getSnrInDb() {
        return this.mSnrInDb;
    }

    public int getState() {
        return this.mState;
    }

    public int getSvid() {
        return this.mSvid;
    }

    public double getTimeOffsetNanos() {
        return this.mTimeOffsetNanos;
    }

    public boolean hasAutomaticGainControlLevelDb() {
        return this.isFlagSet(8192);
    }

    @Deprecated
    public boolean hasCarrierCycles() {
        return this.isFlagSet(1024);
    }

    public boolean hasCarrierFrequencyHz() {
        return this.isFlagSet(512);
    }

    @Deprecated
    public boolean hasCarrierPhase() {
        return this.isFlagSet(2048);
    }

    @Deprecated
    public boolean hasCarrierPhaseUncertainty() {
        return this.isFlagSet(4096);
    }

    public boolean hasCodeType() {
        return this.isFlagSet(16384);
    }

    public boolean hasSnrInDb() {
        return this.isFlagSet(1);
    }

    public void reset() {
        this.initialize();
    }

    public void resetAutomaticGainControlLevel() {
        this.resetFlag(8192);
        this.mAutomaticGainControlLevelInDb = Double.NaN;
    }

    @Deprecated
    public void resetCarrierCycles() {
        this.resetFlag(1024);
        this.mCarrierCycles = Long.MIN_VALUE;
    }

    public void resetCarrierFrequencyHz() {
        this.resetFlag(512);
        this.mCarrierFrequencyHz = Float.NaN;
    }

    @Deprecated
    public void resetCarrierPhase() {
        this.resetFlag(2048);
        this.mCarrierPhase = Double.NaN;
    }

    @Deprecated
    public void resetCarrierPhaseUncertainty() {
        this.resetFlag(4096);
        this.mCarrierPhaseUncertainty = Double.NaN;
    }

    public void resetCodeType() {
        this.resetFlag(16384);
        this.mCodeType = "UNKNOWN";
    }

    public void resetSnrInDb() {
        this.resetFlag(1);
        this.mSnrInDb = Double.NaN;
    }

    public void set(GnssMeasurement gnssMeasurement) {
        this.mFlags = gnssMeasurement.mFlags;
        this.mSvid = gnssMeasurement.mSvid;
        this.mConstellationType = gnssMeasurement.mConstellationType;
        this.mTimeOffsetNanos = gnssMeasurement.mTimeOffsetNanos;
        this.mState = gnssMeasurement.mState;
        this.mReceivedSvTimeNanos = gnssMeasurement.mReceivedSvTimeNanos;
        this.mReceivedSvTimeUncertaintyNanos = gnssMeasurement.mReceivedSvTimeUncertaintyNanos;
        this.mCn0DbHz = gnssMeasurement.mCn0DbHz;
        this.mPseudorangeRateMetersPerSecond = gnssMeasurement.mPseudorangeRateMetersPerSecond;
        this.mPseudorangeRateUncertaintyMetersPerSecond = gnssMeasurement.mPseudorangeRateUncertaintyMetersPerSecond;
        this.mAccumulatedDeltaRangeState = gnssMeasurement.mAccumulatedDeltaRangeState;
        this.mAccumulatedDeltaRangeMeters = gnssMeasurement.mAccumulatedDeltaRangeMeters;
        this.mAccumulatedDeltaRangeUncertaintyMeters = gnssMeasurement.mAccumulatedDeltaRangeUncertaintyMeters;
        this.mCarrierFrequencyHz = gnssMeasurement.mCarrierFrequencyHz;
        this.mCarrierCycles = gnssMeasurement.mCarrierCycles;
        this.mCarrierPhase = gnssMeasurement.mCarrierPhase;
        this.mCarrierPhaseUncertainty = gnssMeasurement.mCarrierPhaseUncertainty;
        this.mMultipathIndicator = gnssMeasurement.mMultipathIndicator;
        this.mSnrInDb = gnssMeasurement.mSnrInDb;
        this.mAutomaticGainControlLevelInDb = gnssMeasurement.mAutomaticGainControlLevelInDb;
        this.mCodeType = gnssMeasurement.mCodeType;
    }

    public void setAccumulatedDeltaRangeMeters(double d) {
        this.mAccumulatedDeltaRangeMeters = d;
    }

    public void setAccumulatedDeltaRangeState(int n) {
        this.mAccumulatedDeltaRangeState = n;
    }

    public void setAccumulatedDeltaRangeUncertaintyMeters(double d) {
        this.mAccumulatedDeltaRangeUncertaintyMeters = d;
    }

    public void setAutomaticGainControlLevelInDb(double d) {
        this.setFlag(8192);
        this.mAutomaticGainControlLevelInDb = d;
    }

    @Deprecated
    public void setCarrierCycles(long l) {
        this.setFlag(1024);
        this.mCarrierCycles = l;
    }

    public void setCarrierFrequencyHz(float f) {
        this.setFlag(512);
        this.mCarrierFrequencyHz = f;
    }

    @Deprecated
    public void setCarrierPhase(double d) {
        this.setFlag(2048);
        this.mCarrierPhase = d;
    }

    @Deprecated
    public void setCarrierPhaseUncertainty(double d) {
        this.setFlag(4096);
        this.mCarrierPhaseUncertainty = d;
    }

    public void setCn0DbHz(double d) {
        this.mCn0DbHz = d;
    }

    public void setCodeType(String string2) {
        this.setFlag(16384);
        this.mCodeType = string2;
    }

    public void setConstellationType(int n) {
        this.mConstellationType = n;
    }

    public void setMultipathIndicator(int n) {
        this.mMultipathIndicator = n;
    }

    public void setPseudorangeRateMetersPerSecond(double d) {
        this.mPseudorangeRateMetersPerSecond = d;
    }

    public void setPseudorangeRateUncertaintyMetersPerSecond(double d) {
        this.mPseudorangeRateUncertaintyMetersPerSecond = d;
    }

    public void setReceivedSvTimeNanos(long l) {
        this.mReceivedSvTimeNanos = l;
    }

    public void setReceivedSvTimeUncertaintyNanos(long l) {
        this.mReceivedSvTimeUncertaintyNanos = l;
    }

    public void setSnrInDb(double d) {
        this.setFlag(1);
        this.mSnrInDb = d;
    }

    public void setState(int n) {
        this.mState = n;
    }

    public void setSvid(int n) {
        this.mSvid = n;
    }

    public void setTimeOffsetNanos(double d) {
        this.mTimeOffsetNanos = d;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GnssMeasurement:\n");
        stringBuilder.append(String.format("   %-29s = %s\n", "Svid", this.mSvid));
        stringBuilder.append(String.format("   %-29s = %s\n", "ConstellationType", this.mConstellationType));
        stringBuilder.append(String.format("   %-29s = %s\n", "TimeOffsetNanos", this.mTimeOffsetNanos));
        stringBuilder.append(String.format("   %-29s = %s\n", "State", this.getStateString()));
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "ReceivedSvTimeNanos", this.mReceivedSvTimeNanos, "ReceivedSvTimeUncertaintyNanos", this.mReceivedSvTimeUncertaintyNanos));
        stringBuilder.append(String.format("   %-29s = %s\n", "Cn0DbHz", this.mCn0DbHz));
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "PseudorangeRateMetersPerSecond", this.mPseudorangeRateMetersPerSecond, "PseudorangeRateUncertaintyMetersPerSecond", this.mPseudorangeRateUncertaintyMetersPerSecond));
        stringBuilder.append(String.format("   %-29s = %s\n", "AccumulatedDeltaRangeState", this.getAccumulatedDeltaRangeStateString()));
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "AccumulatedDeltaRangeMeters", this.mAccumulatedDeltaRangeMeters, "AccumulatedDeltaRangeUncertaintyMeters", this.mAccumulatedDeltaRangeUncertaintyMeters));
        boolean bl = this.hasCarrierFrequencyHz();
        Object var3_3 = null;
        Object object = bl ? Float.valueOf(this.mCarrierFrequencyHz) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "CarrierFrequencyHz", object));
        object = this.hasCarrierCycles() ? Long.valueOf(this.mCarrierCycles) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "CarrierCycles", object));
        object = this.hasCarrierPhase() ? Double.valueOf(this.mCarrierPhase) : null;
        Double d = this.hasCarrierPhaseUncertainty() ? Double.valueOf(this.mCarrierPhaseUncertainty) : null;
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "CarrierPhase", object, "CarrierPhaseUncertainty", d));
        stringBuilder.append(String.format("   %-29s = %s\n", "MultipathIndicator", this.getMultipathIndicatorString()));
        object = this.hasSnrInDb() ? Double.valueOf(this.mSnrInDb) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "SnrInDb", object));
        object = this.hasAutomaticGainControlLevelDb() ? Double.valueOf(this.mAutomaticGainControlLevelInDb) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "AgcLevelDb", object));
        object = var3_3;
        if (this.hasCodeType()) {
            object = this.mCodeType;
        }
        stringBuilder.append(String.format("   %-29s = %s\n", "CodeType", object));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mSvid);
        parcel.writeInt(this.mConstellationType);
        parcel.writeDouble(this.mTimeOffsetNanos);
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mReceivedSvTimeNanos);
        parcel.writeLong(this.mReceivedSvTimeUncertaintyNanos);
        parcel.writeDouble(this.mCn0DbHz);
        parcel.writeDouble(this.mPseudorangeRateMetersPerSecond);
        parcel.writeDouble(this.mPseudorangeRateUncertaintyMetersPerSecond);
        parcel.writeInt(this.mAccumulatedDeltaRangeState);
        parcel.writeDouble(this.mAccumulatedDeltaRangeMeters);
        parcel.writeDouble(this.mAccumulatedDeltaRangeUncertaintyMeters);
        parcel.writeFloat(this.mCarrierFrequencyHz);
        parcel.writeLong(this.mCarrierCycles);
        parcel.writeDouble(this.mCarrierPhase);
        parcel.writeDouble(this.mCarrierPhaseUncertainty);
        parcel.writeInt(this.mMultipathIndicator);
        parcel.writeDouble(this.mSnrInDb);
        parcel.writeDouble(this.mAutomaticGainControlLevelInDb);
        parcel.writeString(this.mCodeType);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AdrState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MultipathIndicator {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }

}

