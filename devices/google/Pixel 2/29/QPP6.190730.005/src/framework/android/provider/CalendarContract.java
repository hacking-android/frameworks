/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.UnsupportedAppUsage;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorEntityIterator;
import android.content.Entity;
import android.content.EntityIterator;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.SyncStateContract;
import com.android.internal.util.Preconditions;

public final class CalendarContract {
    public static final String ACCOUNT_TYPE_LOCAL = "LOCAL";
    public static final String ACTION_EVENT_REMINDER = "android.intent.action.EVENT_REMINDER";
    public static final String ACTION_HANDLE_CUSTOM_EVENT = "android.provider.calendar.action.HANDLE_CUSTOM_EVENT";
    public static final String ACTION_VIEW_MANAGED_PROFILE_CALENDAR_EVENT = "android.provider.calendar.action.VIEW_MANAGED_PROFILE_CALENDAR_EVENT";
    public static final String AUTHORITY = "com.android.calendar";
    public static final String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
    public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar");
    public static final Uri ENTERPRISE_CONTENT_URI = Uri.parse("content://com.android.calendar/enterprise");
    public static final String EXTRA_CUSTOM_APP_URI = "customAppUri";
    public static final String EXTRA_EVENT_ALL_DAY = "allDay";
    public static final String EXTRA_EVENT_BEGIN_TIME = "beginTime";
    public static final String EXTRA_EVENT_END_TIME = "endTime";
    public static final String EXTRA_EVENT_ID = "id";
    private static final String TAG = "Calendar";

    private CalendarContract() {
    }

    public static boolean startViewCalendarEventInManagedProfile(Context context, long l, long l2, long l3, boolean bl, int n) {
        Preconditions.checkNotNull(context, "Context is null");
        return ((DevicePolicyManager)context.getSystemService("device_policy")).startViewCalendarEventInManagedProfile(l, l2, l3, bl, n);
    }

    public static final class Attendees
    implements BaseColumns,
    AttendeesColumns,
    EventsColumns {
        private static final String ATTENDEES_WHERE = "event_id=?";
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/attendees");

        private Attendees() {
        }

        public static final Cursor query(ContentResolver contentResolver, long l, String[] arrstring) {
            String string2 = Long.toString(l);
            return contentResolver.query(CONTENT_URI, arrstring, ATTENDEES_WHERE, new String[]{string2}, null);
        }
    }

    protected static interface AttendeesColumns {
        public static final String ATTENDEE_EMAIL = "attendeeEmail";
        public static final String ATTENDEE_IDENTITY = "attendeeIdentity";
        public static final String ATTENDEE_ID_NAMESPACE = "attendeeIdNamespace";
        public static final String ATTENDEE_NAME = "attendeeName";
        public static final String ATTENDEE_RELATIONSHIP = "attendeeRelationship";
        public static final String ATTENDEE_STATUS = "attendeeStatus";
        public static final int ATTENDEE_STATUS_ACCEPTED = 1;
        public static final int ATTENDEE_STATUS_DECLINED = 2;
        public static final int ATTENDEE_STATUS_INVITED = 3;
        public static final int ATTENDEE_STATUS_NONE = 0;
        public static final int ATTENDEE_STATUS_TENTATIVE = 4;
        public static final String ATTENDEE_TYPE = "attendeeType";
        public static final String EVENT_ID = "event_id";
        public static final int RELATIONSHIP_ATTENDEE = 1;
        public static final int RELATIONSHIP_NONE = 0;
        public static final int RELATIONSHIP_ORGANIZER = 2;
        public static final int RELATIONSHIP_PERFORMER = 3;
        public static final int RELATIONSHIP_SPEAKER = 4;
        public static final int TYPE_NONE = 0;
        public static final int TYPE_OPTIONAL = 2;
        public static final int TYPE_REQUIRED = 1;
        public static final int TYPE_RESOURCE = 3;
    }

    public static final class CalendarAlerts
    implements BaseColumns,
    CalendarAlertsColumns,
    EventsColumns,
    CalendarColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/calendar_alerts");
        public static final Uri CONTENT_URI_BY_INSTANCE = Uri.parse("content://com.android.calendar/calendar_alerts/by_instance");
        private static final boolean DEBUG = false;
        private static final String SORT_ORDER_ALARMTIME_ASC = "alarmTime ASC";
        public static final String TABLE_NAME = "CalendarAlerts";
        private static final String WHERE_ALARM_EXISTS = "event_id=? AND begin=? AND alarmTime=?";
        private static final String WHERE_FINDNEXTALARMTIME = "alarmTime>=?";
        private static final String WHERE_RESCHEDULE_MISSED_ALARMS = "state=0 AND alarmTime<? AND alarmTime>? AND end>=?";

        private CalendarAlerts() {
        }

        public static final boolean alarmExists(ContentResolver contentResolver, long l, long l2, long l3) {
            boolean bl;
            Uri uri = CONTENT_URI;
            String string2 = Long.toString(l);
            Object object = Long.toString(l2);
            String string3 = Long.toString(l3);
            object = contentResolver.query(uri, new String[]{"alarmTime"}, WHERE_ALARM_EXISTS, new String[]{string2, object, string3}, null);
            boolean bl2 = bl = false;
            if (object != null) {
                try {
                    int n = object.getCount();
                    bl2 = bl;
                    if (n > 0) {
                        bl2 = true;
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
            return bl2;
        }

        @UnsupportedAppUsage
        public static final long findNextAlarmTime(ContentResolver contentResolver, long l) {
            long l2;
            Object object = new StringBuilder();
            ((StringBuilder)object).append("alarmTime>=");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).toString();
            Uri uri = CONTENT_URI;
            object = Long.toString(l);
            object = contentResolver.query(uri, new String[]{"alarmTime"}, WHERE_FINDNEXTALARMTIME, new String[]{object}, SORT_ORDER_ALARMTIME_ASC);
            l = l2 = -1L;
            if (object != null) {
                l = l2;
                try {
                    if (object.moveToFirst()) {
                        l = object.getLong(0);
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
            return l;
        }

        public static final Uri insert(ContentResolver contentResolver, long l, long l2, long l3, long l4, int n) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("event_id", l);
            contentValues.put("begin", l2);
            contentValues.put("end", l3);
            contentValues.put("alarmTime", l4);
            contentValues.put("creationTime", System.currentTimeMillis());
            Integer n2 = 0;
            contentValues.put("receivedTime", n2);
            contentValues.put("notifyTime", n2);
            contentValues.put("state", n2);
            contentValues.put("minutes", n);
            return contentResolver.insert(CONTENT_URI, contentValues);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        public static final void rescheduleMissedAlarms(ContentResolver object, Context context, AlarmManager alarmManager) {
            long l = System.currentTimeMillis();
            Uri uri = CONTENT_URI;
            String string2 = Long.toString(l);
            String string3 = Long.toString(l - 86400000L);
            String string4 = Long.toString(l);
            if ((object = ((ContentResolver)object).query(uri, new String[]{"alarmTime"}, WHERE_RESCHEDULE_MISSED_ALARMS, new String[]{string2, string3, string4}, SORT_ORDER_ALARMTIME_ASC)) == null) {
                return;
            }
            l = -1L;
            try {
                do {
                    if (!object.moveToNext()) {
                        object.close();
                        return;
                    }
                    long l3 = object.getLong(0);
                    long l2 = l;
                    if (l != l3) {
                        CalendarAlerts.scheduleAlarm(context, alarmManager, l3);
                        l2 = l3;
                    }
                    l = l2;
                } while (true);
            }
            catch (Throwable throwable) {
                object.close();
                throw throwable;
            }
        }

        @UnsupportedAppUsage
        public static void scheduleAlarm(Context context, AlarmManager object, long l) {
            AlarmManager alarmManager = object;
            if (object == null) {
                alarmManager = (AlarmManager)context.getSystemService("alarm");
            }
            object = new Intent(CalendarContract.ACTION_EVENT_REMINDER);
            ((Intent)object).setData(ContentUris.withAppendedId(CONTENT_URI, l));
            ((Intent)object).putExtra("alarmTime", l);
            ((Intent)object).setFlags(16777216);
            alarmManager.setExactAndAllowWhileIdle(0, l, PendingIntent.getBroadcast(context, 0, (Intent)object, 0));
        }
    }

    protected static interface CalendarAlertsColumns {
        public static final String ALARM_TIME = "alarmTime";
        public static final String BEGIN = "begin";
        public static final String CREATION_TIME = "creationTime";
        public static final String DEFAULT_SORT_ORDER = "begin ASC,title ASC";
        public static final String END = "end";
        public static final String EVENT_ID = "event_id";
        public static final String MINUTES = "minutes";
        public static final String NOTIFY_TIME = "notifyTime";
        public static final String RECEIVED_TIME = "receivedTime";
        public static final String STATE = "state";
        public static final int STATE_DISMISSED = 2;
        public static final int STATE_FIRED = 1;
        public static final int STATE_SCHEDULED = 0;
    }

    public static final class CalendarCache
    implements CalendarCacheColumns {
        public static final String KEY_TIMEZONE_INSTANCES = "timezoneInstances";
        public static final String KEY_TIMEZONE_INSTANCES_PREVIOUS = "timezoneInstancesPrevious";
        public static final String KEY_TIMEZONE_TYPE = "timezoneType";
        public static final String TIMEZONE_TYPE_AUTO = "auto";
        public static final String TIMEZONE_TYPE_HOME = "home";
        public static final Uri URI = Uri.parse("content://com.android.calendar/properties");

        private CalendarCache() {
        }
    }

    protected static interface CalendarCacheColumns {
        public static final String KEY = "key";
        public static final String VALUE = "value";
    }

    protected static interface CalendarColumns {
        public static final String ALLOWED_ATTENDEE_TYPES = "allowedAttendeeTypes";
        public static final String ALLOWED_AVAILABILITY = "allowedAvailability";
        public static final String ALLOWED_REMINDERS = "allowedReminders";
        public static final String CALENDAR_ACCESS_LEVEL = "calendar_access_level";
        public static final String CALENDAR_COLOR = "calendar_color";
        public static final String CALENDAR_COLOR_KEY = "calendar_color_index";
        public static final String CALENDAR_DISPLAY_NAME = "calendar_displayName";
        public static final String CALENDAR_TIME_ZONE = "calendar_timezone";
        public static final int CAL_ACCESS_CONTRIBUTOR = 500;
        public static final int CAL_ACCESS_EDITOR = 600;
        public static final int CAL_ACCESS_FREEBUSY = 100;
        public static final int CAL_ACCESS_NONE = 0;
        public static final int CAL_ACCESS_OVERRIDE = 400;
        public static final int CAL_ACCESS_OWNER = 700;
        public static final int CAL_ACCESS_READ = 200;
        public static final int CAL_ACCESS_RESPOND = 300;
        public static final int CAL_ACCESS_ROOT = 800;
        public static final String CAN_MODIFY_TIME_ZONE = "canModifyTimeZone";
        public static final String CAN_ORGANIZER_RESPOND = "canOrganizerRespond";
        public static final String IS_PRIMARY = "isPrimary";
        public static final String MAX_REMINDERS = "maxReminders";
        public static final String OWNER_ACCOUNT = "ownerAccount";
        public static final String SYNC_EVENTS = "sync_events";
        public static final String VISIBLE = "visible";
    }

    public static final class CalendarEntity
    implements BaseColumns,
    SyncColumns,
    CalendarColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/calendar_entities");

        private CalendarEntity() {
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
                long l = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                Object object = new ContentValues();
                ((ContentValues)object).put("_id", l);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "account_name");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "account_type");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "_sync_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, (ContentValues)object, "dirty");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "mutators");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync1");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync2");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync3");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync4");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync5");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync6");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync7");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync8");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync9");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync10");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "name");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "calendar_displayName");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "calendar_color");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "calendar_color_index");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "calendar_access_level");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "visible");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "sync_events");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "calendar_location");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "calendar_timezone");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "ownerAccount");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "canOrganizerRespond");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "canModifyTimeZone");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "maxReminders");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "canPartiallyUpdate");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "allowedReminders");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "deleted");
                object = new Entity((ContentValues)object);
                cursor.moveToNext();
                return object;
            }
        }

    }

    public static final class CalendarMetaData
    implements CalendarMetaDataColumns,
    BaseColumns {
        private CalendarMetaData() {
        }
    }

    protected static interface CalendarMetaDataColumns {
        public static final String LOCAL_TIMEZONE = "localTimezone";
        public static final String MAX_EVENTDAYS = "maxEventDays";
        public static final String MAX_INSTANCE = "maxInstance";
        public static final String MIN_EVENTDAYS = "minEventDays";
        public static final String MIN_INSTANCE = "minInstance";
    }

    protected static interface CalendarSyncColumns {
        public static final String CAL_SYNC1 = "cal_sync1";
        public static final String CAL_SYNC10 = "cal_sync10";
        public static final String CAL_SYNC2 = "cal_sync2";
        public static final String CAL_SYNC3 = "cal_sync3";
        public static final String CAL_SYNC4 = "cal_sync4";
        public static final String CAL_SYNC5 = "cal_sync5";
        public static final String CAL_SYNC6 = "cal_sync6";
        public static final String CAL_SYNC7 = "cal_sync7";
        public static final String CAL_SYNC8 = "cal_sync8";
        public static final String CAL_SYNC9 = "cal_sync9";
    }

    public static final class Calendars
    implements BaseColumns,
    SyncColumns,
    CalendarColumns {
        public static final String CALENDAR_LOCATION = "calendar_location";
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/calendars");
        public static final String DEFAULT_SORT_ORDER = "calendar_displayName";
        public static final Uri ENTERPRISE_CONTENT_URI = Uri.parse("content://com.android.calendar/enterprise/calendars");
        public static final String NAME = "name";
        public static final String[] SYNC_WRITABLE_COLUMNS = new String[]{"account_name", "account_type", "_sync_id", "dirty", "mutators", "ownerAccount", "maxReminders", "allowedReminders", "canModifyTimeZone", "canOrganizerRespond", "canPartiallyUpdate", "calendar_location", "calendar_timezone", "calendar_access_level", "deleted", "cal_sync1", "cal_sync2", "cal_sync3", "cal_sync4", "cal_sync5", "cal_sync6", "cal_sync7", "cal_sync8", "cal_sync9", "cal_sync10"};

        private Calendars() {
        }
    }

    public static final class Colors
    implements ColorsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/colors");
        public static final String TABLE_NAME = "Colors";

        private Colors() {
        }
    }

    protected static interface ColorsColumns
    extends SyncStateContract.Columns {
        public static final String COLOR = "color";
        public static final String COLOR_KEY = "color_index";
        public static final String COLOR_TYPE = "color_type";
        public static final int TYPE_CALENDAR = 0;
        public static final int TYPE_EVENT = 1;
    }

    public static final class EventDays
    implements EventDaysColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/instances/groupbyday");
        private static final String SELECTION = "selected=1";

        private EventDays() {
        }

        public static final Cursor query(ContentResolver contentResolver, int n, int n2, String[] arrstring) {
            if (n2 < 1) {
                return null;
            }
            Uri.Builder builder = CONTENT_URI.buildUpon();
            ContentUris.appendId(builder, n);
            ContentUris.appendId(builder, n + n2 - 1);
            return contentResolver.query(builder.build(), arrstring, SELECTION, null, "startDay");
        }
    }

    protected static interface EventDaysColumns {
        public static final String ENDDAY = "endDay";
        public static final String STARTDAY = "startDay";
    }

    public static final class Events
    implements BaseColumns,
    SyncColumns,
    EventsColumns,
    CalendarColumns {
        public static final Uri CONTENT_EXCEPTION_URI;
        public static final Uri CONTENT_URI;
        private static final String DEFAULT_SORT_ORDER = "";
        public static final Uri ENTERPRISE_CONTENT_URI;
        @UnsupportedAppUsage
        public static String[] PROVIDER_WRITABLE_COLUMNS;
        public static final String[] SYNC_WRITABLE_COLUMNS;

        static {
            CONTENT_URI = Uri.parse("content://com.android.calendar/events");
            ENTERPRISE_CONTENT_URI = Uri.parse("content://com.android.calendar/enterprise/events");
            CONTENT_EXCEPTION_URI = Uri.parse("content://com.android.calendar/exception");
            PROVIDER_WRITABLE_COLUMNS = new String[]{"account_name", "account_type", "cal_sync1", "cal_sync2", "cal_sync3", "cal_sync4", "cal_sync5", "cal_sync6", "cal_sync7", "cal_sync8", "cal_sync9", "cal_sync10", "allowedReminders", "allowedAttendeeTypes", "allowedAvailability", "calendar_access_level", "calendar_color", "calendar_timezone", "canModifyTimeZone", "canOrganizerRespond", "calendar_displayName", "canPartiallyUpdate", "sync_events", "visible"};
            SYNC_WRITABLE_COLUMNS = new String[]{"_sync_id", "dirty", "mutators", "sync_data1", "sync_data2", "sync_data3", "sync_data4", "sync_data5", "sync_data6", "sync_data7", "sync_data8", "sync_data9", "sync_data10"};
        }

        private Events() {
        }
    }

    protected static interface EventsColumns {
        public static final int ACCESS_CONFIDENTIAL = 1;
        public static final int ACCESS_DEFAULT = 0;
        public static final String ACCESS_LEVEL = "accessLevel";
        public static final int ACCESS_PRIVATE = 2;
        public static final int ACCESS_PUBLIC = 3;
        public static final String ALL_DAY = "allDay";
        public static final String AVAILABILITY = "availability";
        public static final int AVAILABILITY_BUSY = 0;
        public static final int AVAILABILITY_FREE = 1;
        public static final int AVAILABILITY_TENTATIVE = 2;
        public static final String CALENDAR_ID = "calendar_id";
        public static final String CAN_INVITE_OTHERS = "canInviteOthers";
        public static final String CUSTOM_APP_PACKAGE = "customAppPackage";
        public static final String CUSTOM_APP_URI = "customAppUri";
        public static final String DESCRIPTION = "description";
        public static final String DISPLAY_COLOR = "displayColor";
        public static final String DTEND = "dtend";
        public static final String DTSTART = "dtstart";
        public static final String DURATION = "duration";
        public static final String EVENT_COLOR = "eventColor";
        public static final String EVENT_COLOR_KEY = "eventColor_index";
        public static final String EVENT_END_TIMEZONE = "eventEndTimezone";
        public static final String EVENT_LOCATION = "eventLocation";
        public static final String EVENT_TIMEZONE = "eventTimezone";
        public static final String EXDATE = "exdate";
        public static final String EXRULE = "exrule";
        public static final String GUESTS_CAN_INVITE_OTHERS = "guestsCanInviteOthers";
        public static final String GUESTS_CAN_MODIFY = "guestsCanModify";
        public static final String GUESTS_CAN_SEE_GUESTS = "guestsCanSeeGuests";
        public static final String HAS_ALARM = "hasAlarm";
        public static final String HAS_ATTENDEE_DATA = "hasAttendeeData";
        public static final String HAS_EXTENDED_PROPERTIES = "hasExtendedProperties";
        public static final String IS_ORGANIZER = "isOrganizer";
        public static final String LAST_DATE = "lastDate";
        public static final String LAST_SYNCED = "lastSynced";
        public static final String ORGANIZER = "organizer";
        public static final String ORIGINAL_ALL_DAY = "originalAllDay";
        public static final String ORIGINAL_ID = "original_id";
        public static final String ORIGINAL_INSTANCE_TIME = "originalInstanceTime";
        public static final String ORIGINAL_SYNC_ID = "original_sync_id";
        public static final String RDATE = "rdate";
        public static final String RRULE = "rrule";
        public static final String SELF_ATTENDEE_STATUS = "selfAttendeeStatus";
        public static final String STATUS = "eventStatus";
        public static final int STATUS_CANCELED = 2;
        public static final int STATUS_CONFIRMED = 1;
        public static final int STATUS_TENTATIVE = 0;
        public static final String SYNC_DATA1 = "sync_data1";
        public static final String SYNC_DATA10 = "sync_data10";
        public static final String SYNC_DATA2 = "sync_data2";
        public static final String SYNC_DATA3 = "sync_data3";
        public static final String SYNC_DATA4 = "sync_data4";
        public static final String SYNC_DATA5 = "sync_data5";
        public static final String SYNC_DATA6 = "sync_data6";
        public static final String SYNC_DATA7 = "sync_data7";
        public static final String SYNC_DATA8 = "sync_data8";
        public static final String SYNC_DATA9 = "sync_data9";
        public static final String TITLE = "title";
        public static final String UID_2445 = "uid2445";
    }

    public static final class EventsEntity
    implements BaseColumns,
    SyncColumns,
    EventsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/event_entities");

        private EventsEntity() {
        }

        public static EntityIterator newEntityIterator(Cursor cursor, ContentProviderClient contentProviderClient) {
            return new EntityIteratorImpl(cursor, contentProviderClient);
        }

        public static EntityIterator newEntityIterator(Cursor cursor, ContentResolver contentResolver) {
            return new EntityIteratorImpl(cursor, contentResolver);
        }

        private static class EntityIteratorImpl
        extends CursorEntityIterator {
            private static final String[] ATTENDEES_PROJECTION;
            private static final int COLUMN_ATTENDEE_EMAIL = 1;
            private static final int COLUMN_ATTENDEE_IDENTITY = 5;
            private static final int COLUMN_ATTENDEE_ID_NAMESPACE = 6;
            private static final int COLUMN_ATTENDEE_NAME = 0;
            private static final int COLUMN_ATTENDEE_RELATIONSHIP = 2;
            private static final int COLUMN_ATTENDEE_STATUS = 4;
            private static final int COLUMN_ATTENDEE_TYPE = 3;
            private static final int COLUMN_ID = 0;
            private static final int COLUMN_METHOD = 1;
            private static final int COLUMN_MINUTES = 0;
            private static final int COLUMN_NAME = 1;
            private static final int COLUMN_VALUE = 2;
            private static final String[] EXTENDED_PROJECTION;
            private static final String[] REMINDERS_PROJECTION;
            private static final String WHERE_EVENT_ID = "event_id=?";
            private final ContentProviderClient mProvider;
            private final ContentResolver mResolver;

            static {
                REMINDERS_PROJECTION = new String[]{"minutes", "method"};
                ATTENDEES_PROJECTION = new String[]{"attendeeName", "attendeeEmail", "attendeeRelationship", "attendeeType", "attendeeStatus", "attendeeIdentity", "attendeeIdNamespace"};
                EXTENDED_PROJECTION = new String[]{"_id", "name", "value"};
            }

            public EntityIteratorImpl(Cursor cursor, ContentProviderClient contentProviderClient) {
                super(cursor);
                this.mResolver = null;
                this.mProvider = contentProviderClient;
            }

            public EntityIteratorImpl(Cursor cursor, ContentResolver contentResolver) {
                super(cursor);
                this.mResolver = contentResolver;
                this.mProvider = null;
            }

            @Override
            public Entity getEntityAndIncrementCursor(Cursor cursor) throws RemoteException {
                Entity entity;
                Object object;
                ContentValues contentValues;
                block15 : {
                    long l;
                    block13 : {
                        block14 : {
                            block12 : {
                                l = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                                object = new ContentValues();
                                ((ContentValues)object).put("_id", l);
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "calendar_id");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "title");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "description");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "eventLocation");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "eventStatus");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "selfAttendeeStatus");
                                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, (ContentValues)object, "dtstart");
                                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, (ContentValues)object, "dtend");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "duration");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "eventTimezone");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "eventEndTimezone");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, CalendarContract.EXTRA_EVENT_ALL_DAY);
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "accessLevel");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "availability");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "eventColor");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "eventColor_index");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "hasAlarm");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "hasExtendedProperties");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "rrule");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "rdate");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "exrule");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "exdate");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "original_sync_id");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "original_id");
                                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, (ContentValues)object, "originalInstanceTime");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "originalAllDay");
                                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, (ContentValues)object, "lastDate");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "hasAttendeeData");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "guestsCanInviteOthers");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "guestsCanModify");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "guestsCanSeeGuests");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "customAppPackage");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, CalendarContract.EXTRA_CUSTOM_APP_URI);
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "uid2445");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "organizer");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "isOrganizer");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "_sync_id");
                                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, (ContentValues)object, "dirty");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "mutators");
                                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, (ContentValues)object, "lastSynced");
                                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, (ContentValues)object, "deleted");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data1");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data2");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data3");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data4");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data5");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data6");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data7");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data8");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data9");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "sync_data10");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync1");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync2");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync3");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync4");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync5");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync6");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync7");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync8");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync9");
                                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, (ContentValues)object, "cal_sync10");
                                entity = new Entity((ContentValues)object);
                                object = this.mResolver;
                                object = object != null ? ((ContentResolver)object).query(Reminders.CONTENT_URI, REMINDERS_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(l)}, null) : this.mProvider.query(Reminders.CONTENT_URI, REMINDERS_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(l)}, null);
                                while (object.moveToNext()) {
                                    contentValues = new ContentValues();
                                    contentValues.put("minutes", object.getInt(0));
                                    contentValues.put("method", object.getInt(1));
                                    entity.addSubValue(Reminders.CONTENT_URI, contentValues);
                                }
                                object = this.mResolver;
                                if (object == null) break block12;
                                object = ((ContentResolver)object).query(Attendees.CONTENT_URI, ATTENDEES_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(l)}, null);
                                break block14;
                            }
                            object = this.mProvider.query(Attendees.CONTENT_URI, ATTENDEES_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(l)}, null);
                        }
                        while (object.moveToNext()) {
                            contentValues = new ContentValues();
                            contentValues.put("attendeeName", object.getString(0));
                            contentValues.put("attendeeEmail", object.getString(1));
                            contentValues.put("attendeeRelationship", object.getInt(2));
                            contentValues.put("attendeeType", object.getInt(3));
                            contentValues.put("attendeeStatus", object.getInt(4));
                            contentValues.put("attendeeIdentity", object.getString(5));
                            contentValues.put("attendeeIdNamespace", object.getString(6));
                            entity.addSubValue(Attendees.CONTENT_URI, contentValues);
                        }
                        object = this.mResolver;
                        if (object == null) break block13;
                        object = ((ContentResolver)object).query(ExtendedProperties.CONTENT_URI, EXTENDED_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(l)}, null);
                        break block15;
                    }
                    object = this.mProvider.query(ExtendedProperties.CONTENT_URI, EXTENDED_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(l)}, null);
                }
                while (object.moveToNext()) {
                    contentValues = new ContentValues();
                    contentValues.put("_id", object.getString(0));
                    contentValues.put("name", object.getString(1));
                    contentValues.put("value", object.getString(2));
                    entity.addSubValue(ExtendedProperties.CONTENT_URI, contentValues);
                }
                cursor.moveToNext();
                return entity;
                finally {
                    object.close();
                }
                finally {
                    object.close();
                }
                finally {
                    object.close();
                }
            }
        }

    }

    public static final class EventsRawTimes
    implements BaseColumns,
    EventsRawTimesColumns {
        private EventsRawTimes() {
        }
    }

    protected static interface EventsRawTimesColumns {
        public static final String DTEND_2445 = "dtend2445";
        public static final String DTSTART_2445 = "dtstart2445";
        public static final String EVENT_ID = "event_id";
        public static final String LAST_DATE_2445 = "lastDate2445";
        public static final String ORIGINAL_INSTANCE_TIME_2445 = "originalInstanceTime2445";
    }

    public static final class ExtendedProperties
    implements BaseColumns,
    ExtendedPropertiesColumns,
    EventsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/extendedproperties");

        private ExtendedProperties() {
        }
    }

    protected static interface ExtendedPropertiesColumns {
        public static final String EVENT_ID = "event_id";
        public static final String NAME = "name";
        public static final String VALUE = "value";
    }

    public static final class Instances
    implements BaseColumns,
    EventsColumns,
    CalendarColumns {
        public static final String BEGIN = "begin";
        public static final Uri CONTENT_BY_DAY_URI;
        public static final Uri CONTENT_SEARCH_BY_DAY_URI;
        public static final Uri CONTENT_SEARCH_URI;
        public static final Uri CONTENT_URI;
        private static final String DEFAULT_SORT_ORDER = "begin ASC";
        public static final String END = "end";
        public static final String END_DAY = "endDay";
        public static final String END_MINUTE = "endMinute";
        public static final Uri ENTERPRISE_CONTENT_BY_DAY_URI;
        public static final Uri ENTERPRISE_CONTENT_SEARCH_BY_DAY_URI;
        public static final Uri ENTERPRISE_CONTENT_SEARCH_URI;
        public static final Uri ENTERPRISE_CONTENT_URI;
        public static final String EVENT_ID = "event_id";
        public static final String START_DAY = "startDay";
        public static final String START_MINUTE = "startMinute";
        private static final String[] WHERE_CALENDARS_ARGS;
        private static final String WHERE_CALENDARS_SELECTED = "visible=?";

        static {
            WHERE_CALENDARS_ARGS = new String[]{"1"};
            CONTENT_URI = Uri.parse("content://com.android.calendar/instances/when");
            CONTENT_BY_DAY_URI = Uri.parse("content://com.android.calendar/instances/whenbyday");
            CONTENT_SEARCH_URI = Uri.parse("content://com.android.calendar/instances/search");
            CONTENT_SEARCH_BY_DAY_URI = Uri.parse("content://com.android.calendar/instances/searchbyday");
            ENTERPRISE_CONTENT_URI = Uri.parse("content://com.android.calendar/enterprise/instances/when");
            ENTERPRISE_CONTENT_BY_DAY_URI = Uri.parse("content://com.android.calendar/enterprise/instances/whenbyday");
            ENTERPRISE_CONTENT_SEARCH_URI = Uri.parse("content://com.android.calendar/enterprise/instances/search");
            ENTERPRISE_CONTENT_SEARCH_BY_DAY_URI = Uri.parse("content://com.android.calendar/enterprise/instances/searchbyday");
        }

        private Instances() {
        }

        public static final Cursor query(ContentResolver contentResolver, String[] arrstring, long l, long l2) {
            Uri.Builder builder = CONTENT_URI.buildUpon();
            ContentUris.appendId(builder, l);
            ContentUris.appendId(builder, l2);
            return contentResolver.query(builder.build(), arrstring, WHERE_CALENDARS_SELECTED, WHERE_CALENDARS_ARGS, DEFAULT_SORT_ORDER);
        }

        public static final Cursor query(ContentResolver contentResolver, String[] arrstring, long l, long l2, String string2) {
            Uri.Builder builder = CONTENT_SEARCH_URI.buildUpon();
            ContentUris.appendId(builder, l);
            ContentUris.appendId(builder, l2);
            return contentResolver.query(builder.appendPath(string2).build(), arrstring, WHERE_CALENDARS_SELECTED, WHERE_CALENDARS_ARGS, DEFAULT_SORT_ORDER);
        }
    }

    public static final class Reminders
    implements BaseColumns,
    RemindersColumns,
    EventsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/reminders");
        private static final String REMINDERS_WHERE = "event_id=?";

        private Reminders() {
        }

        public static final Cursor query(ContentResolver contentResolver, long l, String[] arrstring) {
            String string2 = Long.toString(l);
            return contentResolver.query(CONTENT_URI, arrstring, REMINDERS_WHERE, new String[]{string2}, null);
        }
    }

    protected static interface RemindersColumns {
        public static final String EVENT_ID = "event_id";
        public static final String METHOD = "method";
        public static final int METHOD_ALARM = 4;
        public static final int METHOD_ALERT = 1;
        public static final int METHOD_DEFAULT = 0;
        public static final int METHOD_EMAIL = 2;
        public static final int METHOD_SMS = 3;
        public static final String MINUTES = "minutes";
        public static final int MINUTES_DEFAULT = -1;
    }

    protected static interface SyncColumns
    extends CalendarSyncColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String CAN_PARTIALLY_UPDATE = "canPartiallyUpdate";
        public static final String DELETED = "deleted";
        public static final String DIRTY = "dirty";
        public static final String MUTATORS = "mutators";
        public static final String _SYNC_ID = "_sync_id";
    }

    public static final class SyncState
    implements SyncStateContract.Columns {
        private static final String CONTENT_DIRECTORY = "syncstate";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "syncstate");

        private SyncState() {
        }
    }

}

