/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.util.EventLog;

public class EventLogTags {
    public static final int NTP_FAILURE = 50081;
    public static final int NTP_SUCCESS = 50080;

    private EventLogTags() {
    }

    public static void writeNtpFailure(String string2, String string3) {
        EventLog.writeEvent(50081, string2, string3);
    }

    public static void writeNtpSuccess(String string2, long l, long l2) {
        EventLog.writeEvent(50080, string2, l, l2);
    }
}

