/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiRecordSources;

@SystemApi
public abstract class HdmiRecordListener {
    public void onClearTimerRecordingResult(int n, int n2) {
    }

    public void onOneTouchRecordResult(int n, int n2) {
    }

    public abstract HdmiRecordSources.RecordSource onOneTouchRecordSourceRequested(int var1);

    public void onTimerRecordingResult(int n, TimerStatusData timerStatusData) {
    }

    @SystemApi
    public static class TimerStatusData {
        private int mDurationHour;
        private int mDurationMinute;
        private int mExtraError;
        private int mMediaInfo;
        private int mNotProgrammedError;
        private boolean mOverlapped;
        private boolean mProgrammed;
        private int mProgrammedInfo;

        private TimerStatusData() {
        }

        private static int bcdByteToInt(byte by) {
            return (by >> 4 & 15) * 10 + by & 15;
        }

        static TimerStatusData parseFrom(int n) {
            TimerStatusData timerStatusData = new TimerStatusData();
            boolean bl = true;
            boolean bl2 = (n >> 31 & 1) != 0;
            timerStatusData.mOverlapped = bl2;
            timerStatusData.mMediaInfo = n >> 29 & 3;
            bl2 = (n >> 28 & 1) != 0 ? bl : false;
            timerStatusData.mProgrammed = bl2;
            if (timerStatusData.mProgrammed) {
                timerStatusData.mProgrammedInfo = n >> 24 & 15;
                timerStatusData.mDurationHour = TimerStatusData.bcdByteToInt((byte)(n >> 16 & 255));
                timerStatusData.mDurationMinute = TimerStatusData.bcdByteToInt((byte)(n >> 8 & 255));
            } else {
                timerStatusData.mNotProgrammedError = n >> 24 & 15;
                timerStatusData.mDurationHour = TimerStatusData.bcdByteToInt((byte)(n >> 16 & 255));
                timerStatusData.mDurationMinute = TimerStatusData.bcdByteToInt((byte)(n >> 8 & 255));
            }
            timerStatusData.mExtraError = n & 255;
            return timerStatusData;
        }

        public int getDurationHour() {
            return this.mDurationHour;
        }

        public int getDurationMinute() {
            return this.mDurationMinute;
        }

        public int getExtraError() {
            return this.mExtraError;
        }

        public int getMediaInfo() {
            return this.mMediaInfo;
        }

        public int getNotProgammedError() {
            if (!this.isProgrammed()) {
                return this.mNotProgrammedError;
            }
            throw new IllegalStateException("Has no not-programmed error. Call getProgrammedInfo() instead.");
        }

        public int getProgrammedInfo() {
            if (this.isProgrammed()) {
                return this.mProgrammedInfo;
            }
            throw new IllegalStateException("No programmed info. Call getNotProgammedError() instead.");
        }

        public boolean isOverlapped() {
            return this.mOverlapped;
        }

        public boolean isProgrammed() {
            return this.mProgrammed;
        }
    }

}

