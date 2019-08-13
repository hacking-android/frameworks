/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SqliteWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcel;
import android.provider.BaseColumns;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Patterns;
import com.android.internal.telephony.SmsApplication;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Telephony {
    private static final String TAG = "Telephony";

    private Telephony() {
    }

    public static interface BaseMmsColumns
    extends BaseColumns {
        @Deprecated
        public static final String ADAPTATION_ALLOWED = "adp_a";
        @Deprecated
        public static final String APPLIC_ID = "apl_id";
        @Deprecated
        public static final String AUX_APPLIC_ID = "aux_apl_id";
        @Deprecated
        public static final String CANCEL_ID = "cl_id";
        @Deprecated
        public static final String CANCEL_STATUS = "cl_st";
        public static final String CONTENT_CLASS = "ct_cls";
        public static final String CONTENT_LOCATION = "ct_l";
        public static final String CONTENT_TYPE = "ct_t";
        public static final String CREATOR = "creator";
        public static final String DATE = "date";
        public static final String DATE_SENT = "date_sent";
        public static final String DELIVERY_REPORT = "d_rpt";
        public static final String DELIVERY_TIME = "d_tm";
        @Deprecated
        public static final String DELIVERY_TIME_TOKEN = "d_tm_tok";
        @Deprecated
        public static final String DISTRIBUTION_INDICATOR = "d_ind";
        @Deprecated
        public static final String DRM_CONTENT = "drm_c";
        @Deprecated
        public static final String ELEMENT_DESCRIPTOR = "e_des";
        public static final String EXPIRY = "exp";
        @Deprecated
        public static final String LIMIT = "limit";
        public static final String LOCKED = "locked";
        @Deprecated
        public static final String MBOX_QUOTAS = "mb_qt";
        @Deprecated
        public static final String MBOX_QUOTAS_TOKEN = "mb_qt_tok";
        @Deprecated
        public static final String MBOX_TOTALS = "mb_t";
        @Deprecated
        public static final String MBOX_TOTALS_TOKEN = "mb_t_tok";
        public static final String MESSAGE_BOX = "msg_box";
        public static final int MESSAGE_BOX_ALL = 0;
        public static final int MESSAGE_BOX_DRAFTS = 3;
        public static final int MESSAGE_BOX_FAILED = 5;
        public static final int MESSAGE_BOX_INBOX = 1;
        public static final int MESSAGE_BOX_OUTBOX = 4;
        public static final int MESSAGE_BOX_SENT = 2;
        public static final String MESSAGE_CLASS = "m_cls";
        @Deprecated
        public static final String MESSAGE_COUNT = "m_cnt";
        public static final String MESSAGE_ID = "m_id";
        public static final String MESSAGE_SIZE = "m_size";
        public static final String MESSAGE_TYPE = "m_type";
        public static final String MMS_VERSION = "v";
        @Deprecated
        public static final String MM_FLAGS = "mm_flg";
        @Deprecated
        public static final String MM_FLAGS_TOKEN = "mm_flg_tok";
        @Deprecated
        public static final String MM_STATE = "mm_st";
        @Deprecated
        public static final String PREVIOUSLY_SENT_BY = "p_s_by";
        @Deprecated
        public static final String PREVIOUSLY_SENT_DATE = "p_s_d";
        public static final String PRIORITY = "pri";
        @Deprecated
        public static final String QUOTAS = "qt";
        public static final String READ = "read";
        public static final String READ_REPORT = "rr";
        public static final String READ_STATUS = "read_status";
        @Deprecated
        public static final String RECOMMENDED_RETRIEVAL_MODE = "r_r_mod";
        @Deprecated
        public static final String RECOMMENDED_RETRIEVAL_MODE_TEXT = "r_r_mod_txt";
        @Deprecated
        public static final String REPLACE_ID = "repl_id";
        @Deprecated
        public static final String REPLY_APPLIC_ID = "r_apl_id";
        @Deprecated
        public static final String REPLY_CHARGING = "r_chg";
        @Deprecated
        public static final String REPLY_CHARGING_DEADLINE = "r_chg_dl";
        @Deprecated
        public static final String REPLY_CHARGING_DEADLINE_TOKEN = "r_chg_dl_tok";
        @Deprecated
        public static final String REPLY_CHARGING_ID = "r_chg_id";
        @Deprecated
        public static final String REPLY_CHARGING_SIZE = "r_chg_sz";
        public static final String REPORT_ALLOWED = "rpt_a";
        public static final String RESPONSE_STATUS = "resp_st";
        public static final String RESPONSE_TEXT = "resp_txt";
        public static final String RETRIEVE_STATUS = "retr_st";
        public static final String RETRIEVE_TEXT = "retr_txt";
        public static final String RETRIEVE_TEXT_CHARSET = "retr_txt_cs";
        public static final String SEEN = "seen";
        @Deprecated
        public static final String SENDER_VISIBILITY = "s_vis";
        @Deprecated
        public static final String START = "start";
        public static final String STATUS = "st";
        @Deprecated
        public static final String STATUS_TEXT = "st_txt";
        @Deprecated
        public static final String STORE = "store";
        @Deprecated
        public static final String STORED = "stored";
        @Deprecated
        public static final String STORE_STATUS = "store_st";
        @Deprecated
        public static final String STORE_STATUS_TEXT = "store_st_txt";
        public static final String SUBJECT = "sub";
        public static final String SUBJECT_CHARSET = "sub_cs";
        public static final String SUBSCRIPTION_ID = "sub_id";
        public static final String TEXT_ONLY = "text_only";
        public static final String THREAD_ID = "thread_id";
        @Deprecated
        public static final String TOTALS = "totals";
        public static final String TRANSACTION_ID = "tr_id";
    }

    public static interface CanonicalAddressesColumns
    extends BaseColumns {
        public static final String ADDRESS = "address";
    }

    public static interface CarrierColumns
    extends BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://carrier_information/carrier");
        public static final String EXPIRATION_TIME = "expiration_time";
        public static final String KEY_IDENTIFIER = "key_identifier";
        public static final String KEY_TYPE = "key_type";
        public static final String LAST_MODIFIED = "last_modified";
        public static final String MCC = "mcc";
        public static final String MNC = "mnc";
        public static final String MVNO_MATCH_DATA = "mvno_match_data";
        public static final String MVNO_TYPE = "mvno_type";
        public static final String PUBLIC_KEY = "public_key";
    }

    public static final class CarrierId
    implements BaseColumns {
        public static final String AUTHORITY = "carrier_id";
        public static final String CARRIER_ID = "carrier_id";
        public static final String CARRIER_NAME = "carrier_name";
        public static final Uri CONTENT_URI = Uri.parse("content://carrier_id");
        public static final String PARENT_CARRIER_ID = "parent_carrier_id";
        public static final String SPECIFIC_CARRIER_ID = "specific_carrier_id";
        public static final String SPECIFIC_CARRIER_ID_NAME = "specific_carrier_id_name";

        private CarrierId() {
        }

        public static Uri getSpecificCarrierIdUriForSubscriptionId(int n) {
            return Uri.withAppendedPath(Uri.withAppendedPath(CONTENT_URI, "specific"), String.valueOf(n));
        }

        public static Uri getUriForSubscriptionId(int n) {
            return CONTENT_URI.buildUpon().appendEncodedPath(String.valueOf(n)).build();
        }

        public static final class All
        implements BaseColumns {
            public static final String APN = "apn";
            public static final Uri CONTENT_URI = Uri.parse("content://carrier_id/all");
            public static final String GID1 = "gid1";
            public static final String GID2 = "gid2";
            public static final String ICCID_PREFIX = "iccid_prefix";
            public static final String IMSI_PREFIX_XPATTERN = "imsi_prefix_xpattern";
            public static final String MCCMNC = "mccmnc";
            public static final String PLMN = "plmn";
            public static final String PRIVILEGE_ACCESS_RULE = "privilege_access_rule";
            public static final String SPN = "spn";
        }

    }

    public static final class Carriers
    implements BaseColumns {
        public static final String APN = "apn";
        @SystemApi
        public static final String APN_SET_ID = "apn_set_id";
        public static final String AUTH_TYPE = "authtype";
        @Deprecated
        public static final String BEARER = "bearer";
        @Deprecated
        public static final String BEARER_BITMASK = "bearer_bitmask";
        public static final int CARRIER_DELETED = 5;
        public static final int CARRIER_DELETED_BUT_PRESENT_IN_XML = 6;
        @SystemApi
        public static final int CARRIER_EDITED = 4;
        public static final String CARRIER_ENABLED = "carrier_enabled";
        public static final String CARRIER_ID = "carrier_id";
        public static final Uri CONTENT_URI = Uri.parse("content://telephony/carriers");
        public static final String CURRENT = "current";
        public static final String DEFAULT_SORT_ORDER = "name ASC";
        public static final Uri DPC_URI;
        @SystemApi
        public static final String EDITED_STATUS = "edited";
        public static final String ENFORCE_KEY = "enforced";
        public static final Uri ENFORCE_MANAGED_URI;
        public static final Uri FILTERED_URI;
        @SystemApi
        public static final String MAX_CONNECTIONS = "max_conns";
        public static final String MCC = "mcc";
        public static final String MMSC = "mmsc";
        public static final String MMSPORT = "mmsport";
        public static final String MMSPROXY = "mmsproxy";
        public static final String MNC = "mnc";
        @SystemApi
        public static final String MODEM_PERSIST = "modem_cognitive";
        @SystemApi
        public static final String MTU = "mtu";
        public static final String MVNO_MATCH_DATA = "mvno_match_data";
        public static final String MVNO_TYPE = "mvno_type";
        public static final String NAME = "name";
        public static final String NETWORK_TYPE_BITMASK = "network_type_bitmask";
        @SystemApi
        public static final int NO_APN_SET_ID = 0;
        public static final String NUMERIC = "numeric";
        public static final String OWNED_BY = "owned_by";
        public static final int OWNED_BY_DPC = 0;
        public static final int OWNED_BY_OTHERS = 1;
        public static final String PASSWORD = "password";
        public static final String PORT = "port";
        public static final String PROFILE_ID = "profile_id";
        public static final String PROTOCOL = "protocol";
        public static final String PROXY = "proxy";
        public static final String ROAMING_PROTOCOL = "roaming_protocol";
        public static final String SERVER = "server";
        public static final Uri SIM_APN_URI;
        public static final String SKIP_464XLAT = "skip_464xlat";
        public static final int SKIP_464XLAT_DEFAULT = -1;
        public static final int SKIP_464XLAT_DISABLE = 0;
        public static final int SKIP_464XLAT_ENABLE = 1;
        public static final String SUBSCRIPTION_ID = "sub_id";
        @SystemApi
        public static final String TIME_LIMIT_FOR_MAX_CONNECTIONS = "max_conns_time";
        public static final String TYPE = "type";
        @SystemApi
        public static final int UNEDITED = 0;
        public static final String USER = "user";
        @SystemApi
        public static final int USER_DELETED = 2;
        public static final int USER_DELETED_BUT_PRESENT_IN_XML = 3;
        @SystemApi
        public static final String USER_EDITABLE = "user_editable";
        @SystemApi
        public static final int USER_EDITED = 1;
        @SystemApi
        public static final String USER_VISIBLE = "user_visible";
        @SystemApi
        public static final String WAIT_TIME_RETRY = "wait_time";

        static {
            SIM_APN_URI = Uri.parse("content://telephony/carriers/sim_apn_list");
            DPC_URI = Uri.parse("content://telephony/carriers/dpc");
            FILTERED_URI = Uri.parse("content://telephony/carriers/filtered");
            ENFORCE_MANAGED_URI = Uri.parse("content://telephony/carriers/enforce_managed");
        }

        private Carriers() {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface EditStatus {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Skip464XlatStatus {
        }

    }

    public static final class CellBroadcasts
    implements BaseColumns {
        public static final String CID = "cid";
        public static final String CMAS_CATEGORY = "cmas_category";
        public static final String CMAS_CERTAINTY = "cmas_certainty";
        public static final String CMAS_MESSAGE_CLASS = "cmas_message_class";
        public static final String CMAS_RESPONSE_TYPE = "cmas_response_type";
        public static final String CMAS_SEVERITY = "cmas_severity";
        public static final String CMAS_URGENCY = "cmas_urgency";
        public static final Uri CONTENT_URI = Uri.parse("content://cellbroadcasts");
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        public static final String DELIVERY_TIME = "date";
        public static final String ETWS_WARNING_TYPE = "etws_warning_type";
        public static final String GEOGRAPHICAL_SCOPE = "geo_scope";
        public static final String LAC = "lac";
        public static final String LANGUAGE_CODE = "language";
        public static final String MESSAGE_BODY = "body";
        public static final String MESSAGE_FORMAT = "format";
        public static final String MESSAGE_PRIORITY = "priority";
        public static final String MESSAGE_READ = "read";
        public static final String PLMN = "plmn";
        public static final String[] QUERY_COLUMNS = new String[]{"_id", "geo_scope", "plmn", "lac", "cid", "serial_number", "service_category", "language", "body", "date", "read", "format", "priority", "etws_warning_type", "cmas_message_class", "cmas_category", "cmas_response_type", "cmas_severity", "cmas_urgency", "cmas_certainty"};
        public static final String SERIAL_NUMBER = "serial_number";
        public static final String SERVICE_CATEGORY = "service_category";
        public static final String V1_MESSAGE_CODE = "message_code";
        public static final String V1_MESSAGE_IDENTIFIER = "message_id";

        private CellBroadcasts() {
        }
    }

    public static final class Mms
    implements BaseMmsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://mms");
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        @UnsupportedAppUsage
        public static final Pattern NAME_ADDR_EMAIL_PATTERN;
        public static final Uri REPORT_REQUEST_URI;
        public static final Uri REPORT_STATUS_URI;

        static {
            REPORT_REQUEST_URI = Uri.withAppendedPath(CONTENT_URI, "report-request");
            REPORT_STATUS_URI = Uri.withAppendedPath(CONTENT_URI, "report-status");
            NAME_ADDR_EMAIL_PATTERN = Pattern.compile("\\s*(\"[^\"]*\"|[^<>\"]+)\\s*<([^<>]+)>\\s*");
        }

        private Mms() {
        }

        @UnsupportedAppUsage
        public static String extractAddrSpec(String string2) {
            Matcher matcher = NAME_ADDR_EMAIL_PATTERN.matcher(string2);
            if (matcher.matches()) {
                return matcher.group(2);
            }
            return string2;
        }

        @UnsupportedAppUsage
        public static boolean isEmailAddress(String string2) {
            if (TextUtils.isEmpty(string2)) {
                return false;
            }
            string2 = Mms.extractAddrSpec(string2);
            return Patterns.EMAIL_ADDRESS.matcher(string2).matches();
        }

        @UnsupportedAppUsage
        public static boolean isPhoneNumber(String string2) {
            if (TextUtils.isEmpty(string2)) {
                return false;
            }
            return Patterns.PHONE.matcher(string2).matches();
        }

        public static Cursor query(ContentResolver contentResolver, String[] arrstring) {
            return contentResolver.query(CONTENT_URI, arrstring, null, null, DEFAULT_SORT_ORDER);
        }

        public static Cursor query(ContentResolver contentResolver, String[] arrstring, String string2, String string3) {
            Uri uri;
            block0 : {
                uri = CONTENT_URI;
                if (string3 != null) break block0;
                string3 = DEFAULT_SORT_ORDER;
            }
            return contentResolver.query(uri, arrstring, string2, null, string3);
        }

        public static final class Addr
        implements BaseColumns {
            public static final String ADDRESS = "address";
            public static final String CHARSET = "charset";
            public static final String CONTACT_ID = "contact_id";
            public static final String MSG_ID = "msg_id";
            public static final String TYPE = "type";

            private Addr() {
            }
        }

        public static final class Draft
        implements BaseMmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://mms/drafts");
            public static final String DEFAULT_SORT_ORDER = "date DESC";

            private Draft() {
            }
        }

        public static final class Inbox
        implements BaseMmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://mms/inbox");
            public static final String DEFAULT_SORT_ORDER = "date DESC";

            private Inbox() {
            }
        }

        public static final class Intents {
            public static final String CONTENT_CHANGED_ACTION = "android.intent.action.CONTENT_CHANGED";
            public static final String DELETED_CONTENTS = "deleted_contents";

            private Intents() {
            }
        }

        public static final class Outbox
        implements BaseMmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://mms/outbox");
            public static final String DEFAULT_SORT_ORDER = "date DESC";

            private Outbox() {
            }
        }

        public static final class Part
        implements BaseColumns {
            public static final String CHARSET = "chset";
            public static final String CONTENT_DISPOSITION = "cd";
            public static final String CONTENT_ID = "cid";
            public static final String CONTENT_LOCATION = "cl";
            public static final String CONTENT_TYPE = "ct";
            public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "part");
            public static final String CT_START = "ctt_s";
            public static final String CT_TYPE = "ctt_t";
            public static final String FILENAME = "fn";
            public static final String MSG_ID = "mid";
            public static final String NAME = "name";
            public static final String SEQ = "seq";
            public static final String TEXT = "text";
            public static final String _DATA = "_data";

            private Part() {
            }
        }

        public static final class Rate {
            public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "rate");
            public static final String SENT_TIME = "sent_time";

            private Rate() {
            }
        }

        public static final class Sent
        implements BaseMmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://mms/sent");
            public static final String DEFAULT_SORT_ORDER = "date DESC";

            private Sent() {
            }
        }

    }

    public static final class MmsSms
    implements BaseColumns {
        public static final Uri CONTENT_CONVERSATIONS_URI;
        public static final Uri CONTENT_DRAFT_URI;
        public static final Uri CONTENT_FILTER_BYPHONE_URI;
        public static final Uri CONTENT_LOCKED_URI;
        public static final Uri CONTENT_UNDELIVERED_URI;
        public static final Uri CONTENT_URI;
        public static final int ERR_TYPE_GENERIC = 1;
        public static final int ERR_TYPE_GENERIC_PERMANENT = 10;
        public static final int ERR_TYPE_MMS_PROTO_PERMANENT = 12;
        public static final int ERR_TYPE_MMS_PROTO_TRANSIENT = 3;
        public static final int ERR_TYPE_SMS_PROTO_PERMANENT = 11;
        public static final int ERR_TYPE_SMS_PROTO_TRANSIENT = 2;
        public static final int ERR_TYPE_TRANSPORT_FAILURE = 4;
        public static final int MMS_PROTO = 1;
        public static final int NO_ERROR = 0;
        public static final Uri SEARCH_URI;
        public static final int SMS_PROTO = 0;
        public static final String TYPE_DISCRIMINATOR_COLUMN = "transport_type";

        static {
            CONTENT_URI = Uri.parse("content://mms-sms/");
            CONTENT_CONVERSATIONS_URI = Uri.parse("content://mms-sms/conversations");
            CONTENT_FILTER_BYPHONE_URI = Uri.parse("content://mms-sms/messages/byphone");
            CONTENT_UNDELIVERED_URI = Uri.parse("content://mms-sms/undelivered");
            CONTENT_DRAFT_URI = Uri.parse("content://mms-sms/draft");
            CONTENT_LOCKED_URI = Uri.parse("content://mms-sms/locked");
            SEARCH_URI = Uri.parse("content://mms-sms/search");
        }

        private MmsSms() {
        }

        public static final class PendingMessages
        implements BaseColumns {
            public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "pending");
            public static final String DUE_TIME = "due_time";
            public static final String ERROR_CODE = "err_code";
            public static final String ERROR_TYPE = "err_type";
            public static final String LAST_TRY = "last_try";
            public static final String MSG_ID = "msg_id";
            public static final String MSG_TYPE = "msg_type";
            public static final String PROTO_TYPE = "proto_type";
            public static final String RETRY_INDEX = "retry_index";
            public static final String SUBSCRIPTION_ID = "pending_sub_id";

            private PendingMessages() {
            }
        }

        public static final class WordsTable {
            public static final String ID = "_id";
            public static final String INDEXED_TEXT = "index_text";
            public static final String SOURCE_ROW_ID = "source_id";
            public static final String TABLE_ID = "table_to_use";

            private WordsTable() {
            }
        }

    }

    public static interface RcsColumns {
        public static final String AUTHORITY = "rcs";
        public static final Uri CONTENT_AND_AUTHORITY = Uri.parse("content://rcs");
        public static final boolean IS_RCS_TABLE_SCHEMA_CODE_COMPLETE = false;
        public static final long TIMESTAMP_NOT_SET = 0L;
        public static final int TRANSACTION_FAILED = Integer.MIN_VALUE;

        public static interface Rcs1To1ThreadColumns
        extends RcsThreadColumns {
            public static final String FALLBACK_THREAD_ID_COLUMN = "rcs_fallback_thread_id";
            public static final Uri RCS_1_TO_1_THREAD_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "p2p_thread");
            public static final String RCS_1_TO_1_THREAD_URI_PART = "p2p_thread";
        }

        public static interface RcsCanonicalAddressHelper {
            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public static long getOrCreateCanonicalAddressId(ContentResolver var0, String var1_2) {
                block9 : {
                    block8 : {
                        var2_4 = RcsColumns.CONTENT_AND_AUTHORITY.buildUpon();
                        var2_4.appendPath("canonical-address");
                        var2_4.appendQueryParameter("address", var1_2);
                        var2_4 = var0.query(var2_4.build(), null, null, null);
                        if (var2_4 == null) ** GOTO lbl13
                        try {
                            if (var2_4.moveToFirst()) {
                                var3_6 = var2_4.getLong(var2_4.getColumnIndex("_id"));
                                var2_4.close();
                                return var3_6;
                            }
lbl13: // 3 sources:
                            Rlog.e("Telephony", "getOrCreateCanonicalAddressId returned no rows");
                            if (var2_4 == null) break block8;
                            var2_4.close();
                        }
                        catch (Throwable var0_1) {
                            break block9;
                        }
                    }
                    Rlog.e("Telephony", "getOrCreateCanonicalAddressId failed");
                    throw new IllegalArgumentException("Unable to find or allocate a canonical address ID");
                }
                try {
                    throw var0_1;
                }
                catch (Throwable var1_3) {
                    if (var2_4 == null) throw var1_3;
                    try {
                        var2_4.close();
                        throw var1_3;
                    }
                    catch (Throwable var2_5) {
                        var0_1.addSuppressed(var2_5);
                    }
                    throw var1_3;
                }
            }
        }

        public static interface RcsEventTypes {
            public static final int ICON_CHANGED_EVENT_TYPE = 8;
            public static final int NAME_CHANGED_EVENT_TYPE = 16;
            public static final int PARTICIPANT_ALIAS_CHANGED_EVENT_TYPE = 1;
            public static final int PARTICIPANT_JOINED_EVENT_TYPE = 2;
            public static final int PARTICIPANT_LEFT_EVENT_TYPE = 4;
        }

        public static interface RcsFileTransferColumns {
            public static final String CONTENT_TYPE_COLUMN = "content_type";
            public static final String CONTENT_URI_COLUMN = "content_uri";
            public static final String DURATION_MILLIS_COLUMN = "duration";
            public static final String FILE_SIZE_COLUMN = "file_size";
            public static final String FILE_TRANSFER_ID_COLUMN = "rcs_file_transfer_id";
            public static final Uri FILE_TRANSFER_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "file_transfer");
            public static final String FILE_TRANSFER_URI_PART = "file_transfer";
            public static final String HEIGHT_COLUMN = "height";
            public static final String PREVIEW_TYPE_COLUMN = "preview_type";
            public static final String PREVIEW_URI_COLUMN = "preview_uri";
            public static final String SESSION_ID_COLUMN = "session_id";
            public static final String SUCCESSFULLY_TRANSFERRED_BYTES = "transfer_offset";
            public static final String TRANSFER_STATUS_COLUMN = "transfer_status";
            public static final String WIDTH_COLUMN = "width";
        }

        public static interface RcsGroupThreadColumns
        extends RcsThreadColumns {
            public static final String CONFERENCE_URI_COLUMN = "conference_uri";
            public static final String GROUP_ICON_COLUMN = "group_icon";
            public static final String GROUP_NAME_COLUMN = "group_name";
            public static final String OWNER_PARTICIPANT_COLUMN = "owner_participant";
            public static final Uri RCS_GROUP_THREAD_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "group_thread");
            public static final String RCS_GROUP_THREAD_URI_PART = "group_thread";
        }

        public static interface RcsIncomingMessageColumns
        extends RcsMessageColumns {
            public static final String ARRIVAL_TIMESTAMP_COLUMN = "arrival_timestamp";
            public static final Uri INCOMING_MESSAGE_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "incoming_message");
            public static final String INCOMING_MESSAGE_URI_PART = "incoming_message";
            public static final String SEEN_TIMESTAMP_COLUMN = "seen_timestamp";
            public static final String SENDER_PARTICIPANT_ID_COLUMN = "sender_participant";
        }

        public static interface RcsMessageColumns {
            public static final String GLOBAL_ID_COLUMN = "rcs_message_global_id";
            public static final String LATITUDE_COLUMN = "latitude";
            public static final String LONGITUDE_COLUMN = "longitude";
            public static final String MESSAGE_ID_COLUMN = "rcs_message_row_id";
            public static final String MESSAGE_TEXT_COLUMN = "rcs_text";
            public static final String MESSAGE_TYPE_COLUMN = "rcs_message_type";
            public static final String ORIGINATION_TIMESTAMP_COLUMN = "origination_timestamp";
            public static final String STATUS_COLUMN = "status";
            public static final String SUB_ID_COLUMN = "sub_id";
        }

        public static interface RcsMessageDeliveryColumns
        extends RcsOutgoingMessageColumns {
            public static final String DELIVERED_TIMESTAMP_COLUMN = "delivered_timestamp";
            public static final String DELIVERY_URI_PART = "delivery";
            public static final String SEEN_TIMESTAMP_COLUMN = "seen_timestamp";
        }

        public static interface RcsOutgoingMessageColumns
        extends RcsMessageColumns {
            public static final Uri OUTGOING_MESSAGE_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "outgoing_message");
            public static final String OUTGOING_MESSAGE_URI_PART = "outgoing_message";
        }

        public static interface RcsParticipantColumns {
            public static final String CANONICAL_ADDRESS_ID_COLUMN = "canonical_address_id";
            public static final String RCS_ALIAS_COLUMN = "rcs_alias";
            public static final String RCS_PARTICIPANT_ID_COLUMN = "rcs_participant_id";
            public static final Uri RCS_PARTICIPANT_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "participant");
            public static final String RCS_PARTICIPANT_URI_PART = "participant";
        }

        public static interface RcsParticipantEventColumns {
            public static final String ALIAS_CHANGE_EVENT_URI_PART = "alias_change_event";
            public static final String NEW_ALIAS_COLUMN = "new_alias";
        }

        public static interface RcsParticipantHelpers
        extends RcsParticipantColumns {
            public static final String RCS_PARTICIPANT_WITH_ADDRESS_VIEW = "rcs_participant_with_address_view";
            public static final String RCS_PARTICIPANT_WITH_THREAD_VIEW = "rcs_participant_with_thread_view";
        }

        public static interface RcsThreadColumns {
            public static final String RCS_THREAD_ID_COLUMN = "rcs_thread_id";
            public static final Uri RCS_THREAD_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "thread");
            public static final String RCS_THREAD_URI_PART = "thread";
        }

        public static interface RcsThreadEventColumns {
            public static final String DESTINATION_PARTICIPANT_ID_COLUMN = "destination_participant";
            public static final String EVENT_ID_COLUMN = "event_id";
            public static final String EVENT_TYPE_COLUMN = "event_type";
            public static final String ICON_CHANGED_URI_PART = "icon_changed_event";
            public static final String NAME_CHANGED_URI_PART = "name_changed_event";
            public static final String NEW_ICON_URI_COLUMN = "new_icon_uri";
            public static final String NEW_NAME_COLUMN = "new_name";
            public static final String PARTICIPANT_JOINED_URI_PART = "participant_joined_event";
            public static final String PARTICIPANT_LEFT_URI_PART = "participant_left_event";
            public static final String SOURCE_PARTICIPANT_ID_COLUMN = "source_participant";
            public static final String TIMESTAMP_COLUMN = "origination_timestamp";
        }

        public static interface RcsUnifiedEventHelper
        extends RcsParticipantEventColumns,
        RcsThreadEventColumns {
            public static final Uri RCS_EVENT_QUERY_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "event");
            public static final String RCS_EVENT_QUERY_URI_PATH = "event";
        }

        public static interface RcsUnifiedMessageColumns
        extends RcsIncomingMessageColumns,
        RcsOutgoingMessageColumns {
            public static final String MESSAGE_TYPE_COLUMN = "message_type";
            public static final int MESSAGE_TYPE_INCOMING = 1;
            public static final int MESSAGE_TYPE_OUTGOING = 0;
            public static final String UNIFIED_INCOMING_MESSAGE_VIEW = "unified_incoming_message_view";
            public static final Uri UNIFIED_MESSAGE_URI = Uri.withAppendedPath(CONTENT_AND_AUTHORITY, "message");
            public static final String UNIFIED_MESSAGE_URI_PART = "message";
            public static final String UNIFIED_OUTGOING_MESSAGE_VIEW = "unified_outgoing_message_view";
        }

        public static interface RcsUnifiedThreadColumns
        extends RcsThreadColumns,
        Rcs1To1ThreadColumns,
        RcsGroupThreadColumns {
            public static final int THREAD_TYPE_1_TO_1 = 0;
            public static final String THREAD_TYPE_COLUMN = "thread_type";
            public static final int THREAD_TYPE_GROUP = 1;
        }

    }

    public static final class ServiceStateTable {
        public static final String AUTHORITY = "service-state";
        public static final String CDMA_DEFAULT_ROAMING_INDICATOR = "cdma_default_roaming_indicator";
        public static final String CDMA_ERI_ICON_INDEX = "cdma_eri_icon_index";
        public static final String CDMA_ERI_ICON_MODE = "cdma_eri_icon_mode";
        public static final String CDMA_ROAMING_INDICATOR = "cdma_roaming_indicator";
        public static final Uri CONTENT_URI = Uri.parse("content://service-state/");
        public static final String CSS_INDICATOR = "css_indicator";
        public static final String DATA_OPERATOR_ALPHA_LONG = "data_operator_alpha_long";
        public static final String DATA_OPERATOR_ALPHA_SHORT = "data_operator_alpha_short";
        public static final String DATA_OPERATOR_NUMERIC = "data_operator_numeric";
        public static final String DATA_REG_STATE = "data_reg_state";
        public static final String DATA_ROAMING_TYPE = "data_roaming_type";
        public static final String IS_DATA_ROAMING_FROM_REGISTRATION = "is_data_roaming_from_registration";
        public static final String IS_EMERGENCY_ONLY = "is_emergency_only";
        public static final String IS_MANUAL_NETWORK_SELECTION = "is_manual_network_selection";
        public static final String IS_USING_CARRIER_AGGREGATION = "is_using_carrier_aggregation";
        public static final String NETWORK_ID = "network_id";
        public static final String OPERATOR_ALPHA_LONG_RAW = "operator_alpha_long_raw";
        public static final String OPERATOR_ALPHA_SHORT_RAW = "operator_alpha_short_raw";
        public static final String RIL_DATA_RADIO_TECHNOLOGY = "ril_data_radio_technology";
        public static final String RIL_VOICE_RADIO_TECHNOLOGY = "ril_voice_radio_technology";
        public static final String SERVICE_STATE = "service_state";
        public static final String SYSTEM_ID = "system_id";
        public static final String VOICE_OPERATOR_ALPHA_LONG = "voice_operator_alpha_long";
        public static final String VOICE_OPERATOR_ALPHA_SHORT = "voice_operator_alpha_short";
        public static final String VOICE_OPERATOR_NUMERIC = "voice_operator_numeric";
        public static final String VOICE_REG_STATE = "voice_reg_state";
        public static final String VOICE_ROAMING_TYPE = "voice_roaming_type";

        private ServiceStateTable() {
        }

        public static ContentValues getContentValuesForServiceState(ServiceState serviceState) {
            ContentValues contentValues = new ContentValues();
            Parcel parcel = Parcel.obtain();
            serviceState.writeToParcel(parcel, 0);
            contentValues.put(SERVICE_STATE, parcel.marshall());
            return contentValues;
        }

        public static Uri getUriForSubscriptionId(int n) {
            return CONTENT_URI.buildUpon().appendEncodedPath(String.valueOf(n)).build();
        }

        public static Uri getUriForSubscriptionIdAndField(int n, String string2) {
            return CONTENT_URI.buildUpon().appendEncodedPath(String.valueOf(n)).appendEncodedPath(string2).build();
        }
    }

    public static final class Sms
    implements BaseColumns,
    TextBasedSmsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://sms");
        public static final String DEFAULT_SORT_ORDER = "date DESC";

        private Sms() {
        }

        @UnsupportedAppUsage
        public static Uri addMessageToUri(int n, ContentResolver contentResolver, Uri uri, String string2, String string3, String string4, Long l, boolean bl, boolean bl2) {
            return Sms.addMessageToUri(n, contentResolver, uri, string2, string3, string4, l, bl, bl2, -1L);
        }

        @UnsupportedAppUsage
        public static Uri addMessageToUri(int n, ContentResolver contentResolver, Uri uri, String string2, String string3, String string4, Long l, boolean bl, boolean bl2, long l2) {
            ContentValues contentValues = new ContentValues(8);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Telephony addMessageToUri sub id: ");
            stringBuilder.append(n);
            Rlog.v(Telephony.TAG, stringBuilder.toString());
            contentValues.put("sub_id", n);
            contentValues.put("address", string2);
            if (l != null) {
                contentValues.put("date", l);
            }
            n = bl ? 1 : 0;
            contentValues.put("read", n);
            contentValues.put("subject", string4);
            contentValues.put("body", string3);
            if (bl2) {
                contentValues.put("status", 32);
            }
            if (l2 != -1L) {
                contentValues.put("thread_id", l2);
            }
            return contentResolver.insert(uri, contentValues);
        }

        @UnsupportedAppUsage
        public static Uri addMessageToUri(ContentResolver contentResolver, Uri uri, String string2, String string3, String string4, Long l, boolean bl, boolean bl2) {
            return Sms.addMessageToUri(SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, uri, string2, string3, string4, l, bl, bl2, -1L);
        }

        @UnsupportedAppUsage
        public static Uri addMessageToUri(ContentResolver contentResolver, Uri uri, String string2, String string3, String string4, Long l, boolean bl, boolean bl2, long l2) {
            return Sms.addMessageToUri(SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, uri, string2, string3, string4, l, bl, bl2, l2);
        }

        public static String getDefaultSmsPackage(Context object) {
            if ((object = SmsApplication.getDefaultSmsApplication((Context)object, false)) != null) {
                return ((ComponentName)object).getPackageName();
            }
            return null;
        }

        @UnsupportedAppUsage
        public static boolean isOutgoingFolder(int n) {
            boolean bl = n == 5 || n == 4 || n == 2 || n == 6;
            return bl;
        }

        @UnsupportedAppUsage
        public static boolean moveMessageToFolder(Context context, Uri uri, int n, int n2) {
            boolean bl = false;
            if (uri == null) {
                return false;
            }
            boolean bl2 = false;
            boolean bl3 = false;
            switch (n) {
                default: {
                    return false;
                }
                case 5: 
                case 6: {
                    bl2 = true;
                    break;
                }
                case 2: 
                case 4: {
                    bl3 = true;
                }
                case 1: 
                case 3: 
            }
            ContentValues contentValues = new ContentValues(3);
            contentValues.put("type", n);
            if (bl2) {
                contentValues.put("read", 0);
            } else if (bl3) {
                contentValues.put("read", 1);
            }
            contentValues.put("error_code", n2);
            if (1 == SqliteWrapper.update(context, context.getContentResolver(), uri, contentValues, null, null)) {
                bl = true;
            }
            return bl;
        }

        public static Cursor query(ContentResolver contentResolver, String[] arrstring) {
            return contentResolver.query(CONTENT_URI, arrstring, null, null, DEFAULT_SORT_ORDER);
        }

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public static Cursor query(ContentResolver contentResolver, String[] arrstring, String string2, String string3) {
            Uri uri;
            block0 : {
                uri = CONTENT_URI;
                if (string3 != null) break block0;
                string3 = DEFAULT_SORT_ORDER;
            }
            return contentResolver.query(uri, arrstring, string2, null, string3);
        }

        public static final class Conversations
        implements BaseColumns,
        TextBasedSmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://sms/conversations");
            public static final String DEFAULT_SORT_ORDER = "date DESC";
            public static final String MESSAGE_COUNT = "msg_count";
            public static final String SNIPPET = "snippet";

            private Conversations() {
            }
        }

        public static final class Draft
        implements BaseColumns,
        TextBasedSmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://sms/draft");
            public static final String DEFAULT_SORT_ORDER = "date DESC";

            private Draft() {
            }

            @UnsupportedAppUsage
            public static Uri addMessage(int n, ContentResolver contentResolver, String string2, String string3, String string4, Long l) {
                return Sms.addMessageToUri(n, contentResolver, CONTENT_URI, string2, string3, string4, l, true, false);
            }

            @UnsupportedAppUsage
            public static Uri addMessage(ContentResolver contentResolver, String string2, String string3, String string4, Long l) {
                return Sms.addMessageToUri(SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, CONTENT_URI, string2, string3, string4, l, true, false);
            }
        }

        public static final class Inbox
        implements BaseColumns,
        TextBasedSmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://sms/inbox");
            public static final String DEFAULT_SORT_ORDER = "date DESC";

            private Inbox() {
            }

            @UnsupportedAppUsage
            public static Uri addMessage(int n, ContentResolver contentResolver, String string2, String string3, String string4, Long l, boolean bl) {
                return Sms.addMessageToUri(n, contentResolver, CONTENT_URI, string2, string3, string4, l, bl, false);
            }

            @UnsupportedAppUsage
            public static Uri addMessage(ContentResolver contentResolver, String string2, String string3, String string4, Long l, boolean bl) {
                return Sms.addMessageToUri(SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, CONTENT_URI, string2, string3, string4, l, bl, false);
            }
        }

        public static final class Intents {
            public static final String ACTION_CHANGE_DEFAULT = "android.provider.Telephony.ACTION_CHANGE_DEFAULT";
            public static final String ACTION_DEFAULT_SMS_PACKAGE_CHANGED = "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED";
            public static final String ACTION_DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL = "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL";
            public static final String ACTION_EXTERNAL_PROVIDER_CHANGE = "android.provider.action.EXTERNAL_PROVIDER_CHANGE";
            public static final String ACTION_SMS_MMS_DB_CREATED = "android.provider.action.SMS_MMS_DB_CREATED";
            public static final String ACTION_SMS_MMS_DB_LOST = "android.provider.action.SMS_MMS_DB_LOST";
            public static final String DATA_SMS_RECEIVED_ACTION = "android.intent.action.DATA_SMS_RECEIVED";
            public static final String EXTRA_IS_CORRUPTED = "android.provider.extra.IS_CORRUPTED";
            public static final String EXTRA_IS_DEFAULT_SMS_APP = "android.provider.extra.IS_DEFAULT_SMS_APP";
            public static final String EXTRA_IS_INITIAL_CREATE = "android.provider.extra.IS_INITIAL_CREATE";
            public static final String EXTRA_PACKAGE_NAME = "package";
            public static final String MMS_DOWNLOADED_ACTION = "android.provider.Telephony.MMS_DOWNLOADED";
            public static final int RESULT_SMS_DUPLICATED = 5;
            public static final int RESULT_SMS_GENERIC_ERROR = 2;
            public static final int RESULT_SMS_HANDLED = 1;
            public static final int RESULT_SMS_OUT_OF_MEMORY = 3;
            public static final int RESULT_SMS_UNSUPPORTED = 4;
            @Deprecated
            public static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";
            public static final String SIM_FULL_ACTION = "android.provider.Telephony.SIM_FULL";
            public static final String SMS_CARRIER_PROVISION_ACTION = "android.provider.Telephony.SMS_CARRIER_PROVISION";
            public static final String SMS_CB_RECEIVED_ACTION = "android.provider.Telephony.SMS_CB_RECEIVED";
            public static final String SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER";
            public static final String SMS_EMERGENCY_CB_RECEIVED_ACTION = "android.provider.Telephony.SMS_EMERGENCY_CB_RECEIVED";
            public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
            public static final String SMS_REJECTED_ACTION = "android.provider.Telephony.SMS_REJECTED";
            public static final String SMS_SERVICE_CATEGORY_PROGRAM_DATA_RECEIVED_ACTION = "android.provider.Telephony.SMS_SERVICE_CATEGORY_PROGRAM_DATA_RECEIVED";
            public static final String WAP_PUSH_DELIVER_ACTION = "android.provider.Telephony.WAP_PUSH_DELIVER";
            public static final String WAP_PUSH_RECEIVED_ACTION = "android.provider.Telephony.WAP_PUSH_RECEIVED";

            private Intents() {
            }

            public static SmsMessage[] getMessagesFromIntent(Intent arrsmsMessage) {
                Object[] arrobject;
                block3 : {
                    try {
                        arrobject = (Object[])arrsmsMessage.getSerializableExtra("pdus");
                        if (arrobject != null) break block3;
                    }
                    catch (ClassCastException classCastException) {
                        arrsmsMessage = new StringBuilder();
                        arrsmsMessage.append("getMessagesFromIntent: ");
                        arrsmsMessage.append(classCastException);
                        Rlog.e(Telephony.TAG, arrsmsMessage.toString());
                        return null;
                    }
                    Rlog.e(Telephony.TAG, "pdus does not exist in the intent");
                    return null;
                }
                String string2 = arrsmsMessage.getStringExtra("format");
                int n = arrsmsMessage.getIntExtra("subscription", SubscriptionManager.getDefaultSmsSubscriptionId());
                arrsmsMessage = new StringBuilder();
                arrsmsMessage.append(" getMessagesFromIntent sub_id : ");
                arrsmsMessage.append(n);
                Rlog.v(Telephony.TAG, arrsmsMessage.toString());
                int n2 = arrobject.length;
                arrsmsMessage = new SmsMessage[n2];
                for (int i = 0; i < n2; ++i) {
                    arrsmsMessage[i] = SmsMessage.createFromPdu((byte[])arrobject[i], string2);
                    if (arrsmsMessage[i] == null) continue;
                    arrsmsMessage[i].setSubId(n);
                }
                return arrsmsMessage;
            }
        }

        public static final class Outbox
        implements BaseColumns,
        TextBasedSmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://sms/outbox");
            public static final String DEFAULT_SORT_ORDER = "date DESC";

            private Outbox() {
            }

            public static Uri addMessage(int n, ContentResolver contentResolver, String string2, String string3, String string4, Long l, boolean bl, long l2) {
                return Sms.addMessageToUri(n, contentResolver, CONTENT_URI, string2, string3, string4, l, true, bl, l2);
            }

            @UnsupportedAppUsage
            public static Uri addMessage(ContentResolver contentResolver, String string2, String string3, String string4, Long l, boolean bl, long l2) {
                return Sms.addMessageToUri(SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, CONTENT_URI, string2, string3, string4, l, true, bl, l2);
            }
        }

        public static final class Sent
        implements BaseColumns,
        TextBasedSmsColumns {
            public static final Uri CONTENT_URI = Uri.parse("content://sms/sent");
            public static final String DEFAULT_SORT_ORDER = "date DESC";

            private Sent() {
            }

            @UnsupportedAppUsage
            public static Uri addMessage(int n, ContentResolver contentResolver, String string2, String string3, String string4, Long l) {
                return Sms.addMessageToUri(n, contentResolver, CONTENT_URI, string2, string3, string4, l, true, false);
            }

            @UnsupportedAppUsage
            public static Uri addMessage(ContentResolver contentResolver, String string2, String string3, String string4, Long l) {
                return Sms.addMessageToUri(SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, CONTENT_URI, string2, string3, string4, l, true, false);
            }
        }

    }

    public static interface TextBasedSmsChangesColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://sms-changes");
        public static final String ID = "_id";
        public static final String NEW_READ_STATUS = "new_read_status";
        public static final String ORIG_ROW_ID = "orig_rowid";
        public static final String SUB_ID = "sub_id";
        public static final String TYPE = "type";
        public static final int TYPE_DELETE = 1;
        public static final int TYPE_UPDATE = 0;
    }

    public static interface TextBasedSmsColumns {
        public static final String ADDRESS = "address";
        public static final String BODY = "body";
        public static final String CREATOR = "creator";
        public static final String DATE = "date";
        public static final String DATE_SENT = "date_sent";
        public static final String ERROR_CODE = "error_code";
        public static final String LOCKED = "locked";
        public static final int MESSAGE_TYPE_ALL = 0;
        public static final int MESSAGE_TYPE_DRAFT = 3;
        public static final int MESSAGE_TYPE_FAILED = 5;
        public static final int MESSAGE_TYPE_INBOX = 1;
        public static final int MESSAGE_TYPE_OUTBOX = 4;
        public static final int MESSAGE_TYPE_QUEUED = 6;
        public static final int MESSAGE_TYPE_SENT = 2;
        public static final String MTU = "mtu";
        public static final String PERSON = "person";
        public static final String PROTOCOL = "protocol";
        public static final String READ = "read";
        public static final String REPLY_PATH_PRESENT = "reply_path_present";
        public static final String SEEN = "seen";
        public static final String SERVICE_CENTER = "service_center";
        public static final String STATUS = "status";
        public static final int STATUS_COMPLETE = 0;
        public static final int STATUS_FAILED = 64;
        public static final int STATUS_NONE = -1;
        public static final int STATUS_PENDING = 32;
        public static final String SUBJECT = "subject";
        public static final String SUBSCRIPTION_ID = "sub_id";
        public static final String THREAD_ID = "thread_id";
        public static final String TYPE = "type";
    }

    public static final class Threads
    implements ThreadsColumns {
        public static final int BROADCAST_THREAD = 1;
        public static final int COMMON_THREAD = 0;
        public static final Uri CONTENT_URI;
        @UnsupportedAppUsage
        private static final String[] ID_PROJECTION;
        public static final Uri OBSOLETE_THREADS_URI;
        @UnsupportedAppUsage
        private static final Uri THREAD_ID_CONTENT_URI;

        static {
            ID_PROJECTION = new String[]{"_id"};
            THREAD_ID_CONTENT_URI = Uri.parse("content://mms-sms/threadID");
            CONTENT_URI = Uri.withAppendedPath(MmsSms.CONTENT_URI, "conversations");
            OBSOLETE_THREADS_URI = Uri.withAppendedPath(CONTENT_URI, "obsolete");
        }

        private Threads() {
        }

        public static long getOrCreateThreadId(Context context, String string2) {
            HashSet<String> hashSet = new HashSet<String>();
            hashSet.add(string2);
            return Threads.getOrCreateThreadId(context, hashSet);
        }

        public static long getOrCreateThreadId(Context object, Set<String> set) {
            Object object2;
            Uri.Builder builder = THREAD_ID_CONTENT_URI.buildUpon();
            for (String string2 : set) {
                object2 = string2;
                if (Mms.isEmailAddress(string2)) {
                    object2 = Mms.extractAddrSpec(string2);
                }
                builder.appendQueryParameter("recipient", (String)object2);
            }
            object2 = builder.build();
            if ((object = SqliteWrapper.query((Context)object, ((Context)object).getContentResolver(), (Uri)object2, ID_PROJECTION, null, null, null)) != null) {
                try {
                    if (object.moveToFirst()) {
                        long l = object.getLong(0);
                        return l;
                    }
                    Rlog.e(Telephony.TAG, "getOrCreateThreadId returned no rows!");
                }
                finally {
                    object.close();
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("getOrCreateThreadId failed with ");
            ((StringBuilder)object).append(set.size());
            ((StringBuilder)object).append(" recipients");
            Rlog.e(Telephony.TAG, ((StringBuilder)object).toString());
            throw new IllegalArgumentException("Unable to find or allocate a thread ID.");
        }
    }

    public static interface ThreadsColumns
    extends BaseColumns {
        public static final String ARCHIVED = "archived";
        public static final String DATE = "date";
        public static final String ERROR = "error";
        public static final String HAS_ATTACHMENT = "has_attachment";
        public static final String MESSAGE_COUNT = "message_count";
        public static final String READ = "read";
        public static final String RECIPIENT_IDS = "recipient_ids";
        public static final String SNIPPET = "snippet";
        public static final String SNIPPET_CHARSET = "snippet_cs";
        public static final String TYPE = "type";
    }

}

