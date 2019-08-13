/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.app.Notification;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import java.util.Objects;

public class NotificationMessagingUtil {
    private static final String DEFAULT_SMS_APP_SETTING = "sms_default_application";
    private final Context mContext;
    private ArrayMap<Integer, String> mDefaultSmsApp = new ArrayMap();
    private final ContentObserver mSmsContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())){

        @Override
        public void onChange(boolean bl, Uri uri, int n) {
            if (Settings.Secure.getUriFor(NotificationMessagingUtil.DEFAULT_SMS_APP_SETTING).equals(uri)) {
                NotificationMessagingUtil.this.cacheDefaultSmsApp(n);
            }
        }
    };

    public NotificationMessagingUtil(Context context) {
        this.mContext = context;
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(DEFAULT_SMS_APP_SETTING), false, this.mSmsContentObserver);
    }

    private void cacheDefaultSmsApp(int n) {
        this.mDefaultSmsApp.put(n, Settings.Secure.getStringForUser(this.mContext.getContentResolver(), DEFAULT_SMS_APP_SETTING, n));
    }

    private boolean hasMessagingStyle(StatusBarNotification statusBarNotification) {
        return Notification.MessagingStyle.class.equals(statusBarNotification.getNotification().getNotificationStyle());
    }

    private boolean isCategoryMessage(StatusBarNotification statusBarNotification) {
        return "msg".equals(statusBarNotification.getNotification().category);
    }

    private boolean isDefaultMessagingApp(StatusBarNotification statusBarNotification) {
        int n = statusBarNotification.getUserId();
        if (n != -10000 && n != -1) {
            if (this.mDefaultSmsApp.get(n) == null) {
                this.cacheDefaultSmsApp(n);
            }
            return Objects.equals(this.mDefaultSmsApp.get(n), statusBarNotification.getPackageName());
        }
        return false;
    }

    public boolean isImportantMessaging(StatusBarNotification statusBarNotification, int n) {
        boolean bl;
        block5 : {
            block4 : {
                boolean bl2 = false;
                if (n < 2) {
                    return false;
                }
                if (this.hasMessagingStyle(statusBarNotification)) break block4;
                bl = bl2;
                if (!this.isCategoryMessage(statusBarNotification)) break block5;
                bl = bl2;
                if (!this.isDefaultMessagingApp(statusBarNotification)) break block5;
            }
            bl = true;
        }
        return bl;
    }

    public boolean isMessaging(StatusBarNotification statusBarNotification) {
        boolean bl = this.hasMessagingStyle(statusBarNotification) || this.isDefaultMessagingApp(statusBarNotification) || this.isCategoryMessage(statusBarNotification);
        return bl;
    }

}

