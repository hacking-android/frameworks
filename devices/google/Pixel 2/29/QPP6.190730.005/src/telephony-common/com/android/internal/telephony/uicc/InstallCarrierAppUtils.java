/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.Notification
 *  android.app.Notification$Action
 *  android.app.Notification$Action$Builder
 *  android.app.Notification$Builder
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Resources
 *  android.graphics.drawable.Icon
 *  android.net.Uri
 *  android.os.SystemClock
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.service.notification.StatusBarNotification
 *  android.text.TextUtils
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony.uicc;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.uicc.CarrierAppInstallReceiver;
import com.android.internal.telephony.uicc.ShowInstallAppNotificationReceiver;
import java.util.Arrays;
import java.util.Iterator;

@VisibleForTesting
public class InstallCarrierAppUtils {
    private static final int ACTIVATE_CELL_SERVICE_NOTIFICATION_ID = 12;
    private static CarrierAppInstallReceiver sCarrierAppInstallReceiver = null;

    static String getAppNameFromPackageName(Context context, String string) {
        return InstallCarrierAppUtils.getAppNameFromPackageName(string, Settings.Global.getString((ContentResolver)context.getContentResolver(), (String)"carrier_app_names"));
    }

    @VisibleForTesting
    public static String getAppNameFromPackageName(String string, String arrstring) {
        string = string.toLowerCase();
        if (TextUtils.isEmpty((CharSequence)arrstring)) {
            return null;
        }
        if ((arrstring = Arrays.asList(arrstring.split("\\s*;\\s*"))).isEmpty()) {
            return null;
        }
        Iterator iterator = arrstring.iterator();
        while (iterator.hasNext()) {
            arrstring = ((String)iterator.next()).split("\\s*:\\s*");
            if (arrstring.length != 2 || !arrstring[0].equals(string)) continue;
            return arrstring[1];
        }
        return null;
    }

    private static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager)context.getSystemService("notification");
    }

    static Intent getPlayStoreIntent(String string) {
        Intent intent = new Intent("android.intent.action.VIEW");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("market://details?id=");
        stringBuilder.append(string);
        intent.setData(Uri.parse((String)stringBuilder.toString()));
        intent.addFlags(268435456);
        return intent;
    }

    static void hideAllNotifications(Context arrstatusBarNotification) {
        NotificationManager notificationManager = InstallCarrierAppUtils.getNotificationManager((Context)arrstatusBarNotification);
        arrstatusBarNotification = notificationManager.getActiveNotifications();
        if (arrstatusBarNotification == null) {
            return;
        }
        for (StatusBarNotification statusBarNotification : arrstatusBarNotification) {
            if (statusBarNotification.getId() != 12) continue;
            notificationManager.cancel(statusBarNotification.getTag(), statusBarNotification.getId());
        }
    }

    static void hideNotification(Context context, String string) {
        InstallCarrierAppUtils.getNotificationManager(context).cancel(string, 12);
    }

    static boolean isPackageInstallNotificationActive(Context arrstatusBarNotification) {
        arrstatusBarNotification = InstallCarrierAppUtils.getNotificationManager((Context)arrstatusBarNotification).getActiveNotifications();
        int n = arrstatusBarNotification.length;
        for (int i = 0; i < n; ++i) {
            if (arrstatusBarNotification[i].getId() != 12) continue;
            return true;
        }
        return false;
    }

    static void registerPackageInstallReceiver(Context context) {
        if (sCarrierAppInstallReceiver == null) {
            sCarrierAppInstallReceiver = new CarrierAppInstallReceiver();
            context = context.getApplicationContext();
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addDataScheme("package");
            context.registerReceiver((BroadcastReceiver)sCarrierAppInstallReceiver, intentFilter);
        }
    }

    static void showNotification(Context context, String string) {
        Object object = Resources.getSystem();
        String string2 = object.getString(17040139);
        String string3 = InstallCarrierAppUtils.getAppNameFromPackageName(context, string);
        boolean bl = TextUtils.isEmpty((CharSequence)string3);
        boolean bl2 = true;
        string3 = bl ? object.getString(17040137) : object.getString(17040138, new Object[]{string3});
        object = object.getString(17040136);
        if (Settings.Global.getInt((ContentResolver)context.getContentResolver(), (String)"install_carrier_app_notification_persistent", (int)1) != 1) {
            bl2 = false;
        }
        object = new Notification.Action.Builder(null, (CharSequence)object, PendingIntent.getActivity((Context)context, (int)0, (Intent)InstallCarrierAppUtils.getPlayStoreIntent(string), (int)134217728)).build();
        string3 = new Notification.Builder(context, "sim").setContentTitle((CharSequence)string2).setContentText((CharSequence)string3).setSmallIcon(17302811).addAction((Notification.Action)object).setOngoing(bl2).setVisibility(-1).build();
        InstallCarrierAppUtils.getNotificationManager(context).notify(string, 12, (Notification)string3);
    }

    static void showNotificationIfNotInstalledDelayed(Context context, String string, long l) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService("alarm");
        context = PendingIntent.getBroadcast((Context)context, (int)0, (Intent)ShowInstallAppNotificationReceiver.get(context, string), (int)0);
        alarmManager.set(3, SystemClock.elapsedRealtime() + l, (PendingIntent)context);
    }

    static void unregisterPackageInstallReceiver(Context context) {
        if (sCarrierAppInstallReceiver == null) {
            return;
        }
        context.getApplicationContext().unregisterReceiver((BroadcastReceiver)sCarrierAppInstallReceiver);
        sCarrierAppInstallReceiver = null;
    }
}

