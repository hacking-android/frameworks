/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageFormat
extends Format {
    private static final int[] DATE_TIME_MODIFIERS;
    private static final String[] DATE_TIME_MODIFIER_KEYWORDS;
    private static final int INITIAL_FORMATS = 10;
    private static final int MODIFIER_CURRENCY = 1;
    private static final int MODIFIER_DEFAULT = 0;
    private static final int MODIFIER_FULL = 4;
    private static final int MODIFIER_INTEGER = 3;
    private static final int MODIFIER_LONG = 3;
    private static final int MODIFIER_MEDIUM = 2;
    private static final int MODIFIER_PERCENT = 2;
    private static final int MODIFIER_SHORT = 1;
    private static final String[] NUMBER_MODIFIER_KEYWORDS;
    private static final int SEG_INDEX = 1;
    private static final int SEG_MODIFIER = 3;
    private static final int SEG_RAW = 0;
    private static final int SEG_TYPE = 2;
    private static final int TYPE_CHOICE = 4;
    private static final int TYPE_DATE = 2;
    private static final String[] TYPE_KEYWORDS;
    private static final int TYPE_NULL = 0;
    private static final int TYPE_NUMBER = 1;
    private static final int TYPE_TIME = 3;
    private static final long serialVersionUID = 6479157306784022952L;
    private int[] argumentNumbers = new int[10];
    private Format[] formats = new Format[10];
    private Locale locale;
    private int maxOffset = -1;
    private int[] offsets = new int[10];
    private String pattern = "";

    static {
        TYPE_KEYWORDS = new String[]{"", "number", "date", "time", "choice"};
        NUMBER_MODIFIER_KEYWORDS = new String[]{"", "currency", "percent", "integer"};
        DATE_TIME_MODIFIER_KEYWORDS = new String[]{"", "short", "medium", "long", "full"};
        DATE_TIME_MODIFIERS = new int[]{2, 3, 2, 1, 0};
    }

    public MessageFormat(String string) {
        this.locale = Locale.getDefault(Locale.Category.FORMAT);
        this.applyPattern(string);
    }

    public MessageFormat(String string, Locale locale) {
        this.locale = locale;
        this.applyPattern(string);
    }

    private void append(StringBuffer stringBuffer, CharacterIterator characterIterator) {
        if (characterIterator.first() != '\uffff') {
            char c;
            stringBuffer.append(characterIterator.first());
            while ((c = characterIterator.next()) != '\uffff') {
                stringBuffer.append(c);
            }
        }
    }

    private static final void copyAndFixQuotes(String string, int n, int n2, StringBuilder stringBuilder) {
        int n3 = 0;
        int n4 = n;
        n = n3;
        while (n4 < n2) {
            char c = string.charAt(n4);
            if (c == '{') {
                n3 = n;
                if (n == 0) {
                    stringBuilder.append('\'');
                    n3 = 1;
                }
                stringBuilder.append(c);
                n = n3;
            } else if (c == '\'') {
                stringBuilder.append("''");
            } else {
                n3 = n;
                if (n != 0) {
                    stringBuilder.append('\'');
                    n3 = 0;
                }
                stringBuilder.append(c);
                n = n3;
            }
            ++n4;
        }
        if (n != 0) {
            stringBuilder.append('\'');
        }
    }

    private static final int findKeyword(String string, String[] arrstring) {
        int n;
        for (n = 0; n < arrstring.length; ++n) {
            if (!string.equals(arrstring[n])) continue;
            return n;
        }
        String string2 = string.trim().toLowerCase(Locale.ROOT);
        if (string2 != string) {
            for (n = 0; n < arrstring.length; ++n) {
                if (!string2.equals(arrstring[n])) continue;
                return n;
            }
        }
        return -1;
    }

    public static String format(String string, Object ... arrobject) {
        return new MessageFormat(string).format(arrobject);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void makeFormat(int n, int n2, StringBuilder[] object) {
        int n3;
        block27 : {
            Object object2;
            String[] arrstring = new String[((Object)object).length];
            for (n = 0; n < ((Object)object).length; ++n) {
                object2 = object[n];
                object2 = object2 != null ? ((StringBuilder)object2).toString() : "";
                arrstring[n] = object2;
            }
            try {
                n3 = Integer.parseInt(arrstring[1]);
                if (n3 < 0) break block27;
            }
            catch (NumberFormatException numberFormatException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("can't parse argument number: ");
                ((StringBuilder)object).append(arrstring[1]);
                throw new IllegalArgumentException(((StringBuilder)object).toString(), numberFormatException);
            }
            Format[] arrformat = this.formats;
            if (n2 >= arrformat.length) {
                n = arrformat.length * 2;
                object = new Format[n];
                int[] arrn = new int[n];
                object2 = new int[n];
                System.arraycopy(arrformat, 0, object, 0, this.maxOffset + 1);
                System.arraycopy((Object)this.offsets, 0, (Object)arrn, 0, this.maxOffset + 1);
                System.arraycopy((Object)this.argumentNumbers, 0, object2, 0, this.maxOffset + 1);
                this.formats = object;
                this.offsets = arrn;
                this.argumentNumbers = object2;
            }
            n = this.maxOffset;
            this.maxOffset = n2;
            this.offsets[n2] = arrstring[0].length();
            this.argumentNumbers[n2] = n3;
            object = object2 = null;
            if (arrstring[2].length() != 0) {
                n3 = MessageFormat.findKeyword(arrstring[2], TYPE_KEYWORDS);
                object = object2;
                if (n3 != 0) {
                    if (n3 != 1) {
                        if (n3 != 2 && n3 != 3) {
                            if (n3 != 4) {
                                this.maxOffset = n;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("unknown format type: ");
                                ((StringBuilder)object).append(arrstring[2]);
                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                            }
                            try {
                                object = new ChoiceFormat(arrstring[3]);
                            }
                            catch (Exception exception) {
                                this.maxOffset = n;
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Choice Pattern incorrect: ");
                                ((StringBuilder)object2).append(arrstring[3]);
                                throw new IllegalArgumentException(((StringBuilder)object2).toString(), exception);
                            }
                        } else {
                            int n4 = MessageFormat.findKeyword(arrstring[3], DATE_TIME_MODIFIER_KEYWORDS);
                            if (n4 >= 0 && n4 < DATE_TIME_MODIFIER_KEYWORDS.length) {
                                object = n3 == 2 ? DateFormat.getDateInstance(DATE_TIME_MODIFIERS[n4], this.locale) : DateFormat.getTimeInstance(DATE_TIME_MODIFIERS[n4], this.locale);
                            } else {
                                try {
                                    object = new SimpleDateFormat(arrstring[3], this.locale);
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    this.maxOffset = n;
                                    throw illegalArgumentException;
                                }
                            }
                        }
                    } else {
                        n3 = MessageFormat.findKeyword(arrstring[3], NUMBER_MODIFIER_KEYWORDS);
                        if (n3 != 0) {
                            if (n3 != 1) {
                                if (n3 != 2) {
                                    if (n3 != 3) {
                                        try {
                                            object = new DecimalFormat(arrstring[3], DecimalFormatSymbols.getInstance(this.locale));
                                        }
                                        catch (IllegalArgumentException illegalArgumentException) {
                                            this.maxOffset = n;
                                            throw illegalArgumentException;
                                        }
                                    } else {
                                        object = NumberFormat.getIntegerInstance(this.locale);
                                    }
                                } else {
                                    object = NumberFormat.getPercentInstance(this.locale);
                                }
                            } else {
                                object = NumberFormat.getCurrencyInstance(this.locale);
                            }
                        } else {
                            object = NumberFormat.getInstance(this.locale);
                        }
                    }
                }
            }
            this.formats[n2] = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("negative argument number: ");
        ((StringBuilder)object).append(n3);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private void readObject(ObjectInputStream arrn) throws IOException, ClassNotFoundException {
        int n;
        block3 : {
            arrn.defaultReadObject();
            int n2 = this.maxOffset;
            n2 = n2 >= -1 && this.formats.length > n2 && this.offsets.length > n2 && this.argumentNumbers.length > n2 ? 1 : 0;
            n = n2;
            if (n2 != 0) {
                int n3 = this.pattern.length() + 1;
                int n4 = this.maxOffset;
                do {
                    n = n2;
                    if (n4 < 0) break block3;
                    arrn = this.offsets;
                    if (arrn[n4] < 0 || arrn[n4] > n3) break;
                    n3 = arrn[n4];
                    --n4;
                } while (true);
                n = 0;
            }
        }
        if (n != 0) {
            return;
        }
        throw new InvalidObjectException("Could not reconstruct MessageFormat from corrupt stream.");
    }

    private StringBuffer subformat(Object[] object, StringBuffer stringBuffer, FieldPosition fieldPosition, List<AttributedCharacterIterator> list) {
        int n = 0;
        int n2 = stringBuffer.length();
        for (int i = 0; i <= this.maxOffset; ++i) {
            int n3;
            stringBuffer.append(this.pattern.substring(n, this.offsets[i]));
            n = this.offsets[i];
            int n4 = this.argumentNumbers[i];
            if (object != null && n4 < ((Object)object).length) {
                Object object2;
                Format format;
                Object object3 = object[n4];
                Object object4 = null;
                Format format2 = null;
                if (object3 == null) {
                    object4 = "null";
                    object2 = object3;
                    format = format2;
                } else {
                    Object object5 = this.formats;
                    if (object5[i] != null) {
                        format2 = object5[i];
                        object2 = object3;
                        format = format2;
                        if (format2 instanceof ChoiceFormat) {
                            object5 = object5[i].format(object3);
                            object2 = object3;
                            object4 = object5;
                            format = format2;
                            if (((String)object5).indexOf(123) >= 0) {
                                format = new MessageFormat((String)object5, this.locale);
                                object2 = object;
                                object4 = null;
                            }
                        }
                    } else if (object3 instanceof Number) {
                        format = NumberFormat.getInstance(this.locale);
                        object2 = object3;
                    } else if (object3 instanceof Date) {
                        format = DateFormat.getDateTimeInstance(3, 3, this.locale);
                        object2 = object3;
                    } else if (object3 instanceof String) {
                        object4 = (String)object3;
                        object2 = object3;
                        format = format2;
                    } else {
                        object5 = object3.toString();
                        object2 = object3;
                        object4 = object5;
                        format = format2;
                        if (object5 == null) {
                            object4 = "null";
                            format = format2;
                            object2 = object3;
                        }
                    }
                }
                if (list != null) {
                    n3 = n2;
                    if (n2 != stringBuffer.length()) {
                        list.add(this.createAttributedCharacterIterator(stringBuffer.substring(n2)));
                        n3 = stringBuffer.length();
                    }
                    n2 = n3;
                    if (format != null) {
                        object4 = format.formatToCharacterIterator(object2);
                        this.append(stringBuffer, (CharacterIterator)object4);
                        n2 = n3;
                        if (n3 != stringBuffer.length()) {
                            list.add(this.createAttributedCharacterIterator((AttributedCharacterIterator)object4, (AttributedCharacterIterator.Attribute)Field.ARGUMENT, (Object)n4));
                            n2 = stringBuffer.length();
                        }
                        object4 = null;
                    }
                    n3 = n2;
                    if (object4 != null) {
                        n3 = n2;
                        if (((String)object4).length() > 0) {
                            stringBuffer.append((String)object4);
                            list.add(this.createAttributedCharacterIterator((String)object4, (AttributedCharacterIterator.Attribute)Field.ARGUMENT, (Object)n4));
                            n3 = stringBuffer.length();
                        }
                    }
                } else {
                    if (format != null) {
                        object4 = format.format(object2);
                    }
                    n3 = stringBuffer.length();
                    stringBuffer.append((String)object4);
                    if (i == 0 && fieldPosition != null && Field.ARGUMENT.equals(fieldPosition.getFieldAttribute())) {
                        fieldPosition.setBeginIndex(n3);
                        fieldPosition.setEndIndex(stringBuffer.length());
                    }
                    n3 = stringBuffer.length();
                }
            } else {
                stringBuffer.append('{');
                stringBuffer.append(n4);
                stringBuffer.append('}');
                n3 = n2;
            }
            n2 = n3;
        }
        object = this.pattern;
        stringBuffer.append(((String)object).substring(n, ((String)object).length()));
        if (list != null && n2 != stringBuffer.length()) {
            list.add(this.createAttributedCharacterIterator(stringBuffer.substring(n2)));
        }
        return stringBuffer;
    }

    public void applyPattern(String string) {
        StringBuilder[] arrstringBuilder = new StringBuilder[4];
        arrstringBuilder[0] = new StringBuilder();
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        this.maxOffset = -1;
        int n5 = 0;
        while (n5 < string.length()) {
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            block20 : {
                char c;
                block28 : {
                    block22 : {
                        block27 : {
                            block23 : {
                                block24 : {
                                    block25 : {
                                        block26 : {
                                            block21 : {
                                                block19 : {
                                                    c = string.charAt(n5);
                                                    if (n != 0) break block19;
                                                    if (c == '\'') {
                                                        if (n5 + 1 < string.length() && string.charAt(n5 + 1) == '\'') {
                                                            arrstringBuilder[n].append(c);
                                                            n8 = n5 + 1;
                                                            n6 = n;
                                                            n10 = n2;
                                                            n7 = n3;
                                                            n9 = n4;
                                                        } else {
                                                            n6 = n3 == 0 ? 1 : 0;
                                                            n7 = n6;
                                                            n6 = n;
                                                            n10 = n2;
                                                            n9 = n4;
                                                            n8 = n5;
                                                        }
                                                    } else if (c == '{' && n3 == 0) {
                                                        n6 = n = 1;
                                                        n10 = n2;
                                                        n7 = n3;
                                                        n9 = n4;
                                                        n8 = n5;
                                                        if (arrstringBuilder[1] == null) {
                                                            arrstringBuilder[1] = new StringBuilder();
                                                            n6 = n;
                                                            n10 = n2;
                                                            n7 = n3;
                                                            n9 = n4;
                                                            n8 = n5;
                                                        }
                                                    } else {
                                                        arrstringBuilder[n].append(c);
                                                        n6 = n;
                                                        n10 = n2;
                                                        n7 = n3;
                                                        n9 = n4;
                                                        n8 = n5;
                                                    }
                                                    break block20;
                                                }
                                                if (n3 == 0) break block21;
                                                arrstringBuilder[n].append(c);
                                                n6 = n;
                                                n10 = n2;
                                                n7 = n3;
                                                n9 = n4;
                                                n8 = n5;
                                                if (c == '\'') {
                                                    n7 = 0;
                                                    n6 = n;
                                                    n10 = n2;
                                                    n9 = n4;
                                                    n8 = n5;
                                                }
                                                break block20;
                                            }
                                            if (c == ' ') break block22;
                                            if (c == '\'') break block23;
                                            if (c == ',') break block24;
                                            if (c == '{') break block25;
                                            if (c == '}') break block26;
                                            n7 = n3;
                                            break block27;
                                        }
                                        if (n4 == 0) {
                                            n6 = 0;
                                            this.makeFormat(n5, n2, arrstringBuilder);
                                            n10 = n2 + 1;
                                            arrstringBuilder[1] = null;
                                            arrstringBuilder[2] = null;
                                            arrstringBuilder[3] = null;
                                            n7 = n3;
                                            n9 = n4;
                                            n8 = n5;
                                        } else {
                                            n9 = n4 - 1;
                                            arrstringBuilder[n].append(c);
                                            n6 = n;
                                            n10 = n2;
                                            n7 = n3;
                                            n8 = n5;
                                        }
                                        break block20;
                                    }
                                    n9 = n4 + 1;
                                    arrstringBuilder[n].append(c);
                                    n6 = n;
                                    n10 = n2;
                                    n7 = n3;
                                    n8 = n5;
                                    break block20;
                                }
                                if (n < 3) {
                                    n6 = ++n;
                                    n10 = n2;
                                    n7 = n3;
                                    n9 = n4;
                                    n8 = n5;
                                    if (arrstringBuilder[n] == null) {
                                        arrstringBuilder[n] = new StringBuilder();
                                        n6 = n;
                                        n10 = n2;
                                        n7 = n3;
                                        n9 = n4;
                                        n8 = n5;
                                    }
                                } else {
                                    arrstringBuilder[n].append(c);
                                    n6 = n;
                                    n10 = n2;
                                    n7 = n3;
                                    n9 = n4;
                                    n8 = n5;
                                }
                                break block20;
                            }
                            n7 = 1;
                        }
                        arrstringBuilder[n].append(c);
                        n6 = n;
                        n10 = n2;
                        n9 = n4;
                        n8 = n5;
                        break block20;
                    }
                    if (n != 2) break block28;
                    n6 = n;
                    n10 = n2;
                    n7 = n3;
                    n9 = n4;
                    n8 = n5;
                    if (arrstringBuilder[2].length() <= 0) break block20;
                }
                arrstringBuilder[n].append(c);
                n8 = n5;
                n9 = n4;
                n7 = n3;
                n10 = n2;
                n6 = n;
            }
            n5 = n8 + 1;
            n = n6;
            n2 = n10;
            n3 = n7;
            n4 = n9;
        }
        if (n4 == 0 && n != 0) {
            this.maxOffset = -1;
            throw new IllegalArgumentException("Unmatched braces in the pattern.");
        }
        this.pattern = arrstringBuilder[0].toString();
    }

    @Override
    public Object clone() {
        Format[] arrformat;
        MessageFormat messageFormat = (MessageFormat)super.clone();
        messageFormat.formats = (Format[])this.formats.clone();
        for (int i = 0; i < (arrformat = this.formats).length; ++i) {
            if (arrformat[i] == null) continue;
            messageFormat.formats[i] = (Format)arrformat[i].clone();
        }
        messageFormat.offsets = (int[])this.offsets.clone();
        messageFormat.argumentNumbers = (int[])this.argumentNumbers.clone();
        return messageFormat;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            Locale locale;
            object = (MessageFormat)object;
            if (!(this.maxOffset == ((MessageFormat)object).maxOffset && this.pattern.equals(((MessageFormat)object).pattern) && ((locale = this.locale) != null && locale.equals(((MessageFormat)object).locale) || this.locale == null && ((MessageFormat)object).locale == null) && Arrays.equals(this.offsets, ((MessageFormat)object).offsets) && Arrays.equals(this.argumentNumbers, ((MessageFormat)object).argumentNumbers) && Arrays.equals(this.formats, ((MessageFormat)object).formats))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public final StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.subformat((Object[])object, stringBuffer, fieldPosition, null);
    }

    public final StringBuffer format(Object[] arrobject, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.subformat(arrobject, stringBuffer, fieldPosition, null);
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<AttributedCharacterIterator> arrayList = new ArrayList<AttributedCharacterIterator>();
        if (object != null) {
            this.subformat((Object[])object, stringBuffer, null, arrayList);
            if (arrayList.size() == 0) {
                return this.createAttributedCharacterIterator("");
            }
            return this.createAttributedCharacterIterator(arrayList.toArray(new AttributedCharacterIterator[arrayList.size()]));
        }
        throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
    }

    public Format[] getFormats() {
        int n = this.maxOffset;
        Format[] arrformat = new Format[n + 1];
        System.arraycopy(this.formats, 0, arrformat, 0, n + 1);
        return arrformat;
    }

    public Format[] getFormatsByArgumentIndex() {
        Object[] arrobject;
        int n;
        int n2 = -1;
        for (n = 0; n <= this.maxOffset; ++n) {
            arrobject = this.argumentNumbers;
            int n3 = n2;
            if (arrobject[n] > n2) {
                n3 = arrobject[n];
            }
            n2 = n3;
        }
        arrobject = new Format[n2 + 1];
        for (n = 0; n <= this.maxOffset; ++n) {
            arrobject[this.argumentNumbers[n]] = (int)this.formats[n];
        }
        return arrobject;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public int hashCode() {
        return this.pattern.hashCode();
    }

    public Object[] parse(String arrobject) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        arrobject = this.parse((String)arrobject, parsePosition);
        if (parsePosition.index != 0) {
            return arrobject;
        }
        throw new ParseException("MessageFormat parse error!", parsePosition.errorIndex);
    }

    public Object[] parse(String string, ParsePosition parsePosition) {
        int n;
        int n2;
        Object[] arrobject;
        if (string == null) {
            return new Object[0];
        }
        int n3 = -1;
        for (n = 0; n <= this.maxOffset; ++n) {
            arrobject = this.argumentNumbers;
            n2 = n3;
            if (arrobject[n] > n3) {
                n2 = arrobject[n];
            }
            n3 = n2;
        }
        arrobject = new Object[n3 + 1];
        n3 = 0;
        n = parsePosition.index;
        ParsePosition parsePosition2 = new ParsePosition(0);
        for (n2 = 0; n2 <= this.maxOffset; ++n2) {
            int n4 = this.offsets[n2] - n3;
            if (n4 != 0 && !this.pattern.regionMatches(n3, string, n, n4)) {
                parsePosition.errorIndex = n;
                return null;
            }
            int n5 = n + n4;
            n3 += n4;
            Object object = this.formats;
            if (object[n2] == null) {
                n = n2 != this.maxOffset ? this.offsets[n2 + 1] : this.pattern.length();
                if ((n = n3 >= n ? string.length() : string.indexOf(this.pattern.substring(n3, n), n5)) < 0) {
                    parsePosition.errorIndex = n5;
                    return null;
                }
                object = string.substring(n5, n);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("{");
                stringBuilder.append(this.argumentNumbers[n2]);
                stringBuilder.append("}");
                if (((String)object).equals(stringBuilder.toString())) continue;
                arrobject[this.argumentNumbers[n2]] = (int)string.substring(n5, n);
                continue;
            }
            parsePosition2.index = n5;
            arrobject[this.argumentNumbers[n2]] = (int)object[n2].parseObject(string, parsePosition2);
            if (parsePosition2.index == n5) {
                parsePosition.errorIndex = n5;
                return null;
            }
            n = parsePosition2.index;
        }
        n2 = this.pattern.length() - n3;
        if (n2 != 0 && !this.pattern.regionMatches(n3, string, n, n2)) {
            parsePosition.errorIndex = n;
            return null;
        }
        parsePosition.index = n + n2;
        return arrobject;
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    public void setFormat(int n, Format serializable) {
        if (n <= this.maxOffset) {
            this.formats[n] = serializable;
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("maxOffset=");
        ((StringBuilder)serializable).append(this.maxOffset);
        ((StringBuilder)serializable).append("; formatElementIndex=");
        ((StringBuilder)serializable).append(n);
        throw new ArrayIndexOutOfBoundsException(((StringBuilder)serializable).toString());
    }

    public void setFormatByArgumentIndex(int n, Format format) {
        for (int i = 0; i <= this.maxOffset; ++i) {
            if (this.argumentNumbers[i] != n) continue;
            this.formats[i] = format;
        }
    }

    public void setFormats(Format[] arrformat) {
        int n = arrformat.length;
        int n2 = this.maxOffset;
        int n3 = n;
        if (n > n2 + 1) {
            n3 = n2 + 1;
        }
        for (n = 0; n < n3; ++n) {
            this.formats[n] = arrformat[n];
        }
    }

    public void setFormatsByArgumentIndex(Format[] arrformat) {
        for (int i = 0; i <= this.maxOffset; ++i) {
            int n = this.argumentNumbers[i];
            if (n >= arrformat.length) continue;
            this.formats[i] = arrformat[n];
        }
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String toPattern() {
        Object object;
        int n = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= this.maxOffset; ++i) {
            MessageFormat.copyAndFixQuotes(this.pattern, n, this.offsets[i], stringBuilder);
            int n2 = this.offsets[i];
            stringBuilder.append('{');
            stringBuilder.append(this.argumentNumbers[i]);
            object = this.formats[i];
            if (object != null) {
                if (object instanceof NumberFormat) {
                    if (object.equals(NumberFormat.getInstance(this.locale))) {
                        stringBuilder.append(",number");
                    } else if (object.equals(NumberFormat.getCurrencyInstance(this.locale))) {
                        stringBuilder.append(",number,currency");
                    } else if (object.equals(NumberFormat.getPercentInstance(this.locale))) {
                        stringBuilder.append(",number,percent");
                    } else if (object.equals(NumberFormat.getIntegerInstance(this.locale))) {
                        stringBuilder.append(",number,integer");
                    } else if (object instanceof DecimalFormat) {
                        stringBuilder.append(",number,");
                        stringBuilder.append(((DecimalFormat)object).toPattern());
                    } else if (object instanceof ChoiceFormat) {
                        stringBuilder.append(",choice,");
                        stringBuilder.append(((ChoiceFormat)object).toPattern());
                    }
                } else if (object instanceof DateFormat) {
                    int[] arrn;
                    for (n = 0; n < (arrn = DATE_TIME_MODIFIERS).length; ++n) {
                        if (object.equals(DateFormat.getDateInstance(arrn[n], this.locale))) {
                            stringBuilder.append(",date");
                            break;
                        }
                        if (!object.equals(DateFormat.getTimeInstance(DATE_TIME_MODIFIERS[n], this.locale))) continue;
                        stringBuilder.append(",time");
                        break;
                    }
                    if (n >= DATE_TIME_MODIFIERS.length) {
                        if (object instanceof SimpleDateFormat) {
                            stringBuilder.append(",date,");
                            stringBuilder.append(((SimpleDateFormat)object).toPattern());
                        }
                    } else if (n != 0) {
                        stringBuilder.append(',');
                        stringBuilder.append(DATE_TIME_MODIFIER_KEYWORDS[n]);
                    }
                }
            }
            stringBuilder.append('}');
            n = n2;
        }
        object = this.pattern;
        MessageFormat.copyAndFixQuotes((String)object, n, ((String)object).length(), stringBuilder);
        return stringBuilder.toString();
    }

    public static class Field
    extends Format.Field {
        public static final Field ARGUMENT = new Field("message argument field");
        private static final long serialVersionUID = 7899943957617360810L;

        protected Field(String string) {
            super(string);
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() == Field.class) {
                return ARGUMENT;
            }
            throw new InvalidObjectException("subclass didn't correctly implement readResolve");
        }
    }

}

