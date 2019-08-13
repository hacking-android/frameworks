/*
 * Decompiled with CFR 0.145.
 */
package com.android.server;

import java.util.List;

public interface WidgetBackupProvider {
    public List<String> getWidgetParticipants(int var1);

    public byte[] getWidgetState(String var1, int var2);

    public void restoreFinished(int var1);

    public void restoreStarting(int var1);

    public void restoreWidgetState(String var1, byte[] var2, int var3);
}

