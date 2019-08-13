/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Utility;
import android.icu.text.BreakIterator;
import android.icu.text.Collator;
import android.icu.text.DateFormat;
import android.icu.text.NumberFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.Currency;
import android.icu.util.Freezable;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class GlobalizationPreferences
implements Freezable<GlobalizationPreferences> {
    public static final int BI_CHARACTER = 0;
    private static final int BI_LIMIT = 5;
    public static final int BI_LINE = 2;
    public static final int BI_SENTENCE = 3;
    public static final int BI_TITLE = 4;
    public static final int BI_WORD = 1;
    public static final int DF_FULL = 0;
    private static final int DF_LIMIT = 5;
    public static final int DF_LONG = 1;
    public static final int DF_MEDIUM = 2;
    public static final int DF_NONE = 4;
    public static final int DF_SHORT = 3;
    public static final int ID_CURRENCY = 7;
    public static final int ID_CURRENCY_SYMBOL = 8;
    public static final int ID_KEYWORD = 5;
    public static final int ID_KEYWORD_VALUE = 6;
    public static final int ID_LANGUAGE = 1;
    public static final int ID_LOCALE = 0;
    public static final int ID_SCRIPT = 2;
    public static final int ID_TERRITORY = 3;
    public static final int ID_TIMEZONE = 9;
    public static final int ID_VARIANT = 4;
    public static final int NF_CURRENCY = 1;
    public static final int NF_INTEGER = 4;
    private static final int NF_LIMIT = 5;
    public static final int NF_NUMBER = 0;
    public static final int NF_PERCENT = 2;
    public static final int NF_SCIENTIFIC = 3;
    private static final int TYPE_BREAKITERATOR = 5;
    private static final int TYPE_CALENDAR = 1;
    private static final int TYPE_COLLATOR = 4;
    private static final int TYPE_DATEFORMAT = 2;
    private static final int TYPE_GENERIC = 0;
    private static final int TYPE_LIMIT = 6;
    private static final int TYPE_NUMBERFORMAT = 3;
    private static final HashMap<ULocale, BitSet> available_locales;
    private static final String[][] language_territory_hack;
    private static final Map<String, String> language_territory_hack_map;
    static final String[][] territory_tzid_hack;
    static final Map<String, String> territory_tzid_hack_map;
    private BreakIterator[] breakIterators;
    private Calendar calendar;
    private Collator collator;
    private Currency currency;
    private DateFormat[][] dateFormats;
    private volatile boolean frozen;
    private List<ULocale> implicitLocales;
    private List<ULocale> locales;
    private NumberFormat[] numberFormats;
    private String territory;
    private TimeZone timezone;

    static {
        String[] arrstring;
        int n;
        Object object;
        available_locales = new HashMap();
        Object[] arrobject = ULocale.getAvailableLocales();
        for (n = 0; n < arrobject.length; ++n) {
            object = new BitSet(6);
            available_locales.put(arrobject[n], (BitSet)object);
            ((BitSet)object).set(0);
        }
        Object[] arrobject2 = Calendar.getAvailableULocales();
        for (n = 0; n < arrobject2.length; ++n) {
            arrstring = available_locales.get(arrobject2[n]);
            object = arrstring;
            if (arrstring == null) {
                object = new BitSet(6);
                available_locales.put(arrobject[n], (BitSet)object);
            }
            ((BitSet)object).set(1);
        }
        arrobject2 = DateFormat.getAvailableULocales();
        for (n = 0; n < arrobject2.length; ++n) {
            arrstring = available_locales.get(arrobject2[n]);
            object = arrstring;
            if (arrstring == null) {
                object = new BitSet(6);
                available_locales.put(arrobject[n], (BitSet)object);
            }
            ((BitSet)object).set(2);
        }
        arrobject2 = NumberFormat.getAvailableULocales();
        for (n = 0; n < arrobject2.length; ++n) {
            arrstring = available_locales.get(arrobject2[n]);
            object = arrstring;
            if (arrstring == null) {
                object = new BitSet(6);
                available_locales.put(arrobject[n], (BitSet)object);
            }
            ((BitSet)object).set(3);
        }
        arrobject2 = Collator.getAvailableULocales();
        for (n = 0; n < arrobject2.length; ++n) {
            arrstring = available_locales.get(arrobject2[n]);
            object = arrstring;
            if (arrstring == null) {
                object = new BitSet(6);
                available_locales.put(arrobject[n], (BitSet)object);
            }
            ((BitSet)object).set(4);
        }
        object = BreakIterator.getAvailableULocales();
        for (n = 0; n < ((ULocale[])object).length; ++n) {
            available_locales.get(object[n]).set(5);
        }
        language_territory_hack_map = new HashMap<String, String>();
        object = new String[]{"af", "ZA"};
        arrstring = new String[]{"am", "ET"};
        arrobject = new String[]{"ar", "SA"};
        arrobject2 = new String[]{"ay", "PE"};
        String[] arrstring2 = new String[]{"bal", "PK"};
        String[] arrstring3 = new String[]{"be", "BY"};
        String[] arrstring4 = new String[]{"bg", "BG"};
        String[] arrstring5 = new String[]{"bs", "BA"};
        String[] arrstring6 = new String[]{"ca", "ES"};
        String[] arrstring7 = new String[]{"cpe", "SL"};
        String[] arrstring8 = new String[]{"de", "DE"};
        String[] arrstring9 = new String[]{"dv", "MV"};
        String[] arrstring10 = new String[]{"dz", "BT"};
        String[] arrstring11 = new String[]{"el", "GR"};
        String[] arrstring12 = new String[]{"fi", "FI"};
        String[] arrstring13 = new String[]{"fo", "FO"};
        String[] arrstring14 = new String[]{"gd", "GB"};
        String[] arrstring15 = new String[]{"gl", "ES"};
        String[] arrstring16 = new String[]{"gn", "PY"};
        String[] arrstring17 = new String[]{"ha", "NG"};
        String[] arrstring18 = new String[]{"he", "IL"};
        String[] arrstring19 = new String[]{"hr", "HR"};
        String[] arrstring20 = new String[]{"hy", "AM"};
        String[] arrstring21 = new String[]{"id", "ID"};
        String[] arrstring22 = new String[]{"it", "IT"};
        String[] arrstring23 = new String[]{"kk", "KZ"};
        String[] arrstring24 = new String[]{"kl", "GL"};
        String[] arrstring25 = new String[]{"km", "KH"};
        String[] arrstring26 = new String[]{"kn", "IN"};
        String[] arrstring27 = new String[]{"ks", "IN"};
        String[] arrstring28 = new String[]{"la", "VA"};
        String[] arrstring29 = new String[]{"lb", "LU"};
        String[] arrstring30 = new String[]{"lt", "LT"};
        String[] arrstring31 = new String[]{"mh", "MH"};
        String[] arrstring32 = new String[]{"mk", "MK"};
        String[] arrstring33 = new String[]{"mn", "MN"};
        String[] arrstring34 = new String[]{"mo", "MD"};
        String[] arrstring35 = new String[]{"ms", "MY"};
        String[] arrstring36 = new String[]{"na", "NR"};
        String[] arrstring37 = new String[]{"nb", "NO"};
        String[] arrstring38 = new String[]{"ne", "NP"};
        String[] arrstring39 = new String[]{"nl", "NL"};
        String[] arrstring40 = new String[]{"nr", "ZA"};
        String[] arrstring41 = new String[]{"om", "KE"};
        String[] arrstring42 = new String[]{"qu", "PE"};
        String[] arrstring43 = new String[]{"ro", "RO"};
        String[] arrstring44 = new String[]{"ru", "RU"};
        String[] arrstring45 = new String[]{"sd", "IN"};
        String[] arrstring46 = new String[]{"si", "LK"};
        String[] arrstring47 = new String[]{"sk", "SK"};
        String[] arrstring48 = new String[]{"sl", "SI"};
        String[] arrstring49 = new String[]{"sq", "CS"};
        String[] arrstring50 = new String[]{"st", "ZA"};
        String[] arrstring51 = new String[]{"ta", "IN"};
        String[] arrstring52 = new String[]{"th", "TH"};
        String[] arrstring53 = new String[]{"tg", "TJ"};
        String[] arrstring54 = new String[]{"tk", "TM"};
        String[] arrstring55 = new String[]{"tkl", "TK"};
        String[] arrstring56 = new String[]{"tl", "PH"};
        String[] arrstring57 = new String[]{"to", "TO"};
        String[] arrstring58 = new String[]{"uz", "UZ"};
        String[] arrstring59 = new String[]{"vi", "VN"};
        String[] arrstring60 = new String[]{"zh", "CN"};
        String[] arrstring61 = new String[]{"zh_Hant", "TW"};
        String[] arrstring62 = new String[]{"gez", "ET"};
        String[] arrstring63 = new String[]{"iu", "CA"};
        String[] arrstring64 = new String[]{"kw", "GB"};
        language_territory_hack = new String[][]{object, arrstring, arrobject, {"as", "IN"}, arrobject2, {"az", "AZ"}, arrstring2, arrstring3, arrstring4, {"bn", "IN"}, arrstring5, arrstring6, {"ch", "MP"}, arrstring7, {"cs", "CZ"}, {"cy", "GB"}, {"da", "DK"}, arrstring8, arrstring9, arrstring10, arrstring11, {"en", "US"}, {"es", "ES"}, {"et", "EE"}, {"eu", "ES"}, {"fa", "IR"}, arrstring12, {"fil", "PH"}, {"fj", "FJ"}, arrstring13, {"fr", "FR"}, {"ga", "IE"}, arrstring14, arrstring15, arrstring16, {"gu", "IN"}, {"gv", "GB"}, arrstring17, arrstring18, {"hi", "IN"}, {"ho", "PG"}, arrstring19, {"ht", "HT"}, {"hu", "HU"}, arrstring20, arrstring21, {"is", "IS"}, arrstring22, {"ja", "JP"}, {"ka", "GE"}, arrstring23, arrstring24, arrstring25, arrstring26, {"ko", "KR"}, {"kok", "IN"}, arrstring27, {"ku", "TR"}, {"ky", "KG"}, arrstring28, arrstring29, {"ln", "CG"}, {"lo", "LA"}, arrstring30, {"lv", "LV"}, {"mai", "IN"}, {"men", "GN"}, {"mg", "MG"}, arrstring31, arrstring32, {"ml", "IN"}, arrstring33, {"mni", "IN"}, arrstring34, {"mr", "IN"}, arrstring35, {"mt", "MT"}, {"my", "MM"}, arrstring36, arrstring37, {"nd", "ZA"}, arrstring38, {"niu", "NU"}, arrstring39, {"nn", "NO"}, {"no", "NO"}, arrstring40, {"nso", "ZA"}, {"ny", "MW"}, arrstring41, {"or", "IN"}, {"pa", "IN"}, {"pau", "PW"}, {"pl", "PL"}, {"ps", "PK"}, {"pt", "BR"}, arrstring42, {"rn", "BI"}, arrstring43, arrstring44, {"rw", "RW"}, arrstring45, {"sg", "CF"}, arrstring46, arrstring47, arrstring48, {"sm", "WS"}, {"so", "DJ"}, arrstring49, {"sr", "CS"}, {"ss", "ZA"}, arrstring50, {"sv", "SE"}, {"sw", "KE"}, arrstring51, {"te", "IN"}, {"tem", "SL"}, {"tet", "TL"}, arrstring52, {"ti", "ET"}, arrstring53, arrstring54, arrstring55, {"tvl", "TV"}, arrstring56, {"tn", "ZA"}, arrstring57, {"tpi", "PG"}, {"tr", "TR"}, {"ts", "ZA"}, {"uk", "UA"}, {"ur", "IN"}, arrstring58, {"ve", "ZA"}, arrstring59, {"wo", "SN"}, {"xh", "ZA"}, arrstring60, arrstring61, {"zu", "ZA"}, {"aa", "ET"}, {"byn", "ER"}, {"eo", "DE"}, arrstring62, {"haw", "US"}, arrstring63, arrstring64, {"sa", "IN"}, {"sh", "HR"}, {"sid", "ET"}, {"syr", "SY"}, {"tig", "ER"}, {"tt", "RU"}, {"wal", "ET"}};
        for (n = 0; n < ((Object)(object = language_territory_hack)).length; ++n) {
            language_territory_hack_map.put((String)object[n][0], (String)object[n][1]);
        }
        territory_tzid_hack_map = new HashMap<String, String>();
        object = new String[]{"AQ", "Antarctica/McMurdo"};
        arrstring = new String[]{"AU", "Australia/Sydney"};
        arrobject = new String[]{"BR", "America/Sao_Paulo"};
        arrobject2 = new String[]{"CA", "America/Toronto"};
        arrstring2 = new String[]{"CD", "Africa/Kinshasa"};
        arrstring3 = new String[]{"CL", "America/Santiago"};
        arrstring4 = new String[]{"CN", "Asia/Shanghai"};
        arrstring5 = new String[]{"EC", "America/Guayaquil"};
        arrstring6 = new String[]{"ES", "Europe/Madrid"};
        arrstring7 = new String[]{"GB", "Europe/London"};
        arrstring8 = new String[]{"GL", "America/Godthab"};
        arrstring9 = new String[]{"ID", "Asia/Jakarta"};
        arrstring10 = new String[]{"MX", "America/Mexico_City"};
        arrstring11 = new String[]{"NZ", "Pacific/Auckland"};
        arrstring12 = new String[]{"PT", "Europe/Lisbon"};
        arrstring13 = new String[]{"RU", "Europe/Moscow"};
        arrstring14 = new String[]{"UA", "Europe/Kiev"};
        arrstring15 = new String[]{"US", "America/New_York"};
        arrstring16 = new String[]{"PF", "Pacific/Tahiti"};
        arrstring17 = new String[]{"FM", "Pacific/Kosrae"};
        arrstring18 = new String[]{"MH", "Pacific/Majuro"};
        arrstring19 = new String[]{"MN", "Asia/Ulaanbaatar"};
        arrstring20 = new String[]{"SJ", "Arctic/Longyearbyen"};
        arrstring21 = new String[]{"UM", "Pacific/Midway"};
        territory_tzid_hack = new String[][]{object, {"AR", "America/Buenos_Aires"}, arrstring, arrobject, arrobject2, arrstring2, arrstring3, arrstring4, arrstring5, arrstring6, arrstring7, arrstring8, arrstring9, {"ML", "Africa/Bamako"}, arrstring10, {"MY", "Asia/Kuala_Lumpur"}, arrstring11, arrstring12, arrstring13, arrstring14, arrstring15, {"UZ", "Asia/Tashkent"}, arrstring16, arrstring17, {"KI", "Pacific/Tarawa"}, {"KZ", "Asia/Almaty"}, arrstring18, arrstring19, arrstring20, arrstring21};
        for (n = 0; n < ((Object)(object = territory_tzid_hack)).length; ++n) {
            territory_tzid_hack_map.put((String)object[n][0], (String)object[n][1]);
        }
    }

    public GlobalizationPreferences() {
        this.reset();
    }

    private ULocale getAvailableLocale(int n) {
        ULocale uLocale;
        List<ULocale> list = this.getLocales();
        ULocale uLocale2 = null;
        int n2 = 0;
        do {
            uLocale = uLocale2;
            if (n2 >= list.size() || this.isAvailableLocale(uLocale = list.get(n2), n)) break;
            ++n2;
        } while (true);
        return uLocale;
    }

    private boolean isAvailableLocale(ULocale serializable, int n) {
        return (serializable = available_locales.get(serializable)) != null && ((BitSet)serializable).get(n);
    }

    @Override
    public GlobalizationPreferences cloneAsThawed() {
        try {
            GlobalizationPreferences globalizationPreferences = (GlobalizationPreferences)this.clone();
            globalizationPreferences.frozen = false;
            return globalizationPreferences;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public GlobalizationPreferences freeze() {
        this.frozen = true;
        return this;
    }

    public BreakIterator getBreakIterator(int n) {
        if (n >= 0 && n < 5) {
            BreakIterator[] arrbreakIterator = this.breakIterators;
            if (arrbreakIterator != null && arrbreakIterator[n] != null) {
                return (BreakIterator)arrbreakIterator[n].clone();
            }
            return this.guessBreakIterator(n);
        }
        throw new IllegalArgumentException("Illegal break iterator type");
    }

    public Calendar getCalendar() {
        Calendar calendar = this.calendar;
        if (calendar == null) {
            return this.guessCalendar();
        }
        calendar = (Calendar)calendar.clone();
        calendar.setTimeZone(this.getTimeZone());
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar;
    }

    public Collator getCollator() {
        Collator collator = this.collator;
        if (collator == null) {
            return this.guessCollator();
        }
        try {
            collator = (Collator)collator.clone();
            return collator;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", cloneNotSupportedException);
        }
    }

    public Currency getCurrency() {
        Currency currency = this.currency;
        if (currency == null) {
            return this.guessCurrency();
        }
        return currency;
    }

    public DateFormat getDateFormat(int n, int n2) {
        if ((n != 4 || n2 != 4) && n >= 0 && n < 5 && n2 >= 0 && n2 < 5) {
            DateFormat dateFormat = null;
            DateFormat[][] arrdateFormat = this.dateFormats;
            if (arrdateFormat != null) {
                dateFormat = arrdateFormat[n][n2];
            }
            if (dateFormat != null) {
                dateFormat = (DateFormat)dateFormat.clone();
                dateFormat.setTimeZone(this.getTimeZone());
            } else {
                dateFormat = this.guessDateFormat(n, n2);
            }
            return dateFormat;
        }
        throw new IllegalArgumentException("Illegal date format style arguments");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String getDisplayName(String var1_1, int var2_2) {
        var3_3 = var1_1;
        var4_4 = this.getLocales().iterator();
        block11 : while (var4_4.hasNext() != false) {
            var5_6 = var4_4.next();
            var6_9 = 0;
            if (!this.isAvailableLocale(var5_6, 0)) continue;
            switch (var2_2) {
                default: {
                    var1_1 = new StringBuilder();
                    var1_1.append("Unknown type: ");
                    var1_1.append(var2_2);
                    throw new IllegalArgumentException(var1_1.toString());
                }
                case 9: {
                    var3_3 = new SimpleDateFormat("vvvv", var5_6);
                    var3_3.setTimeZone(TimeZone.getFrozenTimeZone((String)var1_1));
                    var5_7 = var3_3.format(new Date());
                    var6_9 = 0;
                    var3_3 = var5_7;
                    var7_10 = var5_7.indexOf(40);
                    var8_11 = var5_7.indexOf(41);
                    var9_12 = var3_3;
                    if (var7_10 != -1) {
                        var9_12 = var3_3;
                        if (var8_11 != -1) {
                            var9_12 = var3_3;
                            if (var8_11 - var7_10 == 3) {
                                var9_12 = var5_7.substring(var7_10 + 1, var8_11);
                            }
                        }
                    }
                    if (var9_12.length() != 2) ** GOTO lbl41
                    var8_11 = 1;
                    var7_10 = 0;
                    do {
                        var6_9 = var8_11;
                        if (var7_10 >= 2) ** GOTO lbl41
                        var6_9 = var9_12.charAt(var7_10);
                        if (var6_9 < 65 || 90 < var6_9) break;
                        ++var7_10;
                    } while (true);
                    var6_9 = 0;
lbl41: // 3 sources:
                    var3_3 = var5_7;
                    if (var6_9 == 0) break;
                    var3_3 = var5_7;
                    continue block11;
                }
                case 7: 
                case 8: {
                    var3_3 = new Currency((String)var1_1);
                    if (var2_2 == 7) {
                        var6_9 = 1;
                    }
                    var3_3 = var3_3.getName(var5_6, var6_9, new boolean[1]);
                    break;
                }
                case 6: {
                    var9_12 = new String[2];
                    Utility.split((String)var1_1, '=', (String[])var9_12);
                    var3_3 = new StringBuilder();
                    var3_3.append("und@");
                    var3_3.append((String)var1_1);
                    var5_8 = ULocale.getDisplayKeywordValue(var3_3.toString(), var9_12[0], var5_6);
                    var3_3 = var5_8;
                    if (!var5_8.equals(var9_12[1])) break;
                    var3_3 = var5_8;
                    continue block11;
                }
                case 5: {
                    var3_3 = ULocale.getDisplayKeyword((String)var1_1, var5_6);
                    break;
                }
                case 4: {
                    var3_3 = new StringBuilder();
                    var3_3.append("und-QQ-");
                    var3_3.append((String)var1_1);
                    var3_3 = ULocale.getDisplayVariant(var3_3.toString(), var5_6);
                    break;
                }
                case 3: {
                    var3_3 = new StringBuilder();
                    var3_3.append("und-");
                    var3_3.append((String)var1_1);
                    var3_3 = ULocale.getDisplayCountry(var3_3.toString(), var5_6);
                    break;
                }
                case 2: {
                    var3_3 = new StringBuilder();
                    var3_3.append("und-");
                    var3_3.append((String)var1_1);
                    var3_3 = ULocale.getDisplayScript(var3_3.toString(), var5_6);
                    break;
                }
                case 1: {
                    var3_3 = ULocale.getDisplayLanguage((String)var1_1, var5_6);
                    break;
                }
                case 0: {
                    var3_3 = ULocale.getDisplayName((String)var1_1, var5_6);
                }
            }
            if (!var1_1.equals(var3_3)) return var3_3;
        }
        return var3_3;
    }

    public ULocale getLocale(int n) {
        List<ULocale> list;
        List<ULocale> list2 = list = this.locales;
        if (list == null) {
            list2 = this.guessLocales();
        }
        if (n >= 0 && n < list2.size()) {
            return list2.get(n);
        }
        return null;
    }

    public List<ULocale> getLocales() {
        List<ULocale> list;
        if (this.locales == null) {
            list = this.guessLocales();
        } else {
            list = new ArrayList<ULocale>();
            list.addAll(this.locales);
        }
        return list;
    }

    public NumberFormat getNumberFormat(int n) {
        if (n >= 0 && n < 5) {
            NumberFormat numberFormat = null;
            NumberFormat[] arrnumberFormat = this.numberFormats;
            if (arrnumberFormat != null) {
                numberFormat = arrnumberFormat[n];
            }
            numberFormat = numberFormat != null ? (NumberFormat)numberFormat.clone() : this.guessNumberFormat(n);
            return numberFormat;
        }
        throw new IllegalArgumentException("Illegal number format type");
    }

    public ResourceBundle getResourceBundle(String string) {
        return this.getResourceBundle(string, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ResourceBundle getResourceBundle(String string, ClassLoader object) {
        uResourceBundle2 = null;
        uResourceBundle3 = null;
        string2 = null;
        list = this.getLocales();
        n = 0;
        do {
            uResourceBundle = uResourceBundle2;
            if (n >= list.size()) break;
            string3 = list.get(n).toString();
            if (string2 != null && string3.equals(string2)) {
                uResourceBundle = uResourceBundle3;
                break;
            }
            if (object != null) ** GOTO lbl18
            uResourceBundle = uResourceBundle3;
            try {
                block8 : {
                    uResourceBundle3 = UResourceBundle.getBundleInstance(string, string3);
                    break block8;
lbl18: // 1 sources:
                    uResourceBundle = uResourceBundle3;
                    uResourceBundle3 = UResourceBundle.getBundleInstance(string, string3, (ClassLoader)object);
                }
                uResourceBundle = uResourceBundle2;
                if (uResourceBundle3 != null) {
                    uResourceBundle = uResourceBundle3;
                    string4 = uResourceBundle3.getULocale().getName();
                    uResourceBundle = uResourceBundle3;
                    bl = string4.equals(string3);
                    if (bl) {
                        uResourceBundle = uResourceBundle3;
                        break;
                    }
                    uResourceBundle = uResourceBundle2;
                    string2 = string4;
                    if (uResourceBundle2 == null) {
                        uResourceBundle = uResourceBundle3;
                        string2 = string4;
                    }
                }
                uResourceBundle2 = uResourceBundle;
            }
            catch (MissingResourceException missingResourceException) {
                string2 = null;
                uResourceBundle3 = uResourceBundle;
            }
            ++n;
        } while (true);
        if (uResourceBundle != null) {
            return uResourceBundle;
        }
        object = new StringBuilder();
        object.append("Can't find bundle for base name ");
        object.append(string);
        throw new MissingResourceException(object.toString(), string, "");
    }

    public String getTerritory() {
        String string = this.territory;
        if (string == null) {
            return this.guessTerritory();
        }
        return string;
    }

    public TimeZone getTimeZone() {
        TimeZone timeZone = this.timezone;
        if (timeZone == null) {
            return this.guessTimeZone();
        }
        return timeZone.cloneAsThawed();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected BreakIterator guessBreakIterator(int n) {
        ULocale uLocale = this.getAvailableLocale(5);
        Object object = uLocale;
        if (uLocale == null) {
            object = ULocale.ROOT;
        }
        if (n == 0) return BreakIterator.getCharacterInstance((ULocale)object);
        if (n == 1) return BreakIterator.getWordInstance((ULocale)object);
        if (n == 2) return BreakIterator.getLineInstance((ULocale)object);
        if (n == 3) return BreakIterator.getSentenceInstance((ULocale)object);
        if (n != 4) throw new IllegalArgumentException("Unknown break iterator type");
        return BreakIterator.getTitleInstance((ULocale)object);
    }

    protected Calendar guessCalendar() {
        ULocale uLocale;
        ULocale uLocale2 = uLocale = this.getAvailableLocale(1);
        if (uLocale == null) {
            uLocale2 = ULocale.US;
        }
        return Calendar.getInstance(this.getTimeZone(), uLocale2);
    }

    protected Collator guessCollator() {
        ULocale uLocale;
        ULocale uLocale2 = uLocale = this.getAvailableLocale(4);
        if (uLocale == null) {
            uLocale2 = ULocale.ROOT;
        }
        return Collator.getInstance(uLocale2);
    }

    protected Currency guessCurrency() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("und-");
        stringBuilder.append(this.getTerritory());
        return Currency.getInstance(new ULocale(stringBuilder.toString()));
    }

    protected DateFormat guessDateFormat(int n, int n2) {
        ULocale uLocale;
        Serializable serializable = uLocale = this.getAvailableLocale(2);
        if (uLocale == null) {
            serializable = ULocale.ROOT;
        }
        serializable = n2 == 4 ? DateFormat.getDateInstance(this.getCalendar(), n, serializable) : (n == 4 ? DateFormat.getTimeInstance(this.getCalendar(), n2, serializable) : DateFormat.getDateTimeInstance(this.getCalendar(), n, n2, serializable));
        return serializable;
    }

    protected List<ULocale> guessLocales() {
        if (this.implicitLocales == null) {
            ArrayList<ULocale> arrayList = new ArrayList<ULocale>(1);
            arrayList.add(ULocale.getDefault());
            this.implicitLocales = this.processLocales(arrayList);
        }
        return this.implicitLocales;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected NumberFormat guessNumberFormat(int n) {
        void var3_11;
        ULocale uLocale;
        void var3_5;
        ULocale uLocale2 = uLocale = this.getAvailableLocale(3);
        if (uLocale == null) {
            ULocale uLocale3 = ULocale.ROOT;
        }
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) throw new IllegalArgumentException("Unknown number format style");
                        NumberFormat numberFormat = NumberFormat.getIntegerInstance((ULocale)var3_5);
                        return var3_11;
                    } else {
                        NumberFormat numberFormat = NumberFormat.getScientificInstance((ULocale)var3_5);
                    }
                    return var3_11;
                } else {
                    NumberFormat numberFormat = NumberFormat.getPercentInstance((ULocale)var3_5);
                }
                return var3_11;
            } else {
                NumberFormat numberFormat = NumberFormat.getCurrencyInstance((ULocale)var3_5);
                numberFormat.setCurrency(this.getCurrency());
            }
            return var3_11;
        } else {
            NumberFormat numberFormat = NumberFormat.getInstance((ULocale)var3_5);
        }
        return var3_11;
    }

    protected String guessTerritory() {
        Object object;
        Object object2 = this.getLocales().iterator();
        while (object2.hasNext()) {
            object = object2.next().getCountry();
            if (((String)object).length() == 0) continue;
            return object;
        }
        object = this.getLocale(0);
        String string = ((ULocale)object).getLanguage();
        object = ((ULocale)object).getScript();
        object2 = null;
        if (((String)object).length() != 0) {
            object2 = language_territory_hack_map;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("_");
            stringBuilder.append((String)object);
            object2 = (String)object2.get(stringBuilder.toString());
        }
        object = object2;
        if (object2 == null) {
            object = language_territory_hack_map.get(string);
        }
        object2 = object;
        if (object == null) {
            object2 = "US";
        }
        return object2;
    }

    protected TimeZone guessTimeZone() {
        String[] arrstring;
        Object object = arrstring = territory_tzid_hack_map.get(this.getTerritory());
        if (arrstring == null) {
            object = TimeZone.getAvailableIDs(this.getTerritory());
            if (((String[])object).length == 0) {
                object = "Etc/GMT";
            } else {
                int n;
                for (n = 0; n < ((String[])object).length && object[n].indexOf("/") < 0; ++n) {
                }
                int n2 = n;
                if (n > ((String[])object).length) {
                    n2 = 0;
                }
                object = object[n2];
            }
        }
        return TimeZone.getTimeZone((String)object);
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    protected List<ULocale> processLocales(List<ULocale> object) {
        ULocale uLocale;
        int n;
        int n2;
        int n3;
        int n4;
        ArrayList<ULocale> arrayList = new ArrayList<ULocale>();
        for (n4 = 0; n4 < object.size(); ++n4) {
            uLocale = object.get(n4);
            String string = uLocale.getLanguage();
            String string2 = uLocale.getScript();
            String string3 = uLocale.getCountry();
            String string4 = uLocale.getVariant();
            n2 = 0;
            n = 0;
            do {
                n3 = n2;
                if (n >= arrayList.size()) break;
                Object object2 = (ULocale)arrayList.get(n);
                if (((ULocale)object2).getLanguage().equals(string)) {
                    String string5 = ((ULocale)object2).getScript();
                    String string6 = ((ULocale)object2).getCountry();
                    object2 = ((ULocale)object2).getVariant();
                    if (!string5.equals(string2)) {
                        if (string5.length() == 0 && string6.length() == 0 && ((String)object2).length() == 0) {
                            arrayList.add(n, uLocale);
                            n3 = 1;
                            break;
                        }
                        if (string5.length() == 0 && string6.equals(string3)) {
                            arrayList.add(n, uLocale);
                            n3 = 1;
                            break;
                        }
                        if (string2.length() == 0 && string3.length() > 0 && string6.length() == 0) {
                            arrayList.add(n, uLocale);
                            n3 = 1;
                            break;
                        }
                    } else {
                        if (!string6.equals(string3) && string6.length() == 0 && ((String)object2).length() == 0) {
                            arrayList.add(n, uLocale);
                            n3 = 1;
                            break;
                        }
                        if (!((String)object2).equals(string4) && ((String)object2).length() == 0) {
                            arrayList.add(n, uLocale);
                            n3 = 1;
                            break;
                        }
                    }
                }
                ++n;
            } while (true);
            if (n3 != 0) continue;
            arrayList.add(uLocale);
        }
        block2 : for (n3 = 0; n3 < arrayList.size(); ++n3) {
            object = (ULocale)arrayList.get(n3);
            do {
                uLocale = ((ULocale)object).getFallback();
                object = uLocale;
                if (uLocale == null || ((ULocale)object).getLanguage().length() == 0) continue block2;
                arrayList.add(++n3, (ULocale)object);
            } while (true);
        }
        n3 = 0;
        while (n3 < arrayList.size() - 1) {
            object = (ULocale)arrayList.get(n3);
            n2 = 0;
            n = n3 + 1;
            do {
                n4 = n2;
                if (n >= arrayList.size()) break;
                if (((ULocale)object).equals(arrayList.get(n))) {
                    arrayList.remove(n3);
                    n4 = 1;
                    break;
                }
                ++n;
            } while (true);
            n = n3;
            if (n4 == 0) {
                n = n3 + 1;
            }
            n3 = n;
        }
        return arrayList;
    }

    public GlobalizationPreferences reset() {
        if (!this.isFrozen()) {
            this.locales = null;
            this.territory = null;
            this.calendar = null;
            this.collator = null;
            this.breakIterators = null;
            this.timezone = null;
            this.currency = null;
            this.dateFormats = null;
            this.numberFormats = null;
            this.implicitLocales = null;
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setBreakIterator(int n, BreakIterator breakIterator) {
        if (n >= 0 && n < 5) {
            if (!this.isFrozen()) {
                if (this.breakIterators == null) {
                    this.breakIterators = new BreakIterator[5];
                }
                this.breakIterators[n] = (BreakIterator)breakIterator.clone();
                return this;
            }
            throw new UnsupportedOperationException("Attempt to modify immutable object");
        }
        throw new IllegalArgumentException("Illegal break iterator type");
    }

    public GlobalizationPreferences setCalendar(Calendar calendar) {
        if (!this.isFrozen()) {
            this.calendar = (Calendar)calendar.clone();
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setCollator(Collator collator) {
        if (!this.isFrozen()) {
            try {
                this.collator = (Collator)collator.clone();
                return this;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new ICUCloneNotSupportedException("Error in cloning collator", cloneNotSupportedException);
            }
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setCurrency(Currency currency) {
        if (!this.isFrozen()) {
            this.currency = currency;
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setDateFormat(int n, int n2, DateFormat dateFormat) {
        if (!this.isFrozen()) {
            if (this.dateFormats == null) {
                this.dateFormats = new DateFormat[5][5];
            }
            this.dateFormats[n][n2] = (DateFormat)dateFormat.clone();
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setLocale(ULocale uLocale) {
        if (!this.isFrozen()) {
            return this.setLocales(new ULocale[]{uLocale});
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setLocales(String arruLocale) {
        if (!this.isFrozen()) {
            try {
                arruLocale = ULocale.parseAcceptLanguage((String)arruLocale, true);
                return this.setLocales(arruLocale);
            }
            catch (ParseException parseException) {
                throw new IllegalArgumentException("Invalid Accept-Language string");
            }
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setLocales(List<ULocale> list) {
        if (!this.isFrozen()) {
            this.locales = this.processLocales(list);
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setLocales(ULocale[] arruLocale) {
        if (!this.isFrozen()) {
            return this.setLocales(Arrays.asList(arruLocale));
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setNumberFormat(int n, NumberFormat numberFormat) {
        if (!this.isFrozen()) {
            if (this.numberFormats == null) {
                this.numberFormats = new NumberFormat[5];
            }
            this.numberFormats[n] = (NumberFormat)numberFormat.clone();
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setTerritory(String string) {
        if (!this.isFrozen()) {
            this.territory = string;
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }

    public GlobalizationPreferences setTimeZone(TimeZone timeZone) {
        if (!this.isFrozen()) {
            this.timezone = (TimeZone)timeZone.clone();
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify immutable object");
    }
}

