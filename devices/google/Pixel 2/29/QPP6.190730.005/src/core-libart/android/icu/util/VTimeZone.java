/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Grego;
import android.icu.util.AnnualTimeZoneRule;
import android.icu.util.BasicTimeZone;
import android.icu.util.DateTimeRule;
import android.icu.util.InitialTimeZoneRule;
import android.icu.util.RuleBasedTimeZone;
import android.icu.util.TimeArrayTimeZoneRule;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.TimeZoneTransition;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

public class VTimeZone
extends BasicTimeZone {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String COLON = ":";
    private static final String COMMA = ",";
    private static final int DEF_DSTSAVINGS = 3600000;
    private static final long DEF_TZSTARTTIME = 0L;
    private static final String EQUALS_SIGN = "=";
    private static final int ERR = 3;
    private static final String ICAL_BEGIN = "BEGIN";
    private static final String ICAL_BEGIN_VTIMEZONE = "BEGIN:VTIMEZONE";
    private static final String ICAL_BYDAY = "BYDAY";
    private static final String ICAL_BYMONTH = "BYMONTH";
    private static final String ICAL_BYMONTHDAY = "BYMONTHDAY";
    private static final String ICAL_DAYLIGHT = "DAYLIGHT";
    private static final String[] ICAL_DOW_NAMES = new String[]{"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
    private static final String ICAL_DTSTART = "DTSTART";
    private static final String ICAL_END = "END";
    private static final String ICAL_END_VTIMEZONE = "END:VTIMEZONE";
    private static final String ICAL_FREQ = "FREQ";
    private static final String ICAL_LASTMOD = "LAST-MODIFIED";
    private static final String ICAL_RDATE = "RDATE";
    private static final String ICAL_RRULE = "RRULE";
    private static final String ICAL_STANDARD = "STANDARD";
    private static final String ICAL_TZID = "TZID";
    private static final String ICAL_TZNAME = "TZNAME";
    private static final String ICAL_TZOFFSETFROM = "TZOFFSETFROM";
    private static final String ICAL_TZOFFSETTO = "TZOFFSETTO";
    private static final String ICAL_TZURL = "TZURL";
    private static final String ICAL_UNTIL = "UNTIL";
    private static final String ICAL_VTIMEZONE = "VTIMEZONE";
    private static final String ICAL_YEARLY = "YEARLY";
    private static final String ICU_TZINFO_PROP = "X-TZINFO";
    private static String ICU_TZVERSION;
    private static final int INI = 0;
    private static final long MAX_TIME = Long.MAX_VALUE;
    private static final long MIN_TIME = Long.MIN_VALUE;
    private static final int[] MONTHLENGTH;
    private static final String NEWLINE = "\r\n";
    private static final String SEMICOLON = ";";
    private static final int TZI = 2;
    private static final int VTZ = 1;
    private static final long serialVersionUID = -6851467294127795902L;
    private volatile transient boolean isFrozen = false;
    private Date lastmod = null;
    private String olsonzid = null;
    private BasicTimeZone tz;
    private String tzurl = null;
    private List<String> vtzlines;

    static {
        MONTHLENGTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        try {
            ICU_TZVERSION = TimeZone.getTZDataVersion();
        }
        catch (MissingResourceException missingResourceException) {
            ICU_TZVERSION = null;
        }
    }

    private VTimeZone() {
    }

    private VTimeZone(String string) {
        super(string);
    }

    private static void appendUNTIL(Writer writer, String string) throws IOException {
        if (string != null) {
            writer.write(SEMICOLON);
            writer.write(ICAL_UNTIL);
            writer.write(EQUALS_SIGN);
            writer.write(string);
        }
    }

    private static void beginRRULE(Writer writer, int n) throws IOException {
        writer.write(ICAL_RRULE);
        writer.write(COLON);
        writer.write(ICAL_FREQ);
        writer.write(EQUALS_SIGN);
        writer.write(ICAL_YEARLY);
        writer.write(SEMICOLON);
        writer.write(ICAL_BYMONTH);
        writer.write(EQUALS_SIGN);
        writer.write(Integer.toString(n + 1));
        writer.write(SEMICOLON);
    }

    private static void beginZoneProps(Writer writer, boolean bl, String string, int n, int n2, long l) throws IOException {
        writer.write(ICAL_BEGIN);
        writer.write(COLON);
        if (bl) {
            writer.write(ICAL_DAYLIGHT);
        } else {
            writer.write(ICAL_STANDARD);
        }
        writer.write(NEWLINE);
        writer.write(ICAL_TZOFFSETTO);
        writer.write(COLON);
        writer.write(VTimeZone.millisToOffset(n2));
        writer.write(NEWLINE);
        writer.write(ICAL_TZOFFSETFROM);
        writer.write(COLON);
        writer.write(VTimeZone.millisToOffset(n));
        writer.write(NEWLINE);
        writer.write(ICAL_TZNAME);
        writer.write(COLON);
        writer.write(string);
        writer.write(NEWLINE);
        writer.write(ICAL_DTSTART);
        writer.write(COLON);
        writer.write(VTimeZone.getDateTimeString((long)n + l));
        writer.write(NEWLINE);
    }

    public static VTimeZone create(Reader reader) {
        VTimeZone vTimeZone = new VTimeZone();
        if (vTimeZone.load(reader)) {
            return vTimeZone;
        }
        return null;
    }

    public static VTimeZone create(String object) {
        BasicTimeZone basicTimeZone = TimeZone.getFrozenICUTimeZone((String)object, true);
        if (basicTimeZone == null) {
            return null;
        }
        object = new VTimeZone((String)object);
        ((VTimeZone)object).tz = (BasicTimeZone)basicTimeZone.cloneAsThawed();
        ((VTimeZone)object).olsonzid = ((VTimeZone)object).tz.getID();
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static TimeZoneRule createRuleByRDATE(String string, int n, int n2, long l, List<String> object, int n3) {
        Iterator iterator;
        int n4;
        long[] arrl;
        void var5_11;
        block6 : {
            if (object != null && object.size() != 0) {
                arrl = new long[object.size()];
                n4 = 0;
                try {
                    iterator = object.iterator();
                    break block6;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
                return null;
            }
            long[] arrl2 = new long[]{l};
            return new TimeArrayTimeZoneRule(string, n, n2, (long[])var5_11, 2);
        }
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            try {
                void var6_12;
                arrl[n4] = VTimeZone.parseDateTimeString(string2, (int)var6_12);
                ++n4;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        long[] arrl3 = arrl;
        return new TimeArrayTimeZoneRule(string, n, n2, (long[])var5_11, 2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static TimeZoneRule createRuleByRRULE(String var0, int var1_1, int var2_2, long var3_3, List<String> var5_4, int var6_5) {
        block37 : {
            block36 : {
                block34 : {
                    block35 : {
                        var7_16 /* !! */  = var5_4;
                        if (var7_16 /* !! */  == null) return null;
                        if (var5_4.size() == 0) {
                            return null;
                        }
                        var8_17 = (String)var7_16 /* !! */ .get(0);
                        var7_16 /* !! */  = new long[1];
                        var8_18 = VTimeZone.parseRRULE(var8_17, var7_16 /* !! */ );
                        if (var8_18 == null) {
                            return null;
                        }
                        var9_24 = var8_18[0];
                        var10_25 = var8_18[1];
                        var11_26 = var8_18[2];
                        var12_27 = var8_18[3];
                        if (var5_4.size() != 1) break block34;
                        if (var8_18.length <= 4) break block35;
                        if (var8_18.length != 10) return null;
                        if (var9_24 == -1) return null;
                        if (var10_25 == 0) {
                            return null;
                        }
                        var12_27 = 31;
                        var5_5 = new int[7];
                        break block36;
                    }
                    var5_7 = null;
                    var13_28 = var9_24;
                    ** GOTO lbl128
                }
                if (var9_24 == -1) return null;
                if (var10_25 == 0) return null;
                if (var12_27 == 0) {
                    return null;
                }
                if (var5_4.size() > 7) {
                    return null;
                }
                var14_29 = var9_24;
                var17_33 = var8_18.length - 3;
                var12_27 = 31;
                break block37;
            }
            for (var13_28 = 0; var13_28 < 7; ++var13_28) {
                var5_5[var13_28] = var8_18[var13_28 + 3];
                var14_29 = var5_5[var13_28] > 0 ? var5_5[var13_28] : VTimeZone.MONTHLENGTH[var9_24] + var5_5[var13_28] + 1;
                var5_5[var13_28] = var14_29;
                if (var5_5[var13_28] >= var12_27) continue;
                var12_27 = var5_5[var13_28];
            }
            var13_28 = 1;
            do {
                block38 : {
                    if (var13_28 < 7) break block38;
                    var5_6 = null;
                    var13_28 = var9_24;
                    ** GOTO lbl128
                }
                var15_30 = 0;
                var14_29 = 0;
                do {
                    var16_32 = var15_30;
                    if (var14_29 >= 7) break;
                    if (var5_5[var14_29] == var12_27 + var13_28) {
                        var16_32 = 1;
                        break;
                    }
                    ++var14_29;
                } while (true);
                if (var16_32 == 0) {
                    return null;
                }
                ++var13_28;
            } while (true);
        }
        for (var16_32 = 0; var16_32 < var17_33; ++var16_32) {
            var13_28 = var8_18[var16_32 + 3];
            if (var13_28 <= 0) {
                var13_28 = VTimeZone.MONTHLENGTH[var9_24] + var13_28 + 1;
            }
            if (var13_28 >= var12_27) continue;
            var12_27 = var13_28;
        }
        var15_31 = -1;
        var18_34 = 1;
        var13_28 = var14_29;
        do {
            block40 : {
                block41 : {
                    block39 : {
                        if (var18_34 >= var5_4.size()) break block39;
                        var8_20 = (String)var5_4.get(var18_34);
                        var19_35 = new long[1];
                        var20_36 = VTimeZone.parseRRULE(var8_20, var19_35);
                        var8_21 = var7_16 /* !! */ ;
                        if (var19_35[0] > var7_16 /* !! */ [0]) {
                            var8_22 = var19_35;
                        }
                        if (var20_36[0] == -1) return null;
                        if (var20_36[1] == 0) return null;
                        if (var20_36[3] == 0) {
                            return null;
                        }
                        var21_37 = var20_36.length - 3;
                        if (var17_33 + var21_37 > 7) {
                            return null;
                        }
                        if (var20_36[1] != var10_25) {
                            return null;
                        }
                        var22_38 = var13_28;
                        var16_32 = var12_27;
                        var23_39 = var15_31;
                        if (var20_36[0] != var9_24) {
                            if (var15_31 == -1) {
                                var14_29 = var20_36[0] - var9_24;
                                if (var14_29 != -11 && var14_29 != -1) {
                                    if (var14_29 != 11) {
                                        if (var14_29 != 1) return null;
                                    }
                                    var14_29 = var20_36[0];
                                    var22_38 = var13_28;
                                    var16_32 = var12_27;
                                } else {
                                    var22_38 = var14_29 = var20_36[0];
                                    var16_32 = 31;
                                }
                            } else {
                                var22_38 = var13_28;
                                var16_32 = var12_27;
                                var14_29 = var15_31;
                                if (var20_36[0] != var9_24) {
                                    var22_38 = var13_28;
                                    var16_32 = var12_27;
                                    var14_29 = var15_31;
                                    if (var20_36[0] != var15_31) {
                                        return null;
                                    }
                                }
                            }
                            var23_39 = var14_29;
                        }
                        var14_29 = var16_32;
                        if (var20_36[0] != var22_38) break block40;
                        break block41;
                    }
                    var5_8 = null;
                    if (var17_33 != 7) {
                        return var5_8;
                    }
lbl128: // 4 sources:
                    var5_10 = Grego.timeToFields(var3_3 + (long)var6_15, (int[])var5_9);
                    var14_29 = var5_10[0];
                    var6_15 = var13_28 == -1 ? var5_10[1] : var13_28;
                    if (var10_25 == 0 && var11_26 == 0 && var12_27 == 0) {
                        var12_27 = var5_10[2];
                    }
                    var16_32 = var5_10[5];
                    if (var7_16 /* !! */ [0] != Long.MIN_VALUE) {
                        Grego.timeToFields(var7_16 /* !! */ [0], var5_10);
                        var13_28 = var5_10[0];
                    } else {
                        var13_28 = Integer.MAX_VALUE;
                    }
                    if (var10_25 == 0 && var11_26 == 0 && var12_27 != 0) {
                        var5_11 = new DateTimeRule(var6_15, var12_27, var16_32, 0);
                        return new AnnualTimeZoneRule(var0, var1_1, var2_2, (DateTimeRule)var5_14, var14_29, var13_28);
                    }
                    if (var10_25 != 0 && var11_26 != 0 && var12_27 == 0) {
                        var5_12 = new DateTimeRule(var6_15, var11_26, var10_25, var16_32, 0);
                        return new AnnualTimeZoneRule(var0, var1_1, var2_2, (DateTimeRule)var5_14, var14_29, var13_28);
                    }
                    if (var10_25 == 0) return null;
                    if (var11_26 != 0) return null;
                    if (var12_27 == 0) return null;
                    var5_13 = new DateTimeRule(var6_15, var12_27, var10_25, true, var16_32, 0);
                    return new AnnualTimeZoneRule(var0, var1_1, var2_2, (DateTimeRule)var5_14, var14_29, var13_28);
                }
                var13_28 = 0;
                var12_27 = var16_32;
                var16_32 = var13_28;
                do {
                    var14_29 = var12_27;
                    if (var16_32 >= var21_37) break;
                    var13_28 = var20_36[var16_32 + 3];
                    if (var13_28 <= 0) {
                        var13_28 = VTimeZone.MONTHLENGTH[var20_36[0]] + var13_28 + 1;
                    }
                    if (var13_28 < var12_27) {
                        var12_27 = var13_28;
                    }
                    ++var16_32;
                } while (true);
            }
            var17_33 += var21_37;
            ++var18_34;
            var13_28 = var22_38;
            var7_16 /* !! */  = var8_23;
            var12_27 = var14_29;
            var15_31 = var23_39;
        } while (true);
    }

    private static void endZoneProps(Writer writer, boolean bl) throws IOException {
        writer.write(ICAL_END);
        writer.write(COLON);
        if (bl) {
            writer.write(ICAL_DAYLIGHT);
        } else {
            writer.write(ICAL_STANDARD);
        }
        writer.write(NEWLINE);
    }

    private static String getDateTimeString(long l) {
        int[] arrn = Grego.timeToFields(l, null);
        StringBuilder stringBuilder = new StringBuilder(15);
        stringBuilder.append(VTimeZone.numToString(arrn[0], 4));
        stringBuilder.append(VTimeZone.numToString(arrn[1] + 1, 2));
        stringBuilder.append(VTimeZone.numToString(arrn[2], 2));
        stringBuilder.append('T');
        int n = arrn[5];
        int n2 = n / 3600000;
        int n3 = n % 3600000;
        n = n3 / 60000;
        n3 = n3 % 60000 / 1000;
        stringBuilder.append(VTimeZone.numToString(n2, 2));
        stringBuilder.append(VTimeZone.numToString(n, 2));
        stringBuilder.append(VTimeZone.numToString(n3, 2));
        return stringBuilder.toString();
    }

    private static String getDefaultTZName(String string, boolean bl) {
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("(DST)");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("(STD)");
        return stringBuilder.toString();
    }

    private static String getUTCDateTimeString(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(VTimeZone.getDateTimeString(l));
        stringBuilder.append("Z");
        return stringBuilder.toString();
    }

    private static boolean isEquivalentDateRule(int n, int n2, int n3, DateTimeRule arrn) {
        if (n == arrn.getRuleMonth() && n3 == arrn.getRuleDayOfWeek()) {
            if (arrn.getTimeRuleType() != 0) {
                return false;
            }
            if (arrn.getDateRuleType() == 1 && arrn.getRuleWeekInMonth() == n2) {
                return true;
            }
            n3 = arrn.getRuleDayOfMonth();
            if (arrn.getDateRuleType() == 2) {
                int[] arrn2;
                if (n3 % 7 == 1 && (n3 + 6) / 7 == n2) {
                    return true;
                }
                if (n != 1 && ((arrn2 = MONTHLENGTH)[n] - n3) % 7 == 6 && n2 == (arrn2[n] - n3 + 1) / 7 * -1) {
                    return true;
                }
            }
            if (arrn.getDateRuleType() == 3) {
                if (n3 % 7 == 0 && n3 / 7 == n2) {
                    return true;
                }
                if (n != 1 && ((arrn = MONTHLENGTH)[n] - n3) % 7 == 0 && n2 == ((arrn[n] - n3) / 7 + 1) * -1) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean load(Reader reader) {
        try {
            Serializable serializable = new LinkedList();
            this.vtzlines = serializable;
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            serializable = new StringBuilder();
            do {
                int n;
                block17 : {
                    block18 : {
                        block16 : {
                            block15 : {
                                if ((n = reader.read()) != -1) break block15;
                                bl = bl3;
                                if (bl2) {
                                    bl = bl3;
                                    if (((StringBuilder)serializable).toString().startsWith(ICAL_END_VTIMEZONE)) {
                                        this.vtzlines.add(((StringBuilder)serializable).toString());
                                        bl = true;
                                    }
                                }
                                break block16;
                            }
                            if (n == 13) continue;
                            if (bl) {
                                if (n != 9 && n != 32) {
                                    if (bl2 && ((StringBuilder)serializable).length() > 0) {
                                        this.vtzlines.add(((StringBuilder)serializable).toString());
                                    }
                                    ((StringBuilder)serializable).setLength(0);
                                    if (n != 10) {
                                        ((StringBuilder)serializable).append((char)n);
                                    }
                                }
                                bl = false;
                                continue;
                            }
                            if (n != 10) break block17;
                            bl = true;
                            if (!bl2) break block18;
                            if (!((StringBuilder)serializable).toString().startsWith(ICAL_END_VTIMEZONE)) continue;
                            this.vtzlines.add(((StringBuilder)serializable).toString());
                            bl = true;
                        }
                        if (!bl) {
                            return false;
                        }
                        return this.parse();
                    }
                    if (!((StringBuilder)serializable).toString().startsWith(ICAL_BEGIN_VTIMEZONE)) continue;
                    this.vtzlines.add(((StringBuilder)serializable).toString());
                    ((StringBuilder)serializable).setLength(0);
                    bl2 = true;
                    bl = false;
                    continue;
                }
                ((StringBuilder)serializable).append((char)n);
            } while (true);
        }
        catch (IOException iOException) {
            return false;
        }
    }

    private static String millisToOffset(int n) {
        StringBuilder stringBuilder = new StringBuilder(7);
        if (n >= 0) {
            stringBuilder.append('+');
        } else {
            stringBuilder.append('-');
            n = -n;
        }
        int n2 = n / 1000;
        n = n2 % 60;
        n2 = (n2 - n) / 60;
        stringBuilder.append(VTimeZone.numToString(n2 / 60, 2));
        stringBuilder.append(VTimeZone.numToString(n2 % 60, 2));
        stringBuilder.append(VTimeZone.numToString(n, 2));
        return stringBuilder.toString();
    }

    private static String numToString(int n, int n2) {
        String string = Integer.toString(n);
        n = string.length();
        if (n >= n2) {
            return string.substring(n - n2, n);
        }
        StringBuilder stringBuilder = new StringBuilder(n2);
        while (n < n2) {
            stringBuilder.append('0');
            ++n;
        }
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private static int offsetStrToMillis(String string) {
        int n;
        int n2;
        int n3;
        boolean bl;
        int n4;
        block8 : {
            int n5;
            int n6;
            block6 : {
                int n7;
                boolean bl2;
                int n8;
                int n9;
                block11 : {
                    char c;
                    block10 : {
                        block9 : {
                            block7 : {
                                bl2 = false;
                                n = 0;
                                n4 = 0;
                                n5 = 0;
                                n2 = 0;
                                n6 = 0;
                                n8 = 0;
                                n7 = 0;
                                if (string != null) break block7;
                                bl = bl2;
                                n3 = n8;
                                break block8;
                            }
                            n9 = string.length();
                            if (n9 == 5 || n9 == 7) break block9;
                            bl = bl2;
                            n3 = n8;
                            break block8;
                        }
                        c = string.charAt(0);
                        if (c != '+') break block10;
                        n = 1;
                        break block11;
                    }
                    bl = bl2;
                    n3 = n8;
                    if (c != '-') break block8;
                    n = -1;
                }
                n4 = n5;
                n2 = n6;
                n4 = n5 = Integer.parseInt(string.substring(1, 3));
                n2 = n6;
                n6 = Integer.parseInt(string.substring(3, 5));
                n3 = n7;
                if (n9 != 7) break block6;
                n4 = n5;
                n2 = n6;
                try {
                    n3 = Integer.parseInt(string.substring(5, 7));
                }
                catch (NumberFormatException numberFormatException) {
                    n3 = n8;
                    bl = bl2;
                }
            }
            bl = true;
            n4 = n5;
            n2 = n6;
        }
        if (bl) {
            return ((n4 * 60 + n2) * 60 + n3) * n * 1000;
        }
        throw new IllegalArgumentException("Bad offset string");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean parse() {
        var1_1 = this.vtzlines;
        if (var1_1 == null) return false;
        if (var1_1.size() == 0) {
            return false;
        }
        var1_2 = null;
        var2_22 = null;
        var3_23 = null;
        var4_28 = null;
        var5_29 = null;
        var6_30 = new ArrayList<Object>();
        var7_31 = 0;
        var8_32 = 0;
        var9_33 = this.vtzlines.iterator();
        var10_34 = Long.MAX_VALUE;
        var12_35 = 0;
        var13_36 = false;
        var14_37 = 0;
        var15_38 = null;
        while (var9_33.hasNext()) {
            block62 : {
                block59 : {
                    block72 : {
                        block71 : {
                            block70 : {
                                block69 : {
                                    block60 : {
                                        block68 : {
                                            block58 : {
                                                block56 : {
                                                    block57 : {
                                                        block55 : {
                                                            block54 : {
                                                                block53 : {
                                                                    block67 : {
                                                                        block66 : {
                                                                            block65 : {
                                                                                block64 : {
                                                                                    block63 : {
                                                                                        block61 : {
                                                                                            var16_39 = var9_33.next();
                                                                                            var17_40 = var16_39.indexOf(":");
                                                                                            if (var17_40 < 0) continue;
                                                                                            var18_41 = var16_39.substring(0, var17_40);
                                                                                            var16_39 = var16_39.substring(var17_40 + 1);
                                                                                            if (var14_37 == 0) break block59;
                                                                                            if (var14_37 == true) break block60;
                                                                                            if (var14_37 != 2) ** GOTO lbl-1000
                                                                                            if (!var18_41.equals("DTSTART")) break block61;
                                                                                            var4_28 = var16_39;
                                                                                            var17_40 = var12_35;
                                                                                            break block62;
                                                                                        }
                                                                                        if (!var18_41.equals("TZNAME")) break block63;
                                                                                        var3_23 = var16_39;
                                                                                        var17_40 = var12_35;
                                                                                        break block62;
                                                                                    }
                                                                                    if (!var18_41.equals("TZOFFSETFROM")) break block64;
                                                                                    var1_4 = var16_39;
                                                                                    var17_40 = var12_35;
                                                                                    break block62;
                                                                                }
                                                                                if (!var18_41.equals("TZOFFSETTO")) break block65;
                                                                                var2_22 = var16_39;
                                                                                var17_40 = var12_35;
                                                                                break block62;
                                                                            }
                                                                            if (!var18_41.equals("RDATE")) break block66;
                                                                            if (var12_35 != 0) {
                                                                                var14_37 = 3;
                                                                                var17_40 = var12_35;
                                                                            } else {
                                                                                var19_42 = var5_29;
                                                                                if (var5_29 == null) {
                                                                                    var19_42 = new LinkedList<Object>();
                                                                                }
                                                                                var5_29 = new StringTokenizer((String)var16_39, ",");
                                                                                while (var5_29.hasMoreTokens()) {
                                                                                    var19_42.add(var5_29.nextToken());
                                                                                }
                                                                                var5_29 = var19_42;
                                                                                var17_40 = var12_35;
                                                                            }
                                                                            break block62;
                                                                        }
                                                                        if (!var18_41.equals("RRULE")) break block67;
                                                                        if (var12_35 == 0 && var5_29 != null) {
                                                                            var14_37 = 3;
                                                                            var17_40 = var12_35;
                                                                        } else {
                                                                            var19_42 = var5_29;
                                                                            if (var5_29 == null) {
                                                                                var19_42 = new LinkedList<E>();
                                                                            }
                                                                            var19_42.add(var16_39);
                                                                            var17_40 = 1;
                                                                            var5_29 = var19_42;
                                                                        }
                                                                        break block62;
                                                                    }
                                                                    if (!var18_41.equals("END")) ** GOTO lbl-1000
                                                                    if (var4_28 == null || var1_3 == null || var2_22 == null) break block68;
                                                                    var19_42 = var3_23;
                                                                    if (var3_23 == null) {
                                                                        var19_42 = VTimeZone.getDefaultTZName(var15_38, var13_36);
                                                                    }
                                                                    var16_39 = null;
                                                                    var14_37 = VTimeZone.offsetStrToMillis((String)var1_3);
                                                                    var20_43 = VTimeZone.offsetStrToMillis((String)var2_22);
                                                                    if (var13_36) {
                                                                        if (var20_43 - var14_37 > 0) {
                                                                            var17_40 = var14_37;
                                                                            var21_44 = var20_43 - var14_37;
                                                                        } else {
                                                                            var17_40 = var20_43 - 3600000;
                                                                            var21_44 = 3600000;
                                                                        }
                                                                        break block53;
                                                                    }
                                                                    var17_40 = var20_43;
                                                                    var21_44 = 0;
                                                                }
                                                                try {
                                                                    var22_45 = VTimeZone.parseDateTimeString((String)var4_28, var14_37);
                                                                    if (var12_35 == 0) break block54;
                                                                }
                                                                catch (IllegalArgumentException var3_26) {}
                                                                try {
                                                                    var16_39 = var3_23 = VTimeZone.createRuleByRRULE((String)var19_42, var17_40, var21_44, var22_45, (List<String>)var5_29, var14_37);
                                                                    break block55;
                                                                }
                                                                catch (IllegalArgumentException var3_24) {
                                                                    break block56;
                                                                }
                                                            }
                                                            var16_39 = var3_23 = VTimeZone.createRuleByRDATE((String)var19_42, var17_40, var21_44, var22_45, (List<String>)var5_29, var14_37);
                                                        }
                                                        if (var16_39 != null) {
                                                            try {
                                                                var3_23 = var16_39.getFirstStart(var14_37, 0);
                                                                if (var3_23.getTime() >= var10_34) break block57;
                                                                var22_45 = var3_23.getTime();
                                                                if (var21_44 > 0) {
                                                                    var10_34 = var22_45;
                                                                    var8_32 = 0;
                                                                } else if (var14_37 - var20_43 == 3600000) {
                                                                    var14_37 -= 3600000;
                                                                    var8_32 = 3600000;
                                                                    var10_34 = var22_45;
                                                                } else {
                                                                    var8_32 = 0;
                                                                    var10_34 = var22_45;
                                                                }
                                                                break block58;
                                                            }
                                                            catch (IllegalArgumentException var3_25) {
                                                                break block56;
                                                            }
                                                        }
                                                    }
                                                    var14_37 = var7_31;
                                                    break block58;
                                                    break block56;
                                                    catch (IllegalArgumentException var3_27) {
                                                        // empty catch block
                                                    }
                                                }
                                                var14_37 = var7_31;
                                            }
                                            if (var16_39 == null) {
                                                var17_40 = 3;
                                                var7_31 = var14_37;
                                                var14_37 = var17_40;
                                                var3_23 = var19_42;
                                                var17_40 = var12_35;
                                            } else {
                                                var6_30.add(var16_39);
                                                var17_40 = 1;
                                                var7_31 = var14_37;
                                                var14_37 = var17_40;
                                                var3_23 = var19_42;
                                                var17_40 = var12_35;
                                            }
                                            break block62;
                                        }
                                        var14_37 = 3;
                                        var17_40 = var12_35;
                                        break block62;
                                    }
                                    if (!var18_41.equals("TZID")) break block69;
                                    var15_38 = var16_39;
                                    var17_40 = var12_35;
                                    break block62;
                                }
                                if (!var18_41.equals("TZURL")) break block70;
                                this.tzurl = var16_39;
                                ** GOTO lbl-1000
                            }
                            if (!var18_41.equals("LAST-MODIFIED")) break block71;
                            this.lastmod = new Date(VTimeZone.parseDateTimeString((String)var16_39, 0));
                            ** GOTO lbl-1000
                        }
                        var19_42 = var1_3;
                        if (!var18_41.equals("BEGIN")) break block72;
                        var24_46 = var16_39.equals("DAYLIGHT");
                        if (!var16_39.equals("STANDARD") && !var24_46) {
                            var14_37 = 3;
                            var1_5 = var19_42;
                            var17_40 = var12_35;
                        } else if (var15_38 == null) {
                            var14_37 = 3;
                            var1_6 = var19_42;
                            var17_40 = var12_35;
                        } else {
                            var1_7 = null;
                            var2_22 = null;
                            var3_23 = null;
                            var13_36 = var24_46;
                            var17_40 = 0;
                            var14_37 = 2;
                            var5_29 = null;
                        }
                        break block62;
                    }
                    if (!var18_41.equals("END")) ** GOTO lbl-1000
                    ** GOTO lbl-1000
                }
                if (var18_41.equals("BEGIN") && var16_39.equals("VTIMEZONE")) {
                    var14_37 = 1;
                    var17_40 = var12_35;
                } else lbl-1000: // 7 sources:
                {
                    var17_40 = var12_35;
                }
            }
            if (var14_37 == 3) {
                this.vtzlines = null;
                return false;
            }
            var12_35 = var17_40;
        }
        if (var6_30.size() == 0) {
            return false;
        }
        var1_8 = VTimeZone.getDefaultTZName(var15_38, false);
        var9_33 = new RuleBasedTimeZone(var15_38, new InitialTimeZoneRule(var1_8, var7_31, var8_32));
        var21_44 = 0;
        var17_40 = -1;
        for (var14_37 = 0; var14_37 < var6_30.size(); ++var14_37) {
            var1_10 = (TimeZoneRule)var6_30.get(var14_37);
            if (var1_10 instanceof AnnualTimeZoneRule) {
                var12_35 = var21_44;
                if (((AnnualTimeZoneRule)var1_10).getEndYear() == Integer.MAX_VALUE) {
                    var12_35 = var21_44 + 1;
                    var17_40 = var14_37;
                }
            } else {
                var12_35 = var21_44;
            }
            var21_44 = var12_35;
        }
        if (var21_44 > 2) {
            return false;
        }
        if (var21_44 == 1) {
            if (var6_30.size() == 1) {
                var6_30.clear();
            } else {
                var2_22 = (AnnualTimeZoneRule)var6_30.get(var17_40);
                var21_44 = var2_22.getRawOffset();
                var12_35 = var2_22.getDSTSavings();
                var16_39 = var3_23 = var2_22.getFirstStart(var7_31, var8_32);
                for (var14_37 = 0; var14_37 < var6_30.size(); ++var14_37) {
                    if (var17_40 == var14_37) {
                        var1_12 = var16_39;
                    } else {
                        var4_28 = (TimeZoneRule)var6_30.get(var14_37);
                        var19_42 = var4_28.getFinalStart(var21_44, var12_35);
                        var1_13 = var16_39;
                        if (var19_42.after((Date)var16_39)) {
                            var1_14 = var2_22.getNextStart(var19_42.getTime(), var4_28.getRawOffset(), var4_28.getDSTSavings(), false);
                        }
                    }
                    var16_39 = var1_15;
                }
                if (var16_39 == var3_23) {
                    var1_16 = new TimeArrayTimeZoneRule(var2_22.getName(), var2_22.getRawOffset(), var2_22.getDSTSavings(), new long[]{var3_23.getTime()}, 2);
                } else {
                    var1_17 = Grego.timeToFields(var16_39.getTime(), null);
                    var1_18 = new AnnualTimeZoneRule(var2_22.getName(), var2_22.getRawOffset(), var2_22.getDSTSavings(), var2_22.getRule(), var2_22.getStartYear(), var1_17[0]);
                }
                var6_30.set(var17_40, var1_19);
            }
        }
        var1_21 = var6_30.iterator();
        do {
            if (!var1_21.hasNext()) {
                this.tz = var9_33;
                this.setID(var15_38);
                return true;
            }
            var9_33.addTransitionRule((TimeZoneRule)var1_21.next());
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static long parseDateTimeString(String var0, int var1_2) {
        block17 : {
            block19 : {
                block18 : {
                    block16 : {
                        var2_3 = 0;
                        var3_4 = 0;
                        var4_5 = 0;
                        var5_6 = 0;
                        var6_7 = 0;
                        var7_8 = 0;
                        var8_9 = 0;
                        var9_10 = 0;
                        var10_11 = 0;
                        var11_12 = 0;
                        var12_13 = 0;
                        var13_14 = false;
                        var14_15 = false;
                        var15_16 = 0;
                        if (var0 != null) break block16;
                        var3_4 = var2_3;
                        var16_17 = var15_16;
                        break block17;
                    }
                    var16_17 = var0.length();
                    if (var16_17 == 15 || var16_17 == 16) break block18;
                    var3_4 = var2_3;
                    var16_17 = var15_16;
                    break block17;
                }
                if (var0.charAt(8) == 'T') break block19;
                var3_4 = var2_3;
                var16_17 = var15_16;
                break block17;
            }
            if (var16_17 != 16) ** GOTO lbl37
            if (var0.charAt(15) != 'Z') {
                var3_4 = var2_3;
                var16_17 = var15_16;
            } else {
                var14_15 = true;
lbl37: // 2 sources:
                var4_5 = var5_6;
                var6_7 = var7_8;
                var8_9 = var9_10;
                var10_11 = var11_12;
                var3_4 = var2_3 = Integer.parseInt(var0.substring(0, 4));
                var4_5 = var5_6;
                var6_7 = var7_8;
                var8_9 = var9_10;
                var10_11 = var11_12;
                var5_6 = Integer.parseInt(var0.substring(4, 6)) - 1;
                var3_4 = var2_3;
                var4_5 = var5_6;
                var6_7 = var7_8;
                var8_9 = var9_10;
                var10_11 = var11_12;
                var7_8 = Integer.parseInt(var0.substring(6, 8));
                var3_4 = var2_3;
                var4_5 = var5_6;
                var6_7 = var7_8;
                var8_9 = var9_10;
                var10_11 = var11_12;
                var9_10 = Integer.parseInt(var0.substring(9, 11));
                var3_4 = var2_3;
                var4_5 = var5_6;
                var6_7 = var7_8;
                var8_9 = var9_10;
                var10_11 = var11_12;
                var11_12 = Integer.parseInt(var0.substring(11, 13));
                var3_4 = var2_3;
                var4_5 = var5_6;
                var6_7 = var7_8;
                var8_9 = var9_10;
                var10_11 = var11_12;
                var17_18 = Integer.parseInt(var0.substring(13, 15));
                var18_19 = Grego.monthLength(var2_3, var5_6);
                var3_4 = var2_3;
                var4_5 = var5_6;
                var6_7 = var7_8;
                var8_9 = var9_10;
                var10_11 = var11_12;
                var12_13 = var17_18;
                var13_14 = var14_15;
                var16_17 = var15_16;
                if (var2_3 >= 0) {
                    var3_4 = var2_3;
                    var4_5 = var5_6;
                    var6_7 = var7_8;
                    var8_9 = var9_10;
                    var10_11 = var11_12;
                    var12_13 = var17_18;
                    var13_14 = var14_15;
                    var16_17 = var15_16;
                    if (var5_6 >= 0) {
                        var3_4 = var2_3;
                        var4_5 = var5_6;
                        var6_7 = var7_8;
                        var8_9 = var9_10;
                        var10_11 = var11_12;
                        var12_13 = var17_18;
                        var13_14 = var14_15;
                        var16_17 = var15_16;
                        if (var5_6 <= 11) {
                            var3_4 = var2_3;
                            var4_5 = var5_6;
                            var6_7 = var7_8;
                            var8_9 = var9_10;
                            var10_11 = var11_12;
                            var12_13 = var17_18;
                            var13_14 = var14_15;
                            var16_17 = var15_16;
                            if (var7_8 >= 1) {
                                var3_4 = var2_3;
                                var4_5 = var5_6;
                                var6_7 = var7_8;
                                var8_9 = var9_10;
                                var10_11 = var11_12;
                                var12_13 = var17_18;
                                var13_14 = var14_15;
                                var16_17 = var15_16;
                                if (var7_8 <= var18_19) {
                                    var3_4 = var2_3;
                                    var4_5 = var5_6;
                                    var6_7 = var7_8;
                                    var8_9 = var9_10;
                                    var10_11 = var11_12;
                                    var12_13 = var17_18;
                                    var13_14 = var14_15;
                                    var16_17 = var15_16;
                                    if (var9_10 >= 0) {
                                        var3_4 = var2_3;
                                        var4_5 = var5_6;
                                        var6_7 = var7_8;
                                        var8_9 = var9_10;
                                        var10_11 = var11_12;
                                        var12_13 = var17_18;
                                        var13_14 = var14_15;
                                        var16_17 = var15_16;
                                        if (var9_10 < 24) {
                                            var3_4 = var2_3;
                                            var4_5 = var5_6;
                                            var6_7 = var7_8;
                                            var8_9 = var9_10;
                                            var10_11 = var11_12;
                                            var12_13 = var17_18;
                                            var13_14 = var14_15;
                                            var16_17 = var15_16;
                                            if (var11_12 >= 0) {
                                                var3_4 = var2_3;
                                                var4_5 = var5_6;
                                                var6_7 = var7_8;
                                                var8_9 = var9_10;
                                                var10_11 = var11_12;
                                                var12_13 = var17_18;
                                                var13_14 = var14_15;
                                                var16_17 = var15_16;
                                                if (var11_12 < 60) {
                                                    var3_4 = var2_3;
                                                    var4_5 = var5_6;
                                                    var6_7 = var7_8;
                                                    var8_9 = var9_10;
                                                    var10_11 = var11_12;
                                                    var12_13 = var17_18;
                                                    var13_14 = var14_15;
                                                    var16_17 = var15_16;
                                                    if (var17_18 >= 0) {
                                                        if (var17_18 >= 60) {
                                                            var3_4 = var2_3;
                                                            var4_5 = var5_6;
                                                            var6_7 = var7_8;
                                                            var8_9 = var9_10;
                                                            var10_11 = var11_12;
                                                            var12_13 = var17_18;
                                                            var13_14 = var14_15;
                                                            var16_17 = var15_16;
                                                        } else {
                                                            var16_17 = 1;
                                                            var3_4 = var2_3;
                                                            var4_5 = var5_6;
                                                            var6_7 = var7_8;
                                                            var8_9 = var9_10;
                                                            var10_11 = var11_12;
                                                            var12_13 = var17_18;
                                                            var13_14 = var14_15;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            break block17;
            catch (NumberFormatException var0_1) {
                var16_17 = var15_16;
                var13_14 = var14_15;
            }
        }
        if (var16_17 == 0) throw new IllegalArgumentException("Invalid date time string format");
        var21_21 = var19_20 = Grego.fieldsToDay(var3_4, var4_5, var6_7) * 86400000L + (long)(3600000 * var8_9 + 60000 * var10_11 + var12_13 * 1000);
        if (var13_14 != false) return var21_21;
        return var19_20 - (long)var1_2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static int[] parseRRULE(String var0, long[] var1_1) {
        var2_2 = null;
        var3_7 = Long.MIN_VALUE;
        var5_8 = false;
        var6_9 = 0;
        var7_10 = new StringTokenizer((String)var0, ";");
        var8_11 = 0;
        var9_12 = 0;
        var10_13 = -1;
        var0 = var2_2;
        do {
            block22 : {
                block32 : {
                    block25 : {
                        block26 : {
                            block36 : {
                                block30 : {
                                    block31 : {
                                        block24 : {
                                            block34 : {
                                                block35 : {
                                                    block33 : {
                                                        block29 : {
                                                            block27 : {
                                                                block28 : {
                                                                    if (!var7_10.hasMoreTokens()) break block25;
                                                                    var11_14 = var7_10.nextToken();
                                                                    var12_15 = var11_14.indexOf("=");
                                                                    if (var12_15 == -1) break block26;
                                                                    var2_2 = var11_14.substring(0, var12_15);
                                                                    var11_14 = var11_14.substring(var12_15 + 1);
                                                                    if (!var2_2.equals("FREQ")) break block27;
                                                                    if (!var11_14.equals("YEARLY")) break block28;
                                                                    var5_8 = true;
                                                                    var12_15 = var8_11;
                                                                    break block22;
                                                                }
                                                                var6_9 = 1;
                                                                var12_15 = var10_13;
                                                                var10_13 = var6_9;
                                                                ** GOTO lbl125
                                                            }
                                                            if (var2_2.equals("UNTIL")) {
                                                                try {
                                                                    var3_7 = var13_16 = VTimeZone.parseDateTimeString((String)var11_14, 0);
                                                                    var12_15 = var8_11;
                                                                    break block22;
                                                                }
                                                                catch (IllegalArgumentException var2_3) {
                                                                    var6_9 = 1;
                                                                    var12_15 = var10_13;
                                                                    var10_13 = var6_9;
                                                                    break block23;
                                                                }
                                                            }
                                                            if (!var2_2.equals("BYMONTH")) break block29;
                                                            if (var11_14.length() > 2) {
                                                                var6_9 = 1;
                                                                var12_15 = var10_13;
                                                                var10_13 = var6_9;
                                                            } else {
                                                                try {
                                                                    var12_15 = Integer.parseInt((String)var11_14);
                                                                    var10_13 = var12_15 - 1;
                                                                    if (var10_13 >= 0 && var10_13 < 12) {
                                                                        var12_15 = var8_11;
                                                                        break block22;
                                                                    }
                                                                    var6_9 = 1;
                                                                    var12_15 = var10_13;
                                                                    var10_13 = var6_9;
                                                                }
                                                                catch (NumberFormatException var2_4) {
                                                                    var6_9 = 1;
                                                                    var12_15 = var10_13;
                                                                    var10_13 = var6_9;
                                                                }
                                                            }
                                                            ** GOTO lbl125
                                                        }
                                                        if (!var2_2.equals("BYDAY")) break block30;
                                                        var15_17 = var11_14.length();
                                                        var16_18 = var10_13;
                                                        if (var15_17 < 2 || var15_17 > 4) break block31;
                                                        var12_15 = var8_11;
                                                        var2_2 = var11_14;
                                                        if (var15_17 <= 2) break block32;
                                                        var10_13 = 1;
                                                        if (var11_14.charAt(0) != '+') break block33;
                                                        var10_13 = 1;
                                                        break block34;
                                                    }
                                                    if (var11_14.charAt(0) != '-') break block35;
                                                    var10_13 = -1;
                                                    break block34;
                                                }
                                                if (var15_17 != 4) break block34;
                                                var10_13 = 1;
                                                var12_15 = var16_18;
                                                ** GOTO lbl125
                                            }
                                            try {
                                                var12_15 = Integer.parseInt(var11_14.substring(var15_17 - 3, var15_17 - 2));
                                                if (var12_15 == 0 || var12_15 > 4) break block24;
                                            }
                                            catch (NumberFormatException var2_5) {
                                                var10_13 = 1;
                                                var12_15 = var16_18;
                                            }
                                            var2_2 = var11_14.substring(var15_17 - 2);
                                            var12_15 *= var10_13;
                                            break block32;
                                        }
                                        var10_13 = 1;
                                        var12_15 = var16_18;
                                        ** GOTO lbl125
                                        ** GOTO lbl125
                                    }
                                    var10_13 = 1;
                                    var12_15 = var16_18;
                                    ** GOTO lbl125
                                }
                                if (!var2_2.equals("BYMONTHDAY")) break block36;
                                var2_2 = new StringTokenizer((String)var11_14, ",");
                                var0 = new int[var2_2.countTokens()];
                                var12_15 = 0;
                                ** GOTO lbl147
                            }
                            var12_15 = var8_11;
                            break block22;
                        }
                        var12_15 = var10_13;
                        return null;
                    }
                    var12_15 = var10_13;
                    var10_13 = var6_9;
                    ** GOTO lbl125
                }
                for (var10_13 = 0; var10_13 < (var11_14 = VTimeZone.ICAL_DOW_NAMES).length && !var2_2.equals(var11_14[var10_13]); ++var10_13) {
                }
                if (var10_13 < VTimeZone.ICAL_DOW_NAMES.length) {
                    var9_12 = var10_13 + 1;
                    var10_13 = var16_18;
                } else {
                    block23 : {
                        var10_13 = 1;
                        var8_11 = var12_15;
                        var12_15 = var16_18;
                    }
                    if (var10_13 != 0) {
                        return null;
                    }
                    if (!var5_8) {
                        return null;
                    }
                    var1_1[0] = var3_7;
                    if (var0 == null) {
                        var1_1 = new int[4];
                        var1_1[3] = 0;
                    } else {
                        var2_2 = new int[var0.length + 3];
                        var10_13 = 0;
                        do {
                            var1_1 = var2_2;
                            if (var10_13 >= var0.length) break;
                            var2_2[var10_13 + 3] = var0[var10_13];
                            ++var10_13;
                        } while (true);
                    }
                    var1_1[0] = var12_15;
                    var1_1[1] = var9_12;
                    var1_1[2] = var8_11;
                    return var1_1;
lbl147: // 2 sources:
                    while (var2_2.hasMoreTokens()) {
                        try {
                            var0[var12_15] = Integer.parseInt(var2_2.nextToken());
                            ++var12_15;
                        }
                        catch (NumberFormatException var2_6) {
                            var6_9 = 1;
                            var12_15 = var8_11;
                            break block22;
                        }
                    }
                    var12_15 = var8_11;
                }
            }
            var8_11 = var12_15;
        } while (true);
    }

    private static DateTimeRule toWallTimeRule(DateTimeRule dateTimeRule, int n, int n2) {
        int n3;
        if (dateTimeRule.getTimeRuleType() == 0) {
            return dateTimeRule;
        }
        int n4 = dateTimeRule.getRuleMillisInDay();
        if (dateTimeRule.getTimeRuleType() == 2) {
            n = n4 + (n + n2);
        } else {
            n = n4;
            if (dateTimeRule.getTimeRuleType() == 1) {
                n = n4 + n2;
            }
        }
        int n5 = 0;
        if (n < 0) {
            n5 = -1;
            n3 = n + 86400000;
        } else {
            n3 = n;
            if (n >= 86400000) {
                n5 = 1;
                n3 = n - 86400000;
            }
        }
        int n6 = dateTimeRule.getRuleMonth();
        n2 = dateTimeRule.getRuleDayOfMonth();
        int n7 = dateTimeRule.getRuleDayOfWeek();
        n4 = dateTimeRule.getDateRuleType();
        int n8 = n6;
        int n9 = n2;
        int n10 = n7;
        int n11 = n4;
        if (n5 != 0) {
            n = n4;
            if (n4 == 1) {
                n2 = dateTimeRule.getRuleWeekInMonth();
                if (n2 > 0) {
                    n = 2;
                    n2 = (n2 - 1) * 7 + 1;
                } else {
                    n = 3;
                    n2 = MONTHLENGTH[n6] + (n2 + 1) * 7;
                }
            }
            n10 = n2 + n5;
            n2 = 11;
            if (n10 == 0) {
                n4 = n6 - 1;
                if (n4 >= 0) {
                    n2 = n4;
                }
                n10 = MONTHLENGTH[n2];
                n4 = n2;
                n2 = n10;
            } else {
                n4 = n6;
                n2 = n10;
                if (n10 > MONTHLENGTH[n6]) {
                    n2 = n6 + 1;
                    if (n2 > 11) {
                        n2 = 0;
                    }
                    n4 = n2;
                    n2 = 1;
                }
            }
            n8 = n4;
            n9 = n2;
            n10 = n7;
            n11 = n;
            if (n != 0) {
                if ((n5 = n7 + n5) < 1) {
                    n10 = 7;
                    n8 = n4;
                    n9 = n2;
                    n11 = n;
                } else {
                    n8 = n4;
                    n9 = n2;
                    n10 = n5;
                    n11 = n;
                    if (n5 > 7) {
                        n10 = 1;
                        n11 = n;
                        n9 = n2;
                        n8 = n4;
                    }
                }
            }
        }
        if (n11 == 0) {
            dateTimeRule = new DateTimeRule(n8, n9, n3, 0);
        } else {
            boolean bl = n11 == 2;
            dateTimeRule = new DateTimeRule(n8, n9, n10, bl, n3, 0);
        }
        return dateTimeRule;
    }

    private static void writeFinalRule(Writer writer, boolean bl, AnnualTimeZoneRule annualTimeZoneRule, int n, int n2, long l) throws IOException {
        DateTimeRule dateTimeRule = VTimeZone.toWallTimeRule(annualTimeZoneRule.getRule(), n, n2);
        int n3 = dateTimeRule.getRuleMillisInDay();
        if (n3 < 0) {
            l += (long)(0 - n3);
        } else if (n3 >= 86400000) {
            l -= (long)(n3 - 86399999);
        }
        int n4 = annualTimeZoneRule.getRawOffset() + annualTimeZoneRule.getDSTSavings();
        n3 = dateTimeRule.getDateRuleType();
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 == 3) {
                        VTimeZone.writeZonePropsByDOW_LEQ_DOM(writer, bl, annualTimeZoneRule.getName(), n + n2, n4, dateTimeRule.getRuleMonth(), dateTimeRule.getRuleDayOfMonth(), dateTimeRule.getRuleDayOfWeek(), l, Long.MAX_VALUE);
                    }
                } else {
                    VTimeZone.writeZonePropsByDOW_GEQ_DOM(writer, bl, annualTimeZoneRule.getName(), n + n2, n4, dateTimeRule.getRuleMonth(), dateTimeRule.getRuleDayOfMonth(), dateTimeRule.getRuleDayOfWeek(), l, Long.MAX_VALUE);
                }
            } else {
                VTimeZone.writeZonePropsByDOW(writer, bl, annualTimeZoneRule.getName(), n + n2, n4, dateTimeRule.getRuleMonth(), dateTimeRule.getRuleWeekInMonth(), dateTimeRule.getRuleDayOfWeek(), l, Long.MAX_VALUE);
            }
        } else {
            VTimeZone.writeZonePropsByDOM(writer, bl, annualTimeZoneRule.getName(), n + n2, n4, dateTimeRule.getRuleMonth(), dateTimeRule.getRuleDayOfMonth(), l, Long.MAX_VALUE);
        }
    }

    private static void writeFooter(Writer writer) throws IOException {
        writer.write(ICAL_END);
        writer.write(COLON);
        writer.write(ICAL_VTIMEZONE);
        writer.write(NEWLINE);
    }

    private void writeHeader(Writer writer) throws IOException {
        writer.write(ICAL_BEGIN);
        writer.write(COLON);
        writer.write(ICAL_VTIMEZONE);
        writer.write(NEWLINE);
        writer.write(ICAL_TZID);
        writer.write(COLON);
        writer.write(this.tz.getID());
        writer.write(NEWLINE);
        if (this.tzurl != null) {
            writer.write(ICAL_TZURL);
            writer.write(COLON);
            writer.write(this.tzurl);
            writer.write(NEWLINE);
        }
        if (this.lastmod != null) {
            writer.write(ICAL_LASTMOD);
            writer.write(COLON);
            writer.write(VTimeZone.getUTCDateTimeString(this.lastmod.getTime()));
            writer.write(NEWLINE);
        }
    }

    private void writeZone(Writer writer, BasicTimeZone cloneable, String[] object) throws IOException {
        int n;
        this.writeHeader(writer);
        if (object != null && ((String[])object).length > 0) {
            for (n = 0; n < ((String[])object).length; ++n) {
                if (object[n] == null) continue;
                writer.write(object[n]);
                writer.write(NEWLINE);
            }
        }
        AnnualTimeZoneRule annualTimeZoneRule = null;
        int n2 = 0;
        int n3 = 0;
        long l = 0L;
        long l2 = 0L;
        int n4 = 0;
        Object object2 = null;
        int[] arrn = new int[6];
        int n5 = 0;
        n = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        object = null;
        long l3 = Long.MIN_VALUE;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        Object object3 = null;
        int n14 = 0;
        long l4 = 0L;
        int n15 = 0;
        int n16 = 0;
        int n17 = 0;
        int n18 = 0;
        int n19 = 0;
        long l5 = 0L;
        do {
            int n20;
            int n21;
            int n22;
            int n23;
            block57 : {
                String[] arrstring;
                Object object4;
                block58 : {
                    int n24;
                    block54 : {
                        int n25;
                        int n26;
                        TimeZoneTransition timeZoneTransition;
                        int n27;
                        int n28;
                        int n29;
                        int n30;
                        String string;
                        block55 : {
                            block56 : {
                                block53 : {
                                    n24 = n17;
                                    timeZoneTransition = ((BasicTimeZone)cloneable).getNextTransition(l3, false);
                                    if (timeZoneTransition != null) break block53;
                                    n17 = n5;
                                    n19 = n9;
                                    n13 = n16;
                                    n16 = n4;
                                    n5 = n8;
                                    n8 = n;
                                    n20 = 1;
                                    n9 = n12;
                                    n = n18;
                                    n4 = n19;
                                    n12 = n16;
                                    n16 = n20;
                                    arrstring = object;
                                    n18 = n17;
                                    n23 = n7;
                                    break block54;
                                }
                                n26 = 1;
                                l3 = timeZoneTransition.getTime();
                                string = timeZoneTransition.getTo().getName();
                                n23 = timeZoneTransition.getTo().getDSTSavings() != 0 ? 1 : 0;
                                n20 = timeZoneTransition.getFrom().getRawOffset();
                                n22 = timeZoneTransition.getFrom().getDSTSavings() + n20;
                                n27 = timeZoneTransition.getFrom().getDSTSavings();
                                n20 = timeZoneTransition.getTo().getRawOffset() + timeZoneTransition.getTo().getDSTSavings();
                                Grego.timeToFields(timeZoneTransition.getTime() + (long)n22, arrn);
                                n21 = Grego.getDayOfWeekInMonth(arrn[0], arrn[1], arrn[2]);
                                n29 = arrn[0];
                                n5 = 0;
                                n25 = 0;
                                n30 = 0;
                                n28 = 0;
                                if (n23 == 0) break block55;
                                if (annualTimeZoneRule == null && timeZoneTransition.getTo() instanceof AnnualTimeZoneRule && ((AnnualTimeZoneRule)timeZoneTransition.getTo()).getEndYear() == Integer.MAX_VALUE) {
                                    annualTimeZoneRule = (AnnualTimeZoneRule)timeZoneTransition.getTo();
                                }
                                n23 = n9;
                                if (n16 > 0) {
                                    if (n29 == n11 + n16 && string.equals(object) && n7 == n22 && n8 == n20 && n == arrn[1] && n6 == arrn[3] && n15 == n21 && n19 == arrn[5]) {
                                        l5 = l3;
                                        n9 = 1;
                                        ++n16;
                                    } else {
                                        n9 = n28;
                                    }
                                    if (n9 == 0) {
                                        if (n16 == 1) {
                                            VTimeZone.writeZonePropsByTime(writer, true, (String)object, n7, n8, l4, true);
                                            n5 = n9;
                                            n9 = n20;
                                        } else {
                                            object4 = object;
                                            VTimeZone.writeZonePropsByDOW(writer, true, (String)object, n7, n8, n, n15, n6, l4, l5);
                                            object = object4;
                                            n5 = n9;
                                            n9 = n20;
                                        }
                                    } else {
                                        n5 = n9;
                                        n9 = n20;
                                    }
                                } else {
                                    n9 = n20;
                                }
                                n20 = n12;
                                object4 = arrn;
                                if (n5 == 0) {
                                    n10 = n27;
                                    n = object4[1];
                                    n6 = object4[3];
                                    n19 = object4[5];
                                    l5 = l3;
                                    object = string;
                                    n11 = n29;
                                    n15 = n21;
                                    l4 = l3;
                                    n16 = 1;
                                    n12 = n22;
                                } else {
                                    n9 = n8;
                                    n12 = n7;
                                }
                                if (object2 == null || annualTimeZoneRule == null) break block56;
                                n5 = n9;
                                n7 = n12;
                                n13 = n16;
                                n12 = n4;
                                n4 = n23;
                                n9 = n20;
                                n16 = 1;
                                n8 = n;
                                n = n18;
                                arrstring = object;
                                n24 = n17;
                                n18 = n26;
                                n23 = n7;
                                break block54;
                            }
                            n22 = n23;
                            n23 = n20;
                            n8 = n9;
                            n7 = n12;
                            n20 = n17;
                            n21 = n4;
                            break block57;
                        }
                        int[] arrn2 = arrn;
                        n23 = n7;
                        n28 = n18;
                        n5 = n8;
                        arrstring = object;
                        object4 = object2;
                        if (object2 == null) {
                            object4 = object2;
                            if (timeZoneTransition.getTo() instanceof AnnualTimeZoneRule) {
                                object4 = object2;
                                if (((AnnualTimeZoneRule)timeZoneTransition.getTo()).getEndYear() == Integer.MAX_VALUE) {
                                    object4 = (AnnualTimeZoneRule)timeZoneTransition.getTo();
                                }
                            }
                        }
                        if (n4 > 0) {
                            if (n29 == n3 + n4 && string.equals(object3) && n14 == n22 && n24 == n20 && n28 == arrn2[1] && n9 == arrn2[3] && n12 == n21 && n13 == arrn2[5]) {
                                l2 = l3;
                                n7 = 1;
                                ++n4;
                            } else {
                                n7 = n25;
                            }
                            object = string;
                            n8 = n22;
                            n18 = n21;
                            if (n7 == 0) {
                                if (n4 == 1) {
                                    n24 = n4;
                                    n4 = n12;
                                    n25 = n18;
                                    n18 = n9;
                                    n30 = n20;
                                    n22 = n17;
                                    n21 = n8;
                                    VTimeZone.writeZonePropsByTime(writer, false, object3, n14, n17, l, true);
                                    n20 = n7;
                                    n12 = n24;
                                    n9 = n25;
                                    n8 = n18;
                                    n18 = n30;
                                    n17 = n22;
                                    n7 = n21;
                                } else {
                                    n22 = n4;
                                    n4 = n12;
                                    n24 = n9;
                                    n25 = n20;
                                    n21 = n8;
                                    object2 = object3;
                                    VTimeZone.writeZonePropsByDOW(writer, false, object3, n14, n17, n28, n4, n24, l, l2);
                                    n20 = n7;
                                    n12 = n22;
                                    n9 = n18;
                                    n8 = n24;
                                    n18 = n25;
                                    n7 = n21;
                                    object3 = object2;
                                }
                            } else {
                                n22 = n4;
                                n4 = n12;
                                n21 = n18;
                                n25 = n9;
                                n18 = n20;
                                n24 = n8;
                                n20 = n7;
                                n12 = n22;
                                n9 = n21;
                                n8 = n25;
                                n7 = n24;
                            }
                        } else {
                            n25 = n21;
                            object = string;
                            n17 = n24;
                            n21 = n12;
                            n7 = n22;
                            n18 = n20;
                            n8 = n9;
                            n9 = n25;
                            n12 = n4;
                            n20 = n30;
                            n4 = n21;
                        }
                        n22 = 1;
                        if (n20 == 0) {
                            n14 = n7;
                            n2 = n27;
                            n12 = n18;
                            n8 = arrn2[1];
                            n4 = arrn2[3];
                            n17 = arrn2[5];
                            l2 = l3;
                            l = l3;
                            n3 = n29;
                            n13 = 1;
                        } else {
                            n9 = n12;
                            n18 = n13;
                            n7 = n8;
                            n12 = n17;
                            object = object3;
                            n13 = n9;
                            n8 = n28;
                            n9 = n4;
                            n4 = n7;
                            n17 = n18;
                        }
                        if (object4 == null || annualTimeZoneRule == null) break block58;
                        n17 = n8;
                        n7 = n13;
                        n18 = n26;
                        n24 = n12;
                        object2 = object4;
                        object3 = object;
                        n8 = n;
                        n13 = n16;
                        n16 = n22;
                        n12 = n7;
                        n = n17;
                    }
                    n17 = 0;
                    if (n18 == 0) {
                        n4 = ((TimeZone)cloneable).getOffset(0L);
                        n = n17;
                        if (n4 != ((TimeZone)cloneable).getRawOffset()) {
                            n = n16;
                        }
                        VTimeZone.writeZonePropsByTime(writer, n != 0, VTimeZone.getDefaultTZName(((TimeZone)cloneable).getID(), n != 0), n4, n4, 0L - (long)n4, false);
                    } else {
                        if (n13 > 0) {
                            if (annualTimeZoneRule == null) {
                                if (n13 == n16) {
                                    VTimeZone.writeZonePropsByTime(writer, true, (String)arrstring, n23, n5, l4, true);
                                } else {
                                    VTimeZone.writeZonePropsByDOW(writer, true, (String)arrstring, n23, n5, n8, n15, n6, l4, l5);
                                }
                            } else if (n13 == n16) {
                                VTimeZone.writeFinalRule(writer, true, annualTimeZoneRule, n23 - n10, n10, l4);
                            } else if (VTimeZone.isEquivalentDateRule(n8, n15, n6, annualTimeZoneRule.getRule())) {
                                VTimeZone.writeZonePropsByDOW(writer, true, (String)arrstring, n23, n5, n8, n15, n6, l4, Long.MAX_VALUE);
                            } else {
                                VTimeZone.writeZonePropsByDOW(writer, true, (String)arrstring, n23, n5, n8, n15, n6, l4, l5);
                                cloneable = annualTimeZoneRule.getNextStart(l5, n23 - n10, n10, false);
                                if (cloneable != null) {
                                    VTimeZone.writeFinalRule(writer, true, annualTimeZoneRule, n23 - n10, n10, ((Date)cloneable).getTime());
                                }
                            }
                        }
                        if (n12 > 0) {
                            if (object2 == null) {
                                if (n12 == n16) {
                                    VTimeZone.writeZonePropsByTime(writer, false, object3, n14, n24, l, true);
                                } else {
                                    VTimeZone.writeZonePropsByDOW(writer, false, object3, n14, n24, n, n9, n4, l, l2);
                                }
                            } else if (n12 == n16) {
                                VTimeZone.writeFinalRule(writer, false, (AnnualTimeZoneRule)object2, n14 - n2, n2, l);
                            } else if (VTimeZone.isEquivalentDateRule(n, n9, n4, object2.getRule())) {
                                VTimeZone.writeZonePropsByDOW(writer, false, object3, n14, n24, n, n9, n4, l, Long.MAX_VALUE);
                            } else {
                                VTimeZone.writeZonePropsByDOW(writer, false, object3, n14, n24, n, n9, n4, l, l2);
                                cloneable = object2.getNextStart(l2, n14 - n2, n2, false);
                                if (cloneable != null) {
                                    VTimeZone.writeFinalRule(writer, false, (AnnualTimeZoneRule)object2, n14 - n2, n2, ((Date)cloneable).getTime());
                                }
                            }
                        }
                    }
                    VTimeZone.writeFooter(writer);
                    return;
                }
                n7 = n23;
                object2 = object4;
                n21 = n13;
                object3 = object;
                n18 = n8;
                object = arrstring;
                n20 = n12;
                n8 = n5;
                n23 = n9;
                n22 = n4;
                n13 = n17;
            }
            n5 = 1;
            n9 = n22;
            n12 = n23;
            n17 = n20;
            n4 = n21;
        } while (true);
    }

    private static void writeZonePropsByDOM(Writer writer, boolean bl, String string, int n, int n2, int n3, int n4, long l, long l2) throws IOException {
        VTimeZone.beginZoneProps(writer, bl, string, n, n2, l);
        VTimeZone.beginRRULE(writer, n3);
        writer.write(ICAL_BYMONTHDAY);
        writer.write(EQUALS_SIGN);
        writer.write(Integer.toString(n4));
        if (l2 != Long.MAX_VALUE) {
            VTimeZone.appendUNTIL(writer, VTimeZone.getDateTimeString(l2 + (long)n));
        }
        writer.write(NEWLINE);
        VTimeZone.endZoneProps(writer, bl);
    }

    private static void writeZonePropsByDOW(Writer writer, boolean bl, String string, int n, int n2, int n3, int n4, int n5, long l, long l2) throws IOException {
        VTimeZone.beginZoneProps(writer, bl, string, n, n2, l);
        VTimeZone.beginRRULE(writer, n3);
        writer.write(ICAL_BYDAY);
        writer.write(EQUALS_SIGN);
        writer.write(Integer.toString(n4));
        writer.write(ICAL_DOW_NAMES[n5 - 1]);
        if (l2 != Long.MAX_VALUE) {
            VTimeZone.appendUNTIL(writer, VTimeZone.getDateTimeString(l2 + (long)n));
        }
        writer.write(NEWLINE);
        VTimeZone.endZoneProps(writer, bl);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void writeZonePropsByDOW_GEQ_DOM(Writer var0, boolean var1_1, String var2_2, int var3_3, int var4_4, int var5_5, int var6_6, int var7_7, long var8_8, long var10_9) throws IOException {
        block4 : {
            if (var6_6 % 7 == 1) {
                VTimeZone.writeZonePropsByDOW(var0, var1_1, (String)var2_2, var3_3, var4_4, var5_5, (var6_6 + 6) / 7, var7_7, var8_8, var10_9);
                return;
            }
            if (var5_5 != 1 && ((var12_10 = VTimeZone.MONTHLENGTH)[var5_5] - var6_6) % 7 == 6) {
                VTimeZone.writeZonePropsByDOW(var0, var1_1, (String)var2_2, var3_3, var4_4, var5_5, (var12_10[var5_5] - var6_6 + 1) / 7 * -1, var7_7, var8_8, var10_9);
                return;
            }
            VTimeZone.beginZoneProps(var0, var1_1, (String)var2_2, var3_3, var4_4, var8_8);
            var4_4 = var6_6;
            var13_11 = 7;
            var14_12 = 11;
            if (var6_6 > 0) break block4;
            var6_6 = 1 - var6_6;
            var13_11 = 7 - var6_6;
            var4_4 = var5_5 - 1 < 0 ? var14_12 : var5_5 - 1;
            VTimeZone.writeZonePropsByDOW_GEQ_DOM_sub(var0, var4_4, -var6_6, var7_7, var6_6, Long.MAX_VALUE, var3_3);
            var14_12 = 1;
            ** GOTO lbl-1000
        }
        var2_2 = VTimeZone.MONTHLENGTH;
        var14_12 = var4_4;
        if (var6_6 + 6 > var2_2[var5_5]) {
            var14_12 = var6_6 + 6 - var2_2[var5_5];
            var6_6 = var5_5 + 1 > 11 ? 0 : var5_5 + 1;
            VTimeZone.writeZonePropsByDOW_GEQ_DOM_sub(var0, var6_6, 1, var7_7, var14_12, Long.MAX_VALUE, var3_3);
            var6_6 = 7 - var14_12;
            var14_12 = var4_4;
            var4_4 = var6_6;
        } else lbl-1000: // 2 sources:
        {
            var4_4 = var13_11;
        }
        VTimeZone.writeZonePropsByDOW_GEQ_DOM_sub(var0, var5_5, var14_12, var7_7, var4_4, var10_9, var3_3);
        VTimeZone.endZoneProps(var0, var1_1);
    }

    private static void writeZonePropsByDOW_GEQ_DOM_sub(Writer writer, int n, int n2, int n3, int n4, long l, int n5) throws IOException {
        int n6 = n2;
        boolean bl = n == 1;
        int n7 = n6;
        if (n2 < 0) {
            n7 = n6;
            if (!bl) {
                n7 = MONTHLENGTH[n] + n2 + 1;
            }
        }
        VTimeZone.beginRRULE(writer, n);
        writer.write(ICAL_BYDAY);
        writer.write(EQUALS_SIGN);
        writer.write(ICAL_DOW_NAMES[n3 - 1]);
        writer.write(SEMICOLON);
        writer.write(ICAL_BYMONTHDAY);
        writer.write(EQUALS_SIGN);
        writer.write(Integer.toString(n7));
        for (n = 1; n < n4; ++n) {
            writer.write(COMMA);
            writer.write(Integer.toString(n7 + n));
        }
        if (l != Long.MAX_VALUE) {
            VTimeZone.appendUNTIL(writer, VTimeZone.getDateTimeString((long)n5 + l));
        }
        writer.write(NEWLINE);
    }

    private static void writeZonePropsByDOW_LEQ_DOM(Writer writer, boolean bl, String string, int n, int n2, int n3, int n4, int n5, long l, long l2) throws IOException {
        int[] arrn;
        if (n4 % 7 == 0) {
            VTimeZone.writeZonePropsByDOW(writer, bl, string, n, n2, n3, n4 / 7, n5, l, l2);
        } else if (n3 != 1 && ((arrn = MONTHLENGTH)[n3] - n4) % 7 == 0) {
            VTimeZone.writeZonePropsByDOW(writer, bl, string, n, n2, n3, ((arrn[n3] - n4) / 7 + 1) * -1, n5, l, l2);
        } else if (n3 == 1 && n4 == 29) {
            VTimeZone.writeZonePropsByDOW(writer, bl, string, n, n2, 1, -1, n5, l, l2);
        } else {
            VTimeZone.writeZonePropsByDOW_GEQ_DOM(writer, bl, string, n, n2, n3, n4 - 6, n5, l, l2);
        }
    }

    private static void writeZonePropsByTime(Writer writer, boolean bl, String string, int n, int n2, long l, boolean bl2) throws IOException {
        VTimeZone.beginZoneProps(writer, bl, string, n, n2, l);
        if (bl2) {
            writer.write(ICAL_RDATE);
            writer.write(COLON);
            writer.write(VTimeZone.getDateTimeString((long)n + l));
            writer.write(NEWLINE);
        }
        VTimeZone.endZoneProps(writer, bl);
    }

    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public TimeZone cloneAsThawed() {
        VTimeZone vTimeZone = (VTimeZone)super.cloneAsThawed();
        vTimeZone.tz = (BasicTimeZone)this.tz.cloneAsThawed();
        vTimeZone.isFrozen = false;
        return vTimeZone;
    }

    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }

    public Date getLastModified() {
        return this.lastmod;
    }

    @Override
    public TimeZoneTransition getNextTransition(long l, boolean bl) {
        return this.tz.getNextTransition(l, bl);
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        return this.tz.getOffset(n, n2, n3, n4, n5, n6);
    }

    @Override
    public void getOffset(long l, boolean bl, int[] arrn) {
        this.tz.getOffset(l, bl, arrn);
    }

    @Deprecated
    @Override
    public void getOffsetFromLocal(long l, int n, int n2, int[] arrn) {
        this.tz.getOffsetFromLocal(l, n, n2, arrn);
    }

    @Override
    public TimeZoneTransition getPreviousTransition(long l, boolean bl) {
        return this.tz.getPreviousTransition(l, bl);
    }

    @Override
    public int getRawOffset() {
        return this.tz.getRawOffset();
    }

    public String getTZURL() {
        return this.tzurl;
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules() {
        return this.tz.getTimeZoneRules();
    }

    @Override
    public TimeZoneRule[] getTimeZoneRules(long l) {
        return this.tz.getTimeZoneRules(l);
    }

    @Override
    public boolean hasEquivalentTransitions(TimeZone timeZone, long l, long l2) {
        if (this == timeZone) {
            return true;
        }
        return this.tz.hasEquivalentTransitions(timeZone, l, l2);
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        if (this == timeZone) {
            return true;
        }
        if (timeZone instanceof VTimeZone) {
            return this.tz.hasSameRules(((VTimeZone)timeZone).tz);
        }
        return this.tz.hasSameRules(timeZone);
    }

    @Override
    public boolean inDaylightTime(Date date) {
        return this.tz.inDaylightTime(date);
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public boolean observesDaylightTime() {
        return this.tz.observesDaylightTime();
    }

    public void setLastModified(Date date) {
        if (!this.isFrozen()) {
            this.lastmod = date;
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
    }

    @Override
    public void setRawOffset(int n) {
        if (!this.isFrozen()) {
            this.tz.setRawOffset(n);
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
    }

    public void setTZURL(String string) {
        if (!this.isFrozen()) {
            this.tzurl = string;
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen VTimeZone instance.");
    }

    @Override
    public boolean useDaylightTime() {
        return this.tz.useDaylightTime();
    }

    public void write(Writer object2) throws IOException {
        String[] arrstring = new BufferedWriter((Writer)object2);
        List<String> list = this.vtzlines;
        if (list != null) {
            for (String string : list) {
                if (string.startsWith("TZURL:")) {
                    if (this.tzurl == null) continue;
                    arrstring.write(ICAL_TZURL);
                    arrstring.write(COLON);
                    arrstring.write(this.tzurl);
                    arrstring.write(NEWLINE);
                    continue;
                }
                if (string.startsWith("LAST-MODIFIED:")) {
                    if (this.lastmod == null) continue;
                    arrstring.write(ICAL_LASTMOD);
                    arrstring.write(COLON);
                    arrstring.write(VTimeZone.getUTCDateTimeString(this.lastmod.getTime()));
                    arrstring.write(NEWLINE);
                    continue;
                }
                arrstring.write(string);
                arrstring.write(NEWLINE);
            }
            arrstring.flush();
        } else {
            list = null;
            arrstring = list;
            if (this.olsonzid != null) {
                arrstring = list;
                if (ICU_TZVERSION != null) {
                    arrstring = new String[1];
                    list = new StringBuilder();
                    ((StringBuilder)((Object)list)).append("X-TZINFO:");
                    ((StringBuilder)((Object)list)).append(this.olsonzid);
                    ((StringBuilder)((Object)list)).append("[");
                    ((StringBuilder)((Object)list)).append(ICU_TZVERSION);
                    ((StringBuilder)((Object)list)).append("]");
                    arrstring[0] = ((StringBuilder)((Object)list)).toString();
                }
            }
            this.writeZone((Writer)object2, this.tz, arrstring);
        }
    }

    public void write(Writer writer, long l) throws IOException {
        Object[] arrobject = this.tz.getTimeZoneRules(l);
        RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(this.tz.getID(), (InitialTimeZoneRule)arrobject[0]);
        for (int i = 1; i < arrobject.length; ++i) {
            ruleBasedTimeZone.addTransitionRule(arrobject[i]);
        }
        StringBuilder stringBuilder = null;
        arrobject = stringBuilder;
        if (this.olsonzid != null) {
            arrobject = stringBuilder;
            if (ICU_TZVERSION != null) {
                arrobject = new String[1];
                stringBuilder = new StringBuilder();
                stringBuilder.append("X-TZINFO:");
                stringBuilder.append(this.olsonzid);
                stringBuilder.append("[");
                stringBuilder.append(ICU_TZVERSION);
                stringBuilder.append("/Partial@");
                stringBuilder.append(l);
                stringBuilder.append("]");
                arrobject[0] = stringBuilder.toString();
            }
        }
        this.writeZone(writer, ruleBasedTimeZone, (String[])arrobject);
    }

    public void writeSimple(Writer writer, long l) throws IOException {
        Object[] arrobject = this.tz.getSimpleTimeZoneRulesNear(l);
        RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone(this.tz.getID(), (InitialTimeZoneRule)arrobject[0]);
        for (int i = 1; i < arrobject.length; ++i) {
            ruleBasedTimeZone.addTransitionRule(arrobject[i]);
        }
        StringBuilder stringBuilder = null;
        arrobject = stringBuilder;
        if (this.olsonzid != null) {
            arrobject = stringBuilder;
            if (ICU_TZVERSION != null) {
                arrobject = new String[1];
                stringBuilder = new StringBuilder();
                stringBuilder.append("X-TZINFO:");
                stringBuilder.append(this.olsonzid);
                stringBuilder.append("[");
                stringBuilder.append(ICU_TZVERSION);
                stringBuilder.append("/Simple@");
                stringBuilder.append(l);
                stringBuilder.append("]");
                arrobject[0] = stringBuilder.toString();
            }
        }
        this.writeZone(writer, ruleBasedTimeZone, (String[])arrobject);
    }
}

