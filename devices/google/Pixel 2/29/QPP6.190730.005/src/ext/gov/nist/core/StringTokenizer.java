/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import java.text.ParseException;
import java.util.Vector;

public class StringTokenizer {
    protected String buffer;
    protected int bufferLen;
    protected int ptr;
    protected int savedPtr;

    protected StringTokenizer() {
    }

    public StringTokenizer(String string) {
        this.buffer = string;
        this.bufferLen = string.length();
        this.ptr = 0;
    }

    public static String getSDPFieldName(String string) {
        if (string == null) {
            return null;
        }
        try {
            string = string.substring(0, string.indexOf("="));
            return string;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        }
    }

    public static boolean isAlpha(char c) {
        boolean bl;
        block4 : {
            block6 : {
                block5 : {
                    bl = false;
                    boolean bl2 = false;
                    if (c > '') break block4;
                    if (c >= 'a' && c <= 'z') break block5;
                    bl = bl2;
                    if (c < 'A') break block6;
                    bl = bl2;
                    if (c > 'Z') break block6;
                }
                bl = true;
            }
            return bl;
        }
        if (Character.isLowerCase(c) || Character.isUpperCase(c)) {
            bl = true;
        }
        return bl;
    }

    public static boolean isAlphaDigit(char c) {
        boolean bl;
        block4 : {
            block6 : {
                block5 : {
                    bl = false;
                    boolean bl2 = false;
                    if (c > '') break block4;
                    if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') break block5;
                    bl = bl2;
                    if (c > '9') break block6;
                    bl = bl2;
                    if (c < '0') break block6;
                }
                bl = true;
            }
            return bl;
        }
        if (Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c)) {
            bl = true;
        }
        return bl;
    }

    public static boolean isDigit(char c) {
        if (c <= '') {
            boolean bl = c <= '9' && c >= '0';
            return bl;
        }
        return Character.isDigit(c);
    }

    public static boolean isHexDigit(char c) {
        boolean bl = c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f' || StringTokenizer.isDigit(c);
        return bl;
    }

    public void consume() {
        this.ptr = this.savedPtr;
    }

    public void consume(int n) {
        this.ptr += n;
    }

    public String getLine() {
        int n;
        int n2 = this.ptr;
        while ((n = this.ptr++) < this.bufferLen && this.buffer.charAt(n) != '\n') {
        }
        if ((n = this.ptr++) >= this.bufferLen || this.buffer.charAt(n) == '\n') {
            // empty if block
        }
        return this.buffer.substring(n2, this.ptr);
    }

    public Vector<String> getLines() {
        Vector<String> vector = new Vector<String>();
        while (this.hasMoreChars()) {
            vector.addElement(this.getLine());
        }
        return vector;
    }

    public char getNextChar() throws ParseException {
        int n = this.ptr;
        if (n < this.bufferLen) {
            String string = this.buffer;
            this.ptr = n + 1;
            return string.charAt(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.buffer);
        stringBuilder.append(" getNextChar: End of buffer");
        throw new ParseException(stringBuilder.toString(), this.ptr);
    }

    public String getNextToken(char c) throws ParseException {
        int n = this.ptr;
        do {
            char c2;
            if ((c2 = this.lookAhead(0)) == c) {
                return this.buffer.substring(n, this.ptr);
            }
            if (c2 == '\u0000') break;
            this.consume(1);
        } while (true);
        throw new ParseException("EOL reached", 0);
    }

    public boolean hasMoreChars() {
        boolean bl = this.ptr < this.bufferLen;
        return bl;
    }

    public char lookAhead() throws ParseException {
        return this.lookAhead(0);
    }

    public char lookAhead(int n) throws ParseException {
        try {
            char c = this.buffer.charAt(this.ptr + n);
            return c;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return '\u0000';
        }
    }

    public String nextToken() {
        int n;
        int n2 = this.ptr;
        while ((n = this.ptr) < this.bufferLen) {
            n = this.buffer.charAt(n);
            ++this.ptr;
            if (n != 10) continue;
            break;
        }
        return this.buffer.substring(n2, this.ptr);
    }

    public String peekLine() {
        int n = this.ptr;
        String string = this.getLine();
        this.ptr = n;
        return string;
    }
}

