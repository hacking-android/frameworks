/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.media.AudioAttributes
 *  android.media.AudioAttributes$Builder
 *  android.net.Uri
 *  android.provider.Settings
 *  android.provider.Settings$System
 *  android.telephony.SubscriptionManager
 */
package com.android.internal.telephony.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import com.android.internal.telephony.util.VoicemailNotificationSettingsUtil;
import java.util.Arrays;
import java.util.List;

public class NotificationChannelController {
    public static final String CHANNEL_ID_ALERT = "alert";
    public static final String CHANNEL_ID_CALL_FORWARD = "callForwardNew";
    private static final String CHANNEL_ID_CALL_FORWARD_DEPRECATED = "callForward";
    private static final String CHANNEL_ID_MOBILE_DATA_ALERT_DEPRECATED = "mobileDataAlert";
    public static final String CHANNEL_ID_MOBILE_DATA_STATUS = "mobileDataAlertNew";
    public static final String CHANNEL_ID_SIM = "sim";
    public static final String CHANNEL_ID_SMS = "sms";
    public static final String CHANNEL_ID_VOICE_MAIL = "voiceMail";
    public static final String CHANNEL_ID_WFC = "wfc";
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                NotificationChannelController.createAll(context);
            } else if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction()) && -1 != SubscriptionManager.getDefaultSubscriptionId()) {
                NotificationChannelController.migrateVoicemailNotificationSettings(context);
            }
        }
    };

    public NotificationChannelController(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        context.registerReceiver(this.mBroadcastReceiver, intentFilter);
        NotificationChannelController.createAll(context);
    }

    private static void createAll(Context context) {
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID_ALERT, context.getText(17040493), 3);
        notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, new AudioAttributes.Builder().setUsage(5).build());
        notificationChannel.setBlockableSystem(true);
        NotificationChannel notificationChannel2 = new NotificationChannel(CHANNEL_ID_MOBILE_DATA_STATUS, context.getText(17040492), 2);
        notificationChannel2.setBlockableSystem(true);
        NotificationChannel notificationChannel3 = new NotificationChannel(CHANNEL_ID_SIM, context.getText(17040500), 2);
        notificationChannel3.setSound(null, null);
        NotificationChannel notificationChannel4 = new NotificationChannel(CHANNEL_ID_CALL_FORWARD, context.getText(17040484), 3);
        NotificationChannelController.migrateCallFowardNotificationChannel(context, notificationChannel4);
        ((NotificationManager)context.getSystemService(NotificationManager.class)).createNotificationChannels(Arrays.asList(new NotificationChannel[]{new NotificationChannel(CHANNEL_ID_SMS, context.getText(17040501), 4), new NotificationChannel(CHANNEL_ID_WFC, context.getText(17040508), 2), notificationChannel, notificationChannel2, notificationChannel3, notificationChannel4}));
        if (NotificationChannelController.getChannel(CHANNEL_ID_VOICE_MAIL, context) != null) {
            NotificationChannelController.migrateVoicemailNotificationSettings(context);
        }
        if (NotificationChannelController.getChannel(CHANNEL_ID_MOBILE_DATA_ALERT_DEPRECATED, context) != null) {
            ((NotificationManager)context.getSystemService(NotificationManager.class)).deleteNotificationChannel(CHANNEL_ID_MOBILE_DATA_ALERT_DEPRECATED);
        }
        if (NotificationChannelController.getChannel(CHANNEL_ID_CALL_FORWARD_DEPRECATED, context) != null) {
            ((NotificationManager)context.getSystemService(NotificationManager.class)).deleteNotificationChannel(CHANNEL_ID_CALL_FORWARD_DEPRECATED);
        }
    }

    public static NotificationChannel getChannel(String string, Context context) {
        return ((NotificationManager)context.getSystemService(NotificationManager.class)).getNotificationChannel(string);
    }

    private static void migrateCallFowardNotificationChannel(Context context, NotificationChannel notificationChannel) {
        if ((context = NotificationChannelController.getChannel(CHANNEL_ID_CALL_FORWARD_DEPRECATED, context)) != null) {
            notificationChannel.setSound(context.getSound(), context.getAudioAttributes());
            notificationChannel.setVibrationPattern(context.getVibrationPattern());
            notificationChannel.enableVibration(context.shouldVibrate());
        }
    }

    private static void migrateVoicemailNotificationSettings(Context context) {
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID_VOICE_MAIL, context.getText(17040506), 3);
        notificationChannel.enableVibration(VoicemailNotificationSettingsUtil.getVibrationPreference(context));
        Uri uri = VoicemailNotificationSettingsUtil.getRingTonePreference(context);
        if (uri == null) {
            uri = Settings.System.DEFAULT_NOTIFICATION_URI;
        }
        notificationChannel.setSound(uri, new AudioAttributes.Builder().setUsage(5).build());
        ((NotificationManager)context.getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
    }

}

