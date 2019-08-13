/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public class GpsMeasurement
implements Parcelable {
    private static final short ADR_ALL = 7;
    public static final short ADR_STATE_CYCLE_SLIP = 4;
    public static final short ADR_STATE_RESET = 2;
    public static final short ADR_STATE_UNKNOWN = 0;
    public static final short ADR_STATE_VALID = 1;
    public static final Parcelable.Creator<GpsMeasurement> CREATOR = new Parcelable.Creator<GpsMeasurement>(){

        @Override
        public GpsMeasurement createFromParcel(Parcel parcel) {
            GpsMeasurement gpsMeasurement = new GpsMeasurement();
            gpsMeasurement.mFlags = parcel.readInt();
            gpsMeasurement.mPrn = parcel.readByte();
            gpsMeasurement.mTimeOffsetInNs = parcel.readDouble();
            gpsMeasurement.mState = (short)parcel.readInt();
            gpsMeasurement.mReceivedGpsTowInNs = parcel.readLong();
            gpsMeasurement.mReceivedGpsTowUncertaintyInNs = parcel.readLong();
            gpsMeasurement.mCn0InDbHz = parcel.readDouble();
            gpsMeasurement.mPseudorangeRateInMetersPerSec = parcel.readDouble();
            gpsMeasurement.mPseudorangeRateUncertaintyInMetersPerSec = parcel.readDouble();
            gpsMeasurement.mAccumulatedDeltaRangeState = (short)parcel.readInt();
            gpsMeasurement.mAccumulatedDeltaRangeInMeters = parcel.readDouble();
            gpsMeasurement.mAccumulatedDeltaRangeUncertaintyInMeters = parcel.readDouble();
            gpsMeasurement.mPseudorangeInMeters = parcel.readDouble();
            gpsMeasurement.mPseudorangeUncertaintyInMeters = parcel.readDouble();
            gpsMeasurement.mCodePhaseInChips = parcel.readDouble();
            gpsMeasurement.mCodePhaseUncertaintyInChips = parcel.readDouble();
            gpsMeasurement.mCarrierFrequencyInHz = parcel.readFloat();
            gpsMeasurement.mCarrierCycles = parcel.readLong();
            gpsMeasurement.mCarrierPhase = parcel.readDouble();
            gpsMeasurement.mCarrierPhaseUncertainty = parcel.readDouble();
            gpsMeasurement.mLossOfLock = parcel.readByte();
            gpsMeasurement.mBitNumber = parcel.readInt();
            gpsMeasurement.mTimeFromLastBitInMs = (short)parcel.readInt();
            gpsMeasurement.mDopplerShiftInHz = parcel.readDouble();
            gpsMeasurement.mDopplerShiftUncertaintyInHz = parcel.readDouble();
            gpsMeasurement.mMultipathIndicator = parcel.readByte();
            gpsMeasurement.mSnrInDb = parcel.readDouble();
            gpsMeasurement.mElevationInDeg = parcel.readDouble();
            gpsMeasurement.mElevationUncertaintyInDeg = parcel.readDouble();
            gpsMeasurement.mAzimuthInDeg = parcel.readDouble();
            gpsMeasurement.mAzimuthUncertaintyInDeg = parcel.readDouble();
            boolean bl = parcel.readInt() != 0;
            gpsMeasurement.mUsedInFix = bl;
            return gpsMeasurement;
        }

        public GpsMeasurement[] newArray(int n) {
            return new GpsMeasurement[n];
        }
    };
    private static final int GPS_MEASUREMENT_HAS_UNCORRECTED_PSEUDORANGE_RATE = 262144;
    private static final int HAS_AZIMUTH = 8;
    private static final int HAS_AZIMUTH_UNCERTAINTY = 16;
    private static final int HAS_BIT_NUMBER = 8192;
    private static final int HAS_CARRIER_CYCLES = 1024;
    private static final int HAS_CARRIER_FREQUENCY = 512;
    private static final int HAS_CARRIER_PHASE = 2048;
    private static final int HAS_CARRIER_PHASE_UNCERTAINTY = 4096;
    private static final int HAS_CODE_PHASE = 128;
    private static final int HAS_CODE_PHASE_UNCERTAINTY = 256;
    private static final int HAS_DOPPLER_SHIFT = 32768;
    private static final int HAS_DOPPLER_SHIFT_UNCERTAINTY = 65536;
    private static final int HAS_ELEVATION = 2;
    private static final int HAS_ELEVATION_UNCERTAINTY = 4;
    private static final int HAS_NO_FLAGS = 0;
    private static final int HAS_PSEUDORANGE = 32;
    private static final int HAS_PSEUDORANGE_UNCERTAINTY = 64;
    private static final int HAS_SNR = 1;
    private static final int HAS_TIME_FROM_LAST_BIT = 16384;
    private static final int HAS_USED_IN_FIX = 131072;
    public static final byte LOSS_OF_LOCK_CYCLE_SLIP = 2;
    public static final byte LOSS_OF_LOCK_OK = 1;
    public static final byte LOSS_OF_LOCK_UNKNOWN = 0;
    public static final byte MULTIPATH_INDICATOR_DETECTED = 1;
    public static final byte MULTIPATH_INDICATOR_NOT_USED = 2;
    public static final byte MULTIPATH_INDICATOR_UNKNOWN = 0;
    private static final short STATE_ALL = 31;
    public static final short STATE_BIT_SYNC = 2;
    public static final short STATE_CODE_LOCK = 1;
    public static final short STATE_MSEC_AMBIGUOUS = 16;
    public static final short STATE_SUBFRAME_SYNC = 4;
    public static final short STATE_TOW_DECODED = 8;
    public static final short STATE_UNKNOWN = 0;
    private double mAccumulatedDeltaRangeInMeters;
    private short mAccumulatedDeltaRangeState;
    private double mAccumulatedDeltaRangeUncertaintyInMeters;
    private double mAzimuthInDeg;
    private double mAzimuthUncertaintyInDeg;
    private int mBitNumber;
    private long mCarrierCycles;
    private float mCarrierFrequencyInHz;
    private double mCarrierPhase;
    private double mCarrierPhaseUncertainty;
    private double mCn0InDbHz;
    private double mCodePhaseInChips;
    private double mCodePhaseUncertaintyInChips;
    private double mDopplerShiftInHz;
    private double mDopplerShiftUncertaintyInHz;
    private double mElevationInDeg;
    private double mElevationUncertaintyInDeg;
    private int mFlags;
    private byte mLossOfLock;
    private byte mMultipathIndicator;
    private byte mPrn;
    private double mPseudorangeInMeters;
    private double mPseudorangeRateInMetersPerSec;
    private double mPseudorangeRateUncertaintyInMetersPerSec;
    private double mPseudorangeUncertaintyInMeters;
    private long mReceivedGpsTowInNs;
    private long mReceivedGpsTowUncertaintyInNs;
    private double mSnrInDb;
    private short mState;
    private short mTimeFromLastBitInMs;
    private double mTimeOffsetInNs;
    private boolean mUsedInFix;

    GpsMeasurement() {
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
        if ((n = this.mAccumulatedDeltaRangeState & -8) > 0) {
            stringBuilder.append("Other(");
            stringBuilder.append(Integer.toBinaryString(n));
            stringBuilder.append(")|");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String getLossOfLockString() {
        byte by = this.mLossOfLock;
        if (by != 0) {
            if (by != 1) {
                if (by != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("<Invalid:");
                    stringBuilder.append(this.mLossOfLock);
                    stringBuilder.append(">");
                    return stringBuilder.toString();
                }
                return "CycleSlip";
            }
            return "Ok";
        }
        return "Unknown";
    }

    private String getMultipathIndicatorString() {
        byte by = this.mMultipathIndicator;
        if (by != 0) {
            if (by != 1) {
                if (by != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("<Invalid:");
                    stringBuilder.append(this.mMultipathIndicator);
                    stringBuilder.append(">");
                    return stringBuilder.toString();
                }
                return "NotUsed";
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
        if ((this.mState & 1) == 1) {
            stringBuilder.append("CodeLock|");
        }
        if ((this.mState & 2) == 2) {
            stringBuilder.append("BitSync|");
        }
        if ((this.mState & 4) == 4) {
            stringBuilder.append("SubframeSync|");
        }
        if ((this.mState & 8) == 8) {
            stringBuilder.append("TowDecoded|");
        }
        if ((this.mState & 16) == 16) {
            stringBuilder.append("MsecAmbiguous");
        }
        if ((n = this.mState & -32) > 0) {
            stringBuilder.append("Other(");
            stringBuilder.append(Integer.toBinaryString(n));
            stringBuilder.append(")|");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private void initialize() {
        this.mFlags = 0;
        this.setPrn((byte)-128);
        this.setTimeOffsetInNs(-9.223372036854776E18);
        this.setState((short)0);
        this.setReceivedGpsTowInNs(Long.MIN_VALUE);
        this.setReceivedGpsTowUncertaintyInNs(Long.MAX_VALUE);
        this.setCn0InDbHz(Double.MIN_VALUE);
        this.setPseudorangeRateInMetersPerSec(Double.MIN_VALUE);
        this.setPseudorangeRateUncertaintyInMetersPerSec(Double.MIN_VALUE);
        this.setAccumulatedDeltaRangeState((short)0);
        this.setAccumulatedDeltaRangeInMeters(Double.MIN_VALUE);
        this.setAccumulatedDeltaRangeUncertaintyInMeters(Double.MIN_VALUE);
        this.resetPseudorangeInMeters();
        this.resetPseudorangeUncertaintyInMeters();
        this.resetCodePhaseInChips();
        this.resetCodePhaseUncertaintyInChips();
        this.resetCarrierFrequencyInHz();
        this.resetCarrierCycles();
        this.resetCarrierPhase();
        this.resetCarrierPhaseUncertainty();
        this.setLossOfLock((byte)0);
        this.resetBitNumber();
        this.resetTimeFromLastBitInMs();
        this.resetDopplerShiftInHz();
        this.resetDopplerShiftUncertaintyInHz();
        this.setMultipathIndicator((byte)0);
        this.resetSnrInDb();
        this.resetElevationInDeg();
        this.resetElevationUncertaintyInDeg();
        this.resetAzimuthInDeg();
        this.resetAzimuthUncertaintyInDeg();
        this.setUsedInFix(false);
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

    public double getAccumulatedDeltaRangeInMeters() {
        return this.mAccumulatedDeltaRangeInMeters;
    }

    public short getAccumulatedDeltaRangeState() {
        return this.mAccumulatedDeltaRangeState;
    }

    public double getAccumulatedDeltaRangeUncertaintyInMeters() {
        return this.mAccumulatedDeltaRangeUncertaintyInMeters;
    }

    public double getAzimuthInDeg() {
        return this.mAzimuthInDeg;
    }

    public double getAzimuthUncertaintyInDeg() {
        return this.mAzimuthUncertaintyInDeg;
    }

    public int getBitNumber() {
        return this.mBitNumber;
    }

    public long getCarrierCycles() {
        return this.mCarrierCycles;
    }

    public float getCarrierFrequencyInHz() {
        return this.mCarrierFrequencyInHz;
    }

    public double getCarrierPhase() {
        return this.mCarrierPhase;
    }

    public double getCarrierPhaseUncertainty() {
        return this.mCarrierPhaseUncertainty;
    }

    public double getCn0InDbHz() {
        return this.mCn0InDbHz;
    }

    public double getCodePhaseInChips() {
        return this.mCodePhaseInChips;
    }

    public double getCodePhaseUncertaintyInChips() {
        return this.mCodePhaseUncertaintyInChips;
    }

    public double getDopplerShiftInHz() {
        return this.mDopplerShiftInHz;
    }

    public double getDopplerShiftUncertaintyInHz() {
        return this.mDopplerShiftUncertaintyInHz;
    }

    public double getElevationInDeg() {
        return this.mElevationInDeg;
    }

    public double getElevationUncertaintyInDeg() {
        return this.mElevationUncertaintyInDeg;
    }

    public byte getLossOfLock() {
        return this.mLossOfLock;
    }

    public byte getMultipathIndicator() {
        return this.mMultipathIndicator;
    }

    public byte getPrn() {
        return this.mPrn;
    }

    public double getPseudorangeInMeters() {
        return this.mPseudorangeInMeters;
    }

    public double getPseudorangeRateInMetersPerSec() {
        return this.mPseudorangeRateInMetersPerSec;
    }

    public double getPseudorangeRateUncertaintyInMetersPerSec() {
        return this.mPseudorangeRateUncertaintyInMetersPerSec;
    }

    public double getPseudorangeUncertaintyInMeters() {
        return this.mPseudorangeUncertaintyInMeters;
    }

    public long getReceivedGpsTowInNs() {
        return this.mReceivedGpsTowInNs;
    }

    public long getReceivedGpsTowUncertaintyInNs() {
        return this.mReceivedGpsTowUncertaintyInNs;
    }

    public double getSnrInDb() {
        return this.mSnrInDb;
    }

    public short getState() {
        return this.mState;
    }

    public short getTimeFromLastBitInMs() {
        return this.mTimeFromLastBitInMs;
    }

    public double getTimeOffsetInNs() {
        return this.mTimeOffsetInNs;
    }

    public boolean hasAzimuthInDeg() {
        return this.isFlagSet(8);
    }

    public boolean hasAzimuthUncertaintyInDeg() {
        return this.isFlagSet(16);
    }

    public boolean hasBitNumber() {
        return this.isFlagSet(8192);
    }

    public boolean hasCarrierCycles() {
        return this.isFlagSet(1024);
    }

    public boolean hasCarrierFrequencyInHz() {
        return this.isFlagSet(512);
    }

    public boolean hasCarrierPhase() {
        return this.isFlagSet(2048);
    }

    public boolean hasCarrierPhaseUncertainty() {
        return this.isFlagSet(4096);
    }

    public boolean hasCodePhaseInChips() {
        return this.isFlagSet(128);
    }

    public boolean hasCodePhaseUncertaintyInChips() {
        return this.isFlagSet(256);
    }

    public boolean hasDopplerShiftInHz() {
        return this.isFlagSet(32768);
    }

    public boolean hasDopplerShiftUncertaintyInHz() {
        return this.isFlagSet(65536);
    }

    public boolean hasElevationInDeg() {
        return this.isFlagSet(2);
    }

    public boolean hasElevationUncertaintyInDeg() {
        return this.isFlagSet(4);
    }

    public boolean hasPseudorangeInMeters() {
        return this.isFlagSet(32);
    }

    public boolean hasPseudorangeUncertaintyInMeters() {
        return this.isFlagSet(64);
    }

    public boolean hasSnrInDb() {
        return this.isFlagSet(1);
    }

    public boolean hasTimeFromLastBitInMs() {
        return this.isFlagSet(16384);
    }

    public boolean isPseudorangeRateCorrected() {
        return this.isFlagSet(262144) ^ true;
    }

    public boolean isUsedInFix() {
        return this.mUsedInFix;
    }

    public void reset() {
        this.initialize();
    }

    public void resetAzimuthInDeg() {
        this.resetFlag(8);
        this.mAzimuthInDeg = Double.NaN;
    }

    public void resetAzimuthUncertaintyInDeg() {
        this.resetFlag(16);
        this.mAzimuthUncertaintyInDeg = Double.NaN;
    }

    public void resetBitNumber() {
        this.resetFlag(8192);
        this.mBitNumber = Integer.MIN_VALUE;
    }

    public void resetCarrierCycles() {
        this.resetFlag(1024);
        this.mCarrierCycles = Long.MIN_VALUE;
    }

    public void resetCarrierFrequencyInHz() {
        this.resetFlag(512);
        this.mCarrierFrequencyInHz = Float.NaN;
    }

    public void resetCarrierPhase() {
        this.resetFlag(2048);
        this.mCarrierPhase = Double.NaN;
    }

    public void resetCarrierPhaseUncertainty() {
        this.resetFlag(4096);
        this.mCarrierPhaseUncertainty = Double.NaN;
    }

    public void resetCodePhaseInChips() {
        this.resetFlag(128);
        this.mCodePhaseInChips = Double.NaN;
    }

    public void resetCodePhaseUncertaintyInChips() {
        this.resetFlag(256);
        this.mCodePhaseUncertaintyInChips = Double.NaN;
    }

    public void resetDopplerShiftInHz() {
        this.resetFlag(32768);
        this.mDopplerShiftInHz = Double.NaN;
    }

    public void resetDopplerShiftUncertaintyInHz() {
        this.resetFlag(65536);
        this.mDopplerShiftUncertaintyInHz = Double.NaN;
    }

    public void resetElevationInDeg() {
        this.resetFlag(2);
        this.mElevationInDeg = Double.NaN;
    }

    public void resetElevationUncertaintyInDeg() {
        this.resetFlag(4);
        this.mElevationUncertaintyInDeg = Double.NaN;
    }

    public void resetPseudorangeInMeters() {
        this.resetFlag(32);
        this.mPseudorangeInMeters = Double.NaN;
    }

    public void resetPseudorangeUncertaintyInMeters() {
        this.resetFlag(64);
        this.mPseudorangeUncertaintyInMeters = Double.NaN;
    }

    public void resetSnrInDb() {
        this.resetFlag(1);
        this.mSnrInDb = Double.NaN;
    }

    public void resetTimeFromLastBitInMs() {
        this.resetFlag(16384);
        this.mTimeFromLastBitInMs = (short)-32768;
    }

    public void set(GpsMeasurement gpsMeasurement) {
        this.mFlags = gpsMeasurement.mFlags;
        this.mPrn = gpsMeasurement.mPrn;
        this.mTimeOffsetInNs = gpsMeasurement.mTimeOffsetInNs;
        this.mState = gpsMeasurement.mState;
        this.mReceivedGpsTowInNs = gpsMeasurement.mReceivedGpsTowInNs;
        this.mReceivedGpsTowUncertaintyInNs = gpsMeasurement.mReceivedGpsTowUncertaintyInNs;
        this.mCn0InDbHz = gpsMeasurement.mCn0InDbHz;
        this.mPseudorangeRateInMetersPerSec = gpsMeasurement.mPseudorangeRateInMetersPerSec;
        this.mPseudorangeRateUncertaintyInMetersPerSec = gpsMeasurement.mPseudorangeRateUncertaintyInMetersPerSec;
        this.mAccumulatedDeltaRangeState = gpsMeasurement.mAccumulatedDeltaRangeState;
        this.mAccumulatedDeltaRangeInMeters = gpsMeasurement.mAccumulatedDeltaRangeInMeters;
        this.mAccumulatedDeltaRangeUncertaintyInMeters = gpsMeasurement.mAccumulatedDeltaRangeUncertaintyInMeters;
        this.mPseudorangeInMeters = gpsMeasurement.mPseudorangeInMeters;
        this.mPseudorangeUncertaintyInMeters = gpsMeasurement.mPseudorangeUncertaintyInMeters;
        this.mCodePhaseInChips = gpsMeasurement.mCodePhaseInChips;
        this.mCodePhaseUncertaintyInChips = gpsMeasurement.mCodePhaseUncertaintyInChips;
        this.mCarrierFrequencyInHz = gpsMeasurement.mCarrierFrequencyInHz;
        this.mCarrierCycles = gpsMeasurement.mCarrierCycles;
        this.mCarrierPhase = gpsMeasurement.mCarrierPhase;
        this.mCarrierPhaseUncertainty = gpsMeasurement.mCarrierPhaseUncertainty;
        this.mLossOfLock = gpsMeasurement.mLossOfLock;
        this.mBitNumber = gpsMeasurement.mBitNumber;
        this.mTimeFromLastBitInMs = gpsMeasurement.mTimeFromLastBitInMs;
        this.mDopplerShiftInHz = gpsMeasurement.mDopplerShiftInHz;
        this.mDopplerShiftUncertaintyInHz = gpsMeasurement.mDopplerShiftUncertaintyInHz;
        this.mMultipathIndicator = gpsMeasurement.mMultipathIndicator;
        this.mSnrInDb = gpsMeasurement.mSnrInDb;
        this.mElevationInDeg = gpsMeasurement.mElevationInDeg;
        this.mElevationUncertaintyInDeg = gpsMeasurement.mElevationUncertaintyInDeg;
        this.mAzimuthInDeg = gpsMeasurement.mAzimuthInDeg;
        this.mAzimuthUncertaintyInDeg = gpsMeasurement.mAzimuthUncertaintyInDeg;
        this.mUsedInFix = gpsMeasurement.mUsedInFix;
    }

    public void setAccumulatedDeltaRangeInMeters(double d) {
        this.mAccumulatedDeltaRangeInMeters = d;
    }

    public void setAccumulatedDeltaRangeState(short s) {
        this.mAccumulatedDeltaRangeState = s;
    }

    public void setAccumulatedDeltaRangeUncertaintyInMeters(double d) {
        this.mAccumulatedDeltaRangeUncertaintyInMeters = d;
    }

    public void setAzimuthInDeg(double d) {
        this.setFlag(8);
        this.mAzimuthInDeg = d;
    }

    public void setAzimuthUncertaintyInDeg(double d) {
        this.setFlag(16);
        this.mAzimuthUncertaintyInDeg = d;
    }

    public void setBitNumber(int n) {
        this.setFlag(8192);
        this.mBitNumber = n;
    }

    public void setCarrierCycles(long l) {
        this.setFlag(1024);
        this.mCarrierCycles = l;
    }

    public void setCarrierFrequencyInHz(float f) {
        this.setFlag(512);
        this.mCarrierFrequencyInHz = f;
    }

    public void setCarrierPhase(double d) {
        this.setFlag(2048);
        this.mCarrierPhase = d;
    }

    public void setCarrierPhaseUncertainty(double d) {
        this.setFlag(4096);
        this.mCarrierPhaseUncertainty = d;
    }

    public void setCn0InDbHz(double d) {
        this.mCn0InDbHz = d;
    }

    public void setCodePhaseInChips(double d) {
        this.setFlag(128);
        this.mCodePhaseInChips = d;
    }

    public void setCodePhaseUncertaintyInChips(double d) {
        this.setFlag(256);
        this.mCodePhaseUncertaintyInChips = d;
    }

    public void setDopplerShiftInHz(double d) {
        this.setFlag(32768);
        this.mDopplerShiftInHz = d;
    }

    public void setDopplerShiftUncertaintyInHz(double d) {
        this.setFlag(65536);
        this.mDopplerShiftUncertaintyInHz = d;
    }

    public void setElevationInDeg(double d) {
        this.setFlag(2);
        this.mElevationInDeg = d;
    }

    public void setElevationUncertaintyInDeg(double d) {
        this.setFlag(4);
        this.mElevationUncertaintyInDeg = d;
    }

    public void setLossOfLock(byte by) {
        this.mLossOfLock = by;
    }

    public void setMultipathIndicator(byte by) {
        this.mMultipathIndicator = by;
    }

    public void setPrn(byte by) {
        this.mPrn = by;
    }

    public void setPseudorangeInMeters(double d) {
        this.setFlag(32);
        this.mPseudorangeInMeters = d;
    }

    public void setPseudorangeRateInMetersPerSec(double d) {
        this.mPseudorangeRateInMetersPerSec = d;
    }

    public void setPseudorangeRateUncertaintyInMetersPerSec(double d) {
        this.mPseudorangeRateUncertaintyInMetersPerSec = d;
    }

    public void setPseudorangeUncertaintyInMeters(double d) {
        this.setFlag(64);
        this.mPseudorangeUncertaintyInMeters = d;
    }

    public void setReceivedGpsTowInNs(long l) {
        this.mReceivedGpsTowInNs = l;
    }

    public void setReceivedGpsTowUncertaintyInNs(long l) {
        this.mReceivedGpsTowUncertaintyInNs = l;
    }

    public void setSnrInDb(double d) {
        this.setFlag(1);
        this.mSnrInDb = d;
    }

    public void setState(short s) {
        this.mState = s;
    }

    public void setTimeFromLastBitInMs(short s) {
        this.setFlag(16384);
        this.mTimeFromLastBitInMs = s;
    }

    public void setTimeOffsetInNs(double d) {
        this.mTimeOffsetInNs = d;
    }

    public void setUsedInFix(boolean bl) {
        this.mUsedInFix = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GpsMeasurement:\n");
        stringBuilder.append(String.format("   %-29s = %s\n", "Prn", this.mPrn));
        stringBuilder.append(String.format("   %-29s = %s\n", "TimeOffsetInNs", this.mTimeOffsetInNs));
        stringBuilder.append(String.format("   %-29s = %s\n", "State", this.getStateString()));
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "ReceivedGpsTowInNs", this.mReceivedGpsTowInNs, "ReceivedGpsTowUncertaintyInNs", this.mReceivedGpsTowUncertaintyInNs));
        stringBuilder.append(String.format("   %-29s = %s\n", "Cn0InDbHz", this.mCn0InDbHz));
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "PseudorangeRateInMetersPerSec", this.mPseudorangeRateInMetersPerSec, "PseudorangeRateUncertaintyInMetersPerSec", this.mPseudorangeRateUncertaintyInMetersPerSec));
        stringBuilder.append(String.format("   %-29s = %s\n", "PseudorangeRateIsCorrected", this.isPseudorangeRateCorrected()));
        stringBuilder.append(String.format("   %-29s = %s\n", "AccumulatedDeltaRangeState", this.getAccumulatedDeltaRangeStateString()));
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "AccumulatedDeltaRangeInMeters", this.mAccumulatedDeltaRangeInMeters, "AccumulatedDeltaRangeUncertaintyInMeters", this.mAccumulatedDeltaRangeUncertaintyInMeters));
        boolean bl = this.hasPseudorangeInMeters();
        Object var3_3 = null;
        Number number = bl ? Double.valueOf(this.mPseudorangeInMeters) : null;
        Double d = this.hasPseudorangeUncertaintyInMeters() ? Double.valueOf(this.mPseudorangeUncertaintyInMeters) : null;
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "PseudorangeInMeters", number, "PseudorangeUncertaintyInMeters", d));
        number = this.hasCodePhaseInChips() ? Double.valueOf(this.mCodePhaseInChips) : null;
        d = this.hasCodePhaseUncertaintyInChips() ? Double.valueOf(this.mCodePhaseUncertaintyInChips) : null;
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "CodePhaseInChips", number, "CodePhaseUncertaintyInChips", d));
        number = this.hasCarrierFrequencyInHz() ? Float.valueOf(this.mCarrierFrequencyInHz) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "CarrierFrequencyInHz", number));
        number = this.hasCarrierCycles() ? Long.valueOf(this.mCarrierCycles) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "CarrierCycles", number));
        number = this.hasCarrierPhase() ? Double.valueOf(this.mCarrierPhase) : null;
        d = this.hasCarrierPhaseUncertainty() ? Double.valueOf(this.mCarrierPhaseUncertainty) : null;
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "CarrierPhase", number, "CarrierPhaseUncertainty", d));
        stringBuilder.append(String.format("   %-29s = %s\n", "LossOfLock", this.getLossOfLockString()));
        number = this.hasBitNumber() ? Integer.valueOf(this.mBitNumber) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "BitNumber", number));
        number = this.hasTimeFromLastBitInMs() ? Short.valueOf(this.mTimeFromLastBitInMs) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "TimeFromLastBitInMs", number));
        number = this.hasDopplerShiftInHz() ? Double.valueOf(this.mDopplerShiftInHz) : null;
        d = this.hasDopplerShiftUncertaintyInHz() ? Double.valueOf(this.mDopplerShiftUncertaintyInHz) : null;
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "DopplerShiftInHz", number, "DopplerShiftUncertaintyInHz", d));
        stringBuilder.append(String.format("   %-29s = %s\n", "MultipathIndicator", this.getMultipathIndicatorString()));
        number = this.hasSnrInDb() ? Double.valueOf(this.mSnrInDb) : null;
        stringBuilder.append(String.format("   %-29s = %s\n", "SnrInDb", number));
        number = this.hasElevationInDeg() ? Double.valueOf(this.mElevationInDeg) : null;
        d = this.hasElevationUncertaintyInDeg() ? Double.valueOf(this.mElevationUncertaintyInDeg) : null;
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "ElevationInDeg", number, "ElevationUncertaintyInDeg", d));
        number = this.hasAzimuthInDeg() ? Double.valueOf(this.mAzimuthInDeg) : null;
        d = var3_3;
        if (this.hasAzimuthUncertaintyInDeg()) {
            d = this.mAzimuthUncertaintyInDeg;
        }
        stringBuilder.append(String.format("   %-29s = %-25s   %-40s = %s\n", "AzimuthInDeg", number, "AzimuthUncertaintyInDeg", d));
        stringBuilder.append(String.format("   %-29s = %s\n", "UsedInFix", this.mUsedInFix));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mFlags);
        parcel.writeByte(this.mPrn);
        parcel.writeDouble(this.mTimeOffsetInNs);
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mReceivedGpsTowInNs);
        parcel.writeLong(this.mReceivedGpsTowUncertaintyInNs);
        parcel.writeDouble(this.mCn0InDbHz);
        parcel.writeDouble(this.mPseudorangeRateInMetersPerSec);
        parcel.writeDouble(this.mPseudorangeRateUncertaintyInMetersPerSec);
        parcel.writeInt(this.mAccumulatedDeltaRangeState);
        parcel.writeDouble(this.mAccumulatedDeltaRangeInMeters);
        parcel.writeDouble(this.mAccumulatedDeltaRangeUncertaintyInMeters);
        parcel.writeDouble(this.mPseudorangeInMeters);
        parcel.writeDouble(this.mPseudorangeUncertaintyInMeters);
        parcel.writeDouble(this.mCodePhaseInChips);
        parcel.writeDouble(this.mCodePhaseUncertaintyInChips);
        parcel.writeFloat(this.mCarrierFrequencyInHz);
        parcel.writeLong(this.mCarrierCycles);
        parcel.writeDouble(this.mCarrierPhase);
        parcel.writeDouble(this.mCarrierPhaseUncertainty);
        parcel.writeByte(this.mLossOfLock);
        parcel.writeInt(this.mBitNumber);
        parcel.writeInt(this.mTimeFromLastBitInMs);
        parcel.writeDouble(this.mDopplerShiftInHz);
        parcel.writeDouble(this.mDopplerShiftUncertaintyInHz);
        parcel.writeByte(this.mMultipathIndicator);
        parcel.writeDouble(this.mSnrInDb);
        parcel.writeDouble(this.mElevationInDeg);
        parcel.writeDouble(this.mElevationUncertaintyInDeg);
        parcel.writeDouble(this.mAzimuthInDeg);
        parcel.writeDouble(this.mAzimuthUncertaintyInDeg);
        parcel.writeInt((int)this.mUsedInFix);
    }

}

