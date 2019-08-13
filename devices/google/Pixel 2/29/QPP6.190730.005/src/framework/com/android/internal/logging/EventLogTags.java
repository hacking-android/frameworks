/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.logging;

import android.util.EventLog;

public class EventLogTags {
    public static final int COMMIT_SYS_CONFIG_FILE = 525000;
    public static final int SYSUI_ACTION = 524288;
    public static final int SYSUI_COUNT = 524290;
    public static final int SYSUI_HISTOGRAM = 524291;
    public static final int SYSUI_LATENCY = 36070;
    public static final int SYSUI_MULTI_ACTION = 524292;
    public static final int SYSUI_VIEW_VISIBILITY = 524287;

    private EventLogTags() {
    }

    public static void writeCommitSysConfigFile(String string2, long l) {
        EventLog.writeEvent(525000, string2, l);
    }

    public static void writeSysuiAction(int n, String string2) {
        EventLog.writeEvent(524288, n, string2);
    }

    public static void writeSysuiCount(String string2, int n) {
        EventLog.writeEvent(524290, string2, n);
    }

    public static void writeSysuiHistogram(String string2, int n) {
        EventLog.writeEvent(524291, string2, n);
    }

    public static void writeSysuiLatency(int n, int n2) {
        EventLog.writeEvent(36070, n, n2);
    }

    public static void writeSysuiMultiAction(Object[] arrobject) {
        EventLog.writeEvent(524292, arrobject);
    }

    public static void writeSysuiViewVisibility(int n, int n2) {
        EventLog.writeEvent(524287, n, n2);
    }
}

