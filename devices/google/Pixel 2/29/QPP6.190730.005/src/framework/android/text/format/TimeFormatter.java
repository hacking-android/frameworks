/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.LocaleData
 *  libcore.util.ZoneInfo
 *  libcore.util.ZoneInfo$WallTime
 */
package android.text.format;

import android.content.res.Resources;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.Formatter;
import java.util.Locale;
import libcore.icu.LocaleData;
import libcore.util.ZoneInfo;

class TimeFormatter {
    private static final int DAYSPERLYEAR = 366;
    private static final int DAYSPERNYEAR = 365;
    private static final int DAYSPERWEEK = 7;
    private static final int FORCE_LOWER_CASE = -1;
    private static final int HOURSPERDAY = 24;
    private static final int MINSPERHOUR = 60;
    private static final int MONSPERYEAR = 12;
    private static final int SECSPERMIN = 60;
    private static String sDateOnlyFormat;
    private static String sDateTimeFormat;
    private static Locale sLocale;
    private static LocaleData sLocaleData;
    private static String sTimeOnlyFormat;
    private final String dateOnlyFormat;
    private final String dateTimeFormat;
    private final LocaleData localeData;
    private Formatter numberFormatter;
    private StringBuilder outputBuilder;
    private final String timeOnlyFormat;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TimeFormatter() {
        synchronized (TimeFormatter.class) {
            Object object = Locale.getDefault();
            if (sLocale == null || !((Locale)object).equals(sLocale)) {
                sLocale = object;
                sLocaleData = LocaleData.get((Locale)object);
                object = Resources.getSystem();
                sTimeOnlyFormat = ((Resources)object).getString(17041128);
                sDateOnlyFormat = ((Resources)object).getString(17040449);
                sDateTimeFormat = ((Resources)object).getString(17039815);
            }
            this.dateTimeFormat = sDateTimeFormat;
            this.timeOnlyFormat = sTimeOnlyFormat;
            this.dateOnlyFormat = sDateOnlyFormat;
            this.localeData = sLocaleData;
            return;
        }
    }

    private static boolean brokenIsLower(char c) {
        boolean bl = c >= 'a' && c <= 'z';
        return bl;
    }

    private static boolean brokenIsUpper(char c) {
        boolean bl = c >= 'A' && c <= 'Z';
        return bl;
    }

    private static char brokenToLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char)(c - 65 + 97);
        }
        return c;
    }

    private static char brokenToUpper(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char)(c - 97 + 65);
        }
        return c;
    }

    private void formatInternal(String charSequence, ZoneInfo.WallTime wallTime, ZoneInfo zoneInfo) {
        charSequence = CharBuffer.wrap(charSequence);
        while (((Buffer)((Object)charSequence)).remaining() > 0) {
            boolean bl = true;
            if (((CharBuffer)charSequence).get(((Buffer)((Object)charSequence)).position()) == '%') {
                bl = this.handleToken((CharBuffer)charSequence, wallTime, zoneInfo);
            }
            if (bl) {
                this.outputBuilder.append(((CharBuffer)charSequence).get(((Buffer)((Object)charSequence)).position()));
            }
            ((Buffer)((Object)charSequence)).position(((Buffer)((Object)charSequence)).position() + 1);
        }
    }

    private static String getFormat(int n, String string2, String string3, String string4, String string5) {
        if (n != 45) {
            if (n != 48) {
                if (n != 95) {
                    return string2;
                }
                return string3;
            }
            return string5;
        }
        return string4;
    }

    /*
     * Exception decompiling
     */
    private boolean handleToken(CharBuffer var1_1, ZoneInfo.WallTime var2_2, ZoneInfo var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static boolean isLeap(int n) {
        boolean bl = n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
        return bl;
    }

    private String localizeDigits(String string2) {
        int n = string2.length();
        char c = this.localeData.zeroDigit;
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c2;
            char c3 = c2 = string2.charAt(i);
            if (c2 >= '0') {
                c3 = c2;
                if (c2 <= '9') {
                    c3 = c2 = (char)(c2 + (c - 48));
                }
            }
            stringBuilder.append(c3);
        }
        return stringBuilder.toString();
    }

    private void modifyAndAppend(CharSequence charSequence, int n) {
        if (n != -1) {
            if (n != 35) {
                if (n != 94) {
                    this.outputBuilder.append(charSequence);
                } else {
                    for (n = 0; n < charSequence.length(); ++n) {
                        this.outputBuilder.append(TimeFormatter.brokenToUpper(charSequence.charAt(n)));
                    }
                }
            } else {
                for (n = 0; n < charSequence.length(); ++n) {
                    char c;
                    char c2;
                    char c3 = charSequence.charAt(n);
                    if (TimeFormatter.brokenIsUpper(c3)) {
                        c = c2 = TimeFormatter.brokenToLower(c3);
                    } else {
                        c = c3;
                        if (TimeFormatter.brokenIsLower(c3)) {
                            c = c2 = TimeFormatter.brokenToUpper(c3);
                        }
                    }
                    this.outputBuilder.append(c);
                }
            }
        } else {
            for (n = 0; n < charSequence.length(); ++n) {
                this.outputBuilder.append(TimeFormatter.brokenToLower(charSequence.charAt(n)));
            }
        }
    }

    private void outputYear(int n, boolean bl, boolean bl2, int n2) {
        int n3 = n % 100;
        int n4 = n / 100 + n3 / 100;
        int n5 = n3 % 100;
        if (n5 < 0 && n4 > 0) {
            n = n5 + 100;
            n3 = n4 - 1;
        } else {
            n = n5;
            n3 = n4;
            if (n4 < 0) {
                n = n5;
                n3 = n4;
                if (n5 > 0) {
                    n = n5 - 100;
                    n3 = n4 + 1;
                }
            }
        }
        if (bl) {
            if (n3 == 0 && n < 0) {
                this.outputBuilder.append("-0");
            } else {
                this.numberFormatter.format(TimeFormatter.getFormat(n2, "%02d", "%2d", "%d", "%02d"), n3);
            }
        }
        if (bl2) {
            if (n < 0) {
                n = -n;
            }
            this.numberFormatter.format(TimeFormatter.getFormat(n2, "%02d", "%2d", "%d", "%02d"), n);
        }
    }

    public String format(String string2, ZoneInfo.WallTime object, ZoneInfo zoneInfo) {
        try {
            StringBuilder stringBuilder;
            Formatter formatter;
            this.outputBuilder = stringBuilder = new StringBuilder();
            this.numberFormatter = formatter = new Formatter(stringBuilder, Locale.US);
            this.formatInternal(string2, (ZoneInfo.WallTime)object, zoneInfo);
            object = stringBuilder.toString();
            string2 = object;
            if (this.localeData.zeroDigit != '0') {
                string2 = this.localizeDigits((String)object);
            }
            return string2;
        }
        finally {
            this.outputBuilder = null;
            this.numberFormatter = null;
        }
    }
}

