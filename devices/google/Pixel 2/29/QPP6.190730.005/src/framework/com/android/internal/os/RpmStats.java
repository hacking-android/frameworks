/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.util.ArrayMap;
import java.util.Map;

public final class RpmStats {
    public Map<String, PowerStatePlatformSleepState> mPlatformLowPowerStats = new ArrayMap<String, PowerStatePlatformSleepState>();
    public Map<String, PowerStateSubsystem> mSubsystemLowPowerStats = new ArrayMap<String, PowerStateSubsystem>();

    public PowerStatePlatformSleepState getAndUpdatePlatformState(String string2, long l, int n) {
        PowerStatePlatformSleepState powerStatePlatformSleepState;
        PowerStatePlatformSleepState powerStatePlatformSleepState2 = powerStatePlatformSleepState = this.mPlatformLowPowerStats.get(string2);
        if (powerStatePlatformSleepState == null) {
            powerStatePlatformSleepState2 = new PowerStatePlatformSleepState();
            this.mPlatformLowPowerStats.put(string2, powerStatePlatformSleepState2);
        }
        powerStatePlatformSleepState2.mTimeMs = l;
        powerStatePlatformSleepState2.mCount = n;
        return powerStatePlatformSleepState2;
    }

    public PowerStateSubsystem getSubsystem(String string2) {
        PowerStateSubsystem powerStateSubsystem;
        PowerStateSubsystem powerStateSubsystem2 = powerStateSubsystem = this.mSubsystemLowPowerStats.get(string2);
        if (powerStateSubsystem == null) {
            powerStateSubsystem2 = new PowerStateSubsystem();
            this.mSubsystemLowPowerStats.put(string2, powerStateSubsystem2);
        }
        return powerStateSubsystem2;
    }

    public static class PowerStateElement {
        public int mCount;
        public long mTimeMs;

        private PowerStateElement(long l, int n) {
            this.mTimeMs = l;
            this.mCount = n;
        }
    }

    public static class PowerStatePlatformSleepState {
        public int mCount;
        public long mTimeMs;
        public Map<String, PowerStateElement> mVoters = new ArrayMap<String, PowerStateElement>();

        public void putVoter(String string2, long l, int n) {
            PowerStateElement powerStateElement = this.mVoters.get(string2);
            if (powerStateElement == null) {
                this.mVoters.put(string2, new PowerStateElement(l, n));
            } else {
                powerStateElement.mTimeMs = l;
                powerStateElement.mCount = n;
            }
        }
    }

    public static class PowerStateSubsystem {
        public Map<String, PowerStateElement> mStates = new ArrayMap<String, PowerStateElement>();

        public void putState(String string2, long l, int n) {
            PowerStateElement powerStateElement = this.mStates.get(string2);
            if (powerStateElement == null) {
                this.mStates.put(string2, new PowerStateElement(l, n));
            } else {
                powerStateElement.mTimeMs = l;
                powerStateElement.mCount = n;
            }
        }
    }

}

