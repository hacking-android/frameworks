/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUCache;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleCache;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.text.DateFormat;
import android.icu.text.DateIntervalInfo;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.MessageFormat;
import android.icu.text.MessagePattern;
import android.icu.text.SimpleDateFormat;
import android.icu.text.UFormat;
import android.icu.util.Calendar;
import android.icu.util.DateInterval;
import android.icu.util.Output;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateIntervalFormat
extends UFormat {
    private static ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> LOCAL_PATTERN_CACHE = new SimpleCache<String, Map<String, DateIntervalInfo.PatternInfo>>();
    private static final long serialVersionUID = 1L;
    private SimpleDateFormat fDateFormat;
    private String fDatePattern = null;
    private String fDateTimeFormat = null;
    private Calendar fFromCalendar;
    private DateIntervalInfo fInfo;
    private transient Map<String, DateIntervalInfo.PatternInfo> fIntervalPatterns = null;
    private String fSkeleton = null;
    private String fTimePattern = null;
    private Calendar fToCalendar;
    private boolean isDateIntervalInfoDefault;

    @UnsupportedAppUsage
    private DateIntervalFormat() {
    }

    @Deprecated
    public DateIntervalFormat(String string, DateIntervalInfo dateIntervalInfo, SimpleDateFormat simpleDateFormat) {
        this.fDateFormat = simpleDateFormat;
        dateIntervalInfo.freeze();
        this.fSkeleton = string;
        this.fInfo = dateIntervalInfo;
        this.isDateIntervalInfoDefault = false;
        this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.initializePattern(null);
    }

    private DateIntervalFormat(String string, ULocale uLocale, SimpleDateFormat simpleDateFormat) {
        this.fDateFormat = simpleDateFormat;
        this.fSkeleton = string;
        this.fInfo = new DateIntervalInfo(uLocale).freeze();
        this.isDateIntervalInfoDefault = true;
        this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
        this.initializePattern(LOCAL_PATTERN_CACHE);
    }

    private static String adjustFieldWidth(String string, String charSequence, String string2, int n) {
        if (string2 == null) {
            return null;
        }
        int[] arrn = new int[58];
        int[] arrn2 = new int[58];
        DateIntervalInfo.parseSkeleton(string, arrn);
        DateIntervalInfo.parseSkeleton((String)charSequence, arrn2);
        string = string2;
        if (n == 2) {
            string = string2.replace('v', 'z');
        }
        charSequence = new StringBuilder(string);
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = ((StringBuilder)charSequence).length();
        n = 0;
        int n6 = n3;
        while (n < n5) {
            int n7;
            int n8;
            int n9;
            block17 : {
                block18 : {
                    char c;
                    block19 : {
                        block16 : {
                            c = ((StringBuilder)charSequence).charAt(n);
                            if (c != n6 && n4 > 0) {
                                n3 = n7 = n6;
                                if (n7 == 76) {
                                    n3 = 77;
                                }
                                n7 = arrn2[n3 - 65];
                                n3 = arrn[n3 - 65];
                                if (n7 == n4 && n3 > n7) {
                                    for (n4 = 0; n4 < (n3 -= n7); ++n4) {
                                        ((StringBuilder)charSequence).insert(n, (char)n6);
                                    }
                                    n += n3;
                                    n5 += n3;
                                }
                                n4 = 0;
                                n3 = n5;
                            } else {
                                n3 = n5;
                            }
                            if (c != '\'') break block16;
                            if (n + 1 < ((StringBuilder)charSequence).length() && ((StringBuilder)charSequence).charAt(n + 1) == '\'') {
                                n8 = n + 1;
                                n7 = n2;
                                n9 = n6;
                                n5 = n4;
                            } else {
                                n5 = n2 == 0 ? 1 : 0;
                                n7 = n5;
                                n9 = n6;
                                n5 = n4;
                                n8 = n;
                            }
                            break block17;
                        }
                        if (n2 != 0) break block18;
                        if (c >= 'a' && c <= 'z') break block19;
                        n7 = n2;
                        n9 = n6;
                        n5 = n4;
                        n8 = n;
                        if (c < 'A') break block17;
                        n7 = n2;
                        n9 = n6;
                        n5 = n4;
                        n8 = n;
                        if (c > 'Z') break block17;
                    }
                    n5 = c;
                    n7 = n2;
                    n9 = n5;
                    n5 = ++n4;
                    n8 = n;
                    break block17;
                }
                n8 = n;
                n5 = n4;
                n9 = n6;
                n7 = n2;
            }
            n = n8 + 1;
            n2 = n7;
            n6 = n9;
            n4 = n5;
            n5 = n3;
        }
        if (n4 > 0) {
            n = n5 = n6;
            if (n5 == 76) {
                n = 77;
            }
            n5 = arrn2[n - 65];
            n2 = arrn[n - 65];
            if (n5 == n4 && n2 > n5) {
                for (n = 0; n < n2 - n5; ++n) {
                    ((StringBuilder)charSequence).append((char)n6);
                }
            }
        }
        return ((StringBuilder)charSequence).toString();
    }

    private void adjustPosition(String string, String string2, FieldPosition fieldPosition, String string3, FieldPosition fieldPosition2, FieldPosition fieldPosition3) {
        int n = string.indexOf("{0}");
        int n2 = string.indexOf("{1}");
        if (n >= 0 && n2 >= 0) {
            if (n < n2) {
                if (fieldPosition.getEndIndex() > 0) {
                    fieldPosition3.setBeginIndex(fieldPosition.getBeginIndex() + n);
                    fieldPosition3.setEndIndex(fieldPosition.getEndIndex() + n);
                } else if (fieldPosition2.getEndIndex() > 0) {
                    fieldPosition3.setBeginIndex(fieldPosition2.getBeginIndex() + (n2 += string2.length() - 3));
                    fieldPosition3.setEndIndex(fieldPosition2.getEndIndex() + n2);
                }
            } else if (fieldPosition2.getEndIndex() > 0) {
                fieldPosition3.setBeginIndex(fieldPosition2.getBeginIndex() + n2);
                fieldPosition3.setEndIndex(fieldPosition2.getEndIndex() + n2);
            } else if (fieldPosition.getEndIndex() > 0) {
                n2 = n + (string3.length() - 3);
                fieldPosition3.setBeginIndex(fieldPosition.getBeginIndex() + n2);
                fieldPosition3.setEndIndex(fieldPosition.getEndIndex() + n2);
            }
            return;
        }
    }

    private void concatSingleDate2TimeInterval(String object, String string, int n, Map<String, DateIntervalInfo.PatternInfo> map) {
        DateIntervalInfo.PatternInfo patternInfo = map.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]);
        if (patternInfo != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(patternInfo.getFirstPart());
            stringBuilder.append(patternInfo.getSecondPart());
            object = SimpleFormatterImpl.formatRawPattern((String)object, 2, 2, stringBuilder.toString(), string);
            object = DateIntervalInfo.genPatternInfo((String)object, patternInfo.firstDateInPtnIsLaterDate());
            map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], (DateIntervalInfo.PatternInfo)object);
        }
    }

    private final StringBuffer fallbackFormat(Calendar object, Calendar object2, boolean bl, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        String string;
        boolean bl2 = bl && this.fDatePattern != null && this.fTimePattern != null;
        if (bl2) {
            string = this.fDateFormat.toPattern();
            this.fDateFormat.applyPattern(this.fTimePattern);
        } else {
            string = null;
        }
        Object object3 = new FieldPosition(fieldPosition.getField());
        StringBuffer stringBuffer2 = new StringBuffer(64);
        stringBuffer2 = this.fDateFormat.format((Calendar)object, stringBuffer2, fieldPosition);
        CharSequence charSequence = new StringBuffer(64);
        object2 = this.fDateFormat.format((Calendar)object2, (StringBuffer)charSequence, (FieldPosition)object3);
        charSequence = this.fInfo.getFallbackIntervalPattern();
        this.adjustPosition((String)charSequence, stringBuffer2.toString(), fieldPosition, ((StringBuffer)object2).toString(), (FieldPosition)object3, fieldPosition);
        object2 = SimpleFormatterImpl.formatRawPattern((String)charSequence, 2, 2, new CharSequence[]{stringBuffer2, object2});
        if (bl2) {
            this.fDateFormat.applyPattern(this.fDatePattern);
            stringBuffer2 = new StringBuffer(64);
            ((FieldPosition)object3).setBeginIndex(0);
            ((FieldPosition)object3).setEndIndex(0);
            object = this.fDateFormat.format((Calendar)object, stringBuffer2, (FieldPosition)object3);
            this.adjustPosition(this.fDateTimeFormat, (String)object2, fieldPosition, ((StringBuffer)object).toString(), (FieldPosition)object3, fieldPosition);
            object3 = new MessageFormat("");
            ((MessageFormat)object3).applyPattern(this.fDateTimeFormat, MessagePattern.ApostropheMode.DOUBLE_REQUIRED);
            stringBuffer2 = new StringBuffer(128);
            fieldPosition = new FieldPosition(0);
            object = ((MessageFormat)object3).format(new Object[]{object2, object}, stringBuffer2, fieldPosition).toString();
        } else {
            object = object2;
        }
        stringBuffer.append((String)object);
        if (bl2) {
            this.fDateFormat.applyPattern(string);
        }
        return stringBuffer;
    }

    private final StringBuffer fallbackFormat(Calendar calendar, Calendar calendar2, boolean bl, StringBuffer stringBuffer, FieldPosition fieldPosition, String string) {
        String string2 = this.fDateFormat.toPattern();
        this.fDateFormat.applyPattern(string);
        this.fallbackFormat(calendar, calendar2, bl, stringBuffer, fieldPosition);
        this.fDateFormat.applyPattern(string2);
        return stringBuffer;
    }

    private static boolean fieldExistsInSkeleton(int n, String string) {
        boolean bl = string.indexOf(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]) != -1;
        return bl;
    }

    private void genFallbackPattern(int n, String object, Map<String, DateIntervalInfo.PatternInfo> map, DateTimePatternGenerator dateTimePatternGenerator) {
        object = new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern((String)object), this.fInfo.getDefaultOrder());
        map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], (DateIntervalInfo.PatternInfo)object);
    }

    private SkeletonAndItsBestMatch genIntervalPattern(int n, String object, String object2, int n2, Map<String, DateIntervalInfo.PatternInfo> map) {
        DateIntervalInfo.PatternInfo patternInfo = null;
        Object object3 = this.fInfo.getIntervalPattern((String)object2, n);
        Object object4 = patternInfo;
        Object object5 = object3;
        Object object6 = object;
        Object object7 = object2;
        int n3 = n2;
        if (object3 == null) {
            if (SimpleDateFormat.isFieldUnitIgnored((String)object2, n)) {
                object = new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), null, this.fInfo.getDefaultOrder());
                map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], (DateIntervalInfo.PatternInfo)object);
                return null;
            }
            if (n == 9) {
                object = this.fInfo.getIntervalPattern((String)object2, 10);
                if (object != null) {
                    map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], (DateIntervalInfo.PatternInfo)object);
                }
                return null;
            }
            object7 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n];
            object5 = new StringBuilder();
            ((StringBuilder)object5).append((String)object7);
            ((StringBuilder)object5).append((String)object2);
            object5 = ((StringBuilder)object5).toString();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object7);
            ((StringBuilder)object2).append((String)object);
            object3 = ((StringBuilder)object2).toString();
            object2 = object4 = this.fInfo.getIntervalPattern((String)object5, n);
            object = object5;
            int n4 = n2;
            if (object4 == null) {
                object2 = object4;
                object = object5;
                n4 = n2;
                if (n2 == 0) {
                    object = this.fInfo.getBestSkeleton((String)object3);
                    object7 = ((BestMatchInfo)object).bestMatchSkeleton;
                    n2 = ((BestMatchInfo)object).bestMatchDistanceInfo;
                    object2 = object4;
                    object = object5;
                    n4 = n2;
                    if (((String)object7).length() != 0) {
                        object2 = object4;
                        object = object5;
                        n4 = n2;
                        if (n2 != -1) {
                            object2 = this.fInfo.getIntervalPattern((String)object7, n);
                            object = object7;
                            n4 = n2;
                        }
                    }
                }
            }
            object4 = patternInfo;
            object5 = object2;
            object6 = object3;
            object7 = object;
            n3 = n4;
            if (object2 != null) {
                object4 = new SkeletonAndItsBestMatch((String)object3, (String)object);
                n3 = n4;
                object7 = object;
                object6 = object3;
                object5 = object2;
            }
        }
        if (object5 != null) {
            object = object5;
            if (n3 != 0) {
                object = DateIntervalFormat.adjustFieldWidth((String)object6, (String)object7, ((DateIntervalInfo.PatternInfo)object5).getFirstPart(), n3);
                object = new DateIntervalInfo.PatternInfo((String)object, DateIntervalFormat.adjustFieldWidth((String)object6, (String)object7, ((DateIntervalInfo.PatternInfo)object5).getSecondPart(), n3), ((DateIntervalInfo.PatternInfo)object5).firstDateInPtnIsLaterDate());
            }
            map.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n], (DateIntervalInfo.PatternInfo)object);
        }
        return object4;
    }

    private boolean genSeparateDateTimePtn(String string, String object, Map<String, DateIntervalInfo.PatternInfo> map, DateTimePatternGenerator dateTimePatternGenerator) {
        Object object2 = ((String)object).length() != 0 ? object : string;
        BestMatchInfo bestMatchInfo = this.fInfo.getBestSkeleton((String)object2);
        String string2 = bestMatchInfo.bestMatchSkeleton;
        int n = bestMatchInfo.bestMatchDistanceInfo;
        if (string.length() != 0) {
            this.fDatePattern = dateTimePatternGenerator.getBestPattern(string);
        }
        if (((String)object).length() != 0) {
            this.fTimePattern = dateTimePatternGenerator.getBestPattern((String)object);
        }
        if (n == -1) {
            return false;
        }
        if (((String)object).length() == 0) {
            this.genIntervalPattern(5, (String)object2, string2, n, map);
            object = this.genIntervalPattern(2, (String)object2, string2, n, map);
            string = string2;
            if (object != null) {
                string = ((SkeletonAndItsBestMatch)object).skeleton;
                object2 = ((SkeletonAndItsBestMatch)object).bestMatchSkeleton;
            }
            this.genIntervalPattern(1, (String)object2, string, n, map);
        } else {
            this.genIntervalPattern(12, (String)object2, string2, n, map);
            this.genIntervalPattern(10, (String)object2, string2, n, map);
            this.genIntervalPattern(9, (String)object2, string2, n, map);
        }
        return true;
    }

    private String getConcatenationPattern(ULocale object) {
        if (((UResourceBundle)(object = (ICUResourceBundle)((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object)).getWithFallback("calendar/gregorian/DateTimePatterns").get(8))).getType() == 0) {
            return ((UResourceBundle)object).getString();
        }
        return ((UResourceBundle)object).getString(0);
    }

    private static void getDateTimeSkeleton(String string, StringBuilder stringBuilder, StringBuilder stringBuilder2, StringBuilder stringBuilder3, StringBuilder stringBuilder4) {
        int n;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        block14 : for (n = 0; n < string.length(); ++n) {
            char c = string.charAt(n);
            switch (c) {
                default: {
                    continue block14;
                }
                case 'z': {
                    ++n10;
                    stringBuilder3.append(c);
                    continue block14;
                }
                case 'y': {
                    stringBuilder.append(c);
                    ++n5;
                    continue block14;
                }
                case 'v': {
                    ++n9;
                    stringBuilder3.append(c);
                    continue block14;
                }
                case 'm': {
                    stringBuilder3.append(c);
                    ++n8;
                    continue block14;
                }
                case 'h': {
                    stringBuilder3.append(c);
                    ++n6;
                    continue block14;
                }
                case 'd': {
                    stringBuilder.append(c);
                    ++n3;
                    continue block14;
                }
                case 'a': {
                    stringBuilder3.append(c);
                    continue block14;
                }
                case 'M': {
                    stringBuilder.append(c);
                    ++n4;
                    continue block14;
                }
                case 'H': {
                    stringBuilder3.append(c);
                    ++n7;
                    continue block14;
                }
                case 'E': {
                    stringBuilder.append(c);
                    ++n2;
                    continue block14;
                }
                case 'D': 
                case 'F': 
                case 'G': 
                case 'L': 
                case 'Q': 
                case 'U': 
                case 'W': 
                case 'Y': 
                case 'c': 
                case 'e': 
                case 'g': 
                case 'l': 
                case 'q': 
                case 'r': 
                case 'u': 
                case 'w': {
                    stringBuilder2.append(c);
                    stringBuilder.append(c);
                    continue block14;
                }
                case 'A': 
                case 'K': 
                case 'S': 
                case 'V': 
                case 'Z': 
                case 'j': 
                case 'k': 
                case 's': {
                    stringBuilder3.append(c);
                    stringBuilder4.append(c);
                }
            }
        }
        if (n5 != 0) {
            for (n = 0; n < n5; ++n) {
                stringBuilder2.append('y');
            }
        }
        if (n4 != 0) {
            if (n4 < 3) {
                stringBuilder2.append('M');
            } else {
                for (n = 0; n < n4 && n < 5; ++n) {
                    stringBuilder2.append('M');
                }
            }
        }
        if (n2 != 0) {
            if (n2 <= 3) {
                stringBuilder2.append('E');
            } else {
                for (n = 0; n < n2 && n < 5; ++n) {
                    stringBuilder2.append('E');
                }
            }
        }
        if (n3 != 0) {
            stringBuilder2.append('d');
        }
        if (n7 != 0) {
            stringBuilder4.append('H');
        } else if (n6 != 0) {
            stringBuilder4.append('h');
        }
        if (n8 != 0) {
            stringBuilder4.append('m');
        }
        if (n10 != 0) {
            stringBuilder4.append('z');
        }
        if (n9 != 0) {
            stringBuilder4.append('v');
        }
    }

    public static final DateIntervalFormat getInstance(String string) {
        return DateIntervalFormat.getInstance(string, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final DateIntervalFormat getInstance(String string, DateIntervalInfo dateIntervalInfo) {
        return DateIntervalFormat.getInstance(string, ULocale.getDefault(ULocale.Category.FORMAT), dateIntervalInfo);
    }

    public static final DateIntervalFormat getInstance(String string, ULocale uLocale) {
        return new DateIntervalFormat(string, uLocale, new SimpleDateFormat(DateTimePatternGenerator.getInstance(uLocale).getBestPattern(string), uLocale));
    }

    public static final DateIntervalFormat getInstance(String string, ULocale uLocale, DateIntervalInfo dateIntervalInfo) {
        return new DateIntervalFormat(string, (DateIntervalInfo)dateIntervalInfo.clone(), new SimpleDateFormat(DateTimePatternGenerator.getInstance(uLocale).getBestPattern(string), uLocale));
    }

    public static final DateIntervalFormat getInstance(String string, Locale locale) {
        return DateIntervalFormat.getInstance(string, ULocale.forLocale(locale));
    }

    public static final DateIntervalFormat getInstance(String string, Locale locale, DateIntervalInfo dateIntervalInfo) {
        return DateIntervalFormat.getInstance(string, ULocale.forLocale(locale), dateIntervalInfo);
    }

    private Map<String, DateIntervalInfo.PatternInfo> initializeIntervalPattern(String object, ULocale object2) {
        DateTimePatternGenerator dateTimePatternGenerator = DateTimePatternGenerator.getInstance((ULocale)object2);
        if (this.fSkeleton == null) {
            this.fSkeleton = dateTimePatternGenerator.getSkeleton((String)object);
        }
        String string = this.fSkeleton;
        HashMap<String, DateIntervalInfo.PatternInfo> hashMap = new HashMap<String, DateIntervalInfo.PatternInfo>();
        StringBuilder stringBuilder = new StringBuilder(string.length());
        CharSequence charSequence = new StringBuilder(string.length());
        StringBuilder stringBuilder2 = new StringBuilder(string.length());
        CharSequence charSequence2 = new StringBuilder(string.length());
        DateIntervalFormat.getDateTimeSkeleton(string, stringBuilder, charSequence, stringBuilder2, charSequence2);
        String string2 = stringBuilder.toString();
        object = stringBuilder2.toString();
        charSequence = charSequence.toString();
        charSequence2 = charSequence2.toString();
        if (stringBuilder2.length() != 0 && stringBuilder.length() != 0) {
            this.fDateTimeFormat = this.getConcatenationPattern((ULocale)object2);
        }
        if (!this.genSeparateDateTimePtn((String)charSequence, (String)charSequence2, hashMap, dateTimePatternGenerator)) {
            if (stringBuilder2.length() != 0 && stringBuilder.length() == 0) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("yMd");
                ((StringBuilder)object2).append((String)object);
                object = new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern(((StringBuilder)object2).toString()), this.fInfo.getDefaultOrder());
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], (DateIntervalInfo.PatternInfo)object);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], (DateIntervalInfo.PatternInfo)object);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], (DateIntervalInfo.PatternInfo)object);
            }
            return hashMap;
        }
        if (stringBuilder2.length() != 0) {
            if (stringBuilder.length() == 0) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("yMd");
                ((StringBuilder)object2).append((String)object);
                object = new DateIntervalInfo.PatternInfo(null, dateTimePatternGenerator.getBestPattern(((StringBuilder)object2).toString()), this.fInfo.getDefaultOrder());
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], (DateIntervalInfo.PatternInfo)object);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], (DateIntervalInfo.PatternInfo)object);
                hashMap.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], (DateIntervalInfo.PatternInfo)object);
            } else {
                object = string;
                if (!DateIntervalFormat.fieldExistsInSkeleton(5, string2)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5]);
                    ((StringBuilder)object).append(string);
                    object = ((StringBuilder)object).toString();
                    this.genFallbackPattern(5, (String)object, hashMap, dateTimePatternGenerator);
                }
                object2 = object;
                if (!DateIntervalFormat.fieldExistsInSkeleton(2, string2)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2]);
                    ((StringBuilder)object2).append((String)object);
                    object2 = ((StringBuilder)object2).toString();
                    this.genFallbackPattern(2, (String)object2, hashMap, dateTimePatternGenerator);
                }
                if (!DateIntervalFormat.fieldExistsInSkeleton(1, string2)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1]);
                    ((StringBuilder)object).append((String)object2);
                    this.genFallbackPattern(1, ((StringBuilder)object).toString(), hashMap, dateTimePatternGenerator);
                }
                if (this.fDateTimeFormat == null) {
                    this.fDateTimeFormat = "{1} {0}";
                }
                object = dateTimePatternGenerator.getBestPattern(string2);
                this.concatSingleDate2TimeInterval(this.fDateTimeFormat, (String)object, 9, hashMap);
                this.concatSingleDate2TimeInterval(this.fDateTimeFormat, (String)object, 10, hashMap);
                this.concatSingleDate2TimeInterval(this.fDateTimeFormat, (String)object, 12, hashMap);
            }
        }
        return hashMap;
    }

    private void initializePattern(ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> iCUCache) {
        String string = this.fDateFormat.toPattern();
        ULocale uLocale = this.fDateFormat.getLocale();
        String string2 = null;
        Map<String, DateIntervalInfo.PatternInfo> map = null;
        if (iCUCache != null) {
            if (this.fSkeleton != null) {
                map = new StringBuilder();
                ((StringBuilder)((Object)map)).append(uLocale.toString());
                ((StringBuilder)((Object)map)).append("+");
                ((StringBuilder)((Object)map)).append(string);
                ((StringBuilder)((Object)map)).append("+");
                ((StringBuilder)((Object)map)).append(this.fSkeleton);
                string2 = ((StringBuilder)((Object)map)).toString();
            } else {
                map = new StringBuilder();
                ((StringBuilder)((Object)map)).append(uLocale.toString());
                ((StringBuilder)((Object)map)).append("+");
                ((StringBuilder)((Object)map)).append(string);
                string2 = ((StringBuilder)((Object)map)).toString();
            }
            map = iCUCache.get(string2);
        }
        Object object = map;
        if (map == null) {
            map = Collections.unmodifiableMap(this.initializeIntervalPattern(string, uLocale));
            object = map;
            if (iCUCache != null) {
                iCUCache.put(string2, map);
                object = map;
            }
        }
        this.fIntervalPatterns = object;
    }

    private void readObject(ObjectInputStream iCUCache) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)((Object)iCUCache)).defaultReadObject();
        iCUCache = this.isDateIntervalInfoDefault ? LOCAL_PATTERN_CACHE : null;
        this.initializePattern(iCUCache);
    }

    @Override
    public Object clone() {
        synchronized (this) {
            DateIntervalFormat dateIntervalFormat = (DateIntervalFormat)super.clone();
            dateIntervalFormat.fDateFormat = (SimpleDateFormat)this.fDateFormat.clone();
            dateIntervalFormat.fInfo = (DateIntervalInfo)this.fInfo.clone();
            dateIntervalFormat.fFromCalendar = (Calendar)this.fFromCalendar.clone();
            dateIntervalFormat.fToCalendar = (Calendar)this.fToCalendar.clone();
            dateIntervalFormat.fDatePattern = this.fDatePattern;
            dateIntervalFormat.fTimePattern = this.fTimePattern;
            dateIntervalFormat.fDateTimeFormat = this.fDateTimeFormat;
            return dateIntervalFormat;
        }
    }

    public final StringBuffer format(Calendar serializable, Calendar object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        synchronized (this) {
            block21 : {
                block30 : {
                    Object object2;
                    DateIntervalInfo.PatternInfo patternInfo;
                    block34 : {
                        block33 : {
                            boolean bl;
                            block31 : {
                                block32 : {
                                    int n;
                                    block23 : {
                                        block29 : {
                                            block28 : {
                                                block27 : {
                                                    block26 : {
                                                        block25 : {
                                                            block24 : {
                                                                block22 : {
                                                                    if (!((Calendar)serializable).isEquivalentTo((Calendar)object)) break block21;
                                                                    if (((Calendar)serializable).get(0) == ((Calendar)object).get(0)) break block22;
                                                                    n = 0;
                                                                    break block23;
                                                                }
                                                                if (((Calendar)serializable).get(1) == ((Calendar)object).get(1)) break block24;
                                                                n = 1;
                                                                break block23;
                                                            }
                                                            if (((Calendar)serializable).get(2) == ((Calendar)object).get(2)) break block25;
                                                            n = 2;
                                                            break block23;
                                                        }
                                                        if (((Calendar)serializable).get(5) == ((Calendar)object).get(5)) break block26;
                                                        n = 5;
                                                        break block23;
                                                    }
                                                    if (((Calendar)serializable).get(9) == ((Calendar)object).get(9)) break block27;
                                                    n = 9;
                                                    break block23;
                                                }
                                                if (((Calendar)serializable).get(10) == ((Calendar)object).get(10)) break block28;
                                                n = 10;
                                                break block23;
                                            }
                                            if (((Calendar)serializable).get(12) == ((Calendar)object).get(12)) break block29;
                                            n = 12;
                                            break block23;
                                        }
                                        if (((Calendar)serializable).get(13) == ((Calendar)object).get(13)) break block30;
                                        n = 13;
                                    }
                                    bl = n == 9 || n == 10 || n == 12 || n == 13;
                                    patternInfo = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]);
                                    if (patternInfo != null) break block31;
                                    if (!this.fDateFormat.isFieldUnitIgnored(n)) break block32;
                                    serializable = this.fDateFormat.format((Calendar)serializable, stringBuffer, fieldPosition);
                                    return serializable;
                                }
                                serializable = this.fallbackFormat((Calendar)serializable, (Calendar)object, bl, stringBuffer, fieldPosition);
                                return serializable;
                            }
                            if (patternInfo.getFirstPart() != null) break block33;
                            serializable = this.fallbackFormat((Calendar)serializable, (Calendar)object, bl, stringBuffer, fieldPosition, patternInfo.getSecondPart());
                            return serializable;
                        }
                        if (patternInfo.firstDateInPtnIsLaterDate()) {
                            object2 = object;
                            break block34;
                        }
                        object2 = serializable;
                        serializable = object;
                    }
                    object = this.fDateFormat.toPattern();
                    this.fDateFormat.applyPattern(patternInfo.getFirstPart());
                    this.fDateFormat.format((Calendar)object2, stringBuffer, fieldPosition);
                    if (patternInfo.getSecondPart() != null) {
                        this.fDateFormat.applyPattern(patternInfo.getSecondPart());
                        object2 = new FieldPosition(fieldPosition.getField());
                        this.fDateFormat.format((Calendar)serializable, stringBuffer, (FieldPosition)object2);
                        if (fieldPosition.getEndIndex() == 0 && ((FieldPosition)object2).getEndIndex() > 0) {
                            fieldPosition.setBeginIndex(((FieldPosition)object2).getBeginIndex());
                            fieldPosition.setEndIndex(((FieldPosition)object2).getEndIndex());
                        }
                    }
                    this.fDateFormat.applyPattern((String)object);
                    return stringBuffer;
                }
                serializable = this.fDateFormat.format((Calendar)serializable, stringBuffer, fieldPosition);
                return serializable;
            }
            serializable = new IllegalArgumentException("can not format on two different calendars");
            throw serializable;
        }
    }

    public final StringBuffer format(DateInterval serializable, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        synchronized (this) {
            this.fFromCalendar.setTimeInMillis(serializable.getFromDate());
            this.fToCalendar.setTimeInMillis(serializable.getToDate());
            serializable = this.format(this.fFromCalendar, this.fToCalendar, stringBuffer, fieldPosition);
            return serializable;
        }
    }

    @Override
    public final StringBuffer format(Object object, StringBuffer abstractStringBuilder, FieldPosition fieldPosition) {
        if (object instanceof DateInterval) {
            return this.format((DateInterval)object, (StringBuffer)abstractStringBuilder, fieldPosition);
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("Cannot format given Object (");
        ((StringBuilder)abstractStringBuilder).append(object.getClass().getName());
        ((StringBuilder)abstractStringBuilder).append(") as a DateInterval");
        throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
    }

    public DateFormat getDateFormat() {
        synchronized (this) {
            DateFormat dateFormat = (DateFormat)this.fDateFormat.clone();
            return dateFormat;
        }
    }

    public DateIntervalInfo getDateIntervalInfo() {
        return (DateIntervalInfo)this.fInfo.clone();
    }

    @Deprecated
    public String getPatterns(Calendar cloneable, Calendar calendar, Output<String> output) {
        block10 : {
            int n;
            block3 : {
                block9 : {
                    block8 : {
                        block7 : {
                            block6 : {
                                block5 : {
                                    block4 : {
                                        block2 : {
                                            if (((Calendar)cloneable).get(0) == calendar.get(0)) break block2;
                                            n = 0;
                                            break block3;
                                        }
                                        if (((Calendar)cloneable).get(1) == calendar.get(1)) break block4;
                                        n = 1;
                                        break block3;
                                    }
                                    if (((Calendar)cloneable).get(2) == calendar.get(2)) break block5;
                                    n = 2;
                                    break block3;
                                }
                                if (((Calendar)cloneable).get(5) == calendar.get(5)) break block6;
                                n = 5;
                                break block3;
                            }
                            if (((Calendar)cloneable).get(9) == calendar.get(9)) break block7;
                            n = 9;
                            break block3;
                        }
                        if (((Calendar)cloneable).get(10) == calendar.get(10)) break block8;
                        n = 10;
                        break block3;
                    }
                    if (((Calendar)cloneable).get(12) == calendar.get(12)) break block9;
                    n = 12;
                    break block3;
                }
                if (((Calendar)cloneable).get(13) == calendar.get(13)) break block10;
                n = 13;
            }
            cloneable = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[n]);
            output.value = ((DateIntervalInfo.PatternInfo)cloneable).getSecondPart();
            return ((DateIntervalInfo.PatternInfo)cloneable).getFirstPart();
        }
        return null;
    }

    @Deprecated
    public Map<String, DateIntervalInfo.PatternInfo> getRawPatterns() {
        return this.fIntervalPatterns;
    }

    public TimeZone getTimeZone() {
        SimpleDateFormat simpleDateFormat = this.fDateFormat;
        if (simpleDateFormat != null) {
            return (TimeZone)simpleDateFormat.getTimeZone().clone();
        }
        return TimeZone.getDefault();
    }

    @Deprecated
    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException("parsing is not supported");
    }

    public void setDateIntervalInfo(DateIntervalInfo dateIntervalInfo) {
        this.fInfo = (DateIntervalInfo)dateIntervalInfo.clone();
        this.isDateIntervalInfoDefault = false;
        this.fInfo.freeze();
        if (this.fDateFormat != null) {
            this.initializePattern(null);
        }
    }

    public void setTimeZone(TimeZone timeZone) {
        timeZone = (TimeZone)timeZone.clone();
        Cloneable cloneable = this.fDateFormat;
        if (cloneable != null) {
            ((DateFormat)cloneable).setTimeZone(timeZone);
        }
        if ((cloneable = this.fFromCalendar) != null) {
            ((Calendar)cloneable).setTimeZone(timeZone);
        }
        if ((cloneable = this.fToCalendar) != null) {
            ((Calendar)cloneable).setTimeZone(timeZone);
        }
    }

    static final class BestMatchInfo {
        final int bestMatchDistanceInfo;
        final String bestMatchSkeleton;

        BestMatchInfo(String string, int n) {
            this.bestMatchSkeleton = string;
            this.bestMatchDistanceInfo = n;
        }
    }

    private static final class SkeletonAndItsBestMatch {
        final String bestMatchSkeleton;
        final String skeleton;

        SkeletonAndItsBestMatch(String string, String string2) {
            this.skeleton = string;
            this.bestMatchSkeleton = string2;
        }
    }

}

