/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.IFinancialSmsCallback;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;
import android.telephony._$$Lambda$SmsManager$9$Ma_xGOTcrGGV8QvZI0NSA6WUbKA;
import android.telephony._$$Lambda$SmsManager$9$rvckWwRKQKxMC1PhWEkHayc_gf8;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.IMms;
import com.android.internal.telephony.ISms;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.SmsRawData;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public final class SmsManager {
    public static final int CDMA_SMS_RECORD_LENGTH = 255;
    public static final int CELL_BROADCAST_RAN_TYPE_CDMA = 1;
    public static final int CELL_BROADCAST_RAN_TYPE_GSM = 0;
    public static final String EXTRA_MMS_DATA = "android.telephony.extra.MMS_DATA";
    public static final String EXTRA_MMS_HTTP_STATUS = "android.telephony.extra.MMS_HTTP_STATUS";
    public static final String EXTRA_SIM_SUBSCRIPTION_ID = "android.telephony.extra.SIM_SUBSCRIPTION_ID";
    public static final String EXTRA_SMS_MESSAGE = "android.telephony.extra.SMS_MESSAGE";
    public static final String EXTRA_STATUS = "android.telephony.extra.STATUS";
    public static final String MESSAGE_STATUS_READ = "read";
    public static final String MESSAGE_STATUS_SEEN = "seen";
    public static final String MMS_CONFIG_ALIAS_ENABLED = "aliasEnabled";
    public static final String MMS_CONFIG_ALIAS_MAX_CHARS = "aliasMaxChars";
    public static final String MMS_CONFIG_ALIAS_MIN_CHARS = "aliasMinChars";
    public static final String MMS_CONFIG_ALLOW_ATTACH_AUDIO = "allowAttachAudio";
    public static final String MMS_CONFIG_APPEND_TRANSACTION_ID = "enabledTransID";
    public static final String MMS_CONFIG_CLOSE_CONNECTION = "mmsCloseConnection";
    public static final String MMS_CONFIG_EMAIL_GATEWAY_NUMBER = "emailGatewayNumber";
    public static final String MMS_CONFIG_GROUP_MMS_ENABLED = "enableGroupMms";
    public static final String MMS_CONFIG_HTTP_PARAMS = "httpParams";
    public static final String MMS_CONFIG_HTTP_SOCKET_TIMEOUT = "httpSocketTimeout";
    public static final String MMS_CONFIG_MAX_IMAGE_HEIGHT = "maxImageHeight";
    public static final String MMS_CONFIG_MAX_IMAGE_WIDTH = "maxImageWidth";
    public static final String MMS_CONFIG_MAX_MESSAGE_SIZE = "maxMessageSize";
    public static final String MMS_CONFIG_MESSAGE_TEXT_MAX_SIZE = "maxMessageTextSize";
    public static final String MMS_CONFIG_MMS_DELIVERY_REPORT_ENABLED = "enableMMSDeliveryReports";
    public static final String MMS_CONFIG_MMS_ENABLED = "enabledMMS";
    public static final String MMS_CONFIG_MMS_READ_REPORT_ENABLED = "enableMMSReadReports";
    public static final String MMS_CONFIG_MULTIPART_SMS_ENABLED = "enableMultipartSMS";
    public static final String MMS_CONFIG_NAI_SUFFIX = "naiSuffix";
    public static final String MMS_CONFIG_NOTIFY_WAP_MMSC_ENABLED = "enabledNotifyWapMMSC";
    public static final String MMS_CONFIG_RECIPIENT_LIMIT = "recipientLimit";
    public static final String MMS_CONFIG_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES = "sendMultipartSmsAsSeparateMessages";
    public static final String MMS_CONFIG_SHOW_CELL_BROADCAST_APP_LINKS = "config_cellBroadcastAppLinks";
    public static final String MMS_CONFIG_SMS_DELIVERY_REPORT_ENABLED = "enableSMSDeliveryReports";
    public static final String MMS_CONFIG_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD = "smsToMmsTextLengthThreshold";
    public static final String MMS_CONFIG_SMS_TO_MMS_TEXT_THRESHOLD = "smsToMmsTextThreshold";
    public static final String MMS_CONFIG_SUBJECT_MAX_LENGTH = "maxSubjectLength";
    public static final String MMS_CONFIG_SUPPORT_HTTP_CHARSET_HEADER = "supportHttpCharsetHeader";
    public static final String MMS_CONFIG_SUPPORT_MMS_CONTENT_DISPOSITION = "supportMmsContentDisposition";
    public static final String MMS_CONFIG_UA_PROF_TAG_NAME = "uaProfTagName";
    public static final String MMS_CONFIG_UA_PROF_URL = "uaProfUrl";
    public static final String MMS_CONFIG_USER_AGENT = "userAgent";
    public static final int MMS_ERROR_CONFIGURATION_ERROR = 7;
    public static final int MMS_ERROR_HTTP_FAILURE = 4;
    public static final int MMS_ERROR_INVALID_APN = 2;
    public static final int MMS_ERROR_IO_ERROR = 5;
    public static final int MMS_ERROR_NO_DATA_NETWORK = 8;
    public static final int MMS_ERROR_RETRY = 6;
    public static final int MMS_ERROR_UNABLE_CONNECT_MMS = 3;
    public static final int MMS_ERROR_UNSPECIFIED = 1;
    private static final String NO_DEFAULT_EXTRA = "noDefault";
    private static final String PHONE_PACKAGE_NAME = "com.android.phone";
    public static final String REGEX_PREFIX_DELIMITER = ",";
    @SystemApi
    public static final int RESULT_CANCELLED = 23;
    @SystemApi
    public static final int RESULT_ENCODING_ERROR = 18;
    @SystemApi
    public static final int RESULT_ERROR_FDN_CHECK_FAILURE = 6;
    public static final int RESULT_ERROR_GENERIC_FAILURE = 1;
    public static final int RESULT_ERROR_LIMIT_EXCEEDED = 5;
    @SystemApi
    public static final int RESULT_ERROR_NONE = 0;
    public static final int RESULT_ERROR_NO_SERVICE = 4;
    public static final int RESULT_ERROR_NULL_PDU = 3;
    public static final int RESULT_ERROR_RADIO_OFF = 2;
    public static final int RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED = 8;
    public static final int RESULT_ERROR_SHORT_CODE_NOT_ALLOWED = 7;
    @SystemApi
    public static final int RESULT_INTERNAL_ERROR = 21;
    @SystemApi
    public static final int RESULT_INVALID_ARGUMENTS = 11;
    @SystemApi
    public static final int RESULT_INVALID_SMSC_ADDRESS = 19;
    @SystemApi
    public static final int RESULT_INVALID_SMS_FORMAT = 14;
    @SystemApi
    public static final int RESULT_INVALID_STATE = 12;
    @SystemApi
    public static final int RESULT_MODEM_ERROR = 16;
    @SystemApi
    public static final int RESULT_NETWORK_ERROR = 17;
    @SystemApi
    public static final int RESULT_NETWORK_REJECT = 10;
    @SystemApi
    public static final int RESULT_NO_MEMORY = 13;
    @SystemApi
    public static final int RESULT_NO_RESOURCES = 22;
    @SystemApi
    public static final int RESULT_OPERATION_NOT_ALLOWED = 20;
    @SystemApi
    public static final int RESULT_RADIO_NOT_AVAILABLE = 9;
    @SystemApi
    public static final int RESULT_REQUEST_NOT_SUPPORTED = 24;
    public static final int RESULT_STATUS_SUCCESS = 0;
    public static final int RESULT_STATUS_TIMEOUT = 1;
    @SystemApi
    public static final int RESULT_SYSTEM_ERROR = 15;
    public static final int SMS_CATEGORY_FREE_SHORT_CODE = 1;
    public static final int SMS_CATEGORY_NOT_SHORT_CODE = 0;
    public static final int SMS_CATEGORY_POSSIBLE_PREMIUM_SHORT_CODE = 3;
    public static final int SMS_CATEGORY_PREMIUM_SHORT_CODE = 4;
    public static final int SMS_CATEGORY_STANDARD_SHORT_CODE = 2;
    public static final int SMS_MESSAGE_PERIOD_NOT_SPECIFIED = -1;
    public static final int SMS_MESSAGE_PRIORITY_NOT_SPECIFIED = -1;
    public static final int SMS_RECORD_LENGTH = 176;
    public static final int SMS_TYPE_INCOMING = 0;
    public static final int SMS_TYPE_OUTGOING = 1;
    public static final int STATUS_ON_ICC_FREE = 0;
    public static final int STATUS_ON_ICC_READ = 1;
    public static final int STATUS_ON_ICC_SENT = 5;
    public static final int STATUS_ON_ICC_UNREAD = 3;
    public static final int STATUS_ON_ICC_UNSENT = 7;
    private static final String TAG = "SmsManager";
    private static final SmsManager sInstance = new SmsManager(Integer.MAX_VALUE);
    private static final Object sLockObject = new Object();
    private static final Map<Integer, SmsManager> sSubInstances = new ArrayMap<Integer, SmsManager>();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mSubId;

    private SmsManager(int n) {
        this.mSubId = n;
    }

    private ArrayList<SmsMessage> createMessageListFromRawRecords(List<SmsRawData> list) {
        ArrayList<SmsMessage> arrayList = new ArrayList<SmsMessage>();
        if (list != null) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                Object object = list.get(i);
                if (object == null || (object = SmsMessage.createFromEfRecord(i + 1, ((SmsRawData)object).getBytes(), this.getSubscriptionId())) == null) continue;
                arrayList.add((SmsMessage)object);
            }
        }
        return arrayList;
    }

    public static SmsManager getDefault() {
        return sInstance;
    }

    public static int getDefaultSmsSubscriptionId() {
        try {
            int n = SmsManager.getISmsService().getPreferredSmsSubscription();
            return n;
        }
        catch (NullPointerException nullPointerException) {
            return -1;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    private static ISms getISmsService() {
        return ISms.Stub.asInterface(ServiceManager.getService("isms"));
    }

    private static ISms getISmsServiceOrThrow() {
        ISms iSms = SmsManager.getISmsService();
        if (iSms != null) {
            return iSms;
        }
        throw new UnsupportedOperationException("Sms is not supported");
    }

    private static ITelephony getITelephony() {
        ITelephony iTelephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        if (iTelephony != null) {
            return iTelephony;
        }
        throw new RuntimeException("Could not find Telephony Service.");
    }

    public static Bundle getMmsConfig(BaseBundle baseBundle) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(MMS_CONFIG_APPEND_TRANSACTION_ID, baseBundle.getBoolean(MMS_CONFIG_APPEND_TRANSACTION_ID));
        bundle.putBoolean(MMS_CONFIG_MMS_ENABLED, baseBundle.getBoolean(MMS_CONFIG_MMS_ENABLED));
        bundle.putBoolean(MMS_CONFIG_GROUP_MMS_ENABLED, baseBundle.getBoolean(MMS_CONFIG_GROUP_MMS_ENABLED));
        bundle.putBoolean(MMS_CONFIG_NOTIFY_WAP_MMSC_ENABLED, baseBundle.getBoolean(MMS_CONFIG_NOTIFY_WAP_MMSC_ENABLED));
        bundle.putBoolean(MMS_CONFIG_ALIAS_ENABLED, baseBundle.getBoolean(MMS_CONFIG_ALIAS_ENABLED));
        bundle.putBoolean(MMS_CONFIG_ALLOW_ATTACH_AUDIO, baseBundle.getBoolean(MMS_CONFIG_ALLOW_ATTACH_AUDIO));
        bundle.putBoolean(MMS_CONFIG_MULTIPART_SMS_ENABLED, baseBundle.getBoolean(MMS_CONFIG_MULTIPART_SMS_ENABLED));
        bundle.putBoolean(MMS_CONFIG_SMS_DELIVERY_REPORT_ENABLED, baseBundle.getBoolean(MMS_CONFIG_SMS_DELIVERY_REPORT_ENABLED));
        bundle.putBoolean(MMS_CONFIG_SUPPORT_MMS_CONTENT_DISPOSITION, baseBundle.getBoolean(MMS_CONFIG_SUPPORT_MMS_CONTENT_DISPOSITION));
        bundle.putBoolean(MMS_CONFIG_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES, baseBundle.getBoolean(MMS_CONFIG_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES));
        bundle.putBoolean(MMS_CONFIG_MMS_READ_REPORT_ENABLED, baseBundle.getBoolean(MMS_CONFIG_MMS_READ_REPORT_ENABLED));
        bundle.putBoolean(MMS_CONFIG_MMS_DELIVERY_REPORT_ENABLED, baseBundle.getBoolean(MMS_CONFIG_MMS_DELIVERY_REPORT_ENABLED));
        bundle.putBoolean(MMS_CONFIG_CLOSE_CONNECTION, baseBundle.getBoolean(MMS_CONFIG_CLOSE_CONNECTION));
        bundle.putInt(MMS_CONFIG_MAX_MESSAGE_SIZE, baseBundle.getInt(MMS_CONFIG_MAX_MESSAGE_SIZE));
        bundle.putInt(MMS_CONFIG_MAX_IMAGE_WIDTH, baseBundle.getInt(MMS_CONFIG_MAX_IMAGE_WIDTH));
        bundle.putInt(MMS_CONFIG_MAX_IMAGE_HEIGHT, baseBundle.getInt(MMS_CONFIG_MAX_IMAGE_HEIGHT));
        bundle.putInt(MMS_CONFIG_RECIPIENT_LIMIT, baseBundle.getInt(MMS_CONFIG_RECIPIENT_LIMIT));
        bundle.putInt(MMS_CONFIG_ALIAS_MIN_CHARS, baseBundle.getInt(MMS_CONFIG_ALIAS_MIN_CHARS));
        bundle.putInt(MMS_CONFIG_ALIAS_MAX_CHARS, baseBundle.getInt(MMS_CONFIG_ALIAS_MAX_CHARS));
        bundle.putInt(MMS_CONFIG_SMS_TO_MMS_TEXT_THRESHOLD, baseBundle.getInt(MMS_CONFIG_SMS_TO_MMS_TEXT_THRESHOLD));
        bundle.putInt(MMS_CONFIG_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD, baseBundle.getInt(MMS_CONFIG_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD));
        bundle.putInt(MMS_CONFIG_MESSAGE_TEXT_MAX_SIZE, baseBundle.getInt(MMS_CONFIG_MESSAGE_TEXT_MAX_SIZE));
        bundle.putInt(MMS_CONFIG_SUBJECT_MAX_LENGTH, baseBundle.getInt(MMS_CONFIG_SUBJECT_MAX_LENGTH));
        bundle.putInt(MMS_CONFIG_HTTP_SOCKET_TIMEOUT, baseBundle.getInt(MMS_CONFIG_HTTP_SOCKET_TIMEOUT));
        bundle.putString(MMS_CONFIG_UA_PROF_TAG_NAME, baseBundle.getString(MMS_CONFIG_UA_PROF_TAG_NAME));
        bundle.putString(MMS_CONFIG_USER_AGENT, baseBundle.getString(MMS_CONFIG_USER_AGENT));
        bundle.putString(MMS_CONFIG_UA_PROF_URL, baseBundle.getString(MMS_CONFIG_UA_PROF_URL));
        bundle.putString(MMS_CONFIG_HTTP_PARAMS, baseBundle.getString(MMS_CONFIG_HTTP_PARAMS));
        bundle.putString(MMS_CONFIG_EMAIL_GATEWAY_NUMBER, baseBundle.getString(MMS_CONFIG_EMAIL_GATEWAY_NUMBER));
        bundle.putString(MMS_CONFIG_NAI_SUFFIX, baseBundle.getString(MMS_CONFIG_NAI_SUFFIX));
        bundle.putBoolean(MMS_CONFIG_SHOW_CELL_BROADCAST_APP_LINKS, baseBundle.getBoolean(MMS_CONFIG_SHOW_CELL_BROADCAST_APP_LINKS));
        bundle.putBoolean(MMS_CONFIG_SUPPORT_HTTP_CHARSET_HEADER, baseBundle.getBoolean(MMS_CONFIG_SUPPORT_HTTP_CHARSET_HEADER));
        return bundle;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SmsManager getSmsManagerForSubscriptionId(int n) {
        Object object = sLockObject;
        synchronized (object) {
            SmsManager smsManager;
            SmsManager smsManager2 = smsManager = sSubInstances.get(n);
            if (smsManager == null) {
                smsManager2 = new SmsManager(n);
                sSubInstances.put(n, smsManager2);
            }
            return smsManager2;
        }
    }

    private static int getTargetSdkVersion() {
        int n;
        Context context = ActivityThread.currentApplication().getApplicationContext();
        try {
            n = context.getPackageManager().getApplicationInfo((String)context.getOpPackageName(), (int)0).targetSdkVersion;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            n = -1;
        }
        return n;
    }

    private static void notifySmsErrorNoDefaultSet(Context context, PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            Intent intent = new Intent();
            intent.putExtra(NO_DEFAULT_EXTRA, true);
            try {
                pendingIntent.send(context, 1, intent);
            }
            catch (PendingIntent.CanceledException canceledException) {
                // empty catch block
            }
        }
    }

    private static void notifySmsErrorNoDefaultSet(Context context, List<PendingIntent> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                PendingIntent pendingIntent = (PendingIntent)object.next();
                Intent intent = new Intent();
                intent.putExtra(NO_DEFAULT_EXTRA, true);
                try {
                    pendingIntent.send(context, 1, intent);
                }
                catch (PendingIntent.CanceledException canceledException) {}
            }
        }
    }

    private static void notifySmsGenericError(PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(1);
            }
            catch (PendingIntent.CanceledException canceledException) {
                // empty catch block
            }
        }
    }

    private static void notifySmsGenericError(List<PendingIntent> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                PendingIntent pendingIntent = (PendingIntent)object.next();
                try {
                    pendingIntent.send(1);
                }
                catch (PendingIntent.CanceledException canceledException) {}
            }
        }
    }

    private void resolveSubscriptionForOperation(final SubscriptionResolverResult subscriptionResolverResult) {
        boolean bl;
        int n;
        Object object;
        Object object2;
        block6 : {
            n = this.getSubscriptionId();
            boolean bl2 = false;
            bl = false;
            object2 = ActivityThread.currentApplication().getApplicationContext();
            object = SmsManager.getISmsService();
            if (object == null) break block6;
            try {
                bl = object.isSmsSimPickActivityNeeded(n);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "resolveSubscriptionForOperation", remoteException);
                bl = bl2;
            }
        }
        if (!bl) {
            this.sendResolverResult(subscriptionResolverResult, n, false);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("resolveSubscriptionForOperation isSmsSimPickActivityNeeded is true for package ");
        ((StringBuilder)object).append(((Context)object2).getPackageName());
        Log.d(TAG, ((StringBuilder)object).toString());
        try {
            object = SmsManager.getITelephony();
            object2 = ((Context)object2).getOpPackageName();
            IIntegerConsumer.Stub stub = new IIntegerConsumer.Stub(){

                @Override
                public void accept(int n) {
                    SmsManager.this.sendResolverResult(subscriptionResolverResult, n, true);
                }
            };
            object.enqueueSmsPickResult((String)object2, stub);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to launch activity", remoteException);
            this.sendResolverResult(subscriptionResolverResult, n, true);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void sendMultipartTextMessageInternal(final String string2, String charSequence, List<String> list, List<PendingIntent> object, List<PendingIntent> list2, boolean bl, int n, boolean bl2, int n2) {
        PendingIntent pendingIntent;
        block9 : {
            void var1_4;
            block10 : {
                if (TextUtils.isEmpty(string2)) throw new IllegalArgumentException("Invalid destinationAddress");
                if (list == null) throw new IllegalArgumentException("Invalid message body");
                if (list.size() < 1) throw new IllegalArgumentException("Invalid message body");
                if (n < 0 || n > 3) {
                    n = -1;
                }
                if (n2 < 5 || n2 > 635040) {
                    n2 = -1;
                }
                if (list.size() <= 1) break block9;
                Object object2 = ActivityThread.currentApplication().getApplicationContext();
                if (bl) {
                    this.resolveSubscriptionForOperation(new SubscriptionResolverResult((String)charSequence, list, (List)object, list2, bl, n, bl2, n2, (Context)object2){
                        final /* synthetic */ Context val$context;
                        final /* synthetic */ List val$deliveryIntents;
                        final /* synthetic */ boolean val$expectMore;
                        final /* synthetic */ int val$finalPriority;
                        final /* synthetic */ int val$finalValidity;
                        final /* synthetic */ List val$parts;
                        final /* synthetic */ boolean val$persistMessage;
                        final /* synthetic */ String val$scAddress;
                        final /* synthetic */ List val$sentIntents;
                        {
                            this.val$scAddress = string3;
                            this.val$parts = list;
                            this.val$sentIntents = list2;
                            this.val$deliveryIntents = list3;
                            this.val$persistMessage = bl;
                            this.val$finalPriority = n;
                            this.val$expectMore = bl2;
                            this.val$finalValidity = n2;
                            this.val$context = context;
                        }

                        @Override
                        public void onFailure() {
                            SmsManager.notifySmsErrorNoDefaultSet(this.val$context, this.val$sentIntents);
                        }

                        @Override
                        public void onSuccess(int n) {
                            block3 : {
                                ISms iSms = SmsManager.getISmsServiceOrThrow();
                                if (iSms == null) break block3;
                                try {
                                    iSms.sendMultipartTextForSubscriberWithOptions(n, ActivityThread.currentPackageName(), string2, this.val$scAddress, this.val$parts, this.val$sentIntents, this.val$deliveryIntents, this.val$persistMessage, this.val$finalPriority, this.val$expectMore, this.val$finalValidity);
                                }
                                catch (RemoteException remoteException) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("sendMultipartTextMessageInternal: Couldn't send SMS - ");
                                    stringBuilder.append(remoteException.getMessage());
                                    Log.e(SmsManager.TAG, stringBuilder.toString());
                                    SmsManager.notifySmsGenericError(this.val$sentIntents);
                                }
                            }
                        }
                    });
                    return;
                }
                ISms iSms = SmsManager.getISmsServiceOrThrow();
                if (iSms == null) return;
                int n3 = this.getSubscriptionId();
                object2 = ActivityThread.currentPackageName();
                try {
                    iSms.sendMultipartTextForSubscriberWithOptions(n3, (String)object2, string2, (String)charSequence, list, (List<PendingIntent>)object, list2, bl, n, bl2, n2);
                    return;
                }
                catch (RemoteException remoteException) {}
                break block10;
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendMultipartTextMessageInternal (no persist): Couldn't send SMS - ");
            ((StringBuilder)charSequence).append(var1_4.getMessage());
            Log.e(TAG, ((StringBuilder)charSequence).toString());
            SmsManager.notifySmsGenericError((List<PendingIntent>)object);
            return;
        }
        PendingIntent pendingIntent2 = pendingIntent = null;
        if (object != null) {
            pendingIntent2 = pendingIntent;
            if (object.size() > 0) {
                pendingIntent2 = (PendingIntent)object.get(0);
            }
        }
        object = list2 != null && list2.size() > 0 ? list2.get(0) : null;
        this.sendTextMessageInternal(string2, (String)charSequence, list.get(0), pendingIntent2, (PendingIntent)object, bl, n, bl2, n2);
    }

    private void sendMultipartTextMessageInternal(String charSequence, String string2, List<String> list, List<PendingIntent> object, List<PendingIntent> list2, boolean bl, final String string3) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (list != null && list.size() >= 1) {
                block11 : {
                    if (list.size() > 1) {
                        Object object2 = ActivityThread.currentApplication().getApplicationContext();
                        if (bl) {
                            this.resolveSubscriptionForOperation(new SubscriptionResolverResult((String)charSequence, string2, list, (List)object, list2, bl, (Context)object2){
                                final /* synthetic */ Context val$context;
                                final /* synthetic */ List val$deliveryIntents;
                                final /* synthetic */ String val$destinationAddress;
                                final /* synthetic */ List val$parts;
                                final /* synthetic */ boolean val$persistMessage;
                                final /* synthetic */ String val$scAddress;
                                final /* synthetic */ List val$sentIntents;
                                {
                                    this.val$destinationAddress = string32;
                                    this.val$scAddress = string4;
                                    this.val$parts = list;
                                    this.val$sentIntents = list2;
                                    this.val$deliveryIntents = list3;
                                    this.val$persistMessage = bl;
                                    this.val$context = context;
                                }

                                @Override
                                public void onFailure() {
                                    SmsManager.notifySmsErrorNoDefaultSet(this.val$context, this.val$sentIntents);
                                }

                                @Override
                                public void onSuccess(int n) {
                                    try {
                                        SmsManager.getISmsServiceOrThrow().sendMultipartTextForSubscriber(n, string3, this.val$destinationAddress, this.val$scAddress, this.val$parts, this.val$sentIntents, this.val$deliveryIntents, this.val$persistMessage);
                                    }
                                    catch (RemoteException remoteException) {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("sendMultipartTextMessageInternal: Couldn't send SMS - ");
                                        stringBuilder.append(remoteException.getMessage());
                                        Log.e(SmsManager.TAG, stringBuilder.toString());
                                        SmsManager.notifySmsGenericError(this.val$sentIntents);
                                    }
                                }
                            });
                        } else {
                            object2 = SmsManager.getISmsServiceOrThrow();
                            if (object2 == null) break block11;
                            try {
                                object2.sendMultipartTextForSubscriber(this.getSubscriptionId(), string3, (String)charSequence, string2, list, (List<PendingIntent>)object, list2, bl);
                            }
                            catch (RemoteException remoteException) {
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append("sendMultipartTextMessageInternal: Couldn't send SMS - ");
                                ((StringBuilder)charSequence).append(remoteException.getMessage());
                                Log.e(TAG, ((StringBuilder)charSequence).toString());
                                SmsManager.notifySmsGenericError((List<PendingIntent>)object);
                            }
                        }
                    } else {
                        PendingIntent pendingIntent;
                        PendingIntent pendingIntent2 = pendingIntent = null;
                        if (object != null) {
                            pendingIntent2 = pendingIntent;
                            if (object.size() > 0) {
                                pendingIntent2 = object.get(0);
                            }
                        }
                        object = list2 != null && list2.size() > 0 ? list2.get(0) : null;
                        this.sendTextMessageInternal((String)charSequence, string2, list.get(0), pendingIntent2, (PendingIntent)object, true, string3);
                    }
                }
                return;
            }
            throw new IllegalArgumentException("Invalid message body");
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    private void sendResolverResult(SubscriptionResolverResult subscriptionResolverResult, int n, boolean bl) {
        if (SubscriptionManager.isValidSubscriptionId(n)) {
            subscriptionResolverResult.onSuccess(n);
            return;
        }
        if (SmsManager.getTargetSdkVersion() <= 28 && !bl) {
            subscriptionResolverResult.onSuccess(n);
        } else {
            subscriptionResolverResult.onFailure();
        }
    }

    private void sendTextMessageInternal(final String string2, String charSequence, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, int n, boolean bl2, int n2) {
        if (!TextUtils.isEmpty(string2)) {
            if (!TextUtils.isEmpty(string3)) {
                block9 : {
                    if (n < 0 || n > 3) {
                        n = -1;
                    }
                    if (n2 < 5 || n2 > 635040) {
                        n2 = -1;
                    }
                    Object object = ActivityThread.currentApplication().getApplicationContext();
                    if (bl) {
                        this.resolveSubscriptionForOperation(new SubscriptionResolverResult((String)charSequence, string3, pendingIntent, pendingIntent2, bl, n, bl2, n2, (Context)object){
                            final /* synthetic */ Context val$context;
                            final /* synthetic */ PendingIntent val$deliveryIntent;
                            final /* synthetic */ boolean val$expectMore;
                            final /* synthetic */ int val$finalPriority;
                            final /* synthetic */ int val$finalValidity;
                            final /* synthetic */ boolean val$persistMessage;
                            final /* synthetic */ String val$scAddress;
                            final /* synthetic */ PendingIntent val$sentIntent;
                            final /* synthetic */ String val$text;
                            {
                                this.val$scAddress = string3;
                                this.val$text = string4;
                                this.val$sentIntent = pendingIntent;
                                this.val$deliveryIntent = pendingIntent2;
                                this.val$persistMessage = bl;
                                this.val$finalPriority = n;
                                this.val$expectMore = bl2;
                                this.val$finalValidity = n2;
                                this.val$context = context;
                            }

                            @Override
                            public void onFailure() {
                                SmsManager.notifySmsErrorNoDefaultSet(this.val$context, this.val$sentIntent);
                            }

                            @Override
                            public void onSuccess(int n) {
                                block3 : {
                                    ISms iSms = SmsManager.getISmsServiceOrThrow();
                                    if (iSms == null) break block3;
                                    try {
                                        iSms.sendTextForSubscriberWithOptions(n, ActivityThread.currentPackageName(), string2, this.val$scAddress, this.val$text, this.val$sentIntent, this.val$deliveryIntent, this.val$persistMessage, this.val$finalPriority, this.val$expectMore, this.val$finalValidity);
                                    }
                                    catch (RemoteException remoteException) {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("sendTextMessageInternal: Couldn't send SMS, exception - ");
                                        stringBuilder.append(remoteException.getMessage());
                                        Log.e(SmsManager.TAG, stringBuilder.toString());
                                        SmsManager.notifySmsGenericError(this.val$sentIntent);
                                    }
                                }
                            }
                        });
                    } else {
                        object = SmsManager.getISmsServiceOrThrow();
                        if (object == null) break block9;
                        try {
                            object.sendTextForSubscriberWithOptions(this.getSubscriptionId(), ActivityThread.currentPackageName(), string2, (String)charSequence, string3, pendingIntent, pendingIntent2, bl, n, bl2, n2);
                        }
                        catch (RemoteException remoteException) {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("sendTextMessageInternal(no persist): Couldn't send SMS, exception - ");
                            ((StringBuilder)charSequence).append(remoteException.getMessage());
                            Log.e(TAG, ((StringBuilder)charSequence).toString());
                            SmsManager.notifySmsGenericError(pendingIntent);
                        }
                    }
                }
                return;
            }
            throw new IllegalArgumentException("Invalid message body");
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    private void sendTextMessageInternal(final String string2, String charSequence, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, final String string4) {
        if (!TextUtils.isEmpty(string2)) {
            if (!TextUtils.isEmpty(string3)) {
                Object object = ActivityThread.currentApplication().getApplicationContext();
                if (bl) {
                    this.resolveSubscriptionForOperation(new SubscriptionResolverResult((String)charSequence, string3, pendingIntent, pendingIntent2, bl, (Context)object){
                        final /* synthetic */ Context val$context;
                        final /* synthetic */ PendingIntent val$deliveryIntent;
                        final /* synthetic */ boolean val$persistMessage;
                        final /* synthetic */ String val$scAddress;
                        final /* synthetic */ PendingIntent val$sentIntent;
                        final /* synthetic */ String val$text;
                        {
                            this.val$scAddress = string42;
                            this.val$text = string5;
                            this.val$sentIntent = pendingIntent;
                            this.val$deliveryIntent = pendingIntent2;
                            this.val$persistMessage = bl;
                            this.val$context = context;
                        }

                        @Override
                        public void onFailure() {
                            SmsManager.notifySmsErrorNoDefaultSet(this.val$context, this.val$sentIntent);
                        }

                        @Override
                        public void onSuccess(int n) {
                            Object object = SmsManager.getISmsServiceOrThrow();
                            try {
                                object.sendTextForSubscriber(n, string4, string2, this.val$scAddress, this.val$text, this.val$sentIntent, this.val$deliveryIntent, this.val$persistMessage);
                            }
                            catch (RemoteException remoteException) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("sendTextMessageInternal: Couldn't send SMS, exception - ");
                                ((StringBuilder)object).append(remoteException.getMessage());
                                Log.e(SmsManager.TAG, ((StringBuilder)object).toString());
                                SmsManager.notifySmsGenericError(this.val$sentIntent);
                            }
                        }
                    });
                } else {
                    object = SmsManager.getISmsServiceOrThrow();
                    try {
                        object.sendTextForSubscriber(this.getSubscriptionId(), string4, string2, (String)charSequence, string3, pendingIntent, pendingIntent2, bl);
                    }
                    catch (RemoteException remoteException) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("sendTextMessageInternal (no persist): Couldn't send SMS, exception - ");
                        ((StringBuilder)charSequence).append(remoteException.getMessage());
                        Log.e(TAG, ((StringBuilder)charSequence).toString());
                        SmsManager.notifySmsGenericError(pendingIntent);
                    }
                }
                return;
            }
            throw new IllegalArgumentException("Invalid message body");
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    public Uri addMultimediaMessageDraft(Uri uri) {
        if (uri != null) {
            block4 : {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms == null) break block4;
                try {
                    uri = iMms.addMultimediaMessageDraft(ActivityThread.currentPackageName(), uri);
                    return uri;
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return null;
        }
        throw new IllegalArgumentException("Uri contentUri null");
    }

    public Uri addTextMessageDraft(String object, String string2) {
        block3 : {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms == null) break block3;
            try {
                object = iMms.addTextMessageDraft(ActivityThread.currentPackageName(), (String)object, string2);
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public boolean archiveStoredConversation(long l, boolean bl) {
        block3 : {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms == null) break block3;
            try {
                bl = iMms.archiveStoredConversation(ActivityThread.currentPackageName(), l, bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public int checkSmsShortCodeDestination(String string2, String string3) {
        block3 : {
            ISms iSms = SmsManager.getISmsServiceOrThrow();
            if (iSms == null) break block3;
            try {
                int n = iSms.checkSmsShortCodeDestination(this.getSubscriptionId(), ActivityThread.currentPackageName(), string2, string3);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "checkSmsShortCodeDestination() RemoteException", remoteException);
            }
        }
        return 0;
    }

    @UnsupportedAppUsage
    public boolean copyMessageToIcc(byte[] arrby, byte[] arrby2, int n) {
        boolean bl = false;
        boolean bl2 = false;
        if (arrby2 != null) {
            block4 : {
                ISms iSms = SmsManager.getISmsService();
                if (iSms == null) break block4;
                try {
                    bl2 = iSms.copyMessageToIccEfForSubscriber(this.getSubscriptionId(), ActivityThread.currentPackageName(), n, arrby2, arrby);
                }
                catch (RemoteException remoteException) {
                    bl2 = bl;
                }
            }
            return bl2;
        }
        throw new IllegalArgumentException("pdu is NULL");
    }

    public String createAppSpecificSmsToken(PendingIntent object) {
        try {
            object = SmsManager.getISmsServiceOrThrow().createAppSpecificSmsToken(this.getSubscriptionId(), ActivityThread.currentPackageName(), (PendingIntent)object);
            return object;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    public String createAppSpecificSmsTokenWithPackageInfo(String string2, PendingIntent pendingIntent) {
        try {
            string2 = SmsManager.getISmsServiceOrThrow().createAppSpecificSmsTokenWithPackageInfo(this.getSubscriptionId(), ActivityThread.currentPackageName(), string2, pendingIntent);
            return string2;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    @UnsupportedAppUsage
    public boolean deleteMessageFromIcc(int n) {
        boolean bl;
        block3 : {
            boolean bl2 = false;
            bl = false;
            byte[] arrby = new byte[175];
            Arrays.fill(arrby, (byte)-1);
            ISms iSms = SmsManager.getISmsService();
            if (iSms == null) break block3;
            try {
                bl = iSms.updateMessageOnIccEfForSubscriber(this.getSubscriptionId(), ActivityThread.currentPackageName(), n, 0, arrby);
            }
            catch (RemoteException remoteException) {
                bl = bl2;
            }
        }
        return bl;
    }

    public boolean deleteStoredConversation(long l) {
        block3 : {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms == null) break block3;
            try {
                boolean bl = iMms.deleteStoredConversation(ActivityThread.currentPackageName(), l);
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public boolean deleteStoredMessage(Uri uri) {
        if (uri != null) {
            block4 : {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms == null) break block4;
                try {
                    boolean bl = iMms.deleteStoredMessage(ActivityThread.currentPackageName(), uri);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return false;
        }
        throw new IllegalArgumentException("Empty message URI");
    }

    public boolean disableCellBroadcast(int n, int n2) {
        boolean bl;
        block3 : {
            boolean bl2 = false;
            bl = false;
            ISms iSms = SmsManager.getISmsService();
            if (iSms == null) break block3;
            try {
                bl = iSms.disableCellBroadcastForSubscriber(this.getSubscriptionId(), n, n2);
            }
            catch (RemoteException remoteException) {
                bl = bl2;
            }
        }
        return bl;
    }

    @UnsupportedAppUsage
    public boolean disableCellBroadcastRange(int n, int n2, int n3) {
        boolean bl = false;
        boolean bl2 = false;
        if (n2 >= n) {
            block4 : {
                ISms iSms = SmsManager.getISmsService();
                if (iSms == null) break block4;
                try {
                    bl2 = iSms.disableCellBroadcastRangeForSubscriber(this.getSubscriptionId(), n, n2, n3);
                }
                catch (RemoteException remoteException) {
                    bl2 = bl;
                }
            }
            return bl2;
        }
        throw new IllegalArgumentException("endMessageId < startMessageId");
    }

    public ArrayList<String> divideMessage(String string2) {
        if (string2 != null) {
            return SmsMessage.fragmentText(string2, this.getSubscriptionId());
        }
        throw new IllegalArgumentException("text is null");
    }

    public void downloadMultimediaMessage(Context object, String string2, Uri uri, Bundle bundle, PendingIntent pendingIntent) {
        if (!TextUtils.isEmpty(string2)) {
            if (uri != null) {
                block5 : {
                    object = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                    if (object != null) break block5;
                    return;
                }
                try {
                    object.downloadMessage(this.getSubscriptionId(), ActivityThread.currentPackageName(), string2, uri, bundle, pendingIntent);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                return;
            }
            throw new IllegalArgumentException("Uri contentUri null");
        }
        throw new IllegalArgumentException("Empty MMS location URL");
    }

    public boolean enableCellBroadcast(int n, int n2) {
        boolean bl;
        block3 : {
            boolean bl2 = false;
            bl = false;
            ISms iSms = SmsManager.getISmsService();
            if (iSms == null) break block3;
            try {
                bl = iSms.enableCellBroadcastForSubscriber(this.getSubscriptionId(), n, n2);
            }
            catch (RemoteException remoteException) {
                bl = bl2;
            }
        }
        return bl;
    }

    @UnsupportedAppUsage
    public boolean enableCellBroadcastRange(int n, int n2, int n3) {
        boolean bl = false;
        boolean bl2 = false;
        if (n2 >= n) {
            block4 : {
                ISms iSms = SmsManager.getISmsService();
                if (iSms == null) break block4;
                try {
                    bl2 = iSms.enableCellBroadcastRangeForSubscriber(this.getSubscriptionId(), n, n2, n3);
                }
                catch (RemoteException remoteException) {
                    bl2 = bl;
                }
            }
            return bl2;
        }
        throw new IllegalArgumentException("endMessageId < startMessageId");
    }

    @UnsupportedAppUsage
    public ArrayList<SmsMessage> getAllMessagesFromIcc() {
        List<SmsRawData> list;
        block3 : {
            List<SmsRawData> list2 = null;
            list = null;
            ISms iSms = SmsManager.getISmsService();
            if (iSms == null) break block3;
            try {
                list = iSms.getAllMessagesFromIccEfForSubscriber(this.getSubscriptionId(), ActivityThread.currentPackageName());
            }
            catch (RemoteException remoteException) {
                list = list2;
            }
        }
        return this.createMessageListFromRawRecords(list);
    }

    public boolean getAutoPersisting() {
        block3 : {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms == null) break block3;
            try {
                boolean bl = iMms.getAutoPersisting();
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public Bundle getCarrierConfigValues() {
        block3 : {
            Object object = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (object == null) break block3;
            try {
                object = object.getCarrierConfigValues(this.getSubscriptionId());
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public String getImsSmsFormat() {
        String string2;
        block3 : {
            String string3 = "unknown";
            ISms iSms = SmsManager.getISmsService();
            string2 = string3;
            if (iSms == null) break block3;
            try {
                string2 = iSms.getImsSmsFormatForSubscriber(this.getSubscriptionId());
            }
            catch (RemoteException remoteException) {
                string2 = string3;
            }
        }
        return string2;
    }

    public void getSmsMessagesForFinancialApp(Bundle bundle, final Executor executor, final FinancialSmsCallback financialSmsCallback) {
        try {
            ISms iSms = SmsManager.getISmsServiceOrThrow();
            int n = this.getSubscriptionId();
            String string2 = ActivityThread.currentPackageName();
            IFinancialSmsCallback.Stub stub = new IFinancialSmsCallback.Stub(){

                static /* synthetic */ void lambda$onGetSmsMessagesForFinancialApp$0(FinancialSmsCallback financialSmsCallback2, CursorWindow cursorWindow) {
                    financialSmsCallback2.onFinancialSmsMessages(cursorWindow);
                }

                static /* synthetic */ void lambda$onGetSmsMessagesForFinancialApp$1(Executor executor2, FinancialSmsCallback financialSmsCallback2, CursorWindow cursorWindow) throws Exception {
                    executor2.execute(new _$$Lambda$SmsManager$9$Ma_xGOTcrGGV8QvZI0NSA6WUbKA(financialSmsCallback2, cursorWindow));
                }

                @Override
                public void onGetSmsMessagesForFinancialApp(CursorWindow cursorWindow) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$SmsManager$9$rvckWwRKQKxMC1PhWEkHayc_gf8(executor, financialSmsCallback, cursorWindow));
                }
            };
            iSms.getSmsMessagesForFinancialApp(n, string2, bundle, stub);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public int getSubscriptionId() {
        try {
            int n = this.mSubId == Integer.MAX_VALUE ? SmsManager.getISmsServiceOrThrow().getPreferredSmsSubscription() : this.mSubId;
            return n;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    public Uri importMultimediaMessage(Uri uri, String string2, long l, boolean bl, boolean bl2) {
        if (uri != null) {
            block4 : {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms == null) break block4;
                try {
                    uri = iMms.importMultimediaMessage(ActivityThread.currentPackageName(), uri, string2, l, bl, bl2);
                    return uri;
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return null;
        }
        throw new IllegalArgumentException("Uri contentUri null");
    }

    public Uri importTextMessage(String object, int n, String string2, long l, boolean bl, boolean bl2) {
        block3 : {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms == null) break block3;
            try {
                object = iMms.importTextMessage(ActivityThread.currentPackageName(), (String)object, n, string2, l, bl, bl2);
                return object;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return null;
    }

    public void injectSmsPdu(byte[] arrby, String string2, PendingIntent pendingIntent) {
        block6 : {
            if (!string2.equals("3gpp") && !string2.equals("3gpp2")) {
                throw new IllegalArgumentException("Invalid pdu format. format must be either 3gpp or 3gpp2");
            }
            ISms iSms = ISms.Stub.asInterface(ServiceManager.getService("isms"));
            if (iSms == null) break block6;
            try {
                iSms.injectSmsPduForSubscriber(this.getSubscriptionId(), arrby, string2, pendingIntent);
            }
            catch (RemoteException remoteException) {
                if (pendingIntent == null) break block6;
                try {
                    pendingIntent.send(2);
                }
                catch (PendingIntent.CanceledException canceledException) {}
            }
        }
    }

    public boolean isImsSmsSupported() {
        boolean bl;
        block3 : {
            boolean bl2 = false;
            bl = false;
            ISms iSms = SmsManager.getISmsService();
            if (iSms == null) break block3;
            try {
                bl = iSms.isImsSmsSupportedForSubscriber(this.getSubscriptionId());
            }
            catch (RemoteException remoteException) {
                bl = bl2;
            }
        }
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isSMSPromptEnabled() {
        try {
            boolean bl = ISms.Stub.asInterface(ServiceManager.getService("isms")).isSMSPromptEnabled();
            return bl;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public void sendDataMessage(final String string2, final String string3, final short s, final byte[] arrby, final PendingIntent pendingIntent, final PendingIntent pendingIntent2) {
        if (!TextUtils.isEmpty(string2)) {
            if (arrby != null && arrby.length != 0) {
                this.resolveSubscriptionForOperation(new SubscriptionResolverResult(ActivityThread.currentApplication().getApplicationContext()){
                    final /* synthetic */ Context val$context;
                    {
                        this.val$context = context;
                    }

                    @Override
                    public void onFailure() {
                        SmsManager.notifySmsErrorNoDefaultSet(this.val$context, pendingIntent);
                    }

                    @Override
                    public void onSuccess(int n) {
                        try {
                            SmsManager.getISmsServiceOrThrow().sendDataForSubscriber(n, ActivityThread.currentPackageName(), string2, string3, 65535 & s, arrby, pendingIntent, pendingIntent2);
                        }
                        catch (RemoteException remoteException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("sendDataMessage: Couldn't send SMS - Exception: ");
                            stringBuilder.append(remoteException.getMessage());
                            Log.e(SmsManager.TAG, stringBuilder.toString());
                            SmsManager.notifySmsGenericError(pendingIntent);
                        }
                    }
                });
                return;
            }
            throw new IllegalArgumentException("Invalid message data");
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    public void sendDataMessageWithSelfPermissions(String charSequence, String string2, short s, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (arrby != null && arrby.length != 0) {
                try {
                    SmsManager.getISmsServiceOrThrow().sendDataForSubscriberWithSelfPermissions(this.getSubscriptionId(), ActivityThread.currentPackageName(), (String)charSequence, string2, s & 65535, arrby, pendingIntent, pendingIntent2);
                }
                catch (RemoteException remoteException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("sendDataMessageWithSelfPermissions: Couldn't send SMS - Exception: ");
                    ((StringBuilder)charSequence).append(remoteException.getMessage());
                    Log.e(TAG, ((StringBuilder)charSequence).toString());
                    SmsManager.notifySmsGenericError(pendingIntent);
                }
                return;
            }
            throw new IllegalArgumentException("Invalid message data");
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    public void sendMultimediaMessage(Context object, Uri uri, String string2, Bundle bundle, PendingIntent pendingIntent) {
        if (uri != null) {
            block4 : {
                object = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (object != null) break block4;
                return;
            }
            try {
                object.sendMessage(this.getSubscriptionId(), ActivityThread.currentPackageName(), uri, string2, bundle, pendingIntent);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalArgumentException("Uri contentUri null");
    }

    public void sendMultipartTextMessage(String string2, String string3, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3) {
        this.sendMultipartTextMessageInternal(string2, string3, arrayList, arrayList2, arrayList3, true, ActivityThread.currentPackageName());
    }

    @UnsupportedAppUsage
    public void sendMultipartTextMessage(String string2, String string3, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3, int n, boolean bl, int n2) {
        this.sendMultipartTextMessageInternal(string2, string3, arrayList, arrayList2, arrayList3, true, n, bl, n2);
    }

    public void sendMultipartTextMessageExternal(String string2, String string3, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3, String string4) {
        if (ActivityThread.currentPackageName() != null) {
            string4 = ActivityThread.currentPackageName();
        }
        this.sendMultipartTextMessageInternal(string2, string3, arrayList, arrayList2, arrayList3, true, string4);
    }

    @SystemApi
    public void sendMultipartTextMessageWithoutPersisting(String string2, String string3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3) {
        this.sendMultipartTextMessageInternal(string2, string3, list, list2, list3, false, ActivityThread.currentPackageName());
    }

    public void sendMultipartTextMessageWithoutPersisting(String string2, String string3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, int n, boolean bl, int n2) {
        this.sendMultipartTextMessageInternal(string2, string3, list, list2, list3, false, n, bl, n2);
    }

    public void sendStoredMultimediaMessage(Uri uri, Bundle bundle, PendingIntent pendingIntent) {
        if (uri != null) {
            block4 : {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms == null) break block4;
                try {
                    iMms.sendStoredMessage(this.getSubscriptionId(), ActivityThread.currentPackageName(), uri, bundle, pendingIntent);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return;
        }
        throw new IllegalArgumentException("Empty message URI");
    }

    public void sendStoredMultipartTextMessage(final Uri uri, final String string2, final ArrayList<PendingIntent> arrayList, final ArrayList<PendingIntent> arrayList2) {
        if (uri != null) {
            this.resolveSubscriptionForOperation(new SubscriptionResolverResult(ActivityThread.currentApplication().getApplicationContext()){
                final /* synthetic */ Context val$context;
                {
                    this.val$context = context;
                }

                @Override
                public void onFailure() {
                    SmsManager.notifySmsErrorNoDefaultSet(this.val$context, arrayList);
                }

                @Override
                public void onSuccess(int n) {
                    try {
                        SmsManager.getISmsServiceOrThrow().sendStoredMultipartText(n, ActivityThread.currentPackageName(), uri, string2, arrayList, arrayList2);
                    }
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("sendStoredTextMessage: Couldn't send SMS - Exception: ");
                        stringBuilder.append(remoteException.getMessage());
                        Log.e(SmsManager.TAG, stringBuilder.toString());
                        SmsManager.notifySmsGenericError(arrayList);
                    }
                }
            });
            return;
        }
        throw new IllegalArgumentException("Empty message URI");
    }

    public void sendStoredTextMessage(final Uri uri, final String string2, final PendingIntent pendingIntent, final PendingIntent pendingIntent2) {
        if (uri != null) {
            this.resolveSubscriptionForOperation(new SubscriptionResolverResult(ActivityThread.currentApplication().getApplicationContext()){
                final /* synthetic */ Context val$context;
                {
                    this.val$context = context;
                }

                @Override
                public void onFailure() {
                    SmsManager.notifySmsErrorNoDefaultSet(this.val$context, pendingIntent);
                }

                @Override
                public void onSuccess(int n) {
                    try {
                        SmsManager.getISmsServiceOrThrow().sendStoredText(n, ActivityThread.currentPackageName(), uri, string2, pendingIntent, pendingIntent2);
                    }
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("sendStoredTextMessage: Couldn't send SMS - Exception: ");
                        stringBuilder.append(remoteException.getMessage());
                        Log.e(SmsManager.TAG, stringBuilder.toString());
                        SmsManager.notifySmsGenericError(pendingIntent);
                    }
                }
            });
            return;
        }
        throw new IllegalArgumentException("Empty message URI");
    }

    public void sendTextMessage(String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        this.sendTextMessageInternal(string2, string3, string4, pendingIntent, pendingIntent2, true, ActivityThread.currentPackageName());
    }

    @UnsupportedAppUsage
    public void sendTextMessage(String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, int n, boolean bl, int n2) {
        this.sendTextMessageInternal(string2, string3, string4, pendingIntent, pendingIntent2, true, n, bl, n2);
    }

    public void sendTextMessageWithSelfPermissions(String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        if (!TextUtils.isEmpty(string2)) {
            if (!TextUtils.isEmpty(string4)) {
                try {
                    SmsManager.getISmsServiceOrThrow().sendTextForSubscriberWithSelfPermissions(this.getSubscriptionId(), ActivityThread.currentPackageName(), string2, string3, string4, pendingIntent, pendingIntent2, bl);
                }
                catch (RemoteException remoteException) {
                    SmsManager.notifySmsGenericError(pendingIntent);
                }
                return;
            }
            throw new IllegalArgumentException("Invalid message body");
        }
        throw new IllegalArgumentException("Invalid destinationAddress");
    }

    public void sendTextMessageWithoutPersisting(String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        this.sendTextMessageInternal(string2, string3, string4, pendingIntent, pendingIntent2, false, ActivityThread.currentPackageName());
    }

    @UnsupportedAppUsage
    public void sendTextMessageWithoutPersisting(String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, int n, boolean bl, int n2) {
        this.sendTextMessageInternal(string2, string3, string4, pendingIntent, pendingIntent2, false, n, bl, n2);
    }

    public void setAutoPersisting(boolean bl) {
        block3 : {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms == null) break block3;
            try {
                iMms.setAutoPersisting(ActivityThread.currentPackageName(), bl);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @UnsupportedAppUsage
    public boolean updateMessageOnIcc(int n, int n2, byte[] arrby) {
        boolean bl;
        block3 : {
            boolean bl2 = false;
            bl = false;
            ISms iSms = SmsManager.getISmsService();
            if (iSms == null) break block3;
            try {
                bl = iSms.updateMessageOnIccEfForSubscriber(this.getSubscriptionId(), ActivityThread.currentPackageName(), n, n2, arrby);
            }
            catch (RemoteException remoteException) {
                bl = bl2;
            }
        }
        return bl;
    }

    public boolean updateStoredMessageStatus(Uri uri, ContentValues contentValues) {
        if (uri != null) {
            block4 : {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms == null) break block4;
                try {
                    boolean bl = iMms.updateStoredMessageStatus(ActivityThread.currentPackageName(), uri, contentValues);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return false;
        }
        throw new IllegalArgumentException("Empty message URI");
    }

    public static abstract class FinancialSmsCallback {
        public abstract void onFinancialSmsMessages(CursorWindow var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SmsShortCodeCategory {
    }

    private static interface SubscriptionResolverResult {
        public void onFailure();

        public void onSuccess(int var1);
    }

}

