/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.notification;

import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.Settings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemNotificationChannels {
    public static String ACCOUNT;
    public static String ALERTS;
    public static String CAR_MODE;
    public static String DEVELOPER;
    public static String DEVICE_ADMIN;
    @Deprecated
    public static String DEVICE_ADMIN_DEPRECATED;
    public static String DO_NOT_DISTURB;
    public static String FOREGROUND_SERVICE;
    public static String HEAVY_WEIGHT_APP;
    public static String NETWORK_ALERTS;
    public static String NETWORK_AVAILABLE;
    public static String NETWORK_STATUS;
    public static String PHYSICAL_KEYBOARD;
    public static String RETAIL_MODE;
    public static String SECURITY;
    public static String SYSTEM_CHANGES;
    public static String UPDATES;
    public static String USB;
    public static String VIRTUAL_KEYBOARD;
    public static String VPN;

    static {
        VIRTUAL_KEYBOARD = "VIRTUAL_KEYBOARD";
        PHYSICAL_KEYBOARD = "PHYSICAL_KEYBOARD";
        SECURITY = "SECURITY";
        CAR_MODE = "CAR_MODE";
        ACCOUNT = "ACCOUNT";
        DEVELOPER = "DEVELOPER";
        UPDATES = "UPDATES";
        NETWORK_STATUS = "NETWORK_STATUS";
        NETWORK_ALERTS = "NETWORK_ALERTS";
        NETWORK_AVAILABLE = "NETWORK_AVAILABLE";
        VPN = "VPN";
        DEVICE_ADMIN_DEPRECATED = "DEVICE_ADMIN";
        DEVICE_ADMIN = "DEVICE_ADMIN_ALERTS";
        ALERTS = "ALERTS";
        RETAIL_MODE = "RETAIL_MODE";
        USB = "USB";
        FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
        HEAVY_WEIGHT_APP = "HEAVY_WEIGHT_APP";
        SYSTEM_CHANGES = "SYSTEM_CHANGES";
        DO_NOT_DISTURB = "DO_NOT_DISTURB";
    }

    private SystemNotificationChannels() {
    }

    public static void createAccountChannelForPackage(String string2, int n, Context context) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            ParceledListSlice<NotificationChannel> parceledListSlice = new ParceledListSlice<NotificationChannel>(Arrays.asList(SystemNotificationChannels.newAccountChannel(context)));
            iNotificationManager.createNotificationChannelsForPackage(string2, n, parceledListSlice);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void createAll(Context context) {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        ArrayList<NotificationChannel> arrayList = new ArrayList<NotificationChannel>();
        NotificationChannel notificationChannel = new NotificationChannel(VIRTUAL_KEYBOARD, context.getString(17040505), 2);
        notificationChannel.setBlockableSystem(true);
        arrayList.add(notificationChannel);
        notificationChannel = new NotificationChannel(PHYSICAL_KEYBOARD, context.getString(17040497), 3);
        notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, Notification.AUDIO_ATTRIBUTES_DEFAULT);
        notificationChannel.setBlockableSystem(true);
        arrayList.add(notificationChannel);
        arrayList.add(new NotificationChannel(SECURITY, context.getString(17040499), 2));
        notificationChannel = new NotificationChannel(CAR_MODE, context.getString(17040485), 2);
        notificationChannel.setBlockableSystem(true);
        arrayList.add(notificationChannel);
        arrayList.add(SystemNotificationChannels.newAccountChannel(context));
        notificationChannel = new NotificationChannel(DEVELOPER, context.getString(17040486), 2);
        notificationChannel.setBlockableSystem(true);
        arrayList.add(notificationChannel);
        arrayList.add(new NotificationChannel(UPDATES, context.getString(17040503), 2));
        arrayList.add(new NotificationChannel(NETWORK_STATUS, context.getString(17040496), 2));
        notificationChannel = new NotificationChannel(NETWORK_ALERTS, context.getString(17040494), 4);
        notificationChannel.setBlockableSystem(true);
        arrayList.add(notificationChannel);
        notificationChannel = new NotificationChannel(NETWORK_AVAILABLE, context.getString(17040495), 2);
        notificationChannel.setBlockableSystem(true);
        arrayList.add(notificationChannel);
        arrayList.add(new NotificationChannel(VPN, context.getString(17040507), 2));
        arrayList.add(new NotificationChannel(DEVICE_ADMIN, context.getString(17040487), 4));
        arrayList.add(new NotificationChannel(ALERTS, context.getString(17040483), 3));
        arrayList.add(new NotificationChannel(RETAIL_MODE, context.getString(17040498), 2));
        arrayList.add(new NotificationChannel(USB, context.getString(17040504), 1));
        notificationChannel = new NotificationChannel(FOREGROUND_SERVICE, context.getString(17040490), 2);
        notificationChannel.setBlockableSystem(true);
        arrayList.add(notificationChannel);
        notificationChannel = new NotificationChannel(HEAVY_WEIGHT_APP, context.getString(17040491), 3);
        notificationChannel.setShowBadge(false);
        notificationChannel.setSound(null, new AudioAttributes.Builder().setContentType(4).setUsage(10).build());
        arrayList.add(notificationChannel);
        arrayList.add(new NotificationChannel(SYSTEM_CHANGES, context.getString(17040502), 2));
        arrayList.add(new NotificationChannel(DO_NOT_DISTURB, context.getString(17040488), 2));
        notificationManager.createNotificationChannels(arrayList);
    }

    private static NotificationChannel newAccountChannel(Context context) {
        return new NotificationChannel(ACCOUNT, context.getString(17040482), 2);
    }

    public static void removeDeprecated(Context context) {
        context.getSystemService(NotificationManager.class).deleteNotificationChannel(DEVICE_ADMIN_DEPRECATED);
    }
}

