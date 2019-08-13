/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Person;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.Set;

public final class ShortcutInfo
implements Parcelable {
    private static final String ANDROID_PACKAGE_NAME = "android";
    public static final int CLONE_REMOVE_FOR_APP_PREDICTION = 9;
    public static final int CLONE_REMOVE_FOR_CREATOR = 9;
    public static final int CLONE_REMOVE_FOR_LAUNCHER = 27;
    public static final int CLONE_REMOVE_FOR_LAUNCHER_APPROVAL = 26;
    private static final int CLONE_REMOVE_ICON = 1;
    private static final int CLONE_REMOVE_INTENT = 2;
    public static final int CLONE_REMOVE_NON_KEY_INFO = 4;
    public static final int CLONE_REMOVE_PERSON = 16;
    public static final int CLONE_REMOVE_RES_NAMES = 8;
    public static final Parcelable.Creator<ShortcutInfo> CREATOR = new Parcelable.Creator<ShortcutInfo>(){

        @Override
        public ShortcutInfo createFromParcel(Parcel parcel) {
            return new ShortcutInfo(parcel);
        }

        public ShortcutInfo[] newArray(int n) {
            return new ShortcutInfo[n];
        }
    };
    public static final int DISABLED_REASON_APP_CHANGED = 2;
    public static final int DISABLED_REASON_BACKUP_NOT_SUPPORTED = 101;
    public static final int DISABLED_REASON_BY_APP = 1;
    public static final int DISABLED_REASON_NOT_DISABLED = 0;
    public static final int DISABLED_REASON_OTHER_RESTORE_ISSUE = 103;
    private static final int DISABLED_REASON_RESTORE_ISSUE_START = 100;
    public static final int DISABLED_REASON_SIGNATURE_MISMATCH = 102;
    public static final int DISABLED_REASON_UNKNOWN = 3;
    public static final int DISABLED_REASON_VERSION_LOWER = 100;
    public static final int FLAG_ADAPTIVE_BITMAP = 512;
    public static final int FLAG_DISABLED = 64;
    public static final int FLAG_DYNAMIC = 1;
    public static final int FLAG_HAS_ICON_FILE = 8;
    public static final int FLAG_HAS_ICON_RES = 4;
    public static final int FLAG_ICON_FILE_PENDING_SAVE = 2048;
    public static final int FLAG_IMMUTABLE = 256;
    public static final int FLAG_KEY_FIELDS_ONLY = 16;
    public static final int FLAG_LONG_LIVED = 8192;
    public static final int FLAG_MANIFEST = 32;
    public static final int FLAG_PINNED = 2;
    public static final int FLAG_RETURNED_BY_SERVICE = 1024;
    public static final int FLAG_SHADOW = 4096;
    public static final int FLAG_STRINGS_RESOLVED = 128;
    private static final int IMPLICIT_RANK_MASK = Integer.MAX_VALUE;
    private static final int RANK_CHANGED_BIT = Integer.MIN_VALUE;
    public static final int RANK_NOT_SET = Integer.MAX_VALUE;
    private static final String RES_TYPE_STRING = "string";
    public static final String SHORTCUT_CATEGORY_CONVERSATION = "android.shortcut.conversation";
    static final String TAG = "Shortcut";
    public static final int VERSION_CODE_UNKNOWN = -1;
    private ComponentName mActivity;
    private String mBitmapPath;
    private ArraySet<String> mCategories;
    private CharSequence mDisabledMessage;
    private int mDisabledMessageResId;
    private String mDisabledMessageResName;
    private int mDisabledReason;
    private PersistableBundle mExtras;
    private int mFlags;
    private Icon mIcon;
    private int mIconResId;
    private String mIconResName;
    private final String mId;
    private int mImplicitRank;
    private PersistableBundle[] mIntentPersistableExtrases;
    private Intent[] mIntents;
    private long mLastChangedTimestamp;
    private LocusId mLocusId;
    private final String mPackageName;
    private Person[] mPersons;
    private int mRank;
    private CharSequence mText;
    private int mTextResId;
    private String mTextResName;
    private CharSequence mTitle;
    private int mTitleResId;
    private String mTitleResName;
    private final int mUserId;

    public ShortcutInfo(int n, String string2, String string3, ComponentName componentName, Icon icon, CharSequence charSequence, int n2, String string4, CharSequence charSequence2, int n3, String string5, CharSequence charSequence3, int n4, String string6, Set<String> set, Intent[] arrintent, int n5, PersistableBundle persistableBundle, long l, int n6, int n7, String string7, String string8, int n8, Person[] arrperson, LocusId locusId) {
        this.mUserId = n;
        this.mId = string2;
        this.mPackageName = string3;
        this.mActivity = componentName;
        this.mIcon = icon;
        this.mTitle = charSequence;
        this.mTitleResId = n2;
        this.mTitleResName = string4;
        this.mText = charSequence2;
        this.mTextResId = n3;
        this.mTextResName = string5;
        this.mDisabledMessage = charSequence3;
        this.mDisabledMessageResId = n4;
        this.mDisabledMessageResName = string6;
        this.mCategories = ShortcutInfo.cloneCategories(set);
        this.mIntents = ShortcutInfo.cloneIntents(arrintent);
        this.fixUpIntentExtras();
        this.mRank = n5;
        this.mExtras = persistableBundle;
        this.mLastChangedTimestamp = l;
        this.mFlags = n6;
        this.mIconResId = n7;
        this.mIconResName = string7;
        this.mBitmapPath = string8;
        this.mDisabledReason = n8;
        this.mPersons = arrperson;
        this.mLocusId = locusId;
    }

    private ShortcutInfo(Builder builder) {
        this.mUserId = builder.mContext.getUserId();
        this.mId = Preconditions.checkStringNotEmpty(builder.mId, "Shortcut ID must be provided");
        this.mPackageName = builder.mContext.getPackageName();
        this.mActivity = builder.mActivity;
        this.mIcon = builder.mIcon;
        this.mTitle = builder.mTitle;
        this.mTitleResId = builder.mTitleResId;
        this.mText = builder.mText;
        this.mTextResId = builder.mTextResId;
        this.mDisabledMessage = builder.mDisabledMessage;
        this.mDisabledMessageResId = builder.mDisabledMessageResId;
        this.mCategories = ShortcutInfo.cloneCategories(builder.mCategories);
        this.mIntents = ShortcutInfo.cloneIntents(builder.mIntents);
        this.fixUpIntentExtras();
        this.mPersons = ShortcutInfo.clonePersons(builder.mPersons);
        if (builder.mIsLongLived) {
            this.setLongLived();
        }
        this.mRank = builder.mRank;
        this.mExtras = builder.mExtras;
        this.mLocusId = builder.mLocusId;
        this.updateTimestamp();
    }

    private ShortcutInfo(ShortcutInfo shortcutInfo, int n) {
        this.mUserId = shortcutInfo.mUserId;
        this.mId = shortcutInfo.mId;
        this.mPackageName = shortcutInfo.mPackageName;
        this.mActivity = shortcutInfo.mActivity;
        this.mFlags = shortcutInfo.mFlags;
        this.mLastChangedTimestamp = shortcutInfo.mLastChangedTimestamp;
        this.mDisabledReason = shortcutInfo.mDisabledReason;
        this.mLocusId = shortcutInfo.mLocusId;
        this.mIconResId = shortcutInfo.mIconResId;
        if ((n & 4) == 0) {
            if ((n & 1) == 0) {
                this.mIcon = shortcutInfo.mIcon;
                this.mBitmapPath = shortcutInfo.mBitmapPath;
            }
            this.mTitle = shortcutInfo.mTitle;
            this.mTitleResId = shortcutInfo.mTitleResId;
            this.mText = shortcutInfo.mText;
            this.mTextResId = shortcutInfo.mTextResId;
            this.mDisabledMessage = shortcutInfo.mDisabledMessage;
            this.mDisabledMessageResId = shortcutInfo.mDisabledMessageResId;
            this.mCategories = ShortcutInfo.cloneCategories(shortcutInfo.mCategories);
            if ((n & 16) == 0) {
                this.mPersons = ShortcutInfo.clonePersons(shortcutInfo.mPersons);
            }
            if ((n & 2) == 0) {
                this.mIntents = ShortcutInfo.cloneIntents(shortcutInfo.mIntents);
                this.mIntentPersistableExtrases = ShortcutInfo.clonePersistableBundle(shortcutInfo.mIntentPersistableExtrases);
            }
            this.mRank = shortcutInfo.mRank;
            this.mExtras = shortcutInfo.mExtras;
            if ((n & 8) == 0) {
                this.mTitleResName = shortcutInfo.mTitleResName;
                this.mTextResName = shortcutInfo.mTextResName;
                this.mDisabledMessageResName = shortcutInfo.mDisabledMessageResName;
                this.mIconResName = shortcutInfo.mIconResName;
            }
        } else {
            this.mFlags |= 16;
        }
    }

    private ShortcutInfo(Parcel parcel) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        this.mUserId = parcel.readInt();
        this.mId = parcel.readString();
        this.mPackageName = parcel.readString();
        this.mActivity = (ComponentName)parcel.readParcelable(classLoader);
        this.mFlags = parcel.readInt();
        this.mIconResId = parcel.readInt();
        this.mLastChangedTimestamp = parcel.readLong();
        this.mDisabledReason = parcel.readInt();
        if (parcel.readInt() == 0) {
            return;
        }
        this.mIcon = (Icon)parcel.readParcelable(classLoader);
        this.mTitle = parcel.readCharSequence();
        this.mTitleResId = parcel.readInt();
        this.mText = parcel.readCharSequence();
        this.mTextResId = parcel.readInt();
        this.mDisabledMessage = parcel.readCharSequence();
        this.mDisabledMessageResId = parcel.readInt();
        this.mIntents = (Intent[])parcel.readParcelableArray(classLoader, Intent.class);
        this.mIntentPersistableExtrases = (PersistableBundle[])parcel.readParcelableArray(classLoader, PersistableBundle.class);
        this.mRank = parcel.readInt();
        this.mExtras = (PersistableBundle)parcel.readParcelable(classLoader);
        this.mBitmapPath = parcel.readString();
        this.mIconResName = parcel.readString();
        this.mTitleResName = parcel.readString();
        this.mTextResName = parcel.readString();
        this.mDisabledMessageResName = parcel.readString();
        int n = parcel.readInt();
        if (n == 0) {
            this.mCategories = null;
        } else {
            this.mCategories = new ArraySet(n);
            for (int i = 0; i < n; ++i) {
                this.mCategories.add(parcel.readString().intern());
            }
        }
        this.mPersons = (Person[])parcel.readParcelableArray(classLoader, Person.class);
        this.mLocusId = (LocusId)parcel.readParcelable(classLoader);
    }

    private void addIndentOrComma(StringBuilder stringBuilder, String string2) {
        if (string2 != null) {
            stringBuilder.append("\n  ");
            stringBuilder.append(string2);
        } else {
            stringBuilder.append(", ");
        }
    }

    private static ArraySet<String> cloneCategories(Set<String> object) {
        if (object == null) {
            return null;
        }
        ArraySet<String> arraySet = new ArraySet<String>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            CharSequence charSequence = (CharSequence)object.next();
            if (TextUtils.isEmpty(charSequence)) continue;
            arraySet.add(charSequence.toString().intern());
        }
        return arraySet;
    }

    private static Intent[] cloneIntents(Intent[] arrintent) {
        if (arrintent == null) {
            return null;
        }
        Intent[] arrintent2 = new Intent[arrintent.length];
        for (int i = 0; i < arrintent2.length; ++i) {
            if (arrintent[i] == null) continue;
            arrintent2[i] = new Intent(arrintent[i]);
        }
        return arrintent2;
    }

    private static PersistableBundle[] clonePersistableBundle(PersistableBundle[] arrpersistableBundle) {
        if (arrpersistableBundle == null) {
            return null;
        }
        PersistableBundle[] arrpersistableBundle2 = new PersistableBundle[arrpersistableBundle.length];
        for (int i = 0; i < arrpersistableBundle2.length; ++i) {
            if (arrpersistableBundle[i] == null) continue;
            arrpersistableBundle2[i] = new PersistableBundle(arrpersistableBundle[i]);
        }
        return arrpersistableBundle2;
    }

    private static Person[] clonePersons(Person[] arrperson) {
        if (arrperson == null) {
            return null;
        }
        Person[] arrperson2 = new Person[arrperson.length];
        for (int i = 0; i < arrperson2.length; ++i) {
            if (arrperson[i] == null) continue;
            arrperson2[i] = arrperson[i].toBuilder().setIcon(null).build();
        }
        return arrperson2;
    }

    private void fixUpIntentExtras() {
        Object object = this.mIntents;
        if (object == null) {
            this.mIntentPersistableExtrases = null;
            return;
        }
        this.mIntentPersistableExtrases = new PersistableBundle[((Intent[])object).length];
        for (int i = 0; i < ((Intent[])(object = this.mIntents)).length; ++i) {
            Intent intent = object[i];
            object = intent.getExtras();
            if (object == null) {
                this.mIntentPersistableExtrases[i] = null;
                continue;
            }
            this.mIntentPersistableExtrases[i] = new PersistableBundle((Bundle)object);
            intent.replaceExtras((Bundle)null);
        }
    }

    public static String getDisabledReasonDebugString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    switch (n) {
                        default: {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("[Disabled: unknown reason:");
                            stringBuilder.append(n);
                            stringBuilder.append("]");
                            return stringBuilder.toString();
                        }
                        case 103: {
                            return "[Disabled: unknown restore issue]";
                        }
                        case 102: {
                            return "[Disabled: signature mismatch]";
                        }
                        case 101: {
                            return "[Disabled: backup not supported]";
                        }
                        case 100: 
                    }
                    return "[Disabled: lower version]";
                }
                return "[Disabled: app changed]";
            }
            return "[Disabled: by app]";
        }
        return "[Not disabled]";
    }

    public static String getDisabledReasonForRestoreIssue(Context object, int n) {
        object = ((Context)object).getResources();
        if (n != 3) {
            switch (n) {
                default: {
                    return null;
                }
                case 103: {
                    return ((Resources)object).getString(17041017);
                }
                case 102: {
                    return ((Resources)object).getString(17041016);
                }
                case 101: {
                    return ((Resources)object).getString(17041015);
                }
                case 100: 
            }
            return ((Resources)object).getString(17041018);
        }
        return ((Resources)object).getString(17041014);
    }

    public static IllegalArgumentException getInvalidIconException() {
        return new IllegalArgumentException("Unsupported icon type: only the bitmap and resource types are supported");
    }

    @VisibleForTesting
    public static String getResourceEntryName(String string2) {
        int n = string2.indexOf(47);
        if (n < 0) {
            return null;
        }
        return string2.substring(n + 1);
    }

    @VisibleForTesting
    public static String getResourcePackageName(String string2) {
        int n = string2.indexOf(58);
        if (n < 0) {
            return null;
        }
        return string2.substring(0, n);
    }

    private CharSequence getResourceString(Resources object, int n, CharSequence charSequence) {
        try {
            object = ((Resources)object).getString(n);
            return object;
        }
        catch (Resources.NotFoundException notFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resource for ID=");
            stringBuilder.append(n);
            stringBuilder.append(" not found in package ");
            stringBuilder.append(this.mPackageName);
            Log.e(TAG, stringBuilder.toString());
            return charSequence;
        }
    }

    @VisibleForTesting
    public static String getResourceTypeAndEntryName(String string2) {
        int n = string2.indexOf(58);
        if (n < 0) {
            return null;
        }
        return string2.substring(n + 1);
    }

    @VisibleForTesting
    public static String getResourceTypeName(String string2) {
        int n = string2.indexOf(58);
        if (n < 0) {
            return null;
        }
        int n2 = string2.indexOf(47, n + 1);
        if (n2 < 0) {
            return null;
        }
        return string2.substring(n + 1, n2);
    }

    public static boolean isDisabledForRestoreIssue(int n) {
        boolean bl = n >= 100;
        return bl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public static int lookUpResourceId(Resources resources, String string2, String string3, String string4) {
        block5 : {
            if (string2 == null) {
                return 0;
            }
            try {
                return Integer.parseInt(string2);
            }
            catch (Resources.NotFoundException notFoundException) {
                break block5;
            }
            catch (NumberFormatException numberFormatException) {
                return resources.getIdentifier(string2, string3, string4);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Resource ID for name=");
        stringBuilder.append(string2);
        stringBuilder.append(" not found in package ");
        stringBuilder.append(string4);
        Log.e(TAG, stringBuilder.toString());
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public static String lookUpResourceName(Resources object, int n, boolean bl, String string2) {
        if (n == 0) {
            return null;
        }
        try {
            object = ((Resources)object).getResourceName(n);
            if (ANDROID_PACKAGE_NAME.equals(ShortcutInfo.getResourcePackageName((String)object))) {
                return String.valueOf(n);
            }
            if (!bl) return ShortcutInfo.getResourceEntryName((String)object);
        }
        catch (Resources.NotFoundException notFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resource name for ID=");
            stringBuilder.append(n);
            stringBuilder.append(" not found in package ");
            stringBuilder.append(string2);
            stringBuilder.append(". Resource IDs may change when the application is upgraded, and the system may not be able to find the correct resource.");
            Log.e(TAG, stringBuilder.toString());
            return null;
        }
        return ShortcutInfo.getResourceTypeAndEntryName((String)object);
    }

    public static Intent setIntentExtras(Intent intent, PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            intent.replaceExtras((Bundle)null);
        } else {
            intent.replaceExtras(new Bundle(persistableBundle));
        }
        return intent;
    }

    private String toStringInner(boolean bl, boolean bl2, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        if (string2 != null) {
            stringBuilder.append(string2);
        }
        stringBuilder.append("ShortcutInfo {");
        stringBuilder.append("id=");
        String string3 = "***";
        Object object = bl ? "***" : this.mId;
        stringBuilder.append((String)object);
        stringBuilder.append(", flags=0x");
        stringBuilder.append(Integer.toHexString(this.mFlags));
        stringBuilder.append(" [");
        if ((this.mFlags & 4096) != 0) {
            stringBuilder.append("Sdw");
        }
        if (!this.isEnabled()) {
            stringBuilder.append("Dis");
        }
        if (this.isImmutable()) {
            stringBuilder.append("Im");
        }
        if (this.isManifestShortcut()) {
            stringBuilder.append("Man");
        }
        if (this.isDynamic()) {
            stringBuilder.append("Dyn");
        }
        if (this.isPinned()) {
            stringBuilder.append("Pin");
        }
        if (this.hasIconFile()) {
            stringBuilder.append("Ic-f");
        }
        if (this.isIconPendingSave()) {
            stringBuilder.append("Pens");
        }
        if (this.hasIconResource()) {
            stringBuilder.append("Ic-r");
        }
        if (this.hasKeyFieldsOnly()) {
            stringBuilder.append("Key");
        }
        if (this.hasStringResourcesResolved()) {
            stringBuilder.append("Str");
        }
        if (this.isReturnedByServer()) {
            stringBuilder.append("Rets");
        }
        if (this.isLongLived()) {
            stringBuilder.append("Liv");
        }
        stringBuilder.append("]");
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("packageName=");
        stringBuilder.append(this.mPackageName);
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("activity=");
        stringBuilder.append(this.mActivity);
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("shortLabel=");
        object = bl ? "***" : this.mTitle;
        stringBuilder.append((CharSequence)object);
        stringBuilder.append(", resId=");
        stringBuilder.append(this.mTitleResId);
        stringBuilder.append("[");
        stringBuilder.append(this.mTitleResName);
        stringBuilder.append("]");
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("longLabel=");
        object = bl ? "***" : this.mText;
        stringBuilder.append((CharSequence)object);
        stringBuilder.append(", resId=");
        stringBuilder.append(this.mTextResId);
        stringBuilder.append("[");
        stringBuilder.append(this.mTextResName);
        stringBuilder.append("]");
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("disabledMessage=");
        object = bl ? string3 : this.mDisabledMessage;
        stringBuilder.append((CharSequence)object);
        stringBuilder.append(", resId=");
        stringBuilder.append(this.mDisabledMessageResId);
        stringBuilder.append("[");
        stringBuilder.append(this.mDisabledMessageResName);
        stringBuilder.append("]");
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("disabledReason=");
        stringBuilder.append(ShortcutInfo.getDisabledReasonDebugString(this.mDisabledReason));
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("categories=");
        stringBuilder.append(this.mCategories);
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("persons=");
        stringBuilder.append(this.mPersons);
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("icon=");
        stringBuilder.append(this.mIcon);
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("rank=");
        stringBuilder.append(this.mRank);
        stringBuilder.append(", timestamp=");
        stringBuilder.append(this.mLastChangedTimestamp);
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("intents=");
        object = this.mIntents;
        if (object == null) {
            stringBuilder.append("null");
        } else if (bl) {
            stringBuilder.append("size:");
            stringBuilder.append(this.mIntents.length);
        } else {
            int n = ((Intent[])object).length;
            stringBuilder.append("[");
            object = "";
            for (int i = 0; i < n; ++i) {
                stringBuilder.append((String)object);
                object = ", ";
                stringBuilder.append(this.mIntents[i]);
                stringBuilder.append("/");
                stringBuilder.append(this.mIntentPersistableExtrases[i]);
            }
            stringBuilder.append("]");
        }
        this.addIndentOrComma(stringBuilder, string2);
        stringBuilder.append("extras=");
        stringBuilder.append(this.mExtras);
        if (bl2) {
            this.addIndentOrComma(stringBuilder, string2);
            stringBuilder.append("iconRes=");
            stringBuilder.append(this.mIconResId);
            stringBuilder.append("[");
            stringBuilder.append(this.mIconResName);
            stringBuilder.append("]");
            stringBuilder.append(", bitmapPath=");
            stringBuilder.append(this.mBitmapPath);
        }
        if (this.mLocusId != null) {
            stringBuilder.append("locusId=");
            stringBuilder.append(this.mLocusId);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static Icon validateIcon(Icon icon) {
        int n = icon.getType();
        if (n != 1 && n != 2 && n != 5) {
            throw ShortcutInfo.getInvalidIconException();
        }
        if (!icon.hasTint()) {
            return icon;
        }
        throw new IllegalArgumentException("Icons with tints are not supported");
    }

    public void addFlags(int n) {
        this.mFlags |= n;
    }

    public void clearFlags(int n) {
        this.mFlags &= n;
    }

    public void clearIcon() {
        this.mIcon = null;
    }

    public void clearIconPendingSave() {
        this.clearFlags(2048);
    }

    public void clearImplicitRankAndRankChangedFlag() {
        this.mImplicitRank = 0;
    }

    public ShortcutInfo clone(int n) {
        return new ShortcutInfo(this, n);
    }

    public void copyNonNullFieldsFrom(ShortcutInfo parcelable) {
        int n;
        this.ensureUpdatableWith((ShortcutInfo)parcelable, true);
        Object object = parcelable.mActivity;
        if (object != null) {
            this.mActivity = object;
        }
        if ((object = parcelable.mIcon) != null) {
            this.mIcon = object;
            this.mIconResId = 0;
            this.mIconResName = null;
            this.mBitmapPath = null;
        }
        if ((object = parcelable.mTitle) != null) {
            this.mTitle = object;
            this.mTitleResId = 0;
            this.mTitleResName = null;
        } else {
            n = parcelable.mTitleResId;
            if (n != 0) {
                this.mTitle = null;
                this.mTitleResId = n;
                this.mTitleResName = null;
            }
        }
        object = parcelable.mText;
        if (object != null) {
            this.mText = object;
            this.mTextResId = 0;
            this.mTextResName = null;
        } else {
            n = parcelable.mTextResId;
            if (n != 0) {
                this.mText = null;
                this.mTextResId = n;
                this.mTextResName = null;
            }
        }
        object = parcelable.mDisabledMessage;
        if (object != null) {
            this.mDisabledMessage = object;
            this.mDisabledMessageResId = 0;
            this.mDisabledMessageResName = null;
        } else {
            n = parcelable.mDisabledMessageResId;
            if (n != 0) {
                this.mDisabledMessage = null;
                this.mDisabledMessageResId = n;
                this.mDisabledMessageResName = null;
            }
        }
        object = parcelable.mCategories;
        if (object != null) {
            this.mCategories = ShortcutInfo.cloneCategories(object);
        }
        if ((object = parcelable.mPersons) != null) {
            this.mPersons = ShortcutInfo.clonePersons((Person[])object);
        }
        if ((object = parcelable.mIntents) != null) {
            this.mIntents = ShortcutInfo.cloneIntents((Intent[])object);
            this.mIntentPersistableExtrases = ShortcutInfo.clonePersistableBundle(parcelable.mIntentPersistableExtrases);
        }
        if ((n = parcelable.mRank) != Integer.MAX_VALUE) {
            this.mRank = n;
        }
        if ((object = parcelable.mExtras) != null) {
            this.mExtras = object;
        }
        if ((parcelable = parcelable.mLocusId) != null) {
            this.mLocusId = parcelable;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void enforceMandatoryFields(boolean bl) {
        Preconditions.checkStringNotEmpty(this.mId, "Shortcut ID must be provided");
        if (!bl) {
            Preconditions.checkNotNull(this.mActivity, "Activity must be provided");
        }
        if (this.mTitle == null && this.mTitleResId == 0) {
            throw new IllegalArgumentException("Short label must be provided");
        }
        Preconditions.checkNotNull(this.mIntents, "Shortcut Intent must be provided");
        bl = this.mIntents.length > 0;
        Preconditions.checkArgument(bl, "Shortcut Intent must be provided");
    }

    public void ensureUpdatableWith(ShortcutInfo shortcutInfo, boolean bl) {
        if (bl) {
            Preconditions.checkState(this.isVisibleToPublisher(), "[Framework BUG] Invisible shortcuts can't be updated");
        }
        bl = this.mUserId == shortcutInfo.mUserId;
        Preconditions.checkState(bl, "Owner User ID must match");
        Preconditions.checkState(this.mId.equals(shortcutInfo.mId), "ID must match");
        Preconditions.checkState(this.mPackageName.equals(shortcutInfo.mPackageName), "Package name must match");
        if (this.isVisibleToPublisher()) {
            Preconditions.checkState(this.isImmutable() ^ true, "Target ShortcutInfo is immutable");
        }
    }

    public ComponentName getActivity() {
        return this.mActivity;
    }

    public String getBitmapPath() {
        return this.mBitmapPath;
    }

    public Set<String> getCategories() {
        return this.mCategories;
    }

    public CharSequence getDisabledMessage() {
        return this.mDisabledMessage;
    }

    public String getDisabledMessageResName() {
        return this.mDisabledMessageResName;
    }

    public int getDisabledMessageResourceId() {
        return this.mDisabledMessageResId;
    }

    public int getDisabledReason() {
        return this.mDisabledReason;
    }

    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public Icon getIcon() {
        return this.mIcon;
    }

    public String getIconResName() {
        return this.mIconResName;
    }

    public int getIconResourceId() {
        return this.mIconResId;
    }

    public String getId() {
        return this.mId;
    }

    public int getImplicitRank() {
        return this.mImplicitRank & Integer.MAX_VALUE;
    }

    public Intent getIntent() {
        Intent[] arrintent = this.mIntents;
        if (arrintent != null && arrintent.length != 0) {
            int n = arrintent.length - 1;
            return ShortcutInfo.setIntentExtras(new Intent(arrintent[n]), this.mIntentPersistableExtrases[n]);
        }
        return null;
    }

    public PersistableBundle[] getIntentPersistableExtrases() {
        return this.mIntentPersistableExtrases;
    }

    public Intent[] getIntents() {
        Intent[] arrintent = new Intent[this.mIntents.length];
        for (int i = 0; i < arrintent.length; ++i) {
            arrintent[i] = new Intent(this.mIntents[i]);
            ShortcutInfo.setIntentExtras(arrintent[i], this.mIntentPersistableExtrases[i]);
        }
        return arrintent;
    }

    public Intent[] getIntentsNoExtras() {
        return this.mIntents;
    }

    public long getLastChangedTimestamp() {
        return this.mLastChangedTimestamp;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public CharSequence getLongLabel() {
        return this.mText;
    }

    public int getLongLabelResourceId() {
        return this.mTextResId;
    }

    public String getPackage() {
        return this.mPackageName;
    }

    @SystemApi
    public Person[] getPersons() {
        return ShortcutInfo.clonePersons(this.mPersons);
    }

    public int getRank() {
        return this.mRank;
    }

    public CharSequence getShortLabel() {
        return this.mTitle;
    }

    public int getShortLabelResourceId() {
        return this.mTitleResId;
    }

    @Deprecated
    public CharSequence getText() {
        return this.mText;
    }

    @Deprecated
    public int getTextResId() {
        return this.mTextResId;
    }

    public String getTextResName() {
        return this.mTextResName;
    }

    @Deprecated
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Deprecated
    public int getTitleResId() {
        return this.mTitleResId;
    }

    public String getTitleResName() {
        return this.mTitleResName;
    }

    public UserHandle getUserHandle() {
        return UserHandle.of(this.mUserId);
    }

    public int getUserId() {
        return this.mUserId;
    }

    public boolean hasAdaptiveBitmap() {
        return this.hasFlags(512);
    }

    public boolean hasAnyResources() {
        boolean bl = this.hasIconResource() || this.hasStringResources();
        return bl;
    }

    public boolean hasFlags(int n) {
        boolean bl = (this.mFlags & n) == n;
        return bl;
    }

    public boolean hasIconFile() {
        return this.hasFlags(8);
    }

    public boolean hasIconResource() {
        return this.hasFlags(4);
    }

    public boolean hasKeyFieldsOnly() {
        return this.hasFlags(16);
    }

    public boolean hasRank() {
        boolean bl = this.mRank != Integer.MAX_VALUE;
        return bl;
    }

    public boolean hasStringResources() {
        boolean bl = this.mTitleResId != 0 || this.mTextResId != 0 || this.mDisabledMessageResId != 0;
        return bl;
    }

    public boolean hasStringResourcesResolved() {
        return this.hasFlags(128);
    }

    public boolean isAlive() {
        boolean bl;
        boolean bl2 = this.hasFlags(2);
        boolean bl3 = bl = true;
        if (!bl2) {
            bl3 = bl;
            if (!this.hasFlags(1)) {
                bl3 = this.hasFlags(32) ? bl : false;
            }
        }
        return bl3;
    }

    public boolean isDeclaredInManifest() {
        return this.hasFlags(32);
    }

    public boolean isDynamic() {
        return this.hasFlags(1);
    }

    public boolean isDynamicVisible() {
        boolean bl = this.isDynamic() && this.isVisibleToPublisher();
        return bl;
    }

    public boolean isEnabled() {
        return this.hasFlags(64) ^ true;
    }

    public boolean isFloating() {
        boolean bl = this.isPinned() && !this.isDynamic() && !this.isManifestShortcut();
        return bl;
    }

    public boolean isIconPendingSave() {
        return this.hasFlags(2048);
    }

    public boolean isImmutable() {
        return this.hasFlags(256);
    }

    public boolean isLongLived() {
        return this.hasFlags(8192);
    }

    @Deprecated
    public boolean isManifestShortcut() {
        return this.isDeclaredInManifest();
    }

    public boolean isManifestVisible() {
        boolean bl = this.isDeclaredInManifest() && this.isVisibleToPublisher();
        return bl;
    }

    public boolean isOriginallyFromManifest() {
        return this.hasFlags(256);
    }

    public boolean isPinned() {
        return this.hasFlags(2);
    }

    public boolean isPinnedVisible() {
        boolean bl = this.isPinned() && this.isVisibleToPublisher();
        return bl;
    }

    public boolean isRankChanged() {
        boolean bl = (this.mImplicitRank & Integer.MIN_VALUE) != 0;
        return bl;
    }

    public boolean isReturnedByServer() {
        return this.hasFlags(1024);
    }

    public boolean isVisibleToPublisher() {
        return ShortcutInfo.isDisabledForRestoreIssue(this.mDisabledReason) ^ true;
    }

    public void lookupAndFillInResourceIds(Resources resources) {
        if (this.mTitleResName == null && this.mTextResName == null && this.mDisabledMessageResName == null && this.mIconResName == null) {
            return;
        }
        this.mTitleResId = ShortcutInfo.lookUpResourceId(resources, this.mTitleResName, RES_TYPE_STRING, this.mPackageName);
        this.mTextResId = ShortcutInfo.lookUpResourceId(resources, this.mTextResName, RES_TYPE_STRING, this.mPackageName);
        this.mDisabledMessageResId = ShortcutInfo.lookUpResourceId(resources, this.mDisabledMessageResName, RES_TYPE_STRING, this.mPackageName);
        this.mIconResId = ShortcutInfo.lookUpResourceId(resources, this.mIconResName, null, this.mPackageName);
    }

    public void lookupAndFillInResourceNames(Resources resources) {
        if (this.mTitleResId == 0 && this.mTextResId == 0 && this.mDisabledMessageResId == 0 && this.mIconResId == 0) {
            return;
        }
        this.mTitleResName = ShortcutInfo.lookUpResourceName(resources, this.mTitleResId, false, this.mPackageName);
        this.mTextResName = ShortcutInfo.lookUpResourceName(resources, this.mTextResId, false, this.mPackageName);
        this.mDisabledMessageResName = ShortcutInfo.lookUpResourceName(resources, this.mDisabledMessageResId, false, this.mPackageName);
        this.mIconResName = ShortcutInfo.lookUpResourceName(resources, this.mIconResId, true, this.mPackageName);
    }

    public void replaceFlags(int n) {
        this.mFlags = n;
    }

    public void resolveResourceStrings(Resources resources) {
        this.mFlags |= 128;
        if (this.mTitleResId == 0 && this.mTextResId == 0 && this.mDisabledMessageResId == 0) {
            return;
        }
        int n = this.mTitleResId;
        if (n != 0) {
            this.mTitle = this.getResourceString(resources, n, this.mTitle);
        }
        if ((n = this.mTextResId) != 0) {
            this.mText = this.getResourceString(resources, n, this.mText);
        }
        if ((n = this.mDisabledMessageResId) != 0) {
            this.mDisabledMessage = this.getResourceString(resources, n, this.mDisabledMessage);
        }
    }

    public void setActivity(ComponentName componentName) {
        this.mActivity = componentName;
    }

    public void setBitmapPath(String string2) {
        this.mBitmapPath = string2;
    }

    public void setCategories(Set<String> set) {
        this.mCategories = ShortcutInfo.cloneCategories(set);
    }

    public void setDisabledMessage(String string2) {
        this.mDisabledMessage = string2;
        this.mDisabledMessageResId = 0;
        this.mDisabledMessageResName = null;
    }

    public void setDisabledMessageResId(int n) {
        if (this.mDisabledMessageResId != n) {
            this.mDisabledMessageResName = null;
        }
        this.mDisabledMessageResId = n;
        this.mDisabledMessage = null;
    }

    public void setDisabledMessageResName(String string2) {
        this.mDisabledMessageResName = string2;
    }

    public void setDisabledReason(int n) {
        this.mDisabledReason = n;
    }

    public void setIconPendingSave() {
        this.addFlags(2048);
    }

    public void setIconResName(String string2) {
        this.mIconResName = string2;
    }

    public void setIconResourceId(int n) {
        if (this.mIconResId != n) {
            this.mIconResName = null;
        }
        this.mIconResId = n;
    }

    public void setImplicitRank(int n) {
        this.mImplicitRank = this.mImplicitRank & Integer.MIN_VALUE | Integer.MAX_VALUE & n;
    }

    public void setIntents(Intent[] arrintent) throws IllegalArgumentException {
        Preconditions.checkNotNull(arrintent);
        boolean bl = arrintent.length > 0;
        Preconditions.checkArgument(bl);
        this.mIntents = ShortcutInfo.cloneIntents(arrintent);
        this.fixUpIntentExtras();
    }

    public void setLongLived() {
        this.addFlags(8192);
    }

    public void setRank(int n) {
        this.mRank = n;
    }

    public void setRankChanged() {
        this.mImplicitRank |= Integer.MIN_VALUE;
    }

    public void setReturnedByServer() {
        this.addFlags(1024);
    }

    public void setTextResName(String string2) {
        this.mTextResName = string2;
    }

    public void setTimestamp(long l) {
        this.mLastChangedTimestamp = l;
    }

    public void setTitleResName(String string2) {
        this.mTitleResName = string2;
    }

    public String toDumpString(String string2) {
        return this.toStringInner(false, true, string2);
    }

    public String toInsecureString() {
        return this.toStringInner(false, true, null);
    }

    public String toString() {
        return this.toStringInner(true, false, null);
    }

    public void updateTimestamp() {
        this.mLastChangedTimestamp = System.currentTimeMillis();
    }

    public boolean usesQuota() {
        boolean bl;
        boolean bl2 = bl = true;
        if (!this.hasFlags(1)) {
            bl2 = this.hasFlags(32) ? bl : false;
        }
        return bl2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mUserId);
        parcel.writeString(this.mId);
        parcel.writeString(this.mPackageName);
        parcel.writeParcelable(this.mActivity, n);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mIconResId);
        parcel.writeLong(this.mLastChangedTimestamp);
        parcel.writeInt(this.mDisabledReason);
        if (this.hasKeyFieldsOnly()) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcel.writeParcelable(this.mIcon, n);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeInt(this.mTitleResId);
        parcel.writeCharSequence(this.mText);
        parcel.writeInt(this.mTextResId);
        parcel.writeCharSequence(this.mDisabledMessage);
        parcel.writeInt(this.mDisabledMessageResId);
        parcel.writeParcelableArray((Parcelable[])this.mIntents, n);
        parcel.writeParcelableArray((Parcelable[])this.mIntentPersistableExtrases, n);
        parcel.writeInt(this.mRank);
        parcel.writeParcelable(this.mExtras, n);
        parcel.writeString(this.mBitmapPath);
        parcel.writeString(this.mIconResName);
        parcel.writeString(this.mTitleResName);
        parcel.writeString(this.mTextResName);
        parcel.writeString(this.mDisabledMessageResName);
        ArraySet<String> arraySet = this.mCategories;
        if (arraySet != null) {
            int n2 = arraySet.size();
            parcel.writeInt(n2);
            for (int i = 0; i < n2; ++i) {
                parcel.writeString(this.mCategories.valueAt(i));
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeParcelableArray((Parcelable[])this.mPersons, n);
        parcel.writeParcelable(this.mLocusId, n);
    }

    public static class Builder {
        private ComponentName mActivity;
        private Set<String> mCategories;
        private final Context mContext;
        private CharSequence mDisabledMessage;
        private int mDisabledMessageResId;
        private PersistableBundle mExtras;
        private Icon mIcon;
        private String mId;
        private Intent[] mIntents;
        private boolean mIsLongLived;
        private LocusId mLocusId;
        private Person[] mPersons;
        private int mRank = Integer.MAX_VALUE;
        private CharSequence mText;
        private int mTextResId;
        private CharSequence mTitle;
        private int mTitleResId;

        @Deprecated
        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder(Context context, String string2) {
            this.mContext = context;
            this.mId = Preconditions.checkStringNotEmpty(string2, "id cannot be empty");
        }

        public ShortcutInfo build() {
            return new ShortcutInfo(this);
        }

        public Builder setActivity(ComponentName componentName) {
            this.mActivity = Preconditions.checkNotNull(componentName, "activity cannot be null");
            return this;
        }

        public Builder setCategories(Set<String> set) {
            this.mCategories = set;
            return this;
        }

        public Builder setDisabledMessage(CharSequence charSequence) {
            boolean bl = this.mDisabledMessageResId == 0;
            Preconditions.checkState(bl, "disabledMessageResId already set");
            this.mDisabledMessage = Preconditions.checkStringNotEmpty(charSequence, "disabledMessage cannot be empty");
            return this;
        }

        @Deprecated
        public Builder setDisabledMessageResId(int n) {
            boolean bl = this.mDisabledMessage == null;
            Preconditions.checkState(bl, "disabledMessage already set");
            this.mDisabledMessageResId = n;
            return this;
        }

        public Builder setExtras(PersistableBundle persistableBundle) {
            this.mExtras = persistableBundle;
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = ShortcutInfo.validateIcon(icon);
            return this;
        }

        @Deprecated
        public Builder setId(String string2) {
            this.mId = Preconditions.checkStringNotEmpty(string2, "id cannot be empty");
            return this;
        }

        public Builder setIntent(Intent intent) {
            return this.setIntents(new Intent[]{intent});
        }

        public Builder setIntents(Intent[] arrintent) {
            Preconditions.checkNotNull(arrintent, "intents cannot be null");
            Preconditions.checkNotNull(arrintent.length, "intents cannot be empty");
            int n = arrintent.length;
            for (int i = 0; i < n; ++i) {
                Intent intent = arrintent[i];
                Preconditions.checkNotNull(intent, "intents cannot contain null");
                Preconditions.checkNotNull(intent.getAction(), "intent's action must be set");
            }
            this.mIntents = ShortcutInfo.cloneIntents(arrintent);
            return this;
        }

        public Builder setLocusId(LocusId locusId) {
            this.mLocusId = Preconditions.checkNotNull(locusId, "locusId cannot be null");
            return this;
        }

        public Builder setLongLabel(CharSequence charSequence) {
            boolean bl = this.mTextResId == 0;
            Preconditions.checkState(bl, "longLabelResId already set");
            this.mText = Preconditions.checkStringNotEmpty(charSequence, "longLabel cannot be empty");
            return this;
        }

        @Deprecated
        public Builder setLongLabelResId(int n) {
            boolean bl = this.mText == null;
            Preconditions.checkState(bl, "longLabel already set");
            this.mTextResId = n;
            return this;
        }

        public Builder setLongLived(boolean bl) {
            this.mIsLongLived = bl;
            return this;
        }

        public Builder setPerson(Person person) {
            return this.setPersons(new Person[]{person});
        }

        public Builder setPersons(Person[] arrperson) {
            Preconditions.checkNotNull(arrperson, "persons cannot be null");
            Preconditions.checkNotNull(arrperson.length, "persons cannot be empty");
            int n = arrperson.length;
            for (int i = 0; i < n; ++i) {
                Preconditions.checkNotNull(arrperson[i], "persons cannot contain null");
            }
            this.mPersons = ShortcutInfo.clonePersons(arrperson);
            return this;
        }

        public Builder setRank(int n) {
            boolean bl = n >= 0;
            Preconditions.checkArgument(bl, "Rank cannot be negative or bigger than MAX_RANK");
            this.mRank = n;
            return this;
        }

        public Builder setShortLabel(CharSequence charSequence) {
            boolean bl = this.mTitleResId == 0;
            Preconditions.checkState(bl, "shortLabelResId already set");
            this.mTitle = Preconditions.checkStringNotEmpty(charSequence, "shortLabel cannot be empty");
            return this;
        }

        @Deprecated
        public Builder setShortLabelResId(int n) {
            boolean bl = this.mTitle == null;
            Preconditions.checkState(bl, "shortLabel already set");
            this.mTitleResId = n;
            return this;
        }

        @Deprecated
        public Builder setText(CharSequence charSequence) {
            return this.setLongLabel(charSequence);
        }

        @Deprecated
        public Builder setTextResId(int n) {
            return this.setLongLabelResId(n);
        }

        @Deprecated
        public Builder setTitle(CharSequence charSequence) {
            return this.setShortLabel(charSequence);
        }

        @Deprecated
        public Builder setTitleResId(int n) {
            return this.setShortLabelResId(n);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CloneFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DisabledReason {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ShortcutFlags {
    }

}

