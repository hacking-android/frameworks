/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.InternalErrorHandler;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class SIPDate
implements Cloneable,
Serializable {
    public static final String APR = "Apr";
    public static final String AUG = "Aug";
    public static final String DEC = "Dec";
    public static final String FEB = "Feb";
    public static final String FRI = "Fri";
    public static final String GMT = "GMT";
    public static final String JAN = "Jan";
    public static final String JUL = "Jul";
    public static final String JUN = "Jun";
    public static final String MAR = "Mar";
    public static final String MAY = "May";
    public static final String MON = "Mon";
    public static final String NOV = "Nov";
    public static final String OCT = "Oct";
    public static final String SAT = "Sat";
    public static final String SEP = "Sep";
    public static final String SUN = "Sun";
    public static final String THU = "Thu";
    public static final String TUE = "Tue";
    public static final String WED = "Wed";
    private static final long serialVersionUID = 8544101899928346909L;
    protected int day;
    protected int hour;
    private Calendar javaCal;
    protected int minute;
    protected int month;
    protected int second;
    protected String sipMonth;
    protected String sipWkDay;
    protected int wkday;
    protected int year;

    public SIPDate() {
        this.wkday = -1;
        this.day = -1;
        this.month = -1;
        this.year = -1;
        this.hour = -1;
        this.minute = -1;
        this.second = -1;
        this.javaCal = null;
    }

    public SIPDate(long l) {
        this.javaCal = new GregorianCalendar(TimeZone.getTimeZone("GMT:0"), Locale.getDefault());
        Serializable serializable = new Date(l);
        this.javaCal.setTime((Date)serializable);
        this.wkday = this.javaCal.get(7);
        switch (this.wkday) {
            default: {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("No date map for wkday ");
                ((StringBuilder)serializable).append(this.wkday);
                InternalErrorHandler.handleException(((StringBuilder)serializable).toString());
                break;
            }
            case 7: {
                this.sipWkDay = SAT;
                break;
            }
            case 6: {
                this.sipWkDay = FRI;
                break;
            }
            case 5: {
                this.sipWkDay = THU;
                break;
            }
            case 4: {
                this.sipWkDay = WED;
                break;
            }
            case 3: {
                this.sipWkDay = TUE;
                break;
            }
            case 2: {
                this.sipWkDay = MON;
                break;
            }
            case 1: {
                this.sipWkDay = SUN;
            }
        }
        this.day = this.javaCal.get(5);
        this.month = this.javaCal.get(2);
        switch (this.month) {
            default: {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("No date map for month ");
                ((StringBuilder)serializable).append(this.month);
                InternalErrorHandler.handleException(((StringBuilder)serializable).toString());
                break;
            }
            case 11: {
                this.sipMonth = DEC;
                break;
            }
            case 10: {
                this.sipMonth = NOV;
                break;
            }
            case 9: {
                this.sipMonth = OCT;
                break;
            }
            case 8: {
                this.sipMonth = SEP;
                break;
            }
            case 7: {
                this.sipMonth = AUG;
                break;
            }
            case 6: {
                this.sipMonth = JUL;
                break;
            }
            case 5: {
                this.sipMonth = JUN;
                break;
            }
            case 4: {
                this.sipMonth = MAY;
                break;
            }
            case 3: {
                this.sipMonth = APR;
                break;
            }
            case 2: {
                this.sipMonth = MAR;
                break;
            }
            case 1: {
                this.sipMonth = FEB;
                break;
            }
            case 0: {
                this.sipMonth = JAN;
            }
        }
        this.year = this.javaCal.get(1);
        this.hour = this.javaCal.get(11);
        this.minute = this.javaCal.get(12);
        this.second = this.javaCal.get(13);
    }

    private void setJavaCal() {
        this.javaCal = new GregorianCalendar(TimeZone.getTimeZone("GMT:0"), Locale.getDefault());
        int n = this.year;
        if (n != -1) {
            this.javaCal.set(1, n);
        }
        if ((n = this.day) != -1) {
            this.javaCal.set(5, n);
        }
        if ((n = this.month) != -1) {
            this.javaCal.set(2, n);
        }
        if ((n = this.wkday) != -1) {
            this.javaCal.set(7, n);
        }
        if ((n = this.hour) != -1) {
            this.javaCal.set(10, n);
        }
        if ((n = this.minute) != -1) {
            this.javaCal.set(12, n);
        }
        if ((n = this.second) != -1) {
            this.javaCal.set(13, n);
        }
    }

    public Object clone() {
        SIPDate sIPDate;
        block2 : {
            Calendar calendar;
            try {
                sIPDate = (SIPDate)super.clone();
                calendar = this.javaCal;
                if (calendar == null) break block2;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new RuntimeException("Internal error");
            }
            sIPDate.javaCal = (Calendar)calendar.clone();
        }
        return sIPDate;
    }

    public String encode() {
        CharSequence charSequence;
        CharSequence charSequence2;
        CharSequence charSequence3;
        CharSequence charSequence4;
        if (this.day < 10) {
            charSequence = new StringBuilder();
            charSequence.append("0");
            charSequence.append(this.day);
            charSequence3 = charSequence.toString();
        } else {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(this.day);
            charSequence3 = charSequence.toString();
        }
        if (this.hour < 10) {
            charSequence = new StringBuilder();
            charSequence.append("0");
            charSequence.append(this.hour);
            charSequence = charSequence.toString();
        } else {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(this.hour);
            charSequence = charSequence.toString();
        }
        if (this.minute < 10) {
            charSequence4 = new StringBuilder();
            charSequence4.append("0");
            charSequence4.append(this.minute);
            charSequence4 = charSequence4.toString();
        } else {
            charSequence4 = new StringBuilder();
            charSequence4.append("");
            charSequence4.append(this.minute);
            charSequence4 = charSequence4.toString();
        }
        if (this.second < 10) {
            charSequence2 = new StringBuilder();
            charSequence2.append("0");
            charSequence2.append(this.second);
            charSequence2 = charSequence2.toString();
        } else {
            charSequence2 = new StringBuilder();
            charSequence2.append("");
            charSequence2.append(this.second);
            charSequence2 = charSequence2.toString();
        }
        CharSequence charSequence5 = "";
        if (this.sipWkDay != null) {
            charSequence5 = new StringBuilder();
            ((StringBuilder)charSequence5).append("");
            ((StringBuilder)charSequence5).append(this.sipWkDay);
            ((StringBuilder)charSequence5).append(",");
            ((StringBuilder)charSequence5).append(" ");
            charSequence5 = ((StringBuilder)charSequence5).toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence5);
        stringBuilder.append((String)charSequence3);
        stringBuilder.append(" ");
        charSequence3 = charSequence5 = stringBuilder.toString();
        if (this.sipMonth != null) {
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append((String)charSequence5);
            ((StringBuilder)charSequence3).append(this.sipMonth);
            ((StringBuilder)charSequence3).append(" ");
            charSequence3 = ((StringBuilder)charSequence3).toString();
        }
        charSequence5 = new StringBuilder();
        ((StringBuilder)charSequence5).append((String)charSequence3);
        ((StringBuilder)charSequence5).append(this.year);
        ((StringBuilder)charSequence5).append(" ");
        ((StringBuilder)charSequence5).append((String)charSequence);
        ((StringBuilder)charSequence5).append(":");
        ((StringBuilder)charSequence5).append((String)charSequence4);
        ((StringBuilder)charSequence5).append(":");
        ((StringBuilder)charSequence5).append((String)charSequence2);
        ((StringBuilder)charSequence5).append(" ");
        ((StringBuilder)charSequence5).append(GMT);
        return ((StringBuilder)charSequence5).toString();
    }

    public boolean equals(Object object) {
        Class<?> class_ = object.getClass();
        Class<?> class_2 = this.getClass();
        boolean bl = false;
        if (class_ != class_2) {
            return false;
        }
        object = (SIPDate)object;
        boolean bl2 = bl;
        if (this.wkday == ((SIPDate)object).wkday) {
            bl2 = bl;
            if (this.day == ((SIPDate)object).day) {
                bl2 = bl;
                if (this.month == ((SIPDate)object).month) {
                    bl2 = bl;
                    if (this.year == ((SIPDate)object).year) {
                        bl2 = bl;
                        if (this.hour == ((SIPDate)object).hour) {
                            bl2 = bl;
                            if (this.minute == ((SIPDate)object).minute) {
                                bl2 = bl;
                                if (this.second == ((SIPDate)object).second) {
                                    bl2 = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public int getDeltaSeconds() {
        return (int)(this.getJavaCal().getTime().getTime() - System.currentTimeMillis()) / 1000;
    }

    public int getHour() {
        return this.hour;
    }

    public Calendar getJavaCal() {
        if (this.javaCal == null) {
            this.setJavaCal();
        }
        return this.javaCal;
    }

    public int getMinute() {
        return this.minute;
    }

    public String getMonth() {
        return this.sipMonth;
    }

    public int getSecond() {
        return this.second;
    }

    public String getWkday() {
        return this.sipWkDay;
    }

    public int getYear() {
        return this.year;
    }

    public void setDay(int n) throws IllegalArgumentException {
        if (n >= 1 && n <= 31) {
            this.day = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal Day of the month ");
        stringBuilder.append(Integer.toString(n));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setHour(int n) throws IllegalArgumentException {
        if (n >= 0 && n <= 24) {
            this.javaCal = null;
            this.hour = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal hour : ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setMinute(int n) throws IllegalArgumentException {
        if (n >= 0 && n < 60) {
            this.javaCal = null;
            this.minute = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal minute : ");
        stringBuilder.append(Integer.toString(n));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setMonth(String string) throws IllegalArgumentException {
        block14 : {
            block3 : {
                block13 : {
                    block12 : {
                        block11 : {
                            block10 : {
                                block9 : {
                                    block8 : {
                                        block7 : {
                                            block6 : {
                                                block5 : {
                                                    block4 : {
                                                        block2 : {
                                                            this.sipMonth = string;
                                                            if (this.sipMonth.compareToIgnoreCase(JAN) != 0) break block2;
                                                            this.month = 0;
                                                            break block3;
                                                        }
                                                        if (this.sipMonth.compareToIgnoreCase(FEB) != 0) break block4;
                                                        this.month = 1;
                                                        break block3;
                                                    }
                                                    if (this.sipMonth.compareToIgnoreCase(MAR) != 0) break block5;
                                                    this.month = 2;
                                                    break block3;
                                                }
                                                if (this.sipMonth.compareToIgnoreCase(APR) != 0) break block6;
                                                this.month = 3;
                                                break block3;
                                            }
                                            if (this.sipMonth.compareToIgnoreCase(MAY) != 0) break block7;
                                            this.month = 4;
                                            break block3;
                                        }
                                        if (this.sipMonth.compareToIgnoreCase(JUN) != 0) break block8;
                                        this.month = 5;
                                        break block3;
                                    }
                                    if (this.sipMonth.compareToIgnoreCase(JUL) != 0) break block9;
                                    this.month = 6;
                                    break block3;
                                }
                                if (this.sipMonth.compareToIgnoreCase(AUG) != 0) break block10;
                                this.month = 7;
                                break block3;
                            }
                            if (this.sipMonth.compareToIgnoreCase(SEP) != 0) break block11;
                            this.month = 8;
                            break block3;
                        }
                        if (this.sipMonth.compareToIgnoreCase(OCT) != 0) break block12;
                        this.month = 9;
                        break block3;
                    }
                    if (this.sipMonth.compareToIgnoreCase(NOV) != 0) break block13;
                    this.month = 10;
                    break block3;
                }
                if (this.sipMonth.compareToIgnoreCase(DEC) != 0) break block14;
                this.month = 11;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal Month :");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setSecond(int n) throws IllegalArgumentException {
        if (n >= 0 && n < 60) {
            this.javaCal = null;
            this.second = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal second : ");
        stringBuilder.append(Integer.toString(n));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setWkday(String string) throws IllegalArgumentException {
        block9 : {
            block3 : {
                block8 : {
                    block7 : {
                        block6 : {
                            block5 : {
                                block4 : {
                                    block2 : {
                                        this.sipWkDay = string;
                                        if (this.sipWkDay.compareToIgnoreCase(MON) != 0) break block2;
                                        this.wkday = 2;
                                        break block3;
                                    }
                                    if (this.sipWkDay.compareToIgnoreCase(TUE) != 0) break block4;
                                    this.wkday = 3;
                                    break block3;
                                }
                                if (this.sipWkDay.compareToIgnoreCase(WED) != 0) break block5;
                                this.wkday = 4;
                                break block3;
                            }
                            if (this.sipWkDay.compareToIgnoreCase(THU) != 0) break block6;
                            this.wkday = 5;
                            break block3;
                        }
                        if (this.sipWkDay.compareToIgnoreCase(FRI) != 0) break block7;
                        this.wkday = 6;
                        break block3;
                    }
                    if (this.sipWkDay.compareToIgnoreCase(SAT) != 0) break block8;
                    this.wkday = 7;
                    break block3;
                }
                if (this.sipWkDay.compareToIgnoreCase(SUN) != 0) break block9;
                this.wkday = 1;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal Week day :");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setYear(int n) throws IllegalArgumentException {
        if (n >= 0) {
            this.javaCal = null;
            this.year = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal year : ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

