/*
 * Decompiled with CFR 0.145.
 */
package com.android.server;

import android.util.EventLog;

public class DropboxLogTags {
    public static final int DROPBOX_FILE_COPY = 81002;

    private DropboxLogTags() {
    }

    public static void writeDropboxFileCopy(String string2, int n, String string3) {
        EventLog.writeEvent(81002, string2, n, string3);
    }
}

