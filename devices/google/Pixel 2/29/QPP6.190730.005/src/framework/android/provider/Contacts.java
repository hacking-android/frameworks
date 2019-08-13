/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Deprecated
public class Contacts {
    @Deprecated
    public static final String AUTHORITY = "contacts";
    @Deprecated
    public static final Uri CONTENT_URI = Uri.parse("content://contacts");
    @Deprecated
    public static final int KIND_EMAIL = 1;
    @Deprecated
    public static final int KIND_IM = 3;
    @Deprecated
    public static final int KIND_ORGANIZATION = 4;
    @Deprecated
    public static final int KIND_PHONE = 5;
    @Deprecated
    public static final int KIND_POSTAL = 2;
    private static final String TAG = "Contacts";

    private Contacts() {
    }

    @Deprecated
    public static final class ContactMethods
    implements BaseColumns,
    ContactMethodsColumns,
    PeopleColumns {
        @Deprecated
        public static final String CONTENT_EMAIL_ITEM_TYPE = "vnd.android.cursor.item/email";
        @Deprecated
        public static final String CONTENT_EMAIL_TYPE = "vnd.android.cursor.dir/email";
        @Deprecated
        public static final Uri CONTENT_EMAIL_URI;
        @Deprecated
        public static final String CONTENT_IM_ITEM_TYPE = "vnd.android.cursor.item/jabber-im";
        @Deprecated
        public static final String CONTENT_POSTAL_ITEM_TYPE = "vnd.android.cursor.item/postal-address";
        @Deprecated
        public static final String CONTENT_POSTAL_TYPE = "vnd.android.cursor.dir/postal-address";
        @Deprecated
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact-methods";
        @Deprecated
        public static final Uri CONTENT_URI;
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "name ASC";
        @Deprecated
        public static final String PERSON_ID = "person";
        @Deprecated
        public static final String POSTAL_LOCATION_LATITUDE = "data";
        @Deprecated
        public static final String POSTAL_LOCATION_LONGITUDE = "aux_data";
        @Deprecated
        public static final int PROTOCOL_AIM = 0;
        @Deprecated
        public static final int PROTOCOL_GOOGLE_TALK = 5;
        @Deprecated
        public static final int PROTOCOL_ICQ = 6;
        @Deprecated
        public static final int PROTOCOL_JABBER = 7;
        @Deprecated
        public static final int PROTOCOL_MSN = 1;
        @Deprecated
        public static final int PROTOCOL_QQ = 4;
        @Deprecated
        public static final int PROTOCOL_SKYPE = 3;
        @Deprecated
        public static final int PROTOCOL_YAHOO = 2;

        static {
            CONTENT_URI = Uri.parse("content://contacts/contact_methods");
            CONTENT_EMAIL_URI = Uri.parse("content://contacts/contact_methods/email");
        }

        private ContactMethods() {
        }

        @Deprecated
        public static Object decodeImProtocol(String string2) {
            if (string2 == null) {
                return null;
            }
            if (string2.startsWith("pre:")) {
                return Integer.parseInt(string2.substring(4));
            }
            if (string2.startsWith("custom:")) {
                return string2.substring(7);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("the value is not a valid encoded protocol, ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @Deprecated
        public static String encodeCustomImProtocol(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("custom:");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        @Deprecated
        public static String encodePredefinedImProtocol(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("pre:");
            stringBuilder.append(n);
            return stringBuilder.toString();
        }

        @Deprecated
        public static final CharSequence getDisplayLabel(Context object, int n, int n2, CharSequence charSequence) {
            String string2 = "";
            if (n != 1) {
                if (n != 2) {
                    object = object.getString(17039375);
                } else if (n2 != 0) {
                    object = object.getResources().getTextArray(17235972);
                    object = object[n2 - 1];
                } else {
                    object = string2;
                    if (!TextUtils.isEmpty(charSequence)) {
                        object = charSequence;
                    }
                }
            } else if (n2 != 0) {
                object = object.getResources().getTextArray(17235968);
                object = object[n2 - 1];
            } else {
                object = string2;
                if (!TextUtils.isEmpty(charSequence)) {
                    object = charSequence;
                }
            }
            return object;
        }

        @Deprecated
        public static String lookupProviderNameFromId(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "JABBER";
                }
                case 6: {
                    return "ICQ";
                }
                case 5: {
                    return "GTalk";
                }
                case 4: {
                    return "QQ";
                }
                case 3: {
                    return "SKYPE";
                }
                case 2: {
                    return "Yahoo";
                }
                case 1: {
                    return "MSN";
                }
                case 0: 
            }
            return "AIM";
        }

        @Deprecated
        public void addPostalLocation(Context object, long l, double d, double d2) {
            ContentResolver contentResolver = ((Context)object).getContentResolver();
            object = new ContentValues(2);
            ((ContentValues)object).put(POSTAL_LOCATION_LATITUDE, d);
            ((ContentValues)object).put(POSTAL_LOCATION_LONGITUDE, d2);
            long l2 = ContentUris.parseId(contentResolver.insert(CONTENT_URI, (ContentValues)object));
            ((ContentValues)object).clear();
            ((ContentValues)object).put(POSTAL_LOCATION_LONGITUDE, l2);
            contentResolver.update(ContentUris.withAppendedId(CONTENT_URI, l), (ContentValues)object, null, null);
        }

        static interface ProviderNames {
            public static final String AIM = "AIM";
            public static final String GTALK = "GTalk";
            public static final String ICQ = "ICQ";
            public static final String JABBER = "JABBER";
            public static final String MSN = "MSN";
            public static final String QQ = "QQ";
            public static final String SKYPE = "SKYPE";
            public static final String XMPP = "XMPP";
            public static final String YAHOO = "Yahoo";
        }

    }

    @Deprecated
    public static interface ContactMethodsColumns {
        @Deprecated
        public static final String AUX_DATA = "aux_data";
        @Deprecated
        public static final String DATA = "data";
        @Deprecated
        public static final String ISPRIMARY = "isprimary";
        @Deprecated
        public static final String KIND = "kind";
        @Deprecated
        public static final String LABEL = "label";
        @Deprecated
        public static final int MOBILE_EMAIL_TYPE_INDEX = 2;
        @Deprecated
        public static final String MOBILE_EMAIL_TYPE_NAME = "_AUTO_CELL";
        @Deprecated
        public static final String TYPE = "type";
        @Deprecated
        public static final int TYPE_CUSTOM = 0;
        @Deprecated
        public static final int TYPE_HOME = 1;
        @Deprecated
        public static final int TYPE_OTHER = 3;
        @Deprecated
        public static final int TYPE_WORK = 2;
    }

    @Deprecated
    public static final class Extensions
    implements BaseColumns,
    ExtensionsColumns {
        @Deprecated
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_extensions";
        @Deprecated
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_extensions";
        @Deprecated
        public static final Uri CONTENT_URI = Uri.parse("content://contacts/extensions");
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "person, name ASC";
        @Deprecated
        public static final String PERSON_ID = "person";

        private Extensions() {
        }
    }

    @Deprecated
    public static interface ExtensionsColumns {
        @Deprecated
        public static final String NAME = "name";
        @Deprecated
        public static final String VALUE = "value";
    }

    @Deprecated
    public static final class GroupMembership
    implements BaseColumns,
    GroupsColumns {
        @Deprecated
        public static final String CONTENT_DIRECTORY = "groupmembership";
        @Deprecated
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contactsgroupmembership";
        @Deprecated
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contactsgroupmembership";
        @Deprecated
        public static final Uri CONTENT_URI = Uri.parse("content://contacts/groupmembership");
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "group_id ASC";
        @Deprecated
        public static final String GROUP_ID = "group_id";
        @Deprecated
        public static final String GROUP_SYNC_ACCOUNT = "group_sync_account";
        @Deprecated
        public static final String GROUP_SYNC_ACCOUNT_TYPE = "group_sync_account_type";
        @Deprecated
        public static final String GROUP_SYNC_ID = "group_sync_id";
        @Deprecated
        public static final String PERSON_ID = "person";
        @Deprecated
        public static final Uri RAW_CONTENT_URI = Uri.parse("content://contacts/groupmembershipraw");

        private GroupMembership() {
        }
    }

    @Deprecated
    public static final class Groups
    implements BaseColumns,
    GroupsColumns {
        @Deprecated
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contactsgroup";
        @Deprecated
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contactsgroup";
        @Deprecated
        public static final Uri CONTENT_URI = Uri.parse("content://contacts/groups");
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "name ASC";
        @Deprecated
        public static final Uri DELETED_CONTENT_URI = Uri.parse("content://contacts/deleted_groups");
        @Deprecated
        public static final String GROUP_ANDROID_STARRED = "Starred in Android";
        @Deprecated
        public static final String GROUP_MY_CONTACTS = "Contacts";

        private Groups() {
        }
    }

    @Deprecated
    public static interface GroupsColumns {
        @Deprecated
        public static final String NAME = "name";
        @Deprecated
        public static final String NOTES = "notes";
        @Deprecated
        public static final String SHOULD_SYNC = "should_sync";
        @Deprecated
        public static final String SYSTEM_ID = "system_id";
    }

    @Deprecated
    public static final class Intents {
        @Deprecated
        public static final String ATTACH_IMAGE = "com.android.contacts.action.ATTACH_IMAGE";
        @Deprecated
        public static final String EXTRA_CREATE_DESCRIPTION = "com.android.contacts.action.CREATE_DESCRIPTION";
        @Deprecated
        public static final String EXTRA_FORCE_CREATE = "com.android.contacts.action.FORCE_CREATE";
        @Deprecated
        public static final String EXTRA_TARGET_RECT = "target_rect";
        @Deprecated
        public static final String SEARCH_SUGGESTION_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CLICKED";
        @Deprecated
        public static final String SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED";
        @Deprecated
        public static final String SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED";
        @Deprecated
        public static final String SHOW_OR_CREATE_CONTACT = "com.android.contacts.action.SHOW_OR_CREATE_CONTACT";

        @Deprecated
        public static final class Insert {
            @Deprecated
            public static final String ACTION = "android.intent.action.INSERT";
            @Deprecated
            public static final String COMPANY = "company";
            @Deprecated
            public static final String EMAIL = "email";
            @Deprecated
            public static final String EMAIL_ISPRIMARY = "email_isprimary";
            @Deprecated
            public static final String EMAIL_TYPE = "email_type";
            @Deprecated
            public static final String FULL_MODE = "full_mode";
            @Deprecated
            public static final String IM_HANDLE = "im_handle";
            @Deprecated
            public static final String IM_ISPRIMARY = "im_isprimary";
            @Deprecated
            public static final String IM_PROTOCOL = "im_protocol";
            @Deprecated
            public static final String JOB_TITLE = "job_title";
            @Deprecated
            public static final String NAME = "name";
            @Deprecated
            public static final String NOTES = "notes";
            @Deprecated
            public static final String PHONE = "phone";
            @Deprecated
            public static final String PHONETIC_NAME = "phonetic_name";
            @Deprecated
            public static final String PHONE_ISPRIMARY = "phone_isprimary";
            @Deprecated
            public static final String PHONE_TYPE = "phone_type";
            @Deprecated
            public static final String POSTAL = "postal";
            @Deprecated
            public static final String POSTAL_ISPRIMARY = "postal_isprimary";
            @Deprecated
            public static final String POSTAL_TYPE = "postal_type";
            @Deprecated
            public static final String SECONDARY_EMAIL = "secondary_email";
            @Deprecated
            public static final String SECONDARY_EMAIL_TYPE = "secondary_email_type";
            @Deprecated
            public static final String SECONDARY_PHONE = "secondary_phone";
            @Deprecated
            public static final String SECONDARY_PHONE_TYPE = "secondary_phone_type";
            @Deprecated
            public static final String TERTIARY_EMAIL = "tertiary_email";
            @Deprecated
            public static final String TERTIARY_EMAIL_TYPE = "tertiary_email_type";
            @Deprecated
            public static final String TERTIARY_PHONE = "tertiary_phone";
            @Deprecated
            public static final String TERTIARY_PHONE_TYPE = "tertiary_phone_type";
        }

        @Deprecated
        public static final class UI {
            @Deprecated
            public static final String FILTER_CONTACTS_ACTION = "com.android.contacts.action.FILTER_CONTACTS";
            @Deprecated
            public static final String FILTER_TEXT_EXTRA_KEY = "com.android.contacts.extra.FILTER_TEXT";
            @Deprecated
            public static final String GROUP_NAME_EXTRA_KEY = "com.android.contacts.extra.GROUP";
            @Deprecated
            public static final String LIST_ALL_CONTACTS_ACTION = "com.android.contacts.action.LIST_ALL_CONTACTS";
            @Deprecated
            public static final String LIST_CONTACTS_WITH_PHONES_ACTION = "com.android.contacts.action.LIST_CONTACTS_WITH_PHONES";
            @Deprecated
            public static final String LIST_DEFAULT = "com.android.contacts.action.LIST_DEFAULT";
            @Deprecated
            public static final String LIST_FREQUENT_ACTION = "com.android.contacts.action.LIST_FREQUENT";
            @Deprecated
            public static final String LIST_GROUP_ACTION = "com.android.contacts.action.LIST_GROUP";
            @Deprecated
            public static final String LIST_STARRED_ACTION = "com.android.contacts.action.LIST_STARRED";
            @Deprecated
            public static final String LIST_STREQUENT_ACTION = "com.android.contacts.action.LIST_STREQUENT";
            @Deprecated
            public static final String TITLE_EXTRA_KEY = "com.android.contacts.extra.TITLE_EXTRA";
        }

    }

    @Deprecated
    public static interface OrganizationColumns {
        @Deprecated
        public static final String COMPANY = "company";
        @Deprecated
        public static final String ISPRIMARY = "isprimary";
        @Deprecated
        public static final String LABEL = "label";
        @Deprecated
        public static final String PERSON_ID = "person";
        @Deprecated
        public static final String TITLE = "title";
        @Deprecated
        public static final String TYPE = "type";
        @Deprecated
        public static final int TYPE_CUSTOM = 0;
        @Deprecated
        public static final int TYPE_OTHER = 2;
        @Deprecated
        public static final int TYPE_WORK = 1;
    }

    @Deprecated
    public static final class Organizations
    implements BaseColumns,
    OrganizationColumns {
        @Deprecated
        public static final String CONTENT_DIRECTORY = "organizations";
        @Deprecated
        public static final Uri CONTENT_URI = Uri.parse("content://contacts/organizations");
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "company, title, isprimary ASC";

        private Organizations() {
        }

        @Deprecated
        public static final CharSequence getDisplayLabel(Context object, int n, CharSequence charSequence) {
            String string2 = "";
            if (n != 0) {
                object = object.getResources().getTextArray(17235970);
                object = object[n - 1];
            } else {
                object = string2;
                if (!TextUtils.isEmpty(charSequence)) {
                    object = charSequence;
                }
            }
            return object;
        }
    }

    @Deprecated
    public static final class People
    implements BaseColumns,
    PeopleColumns,
    PhonesColumns,
    PresenceColumns {
        @Deprecated
        public static final Uri CONTENT_FILTER_URI;
        @Deprecated
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/person";
        @Deprecated
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/person";
        @Deprecated
        public static final Uri CONTENT_URI;
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "name ASC";
        @Deprecated
        public static final Uri DELETED_CONTENT_URI;
        private static final String[] GROUPS_PROJECTION;
        @Deprecated
        public static final String PRIMARY_EMAIL_ID = "primary_email";
        @Deprecated
        public static final String PRIMARY_ORGANIZATION_ID = "primary_organization";
        @Deprecated
        public static final String PRIMARY_PHONE_ID = "primary_phone";
        @Deprecated
        public static final Uri WITH_EMAIL_OR_IM_FILTER_URI;

        static {
            CONTENT_URI = Uri.parse("content://contacts/people");
            CONTENT_FILTER_URI = Uri.parse("content://contacts/people/filter");
            DELETED_CONTENT_URI = Uri.parse("content://contacts/deleted_people");
            WITH_EMAIL_OR_IM_FILTER_URI = Uri.parse("content://contacts/people/with_email_or_im_filter");
            GROUPS_PROJECTION = new String[]{"_id"};
        }

        @Deprecated
        private People() {
        }

        @Deprecated
        public static Uri addToGroup(ContentResolver contentResolver, long l, long l2) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("person", l);
            contentValues.put("group_id", l2);
            return contentResolver.insert(GroupMembership.CONTENT_URI, contentValues);
        }

        @Deprecated
        public static Uri addToGroup(ContentResolver contentResolver, long l, String object) {
            long l2 = 0L;
            object = contentResolver.query(Groups.CONTENT_URI, GROUPS_PROJECTION, "name=?", new String[]{object}, null);
            long l3 = l2;
            if (object != null) {
                l3 = l2;
                try {
                    if (object.moveToFirst()) {
                        l3 = object.getLong(0);
                    }
                }
                finally {
                    object.close();
                }
            }
            if (l3 != 0L) {
                return People.addToGroup(contentResolver, l, l3);
            }
            throw new IllegalStateException("Failed to find the My Contacts group");
        }

        @Deprecated
        public static Uri addToMyContactsGroup(ContentResolver contentResolver, long l) {
            long l2 = People.tryGetMyContactsGroupId(contentResolver);
            if (l2 != 0L) {
                return People.addToGroup(contentResolver, l, l2);
            }
            throw new IllegalStateException("Failed to find the My Contacts group");
        }

        @Deprecated
        public static Uri createPersonInMyContactsGroup(ContentResolver contentResolver, ContentValues parcelable) {
            if ((parcelable = contentResolver.insert(CONTENT_URI, (ContentValues)parcelable)) == null) {
                Log.e(Contacts.TAG, "Failed to create the contact");
                return null;
            }
            if (People.addToMyContactsGroup(contentResolver, ContentUris.parseId((Uri)parcelable)) == null) {
                contentResolver.delete((Uri)parcelable, null, null);
                return null;
            }
            return parcelable;
        }

        @Deprecated
        public static Bitmap loadContactPhoto(Context context, Uri parcelable, int n, BitmapFactory.Options options) {
            if (parcelable == null) {
                return People.loadPlaceholderPhoto(n, context, options);
            }
            Object object = People.openContactPhotoInputStream(context.getContentResolver(), parcelable);
            parcelable = null;
            if (object != null) {
                parcelable = BitmapFactory.decodeStream((InputStream)object, null, options);
            }
            object = parcelable;
            if (parcelable == null) {
                object = People.loadPlaceholderPhoto(n, context, options);
            }
            return object;
        }

        private static Bitmap loadPlaceholderPhoto(int n, Context context, BitmapFactory.Options options) {
            if (n == 0) {
                return null;
            }
            return BitmapFactory.decodeResource(context.getResources(), n, options);
        }

        @Deprecated
        public static void markAsContacted(ContentResolver contentResolver, long l) {
        }

        @Deprecated
        public static InputStream openContactPhotoInputStream(ContentResolver object, Uri object2) {
            block6 : {
                if ((object = ((ContentResolver)object).query(Uri.withAppendedPath((Uri)object2, "photo"), new String[]{"data"}, null, null, null)) != null) {
                    block7 : {
                        if (!object.moveToNext()) break block6;
                        object2 = object.getBlob(0);
                        if (object2 != null) break block7;
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

        @Deprecated
        public static Cursor queryGroups(ContentResolver contentResolver, long l) {
            return contentResolver.query(GroupMembership.CONTENT_URI, null, "person=?", new String[]{String.valueOf(l)}, DEFAULT_SORT_ORDER);
        }

        @Deprecated
        public static void setPhotoData(ContentResolver contentResolver, Uri uri, byte[] arrby) {
            uri = Uri.withAppendedPath(uri, "photo");
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", arrby);
            contentResolver.update(uri, contentValues, null, null);
        }

        @Deprecated
        public static long tryGetMyContactsGroupId(ContentResolver object) {
            if ((object = ((ContentResolver)object).query(Groups.CONTENT_URI, GROUPS_PROJECTION, "system_id='Contacts'", null, null)) != null) {
                try {
                    if (object.moveToFirst()) {
                        long l = object.getLong(0);
                        return l;
                    }
                }
                finally {
                    object.close();
                }
            }
            return 0L;
        }

        @Deprecated
        public static final class ContactMethods
        implements BaseColumns,
        ContactMethodsColumns,
        PeopleColumns {
            @Deprecated
            public static final String CONTENT_DIRECTORY = "contact_methods";
            @Deprecated
            public static final String DEFAULT_SORT_ORDER = "data ASC";

            private ContactMethods() {
            }
        }

        @Deprecated
        public static class Extensions
        implements BaseColumns,
        ExtensionsColumns {
            @Deprecated
            public static final String CONTENT_DIRECTORY = "extensions";
            @Deprecated
            public static final String DEFAULT_SORT_ORDER = "name ASC";
            @Deprecated
            public static final String PERSON_ID = "person";

            @Deprecated
            private Extensions() {
            }
        }

        @Deprecated
        public static final class Phones
        implements BaseColumns,
        PhonesColumns,
        PeopleColumns {
            @Deprecated
            public static final String CONTENT_DIRECTORY = "phones";
            @Deprecated
            public static final String DEFAULT_SORT_ORDER = "number ASC";

            private Phones() {
            }
        }

    }

    @Deprecated
    public static interface PeopleColumns {
        @Deprecated
        public static final String CUSTOM_RINGTONE = "custom_ringtone";
        @Deprecated
        public static final String DISPLAY_NAME = "display_name";
        @Deprecated
        public static final String LAST_TIME_CONTACTED = "last_time_contacted";
        @Deprecated
        public static final String NAME = "name";
        @Deprecated
        public static final String NOTES = "notes";
        @Deprecated
        public static final String PHONETIC_NAME = "phonetic_name";
        @Deprecated
        public static final String PHOTO_VERSION = "photo_version";
        @Deprecated
        public static final String SEND_TO_VOICEMAIL = "send_to_voicemail";
        @Deprecated
        public static final String SORT_STRING = "sort_string";
        @Deprecated
        public static final String STARRED = "starred";
        @Deprecated
        public static final String TIMES_CONTACTED = "times_contacted";
    }

    @Deprecated
    public static final class Phones
    implements BaseColumns,
    PhonesColumns,
    PeopleColumns {
        @Deprecated
        public static final Uri CONTENT_FILTER_URL;
        @Deprecated
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone";
        @Deprecated
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone";
        @Deprecated
        public static final Uri CONTENT_URI;
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "name ASC";
        @Deprecated
        public static final String PERSON_ID = "person";

        static {
            CONTENT_URI = Uri.parse("content://contacts/phones");
            CONTENT_FILTER_URL = Uri.parse("content://contacts/phones/filter");
        }

        private Phones() {
        }

        @Deprecated
        public static final CharSequence getDisplayLabel(Context context, int n, CharSequence charSequence) {
            return Phones.getDisplayLabel(context, n, charSequence, null);
        }

        @Deprecated
        public static final CharSequence getDisplayLabel(Context object, int n, CharSequence charSequence, CharSequence[] arrcharSequence) {
            String string2 = "";
            if (n != 0) {
                object = arrcharSequence != null ? arrcharSequence : object.getResources().getTextArray(17235971);
                object = object[n - 1];
            } else {
                object = string2;
                if (!TextUtils.isEmpty(charSequence)) {
                    object = charSequence;
                }
            }
            return object;
        }
    }

    @Deprecated
    public static interface PhonesColumns {
        @Deprecated
        public static final String ISPRIMARY = "isprimary";
        @Deprecated
        public static final String LABEL = "label";
        @Deprecated
        public static final String NUMBER = "number";
        @Deprecated
        public static final String NUMBER_KEY = "number_key";
        @Deprecated
        public static final String TYPE = "type";
        @Deprecated
        public static final int TYPE_CUSTOM = 0;
        @Deprecated
        public static final int TYPE_FAX_HOME = 5;
        @Deprecated
        public static final int TYPE_FAX_WORK = 4;
        @Deprecated
        public static final int TYPE_HOME = 1;
        @Deprecated
        public static final int TYPE_MOBILE = 2;
        @Deprecated
        public static final int TYPE_OTHER = 7;
        @Deprecated
        public static final int TYPE_PAGER = 6;
        @Deprecated
        public static final int TYPE_WORK = 3;
    }

    @Deprecated
    public static final class Photos
    implements BaseColumns,
    PhotosColumns {
        @Deprecated
        public static final String CONTENT_DIRECTORY = "photo";
        @Deprecated
        public static final Uri CONTENT_URI = Uri.parse("content://contacts/photos");
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "person ASC";

        private Photos() {
        }
    }

    @Deprecated
    public static interface PhotosColumns {
        @Deprecated
        public static final String DATA = "data";
        @Deprecated
        public static final String DOWNLOAD_REQUIRED = "download_required";
        @Deprecated
        public static final String EXISTS_ON_SERVER = "exists_on_server";
        @Deprecated
        public static final String LOCAL_VERSION = "local_version";
        @Deprecated
        public static final String PERSON_ID = "person";
        @Deprecated
        public static final String SYNC_ERROR = "sync_error";
    }

    @Deprecated
    public static final class Presence
    implements BaseColumns,
    PresenceColumns,
    PeopleColumns {
        @Deprecated
        public static final Uri CONTENT_URI = Uri.parse("content://contacts/presence");
        @Deprecated
        public static final String PERSON_ID = "person";

        @Deprecated
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

        @Deprecated
        public static final void setPresenceIcon(ImageView imageView, int n) {
            imageView.setImageResource(Presence.getPresenceIconResourceId(n));
        }
    }

    @Deprecated
    public static interface PresenceColumns {
        public static final int AVAILABLE = 5;
        public static final int AWAY = 2;
        public static final int DO_NOT_DISTURB = 4;
        public static final int IDLE = 3;
        @Deprecated
        public static final String IM_ACCOUNT = "im_account";
        @Deprecated
        public static final String IM_HANDLE = "im_handle";
        @Deprecated
        public static final String IM_PROTOCOL = "im_protocol";
        public static final int INVISIBLE = 1;
        public static final int OFFLINE = 0;
        public static final String PRESENCE_CUSTOM_STATUS = "status";
        public static final String PRESENCE_STATUS = "mode";
        public static final String PRIORITY = "priority";
    }

    @Deprecated
    public static final class Settings
    implements BaseColumns,
    SettingsColumns {
        @Deprecated
        public static final String CONTENT_DIRECTORY = "settings";
        @Deprecated
        public static final Uri CONTENT_URI = Uri.parse("content://contacts/settings");
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "key ASC";
        @Deprecated
        public static final String SYNC_EVERYTHING = "syncEverything";

        private Settings() {
        }

        @Deprecated
        public static String getSetting(ContentResolver object, String string2, String string3) {
            block4 : {
                object = ((ContentResolver)object).query(CONTENT_URI, new String[]{"value"}, "key=?", new String[]{string3}, null);
                boolean bl = object.moveToNext();
                if (bl) break block4;
                object.close();
                return null;
            }
            try {
                string2 = object.getString(0);
                return string2;
            }
            finally {
                object.close();
            }
        }

        @Deprecated
        public static void setSetting(ContentResolver contentResolver, String object, String string2, String string3) {
            object = new ContentValues();
            ((ContentValues)object).put("key", string2);
            ((ContentValues)object).put("value", string3);
            contentResolver.update(CONTENT_URI, (ContentValues)object, null, null);
        }
    }

    @Deprecated
    public static interface SettingsColumns {
        @Deprecated
        public static final String KEY = "key";
        @Deprecated
        public static final String VALUE = "value";
        @Deprecated
        public static final String _SYNC_ACCOUNT = "_sync_account";
        @Deprecated
        public static final String _SYNC_ACCOUNT_TYPE = "_sync_account_type";
    }

}

