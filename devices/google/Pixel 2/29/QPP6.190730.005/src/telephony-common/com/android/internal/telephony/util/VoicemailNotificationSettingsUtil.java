/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.NotificationChannel
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.net.Uri
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$System
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 */
package com.android.internal.telephony.util;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.util.NotificationChannelController;

public class VoicemailNotificationSettingsUtil {
    private static final String OLD_VOICEMAIL_NOTIFICATION_RINGTONE_SHARED_PREFS_KEY = "button_voicemail_notification_ringtone_key";
    private static final String OLD_VOICEMAIL_NOTIFICATION_VIBRATION_SHARED_PREFS_KEY = "button_voicemail_notification_vibrate_key";
    private static final String OLD_VOICEMAIL_RINGTONE_SHARED_PREFS_KEY = "button_voicemail_notification_ringtone_key";
    private static final String OLD_VOICEMAIL_VIBRATE_WHEN_SHARED_PREFS_KEY = "button_voicemail_notification_vibrate_when_key";
    private static final String OLD_VOICEMAIL_VIBRATION_ALWAYS = "always";
    private static final String OLD_VOICEMAIL_VIBRATION_NEVER = "never";
    private static final String VOICEMAIL_NOTIFICATION_RINGTONE_SHARED_PREFS_KEY_PREFIX = "voicemail_notification_ringtone_";
    private static final String VOICEMAIL_NOTIFICATION_VIBRATION_SHARED_PREFS_KEY_PREFIX = "voicemail_notification_vibrate_";

    public static Uri getRingTonePreference(Context object) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)object);
        VoicemailNotificationSettingsUtil.migrateVoicemailRingtoneSettingsIfNeeded(object, sharedPreferences);
        object = sharedPreferences.getString(VoicemailNotificationSettingsUtil.getVoicemailRingtoneSharedPrefsKey(), Settings.System.DEFAULT_NOTIFICATION_URI.toString());
        object = !TextUtils.isEmpty((CharSequence)object) ? Uri.parse((String)object) : null;
        return object;
    }

    public static Uri getRingtoneUri(Context context) {
        NotificationChannel notificationChannel = NotificationChannelController.getChannel("voiceMail", context);
        context = notificationChannel != null ? notificationChannel.getSound() : VoicemailNotificationSettingsUtil.getRingTonePreference(context);
        return context;
    }

    public static boolean getVibrationPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)context);
        VoicemailNotificationSettingsUtil.migrateVoicemailVibrationSettingsIfNeeded(context, sharedPreferences);
        return sharedPreferences.getBoolean(VoicemailNotificationSettingsUtil.getVoicemailVibrationSharedPrefsKey(), false);
    }

    private static String getVoicemailRingtoneSharedPrefsKey() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(VOICEMAIL_NOTIFICATION_RINGTONE_SHARED_PREFS_KEY_PREFIX);
        stringBuilder.append(SubscriptionManager.getDefaultSubscriptionId());
        return stringBuilder.toString();
    }

    private static String getVoicemailVibrationSharedPrefsKey() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(VOICEMAIL_NOTIFICATION_VIBRATION_SHARED_PREFS_KEY_PREFIX);
        stringBuilder.append(SubscriptionManager.getDefaultSubscriptionId());
        return stringBuilder.toString();
    }

    public static boolean isVibrationEnabled(Context context) {
        NotificationChannel notificationChannel = NotificationChannelController.getChannel("voiceMail", context);
        boolean bl = notificationChannel != null ? notificationChannel.shouldVibrate() : VoicemailNotificationSettingsUtil.getVibrationPreference(context);
        return bl;
    }

    private static void migrateVoicemailRingtoneSettingsIfNeeded(Context object, SharedPreferences sharedPreferences) {
        String string = VoicemailNotificationSettingsUtil.getVoicemailRingtoneSharedPrefsKey();
        object = TelephonyManager.from((Context)object);
        if (!sharedPreferences.contains(string) && object.getPhoneCount() == 1) {
            if (sharedPreferences.contains("button_voicemail_notification_ringtone_key")) {
                object = sharedPreferences.getString("button_voicemail_notification_ringtone_key", null);
                sharedPreferences.edit().putString(string, (String)object).remove("button_voicemail_notification_ringtone_key").commit();
            }
            return;
        }
    }

    private static void migrateVoicemailVibrationSettingsIfNeeded(Context context, SharedPreferences sharedPreferences) {
        String string = VoicemailNotificationSettingsUtil.getVoicemailVibrationSharedPrefsKey();
        context = TelephonyManager.from((Context)context);
        if (!sharedPreferences.contains(string) && context.getPhoneCount() == 1) {
            boolean bl;
            if (sharedPreferences.contains(OLD_VOICEMAIL_NOTIFICATION_VIBRATION_SHARED_PREFS_KEY)) {
                bl = sharedPreferences.getBoolean(OLD_VOICEMAIL_NOTIFICATION_VIBRATION_SHARED_PREFS_KEY, false);
                sharedPreferences.edit().putBoolean(string, bl).remove(OLD_VOICEMAIL_VIBRATE_WHEN_SHARED_PREFS_KEY).commit();
            }
            if (sharedPreferences.contains(OLD_VOICEMAIL_VIBRATE_WHEN_SHARED_PREFS_KEY)) {
                bl = sharedPreferences.getString(OLD_VOICEMAIL_VIBRATE_WHEN_SHARED_PREFS_KEY, OLD_VOICEMAIL_VIBRATION_NEVER).equals(OLD_VOICEMAIL_VIBRATION_ALWAYS);
                sharedPreferences.edit().putBoolean(string, bl).remove(OLD_VOICEMAIL_NOTIFICATION_VIBRATION_SHARED_PREFS_KEY).commit();
            }
            return;
        }
    }

    public static void setRingtoneUri(Context object, Uri uri) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)object);
        object = uri != null ? uri.toString() : "";
        uri = sharedPreferences.edit();
        uri.putString(VoicemailNotificationSettingsUtil.getVoicemailRingtoneSharedPrefsKey(), (String)object);
        uri.commit();
    }

    public static void setVibrationEnabled(Context context, boolean bl) {
        context = PreferenceManager.getDefaultSharedPreferences((Context)context).edit();
        context.putBoolean(VoicemailNotificationSettingsUtil.getVoicemailVibrationSharedPrefsKey(), bl);
        context.commit();
    }
}

