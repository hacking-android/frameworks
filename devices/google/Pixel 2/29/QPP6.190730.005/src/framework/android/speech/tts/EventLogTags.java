/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.util.EventLog;

public class EventLogTags {
    public static final int TTS_SPEAK_FAILURE = 76002;
    public static final int TTS_SPEAK_SUCCESS = 76001;
    public static final int TTS_V2_SPEAK_FAILURE = 76004;
    public static final int TTS_V2_SPEAK_SUCCESS = 76003;

    private EventLogTags() {
    }

    public static void writeTtsSpeakFailure(String string2, int n, int n2, int n3, String string3, int n4, int n5) {
        EventLog.writeEvent(76002, string2, n, n2, n3, string3, n4, n5);
    }

    public static void writeTtsSpeakSuccess(String string2, int n, int n2, int n3, String string3, int n4, int n5, long l, long l2, long l3) {
        EventLog.writeEvent(76001, string2, n, n2, n3, string3, n4, n5, l, l2, l3);
    }

    public static void writeTtsV2SpeakFailure(String string2, int n, int n2, int n3, String string3, int n4) {
        EventLog.writeEvent(76004, string2, n, n2, n3, string3, n4);
    }

    public static void writeTtsV2SpeakSuccess(String string2, int n, int n2, int n3, String string3, long l, long l2, long l3) {
        EventLog.writeEvent(76003, string2, n, n2, n3, string3, l, l2, l3);
    }
}

