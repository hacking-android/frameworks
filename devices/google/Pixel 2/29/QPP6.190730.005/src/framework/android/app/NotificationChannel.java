/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlSerializer
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Notification;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

public final class NotificationChannel
implements Parcelable {
    private static final String ATT_ALLOW_BUBBLE = "can_bubble";
    private static final String ATT_BLOCKABLE_SYSTEM = "blockable_system";
    private static final String ATT_CONTENT_TYPE = "content_type";
    private static final String ATT_DELETED = "deleted";
    private static final String ATT_DESC = "desc";
    private static final String ATT_FG_SERVICE_SHOWN = "fgservice";
    private static final String ATT_FLAGS = "flags";
    private static final String ATT_GROUP = "group";
    private static final String ATT_ID = "id";
    private static final String ATT_IMPORTANCE = "importance";
    private static final String ATT_LIGHTS = "lights";
    private static final String ATT_LIGHT_COLOR = "light_color";
    private static final String ATT_NAME = "name";
    private static final String ATT_PRIORITY = "priority";
    private static final String ATT_SHOW_BADGE = "show_badge";
    private static final String ATT_SOUND = "sound";
    private static final String ATT_USAGE = "usage";
    private static final String ATT_USER_LOCKED = "locked";
    private static final String ATT_VIBRATION = "vibration";
    private static final String ATT_VIBRATION_ENABLED = "vibration_enabled";
    private static final String ATT_VISIBILITY = "visibility";
    public static final Parcelable.Creator<NotificationChannel> CREATOR;
    private static final boolean DEFAULT_ALLOW_BUBBLE = true;
    public static final String DEFAULT_CHANNEL_ID = "miscellaneous";
    private static final boolean DEFAULT_DELETED = false;
    private static final int DEFAULT_IMPORTANCE = -1000;
    private static final int DEFAULT_LIGHT_COLOR = 0;
    private static final boolean DEFAULT_SHOW_BADGE = true;
    private static final int DEFAULT_VISIBILITY = -1000;
    private static final String DELIMITER = ",";
    public static final int[] LOCKABLE_FIELDS;
    private static final int MAX_TEXT_LENGTH = 1000;
    private static final String TAG_CHANNEL = "channel";
    public static final int USER_LOCKED_ALLOW_BUBBLE = 256;
    public static final int USER_LOCKED_IMPORTANCE = 4;
    public static final int USER_LOCKED_LIGHTS = 8;
    public static final int USER_LOCKED_PRIORITY = 1;
    public static final int USER_LOCKED_SHOW_BADGE = 128;
    public static final int USER_LOCKED_SOUND = 32;
    public static final int USER_LOCKED_VIBRATION = 16;
    public static final int USER_LOCKED_VISIBILITY = 2;
    private boolean mAllowBubbles;
    private AudioAttributes mAudioAttributes;
    private boolean mBlockableSystem;
    private boolean mBypassDnd;
    private boolean mDeleted;
    private String mDesc;
    private boolean mFgServiceShown;
    private String mGroup;
    @UnsupportedAppUsage
    private final String mId;
    private int mImportance = -1000;
    private boolean mImportanceLockedByOEM;
    private boolean mImportanceLockedDefaultApp;
    private int mLightColor;
    private boolean mLights;
    private int mLockscreenVisibility = -1000;
    private String mName;
    private boolean mShowBadge;
    private Uri mSound = Settings.System.DEFAULT_NOTIFICATION_URI;
    private int mUserLockedFields;
    private long[] mVibration;
    private boolean mVibrationEnabled;

    static {
        LOCKABLE_FIELDS = new int[]{1, 2, 4, 8, 16, 32, 128, 256};
        CREATOR = new Parcelable.Creator<NotificationChannel>(){

            @Override
            public NotificationChannel createFromParcel(Parcel parcel) {
                return new NotificationChannel(parcel);
            }

            public NotificationChannel[] newArray(int n) {
                return new NotificationChannel[n];
            }
        };
    }

    protected NotificationChannel(Parcel parcel) {
        boolean bl = false;
        this.mLightColor = 0;
        this.mShowBadge = true;
        this.mDeleted = false;
        this.mAudioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        this.mBlockableSystem = false;
        this.mAllowBubbles = true;
        byte by = parcel.readByte();
        AudioAttributes audioAttributes = null;
        this.mId = by != 0 ? parcel.readString() : null;
        this.mName = parcel.readByte() != 0 ? parcel.readString() : null;
        this.mDesc = parcel.readByte() != 0 ? parcel.readString() : null;
        this.mImportance = parcel.readInt();
        boolean bl2 = parcel.readByte() != 0;
        this.mBypassDnd = bl2;
        this.mLockscreenVisibility = parcel.readInt();
        this.mSound = parcel.readByte() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
        bl2 = parcel.readByte() != 0;
        this.mLights = bl2;
        this.mVibration = parcel.createLongArray();
        this.mUserLockedFields = parcel.readInt();
        bl2 = parcel.readByte() != 0;
        this.mFgServiceShown = bl2;
        bl2 = parcel.readByte() != 0;
        this.mVibrationEnabled = bl2;
        bl2 = parcel.readByte() != 0;
        this.mShowBadge = bl2;
        bl2 = bl;
        if (parcel.readByte() != 0) {
            bl2 = true;
        }
        this.mDeleted = bl2;
        this.mGroup = parcel.readByte() != 0 ? parcel.readString() : null;
        if (parcel.readInt() > 0) {
            audioAttributes = AudioAttributes.CREATOR.createFromParcel(parcel);
        }
        this.mAudioAttributes = audioAttributes;
        this.mLightColor = parcel.readInt();
        this.mBlockableSystem = parcel.readBoolean();
        this.mAllowBubbles = parcel.readBoolean();
        this.mImportanceLockedByOEM = parcel.readBoolean();
    }

    public NotificationChannel(String string2, CharSequence charSequence, int n) {
        this.mLightColor = 0;
        this.mShowBadge = true;
        this.mDeleted = false;
        this.mAudioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        this.mBlockableSystem = false;
        this.mAllowBubbles = true;
        this.mId = this.getTrimmedString(string2);
        string2 = charSequence != null ? this.getTrimmedString(charSequence.toString()) : null;
        this.mName = string2;
        this.mImportance = n;
    }

    private Uri getSoundForBackup(Context object) {
        Uri uri = this.getSound();
        if (uri != null && !Uri.EMPTY.equals(uri)) {
            if ((object = ((Context)object).getContentResolver().canonicalize(uri)) == null) {
                return Settings.System.DEFAULT_NOTIFICATION_URI;
            }
            return object;
        }
        return null;
    }

    private String getTrimmedString(String string2) {
        if (string2 != null && string2.length() > 1000) {
            return string2.substring(0, 1000);
        }
        return string2;
    }

    private static String longArrayToString(long[] arrl) {
        StringBuffer stringBuffer = new StringBuffer();
        if (arrl != null && arrl.length > 0) {
            for (int i = 0; i < arrl.length - 1; ++i) {
                stringBuffer.append(arrl[i]);
                stringBuffer.append(DELIMITER);
            }
            stringBuffer.append(arrl[arrl.length - 1]);
        }
        return stringBuffer.toString();
    }

    private void populateFromXml(XmlPullParser xmlPullParser, boolean bl, Context object) {
        boolean bl2 = !bl || object != null;
        Preconditions.checkArgument(bl2, "forRestore is true but got null context");
        this.setDescription(xmlPullParser.getAttributeValue(null, ATT_DESC));
        bl2 = NotificationChannel.safeInt(xmlPullParser, ATT_PRIORITY, 0) != 0;
        this.setBypassDnd(bl2);
        this.setLockscreenVisibility(NotificationChannel.safeInt(xmlPullParser, ATT_VISIBILITY, -1000));
        Uri uri = NotificationChannel.safeUri(xmlPullParser, ATT_SOUND);
        object = bl ? this.restoreSoundUri((Context)object, uri) : uri;
        this.setSound((Uri)object, NotificationChannel.safeAudioAttributes(xmlPullParser));
        this.enableLights(NotificationChannel.safeBool(xmlPullParser, ATT_LIGHTS, false));
        this.setLightColor(NotificationChannel.safeInt(xmlPullParser, ATT_LIGHT_COLOR, 0));
        this.setVibrationPattern(NotificationChannel.safeLongArray(xmlPullParser, ATT_VIBRATION, null));
        this.enableVibration(NotificationChannel.safeBool(xmlPullParser, ATT_VIBRATION_ENABLED, false));
        this.setShowBadge(NotificationChannel.safeBool(xmlPullParser, ATT_SHOW_BADGE, false));
        this.setDeleted(NotificationChannel.safeBool(xmlPullParser, ATT_DELETED, false));
        this.setGroup(xmlPullParser.getAttributeValue(null, ATT_GROUP));
        this.lockFields(NotificationChannel.safeInt(xmlPullParser, ATT_USER_LOCKED, 0));
        this.setFgServiceShown(NotificationChannel.safeBool(xmlPullParser, ATT_FG_SERVICE_SHOWN, false));
        this.setBlockableSystem(NotificationChannel.safeBool(xmlPullParser, ATT_BLOCKABLE_SYSTEM, false));
        this.setAllowBubbles(NotificationChannel.safeBool(xmlPullParser, ATT_ALLOW_BUBBLE, true));
    }

    private Uri restoreSoundUri(Context object, Uri uri) {
        if (uri != null && !Uri.EMPTY.equals(uri)) {
            uri = ((ContentResolver)(object = ((Context)object).getContentResolver())).canonicalize(uri);
            if (uri == null) {
                return Settings.System.DEFAULT_NOTIFICATION_URI;
            }
            return ((ContentResolver)object).uncanonicalize(uri);
        }
        return null;
    }

    private static AudioAttributes safeAudioAttributes(XmlPullParser xmlPullParser) {
        int n = NotificationChannel.safeInt(xmlPullParser, ATT_USAGE, 5);
        int n2 = NotificationChannel.safeInt(xmlPullParser, ATT_CONTENT_TYPE, 4);
        int n3 = NotificationChannel.safeInt(xmlPullParser, ATT_FLAGS, 0);
        return new AudioAttributes.Builder().setUsage(n).setContentType(n2).setFlags(n3).build();
    }

    private static boolean safeBool(XmlPullParser object, String string2, boolean bl) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return bl;
        }
        return Boolean.parseBoolean((String)object);
    }

    private static int safeInt(XmlPullParser xmlPullParser, String string2, int n) {
        return NotificationChannel.tryParseInt(xmlPullParser.getAttributeValue(null, string2), n);
    }

    private static long[] safeLongArray(XmlPullParser object, String string2, long[] arrl) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return arrl;
        }
        object = ((String)object).split(DELIMITER);
        arrl = new long[((Object)object).length];
        for (int i = 0; i < ((Object)object).length; ++i) {
            try {
                arrl[i] = Long.parseLong((String)object[i]);
                continue;
            }
            catch (NumberFormatException numberFormatException) {
                arrl[i] = 0L;
            }
        }
        return arrl;
    }

    private static Uri safeUri(XmlPullParser object, String string2) {
        Object var2_2 = null;
        object = (object = object.getAttributeValue(null, string2)) == null ? var2_2 : Uri.parse((String)object);
        return object;
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

    private void writeXml(XmlSerializer xmlSerializer, boolean bl, Context object) throws IOException {
        boolean bl2 = !bl || object != null;
        Preconditions.checkArgument(bl2, "forBackup is true but got null context");
        xmlSerializer.startTag(null, TAG_CHANNEL);
        xmlSerializer.attribute(null, ATT_ID, this.getId());
        if (this.getName() != null) {
            xmlSerializer.attribute(null, ATT_NAME, this.getName().toString());
        }
        if (this.getDescription() != null) {
            xmlSerializer.attribute(null, ATT_DESC, this.getDescription());
        }
        if (this.getImportance() != -1000) {
            xmlSerializer.attribute(null, ATT_IMPORTANCE, Integer.toString(this.getImportance()));
        }
        if (this.canBypassDnd()) {
            xmlSerializer.attribute(null, ATT_PRIORITY, Integer.toString(2));
        }
        if (this.getLockscreenVisibility() != -1000) {
            xmlSerializer.attribute(null, ATT_VISIBILITY, Integer.toString(this.getLockscreenVisibility()));
        }
        if ((object = bl ? this.getSoundForBackup((Context)object) : this.getSound()) != null) {
            xmlSerializer.attribute(null, ATT_SOUND, ((Uri)object).toString());
        }
        if (this.getAudioAttributes() != null) {
            xmlSerializer.attribute(null, ATT_USAGE, Integer.toString(this.getAudioAttributes().getUsage()));
            xmlSerializer.attribute(null, ATT_CONTENT_TYPE, Integer.toString(this.getAudioAttributes().getContentType()));
            xmlSerializer.attribute(null, ATT_FLAGS, Integer.toString(this.getAudioAttributes().getFlags()));
        }
        if (this.shouldShowLights()) {
            xmlSerializer.attribute(null, ATT_LIGHTS, Boolean.toString(this.shouldShowLights()));
        }
        if (this.getLightColor() != 0) {
            xmlSerializer.attribute(null, ATT_LIGHT_COLOR, Integer.toString(this.getLightColor()));
        }
        if (this.shouldVibrate()) {
            xmlSerializer.attribute(null, ATT_VIBRATION_ENABLED, Boolean.toString(this.shouldVibrate()));
        }
        if (this.getVibrationPattern() != null) {
            xmlSerializer.attribute(null, ATT_VIBRATION, NotificationChannel.longArrayToString(this.getVibrationPattern()));
        }
        if (this.getUserLockedFields() != 0) {
            xmlSerializer.attribute(null, ATT_USER_LOCKED, Integer.toString(this.getUserLockedFields()));
        }
        if (this.isFgServiceShown()) {
            xmlSerializer.attribute(null, ATT_FG_SERVICE_SHOWN, Boolean.toString(this.isFgServiceShown()));
        }
        if (this.canShowBadge()) {
            xmlSerializer.attribute(null, ATT_SHOW_BADGE, Boolean.toString(this.canShowBadge()));
        }
        if (this.isDeleted()) {
            xmlSerializer.attribute(null, ATT_DELETED, Boolean.toString(this.isDeleted()));
        }
        if (this.getGroup() != null) {
            xmlSerializer.attribute(null, ATT_GROUP, this.getGroup());
        }
        if (this.isBlockableSystem()) {
            xmlSerializer.attribute(null, ATT_BLOCKABLE_SYSTEM, Boolean.toString(this.isBlockableSystem()));
        }
        if (!this.canBubble()) {
            xmlSerializer.attribute(null, ATT_ALLOW_BUBBLE, Boolean.toString(this.canBubble()));
        }
        xmlSerializer.endTag(null, TAG_CHANNEL);
    }

    public boolean canBubble() {
        return this.mAllowBubbles;
    }

    public boolean canBypassDnd() {
        return this.mBypassDnd;
    }

    public boolean canShowBadge() {
        return this.mShowBadge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter, String string2, boolean bl) {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = this.mName;
        if (bl) {
            charSequence2 = TextUtils.trimToLengthWithEllipsis(charSequence, 3);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("NotificationChannel{mId='");
        ((StringBuilder)charSequence).append(this.mId);
        ((StringBuilder)charSequence).append('\'');
        ((StringBuilder)charSequence).append(", mName=");
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(", mDescription=");
        charSequence2 = !TextUtils.isEmpty(this.mDesc) ? "hasDescription " : "";
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(", mImportance=");
        ((StringBuilder)charSequence).append(this.mImportance);
        ((StringBuilder)charSequence).append(", mBypassDnd=");
        ((StringBuilder)charSequence).append(this.mBypassDnd);
        ((StringBuilder)charSequence).append(", mLockscreenVisibility=");
        ((StringBuilder)charSequence).append(this.mLockscreenVisibility);
        ((StringBuilder)charSequence).append(", mSound=");
        ((StringBuilder)charSequence).append(this.mSound);
        ((StringBuilder)charSequence).append(", mLights=");
        ((StringBuilder)charSequence).append(this.mLights);
        ((StringBuilder)charSequence).append(", mLightColor=");
        ((StringBuilder)charSequence).append(this.mLightColor);
        ((StringBuilder)charSequence).append(", mVibration=");
        ((StringBuilder)charSequence).append(Arrays.toString(this.mVibration));
        ((StringBuilder)charSequence).append(", mUserLockedFields=");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.mUserLockedFields));
        ((StringBuilder)charSequence).append(", mFgServiceShown=");
        ((StringBuilder)charSequence).append(this.mFgServiceShown);
        ((StringBuilder)charSequence).append(", mVibrationEnabled=");
        ((StringBuilder)charSequence).append(this.mVibrationEnabled);
        ((StringBuilder)charSequence).append(", mShowBadge=");
        ((StringBuilder)charSequence).append(this.mShowBadge);
        ((StringBuilder)charSequence).append(", mDeleted=");
        ((StringBuilder)charSequence).append(this.mDeleted);
        ((StringBuilder)charSequence).append(", mGroup='");
        ((StringBuilder)charSequence).append(this.mGroup);
        ((StringBuilder)charSequence).append('\'');
        ((StringBuilder)charSequence).append(", mAudioAttributes=");
        ((StringBuilder)charSequence).append(this.mAudioAttributes);
        ((StringBuilder)charSequence).append(", mBlockableSystem=");
        ((StringBuilder)charSequence).append(this.mBlockableSystem);
        ((StringBuilder)charSequence).append(", mAllowBubbles=");
        ((StringBuilder)charSequence).append(this.mAllowBubbles);
        ((StringBuilder)charSequence).append(", mImportanceLockedByOEM=");
        ((StringBuilder)charSequence).append(this.mImportanceLockedByOEM);
        ((StringBuilder)charSequence).append(", mImportanceLockedDefaultApp=");
        ((StringBuilder)charSequence).append(this.mImportanceLockedDefaultApp);
        ((StringBuilder)charSequence).append('}');
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(string2);
        ((StringBuilder)charSequence2).append((String)charSequence);
        printWriter.println(((StringBuilder)charSequence2).toString());
    }

    public void enableLights(boolean bl) {
        this.mLights = bl;
    }

    public void enableVibration(boolean bl) {
        this.mVibrationEnabled = bl;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (NotificationChannel)object;
            if (!(this.getImportance() == ((NotificationChannel)object).getImportance() && this.mBypassDnd == ((NotificationChannel)object).mBypassDnd && this.getLockscreenVisibility() == ((NotificationChannel)object).getLockscreenVisibility() && this.mLights == ((NotificationChannel)object).mLights && this.getLightColor() == ((NotificationChannel)object).getLightColor() && this.getUserLockedFields() == ((NotificationChannel)object).getUserLockedFields() && this.isFgServiceShown() == ((NotificationChannel)object).isFgServiceShown() && this.mVibrationEnabled == ((NotificationChannel)object).mVibrationEnabled && this.mShowBadge == ((NotificationChannel)object).mShowBadge && this.isDeleted() == ((NotificationChannel)object).isDeleted() && this.isBlockableSystem() == ((NotificationChannel)object).isBlockableSystem() && this.mAllowBubbles == ((NotificationChannel)object).mAllowBubbles && Objects.equals(this.getId(), ((NotificationChannel)object).getId()) && Objects.equals(this.getName(), ((NotificationChannel)object).getName()) && Objects.equals(this.mDesc, ((NotificationChannel)object).mDesc) && Objects.equals(this.getSound(), ((NotificationChannel)object).getSound()) && Arrays.equals(this.mVibration, ((NotificationChannel)object).mVibration) && Objects.equals(this.getGroup(), ((NotificationChannel)object).getGroup()) && Objects.equals(this.getAudioAttributes(), ((NotificationChannel)object).getAudioAttributes()) && this.mImportanceLockedByOEM == ((NotificationChannel)object).mImportanceLockedByOEM && this.mImportanceLockedDefaultApp == ((NotificationChannel)object).mImportanceLockedDefaultApp)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public String getDescription() {
        return this.mDesc;
    }

    public String getGroup() {
        return this.mGroup;
    }

    public String getId() {
        return this.mId;
    }

    public int getImportance() {
        return this.mImportance;
    }

    public int getLightColor() {
        return this.mLightColor;
    }

    public int getLockscreenVisibility() {
        return this.mLockscreenVisibility;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public Uri getSound() {
        return this.mSound;
    }

    @SystemApi
    public int getUserLockedFields() {
        return this.mUserLockedFields;
    }

    public long[] getVibrationPattern() {
        return this.mVibration;
    }

    public boolean hasUserSetImportance() {
        boolean bl = (this.mUserLockedFields & 4) != 0;
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.mDesc, this.getImportance(), this.mBypassDnd, this.getLockscreenVisibility(), this.getSound(), this.mLights, this.getLightColor(), this.getUserLockedFields(), this.isFgServiceShown(), this.mVibrationEnabled, this.mShowBadge, this.isDeleted(), this.getGroup(), this.getAudioAttributes(), this.isBlockableSystem(), this.mAllowBubbles, this.mImportanceLockedByOEM, this.mImportanceLockedDefaultApp) * 31 + Arrays.hashCode(this.mVibration);
    }

    public boolean isBlockableSystem() {
        return this.mBlockableSystem;
    }

    @SystemApi
    public boolean isDeleted() {
        return this.mDeleted;
    }

    public boolean isFgServiceShown() {
        return this.mFgServiceShown;
    }

    public boolean isImportanceLockedByCriticalDeviceFunction() {
        return this.mImportanceLockedDefaultApp;
    }

    public boolean isImportanceLockedByOEM() {
        return this.mImportanceLockedByOEM;
    }

    public void lockFields(int n) {
        this.mUserLockedFields |= n;
    }

    @SystemApi
    public void populateFromXml(XmlPullParser xmlPullParser) {
        this.populateFromXml(xmlPullParser, false, null);
    }

    public void populateFromXmlForRestore(XmlPullParser xmlPullParser, Context context) {
        this.populateFromXml(xmlPullParser, true, context);
    }

    public void setAllowBubbles(boolean bl) {
        this.mAllowBubbles = bl;
    }

    @UnsupportedAppUsage
    public void setBlockableSystem(boolean bl) {
        this.mBlockableSystem = bl;
    }

    public void setBypassDnd(boolean bl) {
        this.mBypassDnd = bl;
    }

    public void setDeleted(boolean bl) {
        this.mDeleted = bl;
    }

    public void setDescription(String string2) {
        this.mDesc = this.getTrimmedString(string2);
    }

    public void setFgServiceShown(boolean bl) {
        this.mFgServiceShown = bl;
    }

    public void setGroup(String string2) {
        this.mGroup = string2;
    }

    public void setImportance(int n) {
        this.mImportance = n;
    }

    public void setImportanceLockedByCriticalDeviceFunction(boolean bl) {
        this.mImportanceLockedDefaultApp = bl;
    }

    public void setImportanceLockedByOEM(boolean bl) {
        this.mImportanceLockedByOEM = bl;
    }

    public void setLightColor(int n) {
        this.mLightColor = n;
    }

    public void setLockscreenVisibility(int n) {
        this.mLockscreenVisibility = n;
    }

    public void setName(CharSequence charSequence) {
        charSequence = charSequence != null ? this.getTrimmedString(charSequence.toString()) : null;
        this.mName = charSequence;
    }

    public void setShowBadge(boolean bl) {
        this.mShowBadge = bl;
    }

    public void setSound(Uri uri, AudioAttributes audioAttributes) {
        this.mSound = uri;
        this.mAudioAttributes = audioAttributes;
    }

    public void setVibrationPattern(long[] arrl) {
        boolean bl = arrl != null && arrl.length > 0;
        this.mVibrationEnabled = bl;
        this.mVibration = arrl;
    }

    public boolean shouldShowLights() {
        return this.mLights;
    }

    public boolean shouldVibrate() {
        return this.mVibrationEnabled;
    }

    @SystemApi
    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ATT_ID, (Object)this.getId());
        jSONObject.put(ATT_NAME, (Object)this.getName());
        jSONObject.put(ATT_DESC, (Object)this.getDescription());
        if (this.getImportance() != -1000) {
            jSONObject.put(ATT_IMPORTANCE, (Object)NotificationListenerService.Ranking.importanceToString(this.getImportance()));
        }
        if (this.canBypassDnd()) {
            jSONObject.put(ATT_PRIORITY, 2);
        }
        if (this.getLockscreenVisibility() != -1000) {
            jSONObject.put(ATT_VISIBILITY, (Object)Notification.visibilityToString(this.getLockscreenVisibility()));
        }
        if (this.getSound() != null) {
            jSONObject.put(ATT_SOUND, (Object)this.getSound().toString());
        }
        if (this.getAudioAttributes() != null) {
            jSONObject.put(ATT_USAGE, (Object)Integer.toString(this.getAudioAttributes().getUsage()));
            jSONObject.put(ATT_CONTENT_TYPE, (Object)Integer.toString(this.getAudioAttributes().getContentType()));
            jSONObject.put(ATT_FLAGS, (Object)Integer.toString(this.getAudioAttributes().getFlags()));
        }
        jSONObject.put(ATT_LIGHTS, (Object)Boolean.toString(this.shouldShowLights()));
        jSONObject.put(ATT_LIGHT_COLOR, (Object)Integer.toString(this.getLightColor()));
        jSONObject.put(ATT_VIBRATION_ENABLED, (Object)Boolean.toString(this.shouldVibrate()));
        jSONObject.put(ATT_USER_LOCKED, (Object)Integer.toString(this.getUserLockedFields()));
        jSONObject.put(ATT_FG_SERVICE_SHOWN, (Object)Boolean.toString(this.isFgServiceShown()));
        jSONObject.put(ATT_VIBRATION, (Object)NotificationChannel.longArrayToString(this.getVibrationPattern()));
        jSONObject.put(ATT_SHOW_BADGE, (Object)Boolean.toString(this.canShowBadge()));
        jSONObject.put(ATT_DELETED, (Object)Boolean.toString(this.isDeleted()));
        jSONObject.put(ATT_GROUP, (Object)this.getGroup());
        jSONObject.put(ATT_BLOCKABLE_SYSTEM, this.isBlockableSystem());
        jSONObject.put(ATT_ALLOW_BUBBLE, this.canBubble());
        return jSONObject;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NotificationChannel{mId='");
        stringBuilder.append(this.mId);
        stringBuilder.append('\'');
        stringBuilder.append(", mName=");
        stringBuilder.append(this.mName);
        stringBuilder.append(", mDescription=");
        String string2 = !TextUtils.isEmpty(this.mDesc) ? "hasDescription " : "";
        stringBuilder.append(string2);
        stringBuilder.append(", mImportance=");
        stringBuilder.append(this.mImportance);
        stringBuilder.append(", mBypassDnd=");
        stringBuilder.append(this.mBypassDnd);
        stringBuilder.append(", mLockscreenVisibility=");
        stringBuilder.append(this.mLockscreenVisibility);
        stringBuilder.append(", mSound=");
        stringBuilder.append(this.mSound);
        stringBuilder.append(", mLights=");
        stringBuilder.append(this.mLights);
        stringBuilder.append(", mLightColor=");
        stringBuilder.append(this.mLightColor);
        stringBuilder.append(", mVibration=");
        stringBuilder.append(Arrays.toString(this.mVibration));
        stringBuilder.append(", mUserLockedFields=");
        stringBuilder.append(Integer.toHexString(this.mUserLockedFields));
        stringBuilder.append(", mFgServiceShown=");
        stringBuilder.append(this.mFgServiceShown);
        stringBuilder.append(", mVibrationEnabled=");
        stringBuilder.append(this.mVibrationEnabled);
        stringBuilder.append(", mShowBadge=");
        stringBuilder.append(this.mShowBadge);
        stringBuilder.append(", mDeleted=");
        stringBuilder.append(this.mDeleted);
        stringBuilder.append(", mGroup='");
        stringBuilder.append(this.mGroup);
        stringBuilder.append('\'');
        stringBuilder.append(", mAudioAttributes=");
        stringBuilder.append(this.mAudioAttributes);
        stringBuilder.append(", mBlockableSystem=");
        stringBuilder.append(this.mBlockableSystem);
        stringBuilder.append(", mAllowBubbles=");
        stringBuilder.append(this.mAllowBubbles);
        stringBuilder.append(", mImportanceLockedByOEM=");
        stringBuilder.append(this.mImportanceLockedByOEM);
        stringBuilder.append(", mImportanceLockedDefaultApp=");
        stringBuilder.append(this.mImportanceLockedDefaultApp);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void unlockFields(int n) {
        this.mUserLockedFields &= n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mId != null) {
            parcel.writeByte((byte)1);
            parcel.writeString(this.mId);
        } else {
            parcel.writeByte((byte)0);
        }
        if (this.mName != null) {
            parcel.writeByte((byte)1);
            parcel.writeString(this.mName);
        } else {
            parcel.writeByte((byte)0);
        }
        if (this.mDesc != null) {
            parcel.writeByte((byte)1);
            parcel.writeString(this.mDesc);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeInt(this.mImportance);
        parcel.writeByte((byte)this.mBypassDnd);
        parcel.writeInt(this.mLockscreenVisibility);
        if (this.mSound != null) {
            parcel.writeByte((byte)1);
            this.mSound.writeToParcel(parcel, 0);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeByte((byte)this.mLights);
        parcel.writeLongArray(this.mVibration);
        parcel.writeInt(this.mUserLockedFields);
        parcel.writeByte((byte)this.mFgServiceShown);
        parcel.writeByte((byte)this.mVibrationEnabled);
        parcel.writeByte((byte)this.mShowBadge);
        parcel.writeByte((byte)this.mDeleted);
        if (this.mGroup != null) {
            parcel.writeByte((byte)1);
            parcel.writeString(this.mGroup);
        } else {
            parcel.writeByte((byte)0);
        }
        if (this.mAudioAttributes != null) {
            parcel.writeInt(1);
            this.mAudioAttributes.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mLightColor);
        parcel.writeBoolean(this.mBlockableSystem);
        parcel.writeBoolean(this.mAllowBubbles);
        parcel.writeBoolean(this.mImportanceLockedByOEM);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.mId);
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.write(1138166333443L, this.mDesc);
        protoOutputStream.write(1120986464260L, this.mImportance);
        protoOutputStream.write(1133871366149L, this.mBypassDnd);
        protoOutputStream.write(1120986464262L, this.mLockscreenVisibility);
        Object object = this.mSound;
        if (object != null) {
            protoOutputStream.write(1138166333447L, ((Uri)object).toString());
        }
        protoOutputStream.write(1133871366152L, this.mLights);
        protoOutputStream.write(1120986464265L, this.mLightColor);
        object = this.mVibration;
        if (object != null) {
            int n = ((long[])object).length;
            for (int i = 0; i < n; ++i) {
                protoOutputStream.write(2211908157450L, object[i]);
            }
        }
        protoOutputStream.write(1120986464267L, this.mUserLockedFields);
        protoOutputStream.write(1133871366162L, this.mFgServiceShown);
        protoOutputStream.write(1133871366156L, this.mVibrationEnabled);
        protoOutputStream.write(1133871366157L, this.mShowBadge);
        protoOutputStream.write(1133871366158L, this.mDeleted);
        protoOutputStream.write(1138166333455L, this.mGroup);
        object = this.mAudioAttributes;
        if (object != null) {
            ((AudioAttributes)object).writeToProto(protoOutputStream, 1146756268048L);
        }
        protoOutputStream.write(1133871366161L, this.mBlockableSystem);
        protoOutputStream.write(1133871366163L, this.mAllowBubbles);
        protoOutputStream.end(l);
    }

    @SystemApi
    public void writeXml(XmlSerializer xmlSerializer) throws IOException {
        this.writeXml(xmlSerializer, false, null);
    }

    public void writeXmlForBackup(XmlSerializer xmlSerializer, Context context) throws IOException {
        this.writeXml(xmlSerializer, true, context);
    }

}

