/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Grego;
import android.icu.impl.Utility;
import android.icu.util.Currency;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CurrencyMetaInfo {
    @Deprecated
    protected static final CurrencyDigits defaultDigits;
    private static final boolean hasData;
    private static final CurrencyMetaInfo impl;

    static {
        CurrencyMetaInfo currencyMetaInfo;
        defaultDigits = new CurrencyDigits(2, 0);
        boolean bl = false;
        try {
            currencyMetaInfo = (CurrencyMetaInfo)Class.forName("android.icu.impl.ICUCurrencyMetaInfo").newInstance();
            bl = true;
        }
        catch (Throwable throwable) {
            currencyMetaInfo = new CurrencyMetaInfo();
        }
        impl = currencyMetaInfo;
        hasData = bl;
    }

    @Deprecated
    protected CurrencyMetaInfo() {
    }

    private static String dateString(long l) {
        if (l != Long.MAX_VALUE && l != Long.MIN_VALUE) {
            return Grego.timeToString(l);
        }
        return null;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String debugString(Object object) {
        Object object2;
        StringBuilder stringBuilder;
        block4 : {
            Field[] arrfield;
            int n;
            stringBuilder = new StringBuilder();
            try {
                arrfield = object.getClass().getFields();
                n = arrfield.length;
            }
            catch (Throwable throwable) {
                // empty catch block
                break block4;
            }
            for (int i = 0; i < n; ++i) {
                Field field = arrfield[i];
                object2 = field.get(object);
                if (object2 == null || (object2 = object2 instanceof Date ? CurrencyMetaInfo.dateString(((Date)object2).getTime()) : (object2 instanceof Long ? CurrencyMetaInfo.dateString((Long)object2) : String.valueOf(object2))) == null) continue;
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(field.getName());
                stringBuilder.append("='");
                stringBuilder.append((String)object2);
                stringBuilder.append("'");
            }
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(object.getClass().getSimpleName());
        ((StringBuilder)object2).append("(");
        stringBuilder.insert(0, ((StringBuilder)object2).toString());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static CurrencyMetaInfo getInstance() {
        return impl;
    }

    public static CurrencyMetaInfo getInstance(boolean bl) {
        CurrencyMetaInfo currencyMetaInfo = hasData ? impl : null;
        return currencyMetaInfo;
    }

    @Deprecated
    public static boolean hasData() {
        return hasData;
    }

    public List<String> currencies(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public CurrencyDigits currencyDigits(String string) {
        return this.currencyDigits(string, Currency.CurrencyUsage.STANDARD);
    }

    public CurrencyDigits currencyDigits(String string, Currency.CurrencyUsage currencyUsage) {
        return defaultDigits;
    }

    public List<CurrencyInfo> currencyInfo(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public List<String> regions(CurrencyFilter currencyFilter) {
        return Collections.emptyList();
    }

    public static final class CurrencyDigits {
        public final int fractionDigits;
        public final int roundingIncrement;

        public CurrencyDigits(int n, int n2) {
            this.fractionDigits = n;
            this.roundingIncrement = n2;
        }

        public String toString() {
            return CurrencyMetaInfo.debugString(this);
        }
    }

    public static final class CurrencyFilter {
        private static final CurrencyFilter ALL = new CurrencyFilter(null, null, Long.MIN_VALUE, Long.MAX_VALUE, false);
        public final String currency;
        public final long from;
        public final String region;
        @Deprecated
        public final boolean tenderOnly;
        public final long to;

        private CurrencyFilter(String string, String string2, long l, long l2, boolean bl) {
            this.region = string;
            this.currency = string2;
            this.from = l;
            this.to = l2;
            this.tenderOnly = bl;
        }

        public static CurrencyFilter all() {
            return ALL;
        }

        private static boolean equals(String string, String string2) {
            boolean bl = Utility.sameObjects(string, string2) || string != null && string.equals(string2);
            return bl;
        }

        public static CurrencyFilter now() {
            return ALL.withDate(new Date());
        }

        public static CurrencyFilter onCurrency(String string) {
            return ALL.withCurrency(string);
        }

        public static CurrencyFilter onDate(long l) {
            return ALL.withDate(l);
        }

        public static CurrencyFilter onDate(Date date) {
            return ALL.withDate(date);
        }

        public static CurrencyFilter onDateRange(long l, long l2) {
            return ALL.withDateRange(l, l2);
        }

        public static CurrencyFilter onDateRange(Date date, Date date2) {
            return ALL.withDateRange(date, date2);
        }

        public static CurrencyFilter onRegion(String string) {
            return ALL.withRegion(string);
        }

        public static CurrencyFilter onTender() {
            return ALL.withTender();
        }

        public boolean equals(CurrencyFilter currencyFilter) {
            boolean bl = Utility.sameObjects(this, currencyFilter) || currencyFilter != null && CurrencyFilter.equals(this.region, currencyFilter.region) && CurrencyFilter.equals(this.currency, currencyFilter.currency) && this.from == currencyFilter.from && this.to == currencyFilter.to && this.tenderOnly == currencyFilter.tenderOnly;
            return bl;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof CurrencyFilter && this.equals((CurrencyFilter)object);
            return bl;
        }

        public int hashCode() {
            int n = 0;
            String string = this.region;
            if (string != null) {
                n = string.hashCode();
            }
            string = this.currency;
            int n2 = n;
            if (string != null) {
                n2 = n * 31 + string.hashCode();
            }
            long l = this.from;
            n = (int)l;
            int n3 = (int)(l >>> 32);
            l = this.to;
            return ((((n2 * 31 + n) * 31 + n3) * 31 + (int)l) * 31 + (int)(l >>> 32)) * 31 + this.tenderOnly;
        }

        public String toString() {
            return CurrencyMetaInfo.debugString(this);
        }

        public CurrencyFilter withCurrency(String string) {
            return new CurrencyFilter(this.region, string, this.from, this.to, this.tenderOnly);
        }

        public CurrencyFilter withDate(long l) {
            return new CurrencyFilter(this.region, this.currency, l, l, this.tenderOnly);
        }

        public CurrencyFilter withDate(Date date) {
            return new CurrencyFilter(this.region, this.currency, date.getTime(), date.getTime(), this.tenderOnly);
        }

        public CurrencyFilter withDateRange(long l, long l2) {
            return new CurrencyFilter(this.region, this.currency, l, l2, this.tenderOnly);
        }

        public CurrencyFilter withDateRange(Date date, Date date2) {
            long l = date == null ? Long.MIN_VALUE : date.getTime();
            long l2 = date2 == null ? Long.MAX_VALUE : date2.getTime();
            return new CurrencyFilter(this.region, this.currency, l, l2, this.tenderOnly);
        }

        public CurrencyFilter withRegion(String string) {
            return new CurrencyFilter(string, this.currency, this.from, this.to, this.tenderOnly);
        }

        public CurrencyFilter withTender() {
            return new CurrencyFilter(this.region, this.currency, this.from, this.to, true);
        }
    }

    public static final class CurrencyInfo {
        public final String code;
        public final long from;
        public final int priority;
        public final String region;
        private final boolean tender;
        public final long to;

        @Deprecated
        public CurrencyInfo(String string, String string2, long l, long l2, int n) {
            this(string, string2, l, l2, n, true);
        }

        @Deprecated
        public CurrencyInfo(String string, String string2, long l, long l2, int n, boolean bl) {
            this.region = string;
            this.code = string2;
            this.from = l;
            this.to = l2;
            this.priority = n;
            this.tender = bl;
        }

        public boolean isTender() {
            return this.tender;
        }

        public String toString() {
            return CurrencyMetaInfo.debugString(this);
        }
    }

}

