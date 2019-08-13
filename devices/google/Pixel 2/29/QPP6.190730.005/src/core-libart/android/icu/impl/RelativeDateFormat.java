/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.UResource;
import android.icu.lang.UCharacter;
import android.icu.text.BreakIterator;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.icu.text.MessageFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.Serializable;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;

public class RelativeDateFormat
extends DateFormat {
    private static final long serialVersionUID = 1131984966440549435L;
    private transient BreakIterator capitalizationBrkIter;
    private boolean capitalizationInfoIsSet;
    private boolean capitalizationOfRelativeUnitsForListOrMenu;
    private boolean capitalizationOfRelativeUnitsForStandAlone;
    private boolean combinedFormatHasDateAtStart;
    private MessageFormat fCombinedFormat;
    private DateFormat fDateFormat;
    private String fDatePattern;
    int fDateStyle;
    private SimpleDateFormat fDateTimeFormat;
    private transient List<URelativeString> fDates;
    ULocale fLocale;
    private DateFormat fTimeFormat;
    private String fTimePattern;
    int fTimeStyle;

    public RelativeDateFormat(int n, int n2, ULocale serializable, Calendar cloneable) {
        block8 : {
            block7 : {
                block5 : {
                    block6 : {
                        this.fDateTimeFormat = null;
                        this.fDatePattern = null;
                        this.fTimePattern = null;
                        this.fDates = null;
                        this.combinedFormatHasDateAtStart = false;
                        this.capitalizationInfoIsSet = false;
                        this.capitalizationOfRelativeUnitsForListOrMenu = false;
                        this.capitalizationOfRelativeUnitsForStandAlone = false;
                        this.capitalizationBrkIter = null;
                        this.calendar = cloneable;
                        this.fLocale = serializable;
                        this.fTimeStyle = n;
                        n = this.fDateStyle = n2;
                        if (n == -1) break block5;
                        cloneable = DateFormat.getDateInstance(n & -129, serializable);
                        if (!(cloneable instanceof SimpleDateFormat)) break block6;
                        this.fDateTimeFormat = (SimpleDateFormat)cloneable;
                        this.fDatePattern = this.fDateTimeFormat.toPattern();
                        n = this.fTimeStyle;
                        if (n != -1 && (serializable = DateFormat.getTimeInstance(n & -129, serializable)) instanceof SimpleDateFormat) {
                            this.fTimePattern = ((SimpleDateFormat)serializable).toPattern();
                        }
                        break block7;
                    }
                    throw new IllegalArgumentException("Can't create SimpleDateFormat for date style");
                }
                if (!((serializable = DateFormat.getTimeInstance(this.fTimeStyle & -129, serializable)) instanceof SimpleDateFormat)) break block8;
                this.fDateTimeFormat = (SimpleDateFormat)serializable;
                this.fTimePattern = this.fDateTimeFormat.toPattern();
            }
            this.initializeCalendar(null, this.fLocale);
            this.loadDates();
            this.initializeCombinedFormat(this.calendar, this.fLocale);
            return;
        }
        throw new IllegalArgumentException("Can't create SimpleDateFormat for time style");
    }

    private static int dayDifference(Calendar calendar) {
        Calendar calendar2 = (Calendar)calendar.clone();
        Date date = new Date(System.currentTimeMillis());
        calendar2.clear();
        calendar2.setTime(date);
        return calendar.get(20) - calendar2.get(20);
    }

    private String getStringForDay(int n) {
        if (this.fDates == null) {
            this.loadDates();
        }
        for (URelativeString uRelativeString : this.fDates) {
            if (uRelativeString.offset != n) continue;
            return uRelativeString.string;
        }
        return null;
    }

    private void initCapitalizationContextInfo(ULocale arrn) {
        block5 : {
            arrn = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)arrn);
            arrn = arrn.getWithFallback("contextTransforms/relative").getIntVector();
            if (arrn.length < 2) break block5;
            boolean bl = false;
            boolean bl2 = arrn[0] != 0;
            this.capitalizationOfRelativeUnitsForListOrMenu = bl2;
            bl2 = bl;
            if (arrn[1] != 0) {
                bl2 = true;
            }
            try {
                this.capitalizationOfRelativeUnitsForStandAlone = bl2;
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
    }

    private Calendar initializeCalendar(TimeZone timeZone, ULocale uLocale) {
        if (this.calendar == null) {
            this.calendar = timeZone == null ? Calendar.getInstance(uLocale) : Calendar.getInstance(timeZone, uLocale);
        }
        return this.calendar;
    }

    private MessageFormat initializeCombinedFormat(Calendar object, ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale);
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("calendar/");
        ((StringBuilder)object2).append(((Calendar)object).getType());
        ((StringBuilder)object2).append("/DateTimePatterns");
        ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.findWithFallback(((StringBuilder)object2).toString());
        object2 = iCUResourceBundle2;
        if (iCUResourceBundle2 == null) {
            object2 = iCUResourceBundle2;
            if (!((Calendar)object).getType().equals("gregorian")) {
                object2 = iCUResourceBundle.findWithFallback("calendar/gregorian/DateTimePatterns");
            }
        }
        if (object2 != null && ((UResourceBundle)object2).getSize() >= 9) {
            int n;
            int n2 = n = 8;
            if (((UResourceBundle)object2).getSize() >= 13) {
                n2 = this.fDateStyle;
                if (n2 >= 0 && n2 <= 3) {
                    n2 = 8 + (n2 + 1);
                } else {
                    int n3 = this.fDateStyle;
                    n2 = n;
                    if (n3 >= 128) {
                        n2 = n;
                        if (n3 <= 131) {
                            n2 = 8 + (n3 + 1 - 128);
                        }
                    }
                }
            }
            object = ((UResourceBundle)object2).get(n2).getType() == 8 ? ((UResourceBundle)object2).get(n2).getString(0) : ((UResourceBundle)object2).getString(n2);
        } else {
            object = "{1} {0}";
        }
        this.combinedFormatHasDateAtStart = ((String)object).startsWith("{1}");
        this.fCombinedFormat = new MessageFormat((String)object, uLocale);
        return this.fCombinedFormat;
    }

    private void loadDates() {
        synchronized (this) {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", this.fLocale);
            Object object = new ArrayList();
            this.fDates = object;
            object = new RelDateFmtDataSink();
            iCUResourceBundle.getAllItemsWithFallback("fields/day/relative", (UResource.Sink)object);
            return;
        }
    }

    @Override
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        Object object;
        Object object2;
        Object object3;
        block17 : {
            block18 : {
                block19 : {
                    block20 : {
                        object = null;
                        object3 = this.getContext(DisplayContext.Type.CAPITALIZATION);
                        if (this.fDateStyle != -1) {
                            object = this.getStringForDay(RelativeDateFormat.dayDifference(calendar));
                        }
                        object2 = object;
                        if (this.fDateTimeFormat == null) break block17;
                        if (object == null || this.fDatePattern == null || this.fTimePattern != null && this.fCombinedFormat != null && !this.combinedFormatHasDateAtStart) break block18;
                        object2 = object;
                        if (((String)object).length() <= 0) break block19;
                        object2 = object;
                        if (!UCharacter.isLowerCase(((String)object).codePointAt(0))) break block19;
                        if (object3 == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || object3 == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu) break block20;
                        object2 = object;
                        if (object3 != DisplayContext.CAPITALIZATION_FOR_STANDALONE) break block19;
                        object2 = object;
                        if (!this.capitalizationOfRelativeUnitsForStandAlone) break block19;
                    }
                    if (this.capitalizationBrkIter == null) {
                        this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
                    }
                    object2 = UCharacter.toTitleCase(this.fLocale, (String)object, this.capitalizationBrkIter, 768);
                }
                this.fDateTimeFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
                break block17;
            }
            this.fDateTimeFormat.setContext((DisplayContext)((Object)object3));
            object2 = object;
        }
        if (this.fDateTimeFormat != null && (this.fDatePattern != null || this.fTimePattern != null)) {
            object = this.fDatePattern;
            if (object == null) {
                this.fDateTimeFormat.applyPattern(this.fTimePattern);
                this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
            } else if (this.fTimePattern == null) {
                if (object2 != null) {
                    stringBuffer.append((String)object2);
                } else {
                    this.fDateTimeFormat.applyPattern((String)object);
                    this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
                }
            } else {
                object = this.fDatePattern;
                if (object2 != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("'");
                    ((StringBuilder)object).append(((String)object2).replace("'", "''"));
                    ((StringBuilder)object).append("'");
                    object = ((StringBuilder)object).toString();
                }
                object3 = new StringBuffer("");
                MessageFormat messageFormat = this.fCombinedFormat;
                object2 = this.fTimePattern;
                FieldPosition fieldPosition2 = new FieldPosition(0);
                messageFormat.format(new Object[]{object2, object}, (StringBuffer)object3, fieldPosition2);
                this.fDateTimeFormat.applyPattern(((StringBuffer)object3).toString());
                this.fDateTimeFormat.format(calendar, stringBuffer, fieldPosition);
            }
        } else {
            object = this.fDateFormat;
            if (object != null) {
                if (object2 != null) {
                    stringBuffer.append((String)object2);
                } else {
                    ((DateFormat)object).format(calendar, stringBuffer, fieldPosition);
                }
            }
        }
        return stringBuffer;
    }

    @Override
    public void parse(String string, Calendar calendar, ParsePosition parsePosition) {
        throw new UnsupportedOperationException("Relative Date parse is not implemented yet");
    }

    @Override
    public void setContext(DisplayContext displayContext) {
        super.setContext(displayContext);
        if (!(this.capitalizationInfoIsSet || displayContext != DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && displayContext != DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            this.initCapitalizationContextInfo(this.fLocale);
            this.capitalizationInfoIsSet = true;
        }
        if (this.capitalizationBrkIter == null && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone)) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
        }
    }

    private final class RelDateFmtDataSink
    extends UResource.Sink {
        private RelDateFmtDataSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            if (value.getType() == 3) {
                return;
            }
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2;
                try {
                    n2 = Integer.parseInt(key.toString());
                }
                catch (NumberFormatException numberFormatException) {
                    return;
                }
                if (RelativeDateFormat.this.getStringForDay(n2) == null) {
                    URelativeString uRelativeString = new URelativeString(n2, value.getString());
                    RelativeDateFormat.this.fDates.add(uRelativeString);
                }
                ++n;
            }
        }
    }

    public static class URelativeString {
        public int offset;
        public String string;

        URelativeString(int n, String string) {
            this.offset = n;
            this.string = string;
        }

        URelativeString(String string, String string2) {
            this.offset = Integer.parseInt(string);
            this.string = string2;
        }
    }

}

