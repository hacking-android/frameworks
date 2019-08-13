/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.SimpleClock;
import android.util.Log;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Arrays;

public class BestClock
extends SimpleClock {
    private static final String TAG = "BestClock";
    private final Clock[] clocks;

    public BestClock(ZoneId zoneId, Clock ... arrclock) {
        super(zoneId);
        this.clocks = arrclock;
    }

    @Override
    public long millis() {
        for (Clock clock : this.clocks) {
            try {
                long l = clock.millis();
                return l;
            }
            catch (DateTimeException dateTimeException) {
                Log.w(TAG, dateTimeException.toString());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No clocks in ");
        stringBuilder.append(Arrays.toString(this.clocks));
        stringBuilder.append(" were able to provide time");
        throw new DateTimeException(stringBuilder.toString());
    }
}

