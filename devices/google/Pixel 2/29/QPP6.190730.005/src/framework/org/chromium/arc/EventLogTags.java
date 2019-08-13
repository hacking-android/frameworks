/*
 * Decompiled with CFR 0.145.
 */
package org.chromium.arc;

import android.util.EventLog;

public class EventLogTags {
    public static final int ARC_SYSTEM_EVENT = 300000;

    private EventLogTags() {
    }

    public static void writeArcSystemEvent(String string2) {
        EventLog.writeEvent(300000, string2);
    }
}

