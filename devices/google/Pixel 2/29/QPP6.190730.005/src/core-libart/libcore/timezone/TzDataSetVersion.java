/*
 * Decompiled with CFR 0.145.
 */
package libcore.timezone;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TzDataSetVersion {
    public static final int CURRENT_FORMAT_MAJOR_VERSION = 3;
    public static final int CURRENT_FORMAT_MINOR_VERSION = 1;
    public static final String DEFAULT_FILE_NAME = "tz_version";
    private static final Pattern FORMAT_VERSION_PATTERN;
    private static final int FORMAT_VERSION_STRING_LENGTH;
    private static final String FULL_CURRENT_FORMAT_VERSION_STRING;
    private static final int REVISION_LENGTH = 3;
    private static final Pattern REVISION_PATTERN;
    private static final int RULES_VERSION_LENGTH = 5;
    private static final Pattern RULES_VERSION_PATTERN;
    private static final int TZ_DATA_VERSION_FILE_LENGTH;
    private static final Pattern TZ_DATA_VERSION_FILE_PATTERN;
    public final int formatMajorVersion;
    public final int formatMinorVersion;
    public final int revision;
    public final String rulesVersion;

    static {
        FULL_CURRENT_FORMAT_VERSION_STRING = TzDataSetVersion.toFormatVersionString(3, 1);
        FORMAT_VERSION_STRING_LENGTH = FULL_CURRENT_FORMAT_VERSION_STRING.length();
        FORMAT_VERSION_PATTERN = Pattern.compile("(\\d{3})\\.(\\d{3})");
        RULES_VERSION_PATTERN = Pattern.compile("(\\d{4}\\w)");
        REVISION_PATTERN = Pattern.compile("(\\d{3})");
        TZ_DATA_VERSION_FILE_LENGTH = FORMAT_VERSION_STRING_LENGTH + 1 + 5 + 1 + 3;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FORMAT_VERSION_PATTERN.pattern());
        stringBuilder.append("\\|");
        stringBuilder.append(RULES_VERSION_PATTERN.pattern());
        stringBuilder.append("\\|");
        stringBuilder.append(REVISION_PATTERN.pattern());
        stringBuilder.append(".*");
        TZ_DATA_VERSION_FILE_PATTERN = Pattern.compile(stringBuilder.toString());
    }

    public TzDataSetVersion(int n, int n2, String string, int n3) throws TzDataSetException {
        this.formatMajorVersion = TzDataSetVersion.validate3DigitVersion(n);
        this.formatMinorVersion = TzDataSetVersion.validate3DigitVersion(n2);
        if (RULES_VERSION_PATTERN.matcher(string).matches()) {
            this.rulesVersion = string;
            this.revision = TzDataSetVersion.validate3DigitVersion(n3);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid rulesVersion: ");
        stringBuilder.append(string);
        throw new TzDataSetException(stringBuilder.toString());
    }

    public static int currentFormatMajorVersion() {
        return 3;
    }

    public static int currentFormatMinorVersion() {
        return 1;
    }

    private static int from3DigitVersionString(String string) throws TzDataSetException {
        if (string.length() == 3) {
            try {
                int n = TzDataSetVersion.validate3DigitVersion(Integer.parseInt(string));
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                throw new TzDataSetException("versionString must be a zero padded, 3 digit, positive decimal integer", numberFormatException);
            }
        }
        throw new TzDataSetException("versionString must be a zero padded, 3 digit, positive decimal integer");
    }

    public static TzDataSetVersion fromBytes(byte[] object) throws TzDataSetException {
        object = new String((byte[])object, StandardCharsets.US_ASCII);
        try {
            Object object2 = TZ_DATA_VERSION_FILE_PATTERN.matcher((CharSequence)object);
            if (((Matcher)object2).matches()) {
                String string = ((Matcher)object2).group(1);
                String string2 = ((Matcher)object2).group(2);
                String string3 = ((Matcher)object2).group(3);
                object2 = ((Matcher)object2).group(4);
                return new TzDataSetVersion(TzDataSetVersion.from3DigitVersionString(string), TzDataSetVersion.from3DigitVersionString(string2), string3, TzDataSetVersion.from3DigitVersionString((String)object2));
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid tz data version string: \"");
            stringBuilder.append((String)object);
            stringBuilder.append("\"");
            TzDataSetException tzDataSetException = new TzDataSetException(stringBuilder.toString());
            throw tzDataSetException;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("tz data version string too short: \"");
            stringBuilder.append((String)object);
            stringBuilder.append("\"");
            throw new TzDataSetException(stringBuilder.toString());
        }
    }

    public static boolean isCompatibleWithThisDevice(TzDataSetVersion tzDataSetVersion) {
        int n = tzDataSetVersion.formatMajorVersion;
        boolean bl = true;
        if (3 != n || 1 > tzDataSetVersion.formatMinorVersion) {
            bl = false;
        }
        return bl;
    }

    private static byte[] readBytes(File object, int n) throws IOException {
        if (n > 0) {
            byte[] arrby;
            object = new FileInputStream((File)object);
            try {
                byte[] arrby2 = new byte[n];
                n = ((FileInputStream)object).read(arrby2, 0, n);
                arrby = new byte[n];
                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)n);
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        ((FileInputStream)object).close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    throw throwable2;
                }
            }
            ((FileInputStream)object).close();
            return arrby;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("maxBytes ==");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static TzDataSetVersion readFromFile(File file) throws IOException, TzDataSetException {
        return TzDataSetVersion.fromBytes(TzDataSetVersion.readBytes(file, TZ_DATA_VERSION_FILE_LENGTH));
    }

    private static String to3DigitVersionString(int n) {
        try {
            String string = String.format(Locale.ROOT, "%03d", TzDataSetVersion.validate3DigitVersion(n));
            return string;
        }
        catch (TzDataSetException tzDataSetException) {
            throw new IllegalArgumentException(tzDataSetException);
        }
    }

    private static byte[] toBytes(int n, int n2, String string, int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TzDataSetVersion.toFormatVersionString(n, n2));
        stringBuilder.append("|");
        stringBuilder.append(string);
        stringBuilder.append("|");
        stringBuilder.append(TzDataSetVersion.to3DigitVersionString(n3));
        return stringBuilder.toString().getBytes(StandardCharsets.US_ASCII);
    }

    private static String toFormatVersionString(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TzDataSetVersion.to3DigitVersionString(n));
        stringBuilder.append(".");
        stringBuilder.append(TzDataSetVersion.to3DigitVersionString(n2));
        return stringBuilder.toString();
    }

    private static int validate3DigitVersion(int n) throws TzDataSetException {
        if (n >= 0 && n <= 999) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected 0 <= value <= 999, was ");
        stringBuilder.append(n);
        throw new TzDataSetException(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (TzDataSetVersion)object;
            if (this.formatMajorVersion != ((TzDataSetVersion)object).formatMajorVersion) {
                return false;
            }
            if (this.formatMinorVersion != ((TzDataSetVersion)object).formatMinorVersion) {
                return false;
            }
            if (this.revision != ((TzDataSetVersion)object).revision) {
                return false;
            }
            return this.rulesVersion.equals(((TzDataSetVersion)object).rulesVersion);
        }
        return false;
    }

    public int hashCode() {
        return ((this.formatMajorVersion * 31 + this.formatMinorVersion) * 31 + this.rulesVersion.hashCode()) * 31 + this.revision;
    }

    public byte[] toBytes() {
        return TzDataSetVersion.toBytes(this.formatMajorVersion, this.formatMinorVersion, this.rulesVersion, this.revision);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TzDataSetVersion{formatMajorVersion=");
        stringBuilder.append(this.formatMajorVersion);
        stringBuilder.append(", formatMinorVersion=");
        stringBuilder.append(this.formatMinorVersion);
        stringBuilder.append(", rulesVersion='");
        stringBuilder.append(this.rulesVersion);
        stringBuilder.append('\'');
        stringBuilder.append(", revision=");
        stringBuilder.append(this.revision);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static class TzDataSetException
    extends Exception {
        public TzDataSetException(String string) {
            super(string);
        }

        public TzDataSetException(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

}

