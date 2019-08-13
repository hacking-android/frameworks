/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;

public class ConsoleMessage {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private MessageLevel mLevel;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mLineNumber;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mMessage;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mSourceId;

    public ConsoleMessage(String string2, String string3, int n, MessageLevel messageLevel) {
        this.mMessage = string2;
        this.mSourceId = string3;
        this.mLineNumber = n;
        this.mLevel = messageLevel;
    }

    public int lineNumber() {
        return this.mLineNumber;
    }

    public String message() {
        return this.mMessage;
    }

    public MessageLevel messageLevel() {
        return this.mLevel;
    }

    public String sourceId() {
        return this.mSourceId;
    }

    public static enum MessageLevel {
        TIP,
        LOG,
        WARNING,
        ERROR,
        DEBUG;
        
    }

}

