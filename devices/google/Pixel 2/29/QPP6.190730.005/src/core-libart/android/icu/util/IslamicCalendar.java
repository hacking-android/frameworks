/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.CalendarAstronomer;
import android.icu.impl.CalendarCache;
import android.icu.impl.CalendarUtil;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Locale;

public class IslamicCalendar
extends Calendar {
    private static final long ASTRONOMICAL_EPOC = 1948439L;
    private static final long CIVIL_EPOC = 1948440L;
    public static final int DHU_AL_HIJJAH = 11;
    public static final int DHU_AL_QIDAH = 10;
    private static final long HIJRA_MILLIS = -42521587200000L;
    public static final int JUMADA_1 = 4;
    public static final int JUMADA_2 = 5;
    private static final int[][] LIMITS = new int[][]{{0, 0, 0, 0}, {1, 1, 5000000, 5000000}, {0, 0, 11, 11}, {1, 1, 50, 51}, new int[0], {1, 1, 29, 30}, {1, 1, 354, 355}, new int[0], {-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], {1, 1, 5000000, 5000000}, new int[0], {1, 1, 5000000, 5000000}, new int[0], new int[0]};
    public static final int MUHARRAM = 0;
    public static final int RABI_1 = 2;
    public static final int RABI_2 = 3;
    public static final int RAJAB = 6;
    public static final int RAMADAN = 8;
    public static final int SAFAR = 1;
    public static final int SHABAN = 7;
    public static final int SHAWWAL = 9;
    private static final int[] UMALQURA_MONTHLENGTH = new int[]{2730, 3412, 3785, 1748, 1770, 876, 2733, 1365, 1705, 1938, 2985, 1492, 2778, 1372, 3373, 1685, 1866, 2900, 2922, 1453, 1198, 2639, 1303, 1675, 1701, 2773, 726, 2395, 1181, 2637, 3366, 3477, 1452, 2486, 698, 2651, 1323, 2709, 1738, 2793, 756, 2422, 694, 2390, 2762, 2980, 3026, 1497, 732, 2413, 1357, 2725, 2898, 2981, 1460, 2486, 1367, 663, 1355, 1699, 1874, 2917, 1386, 2731, 1323, 3221, 3402, 3493, 1482, 2774, 2391, 1195, 2379, 2725, 2898, 2922, 1397, 630, 2231, 1115, 1365, 1449, 1460, 2522, 1245, 622, 2358, 2730, 3412, 3506, 1493, 730, 2395, 1195, 2645, 2889, 2916, 2929, 1460, 2741, 2645, 3365, 3730, 3785, 1748, 2793, 2411, 1195, 2707, 3401, 3492, 3506, 2745, 1210, 2651, 1323, 2709, 2858, 2901, 1372, 1213, 573, 2333, 2709, 2890, 2906, 1389, 694, 2363, 1179, 1621, 1705, 1876, 2922, 1388, 2733, 1365, 2857, 2962, 2985, 1492, 2778, 1370, 2731, 1429, 1865, 1892, 2986, 1461, 694, 2646, 3661, 2853, 2898, 2922, 1453, 686, 2351, 1175, 1611, 1701, 1708, 2774, 1373, 1181, 2637, 3350, 3477, 1450, 1461, 730, 2395, 1197, 1429, 1738, 1764, 2794, 1269, 694, 2390, 2730, 2900, 3026, 1497, 746, 2413, 1197, 2709, 2890, 2981, 1458, 2485, 1238, 2711, 1351, 1683, 1865, 2901, 1386, 2667, 1323, 2699, 3398, 3491, 1482, 2774, 1243, 619, 2379, 2725, 2898, 2921, 1397, 374, 2231, 603, 1323, 1381, 1460, 2522, 1261, 365, 2230, 2726, 3410, 3497, 1492, 2778, 2395, 1195, 1619, 1833, 1890, 2985, 1458, 2741, 1365, 2853, 3474, 3785, 1746, 2793, 1387, 1195, 2645, 3369, 3412, 3498, 2485, 1210, 2619, 1179, 2637, 2730, 2773, 730, 2397, 1118, 2606, 3226, 3413, 1714, 1721, 1210, 2653, 1325, 2709, 2898, 2984, 2996, 1465, 730, 2394, 2890, 3492, 3793, 1768, 2922, 1389, 1333, 1685, 3402, 3496, 3540, 1754, 1371, 669, 1579, 2837, 2890, 2965, 1450, 2734, 2350, 3215, 1319, 1685, 1706, 2774, 1373, 669};
    private static final int UMALQURA_YEAR_END = 1600;
    private static final int UMALQURA_YEAR_START = 1300;
    private static final byte[] UMALQURA_YEAR_START_ESTIMATE_FIX = new byte[]{0, 0, -1, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, -1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, -1, -1, 0, -1, 0, 1, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, -1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 0, -1, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 1, 1, 0, 0, -1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, 0, -1, 0, -1, 0, 1, 0, 0, 0, -1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, 0, -1, -1, 0, -1, 0, 1, 0, 0, -1, -1, 0, 0, 1, 1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    private static CalendarAstronomer astro = new CalendarAstronomer();
    private static CalendarCache cache = new CalendarCache();
    private static final long serialVersionUID = -6253365474073869325L;
    private CalculationType cType = CalculationType.ISLAMIC_CIVIL;
    private boolean civil = true;

    public IslamicCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IslamicCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public IslamicCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    public IslamicCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IslamicCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setCalcTypeForLocale(uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public IslamicCalendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }

    public IslamicCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public IslamicCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    public IslamicCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    private static final boolean civilLeapYear(int n) {
        boolean bl = (n * 11 + 14) % 30 < 11;
        return bl;
    }

    private long monthStart(int n, int n2) {
        int n3 = n2 / 12 + n;
        int n4 = n2 % 12;
        long l = 0L;
        if (this.cType != CalculationType.ISLAMIC_CIVIL && this.cType != CalculationType.ISLAMIC_TBLA && (this.cType != CalculationType.ISLAMIC_UMALQURA || n >= 1300)) {
            if (this.cType == CalculationType.ISLAMIC) {
                l = IslamicCalendar.trueMonthStart((n3 - 1) * 12 + n4);
            } else if (this.cType == CalculationType.ISLAMIC_UMALQURA) {
                long l2 = this.yearStart(n);
                n3 = 0;
                do {
                    l = l2;
                    if (n3 < n2) {
                        l2 += (long)this.handleGetMonthLength(n, n3);
                        ++n3;
                        continue;
                    }
                    break;
                } while (true);
            }
        } else {
            l = (long)Math.ceil((double)n4 * 29.5) + (long)((n3 - 1) * 354) + (long)Math.floor((double)(n3 * 11 + 3) / 30.0);
        }
        return l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static final double moonAge(long l) {
        double d;
        double d2;
        CalendarAstronomer calendarAstronomer = astro;
        synchronized (calendarAstronomer) {
            astro.setTime(l);
            d2 = astro.getMoonAge();
        }
        d2 = d = d2 * 180.0 / 3.141592653589793;
        if (!(d > 180.0)) return d2;
        return d - 360.0;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        object.defaultReadObject();
        object = this.cType;
        if (object == null) {
            object = this.civil ? CalculationType.ISLAMIC_CIVIL : CalculationType.ISLAMIC;
            this.cType = object;
        } else {
            boolean bl = object == CalculationType.ISLAMIC_CIVIL;
            this.civil = bl;
        }
    }

    private void setCalcTypeForLocale(ULocale object) {
        if ("islamic-civil".equals(object = CalendarUtil.getCalendarType((ULocale)object))) {
            this.setCalculationType(CalculationType.ISLAMIC_CIVIL);
        } else if ("islamic-umalqura".equals(object)) {
            this.setCalculationType(CalculationType.ISLAMIC_UMALQURA);
        } else if ("islamic-tbla".equals(object)) {
            this.setCalculationType(CalculationType.ISLAMIC_TBLA);
        } else if (((String)object).startsWith("islamic")) {
            this.setCalculationType(CalculationType.ISLAMIC);
        } else {
            this.setCalculationType(CalculationType.ISLAMIC_CIVIL);
        }
    }

    private static final long trueMonthStart(long l) {
        long l2;
        long l3 = l2 = cache.get(l);
        if (l2 == CalendarCache.EMPTY) {
            l2 = (long)Math.floor((double)l * 29.530588853) * 86400000L - 42521587200000L;
            IslamicCalendar.moonAge(l2);
            l3 = l2;
            if (IslamicCalendar.moonAge(l2) >= 0.0) {
                do {
                    l2 = l3 = l2 - 86400000L;
                } while (IslamicCalendar.moonAge(l3) >= 0.0);
            } else {
                do {
                    l3 = l2 = l3 + 86400000L;
                } while (IslamicCalendar.moonAge(l2) < 0.0);
                l3 = l2;
            }
            l3 = (l3 + 42521587200000L) / 86400000L + 1L;
            cache.put(l, l3);
        }
        return l3;
    }

    private long yearStart(int n) {
        long l = 0L;
        if (this.cType != CalculationType.ISLAMIC_CIVIL && this.cType != CalculationType.ISLAMIC_TBLA && (this.cType != CalculationType.ISLAMIC_UMALQURA || n >= 1300 && n <= 1600)) {
            if (this.cType == CalculationType.ISLAMIC) {
                l = IslamicCalendar.trueMonthStart((n - 1) * 12);
            } else if (this.cType == CalculationType.ISLAMIC_UMALQURA) {
                int n2 = (int)((double)(n -= 1300) * 354.3672 + 460322.05 + 0.5);
                l = UMALQURA_YEAR_START_ESTIMATE_FIX[n] + n2;
            }
        } else {
            l = (long)((n - 1) * 354) + (long)Math.floor((double)(n * 11 + 3) / 30.0);
        }
        return l;
    }

    public CalculationType getCalculationType() {
        return this.cType;
    }

    @Override
    public String getType() {
        CalculationType calculationType = this.cType;
        if (calculationType == null) {
            return "islamic";
        }
        return calculationType.bcpType();
    }

    @Override
    protected void handleComputeFields(int n) {
        int n2;
        int n3 = 0;
        boolean bl = false;
        int n4 = 0;
        boolean bl2 = false;
        long l = (long)n - 1948440L;
        if (this.cType != CalculationType.ISLAMIC_CIVIL && this.cType != CalculationType.ISLAMIC_TBLA) {
            if (this.cType == CalculationType.ISLAMIC) {
                n = n4 = (int)Math.floor((double)l / 29.530588853);
                if (l - (long)Math.floor((double)n4 * 29.530588853 - 1.0) >= 25L) {
                    n = n4;
                    if (IslamicCalendar.moonAge(this.internalGetTimeInMillis()) > 0.0) {
                        n = n4 + 1;
                    }
                }
                while (IslamicCalendar.trueMonthStart(n) > l) {
                    --n;
                }
                n4 = n / 12 + 1;
                n3 = n % 12;
                n = n4;
                n4 = n3;
            } else if (this.cType == CalculationType.ISLAMIC_UMALQURA) {
                if (l < this.yearStart(1300)) {
                    n = (int)Math.floor((double)(30L * l + 10646L) / 10631.0);
                    n4 = Math.min((int)Math.ceil((double)(l - 29L - this.yearStart(n)) / 29.5), 11);
                } else {
                    n4 = 1299;
                    n = 0;
                    long l2 = 1L;
                    block1 : while (l2 > 0L) {
                        n2 = n4 + 1;
                        l2 = l - this.yearStart(n2) + 1L;
                        if (l2 == (long)this.handleGetYearLength(n2)) {
                            n = 11;
                            n4 = n2;
                            break;
                        }
                        if (l2 < (long)this.handleGetYearLength(n2)) {
                            int n5 = this.handleGetMonthLength(n2, 0);
                            n3 = 0;
                            do {
                                n4 = n2;
                                n = n3++;
                                if (l2 <= (long)n5) break block1;
                                l2 -= (long)n5;
                                n5 = this.handleGetMonthLength(n2, n3);
                            } while (true);
                        }
                        n4 = n2;
                    }
                    n3 = n4;
                    n4 = n;
                    n = n3;
                }
            } else {
                n = n3;
            }
        } else {
            if (this.cType == CalculationType.ISLAMIC_TBLA) {
                l = (long)n - 1948439L;
            }
            n = (int)Math.floor((double)(30L * l + 10646L) / 10631.0);
            n4 = Math.min((int)Math.ceil((double)(l - 29L - this.yearStart(n)) / 29.5), 11);
        }
        n2 = (int)(l - this.monthStart(n, n4));
        n3 = (int)(l - this.monthStart(n, 0) + 1L);
        this.internalSet(0, 0);
        this.internalSet(1, n);
        this.internalSet(19, n);
        this.internalSet(2, n4);
        this.internalSet(5, n2 + 1);
        this.internalSet(6, n3);
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        long l = this.monthStart(n, n2);
        long l2 = this.cType == CalculationType.ISLAMIC_TBLA ? 1948439L : 1948440L;
        return (int)(l + l2 - 1L);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : this.internalGet(1, 1);
        return n;
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        int n3;
        if (this.cType != CalculationType.ISLAMIC_CIVIL && this.cType != CalculationType.ISLAMIC_TBLA && (this.cType != CalculationType.ISLAMIC_UMALQURA || n >= 1300 && n <= 1600)) {
            if (this.cType == CalculationType.ISLAMIC) {
                n = n2 + (n - 1) * 12;
                n3 = (int)(IslamicCalendar.trueMonthStart(n + 1) - IslamicCalendar.trueMonthStart(n));
            } else {
                n3 = (UMALQURA_MONTHLENGTH[n - 1300] & 1 << 11 - n2) == 0 ? 29 : 30;
            }
        } else {
            int n4;
            n3 = n4 = (n2 + 1) % 2 + 29;
            if (n2 == 11) {
                n3 = n4;
                if (IslamicCalendar.civilLeapYear(n)) {
                    n3 = n4 + 1;
                }
            }
        }
        return n3;
    }

    @Override
    protected int handleGetYearLength(int n) {
        int n2 = 0;
        int n3 = 0;
        if (this.cType != CalculationType.ISLAMIC_CIVIL && this.cType != CalculationType.ISLAMIC_TBLA && (this.cType != CalculationType.ISLAMIC_UMALQURA || n >= 1300 && n <= 1600)) {
            if (this.cType == CalculationType.ISLAMIC) {
                n = (n - 1) * 12;
                n2 = (int)(IslamicCalendar.trueMonthStart(n + 12) - IslamicCalendar.trueMonthStart(n));
            } else if (this.cType == CalculationType.ISLAMIC_UMALQURA) {
                int n4 = 0;
                do {
                    n2 = n3;
                    if (n4 < 12) {
                        n3 += this.handleGetMonthLength(n, n4);
                        ++n4;
                        continue;
                    }
                    break;
                } while (true);
            }
        } else {
            n2 = IslamicCalendar.civilLeapYear(n) + 354;
        }
        return n2;
    }

    public boolean isCivil() {
        return this.cType == CalculationType.ISLAMIC_CIVIL;
    }

    public void setCalculationType(CalculationType calculationType) {
        this.cType = calculationType;
        this.civil = this.cType == CalculationType.ISLAMIC_CIVIL;
    }

    public void setCivil(boolean bl) {
        block1 : {
            block0 : {
                this.civil = bl;
                if (!bl || this.cType == CalculationType.ISLAMIC_CIVIL) break block0;
                long l = this.getTimeInMillis();
                this.cType = CalculationType.ISLAMIC_CIVIL;
                this.clear();
                this.setTimeInMillis(l);
                break block1;
            }
            if (bl || this.cType == CalculationType.ISLAMIC) break block1;
            long l = this.getTimeInMillis();
            this.cType = CalculationType.ISLAMIC;
            this.clear();
            this.setTimeInMillis(l);
        }
    }

    public static enum CalculationType {
        ISLAMIC("islamic"),
        ISLAMIC_CIVIL("islamic-civil"),
        ISLAMIC_UMALQURA("islamic-umalqura"),
        ISLAMIC_TBLA("islamic-tbla");
        
        private String bcpType;

        private CalculationType(String string2) {
            this.bcpType = string2;
        }

        String bcpType() {
            return this.bcpType;
        }
    }

}

