/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.util.EventLog;

public class EventLogTags {
    public static final int BINDER_SAMPLE = 52004;
    public static final int CONTENT_QUERY_SAMPLE = 52002;
    public static final int CONTENT_UPDATE_SAMPLE = 52003;

    private EventLogTags() {
    }

    public static void writeBinderSample(String string2, int n, int n2, String string3, int n3) {
        EventLog.writeEvent(52004, string2, n, n2, string3, n3);
    }

    public static void writeContentQuerySample(String string2, String string3, String string4, String string5, int n, String string6, int n2) {
        EventLog.writeEvent(52002, string2, string3, string4, string5, n, string6, n2);
    }

    public static void writeContentUpdateSample(String string2, String string3, String string4, int n, String string5, int n2) {
        EventLog.writeEvent(52003, string2, string3, string4, n, string5, n2);
    }
}

