/*
 * Decompiled with CFR 0.145.
 */
package android.appwidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;

public class AppWidgetProvider
extends BroadcastReceiver {
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int n, Bundle bundle) {
    }

    public void onDeleted(Context context, int[] arrn) {
    }

    public void onDisabled(Context context) {
    }

    public void onEnabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent parcelable) {
        Object object = ((Intent)parcelable).getAction();
        if ("android.appwidget.action.APPWIDGET_UPDATE".equals(object)) {
            if ((parcelable = ((Intent)parcelable).getExtras()) != null && (parcelable = ((BaseBundle)((Object)parcelable)).getIntArray("appWidgetIds")) != null && ((Parcelable)parcelable).length > 0) {
                this.onUpdate(context, AppWidgetManager.getInstance(context), (int[])parcelable);
            }
        } else if ("android.appwidget.action.APPWIDGET_DELETED".equals(object)) {
            if ((parcelable = ((Intent)parcelable).getExtras()) != null && ((BaseBundle)((Object)parcelable)).containsKey("appWidgetId")) {
                this.onDeleted(context, new int[]{((BaseBundle)((Object)parcelable)).getInt("appWidgetId")});
            }
        } else if ("android.appwidget.action.APPWIDGET_UPDATE_OPTIONS".equals(object)) {
            if ((parcelable = ((Intent)parcelable).getExtras()) != null && ((BaseBundle)((Object)parcelable)).containsKey("appWidgetId") && ((BaseBundle)((Object)parcelable)).containsKey("appWidgetOptions")) {
                int n = ((BaseBundle)((Object)parcelable)).getInt("appWidgetId");
                parcelable = ((Bundle)parcelable).getBundle("appWidgetOptions");
                this.onAppWidgetOptionsChanged(context, AppWidgetManager.getInstance(context), n, (Bundle)parcelable);
            }
        } else if ("android.appwidget.action.APPWIDGET_ENABLED".equals(object)) {
            this.onEnabled(context);
        } else if ("android.appwidget.action.APPWIDGET_DISABLED".equals(object)) {
            this.onDisabled(context);
        } else if ("android.appwidget.action.APPWIDGET_RESTORED".equals(object) && (object = ((Intent)parcelable).getExtras()) != null) {
            parcelable = object.getIntArray("appWidgetOldIds");
            object = object.getIntArray("appWidgetIds");
            if (parcelable != null && ((Parcelable)parcelable).length > 0) {
                this.onRestored(context, (int[])parcelable, (int[])object);
                this.onUpdate(context, AppWidgetManager.getInstance(context), (int[])object);
            }
        }
    }

    public void onRestored(Context context, int[] arrn, int[] arrn2) {
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] arrn) {
    }
}

