/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.PatternProps;
import android.icu.impl.SoftCache;
import android.icu.impl.TZDBTimeZoneNames;
import android.icu.impl.TextTrieMap;
import android.icu.impl.TimeZoneGenericNames;
import android.icu.impl.TimeZoneNamesImpl;
import android.icu.impl.ZoneMeta;
import android.icu.lang.UCharacter;
import android.icu.text.DateFormat;
import android.icu.text.NumberingSystem;
import android.icu.text.TimeZoneNames;
import android.icu.text.UFormat;
import android.icu.util.Calendar;
import android.icu.util.Freezable;
import android.icu.util.Output;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;

public class TimeZoneFormat
extends UFormat
implements Freezable<TimeZoneFormat>,
Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final EnumSet<TimeZoneGenericNames.GenericNameType> ALL_GENERIC_NAME_TYPES;
    private static final EnumSet<TimeZoneNames.NameType> ALL_SIMPLE_NAME_TYPES;
    private static final String[] ALT_GMT_STRINGS;
    private static final String ASCII_DIGITS = "0123456789";
    private static final String[] DEFAULT_GMT_DIGITS;
    private static final char DEFAULT_GMT_OFFSET_SEP = ':';
    private static final String DEFAULT_GMT_PATTERN = "GMT{0}";
    private static final String DEFAULT_GMT_ZERO = "GMT";
    private static final String ISO8601_UTC = "Z";
    private static final int ISO_LOCAL_STYLE_FLAG = 256;
    private static final int ISO_Z_STYLE_FLAG = 128;
    private static final int MAX_OFFSET = 86400000;
    private static final int MAX_OFFSET_HOUR = 23;
    private static final int MAX_OFFSET_MINUTE = 59;
    private static final int MAX_OFFSET_SECOND = 59;
    private static final int MILLIS_PER_HOUR = 3600000;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int MILLIS_PER_SECOND = 1000;
    private static final GMTOffsetPatternType[] PARSE_GMT_OFFSET_TYPES;
    private static volatile TextTrieMap<String> SHORT_ZONE_ID_TRIE;
    private static final String TZID_GMT = "Etc/GMT";
    private static final String UNKNOWN_LOCATION = "Unknown";
    private static final int UNKNOWN_OFFSET = Integer.MAX_VALUE;
    private static final String UNKNOWN_SHORT_ZONE_ID = "unk";
    private static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
    private static volatile TextTrieMap<String> ZONE_ID_TRIE;
    private static TimeZoneFormatCache _tzfCache;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 2281246852693575022L;
    private transient boolean _abuttingOffsetHoursAndMinutes;
    private volatile transient boolean _frozen;
    private String[] _gmtOffsetDigits;
    private transient Object[][] _gmtOffsetPatternItems;
    private String[] _gmtOffsetPatterns;
    private String _gmtPattern;
    private transient String _gmtPatternPrefix;
    private transient String _gmtPatternSuffix;
    private String _gmtZeroFormat;
    private volatile transient TimeZoneGenericNames _gnames;
    private ULocale _locale;
    private boolean _parseAllStyles;
    private boolean _parseTZDBNames;
    private transient String _region;
    private volatile transient TimeZoneNames _tzdbNames;
    private TimeZoneNames _tznames;

    static {
        ALT_GMT_STRINGS = new String[]{DEFAULT_GMT_ZERO, "UTC", "UT"};
        DEFAULT_GMT_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        PARSE_GMT_OFFSET_TYPES = new GMTOffsetPatternType[]{GMTOffsetPatternType.POSITIVE_HMS, GMTOffsetPatternType.NEGATIVE_HMS, GMTOffsetPatternType.POSITIVE_HM, GMTOffsetPatternType.NEGATIVE_HM, GMTOffsetPatternType.POSITIVE_H, GMTOffsetPatternType.NEGATIVE_H};
        _tzfCache = new TimeZoneFormatCache();
        ALL_SIMPLE_NAME_TYPES = EnumSet.of(TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, TimeZoneNames.NameType.EXEMPLAR_LOCATION);
        ALL_GENERIC_NAME_TYPES = EnumSet.of(TimeZoneGenericNames.GenericNameType.LOCATION, TimeZoneGenericNames.GenericNameType.LONG, TimeZoneGenericNames.GenericNameType.SHORT);
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("_locale", ULocale.class), new ObjectStreamField("_tznames", TimeZoneNames.class), new ObjectStreamField("_gmtPattern", String.class), new ObjectStreamField("_gmtOffsetPatterns", String[].class), new ObjectStreamField("_gmtOffsetDigits", String[].class), new ObjectStreamField("_gmtZeroFormat", String.class), new ObjectStreamField("_parseAllStyles", Boolean.TYPE)};
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected TimeZoneFormat(ULocale object) {
        String[] arrstring;
        void var2_14;
        void var5_23;
        block12 : {
            this._locale = object;
            this._tznames = TimeZoneNames.getInstance((ULocale)object);
            String[] arrobject = null;
            arrstring = null;
            Object var4_18 = null;
            Object var5_19 = null;
            this._gmtZeroFormat = DEFAULT_GMT_ZERO;
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/zone", (ULocale)object);
            try {
                String[] missingResourceException;
                arrstring = missingResourceException = iCUResourceBundle.getStringWithFallback("zoneStrings/gmtFormat");
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            try {
                String string;
                String string2 = string = iCUResourceBundle.getStringWithFallback("zoneStrings/hourFormat");
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            try {
                this._gmtZeroFormat = iCUResourceBundle.getStringWithFallback("zoneStrings/gmtZeroFormat");
            }
            catch (MissingResourceException missingResourceException) {}
            break block12;
            catch (MissingResourceException missingResourceException) {
                Object var5_22 = var4_18;
                arrstring = arrobject;
            }
        }
        String[] arrstring2 = arrstring;
        if (arrstring == null) {
            String string = DEFAULT_GMT_PATTERN;
        }
        this.initGMTPattern((String)var2_14);
        arrstring = new String[GMTOffsetPatternType.values().length];
        if (var5_23 != null) {
            String[] arrstring3 = var5_23.split(";", 2);
            arrstring[GMTOffsetPatternType.POSITIVE_H.ordinal()] = TimeZoneFormat.truncateOffsetPattern(arrstring3[0]);
            arrstring[GMTOffsetPatternType.POSITIVE_HM.ordinal()] = arrstring3[0];
            arrstring[GMTOffsetPatternType.POSITIVE_HMS.ordinal()] = TimeZoneFormat.expandOffsetPattern(arrstring3[0]);
            arrstring[GMTOffsetPatternType.NEGATIVE_H.ordinal()] = TimeZoneFormat.truncateOffsetPattern(arrstring3[1]);
            arrstring[GMTOffsetPatternType.NEGATIVE_HM.ordinal()] = arrstring3[1];
            arrstring[GMTOffsetPatternType.NEGATIVE_HMS.ordinal()] = TimeZoneFormat.expandOffsetPattern(arrstring3[1]);
        } else {
            for (GMTOffsetPatternType gMTOffsetPatternType : GMTOffsetPatternType.values()) {
                arrstring[gMTOffsetPatternType.ordinal()] = gMTOffsetPatternType.defaultPattern();
            }
        }
        this.initGMTOffsetPatterns(arrstring);
        this._gmtOffsetDigits = DEFAULT_GMT_DIGITS;
        object = NumberingSystem.getInstance((ULocale)object);
        if (((NumberingSystem)object).isAlgorithmic()) return;
        this._gmtOffsetDigits = TimeZoneFormat.toCodePoints(((NumberingSystem)object).getDescription());
    }

    private void appendOffsetDigits(StringBuilder stringBuilder, int n, int n2) {
        int n3 = n >= 10 ? 2 : 1;
        for (int i = 0; i < n2 - n3; ++i) {
            stringBuilder.append(this._gmtOffsetDigits[0]);
        }
        if (n3 == 2) {
            stringBuilder.append(this._gmtOffsetDigits[n / 10]);
        }
        stringBuilder.append(this._gmtOffsetDigits[n % 10]);
    }

    private void checkAbuttingHoursAndMinutes() {
        this._abuttingOffsetHoursAndMinutes = false;
        block0 : for (Object[] arrobject : this._gmtOffsetPatternItems) {
            int n = arrobject.length;
            boolean bl = false;
            for (int i = 0; i < n; ++i) {
                boolean bl2;
                Object object = arrobject[i];
                if (object instanceof GMTOffsetField) {
                    object = (GMTOffsetField)object;
                    if (bl) {
                        this._abuttingOffsetHoursAndMinutes = true;
                        bl2 = bl;
                    } else {
                        bl2 = bl;
                        if (((GMTOffsetField)object).getType() == 'H') {
                            bl2 = true;
                        }
                    }
                } else {
                    bl2 = bl;
                    if (bl) continue block0;
                }
                bl = bl2;
            }
        }
    }

    private static String expandOffsetPattern(String string) {
        int n = string.indexOf("mm");
        if (n >= 0) {
            String string2 = ":";
            int n2 = string.substring(0, n).lastIndexOf("H");
            if (n2 >= 0) {
                string2 = string.substring(n2 + 1, n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string.substring(0, n + 2));
            stringBuilder.append(string2);
            stringBuilder.append("ss");
            stringBuilder.append(string.substring(n + 2));
            return stringBuilder.toString();
        }
        throw new RuntimeException("Bad time zone hour pattern data");
    }

    private String formatExemplarLocation(TimeZone object) {
        String string = this.getTimeZoneNames().getExemplarLocationName(ZoneMeta.getCanonicalCLDRID((TimeZone)object));
        object = string;
        if (string == null) {
            string = this.getTimeZoneNames().getExemplarLocationName(UNKNOWN_ZONE_ID);
            object = string;
            if (string == null) {
                object = UNKNOWN_LOCATION;
            }
        }
        return object;
    }

    private String formatOffsetISO8601(int n, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n2 = n < 0 ? -n : n;
        if (bl2 && (n2 < 1000 || bl4 && n2 < 60000)) {
            return ISO8601_UTC;
        }
        Object object = bl3 ? OffsetFields.H : OffsetFields.HM;
        OffsetFields offsetFields = bl4 ? OffsetFields.HM : OffsetFields.HMS;
        Character c = bl ? null : Character.valueOf(':');
        if (n2 < 86400000) {
            int n3;
            int[] arrn = new int[]{n2 / 3600000, (n2 %= 3600000) / 60000, n2 % 60000 / 1000};
            for (n2 = offsetFields.ordinal(); n2 > object.ordinal() && arrn[n2] == 0; --n2) {
            }
            object = new StringBuilder();
            int n4 = n3 = 43;
            if (n < 0) {
                n = 0;
                do {
                    n4 = n3;
                    if (n > n2) break;
                    if (arrn[n] != 0) {
                        n4 = n = 45;
                        break;
                    }
                    ++n;
                } while (true);
            }
            ((StringBuilder)object).append((char)n4);
            for (n = 0; n <= n2; ++n) {
                if (c != null && n != 0) {
                    ((StringBuilder)object).append(c);
                }
                if (arrn[n] < 10) {
                    ((StringBuilder)object).append('0');
                }
                ((StringBuilder)object).append(arrn[n]);
            }
            return ((StringBuilder)object).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Offset out of range :");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private String formatOffsetLocalizedGMT(int n, boolean bl) {
        if (n == 0) {
            return this._gmtZeroFormat;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 1;
        int n3 = n;
        if (n < 0) {
            n3 = -n;
            n2 = 0;
        }
        int n4 = n3 / 3600000;
        n = n3 % 3600000;
        n3 = n / 60000;
        int n5 = (n %= 60000) / 1000;
        if (n4 <= 23 && n3 <= 59 && n5 <= 59) {
            Object[] arrobject = n2 != 0 ? (n5 != 0 ? this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_HMS.ordinal()] : (n3 == 0 && bl ? this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_H.ordinal()] : this._gmtOffsetPatternItems[GMTOffsetPatternType.POSITIVE_HM.ordinal()])) : (n5 != 0 ? this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_HMS.ordinal()] : (n3 == 0 && bl ? this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_H.ordinal()] : this._gmtOffsetPatternItems[GMTOffsetPatternType.NEGATIVE_HM.ordinal()]));
            stringBuilder.append(this._gmtPatternPrefix);
            for (Object object : arrobject) {
                if (object instanceof String) {
                    stringBuilder.append((String)object);
                    continue;
                }
                if (!(object instanceof GMTOffsetField)) continue;
                char c = ((GMTOffsetField)object).getType();
                n2 = 2;
                if (c != 'H') {
                    if (c != 'm') {
                        if (c != 's') continue;
                        this.appendOffsetDigits(stringBuilder, n5, 2);
                        continue;
                    }
                    this.appendOffsetDigits(stringBuilder, n3, 2);
                    continue;
                }
                if (bl) {
                    n2 = 1;
                }
                this.appendOffsetDigits(stringBuilder, n4, n2);
            }
            stringBuilder.append(this._gmtPatternSuffix);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Offset out of range :");
        stringBuilder2.append(n);
        throw new IllegalArgumentException(stringBuilder2.toString());
    }

    private String formatSpecific(TimeZone object, TimeZoneNames.NameType enum_, TimeZoneNames.NameType nameType, long l, Output<TimeType> output) {
        boolean bl = ((TimeZone)object).inDaylightTime(new Date(l));
        if ((object = bl ? this.getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID((TimeZone)object), nameType, l) : this.getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID((TimeZone)object), (TimeZoneNames.NameType)enum_, l)) != null && output != null) {
            enum_ = bl ? TimeType.DAYLIGHT : TimeType.STANDARD;
            output.value = enum_;
        }
        return object;
    }

    public static TimeZoneFormat getInstance(ULocale uLocale) {
        if (uLocale != null) {
            return (TimeZoneFormat)_tzfCache.getInstance(uLocale, uLocale);
        }
        throw new NullPointerException("locale is null");
    }

    public static TimeZoneFormat getInstance(Locale locale) {
        return TimeZoneFormat.getInstance(ULocale.forLocale(locale));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private TimeZoneNames getTZDBTimeZoneNames() {
        if (this._tzdbNames != null) return this._tzdbNames;
        synchronized (this) {
            if (this._tzdbNames != null) return this._tzdbNames;
            TZDBTimeZoneNames tZDBTimeZoneNames = new TZDBTimeZoneNames(this._locale);
            this._tzdbNames = tZDBTimeZoneNames;
            return this._tzdbNames;
        }
    }

    private String getTargetRegion() {
        synchronized (this) {
            if (this._region == null) {
                this._region = this._locale.getCountry();
                if (this._region.length() == 0) {
                    this._region = ULocale.addLikelySubtags(this._locale).getCountry();
                    if (this._region.length() == 0) {
                        this._region = "001";
                    }
                }
            }
            String string = this._region;
            return string;
        }
    }

    private TimeType getTimeType(TimeZoneNames.NameType nameType) {
        int n = 1.$SwitchMap$android$icu$text$TimeZoneNames$NameType[nameType.ordinal()];
        if (n != 1 && n != 2) {
            if (n != 3 && n != 4) {
                return TimeType.UNKNOWN;
            }
            return TimeType.DAYLIGHT;
        }
        return TimeType.STANDARD;
    }

    private TimeZone getTimeZoneForOffset(int n) {
        if (n == 0) {
            return TimeZone.getTimeZone(TZID_GMT);
        }
        return ZoneMeta.getCustomTimeZone(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private TimeZoneGenericNames getTimeZoneGenericNames() {
        if (this._gnames != null) return this._gnames;
        synchronized (this) {
            if (this._gnames != null) return this._gnames;
            this._gnames = TimeZoneGenericNames.getInstance(this._locale);
            return this._gnames;
        }
    }

    private String getTimeZoneID(String charSequence, String string) {
        String string2 = charSequence;
        if (charSequence == null && (string2 = this._tznames.getReferenceZoneID(string, this.getTargetRegion())) == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid mzID: ");
            ((StringBuilder)charSequence).append(string);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        return string2;
    }

    private void initGMTOffsetPatterns(String[] arrstring) {
        int n = GMTOffsetPatternType.values().length;
        if (arrstring.length >= n) {
            Object[][] arrarrobject = new Object[n][];
            for (GMTOffsetPatternType gMTOffsetPatternType : GMTOffsetPatternType.values()) {
                int n2 = gMTOffsetPatternType.ordinal();
                arrarrobject[n2] = TimeZoneFormat.parseOffsetPattern(arrstring[n2], gMTOffsetPatternType.required());
            }
            this._gmtOffsetPatterns = new String[n];
            System.arraycopy(arrstring, 0, this._gmtOffsetPatterns, 0, n);
            this._gmtOffsetPatternItems = arrarrobject;
            this.checkAbuttingHoursAndMinutes();
            return;
        }
        throw new IllegalArgumentException("Insufficient number of elements in gmtOffsetPatterns");
    }

    private void initGMTPattern(String string) {
        int n = string.indexOf("{0}");
        if (n >= 0) {
            this._gmtPattern = string;
            this._gmtPatternPrefix = TimeZoneFormat.unquote(string.substring(0, n));
            this._gmtPatternSuffix = TimeZoneFormat.unquote(string.substring(n + 3));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad localized GMT pattern: ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int parseAbuttingAsciiOffsetFields(String string, ParsePosition parsePosition, OffsetFields arrn, OffsetFields offsetFields, boolean bl) {
        int n;
        boolean bl2;
        int n2;
        int n3 = parsePosition.getIndex();
        int n4 = (arrn.ordinal() + 1) * 2 - (bl ^ true);
        arrn = new int[(offsetFields.ordinal() + 1) * 2];
        int n5 = 0;
        for (n = n3; n5 < arrn.length && n < string.length() && (n2 = ASCII_DIGITS.indexOf(string.charAt(n))) >= 0; ++n5, ++n) {
            arrn[n5] = n2;
        }
        n = n5;
        if (bl) {
            n = n5;
            if ((n5 & 1) != 0) {
                n = n5 - 1;
            }
        }
        if (n < n4) {
            parsePosition.setErrorIndex(n3);
            return 0;
        }
        boolean bl3 = false;
        int n6 = n;
        do {
            n2 = 0;
            int n7 = 0;
            n = 0;
            int n8 = 0;
            n5 = 0;
            int n9 = 0;
            bl2 = bl3;
            if (n6 < n4) break;
            switch (n6) {
                default: {
                    n5 = n9;
                    n = n8;
                    n2 = n7;
                    break;
                }
                case 6: {
                    n5 = arrn[0] * 10 + arrn[1];
                    n = arrn[2] * 10 + arrn[3];
                    n2 = arrn[4] * 10 + arrn[5];
                    break;
                }
                case 5: {
                    n5 = arrn[0];
                    n = arrn[1] * 10 + arrn[2];
                    n2 = arrn[3] * 10 + arrn[4];
                    break;
                }
                case 4: {
                    n5 = arrn[0] * 10 + arrn[1];
                    n = arrn[2] * 10 + arrn[3];
                    n2 = n7;
                    break;
                }
                case 3: {
                    n5 = arrn[0];
                    n = arrn[1] * 10 + arrn[2];
                    n2 = n7;
                    break;
                }
                case 2: {
                    n5 = arrn[0] * 10 + arrn[1];
                    n = n8;
                    n2 = n7;
                    break;
                }
                case 1: {
                    n5 = arrn[0];
                    n2 = n7;
                    n = n8;
                }
            }
            if (n5 <= 23 && n <= 59 && n2 <= 59) {
                bl2 = true;
                break;
            }
            n5 = bl ? 2 : 1;
            n6 -= n5;
        } while (true);
        if (!bl2) {
            parsePosition.setErrorIndex(n3);
            return 0;
        }
        parsePosition.setIndex(n3 + n6);
        return ((n5 * 60 + n) * 60 + n2) * 1000;
    }

    private int parseAbuttingOffsetFields(String string, int n, int[] arrn) {
        int n2;
        int[] arrn2 = new int[6];
        int[] arrn3 = new int[6];
        int n3 = n;
        int[] arrn4 = new int[]{0};
        int n4 = 0;
        for (n2 = 0; n2 < 6; ++n2) {
            arrn2[n2] = this.parseSingleLocalizedDigit(string, n3, arrn4);
            if (arrn2[n2] < 0) break;
            arrn3[n2] = (n3 += arrn4[0]) - n;
            ++n4;
        }
        if (n4 == 0) {
            arrn[0] = 0;
            return 0;
        }
        int n5 = 0;
        n3 = n4;
        do {
            n = n5;
            if (n3 <= 0) break;
            n = 0;
            n4 = 0;
            n2 = 0;
            switch (n3) {
                default: {
                    break;
                }
                case 6: {
                    n = arrn2[0] * 10 + arrn2[1];
                    n4 = arrn2[2] * 10 + arrn2[3];
                    n2 = arrn2[4] * 10 + arrn2[5];
                    break;
                }
                case 5: {
                    n = arrn2[0];
                    n4 = arrn2[1] * 10 + arrn2[2];
                    n2 = arrn2[3] * 10 + arrn2[4];
                    break;
                }
                case 4: {
                    n = arrn2[0] * 10 + arrn2[1];
                    n4 = arrn2[2] * 10 + arrn2[3];
                    break;
                }
                case 3: {
                    n = arrn2[0];
                    n4 = arrn2[1] * 10 + arrn2[2];
                    break;
                }
                case 2: {
                    n = arrn2[0] * 10 + arrn2[1];
                    break;
                }
                case 1: {
                    n = arrn2[0];
                }
            }
            if (n <= 23 && n4 <= 59 && n2 <= 59) {
                n = 3600000 * n + 60000 * n4 + n2 * 1000;
                arrn[0] = arrn3[n3 - 1];
                break;
            }
            --n3;
        } while (true);
        return n;
    }

    private static int parseAsciiOffsetFields(String object, ParsePosition parsePosition, char c, OffsetFields offsetFields, OffsetFields offsetFields2) {
        int n;
        int n2;
        int[] arrn;
        int[] arrn2;
        int n3 = parsePosition.getIndex();
        int[] arrn3 = arrn = new int[3];
        arrn3[0] = 0;
        arrn3[1] = 0;
        arrn3[2] = 0;
        int[] arrn4 = arrn2 = new int[3];
        arrn4[0] = 0;
        arrn4[1] = -1;
        arrn4[2] = -1;
        int n4 = 0;
        for (n = n3; n < object.length() && n4 <= offsetFields2.ordinal(); ++n) {
            n2 = object.charAt(n);
            if (n2 == c) {
                if (n4 == 0) {
                    if (arrn2[0] == 0) break;
                    n2 = n4 + 1;
                } else {
                    if (arrn2[n4] != -1) break;
                    arrn2[n4] = 0;
                    n2 = n4;
                }
            } else {
                if (arrn2[n4] == -1 || (n2 = ASCII_DIGITS.indexOf(n2)) < 0) break;
                arrn[n4] = arrn[n4] * 10 + n2;
                arrn2[n4] = arrn2[n4] + 1;
                n2 = n4;
                if (arrn2[n4] >= 2) {
                    n2 = n4 + 1;
                }
            }
            n4 = n2;
        }
        c = '\u0000';
        n2 = 0;
        object = null;
        if (arrn2[0] != 0) {
            if (arrn[0] > 23) {
                c = (char)(arrn[0] / 10 * 3600000);
                object = OffsetFields.H;
                n2 = 1;
            } else {
                n4 = arrn[0] * 3600000;
                n = arrn2[0];
                offsetFields2 = OffsetFields.H;
                c = (char)n4;
                n2 = n;
                object = offsetFields2;
                if (arrn2[1] == 2) {
                    if (arrn[1] > 59) {
                        c = (char)n4;
                        n2 = n;
                        object = offsetFields2;
                    } else {
                        offsetFields2 = OffsetFields.HM;
                        c = (char)(n4 += arrn[1] * 60000);
                        n2 = n += arrn2[1] + 1;
                        object = offsetFields2;
                        if (arrn2[2] == 2) {
                            if (arrn[2] > 59) {
                                c = (char)n4;
                                n2 = n;
                                object = offsetFields2;
                            } else {
                                c = (char)(n4 + arrn[2] * 1000);
                                n2 = n + (arrn2[2] + 1);
                                object = OffsetFields.HMS;
                            }
                        }
                    }
                }
            }
        }
        if (object != null && ((Enum)object).ordinal() >= offsetFields.ordinal()) {
            parsePosition.setIndex(n3 + n2);
            return c;
        }
        parsePosition.setErrorIndex(n3);
        return 0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int parseDefaultOffsetFields(String var1_1, int var2_2, char var3_3, int[] var4_4) {
        block6 : {
            block7 : {
                block5 : {
                    var5_5 = var1_1.length();
                    var6_6 = var2_2;
                    var7_7 = new int[]{0};
                    var8_8 = 0;
                    var9_9 = 0;
                    var10_10 = this.parseOffsetFieldWithLocalizedDigits(var1_1, var6_6, 1, 2, 0, 23, var7_7);
                    if (var7_7[0] != 0) break block5;
                    var3_3 = '\u0000';
                    break block6;
                }
                var6_6 = var11_11 = var6_6 + var7_7[0];
                var8_8 = var9_9;
                if (var11_11 + 1 >= var5_5) ** GOTO lbl-1000
                var6_6 = var11_11;
                var8_8 = var9_9;
                if (var1_1.charAt(var11_11) != var3_3) ** GOTO lbl-1000
                var9_9 = this.parseOffsetFieldWithLocalizedDigits(var1_1, var11_11 + 1, 2, 2, 0, 59, var7_7);
                if (var7_7[0] != 0) break block7;
                var3_3 = '\u0000';
                var6_6 = var11_11;
                var8_8 = var9_9;
                break block6;
            }
            var6_6 = var11_11 += var7_7[0] + 1;
            var8_8 = var9_9;
            if (var11_11 + 1 >= var5_5) ** GOTO lbl-1000
            var6_6 = var11_11;
            var8_8 = var9_9;
            if (var1_1.charAt(var11_11) == var3_3) {
                var3_3 = (char)this.parseOffsetFieldWithLocalizedDigits(var1_1, var11_11 + 1, 2, 2, 0, 59, var7_7);
                if (var7_7[0] == 0) {
                    var6_6 = var11_11;
                    var8_8 = var9_9;
                } else {
                    var6_6 = var11_11 + (var7_7[0] + 1);
                    var8_8 = var9_9;
                }
            } else lbl-1000: // 4 sources:
            {
                var3_3 = '\u0000';
            }
        }
        if (var6_6 == var2_2) {
            var4_4[0] = 0;
            return 0;
        }
        var4_4[0] = var6_6 - var2_2;
        return 3600000 * var10_10 + 60000 * var8_8 + var3_3 * 1000;
    }

    private String parseExemplarLocation(String object, ParsePosition parsePosition) {
        int n = parsePosition.getIndex();
        int n2 = -1;
        Object var5_5 = null;
        Object object2 = EnumSet.of(TimeZoneNames.NameType.EXEMPLAR_LOCATION);
        Object object3 = this._tznames.find((CharSequence)object, n, (EnumSet<TimeZoneNames.NameType>)object2);
        object2 = var5_5;
        if (object3 != null) {
            object = null;
            object3 = object3.iterator();
            while (object3.hasNext()) {
                object2 = (TimeZoneNames.MatchInfo)object3.next();
                int n3 = n2;
                if (((TimeZoneNames.MatchInfo)object2).matchLength() + n > n2) {
                    object = object2;
                    n3 = n + ((TimeZoneNames.MatchInfo)object2).matchLength();
                }
                n2 = n3;
            }
            object2 = var5_5;
            if (object != null) {
                object2 = this.getTimeZoneID(((TimeZoneNames.MatchInfo)object).tzID(), ((TimeZoneNames.MatchInfo)object).mzID());
                parsePosition.setIndex(n2);
            }
        }
        if (object2 == null) {
            parsePosition.setErrorIndex(n);
        }
        return object2;
    }

    private int parseOffsetDefaultLocalizedGMT(String string, int n, int[] arrn) {
        int n2;
        int n3;
        block9 : {
            Object[] arrobject;
            int n4;
            int n5;
            int[] arrn2;
            int n6;
            block12 : {
                int n7;
                block11 : {
                    block10 : {
                        block8 : {
                            n4 = 0;
                            n6 = 0;
                            n5 = 0;
                            arrobject = ALT_GMT_STRINGS;
                            n7 = arrobject.length;
                            n3 = 0;
                            do {
                                n2 = n5;
                                if (n3 >= n7 || string.regionMatches(true, n, (String)(arrn2 = arrobject[n3]), 0, n2 = arrn2.length())) break;
                                ++n3;
                            } while (true);
                            if (n2 != 0) break block8;
                            n3 = n4;
                            n2 = n6;
                            break block9;
                        }
                        n5 = n + n2;
                        if (n5 + 1 < string.length()) break block10;
                        n3 = n4;
                        n2 = n6;
                        break block9;
                    }
                    n7 = string.charAt(n5);
                    if (n7 != 43) break block11;
                    n3 = 1;
                    break block12;
                }
                n3 = n4;
                n2 = n6;
                if (n7 != 45) break block9;
                n3 = -1;
            }
            n2 = n5 + 1;
            arrn2 = new int[]{0};
            n6 = this.parseDefaultOffsetFields(string, n2, ':', arrn2);
            if (arrn2[0] == string.length() - n2) {
                n2 += arrn2[0];
                n3 = n6 * n3;
            } else {
                arrobject = new int[]{(String)false};
                n4 = this.parseAbuttingOffsetFields(string, n2, (int[])arrobject);
                if (arrn2[0] > arrobject[0]) {
                    n3 = n6 * n3;
                    n2 += arrn2[0];
                } else {
                    n3 = n4 * n3;
                    n2 += arrobject[0];
                }
            }
            n2 -= n;
        }
        arrn[0] = n2;
        return n3;
    }

    private int parseOffsetFieldWithLocalizedDigits(String string, int n, int n2, int n3, int n4, int n5, int[] arrn) {
        int n6;
        int n7;
        arrn[0] = 0;
        int n8 = 0;
        int n9 = 0;
        int[] arrn2 = new int[]{0};
        for (n7 = n; n7 < string.length() && n9 < n3 && (n6 = this.parseSingleLocalizedDigit(string, n7, arrn2)) >= 0 && (n6 = n8 * 10 + n6) <= n5; ++n9, n7 += arrn2[0]) {
            n8 = n6;
        }
        if (n9 >= n2 && n8 >= n4) {
            arrn[0] = n7 - n;
        } else {
            n8 = -1;
        }
        return n8;
    }

    private int parseOffsetFields(String string, int n, boolean bl, int[] arrn) {
        int[] arrn2;
        int n2;
        int n3;
        int n4;
        int n5;
        GMTOffsetPatternType[] arrgMTOffsetPatternType;
        int n6;
        int n7;
        int n8;
        GMTOffsetPatternType gMTOffsetPatternType;
        int n9;
        Object[] arrobject;
        int n10;
        block10 : {
            n5 = 0;
            if (arrn != null && arrn.length >= 1) {
                arrn[0] = 0;
            }
            n8 = 0;
            n6 = 0;
            int[] arrn3 = arrn2 = new int[3];
            arrn3[0] = 0;
            arrn3[1] = 0;
            arrn3[2] = 0;
            arrgMTOffsetPatternType = PARSE_GMT_OFFSET_TYPES;
            n7 = arrgMTOffsetPatternType.length;
            n9 = 0;
            n2 = 0;
            do {
                n4 = -1;
                if (n2 >= n7) break;
                gMTOffsetPatternType = arrgMTOffsetPatternType[n2];
                arrobject = this._gmtOffsetPatternItems[gMTOffsetPatternType.ordinal()];
                n3 = this.parseOffsetFieldsWithPattern(string, n, arrobject, false, arrn2);
                if (n3 > 0) {
                    n9 = gMTOffsetPatternType.isPositive() ? 1 : -1;
                    n8 = arrn2[0];
                    n6 = arrn2[1];
                    n2 = arrn2[2];
                    n10 = n3;
                    n7 = n9;
                    n3 = n6;
                    n9 = n8;
                    break block10;
                }
                ++n2;
                n9 = n3;
            } while (true);
            n7 = 1;
            n10 = n9;
            n2 = 0;
            n9 = n6;
            n3 = n8;
        }
        int n11 = n10;
        int n12 = n7;
        int n13 = n3;
        int n14 = n9;
        n6 = n2;
        if (n10 > 0) {
            n11 = n10;
            n12 = n7;
            n13 = n3;
            n14 = n9;
            n6 = n2;
            if (this._abuttingOffsetHoursAndMinutes) {
                block11 : {
                    n13 = 1;
                    arrgMTOffsetPatternType = PARSE_GMT_OFFSET_TYPES;
                    n6 = arrgMTOffsetPatternType.length;
                    n8 = 0;
                    for (n14 = 0; n14 < n6; ++n14) {
                        gMTOffsetPatternType = arrgMTOffsetPatternType[n14];
                        arrobject = this._gmtOffsetPatternItems[gMTOffsetPatternType.ordinal()];
                        n8 = this.parseOffsetFieldsWithPattern(string, n, arrobject, true, arrn2);
                        if (n8 <= 0) continue;
                        n = n4;
                        if (gMTOffsetPatternType.isPositive()) {
                            n = 1;
                        }
                        break block11;
                    }
                    n = n13;
                }
                n11 = n10;
                n12 = n7;
                n13 = n3;
                n14 = n9;
                n6 = n2;
                if (n8 > n10) {
                    n14 = arrn2[0];
                    n13 = arrn2[1];
                    n6 = arrn2[2];
                    n12 = n;
                    n11 = n8;
                }
            }
        }
        if (arrn != null && arrn.length >= 1) {
            arrn[0] = n11;
        }
        n = n5;
        if (n11 > 0) {
            n = ((n14 * 60 + n13) * 60 + n6) * 1000 * n12;
        }
        return n;
    }

    private int parseOffsetFieldsWithPattern(String string, int n, Object[] arrobject, boolean bl, int[] arrn) {
        int n2;
        int n3;
        int n4;
        int n5;
        arrn[2] = 0;
        arrn[1] = 0;
        arrn[0] = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = n;
        int[] arrn2 = new int[]{0};
        int n10 = 0;
        int n11 = 0;
        do {
            n3 = n6;
            n5 = n7;
            n4 = n8;
            n2 = n10;
            if (n11 >= arrobject.length) break;
            if (arrobject[n11] instanceof String) {
                String string2 = (String)arrobject[n11];
                n4 = string2.length();
                n2 = 0;
                n5 = 0;
                if (n11 == 0) {
                    if (n9 < string.length()) {
                        n3 = n4;
                        if (!PatternProps.isWhiteSpace(string.codePointAt(n9))) {
                            do {
                                n3 = n4;
                                n2 = n5;
                                if (n4 > 0) {
                                    int n12 = string2.codePointAt(n5);
                                    n3 = n4;
                                    n2 = n5;
                                    if (PatternProps.isWhiteSpace(n12)) {
                                        n3 = Character.charCount(n12);
                                        n4 -= n3;
                                        n5 += n3;
                                        continue;
                                    }
                                }
                                break;
                            } while (true);
                        }
                    } else {
                        n3 = n4;
                    }
                } else {
                    n3 = n4;
                }
                if (!string.regionMatches(true, n9, string2, n2, n3)) {
                    n3 = 1;
                    n5 = n7;
                    n4 = n8;
                    n2 = n10;
                    break;
                }
                n3 = n9 + n3;
            } else {
                n3 = ((GMTOffsetField)arrobject[n11]).getType();
                if (n3 == 72) {
                    n8 = bl ? 1 : 2;
                    n4 = this.parseOffsetFieldWithLocalizedDigits(string, n9, 1, n8, 0, 23, arrn2);
                    n5 = n7;
                } else if (n3 == 109) {
                    n5 = this.parseOffsetFieldWithLocalizedDigits(string, n9, 2, 2, 0, 59, arrn2);
                    n4 = n8;
                } else {
                    n5 = n7;
                    n4 = n8;
                    if (n3 == 115) {
                        n10 = this.parseOffsetFieldWithLocalizedDigits(string, n9, 2, 2, 0, 59, arrn2);
                        n4 = n8;
                        n5 = n7;
                    }
                }
                if (arrn2[0] == 0) {
                    n3 = 1;
                    n2 = n10;
                    break;
                }
                n3 = n9 + arrn2[0];
                n8 = n4;
                n7 = n5;
            }
            ++n11;
            n9 = n3;
        } while (true);
        if (n3 != 0) {
            return 0;
        }
        arrn[0] = n4;
        arrn[1] = n5;
        arrn[2] = n2;
        return n9 - n;
    }

    private static int parseOffsetISO8601(String string, ParsePosition parsePosition, boolean bl, Output<Boolean> output) {
        int n;
        block15 : {
            int n2;
            int n3;
            block14 : {
                char c;
                block13 : {
                    if (output != null) {
                        output.value = false;
                    }
                    if ((n = parsePosition.getIndex()) >= string.length()) {
                        parsePosition.setErrorIndex(n);
                        return 0;
                    }
                    c = string.charAt(n);
                    if (Character.toUpperCase(c) == ISO8601_UTC.charAt(0)) {
                        parsePosition.setIndex(n + 1);
                        return 0;
                    }
                    if (c != '+') break block13;
                    n3 = 1;
                    break block14;
                }
                if (c != '-') break block15;
                n3 = -1;
            }
            ParsePosition parsePosition2 = new ParsePosition(n + 1);
            int n4 = n2 = TimeZoneFormat.parseAsciiOffsetFields(string, parsePosition2, ':', OffsetFields.H, OffsetFields.HMS);
            if (parsePosition2.getErrorIndex() == -1) {
                n4 = n2;
                if (!bl) {
                    n4 = n2;
                    if (parsePosition2.getIndex() - n <= 3) {
                        ParsePosition parsePosition3 = new ParsePosition(n + 1);
                        int n5 = TimeZoneFormat.parseAbuttingAsciiOffsetFields(string, parsePosition3, OffsetFields.H, OffsetFields.HMS, false);
                        n4 = n2;
                        if (parsePosition3.getErrorIndex() == -1) {
                            n4 = n2;
                            if (parsePosition3.getIndex() > parsePosition2.getIndex()) {
                                n4 = n5;
                                parsePosition2.setIndex(parsePosition3.getIndex());
                            }
                        }
                    }
                }
            }
            if (parsePosition2.getErrorIndex() != -1) {
                parsePosition.setErrorIndex(n);
                return 0;
            }
            parsePosition.setIndex(parsePosition2.getIndex());
            if (output != null) {
                output.value = true;
            }
            return n3 * n4;
        }
        parsePosition.setErrorIndex(n);
        return 0;
    }

    private int parseOffsetLocalizedGMT(String string, ParsePosition parsePosition, boolean bl, Output<Boolean> object) {
        int n = parsePosition.getIndex();
        Boolean bl2 = true;
        int[] arrn = new int[]{0};
        if (object != null) {
            ((Output)object).value = false;
        }
        int n2 = this.parseOffsetLocalizedGMTPattern(string, n, bl, arrn);
        if (arrn[0] > 0) {
            if (object != null) {
                ((Output)object).value = bl2;
            }
            parsePosition.setIndex(arrn[0] + n);
            return n2;
        }
        n2 = this.parseOffsetDefaultLocalizedGMT(string, n, arrn);
        if (arrn[0] > 0) {
            if (object != null) {
                ((Output)object).value = bl2;
            }
            parsePosition.setIndex(arrn[0] + n);
            return n2;
        }
        String string2 = this._gmtZeroFormat;
        if (string.regionMatches(true, n, string2, 0, string2.length())) {
            parsePosition.setIndex(this._gmtZeroFormat.length() + n);
            return 0;
        }
        for (String string3 : ALT_GMT_STRINGS) {
            if (!string.regionMatches(true, n, string3, 0, string3.length())) continue;
            parsePosition.setIndex(string3.length() + n);
            return 0;
        }
        parsePosition.setErrorIndex(n);
        return 0;
    }

    private int parseOffsetLocalizedGMTPattern(String string, int n, boolean bl, int[] arrn) {
        int n2 = n;
        int n3 = 0;
        boolean bl2 = false;
        int n4 = this._gmtPatternPrefix.length();
        if (n4 <= 0 || string.regionMatches(true, n2, this._gmtPatternPrefix, 0, n4)) {
            int[] arrn2 = new int[1];
            n3 = this.parseOffsetFields(string, n2 += n4, false, arrn2);
            if (arrn2[0] != 0) {
                n2 = arrn2[0] + n2;
                n4 = this._gmtPatternSuffix.length();
                if (n4 <= 0 || string.regionMatches(true, n2, this._gmtPatternSuffix, 0, n4)) {
                    n2 += n4;
                    bl2 = true;
                }
            }
        }
        n = bl2 ? n2 - n : 0;
        arrn[0] = n;
        return n3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Object[] parseOffsetPattern(String var0, String var1_1) {
        var2_2 = '\u0000';
        var3_3 = '\u0000';
        var4_4 = new StringBuilder();
        var5_5 = '\u0000';
        var6_6 = 1;
        var7_7 = '\u0000';
        var8_8 = new ArrayList<Object>();
        var9_9 = new BitSet(var1_1.length());
        var10_10 = '\u0000';
        var11_11 = var5_5;
        do {
            block21 : {
                block24 : {
                    block23 : {
                        block22 : {
                            block20 : {
                                var12_12 = var0.length();
                                var13_13 = '\u0000';
                                var5_5 = var7_7;
                                if (var10_10 >= var12_12) break;
                                var14_14 = var0.charAt(var10_10);
                                if (var14_14 != '\'') break block20;
                                if (var2_2 != '\u0000') {
                                    var4_4.append('\'');
                                    var5_5 = '\u0000';
                                    var2_2 = var11_11;
                                } else {
                                    var5_5 = var12_12 = '\u0001';
                                    var2_2 = var11_11;
                                    if (var11_11 != '\u0000') {
                                        if (GMTOffsetField.isValid(var11_11, var6_6)) {
                                            var8_8.add(new GMTOffsetField(var11_11, var6_6));
                                            var2_2 = '\u0000';
                                            var5_5 = var12_12;
                                        } else {
                                            var5_5 = '\u0001';
                                            break;
                                        }
                                    }
                                }
                                if (var3_3 == '\u0000') {
                                    var13_13 = '\u0001';
                                }
                                var3_3 = var13_13;
                                var13_13 = var2_2;
                                break block21;
                            }
                            var5_5 = '\u0000';
                            if (var3_3 == '\u0000') break block22;
                            var4_4.append(var14_14);
                            var13_13 = var11_11;
                            break block21;
                        }
                        var2_2 = var1_1.indexOf(var14_14);
                        if (var2_2 < '\u0000') ** GOTO lbl71
                        if (var14_14 != var11_11) break block23;
                        ++var6_6;
                        var13_13 = var11_11;
                        break block21;
                    }
                    if (var11_11 != '\u0000') break block24;
                    if (var4_4.length() > 0) {
                        var8_8.add(var4_4.toString());
                        var4_4.setLength(0);
                    }
                    ** GOTO lbl65
                }
                if (GMTOffsetField.isValid(var11_11, var6_6)) {
                    var8_8.add(new GMTOffsetField(var11_11, var6_6));
lbl65: // 2 sources:
                    var13_13 = var14_14;
                    var6_6 = 1;
                    var9_9.set(var2_2);
                } else {
                    var5_5 = '\u0001';
                    break;
lbl71: // 1 sources:
                    var13_13 = var11_11;
                    if (var11_11 != '\u0000') {
                        if (GMTOffsetField.isValid(var11_11, var6_6)) {
                            var8_8.add(new GMTOffsetField(var11_11, var6_6));
                            var13_13 = '\u0000';
                        } else {
                            var5_5 = '\u0001';
                            break;
                        }
                    }
                    var4_4.append(var14_14);
                }
            }
            ++var10_10;
            var2_2 = var5_5;
            var11_11 = var13_13;
        } while (true);
        var13_13 = var5_5;
        if (var5_5 == '\u0000') {
            if (var11_11 == '\u0000') {
                var13_13 = var5_5;
                if (var4_4.length() > 0) {
                    var8_8.add(var4_4.toString());
                    var4_4.setLength(0);
                    var13_13 = var5_5;
                }
            } else if (GMTOffsetField.isValid(var11_11, var6_6)) {
                var8_8.add(new GMTOffsetField(var11_11, var6_6));
                var13_13 = var5_5;
            } else {
                var13_13 = '\u0001';
            }
        }
        if (var13_13 == '\u0000' && var9_9.cardinality() == var1_1.length()) {
            return var8_8.toArray(new Object[var8_8.size()]);
        }
        var1_1 = new StringBuilder();
        var1_1.append("Bad localized GMT offset pattern: ");
        var1_1.append(var0);
        throw new IllegalStateException(var1_1.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String parseShortZoneID(String object, ParsePosition parsePosition) {
        TextTrieMap<String> textTrieMap;
        Object var2_2 = null;
        if (SHORT_ZONE_ID_TRIE == null) {
            synchronized (TimeZoneFormat.class) {
                if (SHORT_ZONE_ID_TRIE == null) {
                    textTrieMap = new TextTrieMap<String>(true);
                    for (String string : TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, null, null)) {
                        String string2 = ZoneMeta.getShortID(string);
                        if (string2 == null) continue;
                        textTrieMap.put(string2, string);
                    }
                    textTrieMap.put(UNKNOWN_SHORT_ZONE_ID, UNKNOWN_ZONE_ID);
                    SHORT_ZONE_ID_TRIE = textTrieMap;
                }
            }
        }
        textTrieMap = new TextTrieMap.Output();
        if ((object = SHORT_ZONE_ID_TRIE.get((CharSequence)object, parsePosition.getIndex(), (TextTrieMap.Output)((Object)textTrieMap))) != null) {
            object = (String)object.next();
            parsePosition.setIndex(parsePosition.getIndex() + ((TextTrieMap.Output)textTrieMap).matchLength);
            return object;
        }
        parsePosition.setErrorIndex(parsePosition.getIndex());
        return var2_2;
    }

    private int parseSingleLocalizedDigit(String arrstring, int n, int[] arrn) {
        int n2 = -1;
        arrn[0] = 0;
        int n3 = n2;
        if (n < arrstring.length()) {
            int n4 = Character.codePointAt((CharSequence)arrstring, n);
            int n5 = 0;
            do {
                arrstring = this._gmtOffsetDigits;
                n = n2;
                if (n5 >= arrstring.length) break;
                if (n4 == arrstring[n5].codePointAt(0)) {
                    n = n5;
                    break;
                }
                ++n5;
            } while (true);
            n5 = n;
            if (n < 0) {
                n5 = UCharacter.digit(n4);
            }
            n3 = n5;
            if (n5 >= 0) {
                arrn[0] = Character.charCount(n4);
                n3 = n5;
            }
        }
        return n3;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String parseZoneID(String object, ParsePosition parsePosition) {
        Object object22;
        Object var2_2 = null;
        if (ZONE_ID_TRIE == null) {
            synchronized (TimeZoneFormat.class) {
                if (ZONE_ID_TRIE == null) {
                    TextTrieMap<Object> textTrieMap = new TextTrieMap<Object>(true);
                    for (Object object22 : TimeZone.getAvailableIDs()) {
                        textTrieMap.put((CharSequence)object22, object22);
                    }
                    ZONE_ID_TRIE = textTrieMap;
                }
            }
        }
        object22 = new TextTrieMap.Output();
        if ((object = ZONE_ID_TRIE.get((CharSequence)object, parsePosition.getIndex(), (TextTrieMap.Output)object22)) != null) {
            object = (String)object.next();
            parsePosition.setIndex(parsePosition.getIndex() + ((TextTrieMap.Output)object22).matchLength);
            return object;
        }
        parsePosition.setErrorIndex(parsePosition.getIndex());
        return var2_2;
    }

    private void readObject(ObjectInputStream object) throws ClassNotFoundException, IOException {
        object = ((ObjectInputStream)object).readFields();
        this._locale = (ULocale)((ObjectInputStream.GetField)object).get("_locale", null);
        if (this._locale != null) {
            this._tznames = (TimeZoneNames)((ObjectInputStream.GetField)object).get("_tznames", null);
            if (this._tznames != null) {
                this._gmtPattern = (String)((ObjectInputStream.GetField)object).get("_gmtPattern", null);
                if (this._gmtPattern != null) {
                    String[] arrstring = (String[])((ObjectInputStream.GetField)object).get("_gmtOffsetPatterns", null);
                    if (arrstring != null) {
                        if (arrstring.length >= 4) {
                            this._gmtOffsetPatterns = new String[6];
                            if (arrstring.length == 4) {
                                for (int i = 0; i < 4; ++i) {
                                    this._gmtOffsetPatterns[i] = arrstring[i];
                                }
                                this._gmtOffsetPatterns[GMTOffsetPatternType.POSITIVE_H.ordinal()] = TimeZoneFormat.truncateOffsetPattern(this._gmtOffsetPatterns[GMTOffsetPatternType.POSITIVE_HM.ordinal()]);
                                this._gmtOffsetPatterns[GMTOffsetPatternType.NEGATIVE_H.ordinal()] = TimeZoneFormat.truncateOffsetPattern(this._gmtOffsetPatterns[GMTOffsetPatternType.NEGATIVE_HM.ordinal()]);
                            } else {
                                this._gmtOffsetPatterns = arrstring;
                            }
                            this._gmtOffsetDigits = (String[])((ObjectInputStream.GetField)object).get("_gmtOffsetDigits", null);
                            arrstring = this._gmtOffsetDigits;
                            if (arrstring != null) {
                                if (arrstring.length == 10) {
                                    this._gmtZeroFormat = (String)((ObjectInputStream.GetField)object).get("_gmtZeroFormat", null);
                                    if (this._gmtZeroFormat != null) {
                                        this._parseAllStyles = ((ObjectInputStream.GetField)object).get("_parseAllStyles", false);
                                        if (!((ObjectInputStream.GetField)object).defaulted("_parseAllStyles")) {
                                            object = this._tznames;
                                            if (object instanceof TimeZoneNamesImpl) {
                                                this._tznames = TimeZoneNames.getInstance(this._locale);
                                                this._gnames = null;
                                            } else {
                                                this._gnames = new TimeZoneGenericNames(this._locale, (TimeZoneNames)object);
                                            }
                                            this.initGMTPattern(this._gmtPattern);
                                            this.initGMTOffsetPatterns(this._gmtOffsetPatterns);
                                            return;
                                        }
                                        throw new InvalidObjectException("Missing field: parseAllStyles");
                                    }
                                    throw new InvalidObjectException("Missing field: gmtZeroFormat");
                                }
                                throw new InvalidObjectException("Incompatible field: gmtOffsetDigits");
                            }
                            throw new InvalidObjectException("Missing field: gmtOffsetDigits");
                        }
                        throw new InvalidObjectException("Incompatible field: gmtOffsetPatterns");
                    }
                    throw new InvalidObjectException("Missing field: gmtOffsetPatterns");
                }
                throw new InvalidObjectException("Missing field: gmtPattern");
            }
            throw new InvalidObjectException("Missing field: tznames");
        }
        throw new InvalidObjectException("Missing field: locale");
    }

    private static String[] toCodePoints(String string) {
        int n = string.codePointCount(0, string.length());
        String[] arrstring = new String[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = Character.charCount(string.codePointAt(n2));
            arrstring[i] = string.substring(n2, n2 + n3);
            n2 += n3;
        }
        return arrstring;
    }

    private static String truncateOffsetPattern(String string) {
        int n = string.indexOf("mm");
        if (n >= 0) {
            int n2 = string.substring(0, n).lastIndexOf("HH");
            if (n2 >= 0) {
                return string.substring(0, n2 + 2);
            }
            if ((n = string.substring(0, n).lastIndexOf("H")) >= 0) {
                return string.substring(0, n + 1);
            }
            throw new RuntimeException("Bad time zone hour pattern data");
        }
        throw new RuntimeException("Bad time zone hour pattern data");
    }

    private static String unquote(String string) {
        if (string.indexOf(39) < 0) {
            return string;
        }
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\'') {
                if (bl) {
                    stringBuilder.append(c);
                    bl = false;
                } else {
                    bl = true;
                }
                if (!bl2) {
                    bl2 = true;
                    continue;
                }
                bl2 = false;
                continue;
            }
            bl = false;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putField = objectOutputStream.putFields();
        putField.put("_locale", this._locale);
        putField.put("_tznames", this._tznames);
        putField.put("_gmtPattern", this._gmtPattern);
        putField.put("_gmtOffsetPatterns", this._gmtOffsetPatterns);
        putField.put("_gmtOffsetDigits", this._gmtOffsetDigits);
        putField.put("_gmtZeroFormat", this._gmtZeroFormat);
        putField.put("_parseAllStyles", this._parseAllStyles);
        objectOutputStream.writeFields();
    }

    @Override
    public TimeZoneFormat cloneAsThawed() {
        TimeZoneFormat timeZoneFormat = (TimeZoneFormat)super.clone();
        timeZoneFormat._frozen = false;
        return timeZoneFormat;
    }

    public final String format(Style style, TimeZone timeZone, long l) {
        return this.format(style, timeZone, l, null);
    }

    public String format(Style object, TimeZone object2, long l, Output<TimeType> output) {
        Object object3;
        String string = null;
        if (output != null) {
            output.value = TimeType.UNKNOWN;
        }
        int n = 0;
        switch (object) {
            default: {
                break;
            }
            case EXEMPLAR_LOCATION: {
                string = this.formatExemplarLocation((TimeZone)object2);
                n = 1;
                break;
            }
            case ZONE_ID_SHORT: {
                string = object3 = ZoneMeta.getShortID(object2);
                if (object3 == null) {
                    string = UNKNOWN_SHORT_ZONE_ID;
                }
                n = 1;
                break;
            }
            case ZONE_ID: {
                string = object2.getID();
                n = 1;
                break;
            }
            case SPECIFIC_SHORT: {
                string = this.formatSpecific((TimeZone)object2, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, l, output);
                break;
            }
            case SPECIFIC_LONG: {
                string = this.formatSpecific((TimeZone)object2, TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, l, output);
                break;
            }
            case GENERIC_SHORT: {
                string = this.getTimeZoneGenericNames().getDisplayName((TimeZone)object2, TimeZoneGenericNames.GenericNameType.SHORT, l);
                break;
            }
            case GENERIC_LONG: {
                string = this.getTimeZoneGenericNames().getDisplayName((TimeZone)object2, TimeZoneGenericNames.GenericNameType.LONG, l);
                break;
            }
            case GENERIC_LOCATION: {
                string = this.getTimeZoneGenericNames().getGenericLocationName(ZoneMeta.getCanonicalCLDRID(object2));
            }
        }
        object3 = string;
        if (string == null) {
            object3 = string;
            if (n == 0) {
                int[] arrn;
                int[] arrn2 = arrn = new int[2];
                arrn2[0] = 0;
                arrn2[1] = 0;
                object2.getOffset(l, false, arrn);
                n = arrn[0] + arrn[1];
                switch (object) {
                    default: {
                        object = string;
                        break;
                    }
                    case ISO_EXTENDED_LOCAL_FULL: {
                        object = this.formatOffsetISO8601Extended(n, false, false, false);
                        break;
                    }
                    case ISO_EXTENDED_FULL: {
                        object = this.formatOffsetISO8601Extended(n, true, false, false);
                        break;
                    }
                    case ISO_EXTENDED_LOCAL_FIXED: {
                        object = this.formatOffsetISO8601Extended(n, false, false, true);
                        break;
                    }
                    case ISO_EXTENDED_FIXED: {
                        object = this.formatOffsetISO8601Extended(n, true, false, true);
                        break;
                    }
                    case ISO_BASIC_LOCAL_FULL: {
                        object = this.formatOffsetISO8601Basic(n, false, false, false);
                        break;
                    }
                    case ISO_BASIC_FULL: {
                        object = this.formatOffsetISO8601Basic(n, true, false, false);
                        break;
                    }
                    case ISO_BASIC_LOCAL_FIXED: {
                        object = this.formatOffsetISO8601Basic(n, false, false, true);
                        break;
                    }
                    case ISO_BASIC_FIXED: {
                        object = this.formatOffsetISO8601Basic(n, true, false, true);
                        break;
                    }
                    case ISO_BASIC_LOCAL_SHORT: {
                        object = this.formatOffsetISO8601Basic(n, false, true, true);
                        break;
                    }
                    case ISO_BASIC_SHORT: {
                        object = this.formatOffsetISO8601Basic(n, true, true, true);
                        break;
                    }
                    case GENERIC_SHORT: 
                    case SPECIFIC_SHORT: 
                    case LOCALIZED_GMT_SHORT: {
                        object = this.formatOffsetShortLocalizedGMT(n);
                        break;
                    }
                    case GENERIC_LOCATION: 
                    case GENERIC_LONG: 
                    case SPECIFIC_LONG: 
                    case LOCALIZED_GMT: {
                        object = this.formatOffsetLocalizedGMT(n);
                    }
                }
                object3 = object;
                if (output != null) {
                    object2 = arrn[1] != 0 ? TimeType.DAYLIGHT : TimeType.STANDARD;
                    output.value = object2;
                    object3 = object;
                }
            }
        }
        return object3;
    }

    @Override
    public StringBuffer format(Object object, StringBuffer abstractStringBuilder, FieldPosition fieldPosition) {
        block6 : {
            long l;
            block5 : {
                block4 : {
                    l = System.currentTimeMillis();
                    if (!(object instanceof TimeZone)) break block4;
                    object = (TimeZone)object;
                    break block5;
                }
                if (!(object instanceof Calendar)) break block6;
                TimeZone timeZone = ((Calendar)object).getTimeZone();
                l = ((Calendar)object).getTimeInMillis();
                object = timeZone;
            }
            object = this.formatOffsetLocalizedGMT(((TimeZone)object).getOffset(l));
            ((StringBuffer)abstractStringBuilder).append((String)object);
            if (fieldPosition.getFieldAttribute() == DateFormat.Field.TIME_ZONE || fieldPosition.getField() == 17) {
                fieldPosition.setBeginIndex(0);
                fieldPosition.setEndIndex(((String)object).length());
            }
            return abstractStringBuilder;
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("Cannot format given Object (");
        ((StringBuilder)abstractStringBuilder).append(object.getClass().getName());
        ((StringBuilder)abstractStringBuilder).append(") as a time zone");
        throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
    }

    public final String formatOffsetISO8601Basic(int n, boolean bl, boolean bl2, boolean bl3) {
        return this.formatOffsetISO8601(n, true, bl, bl2, bl3);
    }

    public final String formatOffsetISO8601Extended(int n, boolean bl, boolean bl2, boolean bl3) {
        return this.formatOffsetISO8601(n, false, bl, bl2, bl3);
    }

    public String formatOffsetLocalizedGMT(int n) {
        return this.formatOffsetLocalizedGMT(n, false);
    }

    public String formatOffsetShortLocalizedGMT(int n) {
        return this.formatOffsetLocalizedGMT(n, true);
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        object = new AttributedString(this.format(object, new StringBuffer(), new FieldPosition(0)).toString());
        ((AttributedString)object).addAttribute(DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE);
        return ((AttributedString)object).getIterator();
    }

    @Override
    public TimeZoneFormat freeze() {
        this._frozen = true;
        return this;
    }

    public EnumSet<ParseOption> getDefaultParseOptions() {
        if (this._parseAllStyles && this._parseTZDBNames) {
            return EnumSet.of(ParseOption.ALL_STYLES, ParseOption.TZ_DATABASE_ABBREVIATIONS);
        }
        if (this._parseAllStyles) {
            return EnumSet.of(ParseOption.ALL_STYLES);
        }
        if (this._parseTZDBNames) {
            return EnumSet.of(ParseOption.TZ_DATABASE_ABBREVIATIONS);
        }
        return EnumSet.noneOf(ParseOption.class);
    }

    public String getGMTOffsetDigits() {
        StringBuilder stringBuilder = new StringBuilder(this._gmtOffsetDigits.length);
        String[] arrstring = this._gmtOffsetDigits;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrstring[i]);
        }
        return stringBuilder.toString();
    }

    public String getGMTOffsetPattern(GMTOffsetPatternType gMTOffsetPatternType) {
        return this._gmtOffsetPatterns[gMTOffsetPatternType.ordinal()];
    }

    public String getGMTPattern() {
        return this._gmtPattern;
    }

    public String getGMTZeroFormat() {
        return this._gmtZeroFormat;
    }

    public TimeZoneNames getTimeZoneNames() {
        return this._tznames;
    }

    @Override
    public boolean isFrozen() {
        return this._frozen;
    }

    public TimeZone parse(Style style, String string, ParsePosition parsePosition, Output<TimeType> output) {
        return this.parse(style, string, parsePosition, null, output);
    }

    /*
     * Exception decompiling
     */
    public TimeZone parse(Style var1_1, String var2_2, ParsePosition var3_3, EnumSet<ParseOption> var4_4, Output<TimeType> var5_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
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

    public final TimeZone parse(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Serializable serializable = this.parse(string, parsePosition);
        if (parsePosition.getErrorIndex() < 0) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unparseable time zone: \"");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("\"");
        throw new ParseException(((StringBuilder)serializable).toString(), 0);
    }

    public final TimeZone parse(String string, ParsePosition parsePosition) {
        return this.parse(Style.GENERIC_LOCATION, string, parsePosition, EnumSet.of(ParseOption.ALL_STYLES), null);
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    public final int parseOffsetISO8601(String string, ParsePosition parsePosition) {
        return TimeZoneFormat.parseOffsetISO8601(string, parsePosition, false, null);
    }

    public int parseOffsetLocalizedGMT(String string, ParsePosition parsePosition) {
        return this.parseOffsetLocalizedGMT(string, parsePosition, false, null);
    }

    public int parseOffsetShortLocalizedGMT(String string, ParsePosition parsePosition) {
        return this.parseOffsetLocalizedGMT(string, parsePosition, true, null);
    }

    public TimeZoneFormat setDefaultParseOptions(EnumSet<ParseOption> enumSet) {
        this._parseAllStyles = enumSet.contains((Object)ParseOption.ALL_STYLES);
        this._parseTZDBNames = enumSet.contains((Object)ParseOption.TZ_DATABASE_ABBREVIATIONS);
        return this;
    }

    public TimeZoneFormat setGMTOffsetDigits(String arrstring) {
        if (!this.isFrozen()) {
            if (arrstring != null) {
                if ((arrstring = TimeZoneFormat.toCodePoints((String)arrstring)).length == 10) {
                    this._gmtOffsetDigits = arrstring;
                    return this;
                }
                throw new IllegalArgumentException("Length of digits must be 10");
            }
            throw new NullPointerException("Null GMT offset digits");
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    public TimeZoneFormat setGMTOffsetPattern(GMTOffsetPatternType gMTOffsetPatternType, String string) {
        if (!this.isFrozen()) {
            if (string != null) {
                Object[] arrobject = TimeZoneFormat.parseOffsetPattern(string, gMTOffsetPatternType.required());
                this._gmtOffsetPatterns[gMTOffsetPatternType.ordinal()] = string;
                this._gmtOffsetPatternItems[gMTOffsetPatternType.ordinal()] = arrobject;
                this.checkAbuttingHoursAndMinutes();
                return this;
            }
            throw new NullPointerException("Null GMT offset pattern");
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    public TimeZoneFormat setGMTPattern(String string) {
        if (!this.isFrozen()) {
            this.initGMTPattern(string);
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    public TimeZoneFormat setGMTZeroFormat(String string) {
        if (!this.isFrozen()) {
            if (string != null) {
                if (string.length() != 0) {
                    this._gmtZeroFormat = string;
                    return this;
                }
                throw new IllegalArgumentException("Empty GMT zero format");
            }
            throw new NullPointerException("Null GMT zero format");
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    public TimeZoneFormat setTimeZoneNames(TimeZoneNames timeZoneNames) {
        if (!this.isFrozen()) {
            this._tznames = timeZoneNames;
            this._gnames = new TimeZoneGenericNames(this._locale, this._tznames);
            return this;
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    private static class GMTOffsetField {
        final char _type;
        final int _width;

        GMTOffsetField(char c, int n) {
            this._type = c;
            this._width = n;
        }

        static boolean isValid(char c, int n) {
            boolean bl;
            boolean bl2 = bl = true;
            if (n != 1) {
                bl2 = n == 2 ? bl : false;
            }
            return bl2;
        }

        char getType() {
            return this._type;
        }

        int getWidth() {
            return this._width;
        }
    }

    public static enum GMTOffsetPatternType {
        POSITIVE_HM("+H:mm", "Hm", true),
        POSITIVE_HMS("+H:mm:ss", "Hms", true),
        NEGATIVE_HM("-H:mm", "Hm", false),
        NEGATIVE_HMS("-H:mm:ss", "Hms", false),
        POSITIVE_H("+H", "H", true),
        NEGATIVE_H("-H", "H", false);
        
        private String _defaultPattern;
        private boolean _isPositive;
        private String _required;

        private GMTOffsetPatternType(String string2, String string3, boolean bl) {
            this._defaultPattern = string2;
            this._required = string3;
            this._isPositive = bl;
        }

        private String defaultPattern() {
            return this._defaultPattern;
        }

        private boolean isPositive() {
            return this._isPositive;
        }

        private String required() {
            return this._required;
        }
    }

    private static enum OffsetFields {
        H,
        HM,
        HMS;
        
    }

    public static enum ParseOption {
        ALL_STYLES,
        TZ_DATABASE_ABBREVIATIONS;
        
    }

    public static enum Style {
        GENERIC_LOCATION(1),
        GENERIC_LONG(2),
        GENERIC_SHORT(4),
        SPECIFIC_LONG(8),
        SPECIFIC_SHORT(16),
        LOCALIZED_GMT(32),
        LOCALIZED_GMT_SHORT(64),
        ISO_BASIC_SHORT(128),
        ISO_BASIC_LOCAL_SHORT(256),
        ISO_BASIC_FIXED(128),
        ISO_BASIC_LOCAL_FIXED(256),
        ISO_BASIC_FULL(128),
        ISO_BASIC_LOCAL_FULL(256),
        ISO_EXTENDED_FIXED(128),
        ISO_EXTENDED_LOCAL_FIXED(256),
        ISO_EXTENDED_FULL(128),
        ISO_EXTENDED_LOCAL_FULL(256),
        ZONE_ID(512),
        ZONE_ID_SHORT(1024),
        EXEMPLAR_LOCATION(2048);
        
        final int flag;

        private Style(int n2) {
            this.flag = n2;
        }
    }

    public static enum TimeType {
        UNKNOWN,
        STANDARD,
        DAYLIGHT;
        
    }

    private static class TimeZoneFormatCache
    extends SoftCache<ULocale, TimeZoneFormat, ULocale> {
        private TimeZoneFormatCache() {
        }

        @Override
        protected TimeZoneFormat createInstance(ULocale serializable, ULocale uLocale) {
            serializable = new TimeZoneFormat(uLocale);
            ((TimeZoneFormat)serializable).freeze();
            return serializable;
        }
    }

}

