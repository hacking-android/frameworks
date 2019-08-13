/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Person;
import android.app.RemoteInput;
import android.app._$$Lambda$Notification$hOCsSZH8tWalFSbIzQ9x9IcPa9M;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.media.PlayerBase;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.BidiFormatter;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.widget.RemoteViews;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ContrastColorUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class Notification
implements Parcelable {
    public static final AudioAttributes AUDIO_ATTRIBUTES_DEFAULT;
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    @SystemApi
    public static final String CATEGORY_CAR_EMERGENCY = "car_emergency";
    @SystemApi
    public static final String CATEGORY_CAR_INFORMATION = "car_information";
    @SystemApi
    public static final String CATEGORY_CAR_WARNING = "car_warning";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_NAVIGATION = "navigation";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    public static final int COLOR_DEFAULT = 0;
    public static final int COLOR_INVALID = 1;
    public static final Parcelable.Creator<Notification> CREATOR;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    @SystemApi
    public static final String EXTRA_ALLOW_DURING_SETUP = "android.allowDuringSetup";
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_BUILDER_APPLICATION_INFO = "android.appInfo";
    public static final String EXTRA_CHANNEL_GROUP_ID = "android.intent.extra.CHANNEL_GROUP_ID";
    public static final String EXTRA_CHANNEL_ID = "android.intent.extra.CHANNEL_ID";
    public static final String EXTRA_CHRONOMETER_COUNT_DOWN = "android.chronometerCountDown";
    public static final String EXTRA_COLORIZED = "android.colorized";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONTAINS_CUSTOM_VIEW = "android.contains.customView";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_FOREGROUND_APPS = "android.foregroundApps";
    public static final String EXTRA_HIDE_SMART_REPLIES = "android.hideSmartReplies";
    public static final String EXTRA_HISTORIC_MESSAGES = "android.messages.historic";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_IS_GROUP_CONVERSATION = "android.isGroupConversation";
    @Deprecated
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_MESSAGING_PERSON = "android.messagingUser";
    public static final String EXTRA_NOTIFICATION_ID = "android.intent.extra.NOTIFICATION_ID";
    public static final String EXTRA_NOTIFICATION_TAG = "android.intent.extra.NOTIFICATION_TAG";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PEOPLE_LIST = "android.people.list";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REDUCED_IMAGES = "android.reduced.images";
    public static final String EXTRA_REMOTE_INPUT_DRAFT = "android.remoteInputDraft";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_REMOTE_INPUT_SPINNER = "android.remoteInputSpinner";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    @Deprecated
    public static final String EXTRA_SMALL_ICON = "android.icon";
    @SystemApi
    public static final String EXTRA_SUBSTITUTE_APP_NAME = "android.substName";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    @SystemApi
    public static final int FLAG_AUTOGROUP_SUMMARY = 1024;
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_BUBBLE = 4096;
    public static final int FLAG_CAN_COLORIZE = 2048;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    @Deprecated
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";
    public static final int MAX_ACTION_BUTTONS = 3;
    private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
    private static final int MAX_REPLY_HISTORY = 5;
    @Deprecated
    public static final int PRIORITY_DEFAULT = 0;
    @Deprecated
    public static final int PRIORITY_HIGH = 1;
    @Deprecated
    public static final int PRIORITY_LOW = -1;
    @Deprecated
    public static final int PRIORITY_MAX = 2;
    @Deprecated
    public static final int PRIORITY_MIN = -2;
    private static final ArraySet<Integer> STANDARD_LAYOUTS;
    @Deprecated
    public static final int STREAM_DEFAULT = -1;
    private static final String TAG = "Notification";
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;
    public static IBinder processWhitelistToken;
    public Action[] actions;
    @UnsupportedAppUsage
    public ArraySet<PendingIntent> allPendingIntents;
    @Deprecated
    public AudioAttributes audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
    @Deprecated
    public int audioStreamType = -1;
    @Deprecated
    public RemoteViews bigContentView;
    public String category;
    public int color = 0;
    public PendingIntent contentIntent;
    @Deprecated
    public RemoteViews contentView;
    private long creationTime;
    @Deprecated
    public int defaults;
    public PendingIntent deleteIntent;
    public Bundle extras = new Bundle();
    public int flags;
    public PendingIntent fullScreenIntent;
    @Deprecated
    public RemoteViews headsUpContentView;
    @Deprecated
    public int icon;
    public int iconLevel;
    @Deprecated
    public Bitmap largeIcon;
    @Deprecated
    public int ledARGB;
    @Deprecated
    public int ledOffMS;
    @Deprecated
    public int ledOnMS;
    private boolean mAllowSystemGeneratedContextualActions = true;
    private int mBadgeIcon = 0;
    private BubbleMetadata mBubbleMetadata;
    @UnsupportedAppUsage
    private String mChannelId;
    private int mGroupAlertBehavior = 0;
    @UnsupportedAppUsage
    private String mGroupKey;
    @UnsupportedAppUsage
    private Icon mLargeIcon;
    private LocusId mLocusId;
    private CharSequence mSettingsText;
    private String mShortcutId;
    @UnsupportedAppUsage
    private Icon mSmallIcon;
    private String mSortKey;
    private long mTimeout;
    private boolean mUsesStandardHeader;
    private IBinder mWhitelistToken;
    public int number = 0;
    @Deprecated
    public int priority;
    public Notification publicVersion;
    @Deprecated
    public Uri sound;
    public CharSequence tickerText;
    @Deprecated
    public RemoteViews tickerView;
    @Deprecated
    public long[] vibrate;
    public int visibility;
    public long when;

    static {
        STANDARD_LAYOUTS = new ArraySet();
        STANDARD_LAYOUTS.add(17367198);
        STANDARD_LAYOUTS.add(17367199);
        STANDARD_LAYOUTS.add(17367201);
        STANDARD_LAYOUTS.add(17367202);
        STANDARD_LAYOUTS.add(17367203);
        STANDARD_LAYOUTS.add(17367205);
        STANDARD_LAYOUTS.add(17367204);
        STANDARD_LAYOUTS.add(17367200);
        STANDARD_LAYOUTS.add(17367197);
        AUDIO_ATTRIBUTES_DEFAULT = new AudioAttributes.Builder().setContentType(4).setUsage(5).build();
        CREATOR = new Parcelable.Creator<Notification>(){

            @Override
            public Notification createFromParcel(Parcel parcel) {
                return new Notification(parcel);
            }

            public Notification[] newArray(int n) {
                return new Notification[n];
            }
        };
    }

    public Notification() {
        this.when = System.currentTimeMillis();
        this.creationTime = System.currentTimeMillis();
        this.priority = 0;
    }

    @Deprecated
    public Notification(int n, CharSequence charSequence, long l) {
        this.icon = n;
        this.tickerText = charSequence;
        this.when = l;
        this.creationTime = System.currentTimeMillis();
    }

    @UnsupportedAppUsage
    public Notification(Context context, int n, CharSequence charSequence, long l, CharSequence charSequence2, CharSequence charSequence3, Intent intent) {
        new Builder(context).setWhen(l).setSmallIcon(n).setTicker(charSequence).setContentTitle(charSequence2).setContentText(charSequence3).setContentIntent(PendingIntent.getActivity(context, 0, intent, 0)).buildInto(this);
    }

    public Notification(Parcel parcel) {
        this.readFromParcelImpl(parcel);
        this.allPendingIntents = parcel.readArraySet(null);
    }

    public static void addFieldsFromContext(Context context, Notification notification) {
        Notification.addFieldsFromContext(context.getApplicationInfo(), notification);
    }

    public static void addFieldsFromContext(ApplicationInfo applicationInfo, Notification notification) {
        notification.extras.putParcelable(EXTRA_BUILDER_APPLICATION_INFO, applicationInfo);
    }

    public static boolean areActionsVisiblyDifferent(Notification arrremoteInput, Notification arrremoteInput2) {
        Action[] arraction = arrremoteInput.actions;
        Action[] arraction2 = arrremoteInput2.actions;
        if (arraction == null && arraction2 != null || arraction != null && arraction2 == null) {
            return true;
        }
        if (arraction != null && arraction2 != null) {
            if (arraction.length != arraction2.length) {
                return true;
            }
            for (int i = 0; i < arraction.length; ++i) {
                if (!Objects.equals(String.valueOf(arraction[i].title), String.valueOf(arraction2[i].title))) {
                    return true;
                }
                arrremoteInput2 = arraction[i].getRemoteInputs();
                RemoteInput[] arrremoteInput3 = arraction2[i].getRemoteInputs();
                arrremoteInput = arrremoteInput2;
                if (arrremoteInput2 == null) {
                    arrremoteInput = new RemoteInput[]{};
                }
                arrremoteInput2 = arrremoteInput3;
                if (arrremoteInput3 == null) {
                    arrremoteInput2 = new RemoteInput[]{};
                }
                if (arrremoteInput.length != arrremoteInput2.length) {
                    return true;
                }
                for (int j = 0; j < arrremoteInput.length; ++j) {
                    if (Objects.equals(String.valueOf(arrremoteInput[j].getLabel()), String.valueOf(arrremoteInput2[j].getLabel()))) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean areRemoteViewsChanged(Builder builder, Builder builder2) {
        if (!Objects.equals(builder.usesStandardHeader(), builder2.usesStandardHeader())) {
            return true;
        }
        if (Notification.areRemoteViewsChanged(Builder.access$300((Builder)builder).contentView, Builder.access$300((Builder)builder2).contentView)) {
            return true;
        }
        if (Notification.areRemoteViewsChanged(Builder.access$300((Builder)builder).bigContentView, Builder.access$300((Builder)builder2).bigContentView)) {
            return true;
        }
        return Notification.areRemoteViewsChanged(Builder.access$300((Builder)builder).headsUpContentView, Builder.access$300((Builder)builder2).headsUpContentView);
    }

    private static boolean areRemoteViewsChanged(RemoteViews remoteViews, RemoteViews remoteViews2) {
        if (remoteViews == null && remoteViews2 == null) {
            return false;
        }
        if (remoteViews == null && remoteViews2 != null || remoteViews != null && remoteViews2 == null) {
            return true;
        }
        if (!Objects.equals(remoteViews.getLayoutId(), remoteViews2.getLayoutId())) {
            return true;
        }
        return !Objects.equals(remoteViews.getSequenceNumber(), remoteViews2.getSequenceNumber());
    }

    public static boolean areStyledNotificationsVisiblyDifferent(Builder builder, Builder builder2) {
        Style style2 = builder.getStyle();
        boolean bl = true;
        if (style2 == null) {
            if (builder2.getStyle() == null) {
                bl = false;
            }
            return bl;
        }
        if (builder2.getStyle() == null) {
            return true;
        }
        return builder.getStyle().areNotificationsVisiblyDifferent(builder2.getStyle());
    }

    private void fixDuplicateExtra(Parcelable parcelable, String string2) {
        if (parcelable != null && this.extras.getParcelable(string2) != null) {
            this.extras.putParcelable(string2, parcelable);
        }
    }

    private void fixDuplicateExtras() {
        if (this.extras != null) {
            this.fixDuplicateExtra(this.mLargeIcon, EXTRA_LARGE_ICON);
        }
    }

    private static Notification[] getNotificationArrayFromBundle(Bundle bundle, String string2) {
        Parcelable[] arrparcelable = bundle.getParcelableArray(string2);
        if (!(arrparcelable instanceof Notification[]) && arrparcelable != null) {
            arrparcelable = (Notification[])Arrays.copyOf(arrparcelable, arrparcelable.length, Notification[].class);
            bundle.putParcelableArray(string2, arrparcelable);
            return arrparcelable;
        }
        return (Notification[])arrparcelable;
    }

    @SystemApi
    public static Class<? extends Style> getNotificationStyleClass(String string2) {
        Class[] arrclass = new Class[7];
        arrclass[0] = BigTextStyle.class;
        arrclass[1] = BigPictureStyle.class;
        arrclass[2] = InboxStyle.class;
        arrclass[3] = MediaStyle.class;
        arrclass[4] = DecoratedCustomViewStyle.class;
        arrclass[5] = DecoratedMediaCustomViewStyle.class;
        arrclass[6] = MessagingStyle.class;
        for (Class class_ : arrclass) {
            if (!string2.equals(class_.getName())) continue;
            return class_;
        }
        return null;
    }

    private boolean hasColorizedPermission() {
        boolean bl = (this.flags & 2048) != 0;
        return bl;
    }

    private boolean hasLargeIcon() {
        boolean bl = this.mLargeIcon != null || this.largeIcon != null;
        return bl;
    }

    public static String priorityToString(int n) {
        if (n != -2) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("UNKNOWN(");
                            stringBuilder.append(String.valueOf(n));
                            stringBuilder.append(")");
                            return stringBuilder.toString();
                        }
                        return "MAX";
                    }
                    return "HIGH";
                }
                return "DEFAULT";
            }
            return "LOW";
        }
        return "MIN";
    }

    private void readFromParcelImpl(Parcel parcel) {
        parcel.readInt();
        this.mWhitelistToken = parcel.readStrongBinder();
        if (this.mWhitelistToken == null) {
            this.mWhitelistToken = processWhitelistToken;
        }
        parcel.setClassCookie(PendingIntent.class, this.mWhitelistToken);
        this.when = parcel.readLong();
        this.creationTime = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mSmallIcon = Icon.CREATOR.createFromParcel(parcel);
            if (this.mSmallIcon.getType() == 2) {
                this.icon = this.mSmallIcon.getResId();
            }
        }
        this.number = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.contentIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.deleteIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.tickerText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.tickerView = RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.contentView = RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mLargeIcon = Icon.CREATOR.createFromParcel(parcel);
        }
        this.defaults = parcel.readInt();
        this.flags = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.sound = Uri.CREATOR.createFromParcel(parcel);
        }
        this.audioStreamType = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.audioAttributes = AudioAttributes.CREATOR.createFromParcel(parcel);
        }
        this.vibrate = parcel.createLongArray();
        this.ledARGB = parcel.readInt();
        this.ledOnMS = parcel.readInt();
        this.ledOffMS = parcel.readInt();
        this.iconLevel = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.fullScreenIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        }
        this.priority = parcel.readInt();
        this.category = parcel.readString();
        this.mGroupKey = parcel.readString();
        this.mSortKey = parcel.readString();
        this.extras = Bundle.setDefusable(parcel.readBundle(), true);
        this.fixDuplicateExtras();
        this.actions = parcel.createTypedArray(Action.CREATOR);
        if (parcel.readInt() != 0) {
            this.bigContentView = RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.headsUpContentView = RemoteViews.CREATOR.createFromParcel(parcel);
        }
        this.visibility = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.publicVersion = CREATOR.createFromParcel(parcel);
        }
        this.color = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mChannelId = parcel.readString();
        }
        this.mTimeout = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mShortcutId = parcel.readString();
        }
        if (parcel.readInt() != 0) {
            this.mLocusId = LocusId.CREATOR.createFromParcel(parcel);
        }
        this.mBadgeIcon = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mSettingsText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        this.mGroupAlertBehavior = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mBubbleMetadata = BubbleMetadata.CREATOR.createFromParcel(parcel);
        }
        this.mAllowSystemGeneratedContextualActions = parcel.readBoolean();
    }

    private void reduceImageSizesForRemoteView(RemoteViews remoteViews, Context object, boolean bl) {
        if (remoteViews != null) {
            object = ((Context)object).getResources();
            int n = bl ? 17105313 : 17105312;
            int n2 = ((Resources)object).getDimensionPixelSize(n);
            n = bl ? 17105311 : 17105310;
            remoteViews.reduceImageSizes(n2, ((Resources)object).getDimensionPixelSize(n));
        }
    }

    private static CharSequence removeTextSizeSpans(CharSequence object) {
        if (object instanceof Spanned) {
            Spanned spanned = (Spanned)object;
            int n = spanned.length();
            Object[] arrobject = spanned.getSpans(0, n, Object.class);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned.toString());
            n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                Object object2;
                Object object3 = object2 = (object = arrobject[i]);
                if (object2 instanceof CharacterStyle) {
                    object3 = ((CharacterStyle)object).getUnderlying();
                }
                if (object3 instanceof TextAppearanceSpan) {
                    object2 = (TextAppearanceSpan)object3;
                    object2 = new TextAppearanceSpan(((TextAppearanceSpan)object2).getFamily(), ((TextAppearanceSpan)object2).getTextStyle(), -1, ((TextAppearanceSpan)object2).getTextColor(), ((TextAppearanceSpan)object2).getLinkTextColor());
                } else {
                    if (object3 instanceof RelativeSizeSpan || object3 instanceof AbsoluteSizeSpan) continue;
                    object2 = object;
                }
                spannableStringBuilder.setSpan(object2, spanned.getSpanStart(object), spanned.getSpanEnd(object), spanned.getSpanFlags(object));
            }
            return spannableStringBuilder;
        }
        return object;
    }

    public static CharSequence safeCharSequence(CharSequence charSequence) {
        if (charSequence == null) {
            return charSequence;
        }
        CharSequence charSequence2 = charSequence;
        if (charSequence.length() > 5120) {
            charSequence2 = charSequence.subSequence(0, 5120);
        }
        if (charSequence2 instanceof Parcelable) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("warning: ");
            ((StringBuilder)charSequence).append(charSequence2.getClass().getCanonicalName());
            ((StringBuilder)charSequence).append(" instance is a custom Parcelable and not allowed in Notification");
            Log.e(TAG, ((StringBuilder)charSequence).toString());
            return charSequence2.toString();
        }
        return Notification.removeTextSizeSpans(charSequence2);
    }

    public static String visibilityToString(int n) {
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UNKNOWN(");
                    stringBuilder.append(String.valueOf(n));
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
                return "PUBLIC";
            }
            return "PRIVATE";
        }
        return "SECRET";
    }

    private void writeToParcelImpl(Parcel parcel, int n) {
        int n2;
        Bitmap bitmap;
        parcel.writeInt(1);
        parcel.writeStrongBinder(this.mWhitelistToken);
        parcel.writeLong(this.when);
        parcel.writeLong(this.creationTime);
        if (this.mSmallIcon == null && (n2 = this.icon) != 0) {
            this.mSmallIcon = Icon.createWithResource("", n2);
        }
        if (this.mSmallIcon != null) {
            parcel.writeInt(1);
            this.mSmallIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.number);
        if (this.contentIntent != null) {
            parcel.writeInt(1);
            this.contentIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.deleteIntent != null) {
            parcel.writeInt(1);
            this.deleteIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.tickerText != null) {
            parcel.writeInt(1);
            TextUtils.writeToParcel(this.tickerText, parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.tickerView != null) {
            parcel.writeInt(1);
            this.tickerView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.contentView != null) {
            parcel.writeInt(1);
            this.contentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mLargeIcon == null && (bitmap = this.largeIcon) != null) {
            this.mLargeIcon = Icon.createWithBitmap(bitmap);
        }
        if (this.mLargeIcon != null) {
            parcel.writeInt(1);
            this.mLargeIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.defaults);
        parcel.writeInt(this.flags);
        if (this.sound != null) {
            parcel.writeInt(1);
            this.sound.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.audioStreamType);
        if (this.audioAttributes != null) {
            parcel.writeInt(1);
            this.audioAttributes.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLongArray(this.vibrate);
        parcel.writeInt(this.ledARGB);
        parcel.writeInt(this.ledOnMS);
        parcel.writeInt(this.ledOffMS);
        parcel.writeInt(this.iconLevel);
        if (this.fullScreenIntent != null) {
            parcel.writeInt(1);
            this.fullScreenIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.priority);
        parcel.writeString(this.category);
        parcel.writeString(this.mGroupKey);
        parcel.writeString(this.mSortKey);
        parcel.writeBundle(this.extras);
        parcel.writeTypedArray((Parcelable[])this.actions, 0);
        if (this.bigContentView != null) {
            parcel.writeInt(1);
            this.bigContentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.headsUpContentView != null) {
            parcel.writeInt(1);
            this.headsUpContentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.visibility);
        if (this.publicVersion != null) {
            parcel.writeInt(1);
            this.publicVersion.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.color);
        if (this.mChannelId != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mChannelId);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mTimeout);
        if (this.mShortcutId != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mShortcutId);
        } else {
            parcel.writeInt(0);
        }
        if (this.mLocusId != null) {
            parcel.writeInt(1);
            this.mLocusId.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mBadgeIcon);
        if (this.mSettingsText != null) {
            parcel.writeInt(1);
            TextUtils.writeToParcel(this.mSettingsText, parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mGroupAlertBehavior);
        if (this.mBubbleMetadata != null) {
            parcel.writeInt(1);
            this.mBubbleMetadata.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.mAllowSystemGeneratedContextualActions);
    }

    public Notification clone() {
        Notification notification = new Notification();
        this.cloneInto(notification, true);
        return notification;
    }

    public void cloneInto(Notification notification, boolean bl) {
        int n;
        Object object;
        notification.mWhitelistToken = this.mWhitelistToken;
        notification.when = this.when;
        notification.creationTime = this.creationTime;
        notification.mSmallIcon = this.mSmallIcon;
        notification.number = this.number;
        notification.contentIntent = this.contentIntent;
        notification.deleteIntent = this.deleteIntent;
        notification.fullScreenIntent = this.fullScreenIntent;
        Object object2 = this.tickerText;
        if (object2 != null) {
            notification.tickerText = object2.toString();
        }
        if (bl && (object2 = this.tickerView) != null) {
            notification.tickerView = ((RemoteViews)object2).clone();
        }
        if (bl && (object2 = this.contentView) != null) {
            notification.contentView = ((RemoteViews)object2).clone();
        }
        if (bl && (object2 = this.mLargeIcon) != null) {
            notification.mLargeIcon = object2;
        }
        notification.iconLevel = this.iconLevel;
        notification.sound = this.sound;
        notification.audioStreamType = this.audioStreamType;
        object2 = this.audioAttributes;
        if (object2 != null) {
            notification.audioAttributes = new AudioAttributes.Builder((AudioAttributes)object2).build();
        }
        if ((object2 = this.vibrate) != null) {
            n = ((long[])object2).length;
            object = new long[n];
            notification.vibrate = object;
            System.arraycopy(object2, 0, object, 0, n);
        }
        notification.ledARGB = this.ledARGB;
        notification.ledOnMS = this.ledOnMS;
        notification.ledOffMS = this.ledOffMS;
        notification.defaults = this.defaults;
        notification.flags = this.flags;
        notification.priority = this.priority;
        notification.category = this.category;
        notification.mGroupKey = this.mGroupKey;
        notification.mSortKey = this.mSortKey;
        object = this.extras;
        if (object != null) {
            try {
                object2 = new Bundle((Bundle)object);
                notification.extras = object2;
                notification.extras.size();
            }
            catch (BadParcelableException badParcelableException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("could not unparcel extras from notification: ");
                ((StringBuilder)object).append(this);
                Log.e(TAG, ((StringBuilder)object).toString(), badParcelableException);
                notification.extras = null;
            }
        }
        if (!ArrayUtils.isEmpty(this.allPendingIntents)) {
            notification.allPendingIntents = new ArraySet<PendingIntent>(this.allPendingIntents);
        }
        if ((object2 = this.actions) != null) {
            notification.actions = new Action[((long[])object2).length];
            for (n = 0; n < ((Object)(object2 = this.actions)).length; ++n) {
                if (object2[n] == null) continue;
                notification.actions[n] = ((Action)object2[n]).clone();
            }
        }
        if (bl && (object2 = this.bigContentView) != null) {
            notification.bigContentView = ((RemoteViews)object2).clone();
        }
        if (bl && (object2 = this.headsUpContentView) != null) {
            notification.headsUpContentView = ((RemoteViews)object2).clone();
        }
        notification.visibility = this.visibility;
        if (this.publicVersion != null) {
            notification.publicVersion = new Notification();
            this.publicVersion.cloneInto(notification.publicVersion, bl);
        }
        notification.color = this.color;
        notification.mChannelId = this.mChannelId;
        notification.mTimeout = this.mTimeout;
        notification.mShortcutId = this.mShortcutId;
        notification.mLocusId = this.mLocusId;
        notification.mBadgeIcon = this.mBadgeIcon;
        notification.mSettingsText = this.mSettingsText;
        notification.mGroupAlertBehavior = this.mGroupAlertBehavior;
        notification.mBubbleMetadata = this.mBubbleMetadata;
        notification.mAllowSystemGeneratedContextualActions = this.mAllowSystemGeneratedContextualActions;
        if (!bl) {
            notification.lightenPayload();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Pair<RemoteInput, Action> findRemoteInputActionPair(boolean bl) {
        Action[] arraction = this.actions;
        if (arraction == null) {
            return null;
        }
        for (Action action : arraction) {
            if (action.getRemoteInputs() == null) continue;
            RemoteInput[] arrremoteInput = action.getRemoteInputs();
            int n = arrremoteInput.length;
            RemoteInput remoteInput = null;
            for (int i = 0; i < n; ++i) {
                RemoteInput remoteInput2 = arrremoteInput[i];
                if (!remoteInput2.getAllowFreeFormInput() && bl) continue;
                remoteInput = remoteInput2;
            }
            if (remoteInput == null) continue;
            return Pair.create(remoteInput, action);
        }
        return null;
    }

    public boolean getAllowSystemGeneratedContextualActions() {
        return this.mAllowSystemGeneratedContextualActions;
    }

    public int getBadgeIconType() {
        return this.mBadgeIcon;
    }

    public BubbleMetadata getBubbleMetadata() {
        return this.mBubbleMetadata;
    }

    @Deprecated
    public String getChannel() {
        return this.mChannelId;
    }

    public String getChannelId() {
        return this.mChannelId;
    }

    public List<Action> getContextualActions() {
        if (this.actions == null) {
            return Collections.emptyList();
        }
        ArrayList<Action> arrayList = new ArrayList<Action>();
        for (Action action : this.actions) {
            if (!action.isContextual()) continue;
            arrayList.add(action);
        }
        return arrayList;
    }

    public String getGroup() {
        return this.mGroupKey;
    }

    public int getGroupAlertBehavior() {
        return this.mGroupAlertBehavior;
    }

    public Icon getLargeIcon() {
        return this.mLargeIcon;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public Class<? extends Style> getNotificationStyle() {
        String string2 = this.extras.getString(EXTRA_TEMPLATE);
        if (!TextUtils.isEmpty(string2)) {
            return Notification.getNotificationStyleClass(string2);
        }
        return null;
    }

    public CharSequence getSettingsText() {
        return this.mSettingsText;
    }

    public String getShortcutId() {
        return this.mShortcutId;
    }

    public Icon getSmallIcon() {
        return this.mSmallIcon;
    }

    public String getSortKey() {
        return this.mSortKey;
    }

    @Deprecated
    public long getTimeout() {
        return this.mTimeout;
    }

    public long getTimeoutAfter() {
        return this.mTimeout;
    }

    public boolean hasCompletedProgress() {
        boolean bl = this.extras.containsKey(EXTRA_PROGRESS);
        boolean bl2 = false;
        if (bl && this.extras.containsKey(EXTRA_PROGRESS_MAX)) {
            if (this.extras.getInt(EXTRA_PROGRESS_MAX) == 0) {
                return false;
            }
            if (this.extras.getInt(EXTRA_PROGRESS) == this.extras.getInt(EXTRA_PROGRESS_MAX)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public boolean hasMediaSession() {
        boolean bl = this.extras.getParcelable(EXTRA_MEDIA_SESSION) != null;
        return bl;
    }

    public boolean isBubbleNotification() {
        boolean bl = (this.flags & 4096) != 0;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isColorized() {
        boolean bl = this.isColorizedMedia();
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (!this.extras.getBoolean(EXTRA_COLORIZED)) return false;
        bl = bl2;
        if (this.hasColorizedPermission()) return bl;
        if (!this.isForegroundService()) return false;
        return bl2;
    }

    public boolean isColorizedMedia() {
        Serializable serializable = this.getNotificationStyle();
        return MediaStyle.class.equals(serializable) ? ((serializable = (Boolean)this.extras.get(EXTRA_COLORIZED)) == null || ((Boolean)serializable).booleanValue()) && this.hasMediaSession() : DecoratedMediaCustomViewStyle.class.equals(serializable) && this.extras.getBoolean(EXTRA_COLORIZED) && this.hasMediaSession();
    }

    public boolean isForegroundService() {
        boolean bl = (this.flags & 64) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isGroupChild() {
        boolean bl = this.mGroupKey != null && (this.flags & 512) == 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isGroupSummary() {
        boolean bl = this.mGroupKey != null && (this.flags & 512) != 0;
        return bl;
    }

    public boolean isMediaNotification() {
        Class<? extends Style> class_ = this.getNotificationStyle();
        if (MediaStyle.class.equals(class_)) {
            return true;
        }
        return DecoratedMediaCustomViewStyle.class.equals(class_);
    }

    public /* synthetic */ void lambda$writeToParcel$0$Notification(Parcel parcel, PendingIntent pendingIntent, Parcel parcel2, int n) {
        if (parcel == parcel2) {
            if (this.allPendingIntents == null) {
                this.allPendingIntents = new ArraySet();
            }
            this.allPendingIntents.add(pendingIntent);
        }
    }

    public final void lightenPayload() {
        this.tickerView = null;
        this.contentView = null;
        this.bigContentView = null;
        this.headsUpContentView = null;
        this.mLargeIcon = null;
        Object object = this.extras;
        if (object != null && !((BaseBundle)object).isEmpty()) {
            object = this.extras.keySet();
            int n = object.size();
            String[] arrstring = object.toArray(new String[n]);
            for (int i = 0; i < n; ++i) {
                String string2 = arrstring[i];
                if ("android.tv.EXTENSIONS".equals(string2) || (object = this.extras.get(string2)) == null || !(object instanceof Parcelable) && !(object instanceof Parcelable[]) && !(object instanceof SparseArray) && !(object instanceof ArrayList)) continue;
                this.extras.remove(string2);
            }
        }
    }

    void reduceImageSizes(Context context) {
        if (this.extras.getBoolean(EXTRA_REDUCED_IMAGES)) {
            return;
        }
        boolean bl = ActivityManager.isLowRamDeviceStatic();
        if (this.mLargeIcon != null || this.largeIcon != null) {
            Resources resources = context.getResources();
            Object object = this.getNotificationStyle();
            int n = bl ? 17105341 : 17105340;
            int n2 = n = resources.getDimensionPixelSize(n);
            if (MediaStyle.class.equals(object) || DecoratedMediaCustomViewStyle.class.equals(object)) {
                n = bl ? 17105331 : 17105330;
                n2 = resources.getDimensionPixelSize(n);
                n = bl ? 17105333 : 17105332;
                n = resources.getDimensionPixelSize(n);
            }
            if ((object = this.mLargeIcon) != null) {
                ((Icon)object).scaleDownIfNecessary(n, n2);
            }
            if ((object = this.largeIcon) != null) {
                this.largeIcon = Icon.scaleDownIfNecessary((Bitmap)object, n, n2);
            }
        }
        this.reduceImageSizesForRemoteView(this.contentView, context, bl);
        this.reduceImageSizesForRemoteView(this.headsUpContentView, context, bl);
        this.reduceImageSizesForRemoteView(this.bigContentView, context, bl);
        this.extras.putBoolean(EXTRA_REDUCED_IMAGES, true);
    }

    @Deprecated
    public void setLatestEventInfo(Context object, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent) {
        if (object.getApplicationInfo().targetSdkVersion > 22) {
            Log.e(TAG, "setLatestEventInfo() is deprecated and you should feel deprecated.", new Throwable());
        }
        if (object.getApplicationInfo().targetSdkVersion < 24) {
            this.extras.putBoolean(EXTRA_SHOW_WHEN, true);
        }
        object = new Builder((Context)object, this);
        if (charSequence != null) {
            ((Builder)object).setContentTitle(charSequence);
        }
        if (charSequence2 != null) {
            ((Builder)object).setContentText(charSequence2);
        }
        ((Builder)object).setContentIntent(pendingIntent);
        ((Builder)object).build();
    }

    @UnsupportedAppUsage
    public void setSmallIcon(Icon icon) {
        this.mSmallIcon = icon;
    }

    public boolean showsChronometer() {
        boolean bl = this.when != 0L && this.extras.getBoolean(EXTRA_SHOW_CHRONOMETER);
        return bl;
    }

    public boolean showsTime() {
        boolean bl = this.when != 0L && this.extras.getBoolean(EXTRA_SHOW_WHEN);
        return bl;
    }

    public boolean suppressAlertingDueToGrouping() {
        if (this.isGroupSummary() && this.getGroupAlertBehavior() == 2) {
            return true;
        }
        return this.isGroupChild() && this.getGroupAlertBehavior() == 1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Notification(channel=");
        stringBuilder.append(this.getChannelId());
        stringBuilder.append(" pri=");
        stringBuilder.append(this.priority);
        stringBuilder.append(" contentView=");
        Object object = this.contentView;
        if (object != null) {
            stringBuilder.append(((RemoteViews)object).getPackage());
            stringBuilder.append("/0x");
            stringBuilder.append(Integer.toHexString(this.contentView.getLayoutId()));
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(" vibrate=");
        if ((this.defaults & 2) != 0) {
            stringBuilder.append("default");
        } else {
            object = this.vibrate;
            if (object != null) {
                int n = ((long[])object).length - 1;
                stringBuilder.append("[");
                for (int i = 0; i < n; ++i) {
                    stringBuilder.append(this.vibrate[i]);
                    stringBuilder.append(',');
                }
                if (n != -1) {
                    stringBuilder.append(this.vibrate[n]);
                }
                stringBuilder.append("]");
            } else {
                stringBuilder.append("null");
            }
        }
        stringBuilder.append(" sound=");
        if ((this.defaults & 1) != 0) {
            stringBuilder.append("default");
        } else {
            object = this.sound;
            if (object != null) {
                stringBuilder.append(((Uri)object).toString());
            } else {
                stringBuilder.append("null");
            }
        }
        if (this.tickerText != null) {
            stringBuilder.append(" tick");
        }
        stringBuilder.append(" defaults=0x");
        stringBuilder.append(Integer.toHexString(this.defaults));
        stringBuilder.append(" flags=0x");
        stringBuilder.append(Integer.toHexString(this.flags));
        stringBuilder.append(String.format(" color=0x%08x", this.color));
        if (this.category != null) {
            stringBuilder.append(" category=");
            stringBuilder.append(this.category);
        }
        if (this.mGroupKey != null) {
            stringBuilder.append(" groupKey=");
            stringBuilder.append(this.mGroupKey);
        }
        if (this.mSortKey != null) {
            stringBuilder.append(" sortKey=");
            stringBuilder.append(this.mSortKey);
        }
        if (this.actions != null) {
            stringBuilder.append(" actions=");
            stringBuilder.append(this.actions.length);
        }
        stringBuilder.append(" vis=");
        stringBuilder.append(Notification.visibilityToString(this.visibility));
        if (this.publicVersion != null) {
            stringBuilder.append(" publicVersion=");
            stringBuilder.append(this.publicVersion.toString());
        }
        if (this.mLocusId != null) {
            stringBuilder.append(" locusId=");
            stringBuilder.append(this.mLocusId);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void visitUris(Consumer<Uri> consumer) {
        consumer.accept(this.sound);
        Object object = this.tickerView;
        if (object != null) {
            ((RemoteViews)object).visitUris(consumer);
        }
        if ((object = this.contentView) != null) {
            ((RemoteViews)object).visitUris(consumer);
        }
        if ((object = this.bigContentView) != null) {
            ((RemoteViews)object).visitUris(consumer);
        }
        if ((object = this.headsUpContentView) != null) {
            ((RemoteViews)object).visitUris(consumer);
        }
        if ((object = this.extras) != null) {
            consumer.accept((Uri)((Bundle)object).getParcelable(EXTRA_AUDIO_CONTENTS_URI));
            if (this.extras.containsKey(EXTRA_BACKGROUND_IMAGE_URI)) {
                consumer.accept(Uri.parse(this.extras.getString(EXTRA_BACKGROUND_IMAGE_URI)));
            }
        }
        if (MessagingStyle.class.equals(this.getNotificationStyle()) && (object = this.extras) != null) {
            if (!ArrayUtils.isEmpty(object = ((Bundle)object).getParcelableArray(EXTRA_MESSAGES))) {
                object = MessagingStyle.Message.getMessagesFromBundleArray((Parcelable[])object).iterator();
                while (object.hasNext()) {
                    consumer.accept(((MessagingStyle.Message)object.next()).getDataUri());
                }
            }
            if (!ArrayUtils.isEmpty(object = this.extras.getParcelableArray(EXTRA_HISTORIC_MESSAGES))) {
                object = MessagingStyle.Message.getMessagesFromBundleArray((Parcelable[])object).iterator();
                while (object.hasNext()) {
                    consumer.accept(((MessagingStyle.Message)object.next()).getDataUri());
                }
            }
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        boolean bl = this.allPendingIntents == null;
        if (bl) {
            PendingIntent.setOnMarshaledListener(new _$$Lambda$Notification$hOCsSZH8tWalFSbIzQ9x9IcPa9M(this, parcel));
        }
        try {
            this.writeToParcelImpl(parcel, n);
            parcel.writeArraySet(this.allPendingIntents);
            return;
        }
        finally {
            if (bl) {
                PendingIntent.setOnMarshaledListener(null);
            }
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        int n;
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.getChannelId());
        boolean bl = this.tickerText != null;
        protoOutputStream.write(1133871366146L, bl);
        protoOutputStream.write(1120986464259L, this.flags);
        protoOutputStream.write(1120986464260L, this.color);
        protoOutputStream.write(1138166333445L, this.category);
        protoOutputStream.write(1138166333446L, this.mGroupKey);
        protoOutputStream.write(1138166333447L, this.mSortKey);
        Object object = this.actions;
        if (object != null) {
            protoOutputStream.write(1120986464264L, ((Action[])object).length);
        }
        if ((n = this.visibility) >= -1 && n <= 1) {
            protoOutputStream.write(1159641169929L, n);
        }
        if ((object = this.publicVersion) != null) {
            ((Notification)object).writeToProto(protoOutputStream, 1146756268042L);
        }
        protoOutputStream.end(l);
    }

    public static class Action
    implements Parcelable {
        public static final Parcelable.Creator<Action> CREATOR = new Parcelable.Creator<Action>(){

            @Override
            public Action createFromParcel(Parcel parcel) {
                return new Action(parcel);
            }

            public Action[] newArray(int n) {
                return new Action[n];
            }
        };
        private static final String EXTRA_DATA_ONLY_INPUTS = "android.extra.DATA_ONLY_INPUTS";
        public static final int SEMANTIC_ACTION_ARCHIVE = 5;
        public static final int SEMANTIC_ACTION_CALL = 10;
        public static final int SEMANTIC_ACTION_DELETE = 4;
        public static final int SEMANTIC_ACTION_MARK_AS_READ = 2;
        public static final int SEMANTIC_ACTION_MARK_AS_UNREAD = 3;
        public static final int SEMANTIC_ACTION_MUTE = 6;
        public static final int SEMANTIC_ACTION_NONE = 0;
        public static final int SEMANTIC_ACTION_REPLY = 1;
        public static final int SEMANTIC_ACTION_THUMBS_DOWN = 9;
        public static final int SEMANTIC_ACTION_THUMBS_UP = 8;
        public static final int SEMANTIC_ACTION_UNMUTE = 7;
        public PendingIntent actionIntent;
        @Deprecated
        public int icon;
        private boolean mAllowGeneratedReplies;
        private final Bundle mExtras;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        private Icon mIcon;
        private final boolean mIsContextual;
        private final RemoteInput[] mRemoteInputs;
        private final int mSemanticAction;
        public CharSequence title;

        @Deprecated
        public Action(int n, CharSequence charSequence, PendingIntent pendingIntent) {
            this(Icon.createWithResource("", n), charSequence, pendingIntent, new Bundle(), null, true, 0, false);
        }

        private Action(Icon icon, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, boolean bl, int n, boolean bl2) {
            this.mAllowGeneratedReplies = true;
            this.mIcon = icon;
            if (icon != null && icon.getType() == 2) {
                this.icon = icon.getResId();
            }
            this.title = charSequence;
            this.actionIntent = pendingIntent;
            if (bundle == null) {
                bundle = new Bundle();
            }
            this.mExtras = bundle;
            this.mRemoteInputs = arrremoteInput;
            this.mAllowGeneratedReplies = bl;
            this.mSemanticAction = n;
            this.mIsContextual = bl2;
        }

        private Action(Parcel parcel) {
            boolean bl = true;
            this.mAllowGeneratedReplies = true;
            if (parcel.readInt() != 0) {
                this.mIcon = Icon.CREATOR.createFromParcel(parcel);
                if (this.mIcon.getType() == 2) {
                    this.icon = this.mIcon.getResId();
                }
            }
            this.title = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            if (parcel.readInt() == 1) {
                this.actionIntent = PendingIntent.CREATOR.createFromParcel(parcel);
            }
            this.mExtras = Bundle.setDefusable(parcel.readBundle(), true);
            this.mRemoteInputs = parcel.createTypedArray(RemoteInput.CREATOR);
            boolean bl2 = parcel.readInt() == 1;
            this.mAllowGeneratedReplies = bl2;
            this.mSemanticAction = parcel.readInt();
            bl2 = parcel.readInt() == 1 ? bl : false;
            this.mIsContextual = bl2;
        }

        public Action clone() {
            Icon icon = this.getIcon();
            CharSequence charSequence = this.title;
            PendingIntent pendingIntent = this.actionIntent;
            Bundle bundle = this.mExtras;
            bundle = bundle == null ? new Bundle() : new Bundle(bundle);
            return new Action(icon, charSequence, pendingIntent, bundle, this.getRemoteInputs(), this.getAllowGeneratedReplies(), this.getSemanticAction(), this.isContextual());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public RemoteInput[] getDataOnlyRemoteInputs() {
            return (RemoteInput[])this.mExtras.getParcelableArray(EXTRA_DATA_ONLY_INPUTS);
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public Icon getIcon() {
            int n;
            if (this.mIcon == null && (n = this.icon) != 0) {
                this.mIcon = Icon.createWithResource("", n);
            }
            return this.mIcon;
        }

        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public int getSemanticAction() {
            return this.mSemanticAction;
        }

        public boolean isContextual() {
            return this.mIsContextual;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            Icon icon = this.getIcon();
            if (icon != null) {
                parcel.writeInt(1);
                icon.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            TextUtils.writeToParcel(this.title, parcel, n);
            if (this.actionIntent != null) {
                parcel.writeInt(1);
                this.actionIntent.writeToParcel(parcel, n);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBundle(this.mExtras);
            parcel.writeTypedArray((Parcelable[])this.mRemoteInputs, n);
            parcel.writeInt((int)this.mAllowGeneratedReplies);
            parcel.writeInt(this.mSemanticAction);
            parcel.writeInt((int)this.mIsContextual);
        }

        public static final class Builder {
            private boolean mAllowGeneratedReplies = true;
            private final Bundle mExtras;
            private final Icon mIcon;
            private final PendingIntent mIntent;
            private boolean mIsContextual;
            private ArrayList<RemoteInput> mRemoteInputs;
            private int mSemanticAction;
            private final CharSequence mTitle;

            @Deprecated
            public Builder(int n, CharSequence charSequence, PendingIntent pendingIntent) {
                this(Icon.createWithResource("", n), charSequence, pendingIntent);
            }

            public Builder(Action action) {
                this(action.getIcon(), action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies(), action.getSemanticAction());
            }

            public Builder(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
                this(icon, charSequence, pendingIntent, new Bundle(), null, true, 0);
            }

            private Builder(Icon icon, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, boolean bl, int n) {
                this.mIcon = icon;
                this.mTitle = charSequence;
                this.mIntent = pendingIntent;
                this.mExtras = bundle;
                if (arrremoteInput != null) {
                    this.mRemoteInputs = new ArrayList(arrremoteInput.length);
                    Collections.addAll(this.mRemoteInputs, arrremoteInput);
                }
                this.mAllowGeneratedReplies = bl;
                this.mSemanticAction = n;
            }

            private void checkContextualActionNullFields() {
                if (!this.mIsContextual) {
                    return;
                }
                if (this.mIcon != null) {
                    if (this.mIntent != null) {
                        return;
                    }
                    throw new NullPointerException("Contextual Actions must contain a valid PendingIntent");
                }
                throw new NullPointerException("Contextual Actions must contain a valid icon");
            }

            public Builder addExtras(Bundle bundle) {
                if (bundle != null) {
                    this.mExtras.putAll(bundle);
                }
                return this;
            }

            public Builder addRemoteInput(RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList();
                }
                this.mRemoteInputs.add(remoteInput);
                return this;
            }

            public Action build() {
                this.checkContextualActionNullFields();
                Parcelable[] arrparcelable = new ArrayList();
                Object object = (RemoteInput[])this.mExtras.getParcelableArray(Action.EXTRA_DATA_ONLY_INPUTS);
                if (object != null) {
                    int n = ((RemoteInput[])object).length;
                    for (int i = 0; i < n; ++i) {
                        arrparcelable.add(object[i]);
                    }
                }
                object = new ArrayList();
                ArrayList<RemoteInput> arrayList = this.mRemoteInputs;
                if (arrayList != null) {
                    for (RemoteInput remoteInput : arrayList) {
                        if (remoteInput.isDataOnly()) {
                            arrparcelable.add(remoteInput);
                            continue;
                        }
                        object.add(remoteInput);
                    }
                }
                if (!arrparcelable.isEmpty()) {
                    arrparcelable = arrparcelable.toArray(new RemoteInput[arrparcelable.size()]);
                    this.mExtras.putParcelableArray(Action.EXTRA_DATA_ONLY_INPUTS, arrparcelable);
                }
                object = object.isEmpty() ? null : object.toArray(new RemoteInput[object.size()]);
                return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, (RemoteInput[])object, this.mAllowGeneratedReplies, this.mSemanticAction, this.mIsContextual);
            }

            public Builder extend(Extender extender) {
                extender.extend(this);
                return this;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public Builder setAllowGeneratedReplies(boolean bl) {
                this.mAllowGeneratedReplies = bl;
                return this;
            }

            public Builder setContextual(boolean bl) {
                this.mIsContextual = bl;
                return this;
            }

            public Builder setSemanticAction(int n) {
                this.mSemanticAction = n;
                return this;
            }
        }

        public static interface Extender {
            public Builder extend(Builder var1);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface SemanticAction {
        }

        public static final class WearableExtender
        implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags = 1;
            private CharSequence mInProgressLabel;

            public WearableExtender() {
            }

            public WearableExtender(Action parcelable) {
                parcelable = ((Action)parcelable).getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (parcelable != null) {
                    this.mFlags = ((BaseBundle)((Object)parcelable)).getInt(KEY_FLAGS, 1);
                    this.mInProgressLabel = ((Bundle)parcelable).getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = ((Bundle)parcelable).getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = ((Bundle)parcelable).getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            private void setFlag(int n, boolean bl) {
                this.mFlags = bl ? (this.mFlags |= n) : (this.mFlags &= n);
            }

            public WearableExtender clone() {
                WearableExtender wearableExtender = new WearableExtender();
                wearableExtender.mFlags = this.mFlags;
                wearableExtender.mInProgressLabel = this.mInProgressLabel;
                wearableExtender.mConfirmLabel = this.mConfirmLabel;
                wearableExtender.mCancelLabel = this.mCancelLabel;
                return wearableExtender;
            }

            @Override
            public Builder extend(Builder builder) {
                CharSequence charSequence;
                Bundle bundle = new Bundle();
                int n = this.mFlags;
                if (n != 1) {
                    bundle.putInt(KEY_FLAGS, n);
                }
                if ((charSequence = this.mInProgressLabel) != null) {
                    bundle.putCharSequence(KEY_IN_PROGRESS_LABEL, charSequence);
                }
                if ((charSequence = this.mConfirmLabel) != null) {
                    bundle.putCharSequence(KEY_CONFIRM_LABEL, charSequence);
                }
                if ((charSequence = this.mCancelLabel) != null) {
                    bundle.putCharSequence(KEY_CANCEL_LABEL, charSequence);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
                return builder;
            }

            @Deprecated
            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            @Deprecated
            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            public boolean getHintDisplayActionInline() {
                boolean bl = (this.mFlags & 4) != 0;
                return bl;
            }

            public boolean getHintLaunchesActivity() {
                boolean bl = (this.mFlags & 2) != 0;
                return bl;
            }

            @Deprecated
            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            public boolean isAvailableOffline() {
                int n = this.mFlags;
                boolean bl = true;
                if ((n & 1) == 0) {
                    bl = false;
                }
                return bl;
            }

            public WearableExtender setAvailableOffline(boolean bl) {
                this.setFlag(1, bl);
                return this;
            }

            @Deprecated
            public WearableExtender setCancelLabel(CharSequence charSequence) {
                this.mCancelLabel = charSequence;
                return this;
            }

            @Deprecated
            public WearableExtender setConfirmLabel(CharSequence charSequence) {
                this.mConfirmLabel = charSequence;
                return this;
            }

            public WearableExtender setHintDisplayActionInline(boolean bl) {
                this.setFlag(4, bl);
                return this;
            }

            public WearableExtender setHintLaunchesActivity(boolean bl) {
                this.setFlag(2, bl);
                return this;
            }

            @Deprecated
            public WearableExtender setInProgressLabel(CharSequence charSequence) {
                this.mInProgressLabel = charSequence;
                return this;
            }
        }

    }

    public static class BigPictureStyle
    extends Style {
        public static final int MIN_ASHMEM_BITMAP_SIZE = 131072;
        private Icon mBigLargeIcon;
        private boolean mBigLargeIconSet = false;
        private Bitmap mPicture;

        public BigPictureStyle() {
        }

        @Deprecated
        public BigPictureStyle(Builder builder) {
            this.setBuilder(builder);
        }

        private static boolean areBitmapsObviouslyDifferent(Bitmap bitmap, Bitmap bitmap2) {
            boolean bl = false;
            if (bitmap == bitmap2) {
                return false;
            }
            if (bitmap != null && bitmap2 != null) {
                if (bitmap.getWidth() != bitmap2.getWidth() || bitmap.getHeight() != bitmap2.getHeight() || bitmap.getConfig() != bitmap2.getConfig() || bitmap.getGenerationId() != bitmap2.getGenerationId()) {
                    bl = true;
                }
                return bl;
            }
            return true;
        }

        @Override
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            if (this.mBigLargeIconSet) {
                bundle.putParcelable(Notification.EXTRA_LARGE_ICON_BIG, this.mBigLargeIcon);
            }
            bundle.putParcelable(Notification.EXTRA_PICTURE, this.mPicture);
        }

        @Override
        public boolean areNotificationsVisiblyDifferent(Style style2) {
            if (style2 != null && this.getClass() == style2.getClass()) {
                style2 = (BigPictureStyle)style2;
                return BigPictureStyle.areBitmapsObviouslyDifferent(this.getBigPicture(), ((BigPictureStyle)style2).getBigPicture());
            }
            return true;
        }

        public BigPictureStyle bigLargeIcon(Bitmap parcelable) {
            parcelable = parcelable != null ? Icon.createWithBitmap(parcelable) : null;
            return this.bigLargeIcon((Icon)parcelable);
        }

        public BigPictureStyle bigLargeIcon(Icon icon) {
            this.mBigLargeIconSet = true;
            this.mBigLargeIcon = icon;
            return this;
        }

        public BigPictureStyle bigPicture(Bitmap bitmap) {
            this.mPicture = bitmap;
            return this;
        }

        public Bitmap getBigPicture() {
            return this.mPicture;
        }

        @Override
        public boolean hasSummaryInHeader() {
            return false;
        }

        @Override
        public RemoteViews makeBigContentView() {
            Icon icon = null;
            Bitmap bitmap = null;
            if (this.mBigLargeIconSet) {
                icon = this.mBuilder.mN.mLargeIcon;
                this.mBuilder.mN.mLargeIcon = this.mBigLargeIcon;
                bitmap = Builder.access$300((Builder)this.mBuilder).largeIcon;
                Builder.access$300((Builder)this.mBuilder).largeIcon = null;
            }
            StandardTemplateParams standardTemplateParams = this.mBuilder.mParams.reset().fillTextsFrom(this.mBuilder);
            RemoteViews remoteViews = this.getStandardView(this.mBuilder.getBigPictureLayoutResource(), standardTemplateParams, null);
            if (this.mSummaryTextSet) {
                remoteViews.setTextViewText(16909429, this.mBuilder.processTextSpans(this.mBuilder.processLegacyText(this.mSummaryText)));
                this.mBuilder.setTextViewColorSecondary(remoteViews, 16909429, standardTemplateParams);
                remoteViews.setViewVisibility(16909429, 0);
            }
            this.mBuilder.setContentMinHeight(remoteViews, this.mBuilder.mN.hasLargeIcon());
            if (this.mBigLargeIconSet) {
                this.mBuilder.mN.mLargeIcon = icon;
                Builder.access$300((Builder)this.mBuilder).largeIcon = bitmap;
            }
            remoteViews.setImageViewBitmap(16908760, this.mPicture);
            return remoteViews;
        }

        @Override
        public void purgeResources() {
            super.purgeResources();
            Parcelable parcelable = this.mPicture;
            if (parcelable != null && ((Bitmap)parcelable).isMutable() && this.mPicture.getAllocationByteCount() >= 131072) {
                this.mPicture = this.mPicture.createAshmemBitmap();
            }
            if ((parcelable = this.mBigLargeIcon) != null) {
                ((Icon)parcelable).convertToAshmem();
            }
        }

        @Override
        public void reduceImageSizes(Context object) {
            int n;
            super.reduceImageSizes((Context)object);
            object = ((Context)object).getResources();
            boolean bl = ActivityManager.isLowRamDeviceStatic();
            if (this.mPicture != null) {
                n = bl ? 17105302 : 17105301;
                int n2 = ((Resources)object).getDimensionPixelSize(n);
                n = bl ? 17105304 : 17105303;
                n = ((Resources)object).getDimensionPixelSize(n);
                this.mPicture = Icon.scaleDownIfNecessary(this.mPicture, n2, n);
            }
            if (this.mBigLargeIcon != null) {
                n = bl ? 17105341 : 17105340;
                n = ((Resources)object).getDimensionPixelSize(n);
                this.mBigLargeIcon.scaleDownIfNecessary(n, n);
            }
        }

        @Override
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            if (bundle.containsKey(Notification.EXTRA_LARGE_ICON_BIG)) {
                this.mBigLargeIconSet = true;
                this.mBigLargeIcon = (Icon)bundle.getParcelable(Notification.EXTRA_LARGE_ICON_BIG);
            }
            this.mPicture = (Bitmap)bundle.getParcelable(Notification.EXTRA_PICTURE);
        }

        public BigPictureStyle setBigContentTitle(CharSequence charSequence) {
            this.internalSetBigContentTitle(Notification.safeCharSequence(charSequence));
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence charSequence) {
            this.internalSetSummaryText(Notification.safeCharSequence(charSequence));
            return this;
        }
    }

    public static class BigTextStyle
    extends Style {
        private CharSequence mBigText;

        public BigTextStyle() {
        }

        @Deprecated
        public BigTextStyle(Builder builder) {
            this.setBuilder(builder);
        }

        @Override
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            bundle.putCharSequence(Notification.EXTRA_BIG_TEXT, this.mBigText);
        }

        @Override
        public boolean areNotificationsVisiblyDifferent(Style style2) {
            if (style2 != null && this.getClass() == style2.getClass()) {
                style2 = (BigTextStyle)style2;
                return true ^ Objects.equals(String.valueOf(this.getBigText()), String.valueOf(((BigTextStyle)style2).getBigText()));
            }
            return true;
        }

        public BigTextStyle bigText(CharSequence charSequence) {
            this.mBigText = Notification.safeCharSequence(charSequence);
            return this;
        }

        public CharSequence getBigText() {
            return this.mBigText;
        }

        @Override
        public RemoteViews makeBigContentView() {
            CharSequence charSequence;
            StandardTemplateParams standardTemplateParams = this.mBuilder.mParams.reset().fillTextsFrom(this.mBuilder).text(null);
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews remoteViews = this.getStandardView(this.mBuilder.getBigTextLayoutResource(), standardTemplateParams, templateBindResult);
            remoteViews.setInt(16908761, "setImageEndMargin", templateBindResult.getIconMarginEnd());
            CharSequence charSequence2 = charSequence = this.mBuilder.processLegacyText(this.mBigText);
            if (TextUtils.isEmpty(charSequence)) {
                charSequence2 = this.mBuilder.processLegacyText(this.mBuilder.getAllExtras().getCharSequence(Notification.EXTRA_TEXT));
            }
            remoteViews.setTextViewText(16908761, this.mBuilder.processTextSpans(charSequence2));
            this.mBuilder.setTextViewColorSecondary(remoteViews, 16908761, standardTemplateParams);
            int n = TextUtils.isEmpty(charSequence2) ? 8 : 0;
            remoteViews.setViewVisibility(16908761, n);
            remoteViews.setBoolean(16908761, "setHasImage", templateBindResult.isRightIconContainerVisible());
            return remoteViews;
        }

        @Override
        public RemoteViews makeContentView(boolean bl) {
            if (bl) {
                this.mBuilder.mOriginalActions = this.mBuilder.mActions;
                this.mBuilder.mActions = new ArrayList();
                RemoteViews remoteViews = this.makeBigContentView();
                this.mBuilder.mActions = this.mBuilder.mOriginalActions;
                this.mBuilder.mOriginalActions = null;
                return remoteViews;
            }
            return super.makeContentView(bl);
        }

        @Override
        public RemoteViews makeHeadsUpContentView(boolean bl) {
            if (bl && this.mBuilder.mActions.size() > 0) {
                return this.makeBigContentView();
            }
            return super.makeHeadsUpContentView(bl);
        }

        @Override
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mBigText = bundle.getCharSequence(Notification.EXTRA_BIG_TEXT);
        }

        public BigTextStyle setBigContentTitle(CharSequence charSequence) {
            this.internalSetBigContentTitle(Notification.safeCharSequence(charSequence));
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence charSequence) {
            this.internalSetSummaryText(Notification.safeCharSequence(charSequence));
            return this;
        }
    }

    public static final class BubbleMetadata
    implements Parcelable {
        public static final Parcelable.Creator<BubbleMetadata> CREATOR = new Parcelable.Creator<BubbleMetadata>(){

            @Override
            public BubbleMetadata createFromParcel(Parcel parcel) {
                return new BubbleMetadata(parcel);
            }

            public BubbleMetadata[] newArray(int n) {
                return new BubbleMetadata[n];
            }
        };
        private static final int FLAG_AUTO_EXPAND_BUBBLE = 1;
        private static final int FLAG_SUPPRESS_NOTIFICATION = 2;
        private PendingIntent mDeleteIntent;
        private int mDesiredHeight;
        private int mDesiredHeightResId;
        private int mFlags;
        private Icon mIcon;
        private PendingIntent mPendingIntent;

        private BubbleMetadata(PendingIntent pendingIntent, PendingIntent pendingIntent2, Icon icon, int n, int n2) {
            this.mPendingIntent = pendingIntent;
            this.mIcon = icon;
            this.mDesiredHeight = n;
            this.mDesiredHeightResId = n2;
            this.mDeleteIntent = pendingIntent2;
        }

        private BubbleMetadata(Parcel parcel) {
            this.mPendingIntent = PendingIntent.CREATOR.createFromParcel(parcel);
            this.mIcon = Icon.CREATOR.createFromParcel(parcel);
            this.mDesiredHeight = parcel.readInt();
            this.mFlags = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.mDeleteIntent = PendingIntent.CREATOR.createFromParcel(parcel);
            }
            this.mDesiredHeightResId = parcel.readInt();
        }

        private void setFlags(int n) {
            this.mFlags = n;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean getAutoExpandBubble() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public int getDesiredHeight() {
            return this.mDesiredHeight;
        }

        public int getDesiredHeightResId() {
            return this.mDesiredHeightResId;
        }

        public Icon getIcon() {
            return this.mIcon;
        }

        public PendingIntent getIntent() {
            return this.mPendingIntent;
        }

        public boolean isNotificationSuppressed() {
            boolean bl = (this.mFlags & 2) != 0;
            return bl;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.mPendingIntent.writeToParcel(parcel, 0);
            this.mIcon.writeToParcel(parcel, 0);
            parcel.writeInt(this.mDesiredHeight);
            parcel.writeInt(this.mFlags);
            n = this.mDeleteIntent != null ? 1 : 0;
            parcel.writeInt(n);
            PendingIntent pendingIntent = this.mDeleteIntent;
            if (pendingIntent != null) {
                pendingIntent.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mDesiredHeightResId);
        }

        public static final class Builder {
            private PendingIntent mDeleteIntent;
            private int mDesiredHeight;
            private int mDesiredHeightResId;
            private int mFlags;
            private Icon mIcon;
            private PendingIntent mPendingIntent;

            public BubbleMetadata build() {
                Parcelable parcelable = this.mPendingIntent;
                if (parcelable != null) {
                    Icon icon = this.mIcon;
                    if (icon != null) {
                        parcelable = new BubbleMetadata((PendingIntent)parcelable, this.mDeleteIntent, icon, this.mDesiredHeight, this.mDesiredHeightResId);
                        ((BubbleMetadata)parcelable).setFlags(this.mFlags);
                        return parcelable;
                    }
                    throw new IllegalStateException("Must supply an icon for the bubble");
                }
                throw new IllegalStateException("Must supply pending intent to bubble");
            }

            public Builder setAutoExpandBubble(boolean bl) {
                this.setFlag(1, bl);
                return this;
            }

            public Builder setDeleteIntent(PendingIntent pendingIntent) {
                this.mDeleteIntent = pendingIntent;
                return this;
            }

            public Builder setDesiredHeight(int n) {
                this.mDesiredHeight = Math.max(n, 0);
                this.mDesiredHeightResId = 0;
                return this;
            }

            public Builder setDesiredHeightResId(int n) {
                this.mDesiredHeightResId = n;
                this.mDesiredHeight = 0;
                return this;
            }

            public Builder setFlag(int n, boolean bl) {
                this.mFlags = bl ? (this.mFlags |= n) : (this.mFlags &= n);
                return this;
            }

            public Builder setIcon(Icon icon) {
                if (icon != null) {
                    if (icon.getType() != 1) {
                        this.mIcon = icon;
                        return this;
                    }
                    throw new IllegalArgumentException("When using bitmap based icons, Bubbles require TYPE_ADAPTIVE_BITMAP, please use Icon#createWithAdaptiveBitmap instead");
                }
                throw new IllegalArgumentException("Bubbles require non-null icon");
            }

            public Builder setIntent(PendingIntent pendingIntent) {
                if (pendingIntent != null) {
                    this.mPendingIntent = pendingIntent;
                    return this;
                }
                throw new IllegalArgumentException("Bubble requires non-null pending intent");
            }

            public Builder setSuppressNotification(boolean bl) {
                this.setFlag(2, bl);
                return this;
            }
        }

    }

    public static class Builder {
        public static final String EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.bigViewActionCount";
        public static final String EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.contentViewActionCount";
        public static final String EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.hudViewActionCount";
        private static final int LIGHTNESS_TEXT_DIFFERENCE_DARK = -10;
        private static final int LIGHTNESS_TEXT_DIFFERENCE_LIGHT = 20;
        private static final boolean USE_ONLY_TITLE_IN_LOW_PRIORITY_SUMMARY = SystemProperties.getBoolean("notifications.only_title", true);
        @UnsupportedAppUsage
        private ArrayList<Action> mActions = new ArrayList(3);
        private int mBackgroundColor = 1;
        private int mCachedAmbientColor = 1;
        private int mCachedAmbientColorIsFor = 1;
        private int mCachedContrastColor = 1;
        private int mCachedContrastColorIsFor = 1;
        private ContrastColorUtil mColorUtil;
        private Context mContext;
        private int mForegroundColor = 1;
        private boolean mInNightMode;
        private boolean mIsLegacy;
        private boolean mIsLegacyInitialized;
        private Notification mN;
        private int mNeutralColor = 1;
        private ArrayList<Action> mOriginalActions;
        StandardTemplateParams mParams = new StandardTemplateParams();
        private ArrayList<Person> mPersonList = new ArrayList();
        private int mPrimaryTextColor = 1;
        private boolean mRebuildStyledRemoteViews;
        private int mSecondaryTextColor = 1;
        private Style mStyle;
        private int mTextColorsAreForBackground = 1;
        private boolean mTintActionButtons;
        private Bundle mUserExtras = new Bundle();

        @Deprecated
        public Builder(Context context) {
            this(context, (Notification)null);
        }

        public Builder(Context object, Notification class_) {
            this.mContext = object;
            Resources resources = this.mContext.getResources();
            this.mTintActionButtons = resources.getBoolean(17891551);
            if (resources.getBoolean(17891446)) {
                boolean bl = (resources.getConfiguration().uiMode & 48) == 32;
                this.mInNightMode = bl;
            }
            if (class_ == null) {
                this.mN = new Notification();
                if (object.getApplicationInfo().targetSdkVersion < 24) {
                    this.mN.extras.putBoolean(Notification.EXTRA_SHOW_WHEN, true);
                }
                object = this.mN;
                ((Notification)object).priority = 0;
                ((Notification)object).visibility = 0;
            } else {
                this.mN = class_;
                if (this.mN.actions != null) {
                    Collections.addAll(this.mActions, this.mN.actions);
                }
                if (this.mN.extras.containsKey(Notification.EXTRA_PEOPLE_LIST)) {
                    object = this.mN.extras.getParcelableArrayList(Notification.EXTRA_PEOPLE_LIST);
                    this.mPersonList.addAll((Collection<Person>)object);
                }
                if (this.mN.getSmallIcon() == null && this.mN.icon != 0) {
                    this.setSmallIcon(this.mN.icon);
                }
                if (this.mN.getLargeIcon() == null && this.mN.largeIcon != null) {
                    this.setLargeIcon(this.mN.largeIcon);
                }
                if (!TextUtils.isEmpty((CharSequence)(object = this.mN.extras.getString(Notification.EXTRA_TEMPLATE)))) {
                    class_ = Notification.getNotificationStyleClass((String)object);
                    if (class_ == null) {
                        class_ = new StringBuilder();
                        ((StringBuilder)((Object)class_)).append("Unknown style class: ");
                        ((StringBuilder)((Object)class_)).append((String)object);
                        Log.d(Notification.TAG, ((StringBuilder)((Object)class_)).toString());
                    } else {
                        try {
                            object = class_.getDeclaredConstructor(new Class[0]);
                            ((AccessibleObject)object).setAccessible(true);
                            object = (Style)((Constructor)object).newInstance(new Object[0]);
                            ((Style)object).restoreFromExtras(this.mN.extras);
                            this.setStyle((Style)object);
                        }
                        catch (Throwable throwable) {
                            Log.e(Notification.TAG, "Could not create Style", throwable);
                        }
                    }
                }
            }
        }

        public Builder(Context context, String string2) {
            this(context, (Notification)null);
            this.mN.mChannelId = string2;
        }

        static /* synthetic */ int access$3900(Builder builder) {
            return builder.getInboxLayoutResource();
        }

        private RemoteViews applyStandardTemplate(int n, StandardTemplateParams standardTemplateParams, TemplateBindResult object) {
            BuilderRemoteViews builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), n);
            this.resetStandardTemplate(builderRemoteViews);
            Bundle bundle = this.mN.extras;
            this.updateBackgroundColor(builderRemoteViews, standardTemplateParams);
            this.bindNotificationHeader(builderRemoteViews, standardTemplateParams);
            this.bindLargeIconAndReply(builderRemoteViews, standardTemplateParams, (TemplateBindResult)object);
            boolean bl = this.handleProgressBar(builderRemoteViews, bundle, standardTemplateParams);
            object = standardTemplateParams.title;
            boolean bl2 = false;
            if (object != null) {
                builderRemoteViews.setViewVisibility(16908310, 0);
                builderRemoteViews.setTextViewText(16908310, this.processTextSpans(standardTemplateParams.title));
                this.setTextViewColorPrimary(builderRemoteViews, 16908310, standardTemplateParams);
                n = bl ? -2 : -1;
                builderRemoteViews.setViewLayoutWidth(16908310, n);
            }
            if (standardTemplateParams.text != null) {
                n = bl ? 16909457 : 16909429;
                builderRemoteViews.setTextViewText(n, this.processTextSpans(standardTemplateParams.text));
                this.setTextViewColorSecondary(builderRemoteViews, n, standardTemplateParams);
                builderRemoteViews.setViewVisibility(n, 0);
            }
            if (bl || this.mN.hasLargeIcon()) {
                bl2 = true;
            }
            this.setContentMinHeight(builderRemoteViews, bl2);
            return builderRemoteViews;
        }

        private RemoteViews applyStandardTemplate(int n, TemplateBindResult templateBindResult) {
            return this.applyStandardTemplate(n, this.mParams.reset().fillTextsFrom(this), templateBindResult);
        }

        private RemoteViews applyStandardTemplateWithActions(int n, StandardTemplateParams standardTemplateParams, TemplateBindResult object) {
            object = this.applyStandardTemplate(n, standardTemplateParams, (TemplateBindResult)object);
            this.resetStandardTemplateWithActions((RemoteViews)object);
            int n2 = 0;
            n = 0;
            CharSequence[] arrcharSequence = Builder.filterOutContextualActions(this.mActions);
            int n3 = arrcharSequence.size();
            boolean bl = this.mN.fullScreenIntent != null;
            ((RemoteViews)object).setBoolean(16908694, "setEmphasizedMode", bl);
            int n4 = 8;
            if (n3 > 0) {
                ((RemoteViews)object).setViewVisibility(16908695, 0);
                ((RemoteViews)object).setViewVisibility(16908694, 0);
                ((RemoteViews)object).setViewLayoutMarginBottomDimen(16909161, 0);
                n2 = n3;
                if (n3 > 3) {
                    n2 = 3;
                }
                for (n3 = 0; n3 < n2; ++n3) {
                    Parcelable parcelable = (Action)arrcharSequence.get(n3);
                    int n5 = this.hasValidRemoteInput((Action)parcelable);
                    n |= n5;
                    parcelable = this.generateActionButton((Action)parcelable, bl, standardTemplateParams);
                    if (n5 != 0 && !bl) {
                        ((RemoteViews)parcelable).setInt(16908669, "setBackgroundResource", 0);
                    }
                    ((RemoteViews)object).addView(16908694, (RemoteViews)parcelable);
                }
            } else {
                ((RemoteViews)object).setViewVisibility(16908695, 8);
                n = n2;
            }
            arrcharSequence = this.mN.extras.getCharSequenceArray(Notification.EXTRA_REMOTE_INPUT_HISTORY);
            if (n != 0 && arrcharSequence != null && arrcharSequence.length > 0 && !TextUtils.isEmpty(arrcharSequence[0]) && standardTemplateParams.maxRemoteInputHistory > 0) {
                bl = this.mN.extras.getBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER);
                ((RemoteViews)object).setViewVisibility(16909166, 0);
                ((RemoteViews)object).setViewVisibility(16909169, 0);
                ((RemoteViews)object).setTextViewText(16909168, this.processTextSpans(arrcharSequence[0]));
                this.setTextViewColorSecondary((RemoteViews)object, 16909168, standardTemplateParams);
                n = n4;
                if (bl) {
                    n = 0;
                }
                ((RemoteViews)object).setViewVisibility(16909167, n);
                n = this.isColorized(standardTemplateParams) ? this.getPrimaryTextColor(standardTemplateParams) : this.resolveContrastColor(standardTemplateParams);
                ((RemoteViews)object).setProgressIndeterminateTintList(16909167, ColorStateList.valueOf(n));
                if (arrcharSequence.length > 1 && !TextUtils.isEmpty(arrcharSequence[1]) && standardTemplateParams.maxRemoteInputHistory > 1) {
                    ((RemoteViews)object).setViewVisibility(16909170, 0);
                    ((RemoteViews)object).setTextViewText(16909170, this.processTextSpans(arrcharSequence[1]));
                    this.setTextViewColorSecondary((RemoteViews)object, 16909170, standardTemplateParams);
                    if (arrcharSequence.length > 2 && !TextUtils.isEmpty(arrcharSequence[2]) && standardTemplateParams.maxRemoteInputHistory > 2) {
                        ((RemoteViews)object).setViewVisibility(16909171, 0);
                        ((RemoteViews)object).setTextViewText(16909171, this.processTextSpans(arrcharSequence[2]));
                        this.setTextViewColorSecondary((RemoteViews)object, 16909171, standardTemplateParams);
                    }
                }
            }
            return object;
        }

        private RemoteViews applyStandardTemplateWithActions(int n, TemplateBindResult templateBindResult) {
            return this.applyStandardTemplateWithActions(n, this.mParams.reset().fillTextsFrom(this), templateBindResult);
        }

        private void bindActivePermissions(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            int n = this.getNeutralColor(standardTemplateParams);
            remoteViews.setDrawableTint(16908791, false, n, PorterDuff.Mode.SRC_ATOP);
            remoteViews.setDrawableTint(16909111, false, n, PorterDuff.Mode.SRC_ATOP);
            remoteViews.setDrawableTint(16909207, false, n, PorterDuff.Mode.SRC_ATOP);
        }

        private void bindAlertedIcon(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            remoteViews.setDrawableTint(16908712, false, this.getNeutralColor(standardTemplateParams), PorterDuff.Mode.SRC_ATOP);
        }

        private void bindExpandButton(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            int n = this.isColorized(standardTemplateParams) ? this.getPrimaryTextColor(standardTemplateParams) : this.getSecondaryTextColor(standardTemplateParams);
            remoteViews.setDrawableTint(16908898, false, n, PorterDuff.Mode.SRC_ATOP);
            remoteViews.setInt(16909164, "setOriginalNotificationColor", n);
        }

        private void bindHeaderAppName(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            remoteViews.setTextViewText(16908731, this.loadHeaderAppName());
            if (this.isColorized(standardTemplateParams)) {
                this.setTextViewColorPrimary(remoteViews, 16908731, standardTemplateParams);
            } else {
                remoteViews.setTextColor(16908731, this.getSecondaryTextColor(standardTemplateParams));
            }
        }

        private void bindHeaderChronometerAndTime(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (this.showsTimeOrChronometer()) {
                remoteViews.setViewVisibility(16909464, 0);
                this.setTextViewColorSecondary(remoteViews, 16909464, standardTemplateParams);
                if (this.mN.extras.getBoolean(Notification.EXTRA_SHOW_CHRONOMETER)) {
                    remoteViews.setViewVisibility(16908808, 0);
                    remoteViews.setLong(16908808, "setBase", this.mN.when + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
                    remoteViews.setBoolean(16908808, "setStarted", true);
                    remoteViews.setChronometerCountDown(16908808, this.mN.extras.getBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN));
                    this.setTextViewColorSecondary(remoteViews, 16908808, standardTemplateParams);
                } else {
                    remoteViews.setViewVisibility(16909460, 0);
                    remoteViews.setLong(16909460, "setTime", this.mN.when);
                    this.setTextViewColorSecondary(remoteViews, 16909460, standardTemplateParams);
                }
            } else {
                long l = this.mN.when != 0L ? this.mN.when : this.mN.creationTime;
                remoteViews.setLong(16909460, "setTime", l);
            }
        }

        private void bindHeaderText(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = standardTemplateParams.summaryText;
            if (charSequence == null) {
                Style style2 = this.mStyle;
                charSequence2 = charSequence;
                if (style2 != null) {
                    charSequence2 = charSequence;
                    if (style2.mSummaryTextSet) {
                        charSequence2 = charSequence;
                        if (this.mStyle.hasSummaryInHeader()) {
                            charSequence2 = this.mStyle.mSummaryText;
                        }
                    }
                }
            }
            charSequence = charSequence2;
            if (charSequence2 == null) {
                charSequence = charSequence2;
                if (this.mContext.getApplicationInfo().targetSdkVersion < 24) {
                    charSequence = charSequence2;
                    if (this.mN.extras.getCharSequence(Notification.EXTRA_INFO_TEXT) != null) {
                        charSequence = this.mN.extras.getCharSequence(Notification.EXTRA_INFO_TEXT);
                    }
                }
            }
            if (charSequence != null) {
                remoteViews.setTextViewText(16908977, this.processTextSpans(this.processLegacyText(charSequence)));
                this.setTextViewColorSecondary(remoteViews, 16908977, standardTemplateParams);
                remoteViews.setViewVisibility(16908977, 0);
                remoteViews.setViewVisibility(16908978, 0);
                this.setTextViewColorSecondary(remoteViews, 16908978, standardTemplateParams);
            }
        }

        private void bindHeaderTextSecondary(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (!TextUtils.isEmpty(standardTemplateParams.headerTextSecondary)) {
                remoteViews.setTextViewText(16908979, this.processTextSpans(this.processLegacyText(standardTemplateParams.headerTextSecondary)));
                this.setTextViewColorSecondary(remoteViews, 16908979, standardTemplateParams);
                remoteViews.setViewVisibility(16908979, 0);
                remoteViews.setViewVisibility(16908980, 0);
                this.setTextViewColorSecondary(remoteViews, 16908980, standardTemplateParams);
            }
        }

        private boolean bindLargeIcon(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            boolean bl;
            if (this.mN.mLargeIcon == null && this.mN.largeIcon != null) {
                Notification notification = this.mN;
                notification.mLargeIcon = Icon.createWithBitmap(notification.largeIcon);
            }
            if (bl = this.mN.mLargeIcon != null && !standardTemplateParams.hideLargeIcon) {
                remoteViews.setViewVisibility(16909294, 0);
                remoteViews.setImageViewIcon(16909294, this.mN.mLargeIcon);
                this.processLargeLegacyIcon(this.mN.mLargeIcon, remoteViews, standardTemplateParams);
            }
            return bl;
        }

        private void bindLargeIconAndReply(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, TemplateBindResult templateBindResult) {
            boolean bl = this.bindLargeIcon(remoteViews, standardTemplateParams);
            boolean bl2 = this.bindReplyIcon(remoteViews, standardTemplateParams);
            int n = 0;
            boolean bl3 = bl || bl2;
            if (!bl3) {
                n = 8;
            }
            remoteViews.setViewVisibility(16909295, n);
            n = this.calculateMarginEnd(bl, bl2);
            remoteViews.setViewLayoutMarginEnd(16909068, n);
            remoteViews.setViewLayoutMarginEnd(16909429, n);
            remoteViews.setViewLayoutMarginEnd(16908301, n);
            if (templateBindResult != null) {
                templateBindResult.setIconMarginEnd(n);
                templateBindResult.setRightIconContainerVisible(bl3);
            }
        }

        private void bindNotificationHeader(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            this.bindSmallIcon(remoteViews, standardTemplateParams);
            this.bindHeaderAppName(remoteViews, standardTemplateParams);
            this.bindHeaderText(remoteViews, standardTemplateParams);
            this.bindHeaderTextSecondary(remoteViews, standardTemplateParams);
            this.bindHeaderChronometerAndTime(remoteViews, standardTemplateParams);
            this.bindProfileBadge(remoteViews, standardTemplateParams);
            this.bindAlertedIcon(remoteViews, standardTemplateParams);
            this.bindActivePermissions(remoteViews, standardTemplateParams);
            this.bindExpandButton(remoteViews, standardTemplateParams);
            this.mN.mUsesStandardHeader = true;
        }

        private void bindProfileBadge(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            Bitmap bitmap = this.getProfileBadge();
            if (bitmap != null) {
                remoteViews.setImageViewBitmap(16909257, bitmap);
                remoteViews.setViewVisibility(16909257, 0);
                if (this.isColorized(standardTemplateParams)) {
                    remoteViews.setDrawableTint(16909257, false, this.getPrimaryTextColor(standardTemplateParams), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }

        private boolean bindReplyIcon(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            boolean bl = standardTemplateParams.hideReplyIcon;
            boolean bl2 = true;
            boolean bl3 = bl ^ true;
            Action action = null;
            int n = 0;
            bl = bl3;
            if (bl3) {
                action = this.findReplyAction();
                bl = action != null ? bl2 : false;
            }
            if (bl) {
                remoteViews.setViewVisibility(16909284, 0);
                remoteViews.setDrawableTint(16909284, false, this.getNeutralColor(standardTemplateParams), PorterDuff.Mode.SRC_ATOP);
                remoteViews.setOnClickPendingIntent(16909284, action.actionIntent);
                remoteViews.setRemoteInputs(16909284, action.mRemoteInputs);
            } else {
                remoteViews.setRemoteInputs(16909284, null);
            }
            if (!bl) {
                n = 8;
            }
            remoteViews.setViewVisibility(16909284, n);
            return bl;
        }

        private void bindSmallIcon(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (this.mN.mSmallIcon == null && this.mN.icon != 0) {
                Notification notification = this.mN;
                notification.mSmallIcon = Icon.createWithResource(this.mContext, notification.icon);
            }
            remoteViews.setImageViewIcon(16908294, this.mN.mSmallIcon);
            remoteViews.setInt(16908294, "setImageLevel", this.mN.iconLevel);
            this.processSmallIconColor(this.mN.mSmallIcon, remoteViews, standardTemplateParams);
        }

        private int calculateMarginEnd(boolean bl, boolean bl2) {
            int n;
            block7 : {
                int n2;
                int n3;
                block6 : {
                    n = 0;
                    n3 = this.mContext.getResources().getDimensionPixelSize(17105307);
                    int n4 = this.mContext.getResources().getDimensionPixelSize(17105340);
                    if (bl2) {
                        n = 0 + n4 - this.mContext.getResources().getDimensionPixelSize(17105339) * 2;
                    }
                    n2 = n;
                    if (bl) {
                        n2 = n += n4;
                        if (bl2) {
                            n2 = n + n3;
                        }
                    }
                    if (bl2) break block6;
                    n = n2;
                    if (!bl) break block7;
                }
                n = n2 + n3;
            }
            return n;
        }

        private CharSequence createSummaryText() {
            CharSequence charSequence = this.mN.extras.getCharSequence(Notification.EXTRA_TITLE);
            if (USE_ONLY_TITLE_IN_LOW_PRIORITY_SUMMARY) {
                return charSequence;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            CharSequence charSequence2 = charSequence;
            if (charSequence == null) {
                charSequence2 = this.mN.extras.getCharSequence(Notification.EXTRA_TITLE_BIG);
            }
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            if (charSequence2 != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(charSequence2));
            }
            charSequence = this.mN.extras.getCharSequence(Notification.EXTRA_TEXT);
            if (charSequence2 != null && charSequence != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(this.mContext.getText(17040510)));
            }
            if (charSequence != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(charSequence));
            }
            return spannableStringBuilder;
        }

        private CharSequence ensureColorSpanContrast(CharSequence charSequence, int n, ColorStateList[] arrcolorStateList) {
            if (charSequence instanceof Spanned) {
                Spanned spanned = (Spanned)charSequence;
                int n2 = spanned.length();
                boolean bl = false;
                Object[] arrobject = spanned.getSpans(0, n2, Object.class);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned.toString());
                n2 = arrobject.length;
                for (int i = 0; i < n2; ++i) {
                    int n3;
                    int[] arrn;
                    Object object = arrn = arrobject[i];
                    int n4 = spanned.getSpanStart(arrn);
                    int n5 = spanned.getSpanEnd(arrn);
                    if (n5 - n4 == charSequence.length()) {
                        bl = true;
                    }
                    Object object2 = object;
                    if (object instanceof CharacterStyle) {
                        object2 = ((CharacterStyle)arrn).getUnderlying();
                    }
                    if (object2 instanceof TextAppearanceSpan) {
                        TextAppearanceSpan textAppearanceSpan = (TextAppearanceSpan)object2;
                        object = textAppearanceSpan.getTextColor();
                        if (object != null) {
                            object2 = object.getColors();
                            int[] arrn2 = new int[((int[])object2).length];
                            for (n3 = 0; n3 < arrn2.length; ++n3) {
                                arrn2[n3] = ContrastColorUtil.ensureLargeTextContrast(object2[n3], n, this.mInNightMode);
                            }
                            object2 = object = new ColorStateList((int[][])object.getStates().clone(), arrn2);
                            if (bl) {
                                arrcolorStateList[0] = object;
                                object2 = null;
                            }
                            object2 = new TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), textAppearanceSpan.getTextSize(), (ColorStateList)object2, textAppearanceSpan.getLinkTextColor());
                        }
                    } else if (object2 instanceof ForegroundColorSpan) {
                        n3 = ContrastColorUtil.ensureLargeTextContrast(((ForegroundColorSpan)object2).getForegroundColor(), n, this.mInNightMode);
                        if (bl) {
                            arrcolorStateList[0] = ColorStateList.valueOf(n3);
                            object2 = null;
                        } else {
                            object2 = new ForegroundColorSpan(n3);
                        }
                    } else {
                        object2 = arrn;
                    }
                    if (object2 != null) {
                        spannableStringBuilder.setSpan(object2, n4, n5, spanned.getSpanFlags(arrn));
                    }
                    bl = false;
                }
                return spannableStringBuilder;
            }
            return charSequence;
        }

        private void ensureColors(StandardTemplateParams standardTemplateParams) {
            int n = this.getBackgroundColor(standardTemplateParams);
            if (this.mPrimaryTextColor == 1 || this.mSecondaryTextColor == 1 || this.mTextColorsAreForBackground != n) {
                this.mTextColorsAreForBackground = n;
                if (this.hasForegroundColor() && this.isColorized(standardTemplateParams)) {
                    double d = ContrastColorUtil.calculateLuminance(n);
                    double d2 = ContrastColorUtil.calculateLuminance(this.mForegroundColor);
                    double d3 = ContrastColorUtil.calculateContrast(this.mForegroundColor, n);
                    int n2 = d > d2 && ContrastColorUtil.satisfiesTextContrast(n, -16777216) || d <= d2 && !ContrastColorUtil.satisfiesTextContrast(n, -1) ? 1 : 0;
                    int n3 = 10;
                    if (d3 < 4.5) {
                        if (n2 != 0) {
                            this.mSecondaryTextColor = ContrastColorUtil.findContrastColor(this.mForegroundColor, n, true, 4.5);
                            this.mPrimaryTextColor = ContrastColorUtil.changeColorLightness(this.mSecondaryTextColor, -20);
                        } else {
                            this.mSecondaryTextColor = ContrastColorUtil.findContrastColorAgainstDark(this.mForegroundColor, n, true, 4.5);
                            this.mPrimaryTextColor = ContrastColorUtil.changeColorLightness(this.mSecondaryTextColor, 10);
                        }
                    } else {
                        int n4 = this.mPrimaryTextColor = this.mForegroundColor;
                        int n5 = n2 != 0 ? 20 : -10;
                        this.mSecondaryTextColor = ContrastColorUtil.changeColorLightness(n4, n5);
                        if (ContrastColorUtil.calculateContrast(this.mSecondaryTextColor, n) < 4.5) {
                            this.mSecondaryTextColor = n2 != 0 ? ContrastColorUtil.findContrastColor(this.mSecondaryTextColor, n, true, 4.5) : ContrastColorUtil.findContrastColorAgainstDark(this.mSecondaryTextColor, n, true, 4.5);
                            n5 = this.mSecondaryTextColor;
                            n2 = n2 != 0 ? -20 : n3;
                            this.mPrimaryTextColor = ContrastColorUtil.changeColorLightness(n5, n2);
                        }
                    }
                } else {
                    this.mPrimaryTextColor = ContrastColorUtil.resolvePrimaryColor(this.mContext, n, this.mInNightMode);
                    this.mSecondaryTextColor = ContrastColorUtil.resolveSecondaryColor(this.mContext, n, this.mInNightMode);
                    if (n != 0 && this.isColorized(standardTemplateParams)) {
                        this.mPrimaryTextColor = ContrastColorUtil.findAlphaToMeetContrast(this.mPrimaryTextColor, n, 4.5);
                        this.mSecondaryTextColor = ContrastColorUtil.findAlphaToMeetContrast(this.mSecondaryTextColor, n, 4.5);
                    }
                }
            }
        }

        private static List<Action> filterOutContextualActions(List<Action> object) {
            ArrayList<Action> arrayList = new ArrayList<Action>();
            Iterator<Action> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (((Action)object).isContextual()) continue;
                arrayList.add((Action)object);
            }
            return arrayList;
        }

        private Action findReplyAction() {
            ArrayList<Action> arrayList = this.mActions;
            if (this.mOriginalActions != null) {
                arrayList = this.mOriginalActions;
            }
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                Action action = arrayList.get(i);
                if (!this.hasValidRemoteInput(action)) continue;
                return action;
            }
            return null;
        }

        private RemoteViews generateActionButton(Action action, boolean bl, StandardTemplateParams standardTemplateParams) {
            ColorStateList[] arrcolorStateList = action.actionIntent;
            boolean bl2 = true;
            boolean bl3 = arrcolorStateList == null;
            arrcolorStateList = this.mContext.getApplicationInfo();
            int n = bl ? this.getEmphasizedActionLayoutResource() : (bl3 ? this.getActionTombstoneLayoutResource() : this.getActionLayoutResource());
            BuilderRemoteViews builderRemoteViews = new BuilderRemoteViews((ApplicationInfo)arrcolorStateList, n);
            if (!bl3) {
                builderRemoteViews.setOnClickPendingIntent(16908669, action.actionIntent);
            }
            builderRemoteViews.setContentDescription(16908669, action.title);
            if (action.mRemoteInputs != null) {
                builderRemoteViews.setRemoteInputs(16908669, action.mRemoteInputs);
            }
            if (bl) {
                CharSequence charSequence = action.title;
                arrcolorStateList = null;
                int n2 = this.resolveBackgroundColor(standardTemplateParams);
                if (this.isLegacy()) {
                    charSequence = ContrastColorUtil.clearColorSpans(charSequence);
                } else {
                    arrcolorStateList = new ColorStateList[1];
                    charSequence = this.ensureColorSpanContrast(charSequence, n2, arrcolorStateList);
                }
                builderRemoteViews.setTextViewText(16908669, this.processTextSpans(charSequence));
                this.setTextViewColorPrimary(builderRemoteViews, 16908669, standardTemplateParams);
                bl3 = arrcolorStateList != null && arrcolorStateList[0] != null;
                if (bl3) {
                    n2 = arrcolorStateList[0].getDefaultColor();
                    n = ContrastColorUtil.resolvePrimaryColor(this.mContext, n2, this.mInNightMode);
                    builderRemoteViews.setTextColor(16908669, n);
                } else if (this.getRawColor(standardTemplateParams) != 0 && !this.isColorized(standardTemplateParams) && this.mTintActionButtons && !this.mInNightMode) {
                    n = this.resolveContrastColor(standardTemplateParams);
                    builderRemoteViews.setTextColor(16908669, n);
                } else {
                    n = this.getPrimaryTextColor(standardTemplateParams);
                }
                builderRemoteViews.setColorStateList(16908669, "setRippleColor", ColorStateList.valueOf(16777215 & n | 855638016));
                builderRemoteViews.setColorStateList(16908669, "setButtonBackground", ColorStateList.valueOf(n2));
                bl = !bl3 ? bl2 : false;
                builderRemoteViews.setBoolean(16908669, "setHasStroke", bl);
            } else {
                builderRemoteViews.setTextViewText(16908669, this.processTextSpans(this.processLegacyText(action.title)));
                if (this.isColorized(standardTemplateParams)) {
                    this.setTextViewColorPrimary(builderRemoteViews, 16908669, standardTemplateParams);
                } else if (this.getRawColor(standardTemplateParams) != 0 && this.mTintActionButtons) {
                    builderRemoteViews.setTextColor(16908669, this.resolveContrastColor(standardTemplateParams));
                }
            }
            builderRemoteViews.setIntTag(16908669, 16909160, this.mActions.indexOf(action));
            return builderRemoteViews;
        }

        private int getActionLayoutResource() {
            return 17367190;
        }

        private int getActionTombstoneLayoutResource() {
            return 17367193;
        }

        private Bundle getAllExtras() {
            Bundle bundle = (Bundle)this.mUserExtras.clone();
            bundle.putAll(this.mN.extras);
            return bundle;
        }

        private int getBackgroundColor(StandardTemplateParams standardTemplateParams) {
            if (this.isColorized(standardTemplateParams)) {
                int n = this.mBackgroundColor;
                if (n == 1) {
                    n = this.getRawColor(standardTemplateParams);
                }
                return n;
            }
            return 0;
        }

        @UnsupportedAppUsage
        private int getBaseLayoutResource() {
            return 17367198;
        }

        private int getBigBaseLayoutResource() {
            return 17367199;
        }

        private int getBigPictureLayoutResource() {
            return 17367201;
        }

        private int getBigTextLayoutResource() {
            return 17367202;
        }

        private ContrastColorUtil getColorUtil() {
            if (this.mColorUtil == null) {
                this.mColorUtil = ContrastColorUtil.getInstance(this.mContext);
            }
            return this.mColorUtil;
        }

        private int getEmphasizedActionLayoutResource() {
            return 17367191;
        }

        private int getInboxLayoutResource() {
            return 17367203;
        }

        private int getMessagingLayoutResource() {
            return 17367205;
        }

        private int getNeutralColor(StandardTemplateParams standardTemplateParams) {
            if (this.isColorized(standardTemplateParams)) {
                return this.getSecondaryTextColor(standardTemplateParams);
            }
            return this.resolveNeutralColor();
        }

        private Bitmap getProfileBadge() {
            Drawable drawable2 = this.getProfileBadgeDrawable();
            if (drawable2 == null) {
                return null;
            }
            int n = this.mContext.getResources().getDimensionPixelSize(17105300);
            Bitmap bitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable2.setBounds(0, 0, n, n);
            drawable2.draw(canvas);
            return bitmap;
        }

        private Drawable getProfileBadgeDrawable() {
            if (this.mContext.getUserId() == 0) {
                return null;
            }
            return this.mContext.getPackageManager().getUserBadgeForDensityNoBackground(new UserHandle(this.mContext.getUserId()), 0);
        }

        private int getRawColor(StandardTemplateParams standardTemplateParams) {
            if (standardTemplateParams.forceDefaultColor) {
                return 0;
            }
            return this.mN.color;
        }

        private boolean handleProgressBar(RemoteViews remoteViews, Bundle parcelable, StandardTemplateParams standardTemplateParams) {
            int n = parcelable.getInt(Notification.EXTRA_PROGRESS_MAX, 0);
            int n2 = parcelable.getInt(Notification.EXTRA_PROGRESS, 0);
            boolean bl = parcelable.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE);
            if (standardTemplateParams.hasProgress && (n != 0 || bl)) {
                remoteViews.setViewVisibility(16908301, 0);
                remoteViews.setProgressBar(16908301, n, n2, bl);
                remoteViews.setProgressBackgroundTintList(16908301, ColorStateList.valueOf(this.mContext.getColor(17170889)));
                if (this.getRawColor(standardTemplateParams) != 0) {
                    n = this.isColorized(standardTemplateParams) ? this.getPrimaryTextColor(standardTemplateParams) : this.resolveContrastColor(standardTemplateParams);
                    parcelable = ColorStateList.valueOf(n);
                    remoteViews.setProgressTintList(16908301, (ColorStateList)parcelable);
                    remoteViews.setProgressIndeterminateTintList(16908301, (ColorStateList)parcelable);
                }
                return true;
            }
            remoteViews.setViewVisibility(16908301, 8);
            return false;
        }

        private boolean hasForegroundColor() {
            int n = this.mForegroundColor;
            boolean bl = true;
            if (n == 1) {
                bl = false;
            }
            return bl;
        }

        private boolean hasValidRemoteInput(Action arrcharSequence) {
            if (!TextUtils.isEmpty(arrcharSequence.title) && arrcharSequence.actionIntent != null) {
                RemoteInput[] arrremoteInput = arrcharSequence.getRemoteInputs();
                if (arrremoteInput == null) {
                    return false;
                }
                for (RemoteInput remoteInput : arrremoteInput) {
                    arrcharSequence = remoteInput.getChoices();
                    if (!(remoteInput.getAllowFreeFormInput() || arrcharSequence != null && arrcharSequence.length != 0)) {
                        continue;
                    }
                    return true;
                }
                return false;
            }
            return false;
        }

        private void hideLine1Text(RemoteViews remoteViews) {
            if (remoteViews != null) {
                remoteViews.setViewVisibility(16909457, 8);
            }
        }

        private boolean isColorized(StandardTemplateParams standardTemplateParams) {
            boolean bl = standardTemplateParams.allowColorization && this.mN.isColorized();
            return bl;
        }

        private boolean isLegacy() {
            if (!this.mIsLegacyInitialized) {
                boolean bl = this.mContext.getApplicationInfo().targetSdkVersion < 21;
                this.mIsLegacy = bl;
                this.mIsLegacyInitialized = true;
            }
            return this.mIsLegacy;
        }

        public static void makeHeaderExpanded(RemoteViews remoteViews) {
            if (remoteViews != null) {
                remoteViews.setBoolean(16909164, "setExpanded", true);
            }
        }

        private RemoteViews makeNotificationHeader(StandardTemplateParams standardTemplateParams) {
            standardTemplateParams.disallowColorization();
            BuilderRemoteViews builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), 17367197);
            this.resetNotificationHeader(builderRemoteViews);
            this.bindNotificationHeader(builderRemoteViews, standardTemplateParams);
            return builderRemoteViews;
        }

        private RemoteViews makePublicView(boolean bl) {
            if (this.mN.publicVersion != null) {
                Object object = Builder.recoverBuilder(this.mContext, this.mN.publicVersion);
                object = bl ? ((Builder)object).makeAmbientNotification() : ((Builder)object).createContentView();
                return object;
            }
            Bundle bundle = this.mN.extras;
            Style style2 = this.mStyle;
            this.mStyle = null;
            Icon icon = this.mN.mLargeIcon;
            this.mN.mLargeIcon = null;
            Bitmap bitmap = this.mN.largeIcon;
            this.mN.largeIcon = null;
            ArrayList<Action> arrayList = this.mActions;
            this.mActions = new ArrayList();
            Parcelable parcelable = new Bundle();
            parcelable.putBoolean(Notification.EXTRA_SHOW_WHEN, bundle.getBoolean(Notification.EXTRA_SHOW_WHEN));
            parcelable.putBoolean(Notification.EXTRA_SHOW_CHRONOMETER, bundle.getBoolean(Notification.EXTRA_SHOW_CHRONOMETER));
            parcelable.putBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN, bundle.getBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN));
            Object object = bundle.getString(Notification.EXTRA_SUBSTITUTE_APP_NAME);
            if (object != null) {
                parcelable.putString(Notification.EXTRA_SUBSTITUTE_APP_NAME, (String)object);
            }
            this.mN.extras = parcelable;
            object = this.makeNotificationHeader();
            ((RemoteViews)object).setBoolean(16909164, "setExpandOnlyOnButton", true);
            parcelable = this.mN;
            ((Notification)parcelable).extras = bundle;
            ((Notification)parcelable).mLargeIcon = icon;
            this.mN.largeIcon = bitmap;
            this.mActions = arrayList;
            this.mStyle = style2;
            return object;
        }

        public static Notification maybeCloneStrippedForDelivery(Notification notification, boolean bl, Context context) {
            String string2 = notification.extras.getString(Notification.EXTRA_TEMPLATE);
            if (!bl && !TextUtils.isEmpty(string2) && Notification.getNotificationStyleClass(string2) == null) {
                return notification;
            }
            boolean bl2 = notification.contentView instanceof BuilderRemoteViews;
            boolean bl3 = true;
            boolean bl4 = bl2 && notification.extras.getInt(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT, -1) == notification.contentView.getSequenceNumber();
            boolean bl5 = notification.bigContentView instanceof BuilderRemoteViews && notification.extras.getInt(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT, -1) == notification.bigContentView.getSequenceNumber();
            if (!(notification.headsUpContentView instanceof BuilderRemoteViews) || notification.extras.getInt(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT, -1) != notification.headsUpContentView.getSequenceNumber()) {
                bl3 = false;
            }
            if (!(bl || bl4 || bl5 || bl3)) {
                return notification;
            }
            notification = notification.clone();
            if (bl4) {
                notification.contentView = null;
                notification.extras.remove(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT);
            }
            if (bl5) {
                notification.bigContentView = null;
                notification.extras.remove(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT);
            }
            if (bl3) {
                notification.headsUpContentView = null;
                notification.extras.remove(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT);
            }
            if (bl && context.getResources().getStringArray(17235977).length == 0) {
                notification.extras.remove("android.tv.EXTENSIONS");
                notification.extras.remove("android.wearable.EXTENSIONS");
                notification.extras.remove("android.car.EXTENSIONS");
            }
            return notification;
        }

        private void processLargeLegacyIcon(Icon icon, RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (icon != null && this.isLegacy() && this.getColorUtil().isGrayscaleIcon(this.mContext, icon)) {
                remoteViews.setDrawableTint(16908294, false, this.resolveContrastColor(standardTemplateParams), PorterDuff.Mode.SRC_ATOP);
            }
        }

        private CharSequence processLegacyText(CharSequence charSequence) {
            boolean bl = this.isLegacy() || this.textColorsNeedInversion();
            if (bl) {
                return this.getColorUtil().invertCharSequenceColors(charSequence);
            }
            return charSequence;
        }

        private void processSmallIconColor(Icon icon, RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            boolean bl = this.isLegacy();
            int n = 1;
            boolean bl2 = !bl || this.getColorUtil().isGrayscaleIcon(this.mContext, icon);
            int n2 = this.isColorized(standardTemplateParams) ? this.getPrimaryTextColor(standardTemplateParams) : this.resolveContrastColor(standardTemplateParams);
            if (bl2) {
                remoteViews.setDrawableTint(16908294, false, n2, PorterDuff.Mode.SRC_ATOP);
            }
            if (bl2) {
                n = n2;
            }
            remoteViews.setInt(16909164, "setOriginalIconColor", n);
        }

        private CharSequence processTextSpans(CharSequence charSequence) {
            if (!this.hasForegroundColor() && !this.mInNightMode) {
                return charSequence;
            }
            return ContrastColorUtil.clearColorSpans(charSequence);
        }

        public static Builder recoverBuilder(Context context, Notification notification) {
            block2 : {
                ApplicationInfo applicationInfo = (ApplicationInfo)notification.extras.getParcelable(Notification.EXTRA_BUILDER_APPLICATION_INFO);
                if (applicationInfo == null) break block2;
                try {
                    Context context2;
                    context = context2 = context.createApplicationContext(applicationInfo, 4);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("ApplicationInfo ");
                    stringBuilder.append(applicationInfo);
                    stringBuilder.append(" not found");
                    Log.e(Notification.TAG, stringBuilder.toString());
                }
            }
            return new Builder(context, notification);
        }

        private void resetNotificationHeader(RemoteViews remoteViews) {
            remoteViews.setBoolean(16909164, "setExpanded", false);
            remoteViews.setTextViewText(16908731, null);
            remoteViews.setViewVisibility(16908808, 8);
            remoteViews.setViewVisibility(16908977, 8);
            remoteViews.setTextViewText(16908977, null);
            remoteViews.setViewVisibility(16908979, 8);
            remoteViews.setTextViewText(16908979, null);
            remoteViews.setViewVisibility(16908978, 8);
            remoteViews.setViewVisibility(16908980, 8);
            remoteViews.setViewVisibility(16909464, 8);
            remoteViews.setViewVisibility(16909460, 8);
            remoteViews.setImageViewIcon(16909257, null);
            remoteViews.setViewVisibility(16909257, 8);
            remoteViews.setViewVisibility(16908712, 8);
            this.mN.mUsesStandardHeader = false;
        }

        private void resetStandardTemplate(RemoteViews remoteViews) {
            this.resetNotificationHeader(remoteViews);
            remoteViews.setViewVisibility(16909294, 8);
            remoteViews.setViewVisibility(16908310, 8);
            remoteViews.setTextViewText(16908310, null);
            remoteViews.setViewVisibility(16909429, 8);
            remoteViews.setTextViewText(16909429, null);
            remoteViews.setViewVisibility(16909457, 8);
            remoteViews.setTextViewText(16909457, null);
        }

        private void resetStandardTemplateWithActions(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(16908694, 8);
            remoteViews.removeAllViews(16908694);
            remoteViews.setViewVisibility(16909166, 8);
            remoteViews.setTextViewText(16909168, null);
            remoteViews.setViewVisibility(16909169, 8);
            remoteViews.setViewVisibility(16909167, 8);
            remoteViews.setViewVisibility(16909170, 8);
            remoteViews.setTextViewText(16909170, null);
            remoteViews.setViewVisibility(16909171, 8);
            remoteViews.setTextViewText(16909171, null);
            remoteViews.setViewLayoutMarginBottomDimen(16909161, 17105306);
        }

        private int resolveBackgroundColor(StandardTemplateParams standardTemplateParams) {
            int n;
            int n2 = n = this.getBackgroundColor(standardTemplateParams);
            if (n == 0) {
                n2 = this.mContext.getColor(17170886);
            }
            return n2;
        }

        private void sanitizeColor() {
            if (this.mN.color != 0) {
                Notification notification = this.mN;
                notification.color |= -16777216;
            }
        }

        private void setTextViewColorPrimary(RemoteViews remoteViews, int n, StandardTemplateParams standardTemplateParams) {
            this.ensureColors(standardTemplateParams);
            remoteViews.setTextColor(n, this.mPrimaryTextColor);
        }

        private void setTextViewColorSecondary(RemoteViews remoteViews, int n, StandardTemplateParams standardTemplateParams) {
            this.ensureColors(standardTemplateParams);
            remoteViews.setTextColor(n, this.mSecondaryTextColor);
        }

        private boolean shouldTintActionButtons() {
            return this.mTintActionButtons;
        }

        private boolean showsTimeOrChronometer() {
            boolean bl = this.mN.showsTime() || this.mN.showsChronometer();
            return bl;
        }

        private boolean textColorsNeedInversion() {
            Style style2 = this.mStyle;
            boolean bl = false;
            if (style2 != null && MediaStyle.class.equals(style2.getClass())) {
                int n = this.mContext.getApplicationInfo().targetSdkVersion;
                boolean bl2 = bl;
                if (n > 23) {
                    bl2 = bl;
                    if (n < 26) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
            return false;
        }

        private void updateBackgroundColor(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (this.isColorized(standardTemplateParams)) {
                remoteViews.setInt(16909406, "setBackgroundColor", this.getBackgroundColor(standardTemplateParams));
            } else {
                remoteViews.setInt(16909406, "setBackgroundResource", 0);
            }
        }

        private boolean useExistingRemoteView() {
            Style style2 = this.mStyle;
            boolean bl = style2 == null || !style2.displayCustomViewInline() && !this.mRebuildStyledRemoteViews;
            return bl;
        }

        @Deprecated
        public Builder addAction(int n, CharSequence charSequence, PendingIntent pendingIntent) {
            this.mActions.add(new Action(n, Notification.safeCharSequence(charSequence), pendingIntent));
            return this;
        }

        public Builder addAction(Action action) {
            if (action != null) {
                this.mActions.add(action);
            }
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                this.mUserExtras.putAll(bundle);
            }
            return this;
        }

        public Builder addPerson(Person person) {
            this.mPersonList.add(person);
            return this;
        }

        public Builder addPerson(String string2) {
            this.addPerson(new Person.Builder().setUri(string2).build());
            return this;
        }

        public Notification build() {
            if (this.mUserExtras != null) {
                this.mN.extras = this.getAllExtras();
            }
            this.mN.creationTime = System.currentTimeMillis();
            Notification.addFieldsFromContext(this.mContext, this.mN);
            this.buildUnstyled();
            Object object = this.mStyle;
            if (object != null) {
                ((Style)object).reduceImageSizes(this.mContext);
                this.mStyle.purgeResources();
                this.mStyle.validate(this.mContext);
                this.mStyle.buildStyled(this.mN);
            }
            this.mN.reduceImageSizes(this.mContext);
            if (this.mContext.getApplicationInfo().targetSdkVersion < 24 && this.useExistingRemoteView()) {
                if (this.mN.contentView == null) {
                    this.mN.contentView = this.createContentView();
                    this.mN.extras.putInt(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT, this.mN.contentView.getSequenceNumber());
                }
                if (this.mN.bigContentView == null) {
                    this.mN.bigContentView = this.createBigContentView();
                    if (this.mN.bigContentView != null) {
                        this.mN.extras.putInt(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT, this.mN.bigContentView.getSequenceNumber());
                    }
                }
                if (this.mN.headsUpContentView == null) {
                    this.mN.headsUpContentView = this.createHeadsUpContentView();
                    if (this.mN.headsUpContentView != null) {
                        this.mN.extras.putInt(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT, this.mN.headsUpContentView.getSequenceNumber());
                    }
                }
            }
            if ((this.mN.defaults & 4) != 0) {
                object = this.mN;
                ((Notification)object).flags |= 1;
            }
            object = this.mN;
            ((Notification)object).allPendingIntents = null;
            return object;
        }

        public Notification buildInto(Notification notification) {
            this.build().cloneInto(notification, true);
            return notification;
        }

        public Notification buildUnstyled() {
            if (this.mActions.size() > 0) {
                this.mN.actions = new Action[this.mActions.size()];
                this.mActions.toArray(this.mN.actions);
            }
            if (!this.mPersonList.isEmpty()) {
                this.mN.extras.putParcelableArrayList(Notification.EXTRA_PEOPLE_LIST, this.mPersonList);
            }
            if (this.mN.bigContentView != null || this.mN.contentView != null || this.mN.headsUpContentView != null) {
                this.mN.extras.putBoolean(Notification.EXTRA_CONTAINS_CUSTOM_VIEW, true);
            }
            return this.mN;
        }

        public RemoteViews createBigContentView() {
            RemoteViews remoteViews = null;
            if (this.mN.bigContentView != null && this.useExistingRemoteView()) {
                return this.mN.bigContentView;
            }
            Style style2 = this.mStyle;
            if (style2 != null) {
                remoteViews = style2.makeBigContentView();
                this.hideLine1Text(remoteViews);
            } else if (this.mActions.size() != 0) {
                remoteViews = this.applyStandardTemplateWithActions(this.getBigBaseLayoutResource(), null);
            }
            Builder.makeHeaderExpanded(remoteViews);
            return remoteViews;
        }

        public RemoteViews createContentView() {
            return this.createContentView(false);
        }

        public RemoteViews createContentView(boolean bl) {
            if (this.mN.contentView != null && this.useExistingRemoteView()) {
                return this.mN.contentView;
            }
            Object object = this.mStyle;
            if (object != null && (object = ((Style)object).makeContentView(bl)) != null) {
                return object;
            }
            return this.applyStandardTemplate(this.getBaseLayoutResource(), null);
        }

        public RemoteViews createHeadsUpContentView() {
            return this.createHeadsUpContentView(false);
        }

        public RemoteViews createHeadsUpContentView(boolean bl) {
            if (this.mN.headsUpContentView != null && this.useExistingRemoteView()) {
                return this.mN.headsUpContentView;
            }
            Object object = this.mStyle;
            if (object != null) {
                if ((object = ((Style)object).makeHeadsUpContentView(bl)) != null) {
                    return object;
                }
            } else if (this.mActions.size() == 0) {
                return null;
            }
            object = this.mParams.reset().fillTextsFrom(this).setMaxRemoteInputHistory(1);
            return this.applyStandardTemplateWithActions(this.getBigBaseLayoutResource(), (StandardTemplateParams)object, null);
        }

        public Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        public Bundle getExtras() {
            return this.mUserExtras;
        }

        public CharSequence getHeadsUpStatusBarText(boolean bl) {
            Object object = this.mStyle;
            if (object != null && !bl && !TextUtils.isEmpty((CharSequence)(object = ((Style)object).getHeadsUpStatusBarText()))) {
                return object;
            }
            return this.loadHeaderAppName();
        }

        @Deprecated
        public Notification getNotification() {
            return this.build();
        }

        @VisibleForTesting
        public int getPrimaryTextColor() {
            return this.getPrimaryTextColor(this.mParams);
        }

        @VisibleForTesting
        public int getPrimaryTextColor(StandardTemplateParams standardTemplateParams) {
            this.ensureColors(standardTemplateParams);
            return this.mPrimaryTextColor;
        }

        @VisibleForTesting
        public int getSecondaryTextColor() {
            return this.getSecondaryTextColor(this.mParams);
        }

        @VisibleForTesting
        public int getSecondaryTextColor(StandardTemplateParams standardTemplateParams) {
            this.ensureColors(standardTemplateParams);
            return this.mSecondaryTextColor;
        }

        public Style getStyle() {
            return this.mStyle;
        }

        @UnsupportedAppUsage
        public String loadHeaderAppName() {
            CharSequence charSequence = null;
            PackageManager packageManager = this.mContext.getPackageManager();
            String string2 = charSequence;
            if (this.mN.extras.containsKey(Notification.EXTRA_SUBSTITUTE_APP_NAME)) {
                String string3 = this.mContext.getPackageName();
                string2 = this.mN.extras.getString(Notification.EXTRA_SUBSTITUTE_APP_NAME);
                if (packageManager.checkPermission("android.permission.SUBSTITUTE_NOTIFICATION_APP_NAME", string3) != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("warning: pkg ");
                    stringBuilder.append(string3);
                    stringBuilder.append(" attempting to substitute app name '");
                    stringBuilder.append(string2);
                    stringBuilder.append("' without holding perm ");
                    stringBuilder.append("android.permission.SUBSTITUTE_NOTIFICATION_APP_NAME");
                    Log.w(Notification.TAG, stringBuilder.toString());
                    string2 = charSequence;
                }
            }
            charSequence = string2;
            if (TextUtils.isEmpty(string2)) {
                charSequence = packageManager.getApplicationLabel(this.mContext.getApplicationInfo());
            }
            if (TextUtils.isEmpty(charSequence)) {
                return null;
            }
            return String.valueOf(charSequence);
        }

        public RemoteViews makeAmbientNotification() {
            RemoteViews remoteViews = this.createHeadsUpContentView(false);
            if (remoteViews != null) {
                return remoteViews;
            }
            return this.createContentView();
        }

        public RemoteViews makeLowPriorityContentView(boolean bl) {
            Object object = this.mParams.reset().forceDefaultColor().fillTextsFrom(this);
            if (!bl || TextUtils.isEmpty(this.mParams.summaryText)) {
                ((StandardTemplateParams)object).summaryText(this.createSummaryText());
            }
            object = this.makeNotificationHeader((StandardTemplateParams)object);
            ((RemoteViews)object).setBoolean(16909164, "setAcceptAllTouches", true);
            return object;
        }

        public RemoteViews makeNotificationHeader() {
            return this.makeNotificationHeader(this.mParams.reset().fillTextsFrom(this));
        }

        public RemoteViews makePublicAmbientNotification() {
            return this.makePublicView(true);
        }

        @UnsupportedAppUsage
        public RemoteViews makePublicContentView() {
            return this.makePublicView(false);
        }

        int resolveContrastColor(StandardTemplateParams standardTemplateParams) {
            int n;
            int n2 = this.getRawColor(standardTemplateParams);
            if (this.mCachedContrastColorIsFor == n2 && (n = this.mCachedContrastColor) != 1) {
                return n;
            }
            int n3 = this.mContext.getColor(17170886);
            if (n2 == 0) {
                this.ensureColors(standardTemplateParams);
                n = ContrastColorUtil.resolveDefaultColor(this.mContext, n3, this.mInNightMode);
            } else {
                n = ContrastColorUtil.resolveContrastColor(this.mContext, n2, n3, this.mInNightMode);
            }
            int n4 = n;
            if (Color.alpha(n) < 255) {
                n4 = ContrastColorUtil.compositeColors(n, n3);
            }
            this.mCachedContrastColorIsFor = n2;
            this.mCachedContrastColor = n4;
            return n4;
        }

        int resolveNeutralColor() {
            int n = this.mNeutralColor;
            if (n != 1) {
                return n;
            }
            n = this.mContext.getColor(17170886);
            this.mNeutralColor = ContrastColorUtil.resolveDefaultColor(this.mContext, n, this.mInNightMode);
            if (Color.alpha(this.mNeutralColor) < 255) {
                this.mNeutralColor = ContrastColorUtil.compositeColors(this.mNeutralColor, n);
            }
            return this.mNeutralColor;
        }

        public Builder setActions(Action ... arraction) {
            this.mActions.clear();
            for (int i = 0; i < arraction.length; ++i) {
                if (arraction[i] == null) continue;
                this.mActions.add(arraction[i]);
            }
            return this;
        }

        public Builder setAllowSystemGeneratedContextualActions(boolean bl) {
            this.mN.mAllowSystemGeneratedContextualActions = bl;
            return this;
        }

        public Builder setAutoCancel(boolean bl) {
            this.setFlag(16, bl);
            return this;
        }

        public Builder setBadgeIconType(int n) {
            this.mN.mBadgeIcon = n;
            return this;
        }

        public Builder setBubbleMetadata(BubbleMetadata bubbleMetadata) {
            this.mN.mBubbleMetadata = bubbleMetadata;
            return this;
        }

        public Builder setCategory(String string2) {
            this.mN.category = string2;
            return this;
        }

        @Deprecated
        public Builder setChannel(String string2) {
            this.mN.mChannelId = string2;
            return this;
        }

        public Builder setChannelId(String string2) {
            this.mN.mChannelId = string2;
            return this;
        }

        public Builder setChronometerCountDown(boolean bl) {
            this.mN.extras.putBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN, bl);
            return this;
        }

        public Builder setColor(int n) {
            this.mN.color = n;
            this.sanitizeColor();
            return this;
        }

        public void setColorPalette(int n, int n2) {
            this.mBackgroundColor = n;
            this.mForegroundColor = n2;
            this.mTextColorsAreForBackground = 1;
            this.ensureColors(this.mParams.reset().fillTextsFrom(this));
        }

        public Builder setColorized(boolean bl) {
            this.mN.extras.putBoolean(Notification.EXTRA_COLORIZED, bl);
            return this;
        }

        @Deprecated
        public Builder setContent(RemoteViews remoteViews) {
            return this.setCustomContentView(remoteViews);
        }

        @Deprecated
        public Builder setContentInfo(CharSequence charSequence) {
            this.mN.extras.putCharSequence(Notification.EXTRA_INFO_TEXT, Notification.safeCharSequence(charSequence));
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingIntent) {
            this.mN.contentIntent = pendingIntent;
            return this;
        }

        void setContentMinHeight(RemoteViews remoteViews, boolean bl) {
            int n = 0;
            if (bl) {
                n = this.mContext.getResources().getDimensionPixelSize(17105335);
            }
            remoteViews.setInt(16909165, "setMinimumHeight", n);
        }

        public Builder setContentText(CharSequence charSequence) {
            this.mN.extras.putCharSequence(Notification.EXTRA_TEXT, Notification.safeCharSequence(charSequence));
            return this;
        }

        public Builder setContentTitle(CharSequence charSequence) {
            this.mN.extras.putCharSequence(Notification.EXTRA_TITLE, Notification.safeCharSequence(charSequence));
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews remoteViews) {
            this.mN.bigContentView = remoteViews;
            return this;
        }

        public Builder setCustomContentView(RemoteViews remoteViews) {
            this.mN.contentView = remoteViews;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews remoteViews) {
            this.mN.headsUpContentView = remoteViews;
            return this;
        }

        @Deprecated
        public Builder setDefaults(int n) {
            this.mN.defaults = n;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingIntent) {
            this.mN.deleteIntent = pendingIntent;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            if (bundle != null) {
                this.mUserExtras = bundle;
            }
            return this;
        }

        public Builder setFlag(int n, boolean bl) {
            if (bl) {
                Notification notification = this.mN;
                notification.flags |= n;
            } else {
                Notification notification = this.mN;
                notification.flags &= n;
            }
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent pendingIntent, boolean bl) {
            this.mN.fullScreenIntent = pendingIntent;
            this.setFlag(128, bl);
            return this;
        }

        public Builder setGroup(String string2) {
            this.mN.mGroupKey = string2;
            return this;
        }

        public Builder setGroupAlertBehavior(int n) {
            this.mN.mGroupAlertBehavior = n;
            return this;
        }

        public Builder setGroupSummary(boolean bl) {
            this.setFlag(512, bl);
            return this;
        }

        public Builder setHideSmartReplies(boolean bl) {
            this.mN.extras.putBoolean(Notification.EXTRA_HIDE_SMART_REPLIES, bl);
            return this;
        }

        public Builder setLargeIcon(Bitmap parcelable) {
            parcelable = parcelable != null ? Icon.createWithBitmap(parcelable) : null;
            return this.setLargeIcon((Icon)parcelable);
        }

        public Builder setLargeIcon(Icon icon) {
            this.mN.mLargeIcon = icon;
            this.mN.extras.putParcelable(Notification.EXTRA_LARGE_ICON, icon);
            return this;
        }

        @Deprecated
        public Builder setLights(int n, int n2, int n3) {
            Notification notification = this.mN;
            notification.ledARGB = n;
            notification.ledOnMS = n2;
            notification.ledOffMS = n3;
            if (n2 != 0 || n3 != 0) {
                notification = this.mN;
                notification.flags |= 1;
            }
            return this;
        }

        public Builder setLocalOnly(boolean bl) {
            this.setFlag(256, bl);
            return this;
        }

        public Builder setLocusId(LocusId locusId) {
            this.mN.mLocusId = locusId;
            return this;
        }

        public Builder setNumber(int n) {
            this.mN.number = n;
            return this;
        }

        public Builder setOngoing(boolean bl) {
            this.setFlag(2, bl);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean bl) {
            this.setFlag(8, bl);
            return this;
        }

        @Deprecated
        public Builder setPriority(int n) {
            this.mN.priority = n;
            return this;
        }

        public Builder setProgress(int n, int n2, boolean bl) {
            this.mN.extras.putInt(Notification.EXTRA_PROGRESS, n2);
            this.mN.extras.putInt(Notification.EXTRA_PROGRESS_MAX, n);
            this.mN.extras.putBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE, bl);
            return this;
        }

        public Builder setPublicVersion(Notification notification) {
            if (notification != null) {
                this.mN.publicVersion = new Notification();
                notification.cloneInto(this.mN.publicVersion, true);
            } else {
                this.mN.publicVersion = null;
            }
            return this;
        }

        public void setRebuildStyledRemoteViews(boolean bl) {
            this.mRebuildStyledRemoteViews = bl;
        }

        public Builder setRemoteInputHistory(CharSequence[] arrcharSequence) {
            if (arrcharSequence == null) {
                this.mN.extras.putCharSequenceArray(Notification.EXTRA_REMOTE_INPUT_HISTORY, null);
            } else {
                int n = Math.min(5, arrcharSequence.length);
                CharSequence[] arrcharSequence2 = new CharSequence[n];
                for (int i = 0; i < n; ++i) {
                    arrcharSequence2[i] = Notification.safeCharSequence(arrcharSequence[i]);
                }
                this.mN.extras.putCharSequenceArray(Notification.EXTRA_REMOTE_INPUT_HISTORY, arrcharSequence2);
            }
            return this;
        }

        public Builder setSettingsText(CharSequence charSequence) {
            this.mN.mSettingsText = Notification.safeCharSequence(charSequence);
            return this;
        }

        public Builder setShortcutId(String string2) {
            this.mN.mShortcutId = string2;
            return this;
        }

        public Builder setShowRemoteInputSpinner(boolean bl) {
            this.mN.extras.putBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, bl);
            return this;
        }

        public Builder setShowWhen(boolean bl) {
            this.mN.extras.putBoolean(Notification.EXTRA_SHOW_WHEN, bl);
            return this;
        }

        public Builder setSmallIcon(int n) {
            Icon icon = n != 0 ? Icon.createWithResource(this.mContext, n) : null;
            return this.setSmallIcon(icon);
        }

        public Builder setSmallIcon(int n, int n2) {
            this.mN.iconLevel = n2;
            return this.setSmallIcon(n);
        }

        public Builder setSmallIcon(Icon icon) {
            this.mN.setSmallIcon(icon);
            if (icon != null && icon.getType() == 2) {
                this.mN.icon = icon.getResId();
            }
            return this;
        }

        public Builder setSortKey(String string2) {
            this.mN.mSortKey = string2;
            return this;
        }

        @Deprecated
        public Builder setSound(Uri uri) {
            Notification notification = this.mN;
            notification.sound = uri;
            notification.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
            return this;
        }

        @Deprecated
        public Builder setSound(Uri uri, int n) {
            PlayerBase.deprecateStreamTypeForPlayback(n, Notification.TAG, "setSound()");
            Notification notification = this.mN;
            notification.sound = uri;
            notification.audioStreamType = n;
            return this;
        }

        @Deprecated
        public Builder setSound(Uri uri, AudioAttributes audioAttributes) {
            Notification notification = this.mN;
            notification.sound = uri;
            notification.audioAttributes = audioAttributes;
            return this;
        }

        public Builder setStyle(Style style2) {
            if (this.mStyle != style2) {
                this.mStyle = style2;
                Style style3 = this.mStyle;
                if (style3 != null) {
                    style3.setBuilder(this);
                    this.mN.extras.putString(Notification.EXTRA_TEMPLATE, style2.getClass().getName());
                } else {
                    this.mN.extras.remove(Notification.EXTRA_TEMPLATE);
                }
            }
            return this;
        }

        public Builder setSubText(CharSequence charSequence) {
            this.mN.extras.putCharSequence(Notification.EXTRA_SUB_TEXT, Notification.safeCharSequence(charSequence));
            return this;
        }

        public Builder setTicker(CharSequence charSequence) {
            this.mN.tickerText = Notification.safeCharSequence(charSequence);
            return this;
        }

        @Deprecated
        public Builder setTicker(CharSequence charSequence, RemoteViews remoteViews) {
            this.setTicker(charSequence);
            return this;
        }

        @Deprecated
        public Builder setTimeout(long l) {
            this.mN.mTimeout = l;
            return this;
        }

        public Builder setTimeoutAfter(long l) {
            this.mN.mTimeout = l;
            return this;
        }

        public Builder setUsesChronometer(boolean bl) {
            this.mN.extras.putBoolean(Notification.EXTRA_SHOW_CHRONOMETER, bl);
            return this;
        }

        @Deprecated
        public Builder setVibrate(long[] arrl) {
            this.mN.vibrate = arrl;
            return this;
        }

        public Builder setVisibility(int n) {
            this.mN.visibility = n;
            return this;
        }

        public Builder setWhen(long l) {
            this.mN.when = l;
            return this;
        }

        public boolean usesStandardHeader() {
            boolean bl = this.mN.mUsesStandardHeader;
            boolean bl2 = true;
            if (bl) {
                return true;
            }
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 24 && this.mN.contentView == null && this.mN.bigContentView == null) {
                return true;
            }
            boolean bl3 = this.mN.contentView == null || STANDARD_LAYOUTS.contains(this.mN.contentView.getLayoutId());
            boolean bl4 = this.mN.bigContentView == null || STANDARD_LAYOUTS.contains(this.mN.bigContentView.getLayoutId());
            if (!bl3 || !bl4) {
                bl2 = false;
            }
            return bl2;
        }
    }

    private static class BuilderRemoteViews
    extends RemoteViews {
        public BuilderRemoteViews(ApplicationInfo applicationInfo, int n) {
            super(applicationInfo, n);
        }

        public BuilderRemoteViews(Parcel parcel) {
            super(parcel);
        }

        @Override
        public BuilderRemoteViews clone() {
            Parcel parcel = Parcel.obtain();
            this.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            BuilderRemoteViews builderRemoteViews = new BuilderRemoteViews(parcel);
            parcel.recycle();
            return builderRemoteViews;
        }
    }

    public static final class CarExtender
    implements Extender {
        private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String TAG = "CarExtender";
        private int mColor = 0;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        public CarExtender() {
        }

        public CarExtender(Notification parcelable) {
            parcelable = parcelable.extras == null ? null : parcelable.extras.getBundle(EXTRA_CAR_EXTENDER);
            if (parcelable != null) {
                this.mLargeIcon = (Bitmap)((Bundle)parcelable).getParcelable(EXTRA_LARGE_ICON);
                this.mColor = ((BaseBundle)((Object)parcelable)).getInt(EXTRA_COLOR, 0);
                this.mUnreadConversation = UnreadConversation.getUnreadConversationFromBundle(((Bundle)parcelable).getBundle(EXTRA_CONVERSATION));
            }
        }

        @Override
        public android.app.Notification$Builder extend(android.app.Notification$Builder builder) {
            int n;
            Bundle bundle = new Bundle();
            Object object = this.mLargeIcon;
            if (object != null) {
                bundle.putParcelable(EXTRA_LARGE_ICON, (Parcelable)object);
            }
            if ((n = this.mColor) != 0) {
                bundle.putInt(EXTRA_COLOR, n);
            }
            if ((object = this.mUnreadConversation) != null) {
                bundle.putBundle(EXTRA_CONVERSATION, ((UnreadConversation)object).getBundleForUnreadConversation());
            }
            builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, bundle);
            return builder;
        }

        public int getColor() {
            return this.mColor;
        }

        public Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        public UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }

        public CarExtender setColor(int n) {
            this.mColor = n;
            return this;
        }

        public CarExtender setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        public static class Builder {
            private long mLatestTimestamp;
            private final List<String> mMessages = new ArrayList<String>();
            private final String mParticipant;
            private PendingIntent mReadPendingIntent;
            private RemoteInput mRemoteInput;
            private PendingIntent mReplyPendingIntent;

            public Builder(String string2) {
                this.mParticipant = string2;
            }

            public Builder addMessage(String string2) {
                this.mMessages.add(string2);
                return this;
            }

            public UnreadConversation build() {
                String[] arrstring = this.mMessages;
                arrstring = arrstring.toArray(new String[arrstring.size()]);
                String string2 = this.mParticipant;
                RemoteInput remoteInput = this.mRemoteInput;
                PendingIntent pendingIntent = this.mReplyPendingIntent;
                PendingIntent pendingIntent2 = this.mReadPendingIntent;
                long l = this.mLatestTimestamp;
                return new UnreadConversation(arrstring, remoteInput, pendingIntent, pendingIntent2, new String[]{string2}, l);
            }

            public Builder setLatestTimestamp(long l) {
                this.mLatestTimestamp = l;
                return this;
            }

            public Builder setReadPendingIntent(PendingIntent pendingIntent) {
                this.mReadPendingIntent = pendingIntent;
                return this;
            }

            public Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
                this.mRemoteInput = remoteInput;
                this.mReplyPendingIntent = pendingIntent;
                return this;
            }
        }

        public static class UnreadConversation {
            private static final String KEY_AUTHOR = "author";
            private static final String KEY_MESSAGES = "messages";
            private static final String KEY_ON_READ = "on_read";
            private static final String KEY_ON_REPLY = "on_reply";
            private static final String KEY_PARTICIPANTS = "participants";
            private static final String KEY_REMOTE_INPUT = "remote_input";
            private static final String KEY_TEXT = "text";
            private static final String KEY_TIMESTAMP = "timestamp";
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            UnreadConversation(String[] arrstring, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] arrstring2, long l) {
                this.mMessages = arrstring;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = pendingIntent2;
                this.mReplyPendingIntent = pendingIntent;
                this.mParticipants = arrstring2;
                this.mLatestTimestamp = l;
            }

            static UnreadConversation getUnreadConversationFromBundle(Bundle bundle) {
                if (bundle == null) {
                    return null;
                }
                Object object = bundle.getParcelableArray(KEY_MESSAGES);
                String[] arrstring = null;
                if (object != null) {
                    boolean bl;
                    arrstring = new String[((Parcelable[])object).length];
                    boolean bl2 = true;
                    int n = 0;
                    do {
                        bl = bl2;
                        if (n >= arrstring.length) break;
                        if (!(object[n] instanceof Bundle)) {
                            bl = false;
                            break;
                        }
                        arrstring[n] = ((Bundle)object[n]).getString(KEY_TEXT);
                        if (arrstring[n] == null) {
                            bl = false;
                            break;
                        }
                        ++n;
                    } while (true);
                    if (!bl) {
                        return null;
                    }
                }
                PendingIntent pendingIntent = (PendingIntent)bundle.getParcelable(KEY_ON_READ);
                PendingIntent pendingIntent2 = (PendingIntent)bundle.getParcelable(KEY_ON_REPLY);
                object = (RemoteInput)bundle.getParcelable(KEY_REMOTE_INPUT);
                String[] arrstring2 = bundle.getStringArray(KEY_PARTICIPANTS);
                if (arrstring2 != null && arrstring2.length == 1) {
                    return new UnreadConversation(arrstring, (RemoteInput)object, pendingIntent2, pendingIntent, arrstring2, bundle.getLong(KEY_TIMESTAMP));
                }
                return null;
            }

            Bundle getBundleForUnreadConversation() {
                Bundle bundle = new Bundle();
                Object object = null;
                Object[] arrobject = this.mParticipants;
                Object object2 = object;
                if (arrobject != null) {
                    object2 = object;
                    if (arrobject.length > 1) {
                        object2 = arrobject[0];
                    }
                }
                arrobject = new Parcelable[this.mMessages.length];
                for (int i = 0; i < arrobject.length; ++i) {
                    object = new Bundle();
                    ((BaseBundle)object).putString(KEY_TEXT, this.mMessages[i]);
                    ((BaseBundle)object).putString(KEY_AUTHOR, (String)object2);
                    arrobject[i] = object;
                }
                bundle.putParcelableArray(KEY_MESSAGES, (Parcelable[])arrobject);
                object2 = this.mRemoteInput;
                if (object2 != null) {
                    bundle.putParcelable(KEY_REMOTE_INPUT, (Parcelable)object2);
                }
                bundle.putParcelable(KEY_ON_REPLY, this.mReplyPendingIntent);
                bundle.putParcelable(KEY_ON_READ, this.mReadPendingIntent);
                bundle.putStringArray(KEY_PARTICIPANTS, this.mParticipants);
                bundle.putLong(KEY_TIMESTAMP, this.mLatestTimestamp);
                return bundle;
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }

            public String[] getMessages() {
                return this.mMessages;
            }

            public String getParticipant() {
                Object object = this.mParticipants;
                object = ((String[])object).length > 0 ? object[0] : null;
                return object;
            }

            public String[] getParticipants() {
                return this.mParticipants;
            }

            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            public RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }
        }

    }

    public static class DecoratedCustomViewStyle
    extends Style {
        private void buildIntoRemoteViewContent(RemoteViews remoteViews, RemoteViews remoteViews2, TemplateBindResult templateBindResult) {
            int n = -1;
            if (remoteViews2 != null) {
                remoteViews2 = remoteViews2.clone();
                remoteViews.removeAllViewsExceptId(16909165, 16908301);
                remoteViews.addView(16909165, remoteViews2, 0);
                remoteViews.addFlags(1);
                n = 0;
            }
            remoteViews.setIntTag(16909165, 16909163, n);
            remoteViews.setViewLayoutMarginEnd(16909165, this.mBuilder.mContext.getResources().getDimensionPixelSize(17105307) + templateBindResult.getIconMarginEnd());
        }

        private RemoteViews makeDecoratedBigContentView() {
            RemoteViews remoteViews = Builder.access$300((Builder)this.mBuilder).bigContentView == null ? Builder.access$300((Builder)this.mBuilder).contentView : Builder.access$300((Builder)this.mBuilder).bigContentView;
            if (this.mBuilder.mActions.size() == 0) {
                return this.makeStandardTemplateWithCustomContent(remoteViews);
            }
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews remoteViews2 = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getBigBaseLayoutResource(), templateBindResult);
            this.buildIntoRemoteViewContent(remoteViews2, remoteViews, templateBindResult);
            return remoteViews2;
        }

        private RemoteViews makeDecoratedHeadsUpContentView() {
            RemoteViews remoteViews = Builder.access$300((Builder)this.mBuilder).headsUpContentView == null ? Builder.access$300((Builder)this.mBuilder).contentView : Builder.access$300((Builder)this.mBuilder).headsUpContentView;
            if (this.mBuilder.mActions.size() == 0) {
                return this.makeStandardTemplateWithCustomContent(remoteViews);
            }
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews remoteViews2 = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getBigBaseLayoutResource(), templateBindResult);
            this.buildIntoRemoteViewContent(remoteViews2, remoteViews, templateBindResult);
            return remoteViews2;
        }

        private RemoteViews makeStandardTemplateWithCustomContent(RemoteViews remoteViews) {
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews remoteViews2 = this.mBuilder.applyStandardTemplate(this.mBuilder.getBaseLayoutResource(), templateBindResult);
            this.buildIntoRemoteViewContent(remoteViews2, remoteViews, templateBindResult);
            return remoteViews2;
        }

        @Override
        public boolean areNotificationsVisiblyDifferent(Style style2) {
            return style2 == null || this.getClass() != style2.getClass();
            {
            }
        }

        @Override
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override
        public RemoteViews makeBigContentView() {
            return this.makeDecoratedBigContentView();
        }

        @Override
        public RemoteViews makeContentView(boolean bl) {
            return this.makeStandardTemplateWithCustomContent(Builder.access$300((Builder)this.mBuilder).contentView);
        }

        @Override
        public RemoteViews makeHeadsUpContentView(boolean bl) {
            return this.makeDecoratedHeadsUpContentView();
        }
    }

    public static class DecoratedMediaCustomViewStyle
    extends MediaStyle {
        private RemoteViews buildIntoRemoteView(RemoteViews remoteViews, int n, RemoteViews remoteViews2) {
            if (remoteViews2 != null) {
                remoteViews2 = remoteViews2.clone();
                remoteViews2.overrideTextColors(this.mBuilder.getPrimaryTextColor(this.mBuilder.mParams));
                remoteViews.removeAllViews(n);
                remoteViews.addView(n, remoteViews2);
                remoteViews.addFlags(1);
            }
            return remoteViews;
        }

        private RemoteViews makeBigContentViewWithCustomContent(RemoteViews remoteViews) {
            RemoteViews remoteViews2 = super.makeBigContentView();
            if (remoteViews2 != null) {
                return this.buildIntoRemoteView(remoteViews2, 16909165, remoteViews);
            }
            if (remoteViews != Builder.access$300((Builder)this.mBuilder).contentView) {
                return this.buildIntoRemoteView(super.makeContentView(false), 16909162, remoteViews);
            }
            return null;
        }

        @Override
        public boolean areNotificationsVisiblyDifferent(Style style2) {
            return style2 == null || this.getClass() != style2.getClass();
            {
            }
        }

        @Override
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override
        public RemoteViews makeBigContentView() {
            RemoteViews remoteViews = Builder.access$300((Builder)this.mBuilder).bigContentView != null ? Builder.access$300((Builder)this.mBuilder).bigContentView : Builder.access$300((Builder)this.mBuilder).contentView;
            return this.makeBigContentViewWithCustomContent(remoteViews);
        }

        @Override
        public RemoteViews makeContentView(boolean bl) {
            return this.buildIntoRemoteView(super.makeContentView(false), 16909162, Builder.access$300((Builder)this.mBuilder).contentView);
        }

        @Override
        public RemoteViews makeHeadsUpContentView(boolean bl) {
            RemoteViews remoteViews = Builder.access$300((Builder)this.mBuilder).headsUpContentView != null ? Builder.access$300((Builder)this.mBuilder).headsUpContentView : Builder.access$300((Builder)this.mBuilder).contentView;
            return this.makeBigContentViewWithCustomContent(remoteViews);
        }
    }

    public static interface Extender {
        public Builder extend(Builder var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GroupAlertBehavior {
    }

    public static class InboxStyle
    extends Style {
        private static final int NUMBER_OF_HISTORY_ALLOWED_UNTIL_REDUCTION = 1;
        private ArrayList<CharSequence> mTexts = new ArrayList(5);

        public InboxStyle() {
        }

        @Deprecated
        public InboxStyle(Builder builder) {
            this.setBuilder(builder);
        }

        private void handleInboxImageMargin(RemoteViews remoteViews, int n, boolean bl, int n2) {
            int n3;
            int n4 = n3 = 0;
            if (bl) {
                Bundle bundle = Builder.access$300((Builder)this.mBuilder).extras;
                boolean bl2 = false;
                n4 = bundle.getInt(Notification.EXTRA_PROGRESS_MAX, 0);
                bl = Builder.access$300((Builder)this.mBuilder).extras.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE);
                if (n4 != 0 || bl) {
                    bl2 = true;
                }
                n4 = n3;
                if (!bl2) {
                    n4 = n2;
                }
            }
            remoteViews.setViewLayoutMarginEnd(n, n4);
        }

        @Override
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            CharSequence[] arrcharSequence = new CharSequence[this.mTexts.size()];
            bundle.putCharSequenceArray(Notification.EXTRA_TEXT_LINES, this.mTexts.toArray(arrcharSequence));
        }

        public InboxStyle addLine(CharSequence charSequence) {
            this.mTexts.add(Notification.safeCharSequence(charSequence));
            return this;
        }

        @Override
        public boolean areNotificationsVisiblyDifferent(Style object) {
            if (object != null && this.getClass() == object.getClass()) {
                Object object2 = (InboxStyle)object;
                object = this.getLines();
                object2 = ((InboxStyle)object2).getLines();
                int n = ((ArrayList)object).size();
                if (n != ((ArrayList)object2).size()) {
                    return true;
                }
                for (int i = 0; i < n; ++i) {
                    if (Objects.equals(String.valueOf(((ArrayList)object).get(i)), String.valueOf(((ArrayList)object2).get(i)))) continue;
                    return true;
                }
                return false;
            }
            return true;
        }

        public ArrayList<CharSequence> getLines() {
            return this.mTexts;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public RemoteViews makeBigContentView() {
            var1_1 = this.mBuilder.mParams.reset().fillTextsFrom(this.mBuilder).text(null);
            var2_2 = new TemplateBindResult();
            var3_3 = this.getStandardView(Builder.access$3900(this.mBuilder), var1_1, var2_2);
            v0 = var4_4 = new int[7];
            v0[0] = 16909007;
            v0[1] = 16909008;
            v0[2] = 16909009;
            v0[3] = 16909010;
            v0[4] = 16909011;
            v0[5] = 16909012;
            v0[6] = 16909013;
            var5_5 = var4_4.length;
            for (var6_6 = 0; var6_6 < var5_5; ++var6_6) {
                var3_3.setViewVisibility(var4_4[var6_6], 8);
            }
            var7_7 = Builder.access$3400(this.mBuilder).getResources().getDimensionPixelSize(17105327);
            var6_6 = var5_5 = var4_4.length;
            if (Builder.access$2900(this.mBuilder).size() > 0) {
                var6_6 = var5_5 - 1;
            }
            if ((var8_8 = Builder.access$300((Builder)this.mBuilder).extras.getCharSequenceArray("android.remoteInputHistory")) == null || var8_8.length <= 1) ** GOTO lbl-1000
            var5_5 = Math.min(var8_8.length, 3);
            var5_5 = this.mTexts.size() + var5_5 - 1;
            if (var5_5 > var6_6) {
                var9_11 = var5_5 - var6_6;
                if (this.mTexts.size() > var6_6) {
                    var10_12 = 0;
                    var11_13 = true;
                    var5_5 = 0;
                    var9_11 = var6_6 - var9_11;
                    var6_6 = var10_12;
                    var10_12 = var9_11;
                } else {
                    var5_5 = var9_11;
                    var11_13 = true;
                    var9_11 = 0;
                    var10_12 = var6_6;
                    var6_6 = var5_5;
                    var5_5 = var9_11;
                }
            } else lbl-1000: // 2 sources:
            {
                var9_11 = 0;
                var11_13 = true;
                var5_5 = 0;
                var10_12 = var6_6;
                var6_6 = var9_11;
            }
            while (var6_6 < this.mTexts.size() && var6_6 < var10_12) {
                var8_10 = this.mTexts.get(var6_6);
                if (!TextUtils.isEmpty(var8_10)) {
                    var3_3.setViewVisibility(var4_4[var6_6], 0);
                    var3_3.setTextViewText(var4_4[var6_6], Builder.access$2600(this.mBuilder, Builder.access$2500(this.mBuilder, var8_10)));
                    Builder.access$2700(this.mBuilder, var3_3, var4_4[var6_6], var1_1);
                    var3_3.setViewPadding(var4_4[var6_6], 0, var7_7, 0, 0);
                    this.handleInboxImageMargin(var3_3, var4_4[var6_6], var11_13, var2_2.getIconMarginEnd());
                    var5_5 = var11_13 != false ? var4_4[var6_6] : 0;
                    var11_13 = false;
                }
                ++var6_6;
            }
            if (var5_5 == 0) return var3_3;
            var3_3.setViewPadding(var5_5, 0, Builder.access$3400(this.mBuilder).getResources().getDimensionPixelSize(17105344), 0, 0);
            return var3_3;
        }

        @Override
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mTexts.clear();
            if (bundle.containsKey(Notification.EXTRA_TEXT_LINES)) {
                Collections.addAll(this.mTexts, bundle.getCharSequenceArray(Notification.EXTRA_TEXT_LINES));
            }
        }

        public InboxStyle setBigContentTitle(CharSequence charSequence) {
            this.internalSetBigContentTitle(Notification.safeCharSequence(charSequence));
            return this;
        }

        public InboxStyle setSummaryText(CharSequence charSequence) {
            this.internalSetSummaryText(Notification.safeCharSequence(charSequence));
            return this;
        }
    }

    public static class MediaStyle
    extends Style {
        static final int MAX_MEDIA_BUTTONS = 5;
        static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;
        private static final int[] MEDIA_BUTTON_IDS = new int[]{16908669, 16908670, 16908671, 16908672, 16908673};
        private int[] mActionsToShowInCompact = null;
        private MediaSession.Token mToken;

        public MediaStyle() {
        }

        @Deprecated
        public MediaStyle(Builder builder) {
            this.setBuilder(builder);
        }

        private void bindMediaActionButton(RemoteViews remoteViews, int n, Action action, StandardTemplateParams object) {
            boolean bl = action.actionIntent == null;
            remoteViews.setViewVisibility(n, 0);
            remoteViews.setImageViewIcon(n, action.getIcon());
            boolean bl2 = (Builder.access$3400((Builder)this.mBuilder).getResources().getConfiguration().uiMode & 48) == 32;
            int n2 = !this.mBuilder.shouldTintActionButtons() && !this.mBuilder.isColorized((StandardTemplateParams)object) ? ContrastColorUtil.resolveColor(this.mBuilder.mContext, 0, bl2) : this.getActionColor((StandardTemplateParams)object);
            remoteViews.setDrawableTint(n, false, n2, PorterDuff.Mode.SRC_ATOP);
            object = this.mBuilder.mContext.obtainStyledAttributes(new int[]{16843820});
            int n3 = Color.alpha(((TypedArray)object).getColor(0, 0));
            ((TypedArray)object).recycle();
            remoteViews.setRippleDrawableColor(n, ColorStateList.valueOf(Color.argb(n3, Color.red(n2), Color.green(n2), Color.blue(n2))));
            if (!bl) {
                remoteViews.setOnClickPendingIntent(n, action.actionIntent);
            }
            remoteViews.setContentDescription(n, action.title);
        }

        private int getActionColor(StandardTemplateParams standardTemplateParams) {
            int n = this.mBuilder.isColorized(standardTemplateParams) ? this.mBuilder.getPrimaryTextColor(standardTemplateParams) : this.mBuilder.resolveContrastColor(standardTemplateParams);
            return n;
        }

        private void handleImage(RemoteViews remoteViews) {
            if (this.mBuilder.mN.hasLargeIcon()) {
                remoteViews.setViewLayoutMarginEndDimen(16909068, 0);
                remoteViews.setViewLayoutMarginEndDimen(16909429, 0);
            }
        }

        private RemoteViews makeMediaBigContentView() {
            int n = Math.min(this.mBuilder.mActions.size(), 5);
            Object object = this.mActionsToShowInCompact;
            int n2 = object == null ? 0 : Math.min(((int[])object).length, 3);
            if (!this.mBuilder.mN.hasLargeIcon() && n <= n2) {
                return null;
            }
            StandardTemplateParams standardTemplateParams = this.mBuilder.mParams.reset().hasProgress(false).fillTextsFrom(this.mBuilder);
            object = this.mBuilder.applyStandardTemplate(17367200, standardTemplateParams, null);
            for (n2 = 0; n2 < 5; ++n2) {
                if (n2 < n) {
                    this.bindMediaActionButton((RemoteViews)object, MEDIA_BUTTON_IDS[n2], (Action)this.mBuilder.mActions.get(n2), standardTemplateParams);
                    continue;
                }
                ((RemoteViews)object).setViewVisibility(MEDIA_BUTTON_IDS[n2], 8);
            }
            this.bindMediaActionButton((RemoteViews)object, 16909102, new Action(17302657, this.mBuilder.mContext.getString(17039934), null), standardTemplateParams);
            ((RemoteViews)object).setViewVisibility(16909102, 8);
            this.handleImage((RemoteViews)object);
            return object;
        }

        private RemoteViews makeMediaContentView() {
            int n;
            StandardTemplateParams standardTemplateParams = this.mBuilder.mParams.reset().hasProgress(false).fillTextsFrom(this.mBuilder);
            RemoteViews remoteViews = this.mBuilder.applyStandardTemplate(17367204, standardTemplateParams, null);
            Object object = this.mActionsToShowInCompact;
            int n2 = object == null ? 0 : Math.min(((int[])object).length, 3);
            if (n2 <= (n = this.mBuilder.mActions.size())) {
                for (n = 0; n < 3; ++n) {
                    if (n < n2) {
                        object = (Action)this.mBuilder.mActions.get(this.mActionsToShowInCompact[n]);
                        this.bindMediaActionButton(remoteViews, MEDIA_BUTTON_IDS[n], (Action)object, standardTemplateParams);
                        continue;
                    }
                    remoteViews.setViewVisibility(MEDIA_BUTTON_IDS[n], 8);
                }
                this.handleImage(remoteViews);
                n2 = 17105307;
                if (this.mBuilder.mN.hasLargeIcon()) {
                    n2 = 17105329;
                }
                remoteViews.setViewLayoutMarginEndDimen(16909165, n2);
                return remoteViews;
            }
            throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", n, n - 1));
        }

        @Override
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            int[] arrn = this.mToken;
            if (arrn != null) {
                bundle.putParcelable(Notification.EXTRA_MEDIA_SESSION, (Parcelable)arrn);
            }
            if ((arrn = this.mActionsToShowInCompact) != null) {
                bundle.putIntArray(Notification.EXTRA_COMPACT_ACTIONS, arrn);
            }
        }

        @Override
        public boolean areNotificationsVisiblyDifferent(Style style2) {
            return style2 == null || this.getClass() != style2.getClass();
            {
            }
        }

        @UnsupportedAppUsage
        @Override
        public Notification buildStyled(Notification notification) {
            super.buildStyled(notification);
            if (notification.category == null) {
                notification.category = Notification.CATEGORY_TRANSPORT;
            }
            return notification;
        }

        @Override
        protected boolean hasProgress() {
            return false;
        }

        @Override
        public RemoteViews makeBigContentView() {
            return this.makeMediaBigContentView();
        }

        @Override
        public RemoteViews makeContentView(boolean bl) {
            return this.makeMediaContentView();
        }

        @Override
        public RemoteViews makeHeadsUpContentView(boolean bl) {
            RemoteViews remoteViews = this.makeMediaBigContentView();
            if (remoteViews == null) {
                remoteViews = this.makeMediaContentView();
            }
            return remoteViews;
        }

        @Override
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            if (bundle.containsKey(Notification.EXTRA_MEDIA_SESSION)) {
                this.mToken = (MediaSession.Token)bundle.getParcelable(Notification.EXTRA_MEDIA_SESSION);
            }
            if (bundle.containsKey(Notification.EXTRA_COMPACT_ACTIONS)) {
                this.mActionsToShowInCompact = bundle.getIntArray(Notification.EXTRA_COMPACT_ACTIONS);
            }
        }

        public MediaStyle setMediaSession(MediaSession.Token token) {
            this.mToken = token;
            return this;
        }

        public MediaStyle setShowActionsInCompactView(int ... arrn) {
            this.mActionsToShowInCompact = arrn;
            return this;
        }
    }

    public static class MessagingStyle
    extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence mConversationTitle;
        List<Message> mHistoricMessages = new ArrayList<Message>();
        boolean mIsGroupConversation;
        List<Message> mMessages = new ArrayList<Message>();
        Person mUser;

        MessagingStyle() {
        }

        public MessagingStyle(Person person) {
            this.mUser = person;
        }

        public MessagingStyle(CharSequence charSequence) {
            this(new Person.Builder().setName(charSequence).build());
        }

        private CharSequence createConversationTitleFromMessages() {
            int n;
            Object object;
            ArraySet<CharSequence> arraySet = new ArraySet<CharSequence>();
            for (n = 0; n < this.mMessages.size(); ++n) {
                object = this.mMessages.get(n).getSenderPerson();
                if (object == null) continue;
                arraySet.add(((Person)object).getName());
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            int n2 = arraySet.size();
            for (n = 0; n < n2; ++n) {
                object = (CharSequence)arraySet.valueAt(n);
                if (!TextUtils.isEmpty(spannableStringBuilder)) {
                    spannableStringBuilder.append(", ");
                }
                spannableStringBuilder.append(BidiFormatter.getInstance().unicodeWrap((CharSequence)object));
            }
            return spannableStringBuilder;
        }

        private Message findLatestIncomingMessage() {
            return MessagingStyle.findLatestIncomingMessage(this.mMessages);
        }

        public static Message findLatestIncomingMessage(List<Message> list) {
            for (int i = list.size() - 1; i >= 0; --i) {
                Message message = list.get(i);
                if (message.mSender == null || TextUtils.isEmpty(message.mSender.getName())) continue;
                return message;
            }
            if (!list.isEmpty()) {
                return list.get(list.size() - 1);
            }
            return null;
        }

        private void fixTitleAndTextExtras(Bundle bundle) {
            Object object = this.findLatestIncomingMessage();
            Object object2 = null;
            CharSequence charSequence = object == null ? null : ((Message)object).mText;
            if (object != null) {
                object2 = ((Message)object).mSender != null && !TextUtils.isEmpty(((Message)object).mSender.getName()) ? ((Message)object).mSender : this.mUser;
                object2 = ((Person)object2).getName();
            }
            if (!TextUtils.isEmpty(this.mConversationTitle)) {
                if (!TextUtils.isEmpty((CharSequence)object2)) {
                    object = BidiFormatter.getInstance();
                    object2 = this.mBuilder.mContext.getString(17040514, ((BidiFormatter)object).unicodeWrap(this.mConversationTitle), ((BidiFormatter)object).unicodeWrap((CharSequence)object2));
                } else {
                    object2 = this.mConversationTitle;
                }
            }
            if (object2 != null) {
                bundle.putCharSequence(Notification.EXTRA_TITLE, (CharSequence)object2);
            }
            if (charSequence != null) {
                bundle.putCharSequence(Notification.EXTRA_TEXT, charSequence);
            }
        }

        private boolean hasOnlyWhiteSpaceSenders() {
            for (int i = 0; i < this.mMessages.size(); ++i) {
                Person person = this.mMessages.get(i).getSenderPerson();
                if (person == null || this.isWhiteSpace(person.getName())) continue;
                return false;
            }
            return true;
        }

        private boolean isWhiteSpace(CharSequence charSequence) {
            if (TextUtils.isEmpty(charSequence)) {
                return true;
            }
            if (charSequence.toString().matches("^\\s*$")) {
                return true;
            }
            for (int i = 0; i < charSequence.length(); ++i) {
                if (charSequence.charAt(i) == '\u200b') continue;
                return false;
            }
            return true;
        }

        private static TextAppearanceSpan makeFontColorSpan(int n) {
            return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(n), null);
        }

        private RemoteViews makeMessagingView(boolean bl, boolean bl2) {
            Object object;
            Object object2;
            boolean bl3;
            Object object3 = !TextUtils.isEmpty(this.mBigContentTitle) ? this.mBigContentTitle : this.mConversationTitle;
            int n = Builder.access$3400((Builder)this.mBuilder).getApplicationInfo().targetSdkVersion;
            boolean bl4 = true;
            n = n >= 28 ? 1 : 0;
            Object object4 = null;
            Icon icon = null;
            if (n == 0) {
                bl3 = TextUtils.isEmpty((CharSequence)object3);
                object2 = this.mBuilder.mN.mLargeIcon;
                object = object3;
                icon = object2;
                if (this.hasOnlyWhiteSpaceSenders()) {
                    bl3 = true;
                    object = null;
                    object4 = object3;
                    icon = object2;
                }
            } else {
                bl3 = this.isGroupConversation() ^ true;
                object = object3;
            }
            object3 = new TemplateBindResult();
            object2 = this.mBuilder.mParams.reset().hasProgress(false).title((CharSequence)object).text(null);
            boolean bl5 = bl4;
            if (!bl2) {
                bl5 = bl3 ? bl4 : false;
            }
            object = ((StandardTemplateParams)object2).hideLargeIcon(bl5).hideReplyIcon(bl2).headerTextSecondary((CharSequence)object);
            object2 = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getMessagingLayoutResource(), (StandardTemplateParams)object, (TemplateBindResult)object3);
            this.addExtras(Builder.access$300((Builder)this.mBuilder).extras);
            ((RemoteViews)object2).setViewLayoutMarginEnd(16909179, ((TemplateBindResult)object3).getIconMarginEnd());
            n = this.mBuilder.isColorized((StandardTemplateParams)object) ? this.mBuilder.getPrimaryTextColor((StandardTemplateParams)object) : this.mBuilder.resolveContrastColor((StandardTemplateParams)object);
            ((RemoteViews)object2).setInt(16909406, "setLayoutColor", n);
            ((RemoteViews)object2).setInt(16909406, "setSenderTextColor", this.mBuilder.getPrimaryTextColor((StandardTemplateParams)object));
            ((RemoteViews)object2).setInt(16909406, "setMessageTextColor", this.mBuilder.getSecondaryTextColor((StandardTemplateParams)object));
            ((RemoteViews)object2).setBoolean(16909406, "setDisplayImagesAtEnd", bl);
            ((RemoteViews)object2).setIcon(16909406, "setAvatarReplacement", icon);
            ((RemoteViews)object2).setCharSequence(16909406, "setNameReplacement", (CharSequence)object4);
            ((RemoteViews)object2).setBoolean(16909406, "setIsOneToOne", bl3);
            ((RemoteViews)object2).setBundle(16909406, "setData", Builder.access$300((Builder)this.mBuilder).extras);
            return object2;
        }

        @Override
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            Object object = this.mUser;
            if (object != null) {
                bundle.putCharSequence(Notification.EXTRA_SELF_DISPLAY_NAME, ((Person)object).getName());
                bundle.putParcelable(Notification.EXTRA_MESSAGING_PERSON, this.mUser);
            }
            if ((object = this.mConversationTitle) != null) {
                bundle.putCharSequence(Notification.EXTRA_CONVERSATION_TITLE, (CharSequence)object);
            }
            if (!this.mMessages.isEmpty()) {
                bundle.putParcelableArray(Notification.EXTRA_MESSAGES, Message.getBundleArrayForMessages(this.mMessages));
            }
            if (!this.mHistoricMessages.isEmpty()) {
                bundle.putParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES, Message.getBundleArrayForMessages(this.mHistoricMessages));
            }
            this.fixTitleAndTextExtras(bundle);
            bundle.putBoolean(Notification.EXTRA_IS_GROUP_CONVERSATION, this.mIsGroupConversation);
        }

        public MessagingStyle addHistoricMessage(Message message) {
            this.mHistoricMessages.add(message);
            if (this.mHistoricMessages.size() > 25) {
                this.mHistoricMessages.remove(0);
            }
            return this;
        }

        public MessagingStyle addMessage(Message message) {
            this.mMessages.add(message);
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public MessagingStyle addMessage(CharSequence charSequence, long l, Person person) {
            return this.addMessage(new Message(charSequence, l, person));
        }

        public MessagingStyle addMessage(CharSequence charSequence, long l, CharSequence object) {
            object = object == null ? null : new Person.Builder().setName((CharSequence)object).build();
            return this.addMessage(charSequence, l, (Person)object);
        }

        @Override
        public boolean areNotificationsVisiblyDifferent(Style arrayList) {
            block9 : {
                Object object;
                List<Message> list;
                int n;
                block11 : {
                    block10 : {
                        if (arrayList == null || this.getClass() != arrayList.getClass()) break block9;
                        arrayList = (MessagingStyle)((Object)arrayList);
                        list = this.getMessages();
                        object = ((MessagingStyle)((Object)arrayList)).getMessages();
                        if (list == null) break block10;
                        arrayList = object;
                        if (object != null) break block11;
                    }
                    arrayList = new ArrayList();
                }
                if ((n = list.size()) != arrayList.size()) {
                    return true;
                }
                for (int i = 0; i < n; ++i) {
                    Message message = list.get(i);
                    Message message2 = (Message)arrayList.get(i);
                    if (!Objects.equals(String.valueOf(message.getText()), String.valueOf(message2.getText()))) {
                        return true;
                    }
                    if (!Objects.equals(message.getDataUri(), message2.getDataUri())) {
                        return true;
                    }
                    object = message.getSenderPerson() == null ? message.getSender() : message.getSenderPerson().getName();
                    String string2 = String.valueOf(object);
                    object = message2.getSenderPerson() == null ? message2.getSender() : message2.getSenderPerson().getName();
                    if (!Objects.equals(string2, String.valueOf(object))) {
                        return true;
                    }
                    object = message.getSenderPerson();
                    string2 = null;
                    object = object == null ? null : message.getSenderPerson().getKey();
                    if (message2.getSenderPerson() != null) {
                        string2 = message2.getSenderPerson().getKey();
                    }
                    if (Objects.equals(object, string2)) continue;
                    return true;
                }
                return false;
            }
            return true;
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        @Override
        public CharSequence getHeadsUpStatusBarText() {
            CharSequence charSequence = !TextUtils.isEmpty(this.mBigContentTitle) ? this.mBigContentTitle : this.mConversationTitle;
            if (!TextUtils.isEmpty(charSequence) && !this.hasOnlyWhiteSpaceSenders()) {
                return charSequence;
            }
            return null;
        }

        public List<Message> getHistoricMessages() {
            return this.mHistoricMessages;
        }

        public List<Message> getMessages() {
            return this.mMessages;
        }

        public Person getUser() {
            return this.mUser;
        }

        public CharSequence getUserDisplayName() {
            return this.mUser.getName();
        }

        public boolean isGroupConversation() {
            if (this.mBuilder != null && Builder.access$3400((Builder)this.mBuilder).getApplicationInfo().targetSdkVersion < 28) {
                boolean bl = this.mConversationTitle != null;
                return bl;
            }
            return this.mIsGroupConversation;
        }

        @Override
        public RemoteViews makeBigContentView() {
            return this.makeMessagingView(false, true);
        }

        @Override
        public RemoteViews makeContentView(boolean bl) {
            this.mBuilder.mOriginalActions = this.mBuilder.mActions;
            this.mBuilder.mActions = new ArrayList();
            RemoteViews remoteViews = this.makeMessagingView(true, false);
            this.mBuilder.mActions = this.mBuilder.mOriginalActions;
            this.mBuilder.mOriginalActions = null;
            return remoteViews;
        }

        @Override
        public RemoteViews makeHeadsUpContentView(boolean bl) {
            RemoteViews remoteViews = this.makeMessagingView(true, true);
            remoteViews.setInt(16909179, "setMaxDisplayedLines", 1);
            return remoteViews;
        }

        @Override
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mUser = (Person)bundle.getParcelable(Notification.EXTRA_MESSAGING_PERSON);
            if (this.mUser == null) {
                CharSequence charSequence = bundle.getCharSequence(Notification.EXTRA_SELF_DISPLAY_NAME);
                this.mUser = new Person.Builder().setName(charSequence).build();
            }
            this.mConversationTitle = bundle.getCharSequence(Notification.EXTRA_CONVERSATION_TITLE);
            this.mMessages = Message.getMessagesFromBundleArray(bundle.getParcelableArray(Notification.EXTRA_MESSAGES));
            this.mHistoricMessages = Message.getMessagesFromBundleArray(bundle.getParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES));
            this.mIsGroupConversation = bundle.getBoolean(Notification.EXTRA_IS_GROUP_CONVERSATION);
        }

        public MessagingStyle setConversationTitle(CharSequence charSequence) {
            this.mConversationTitle = charSequence;
            return this;
        }

        public MessagingStyle setGroupConversation(boolean bl) {
            this.mIsGroupConversation = bl;
            return this;
        }

        @Override
        public void validate(Context object) {
            super.validate((Context)object);
            if (object.getApplicationInfo().targetSdkVersion >= 28 && ((object = this.mUser) == null || ((Person)object).getName() == null)) {
                throw new RuntimeException("User must be valid and have a name.");
            }
        }

        public static final class Message {
            static final String KEY_DATA_MIME_TYPE = "type";
            static final String KEY_DATA_URI = "uri";
            static final String KEY_EXTRAS_BUNDLE = "extras";
            static final String KEY_REMOTE_INPUT_HISTORY = "remote_input_history";
            static final String KEY_SENDER = "sender";
            static final String KEY_SENDER_PERSON = "sender_person";
            public static final String KEY_TEXT = "text";
            static final String KEY_TIMESTAMP = "time";
            private String mDataMimeType;
            private Uri mDataUri;
            private Bundle mExtras;
            private final boolean mRemoteInputHistory;
            private final Person mSender;
            private final CharSequence mText;
            private final long mTimestamp;

            public Message(CharSequence charSequence, long l, Person person) {
                this(charSequence, l, person, false);
            }

            public Message(CharSequence charSequence, long l, Person person, boolean bl) {
                this.mExtras = new Bundle();
                this.mText = charSequence;
                this.mTimestamp = l;
                this.mSender = person;
                this.mRemoteInputHistory = bl;
            }

            public Message(CharSequence charSequence, long l, CharSequence object) {
                object = object == null ? null : new Person.Builder().setName((CharSequence)object).build();
                this(charSequence, l, (Person)object);
            }

            static Bundle[] getBundleArrayForMessages(List<Message> list) {
                Bundle[] arrbundle = new Bundle[list.size()];
                int n = list.size();
                for (int i = 0; i < n; ++i) {
                    arrbundle[i] = list.get(i).toBundle();
                }
                return arrbundle;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static Message getMessageFromBundle(Bundle bundle) {
                try {
                    Object object;
                    if (!bundle.containsKey(KEY_TEXT)) return null;
                    if (!bundle.containsKey(KEY_TIMESTAMP)) {
                        return null;
                    }
                    Object object2 = (Person)bundle.getParcelable(KEY_SENDER_PERSON);
                    if (object2 == null && (object = bundle.getCharSequence(KEY_SENDER)) != null) {
                        object2 = new Person.Builder();
                        object2 = ((Person.Builder)object2).setName((CharSequence)object).build();
                    }
                    object = new Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), (Person)object2, bundle.getBoolean(KEY_REMOTE_INPUT_HISTORY, false));
                    if (bundle.containsKey(KEY_DATA_MIME_TYPE) && bundle.containsKey(KEY_DATA_URI)) {
                        ((Message)object).setData(bundle.getString(KEY_DATA_MIME_TYPE), (Uri)bundle.getParcelable(KEY_DATA_URI));
                    }
                    if (!bundle.containsKey(KEY_EXTRAS_BUNDLE)) return object;
                    ((Message)object).getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE));
                    return object;
                }
                catch (ClassCastException classCastException) {
                    return null;
                }
            }

            public static List<Message> getMessagesFromBundleArray(Parcelable[] arrparcelable) {
                if (arrparcelable == null) {
                    return new ArrayList<Message>();
                }
                ArrayList<Message> arrayList = new ArrayList<Message>(arrparcelable.length);
                for (int i = 0; i < arrparcelable.length; ++i) {
                    Message message;
                    if (!(arrparcelable[i] instanceof Bundle) || (message = Message.getMessageFromBundle((Bundle)arrparcelable[i])) == null) continue;
                    arrayList.add(message);
                }
                return arrayList;
            }

            public String getDataMimeType() {
                return this.mDataMimeType;
            }

            public Uri getDataUri() {
                return this.mDataUri;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public CharSequence getSender() {
                Object object = this.mSender;
                object = object == null ? null : ((Person)object).getName();
                return object;
            }

            public Person getSenderPerson() {
                return this.mSender;
            }

            public CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            public boolean isRemoteInputHistory() {
                return this.mRemoteInputHistory;
            }

            public Message setData(String string2, Uri uri) {
                this.mDataMimeType = string2;
                this.mDataUri = uri;
                return this;
            }

            @VisibleForTesting
            public Bundle toBundle() {
                boolean bl;
                Bundle bundle = new Bundle();
                Object object = this.mText;
                if (object != null) {
                    bundle.putCharSequence(KEY_TEXT, (CharSequence)object);
                }
                bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
                object = this.mSender;
                if (object != null) {
                    bundle.putCharSequence(KEY_SENDER, ((Person)object).getName());
                    bundle.putParcelable(KEY_SENDER_PERSON, this.mSender);
                }
                if ((object = this.mDataMimeType) != null) {
                    bundle.putString(KEY_DATA_MIME_TYPE, (String)object);
                }
                if ((object = this.mDataUri) != null) {
                    bundle.putParcelable(KEY_DATA_URI, (Parcelable)object);
                }
                if ((object = this.mExtras) != null) {
                    bundle.putBundle(KEY_EXTRAS_BUNDLE, (Bundle)object);
                }
                if (bl = this.mRemoteInputHistory) {
                    bundle.putBoolean(KEY_REMOTE_INPUT_HISTORY, bl);
                }
                return bundle;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Priority {
    }

    private static class StandardTemplateParams {
        boolean allowColorization = true;
        boolean forceDefaultColor = false;
        boolean hasProgress = true;
        CharSequence headerTextSecondary;
        boolean hideLargeIcon;
        boolean hideReplyIcon;
        int maxRemoteInputHistory = 3;
        CharSequence summaryText;
        CharSequence text;
        CharSequence title;

        private StandardTemplateParams() {
        }

        final StandardTemplateParams disallowColorization() {
            this.allowColorization = false;
            return this;
        }

        final StandardTemplateParams fillTextsFrom(Builder builder) {
            CharSequence charSequence;
            Bundle bundle = Builder.access$300((Builder)builder).extras;
            this.title = builder.processLegacyText(bundle.getCharSequence(Notification.EXTRA_TITLE));
            CharSequence charSequence2 = charSequence = bundle.getCharSequence(Notification.EXTRA_BIG_TEXT);
            if (TextUtils.isEmpty(charSequence)) {
                charSequence2 = bundle.getCharSequence(Notification.EXTRA_TEXT);
            }
            this.text = builder.processLegacyText(charSequence2);
            this.summaryText = bundle.getCharSequence(Notification.EXTRA_SUB_TEXT);
            return this;
        }

        final StandardTemplateParams forceDefaultColor() {
            this.forceDefaultColor = true;
            return this;
        }

        final StandardTemplateParams hasProgress(boolean bl) {
            this.hasProgress = bl;
            return this;
        }

        final StandardTemplateParams headerTextSecondary(CharSequence charSequence) {
            this.headerTextSecondary = charSequence;
            return this;
        }

        final StandardTemplateParams hideLargeIcon(boolean bl) {
            this.hideLargeIcon = bl;
            return this;
        }

        final StandardTemplateParams hideReplyIcon(boolean bl) {
            this.hideReplyIcon = bl;
            return this;
        }

        final StandardTemplateParams reset() {
            this.hasProgress = true;
            this.title = null;
            this.text = null;
            this.summaryText = null;
            this.headerTextSecondary = null;
            this.maxRemoteInputHistory = 3;
            this.allowColorization = true;
            this.forceDefaultColor = false;
            return this;
        }

        public StandardTemplateParams setMaxRemoteInputHistory(int n) {
            this.maxRemoteInputHistory = n;
            return this;
        }

        final StandardTemplateParams summaryText(CharSequence charSequence) {
            this.summaryText = charSequence;
            return this;
        }

        final StandardTemplateParams text(CharSequence charSequence) {
            this.text = charSequence;
            return this;
        }

        final StandardTemplateParams title(CharSequence charSequence) {
            this.title = charSequence;
            return this;
        }
    }

    public static abstract class Style {
        static final int MAX_REMOTE_INPUT_HISTORY_LINES = 3;
        private CharSequence mBigContentTitle;
        protected Builder mBuilder;
        protected CharSequence mSummaryText = null;
        protected boolean mSummaryTextSet = false;

        public void addExtras(Bundle bundle) {
            CharSequence charSequence;
            if (this.mSummaryTextSet) {
                bundle.putCharSequence(Notification.EXTRA_SUMMARY_TEXT, this.mSummaryText);
            }
            if ((charSequence = this.mBigContentTitle) != null) {
                bundle.putCharSequence(Notification.EXTRA_TITLE_BIG, charSequence);
            }
            bundle.putString(Notification.EXTRA_TEMPLATE, this.getClass().getName());
        }

        public abstract boolean areNotificationsVisiblyDifferent(Style var1);

        public Notification build() {
            this.checkBuilder();
            return this.mBuilder.build();
        }

        public Notification buildStyled(Notification notification) {
            this.addExtras(notification.extras);
            return notification;
        }

        protected void checkBuilder() {
            if (this.mBuilder != null) {
                return;
            }
            throw new IllegalArgumentException("Style requires a valid Builder object");
        }

        public boolean displayCustomViewInline() {
            return false;
        }

        public CharSequence getHeadsUpStatusBarText() {
            return null;
        }

        protected RemoteViews getStandardView(int n) {
            return this.getStandardView(n, this.mBuilder.mParams.reset().fillTextsFrom(this.mBuilder), null);
        }

        protected RemoteViews getStandardView(int n, StandardTemplateParams object, TemplateBindResult object2) {
            this.checkBuilder();
            CharSequence charSequence = this.mBigContentTitle;
            if (charSequence != null) {
                ((StandardTemplateParams)object).title = charSequence;
            }
            object2 = this.mBuilder.applyStandardTemplateWithActions(n, (StandardTemplateParams)object, (TemplateBindResult)object2);
            object = this.mBigContentTitle;
            if (object != null && object.equals("")) {
                ((RemoteViews)object2).setViewVisibility(16909068, 8);
            } else {
                ((RemoteViews)object2).setViewVisibility(16909068, 0);
            }
            return object2;
        }

        protected boolean hasProgress() {
            return true;
        }

        public boolean hasSummaryInHeader() {
            return true;
        }

        protected void internalSetBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = charSequence;
        }

        protected void internalSetSummaryText(CharSequence charSequence) {
            this.mSummaryText = charSequence;
            this.mSummaryTextSet = true;
        }

        public RemoteViews makeBigContentView() {
            return null;
        }

        public RemoteViews makeContentView(boolean bl) {
            return null;
        }

        public RemoteViews makeHeadsUpContentView(boolean bl) {
            return null;
        }

        public void purgeResources() {
        }

        public void reduceImageSizes(Context context) {
        }

        protected void restoreFromExtras(Bundle bundle) {
            if (bundle.containsKey("android.summaryText")) {
                this.mSummaryText = bundle.getCharSequence("android.summaryText");
                this.mSummaryTextSet = true;
            }
            if (bundle.containsKey("android.title.big")) {
                this.mBigContentTitle = bundle.getCharSequence("android.title.big");
            }
        }

        public void setBuilder(Builder builder) {
            if (this.mBuilder != builder && (builder = (this.mBuilder = builder)) != null) {
                builder.setStyle(this);
            }
        }

        public void validate(Context context) {
        }
    }

    private static class TemplateBindResult {
        int mIconMarginEnd;
        boolean mRightIconContainerVisible;

        private TemplateBindResult() {
        }

        public int getIconMarginEnd() {
            return this.mIconMarginEnd;
        }

        public boolean isRightIconContainerVisible() {
            return this.mRightIconContainerVisible;
        }

        public void setIconMarginEnd(int n) {
            this.mIconMarginEnd = n;
        }

        public void setRightIconContainerVisible(boolean bl) {
            this.mRightIconContainerVisible = bl;
        }
    }

    @SystemApi
    public static final class TvExtender
    implements Extender {
        private static final String EXTRA_CHANNEL_ID = "channel_id";
        private static final String EXTRA_CONTENT_INTENT = "content_intent";
        private static final String EXTRA_DELETE_INTENT = "delete_intent";
        private static final String EXTRA_FLAGS = "flags";
        private static final String EXTRA_SUPPRESS_SHOW_OVER_APPS = "suppressShowOverApps";
        private static final String EXTRA_TV_EXTENDER = "android.tv.EXTENSIONS";
        private static final int FLAG_AVAILABLE_ON_TV = 1;
        private static final String TAG = "TvExtender";
        private String mChannelId;
        private PendingIntent mContentIntent;
        private PendingIntent mDeleteIntent;
        private int mFlags;
        private boolean mSuppressShowOverApps;

        public TvExtender() {
            this.mFlags = 1;
        }

        public TvExtender(Notification parcelable) {
            parcelable = parcelable.extras == null ? null : parcelable.extras.getBundle(EXTRA_TV_EXTENDER);
            if (parcelable != null) {
                this.mFlags = ((BaseBundle)((Object)parcelable)).getInt(EXTRA_FLAGS);
                this.mChannelId = ((BaseBundle)((Object)parcelable)).getString(EXTRA_CHANNEL_ID);
                this.mSuppressShowOverApps = ((BaseBundle)((Object)parcelable)).getBoolean(EXTRA_SUPPRESS_SHOW_OVER_APPS);
                this.mContentIntent = (PendingIntent)((Bundle)parcelable).getParcelable(EXTRA_CONTENT_INTENT);
                this.mDeleteIntent = (PendingIntent)((Bundle)parcelable).getParcelable(EXTRA_DELETE_INTENT);
            }
        }

        @Override
        public Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            bundle.putInt(EXTRA_FLAGS, this.mFlags);
            bundle.putString(EXTRA_CHANNEL_ID, this.mChannelId);
            bundle.putBoolean(EXTRA_SUPPRESS_SHOW_OVER_APPS, this.mSuppressShowOverApps);
            PendingIntent pendingIntent = this.mContentIntent;
            if (pendingIntent != null) {
                bundle.putParcelable(EXTRA_CONTENT_INTENT, pendingIntent);
            }
            if ((pendingIntent = this.mDeleteIntent) != null) {
                bundle.putParcelable(EXTRA_DELETE_INTENT, pendingIntent);
            }
            builder.getExtras().putBundle(EXTRA_TV_EXTENDER, bundle);
            return builder;
        }

        @Deprecated
        public String getChannel() {
            return this.mChannelId;
        }

        public String getChannelId() {
            return this.mChannelId;
        }

        public PendingIntent getContentIntent() {
            return this.mContentIntent;
        }

        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public boolean getSuppressShowOverApps() {
            return this.mSuppressShowOverApps;
        }

        public boolean isAvailableOnTv() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        public TvExtender setChannel(String string2) {
            this.mChannelId = string2;
            return this;
        }

        public TvExtender setChannelId(String string2) {
            this.mChannelId = string2;
            return this;
        }

        public TvExtender setContentIntent(PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent;
            return this;
        }

        public TvExtender setDeleteIntent(PendingIntent pendingIntent) {
            this.mDeleteIntent = pendingIntent;
            return this;
        }

        public TvExtender setSuppressShowOverApps(boolean bl) {
            this.mSuppressShowOverApps = bl;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Visibility {
    }

    public static final class WearableExtender
    implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        @Deprecated
        public static final int SCREEN_TIMEOUT_LONG = -1;
        @Deprecated
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        @Deprecated
        public static final int SIZE_DEFAULT = 0;
        @Deprecated
        public static final int SIZE_FULL_SCREEN = 5;
        @Deprecated
        public static final int SIZE_LARGE = 4;
        @Deprecated
        public static final int SIZE_MEDIUM = 3;
        @Deprecated
        public static final int SIZE_SMALL = 2;
        @Deprecated
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions = new ArrayList();
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex = -1;
        private int mContentIcon;
        private int mContentIconGravity = 8388613;
        private int mCustomContentHeight;
        private int mCustomSizePreset = 0;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags = 1;
        private int mGravity = 80;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages = new ArrayList();

        public WearableExtender() {
        }

        public WearableExtender(Notification parcelable) {
            parcelable = ((Notification)parcelable).extras.getBundle(EXTRA_WEARABLE_EXTENSIONS);
            if (parcelable != null) {
                Notification[] arrnotification = ((Bundle)parcelable).getParcelableArrayList(KEY_ACTIONS);
                if (arrnotification != null) {
                    this.mActions.addAll((Collection<Action>)arrnotification);
                }
                this.mFlags = ((BaseBundle)((Object)parcelable)).getInt(KEY_FLAGS, 1);
                this.mDisplayIntent = (PendingIntent)((Bundle)parcelable).getParcelable(KEY_DISPLAY_INTENT);
                arrnotification = Notification.getNotificationArrayFromBundle((Bundle)parcelable, KEY_PAGES);
                if (arrnotification != null) {
                    Collections.addAll(this.mPages, arrnotification);
                }
                this.mBackground = (Bitmap)((Bundle)parcelable).getParcelable(KEY_BACKGROUND);
                this.mContentIcon = ((BaseBundle)((Object)parcelable)).getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = ((BaseBundle)((Object)parcelable)).getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = ((BaseBundle)((Object)parcelable)).getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = ((BaseBundle)((Object)parcelable)).getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = ((BaseBundle)((Object)parcelable)).getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = ((BaseBundle)((Object)parcelable)).getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = ((BaseBundle)((Object)parcelable)).getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = ((BaseBundle)((Object)parcelable)).getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = ((BaseBundle)((Object)parcelable)).getString(KEY_BRIDGE_TAG);
            }
        }

        private void setFlag(int n, boolean bl) {
            this.mFlags = bl ? (this.mFlags |= n) : (this.mFlags &= n);
        }

        public WearableExtender addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public WearableExtender addActions(List<Action> list) {
            this.mActions.addAll(list);
            return this;
        }

        @Deprecated
        public WearableExtender addPage(Notification notification) {
            this.mPages.add(notification);
            return this;
        }

        @Deprecated
        public WearableExtender addPages(List<Notification> list) {
            this.mPages.addAll(list);
            return this;
        }

        public WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        @Deprecated
        public WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        public WearableExtender clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.mActions = new ArrayList<Action>(this.mActions);
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mDisplayIntent = this.mDisplayIntent;
            wearableExtender.mPages = new ArrayList<Notification>(this.mPages);
            wearableExtender.mBackground = this.mBackground;
            wearableExtender.mContentIcon = this.mContentIcon;
            wearableExtender.mContentIconGravity = this.mContentIconGravity;
            wearableExtender.mContentActionIndex = this.mContentActionIndex;
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset;
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight;
            wearableExtender.mGravity = this.mGravity;
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
            wearableExtender.mDismissalId = this.mDismissalId;
            wearableExtender.mBridgeTag = this.mBridgeTag;
            return wearableExtender;
        }

        @Override
        public Builder extend(Builder builder) {
            Object object;
            int n;
            Bundle bundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                bundle.putParcelableArrayList(KEY_ACTIONS, this.mActions);
            }
            if ((n = this.mFlags) != 1) {
                bundle.putInt(KEY_FLAGS, n);
            }
            if ((object = this.mDisplayIntent) != null) {
                bundle.putParcelable(KEY_DISPLAY_INTENT, (Parcelable)object);
            }
            if (!this.mPages.isEmpty()) {
                object = this.mPages;
                bundle.putParcelableArray(KEY_PAGES, ((ArrayList)object).toArray(new Notification[((ArrayList)object).size()]));
            }
            if ((object = this.mBackground) != null) {
                bundle.putParcelable(KEY_BACKGROUND, (Parcelable)object);
            }
            if ((n = this.mContentIcon) != 0) {
                bundle.putInt(KEY_CONTENT_ICON, n);
            }
            if ((n = this.mContentIconGravity) != 8388613) {
                bundle.putInt(KEY_CONTENT_ICON_GRAVITY, n);
            }
            if ((n = this.mContentActionIndex) != -1) {
                bundle.putInt(KEY_CONTENT_ACTION_INDEX, n);
            }
            if ((n = this.mCustomSizePreset) != 0) {
                bundle.putInt(KEY_CUSTOM_SIZE_PRESET, n);
            }
            if ((n = this.mCustomContentHeight) != 0) {
                bundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, n);
            }
            if ((n = this.mGravity) != 80) {
                bundle.putInt(KEY_GRAVITY, n);
            }
            if ((n = this.mHintScreenTimeout) != 0) {
                bundle.putInt(KEY_HINT_SCREEN_TIMEOUT, n);
            }
            if ((object = this.mDismissalId) != null) {
                bundle.putString(KEY_DISMISSAL_ID, (String)object);
            }
            if ((object = this.mBridgeTag) != null) {
                bundle.putString(KEY_BRIDGE_TAG, (String)object);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return builder;
        }

        public List<Action> getActions() {
            return this.mActions;
        }

        @Deprecated
        public Bitmap getBackground() {
            return this.mBackground;
        }

        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        @Deprecated
        public int getContentIcon() {
            return this.mContentIcon;
        }

        @Deprecated
        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public boolean getContentIntentAvailableOffline() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        @Deprecated
        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        @Deprecated
        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        public String getDismissalId() {
            return this.mDismissalId;
        }

        @Deprecated
        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        @Deprecated
        public int getGravity() {
            return this.mGravity;
        }

        @Deprecated
        public boolean getHintAmbientBigPicture() {
            boolean bl = (this.mFlags & 32) != 0;
            return bl;
        }

        @Deprecated
        public boolean getHintAvoidBackgroundClipping() {
            boolean bl = (this.mFlags & 16) != 0;
            return bl;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            boolean bl = (this.mFlags & 64) != 0;
            return bl;
        }

        @Deprecated
        public boolean getHintHideIcon() {
            boolean bl = (this.mFlags & 2) != 0;
            return bl;
        }

        @Deprecated
        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        @Deprecated
        public boolean getHintShowBackgroundOnly() {
            boolean bl = (this.mFlags & 4) != 0;
            return bl;
        }

        @Deprecated
        public List<Notification> getPages() {
            return this.mPages;
        }

        public boolean getStartScrollBottom() {
            boolean bl = (this.mFlags & 8) != 0;
            return bl;
        }

        @Deprecated
        public WearableExtender setBackground(Bitmap bitmap) {
            this.mBackground = bitmap;
            return this;
        }

        public WearableExtender setBridgeTag(String string2) {
            this.mBridgeTag = string2;
            return this;
        }

        public WearableExtender setContentAction(int n) {
            this.mContentActionIndex = n;
            return this;
        }

        @Deprecated
        public WearableExtender setContentIcon(int n) {
            this.mContentIcon = n;
            return this;
        }

        @Deprecated
        public WearableExtender setContentIconGravity(int n) {
            this.mContentIconGravity = n;
            return this;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean bl) {
            this.setFlag(1, bl);
            return this;
        }

        @Deprecated
        public WearableExtender setCustomContentHeight(int n) {
            this.mCustomContentHeight = n;
            return this;
        }

        @Deprecated
        public WearableExtender setCustomSizePreset(int n) {
            this.mCustomSizePreset = n;
            return this;
        }

        public WearableExtender setDismissalId(String string2) {
            this.mDismissalId = string2;
            return this;
        }

        @Deprecated
        public WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
            this.mDisplayIntent = pendingIntent;
            return this;
        }

        @Deprecated
        public WearableExtender setGravity(int n) {
            this.mGravity = n;
            return this;
        }

        @Deprecated
        public WearableExtender setHintAmbientBigPicture(boolean bl) {
            this.setFlag(32, bl);
            return this;
        }

        @Deprecated
        public WearableExtender setHintAvoidBackgroundClipping(boolean bl) {
            this.setFlag(16, bl);
            return this;
        }

        public WearableExtender setHintContentIntentLaunchesActivity(boolean bl) {
            this.setFlag(64, bl);
            return this;
        }

        @Deprecated
        public WearableExtender setHintHideIcon(boolean bl) {
            this.setFlag(2, bl);
            return this;
        }

        @Deprecated
        public WearableExtender setHintScreenTimeout(int n) {
            this.mHintScreenTimeout = n;
            return this;
        }

        @Deprecated
        public WearableExtender setHintShowBackgroundOnly(boolean bl) {
            this.setFlag(4, bl);
            return this;
        }

        public WearableExtender setStartScrollBottom(boolean bl) {
            this.setFlag(8, bl);
            return this;
        }
    }

}

