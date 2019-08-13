/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.util.EventLog;

public class EventLogTags {
    public static final int SERVICE_MANAGER_SLOW = 230001;
    public static final int SERVICE_MANAGER_STATS = 230000;

    private EventLogTags() {
    }

    public static void writeServiceManagerSlow(int n, String string2) {
        EventLog.writeEvent(230001, n, string2);
    }

    public static void writeServiceManagerStats(int n, int n2, int n3) {
        EventLog.writeEvent(230000, n, n2, n3);
    }
}

