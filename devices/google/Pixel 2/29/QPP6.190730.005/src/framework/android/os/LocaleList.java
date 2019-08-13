/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.ULocale
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.GuardedBy;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public final class LocaleList
implements Parcelable {
    public static final Parcelable.Creator<LocaleList> CREATOR;
    private static final Locale EN_LATN;
    private static final Locale LOCALE_AR_XB;
    private static final Locale LOCALE_EN_XA;
    private static final int NUM_PSEUDO_LOCALES = 2;
    private static final String STRING_AR_XB = "ar-XB";
    private static final String STRING_EN_XA = "en-XA";
    @GuardedBy(value={"sLock"})
    private static LocaleList sDefaultAdjustedLocaleList;
    @GuardedBy(value={"sLock"})
    private static LocaleList sDefaultLocaleList;
    private static final Locale[] sEmptyList;
    private static final LocaleList sEmptyLocaleList;
    @GuardedBy(value={"sLock"})
    private static Locale sLastDefaultLocale;
    @GuardedBy(value={"sLock"})
    private static LocaleList sLastExplicitlySetLocaleList;
    private static final Object sLock;
    private final Locale[] mList;
    private final String mStringRepresentation;

    static {
        sEmptyList = new Locale[0];
        sEmptyLocaleList = new LocaleList(new Locale[0]);
        CREATOR = new Parcelable.Creator<LocaleList>(){

            @Override
            public LocaleList createFromParcel(Parcel parcel) {
                return LocaleList.forLanguageTags(parcel.readString());
            }

            public LocaleList[] newArray(int n) {
                return new LocaleList[n];
            }
        };
        LOCALE_EN_XA = new Locale("en", "XA");
        LOCALE_AR_XB = new Locale("ar", "XB");
        EN_LATN = Locale.forLanguageTag("en-Latn");
        sLock = new Object();
        sLastExplicitlySetLocaleList = null;
        sDefaultLocaleList = null;
        sDefaultAdjustedLocaleList = null;
        sLastDefaultLocale = null;
    }

    public LocaleList(Locale serializable, LocaleList localeList) {
        if (serializable != null) {
            int n;
            int n2 = localeList == null ? 0 : localeList.mList.length;
            int n3 = -1;
            int n4 = 0;
            do {
                n = n3;
                if (n4 >= n2) break;
                if (((Locale)serializable).equals(localeList.mList[n4])) {
                    n = n4;
                    break;
                }
                ++n4;
            } while (true);
            n4 = n == -1 ? 1 : 0;
            n3 = n4 + n2;
            Locale[] arrlocale = new Locale[n3];
            arrlocale[0] = (Locale)((Locale)serializable).clone();
            if (n == -1) {
                for (n4 = 0; n4 < n2; ++n4) {
                    arrlocale[n4 + 1] = (Locale)localeList.mList[n4].clone();
                }
            } else {
                for (n4 = 0; n4 < n; ++n4) {
                    arrlocale[n4 + 1] = (Locale)localeList.mList[n4].clone();
                }
                for (n4 = n + 1; n4 < n2; ++n4) {
                    arrlocale[n4] = (Locale)localeList.mList[n4].clone();
                }
            }
            serializable = new StringBuilder();
            for (n4 = 0; n4 < n3; ++n4) {
                ((StringBuilder)serializable).append(arrlocale[n4].toLanguageTag());
                if (n4 >= n3 - 1) continue;
                ((StringBuilder)serializable).append(',');
            }
            this.mList = arrlocale;
            this.mStringRepresentation = ((StringBuilder)serializable).toString();
            return;
        }
        throw new NullPointerException("topLocale is null");
    }

    public LocaleList(Locale ... object) {
        if (((Locale[])object).length == 0) {
            this.mList = sEmptyList;
            this.mStringRepresentation = "";
        } else {
            Locale[] arrlocale = new Locale[((Locale[])object).length];
            HashSet<Locale> hashSet = new HashSet<Locale>();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < ((Locale[])object).length; ++i) {
                Locale locale = object[i];
                if (locale != null) {
                    if (!hashSet.contains(locale)) {
                        arrlocale[i] = locale = (Locale)locale.clone();
                        stringBuilder.append(locale.toLanguageTag());
                        if (i < ((Object)object).length - 1) {
                            stringBuilder.append(',');
                        }
                        hashSet.add(locale);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("list[");
                    ((StringBuilder)object).append(i);
                    ((StringBuilder)object).append("] is a repetition");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("list[");
                ((StringBuilder)object).append(i);
                ((StringBuilder)object).append("] is null");
                throw new NullPointerException(((StringBuilder)object).toString());
            }
            this.mList = arrlocale;
            this.mStringRepresentation = stringBuilder.toString();
        }
    }

    private Locale computeFirstMatch(Collection<String> object, boolean bl) {
        int n = this.computeFirstMatchIndex((Collection<String>)object, bl);
        object = n == -1 ? null : this.mList[n];
        return object;
    }

    private int computeFirstMatchIndex(Collection<String> object, boolean bl) {
        int n;
        int n2;
        Locale[] arrlocale = this.mList;
        if (arrlocale.length == 1) {
            return 0;
        }
        if (arrlocale.length == 0) {
            return -1;
        }
        int n3 = n2 = Integer.MAX_VALUE;
        if (bl) {
            n = this.findFirstMatchIndex(EN_LATN);
            if (n == 0) {
                return 0;
            }
            n3 = n2;
            if (n < Integer.MAX_VALUE) {
                n3 = n;
            }
        }
        object = object.iterator();
        while (object.hasNext()) {
            n2 = this.findFirstMatchIndex(Locale.forLanguageTag((String)object.next()));
            if (n2 == 0) {
                return 0;
            }
            n = n3;
            if (n2 < n3) {
                n = n2;
            }
            n3 = n;
        }
        if (n3 == Integer.MAX_VALUE) {
            return 0;
        }
        return n3;
    }

    private int findFirstMatchIndex(Locale locale) {
        Locale[] arrlocale;
        for (int i = 0; i < (arrlocale = this.mList).length; ++i) {
            if (LocaleList.matchScore(locale, arrlocale[i]) <= 0) continue;
            return i;
        }
        return Integer.MAX_VALUE;
    }

    public static LocaleList forLanguageTags(String arrstring) {
        if (arrstring != null && !arrstring.equals("")) {
            arrstring = arrstring.split(",");
            Locale[] arrlocale = new Locale[arrstring.length];
            for (int i = 0; i < arrlocale.length; ++i) {
                arrlocale[i] = Locale.forLanguageTag(arrstring[i]);
            }
            return new LocaleList(arrlocale);
        }
        return LocaleList.getEmptyLocaleList();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static LocaleList getAdjustedDefault() {
        LocaleList.getDefault();
        Object object = sLock;
        synchronized (object) {
            return sDefaultAdjustedLocaleList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static LocaleList getDefault() {
        Locale locale = Locale.getDefault();
        Object object = sLock;
        synchronized (object) {
            LocaleList localeList;
            if (locale.equals(sLastDefaultLocale)) return sDefaultLocaleList;
            sLastDefaultLocale = locale;
            if (sDefaultLocaleList != null && locale.equals(sDefaultLocaleList.get(0))) {
                return sDefaultLocaleList;
            }
            sDefaultAdjustedLocaleList = sDefaultLocaleList = (localeList = new LocaleList(locale, sLastExplicitlySetLocaleList));
            return sDefaultLocaleList;
        }
    }

    public static LocaleList getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    private static String getLikelyScript(Locale locale) {
        String string2 = locale.getScript();
        if (!string2.isEmpty()) {
            return string2;
        }
        return ULocale.addLikelySubtags((ULocale)ULocale.forLocale((Locale)locale)).getScript();
    }

    public static boolean isPseudoLocale(ULocale object) {
        object = object != null ? object.toLocale() : null;
        return LocaleList.isPseudoLocale((Locale)object);
    }

    private static boolean isPseudoLocale(String string2) {
        boolean bl = STRING_EN_XA.equals(string2) || STRING_AR_XB.equals(string2);
        return bl;
    }

    public static boolean isPseudoLocale(Locale locale) {
        boolean bl = LOCALE_EN_XA.equals(locale) || LOCALE_AR_XB.equals(locale);
        return bl;
    }

    public static boolean isPseudoLocalesOnly(String[] arrstring) {
        if (arrstring == null) {
            return true;
        }
        if (arrstring.length > 3) {
            return false;
        }
        for (String string2 : arrstring) {
            if (string2.isEmpty() || LocaleList.isPseudoLocale(string2)) continue;
            return false;
        }
        return true;
    }

    private static int matchScore(Locale object, Locale locale) {
        boolean bl = ((Locale)object).equals(locale);
        int n = 1;
        if (bl) {
            return 1;
        }
        if (!((Locale)object).getLanguage().equals(locale.getLanguage())) {
            return 0;
        }
        if (!LocaleList.isPseudoLocale((Locale)object) && !LocaleList.isPseudoLocale(locale)) {
            String string2 = LocaleList.getLikelyScript((Locale)object);
            if (string2.isEmpty()) {
                if (!((String)(object = ((Locale)object).getCountry())).isEmpty() && !((String)object).equals(locale.getCountry())) {
                    n = 0;
                }
                return n;
            }
            return (int)string2.equals(LocaleList.getLikelyScript(locale));
        }
        return 0;
    }

    public static void setDefault(LocaleList localeList) {
        LocaleList.setDefault(localeList, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void setDefault(LocaleList localeList, int n) {
        if (localeList == null) {
            throw new NullPointerException("locales is null");
        }
        if (localeList.isEmpty()) {
            throw new IllegalArgumentException("locales is empty");
        }
        Object object = sLock;
        synchronized (object) {
            sLastDefaultLocale = localeList.get(n);
            Locale.setDefault(sLastDefaultLocale);
            sLastExplicitlySetLocaleList = localeList;
            sDefaultLocaleList = localeList;
            sDefaultAdjustedLocaleList = n == 0 ? sDefaultLocaleList : (localeList = new LocaleList(sLastDefaultLocale, sDefaultLocaleList));
            return;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object arrlocale) {
        if (arrlocale == this) {
            return true;
        }
        if (!(arrlocale instanceof LocaleList)) {
            return false;
        }
        Locale[] arrlocale2 = ((LocaleList)arrlocale).mList;
        if (this.mList.length != arrlocale2.length) {
            return false;
        }
        for (int i = 0; i < (arrlocale = this.mList).length; ++i) {
            if (arrlocale[i].equals(arrlocale2[i])) continue;
            return false;
        }
        return true;
    }

    public Locale get(int n) {
        Object object;
        object = n >= 0 && n < ((Locale[])(object = this.mList)).length ? object[n] : null;
        return object;
    }

    public Locale getFirstMatch(String[] arrstring) {
        return this.computeFirstMatch(Arrays.asList(arrstring), false);
    }

    public int getFirstMatchIndex(String[] arrstring) {
        return this.computeFirstMatchIndex(Arrays.asList(arrstring), false);
    }

    public int getFirstMatchIndexWithEnglishSupported(Collection<String> collection) {
        return this.computeFirstMatchIndex(collection, true);
    }

    public int getFirstMatchIndexWithEnglishSupported(String[] arrstring) {
        return this.getFirstMatchIndexWithEnglishSupported(Arrays.asList(arrstring));
    }

    public Locale getFirstMatchWithEnglishSupported(String[] arrstring) {
        return this.computeFirstMatch(Arrays.asList(arrstring), true);
    }

    public int hashCode() {
        Locale[] arrlocale;
        int n = 1;
        for (int i = 0; i < (arrlocale = this.mList).length; ++i) {
            n = n * 31 + arrlocale[i].hashCode();
        }
        return n;
    }

    public int indexOf(Locale locale) {
        Locale[] arrlocale;
        for (int i = 0; i < (arrlocale = this.mList).length; ++i) {
            if (!arrlocale[i].equals(locale)) continue;
            return i;
        }
        return -1;
    }

    public boolean isEmpty() {
        boolean bl = this.mList.length == 0;
        return bl;
    }

    public int size() {
        return this.mList.length;
    }

    public String toLanguageTags() {
        return this.mStringRepresentation;
    }

    public String toString() {
        Locale[] arrlocale;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < (arrlocale = this.mList).length; ++i) {
            stringBuilder.append(arrlocale[i]);
            if (i >= this.mList.length - 1) continue;
            stringBuilder.append(',');
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mStringRepresentation);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        Object object;
        for (int i = 0; i < ((Locale[])(object = this.mList)).length; ++i) {
            object = object[i];
            long l2 = protoOutputStream.start(l);
            protoOutputStream.write(1138166333441L, ((Locale)object).getLanguage());
            protoOutputStream.write(1138166333442L, ((Locale)object).getCountry());
            protoOutputStream.write(1138166333443L, ((Locale)object).getVariant());
            protoOutputStream.write(1138166333444L, ((Locale)object).getScript());
            protoOutputStream.end(l2);
        }
    }

}

