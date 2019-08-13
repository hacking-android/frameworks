/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.net.IDN;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;

public final class SNIHostName
extends SNIServerName {
    private final String hostname;

    public SNIHostName(String string) {
        string = IDN.toASCII(Objects.requireNonNull(string, "Server name value of host_name cannot be null"), 2);
        super(0, string.getBytes(StandardCharsets.US_ASCII));
        this.hostname = string;
        this.checkHostName();
    }

    public SNIHostName(byte[] arrby) {
        super(0, arrby);
        try {
            CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
            this.hostname = IDN.toASCII(charsetDecoder.decode(ByteBuffer.wrap(arrby)).toString());
        }
        catch (RuntimeException | CharacterCodingException exception) {
            throw new IllegalArgumentException("The encoded server name value is invalid", exception);
        }
        this.checkHostName();
        return;
    }

    private void checkHostName() {
        if (!this.hostname.isEmpty()) {
            if (!this.hostname.endsWith(".")) {
                return;
            }
            throw new IllegalArgumentException("Server name value of host_name cannot have the trailing dot");
        }
        throw new IllegalArgumentException("Server name value of host_name cannot be empty");
    }

    public static SNIMatcher createSNIMatcher(String string) {
        if (string != null) {
            return new SNIHostNameMatcher(string);
        }
        throw new NullPointerException("The regular expression cannot be null");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof SNIHostName) {
            return this.hostname.equalsIgnoreCase(((SNIHostName)object).hostname);
        }
        return false;
    }

    public String getAsciiName() {
        return this.hostname;
    }

    @Override
    public int hashCode() {
        return 17 * 31 + this.hostname.toUpperCase(Locale.ENGLISH).hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type=host_name (0), value=");
        stringBuilder.append(this.hostname);
        return stringBuilder.toString();
    }

    private static final class SNIHostNameMatcher
    extends SNIMatcher {
        private final Pattern pattern;

        SNIHostNameMatcher(String string) {
            super(0);
            this.pattern = Pattern.compile(string, 2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean matches(SNIServerName object) {
            if (object == null) throw new NullPointerException("The SNIServerName argument cannot be null");
            if (!(object instanceof SNIHostName)) {
                if (((SNIServerName)object).getType() != 0) throw new IllegalArgumentException("The server name type is not host_name");
                try {
                    object = new SNIHostName(((SNIServerName)object).getEncoded());
                }
                catch (IllegalArgumentException | NullPointerException runtimeException) {
                    return false;
                }
            } else {
                object = (SNIHostName)object;
            }
            object = ((SNIHostName)object).getAsciiName();
            if (!this.pattern.matcher((CharSequence)object).matches()) return this.pattern.matcher(IDN.toUnicode((String)object)).matches();
            return true;
        }
    }

}

