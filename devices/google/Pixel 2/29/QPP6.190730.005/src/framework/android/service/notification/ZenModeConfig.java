/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.service.notification;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.service.notification.Condition;
import android.service.notification.ScheduleCalendar;
import android.service.notification.ZenPolicy;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class ZenModeConfig
implements Parcelable {
    private static final String ALLOW_ATT_ALARMS = "alarms";
    private static final String ALLOW_ATT_CALLS = "calls";
    private static final String ALLOW_ATT_CALLS_FROM = "callsFrom";
    private static final String ALLOW_ATT_EVENTS = "events";
    private static final String ALLOW_ATT_FROM = "from";
    private static final String ALLOW_ATT_MEDIA = "media";
    private static final String ALLOW_ATT_MESSAGES = "messages";
    private static final String ALLOW_ATT_MESSAGES_FROM = "messagesFrom";
    private static final String ALLOW_ATT_REMINDERS = "reminders";
    private static final String ALLOW_ATT_REPEAT_CALLERS = "repeatCallers";
    private static final String ALLOW_ATT_SCREEN_OFF = "visualScreenOff";
    private static final String ALLOW_ATT_SCREEN_ON = "visualScreenOn";
    private static final String ALLOW_ATT_SYSTEM = "system";
    private static final String ALLOW_TAG = "allow";
    public static final int[] ALL_DAYS;
    private static final String AUTOMATIC_TAG = "automatic";
    private static final String CONDITION_ATT_FLAGS = "flags";
    private static final String CONDITION_ATT_ICON = "icon";
    private static final String CONDITION_ATT_ID = "id";
    private static final String CONDITION_ATT_LINE1 = "line1";
    private static final String CONDITION_ATT_LINE2 = "line2";
    private static final String CONDITION_ATT_STATE = "state";
    private static final String CONDITION_ATT_SUMMARY = "summary";
    public static final String COUNTDOWN_PATH = "countdown";
    public static final Parcelable.Creator<ZenModeConfig> CREATOR;
    private static final int DAY_MINUTES = 1440;
    private static final boolean DEFAULT_ALLOW_ALARMS = true;
    private static final boolean DEFAULT_ALLOW_CALLS = true;
    private static final boolean DEFAULT_ALLOW_EVENTS = false;
    private static final boolean DEFAULT_ALLOW_MEDIA = true;
    private static final boolean DEFAULT_ALLOW_MESSAGES = false;
    private static final boolean DEFAULT_ALLOW_REMINDERS = false;
    private static final boolean DEFAULT_ALLOW_REPEAT_CALLERS = true;
    private static final boolean DEFAULT_ALLOW_SYSTEM = false;
    private static final int DEFAULT_CALLS_SOURCE = 2;
    private static final boolean DEFAULT_CHANNELS_BYPASSING_DND = false;
    public static final List<String> DEFAULT_RULE_IDS;
    private static final int DEFAULT_SOURCE = 1;
    private static final int DEFAULT_SUPPRESSED_VISUAL_EFFECTS = 0;
    private static final String DISALLOW_ATT_VISUAL_EFFECTS = "visualEffects";
    private static final String DISALLOW_TAG = "disallow";
    public static final String EVENTS_DEFAULT_RULE_ID = "EVENTS_DEFAULT_RULE";
    public static final String EVENT_PATH = "event";
    public static final String EVERY_NIGHT_DEFAULT_RULE_ID = "EVERY_NIGHT_DEFAULT_RULE";
    public static final String IS_ALARM_PATH = "alarm";
    private static final String MANUAL_TAG = "manual";
    public static final int MAX_SOURCE = 2;
    private static final int MINUTES_MS = 60000;
    public static final int[] MINUTE_BUCKETS;
    private static final String RULE_ATT_COMPONENT = "component";
    private static final String RULE_ATT_CONDITION_ID = "conditionId";
    private static final String RULE_ATT_CONFIG_ACTIVITY = "configActivity";
    private static final String RULE_ATT_CREATION_TIME = "creationTime";
    private static final String RULE_ATT_ENABLED = "enabled";
    private static final String RULE_ATT_ENABLER = "enabler";
    private static final String RULE_ATT_ID = "ruleId";
    private static final String RULE_ATT_MODIFIED = "modified";
    private static final String RULE_ATT_NAME = "name";
    private static final String RULE_ATT_SNOOZING = "snoozing";
    private static final String RULE_ATT_ZEN = "zen";
    public static final String SCHEDULE_PATH = "schedule";
    private static final int SECONDS_MS = 1000;
    private static final String SHOW_ATT_AMBIENT = "showAmbient";
    private static final String SHOW_ATT_BADGES = "showBadges";
    private static final String SHOW_ATT_FULL_SCREEN_INTENT = "showFullScreenIntent";
    private static final String SHOW_ATT_LIGHTS = "showLights";
    private static final String SHOW_ATT_NOTIFICATION_LIST = "showNotificationList";
    private static final String SHOW_ATT_PEEK = "shoePeek";
    private static final String SHOW_ATT_STATUS_BAR_ICONS = "showStatusBarIcons";
    public static final int SOURCE_ANYONE = 0;
    public static final int SOURCE_CONTACT = 1;
    public static final int SOURCE_STAR = 2;
    private static final String STATE_ATT_CHANNELS_BYPASSING_DND = "areChannelsBypassingDnd";
    private static final String STATE_TAG = "state";
    public static final String SYSTEM_AUTHORITY = "android";
    private static String TAG;
    public static final int XML_VERSION = 8;
    private static final String ZEN_ATT_USER = "user";
    private static final String ZEN_ATT_VERSION = "version";
    private static final String ZEN_POLICY_TAG = "zen_policy";
    public static final String ZEN_TAG = "zen";
    private static final int ZERO_VALUE_MS = 10000;
    @UnsupportedAppUsage
    public boolean allowAlarms;
    public boolean allowCalls;
    public int allowCallsFrom;
    public boolean allowEvents;
    public boolean allowMedia;
    public boolean allowMessages;
    public int allowMessagesFrom;
    public boolean allowReminders;
    public boolean allowRepeatCallers;
    public boolean allowSystem;
    public boolean areChannelsBypassingDnd;
    @UnsupportedAppUsage
    public ArrayMap<String, ZenRule> automaticRules;
    public ZenRule manualRule;
    public int suppressedVisualEffects;
    public int user;
    public int version;

    static {
        TAG = "ZenModeConfig";
        DEFAULT_RULE_IDS = Arrays.asList(EVERY_NIGHT_DEFAULT_RULE_ID, EVENTS_DEFAULT_RULE_ID);
        ALL_DAYS = new int[]{1, 2, 3, 4, 5, 6, 7};
        MINUTE_BUCKETS = ZenModeConfig.generateMinuteBuckets();
        CREATOR = new Parcelable.Creator<ZenModeConfig>(){

            @Override
            public ZenModeConfig createFromParcel(Parcel parcel) {
                return new ZenModeConfig(parcel);
            }

            public ZenModeConfig[] newArray(int n) {
                return new ZenModeConfig[n];
            }
        };
    }

    @UnsupportedAppUsage
    public ZenModeConfig() {
        this.allowAlarms = true;
        this.allowMedia = true;
        this.allowSystem = false;
        this.allowCalls = true;
        this.allowRepeatCallers = true;
        this.allowMessages = false;
        this.allowReminders = false;
        this.allowEvents = false;
        this.allowCallsFrom = 2;
        this.allowMessagesFrom = 1;
        this.user = 0;
        this.suppressedVisualEffects = 0;
        this.areChannelsBypassingDnd = false;
        this.automaticRules = new ArrayMap();
    }

    public ZenModeConfig(Parcel parcel) {
        boolean bl = true;
        this.allowAlarms = true;
        this.allowMedia = true;
        this.allowSystem = false;
        this.allowCalls = true;
        this.allowRepeatCallers = true;
        this.allowMessages = false;
        this.allowReminders = false;
        this.allowEvents = false;
        this.allowCallsFrom = 2;
        this.allowMessagesFrom = 1;
        this.user = 0;
        this.suppressedVisualEffects = 0;
        this.areChannelsBypassingDnd = false;
        this.automaticRules = new ArrayMap();
        boolean bl2 = parcel.readInt() == 1;
        this.allowCalls = bl2;
        bl2 = parcel.readInt() == 1;
        this.allowRepeatCallers = bl2;
        bl2 = parcel.readInt() == 1;
        this.allowMessages = bl2;
        bl2 = parcel.readInt() == 1;
        this.allowReminders = bl2;
        bl2 = parcel.readInt() == 1;
        this.allowEvents = bl2;
        this.allowCallsFrom = parcel.readInt();
        this.allowMessagesFrom = parcel.readInt();
        this.user = parcel.readInt();
        this.manualRule = (ZenRule)parcel.readParcelable(null);
        int n = parcel.readInt();
        if (n > 0) {
            String[] arrstring = new String[n];
            ZenRule[] arrzenRule = new ZenRule[n];
            parcel.readStringArray(arrstring);
            parcel.readTypedArray(arrzenRule, ZenRule.CREATOR);
            for (int i = 0; i < n; ++i) {
                this.automaticRules.put(arrstring[i], arrzenRule[i]);
            }
        }
        bl2 = parcel.readInt() == 1;
        this.allowAlarms = bl2;
        bl2 = parcel.readInt() == 1;
        this.allowMedia = bl2;
        bl2 = parcel.readInt() == 1;
        this.allowSystem = bl2;
        this.suppressedVisualEffects = parcel.readInt();
        bl2 = parcel.readInt() == 1 ? bl : false;
        this.areChannelsBypassingDnd = bl2;
    }

    private static <T> void addKeys(ArraySet<T> arraySet, ArrayMap<T, ?> arrayMap) {
        if (arrayMap != null) {
            for (int i = 0; i < arrayMap.size(); ++i) {
                arraySet.add(arrayMap.keyAt(i));
            }
        }
    }

    public static boolean areAllPriorityOnlyNotificationZenSoundsMuted(NotificationManager.Policy policy) {
        int n = policy.priorityCategories;
        boolean bl = true;
        n = (n & 1) != 0 ? 1 : 0;
        boolean bl2 = (policy.priorityCategories & 8) != 0;
        boolean bl3 = (policy.priorityCategories & 4) != 0;
        boolean bl4 = (policy.priorityCategories & 2) != 0;
        boolean bl5 = (policy.priorityCategories & 16) != 0;
        boolean bl6 = (policy.state & 1) != 0;
        if (n != 0 || bl2 || bl3 || bl4 || bl5 || bl6) {
            bl = false;
        }
        return bl;
    }

    public static boolean areAllPriorityOnlyNotificationZenSoundsMuted(ZenModeConfig zenModeConfig) {
        boolean bl = !zenModeConfig.allowReminders && !zenModeConfig.allowCalls && !zenModeConfig.allowMessages && !zenModeConfig.allowEvents && !zenModeConfig.allowRepeatCallers && !zenModeConfig.areChannelsBypassingDnd;
        return bl;
    }

    public static boolean areAllZenBehaviorSoundsMuted(NotificationManager.Policy policy) {
        int n = policy.priorityCategories;
        boolean bl = true;
        n = (n & 32) != 0 ? 1 : 0;
        boolean bl2 = (policy.priorityCategories & 64) != 0;
        boolean bl3 = (policy.priorityCategories & 128) != 0;
        if (n != 0 || bl2 || bl3 || !ZenModeConfig.areAllPriorityOnlyNotificationZenSoundsMuted(policy)) {
            bl = false;
        }
        return bl;
    }

    public static boolean areAllZenBehaviorSoundsMuted(ZenModeConfig zenModeConfig) {
        boolean bl = !zenModeConfig.allowAlarms && !zenModeConfig.allowMedia && !zenModeConfig.allowSystem && ZenModeConfig.areAllPriorityOnlyNotificationZenSoundsMuted(zenModeConfig);
        return bl;
    }

    public static Diff diff(ZenModeConfig object, ZenModeConfig zenModeConfig) {
        if (object == null) {
            object = new Diff();
            if (zenModeConfig != null) {
                ((Diff)object).addLine("config", "insert");
            }
            return object;
        }
        return ((ZenModeConfig)object).diff(zenModeConfig);
    }

    private static int[] generateMinuteBuckets() {
        int[] arrn = new int[15];
        arrn[0] = 15;
        arrn[1] = 30;
        arrn[2] = 45;
        for (int i = 1; i <= 12; ++i) {
            arrn[i + 2] = i * 60;
        }
        return arrn;
    }

    private static String getConditionLine(Context context, ZenModeConfig object, int n, boolean bl, boolean bl2) {
        String string2 = "";
        if (object == null) {
            return "";
        }
        Object object2 = "";
        ZenRule zenRule2 = ((ZenModeConfig)object).manualRule;
        if (zenRule2 != null) {
            object2 = zenRule2.conditionId;
            if (object.manualRule.enabler != null) {
                object2 = ZenModeConfig.getOwnerCaption(context, object.manualRule.enabler);
            } else if (object2 == null) {
                object2 = context.getString(17041330);
            } else {
                long l = ZenModeConfig.tryParseCountdownConditionId((Uri)object2);
                object2 = object.manualRule.condition;
                if (l > 0L) {
                    object2 = ZenModeConfig.toTimeCondition(context, l, Math.round((float)(l - System.currentTimeMillis()) / 60000.0f), n, bl2);
                }
                if (TextUtils.isEmpty((CharSequence)(object2 = object2 == null ? "" : (bl ? ((Condition)object2).line1 : ((Condition)object2).summary)))) {
                    object2 = string2;
                }
            }
        }
        for (ZenRule zenRule2 : ((ZenModeConfig)object).automaticRules.values()) {
            object = object2;
            if (zenRule2.isAutomaticActive()) {
                object = ((String)object2).isEmpty() ? zenRule2.name : context.getResources().getString(17041332, object2, zenRule2.name);
            }
            object2 = object;
        }
        return object2;
    }

    public static String getConditionSummary(Context context, ZenModeConfig zenModeConfig, int n, boolean bl) {
        return ZenModeConfig.getConditionLine(context, zenModeConfig, n, false, bl);
    }

    public static String getDescription(Context object, boolean bl, ZenModeConfig object2, boolean bl2) {
        Object var4_4 = null;
        if (bl && object2 != null) {
            String string2 = "";
            long l = -1L;
            ZenRule object32 = ((ZenModeConfig)object2).manualRule;
            Object object3 = string2;
            long l2 = l;
            if (object32 != null) {
                object3 = object32.conditionId;
                if (object2.manualRule.enabler != null) {
                    String string3 = ZenModeConfig.getOwnerCaption((Context)object, object2.manualRule.enabler);
                    object3 = string2;
                    if (!string3.isEmpty()) {
                        object3 = string3;
                    }
                    l2 = l;
                } else {
                    if (object3 == null) {
                        if (bl2) {
                            return ((Context)object).getString(17041330);
                        }
                        return null;
                    }
                    l = ZenModeConfig.tryParseCountdownConditionId((Uri)object3);
                    object3 = string2;
                    l2 = l;
                    if (l > 0L) {
                        object3 = ((Context)object).getString(17041333, ZenModeConfig.getFormattedTime((Context)object, l, ZenModeConfig.isToday(l), ((Context)object).getUserId()));
                        l2 = l;
                    }
                }
            }
            for (ZenRule zenRule : ((ZenModeConfig)object2).automaticRules.values()) {
                object2 = object3;
                l = l2;
                if (zenRule.isAutomaticActive()) {
                    if (!ZenModeConfig.isValidEventConditionId(zenRule.conditionId) && !ZenModeConfig.isValidScheduleConditionId(zenRule.conditionId)) {
                        return zenRule.name;
                    }
                    long l3 = ZenModeConfig.parseAutomaticRuleEndTime((Context)object, zenRule.conditionId);
                    object2 = object3;
                    l = l2;
                    if (l3 > l2) {
                        l = l3;
                        object2 = zenRule.name;
                    }
                }
                object3 = object2;
                l2 = l;
            }
            object = var4_4;
            if (!((String)object3).equals("")) {
                object = object3;
            }
            return object;
        }
        return null;
    }

    public static ComponentName getEventConditionProvider() {
        return new ComponentName(SYSTEM_AUTHORITY, "EventConditionProvider");
    }

    public static CharSequence getFormattedTime(Context object, long l, boolean bl, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = !bl ? "EEE " : "";
        stringBuilder.append(string2);
        object = DateFormat.is24HourFormat((Context)object, n) ? "Hm" : "hma";
        stringBuilder.append((String)object);
        object = stringBuilder.toString();
        return DateFormat.format((CharSequence)DateFormat.getBestDateTimePattern(Locale.getDefault(), (String)object), l);
    }

    private static long getNextAlarm(Context object) {
        long l = (object = ((AlarmManager)((Context)object).getSystemService(IS_ALARM_PATH)).getNextAlarmClock(((Context)object).getUserId())) != null ? ((AlarmManager.AlarmClockInfo)object).getTriggerTime() : 0L;
        return l;
    }

    private int getNotificationPolicySenders(int n, int n2) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return n2;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public static String getOwnerCaption(Context object, String object2) {
        block5 : {
            object = ((Context)object).getPackageManager();
            object2 = ((PackageManager)object).getApplicationInfo((String)object2, 0);
            if (object2 == null) break block5;
            object = ((PackageItemInfo)object2).loadLabel((PackageManager)object);
            if (object == null) break block5;
            try {
                object = object.toString().trim();
                int n = ((String)object).length();
                if (n > 0) {
                    return object;
                }
            }
            catch (Throwable throwable) {
                Slog.w(TAG, "Error loading owner caption", throwable);
            }
        }
        return "";
    }

    public static ComponentName getScheduleConditionProvider() {
        return new ComponentName(SYSTEM_AUTHORITY, "ScheduleConditionProvider");
    }

    public static int getZenPolicySenders(int n) {
        if (n != 0) {
            if (n != 1) {
                return 3;
            }
            return 2;
        }
        return 1;
    }

    private boolean isPriorityCategoryEnabled(int n, NotificationManager.Policy policy) {
        boolean bl = (policy.priorityCategories & n) != 0;
        return bl;
    }

    public static boolean isToday(long l) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.setTimeInMillis(l);
        return gregorianCalendar.get(1) == gregorianCalendar2.get(1) && gregorianCalendar.get(2) == gregorianCalendar2.get(2) && gregorianCalendar.get(5) == gregorianCalendar2.get(5);
    }

    private static boolean isValidAutomaticRule(ZenRule zenRule) {
        boolean bl = zenRule != null && !TextUtils.isEmpty(zenRule.name) && Settings.Global.isValidZenMode(zenRule.zenMode) && zenRule.conditionId != null && ZenModeConfig.sameCondition(zenRule);
        return bl;
    }

    public static boolean isValidCountdownConditionId(Uri uri) {
        boolean bl = ZenModeConfig.tryParseCountdownConditionId(uri) != 0L;
        return bl;
    }

    public static boolean isValidCountdownToAlarmConditionId(Uri uri) {
        if (ZenModeConfig.tryParseCountdownConditionId(uri) != 0L) {
            if (uri.getPathSegments().size() >= 4 && IS_ALARM_PATH.equals(uri.getPathSegments().get(2))) {
                try {
                    boolean bl = Boolean.parseBoolean(uri.getPathSegments().get(3));
                    return bl;
                }
                catch (RuntimeException runtimeException) {
                    String string2 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error parsing countdown alarm condition: ");
                    stringBuilder.append(uri);
                    Slog.w(string2, stringBuilder.toString(), runtimeException);
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean isValidEventConditionId(Uri uri) {
        boolean bl = ZenModeConfig.tryParseEventConditionId(uri) != null;
        return bl;
    }

    public static boolean isValidHour(int n) {
        boolean bl = n >= 0 && n < 24;
        return bl;
    }

    private static boolean isValidManualRule(ZenRule zenRule) {
        boolean bl = zenRule == null || Settings.Global.isValidZenMode(zenRule.zenMode) && ZenModeConfig.sameCondition(zenRule);
        return bl;
    }

    public static boolean isValidMinute(int n) {
        boolean bl = n >= 0 && n < 60;
        return bl;
    }

    public static boolean isValidScheduleConditionId(Uri object) {
        try {
            object = ZenModeConfig.tryParseScheduleConditionId((Uri)object);
        }
        catch (ArrayIndexOutOfBoundsException | NullPointerException runtimeException) {
            return false;
        }
        return object != null && ((ScheduleInfo)object).days != null && ((ScheduleInfo)object).days.length != 0;
        {
        }
    }

    public static boolean isValidScheduleConditionId(Uri object, boolean bl) {
        try {
            object = ZenModeConfig.tryParseScheduleConditionId((Uri)object);
        }
        catch (ArrayIndexOutOfBoundsException | NullPointerException runtimeException) {
            return false;
        }
        return object != null && (bl || ((ScheduleInfo)object).days != null && ((ScheduleInfo)object).days.length != 0);
        {
        }
    }

    private static boolean isValidSource(int n) {
        boolean bl = n >= 0 && n <= 2;
        return bl;
    }

    private boolean isVisualEffectAllowed(int n, NotificationManager.Policy policy) {
        boolean bl = (policy.suppressedVisualEffects & n) == 0;
        return bl;
    }

    public static boolean isZenOverridingRinger(int n, NotificationManager.Policy policy) {
        boolean bl;
        block0 : {
            bl = true;
            if (n == 2 || n == 3 || n == 1 && ZenModeConfig.areAllPriorityOnlyNotificationZenSoundsMuted(policy)) break block0;
            bl = false;
        }
        return bl;
    }

    public static String newRuleId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static long parseAutomaticRuleEndTime(Context context, Uri object) {
        if (ZenModeConfig.isValidEventConditionId((Uri)object)) {
            return Long.MAX_VALUE;
        }
        if (ZenModeConfig.isValidScheduleConditionId((Uri)object)) {
            object = ZenModeConfig.toScheduleCalendar((Uri)object);
            long l = ((ScheduleCalendar)object).getNextChangeTime(System.currentTimeMillis());
            if (((ScheduleCalendar)object).exitAtAlarm()) {
                long l2 = ZenModeConfig.getNextAlarm(context);
                ((ScheduleCalendar)object).maybeSetNextAlarm(System.currentTimeMillis(), l2);
                if (((ScheduleCalendar)object).shouldExitForAlarm(l)) {
                    return l2;
                }
            }
            return l;
        }
        return -1L;
    }

    private static int prioritySendersToSource(int n, int n2) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return n2;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public static Condition readConditionXml(XmlPullParser object) {
        Uri uri = ZenModeConfig.safeUri(object, CONDITION_ATT_ID);
        if (uri == null) {
            return null;
        }
        String string2 = object.getAttributeValue(null, CONDITION_ATT_SUMMARY);
        String string3 = object.getAttributeValue(null, CONDITION_ATT_LINE1);
        String string4 = object.getAttributeValue(null, CONDITION_ATT_LINE2);
        int n = ZenModeConfig.safeInt(object, CONDITION_ATT_ICON, -1);
        int n2 = ZenModeConfig.safeInt(object, "state", -1);
        int n3 = ZenModeConfig.safeInt(object, CONDITION_ATT_FLAGS, -1);
        try {
            object = new Condition(uri, string2, string3, string4, n, n2, n3);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Slog.w(TAG, "Unable to read condition xml", illegalArgumentException);
            return null;
        }
    }

    public static ZenRule readRuleXml(XmlPullParser object) {
        Object object2 = new ZenRule();
        ((ZenRule)object2).enabled = ZenModeConfig.safeBoolean(object, RULE_ATT_ENABLED, true);
        ((ZenRule)object2).name = object.getAttributeValue(null, RULE_ATT_NAME);
        String string2 = object.getAttributeValue(null, "zen");
        ((ZenRule)object2).zenMode = ZenModeConfig.tryParseZenMode(string2, -1);
        if (((ZenRule)object2).zenMode == -1) {
            object = TAG;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Bad zen mode in rule xml:");
            ((StringBuilder)object2).append(string2);
            Slog.w((String)object, ((StringBuilder)object2).toString());
            return null;
        }
        ((ZenRule)object2).conditionId = ZenModeConfig.safeUri(object, RULE_ATT_CONDITION_ID);
        ((ZenRule)object2).component = ZenModeConfig.safeComponentName(object, RULE_ATT_COMPONENT);
        ((ZenRule)object2).configurationActivity = ZenModeConfig.safeComponentName(object, RULE_ATT_CONFIG_ACTIVITY);
        string2 = ((ZenRule)object2).component != null ? ((ZenRule)object2).component.getPackageName() : (((ZenRule)object2).configurationActivity != null ? ((ZenRule)object2).configurationActivity.getPackageName() : null);
        ((ZenRule)object2).pkg = string2;
        ((ZenRule)object2).creationTime = ZenModeConfig.safeLong(object, RULE_ATT_CREATION_TIME, 0L);
        ((ZenRule)object2).enabler = object.getAttributeValue(null, RULE_ATT_ENABLER);
        ((ZenRule)object2).condition = ZenModeConfig.readConditionXml(object);
        if (((ZenRule)object2).zenMode != 1 && Condition.isValidId(((ZenRule)object2).conditionId, SYSTEM_AUTHORITY)) {
            string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Updating zenMode of automatic rule ");
            stringBuilder.append(((ZenRule)object2).name);
            Slog.i(string2, stringBuilder.toString());
            ((ZenRule)object2).zenMode = 1;
        }
        ((ZenRule)object2).modified = ZenModeConfig.safeBoolean(object, RULE_ATT_MODIFIED, false);
        ((ZenRule)object2).zenPolicy = ZenModeConfig.readZenPolicyXml(object);
        return object2;
    }

    public static ZenModeConfig readXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        if (xmlPullParser.getEventType() != 2) {
            return null;
        }
        if (!"zen".equals(xmlPullParser.getName())) {
            return null;
        }
        ZenModeConfig zenModeConfig = new ZenModeConfig();
        zenModeConfig.version = ZenModeConfig.safeInt(xmlPullParser, ZEN_ATT_VERSION, 8);
        zenModeConfig.user = ZenModeConfig.safeInt(xmlPullParser, ZEN_ATT_USER, zenModeConfig.user);
        int n2 = 0;
        while ((n = xmlPullParser.next()) != 1) {
            Object object;
            Object object2 = xmlPullParser.getName();
            if (n == 3 && "zen".equals(object2)) {
                return zenModeConfig;
            }
            if (n != 2) continue;
            if (ALLOW_TAG.equals(object2)) {
                zenModeConfig.allowCalls = ZenModeConfig.safeBoolean(xmlPullParser, ALLOW_ATT_CALLS, true);
                zenModeConfig.allowRepeatCallers = ZenModeConfig.safeBoolean(xmlPullParser, ALLOW_ATT_REPEAT_CALLERS, true);
                zenModeConfig.allowMessages = ZenModeConfig.safeBoolean(xmlPullParser, ALLOW_ATT_MESSAGES, false);
                zenModeConfig.allowReminders = ZenModeConfig.safeBoolean(xmlPullParser, ALLOW_ATT_REMINDERS, false);
                zenModeConfig.allowEvents = ZenModeConfig.safeBoolean(xmlPullParser, ALLOW_ATT_EVENTS, false);
                int n3 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_FROM, -1);
                n = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_CALLS_FROM, -1);
                int n4 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_MESSAGES_FROM, -1);
                if (ZenModeConfig.isValidSource(n) && ZenModeConfig.isValidSource(n4)) {
                    zenModeConfig.allowCallsFrom = n;
                    zenModeConfig.allowMessagesFrom = n4;
                } else if (ZenModeConfig.isValidSource(n3)) {
                    object2 = TAG;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Migrating existing shared 'from': ");
                    ((StringBuilder)object).append(ZenModeConfig.sourceToString(n3));
                    Slog.i((String)object2, ((StringBuilder)object).toString());
                    zenModeConfig.allowCallsFrom = n3;
                    zenModeConfig.allowMessagesFrom = n3;
                } else {
                    zenModeConfig.allowCallsFrom = 2;
                    zenModeConfig.allowMessagesFrom = 1;
                }
                zenModeConfig.allowAlarms = ZenModeConfig.safeBoolean(xmlPullParser, ALLOW_ATT_ALARMS, true);
                zenModeConfig.allowMedia = ZenModeConfig.safeBoolean(xmlPullParser, ALLOW_ATT_MEDIA, true);
                zenModeConfig.allowSystem = ZenModeConfig.safeBoolean(xmlPullParser, ALLOW_ATT_SYSTEM, false);
                object2 = ZenModeConfig.unsafeBoolean(xmlPullParser, ALLOW_ATT_SCREEN_OFF);
                if (object2 != null) {
                    n2 = n = 1;
                    if (!((Boolean)object2).booleanValue()) {
                        zenModeConfig.suppressedVisualEffects |= 12;
                        n2 = n;
                    }
                }
                if ((object2 = ZenModeConfig.unsafeBoolean(xmlPullParser, ALLOW_ATT_SCREEN_ON)) != null) {
                    n2 = n = 1;
                    if (!((Boolean)object2).booleanValue()) {
                        zenModeConfig.suppressedVisualEffects |= 16;
                        n2 = n;
                    }
                }
                if (n2 == 0) continue;
                object2 = TAG;
                object = new StringBuilder();
                ((StringBuilder)object).append("Migrated visual effects to ");
                ((StringBuilder)object).append(zenModeConfig.suppressedVisualEffects);
                Slog.d((String)object2, ((StringBuilder)object).toString());
                continue;
            }
            if (DISALLOW_TAG.equals(object2) && n2 == 0) {
                zenModeConfig.suppressedVisualEffects = ZenModeConfig.safeInt(xmlPullParser, DISALLOW_ATT_VISUAL_EFFECTS, 0);
                continue;
            }
            if (MANUAL_TAG.equals(object2)) {
                zenModeConfig.manualRule = ZenModeConfig.readRuleXml(xmlPullParser);
                continue;
            }
            if (AUTOMATIC_TAG.equals(object2)) {
                object2 = xmlPullParser.getAttributeValue(null, RULE_ATT_ID);
                object = ZenModeConfig.readRuleXml(xmlPullParser);
                if (object2 == null || object == null) continue;
                ((ZenRule)object).id = object2;
                zenModeConfig.automaticRules.put((String)object2, (ZenRule)object);
                continue;
            }
            if (!"state".equals(object2)) continue;
            zenModeConfig.areChannelsBypassingDnd = ZenModeConfig.safeBoolean(xmlPullParser, STATE_ATT_CHANNELS_BYPASSING_DND, false);
        }
        throw new IllegalStateException("Failed to reach END_DOCUMENT");
    }

    public static ZenPolicy readZenPolicyXml(XmlPullParser xmlPullParser) {
        boolean bl;
        boolean bl2 = false;
        ZenPolicy.Builder builder = new ZenPolicy.Builder();
        int n = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_CALLS_FROM, 0);
        int n2 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_MESSAGES_FROM, 0);
        int n3 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_REPEAT_CALLERS, 0);
        int n4 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_ALARMS, 0);
        int n5 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_MEDIA, 0);
        int n6 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_SYSTEM, 0);
        int n7 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_EVENTS, 0);
        int n8 = ZenModeConfig.safeInt(xmlPullParser, ALLOW_ATT_REMINDERS, 0);
        if (n != 0) {
            builder.allowCalls(n);
            bl2 = true;
        }
        if (n2 != 0) {
            builder.allowMessages(n2);
            bl2 = true;
        }
        if (n3 != 0) {
            bl = n3 == 1;
            builder.allowRepeatCallers(bl);
            bl2 = true;
        }
        if (n4 != 0) {
            bl = n4 == 1;
            builder.allowAlarms(bl);
            bl2 = true;
        }
        if (n5 != 0) {
            bl = n5 == 1;
            builder.allowMedia(bl);
            bl2 = true;
        }
        if (n6 != 0) {
            bl = n6 == 1;
            builder.allowSystem(bl);
            bl2 = true;
        }
        if (n7 != 0) {
            bl = n7 == 1;
            builder.allowEvents(bl);
            bl2 = true;
        }
        if (n8 != 0) {
            bl = n8 == 1;
            builder.allowReminders(bl);
            bl2 = true;
        }
        n2 = ZenModeConfig.safeInt(xmlPullParser, SHOW_ATT_FULL_SCREEN_INTENT, 0);
        n3 = ZenModeConfig.safeInt(xmlPullParser, SHOW_ATT_LIGHTS, 0);
        n4 = ZenModeConfig.safeInt(xmlPullParser, SHOW_ATT_PEEK, 0);
        n5 = ZenModeConfig.safeInt(xmlPullParser, SHOW_ATT_STATUS_BAR_ICONS, 0);
        n6 = ZenModeConfig.safeInt(xmlPullParser, SHOW_ATT_BADGES, 0);
        n7 = ZenModeConfig.safeInt(xmlPullParser, SHOW_ATT_AMBIENT, 0);
        n8 = ZenModeConfig.safeInt(xmlPullParser, SHOW_ATT_NOTIFICATION_LIST, 0);
        if (n2 != 0) {
            bl = n2 == 1;
            builder.showFullScreenIntent(bl);
            bl2 = true;
        }
        if (n3 != 0) {
            bl = n3 == 1;
            builder.showLights(bl);
            bl2 = true;
        }
        if (n4 != 0) {
            bl = n4 == 1;
            builder.showPeeking(bl);
            bl2 = true;
        }
        if (n5 != 0) {
            bl = n5 == 1;
            builder.showStatusBarIcons(bl);
            bl2 = true;
        }
        if (n6 != 0) {
            bl = n6 == 1;
            builder.showBadges(bl);
            bl2 = true;
        }
        if (n7 != 0) {
            bl = n7 == 1;
            builder.showInAmbientDisplay(bl);
            bl2 = true;
        }
        if (n8 != 0) {
            bl = true;
            if (n8 != 1) {
                bl = false;
            }
            builder.showInNotificationList(bl);
            bl2 = true;
        }
        if (bl2) {
            return builder.build();
        }
        return null;
    }

    private String rulesToString() {
        if (this.automaticRules.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.automaticRules.size() * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.automaticRules.size(); ++i) {
            if (i > 0) {
                stringBuilder.append(",\n");
            }
            stringBuilder.append(this.automaticRules.valueAt(i));
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    private static boolean safeBoolean(String string2, boolean bl) {
        if (TextUtils.isEmpty(string2)) {
            return bl;
        }
        return Boolean.parseBoolean(string2);
    }

    private static boolean safeBoolean(XmlPullParser xmlPullParser, String string2, boolean bl) {
        return ZenModeConfig.safeBoolean(xmlPullParser.getAttributeValue(null, string2), bl);
    }

    private static ComponentName safeComponentName(XmlPullParser object, String string2) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return null;
        }
        return ComponentName.unflattenFromString((String)object);
    }

    private static int safeInt(XmlPullParser xmlPullParser, String string2, int n) {
        return ZenModeConfig.tryParseInt(xmlPullParser.getAttributeValue(null, string2), n);
    }

    private static long safeLong(XmlPullParser xmlPullParser, String string2, long l) {
        return ZenModeConfig.tryParseLong(xmlPullParser.getAttributeValue(null, string2), l);
    }

    private static Uri safeUri(XmlPullParser object, String string2) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return null;
        }
        return Uri.parse((String)object);
    }

    private static boolean sameCondition(ZenRule zenRule) {
        boolean bl;
        block7 : {
            block6 : {
                boolean bl2 = false;
                bl = false;
                if (zenRule == null) {
                    return false;
                }
                if (zenRule.conditionId == null) {
                    if (zenRule.condition == null) {
                        bl = true;
                    }
                    return bl;
                }
                if (zenRule.condition == null) break block6;
                bl = bl2;
                if (!zenRule.conditionId.equals(zenRule.condition.id)) break block7;
            }
            bl = true;
        }
        return bl;
    }

    private static int sourceToPrioritySenders(int n, int n2) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return n2;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public static String sourceToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return "UNKNOWN";
                }
                return "stars";
            }
            return "contacts";
        }
        return "anyone";
    }

    public static Uri toCountdownConditionId(long l, boolean bl) {
        return new Uri.Builder().scheme("condition").authority(SYSTEM_AUTHORITY).appendPath(COUNTDOWN_PATH).appendPath(Long.toString(l)).appendPath(IS_ALARM_PATH).appendPath(Boolean.toString(bl)).build();
    }

    private static String toDayList(int[] arrn) {
        if (arrn != null && arrn.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < arrn.length; ++i) {
                if (i > 0) {
                    stringBuilder.append('.');
                }
                stringBuilder.append(arrn[i]);
            }
            return stringBuilder.toString();
        }
        return "";
    }

    public static Uri toEventConditionId(EventInfo eventInfo) {
        Uri.Builder builder = new Uri.Builder().scheme("condition").authority(SYSTEM_AUTHORITY).appendPath(EVENT_PATH).appendQueryParameter("userId", Long.toString(eventInfo.userId));
        String string2 = eventInfo.calName;
        String string3 = "";
        string2 = string2 != null ? eventInfo.calName : "";
        builder = builder.appendQueryParameter("calendar", string2);
        string2 = eventInfo.calendarId != null ? eventInfo.calendarId.toString() : string3;
        return builder.appendQueryParameter("calendarId", string2).appendQueryParameter("reply", Integer.toString(eventInfo.reply)).build();
    }

    public static Condition toNextAlarmCondition(Context object, long l, int n) {
        CharSequence charSequence = ZenModeConfig.getFormattedTime((Context)object, l, ZenModeConfig.isToday(l), n);
        object = ((Context)object).getResources().getString(17041333, charSequence);
        return new Condition(ZenModeConfig.toCountdownConditionId(l, true), "", (String)object, "", 0, 1, 1);
    }

    public static ScheduleCalendar toScheduleCalendar(Uri object) {
        if ((object = ZenModeConfig.tryParseScheduleConditionId((Uri)object)) != null && ((ScheduleInfo)object).days != null && ((ScheduleInfo)object).days.length != 0) {
            ScheduleCalendar scheduleCalendar = new ScheduleCalendar();
            scheduleCalendar.setSchedule((ScheduleInfo)object);
            scheduleCalendar.setTimeZone(TimeZone.getDefault());
            return scheduleCalendar;
        }
        return null;
    }

    public static Uri toScheduleConditionId(ScheduleInfo scheduleInfo) {
        Uri.Builder builder = new Uri.Builder().scheme("condition").authority(SYSTEM_AUTHORITY).appendPath(SCHEDULE_PATH).appendQueryParameter("days", ZenModeConfig.toDayList(scheduleInfo.days));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scheduleInfo.startHour);
        stringBuilder.append(".");
        stringBuilder.append(scheduleInfo.startMinute);
        builder = builder.appendQueryParameter("start", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(scheduleInfo.endHour);
        stringBuilder.append(".");
        stringBuilder.append(scheduleInfo.endMinute);
        return builder.appendQueryParameter("end", stringBuilder.toString()).appendQueryParameter("exitAtAlarm", String.valueOf(scheduleInfo.exitAtAlarm)).build();
    }

    public static Condition toTimeCondition(Context context, int n, int n2) {
        return ZenModeConfig.toTimeCondition(context, n, n2, false);
    }

    public static Condition toTimeCondition(Context context, int n, int n2, boolean bl) {
        long l = System.currentTimeMillis();
        long l2 = n == 0 ? 10000L : (long)(60000 * n);
        return ZenModeConfig.toTimeCondition(context, l + l2, n, n2, bl);
    }

    public static Condition toTimeCondition(Context object, long l, int n, int n2, boolean bl) {
        Object object2;
        Object object3;
        CharSequence charSequence = ZenModeConfig.getFormattedTime((Context)object, l, ZenModeConfig.isToday(l), n2);
        object = ((Context)object).getResources();
        if (n < 60) {
            n2 = bl ? 18153507 : 18153506;
            object2 = ((Resources)object).getQuantityString(n2, n, n, charSequence);
            n2 = bl ? 18153505 : 18153504;
            object3 = ((Resources)object).getQuantityString(n2, n, n, charSequence);
            object = ((Resources)object).getString(17041333, charSequence);
        } else if (n < 1440) {
            n2 = Math.round((float)n / 60.0f);
            n = bl ? 18153503 : 18153502;
            object2 = ((Resources)object).getQuantityString(n, n2, n2, charSequence);
            n = bl ? 18153501 : 18153500;
            object3 = ((Resources)object).getQuantityString(n, n2, n2, charSequence);
            object = ((Resources)object).getString(17041333, charSequence);
        } else {
            object2 = object = ((Resources)object).getString(17041333, charSequence);
            object3 = object;
        }
        return new Condition(ZenModeConfig.toCountdownConditionId(l, false), (String)object2, (String)object3, (String)object, 0, 1, 1);
    }

    public static long tryParseCountdownConditionId(Uri uri) {
        if (!Condition.isValidId(uri, SYSTEM_AUTHORITY)) {
            return 0L;
        }
        if (uri.getPathSegments().size() >= 2 && COUNTDOWN_PATH.equals(uri.getPathSegments().get(0))) {
            try {
                long l = Long.parseLong(uri.getPathSegments().get(1));
                return l;
            }
            catch (RuntimeException runtimeException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error parsing countdown condition: ");
                stringBuilder.append(uri);
                Slog.w(string2, stringBuilder.toString(), runtimeException);
                return 0L;
            }
        }
        return 0L;
    }

    private static int[] tryParseDayList(String arrstring, String arrn) {
        if (arrstring == null) {
            return null;
        }
        if ((arrstring = arrstring.split((String)arrn)).length == 0) {
            return null;
        }
        arrn = new int[arrstring.length];
        for (int i = 0; i < arrstring.length; ++i) {
            int n = ZenModeConfig.tryParseInt(arrstring[i], -1);
            if (n == -1) {
                return null;
            }
            arrn[i] = n;
        }
        return arrn;
    }

    public static EventInfo tryParseEventConditionId(Uri uri) {
        boolean bl = true;
        if (!(uri != null && "condition".equals(uri.getScheme()) && SYSTEM_AUTHORITY.equals(uri.getAuthority()) && uri.getPathSegments().size() == 1 && EVENT_PATH.equals(uri.getPathSegments().get(0)))) {
            bl = false;
        }
        if (!bl) {
            return null;
        }
        EventInfo eventInfo = new EventInfo();
        eventInfo.userId = ZenModeConfig.tryParseInt(uri.getQueryParameter("userId"), -10000);
        eventInfo.calName = uri.getQueryParameter("calendar");
        if (TextUtils.isEmpty(eventInfo.calName)) {
            eventInfo.calName = null;
        }
        eventInfo.calendarId = ZenModeConfig.tryParseLong(uri.getQueryParameter("calendarId"), null);
        eventInfo.reply = ZenModeConfig.tryParseInt(uri.getQueryParameter("reply"), 0);
        return eventInfo;
    }

    private static int[] tryParseHourAndMinute(String arrn) {
        boolean bl = TextUtils.isEmpty((CharSequence)arrn);
        Object var2_2 = null;
        if (bl) {
            return null;
        }
        int n = arrn.indexOf(46);
        if (n >= 1 && n < arrn.length() - 1) {
            int n2 = ZenModeConfig.tryParseInt(arrn.substring(0, n), -1);
            n = ZenModeConfig.tryParseInt(arrn.substring(n + 1), -1);
            arrn = var2_2;
            if (ZenModeConfig.isValidHour(n2)) {
                arrn = var2_2;
                if (ZenModeConfig.isValidMinute(n)) {
                    arrn = new int[]{n2, n};
                }
            }
            return arrn;
        }
        return null;
    }

    private static int tryParseInt(String string2, int n) {
        if (TextUtils.isEmpty(string2)) {
            return n;
        }
        try {
            int n2 = Integer.parseInt(string2);
            return n2;
        }
        catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    private static long tryParseLong(String string2, long l) {
        if (TextUtils.isEmpty(string2)) {
            return l;
        }
        try {
            long l2 = Long.parseLong(string2);
            return l2;
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
    }

    private static Long tryParseLong(String string2, Long l) {
        long l2;
        if (TextUtils.isEmpty(string2)) {
            return l;
        }
        try {
            l2 = Long.parseLong(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
        return l2;
    }

    @UnsupportedAppUsage
    public static ScheduleInfo tryParseScheduleConditionId(Uri uri) {
        boolean bl = uri != null && "condition".equals(uri.getScheme()) && SYSTEM_AUTHORITY.equals(uri.getAuthority()) && uri.getPathSegments().size() == 1 && SCHEDULE_PATH.equals(uri.getPathSegments().get(0));
        if (!bl) {
            return null;
        }
        int[] arrn = ZenModeConfig.tryParseHourAndMinute(uri.getQueryParameter("start"));
        int[] arrn2 = ZenModeConfig.tryParseHourAndMinute(uri.getQueryParameter("end"));
        if (arrn != null && arrn2 != null) {
            ScheduleInfo scheduleInfo = new ScheduleInfo();
            scheduleInfo.days = ZenModeConfig.tryParseDayList(uri.getQueryParameter("days"), "\\.");
            scheduleInfo.startHour = arrn[0];
            scheduleInfo.startMinute = arrn[1];
            scheduleInfo.endHour = arrn2[0];
            scheduleInfo.endMinute = arrn2[1];
            scheduleInfo.exitAtAlarm = ZenModeConfig.safeBoolean(uri.getQueryParameter("exitAtAlarm"), false);
            return scheduleInfo;
        }
        return null;
    }

    private static int tryParseZenMode(String string2, int n) {
        block0 : {
            int n2 = ZenModeConfig.tryParseInt(string2, n);
            if (!Settings.Global.isValidZenMode(n2)) break block0;
            n = n2;
        }
        return n;
    }

    private static Boolean unsafeBoolean(XmlPullParser object, String string2) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return null;
        }
        return Boolean.parseBoolean((String)object);
    }

    public static void writeConditionXml(Condition condition, XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.attribute(null, CONDITION_ATT_ID, condition.id.toString());
        xmlSerializer.attribute(null, CONDITION_ATT_SUMMARY, condition.summary);
        xmlSerializer.attribute(null, CONDITION_ATT_LINE1, condition.line1);
        xmlSerializer.attribute(null, CONDITION_ATT_LINE2, condition.line2);
        xmlSerializer.attribute(null, CONDITION_ATT_ICON, Integer.toString(condition.icon));
        xmlSerializer.attribute(null, "state", Integer.toString(condition.state));
        xmlSerializer.attribute(null, CONDITION_ATT_FLAGS, Integer.toString(condition.flags));
    }

    public static void writeRuleXml(ZenRule zenRule, XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.attribute(null, RULE_ATT_ENABLED, Boolean.toString(zenRule.enabled));
        if (zenRule.name != null) {
            xmlSerializer.attribute(null, RULE_ATT_NAME, zenRule.name);
        }
        xmlSerializer.attribute(null, "zen", Integer.toString(zenRule.zenMode));
        if (zenRule.component != null) {
            xmlSerializer.attribute(null, RULE_ATT_COMPONENT, zenRule.component.flattenToString());
        }
        if (zenRule.configurationActivity != null) {
            xmlSerializer.attribute(null, RULE_ATT_CONFIG_ACTIVITY, zenRule.configurationActivity.flattenToString());
        }
        if (zenRule.conditionId != null) {
            xmlSerializer.attribute(null, RULE_ATT_CONDITION_ID, zenRule.conditionId.toString());
        }
        xmlSerializer.attribute(null, RULE_ATT_CREATION_TIME, Long.toString(zenRule.creationTime));
        if (zenRule.enabler != null) {
            xmlSerializer.attribute(null, RULE_ATT_ENABLER, zenRule.enabler);
        }
        if (zenRule.condition != null) {
            ZenModeConfig.writeConditionXml(zenRule.condition, xmlSerializer);
        }
        if (zenRule.zenPolicy != null) {
            ZenModeConfig.writeZenPolicyXml(zenRule.zenPolicy, xmlSerializer);
        }
        xmlSerializer.attribute(null, RULE_ATT_MODIFIED, Boolean.toString(zenRule.modified));
    }

    private static void writeZenPolicyState(String string2, int n, XmlSerializer xmlSerializer) throws IOException {
        if (!Objects.equals(string2, ALLOW_ATT_CALLS_FROM) && !Objects.equals(string2, ALLOW_ATT_MESSAGES_FROM)) {
            if (n != 0) {
                xmlSerializer.attribute(null, string2, Integer.toString(n));
            }
        } else if (n != 0) {
            xmlSerializer.attribute(null, string2, Integer.toString(n));
        }
    }

    public static void writeZenPolicyXml(ZenPolicy zenPolicy, XmlSerializer xmlSerializer) throws IOException {
        ZenModeConfig.writeZenPolicyState(ALLOW_ATT_CALLS_FROM, zenPolicy.getPriorityCallSenders(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(ALLOW_ATT_MESSAGES_FROM, zenPolicy.getPriorityMessageSenders(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(ALLOW_ATT_REPEAT_CALLERS, zenPolicy.getPriorityCategoryRepeatCallers(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(ALLOW_ATT_ALARMS, zenPolicy.getPriorityCategoryAlarms(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(ALLOW_ATT_MEDIA, zenPolicy.getPriorityCategoryMedia(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(ALLOW_ATT_SYSTEM, zenPolicy.getPriorityCategorySystem(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(ALLOW_ATT_REMINDERS, zenPolicy.getPriorityCategoryReminders(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(ALLOW_ATT_EVENTS, zenPolicy.getPriorityCategoryEvents(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(SHOW_ATT_FULL_SCREEN_INTENT, zenPolicy.getVisualEffectFullScreenIntent(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(SHOW_ATT_LIGHTS, zenPolicy.getVisualEffectLights(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(SHOW_ATT_PEEK, zenPolicy.getVisualEffectPeek(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(SHOW_ATT_STATUS_BAR_ICONS, zenPolicy.getVisualEffectStatusBar(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(SHOW_ATT_BADGES, zenPolicy.getVisualEffectBadge(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(SHOW_ATT_AMBIENT, zenPolicy.getVisualEffectAmbient(), xmlSerializer);
        ZenModeConfig.writeZenPolicyState(SHOW_ATT_NOTIFICATION_LIST, zenPolicy.getVisualEffectNotificationList(), xmlSerializer);
    }

    public void applyNotificationPolicy(NotificationManager.Policy policy) {
        if (policy == null) {
            return;
        }
        int n = policy.priorityCategories;
        boolean bl = false;
        boolean bl2 = (n & 32) != 0;
        this.allowAlarms = bl2;
        bl2 = (policy.priorityCategories & 64) != 0;
        this.allowMedia = bl2;
        bl2 = (policy.priorityCategories & 128) != 0;
        this.allowSystem = bl2;
        bl2 = (policy.priorityCategories & 2) != 0;
        this.allowEvents = bl2;
        bl2 = (policy.priorityCategories & 1) != 0;
        this.allowReminders = bl2;
        bl2 = (policy.priorityCategories & 8) != 0;
        this.allowCalls = bl2;
        bl2 = (policy.priorityCategories & 4) != 0;
        this.allowMessages = bl2;
        bl2 = (policy.priorityCategories & 16) != 0;
        this.allowRepeatCallers = bl2;
        this.allowCallsFrom = ZenModeConfig.prioritySendersToSource(policy.priorityCallSenders, this.allowCallsFrom);
        this.allowMessagesFrom = ZenModeConfig.prioritySendersToSource(policy.priorityMessageSenders, this.allowMessagesFrom);
        if (policy.suppressedVisualEffects != -1) {
            this.suppressedVisualEffects = policy.suppressedVisualEffects;
        }
        if (policy.state != -1) {
            bl2 = bl;
            if ((policy.state & 1) != 0) {
                bl2 = true;
            }
            this.areChannelsBypassingDnd = bl2;
        }
    }

    public ZenModeConfig copy() {
        Parcel parcel = Parcel.obtain();
        try {
            this.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            ZenModeConfig zenModeConfig = new ZenModeConfig(parcel);
            return zenModeConfig;
        }
        finally {
            parcel.recycle();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Diff diff(ZenModeConfig zenModeConfig) {
        boolean bl;
        Diff diff = new Diff();
        if (zenModeConfig == null) {
            return diff.addLine("config", "delete");
        }
        int n = this.user;
        if (n != zenModeConfig.user) {
            diff.addLine(ZEN_ATT_USER, n, zenModeConfig.user);
        }
        if ((bl = this.allowAlarms) != zenModeConfig.allowAlarms) {
            diff.addLine("allowAlarms", bl, zenModeConfig.allowAlarms);
        }
        if ((bl = this.allowMedia) != zenModeConfig.allowMedia) {
            diff.addLine("allowMedia", bl, zenModeConfig.allowMedia);
        }
        if ((bl = this.allowSystem) != zenModeConfig.allowSystem) {
            diff.addLine("allowSystem", bl, zenModeConfig.allowSystem);
        }
        if ((bl = this.allowCalls) != zenModeConfig.allowCalls) {
            diff.addLine("allowCalls", bl, zenModeConfig.allowCalls);
        }
        if ((bl = this.allowReminders) != zenModeConfig.allowReminders) {
            diff.addLine("allowReminders", bl, zenModeConfig.allowReminders);
        }
        if ((bl = this.allowEvents) != zenModeConfig.allowEvents) {
            diff.addLine("allowEvents", bl, zenModeConfig.allowEvents);
        }
        if ((bl = this.allowRepeatCallers) != zenModeConfig.allowRepeatCallers) {
            diff.addLine("allowRepeatCallers", bl, zenModeConfig.allowRepeatCallers);
        }
        if ((bl = this.allowMessages) != zenModeConfig.allowMessages) {
            diff.addLine("allowMessages", bl, zenModeConfig.allowMessages);
        }
        if ((n = this.allowCallsFrom) != zenModeConfig.allowCallsFrom) {
            diff.addLine("allowCallsFrom", n, zenModeConfig.allowCallsFrom);
        }
        if ((n = this.allowMessagesFrom) != zenModeConfig.allowMessagesFrom) {
            diff.addLine("allowMessagesFrom", n, zenModeConfig.allowMessagesFrom);
        }
        if ((n = this.suppressedVisualEffects) != zenModeConfig.suppressedVisualEffects) {
            diff.addLine("suppressedVisualEffects", n, zenModeConfig.suppressedVisualEffects);
        }
        ArraySet arraySet = new ArraySet();
        ZenModeConfig.addKeys(arraySet, this.automaticRules);
        ZenModeConfig.addKeys(arraySet, zenModeConfig.automaticRules);
        int n2 = arraySet.size();
        for (n = 0; n < n2; ++n) {
            String string2 = (String)arraySet.valueAt(n);
            Object object = this.automaticRules;
            ZenRule zenRule = null;
            object = object != null ? ((ArrayMap)object).get(string2) : null;
            ArrayMap<String, ZenRule> arrayMap = zenModeConfig.automaticRules;
            if (arrayMap != null) {
                zenRule = arrayMap.get(string2);
            }
            arrayMap = new StringBuilder();
            ((StringBuilder)((Object)arrayMap)).append("automaticRule[");
            ((StringBuilder)((Object)arrayMap)).append(string2);
            ((StringBuilder)((Object)arrayMap)).append("]");
            ZenRule.appendDiff(diff, ((StringBuilder)((Object)arrayMap)).toString(), (ZenRule)object, zenRule);
        }
        ZenRule.appendDiff(diff, "manualRule", this.manualRule, zenModeConfig.manualRule);
        bl = this.areChannelsBypassingDnd;
        if (bl != zenModeConfig.areChannelsBypassingDnd) {
            diff.addLine(STATE_ATT_CHANNELS_BYPASSING_DND, bl, zenModeConfig.areChannelsBypassingDnd);
        }
        return diff;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ZenModeConfig)) {
            return false;
        }
        boolean bl = true;
        if (object == this) {
            return true;
        }
        object = (ZenModeConfig)object;
        if (((ZenModeConfig)object).allowAlarms != this.allowAlarms || ((ZenModeConfig)object).allowMedia != this.allowMedia || ((ZenModeConfig)object).allowSystem != this.allowSystem || ((ZenModeConfig)object).allowCalls != this.allowCalls || ((ZenModeConfig)object).allowRepeatCallers != this.allowRepeatCallers || ((ZenModeConfig)object).allowMessages != this.allowMessages || ((ZenModeConfig)object).allowCallsFrom != this.allowCallsFrom || ((ZenModeConfig)object).allowMessagesFrom != this.allowMessagesFrom || ((ZenModeConfig)object).allowReminders != this.allowReminders || ((ZenModeConfig)object).allowEvents != this.allowEvents || ((ZenModeConfig)object).user != this.user || !Objects.equals(((ZenModeConfig)object).automaticRules, this.automaticRules) || !Objects.equals(((ZenModeConfig)object).manualRule, this.manualRule) || ((ZenModeConfig)object).suppressedVisualEffects != this.suppressedVisualEffects || ((ZenModeConfig)object).areChannelsBypassingDnd != this.areChannelsBypassingDnd) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.allowAlarms, this.allowMedia, this.allowSystem, this.allowCalls, this.allowRepeatCallers, this.allowMessages, this.allowCallsFrom, this.allowMessagesFrom, this.allowReminders, this.allowEvents, this.user, this.automaticRules, this.manualRule, this.suppressedVisualEffects, this.areChannelsBypassingDnd);
    }

    public boolean isValid() {
        if (!ZenModeConfig.isValidManualRule(this.manualRule)) {
            return false;
        }
        int n = this.automaticRules.size();
        for (int i = 0; i < n; ++i) {
            if (ZenModeConfig.isValidAutomaticRule(this.automaticRules.valueAt(i))) continue;
            return false;
        }
        return true;
    }

    public NotificationManager.Policy toNotificationPolicy() {
        int n = 0;
        if (this.allowCalls) {
            n = 0 | 8;
        }
        int n2 = n;
        if (this.allowMessages) {
            n2 = n | 4;
        }
        n = n2;
        if (this.allowEvents) {
            n = n2 | 2;
        }
        n2 = n;
        if (this.allowReminders) {
            n2 = n | 1;
        }
        n = n2;
        if (this.allowRepeatCallers) {
            n = n2 | 16;
        }
        n2 = n;
        if (this.allowAlarms) {
            n2 = n | 32;
        }
        n = n2;
        if (this.allowMedia) {
            n = n2 | 64;
        }
        n2 = n;
        if (this.allowSystem) {
            n2 = n | 128;
        }
        int n3 = ZenModeConfig.sourceToPrioritySenders(this.allowCallsFrom, 1);
        int n4 = ZenModeConfig.sourceToPrioritySenders(this.allowMessagesFrom, 1);
        int n5 = this.suppressedVisualEffects;
        n = this.areChannelsBypassingDnd ? 1 : 0;
        return new NotificationManager.Policy(n2, n3, n4, n5, n);
    }

    public NotificationManager.Policy toNotificationPolicy(ZenPolicy zenPolicy) {
        NotificationManager.Policy policy = this.toNotificationPolicy();
        int n = 0;
        int n2 = 0;
        int n3 = policy.priorityCallSenders;
        int n4 = policy.priorityMessageSenders;
        if (zenPolicy.isCategoryAllowed(0, this.isPriorityCategoryEnabled(1, policy))) {
            n = false | true;
        }
        int n5 = n;
        if (zenPolicy.isCategoryAllowed(1, this.isPriorityCategoryEnabled(2, policy))) {
            n5 = n | 2;
        }
        n = n5;
        int n6 = n4;
        if (zenPolicy.isCategoryAllowed(2, this.isPriorityCategoryEnabled(4, policy))) {
            n = n5 | 4;
            n6 = this.getNotificationPolicySenders(zenPolicy.getPriorityMessageSenders(), n4);
        }
        n5 = n;
        n4 = n3;
        if (zenPolicy.isCategoryAllowed(3, this.isPriorityCategoryEnabled(8, policy))) {
            n5 = n | 8;
            n4 = this.getNotificationPolicySenders(zenPolicy.getPriorityCallSenders(), n3);
        }
        n = n5;
        if (zenPolicy.isCategoryAllowed(4, this.isPriorityCategoryEnabled(16, policy))) {
            n = n5 | 16;
        }
        n5 = n;
        if (zenPolicy.isCategoryAllowed(5, this.isPriorityCategoryEnabled(32, policy))) {
            n5 = n | 32;
        }
        n = n5;
        if (zenPolicy.isCategoryAllowed(6, this.isPriorityCategoryEnabled(64, policy))) {
            n = n5 | 64;
        }
        n3 = n;
        if (zenPolicy.isCategoryAllowed(7, this.isPriorityCategoryEnabled(128, policy))) {
            n3 = n | 128;
        }
        boolean bl = zenPolicy.isVisualEffectAllowed(0, this.isVisualEffectAllowed(4, policy)) ^ true;
        boolean bl2 = zenPolicy.isVisualEffectAllowed(1, this.isVisualEffectAllowed(8, policy)) ^ true;
        boolean bl3 = true ^ zenPolicy.isVisualEffectAllowed(5, this.isVisualEffectAllowed(128, policy));
        n = n2;
        if (bl) {
            n = n2;
            if (bl2) {
                n = n2;
                if (bl3) {
                    n = false | true;
                }
            }
        }
        n5 = n;
        if (bl) {
            n5 = n | 4;
        }
        n = n5;
        if (bl2) {
            n = n5 | 8;
        }
        n5 = n;
        if (!zenPolicy.isVisualEffectAllowed(2, this.isVisualEffectAllowed(16, policy))) {
            n5 = n | 16 | 2;
        }
        n = n5;
        if (!zenPolicy.isVisualEffectAllowed(3, this.isVisualEffectAllowed(32, policy))) {
            n = n5 | 32;
        }
        n5 = n;
        if (!zenPolicy.isVisualEffectAllowed(4, this.isVisualEffectAllowed(64, policy))) {
            n5 = n | 64;
        }
        n = n5;
        if (bl3) {
            n = n5 | 128;
        }
        n5 = n;
        if (!zenPolicy.isVisualEffectAllowed(6, this.isVisualEffectAllowed(256, policy))) {
            n5 = n | 256;
        }
        return new NotificationManager.Policy(n3, n4, n6, n5, policy.state);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(ZenModeConfig.class.getSimpleName());
        stringBuilder.append('[');
        stringBuilder.append("user=");
        stringBuilder.append(this.user);
        stringBuilder.append(",allowAlarms=");
        stringBuilder.append(this.allowAlarms);
        stringBuilder.append(",allowMedia=");
        stringBuilder.append(this.allowMedia);
        stringBuilder.append(",allowSystem=");
        stringBuilder.append(this.allowSystem);
        stringBuilder.append(",allowReminders=");
        stringBuilder.append(this.allowReminders);
        stringBuilder.append(",allowEvents=");
        stringBuilder.append(this.allowEvents);
        stringBuilder.append(",allowCalls=");
        stringBuilder.append(this.allowCalls);
        stringBuilder.append(",allowRepeatCallers=");
        stringBuilder.append(this.allowRepeatCallers);
        stringBuilder.append(",allowMessages=");
        stringBuilder.append(this.allowMessages);
        stringBuilder.append(",allowCallsFrom=");
        stringBuilder.append(ZenModeConfig.sourceToString(this.allowCallsFrom));
        stringBuilder.append(",allowMessagesFrom=");
        stringBuilder.append(ZenModeConfig.sourceToString(this.allowMessagesFrom));
        stringBuilder.append(",suppressedVisualEffects=");
        stringBuilder.append(this.suppressedVisualEffects);
        stringBuilder.append(",areChannelsBypassingDnd=");
        stringBuilder.append(this.areChannelsBypassingDnd);
        stringBuilder.append(",\nautomaticRules=");
        stringBuilder.append(this.rulesToString());
        stringBuilder.append(",\nmanualRule=");
        stringBuilder.append(this.manualRule);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.allowCalls);
        parcel.writeInt((int)this.allowRepeatCallers);
        parcel.writeInt((int)this.allowMessages);
        parcel.writeInt((int)this.allowReminders);
        parcel.writeInt((int)this.allowEvents);
        parcel.writeInt(this.allowCallsFrom);
        parcel.writeInt(this.allowMessagesFrom);
        parcel.writeInt(this.user);
        parcel.writeParcelable(this.manualRule, 0);
        if (!this.automaticRules.isEmpty()) {
            int n2 = this.automaticRules.size();
            String[] arrstring = new String[n2];
            Parcelable[] arrparcelable = new ZenRule[n2];
            for (n = 0; n < n2; ++n) {
                arrstring[n] = this.automaticRules.keyAt(n);
                arrparcelable[n] = this.automaticRules.valueAt(n);
            }
            parcel.writeInt(n2);
            parcel.writeStringArray(arrstring);
            parcel.writeTypedArray(arrparcelable, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt((int)this.allowAlarms);
        parcel.writeInt((int)this.allowMedia);
        parcel.writeInt((int)this.allowSystem);
        parcel.writeInt(this.suppressedVisualEffects);
        parcel.writeInt((int)this.areChannelsBypassingDnd);
    }

    public void writeXml(XmlSerializer xmlSerializer, Integer object) throws IOException {
        xmlSerializer.startTag(null, "zen");
        int n = object == null ? 8 : (Integer)object;
        xmlSerializer.attribute(null, ZEN_ATT_VERSION, Integer.toString(n));
        xmlSerializer.attribute(null, ZEN_ATT_USER, Integer.toString(this.user));
        xmlSerializer.startTag(null, ALLOW_TAG);
        xmlSerializer.attribute(null, ALLOW_ATT_CALLS, Boolean.toString(this.allowCalls));
        xmlSerializer.attribute(null, ALLOW_ATT_REPEAT_CALLERS, Boolean.toString(this.allowRepeatCallers));
        xmlSerializer.attribute(null, ALLOW_ATT_MESSAGES, Boolean.toString(this.allowMessages));
        xmlSerializer.attribute(null, ALLOW_ATT_REMINDERS, Boolean.toString(this.allowReminders));
        xmlSerializer.attribute(null, ALLOW_ATT_EVENTS, Boolean.toString(this.allowEvents));
        xmlSerializer.attribute(null, ALLOW_ATT_CALLS_FROM, Integer.toString(this.allowCallsFrom));
        xmlSerializer.attribute(null, ALLOW_ATT_MESSAGES_FROM, Integer.toString(this.allowMessagesFrom));
        xmlSerializer.attribute(null, ALLOW_ATT_ALARMS, Boolean.toString(this.allowAlarms));
        xmlSerializer.attribute(null, ALLOW_ATT_MEDIA, Boolean.toString(this.allowMedia));
        xmlSerializer.attribute(null, ALLOW_ATT_SYSTEM, Boolean.toString(this.allowSystem));
        xmlSerializer.endTag(null, ALLOW_TAG);
        xmlSerializer.startTag(null, DISALLOW_TAG);
        xmlSerializer.attribute(null, DISALLOW_ATT_VISUAL_EFFECTS, Integer.toString(this.suppressedVisualEffects));
        xmlSerializer.endTag(null, DISALLOW_TAG);
        if (this.manualRule != null) {
            xmlSerializer.startTag(null, MANUAL_TAG);
            ZenModeConfig.writeRuleXml(this.manualRule, xmlSerializer);
            xmlSerializer.endTag(null, MANUAL_TAG);
        }
        int n2 = this.automaticRules.size();
        for (n = 0; n < n2; ++n) {
            object = this.automaticRules.keyAt(n);
            ZenRule zenRule = this.automaticRules.valueAt(n);
            xmlSerializer.startTag(null, AUTOMATIC_TAG);
            xmlSerializer.attribute(null, RULE_ATT_ID, (String)object);
            ZenModeConfig.writeRuleXml(zenRule, xmlSerializer);
            xmlSerializer.endTag(null, AUTOMATIC_TAG);
        }
        xmlSerializer.startTag(null, "state");
        xmlSerializer.attribute(null, STATE_ATT_CHANNELS_BYPASSING_DND, Boolean.toString(this.areChannelsBypassingDnd));
        xmlSerializer.endTag(null, "state");
        xmlSerializer.endTag(null, "zen");
    }

    public static class Diff {
        private final ArrayList<String> lines = new ArrayList();

        private Diff addLine(String string2, String string3) {
            ArrayList<String> arrayList = this.lines;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(":");
            stringBuilder.append(string3);
            arrayList.add(stringBuilder.toString());
            return this;
        }

        public Diff addLine(String string2, Object object, Object object2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object);
            stringBuilder.append("->");
            stringBuilder.append(object2);
            return this.addLine(string2, stringBuilder.toString());
        }

        public Diff addLine(String string2, String string3, Object object, Object object2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(".");
            stringBuilder.append(string3);
            return this.addLine(stringBuilder.toString(), object, object2);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Diff[");
            int n = this.lines.size();
            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    stringBuilder.append(",\n");
                }
                stringBuilder.append(this.lines.get(i));
            }
            stringBuilder.append(']');
            return stringBuilder.toString();
        }
    }

    public static class EventInfo {
        public static final int REPLY_ANY_EXCEPT_NO = 0;
        public static final int REPLY_YES = 2;
        public static final int REPLY_YES_OR_MAYBE = 1;
        public String calName;
        public Long calendarId;
        public int reply;
        public int userId = -10000;

        public static int resolveUserId(int n) {
            block0 : {
                if (n != -10000) break block0;
                n = ActivityManager.getCurrentUser();
            }
            return n;
        }

        public EventInfo copy() {
            EventInfo eventInfo = new EventInfo();
            eventInfo.userId = this.userId;
            eventInfo.calName = this.calName;
            eventInfo.reply = this.reply;
            eventInfo.calendarId = this.calendarId;
            return eventInfo;
        }

        public boolean equals(Object object) {
            boolean bl;
            block1 : {
                boolean bl2 = object instanceof EventInfo;
                bl = false;
                if (!bl2) {
                    return false;
                }
                object = (EventInfo)object;
                if (this.userId != ((EventInfo)object).userId || !Objects.equals(this.calName, ((EventInfo)object).calName) || this.reply != ((EventInfo)object).reply || !Objects.equals(this.calendarId, ((EventInfo)object).calendarId)) break block1;
                bl = true;
            }
            return bl;
        }

        public int hashCode() {
            return Objects.hash(this.userId, this.calName, this.calendarId, this.reply);
        }
    }

    public static class ScheduleInfo {
        @UnsupportedAppUsage
        public int[] days;
        @UnsupportedAppUsage
        public int endHour;
        @UnsupportedAppUsage
        public int endMinute;
        public boolean exitAtAlarm;
        public long nextAlarm;
        @UnsupportedAppUsage
        public int startHour;
        @UnsupportedAppUsage
        public int startMinute;

        protected static String ts(long l) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(new Date(l));
            stringBuilder.append(" (");
            stringBuilder.append(l);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        public ScheduleInfo copy() {
            ScheduleInfo scheduleInfo = new ScheduleInfo();
            int[] arrn = this.days;
            if (arrn != null) {
                scheduleInfo.days = new int[arrn.length];
                arrn = this.days;
                System.arraycopy(arrn, 0, scheduleInfo.days, 0, arrn.length);
            }
            scheduleInfo.startHour = this.startHour;
            scheduleInfo.startMinute = this.startMinute;
            scheduleInfo.endHour = this.endHour;
            scheduleInfo.endMinute = this.endMinute;
            scheduleInfo.exitAtAlarm = this.exitAtAlarm;
            scheduleInfo.nextAlarm = this.nextAlarm;
            return scheduleInfo;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof ScheduleInfo;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (ScheduleInfo)object;
            bl = bl2;
            if (ZenModeConfig.toDayList(this.days).equals(ZenModeConfig.toDayList(((ScheduleInfo)object).days))) {
                bl = bl2;
                if (this.startHour == ((ScheduleInfo)object).startHour) {
                    bl = bl2;
                    if (this.startMinute == ((ScheduleInfo)object).startMinute) {
                        bl = bl2;
                        if (this.endHour == ((ScheduleInfo)object).endHour) {
                            bl = bl2;
                            if (this.endMinute == ((ScheduleInfo)object).endMinute) {
                                bl = bl2;
                                if (this.exitAtAlarm == ((ScheduleInfo)object).exitAtAlarm) {
                                    bl = true;
                                }
                            }
                        }
                    }
                }
            }
            return bl;
        }

        public int hashCode() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ScheduleInfo{days=");
            stringBuilder.append(Arrays.toString(this.days));
            stringBuilder.append(", startHour=");
            stringBuilder.append(this.startHour);
            stringBuilder.append(", startMinute=");
            stringBuilder.append(this.startMinute);
            stringBuilder.append(", endHour=");
            stringBuilder.append(this.endHour);
            stringBuilder.append(", endMinute=");
            stringBuilder.append(this.endMinute);
            stringBuilder.append(", exitAtAlarm=");
            stringBuilder.append(this.exitAtAlarm);
            stringBuilder.append(", nextAlarm=");
            stringBuilder.append(ScheduleInfo.ts(this.nextAlarm));
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static class ZenRule
    implements Parcelable {
        public static final Parcelable.Creator<ZenRule> CREATOR = new Parcelable.Creator<ZenRule>(){

            @Override
            public ZenRule createFromParcel(Parcel parcel) {
                return new ZenRule(parcel);
            }

            public ZenRule[] newArray(int n) {
                return new ZenRule[n];
            }
        };
        public ComponentName component;
        public Condition condition;
        @UnsupportedAppUsage
        public Uri conditionId;
        public ComponentName configurationActivity;
        @UnsupportedAppUsage
        public long creationTime;
        @UnsupportedAppUsage
        public boolean enabled;
        public String enabler;
        public String id;
        public boolean modified;
        @UnsupportedAppUsage
        public String name;
        public String pkg;
        @UnsupportedAppUsage
        public boolean snoozing;
        @UnsupportedAppUsage
        public int zenMode;
        public ZenPolicy zenPolicy;

        public ZenRule() {
        }

        public ZenRule(Parcel parcel) {
            int n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n == 1;
            this.enabled = bl2;
            bl2 = parcel.readInt() == 1;
            this.snoozing = bl2;
            if (parcel.readInt() == 1) {
                this.name = parcel.readString();
            }
            this.zenMode = parcel.readInt();
            this.conditionId = (Uri)parcel.readParcelable(null);
            this.condition = (Condition)parcel.readParcelable(null);
            this.component = (ComponentName)parcel.readParcelable(null);
            this.configurationActivity = (ComponentName)parcel.readParcelable(null);
            if (parcel.readInt() == 1) {
                this.id = parcel.readString();
            }
            this.creationTime = parcel.readLong();
            if (parcel.readInt() == 1) {
                this.enabler = parcel.readString();
            }
            this.zenPolicy = (ZenPolicy)parcel.readParcelable(null);
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.modified = bl2;
            this.pkg = parcel.readString();
        }

        private void appendDiff(Diff diff, String string2, ZenRule object) {
            int n;
            long l;
            String string3;
            if (object == null) {
                diff.addLine(string2, "delete");
                return;
            }
            boolean bl = this.enabled;
            if (bl != ((ZenRule)object).enabled) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_ENABLED, bl, ((ZenRule)object).enabled);
            }
            if ((bl = this.snoozing) != ((ZenRule)object).snoozing) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_SNOOZING, bl, ((ZenRule)object).snoozing);
            }
            if (!Objects.equals(this.name, ((ZenRule)object).name)) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_NAME, this.name, ((ZenRule)object).name);
            }
            if ((n = this.zenMode) != ((ZenRule)object).zenMode) {
                diff.addLine(string2, "zenMode", n, ((ZenRule)object).zenMode);
            }
            if (!Objects.equals(this.conditionId, ((ZenRule)object).conditionId)) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_CONDITION_ID, this.conditionId, ((ZenRule)object).conditionId);
            }
            if (!Objects.equals(this.condition, ((ZenRule)object).condition)) {
                diff.addLine(string2, "condition", this.condition, ((ZenRule)object).condition);
            }
            if (!Objects.equals(this.component, ((ZenRule)object).component)) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_COMPONENT, this.component, ((ZenRule)object).component);
            }
            if (!Objects.equals(this.configurationActivity, ((ZenRule)object).configurationActivity)) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_CONFIG_ACTIVITY, this.configurationActivity, ((ZenRule)object).configurationActivity);
            }
            if (!Objects.equals(this.id, ((ZenRule)object).id)) {
                diff.addLine(string2, ZenModeConfig.CONDITION_ATT_ID, this.id, ((ZenRule)object).id);
            }
            if ((l = this.creationTime) != ((ZenRule)object).creationTime) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_CREATION_TIME, l, ((ZenRule)object).creationTime);
            }
            if (!Objects.equals(this.enabler, ((ZenRule)object).enabler)) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_ENABLER, this.enabler, ((ZenRule)object).enabler);
            }
            if (!Objects.equals(this.zenPolicy, ((ZenRule)object).zenPolicy)) {
                diff.addLine(string2, "zenPolicy", this.zenPolicy, ((ZenRule)object).zenPolicy);
            }
            if ((bl = this.modified) != ((ZenRule)object).modified) {
                diff.addLine(string2, ZenModeConfig.RULE_ATT_MODIFIED, bl, ((ZenRule)object).modified);
            }
            if ((string3 = this.pkg) != (object = ((ZenRule)object).pkg)) {
                diff.addLine(string2, "pkg", string3, object);
            }
        }

        private static void appendDiff(Diff diff, String string2, ZenRule zenRule, ZenRule zenRule2) {
            if (diff == null) {
                return;
            }
            if (zenRule == null) {
                if (zenRule2 != null) {
                    diff.addLine(string2, "insert");
                }
                return;
            }
            zenRule.appendDiff(diff, string2, zenRule2);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (!(object instanceof ZenRule)) {
                return false;
            }
            boolean bl = true;
            if (object == this) {
                return true;
            }
            object = (ZenRule)object;
            if (!(((ZenRule)object).enabled == this.enabled && ((ZenRule)object).snoozing == this.snoozing && Objects.equals(((ZenRule)object).name, this.name) && ((ZenRule)object).zenMode == this.zenMode && Objects.equals(((ZenRule)object).conditionId, this.conditionId) && Objects.equals(((ZenRule)object).condition, this.condition) && Objects.equals(((ZenRule)object).component, this.component) && Objects.equals(((ZenRule)object).configurationActivity, this.configurationActivity) && Objects.equals(((ZenRule)object).id, this.id) && Objects.equals(((ZenRule)object).enabler, this.enabler) && Objects.equals(((ZenRule)object).zenPolicy, this.zenPolicy) && Objects.equals(((ZenRule)object).pkg, this.pkg) && ((ZenRule)object).modified == this.modified)) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return Objects.hash(this.enabled, this.snoozing, this.name, this.zenMode, this.conditionId, this.condition, this.component, this.configurationActivity, this.pkg, this.id, this.enabler, this.zenPolicy, this.modified);
        }

        public boolean isAutomaticActive() {
            boolean bl = this.enabled && !this.snoozing && this.pkg != null && this.isTrueOrUnknown();
            return bl;
        }

        public boolean isTrueOrUnknown() {
            Condition condition = this.condition;
            boolean bl = true;
            if (condition == null || condition.state != 1 && this.condition.state != 2) {
                bl = false;
            }
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(ZenRule.class.getSimpleName());
            stringBuilder.append('[');
            stringBuilder.append("id=");
            stringBuilder.append(this.id);
            stringBuilder.append(",enabled=");
            stringBuilder.append(String.valueOf(this.enabled).toUpperCase());
            stringBuilder.append(",snoozing=");
            stringBuilder.append(this.snoozing);
            stringBuilder.append(",name=");
            stringBuilder.append(this.name);
            stringBuilder.append(",zenMode=");
            stringBuilder.append(Settings.Global.zenModeToString(this.zenMode));
            stringBuilder.append(",conditionId=");
            stringBuilder.append(this.conditionId);
            stringBuilder.append(",condition=");
            stringBuilder.append(this.condition);
            stringBuilder.append(",pkg=");
            stringBuilder.append(this.pkg);
            stringBuilder.append(",component=");
            stringBuilder.append(this.component);
            stringBuilder.append(",configActivity=");
            stringBuilder.append(this.configurationActivity);
            stringBuilder.append(",creationTime=");
            stringBuilder.append(this.creationTime);
            stringBuilder.append(",enabler=");
            stringBuilder.append(this.enabler);
            stringBuilder.append(",zenPolicy=");
            stringBuilder.append(this.zenPolicy);
            stringBuilder.append(",modified=");
            stringBuilder.append(this.modified);
            stringBuilder.append(']');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt((int)this.enabled);
            parcel.writeInt((int)this.snoozing);
            if (this.name != null) {
                parcel.writeInt(1);
                parcel.writeString(this.name);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeInt(this.zenMode);
            parcel.writeParcelable(this.conditionId, 0);
            parcel.writeParcelable(this.condition, 0);
            parcel.writeParcelable(this.component, 0);
            parcel.writeParcelable(this.configurationActivity, 0);
            if (this.id != null) {
                parcel.writeInt(1);
                parcel.writeString(this.id);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeLong(this.creationTime);
            if (this.enabler != null) {
                parcel.writeInt(1);
                parcel.writeString(this.enabler);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeParcelable(this.zenPolicy, 0);
            parcel.writeInt((int)this.modified);
            parcel.writeString(this.pkg);
        }

        public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
            l = protoOutputStream.start(l);
            protoOutputStream.write(1138166333441L, this.id);
            protoOutputStream.write(1138166333442L, this.name);
            protoOutputStream.write(1112396529667L, this.creationTime);
            protoOutputStream.write(1133871366148L, this.enabled);
            protoOutputStream.write(1138166333445L, this.enabler);
            protoOutputStream.write(1133871366150L, this.snoozing);
            protoOutputStream.write(1159641169927L, this.zenMode);
            Parcelable parcelable = this.conditionId;
            if (parcelable != null) {
                protoOutputStream.write(1138166333448L, ((Uri)parcelable).toString());
            }
            if ((parcelable = this.condition) != null) {
                ((Condition)parcelable).writeToProto(protoOutputStream, 1146756268041L);
            }
            if ((parcelable = this.component) != null) {
                ((ComponentName)parcelable).writeToProto(protoOutputStream, 1146756268042L);
            }
            if ((parcelable = this.zenPolicy) != null) {
                ((ZenPolicy)parcelable).writeToProto(protoOutputStream, 1146756268043L);
            }
            protoOutputStream.write(1133871366156L, this.modified);
            protoOutputStream.end(l);
        }

    }

}

