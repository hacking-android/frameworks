/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class MediaPowerCalculator
extends PowerCalculator {
    private static final int MS_IN_HR = 3600000;
    private final double mAudioAveragePowerMa;
    private final double mVideoAveragePowerMa;

    public MediaPowerCalculator(PowerProfile powerProfile) {
        this.mAudioAveragePowerMa = powerProfile.getAveragePower("audio");
        this.mVideoAveragePowerMa = powerProfile.getAveragePower("video");
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid object, long l, long l2, int n) {
        BatteryStats.Timer timer = ((BatteryStats.Uid)object).getAudioTurnedOnTimer();
        if (timer == null) {
            batterySipper.audioTimeMs = 0L;
            batterySipper.audioPowerMah = 0.0;
        } else {
            batterySipper.audioTimeMs = l2 = timer.getTotalTimeLocked(l, n) / 1000L;
            batterySipper.audioPowerMah = (double)l2 * this.mAudioAveragePowerMa / 3600000.0;
        }
        object = ((BatteryStats.Uid)object).getVideoTurnedOnTimer();
        if (object == null) {
            batterySipper.videoTimeMs = 0L;
            batterySipper.videoPowerMah = 0.0;
        } else {
            batterySipper.videoTimeMs = l = ((BatteryStats.Timer)object).getTotalTimeLocked(l, n) / 1000L;
            batterySipper.videoPowerMah = (double)l * this.mVideoAveragePowerMa / 3600000.0;
        }
    }
}

