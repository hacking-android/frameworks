/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.telecom.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BlockedNumberContract {
    public static final String AUTHORITY = "com.android.blockednumber";
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.android.blockednumber");
    public static final String EXTRA_CALL_PRESENTATION = "extra_call_presentation";
    public static final String EXTRA_CONTACT_EXIST = "extra_contact_exist";
    public static final String EXTRA_ENHANCED_SETTING_KEY = "extra_enhanced_setting_key";
    public static final String EXTRA_ENHANCED_SETTING_VALUE = "extra_enhanced_setting_value";
    private static final String LOG_TAG = BlockedNumberContract.class.getSimpleName();
    public static final String METHOD_CAN_CURRENT_USER_BLOCK_NUMBERS = "can_current_user_block_numbers";
    public static final String METHOD_IS_BLOCKED = "is_blocked";
    public static final String METHOD_UNBLOCK = "unblock";
    public static final String RES_BLOCK_STATUS = "block_status";
    public static final String RES_CAN_BLOCK_NUMBERS = "can_block";
    public static final String RES_ENHANCED_SETTING_IS_ENABLED = "enhanced_setting_enabled";
    public static final String RES_NUMBER_IS_BLOCKED = "blocked";
    public static final String RES_NUM_ROWS_DELETED = "num_deleted";
    public static final String RES_SHOW_EMERGENCY_CALL_NOTIFICATION = "show_emergency_call_notification";
    public static final int STATUS_BLOCKED_IN_LIST = 1;
    public static final int STATUS_BLOCKED_NOT_IN_CONTACTS = 5;
    public static final int STATUS_BLOCKED_PAYPHONE = 4;
    public static final int STATUS_BLOCKED_RESTRICTED = 2;
    public static final int STATUS_BLOCKED_UNKNOWN_NUMBER = 3;
    public static final int STATUS_NOT_BLOCKED = 0;

    private BlockedNumberContract() {
    }

    public static boolean canCurrentUserBlockNumbers(Context object) {
        boolean bl;
        block3 : {
            boolean bl2 = false;
            object = ((Context)object).getContentResolver().call(AUTHORITY_URI, METHOD_CAN_CURRENT_USER_BLOCK_NUMBERS, null, null);
            bl = bl2;
            if (object == null) break block3;
            try {
                boolean bl3 = ((BaseBundle)object).getBoolean(RES_CAN_BLOCK_NUMBERS, false);
                bl = bl2;
                if (!bl3) break block3;
                bl = true;
            }
            catch (IllegalArgumentException | NullPointerException runtimeException) {
                Log.w(null, "canCurrentUserBlockNumbers: provider not ready.", new Object[0]);
                return false;
            }
        }
        return bl;
    }

    public static boolean isBlocked(Context object, String string2) {
        boolean bl;
        block5 : {
            block4 : {
                object = ((Context)object).getContentResolver().call(AUTHORITY_URI, METHOD_IS_BLOCKED, string2, null);
                if (object == null) break block4;
                if (!((BaseBundle)object).getBoolean(RES_NUMBER_IS_BLOCKED, false)) break block4;
                bl = true;
                break block5;
            }
            bl = false;
        }
        try {
            Log.d(LOG_TAG, "isBlocked: phoneNumber=%s, isBlocked=%b", Log.piiHandle(string2), bl);
            return bl;
        }
        catch (IllegalArgumentException | NullPointerException runtimeException) {
            Log.w(null, "isBlocked: provider not ready.", new Object[0]);
            return false;
        }
    }

    public static int unblock(Context context, String string2) {
        Log.d(LOG_TAG, "unblock: phoneNumber=%s", Log.piiHandle(string2));
        return context.getContentResolver().call(AUTHORITY_URI, METHOD_UNBLOCK, string2, null).getInt(RES_NUM_ROWS_DELETED, 0);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BlockStatus {
    }

    public static class BlockedNumbers {
        public static final String COLUMN_E164_NUMBER = "e164_number";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ORIGINAL_NUMBER = "original_number";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/blocked_number";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/blocked_number";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "blocked");

        private BlockedNumbers() {
        }
    }

    public static class SystemContract {
        public static final String ACTION_BLOCK_SUPPRESSION_STATE_CHANGED = "android.provider.action.BLOCK_SUPPRESSION_STATE_CHANGED";
        public static final String ENHANCED_SETTING_KEY_BLOCK_PAYPHONE = "block_payphone_calls_setting";
        public static final String ENHANCED_SETTING_KEY_BLOCK_PRIVATE = "block_private_number_calls_setting";
        public static final String ENHANCED_SETTING_KEY_BLOCK_UNKNOWN = "block_unknown_calls_setting";
        public static final String ENHANCED_SETTING_KEY_BLOCK_UNREGISTERED = "block_numbers_not_in_contacts_setting";
        public static final String ENHANCED_SETTING_KEY_SHOW_EMERGENCY_CALL_NOTIFICATION = "show_emergency_call_notification";
        public static final String METHOD_END_BLOCK_SUPPRESSION = "end_block_suppression";
        public static final String METHOD_GET_BLOCK_SUPPRESSION_STATUS = "get_block_suppression_status";
        public static final String METHOD_GET_ENHANCED_BLOCK_SETTING = "get_enhanced_block_setting";
        public static final String METHOD_NOTIFY_EMERGENCY_CONTACT = "notify_emergency_contact";
        public static final String METHOD_SET_ENHANCED_BLOCK_SETTING = "set_enhanced_block_setting";
        public static final String METHOD_SHOULD_SHOW_EMERGENCY_CALL_NOTIFICATION = "should_show_emergency_call_notification";
        public static final String METHOD_SHOULD_SYSTEM_BLOCK_NUMBER = "should_system_block_number";
        public static final String RES_BLOCKING_SUPPRESSED_UNTIL_TIMESTAMP = "blocking_suppressed_until_timestamp";
        public static final String RES_IS_BLOCKING_SUPPRESSED = "blocking_suppressed";

        public static String blockStatusToString(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                if (n != 5) {
                                    return "unknown";
                                }
                                return "blocked - not in contacts";
                            }
                            return "blocked - payphone";
                        }
                        return "blocked - unknown";
                    }
                    return "blocked - restricted";
                }
                return "blocked - in list";
            }
            return "not blocked";
        }

        public static void endBlockSuppression(Context context) {
            String string2 = context.getOpPackageName();
            Log.i(LOG_TAG, "endBlockSuppression: caller=%s", string2);
            context.getContentResolver().call(AUTHORITY_URI, METHOD_END_BLOCK_SUPPRESSION, null, null);
        }

        public static BlockSuppressionStatus getBlockSuppressionStatus(Context context) {
            Object object = context.getContentResolver().call(AUTHORITY_URI, METHOD_GET_BLOCK_SUPPRESSION_STATUS, null, null);
            object = new BlockSuppressionStatus(((BaseBundle)object).getBoolean(RES_IS_BLOCKING_SUPPRESSED, false), ((BaseBundle)object).getLong(RES_BLOCKING_SUPPRESSED_UNTIL_TIMESTAMP, 0L));
            Log.d(LOG_TAG, "getBlockSuppressionStatus: caller=%s, status=%s", context.getOpPackageName(), object);
            return object;
        }

        public static boolean getEnhancedBlockSetting(Context object, String string2) {
            boolean bl;
            block3 : {
                Bundle bundle = new Bundle();
                bundle.putString(BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, string2);
                boolean bl2 = false;
                object = ((Context)object).getContentResolver().call(AUTHORITY_URI, METHOD_GET_ENHANCED_BLOCK_SETTING, null, bundle);
                bl = bl2;
                if (object == null) break block3;
                try {
                    boolean bl3 = ((BaseBundle)object).getBoolean(BlockedNumberContract.RES_ENHANCED_SETTING_IS_ENABLED, false);
                    bl = bl2;
                    if (!bl3) break block3;
                    bl = true;
                }
                catch (IllegalArgumentException | NullPointerException runtimeException) {
                    Log.w(null, "getEnhancedBlockSetting: provider not ready.", new Object[0]);
                    return false;
                }
            }
            return bl;
        }

        public static void notifyEmergencyContact(Context context) {
            try {
                Log.i(LOG_TAG, "notifyEmergencyContact; caller=%s", context.getOpPackageName());
                context.getContentResolver().call(AUTHORITY_URI, METHOD_NOTIFY_EMERGENCY_CONTACT, null, null);
            }
            catch (IllegalArgumentException | NullPointerException runtimeException) {
                Log.w(null, "notifyEmergencyContact: provider not ready.", new Object[0]);
            }
        }

        public static void setEnhancedBlockSetting(Context context, String string2, boolean bl) {
            Bundle bundle = new Bundle();
            bundle.putString(BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, string2);
            bundle.putBoolean(BlockedNumberContract.EXTRA_ENHANCED_SETTING_VALUE, bl);
            context.getContentResolver().call(AUTHORITY_URI, METHOD_SET_ENHANCED_BLOCK_SETTING, null, bundle);
        }

        public static boolean shouldShowEmergencyCallNotification(Context object) {
            boolean bl;
            block3 : {
                boolean bl2 = false;
                object = ((Context)object).getContentResolver().call(AUTHORITY_URI, METHOD_SHOULD_SHOW_EMERGENCY_CALL_NOTIFICATION, null, null);
                bl = bl2;
                if (object == null) break block3;
                try {
                    boolean bl3 = ((BaseBundle)object).getBoolean("show_emergency_call_notification", false);
                    bl = bl2;
                    if (!bl3) break block3;
                    bl = true;
                }
                catch (IllegalArgumentException | NullPointerException runtimeException) {
                    Log.w(null, "shouldShowEmergencyCallNotification: provider not ready.", new Object[0]);
                    return false;
                }
            }
            return bl;
        }

        public static int shouldSystemBlockNumber(Context object, String string2, Bundle bundle) {
            String string3;
            int n;
            block5 : {
                block4 : {
                    string3 = ((Context)object).getOpPackageName();
                    object = ((Context)object).getContentResolver().call(AUTHORITY_URI, METHOD_SHOULD_SYSTEM_BLOCK_NUMBER, string2, bundle);
                    if (object == null) break block4;
                    n = ((BaseBundle)object).getInt(BlockedNumberContract.RES_BLOCK_STATUS, 0);
                    break block5;
                }
                n = 0;
            }
            try {
                Log.d(LOG_TAG, "shouldSystemBlockNumber: number=%s, caller=%s, result=%s", Log.piiHandle(string2), string3, SystemContract.blockStatusToString(n));
                return n;
            }
            catch (IllegalArgumentException | NullPointerException runtimeException) {
                Log.w(null, "shouldSystemBlockNumber: provider not ready.", new Object[0]);
                return 0;
            }
        }

        public static class BlockSuppressionStatus {
            public final boolean isSuppressed;
            public final long untilTimestampMillis;

            public BlockSuppressionStatus(boolean bl, long l) {
                this.isSuppressed = bl;
                this.untilTimestampMillis = l;
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[BlockSuppressionStatus; isSuppressed=");
                stringBuilder.append(this.isSuppressed);
                stringBuilder.append(", until=");
                stringBuilder.append(this.untilTimestampMillis);
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        }

    }

}

