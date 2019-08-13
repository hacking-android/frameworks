/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.location.Country;
import android.location.CountryDetector;
import android.net.Uri;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.CallerInfo;
import java.util.List;

public class CallLog {
    public static final String AUTHORITY = "call_log";
    public static final Uri CONTENT_URI = Uri.parse("content://call_log");
    private static final String LOG_TAG = "CallLog";
    public static final String SHADOW_AUTHORITY = "call_log_shadow";
    private static final boolean VERBOSE_LOG = false;

    public static class Calls
    implements BaseColumns {
        public static final String ADD_FOR_ALL_USERS = "add_for_all_users";
        public static final String ALLOW_VOICEMAILS_PARAM_KEY = "allow_voicemails";
        public static final int ANSWERED_EXTERNALLY_TYPE = 7;
        public static final int BLOCKED_TYPE = 6;
        public static final String BLOCK_REASON = "block_reason";
        public static final int BLOCK_REASON_BLOCKED_NUMBER = 3;
        public static final int BLOCK_REASON_CALL_SCREENING_SERVICE = 1;
        public static final int BLOCK_REASON_DIRECT_TO_VOICEMAIL = 2;
        public static final int BLOCK_REASON_NOT_BLOCKED = 0;
        public static final int BLOCK_REASON_NOT_IN_CONTACTS = 7;
        public static final int BLOCK_REASON_PAY_PHONE = 6;
        public static final int BLOCK_REASON_RESTRICTED_NUMBER = 5;
        public static final int BLOCK_REASON_UNKNOWN_NUMBER = 4;
        public static final String CACHED_FORMATTED_NUMBER = "formatted_number";
        public static final String CACHED_LOOKUP_URI = "lookup_uri";
        public static final String CACHED_MATCHED_NUMBER = "matched_number";
        public static final String CACHED_NAME = "name";
        public static final String CACHED_NORMALIZED_NUMBER = "normalized_number";
        public static final String CACHED_NUMBER_LABEL = "numberlabel";
        public static final String CACHED_NUMBER_TYPE = "numbertype";
        public static final String CACHED_PHOTO_ID = "photo_id";
        public static final String CACHED_PHOTO_URI = "photo_uri";
        public static final String CALL_SCREENING_APP_NAME = "call_screening_app_name";
        public static final String CALL_SCREENING_COMPONENT_NAME = "call_screening_component_name";
        public static final Uri CONTENT_FILTER_URI;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/calls";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/calls";
        public static final Uri CONTENT_URI;
        public static final Uri CONTENT_URI_WITH_VOICEMAIL;
        public static final String COUNTRY_ISO = "countryiso";
        public static final String DATA_USAGE = "data_usage";
        public static final String DATE = "date";
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        public static final String DURATION = "duration";
        public static final String EXTRA_CALL_TYPE_FILTER = "android.provider.extra.CALL_TYPE_FILTER";
        public static final String FEATURES = "features";
        public static final int FEATURES_ASSISTED_DIALING_USED = 16;
        public static final int FEATURES_HD_CALL = 4;
        public static final int FEATURES_PULLED_EXTERNALLY = 2;
        public static final int FEATURES_RTT = 32;
        public static final int FEATURES_VIDEO = 1;
        public static final int FEATURES_WIFI = 8;
        public static final String GEOCODED_LOCATION = "geocoded_location";
        public static final int INCOMING_TYPE = 1;
        public static final String IS_READ = "is_read";
        public static final String LAST_MODIFIED = "last_modified";
        public static final String LIMIT_PARAM_KEY = "limit";
        private static final int MIN_DURATION_FOR_NORMALIZED_NUMBER_UPDATE_MS = 10000;
        public static final int MISSED_TYPE = 3;
        public static final String NEW = "new";
        public static final String NUMBER = "number";
        public static final String NUMBER_PRESENTATION = "presentation";
        public static final String OFFSET_PARAM_KEY = "offset";
        public static final int OUTGOING_TYPE = 2;
        public static final String PHONE_ACCOUNT_ADDRESS = "phone_account_address";
        public static final String PHONE_ACCOUNT_COMPONENT_NAME = "subscription_component_name";
        public static final String PHONE_ACCOUNT_HIDDEN = "phone_account_hidden";
        public static final String PHONE_ACCOUNT_ID = "subscription_id";
        public static final String POST_DIAL_DIGITS = "post_dial_digits";
        public static final int PRESENTATION_ALLOWED = 1;
        public static final int PRESENTATION_PAYPHONE = 4;
        public static final int PRESENTATION_RESTRICTED = 2;
        public static final int PRESENTATION_UNKNOWN = 3;
        public static final int REJECTED_TYPE = 5;
        public static final Uri SHADOW_CONTENT_URI;
        public static final String SUB_ID = "sub_id";
        public static final String TRANSCRIPTION = "transcription";
        public static final String TRANSCRIPTION_STATE = "transcription_state";
        public static final String TYPE = "type";
        public static final String VIA_NUMBER = "via_number";
        public static final int VOICEMAIL_TYPE = 4;
        public static final String VOICEMAIL_URI = "voicemail_uri";

        static {
            CONTENT_URI = Uri.parse("content://call_log/calls");
            SHADOW_CONTENT_URI = Uri.parse("content://call_log_shadow/calls");
            CONTENT_FILTER_URI = Uri.parse("content://call_log/calls/filter");
            CONTENT_URI_WITH_VOICEMAIL = CONTENT_URI.buildUpon().appendQueryParameter(ALLOW_VOICEMAILS_PARAM_KEY, "true").build();
        }

        public static Uri addCall(CallerInfo callerInfo, Context context, String string2, int n, int n2, int n3, PhoneAccountHandle phoneAccountHandle, long l, int n4, Long l2) {
            return Calls.addCall(callerInfo, context, string2, "", "", n, n2, n3, phoneAccountHandle, l, n4, l2, false, null, false, 0, null, null);
        }

        public static Uri addCall(CallerInfo callerInfo, Context context, String string2, String string3, String string4, int n, int n2, int n3, PhoneAccountHandle phoneAccountHandle, long l, int n4, Long l2, boolean bl, UserHandle userHandle) {
            return Calls.addCall(callerInfo, context, string2, string3, string4, n, n2, n3, phoneAccountHandle, l, n4, l2, bl, userHandle, false, 0, null, null);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public static Uri addCall(CallerInfo object, Context context, String object2, String object3, String object4, int n, int n2, int n3, PhoneAccountHandle object5, long l, int n4, Long l2, boolean bl, UserHandle userHandle, boolean bl2, int n5, CharSequence charSequence, String string2) {
            ContentValues contentValues;
            block26 : {
                String string3;
                ContentResolver contentResolver = context.getContentResolver();
                String string4 = Calls.getLogAccountAddress(context, (PhoneAccountHandle)object5);
                if ((n = Calls.getLogNumberPresentation((String)object2, n)) != 1) {
                    if (object != null) {
                        ((CallerInfo)object).name = "";
                    }
                    object2 = "";
                }
                if (object5 != null) {
                    string3 = ((PhoneAccountHandle)object5).getComponentName().flattenToString();
                    object5 = ((PhoneAccountHandle)object5).getId();
                } else {
                    string3 = null;
                    object5 = null;
                }
                contentValues = new ContentValues(6);
                contentValues.put(NUMBER, (String)object2);
                contentValues.put(POST_DIAL_DIGITS, (String)object3);
                contentValues.put(VIA_NUMBER, (String)object4);
                contentValues.put(NUMBER_PRESENTATION, n);
                contentValues.put(TYPE, n2);
                contentValues.put(FEATURES, n3);
                contentValues.put(DATE, l);
                contentValues.put(DURATION, Long.valueOf(n4));
                if (l2 != null) {
                    contentValues.put(DATA_USAGE, l2);
                }
                contentValues.put(PHONE_ACCOUNT_COMPONENT_NAME, string3);
                contentValues.put(PHONE_ACCOUNT_ID, (String)object5);
                contentValues.put(PHONE_ACCOUNT_ADDRESS, string4);
                contentValues.put(NEW, 1);
                if (object != null && ((CallerInfo)object).name != null) {
                    contentValues.put(CACHED_NAME, ((CallerInfo)object).name);
                }
                contentValues.put(ADD_FOR_ALL_USERS, (int)bl);
                if (n2 == 3) {
                    contentValues.put(IS_READ, (int)bl2);
                }
                contentValues.put(BLOCK_REASON, n5);
                contentValues.put(CALL_SCREENING_APP_NAME, Calls.charSequenceToString(charSequence));
                contentValues.put(CALL_SCREENING_COMPONENT_NAME, string2);
                if (object != null && ((CallerInfo)object).contactIdOrZero > 0L) {
                    if (((CallerInfo)object).normalizedNumber != null) {
                        object3 = ((CallerInfo)object).normalizedNumber;
                        object4 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        l = ((CallerInfo)object).contactIdOrZero;
                        object3 = contentResolver.query((Uri)object4, new String[]{"_id"}, "contact_id =? AND data4 =?", new String[]{String.valueOf(l), object3}, null);
                    } else {
                        object3 = ((CallerInfo)object).phoneNumber != null ? ((CallerInfo)object).phoneNumber : object2;
                        object3 = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Callable.CONTENT_FILTER_URI, Uri.encode((String)object3));
                        l = ((CallerInfo)object).contactIdOrZero;
                        object3 = contentResolver.query((Uri)object3, new String[]{"_id"}, "contact_id =?", new String[]{String.valueOf(l)}, null);
                    }
                    if (object3 != null) {
                        if (object3.getCount() <= 0 || !object3.moveToFirst()) break block26;
                        object4 = object3.getString(0);
                        Calls.updateDataUsageStatForData(contentResolver, (String)object4);
                        if (n4 < 10000 || n2 != 2) break block26;
                        try {
                            if (TextUtils.isEmpty(((CallerInfo)object).normalizedNumber)) {
                                Calls.updateNormalizedNumber(context, contentResolver, (String)object4, (String)object2);
                            }
                        }
                        finally {
                            object3.close();
                        }
                    }
                }
            }
            object = null;
            object5 = context.getSystemService(UserManager.class);
            n2 = ((UserManager)object5).getUserHandle();
            if (bl) {
                object2 = Calls.addEntryAndRemoveExpiredEntries(context, (UserManager)object5, UserHandle.SYSTEM, contentValues);
                if (object2 == null) return null;
                if (CallLog.SHADOW_AUTHORITY.equals(((Uri)object2).getAuthority())) return null;
                if (n2 == 0) {
                    object = object2;
                }
                object3 = ((UserManager)object5).getUsers(true);
                n3 = object3.size();
                n = 0;
                while (n < n3) {
                    object4 = ((UserInfo)object3.get(n)).getUserHandle();
                    n4 = ((UserHandle)object4).getIdentifier();
                    if (((UserHandle)object4).isSystem()) {
                        object2 = object;
                    } else if (!Calls.shouldHaveSharedCallLogEntries(context, (UserManager)object5, n4)) {
                        object2 = object;
                    } else {
                        object2 = object;
                        if (((UserManager)object5).isUserRunning((UserHandle)object4)) {
                            object2 = object;
                            if (((UserManager)object5).isUserUnlocked((UserHandle)object4)) {
                                object4 = Calls.addEntryAndRemoveExpiredEntries(context, (UserManager)object5, (UserHandle)object4, contentValues);
                                object2 = object;
                                if (n4 == n2) {
                                    object2 = object4;
                                }
                            }
                        }
                    }
                    ++n;
                    object = object2;
                }
                return object;
            }
            if (userHandle != null) return Calls.addEntryAndRemoveExpiredEntries(context, (UserManager)object5, userHandle, contentValues);
            userHandle = UserHandle.of(n2);
            return Calls.addEntryAndRemoveExpiredEntries(context, (UserManager)object5, userHandle, contentValues);
        }

        private static Uri addEntryAndRemoveExpiredEntries(Context object, UserManager object2, UserHandle userHandle, ContentValues contentValues) {
            ContentResolver contentResolver = ((Context)object).getContentResolver();
            object = ((UserManager)object2).isUserUnlocked(userHandle) ? CONTENT_URI : SHADOW_CONTENT_URI;
            object = ContentProvider.maybeAddUserId((Uri)object, userHandle.getIdentifier());
            try {
                object2 = contentResolver.insert((Uri)object, contentValues);
                if (contentValues.containsKey(PHONE_ACCOUNT_ID) && !TextUtils.isEmpty(contentValues.getAsString(PHONE_ACCOUNT_ID)) && contentValues.containsKey(PHONE_ACCOUNT_COMPONENT_NAME) && !TextUtils.isEmpty(contentValues.getAsString(PHONE_ACCOUNT_COMPONENT_NAME))) {
                    contentResolver.delete((Uri)object, "_id IN (SELECT _id FROM calls WHERE subscription_component_name = ? AND subscription_id = ? ORDER BY date DESC LIMIT -1 OFFSET 500)", new String[]{contentValues.getAsString(PHONE_ACCOUNT_COMPONENT_NAME), contentValues.getAsString(PHONE_ACCOUNT_ID)});
                } else {
                    contentResolver.delete((Uri)object, "_id IN (SELECT _id FROM calls ORDER BY date DESC LIMIT -1 OFFSET 500)", null);
                }
                return object2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.w(CallLog.LOG_TAG, "Failed to insert calllog", illegalArgumentException);
                return null;
            }
        }

        private static String charSequenceToString(CharSequence charSequence) {
            charSequence = charSequence == null ? null : charSequence.toString();
            return charSequence;
        }

        private static String getCurrentCountryIso(Context object) {
            Object var1_1 = null;
            Object object2 = (CountryDetector)((Context)object).getSystemService("country_detector");
            object = var1_1;
            if (object2 != null) {
                object2 = ((CountryDetector)object2).detectCountry();
                object = var1_1;
                if (object2 != null) {
                    object = ((Country)object2).getCountryIso();
                }
            }
            return object;
        }

        public static String getLastOutgoingCall(Context object) {
            Object object2;
            block6 : {
                object2 = ((Context)object).getContentResolver();
                object = null;
                try {
                    object2 = ((ContentResolver)object2).query(CONTENT_URI, new String[]{NUMBER}, "type = 2", null, "date DESC LIMIT 1");
                    if (object2 == null) break block6;
                    object = object2;
                }
                catch (Throwable throwable) {
                    if (object != null) {
                        object.close();
                    }
                    throw throwable;
                }
                if (!object2.moveToFirst()) break block6;
                object = object2;
                String string2 = object2.getString(0);
                object2.close();
                return string2;
            }
            if (object2 != null) {
                object2.close();
            }
            return "";
        }

        private static String getLogAccountAddress(Context object, PhoneAccountHandle parcelable) {
            Object object2 = null;
            try {
                object2 = object = TelecomManager.from((Context)object);
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                // empty catch block
            }
            Object var3_4 = null;
            object = var3_4;
            if (object2 != null) {
                object = var3_4;
                if (parcelable != null) {
                    parcelable = ((TelecomManager)object2).getPhoneAccount((PhoneAccountHandle)parcelable);
                    object = var3_4;
                    if (parcelable != null) {
                        parcelable = ((PhoneAccount)parcelable).getSubscriptionAddress();
                        object = var3_4;
                        if (parcelable != null) {
                            object = ((Uri)parcelable).getSchemeSpecificPart();
                        }
                    }
                }
            }
            return object;
        }

        private static int getLogNumberPresentation(String string2, int n) {
            if (n == 2) {
                return n;
            }
            if (n == 4) {
                return n;
            }
            if (!TextUtils.isEmpty(string2) && n != 3) {
                return 1;
            }
            return 3;
        }

        public static boolean shouldHaveSharedCallLogEntries(Context object, UserManager userManager, int n) {
            boolean bl = userManager.hasUserRestriction("no_outgoing_calls", UserHandle.of(n));
            boolean bl2 = false;
            if (bl) {
                return false;
            }
            object = userManager.getUserInfo(n);
            bl = bl2;
            if (object != null) {
                bl = bl2;
                if (!((UserInfo)object).isManagedProfile()) {
                    bl = true;
                }
            }
            return bl;
        }

        private static void updateDataUsageStatForData(ContentResolver contentResolver, String string2) {
            contentResolver.update(ContactsContract.DataUsageFeedback.FEEDBACK_URI.buildUpon().appendPath(string2).appendQueryParameter(TYPE, "call").build(), new ContentValues(), null, null);
        }

        private static void updateNormalizedNumber(Context object, ContentResolver contentResolver, String string2, String string3) {
            if (!TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string2)) {
                if (TextUtils.isEmpty((CharSequence)(object = Calls.getCurrentCountryIso((Context)object)))) {
                    return;
                }
                if (TextUtils.isEmpty(string3 = PhoneNumberUtils.formatNumberToE164(string3, (String)object))) {
                    return;
                }
                object = new ContentValues();
                ((ContentValues)object).put("data4", string3);
                contentResolver.update(ContactsContract.Data.CONTENT_URI, (ContentValues)object, "_id=?", new String[]{string2});
                return;
            }
        }
    }

}

