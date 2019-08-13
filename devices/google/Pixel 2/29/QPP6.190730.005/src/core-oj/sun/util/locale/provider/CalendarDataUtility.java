/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DateFormatSymbols
 *  android.icu.util.ULocale
 */
package sun.util.locale.provider;

import android.icu.text.DateFormatSymbols;
import android.icu.util.ULocale;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class CalendarDataUtility {
    private static final String BUDDHIST_CALENDAR = "buddhist";
    private static final String GREGORIAN_CALENDAR = "gregorian";
    private static final String ISLAMIC_CALENDAR = "islamic";
    private static final String JAPANESE_CALENDAR = "japanese";
    private static int[] REST_OF_STYLES = new int[]{32769, 2, 32770, 4, 32772};

    private CalendarDataUtility() {
    }

    private static DateFormatSymbols getDateFormatSymbols(String string, Locale locale) {
        string = CalendarDataUtility.normalizeCalendarType(string);
        return new DateFormatSymbols(ULocale.forLocale((Locale)locale), string);
    }

    private static String[] getNames(String charSequence, int n, int n2, Locale locale) {
        int n3 = CalendarDataUtility.toContext(n2);
        n2 = CalendarDataUtility.toWidth(n2);
        charSequence = CalendarDataUtility.getDateFormatSymbols((String)charSequence, locale);
        if (n != 0) {
            if (n != 2) {
                if (n != 7) {
                    if (n == 9) {
                        return charSequence.getAmPmStrings();
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Unknown field: ");
                    ((StringBuilder)charSequence).append(n);
                    throw new UnsupportedOperationException(((StringBuilder)charSequence).toString());
                }
                return charSequence.getWeekdays(n3, n2);
            }
            return charSequence.getMonths(n3, n2);
        }
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 == 2) {
                    return charSequence.getNarrowEras();
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown width: ");
                ((StringBuilder)charSequence).append(n2);
                throw new UnsupportedOperationException(((StringBuilder)charSequence).toString());
            }
            return charSequence.getEraNames();
        }
        return charSequence.getEras();
    }

    private static String normalizeCalendarType(String string) {
        if (!string.equals("gregory") && !string.equals("iso8601")) {
            if (string.startsWith(ISLAMIC_CALENDAR)) {
                string = ISLAMIC_CALENDAR;
            }
        } else {
            string = GREGORIAN_CALENDAR;
        }
        return string;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String retrieveFieldValueName(String var0, int var1_1, int var2_2, int var3_3, Locale var4_4) {
        block3 : {
            block6 : {
                block4 : {
                    block5 : {
                        var5_5 = var2_2;
                        if (var1_1 != 0) break block3;
                        var6_6 = CalendarDataUtility.normalizeCalendarType((String)var0);
                        var5_5 = var6_6.hashCode();
                        if (var5_5 == -1581060683) break block4;
                        if (var5_5 == -752730191) break block5;
                        if (var5_5 != 2093696456 || !var6_6.equals("islamic")) ** GOTO lbl-1000
                        var5_5 = 1;
                        break block6;
                    }
                    if (!var6_6.equals("japanese")) ** GOTO lbl-1000
                    var5_5 = 2;
                    break block6;
                }
                if (var6_6.equals("buddhist")) {
                    var5_5 = 0;
                } else lbl-1000: // 3 sources:
                {
                    var5_5 = -1;
                }
            }
            var5_5 = var5_5 != 0 && var5_5 != 1 ? (var5_5 != 2 ? var2_2 : var2_2 + 231) : var2_2 - 1;
        }
        if (var5_5 < 0) {
            return null;
        }
        if (var5_5 < (var0 = CalendarDataUtility.getNames((String)var0, var1_1, var3_3, var4_4)).length) return var0[var5_5];
        return null;
    }

    public static Map<String, Integer> retrieveFieldValueNames(String object, int n, int n2, Locale locale) {
        Map<String, Integer> map;
        if (n2 == 0) {
            Map<String, Integer> map2 = CalendarDataUtility.retrieveFieldValueNamesImpl((String)object, n, 1, locale);
            int[] arrn = REST_OF_STYLES;
            int n3 = arrn.length;
            n2 = 0;
            do {
                map = map2;
                if (n2 < n3) {
                    map2.putAll(CalendarDataUtility.retrieveFieldValueNamesImpl((String)object, n, arrn[n2], locale));
                    ++n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            map = CalendarDataUtility.retrieveFieldValueNamesImpl((String)object, n, n2, locale);
        }
        object = map.isEmpty() ? null : map;
        return object;
    }

    private static Map<String, Integer> retrieveFieldValueNamesImpl(String object, int n, int n2, Locale arrstring) {
        arrstring = CalendarDataUtility.getNames((String)object, n, n2, (Locale)arrstring);
        int n3 = 0;
        int n4 = 0;
        int n5 = n3;
        n2 = n4;
        if (n == 0) {
            object = CalendarDataUtility.normalizeCalendarType((String)object);
            n = -1;
            n2 = ((String)object).hashCode();
            if (n2 != -1581060683) {
                if (n2 != -752730191) {
                    if (n2 == 2093696456 && ((String)object).equals(ISLAMIC_CALENDAR)) {
                        n = 1;
                    }
                } else if (((String)object).equals(JAPANESE_CALENDAR)) {
                    n = 2;
                }
            } else if (((String)object).equals(BUDDHIST_CALENDAR)) {
                n = 0;
            }
            if (n != 0 && n != 1) {
                if (n != 2) {
                    n5 = n3;
                    n2 = n4;
                } else {
                    n5 = 232;
                    n2 = -231;
                }
            } else {
                n2 = 1;
                n5 = n3;
            }
        }
        object = new LinkedHashMap();
        while (n5 < arrstring.length) {
            if (!arrstring[n5].isEmpty() && object.put(arrstring[n5], n5 + n2) != null) {
                return new LinkedHashMap<String, Integer>();
            }
            ++n5;
        }
        return object;
    }

    public static String retrieveJavaTimeFieldValueName(String string, int n, int n2, int n3, Locale locale) {
        return CalendarDataUtility.retrieveFieldValueName(string, n, n2, n3, locale);
    }

    public static Map<String, Integer> retrieveJavaTimeFieldValueNames(String string, int n, int n2, Locale locale) {
        return CalendarDataUtility.retrieveFieldValueNames(string, n, n2, locale);
    }

    private static int toContext(int n) {
        if (n != 1 && n != 2 && n != 4) {
            if (n != 32772) {
                switch (n) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid style: ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    case 32769: 
                    case 32770: 
                }
            }
            return 1;
        }
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int toWidth(int n) {
        if (n == 1) return 0;
        if (n == 2) return 1;
        if (n == 4 || n == 32772) return 2;
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid style: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 32770: {
                return 1;
            }
            case 32769: 
        }
        return 0;
    }
}

