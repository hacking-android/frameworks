/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.misc.LRUCache;

public final class Scanner
implements Iterator<String>,
Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String BOOLEAN_PATTERN = "true|false";
    private static final int BUFFER_SIZE = 1024;
    private static Pattern FIND_ANY_PATTERN;
    private static final String LINE_PATTERN = ".*(\r\n|[\n\r\u2028\u2029\u0085])|.+$";
    private static final String LINE_SEPARATOR_PATTERN = "\r\n|[\n\r\u2028\u2029\u0085]";
    private static Pattern NON_ASCII_DIGIT;
    private static Pattern WHITESPACE_PATTERN;
    private static volatile Pattern boolPattern;
    private static volatile Pattern linePattern;
    private static volatile Pattern separatorPattern;
    private int SIMPLE_GROUP_INDEX = 5;
    private CharBuffer buf;
    private boolean closed = false;
    private Pattern decimalPattern;
    private String decimalSeparator = "\\.";
    private int defaultRadix = 10;
    private Pattern delimPattern;
    private String digits = "0123456789abcdefghijklmnopqrstuvwxyz";
    private Pattern floatPattern;
    private String groupSeparator = "\\,";
    private Pattern hasNextPattern;
    private int hasNextPosition;
    private String hasNextResult;
    private String infinityString = "Infinity";
    private Pattern integerPattern;
    private IOException lastException;
    private Locale locale = null;
    private boolean matchValid = false;
    private Matcher matcher;
    private String nanString = "NaN";
    private boolean needInput = false;
    private String negativePrefix = "\\-";
    private String negativeSuffix = "";
    private String non0Digit = "[\\p{javaDigit}&&[^0]]";
    private LRUCache<String, Pattern> patternCache = new LRUCache<String, Pattern>(7){

        @Override
        protected Pattern create(String string) {
            return Pattern.compile(string);
        }

        @Override
        protected boolean hasName(Pattern pattern, String string) {
            return pattern.pattern().equals(string);
        }
    };
    private int position;
    private String positivePrefix = "";
    private String positiveSuffix = "";
    private int radix = 10;
    private int savedScannerPosition = -1;
    private boolean skipped = false;
    private Readable source;
    private boolean sourceClosed = false;
    private Object typeCache = null;

    static {
        WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
        FIND_ANY_PATTERN = Pattern.compile("(?s).*");
        NON_ASCII_DIGIT = Pattern.compile("[\\p{javaDigit}&&[^0-9]]");
    }

    public Scanner(File file) throws FileNotFoundException {
        this(new FileInputStream(file).getChannel());
    }

    public Scanner(File file, String string) throws FileNotFoundException {
        this(Objects.requireNonNull(file), Scanner.toDecoder(string));
    }

    private Scanner(File file, CharsetDecoder charsetDecoder) throws FileNotFoundException {
        this(Scanner.makeReadable(new FileInputStream(file).getChannel(), charsetDecoder));
    }

    public Scanner(InputStream inputStream) {
        this(new InputStreamReader(inputStream), WHITESPACE_PATTERN);
    }

    public Scanner(InputStream inputStream, String string) {
        this(Scanner.makeReadable(Objects.requireNonNull(inputStream, "source"), Scanner.toCharset(string)), WHITESPACE_PATTERN);
    }

    public Scanner(Readable readable) {
        this(Objects.requireNonNull(readable, "source"), WHITESPACE_PATTERN);
    }

    private Scanner(Readable readable, Pattern pattern) {
        this.source = readable;
        this.delimPattern = pattern;
        this.buf = CharBuffer.allocate(1024);
        this.buf.limit(0);
        this.matcher = this.delimPattern.matcher(this.buf);
        this.matcher.useTransparentBounds(true);
        this.matcher.useAnchoringBounds(false);
        this.useLocale(Locale.getDefault(Locale.Category.FORMAT));
    }

    public Scanner(String string) {
        this(new StringReader(string), WHITESPACE_PATTERN);
    }

    public Scanner(ReadableByteChannel readableByteChannel) {
        this(Scanner.makeReadable(Objects.requireNonNull(readableByteChannel, "source")), WHITESPACE_PATTERN);
    }

    public Scanner(ReadableByteChannel readableByteChannel, String string) {
        this(Scanner.makeReadable(Objects.requireNonNull(readableByteChannel, "source"), Scanner.toDecoder(string)), WHITESPACE_PATTERN);
    }

    public Scanner(Path path) throws IOException {
        this(Files.newInputStream(path, new OpenOption[0]));
    }

    public Scanner(Path path, String string) throws IOException {
        this(Objects.requireNonNull(path), Scanner.toCharset(string));
    }

    private Scanner(Path path, Charset charset) throws IOException {
        this(Scanner.makeReadable(Files.newInputStream(path, new OpenOption[0]), charset));
    }

    private static Pattern boolPattern() {
        Pattern pattern;
        Pattern pattern2 = pattern = boolPattern;
        if (pattern == null) {
            pattern2 = pattern = Pattern.compile(BOOLEAN_PATTERN, 2);
            boolPattern = pattern;
        }
        return pattern2;
    }

    private void buildFloatAndDecimalPattern() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("([eE][+-]?");
        charSequence.append("([0-9]|(\\p{javaDigit}))");
        charSequence.append("+)?");
        charSequence = charSequence.toString();
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("(");
        ((StringBuilder)charSequence2).append(this.non0Digit);
        ((StringBuilder)charSequence2).append("([0-9]|(\\p{javaDigit}))");
        ((StringBuilder)charSequence2).append("?");
        ((StringBuilder)charSequence2).append("([0-9]|(\\p{javaDigit}))");
        ((StringBuilder)charSequence2).append("?(");
        ((StringBuilder)charSequence2).append(this.groupSeparator);
        ((StringBuilder)charSequence2).append("([0-9]|(\\p{javaDigit}))");
        ((StringBuilder)charSequence2).append("([0-9]|(\\p{javaDigit}))");
        ((StringBuilder)charSequence2).append("([0-9]|(\\p{javaDigit}))");
        ((StringBuilder)charSequence2).append(")+)");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        CharSequence charSequence3 = new StringBuilder();
        ((StringBuilder)charSequence3).append("((");
        ((StringBuilder)charSequence3).append("([0-9]|(\\p{javaDigit}))");
        ((StringBuilder)charSequence3).append("++)|");
        ((StringBuilder)charSequence3).append((String)charSequence2);
        ((StringBuilder)charSequence3).append(")");
        charSequence3 = ((StringBuilder)charSequence3).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("(");
        ((StringBuilder)charSequence2).append((String)charSequence3);
        ((StringBuilder)charSequence2).append("|");
        ((StringBuilder)charSequence2).append((String)charSequence3);
        ((StringBuilder)charSequence2).append(this.decimalSeparator);
        ((StringBuilder)charSequence2).append("([0-9]|(\\p{javaDigit}))");
        ((StringBuilder)charSequence2).append("*+|");
        ((StringBuilder)charSequence2).append(this.decimalSeparator);
        ((StringBuilder)charSequence2).append("([0-9]|(\\p{javaDigit}))");
        ((StringBuilder)charSequence2).append("++)");
        charSequence3 = ((StringBuilder)charSequence2).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("(NaN|");
        ((StringBuilder)charSequence2).append(this.nanString);
        ((StringBuilder)charSequence2).append("|Infinity|");
        ((StringBuilder)charSequence2).append(this.infinityString);
        ((StringBuilder)charSequence2).append(")");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        CharSequence charSequence4 = new StringBuilder();
        ((StringBuilder)charSequence4).append("(");
        ((StringBuilder)charSequence4).append(this.positivePrefix);
        ((StringBuilder)charSequence4).append((String)charSequence3);
        ((StringBuilder)charSequence4).append(this.positiveSuffix);
        ((StringBuilder)charSequence4).append((String)charSequence);
        ((StringBuilder)charSequence4).append(")");
        charSequence4 = ((StringBuilder)charSequence4).toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(this.negativePrefix);
        stringBuilder.append((String)charSequence3);
        stringBuilder.append(this.negativeSuffix);
        stringBuilder.append((String)charSequence);
        stringBuilder.append(")");
        String string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("(([-+]?");
        stringBuilder.append((String)charSequence3);
        stringBuilder.append((String)charSequence);
        stringBuilder.append(")|");
        stringBuilder.append((String)charSequence4);
        stringBuilder.append("|");
        stringBuilder.append(string);
        stringBuilder.append(")");
        charSequence = stringBuilder.toString();
        charSequence3 = new StringBuilder();
        ((StringBuilder)charSequence3).append("(");
        ((StringBuilder)charSequence3).append(this.positivePrefix);
        ((StringBuilder)charSequence3).append((String)charSequence2);
        ((StringBuilder)charSequence3).append(this.positiveSuffix);
        ((StringBuilder)charSequence3).append(")");
        charSequence3 = ((StringBuilder)charSequence3).toString();
        charSequence4 = new StringBuilder();
        ((StringBuilder)charSequence4).append("(");
        ((StringBuilder)charSequence4).append(this.negativePrefix);
        ((StringBuilder)charSequence4).append((String)charSequence2);
        ((StringBuilder)charSequence4).append(this.negativeSuffix);
        ((StringBuilder)charSequence4).append(")");
        charSequence4 = ((StringBuilder)charSequence4).toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("(([-+]?");
        stringBuilder.append((String)charSequence2);
        stringBuilder.append(")|");
        stringBuilder.append((String)charSequence3);
        stringBuilder.append("|");
        stringBuilder.append((String)charSequence4);
        stringBuilder.append(")");
        charSequence3 = stringBuilder.toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("|");
        ((StringBuilder)charSequence2).append("[-+]?0[xX][0-9a-fA-F]*\\.[0-9a-fA-F]+([pP][-+]?[0-9]+)?");
        ((StringBuilder)charSequence2).append("|");
        ((StringBuilder)charSequence2).append((String)charSequence3);
        this.floatPattern = Pattern.compile(((StringBuilder)charSequence2).toString());
        this.decimalPattern = Pattern.compile((String)charSequence);
    }

    private String buildIntegerPatternString() {
        CharSequence charSequence = this.digits.substring(0, this.radix);
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("((?i)[");
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("]|\\p{javaDigit})");
        charSequence = ((StringBuilder)charSequence2).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("((?i)[");
        ((StringBuilder)charSequence2).append(this.digits.substring(1, this.radix));
        ((StringBuilder)charSequence2).append("]|(");
        ((StringBuilder)charSequence2).append(this.non0Digit);
        ((StringBuilder)charSequence2).append("))");
        CharSequence charSequence3 = ((StringBuilder)charSequence2).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("(");
        ((StringBuilder)charSequence2).append((String)charSequence3);
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("?");
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("?(");
        ((StringBuilder)charSequence2).append(this.groupSeparator);
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(")+)");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence3 = new StringBuilder();
        ((StringBuilder)charSequence3).append("((");
        ((StringBuilder)charSequence3).append((String)charSequence);
        ((StringBuilder)charSequence3).append("++)|");
        ((StringBuilder)charSequence3).append((String)charSequence2);
        ((StringBuilder)charSequence3).append(")");
        charSequence3 = ((StringBuilder)charSequence3).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("([-+]?(");
        ((StringBuilder)charSequence).append((String)charSequence3);
        ((StringBuilder)charSequence).append("))");
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(this.negativePrefix);
        ((StringBuilder)charSequence2).append((String)charSequence3);
        ((StringBuilder)charSequence2).append(this.negativeSuffix);
        charSequence2 = ((StringBuilder)charSequence2).toString();
        CharSequence charSequence4 = new StringBuilder();
        charSequence4.append(this.positivePrefix);
        charSequence4.append((String)charSequence3);
        charSequence4.append(this.positiveSuffix);
        charSequence4 = charSequence4.toString();
        charSequence3 = new StringBuilder();
        ((StringBuilder)charSequence3).append("(");
        ((StringBuilder)charSequence3).append((String)charSequence);
        ((StringBuilder)charSequence3).append(")|(");
        ((StringBuilder)charSequence3).append((String)charSequence4);
        ((StringBuilder)charSequence3).append(")|(");
        ((StringBuilder)charSequence3).append((String)charSequence2);
        ((StringBuilder)charSequence3).append(")");
        return ((StringBuilder)charSequence3).toString();
    }

    private void cacheResult() {
        this.hasNextResult = this.matcher.group();
        this.hasNextPosition = this.matcher.end();
        this.hasNextPattern = this.matcher.pattern();
    }

    private void cacheResult(String string) {
        this.hasNextResult = string;
        this.hasNextPosition = this.matcher.end();
        this.hasNextPattern = this.matcher.pattern();
    }

    private void clearCaches() {
        this.hasNextPattern = null;
        this.typeCache = null;
    }

    private Pattern decimalPattern() {
        if (this.decimalPattern == null) {
            this.buildFloatAndDecimalPattern();
        }
        return this.decimalPattern;
    }

    private void ensureOpen() {
        if (!this.closed) {
            return;
        }
        throw new IllegalStateException("Scanner closed");
    }

    private String findPatternInBuffer(Pattern pattern, int n) {
        int n2;
        this.matchValid = false;
        this.matcher.usePattern(pattern);
        int n3 = this.buf.limit();
        int n4 = -1;
        int n5 = n2 = n3;
        if (n > 0) {
            int n6;
            n4 = n6 = this.position + n;
            n5 = n2;
            if (n6 < n3) {
                n5 = n6;
                n4 = n6;
            }
        }
        this.matcher.region(this.position, n5);
        if (this.matcher.find()) {
            if (this.matcher.hitEnd() && !this.sourceClosed) {
                if (n5 != n4) {
                    this.needInput = true;
                    return null;
                }
                if (n5 == n4 && this.matcher.requireEnd()) {
                    this.needInput = true;
                    return null;
                }
            }
            this.position = this.matcher.end();
            return this.matcher.group();
        }
        if (this.sourceClosed) {
            return null;
        }
        if (n == 0 || n5 != n4) {
            this.needInput = true;
        }
        return null;
    }

    private Pattern floatPattern() {
        if (this.floatPattern == null) {
            this.buildFloatAndDecimalPattern();
        }
        return this.floatPattern;
    }

    private String getCachedResult() {
        this.position = this.hasNextPosition;
        this.hasNextPattern = null;
        this.typeCache = null;
        return this.hasNextResult;
    }

    private String getCompleteTokenInBuffer(Pattern object) {
        boolean bl;
        this.matchValid = false;
        this.matcher.usePattern(this.delimPattern);
        if (!this.skipped) {
            this.matcher.region(this.position, this.buf.limit());
            if (this.matcher.lookingAt()) {
                if (this.matcher.hitEnd() && !this.sourceClosed) {
                    this.needInput = true;
                    return null;
                }
                this.skipped = true;
                this.position = this.matcher.end();
            }
        }
        if (this.position == this.buf.limit()) {
            if (this.sourceClosed) {
                return null;
            }
            this.needInput = true;
            return null;
        }
        this.matcher.region(this.position, this.buf.limit());
        boolean bl2 = bl = this.matcher.find();
        if (bl) {
            bl2 = bl;
            if (this.matcher.end() == this.position) {
                bl2 = this.matcher.find();
            }
        }
        if (bl2) {
            if (this.matcher.requireEnd() && !this.sourceClosed) {
                this.needInput = true;
                return null;
            }
            int n = this.matcher.start();
            Pattern pattern = object;
            if (object == null) {
                pattern = FIND_ANY_PATTERN;
            }
            this.matcher.usePattern(pattern);
            this.matcher.region(this.position, n);
            if (this.matcher.matches()) {
                object = this.matcher.group();
                this.position = this.matcher.end();
                return object;
            }
            return null;
        }
        if (this.sourceClosed) {
            Pattern pattern = object;
            if (object == null) {
                pattern = FIND_ANY_PATTERN;
            }
            this.matcher.usePattern(pattern);
            this.matcher.region(this.position, this.buf.limit());
            if (this.matcher.matches()) {
                object = this.matcher.group();
                this.position = this.matcher.end();
                return object;
            }
            return null;
        }
        this.needInput = true;
        return null;
    }

    private boolean hasTokenInBuffer() {
        this.matchValid = false;
        this.matcher.usePattern(this.delimPattern);
        this.matcher.region(this.position, this.buf.limit());
        if (this.matcher.lookingAt()) {
            this.position = this.matcher.end();
        }
        return this.position != this.buf.limit();
    }

    private Pattern integerPattern() {
        if (this.integerPattern == null) {
            this.integerPattern = this.patternCache.forName(this.buildIntegerPatternString());
        }
        return this.integerPattern;
    }

    private static Pattern linePattern() {
        Pattern pattern;
        Pattern pattern2 = pattern = linePattern;
        if (pattern == null) {
            pattern2 = pattern = Pattern.compile(LINE_PATTERN);
            linePattern = pattern;
        }
        return pattern2;
    }

    private static Readable makeReadable(InputStream inputStream, Charset charset) {
        return new InputStreamReader(inputStream, charset);
    }

    private static Readable makeReadable(ReadableByteChannel readableByteChannel) {
        return Scanner.makeReadable(readableByteChannel, Charset.defaultCharset().newDecoder());
    }

    private static Readable makeReadable(ReadableByteChannel readableByteChannel, CharsetDecoder charsetDecoder) {
        return Channels.newReader(readableByteChannel, charsetDecoder, -1);
    }

    private boolean makeSpace() {
        this.clearCaches();
        int n = this.savedScannerPosition;
        if (n == -1) {
            n = this.position;
        }
        this.buf.position(n);
        if (n > 0) {
            this.buf.compact();
            this.translateSavedIndexes(n);
            this.position -= n;
            this.buf.flip();
            return true;
        }
        CharBuffer charBuffer = CharBuffer.allocate(this.buf.capacity() * 2);
        charBuffer.put(this.buf);
        charBuffer.flip();
        this.translateSavedIndexes(n);
        this.position -= n;
        this.buf = charBuffer;
        this.matcher.reset(this.buf);
        return true;
    }

    private String matchPatternInBuffer(Pattern pattern) {
        this.matchValid = false;
        this.matcher.usePattern(pattern);
        this.matcher.region(this.position, this.buf.limit());
        if (this.matcher.lookingAt()) {
            if (this.matcher.hitEnd() && !this.sourceClosed) {
                this.needInput = true;
                return null;
            }
            this.position = this.matcher.end();
            return this.matcher.group();
        }
        if (this.sourceClosed) {
            return null;
        }
        this.needInput = true;
        return null;
    }

    private String processFloatToken(String charSequence) {
        CharSequence charSequence2;
        charSequence = charSequence2 = ((String)charSequence).replaceAll(this.groupSeparator, "");
        if (!this.decimalSeparator.equals("\\.")) {
            charSequence = ((String)charSequence2).replaceAll(this.decimalSeparator, ".");
        }
        int n = 0;
        int n2 = this.negativePrefix.length();
        charSequence2 = charSequence;
        int n3 = n;
        if (n2 > 0) {
            charSequence2 = charSequence;
            n3 = n;
            if (((String)charSequence).startsWith(this.negativePrefix)) {
                n3 = 1;
                charSequence2 = ((String)charSequence).substring(n2);
            }
        }
        n2 = this.negativeSuffix.length();
        charSequence = charSequence2;
        n = n3;
        if (n2 > 0) {
            charSequence = charSequence2;
            n = n3;
            if (((String)charSequence2).endsWith(this.negativeSuffix)) {
                n = 1;
                charSequence = ((String)charSequence2).substring(((String)charSequence2).length() - n2, ((String)charSequence2).length());
            }
        }
        String string = charSequence;
        if (((String)charSequence).equals(this.nanString)) {
            string = "NaN";
        }
        charSequence2 = string;
        if (string.equals(this.infinityString)) {
            charSequence2 = "Infinity";
        }
        charSequence = charSequence2;
        if (((String)charSequence2).equals("\u221e")) {
            charSequence = "Infinity";
        }
        charSequence2 = charSequence;
        if (n != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("-");
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (NON_ASCII_DIGIT.matcher(charSequence2).find()) {
            charSequence = new StringBuilder();
            for (n3 = 0; n3 < ((String)charSequence2).length(); ++n3) {
                char c = ((String)charSequence2).charAt(n3);
                if (Character.isDigit(c)) {
                    n = Character.digit(c, 10);
                    if (n != -1) {
                        ((StringBuilder)charSequence).append(n);
                        continue;
                    }
                    ((StringBuilder)charSequence).append(c);
                    continue;
                }
                ((StringBuilder)charSequence).append(c);
            }
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    private String processIntegerToken(String charSequence) {
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("");
        ((StringBuilder)charSequence2).append(this.groupSeparator);
        charSequence = charSequence.replaceAll(((StringBuilder)charSequence2).toString(), "");
        boolean bl = false;
        int n = this.negativePrefix.length();
        charSequence2 = charSequence;
        boolean bl2 = bl;
        if (n > 0) {
            charSequence2 = charSequence;
            bl2 = bl;
            if (charSequence.startsWith(this.negativePrefix)) {
                bl2 = true;
                charSequence2 = charSequence.substring(n);
            }
        }
        n = this.negativeSuffix.length();
        charSequence = charSequence2;
        bl = bl2;
        if (n > 0) {
            charSequence = charSequence2;
            bl = bl2;
            if (((String)charSequence2).endsWith(this.negativeSuffix)) {
                bl = true;
                charSequence = ((String)charSequence2).substring(((String)charSequence2).length() - n, ((String)charSequence2).length());
            }
        }
        charSequence2 = charSequence;
        if (bl) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("-");
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return charSequence2;
    }

    private void readInput() {
        int n;
        if (this.buf.limit() == this.buf.capacity()) {
            this.makeSpace();
        }
        int n2 = this.buf.position();
        CharBuffer charBuffer = this.buf;
        charBuffer.position(charBuffer.limit());
        charBuffer = this.buf;
        charBuffer.limit(charBuffer.capacity());
        try {
            n = this.source.read(this.buf);
        }
        catch (IOException iOException) {
            this.lastException = iOException;
            n = -1;
        }
        if (n == -1) {
            this.sourceClosed = true;
            this.needInput = false;
        }
        if (n > 0) {
            this.needInput = false;
        }
        charBuffer = this.buf;
        charBuffer.limit(charBuffer.position());
        this.buf.position(n2);
        this.matcher.reset(this.buf);
    }

    private void revertState() {
        this.position = this.savedScannerPosition;
        this.savedScannerPosition = -1;
        this.skipped = false;
    }

    private boolean revertState(boolean bl) {
        this.position = this.savedScannerPosition;
        this.savedScannerPosition = -1;
        this.skipped = false;
        return bl;
    }

    private void saveState() {
        this.savedScannerPosition = this.position;
    }

    private static Pattern separatorPattern() {
        Pattern pattern;
        Pattern pattern2 = pattern = separatorPattern;
        if (pattern == null) {
            pattern2 = pattern = Pattern.compile(LINE_SEPARATOR_PATTERN);
            separatorPattern = pattern;
        }
        return pattern2;
    }

    private void setRadix(int n) {
        if (n <= 36) {
            if (this.radix != n) {
                this.integerPattern = null;
                this.radix = n;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("radix == ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void throwFor() {
        this.skipped = false;
        if (this.sourceClosed && this.position == this.buf.limit()) {
            throw new NoSuchElementException();
        }
        throw new InputMismatchException();
    }

    private static Charset toCharset(String object) {
        Objects.requireNonNull(object, "charsetName");
        try {
            object = Charset.forName((String)object);
            return object;
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException illegalArgumentException) {
            throw new IllegalArgumentException(illegalArgumentException);
        }
    }

    private static CharsetDecoder toDecoder(String string) {
        if (string != null) {
            try {
                CharsetDecoder charsetDecoder = Charset.forName(string).newDecoder();
                return charsetDecoder;
            }
            catch (IllegalCharsetNameException | UnsupportedCharsetException illegalArgumentException) {
                throw new IllegalArgumentException(string);
            }
        }
        throw new IllegalArgumentException("charsetName == null");
    }

    private void translateSavedIndexes(int n) {
        int n2 = this.savedScannerPosition;
        if (n2 != -1) {
            this.savedScannerPosition = n2 - n;
        }
    }

    private void useTypeCache() {
        if (!this.closed) {
            this.position = this.hasNextPosition;
            this.hasNextPattern = null;
            this.typeCache = null;
            return;
        }
        throw new IllegalStateException("Scanner closed");
    }

    @Override
    public void close() {
        if (this.closed) {
            return;
        }
        Readable readable = this.source;
        if (readable instanceof Closeable) {
            try {
                ((Closeable)((Object)readable)).close();
            }
            catch (IOException iOException) {
                this.lastException = iOException;
            }
        }
        this.sourceClosed = true;
        this.source = null;
        this.closed = true;
    }

    public Pattern delimiter() {
        return this.delimPattern;
    }

    public String findInLine(String string) {
        return this.findInLine(this.patternCache.forName(string));
    }

    public String findInLine(Pattern pattern) {
        this.ensureOpen();
        if (pattern != null) {
            int n;
            block4 : {
                this.clearCaches();
                this.saveState();
                do {
                    if (this.findPatternInBuffer(Scanner.separatorPattern(), 0) != null) {
                        n = this.matcher.start();
                        break block4;
                    }
                    if (!this.needInput) break;
                    this.readInput();
                } while (true);
                n = this.buf.limit();
            }
            this.revertState();
            if ((n -= this.position) == 0) {
                return null;
            }
            return this.findWithinHorizon(pattern, n);
        }
        throw new NullPointerException();
    }

    public String findWithinHorizon(String string, int n) {
        return this.findWithinHorizon(this.patternCache.forName(string), n);
    }

    public String findWithinHorizon(Pattern pattern, int n) {
        this.ensureOpen();
        if (pattern != null) {
            if (n >= 0) {
                this.clearCaches();
                do {
                    String string;
                    if ((string = this.findPatternInBuffer(pattern, n)) != null) {
                        this.matchValid = true;
                        return string;
                    }
                    if (!this.needInput) break;
                    this.readInput();
                } while (true);
                return null;
            }
            throw new IllegalArgumentException("horizon < 0");
        }
        throw new NullPointerException();
    }

    @Override
    public boolean hasNext() {
        this.ensureOpen();
        this.saveState();
        while (!this.sourceClosed) {
            if (this.hasTokenInBuffer()) {
                return this.revertState(true);
            }
            this.readInput();
        }
        return this.revertState(this.hasTokenInBuffer());
    }

    public boolean hasNext(String string) {
        return this.hasNext(this.patternCache.forName(string));
    }

    public boolean hasNext(Pattern pattern) {
        this.ensureOpen();
        if (pattern != null) {
            this.hasNextPattern = null;
            this.saveState();
            do {
                if (this.getCompleteTokenInBuffer(pattern) != null) {
                    this.matchValid = true;
                    this.cacheResult();
                    return this.revertState(true);
                }
                if (!this.needInput) break;
                this.readInput();
            } while (true);
            return this.revertState(false);
        }
        throw new NullPointerException();
    }

    public boolean hasNextBigDecimal() {
        boolean bl;
        this.setRadix(10);
        boolean bl2 = bl = this.hasNext(this.decimalPattern());
        if (bl) {
            try {
                String string = this.processFloatToken(this.hasNextResult);
                BigDecimal bigDecimal = new BigDecimal(string);
                this.typeCache = bigDecimal;
                bl2 = bl;
            }
            catch (NumberFormatException numberFormatException) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public boolean hasNextBigInteger() {
        return this.hasNextBigInteger(this.defaultRadix);
    }

    public boolean hasNextBigInteger(int n) {
        boolean bl;
        this.setRadix(n);
        boolean bl2 = bl = this.hasNext(this.integerPattern());
        if (bl) {
            try {
                String string = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult;
                BigInteger bigInteger = new BigInteger(string, n);
                this.typeCache = bigInteger;
                bl2 = bl;
            }
            catch (NumberFormatException numberFormatException) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public boolean hasNextBoolean() {
        return this.hasNext(Scanner.boolPattern());
    }

    public boolean hasNextByte() {
        return this.hasNextByte(this.defaultRadix);
    }

    public boolean hasNextByte(int n) {
        boolean bl;
        this.setRadix(n);
        boolean bl2 = bl = this.hasNext(this.integerPattern());
        if (bl) {
            try {
                String string = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult;
                this.typeCache = Byte.parseByte(string, n);
                bl2 = bl;
            }
            catch (NumberFormatException numberFormatException) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public boolean hasNextDouble() {
        boolean bl;
        this.setRadix(10);
        boolean bl2 = bl = this.hasNext(this.floatPattern());
        if (bl) {
            try {
                this.typeCache = Double.parseDouble(this.processFloatToken(this.hasNextResult));
                bl2 = bl;
            }
            catch (NumberFormatException numberFormatException) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public boolean hasNextFloat() {
        boolean bl;
        this.setRadix(10);
        boolean bl2 = bl = this.hasNext(this.floatPattern());
        if (bl) {
            try {
                this.typeCache = Float.valueOf(Float.parseFloat(this.processFloatToken(this.hasNextResult)));
                bl2 = bl;
            }
            catch (NumberFormatException numberFormatException) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public boolean hasNextInt() {
        return this.hasNextInt(this.defaultRadix);
    }

    public boolean hasNextInt(int n) {
        boolean bl;
        this.setRadix(n);
        boolean bl2 = bl = this.hasNext(this.integerPattern());
        if (bl) {
            try {
                String string = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult;
                this.typeCache = Integer.parseInt(string, n);
                bl2 = bl;
            }
            catch (NumberFormatException numberFormatException) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public boolean hasNextLine() {
        this.saveState();
        Object object = Scanner.linePattern();
        boolean bl = false;
        String string = this.findWithinHorizon((Pattern)object, 0);
        object = string;
        if (string != null) {
            object = this.match().group(1);
            if (object != null) {
                object = string.substring(0, string.length() - ((String)object).length());
                this.cacheResult((String)object);
            } else {
                this.cacheResult();
                object = string;
            }
        }
        this.revertState();
        if (object != null) {
            bl = true;
        }
        return bl;
    }

    public boolean hasNextLong() {
        return this.hasNextLong(this.defaultRadix);
    }

    public boolean hasNextLong(int n) {
        boolean bl;
        this.setRadix(n);
        boolean bl2 = bl = this.hasNext(this.integerPattern());
        if (bl) {
            try {
                String string = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult;
                this.typeCache = Long.parseLong(string, n);
                bl2 = bl;
            }
            catch (NumberFormatException numberFormatException) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public boolean hasNextShort() {
        return this.hasNextShort(this.defaultRadix);
    }

    public boolean hasNextShort(int n) {
        boolean bl;
        this.setRadix(n);
        boolean bl2 = bl = this.hasNext(this.integerPattern());
        if (bl) {
            try {
                String string = this.matcher.group(this.SIMPLE_GROUP_INDEX) == null ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult;
                this.typeCache = Short.parseShort(string, n);
                bl2 = bl;
            }
            catch (NumberFormatException numberFormatException) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public IOException ioException() {
        return this.lastException;
    }

    public Locale locale() {
        return this.locale;
    }

    public MatchResult match() {
        if (this.matchValid) {
            return this.matcher.toMatchResult();
        }
        throw new IllegalStateException("No match result available");
    }

    @Override
    public String next() {
        this.ensureOpen();
        this.clearCaches();
        do {
            String string;
            if ((string = this.getCompleteTokenInBuffer(null)) != null) {
                this.matchValid = true;
                this.skipped = false;
                return string;
            }
            if (this.needInput) {
                this.readInput();
                continue;
            }
            this.throwFor();
        } while (true);
    }

    public String next(String string) {
        return this.next(this.patternCache.forName(string));
    }

    public String next(Pattern pattern) {
        this.ensureOpen();
        if (pattern != null) {
            if (this.hasNextPattern == pattern) {
                return this.getCachedResult();
            }
            this.clearCaches();
            do {
                String string;
                if ((string = this.getCompleteTokenInBuffer(pattern)) != null) {
                    this.matchValid = true;
                    this.skipped = false;
                    return string;
                }
                if (this.needInput) {
                    this.readInput();
                    continue;
                }
                this.throwFor();
            } while (true);
        }
        throw new NullPointerException();
    }

    public BigDecimal nextBigDecimal() {
        Object object = this.typeCache;
        if (object != null && object instanceof BigDecimal) {
            object = (BigDecimal)object;
            this.useTypeCache();
            return object;
        }
        this.setRadix(10);
        this.clearCaches();
        try {
            object = new BigDecimal(this.processFloatToken(this.next(this.decimalPattern())));
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            this.position = this.matcher.start();
            throw new InputMismatchException(numberFormatException.getMessage());
        }
    }

    public BigInteger nextBigInteger() {
        return this.nextBigInteger(this.defaultRadix);
    }

    public BigInteger nextBigInteger(int n) {
        String string;
        Object object = this.typeCache;
        if (object != null && object instanceof BigInteger && this.radix == n) {
            object = (BigInteger)object;
            this.useTypeCache();
            return object;
        }
        this.setRadix(n);
        this.clearCaches();
        try {
            string = this.next(this.integerPattern());
            object = string;
        }
        catch (NumberFormatException numberFormatException) {
            this.position = this.matcher.start();
            throw new InputMismatchException(numberFormatException.getMessage());
        }
        if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
            object = this.processIntegerToken(string);
        }
        object = new BigInteger((String)object, n);
        return object;
    }

    public boolean nextBoolean() {
        this.clearCaches();
        return Boolean.parseBoolean(this.next(Scanner.boolPattern()));
    }

    public byte nextByte() {
        return this.nextByte(this.defaultRadix);
    }

    public byte nextByte(int n) {
        String string;
        Object object = this.typeCache;
        if (object != null && object instanceof Byte && this.radix == n) {
            byte by = (Byte)object;
            this.useTypeCache();
            return by;
        }
        this.setRadix(n);
        this.clearCaches();
        try {
            string = this.next(this.integerPattern());
            object = string;
        }
        catch (NumberFormatException numberFormatException) {
            this.position = this.matcher.start();
            throw new InputMismatchException(numberFormatException.getMessage());
        }
        if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
            object = this.processIntegerToken(string);
        }
        byte by = Byte.parseByte((String)object, n);
        return by;
    }

    public double nextDouble() {
        Object object = this.typeCache;
        if (object != null && object instanceof Double) {
            double d = (Double)object;
            this.useTypeCache();
            return d;
        }
        this.setRadix(10);
        this.clearCaches();
        try {
            double d = Double.parseDouble(this.processFloatToken(this.next(this.floatPattern())));
            return d;
        }
        catch (NumberFormatException numberFormatException) {
            this.position = this.matcher.start();
            throw new InputMismatchException(numberFormatException.getMessage());
        }
    }

    public float nextFloat() {
        Object object = this.typeCache;
        if (object != null && object instanceof Float) {
            float f = ((Float)object).floatValue();
            this.useTypeCache();
            return f;
        }
        this.setRadix(10);
        this.clearCaches();
        try {
            float f = Float.parseFloat(this.processFloatToken(this.next(this.floatPattern())));
            return f;
        }
        catch (NumberFormatException numberFormatException) {
            this.position = this.matcher.start();
            throw new InputMismatchException(numberFormatException.getMessage());
        }
    }

    public int nextInt() {
        return this.nextInt(this.defaultRadix);
    }

    public int nextInt(int n) {
        String string;
        Object object = this.typeCache;
        if (object != null && object instanceof Integer && this.radix == n) {
            n = (Integer)object;
            this.useTypeCache();
            return n;
        }
        this.setRadix(n);
        this.clearCaches();
        try {
            string = this.next(this.integerPattern());
            object = string;
        }
        catch (NumberFormatException numberFormatException) {
            this.position = this.matcher.start();
            throw new InputMismatchException(numberFormatException.getMessage());
        }
        if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
            object = this.processIntegerToken(string);
        }
        n = Integer.parseInt((String)object, n);
        return n;
    }

    public String nextLine() {
        if (this.hasNextPattern == Scanner.linePattern()) {
            return this.getCachedResult();
        }
        this.clearCaches();
        String string = this.findWithinHorizon(linePattern, 0);
        if (string != null) {
            String string2 = this.match().group(1);
            String string3 = string;
            if (string2 != null) {
                string3 = string.substring(0, string.length() - string2.length());
            }
            if (string3 != null) {
                return string3;
            }
            throw new NoSuchElementException();
        }
        throw new NoSuchElementException("No line found");
    }

    public long nextLong() {
        return this.nextLong(this.defaultRadix);
    }

    public long nextLong(int n) {
        String string;
        Object object = this.typeCache;
        if (object != null && object instanceof Long && this.radix == n) {
            long l = (Long)object;
            this.useTypeCache();
            return l;
        }
        this.setRadix(n);
        this.clearCaches();
        try {
            string = this.next(this.integerPattern());
            object = string;
        }
        catch (NumberFormatException numberFormatException) {
            this.position = this.matcher.start();
            throw new InputMismatchException(numberFormatException.getMessage());
        }
        if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
            object = this.processIntegerToken(string);
        }
        long l = Long.parseLong((String)object, n);
        return l;
    }

    public short nextShort() {
        return this.nextShort(this.defaultRadix);
    }

    public short nextShort(int n) {
        String string;
        Object object = this.typeCache;
        if (object != null && object instanceof Short && this.radix == n) {
            short s = (Short)object;
            this.useTypeCache();
            return s;
        }
        this.setRadix(n);
        this.clearCaches();
        try {
            string = this.next(this.integerPattern());
            object = string;
        }
        catch (NumberFormatException numberFormatException) {
            this.position = this.matcher.start();
            throw new InputMismatchException(numberFormatException.getMessage());
        }
        if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
            object = this.processIntegerToken(string);
        }
        short s = Short.parseShort((String)object, n);
        return s;
    }

    public int radix() {
        return this.defaultRadix;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public Scanner reset() {
        this.delimPattern = WHITESPACE_PATTERN;
        this.useLocale(Locale.getDefault(Locale.Category.FORMAT));
        this.useRadix(10);
        this.clearCaches();
        return this;
    }

    public Scanner skip(String string) {
        return this.skip(this.patternCache.forName(string));
    }

    public Scanner skip(Pattern pattern) {
        this.ensureOpen();
        if (pattern != null) {
            this.clearCaches();
            do {
                if (this.matchPatternInBuffer(pattern) != null) {
                    this.matchValid = true;
                    this.position = this.matcher.end();
                    return this;
                }
                if (!this.needInput) break;
                this.readInput();
            } while (true);
            throw new NoSuchElementException();
        }
        throw new NullPointerException();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("java.util.Scanner");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[delimiters=");
        stringBuilder2.append(this.delimPattern);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[position=");
        stringBuilder2.append(this.position);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[match valid=");
        stringBuilder2.append(this.matchValid);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[need input=");
        stringBuilder2.append(this.needInput);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[source closed=");
        stringBuilder2.append(this.sourceClosed);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[skipped=");
        stringBuilder2.append(this.skipped);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[group separator=");
        stringBuilder2.append(this.groupSeparator);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[decimal separator=");
        stringBuilder2.append(this.decimalSeparator);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[positive prefix=");
        stringBuilder2.append(this.positivePrefix);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[negative prefix=");
        stringBuilder2.append(this.negativePrefix);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[positive suffix=");
        stringBuilder2.append(this.positiveSuffix);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[negative suffix=");
        stringBuilder2.append(this.negativeSuffix);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[NaN string=");
        stringBuilder2.append(this.nanString);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[infinity string=");
        stringBuilder2.append(this.infinityString);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }

    public Scanner useDelimiter(String string) {
        this.delimPattern = this.patternCache.forName(string);
        return this;
    }

    public Scanner useDelimiter(Pattern pattern) {
        this.delimPattern = pattern;
        return this;
    }

    public Scanner useLocale(Locale serializable) {
        if (((Locale)serializable).equals(this.locale)) {
            return this;
        }
        this.locale = serializable;
        DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance((Locale)serializable);
        serializable = DecimalFormatSymbols.getInstance((Locale)serializable);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\");
        stringBuilder.append(((DecimalFormatSymbols)serializable).getGroupingSeparator());
        this.groupSeparator = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("\\");
        stringBuilder.append(((DecimalFormatSymbols)serializable).getDecimalSeparator());
        this.decimalSeparator = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("\\Q");
        stringBuilder.append(((DecimalFormatSymbols)serializable).getNaN());
        stringBuilder.append("\\E");
        this.nanString = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("\\Q");
        stringBuilder.append(((DecimalFormatSymbols)serializable).getInfinity());
        stringBuilder.append("\\E");
        this.infinityString = stringBuilder.toString();
        this.positivePrefix = decimalFormat.getPositivePrefix();
        if (this.positivePrefix.length() > 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("\\Q");
            ((StringBuilder)serializable).append(this.positivePrefix);
            ((StringBuilder)serializable).append("\\E");
            this.positivePrefix = ((StringBuilder)serializable).toString();
        }
        this.negativePrefix = decimalFormat.getNegativePrefix();
        if (this.negativePrefix.length() > 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("\\Q");
            ((StringBuilder)serializable).append(this.negativePrefix);
            ((StringBuilder)serializable).append("\\E");
            this.negativePrefix = ((StringBuilder)serializable).toString();
        }
        this.positiveSuffix = decimalFormat.getPositiveSuffix();
        if (this.positiveSuffix.length() > 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("\\Q");
            ((StringBuilder)serializable).append(this.positiveSuffix);
            ((StringBuilder)serializable).append("\\E");
            this.positiveSuffix = ((StringBuilder)serializable).toString();
        }
        this.negativeSuffix = decimalFormat.getNegativeSuffix();
        if (this.negativeSuffix.length() > 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("\\Q");
            ((StringBuilder)serializable).append(this.negativeSuffix);
            ((StringBuilder)serializable).append("\\E");
            this.negativeSuffix = ((StringBuilder)serializable).toString();
        }
        this.integerPattern = null;
        this.floatPattern = null;
        return this;
    }

    public Scanner useRadix(int n) {
        if (n >= 2 && n <= 36) {
            if (this.defaultRadix == n) {
                return this;
            }
            this.defaultRadix = n;
            this.integerPattern = null;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("radix:");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

}

