/*
 * Decompiled with CFR 0.145.
 */
package com.android.server;

import com.android.server.WidgetBackupProvider;
import java.util.List;

public class AppWidgetBackupBridge {
    private static WidgetBackupProvider sAppWidgetService;

    public static List<String> getWidgetParticipants(int n) {
        Object object = sAppWidgetService;
        object = object != null ? object.getWidgetParticipants(n) : null;
        return object;
    }

    public static byte[] getWidgetState(String object, int n) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        object = widgetBackupProvider != null ? widgetBackupProvider.getWidgetState((String)object, n) : null;
        return object;
    }

    public static void register(WidgetBackupProvider widgetBackupProvider) {
        sAppWidgetService = widgetBackupProvider;
    }

    public static void restoreFinished(int n) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        if (widgetBackupProvider != null) {
            widgetBackupProvider.restoreFinished(n);
        }
    }

    public static void restoreStarting(int n) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        if (widgetBackupProvider != null) {
            widgetBackupProvider.restoreStarting(n);
        }
    }

    public static void restoreWidgetState(String string2, byte[] arrby, int n) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        if (widgetBackupProvider != null) {
            widgetBackupProvider.restoreWidgetState(string2, arrby, n);
        }
    }
}

