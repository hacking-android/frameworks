/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DisplayContext
 *  android.icu.text.LocaleDisplayNames
 */
package android.view.inputmethod;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.text.DisplayContext;
import android.icu.text.LocaleDisplayNames;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import com.android.internal.inputmethod.SubtypeLocaleUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class InputMethodSubtype
implements Parcelable {
    public static final Parcelable.Creator<InputMethodSubtype> CREATOR;
    private static final String EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME = "UntranslatableReplacementStringInSubtypeName";
    private static final String EXTRA_VALUE_KEY_VALUE_SEPARATOR = "=";
    private static final String EXTRA_VALUE_PAIR_SEPARATOR = ",";
    private static final String LANGUAGE_TAG_NONE = "";
    private static final int SUBTYPE_ID_NONE = 0;
    private static final String TAG;
    private volatile Locale mCachedLocaleObj;
    private volatile HashMap<String, String> mExtraValueHashMapCache;
    private final boolean mIsAsciiCapable;
    private final boolean mIsAuxiliary;
    private final Object mLock = new Object();
    private final boolean mOverridesImplicitlyEnabledSubtype;
    private final String mSubtypeExtraValue;
    private final int mSubtypeHashCode;
    private final int mSubtypeIconResId;
    private final int mSubtypeId;
    private final String mSubtypeLanguageTag;
    private final String mSubtypeLocale;
    private final String mSubtypeMode;
    private final int mSubtypeNameResId;

    static {
        TAG = InputMethodSubtype.class.getSimpleName();
        CREATOR = new Parcelable.Creator<InputMethodSubtype>(){

            @Override
            public InputMethodSubtype createFromParcel(Parcel parcel) {
                return new InputMethodSubtype(parcel);
            }

            public InputMethodSubtype[] newArray(int n) {
                return new InputMethodSubtype[n];
            }
        };
    }

    @Deprecated
    public InputMethodSubtype(int n, int n2, String string2, String string3, String string4, boolean bl, boolean bl2) {
        this(n, n2, string2, string3, string4, bl, bl2, 0);
    }

    @Deprecated
    public InputMethodSubtype(int n, int n2, String string2, String string3, String string4, boolean bl, boolean bl2, int n3) {
        this(InputMethodSubtype.getBuilder(n, n2, string2, string3, string4, bl, bl2, n3, false));
    }

    InputMethodSubtype(Parcel parcel) {
        this.mSubtypeNameResId = parcel.readInt();
        this.mSubtypeIconResId = parcel.readInt();
        String string2 = parcel.readString();
        String string3 = LANGUAGE_TAG_NONE;
        if (string2 == null) {
            string2 = LANGUAGE_TAG_NONE;
        }
        this.mSubtypeLocale = string2;
        string2 = parcel.readString();
        if (string2 == null) {
            string2 = LANGUAGE_TAG_NONE;
        }
        this.mSubtypeLanguageTag = string2;
        string2 = parcel.readString();
        if (string2 == null) {
            string2 = LANGUAGE_TAG_NONE;
        }
        this.mSubtypeMode = string2;
        String string4 = parcel.readString();
        string2 = string3;
        if (string4 != null) {
            string2 = string4;
        }
        this.mSubtypeExtraValue = string2;
        int n = parcel.readInt();
        boolean bl = false;
        boolean bl2 = n == 1;
        this.mIsAuxiliary = bl2;
        bl2 = parcel.readInt() == 1;
        this.mOverridesImplicitlyEnabledSubtype = bl2;
        this.mSubtypeHashCode = parcel.readInt();
        this.mSubtypeId = parcel.readInt();
        bl2 = bl;
        if (parcel.readInt() == 1) {
            bl2 = true;
        }
        this.mIsAsciiCapable = bl2;
    }

    private InputMethodSubtype(InputMethodSubtypeBuilder inputMethodSubtypeBuilder) {
        this.mSubtypeNameResId = inputMethodSubtypeBuilder.mSubtypeNameResId;
        this.mSubtypeIconResId = inputMethodSubtypeBuilder.mSubtypeIconResId;
        this.mSubtypeLocale = inputMethodSubtypeBuilder.mSubtypeLocale;
        this.mSubtypeLanguageTag = inputMethodSubtypeBuilder.mSubtypeLanguageTag;
        this.mSubtypeMode = inputMethodSubtypeBuilder.mSubtypeMode;
        this.mSubtypeExtraValue = inputMethodSubtypeBuilder.mSubtypeExtraValue;
        this.mIsAuxiliary = inputMethodSubtypeBuilder.mIsAuxiliary;
        this.mOverridesImplicitlyEnabledSubtype = inputMethodSubtypeBuilder.mOverridesImplicitlyEnabledSubtype;
        this.mSubtypeId = inputMethodSubtypeBuilder.mSubtypeId;
        this.mIsAsciiCapable = inputMethodSubtypeBuilder.mIsAsciiCapable;
        int n = this.mSubtypeId;
        this.mSubtypeHashCode = n != 0 ? n : InputMethodSubtype.hashCodeInternal(this.mSubtypeLocale, this.mSubtypeMode, this.mSubtypeExtraValue, this.mIsAuxiliary, this.mOverridesImplicitlyEnabledSubtype, this.mIsAsciiCapable);
    }

    private static InputMethodSubtypeBuilder getBuilder(int n, int n2, String string2, String string3, String string4, boolean bl, boolean bl2, int n3, boolean bl3) {
        InputMethodSubtypeBuilder inputMethodSubtypeBuilder = new InputMethodSubtypeBuilder();
        inputMethodSubtypeBuilder.mSubtypeNameResId = n;
        inputMethodSubtypeBuilder.mSubtypeIconResId = n2;
        inputMethodSubtypeBuilder.mSubtypeLocale = string2;
        inputMethodSubtypeBuilder.mSubtypeMode = string3;
        inputMethodSubtypeBuilder.mSubtypeExtraValue = string4;
        inputMethodSubtypeBuilder.mIsAuxiliary = bl;
        inputMethodSubtypeBuilder.mOverridesImplicitlyEnabledSubtype = bl2;
        inputMethodSubtypeBuilder.mSubtypeId = n3;
        inputMethodSubtypeBuilder.mIsAsciiCapable = bl3;
        return inputMethodSubtypeBuilder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private HashMap<String, String> getExtraValueHashMap() {
        synchronized (this) {
            String[] arrstring = this.mExtraValueHashMapCache;
            if (arrstring != null) {
                return arrstring;
            }
            HashMap<String, String> hashMap = new HashMap<String, String>();
            String[] arrstring2 = this.mSubtypeExtraValue.split(EXTRA_VALUE_PAIR_SEPARATOR);
            int n = 0;
            do {
                if (n >= arrstring2.length) {
                    this.mExtraValueHashMapCache = hashMap;
                    return hashMap;
                }
                arrstring = arrstring2[n].split(EXTRA_VALUE_KEY_VALUE_SEPARATOR);
                if (arrstring.length == 1) {
                    hashMap.put(arrstring[0], null);
                } else if (arrstring.length > 1) {
                    if (arrstring.length > 2) {
                        Slog.w(TAG, "ExtraValue has two or more '='s");
                    }
                    hashMap.put(arrstring[0], arrstring[1]);
                }
                ++n;
            } while (true);
        }
    }

    private static String getLocaleDisplayName(Locale locale, Locale locale2, DisplayContext displayContext) {
        if (locale2 == null) {
            return LANGUAGE_TAG_NONE;
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return LocaleDisplayNames.getInstance((Locale)locale, (DisplayContext[])new DisplayContext[]{displayContext}).localeDisplayName(locale2);
    }

    private static Locale getLocaleFromContext(Context object) {
        if (object == null) {
            return null;
        }
        if (((Context)object).getResources() == null) {
            return null;
        }
        if ((object = ((Context)object).getResources().getConfiguration()) == null) {
            return null;
        }
        return ((Configuration)object).getLocales().get(0);
    }

    private static int hashCodeInternal(String string2, String string3, String string4, boolean bl, boolean bl2, boolean bl3) {
        if (bl3 ^ true) {
            return Arrays.hashCode(new Object[]{string2, string3, string4, bl, bl2});
        }
        return Arrays.hashCode(new Object[]{string2, string3, string4, bl, bl2, bl3});
    }

    public static List<InputMethodSubtype> sort(Context object, int n, InputMethodInfo object2, List<InputMethodSubtype> object3) {
        if (object2 == null) {
            return object3;
        }
        HashSet<InputMethodSubtype> hashSet = new HashSet<InputMethodSubtype>((Collection<InputMethodSubtype>)object3);
        object = new ArrayList();
        int n2 = ((InputMethodInfo)object2).getSubtypeCount();
        for (n = 0; n < n2; ++n) {
            object3 = ((InputMethodInfo)object2).getSubtypeAt(n);
            if (!hashSet.contains(object3)) continue;
            ((ArrayList)object).add(object3);
            hashSet.remove(object3);
        }
        object2 = hashSet.iterator();
        while (object2.hasNext()) {
            ((ArrayList)object).add((InputMethodSubtype)object2.next());
        }
        return object;
    }

    public boolean containsExtraValueKey(String string2) {
        return this.getExtraValueHashMap().containsKey(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof InputMethodSubtype;
        boolean bl2 = false;
        boolean bl3 = false;
        if (bl) {
            object = (InputMethodSubtype)object;
            if (((InputMethodSubtype)object).mSubtypeId == 0 && this.mSubtypeId == 0) {
                if (((InputMethodSubtype)object).hashCode() == this.hashCode() && ((InputMethodSubtype)object).getLocale().equals(this.getLocale()) && ((InputMethodSubtype)object).getLanguageTag().equals(this.getLanguageTag()) && ((InputMethodSubtype)object).getMode().equals(this.getMode()) && ((InputMethodSubtype)object).getExtraValue().equals(this.getExtraValue()) && ((InputMethodSubtype)object).isAuxiliary() == this.isAuxiliary() && ((InputMethodSubtype)object).overridesImplicitlyEnabledSubtype() == this.overridesImplicitlyEnabledSubtype() && ((InputMethodSubtype)object).isAsciiCapable() == this.isAsciiCapable()) {
                    bl3 = true;
                }
                return bl3;
            }
            bl3 = bl2;
            if (((InputMethodSubtype)object).hashCode() == this.hashCode()) {
                bl3 = true;
            }
            return bl3;
        }
        return false;
    }

    public CharSequence getDisplayName(Context object, String object2, ApplicationInfo object3) {
        if (this.mSubtypeNameResId == 0) {
            return InputMethodSubtype.getLocaleDisplayName(InputMethodSubtype.getLocaleFromContext((Context)object), this.getLocaleObject(), DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU);
        }
        object3 = ((Context)object).getPackageManager().getText((String)object2, this.mSubtypeNameResId, (ApplicationInfo)object3);
        if (TextUtils.isEmpty((CharSequence)object3)) {
            return LANGUAGE_TAG_NONE;
        }
        String string2 = object3.toString();
        if (this.containsExtraValueKey(EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME)) {
            object = this.getExtraValueOf(EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME);
        } else {
            object2 = TextUtils.equals(string2, "%s") ? DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU : (string2.startsWith("%s") ? DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE : DisplayContext.CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE);
            object = InputMethodSubtype.getLocaleDisplayName(InputMethodSubtype.getLocaleFromContext((Context)object), this.getLocaleObject(), (DisplayContext)object2);
        }
        object2 = object;
        if (object == null) {
            object2 = LANGUAGE_TAG_NONE;
        }
        try {
            object = String.format(string2, object2);
            return object;
        }
        catch (IllegalFormatException illegalFormatException) {
            object2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("Found illegal format in subtype name(");
            ((StringBuilder)object).append(object3);
            ((StringBuilder)object).append("): ");
            ((StringBuilder)object).append(illegalFormatException);
            Slog.w((String)object2, ((StringBuilder)object).toString());
            return LANGUAGE_TAG_NONE;
        }
    }

    public String getExtraValue() {
        return this.mSubtypeExtraValue;
    }

    public String getExtraValueOf(String string2) {
        return this.getExtraValueHashMap().get(string2);
    }

    public int getIconResId() {
        return this.mSubtypeIconResId;
    }

    public String getLanguageTag() {
        return this.mSubtypeLanguageTag;
    }

    @Deprecated
    public String getLocale() {
        return this.mSubtypeLocale;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Locale getLocaleObject() {
        if (this.mCachedLocaleObj != null) {
            return this.mCachedLocaleObj;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCachedLocaleObj != null) {
                return this.mCachedLocaleObj;
            }
            this.mCachedLocaleObj = !TextUtils.isEmpty(this.mSubtypeLanguageTag) ? Locale.forLanguageTag(this.mSubtypeLanguageTag) : SubtypeLocaleUtils.constructLocaleFromString(this.mSubtypeLocale);
            return this.mCachedLocaleObj;
        }
    }

    public String getMode() {
        return this.mSubtypeMode;
    }

    public int getNameResId() {
        return this.mSubtypeNameResId;
    }

    public final int getSubtypeId() {
        return this.mSubtypeId;
    }

    public final boolean hasSubtypeId() {
        boolean bl = this.mSubtypeId != 0;
        return bl;
    }

    public int hashCode() {
        return this.mSubtypeHashCode;
    }

    public boolean isAsciiCapable() {
        return this.mIsAsciiCapable;
    }

    public boolean isAuxiliary() {
        return this.mIsAuxiliary;
    }

    public boolean overridesImplicitlyEnabledSubtype() {
        return this.mOverridesImplicitlyEnabledSubtype;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSubtypeNameResId);
        parcel.writeInt(this.mSubtypeIconResId);
        parcel.writeString(this.mSubtypeLocale);
        parcel.writeString(this.mSubtypeLanguageTag);
        parcel.writeString(this.mSubtypeMode);
        parcel.writeString(this.mSubtypeExtraValue);
        parcel.writeInt((int)this.mIsAuxiliary);
        parcel.writeInt((int)this.mOverridesImplicitlyEnabledSubtype);
        parcel.writeInt(this.mSubtypeHashCode);
        parcel.writeInt(this.mSubtypeId);
        parcel.writeInt((int)this.mIsAsciiCapable);
    }

    public static class InputMethodSubtypeBuilder {
        private boolean mIsAsciiCapable = false;
        private boolean mIsAuxiliary = false;
        private boolean mOverridesImplicitlyEnabledSubtype = false;
        private String mSubtypeExtraValue = "";
        private int mSubtypeIconResId = 0;
        private int mSubtypeId = 0;
        private String mSubtypeLanguageTag = "";
        private String mSubtypeLocale = "";
        private String mSubtypeMode = "";
        private int mSubtypeNameResId = 0;

        public InputMethodSubtype build() {
            return new InputMethodSubtype(this);
        }

        public InputMethodSubtypeBuilder setIsAsciiCapable(boolean bl) {
            this.mIsAsciiCapable = bl;
            return this;
        }

        public InputMethodSubtypeBuilder setIsAuxiliary(boolean bl) {
            this.mIsAuxiliary = bl;
            return this;
        }

        public InputMethodSubtypeBuilder setLanguageTag(String string2) {
            if (string2 == null) {
                string2 = InputMethodSubtype.LANGUAGE_TAG_NONE;
            }
            this.mSubtypeLanguageTag = string2;
            return this;
        }

        public InputMethodSubtypeBuilder setOverridesImplicitlyEnabledSubtype(boolean bl) {
            this.mOverridesImplicitlyEnabledSubtype = bl;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeExtraValue(String string2) {
            if (string2 == null) {
                string2 = InputMethodSubtype.LANGUAGE_TAG_NONE;
            }
            this.mSubtypeExtraValue = string2;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeIconResId(int n) {
            this.mSubtypeIconResId = n;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeId(int n) {
            this.mSubtypeId = n;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeLocale(String string2) {
            if (string2 == null) {
                string2 = InputMethodSubtype.LANGUAGE_TAG_NONE;
            }
            this.mSubtypeLocale = string2;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeMode(String string2) {
            if (string2 == null) {
                string2 = InputMethodSubtype.LANGUAGE_TAG_NONE;
            }
            this.mSubtypeMode = string2;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeNameResId(int n) {
            this.mSubtypeNameResId = n;
            return this;
        }
    }

}

