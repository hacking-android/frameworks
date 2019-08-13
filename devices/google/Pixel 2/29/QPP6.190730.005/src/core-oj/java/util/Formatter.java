/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.LocaleData
 */
package java.util;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Formattable;
import java.util.FormatterClosedException;
import java.util.IllegalFormatCodePointException;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatFlagsException;
import java.util.IllegalFormatPrecisionException;
import java.util.IllegalFormatWidthException;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.MissingFormatWidthException;
import java.util.Objects;
import java.util.UnknownFormatConversionException;
import java.util.UnknownFormatFlagsException;
import libcore.icu.LocaleData;
import sun.misc.FormattedFloatingDecimal;

public final class Formatter
implements Closeable,
Flushable {
    private static final int MAX_FD_CHARS = 30;
    private static double scaleUp;
    private Appendable a;
    private final Locale l;
    private IOException lastException;
    private final char zero;

    public Formatter() {
        this(Locale.getDefault(Locale.Category.FORMAT), new StringBuilder());
    }

    public Formatter(File file) throws FileNotFoundException {
        this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file))));
    }

    public Formatter(File file, String string) throws FileNotFoundException, UnsupportedEncodingException {
        this(file, string, Locale.getDefault(Locale.Category.FORMAT));
    }

    public Formatter(File file, String string, Locale locale) throws FileNotFoundException, UnsupportedEncodingException {
        this(Formatter.toCharset(string), locale, file);
    }

    public Formatter(OutputStream outputStream) {
        this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public Formatter(OutputStream outputStream, String string) throws UnsupportedEncodingException {
        this(outputStream, string, Locale.getDefault(Locale.Category.FORMAT));
    }

    public Formatter(OutputStream outputStream, String string, Locale locale) throws UnsupportedEncodingException {
        this(locale, new BufferedWriter(new OutputStreamWriter(outputStream, string)));
    }

    public Formatter(PrintStream printStream) {
        this(Locale.getDefault(Locale.Category.FORMAT), Objects.requireNonNull(printStream));
    }

    public Formatter(Appendable appendable) {
        this(Locale.getDefault(Locale.Category.FORMAT), Formatter.nonNullAppendable(appendable));
    }

    public Formatter(Appendable appendable, Locale locale) {
        this(locale, Formatter.nonNullAppendable(appendable));
    }

    public Formatter(String string) throws FileNotFoundException {
        this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(new FileOutputStream(string))));
    }

    public Formatter(String string, String string2) throws FileNotFoundException, UnsupportedEncodingException {
        this(string, string2, Locale.getDefault(Locale.Category.FORMAT));
    }

    public Formatter(String string, String string2, Locale locale) throws FileNotFoundException, UnsupportedEncodingException {
        this(Formatter.toCharset(string2), locale, new File(string));
    }

    private Formatter(Charset charset, Locale locale, File file) throws FileNotFoundException {
        this(locale, new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file), charset)));
    }

    public Formatter(Locale locale) {
        this(locale, new StringBuilder());
    }

    private Formatter(Locale locale, Appendable appendable) {
        this.a = appendable;
        this.l = locale;
        this.zero = Formatter.getZero(locale);
    }

    private void ensureOpen() {
        if (this.a != null) {
            return;
        }
        throw new FormatterClosedException();
    }

    private static char getZero(Locale locale) {
        if (locale != null && !locale.equals(Locale.US)) {
            return DecimalFormatSymbols.getInstance(locale).getZeroDigit();
        }
        return '0';
    }

    private static final Appendable nonNullAppendable(Appendable appendable) {
        if (appendable == null) {
            return new StringBuilder();
        }
        return appendable;
    }

    private FormatString[] parse(String string) {
        ArrayList<FormatString> arrayList = new ArrayList<FormatString>();
        int n = 0;
        int n2 = string.length();
        while (n < n2) {
            int n3 = string.indexOf(37, n);
            if (string.charAt(n) != '%') {
                if (n3 == -1) {
                    n3 = n2;
                }
                arrayList.add(new FixedString(string.substring(n, n3)));
            } else {
                FormatSpecifierParser formatSpecifierParser = new FormatSpecifierParser(string, n + 1);
                arrayList.add(formatSpecifierParser.getFormatSpecifier());
                n3 = formatSpecifierParser.getEndIdx();
            }
            n = n3;
        }
        return arrayList.toArray(new FormatString[arrayList.size()]);
    }

    private static Charset toCharset(String string) throws UnsupportedEncodingException {
        Objects.requireNonNull(string, "charsetName");
        try {
            Charset charset = Charset.forName(string);
            return charset;
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException illegalArgumentException) {
            throw new UnsupportedEncodingException(string);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Appendable appendable = this.a;
        if (appendable == null) {
            return;
        }
        try {
            try {
                if (appendable instanceof Closeable) {
                    ((Closeable)((Object)appendable)).close();
                }
            }
            catch (IOException iOException) {
                this.lastException = iOException;
            }
            this.a = null;
            return;
        }
        catch (Throwable throwable2) {}
        this.a = null;
        throw throwable2;
    }

    @Override
    public void flush() {
        this.ensureOpen();
        Appendable appendable = this.a;
        if (appendable instanceof Flushable) {
            try {
                ((Flushable)((Object)appendable)).flush();
            }
            catch (IOException iOException) {
                this.lastException = iOException;
            }
        }
    }

    public Formatter format(String string, Object ... arrobject) {
        return this.format(this.l, string, arrobject);
    }

    public Formatter format(Locale locale, String object, Object ... arrobject) {
        this.ensureOpen();
        int n = -1;
        int n2 = -1;
        FormatString[] arrformatString = this.parse((String)object);
        for (int i = 0; i < arrformatString.length; ++i) {
            int n3;
            FormatString formatString = arrformatString[i];
            int n4 = formatString.index();
            Object var10_11 = null;
            Object var11_12 = null;
            object = null;
            if (n4 != -2) {
                block28 : {
                    if (n4 != -1) {
                        block27 : {
                            if (n4 != 0) {
                                block26 : {
                                    n = n4 - 1;
                                    if (arrobject != null) {
                                        n4 = n;
                                        n3 = n2;
                                        if (n <= arrobject.length - 1) break block26;
                                        n4 = n;
                                        n3 = n2;
                                        n4 = n;
                                        n3 = n2;
                                        object = new MissingFormatArgumentException(formatString.toString());
                                        n4 = n;
                                        n3 = n2;
                                        throw object;
                                    }
                                }
                                if (arrobject != null) {
                                    object = arrobject[n];
                                }
                                n4 = n;
                                n3 = n2;
                                formatString.print(object, locale);
                                continue;
                            }
                            n = ++n2;
                            if (arrobject != null) {
                                n4 = n;
                                n3 = n2;
                                if (n2 <= arrobject.length - 1) break block27;
                                n4 = n;
                                n3 = n2;
                                n4 = n;
                                n3 = n2;
                                object = new MissingFormatArgumentException(formatString.toString());
                                n4 = n;
                                n3 = n2;
                                throw object;
                            }
                        }
                        object = arrobject == null ? var10_11 : arrobject[n2];
                        n4 = n;
                        n3 = n2;
                        formatString.print(object, locale);
                        continue;
                    }
                    if (n >= 0) {
                        if (arrobject != null) {
                            n4 = n;
                            n3 = n2;
                            if (n > arrobject.length - 1) break block28;
                        }
                        object = arrobject == null ? var11_12 : arrobject[n];
                        n4 = n;
                        n3 = n2;
                        formatString.print(object, locale);
                        continue;
                    }
                }
                n4 = n;
                n3 = n2;
                n4 = n;
                n3 = n2;
                object = new MissingFormatArgumentException(formatString.toString());
                n4 = n;
                n3 = n2;
                throw object;
            }
            n4 = n;
            n3 = n2;
            try {
                formatString.print(null, locale);
                continue;
            }
            catch (IOException iOException) {
                this.lastException = iOException;
                n2 = n3;
                n = n4;
            }
        }
        return this;
    }

    public IOException ioException() {
        return this.lastException;
    }

    public Locale locale() {
        this.ensureOpen();
        return this.l;
    }

    public Appendable out() {
        this.ensureOpen();
        return this.a;
    }

    public String toString() {
        this.ensureOpen();
        return this.a.toString();
    }

    public static enum BigDecimalLayoutForm {
        SCIENTIFIC,
        DECIMAL_FLOAT;
        
    }

    private static class Conversion {
        static final char BOOLEAN = 'b';
        static final char BOOLEAN_UPPER = 'B';
        static final char CHARACTER = 'c';
        static final char CHARACTER_UPPER = 'C';
        static final char DATE_TIME = 't';
        static final char DATE_TIME_UPPER = 'T';
        static final char DECIMAL_FLOAT = 'f';
        static final char DECIMAL_INTEGER = 'd';
        static final char GENERAL = 'g';
        static final char GENERAL_UPPER = 'G';
        static final char HASHCODE = 'h';
        static final char HASHCODE_UPPER = 'H';
        static final char HEXADECIMAL_FLOAT = 'a';
        static final char HEXADECIMAL_FLOAT_UPPER = 'A';
        static final char HEXADECIMAL_INTEGER = 'x';
        static final char HEXADECIMAL_INTEGER_UPPER = 'X';
        static final char LINE_SEPARATOR = 'n';
        static final char OCTAL_INTEGER = 'o';
        static final char PERCENT_SIGN = '%';
        static final char SCIENTIFIC = 'e';
        static final char SCIENTIFIC_UPPER = 'E';
        static final char STRING = 's';
        static final char STRING_UPPER = 'S';

        private Conversion() {
        }

        static boolean isCharacter(char c) {
            return c == 'C' || c == 'c';
        }

        static boolean isFloat(char c) {
            if (c != 'A' && c != 'E' && c != 'G' && c != 'a') {
                switch (c) {
                    default: {
                        return false;
                    }
                    case 'e': 
                    case 'f': 
                    case 'g': 
                }
            }
            return true;
        }

        static boolean isGeneral(char c) {
            return c == 'B' || c == 'H' || c == 'S' || c == 'b' || c == 'h' || c == 's';
        }

        static boolean isInteger(char c) {
            return c == 'X' || c == 'd' || c == 'o' || c == 'x';
        }

        static boolean isText(char c) {
            return c == '%' || c == 'n';
        }

        static boolean isValid(char c) {
            boolean bl = Conversion.isGeneral(c) || Conversion.isInteger(c) || Conversion.isFloat(c) || Conversion.isText(c) || c == 't' || Conversion.isCharacter(c);
            return bl;
        }
    }

    private static class DateTime {
        static final char AM_PM = 'p';
        static final char CENTURY = 'C';
        static final char DATE = 'D';
        static final char DATE_TIME = 'c';
        static final char DAY_OF_MONTH = 'e';
        static final char DAY_OF_MONTH_0 = 'd';
        static final char DAY_OF_YEAR = 'j';
        static final char HOUR = 'l';
        static final char HOUR_0 = 'I';
        static final char HOUR_OF_DAY = 'k';
        static final char HOUR_OF_DAY_0 = 'H';
        static final char ISO_STANDARD_DATE = 'F';
        static final char MILLISECOND = 'L';
        static final char MILLISECOND_SINCE_EPOCH = 'Q';
        static final char MINUTE = 'M';
        static final char MONTH = 'm';
        static final char NAME_OF_DAY = 'A';
        static final char NAME_OF_DAY_ABBREV = 'a';
        static final char NAME_OF_MONTH = 'B';
        static final char NAME_OF_MONTH_ABBREV = 'b';
        static final char NAME_OF_MONTH_ABBREV_X = 'h';
        static final char NANOSECOND = 'N';
        static final char SECOND = 'S';
        static final char SECONDS_SINCE_EPOCH = 's';
        static final char TIME = 'T';
        static final char TIME_12_HOUR = 'r';
        static final char TIME_24_HOUR = 'R';
        static final char YEAR_2 = 'y';
        static final char YEAR_4 = 'Y';
        static final char ZONE = 'Z';
        static final char ZONE_NUMERIC = 'z';

        private DateTime() {
        }

        static boolean isValid(char c) {
            if (c != 'F' && c != 'h' && c != 'p' && c != 'H' && c != 'I' && c != 'Y' && c != 'Z' && c != 'r' && c != 's' && c != 'y' && c != 'z') {
                switch (c) {
                    default: {
                        switch (c) {
                            default: {
                                switch (c) {
                                    default: {
                                        switch (c) {
                                            default: {
                                                switch (c) {
                                                    default: {
                                                        return false;
                                                    }
                                                    case 'j': 
                                                    case 'k': 
                                                    case 'l': 
                                                    case 'm': 
                                                }
                                            }
                                            case 'a': 
                                            case 'b': 
                                            case 'c': 
                                            case 'd': 
                                            case 'e': 
                                        }
                                    }
                                    case 'Q': 
                                    case 'R': 
                                    case 'S': 
                                    case 'T': 
                                }
                            }
                            case 'L': 
                            case 'M': 
                            case 'N': 
                        }
                    }
                    case 'A': 
                    case 'B': 
                    case 'C': 
                    case 'D': 
                }
            }
            return true;
        }
    }

    private class FixedString
    implements FormatString {
        private String s;

        FixedString(String string) {
            this.s = string;
        }

        @Override
        public int index() {
            return -2;
        }

        @Override
        public void print(Object object, Locale locale) throws IOException {
            Formatter.this.a.append(this.s);
        }

        @Override
        public String toString() {
            return this.s;
        }
    }

    private static class Flags {
        static final Flags ALTERNATE;
        static final Flags GROUP;
        static final Flags LEADING_SPACE;
        static final Flags LEFT_JUSTIFY;
        static final Flags NONE;
        static final Flags PARENTHESES;
        static final Flags PLUS;
        static final Flags PREVIOUS;
        static final Flags UPPERCASE;
        static final Flags ZERO_PAD;
        private int flags;

        static {
            NONE = new Flags(0);
            LEFT_JUSTIFY = new Flags(1);
            UPPERCASE = new Flags(2);
            ALTERNATE = new Flags(4);
            PLUS = new Flags(8);
            LEADING_SPACE = new Flags(16);
            ZERO_PAD = new Flags(32);
            GROUP = new Flags(64);
            PARENTHESES = new Flags(128);
            PREVIOUS = new Flags(256);
        }

        private Flags(int n) {
            this.flags = n;
        }

        private Flags add(Flags flags) {
            this.flags |= flags.valueOf();
            return this;
        }

        private static Flags parse(char c) {
            if (c != ' ') {
                if (c != '#') {
                    if (c != '(') {
                        if (c != '0') {
                            if (c != '<') {
                                switch (c) {
                                    default: {
                                        throw new UnknownFormatFlagsException(String.valueOf(c));
                                    }
                                    case '-': {
                                        return LEFT_JUSTIFY;
                                    }
                                    case ',': {
                                        return GROUP;
                                    }
                                    case '+': 
                                }
                                return PLUS;
                            }
                            return PREVIOUS;
                        }
                        return ZERO_PAD;
                    }
                    return PARENTHESES;
                }
                return ALTERNATE;
            }
            return LEADING_SPACE;
        }

        public static Flags parse(String object) {
            char[] arrc = ((String)object).toCharArray();
            object = new Flags(0);
            for (int i = 0; i < arrc.length; ++i) {
                Flags flags = Flags.parse(arrc[i]);
                if (!((Flags)object).contains(flags)) {
                    Flags.super.add(flags);
                    continue;
                }
                throw new DuplicateFormatFlagsException(flags.toString());
            }
            return object;
        }

        public static String toString(Flags flags) {
            return flags.toString();
        }

        public boolean contains(Flags flags) {
            boolean bl = (this.flags & flags.valueOf()) == flags.valueOf();
            return bl;
        }

        public Flags dup() {
            return new Flags(this.flags);
        }

        public Flags remove(Flags flags) {
            this.flags &= flags.valueOf();
            return this;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.contains(LEFT_JUSTIFY)) {
                stringBuilder.append('-');
            }
            if (this.contains(UPPERCASE)) {
                stringBuilder.append('^');
            }
            if (this.contains(ALTERNATE)) {
                stringBuilder.append('#');
            }
            if (this.contains(PLUS)) {
                stringBuilder.append('+');
            }
            if (this.contains(LEADING_SPACE)) {
                stringBuilder.append(' ');
            }
            if (this.contains(ZERO_PAD)) {
                stringBuilder.append('0');
            }
            if (this.contains(GROUP)) {
                stringBuilder.append(',');
            }
            if (this.contains(PARENTHESES)) {
                stringBuilder.append('(');
            }
            if (this.contains(PREVIOUS)) {
                stringBuilder.append('<');
            }
            return stringBuilder.toString();
        }

        public int valueOf() {
            return this.flags;
        }
    }

    private class FormatSpecifier
    implements FormatString {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private char c;
        private boolean dt;
        private Flags f;
        private int index;
        private int precision;
        private int width;

        FormatSpecifier(String string, String string2, String string3, String string4, String string5, String string6) {
            block11 : {
                block6 : {
                    block10 : {
                        block9 : {
                            block8 : {
                                block7 : {
                                    block5 : {
                                        this.index = -1;
                                        this.f = Flags.NONE;
                                        this.dt = false;
                                        this.index(string);
                                        this.flags(string2);
                                        this.width(string3);
                                        this.precision(string4);
                                        if (string5 != null) {
                                            this.dt = true;
                                            if (string5.equals("T")) {
                                                this.f.add(Flags.UPPERCASE);
                                            }
                                        }
                                        this.conversion(string6);
                                        if (!this.dt) break block5;
                                        this.checkDateTime();
                                        break block6;
                                    }
                                    if (!Conversion.isGeneral(this.c)) break block7;
                                    this.checkGeneral();
                                    break block6;
                                }
                                if (!Conversion.isCharacter(this.c)) break block8;
                                this.checkCharacter();
                                break block6;
                            }
                            if (!Conversion.isInteger(this.c)) break block9;
                            this.checkInteger();
                            break block6;
                        }
                        if (!Conversion.isFloat(this.c)) break block10;
                        this.checkFloat();
                        break block6;
                    }
                    if (!Conversion.isText(this.c)) break block11;
                    this.checkText();
                }
                return;
            }
            throw new UnknownFormatConversionException(String.valueOf(this.c));
        }

        private char[] addDot(char[] arrc) {
            char[] arrc2 = new char[arrc.length + 1];
            System.arraycopy((Object)arrc, 0, (Object)arrc2, 0, arrc.length);
            arrc2[arrc2.length - 1] = (char)46;
            return arrc2;
        }

        private char[] addZeros(char[] arrc, int n) {
            int n2;
            for (n2 = 0; n2 < arrc.length && arrc[n2] != '.'; ++n2) {
            }
            boolean bl = false;
            if (n2 == arrc.length) {
                bl = true;
            }
            int n3 = arrc.length;
            int n4 = 1;
            int n5 = bl ? 0 : 1;
            if ((n3 = n3 - n2 - n5) == n) {
                return arrc;
            }
            n5 = arrc.length;
            n2 = bl ? n4 : 0;
            char[] arrc2 = new char[n5 + n - n3 + n2];
            System.arraycopy((Object)arrc, 0, (Object)arrc2, 0, arrc.length);
            n = n2 = arrc.length;
            if (bl) {
                arrc2[arrc.length] = (char)46;
                n = n2 + 1;
            }
            while (n < arrc2.length) {
                arrc2[n] = (char)48;
                ++n;
            }
            return arrc2;
        }

        private int adjustWidth(int n, Flags flags, boolean bl) {
            int n2 = n;
            if (n != -1) {
                n2 = n;
                if (bl) {
                    n2 = n;
                    if (flags.contains(Flags.PARENTHESES)) {
                        n2 = n - 1;
                    }
                }
            }
            return n2;
        }

        private void checkBadFlags(Flags ... arrflags) {
            for (int i = 0; i < arrflags.length; ++i) {
                if (!this.f.contains(arrflags[i])) continue;
                this.failMismatch(arrflags[i], this.c);
            }
        }

        private void checkCharacter() {
            int n = this.precision;
            if (n == -1) {
                this.checkBadFlags(Flags.ALTERNATE, Flags.PLUS, Flags.LEADING_SPACE, Flags.ZERO_PAD, Flags.GROUP, Flags.PARENTHESES);
                if (this.width == -1 && this.f.contains(Flags.LEFT_JUSTIFY)) {
                    throw new MissingFormatWidthException(this.toString());
                }
                return;
            }
            throw new IllegalFormatPrecisionException(n);
        }

        private void checkDateTime() {
            int n = this.precision;
            if (n == -1) {
                if (DateTime.isValid(this.c)) {
                    this.checkBadFlags(Flags.ALTERNATE, Flags.PLUS, Flags.LEADING_SPACE, Flags.ZERO_PAD, Flags.GROUP, Flags.PARENTHESES);
                    if (this.width == -1 && this.f.contains(Flags.LEFT_JUSTIFY)) {
                        throw new MissingFormatWidthException(this.toString());
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("t");
                stringBuilder.append(this.c);
                throw new UnknownFormatConversionException(stringBuilder.toString());
            }
            throw new IllegalFormatPrecisionException(n);
        }

        private void checkFloat() {
            this.checkNumeric();
            char c = this.c;
            if (c != 'f') {
                if (c == 'a') {
                    this.checkBadFlags(Flags.PARENTHESES, Flags.GROUP);
                } else if (c == 'e') {
                    this.checkBadFlags(Flags.GROUP);
                } else if (c == 'g') {
                    this.checkBadFlags(Flags.ALTERNATE);
                }
            }
        }

        private void checkGeneral() {
            char c = this.c;
            if ((c == 'b' || c == 'h') && this.f.contains(Flags.ALTERNATE)) {
                this.failMismatch(Flags.ALTERNATE, this.c);
            }
            if (this.width == -1 && this.f.contains(Flags.LEFT_JUSTIFY)) {
                throw new MissingFormatWidthException(this.toString());
            }
            this.checkBadFlags(Flags.PLUS, Flags.LEADING_SPACE, Flags.ZERO_PAD, Flags.GROUP, Flags.PARENTHESES);
        }

        private void checkInteger() {
            this.checkNumeric();
            int n = this.precision;
            if (n == -1) {
                n = this.c;
                if (n == 100) {
                    this.checkBadFlags(Flags.ALTERNATE);
                } else if (n == 111) {
                    this.checkBadFlags(Flags.GROUP);
                } else {
                    this.checkBadFlags(Flags.GROUP);
                }
                return;
            }
            throw new IllegalFormatPrecisionException(n);
        }

        private void checkNumeric() {
            int n = this.width;
            if (n != -1 && n < 0) {
                throw new IllegalFormatWidthException(n);
            }
            n = this.precision;
            if (n != -1 && n < 0) {
                throw new IllegalFormatPrecisionException(n);
            }
            if (this.width == -1 && (this.f.contains(Flags.LEFT_JUSTIFY) || this.f.contains(Flags.ZERO_PAD))) {
                throw new MissingFormatWidthException(this.toString());
            }
            if (this.f.contains(Flags.PLUS) && this.f.contains(Flags.LEADING_SPACE) || this.f.contains(Flags.LEFT_JUSTIFY) && this.f.contains(Flags.ZERO_PAD)) {
                throw new IllegalFormatFlagsException(this.f.toString());
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void checkText() {
            int n = this.precision;
            if (n != -1) throw new IllegalFormatPrecisionException(n);
            n = this.c;
            if (n != 37) {
                if (n != 110) return;
                n = this.width;
                if (n != -1) throw new IllegalFormatWidthException(n);
                if (this.f.valueOf() == Flags.NONE.valueOf()) return;
                throw new IllegalFormatFlagsException(this.f.toString());
            }
            if (this.f.valueOf() != Flags.LEFT_JUSTIFY.valueOf() && this.f.valueOf() != Flags.NONE.valueOf()) {
                throw new IllegalFormatFlagsException(this.f.toString());
            }
            if (this.width != -1 || !this.f.contains(Flags.LEFT_JUSTIFY)) return;
            throw new MissingFormatWidthException(this.toString());
        }

        private char conversion() {
            return this.c;
        }

        private char conversion(String string) {
            this.c = string.charAt(0);
            if (!this.dt) {
                if (Conversion.isValid(this.c)) {
                    if (Character.isUpperCase(this.c)) {
                        this.f.add(Flags.UPPERCASE);
                    }
                    this.c = Character.toLowerCase(this.c);
                    if (Conversion.isText(this.c)) {
                        this.index = -2;
                    }
                } else {
                    throw new UnknownFormatConversionException(String.valueOf(this.c));
                }
            }
            return this.c;
        }

        private void failConversion(char c, Object object) {
            throw new IllegalFormatConversionException(c, object.getClass());
        }

        private void failMismatch(Flags flags, char c) {
            throw new FormatFlagsConversionMismatchException(flags.toString(), c);
        }

        private Flags flags(String string) {
            this.f = Flags.parse(string);
            if (this.f.contains(Flags.PREVIOUS)) {
                this.index = -1;
            }
            return this.f;
        }

        private char getZero(Locale locale) {
            if (locale != null && !locale.equals(Formatter.this.locale())) {
                return DecimalFormatSymbols.getInstance(locale).getZeroDigit();
            }
            return Formatter.this.zero;
        }

        private String hexDouble(double d, int n) {
            block7 : {
                long l;
                long l2;
                int n2;
                int n3;
                block9 : {
                    long l3;
                    block8 : {
                        if (!Double.isFinite(d) || d == 0.0 || n == 0 || n >= 13) break block7;
                        int n4 = Math.getExponent(d);
                        n3 = n4 == -1023 ? 1 : 0;
                        if (n3 != 0) {
                            scaleUp = Math.scalb(1.0, 54);
                            d = scaleUp * d;
                            n4 = Math.getExponent(d);
                        }
                        n2 = 53 - (n * 4 + 1);
                        l = Double.doubleToLongBits(d);
                        l3 = (Long.MAX_VALUE & l) >> n2;
                        l2 = -1L << n2 & l;
                        n = (l3 & 1L) == 0L ? 1 : 0;
                        n4 = (1L << n2 - 1 & l2) != 0L ? 1 : 0;
                        boolean bl = n2 > 1 && (1L << n2 - 1 & l2) != 0L;
                        if (n != 0 && n4 != 0 && bl) break block8;
                        l2 = l3;
                        if (n != 0) break block9;
                        l2 = l3;
                        if (n4 == 0) break block9;
                    }
                    l2 = l3 + 1L;
                }
                if (Double.isInfinite(d = Double.longBitsToDouble(Long.MIN_VALUE & l | l2 << n2))) {
                    return "1.0p1024";
                }
                String string = Double.toHexString(d).substring(2);
                if (n3 == 0) {
                    return string;
                }
                n3 = string.indexOf(112);
                if (n3 == -1) {
                    return null;
                }
                n = Integer.parseInt(string.substring(n3 + 1));
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string.substring(0, n3));
                stringBuilder.append("p");
                stringBuilder.append(Integer.toString(n - 54));
                return stringBuilder.toString();
            }
            return Double.toHexString(d).substring(2);
        }

        private int index(String string) {
            if (string != null) {
                try {
                    this.index = Integer.parseInt(string);
                }
                catch (NumberFormatException numberFormatException) {}
            } else {
                this.index = 0;
            }
            return this.index;
        }

        private String justify(String string) {
            int n;
            if (this.width == -1) {
                return string;
            }
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = this.f.contains(Flags.LEFT_JUSTIFY);
            int n2 = this.width - string.length();
            if (!bl) {
                for (n = 0; n < n2; ++n) {
                    stringBuilder.append(' ');
                }
            }
            stringBuilder.append(string);
            if (bl) {
                for (n = 0; n < n2; ++n) {
                    stringBuilder.append(' ');
                }
            }
            return stringBuilder.toString();
        }

        private StringBuilder leadingSign(StringBuilder stringBuilder, boolean bl) {
            if (!bl) {
                if (this.f.contains(Flags.PLUS)) {
                    stringBuilder.append('+');
                } else if (this.f.contains(Flags.LEADING_SPACE)) {
                    stringBuilder.append(' ');
                }
            } else if (this.f.contains(Flags.PARENTHESES)) {
                stringBuilder.append('(');
            } else {
                stringBuilder.append('-');
            }
            return stringBuilder;
        }

        private StringBuilder localizedMagnitude(StringBuilder stringBuilder, long l, Flags flags, int n, Locale locale) {
            return this.localizedMagnitude(stringBuilder, Long.toString(l, 10).toCharArray(), flags, n, locale);
        }

        private StringBuilder localizedMagnitude(StringBuilder stringBuilder, char[] arrc, Flags flags, int n, Locale cloneable) {
            char c;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            block14 : {
                block15 : {
                    block16 : {
                        char[] arrc2 = arrc;
                        if (stringBuilder == null) {
                            stringBuilder = new StringBuilder();
                        }
                        n8 = stringBuilder.length();
                        c = this.getZero((Locale)cloneable);
                        int n9 = 0;
                        n2 = -1;
                        int n10 = 0;
                        n7 = n4 = arrc2.length;
                        n6 = 0;
                        do {
                            n3 = n7;
                            if (n6 >= n4) break;
                            if (arrc2[n6] == '.') {
                                n3 = n6;
                                break;
                            }
                            ++n6;
                        } while (true);
                        n5 = n10;
                        if (n3 < n4) {
                            n5 = cloneable != null && !((Locale)cloneable).equals(Locale.US) ? (n6 = (int)DecimalFormatSymbols.getInstance((Locale)cloneable).getDecimalSeparator()) : (n6 = 46);
                        }
                        n6 = n9;
                        n7 = n2;
                        if (!flags.contains(Flags.GROUP)) break block14;
                        if (cloneable == null || ((Locale)cloneable).equals(Locale.US)) break block15;
                        n6 = DecimalFormatSymbols.getInstance((Locale)cloneable).getGroupingSeparator();
                        cloneable = (DecimalFormat)NumberFormat.getIntegerInstance((Locale)cloneable);
                        n2 = ((DecimalFormat)cloneable).getGroupingSize();
                        if (!((DecimalFormat)cloneable).isGroupingUsed()) break block16;
                        n7 = n2;
                        if (((DecimalFormat)cloneable).getGroupingSize() != 0) break block14;
                    }
                    n6 = 0;
                    n7 = n2;
                    break block14;
                }
                n6 = 44;
                n7 = 3;
            }
            int n11 = n6;
            for (n2 = 0; n2 < n4; ++n2) {
                if (n2 == n3) {
                    stringBuilder.append((char)n5);
                    n6 = 0;
                } else {
                    stringBuilder.append((char)(arrc[n2] - 48 + c));
                    n6 = n11;
                    if (n11 != 0) {
                        n6 = n11;
                        if (n2 != n3 - 1) {
                            n6 = n11;
                            if ((n3 - n2) % n7 == 1) {
                                stringBuilder.append((char)n11);
                                n6 = n11;
                            }
                        }
                    }
                }
                n11 = n6;
            }
            n7 = stringBuilder.length();
            if (n != -1 && flags.contains(Flags.ZERO_PAD)) {
                for (n6 = 0; n6 < n - n7; ++n6) {
                    stringBuilder.insert(n8, c);
                }
            }
            return stringBuilder;
        }

        private int precision(String object) {
            this.precision = -1;
            if (object != null) {
                try {
                    this.precision = Integer.parseInt((String)object);
                    if (this.precision < 0) {
                        object = new IllegalFormatPrecisionException(this.precision);
                        throw object;
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            return this.precision;
        }

        /*
         * Exception decompiling
         */
        private Appendable print(StringBuilder var1_1, TemporalAccessor var2_3, char var3_4, Locale var4_5) throws IOException {
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
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Exception decompiling
         */
        private Appendable print(StringBuilder var1_1, Calendar var2_2, char var3_3, Locale var4_4) throws IOException {
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
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        private void print(byte by, Locale locale) throws IOException {
            long l;
            block2 : {
                long l2;
                block3 : {
                    l = l2 = (long)by;
                    if (by >= 0) break block2;
                    by = (byte)this.c;
                    if (by == 111) break block3;
                    l = l2;
                    if (by != 120) break block2;
                }
                l = l2 + 256L;
            }
            this.print(l, locale);
        }

        private void print(double d, Locale object) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = Double.compare(d, 0.0) == -1;
            if (!Double.isNaN(d)) {
                d = Math.abs(d);
                this.leadingSign(stringBuilder, bl);
                if (!Double.isInfinite(d)) {
                    this.print(stringBuilder, d, (Locale)object, this.f, this.c, this.precision, bl);
                } else {
                    object = this.f.contains(Flags.UPPERCASE) ? "INFINITY" : "Infinity";
                    stringBuilder.append((String)object);
                }
                this.trailingSign(stringBuilder, bl);
            } else {
                object = this.f.contains(Flags.UPPERCASE) ? "NAN" : "NaN";
                stringBuilder.append((String)object);
            }
            Formatter.this.a.append(this.justify(stringBuilder.toString()));
        }

        private void print(float f, Locale locale) throws IOException {
            this.print((double)f, locale);
        }

        private void print(int n, Locale locale) throws IOException {
            long l;
            block2 : {
                long l2;
                block3 : {
                    l = l2 = (long)n;
                    if (n >= 0) break block2;
                    n = this.c;
                    if (n == 111) break block3;
                    l = l2;
                    if (n != 120) break block2;
                }
                l = l2 + 0x100000000L;
            }
            this.print(l, locale);
        }

        private void print(long l, Locale object) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            int n = this.c;
            boolean bl = false;
            if (n == 100) {
                if (l < 0L) {
                    bl = true;
                }
                char[] arrc = l < 0L ? Long.toString(l, 10).substring(1).toCharArray() : Long.toString(l, 10).toCharArray();
                this.leadingSign(stringBuilder, bl);
                Flags flags = this.f;
                this.localizedMagnitude(stringBuilder, arrc, flags, this.adjustWidth(this.width, flags, bl), (Locale)object);
                this.trailingSign(stringBuilder, bl);
            } else if (n == 111) {
                this.checkBadFlags(Flags.PARENTHESES, Flags.LEADING_SPACE, Flags.PLUS);
                object = Long.toOctalString(l);
                n = this.f.contains(Flags.ALTERNATE) ? ((String)object).length() + 1 : ((String)object).length();
                if (this.f.contains(Flags.ALTERNATE)) {
                    stringBuilder.append('0');
                }
                if (this.f.contains(Flags.ZERO_PAD)) {
                    for (int i = 0; i < this.width - n; ++i) {
                        stringBuilder.append('0');
                    }
                }
                stringBuilder.append((String)object);
            } else if (n == 120) {
                this.checkBadFlags(Flags.PARENTHESES, Flags.LEADING_SPACE, Flags.PLUS);
                String string = Long.toHexString(l);
                n = this.f.contains(Flags.ALTERNATE) ? string.length() + 2 : string.length();
                if (this.f.contains(Flags.ALTERNATE)) {
                    object = this.f.contains(Flags.UPPERCASE) ? "0X" : "0x";
                    stringBuilder.append((String)object);
                }
                if (this.f.contains(Flags.ZERO_PAD)) {
                    for (int i = 0; i < this.width - n; ++i) {
                        stringBuilder.append('0');
                    }
                }
                object = string;
                if (this.f.contains(Flags.UPPERCASE)) {
                    object = string.toUpperCase();
                }
                stringBuilder.append((String)object);
            }
            Formatter.this.a.append(this.justify(stringBuilder.toString()));
        }

        private void print(String object) throws IOException {
            int n = this.precision;
            String string = object;
            if (n != -1) {
                string = object;
                if (n < ((String)object).length()) {
                    string = ((String)object).substring(0, this.precision);
                }
            }
            object = string;
            if (this.f.contains(Flags.UPPERCASE)) {
                object = Formatter.this.l != null ? Formatter.this.l : Locale.getDefault();
                object = string.toUpperCase((Locale)object);
            }
            Formatter.this.a.append(this.justify((String)object));
        }

        private void print(StringBuilder stringBuilder, double d, Locale object, Flags object2, char c, int n, boolean bl) throws IOException {
            block24 : {
                char c2;
                block26 : {
                    char[] arrc;
                    char[] arrc2;
                    block25 : {
                        block23 : {
                            c2 = '\u0006';
                            if (c != 'e') break block23;
                            if (n == -1) {
                                n = c2;
                            }
                            char[] arrc3 = FormattedFloatingDecimal.valueOf(d, n, FormattedFloatingDecimal.Form.SCIENTIFIC);
                            Object object3 = this.addZeros(arrc3.getMantissa(), n);
                            if (object2.contains(Flags.ALTERNATE) && n == 0) {
                                object3 = this.addDot((char[])object3);
                            }
                            if (d == 0.0) {
                                char[] arrc4 = arrc3 = new char[3];
                                arrc4[0] = 43;
                                arrc4[1] = 48;
                                arrc4[2] = 48;
                            } else {
                                arrc3 = arrc3.getExponent();
                            }
                            c = (char)this.width;
                            n = this.width;
                            if (n != -1) {
                                c = (char)this.adjustWidth(n - arrc3.length - 1, (Flags)object2, bl);
                            }
                            this.localizedMagnitude(stringBuilder, (char[])object3, (Flags)object2, (int)c, (Locale)object);
                            object3 = object != null ? object : Locale.getDefault();
                            LocaleData localeData = LocaleData.get((Locale)object3);
                            object3 = object2.contains(Flags.UPPERCASE) ? localeData.exponentSeparator.toUpperCase((Locale)object3) : localeData.exponentSeparator.toLowerCase((Locale)object3);
                            stringBuilder.append((String)object3);
                            object3 = object2.dup().remove(Flags.GROUP);
                            char c3 = arrc3[0];
                            stringBuilder.append(c3);
                            object2 = new char[arrc3.length - 1];
                            System.arraycopy((Object)arrc3, 1, object2, 0, arrc3.length - 1);
                            stringBuilder.append(this.localizedMagnitude(null, (char[])object2, (Flags)object3, -1, (Locale)object));
                            break block24;
                        }
                        if (c != 'f') break block25;
                        if (n != -1) {
                            c2 = n;
                        }
                        char[] arrc5 = this.addZeros(FormattedFloatingDecimal.valueOf(d, c2, FormattedFloatingDecimal.Form.DECIMAL_FLOAT).getMantissa(), c2);
                        if (object2.contains(Flags.ALTERNATE) && c2 == '\u0000') {
                            arrc5 = this.addDot(arrc5);
                        }
                        c = (char)this.width;
                        n = this.width;
                        if (n != -1) {
                            c = (char)this.adjustWidth(n, (Flags)object2, bl);
                        }
                        this.localizedMagnitude(stringBuilder, arrc5, (Flags)object2, (int)c, (Locale)object);
                        break block24;
                    }
                    if (c != 'g') break block26;
                    c = (char)n;
                    if (n == -1) {
                        c = (char)6;
                    } else if (n == 0) {
                        c = '\u0001';
                    }
                    if (d == 0.0) {
                        arrc = new char[]{(char)48};
                        arrc2 = null;
                        n = 0;
                    } else {
                        FormattedFloatingDecimal formattedFloatingDecimal = FormattedFloatingDecimal.valueOf(d, c, FormattedFloatingDecimal.Form.GENERAL);
                        arrc2 = formattedFloatingDecimal.getExponent();
                        arrc = formattedFloatingDecimal.getMantissa();
                        n = formattedFloatingDecimal.getExponentRounded();
                    }
                    c = arrc2 != null ? (char)(c - 1) : (char)(c - (n + 1));
                    arrc = this.addZeros(arrc, c);
                    if (object2.contains(Flags.ALTERNATE) && c == '\u0000') {
                        arrc = this.addDot(arrc);
                    }
                    c = (char)this.width;
                    n = this.width;
                    if (n != -1) {
                        c = arrc2 != null ? (char)this.adjustWidth(n - arrc2.length - 1, (Flags)object2, bl) : (char)this.adjustWidth(n, (Flags)object2, bl);
                    }
                    this.localizedMagnitude(stringBuilder, arrc, (Flags)object2, (int)c, (Locale)object);
                    if (arrc2 == null) break block24;
                    char c4 = object2.contains(Flags.UPPERCASE) ? (c = (char)69) : (c = (char)101);
                    stringBuilder.append(c4);
                    object2 = object2.dup().remove(Flags.GROUP);
                    c4 = arrc2[0];
                    stringBuilder.append(c4);
                    arrc = new char[arrc2.length - 1];
                    System.arraycopy((Object)arrc2, 1, (Object)arrc, 0, arrc2.length - 1);
                    stringBuilder.append(this.localizedMagnitude(null, arrc, (Flags)object2, -1, (Locale)object));
                    break block24;
                }
                if (c != 'a') break block24;
                c = (char)n;
                if (n == -1) {
                    c = '\u0000';
                } else if (n == 0) {
                    c = '\u0001';
                }
                String string = this.hexDouble(d, c);
                bl = object2.contains(Flags.UPPERCASE);
                object = bl ? "0X" : "0x";
                stringBuilder.append((String)object);
                if (object2.contains(Flags.ZERO_PAD)) {
                    for (n = 0; n < this.width - string.length() - 2; ++n) {
                        stringBuilder.append('0');
                    }
                }
                c2 = 'p';
                n = string.indexOf(112);
                object = object2 = string.substring(0, n).toCharArray();
                if (bl) {
                    object = new String((char[])object2).toUpperCase(Locale.US).toCharArray();
                }
                if (c != '\u0000') {
                    object = this.addZeros((char[])object, c);
                }
                stringBuilder.append((char[])object);
                char c5 = c2;
                if (bl) {
                    c5 = c = (char)80;
                }
                stringBuilder.append(c5);
                stringBuilder.append(string.substring(n + 1));
            }
        }

        private void print(StringBuilder stringBuilder, BigDecimal object, Locale locale, Flags flags, char object2, int n, boolean bl) throws IOException {
            block21 : {
                Object object3;
                block22 : {
                    block23 : {
                        block24 : {
                            int n2;
                            block17 : {
                                char[] arrc;
                                block19 : {
                                    block20 : {
                                        block18 : {
                                            object3 = object;
                                            n2 = 6;
                                            if (object2 != 'e') break block17;
                                            if (n == -1) {
                                                n = n2;
                                            }
                                            int n3 = ((BigDecimal)object).scale();
                                            n2 = ((BigDecimal)object).precision();
                                            int n4 = 0;
                                            if (n > n2 - 1) {
                                                object2 = n2;
                                                n -= n2 - 1;
                                            } else {
                                                object2 = n + 1;
                                                n = n4;
                                            }
                                            arrc = new MathContext((int)object2);
                                            object = new BigDecimal(((BigDecimal)object).unscaledValue(), n3, (MathContext)arrc);
                                            object3 = new BigDecimalLayout(((BigDecimal)object).unscaledValue(), ((BigDecimal)object).scale(), BigDecimalLayoutForm.SCIENTIFIC);
                                            arrc = ((BigDecimalLayout)object3).mantissa();
                                            if (n2 == 1) break block18;
                                            object = arrc;
                                            if (((BigDecimalLayout)object3).hasDot()) break block19;
                                        }
                                        if (n > 0) break block20;
                                        object = arrc;
                                        if (!flags.contains(Flags.ALTERNATE)) break block19;
                                    }
                                    object = this.addDot(arrc);
                                }
                                arrc = this.trailingZeros((char[])object, n);
                                object = ((BigDecimalLayout)object3).exponent();
                                object2 = this.width;
                                n = this.width;
                                if (n != -1) {
                                    object2 = this.adjustWidth(n - ((Object)object).length - 1, flags, bl);
                                }
                                this.localizedMagnitude(stringBuilder, arrc, flags, (int)object2, locale);
                                char c = flags.contains(Flags.UPPERCASE) ? (object2 = (Object)69) : (object2 = (Object)101);
                                stringBuilder.append(c);
                                flags = flags.dup().remove(Flags.GROUP);
                                object2 = object[0];
                                stringBuilder.append((char)object[0]);
                                arrc = new char[((Object)object).length - 1];
                                System.arraycopy(object, 1, (Object)arrc, 0, ((Object)object).length - 1);
                                stringBuilder.append(this.localizedMagnitude(null, arrc, flags, -1, locale));
                                break block21;
                            }
                            int n5 = 0;
                            if (object2 != 'f') break block22;
                            object2 = n == -1 ? (Object)n2 : (Object)n;
                            n = ((BigDecimal)object).scale();
                            Object object4 = object3;
                            if (n > object2) {
                                n2 = ((BigDecimal)object).precision();
                                object4 = n2 <= n ? ((BigDecimal)object3).setScale((int)object2, RoundingMode.HALF_UP) : new BigDecimal(((BigDecimal)object).unscaledValue(), n, new MathContext(n2 - (n - object2)));
                            }
                            object3 = new BigDecimalLayout(object4.unscaledValue(), object4.scale(), BigDecimalLayoutForm.DECIMAL_FLOAT);
                            object4 = ((BigDecimalLayout)object3).mantissa();
                            n = n5;
                            if (((BigDecimalLayout)object3).scale() < object2) {
                                n = object2 - ((BigDecimalLayout)object3).scale();
                            }
                            object = object4;
                            if (((BigDecimalLayout)object3).scale() != 0) break block23;
                            if (flags.contains(Flags.ALTERNATE)) break block24;
                            object = object4;
                            if (n <= 0) break block23;
                        }
                        object = this.addDot(((BigDecimalLayout)object3).mantissa());
                    }
                    this.localizedMagnitude(stringBuilder, this.trailingZeros((char[])object, n), flags, this.adjustWidth(this.width, flags, bl), locale);
                    break block21;
                }
                if (object2 == 103) {
                    object2 = n == -1 ? (Object)6 : (n == 0 ? (char)'\u0001' : (Object)n);
                    BigDecimal bigDecimal = BigDecimal.valueOf(1L, 4);
                    BigDecimal bigDecimal2 = BigDecimal.valueOf(1L, -object2);
                    if (!(((BigDecimal)object3).equals(BigDecimal.ZERO) || ((BigDecimal)object3).compareTo(bigDecimal) != -1 && ((BigDecimal)object3).compareTo(bigDecimal2) == -1)) {
                        this.print(stringBuilder, (BigDecimal)object, locale, flags, 'e', object2 - '\u0001', bl);
                    } else {
                        this.print(stringBuilder, (BigDecimal)object, locale, flags, 'f', object2 - (-((BigDecimal)object).scale() + (((BigDecimal)object).unscaledValue().toString().length() - 1)) - 1, bl);
                    }
                } else if (object2 != 97) {
                    // empty if block
                }
            }
        }

        private void print(BigDecimal bigDecimal, Locale locale) throws IOException {
            char c = this.c;
            if (c == 'a') {
                this.failConversion(c, bigDecimal);
            }
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = bigDecimal.signum() == -1;
            bigDecimal = bigDecimal.abs();
            this.leadingSign(stringBuilder, bl);
            this.print(stringBuilder, bigDecimal, locale, this.f, this.c, this.precision, bl);
            this.trailingSign(stringBuilder, bl);
            Formatter.this.a.append(this.justify(stringBuilder.toString()));
        }

        private void print(BigInteger bigInteger, Locale object) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            int n = bigInteger.signum();
            boolean bl = false;
            boolean bl2 = n == -1;
            Object object2 = bigInteger.abs();
            this.leadingSign(stringBuilder, bl2);
            n = this.c;
            if (n == 100) {
                char[] arrc = ((BigInteger)object2).toString().toCharArray();
                object2 = this.f;
                this.localizedMagnitude(stringBuilder, arrc, (Flags)object2, this.adjustWidth(this.width, (Flags)object2, bl2), (Locale)object);
            } else if (n == 111) {
                int n2;
                object = ((BigInteger)object2).toString(8);
                n = n2 = ((String)object).length() + stringBuilder.length();
                if (bl2) {
                    n = n2;
                    if (this.f.contains(Flags.PARENTHESES)) {
                        n = n2 + 1;
                    }
                }
                n2 = n;
                if (this.f.contains(Flags.ALTERNATE)) {
                    n2 = n + 1;
                    stringBuilder.append('0');
                }
                if (this.f.contains(Flags.ZERO_PAD)) {
                    for (n = 0; n < this.width - n2; ++n) {
                        stringBuilder.append('0');
                    }
                }
                stringBuilder.append((String)object);
            } else if (n == 120) {
                int n3;
                object2 = ((BigInteger)object2).toString(16);
                n = n3 = ((String)object2).length() + stringBuilder.length();
                if (bl2) {
                    n = n3;
                    if (this.f.contains(Flags.PARENTHESES)) {
                        n = n3 + 1;
                    }
                }
                n3 = n;
                if (this.f.contains(Flags.ALTERNATE)) {
                    n3 = n + 2;
                    object = this.f.contains(Flags.UPPERCASE) ? "0X" : "0x";
                    stringBuilder.append((String)object);
                }
                if (this.f.contains(Flags.ZERO_PAD)) {
                    for (n = 0; n < this.width - n3; ++n) {
                        stringBuilder.append('0');
                    }
                }
                object = object2;
                if (this.f.contains(Flags.UPPERCASE)) {
                    object = ((String)object2).toUpperCase();
                }
                stringBuilder.append((String)object);
            }
            bl2 = bl;
            if (bigInteger.signum() == -1) {
                bl2 = true;
            }
            this.trailingSign(stringBuilder, bl2);
            Formatter.this.a.append(this.justify(stringBuilder.toString()));
        }

        private void print(TemporalAccessor object, char c, Locale object2) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            this.print(stringBuilder, (TemporalAccessor)object, c, (Locale)object2);
            object = object2 = this.justify(stringBuilder.toString());
            if (this.f.contains(Flags.UPPERCASE)) {
                object = ((String)object2).toUpperCase();
            }
            Formatter.this.a.append((CharSequence)object);
        }

        private void print(Calendar object, char c, Locale object2) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            this.print(stringBuilder, (Calendar)object, c, (Locale)object2);
            object = object2 = this.justify(stringBuilder.toString());
            if (this.f.contains(Flags.UPPERCASE)) {
                object = ((String)object2).toUpperCase();
            }
            Formatter.this.a.append((CharSequence)object);
        }

        private void print(short s, Locale locale) throws IOException {
            long l;
            block2 : {
                long l2;
                block3 : {
                    l = l2 = (long)s;
                    if (s >= 0) break block2;
                    s = (short)this.c;
                    if (s == 111) break block3;
                    l = l2;
                    if (s != 120) break block2;
                }
                l = l2 + 65536L;
            }
            this.print(l, locale);
        }

        private void printBoolean(Object object) throws IOException {
            object = object != null ? (object instanceof Boolean ? ((Boolean)object).toString() : Boolean.toString(true)) : Boolean.toString(false);
            this.print((String)object);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void printCharacter(Object object) throws IOException {
            if (object == null) {
                this.print("null");
                return;
            }
            Object var2_2 = null;
            if (object instanceof Character) {
                object = ((Character)object).toString();
            } else if (object instanceof Byte) {
                byte by = (Byte)object;
                if (!Character.isValidCodePoint(by)) throw new IllegalFormatCodePointException(by);
                object = new String(Character.toChars(by));
            } else if (object instanceof Short) {
                short s = (Short)object;
                if (!Character.isValidCodePoint(s)) throw new IllegalFormatCodePointException(s);
                object = new String(Character.toChars(s));
            } else if (object instanceof Integer) {
                int n = (Integer)object;
                if (!Character.isValidCodePoint(n)) throw new IllegalFormatCodePointException(n);
                object = new String(Character.toChars(n));
            } else {
                this.failConversion(this.c, object);
                object = var2_2;
            }
            this.print((String)object);
        }

        private void printDateTime(Object object, Locale locale) throws IOException {
            if (object == null) {
                this.print("null");
                return;
            }
            Cloneable cloneable = null;
            if (object instanceof Long) {
                cloneable = locale == null ? Locale.US : locale;
                cloneable = Calendar.getInstance((Locale)cloneable);
                ((Calendar)cloneable).setTimeInMillis((Long)object);
                object = cloneable;
            } else if (object instanceof Date) {
                cloneable = locale == null ? Locale.US : locale;
                cloneable = Calendar.getInstance((Locale)cloneable);
                ((Calendar)cloneable).setTime((Date)object);
                object = cloneable;
            } else if (object instanceof Calendar) {
                object = (Calendar)((Calendar)object).clone();
                ((Calendar)object).setLenient(true);
            } else {
                if (object instanceof TemporalAccessor) {
                    this.print((TemporalAccessor)object, this.c, locale);
                    return;
                }
                this.failConversion(this.c, object);
                object = cloneable;
            }
            this.print((Calendar)object, this.c, locale);
        }

        private void printFloat(Object object, Locale locale) throws IOException {
            if (object == null) {
                this.print("null");
            } else if (object instanceof Float) {
                this.print(((Float)object).floatValue(), locale);
            } else if (object instanceof Double) {
                this.print((Double)object, locale);
            } else if (object instanceof BigDecimal) {
                this.print((BigDecimal)object, locale);
            } else {
                this.failConversion(this.c, object);
            }
        }

        private void printHashCode(Object object) throws IOException {
            object = object == null ? "null" : Integer.toHexString(object.hashCode());
            this.print((String)object);
        }

        private void printInteger(Object object, Locale locale) throws IOException {
            if (object == null) {
                this.print("null");
            } else if (object instanceof Byte) {
                this.print((Byte)object, locale);
            } else if (object instanceof Short) {
                this.print((Short)object, locale);
            } else if (object instanceof Integer) {
                this.print((Integer)object, locale);
            } else if (object instanceof Long) {
                this.print((Long)object, locale);
            } else if (object instanceof BigInteger) {
                this.print((BigInteger)object, locale);
            } else {
                this.failConversion(this.c, object);
            }
        }

        private void printString(Object object, Locale locale) throws IOException {
            if (object instanceof Formattable) {
                Formatter formatter;
                Formatter formatter2 = formatter = Formatter.this;
                if (formatter.locale() != locale) {
                    formatter2 = new Formatter(formatter.out(), locale);
                }
                ((Formattable)object).formatTo(formatter2, this.f.valueOf(), this.width, this.precision);
            } else {
                if (this.f.contains(Flags.ALTERNATE)) {
                    this.failMismatch(Flags.ALTERNATE, 's');
                }
                if (object == null) {
                    this.print("null");
                } else {
                    this.print(object.toString());
                }
            }
        }

        private StringBuilder trailingSign(StringBuilder stringBuilder, boolean bl) {
            if (bl && this.f.contains(Flags.PARENTHESES)) {
                stringBuilder.append(')');
            }
            return stringBuilder;
        }

        private char[] trailingZeros(char[] arrc, int n) {
            char[] arrc2 = arrc;
            if (n > 0) {
                char[] arrc3 = new char[arrc.length + n];
                System.arraycopy((Object)arrc, 0, (Object)arrc3, 0, arrc.length);
                n = arrc.length;
                do {
                    arrc2 = arrc3;
                    if (n >= arrc3.length) break;
                    arrc3[n] = (char)48;
                    ++n;
                } while (true);
            }
            return arrc2;
        }

        private int width(String object) {
            this.width = -1;
            if (object != null) {
                try {
                    this.width = Integer.parseInt((String)object);
                    if (this.width < 0) {
                        object = new IllegalFormatWidthException(this.width);
                        throw object;
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            return this.width;
        }

        Flags flags() {
            return this.f;
        }

        @Override
        public int index() {
            return this.index;
        }

        int precision() {
            return this.precision;
        }

        /*
         * Unable to fully structure code
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void print(Object var1_1, Locale var2_2) throws IOException {
            block9 : {
                if (this.dt) {
                    this.printDateTime(var1_1, var2_2);
                    return;
                }
                var3_3 = this.c;
                if (var3_3 == '%') break block9;
                if (var3_3 == 'C') ** GOTO lbl-1000
                if (var3_3 == 's') ** GOTO lbl30
                if (var3_3 == 'x') ** GOTO lbl-1000
                if (var3_3 == 'n') ** GOTO lbl-1000
                if (var3_3 != 'o') {
                    switch (var3_3) {
                        default: {
                            return;
                        }
                        case 'h': {
                            this.printHashCode(var1_1);
                            return;
                        }
                        case 'b': {
                            this.printBoolean(var1_1);
                            return;
                        }
                        case 'a': 
                        case 'e': 
                        case 'f': 
                        case 'g': {
                            this.printFloat(var1_1, var2_2);
                            return;
                        }
                    }
                }
                ** GOTO lbl-1000
lbl-1000: // 1 sources:
                {
                    Formatter.access$000(Formatter.this).append(System.lineSeparator());
                    return;
                    case 'd': lbl-1000: // 3 sources:
                    {
                        this.printInteger(var1_1, var2_2);
                        return;
                    }
lbl30: // 1 sources:
                    this.printString(var1_1, var2_2);
                    return;
                    case 'c': lbl-1000: // 2 sources:
                    {
                        this.printCharacter(var1_1);
                        return;
                    }
                }
            }
            Formatter.access$000(Formatter.this).append('%');
        }

        @Override
        public String toString() {
            char c;
            StringBuilder stringBuilder = new StringBuilder("%");
            stringBuilder.append(this.f.dup().remove(Flags.UPPERCASE).toString());
            char c2 = this.index;
            if (c2 > '\u0000') {
                stringBuilder.append((int)c2);
                stringBuilder.append('$');
            }
            if ((c2 = this.width) != '\uffffffff') {
                stringBuilder.append((int)c2);
            }
            if (this.precision != -1) {
                stringBuilder.append('.');
                stringBuilder.append(this.precision);
            }
            if (this.dt) {
                c = this.f.contains(Flags.UPPERCASE) ? (c2 = 'T') : (c2 = 't');
                stringBuilder.append(c);
            }
            c = this.f.contains(Flags.UPPERCASE) ? (c2 = (char)Character.toUpperCase(this.c)) : (c2 = (char)this.c);
            stringBuilder.append(c);
            return stringBuilder.toString();
        }

        int width() {
            return this.width;
        }

        private class BigDecimalLayout {
            private boolean dot = false;
            private StringBuilder exp;
            private StringBuilder mant;
            private int scale;

            public BigDecimalLayout(BigInteger bigInteger, int n, BigDecimalLayoutForm bigDecimalLayoutForm) {
                this.layout(bigInteger, n, bigDecimalLayoutForm);
            }

            private void layout(BigInteger object, int n, BigDecimalLayoutForm bigDecimalLayoutForm) {
                object = ((BigInteger)object).toString().toCharArray();
                this.scale = n;
                this.mant = new StringBuilder(((char[])object).length + 14);
                if (n == 0) {
                    n = ((char[])object).length;
                    if (n > 1) {
                        this.mant.append(object[0]);
                        if (bigDecimalLayoutForm == BigDecimalLayoutForm.SCIENTIFIC) {
                            this.mant.append('.');
                            this.dot = true;
                            this.mant.append((char[])object, 1, n - 1);
                            this.exp = new StringBuilder("+");
                            if (n < 10) {
                                object = this.exp;
                                ((StringBuilder)object).append("0");
                                ((StringBuilder)object).append(n - 1);
                            } else {
                                this.exp.append(n - 1);
                            }
                        } else {
                            this.mant.append((char[])object, 1, n - 1);
                        }
                    } else {
                        this.mant.append((char[])object);
                        if (bigDecimalLayoutForm == BigDecimalLayoutForm.SCIENTIFIC) {
                            this.exp = new StringBuilder("+00");
                        }
                    }
                    return;
                }
                long l = -((long)n) + (long)(((char[])object).length - 1);
                if (bigDecimalLayoutForm == BigDecimalLayoutForm.DECIMAL_FLOAT) {
                    int n2 = n - ((char[])object).length;
                    if (n2 >= 0) {
                        this.mant.append("0.");
                        this.dot = true;
                        for (n = n2; n > 0; --n) {
                            this.mant.append('0');
                        }
                        this.mant.append((char[])object);
                    } else if (-n2 < ((char[])object).length) {
                        this.mant.append((char[])object, 0, -n2);
                        this.mant.append('.');
                        this.dot = true;
                        this.mant.append((char[])object, -n2, n);
                    } else {
                        this.mant.append((char[])object, 0, ((char[])object).length);
                        for (n2 = 0; n2 < -n; ++n2) {
                            this.mant.append('0');
                        }
                        this.scale = 0;
                    }
                } else {
                    this.mant.append(object[0]);
                    if (((char[])object).length > 1) {
                        this.mant.append('.');
                        this.dot = true;
                        this.mant.append((char[])object, 1, ((char[])object).length - 1);
                    }
                    this.exp = new StringBuilder();
                    if (l != 0L) {
                        char c;
                        long l2 = Math.abs(l);
                        object = this.exp;
                        if (l < 0L) {
                            n = 45;
                            c = n;
                        } else {
                            n = 43;
                            c = n;
                        }
                        ((StringBuilder)object).append(c);
                        if (l2 < 10L) {
                            this.exp.append('0');
                        }
                        this.exp.append(l2);
                    } else {
                        this.exp.append("+00");
                    }
                }
            }

            private char[] toCharArray(StringBuilder stringBuilder) {
                if (stringBuilder == null) {
                    return null;
                }
                char[] arrc = new char[stringBuilder.length()];
                stringBuilder.getChars(0, arrc.length, arrc, 0);
                return arrc;
            }

            public char[] exponent() {
                return this.toCharArray(this.exp);
            }

            public boolean hasDot() {
                return this.dot;
            }

            public char[] layoutChars() {
                StringBuilder stringBuilder = new StringBuilder(this.mant);
                if (this.exp != null) {
                    stringBuilder.append('E');
                    stringBuilder.append(this.exp);
                }
                return this.toCharArray(stringBuilder);
            }

            public char[] mantissa() {
                return this.toCharArray(this.mant);
            }

            public int scale() {
                return this.scale;
            }
        }

    }

    private class FormatSpecifierParser {
        private static final String FLAGS = ",-(+# 0<";
        private String conv;
        private int cursor;
        private String flags;
        private final String format;
        private FormatSpecifier fs;
        private String index;
        private String precision;
        private String tT;
        private String width;

        public FormatSpecifierParser(String charSequence, int n) {
            this.format = charSequence;
            this.cursor = n;
            if (this.nextIsInt()) {
                charSequence = this.nextInt();
                if (this.peek() == '$') {
                    this.index = charSequence;
                    this.advance();
                } else if (((String)charSequence).charAt(0) == '0') {
                    this.back(((String)charSequence).length());
                } else {
                    this.width = charSequence;
                }
            }
            this.flags = "";
            while (this.width == null && FLAGS.indexOf(this.peek()) >= 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.flags);
                ((StringBuilder)charSequence).append(this.advance());
                this.flags = ((StringBuilder)charSequence).toString();
            }
            if (this.width == null && this.nextIsInt()) {
                this.width = this.nextInt();
            }
            if (this.peek() == '.') {
                this.advance();
                if (this.nextIsInt()) {
                    this.precision = this.nextInt();
                } else {
                    throw new IllegalFormatPrecisionException(this.peek());
                }
            }
            if (this.peek() == 't' || this.peek() == 'T') {
                this.tT = String.valueOf(this.advance());
            }
            this.conv = String.valueOf(this.advance());
            this.fs = new FormatSpecifier(this.index, this.flags, this.width, this.precision, this.tT, this.conv);
        }

        private char advance() {
            if (!this.isEnd()) {
                String string = this.format;
                int n = this.cursor;
                this.cursor = n + 1;
                return string.charAt(n);
            }
            throw new UnknownFormatConversionException("End of String");
        }

        private void back(int n) {
            this.cursor -= n;
        }

        private boolean isEnd() {
            boolean bl = this.cursor == this.format.length();
            return bl;
        }

        private String nextInt() {
            int n = this.cursor;
            while (this.nextIsInt()) {
                this.advance();
            }
            return this.format.substring(n, this.cursor);
        }

        private boolean nextIsInt() {
            boolean bl = !this.isEnd() && Character.isDigit(this.peek());
            return bl;
        }

        private char peek() {
            if (!this.isEnd()) {
                return this.format.charAt(this.cursor);
            }
            throw new UnknownFormatConversionException("End of String");
        }

        public int getEndIdx() {
            return this.cursor;
        }

        public FormatSpecifier getFormatSpecifier() {
            return this.fs;
        }
    }

    private static interface FormatString {
        public int index();

        public void print(Object var1, Locale var2) throws IOException;

        public String toString();
    }

}

