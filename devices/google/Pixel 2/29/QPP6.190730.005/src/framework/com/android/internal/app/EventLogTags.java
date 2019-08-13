/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.util.EventLog;

public class EventLogTags {
    public static final int HARMFUL_APP_WARNING_LAUNCH_ANYWAY = 53001;
    public static final int HARMFUL_APP_WARNING_UNINSTALL = 53000;

    private EventLogTags() {
    }

    public static void writeHarmfulAppWarningLaunchAnyway(String string2) {
        EventLog.writeEvent(53001, string2);
    }

    public static void writeHarmfulAppWarningUninstall(String string2) {
        EventLog.writeEvent(53000, string2);
    }
}

