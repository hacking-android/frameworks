/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.accounts.Account;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.CursorEntityIterator;
import android.content.Entity;
import android.content.EntityIterator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.CompatibilityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.ContactsInternal;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public final class ContactsContract {
    public static final String AUTHORITY = "com.android.contacts";
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.android.contacts");
    public static final String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
    public static final String DEFERRED_SNIPPETING = "deferred_snippeting";
    public static final String DEFERRED_SNIPPETING_QUERY = "deferred_snippeting_query";
    public static final String DIRECTORY_PARAM_KEY = "directory";
    public static final String HIDDEN_COLUMN_PREFIX = "x_";
    public static final String LIMIT_PARAM_KEY = "limit";
    public static final String PRIMARY_ACCOUNT_NAME = "name_for_primary_account";
    public static final String PRIMARY_ACCOUNT_TYPE = "type_for_primary_account";
    public static final String REMOVE_DUPLICATE_ENTRIES = "remove_duplicate_entries";
    public static final String STREQUENT_PHONE_ONLY = "strequent_phone_only";

    public static boolean isProfileId(long l) {
        boolean bl = l >= 9223372034707292160L;
        return bl;
    }

    public static final class AggregationExceptions
    implements BaseColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/aggregation_exception";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/aggregation_exception";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "aggregation_exceptions");
        public static final String RAW_CONTACT_ID1 = "raw_contact_id1";
        public static final String RAW_CONTACT_ID2 = "raw_contact_id2";
        public static final String TYPE = "type";
        public static final int TYPE_AUTOMATIC = 0;
        public static final int TYPE_KEEP_SEPARATE = 2;
        public static final int TYPE_KEEP_TOGETHER = 1;

        private AggregationExceptions() {
        }
    }

    public static final class Authorization {
        public static final String AUTHORIZATION_METHOD = "authorize";
        public static final String KEY_AUTHORIZED_URI = "authorized_uri";
        public static final String KEY_URI_TO_AUTHORIZE = "uri_to_authorize";
    }

    protected static interface BaseSyncColumns {
        public static final String SYNC1 = "sync1";
        public static final String SYNC2 = "sync2";
        public static final String SYNC3 = "sync3";
        public static final String SYNC4 = "sync4";
    }

    public static final class CommonDataKinds {
        public static final String PACKAGE_COMMON = "common";

        private CommonDataKinds() {
        }

        public static interface BaseTypes {
            public static final int TYPE_CUSTOM = 0;
        }

        public static final class Callable
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final Uri CONTENT_FILTER_URI;
            public static final Uri CONTENT_URI;
            public static final Uri ENTERPRISE_CONTENT_FILTER_URI;

            static {
                CONTENT_URI = Uri.withAppendedPath(Data.CONTENT_URI, "callables");
                CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter");
                ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter_enterprise");
            }
        }

        protected static interface CommonColumns
        extends BaseTypes {
            public static final String DATA = "data1";
            public static final String LABEL = "data3";
            public static final String TYPE = "data2";
        }

        public static final class Contactables
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final Uri CONTENT_FILTER_URI;
            public static final Uri CONTENT_URI;
            public static final String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";

            static {
                CONTENT_URI = Uri.withAppendedPath(Data.CONTENT_URI, "contactables");
                CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter");
            }
        }

        public static final class Email
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String ADDRESS = "data1";
            public static final Uri CONTENT_FILTER_URI;
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/email_v2";
            public static final Uri CONTENT_LOOKUP_URI;
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/email_v2";
            public static final Uri CONTENT_URI;
            public static final String DISPLAY_NAME = "data4";
            public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
            public static final Uri ENTERPRISE_CONTENT_LOOKUP_URI;
            public static final int TYPE_HOME = 1;
            public static final int TYPE_MOBILE = 4;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            static {
                CONTENT_URI = Uri.withAppendedPath(Data.CONTENT_URI, "emails");
                CONTENT_LOOKUP_URI = Uri.withAppendedPath(CONTENT_URI, "lookup");
                ENTERPRISE_CONTENT_LOOKUP_URI = Uri.withAppendedPath(CONTENT_URI, "lookup_enterprise");
                CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter");
                ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter_enterprise");
            }

            private Email() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int n, CharSequence charSequence) {
                if (n == 0 && !TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(Email.getTypeLabelResource(n));
            }

            public static final int getTypeLabelResource(int n) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                return 17039889;
                            }
                            return 17039891;
                        }
                        return 17039892;
                    }
                    return 17039893;
                }
                return 17039890;
            }
        }

        public static final class Event
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_event";
            public static final String START_DATE = "data1";
            public static final int TYPE_ANNIVERSARY = 1;
            public static final int TYPE_BIRTHDAY = 3;
            public static final int TYPE_OTHER = 2;

            private Event() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int n, CharSequence charSequence) {
                if (n == 0 && !TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(Event.getTypeResource(n));
            }

            public static int getTypeResource(Integer n) {
                if (n == null) {
                    return 17039910;
                }
                int n2 = n;
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            return 17039909;
                        }
                        return 17039908;
                    }
                    return 17039910;
                }
                return 17039907;
            }
        }

        public static final class GroupMembership
        implements DataColumnsWithJoins,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group_membership";
            public static final String GROUP_ROW_ID = "data1";
            public static final String GROUP_SOURCE_ID = "group_sourceid";

            private GroupMembership() {
            }
        }

        public static final class Identity
        implements DataColumnsWithJoins,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/identity";
            public static final String IDENTITY = "data1";
            public static final String NAMESPACE = "data2";

            private Identity() {
            }
        }

        public static final class Im
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im";
            public static final String CUSTOM_PROTOCOL = "data6";
            public static final String PROTOCOL = "data5";
            public static final int PROTOCOL_AIM = 0;
            public static final int PROTOCOL_CUSTOM = -1;
            public static final int PROTOCOL_GOOGLE_TALK = 5;
            public static final int PROTOCOL_ICQ = 6;
            public static final int PROTOCOL_JABBER = 7;
            public static final int PROTOCOL_MSN = 1;
            public static final int PROTOCOL_NETMEETING = 8;
            public static final int PROTOCOL_QQ = 4;
            public static final int PROTOCOL_SKYPE = 3;
            public static final int PROTOCOL_YAHOO = 2;
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            private Im() {
            }

            public static final CharSequence getProtocolLabel(Resources resources, int n, CharSequence charSequence) {
                if (n == -1 && !TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(Im.getProtocolLabelResource(n));
            }

            public static final int getProtocolLabelResource(int n) {
                switch (n) {
                    default: {
                        return 17040107;
                    }
                    case 8: {
                        return 17040112;
                    }
                    case 7: {
                        return 17040110;
                    }
                    case 6: {
                        return 17040109;
                    }
                    case 5: {
                        return 17040108;
                    }
                    case 4: {
                        return 17040113;
                    }
                    case 3: {
                        return 17040114;
                    }
                    case 2: {
                        return 17040115;
                    }
                    case 1: {
                        return 17040111;
                    }
                    case 0: 
                }
                return 17040106;
            }

            public static final CharSequence getTypeLabel(Resources resources, int n, CharSequence charSequence) {
                if (n == 0 && !TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(Im.getTypeLabelResource(n));
            }

            public static final int getTypeLabelResource(int n) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return 17040116;
                        }
                        return 17040118;
                    }
                    return 17040119;
                }
                return 17040117;
            }
        }

        public static final class Nickname
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/nickname";
            public static final String NAME = "data1";
            public static final int TYPE_DEFAULT = 1;
            public static final int TYPE_INITIALS = 5;
            public static final int TYPE_MAIDEN_NAME = 3;
            @Deprecated
            public static final int TYPE_MAINDEN_NAME = 3;
            public static final int TYPE_OTHER_NAME = 2;
            public static final int TYPE_SHORT_NAME = 4;

            private Nickname() {
            }
        }

        public static final class Note
        implements DataColumnsWithJoins,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/note";
            public static final String NOTE = "data1";

            private Note() {
            }
        }

        public static final class Organization
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String COMPANY = "data1";
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/organization";
            public static final String DEPARTMENT = "data5";
            public static final String JOB_DESCRIPTION = "data6";
            public static final String OFFICE_LOCATION = "data9";
            public static final String PHONETIC_NAME = "data8";
            public static final String PHONETIC_NAME_STYLE = "data10";
            public static final String SYMBOL = "data7";
            public static final String TITLE = "data4";
            public static final int TYPE_OTHER = 2;
            public static final int TYPE_WORK = 1;

            private Organization() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int n, CharSequence charSequence) {
                if (n == 0 && !TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(Organization.getTypeLabelResource(n));
            }

            public static final int getTypeLabelResource(int n) {
                if (n != 1) {
                    if (n != 2) {
                        return 17040528;
                    }
                    return 17040529;
                }
                return 17040530;
            }
        }

        public static final class Phone
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final Uri CONTENT_FILTER_URI;
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone_v2";
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone_v2";
            public static final Uri CONTENT_URI;
            public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
            public static final Uri ENTERPRISE_CONTENT_URI;
            public static final String NORMALIZED_NUMBER = "data4";
            public static final String NUMBER = "data1";
            public static final String SEARCH_DISPLAY_NAME_KEY = "search_display_name";
            public static final String SEARCH_PHONE_NUMBER_KEY = "search_phone_number";
            public static final int TYPE_ASSISTANT = 19;
            public static final int TYPE_CALLBACK = 8;
            public static final int TYPE_CAR = 9;
            public static final int TYPE_COMPANY_MAIN = 10;
            public static final int TYPE_FAX_HOME = 5;
            public static final int TYPE_FAX_WORK = 4;
            public static final int TYPE_HOME = 1;
            public static final int TYPE_ISDN = 11;
            public static final int TYPE_MAIN = 12;
            public static final int TYPE_MMS = 20;
            public static final int TYPE_MOBILE = 2;
            public static final int TYPE_OTHER = 7;
            public static final int TYPE_OTHER_FAX = 13;
            public static final int TYPE_PAGER = 6;
            public static final int TYPE_RADIO = 14;
            public static final int TYPE_TELEX = 15;
            public static final int TYPE_TTY_TDD = 16;
            public static final int TYPE_WORK = 3;
            public static final int TYPE_WORK_MOBILE = 17;
            public static final int TYPE_WORK_PAGER = 18;

            static {
                CONTENT_URI = Uri.withAppendedPath(Data.CONTENT_URI, "phones");
                ENTERPRISE_CONTENT_URI = Uri.withAppendedPath(Data.ENTERPRISE_CONTENT_URI, "phones");
                CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter");
                ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter_enterprise");
            }

            private Phone() {
            }

            @Deprecated
            @UnsupportedAppUsage
            public static final CharSequence getDisplayLabel(Context context, int n, CharSequence charSequence) {
                return Phone.getTypeLabel(context.getResources(), n, charSequence);
            }

            @Deprecated
            public static final CharSequence getDisplayLabel(Context context, int n, CharSequence charSequence, CharSequence[] arrcharSequence) {
                return Phone.getTypeLabel(context.getResources(), n, charSequence);
            }

            public static final CharSequence getTypeLabel(Resources resources, int n, CharSequence charSequence) {
                if (!(n != 0 && n != 19 || TextUtils.isEmpty(charSequence))) {
                    return charSequence;
                }
                return resources.getText(Phone.getTypeLabelResource(n));
            }

            public static final int getTypeLabelResource(int n) {
                switch (n) {
                    default: {
                        return 17040831;
                    }
                    case 20: {
                        return 17040837;
                    }
                    case 19: {
                        return 17040827;
                    }
                    case 18: {
                        return 17040847;
                    }
                    case 17: {
                        return 17040846;
                    }
                    case 16: {
                        return 17040844;
                    }
                    case 15: {
                        return 17040843;
                    }
                    case 14: {
                        return 17040842;
                    }
                    case 13: {
                        return 17040840;
                    }
                    case 12: {
                        return 17040836;
                    }
                    case 11: {
                        return 17040835;
                    }
                    case 10: {
                        return 17040830;
                    }
                    case 9: {
                        return 17040829;
                    }
                    case 8: {
                        return 17040828;
                    }
                    case 7: {
                        return 17040839;
                    }
                    case 6: {
                        return 17040841;
                    }
                    case 5: {
                        return 17040832;
                    }
                    case 4: {
                        return 17040833;
                    }
                    case 3: {
                        return 17040845;
                    }
                    case 2: {
                        return 17040838;
                    }
                    case 1: 
                }
                return 17040834;
            }
        }

        public static final class Photo
        implements DataColumnsWithJoins,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/photo";
            public static final String PHOTO = "data15";
            public static final String PHOTO_FILE_ID = "data14";

            private Photo() {
            }
        }

        public static final class Relation
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/relation";
            public static final String NAME = "data1";
            public static final int TYPE_ASSISTANT = 1;
            public static final int TYPE_BROTHER = 2;
            public static final int TYPE_CHILD = 3;
            public static final int TYPE_DOMESTIC_PARTNER = 4;
            public static final int TYPE_FATHER = 5;
            public static final int TYPE_FRIEND = 6;
            public static final int TYPE_MANAGER = 7;
            public static final int TYPE_MOTHER = 8;
            public static final int TYPE_PARENT = 9;
            public static final int TYPE_PARTNER = 10;
            public static final int TYPE_REFERRED_BY = 11;
            public static final int TYPE_RELATIVE = 12;
            public static final int TYPE_SISTER = 13;
            public static final int TYPE_SPOUSE = 14;

            private Relation() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int n, CharSequence charSequence) {
                if (n == 0 && !TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(Relation.getTypeLabelResource(n));
            }

            public static final int getTypeLabelResource(int n) {
                switch (n) {
                    default: {
                        return 17040528;
                    }
                    case 14: {
                        return 17040919;
                    }
                    case 13: {
                        return 17040918;
                    }
                    case 12: {
                        return 17040917;
                    }
                    case 11: {
                        return 17040916;
                    }
                    case 10: {
                        return 17040915;
                    }
                    case 9: {
                        return 17040914;
                    }
                    case 8: {
                        return 17040913;
                    }
                    case 7: {
                        return 17040912;
                    }
                    case 6: {
                        return 17040911;
                    }
                    case 5: {
                        return 17040910;
                    }
                    case 4: {
                        return 17040909;
                    }
                    case 3: {
                        return 17040907;
                    }
                    case 2: {
                        return 17040906;
                    }
                    case 1: 
                }
                return 17040905;
            }
        }

        public static final class SipAddress
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/sip_address";
            public static final String SIP_ADDRESS = "data1";
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            private SipAddress() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int n, CharSequence charSequence) {
                if (n == 0 && !TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(SipAddress.getTypeLabelResource(n));
            }

            public static final int getTypeLabelResource(int n) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return 17041033;
                        }
                        return 17041035;
                    }
                    return 17041036;
                }
                return 17041034;
            }
        }

        public static final class StructuredName
        implements DataColumnsWithJoins,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/name";
            public static final String DISPLAY_NAME = "data1";
            public static final String FAMILY_NAME = "data3";
            public static final String FULL_NAME_STYLE = "data10";
            public static final String GIVEN_NAME = "data2";
            public static final String MIDDLE_NAME = "data5";
            public static final String PHONETIC_FAMILY_NAME = "data9";
            public static final String PHONETIC_GIVEN_NAME = "data7";
            public static final String PHONETIC_MIDDLE_NAME = "data8";
            public static final String PHONETIC_NAME_STYLE = "data11";
            public static final String PREFIX = "data4";
            public static final String SUFFIX = "data6";

            private StructuredName() {
            }
        }

        public static final class StructuredPostal
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String CITY = "data7";
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/postal-address_v2";
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/postal-address_v2";
            public static final Uri CONTENT_URI = Uri.withAppendedPath(Data.CONTENT_URI, "postals");
            public static final String COUNTRY = "data10";
            public static final String FORMATTED_ADDRESS = "data1";
            public static final String NEIGHBORHOOD = "data6";
            public static final String POBOX = "data5";
            public static final String POSTCODE = "data9";
            public static final String REGION = "data8";
            public static final String STREET = "data4";
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            private StructuredPostal() {
            }

            public static final CharSequence getTypeLabel(Resources resources, int n, CharSequence charSequence) {
                if (n == 0 && !TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(StructuredPostal.getTypeLabelResource(n));
            }

            public static final int getTypeLabelResource(int n) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return 17040872;
                        }
                        return 17040874;
                    }
                    return 17040875;
                }
                return 17040873;
            }
        }

        public static final class Website
        implements DataColumnsWithJoins,
        CommonColumns,
        ContactCounts {
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/website";
            public static final int TYPE_BLOG = 2;
            public static final int TYPE_FTP = 6;
            public static final int TYPE_HOME = 4;
            public static final int TYPE_HOMEPAGE = 1;
            public static final int TYPE_OTHER = 7;
            public static final int TYPE_PROFILE = 3;
            public static final int TYPE_WORK = 5;
            public static final String URL = "data1";

            private Website() {
            }
        }

    }

    static interface ContactCounts {
        public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    }

    protected static interface ContactNameColumns {
        public static final String DISPLAY_NAME_ALTERNATIVE = "display_name_alt";
        public static final String DISPLAY_NAME_PRIMARY = "display_name";
        public static final String DISPLAY_NAME_SOURCE = "display_name_source";
        public static final String PHONETIC_NAME = "phonetic_name";
        public static final String PHONETIC_NAME_STYLE = "phonetic_name_style";
        public static final String SORT_KEY_ALTERNATIVE = "sort_key_alt";
        public static final String SORT_KEY_PRIMARY = "sort_key";
    }

    protected static interface ContactOptionsColumns {
        public static final String CUSTOM_RINGTONE = "custom_ringtone";
        @Deprecated
        public static final String LAST_TIME_CONTACTED = "last_time_contacted";
        public static final String LR_LAST_TIME_CONTACTED = "last_time_contacted";
        public static final String LR_TIMES_CONTACTED = "times_contacted";
        public static final String PINNED = "pinned";
        public static final String RAW_LAST_TIME_CONTACTED = "x_last_time_contacted";
        public static final String RAW_TIMES_CONTACTED = "x_times_contacted";
        public static final String SEND_TO_VOICEMAIL = "send_to_voicemail";
        public static final String STARRED = "starred";
        @Deprecated
        public static final String TIMES_CONTACTED = "times_contacted";
    }

    protected static interface ContactStatusColumns {
        public static final String CONTACT_CHAT_CAPABILITY = "contact_chat_capability";
        public static final String CONTACT_PRESENCE = "contact_presence";
        public static final String CONTACT_STATUS = "contact_status";
        public static final String CONTACT_STATUS_ICON = "contact_status_icon";
        public static final String CONTACT_STATUS_LABEL = "contact_status_label";
        public static final String CONTACT_STATUS_RES_PACKAGE = "contact_status_res_package";
        public static final String CONTACT_STATUS_TIMESTAMP = "contact_status_ts";
    }

    public static class Contacts
    implements BaseColumns,
    ContactsColumns,
    ContactOptionsColumns,
    ContactNameColumns,
    ContactStatusColumns,
    ContactCounts {
        public static final Uri CONTENT_FILTER_URI;
        @Deprecated
        public static final Uri CONTENT_FREQUENT_URI;
        public static final Uri CONTENT_GROUP_URI;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact";
        public static final Uri CONTENT_LOOKUP_URI;
        public static final Uri CONTENT_MULTI_VCARD_URI;
        public static final Uri CONTENT_STREQUENT_FILTER_URI;
        public static final Uri CONTENT_STREQUENT_URI;
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact";
        public static final Uri CONTENT_URI;
        public static final String CONTENT_VCARD_TYPE = "text/x-vcard";
        public static final Uri CONTENT_VCARD_URI;
        @UnsupportedAppUsage
        public static final Uri CORP_CONTENT_URI;
        public static long ENTERPRISE_CONTACT_ID_BASE = 0L;
        public static String ENTERPRISE_CONTACT_LOOKUP_PREFIX;
        public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
        public static final String QUERY_PARAMETER_VCARD_NO_PHOTO = "no_photo";

        static {
            CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "contacts");
            CORP_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "contacts_corp");
            CONTENT_LOOKUP_URI = Uri.withAppendedPath(CONTENT_URI, "lookup");
            CONTENT_VCARD_URI = Uri.withAppendedPath(CONTENT_URI, "as_vcard");
            CONTENT_MULTI_VCARD_URI = Uri.withAppendedPath(CONTENT_URI, "as_multi_vcard");
            CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter");
            ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter_enterprise");
            CONTENT_STREQUENT_URI = Uri.withAppendedPath(CONTENT_URI, "strequent");
            CONTENT_FREQUENT_URI = Uri.withAppendedPath(CONTENT_URI, "frequent");
            CONTENT_STREQUENT_FILTER_URI = Uri.withAppendedPath(CONTENT_STREQUENT_URI, "filter");
            CONTENT_GROUP_URI = Uri.withAppendedPath(CONTENT_URI, "group");
            ENTERPRISE_CONTACT_ID_BASE = 1000000000L;
            ENTERPRISE_CONTACT_LOOKUP_PREFIX = "c-";
        }

        private Contacts() {
        }

        public static Uri getLookupUri(long l, String string2) {
            if (TextUtils.isEmpty(string2)) {
                return null;
            }
            return ContentUris.withAppendedId(Uri.withAppendedPath(CONTENT_LOOKUP_URI, string2), l);
        }

        public static Uri getLookupUri(ContentResolver object, Uri object2) {
            if ((object = ((ContentResolver)object).query((Uri)object2, new String[]{"lookup", "_id"}, null, null, null)) == null) {
                return null;
            }
            try {
                if (object.moveToFirst()) {
                    object2 = object.getString(0);
                    object2 = Contacts.getLookupUri(object.getLong(1), (String)object2);
                    return object2;
                }
                return null;
            }
            finally {
                object.close();
            }
        }

        public static boolean isEnterpriseContactId(long l) {
            boolean bl = l >= ENTERPRISE_CONTACT_ID_BASE && l < 9223372034707292160L;
            return bl;
        }

        public static Uri lookupContact(ContentResolver object, Uri uri) {
            if (uri == null) {
                return null;
            }
            if ((object = ((ContentResolver)object).query(uri, new String[]{"_id"}, null, null, null)) == null) {
                return null;
            }
            try {
                if (object.moveToFirst()) {
                    long l = object.getLong(0);
                    uri = ContentUris.withAppendedId(CONTENT_URI, l);
                    return uri;
                }
                return null;
            }
            finally {
                object.close();
            }
        }

        @Deprecated
        public static void markAsContacted(ContentResolver contentResolver, long l) {
        }

        public static InputStream openContactPhotoInputStream(ContentResolver contentResolver, Uri uri) {
            return Contacts.openContactPhotoInputStream(contentResolver, uri, false);
        }

        public static InputStream openContactPhotoInputStream(ContentResolver object, Uri object2, boolean bl) {
            block12 : {
                block11 : {
                    if (bl) {
                        Object object3 = Uri.withAppendedPath((Uri)object2, "display_photo");
                        object3 = ((ContentResolver)object).openAssetFileDescriptor((Uri)object3, "r");
                        if (object3 == null) break block11;
                        try {
                            object3 = ((AssetFileDescriptor)object3).createInputStream();
                            return object3;
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                    }
                }
                if ((object2 = Uri.withAppendedPath((Uri)object2, "photo")) == null) {
                    return null;
                }
                if ((object = ((ContentResolver)object).query((Uri)object2, new String[]{"data15"}, null, null, null)) != null) {
                    block13 : {
                        if (!object.moveToNext()) break block12;
                        object2 = object.getBlob(0);
                        if (object2 != null) break block13;
                        object.close();
                        return null;
                    }
                    try {
                        object2 = new ByteArrayInputStream((byte[])object2);
                        return object2;
                    }
                    finally {
                        object.close();
                    }
                }
            }
            if (object != null) {
                object.close();
            }
            return null;
        }

        public static final class AggregationSuggestions
        implements BaseColumns,
        ContactsColumns,
        ContactOptionsColumns,
        ContactStatusColumns {
            public static final String CONTENT_DIRECTORY = "suggestions";
            public static final String PARAMETER_MATCH_NAME = "name";

            private AggregationSuggestions() {
            }

            @UnsupportedAppUsage
            public static final Builder builder() {
                return new Builder();
            }

            public static final class Builder {
                private long mContactId;
                private int mLimit;
                private final ArrayList<String> mValues = new ArrayList();

                public Builder addNameParameter(String string2) {
                    this.mValues.add(string2);
                    return this;
                }

                public Uri build() {
                    Uri.Builder builder = CONTENT_URI.buildUpon();
                    builder.appendEncodedPath(String.valueOf(this.mContactId));
                    builder.appendPath(AggregationSuggestions.CONTENT_DIRECTORY);
                    int n = this.mLimit;
                    if (n != 0) {
                        builder.appendQueryParameter(ContactsContract.LIMIT_PARAM_KEY, String.valueOf(n));
                    }
                    int n2 = this.mValues.size();
                    for (n = 0; n < n2; ++n) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("name:");
                        stringBuilder.append(this.mValues.get(n));
                        builder.appendQueryParameter("query", stringBuilder.toString());
                    }
                    return builder.build();
                }

                public Builder setContactId(long l) {
                    this.mContactId = l;
                    return this;
                }

                public Builder setLimit(int n) {
                    this.mLimit = n;
                    return this;
                }
            }

        }

        public static final class Data
        implements BaseColumns,
        DataColumns {
            public static final String CONTENT_DIRECTORY = "data";

            private Data() {
            }
        }

        public static final class Entity
        implements BaseColumns,
        ContactsColumns,
        ContactNameColumns,
        RawContactsColumns,
        BaseSyncColumns,
        SyncColumns,
        DataColumns,
        StatusColumns,
        ContactOptionsColumns,
        ContactStatusColumns,
        DataUsageStatColumns {
            public static final String CONTENT_DIRECTORY = "entities";
            public static final String DATA_ID = "data_id";
            public static final String RAW_CONTACT_ID = "raw_contact_id";

            private Entity() {
            }
        }

        public static final class Photo
        implements BaseColumns,
        DataColumnsWithJoins {
            public static final String CONTENT_DIRECTORY = "photo";
            public static final String DISPLAY_PHOTO = "display_photo";
            public static final String PHOTO = "data15";
            public static final String PHOTO_FILE_ID = "data14";

            private Photo() {
            }
        }

        @Deprecated
        public static final class StreamItems
        implements StreamItemsColumns {
            @Deprecated
            public static final String CONTENT_DIRECTORY = "stream_items";

            @Deprecated
            private StreamItems() {
            }
        }

    }

    protected static interface ContactsColumns {
        public static final String CONTACT_LAST_UPDATED_TIMESTAMP = "contact_last_updated_timestamp";
        public static final String DISPLAY_NAME = "display_name";
        public static final String HAS_PHONE_NUMBER = "has_phone_number";
        public static final String IN_DEFAULT_DIRECTORY = "in_default_directory";
        public static final String IN_VISIBLE_GROUP = "in_visible_group";
        public static final String IS_USER_PROFILE = "is_user_profile";
        public static final String LOOKUP_KEY = "lookup";
        public static final String NAME_RAW_CONTACT_ID = "name_raw_contact_id";
        public static final String PHOTO_FILE_ID = "photo_file_id";
        public static final String PHOTO_ID = "photo_id";
        public static final String PHOTO_THUMBNAIL_URI = "photo_thumb_uri";
        public static final String PHOTO_URI = "photo_uri";
    }

    public static final class Data
    implements DataColumnsWithJoins,
    ContactCounts {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/data";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "data");
        static final Uri ENTERPRISE_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "data_enterprise");
        public static final String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";

        private Data() {
        }

        public static Uri getContactLookupUri(ContentResolver object, Uri uri) {
            if ((object = ((ContentResolver)object).query(uri, new String[]{"contact_id", "lookup"}, null, null, null)) != null) {
                try {
                    if (object.moveToFirst()) {
                        uri = Contacts.getLookupUri(object.getLong(0), object.getString(1));
                        object.close();
                        return uri;
                    }
                }
                catch (Throwable throwable) {
                    object.close();
                    throw throwable;
                }
            }
            if (object != null) {
                object.close();
            }
            return null;
        }
    }

    protected static interface DataColumns {
        public static final String CARRIER_PRESENCE = "carrier_presence";
        public static final int CARRIER_PRESENCE_VT_CAPABLE = 1;
        public static final String DATA1 = "data1";
        public static final String DATA10 = "data10";
        public static final String DATA11 = "data11";
        public static final String DATA12 = "data12";
        public static final String DATA13 = "data13";
        public static final String DATA14 = "data14";
        public static final String DATA15 = "data15";
        public static final String DATA2 = "data2";
        public static final String DATA3 = "data3";
        public static final String DATA4 = "data4";
        public static final String DATA5 = "data5";
        public static final String DATA6 = "data6";
        public static final String DATA7 = "data7";
        public static final String DATA8 = "data8";
        public static final String DATA9 = "data9";
        public static final String DATA_VERSION = "data_version";
        public static final String HASH_ID = "hash_id";
        public static final String IS_PRIMARY = "is_primary";
        public static final String IS_READ_ONLY = "is_read_only";
        public static final String IS_SUPER_PRIMARY = "is_super_primary";
        public static final String MIMETYPE = "mimetype";
        public static final String PREFERRED_PHONE_ACCOUNT_COMPONENT_NAME = "preferred_phone_account_component_name";
        public static final String PREFERRED_PHONE_ACCOUNT_ID = "preferred_phone_account_id";
        public static final String RAW_CONTACT_ID = "raw_contact_id";
        public static final String RES_PACKAGE = "res_package";
        public static final String SYNC1 = "data_sync1";
        public static final String SYNC2 = "data_sync2";
        public static final String SYNC3 = "data_sync3";
        public static final String SYNC4 = "data_sync4";
    }

    protected static interface DataColumnsWithJoins
    extends BaseColumns,
    DataColumns,
    StatusColumns,
    RawContactsColumns,
    ContactsColumns,
    ContactNameColumns,
    ContactOptionsColumns,
    ContactStatusColumns,
    DataUsageStatColumns {
    }

    @Deprecated
    public static final class DataUsageFeedback {
        public static final Uri DELETE_USAGE_URI;
        public static final Uri FEEDBACK_URI;
        public static final String USAGE_TYPE = "type";
        public static final String USAGE_TYPE_CALL = "call";
        public static final String USAGE_TYPE_LONG_TEXT = "long_text";
        public static final String USAGE_TYPE_SHORT_TEXT = "short_text";

        static {
            FEEDBACK_URI = Uri.withAppendedPath(Data.CONTENT_URI, "usagefeedback");
            DELETE_USAGE_URI = Uri.withAppendedPath(Contacts.CONTENT_URI, "delete_usage");
        }
    }

    protected static interface DataUsageStatColumns {
        @Deprecated
        public static final String LAST_TIME_USED = "last_time_used";
        public static final String LR_LAST_TIME_USED = "last_time_used";
        public static final String LR_TIMES_USED = "times_used";
        public static final String RAW_LAST_TIME_USED = "x_last_time_used";
        public static final String RAW_TIMES_USED = "x_times_used";
        @Deprecated
        public static final String TIMES_USED = "times_used";
    }

    public static final class DeletedContacts
    implements DeletedContactsColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "deleted_contacts");
        private static final int DAYS_KEPT = 30;
        public static final long DAYS_KEPT_MILLISECONDS = 2592000000L;

        private DeletedContacts() {
        }
    }

    protected static interface DeletedContactsColumns {
        public static final String CONTACT_DELETED_TIMESTAMP = "contact_deleted_timestamp";
        public static final String CONTACT_ID = "contact_id";
    }

    public static final class Directory
    implements BaseColumns {
        public static final String ACCOUNT_NAME = "accountName";
        public static final String ACCOUNT_TYPE = "accountType";
        public static final String CALLER_PACKAGE_PARAM_KEY = "callerPackage";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_directory";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_directories";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "directories");
        public static final long DEFAULT = 0L;
        public static final String DIRECTORY_AUTHORITY = "authority";
        public static final String DISPLAY_NAME = "displayName";
        public static final Uri ENTERPRISE_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "directories_enterprise");
        public static final long ENTERPRISE_DEFAULT = 1000000000L;
        public static final long ENTERPRISE_DIRECTORY_ID_BASE = 1000000000L;
        public static final Uri ENTERPRISE_FILE_URI = Uri.withAppendedPath(AUTHORITY_URI, "directory_file_enterprise");
        public static final long ENTERPRISE_LOCAL_INVISIBLE = 1000000001L;
        public static final String EXPORT_SUPPORT = "exportSupport";
        public static final int EXPORT_SUPPORT_ANY_ACCOUNT = 2;
        public static final int EXPORT_SUPPORT_NONE = 0;
        public static final int EXPORT_SUPPORT_SAME_ACCOUNT_ONLY = 1;
        public static final long LOCAL_INVISIBLE = 1L;
        public static final String PACKAGE_NAME = "packageName";
        public static final String PHOTO_SUPPORT = "photoSupport";
        public static final int PHOTO_SUPPORT_FULL = 3;
        public static final int PHOTO_SUPPORT_FULL_SIZE_ONLY = 2;
        public static final int PHOTO_SUPPORT_NONE = 0;
        public static final int PHOTO_SUPPORT_THUMBNAIL_ONLY = 1;
        public static final String SHORTCUT_SUPPORT = "shortcutSupport";
        public static final int SHORTCUT_SUPPORT_DATA_ITEMS_ONLY = 1;
        public static final int SHORTCUT_SUPPORT_FULL = 2;
        public static final int SHORTCUT_SUPPORT_NONE = 0;
        public static final String TYPE_RESOURCE_ID = "typeResourceId";

        private Directory() {
        }

        public static boolean isEnterpriseDirectoryId(long l) {
            boolean bl = l >= 1000000000L;
            return bl;
        }

        public static boolean isRemoteDirectory(long l) {
            return Directory.isRemoteDirectoryId(l);
        }

        public static boolean isRemoteDirectoryId(long l) {
            boolean bl = l != 0L && l != 1L && l != 1000000000L && l != 1000000001L;
            return bl;
        }

        public static void notifyDirectoryChange(ContentResolver contentResolver) {
            ContentValues contentValues = new ContentValues();
            contentResolver.update(CONTENT_URI, contentValues, null, null);
        }
    }

    public static interface DisplayNameSources {
        public static final int EMAIL = 10;
        public static final int NICKNAME = 35;
        public static final int ORGANIZATION = 30;
        public static final int PHONE = 20;
        public static final int STRUCTURED_NAME = 40;
        public static final int STRUCTURED_PHONETIC_NAME = 37;
        public static final int UNDEFINED = 0;
    }

    public static final class DisplayPhoto {
        public static final Uri CONTENT_MAX_DIMENSIONS_URI;
        public static final Uri CONTENT_URI;
        public static final String DISPLAY_MAX_DIM = "display_max_dim";
        public static final String THUMBNAIL_MAX_DIM = "thumbnail_max_dim";

        static {
            CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "display_photo");
            CONTENT_MAX_DIMENSIONS_URI = Uri.withAppendedPath(AUTHORITY_URI, "photo_dimensions");
        }

        private DisplayPhoto() {
        }
    }

    public static interface FullNameStyle {
        public static final int CHINESE = 3;
        public static final int CJK = 2;
        public static final int JAPANESE = 4;
        public static final int KOREAN = 5;
        public static final int UNDEFINED = 0;
        public static final int WESTERN = 1;
    }

    public static final class Groups
    implements BaseColumns,
    GroupsColumns,
    SyncColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group";
        public static final Uri CONTENT_SUMMARY_URI;
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/group";
        public static final Uri CONTENT_URI;

        static {
            CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "groups");
            CONTENT_SUMMARY_URI = Uri.withAppendedPath(AUTHORITY_URI, "groups_summary");
        }

        private Groups() {
        }

        public static EntityIterator newEntityIterator(Cursor cursor) {
            return new EntityIteratorImpl(cursor);
        }

        private static class EntityIteratorImpl
        extends CursorEntityIterator {
            public EntityIteratorImpl(Cursor cursor) {
                super(cursor);
            }

            @Override
            public Entity getEntityAndIncrementCursor(Cursor cursor) throws RemoteException {
                ContentValues contentValues = new ContentValues();
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "_id");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_name");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_type");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "dirty");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "version");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sourceid");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "res_package");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "title");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "title_res");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "group_visible");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync1");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync2");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync3");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync4");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "system_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "deleted");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "notes");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "should_sync");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "favorites");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "auto_add");
                cursor.moveToNext();
                return new Entity(contentValues);
            }
        }

    }

    protected static interface GroupsColumns {
        public static final String ACCOUNT_TYPE_AND_DATA_SET = "account_type_and_data_set";
        public static final String AUTO_ADD = "auto_add";
        public static final String DATA_SET = "data_set";
        public static final String DELETED = "deleted";
        public static final String FAVORITES = "favorites";
        public static final String GROUP_IS_READ_ONLY = "group_is_read_only";
        public static final String GROUP_VISIBLE = "group_visible";
        public static final String NOTES = "notes";
        public static final String PARAM_RETURN_GROUP_COUNT_PER_ACCOUNT = "return_group_count_per_account";
        public static final String RES_PACKAGE = "res_package";
        public static final String SHOULD_SYNC = "should_sync";
        public static final String SUMMARY_COUNT = "summ_count";
        public static final String SUMMARY_GROUP_COUNT_PER_ACCOUNT = "group_count_per_account";
        public static final String SUMMARY_WITH_PHONES = "summ_phones";
        public static final String SYSTEM_ID = "system_id";
        public static final String TITLE = "title";
        public static final String TITLE_RES = "title_res";
    }

    public static final class Intents {
        public static final String ACTION_GET_MULTIPLE_PHONES = "com.android.contacts.action.GET_MULTIPLE_PHONES";
        public static final String ACTION_PROFILE_CHANGED = "android.provider.Contacts.PROFILE_CHANGED";
        public static final String ACTION_VOICE_SEND_MESSAGE_TO_CONTACTS = "android.provider.action.VOICE_SEND_MESSAGE_TO_CONTACTS";
        public static final String ATTACH_IMAGE = "com.android.contacts.action.ATTACH_IMAGE";
        public static final String CONTACTS_DATABASE_CREATED = "android.provider.Contacts.DATABASE_CREATED";
        public static final String EXTRA_CREATE_DESCRIPTION = "com.android.contacts.action.CREATE_DESCRIPTION";
        @Deprecated
        public static final String EXTRA_EXCLUDE_MIMES = "exclude_mimes";
        public static final String EXTRA_FORCE_CREATE = "com.android.contacts.action.FORCE_CREATE";
        @Deprecated
        public static final String EXTRA_MODE = "mode";
        public static final String EXTRA_PHONE_URIS = "com.android.contacts.extra.PHONE_URIS";
        public static final String EXTRA_RECIPIENT_CONTACT_CHAT_ID = "android.provider.extra.RECIPIENT_CONTACT_CHAT_ID";
        public static final String EXTRA_RECIPIENT_CONTACT_NAME = "android.provider.extra.RECIPIENT_CONTACT_NAME";
        public static final String EXTRA_RECIPIENT_CONTACT_URI = "android.provider.extra.RECIPIENT_CONTACT_URI";
        @Deprecated
        public static final String EXTRA_TARGET_RECT = "target_rect";
        public static final String INVITE_CONTACT = "com.android.contacts.action.INVITE_CONTACT";
        public static final String METADATA_ACCOUNT_TYPE = "android.provider.account_type";
        public static final String METADATA_MIMETYPE = "android.provider.mimetype";
        @Deprecated
        public static final int MODE_LARGE = 3;
        @Deprecated
        public static final int MODE_MEDIUM = 2;
        @Deprecated
        public static final int MODE_SMALL = 1;
        public static final String SEARCH_SUGGESTION_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CLICKED";
        public static final String SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED";
        public static final String SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED";
        public static final String SHOW_OR_CREATE_CONTACT = "com.android.contacts.action.SHOW_OR_CREATE_CONTACT";

        public static final class Insert {
            public static final String ACTION = "android.intent.action.INSERT";
            public static final String COMPANY = "company";
            public static final String DATA = "data";
            public static final String EMAIL = "email";
            public static final String EMAIL_ISPRIMARY = "email_isprimary";
            public static final String EMAIL_TYPE = "email_type";
            public static final String EXTRA_ACCOUNT = "android.provider.extra.ACCOUNT";
            public static final String EXTRA_DATA_SET = "android.provider.extra.DATA_SET";
            public static final String FULL_MODE = "full_mode";
            public static final String IM_HANDLE = "im_handle";
            public static final String IM_ISPRIMARY = "im_isprimary";
            public static final String IM_PROTOCOL = "im_protocol";
            public static final String JOB_TITLE = "job_title";
            public static final String NAME = "name";
            public static final String NOTES = "notes";
            public static final String PHONE = "phone";
            public static final String PHONETIC_NAME = "phonetic_name";
            public static final String PHONE_ISPRIMARY = "phone_isprimary";
            public static final String PHONE_TYPE = "phone_type";
            public static final String POSTAL = "postal";
            public static final String POSTAL_ISPRIMARY = "postal_isprimary";
            public static final String POSTAL_TYPE = "postal_type";
            public static final String SECONDARY_EMAIL = "secondary_email";
            public static final String SECONDARY_EMAIL_TYPE = "secondary_email_type";
            public static final String SECONDARY_PHONE = "secondary_phone";
            public static final String SECONDARY_PHONE_TYPE = "secondary_phone_type";
            public static final String TERTIARY_EMAIL = "tertiary_email";
            public static final String TERTIARY_EMAIL_TYPE = "tertiary_email_type";
            public static final String TERTIARY_PHONE = "tertiary_phone";
            public static final String TERTIARY_PHONE_TYPE = "tertiary_phone_type";
        }

    }

    @SystemApi
    public static final class MetadataSync
    implements BaseColumns,
    MetadataSyncColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_metadata";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_metadata";
        public static final Uri CONTENT_URI;
        public static final String METADATA_AUTHORITY = "com.android.contacts.metadata";
        public static final Uri METADATA_AUTHORITY_URI;

        static {
            METADATA_AUTHORITY_URI = Uri.parse("content://com.android.contacts.metadata");
            CONTENT_URI = Uri.withAppendedPath(METADATA_AUTHORITY_URI, "metadata_sync");
        }

        private MetadataSync() {
        }
    }

    @SystemApi
    protected static interface MetadataSyncColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DATA = "data";
        public static final String DATA_SET = "data_set";
        public static final String DELETED = "deleted";
        public static final String RAW_CONTACT_BACKUP_ID = "raw_contact_backup_id";
    }

    @SystemApi
    public static final class MetadataSyncState
    implements BaseColumns,
    MetadataSyncStateColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_metadata_sync_state";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_metadata_sync_state";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(MetadataSync.METADATA_AUTHORITY_URI, "metadata_sync_state");

        private MetadataSyncState() {
        }
    }

    @SystemApi
    protected static interface MetadataSyncStateColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DATA_SET = "data_set";
        public static final String STATE = "state";
    }

    public static final class PhoneLookup
    implements BaseColumns,
    PhoneLookupColumns,
    ContactsColumns,
    ContactOptionsColumns,
    ContactNameColumns {
        public static final Uri CONTENT_FILTER_URI = Uri.withAppendedPath(AUTHORITY_URI, "phone_lookup");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone_lookup";
        public static final Uri ENTERPRISE_CONTENT_FILTER_URI = Uri.withAppendedPath(AUTHORITY_URI, "phone_lookup_enterprise");
        public static final String QUERY_PARAMETER_SIP_ADDRESS = "sip";

        private PhoneLookup() {
        }
    }

    protected static interface PhoneLookupColumns {
        public static final String CONTACT_ID = "contact_id";
        public static final String DATA_ID = "data_id";
        public static final String LABEL = "label";
        public static final String NORMALIZED_NUMBER = "normalized_number";
        public static final String NUMBER = "number";
        public static final String TYPE = "type";
    }

    public static interface PhoneticNameStyle {
        public static final int JAPANESE = 4;
        public static final int KOREAN = 5;
        public static final int PINYIN = 3;
        public static final int UNDEFINED = 0;
    }

    public static final class PhotoFiles
    implements BaseColumns,
    PhotoFilesColumns {
        private PhotoFiles() {
        }
    }

    protected static interface PhotoFilesColumns {
        public static final String FILESIZE = "filesize";
        public static final String HEIGHT = "height";
        public static final String WIDTH = "width";
    }

    public static final class PinnedPositions {
        public static final int DEMOTED = -1;
        public static final String UNDEMOTE_METHOD = "undemote";
        public static final int UNPINNED = 0;

        public static void pin(ContentResolver contentResolver, long l, int n) {
            Uri uri = Uri.withAppendedPath(Contacts.CONTENT_URI, String.valueOf(l));
            ContentValues contentValues = new ContentValues();
            contentValues.put("pinned", n);
            contentResolver.update(uri, contentValues, null, null);
        }

        public static void undemote(ContentResolver contentResolver, long l) {
            contentResolver.call(AUTHORITY_URI, UNDEMOTE_METHOD, String.valueOf(l), null);
        }
    }

    @Deprecated
    public static final class Presence
    extends StatusUpdates {
    }

    protected static interface PresenceColumns {
        public static final String CUSTOM_PROTOCOL = "custom_protocol";
        public static final String DATA_ID = "presence_data_id";
        public static final String IM_ACCOUNT = "im_account";
        public static final String IM_HANDLE = "im_handle";
        public static final String PROTOCOL = "protocol";
    }

    public static final class Profile
    implements BaseColumns,
    ContactsColumns,
    ContactOptionsColumns,
    ContactNameColumns,
    ContactStatusColumns {
        public static final Uri CONTENT_RAW_CONTACTS_URI;
        public static final Uri CONTENT_URI;
        public static final Uri CONTENT_VCARD_URI;
        public static final long MIN_ID = 9223372034707292160L;

        static {
            CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "profile");
            CONTENT_VCARD_URI = Uri.withAppendedPath(CONTENT_URI, "as_vcard");
            CONTENT_RAW_CONTACTS_URI = Uri.withAppendedPath(CONTENT_URI, "raw_contacts");
        }

        private Profile() {
        }
    }

    public static final class ProfileSyncState
    implements SyncStateContract.Columns {
        public static final String CONTENT_DIRECTORY = "syncstate";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Profile.CONTENT_URI, "syncstate");

        private ProfileSyncState() {
        }

        public static byte[] get(ContentProviderClient contentProviderClient, Account account) throws RemoteException {
            return SyncStateContract.Helpers.get(contentProviderClient, CONTENT_URI, account);
        }

        public static Pair<Uri, byte[]> getWithUri(ContentProviderClient contentProviderClient, Account account) throws RemoteException {
            return SyncStateContract.Helpers.getWithUri(contentProviderClient, CONTENT_URI, account);
        }

        public static ContentProviderOperation newSetOperation(Account account, byte[] arrby) {
            return SyncStateContract.Helpers.newSetOperation(CONTENT_URI, account, arrby);
        }

        public static void set(ContentProviderClient contentProviderClient, Account account, byte[] arrby) throws RemoteException {
            SyncStateContract.Helpers.set(contentProviderClient, CONTENT_URI, account, arrby);
        }
    }

    public static final class ProviderStatus {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/provider_status";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "provider_status");
        public static final String DATABASE_CREATION_TIMESTAMP = "database_creation_timestamp";
        public static final String STATUS = "status";
        public static final int STATUS_BUSY = 1;
        public static final int STATUS_EMPTY = 2;
        public static final int STATUS_NORMAL = 0;

        private ProviderStatus() {
        }
    }

    public static final class QuickContact {
        public static final String ACTION_QUICK_CONTACT = "android.provider.action.QUICK_CONTACT";
        public static final String EXTRA_EXCLUDE_MIMES = "android.provider.extra.EXCLUDE_MIMES";
        public static final String EXTRA_MODE = "android.provider.extra.MODE";
        public static final String EXTRA_PRIORITIZED_MIMETYPE = "android.provider.extra.PRIORITIZED_MIMETYPE";
        @Deprecated
        public static final String EXTRA_TARGET_RECT = "android.provider.extra.TARGET_RECT";
        public static final int MODE_DEFAULT = 3;
        public static final int MODE_LARGE = 3;
        public static final int MODE_MEDIUM = 2;
        public static final int MODE_SMALL = 1;

        @UnsupportedAppUsage
        public static Intent composeQuickContactsIntent(Context object, Rect rect, Uri uri, int n, String[] arrstring) {
            while (object instanceof ContextWrapper && !(object instanceof Activity)) {
                object = ((ContextWrapper)object).getBaseContext();
            }
            int n2 = object instanceof Activity ? 0 : 268468224;
            object = new Intent(ACTION_QUICK_CONTACT).addFlags(n2 | 536870912);
            ((Intent)object).setData(uri);
            ((Intent)object).setSourceBounds(rect);
            ((Intent)object).putExtra(EXTRA_MODE, n);
            ((Intent)object).putExtra(EXTRA_EXCLUDE_MIMES, arrstring);
            return object;
        }

        public static Intent composeQuickContactsIntent(Context context, View view, Uri uri, int n, String[] arrstring) {
            float f = context.getResources().getCompatibilityInfo().applicationScale;
            int[] arrn = new int[2];
            view.getLocationOnScreen(arrn);
            Rect rect = new Rect();
            rect.left = (int)((float)arrn[0] * f + 0.5f);
            rect.top = (int)((float)arrn[1] * f + 0.5f);
            rect.right = (int)((float)(arrn[0] + view.getWidth()) * f + 0.5f);
            rect.bottom = (int)((float)(arrn[1] + view.getHeight()) * f + 0.5f);
            return QuickContact.composeQuickContactsIntent(context, rect, uri, n, arrstring);
        }

        public static Intent rebuildManagedQuickContactsIntent(String object, long l, boolean bl, long l2, Intent intent) {
            Intent intent2 = new Intent(ACTION_QUICK_CONTACT);
            Object object2 = null;
            if (!TextUtils.isEmpty((CharSequence)object)) {
                object = bl ? Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, (String)object) : Contacts.getLookupUri(l, (String)object);
                object2 = object;
            }
            object = object2;
            if (object2 != null) {
                object = object2;
                if (l2 != 0L) {
                    object = ((Uri)object2).buildUpon().appendQueryParameter(ContactsContract.DIRECTORY_PARAM_KEY, String.valueOf(l2)).build();
                }
            }
            intent2.setData((Uri)object);
            intent2.setFlags(intent.getFlags() | 268435456);
            intent2.setSourceBounds(intent.getSourceBounds());
            intent2.putExtra(EXTRA_MODE, intent.getIntExtra(EXTRA_MODE, 3));
            intent2.putExtra(EXTRA_EXCLUDE_MIMES, intent.getStringArrayExtra(EXTRA_EXCLUDE_MIMES));
            return intent2;
        }

        public static void showQuickContact(Context context, Rect rect, Uri uri, int n, String[] arrstring) {
            ContactsInternal.startQuickContactWithErrorToast(context, QuickContact.composeQuickContactsIntent(context, rect, uri, n, arrstring));
        }

        public static void showQuickContact(Context context, Rect parcelable, Uri uri, String[] arrstring, String string2) {
            parcelable = QuickContact.composeQuickContactsIntent(context, (Rect)parcelable, uri, 3, arrstring);
            ((Intent)parcelable).putExtra(EXTRA_PRIORITIZED_MIMETYPE, string2);
            ContactsInternal.startQuickContactWithErrorToast(context, (Intent)parcelable);
        }

        public static void showQuickContact(Context context, View view, Uri uri, int n, String[] arrstring) {
            ContactsInternal.startQuickContactWithErrorToast(context, QuickContact.composeQuickContactsIntent(context, view, uri, n, arrstring));
        }

        public static void showQuickContact(Context context, View object, Uri uri, String[] arrstring, String string2) {
            object = QuickContact.composeQuickContactsIntent(context, (View)object, uri, 3, arrstring);
            ((Intent)object).putExtra(EXTRA_PRIORITIZED_MIMETYPE, string2);
            ContactsInternal.startQuickContactWithErrorToast(context, (Intent)object);
        }
    }

    public static final class RawContacts
    implements BaseColumns,
    RawContactsColumns,
    ContactOptionsColumns,
    ContactNameColumns,
    SyncColumns {
        public static final int AGGREGATION_MODE_DEFAULT = 0;
        public static final int AGGREGATION_MODE_DISABLED = 3;
        @Deprecated
        public static final int AGGREGATION_MODE_IMMEDIATE = 1;
        public static final int AGGREGATION_MODE_SUSPENDED = 2;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/raw_contact";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/raw_contact";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "raw_contacts");

        private RawContacts() {
        }

        public static Uri getContactLookupUri(ContentResolver object, Uri uri) {
            if ((object = ((ContentResolver)object).query(Uri.withAppendedPath(uri, "data"), new String[]{"contact_id", "lookup"}, null, null, null)) != null) {
                try {
                    if (object.moveToFirst()) {
                        uri = Contacts.getLookupUri(object.getLong(0), object.getString(1));
                        object.close();
                        return uri;
                    }
                }
                catch (Throwable throwable) {
                    object.close();
                    throw throwable;
                }
            }
            if (object != null) {
                object.close();
            }
            return null;
        }

        public static EntityIterator newEntityIterator(Cursor cursor) {
            return new EntityIteratorImpl(cursor);
        }

        public static final class Data
        implements BaseColumns,
        DataColumns {
            public static final String CONTENT_DIRECTORY = "data";

            private Data() {
            }
        }

        public static final class DisplayPhoto {
            public static final String CONTENT_DIRECTORY = "display_photo";

            private DisplayPhoto() {
            }
        }

        public static final class Entity
        implements BaseColumns,
        DataColumns {
            public static final String CONTENT_DIRECTORY = "entity";
            public static final String DATA_ID = "data_id";

            private Entity() {
            }
        }

        private static class EntityIteratorImpl
        extends CursorEntityIterator {
            private static final String[] DATA_KEYS = new String[]{"data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8", "data9", "data10", "data11", "data12", "data13", "data14", "data15", "data_sync1", "data_sync2", "data_sync3", "data_sync4"};

            public EntityIteratorImpl(Cursor cursor) {
                super(cursor);
            }

            @Override
            public android.content.Entity getEntityAndIncrementCursor(Cursor cursor) throws RemoteException {
                int n = cursor.getColumnIndexOrThrow("_id");
                long l = cursor.getLong(n);
                ContentValues object2 = new ContentValues();
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, object2, "account_name");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, object2, "account_type");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, object2, "data_set");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, object2, "_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, object2, "dirty");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, object2, "version");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, object2, "sourceid");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, object2, "sync1");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, object2, "sync2");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, object2, "sync3");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, object2, "sync4");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, object2, "deleted");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, object2, "contact_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, object2, "starred");
                android.content.Entity entity = new android.content.Entity(object2);
                while (l == cursor.getLong(n)) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_id", cursor.getLong(cursor.getColumnIndexOrThrow("data_id")));
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "res_package");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "mimetype");
                    DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "is_primary");
                    DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "is_super_primary");
                    DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "data_version");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "group_sourceid");
                    DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data_version");
                    for (String string2 : DATA_KEYS) {
                        int n2 = cursor.getColumnIndexOrThrow(string2);
                        int n3 = cursor.getType(n2);
                        if (n3 == 0) continue;
                        if (n3 != 1 && n3 != 2 && n3 != 3) {
                            if (n3 == 4) {
                                contentValues.put(string2, cursor.getBlob(n2));
                                continue;
                            }
                            throw new IllegalStateException("Invalid or unhandled data type");
                        }
                        contentValues.put(string2, cursor.getString(n2));
                    }
                    entity.addSubValue(android.provider.ContactsContract$Data.CONTENT_URI, contentValues);
                    if (cursor.moveToNext()) continue;
                }
                return entity;
            }
        }

        @Deprecated
        public static final class StreamItems
        implements BaseColumns,
        StreamItemsColumns {
            @Deprecated
            public static final String CONTENT_DIRECTORY = "stream_items";

            @Deprecated
            private StreamItems() {
            }
        }

    }

    protected static interface RawContactsColumns {
        public static final String ACCOUNT_TYPE_AND_DATA_SET = "account_type_and_data_set";
        public static final String AGGREGATION_MODE = "aggregation_mode";
        public static final String BACKUP_ID = "backup_id";
        public static final String CONTACT_ID = "contact_id";
        public static final String DATA_SET = "data_set";
        public static final String DELETED = "deleted";
        public static final String METADATA_DIRTY = "metadata_dirty";
        public static final String RAW_CONTACT_IS_READ_ONLY = "raw_contact_is_read_only";
        public static final String RAW_CONTACT_IS_USER_PROFILE = "raw_contact_is_user_profile";
    }

    public static final class RawContactsEntity
    implements BaseColumns,
    DataColumns,
    RawContactsColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/raw_contact_entity";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "raw_contact_entities");
        public static final Uri CORP_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "raw_contact_entities_corp");
        public static final String DATA_ID = "data_id";
        public static final String FOR_EXPORT_ONLY = "for_export_only";
        public static final Uri PROFILE_CONTENT_URI = Uri.withAppendedPath(Profile.CONTENT_URI, "raw_contact_entities");

        private RawContactsEntity() {
        }
    }

    public static class SearchSnippets {
        public static final String DEFERRED_SNIPPETING_KEY = "deferred_snippeting";
        public static final String SNIPPET = "snippet";
        public static final String SNIPPET_ARGS_PARAM_KEY = "snippet_args";
    }

    public static final class Settings
    implements SettingsColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/setting";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/setting";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "settings");

        private Settings() {
        }
    }

    protected static interface SettingsColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String ANY_UNSYNCED = "any_unsynced";
        public static final String DATA_SET = "data_set";
        public static final String SHOULD_SYNC = "should_sync";
        public static final String UNGROUPED_COUNT = "summ_count";
        public static final String UNGROUPED_VISIBLE = "ungrouped_visible";
        public static final String UNGROUPED_WITH_PHONES = "summ_phones";
    }

    protected static interface StatusColumns {
        public static final int AVAILABLE = 5;
        public static final int AWAY = 2;
        public static final int CAPABILITY_HAS_CAMERA = 4;
        public static final int CAPABILITY_HAS_VIDEO = 2;
        public static final int CAPABILITY_HAS_VOICE = 1;
        public static final String CHAT_CAPABILITY = "chat_capability";
        public static final int DO_NOT_DISTURB = 4;
        public static final int IDLE = 3;
        public static final int INVISIBLE = 1;
        public static final int OFFLINE = 0;
        public static final String PRESENCE = "mode";
        @Deprecated
        public static final String PRESENCE_CUSTOM_STATUS = "status";
        @Deprecated
        public static final String PRESENCE_STATUS = "mode";
        public static final String STATUS = "status";
        public static final String STATUS_ICON = "status_icon";
        public static final String STATUS_LABEL = "status_label";
        public static final String STATUS_RES_PACKAGE = "status_res_package";
        public static final String STATUS_TIMESTAMP = "status_ts";
    }

    public static class StatusUpdates
    implements StatusColumns,
    PresenceColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/status-update";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/status-update";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "status_updates");
        public static final Uri PROFILE_CONTENT_URI = Uri.withAppendedPath(Profile.CONTENT_URI, "status_updates");

        private StatusUpdates() {
        }

        public static final int getPresenceIconResourceId(int n) {
            if (n != 1) {
                if (n != 2 && n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return 17301610;
                        }
                        return 17301611;
                    }
                    return 17301608;
                }
                return 17301607;
            }
            return 17301609;
        }

        public static final int getPresencePrecedence(int n) {
            return n;
        }
    }

    @Deprecated
    public static final class StreamItemPhotos
    implements BaseColumns,
    StreamItemPhotosColumns {
        @Deprecated
        public static final String PHOTO = "photo";

        @Deprecated
        private StreamItemPhotos() {
        }
    }

    @Deprecated
    protected static interface StreamItemPhotosColumns {
        @Deprecated
        public static final String PHOTO_FILE_ID = "photo_file_id";
        @Deprecated
        public static final String PHOTO_URI = "photo_uri";
        @Deprecated
        public static final String SORT_INDEX = "sort_index";
        @Deprecated
        public static final String STREAM_ITEM_ID = "stream_item_id";
        @Deprecated
        public static final String SYNC1 = "stream_item_photo_sync1";
        @Deprecated
        public static final String SYNC2 = "stream_item_photo_sync2";
        @Deprecated
        public static final String SYNC3 = "stream_item_photo_sync3";
        @Deprecated
        public static final String SYNC4 = "stream_item_photo_sync4";
    }

    @Deprecated
    public static final class StreamItems
    implements BaseColumns,
    StreamItemsColumns {
        @Deprecated
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/stream_item";
        @Deprecated
        public static final Uri CONTENT_LIMIT_URI;
        @Deprecated
        public static final Uri CONTENT_PHOTO_URI;
        @Deprecated
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/stream_item";
        @Deprecated
        public static final Uri CONTENT_URI;
        @Deprecated
        public static final String MAX_ITEMS = "max_items";

        static {
            CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "stream_items");
            CONTENT_PHOTO_URI = Uri.withAppendedPath(CONTENT_URI, "photo");
            CONTENT_LIMIT_URI = Uri.withAppendedPath(AUTHORITY_URI, "stream_items_limit");
        }

        @Deprecated
        private StreamItems() {
        }

        @Deprecated
        public static final class StreamItemPhotos
        implements BaseColumns,
        StreamItemPhotosColumns {
            @Deprecated
            public static final String CONTENT_DIRECTORY = "photo";
            @Deprecated
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/stream_item_photo";
            @Deprecated
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/stream_item_photo";

            @Deprecated
            private StreamItemPhotos() {
            }
        }

    }

    @Deprecated
    protected static interface StreamItemsColumns {
        @Deprecated
        public static final String ACCOUNT_NAME = "account_name";
        @Deprecated
        public static final String ACCOUNT_TYPE = "account_type";
        @Deprecated
        public static final String COMMENTS = "comments";
        @Deprecated
        public static final String CONTACT_ID = "contact_id";
        @Deprecated
        public static final String CONTACT_LOOKUP_KEY = "contact_lookup";
        @Deprecated
        public static final String DATA_SET = "data_set";
        @Deprecated
        public static final String RAW_CONTACT_ID = "raw_contact_id";
        @Deprecated
        public static final String RAW_CONTACT_SOURCE_ID = "raw_contact_source_id";
        @Deprecated
        public static final String RES_ICON = "icon";
        @Deprecated
        public static final String RES_LABEL = "label";
        @Deprecated
        public static final String RES_PACKAGE = "res_package";
        @Deprecated
        public static final String SYNC1 = "stream_item_sync1";
        @Deprecated
        public static final String SYNC2 = "stream_item_sync2";
        @Deprecated
        public static final String SYNC3 = "stream_item_sync3";
        @Deprecated
        public static final String SYNC4 = "stream_item_sync4";
        @Deprecated
        public static final String TEXT = "text";
        @Deprecated
        public static final String TIMESTAMP = "timestamp";
    }

    protected static interface SyncColumns
    extends BaseSyncColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DIRTY = "dirty";
        public static final String SOURCE_ID = "sourceid";
        public static final String VERSION = "version";
    }

    public static final class SyncState
    implements SyncStateContract.Columns {
        public static final String CONTENT_DIRECTORY = "syncstate";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "syncstate");

        private SyncState() {
        }

        public static byte[] get(ContentProviderClient contentProviderClient, Account account) throws RemoteException {
            return SyncStateContract.Helpers.get(contentProviderClient, CONTENT_URI, account);
        }

        public static Pair<Uri, byte[]> getWithUri(ContentProviderClient contentProviderClient, Account account) throws RemoteException {
            return SyncStateContract.Helpers.getWithUri(contentProviderClient, CONTENT_URI, account);
        }

        public static ContentProviderOperation newSetOperation(Account account, byte[] arrby) {
            return SyncStateContract.Helpers.newSetOperation(CONTENT_URI, account, arrby);
        }

        public static void set(ContentProviderClient contentProviderClient, Account account, byte[] arrby) throws RemoteException {
            SyncStateContract.Helpers.set(contentProviderClient, CONTENT_URI, account, arrby);
        }
    }

    @Deprecated
    public static interface SyncStateColumns
    extends SyncStateContract.Columns {
    }

}

