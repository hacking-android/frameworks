/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.util.EventLog;

public class EventLogTags {
    public static final int BROWSER_DOUBLE_TAP_DURATION = 70102;
    public static final int BROWSER_SNAP_CENTER = 70150;
    public static final int BROWSER_ZOOM_LEVEL_CHANGE = 70101;
    public static final int EXP_DET_ATTEMPT_TO_CALL_OBJECT_GETCLASS = 70151;

    private EventLogTags() {
    }

    public static void writeBrowserDoubleTapDuration(int n, long l) {
        EventLog.writeEvent(70102, n, l);
    }

    public static void writeBrowserSnapCenter() {
        EventLog.writeEvent(70150, new Object[0]);
    }

    public static void writeBrowserZoomLevelChange(int n, int n2, long l) {
        EventLog.writeEvent(70101, n, n2, l);
    }

    public static void writeExpDetAttemptToCallObjectGetclass(String string2) {
        EventLog.writeEvent(70151, string2);
    }
}

