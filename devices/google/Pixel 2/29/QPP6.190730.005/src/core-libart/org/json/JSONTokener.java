/*
 * Decompiled with CFR 0.145.
 */
package org.json;

import dalvik.annotation.compat.UnsupportedAppUsage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTokener {
    @UnsupportedAppUsage
    private final String in;
    @UnsupportedAppUsage
    private int pos;

    public JSONTokener(String string) {
        String string2 = string;
        if (string != null) {
            string2 = string;
            if (string.startsWith("\ufeff")) {
                string2 = string.substring(1);
            }
        }
        this.in = string2;
    }

    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        return -1;
    }

    @UnsupportedAppUsage
    private int nextCleanInternal() throws JSONException {
        while (this.pos < this.in.length()) {
            String string = this.in;
            int n = this.pos;
            this.pos = n + 1;
            char c = string.charAt(n);
            if (c == '\t' || c == '\n' || c == '\r' || c == ' ') continue;
            if (c != '#') {
                if (c != '/') {
                    return c;
                }
                if (this.pos == this.in.length()) {
                    return c;
                }
                n = this.in.charAt(this.pos);
                if (n != 42) {
                    if (n != 47) {
                        return c;
                    }
                    ++this.pos;
                    this.skipToEndOfLine();
                    continue;
                }
                ++this.pos;
                n = this.in.indexOf("*/", this.pos);
                if (n != -1) {
                    this.pos = n + 2;
                    continue;
                }
                throw this.syntaxError("Unterminated comment");
            }
            this.skipToEndOfLine();
        }
        return -1;
    }

    @UnsupportedAppUsage
    private String nextToInternal(String string) {
        int n = this.pos;
        while (this.pos < this.in.length()) {
            char c = this.in.charAt(this.pos);
            if (c != '\r' && c != '\n' && string.indexOf(c) == -1) {
                ++this.pos;
                continue;
            }
            return this.in.substring(n, this.pos);
        }
        return this.in.substring(n);
    }

    @UnsupportedAppUsage
    private JSONArray readArray() throws JSONException {
        int n;
        JSONArray jSONArray = new JSONArray();
        int n2 = 0;
        while ((n = this.nextCleanInternal()) != -1) {
            if (n != 44 && n != 59) {
                if (n != 93) {
                    --this.pos;
                    jSONArray.put(this.nextValue());
                    n2 = this.nextCleanInternal();
                    if (n2 != 44 && n2 != 59) {
                        if (n2 == 93) {
                            return jSONArray;
                        }
                        throw this.syntaxError("Unterminated array");
                    }
                    n2 = 1;
                    continue;
                }
                if (n2 != 0) {
                    jSONArray.put(null);
                }
                return jSONArray;
            }
            jSONArray.put(null);
            n2 = 1;
        }
        throw this.syntaxError("Unterminated array");
    }

    @UnsupportedAppUsage
    private char readEscapeCharacter() throws JSONException {
        String string = this.in;
        int n = this.pos;
        this.pos = n + 1;
        char c = string.charAt(n);
        if (c != 'b') {
            if (c != 'f') {
                if (c != 'n') {
                    if (c != 'r') {
                        if (c != 't') {
                            if (c != 'u') {
                                return c;
                            }
                            if (this.pos + 4 <= this.in.length()) {
                                string = this.in;
                                n = this.pos;
                                string = string.substring(n, n + 4);
                                this.pos += 4;
                                try {
                                    n = Integer.parseInt(string, 16);
                                }
                                catch (NumberFormatException numberFormatException) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Invalid escape sequence: ");
                                    stringBuilder.append(string);
                                    throw this.syntaxError(stringBuilder.toString());
                                }
                                return (char)n;
                            }
                            throw this.syntaxError("Unterminated escape sequence");
                        }
                        return '\t';
                    }
                    return '\r';
                }
                return '\n';
            }
            return '\f';
        }
        return '\b';
    }

    @UnsupportedAppUsage
    private Object readLiteral() throws JSONException {
        String string = this.nextToInternal("{}[]/\\:,=;# \t\f");
        if (string.length() != 0) {
            if ("null".equalsIgnoreCase(string)) {
                return JSONObject.NULL;
            }
            if ("true".equalsIgnoreCase(string)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(string)) {
                return Boolean.FALSE;
            }
            if (string.indexOf(46) == -1) {
                long l;
                block14 : {
                    int n;
                    String string2;
                    int n2 = 10;
                    String string3 = string;
                    if (!string3.startsWith("0x") && !string3.startsWith("0X")) {
                        n = n2;
                        string2 = string3;
                        if (string3.startsWith("0")) {
                            n = n2;
                            string2 = string3;
                            if (string3.length() > 1) {
                                string2 = string3.substring(1);
                                n = 8;
                            }
                        }
                    } else {
                        string2 = string3.substring(2);
                        n = 16;
                    }
                    l = Long.parseLong(string2, n);
                    if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) break block14;
                    try {
                        return (int)l;
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
                return l;
            }
            try {
                Double d = Double.valueOf(string);
                return d;
            }
            catch (NumberFormatException numberFormatException) {
                return new String(string);
            }
        }
        throw this.syntaxError("Expected literal value");
    }

    @UnsupportedAppUsage
    private JSONObject readObject() throws JSONException {
        Object object = new JSONObject();
        int n = this.nextCleanInternal();
        if (n == 125) {
            return object;
        }
        if (n != -1) {
            --this.pos;
        }
        do {
            Object object2;
            if (!((object2 = this.nextValue()) instanceof String)) {
                if (object2 == null) {
                    throw this.syntaxError("Names cannot be null");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Names must be strings, but ");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append(" is of type ");
                ((StringBuilder)object).append(object2.getClass().getName());
                throw this.syntaxError(((StringBuilder)object).toString());
            }
            n = this.nextCleanInternal();
            if (n != 58 && n != 61) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Expected ':' after ");
                ((StringBuilder)object).append(object2);
                throw this.syntaxError(((StringBuilder)object).toString());
            }
            if (this.pos < this.in.length() && this.in.charAt(this.pos) == '>') {
                ++this.pos;
            }
            ((JSONObject)object).put((String)object2, this.nextValue());
        } while ((n = this.nextCleanInternal()) == 44 || n == 59);
        if (n == 125) {
            return object;
        }
        throw this.syntaxError("Unterminated object");
    }

    @UnsupportedAppUsage
    private void skipToEndOfLine() {
        while (this.pos < this.in.length()) {
            char c = this.in.charAt(this.pos);
            if (c != '\r' && c != '\n') {
                ++this.pos;
                continue;
            }
            ++this.pos;
            break;
        }
    }

    public void back() {
        int n;
        this.pos = n = this.pos - 1;
        if (n == -1) {
            this.pos = 0;
        }
    }

    public boolean more() {
        boolean bl = this.pos < this.in.length();
        return bl;
    }

    public char next() {
        int n;
        if (this.pos < this.in.length()) {
            String string = this.in;
            int n2 = this.pos;
            this.pos = n2 + 1;
            n = n2 = (int)string.charAt(n2);
        } else {
            int n3;
            n = n3 = 0;
        }
        return (char)n;
    }

    public char next(char c) throws JSONException {
        char c2 = this.next();
        if (c2 == c) {
            return c2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected ");
        stringBuilder.append(c);
        stringBuilder.append(" but was ");
        stringBuilder.append(c2);
        throw this.syntaxError(stringBuilder.toString());
    }

    public String next(int n) throws JSONException {
        if (this.pos + n <= this.in.length()) {
            String string = this.in;
            int n2 = this.pos;
            string = string.substring(n2, n2 + n);
            this.pos += n;
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is out of bounds");
        throw this.syntaxError(stringBuilder.toString());
    }

    public char nextClean() throws JSONException {
        int n = this.nextCleanInternal();
        int n2 = n == -1 ? (n = 0) : (n = (int)((char)n));
        return (char)n2;
    }

    public String nextString(char c) throws JSONException {
        String string = null;
        int n = this.pos;
        while (this.pos < this.in.length()) {
            CharSequence charSequence = this.in;
            int n2 = this.pos;
            this.pos = n2 + 1;
            char c2 = ((String)charSequence).charAt(n2);
            if (c2 == c) {
                if (string == null) {
                    return new String(this.in.substring(n, this.pos - 1));
                }
                ((StringBuilder)((Object)string)).append(this.in, n, this.pos - 1);
                return ((StringBuilder)((Object)string)).toString();
            }
            charSequence = string;
            n2 = n;
            if (c2 == '\\') {
                if (this.pos != this.in.length()) {
                    charSequence = string;
                    if (string == null) {
                        charSequence = new StringBuilder();
                    }
                    ((StringBuilder)charSequence).append(this.in, n, this.pos - 1);
                    ((StringBuilder)charSequence).append(this.readEscapeCharacter());
                    n2 = this.pos;
                } else {
                    throw this.syntaxError("Unterminated escape sequence");
                }
            }
            string = charSequence;
            n = n2;
        }
        throw this.syntaxError("Unterminated string");
    }

    public String nextTo(char c) {
        return this.nextToInternal(String.valueOf(c)).trim();
    }

    public String nextTo(String string) {
        if (string != null) {
            return this.nextToInternal(string).trim();
        }
        throw new NullPointerException("excluded == null");
    }

    public Object nextValue() throws JSONException {
        int n = this.nextCleanInternal();
        if (n != -1) {
            if (n != 34 && n != 39) {
                if (n != 91) {
                    if (n != 123) {
                        --this.pos;
                        return this.readLiteral();
                    }
                    return this.readObject();
                }
                return this.readArray();
            }
            return this.nextString((char)n);
        }
        throw this.syntaxError("End of input");
    }

    public void skipPast(String string) {
        int n = this.in.indexOf(string, this.pos);
        n = n == -1 ? this.in.length() : string.length() + n;
        this.pos = n;
    }

    public char skipTo(char c) {
        int n = this.in.indexOf(c, this.pos);
        if (n != -1) {
            this.pos = n;
            return c;
        }
        return '\u0000';
    }

    public JSONException syntaxError(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(this);
        return new JSONException(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" at character ");
        stringBuilder.append(this.pos);
        stringBuilder.append(" of ");
        stringBuilder.append(this.in);
        return stringBuilder.toString();
    }
}

