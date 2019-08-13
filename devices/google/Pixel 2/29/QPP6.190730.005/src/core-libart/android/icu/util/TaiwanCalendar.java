/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class TaiwanCalendar
extends GregorianCalendar {
    public static final int BEFORE_MINGUO = 0;
    private static final int GREGORIAN_EPOCH = 1970;
    public static final int MINGUO = 1;
    private static final int Taiwan_ERA_START = 1911;
    private static final long serialVersionUID = 2583005278132380631L;

    public TaiwanCalendar() {
    }

    public TaiwanCalendar(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public TaiwanCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n4, n5, n6);
    }

    public TaiwanCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public TaiwanCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
    }

    public TaiwanCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
    }

    public TaiwanCalendar(ULocale uLocale) {
        super(uLocale);
    }

    public TaiwanCalendar(Date date) {
        this();
        this.setTime(date);
    }

    public TaiwanCalendar(Locale locale) {
        super(locale);
    }

    @Override
    public String getType() {
        return "roc";
    }

    @Override
    protected void handleComputeFields(int n) {
        super.handleComputeFields(n);
        n = this.internalGet(19) - 1911;
        if (n > 0) {
            this.internalSet(0, 1);
            this.internalSet(1, n);
        } else {
            this.internalSet(0, 0);
            this.internalSet(1, 1 - n);
        }
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 && this.newerField(19, 0) == 19 ? this.internalGet(19, 1970) : (this.internalGet(0, 1) == 1 ? this.internalGet(1, 1) + 1911 : 1 - this.internalGet(1, 1) + 1911);
        return n;
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        if (n == 0) {
            return n2 != 0 && n2 != 1;
            {
            }
        }
        return super.handleGetLimit(n, n2);
    }
}

