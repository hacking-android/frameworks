/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import java.text.ParseException;

public class StringPrepParseException
extends ParseException {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ACE_PREFIX_ERROR = 6;
    public static final int BUFFER_OVERFLOW_ERROR = 9;
    public static final int CHECK_BIDI_ERROR = 4;
    public static final int DOMAIN_NAME_TOO_LONG_ERROR = 11;
    public static final int ILLEGAL_CHAR_FOUND = 1;
    public static final int INVALID_CHAR_FOUND = 0;
    public static final int LABEL_TOO_LONG_ERROR = 8;
    private static final int PARSE_CONTEXT_LEN = 16;
    public static final int PROHIBITED_ERROR = 2;
    public static final int STD3_ASCII_RULES_ERROR = 5;
    public static final int UNASSIGNED_ERROR = 3;
    public static final int VERIFICATION_ERROR = 7;
    public static final int ZERO_LENGTH_LABEL = 10;
    static final long serialVersionUID = 7160264827701651255L;
    private int error;
    private int line;
    private StringBuffer postContext = new StringBuffer();
    private StringBuffer preContext = new StringBuffer();

    public StringPrepParseException(String string, int n) {
        super(string, -1);
        this.error = n;
        this.line = 0;
    }

    public StringPrepParseException(String string, int n, String string2, int n2) {
        super(string, -1);
        this.error = n;
        this.setContext(string2, n2);
        this.line = 0;
    }

    public StringPrepParseException(String string, int n, String string2, int n2, int n3) {
        super(string, -1);
        this.error = n;
        this.setContext(string2, n2);
        this.line = n3;
    }

    private void setContext(String string, int n) {
        this.setPreContext(string, n);
        this.setPostContext(string, n);
    }

    private void setPostContext(String string, int n) {
        this.setPostContext(string.toCharArray(), n);
    }

    private void setPostContext(char[] arrc, int n) {
        int n2 = arrc.length;
        this.postContext.append(arrc, n, n2 - n);
    }

    private void setPreContext(String string, int n) {
        this.setPreContext(string.toCharArray(), n);
    }

    private void setPreContext(char[] arrc, int n) {
        int n2 = 16;
        n = n <= 16 ? 0 : (n -= 15);
        if (n <= 16) {
            n2 = n;
        }
        this.preContext.append(arrc, n, n2);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof StringPrepParseException;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (((StringPrepParseException)object).error == this.error) {
            bl2 = true;
        }
        return bl2;
    }

    public int getError() {
        return this.error;
    }

    public int hashCode() {
        return 42;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.getMessage());
        stringBuilder.append(". line:  ");
        stringBuilder.append(this.line);
        stringBuilder.append(". preContext:  ");
        stringBuilder.append(this.preContext);
        stringBuilder.append(". postContext: ");
        stringBuilder.append(this.postContext);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

