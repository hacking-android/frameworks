/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.CECalendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public final class CopticCalendar
extends CECalendar {
    public static final int AMSHIR = 5;
    public static final int BABA = 1;
    public static final int BARAMHAT = 6;
    public static final int BARAMOUDA = 7;
    public static final int BASHANS = 8;
    private static final int BCE = 0;
    private static final int CE = 1;
    public static final int EPEP = 10;
    public static final int HATOR = 2;
    private static final int JD_EPOCH_OFFSET = 1824665;
    public static final int KIAHK = 3;
    public static final int MESRA = 11;
    public static final int NASIE = 12;
    public static final int PAONA = 9;
    public static final int TOBA = 4;
    public static final int TOUT = 0;
    private static final long serialVersionUID = 5903818751846742911L;

    public CopticCalendar() {
    }

    public CopticCalendar(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public CopticCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n4, n5, n6);
    }

    public CopticCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public CopticCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
    }

    public CopticCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
    }

    public CopticCalendar(ULocale uLocale) {
        super(uLocale);
    }

    public CopticCalendar(Date date) {
        super(date);
    }

    public CopticCalendar(Locale locale) {
        super(locale);
    }

    public static int copticToJD(long l, int n, int n2) {
        return CopticCalendar.ceToJD(l, n, n2, 1824665);
    }

    @Deprecated
    @Override
    protected int getJDEpochOffset() {
        return 1824665;
    }

    @Override
    public String getType() {
        return "coptic";
    }

    @Deprecated
    @Override
    protected void handleComputeFields(int n) {
        int n2;
        int[] arrn = new int[3];
        CopticCalendar.jdToCE(n, this.getJDEpochOffset(), arrn);
        if (arrn[0] <= 0) {
            n = 0;
            n2 = 1 - arrn[0];
        } else {
            n = 1;
            n2 = arrn[0];
        }
        this.internalSet(19, arrn[0]);
        this.internalSet(0, n);
        this.internalSet(1, n2);
        this.internalSet(2, arrn[1]);
        this.internalSet(5, arrn[2]);
        this.internalSet(6, arrn[1] * 30 + arrn[2]);
    }

    @Deprecated
    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : (this.internalGet(0, 1) == 0 ? 1 - this.internalGet(1, 1) : this.internalGet(1, 1));
        return n;
    }
}

