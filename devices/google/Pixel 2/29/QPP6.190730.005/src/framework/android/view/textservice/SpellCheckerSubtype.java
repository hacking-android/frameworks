/*
 * Decompiled with CFR 0.145.
 */
package android.view.textservice;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Slog;
import android.view.textservice.SpellCheckerInfo;
import com.android.internal.inputmethod.SubtypeLocaleUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class SpellCheckerSubtype
implements Parcelable {
    public static final Parcelable.Creator<SpellCheckerSubtype> CREATOR;
    private static final String EXTRA_VALUE_KEY_VALUE_SEPARATOR = "=";
    private static final String EXTRA_VALUE_PAIR_SEPARATOR = ",";
    public static final int SUBTYPE_ID_NONE = 0;
    private static final String SUBTYPE_LANGUAGE_TAG_NONE = "";
    private static final String TAG;
    private HashMap<String, String> mExtraValueHashMapCache;
    private final String mSubtypeExtraValue;
    private final int mSubtypeHashCode;
    private final int mSubtypeId;
    private final String mSubtypeLanguageTag;
    private final String mSubtypeLocale;
    private final int mSubtypeNameResId;

    static {
        TAG = SpellCheckerSubtype.class.getSimpleName();
        CREATOR = new Parcelable.Creator<SpellCheckerSubtype>(){

            @Override
            public SpellCheckerSubtype createFromParcel(Parcel parcel) {
                return new SpellCheckerSubtype(parcel);
            }

            public SpellCheckerSubtype[] newArray(int n) {
                return new SpellCheckerSubtype[n];
            }
        };
    }

    @Deprecated
    public SpellCheckerSubtype(int n, String string2, String string3) {
        this(n, string2, SUBTYPE_LANGUAGE_TAG_NONE, string3, 0);
    }

    public SpellCheckerSubtype(int n, String string2, String string3, String string4, int n2) {
        this.mSubtypeNameResId = n;
        String string5 = SUBTYPE_LANGUAGE_TAG_NONE;
        if (string2 == null) {
            string2 = SUBTYPE_LANGUAGE_TAG_NONE;
        }
        this.mSubtypeLocale = string2;
        string2 = string3 != null ? string3 : SUBTYPE_LANGUAGE_TAG_NONE;
        this.mSubtypeLanguageTag = string2;
        string2 = string5;
        if (string4 != null) {
            string2 = string4;
        }
        this.mSubtypeExtraValue = string2;
        this.mSubtypeId = n2;
        n = this.mSubtypeId;
        if (n == 0) {
            n = SpellCheckerSubtype.hashCodeInternal(this.mSubtypeLocale, this.mSubtypeExtraValue);
        }
        this.mSubtypeHashCode = n;
    }

    SpellCheckerSubtype(Parcel parcel) {
        this.mSubtypeNameResId = parcel.readInt();
        String string2 = parcel.readString();
        String string3 = SUBTYPE_LANGUAGE_TAG_NONE;
        if (string2 == null) {
            string2 = SUBTYPE_LANGUAGE_TAG_NONE;
        }
        this.mSubtypeLocale = string2;
        string2 = parcel.readString();
        if (string2 == null) {
            string2 = SUBTYPE_LANGUAGE_TAG_NONE;
        }
        this.mSubtypeLanguageTag = string2;
        String string4 = parcel.readString();
        string2 = string3;
        if (string4 != null) {
            string2 = string4;
        }
        this.mSubtypeExtraValue = string2;
        this.mSubtypeId = parcel.readInt();
        int n = this.mSubtypeId;
        if (n == 0) {
            n = SpellCheckerSubtype.hashCodeInternal(this.mSubtypeLocale, this.mSubtypeExtraValue);
        }
        this.mSubtypeHashCode = n;
    }

    private HashMap<String, String> getExtraValueHashMap() {
        if (this.mExtraValueHashMapCache == null) {
            this.mExtraValueHashMapCache = new HashMap();
            String[] arrstring = this.mSubtypeExtraValue.split(EXTRA_VALUE_PAIR_SEPARATOR);
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                String[] arrstring2 = arrstring[i].split(EXTRA_VALUE_KEY_VALUE_SEPARATOR);
                if (arrstring2.length == 1) {
                    this.mExtraValueHashMapCache.put(arrstring2[0], null);
                    continue;
                }
                if (arrstring2.length <= 1) continue;
                if (arrstring2.length > 2) {
                    Slog.w(TAG, "ExtraValue has two or more '='s");
                }
                this.mExtraValueHashMapCache.put(arrstring2[0], arrstring2[1]);
            }
        }
        return this.mExtraValueHashMapCache;
    }

    private static int hashCodeInternal(String string2, String string3) {
        return Arrays.hashCode(new Object[]{string2, string3});
    }

    public static List<SpellCheckerSubtype> sort(Context object, int n, SpellCheckerInfo object2, List<SpellCheckerSubtype> collection) {
        if (object2 == null) {
            return collection;
        }
        collection = new HashSet<SpellCheckerSubtype>(collection);
        object = new ArrayList();
        int n2 = ((SpellCheckerInfo)object2).getSubtypeCount();
        for (n = 0; n < n2; ++n) {
            SpellCheckerSubtype spellCheckerSubtype = ((SpellCheckerInfo)object2).getSubtypeAt(n);
            if (!((HashSet)collection).contains(spellCheckerSubtype)) continue;
            ((ArrayList)object).add(spellCheckerSubtype);
            ((HashSet)collection).remove(spellCheckerSubtype);
        }
        object2 = ((HashSet)collection).iterator();
        while (object2.hasNext()) {
            ((ArrayList)object).add((SpellCheckerSubtype)object2.next());
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
        boolean bl = object instanceof SpellCheckerSubtype;
        boolean bl2 = false;
        boolean bl3 = false;
        if (bl) {
            object = (SpellCheckerSubtype)object;
            if (((SpellCheckerSubtype)object).mSubtypeId == 0 && this.mSubtypeId == 0) {
                if (((SpellCheckerSubtype)object).hashCode() == this.hashCode() && ((SpellCheckerSubtype)object).getNameResId() == this.getNameResId() && ((SpellCheckerSubtype)object).getLocale().equals(this.getLocale()) && ((SpellCheckerSubtype)object).getLanguageTag().equals(this.getLanguageTag()) && ((SpellCheckerSubtype)object).getExtraValue().equals(this.getExtraValue())) {
                    bl3 = true;
                }
                return bl3;
            }
            bl3 = bl2;
            if (((SpellCheckerSubtype)object).hashCode() == this.hashCode()) {
                bl3 = true;
            }
            return bl3;
        }
        return false;
    }

    public CharSequence getDisplayName(Context object, String string2, ApplicationInfo applicationInfo) {
        Object object2 = this.getLocaleObject();
        object2 = object2 != null ? ((Locale)object2).getDisplayName() : this.mSubtypeLocale;
        if (this.mSubtypeNameResId == 0) {
            return object2;
        }
        if (!TextUtils.isEmpty((CharSequence)(object = ((Context)object).getPackageManager().getText(string2, this.mSubtypeNameResId, applicationInfo)))) {
            return String.format(object.toString(), object2);
        }
        return object2;
    }

    public String getExtraValue() {
        return this.mSubtypeExtraValue;
    }

    public String getExtraValueOf(String string2) {
        return this.getExtraValueHashMap().get(string2);
    }

    public String getLanguageTag() {
        return this.mSubtypeLanguageTag;
    }

    @Deprecated
    public String getLocale() {
        return this.mSubtypeLocale;
    }

    public Locale getLocaleObject() {
        if (!TextUtils.isEmpty(this.mSubtypeLanguageTag)) {
            return Locale.forLanguageTag(this.mSubtypeLanguageTag);
        }
        return SubtypeLocaleUtils.constructLocaleFromString(this.mSubtypeLocale);
    }

    public int getNameResId() {
        return this.mSubtypeNameResId;
    }

    public int hashCode() {
        return this.mSubtypeHashCode;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSubtypeNameResId);
        parcel.writeString(this.mSubtypeLocale);
        parcel.writeString(this.mSubtypeLanguageTag);
        parcel.writeString(this.mSubtypeExtraValue);
        parcel.writeInt(this.mSubtypeId);
    }

}

