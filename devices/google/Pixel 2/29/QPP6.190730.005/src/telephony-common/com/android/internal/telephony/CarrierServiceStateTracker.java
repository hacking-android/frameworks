/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$Style
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Resources
 *  android.database.ContentObserver
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SubscriptionManager
 *  android.telephony.SubscriptionManager$OnSubscriptionsChangedListener
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.ServiceStateTracker;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CarrierServiceStateTracker
extends Handler {
    protected static final int CARRIER_EVENT_BASE = 100;
    protected static final int CARRIER_EVENT_DATA_DEREGISTRATION = 104;
    protected static final int CARRIER_EVENT_DATA_REGISTRATION = 103;
    protected static final int CARRIER_EVENT_IMS_CAPABILITIES_CHANGED = 105;
    protected static final int CARRIER_EVENT_VOICE_DEREGISTRATION = 102;
    protected static final int CARRIER_EVENT_VOICE_REGISTRATION = 101;
    private static final String LOG_TAG = "CSST";
    public static final int NOTIFICATION_EMERGENCY_NETWORK = 1001;
    public static final int NOTIFICATION_PREF_NETWORK = 1000;
    private static final int UNINITIALIZED_DELAY_VALUE = -1;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            intent = ((CarrierConfigManager)object.getSystemService("carrier_config")).getConfigForSubId(CarrierServiceStateTracker.this.mPhone.getSubId());
            object = CarrierServiceStateTracker.this.mNotificationTypeMap.entrySet().iterator();
            while (object.hasNext()) {
                ((NotificationType)((Map.Entry)object.next()).getValue()).setDelay((PersistableBundle)intent);
            }
            CarrierServiceStateTracker.this.handleConfigChanges();
        }
    };
    private final Map<Integer, NotificationType> mNotificationTypeMap = new HashMap<Integer, NotificationType>();
    private Phone mPhone;
    private ContentObserver mPrefNetworkModeObserver = new ContentObserver(this){

        public void onChange(boolean bl) {
            CarrierServiceStateTracker.this.handlePrefNetworkModeChanged();
        }
    };
    private int mPreviousSubId = -1;
    private ServiceStateTracker mSST;

    public CarrierServiceStateTracker(Phone phone, ServiceStateTracker serviceStateTracker) {
        this.mPhone = phone;
        this.mSST = serviceStateTracker;
        phone.getContext().registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"));
        SubscriptionManager.from((Context)this.mPhone.getContext()).addOnSubscriptionsChangedListener(new SubscriptionManager.OnSubscriptionsChangedListener(this.getLooper()){

            public void onSubscriptionsChanged() {
                int n = CarrierServiceStateTracker.this.mPhone.getSubId();
                if (CarrierServiceStateTracker.this.mPreviousSubId != n) {
                    CarrierServiceStateTracker.this.mPreviousSubId = n;
                    CarrierServiceStateTracker.this.registerPrefNetworkModeObserver();
                }
            }
        });
        this.registerNotificationTypes();
        this.registerPrefNetworkModeObserver();
    }

    private void evaluateSendingMessageOrCancelNotification(NotificationType notificationType) {
        if (this.evaluateSendingMessage(notificationType)) {
            Message message = this.obtainMessage(notificationType.getTypeId(), null);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("starting timer for notifications.");
            stringBuilder.append(notificationType.getTypeId());
            Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
            this.sendMessageDelayed(message, (long)this.getDelay(notificationType));
        } else {
            this.cancelNotification(notificationType.getTypeId());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("canceling notifications: ");
            stringBuilder.append(notificationType.getTypeId());
            Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
        }
    }

    private void handleConfigChanges() {
        Iterator<Map.Entry<Integer, NotificationType>> iterator = this.mNotificationTypeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            this.evaluateSendingMessageOrCancelNotification(iterator.next().getValue());
        }
    }

    private void handleImsCapabilitiesChanged() {
        NotificationType notificationType = this.mNotificationTypeMap.get(1001);
        if (notificationType != null) {
            this.evaluateSendingMessageOrCancelNotification(notificationType);
        }
    }

    private void handlePrefNetworkModeChanged() {
        NotificationType notificationType = this.mNotificationTypeMap.get(1000);
        if (notificationType != null) {
            this.evaluateSendingMessageOrCancelNotification(notificationType);
        }
    }

    private boolean isGlobalMode() {
        Context context = this.mPhone.getContext();
        boolean bl = true;
        try {
            context = context.getContentResolver();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("preferred_network_mode");
            stringBuilder.append(this.mPhone.getSubId());
            int n = Settings.Global.getInt((ContentResolver)context, (String)stringBuilder.toString(), (int)Phone.PREFERRED_NT_MODE);
            if (n != 10) {
                bl = false;
            }
            return bl;
        }
        catch (Exception exception) {
            Rlog.e((String)LOG_TAG, (String)"Unable to get PREFERRED_NETWORK_MODE.");
            return true;
        }
    }

    private boolean isPhoneRegisteredForWifiCalling() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isPhoneRegisteredForWifiCalling: ");
        stringBuilder.append(this.mPhone.isWifiCallingEnabled());
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        return this.mPhone.isWifiCallingEnabled();
    }

    private boolean isPhoneStillRegistered() {
        boolean bl;
        block1 : {
            ServiceState serviceState = this.mSST.mSS;
            bl = true;
            if (serviceState == null) {
                return true;
            }
            if (this.mSST.mSS.getVoiceRegState() == 0 || this.mSST.mSS.getDataRegState() == 0) break block1;
            bl = false;
        }
        return bl;
    }

    private boolean isPhoneVoiceRegistered() {
        ServiceState serviceState = this.mSST.mSS;
        boolean bl = true;
        if (serviceState == null) {
            return true;
        }
        if (this.mSST.mSS.getVoiceRegState() != 0) {
            bl = false;
        }
        return bl;
    }

    private void registerNotificationTypes() {
        this.mNotificationTypeMap.put(1000, new PrefNetworkNotification(1000));
        this.mNotificationTypeMap.put(1001, new EmergencyNetworkNotification(1001));
    }

    private void registerPrefNetworkModeObserver() {
        int n = this.mPhone.getSubId();
        this.unregisterPrefNetworkModeObserver();
        if (SubscriptionManager.isValidSubscriptionId((int)n)) {
            ContentResolver contentResolver = this.mPhone.getContext().getContentResolver();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("preferred_network_mode");
            stringBuilder.append(n);
            contentResolver.registerContentObserver(Settings.Global.getUriFor((String)stringBuilder.toString()), true, this.mPrefNetworkModeObserver);
        }
    }

    private void unregisterPrefNetworkModeObserver() {
        this.mPhone.getContext().getContentResolver().unregisterContentObserver(this.mPrefNetworkModeObserver);
    }

    public void cancelNotification(int n) {
        Context context = this.mPhone.getContext();
        this.removeMessages(n);
        this.getNotificationManager(context).cancel(n);
    }

    public void dispose() {
        this.unregisterPrefNetworkModeObserver();
    }

    @VisibleForTesting
    public boolean evaluateSendingMessage(NotificationType notificationType) {
        return notificationType.sendMessage();
    }

    @VisibleForTesting
    public ContentObserver getContentObserver() {
        return this.mPrefNetworkModeObserver;
    }

    @VisibleForTesting
    public int getDelay(NotificationType notificationType) {
        return notificationType.getDelay();
    }

    @VisibleForTesting
    public Notification.Builder getNotificationBuilder(NotificationType notificationType) {
        return notificationType.getNotificationBuilder();
    }

    @VisibleForTesting
    public NotificationManager getNotificationManager(Context context) {
        return (NotificationManager)context.getSystemService("notification");
    }

    @VisibleForTesting
    public Map<Integer, NotificationType> getNotificationTypeMap() {
        return this.mNotificationTypeMap;
    }

    public void handleMessage(Message object) {
        int n = object.what;
        if (n != 1000 && n != 1001) {
            switch (n) {
                default: {
                    break;
                }
                case 105: {
                    this.handleImsCapabilitiesChanged();
                    break;
                }
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.handleConfigChanges();
                    break;
                }
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sending notification after delay: ");
            stringBuilder.append(object.what);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            object = this.mNotificationTypeMap.get(object.what);
            if (object != null) {
                this.sendNotification((NotificationType)object);
            }
        }
    }

    @VisibleForTesting
    public boolean isRadioOffOrAirplaneMode() {
        Context context = this.mPhone.getContext();
        boolean bl = true;
        try {
            int n = Settings.Global.getInt((ContentResolver)context.getContentResolver(), (String)"airplane_mode_on", (int)0);
            boolean bl2 = bl;
            if (this.mSST.isRadioOn()) {
                bl2 = n != 0 ? bl : false;
            }
            return bl2;
        }
        catch (Exception exception) {
            Rlog.e((String)LOG_TAG, (String)"Unable to get AIRPLACE_MODE_ON.");
            return true;
        }
    }

    @VisibleForTesting
    public void sendNotification(NotificationType notificationType) {
        if (!this.evaluateSendingMessage(notificationType)) {
            return;
        }
        Context context = this.mPhone.getContext();
        Notification.Builder builder = this.getNotificationBuilder(notificationType);
        builder.setWhen(System.currentTimeMillis()).setAutoCancel(true).setSmallIcon(17301642).setColor(context.getResources().getColor(17170460));
        this.getNotificationManager(context).notify(notificationType.getTypeId(), builder.build());
    }

    public class EmergencyNetworkNotification
    implements NotificationType {
        private int mDelay = -1;
        private final int mTypeId;

        EmergencyNetworkNotification(int n) {
            this.mTypeId = n;
        }

        @Override
        public int getDelay() {
            return this.mDelay;
        }

        @Override
        public Notification.Builder getNotificationBuilder() {
            Context context = CarrierServiceStateTracker.this.mPhone.getContext();
            CharSequence charSequence = context.getText(17039414);
            CharSequence charSequence2 = context.getText(17039413);
            return new Notification.Builder(context).setContentTitle(charSequence).setStyle((Notification.Style)new Notification.BigTextStyle().bigText(charSequence2)).setContentText(charSequence2).setChannel("wfc");
        }

        @Override
        public int getTypeId() {
            return this.mTypeId;
        }

        @Override
        public boolean sendMessage() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("EmergencyNetworkNotification: sendMessage() w/values: ,");
            stringBuilder.append(CarrierServiceStateTracker.this.isPhoneVoiceRegistered());
            stringBuilder.append(",");
            stringBuilder.append(this.mDelay);
            stringBuilder.append(",");
            stringBuilder.append(CarrierServiceStateTracker.this.isPhoneRegisteredForWifiCalling());
            stringBuilder.append(",");
            stringBuilder.append(CarrierServiceStateTracker.this.mSST.isRadioOn());
            Rlog.i((String)CarrierServiceStateTracker.LOG_TAG, (String)stringBuilder.toString());
            return this.mDelay != -1 && !CarrierServiceStateTracker.this.isPhoneVoiceRegistered() && CarrierServiceStateTracker.this.isPhoneRegisteredForWifiCalling();
            {
            }
        }

        @Override
        public void setDelay(PersistableBundle object) {
            if (object == null) {
                Rlog.e((String)CarrierServiceStateTracker.LOG_TAG, (String)"bundle is null");
                return;
            }
            this.mDelay = object.getInt("emergency_notification_delay_int");
            object = new StringBuilder();
            ((StringBuilder)object).append("reading time to delay notification emergency: ");
            ((StringBuilder)object).append(this.mDelay);
            Rlog.i((String)CarrierServiceStateTracker.LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

    public static interface NotificationType {
        public int getDelay();

        public Notification.Builder getNotificationBuilder();

        public int getTypeId();

        public boolean sendMessage();

        public void setDelay(PersistableBundle var1);
    }

    public class PrefNetworkNotification
    implements NotificationType {
        private int mDelay = -1;
        private final int mTypeId;

        PrefNetworkNotification(int n) {
            this.mTypeId = n;
        }

        @Override
        public int getDelay() {
            return this.mDelay;
        }

        @Override
        public Notification.Builder getNotificationBuilder() {
            Context context = CarrierServiceStateTracker.this.mPhone.getContext();
            Intent intent = new Intent("android.settings.DATA_ROAMING_SETTINGS");
            intent.putExtra("expandable", true);
            intent = PendingIntent.getActivity((Context)context, (int)0, (Intent)intent, (int)1073741824);
            CharSequence charSequence = context.getText(17039417);
            CharSequence charSequence2 = context.getText(17039416);
            return new Notification.Builder(context).setContentTitle(charSequence).setStyle((Notification.Style)new Notification.BigTextStyle().bigText(charSequence2)).setContentText(charSequence2).setChannel("alert").setContentIntent((PendingIntent)intent);
        }

        @Override
        public int getTypeId() {
            return this.mTypeId;
        }

        @Override
        public boolean sendMessage() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PrefNetworkNotification: sendMessage() w/values: ,");
            stringBuilder.append(CarrierServiceStateTracker.this.isPhoneStillRegistered());
            stringBuilder.append(",");
            stringBuilder.append(this.mDelay);
            stringBuilder.append(",");
            stringBuilder.append(CarrierServiceStateTracker.this.isGlobalMode());
            stringBuilder.append(",");
            stringBuilder.append(CarrierServiceStateTracker.this.mSST.isRadioOn());
            Rlog.i((String)CarrierServiceStateTracker.LOG_TAG, (String)stringBuilder.toString());
            return !(this.mDelay == -1 || CarrierServiceStateTracker.this.isPhoneStillRegistered() || CarrierServiceStateTracker.this.isGlobalMode() || CarrierServiceStateTracker.this.isRadioOffOrAirplaneMode());
            {
            }
        }

        @Override
        public void setDelay(PersistableBundle object) {
            if (object == null) {
                Rlog.e((String)CarrierServiceStateTracker.LOG_TAG, (String)"bundle is null");
                return;
            }
            this.mDelay = object.getInt("network_notification_delay_int");
            object = new StringBuilder();
            ((StringBuilder)object).append("reading time to delay notification pref network: ");
            ((StringBuilder)object).append(this.mDelay);
            Rlog.i((String)CarrierServiceStateTracker.LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

}

