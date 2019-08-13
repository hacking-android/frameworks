/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.provider.OpenableColumns;
import android.telecom.PhoneAccountHandle;
import android.telecom.Voicemail;
import java.util.List;

public class VoicemailContract {
    public static final String ACTION_FETCH_VOICEMAIL = "android.intent.action.FETCH_VOICEMAIL";
    public static final String ACTION_NEW_VOICEMAIL = "android.intent.action.NEW_VOICEMAIL";
    public static final String ACTION_SYNC_VOICEMAIL = "android.provider.action.SYNC_VOICEMAIL";
    public static final String ACTION_VOICEMAIL_SMS_RECEIVED = "com.android.internal.provider.action.VOICEMAIL_SMS_RECEIVED";
    public static final String AUTHORITY = "com.android.voicemail";
    public static final String EXTRA_PHONE_ACCOUNT_HANDLE = "android.provider.extra.PHONE_ACCOUNT_HANDLE";
    public static final String EXTRA_SELF_CHANGE = "com.android.voicemail.extra.SELF_CHANGE";
    public static final String EXTRA_TARGET_PACKAGE = "android.provider.extra.TARGET_PACAKGE";
    public static final String EXTRA_VOICEMAIL_SMS = "android.provider.extra.VOICEMAIL_SMS";
    public static final String PARAM_KEY_SOURCE_PACKAGE = "source_package";
    public static final String SOURCE_PACKAGE_FIELD = "source_package";

    private VoicemailContract() {
    }

    public static final class Status
    implements BaseColumns {
        public static final String CONFIGURATION_STATE = "configuration_state";
        public static final int CONFIGURATION_STATE_CAN_BE_CONFIGURED = 2;
        public static final int CONFIGURATION_STATE_CONFIGURING = 3;
        public static final int CONFIGURATION_STATE_DISABLED = 5;
        public static final int CONFIGURATION_STATE_FAILED = 4;
        public static final int CONFIGURATION_STATE_NOT_CONFIGURED = 1;
        public static final int CONFIGURATION_STATE_OK = 0;
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.voicemail/status");
        public static final String DATA_CHANNEL_STATE = "data_channel_state";
        public static final int DATA_CHANNEL_STATE_BAD_CONFIGURATION = 3;
        public static final int DATA_CHANNEL_STATE_COMMUNICATION_ERROR = 4;
        public static final int DATA_CHANNEL_STATE_NO_CONNECTION = 1;
        public static final int DATA_CHANNEL_STATE_NO_CONNECTION_CELLULAR_REQUIRED = 2;
        public static final int DATA_CHANNEL_STATE_OK = 0;
        public static final int DATA_CHANNEL_STATE_SERVER_CONNECTION_ERROR = 6;
        public static final int DATA_CHANNEL_STATE_SERVER_ERROR = 5;
        public static final String DIR_TYPE = "vnd.android.cursor.dir/voicemail.source.status";
        public static final String ITEM_TYPE = "vnd.android.cursor.item/voicemail.source.status";
        public static final String NOTIFICATION_CHANNEL_STATE = "notification_channel_state";
        public static final int NOTIFICATION_CHANNEL_STATE_MESSAGE_WAITING = 2;
        public static final int NOTIFICATION_CHANNEL_STATE_NO_CONNECTION = 1;
        public static final int NOTIFICATION_CHANNEL_STATE_OK = 0;
        public static final String PHONE_ACCOUNT_COMPONENT_NAME = "phone_account_component_name";
        public static final String PHONE_ACCOUNT_ID = "phone_account_id";
        public static final String QUOTA_OCCUPIED = "quota_occupied";
        public static final String QUOTA_TOTAL = "quota_total";
        public static final int QUOTA_UNAVAILABLE = -1;
        public static final String SETTINGS_URI = "settings_uri";
        public static final String SOURCE_PACKAGE = "source_package";
        public static final String SOURCE_TYPE = "source_type";
        public static final String VOICEMAIL_ACCESS_URI = "voicemail_access_uri";

        private Status() {
        }

        public static Uri buildSourceUri(String string2) {
            return CONTENT_URI.buildUpon().appendQueryParameter("source_package", string2).build();
        }
    }

    public static final class Voicemails
    implements BaseColumns,
    OpenableColumns {
        public static final String ARCHIVED = "archived";
        public static final String BACKED_UP = "backed_up";
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.voicemail/voicemail");
        public static final String DATE = "date";
        public static final String DELETED = "deleted";
        public static final String DIRTY = "dirty";
        public static final int DIRTY_RETAIN = -1;
        public static final String DIR_TYPE = "vnd.android.cursor.dir/voicemails";
        public static final String DURATION = "duration";
        public static final String HAS_CONTENT = "has_content";
        public static final String IS_OMTP_VOICEMAIL = "is_omtp_voicemail";
        public static final String IS_READ = "is_read";
        public static final String ITEM_TYPE = "vnd.android.cursor.item/voicemail";
        public static final String LAST_MODIFIED = "last_modified";
        public static final String MIME_TYPE = "mime_type";
        public static final String NEW = "new";
        public static final String NUMBER = "number";
        public static final String PHONE_ACCOUNT_COMPONENT_NAME = "subscription_component_name";
        public static final String PHONE_ACCOUNT_ID = "subscription_id";
        public static final String RESTORED = "restored";
        public static final String SOURCE_DATA = "source_data";
        public static final String SOURCE_PACKAGE = "source_package";
        public static final String STATE = "state";
        public static int STATE_DELETED = 0;
        public static int STATE_INBOX = 0;
        public static int STATE_UNDELETED = 0;
        public static final String TRANSCRIPTION = "transcription";
        public static final int TRANSCRIPTION_AVAILABLE = 3;
        public static final int TRANSCRIPTION_FAILED = 2;
        public static final int TRANSCRIPTION_IN_PROGRESS = 1;
        public static final int TRANSCRIPTION_NOT_STARTED = 0;
        public static final String TRANSCRIPTION_STATE = "transcription_state";
        public static final String _DATA = "_data";

        static {
            STATE_INBOX = 0;
            STATE_DELETED = 1;
            STATE_UNDELETED = 2;
        }

        private Voicemails() {
        }

        public static Uri buildSourceUri(String string2) {
            return CONTENT_URI.buildUpon().appendQueryParameter("source_package", string2).build();
        }

        public static int deleteAll(Context context) {
            return context.getContentResolver().delete(Voicemails.buildSourceUri(context.getPackageName()), "", new String[0]);
        }

        private static ContentValues getContentValues(Voicemail voicemail) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DATE, String.valueOf(voicemail.getTimestampMillis()));
            contentValues.put(NUMBER, voicemail.getNumber());
            contentValues.put(DURATION, String.valueOf(voicemail.getDuration()));
            contentValues.put("source_package", voicemail.getSourcePackage());
            contentValues.put(SOURCE_DATA, voicemail.getSourceData());
            contentValues.put(IS_READ, (int)voicemail.isRead());
            PhoneAccountHandle phoneAccountHandle = voicemail.getPhoneAccount();
            if (phoneAccountHandle != null) {
                contentValues.put(PHONE_ACCOUNT_COMPONENT_NAME, phoneAccountHandle.getComponentName().flattenToString());
                contentValues.put(PHONE_ACCOUNT_ID, phoneAccountHandle.getId());
            }
            if (voicemail.getTranscription() != null) {
                contentValues.put(TRANSCRIPTION, voicemail.getTranscription());
            }
            return contentValues;
        }

        public static int insert(Context context, List<Voicemail> list) {
            ContentResolver contentResolver = context.getContentResolver();
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                ContentValues contentValues = Voicemails.getContentValues(list.get(i));
                contentResolver.insert(Voicemails.buildSourceUri(context.getPackageName()), contentValues);
            }
            return n;
        }

        public static Uri insert(Context context, Voicemail parcelable) {
            ContentResolver contentResolver = context.getContentResolver();
            parcelable = Voicemails.getContentValues(parcelable);
            return contentResolver.insert(Voicemails.buildSourceUri(context.getPackageName()), (ContentValues)parcelable);
        }
    }

}

