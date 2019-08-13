/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.internal.StringPool
 */
package android.util;

import android.util.JsonScope;
import android.util.JsonToken;
import android.util.MalformedJsonException;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import libcore.internal.StringPool;

public final class JsonReader
implements Closeable {
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private final char[] buffer = new char[1024];
    private int bufferStartColumn = 1;
    private int bufferStartLine = 1;
    private final Reader in;
    private boolean lenient = false;
    private int limit = 0;
    private String name;
    private int pos = 0;
    private boolean skipping;
    private final List<JsonScope> stack = new ArrayList<JsonScope>();
    private final StringPool stringPool = new StringPool();
    private JsonToken token;
    private String value;
    private int valueLength;
    private int valuePos;

    public JsonReader(Reader reader) {
        this.push(JsonScope.EMPTY_DOCUMENT);
        this.skipping = false;
        if (reader != null) {
            this.in = reader;
            return;
        }
        throw new NullPointerException("in == null");
    }

    private JsonToken advance() throws IOException {
        this.peek();
        JsonToken jsonToken = this.token;
        this.token = null;
        this.value = null;
        this.name = null;
        return jsonToken;
    }

    private void checkLenient() throws IOException {
        if (this.lenient) {
            return;
        }
        throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
    }

    private JsonToken decodeLiteral() throws IOException {
        char[] arrc;
        int n = this.valuePos;
        if (n == -1) {
            return JsonToken.STRING;
        }
        if (!(this.valueLength != 4 || 'n' != (arrc = this.buffer)[n] && 'N' != arrc[n] || 'u' != (arrc = this.buffer)[(n = this.valuePos) + 1] && 'U' != arrc[n + 1] || 'l' != (arrc = this.buffer)[(n = this.valuePos) + 2] && 'L' != arrc[n + 2] || 'l' != (arrc = this.buffer)[(n = this.valuePos) + 3] && 'L' != arrc[n + 3])) {
            this.value = "null";
            return JsonToken.NULL;
        }
        if (!(this.valueLength != 4 || 't' != (arrc = this.buffer)[n = this.valuePos] && 'T' != arrc[n] || 'r' != (arrc = this.buffer)[(n = this.valuePos) + 1] && 'R' != arrc[n + 1] || 'u' != (arrc = this.buffer)[(n = this.valuePos) + 2] && 'U' != arrc[n + 2] || 'e' != (arrc = this.buffer)[(n = this.valuePos) + 3] && 'E' != arrc[n + 3])) {
            this.value = TRUE;
            return JsonToken.BOOLEAN;
        }
        if (!(this.valueLength != 5 || 'f' != (arrc = this.buffer)[n = this.valuePos] && 'F' != arrc[n] || 'a' != (arrc = this.buffer)[(n = this.valuePos) + 1] && 'A' != arrc[n + 1] || 'l' != (arrc = this.buffer)[(n = this.valuePos) + 2] && 'L' != arrc[n + 2] || 's' != (arrc = this.buffer)[(n = this.valuePos) + 3] && 'S' != arrc[n + 3] || 'e' != (arrc = this.buffer)[(n = this.valuePos) + 4] && 'E' != arrc[n + 4])) {
            this.value = FALSE;
            return JsonToken.BOOLEAN;
        }
        this.value = this.stringPool.get(this.buffer, this.valuePos, this.valueLength);
        return this.decodeNumber(this.buffer, this.valuePos, this.valueLength);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private JsonToken decodeNumber(char[] arrc, int n, int n2) {
        int n3;
        block11 : {
            int n4;
            int n5;
            block13 : {
                int n6;
                block12 : {
                    block10 : {
                        n5 = n;
                        n6 = arrc[n5];
                        n3 = n5;
                        n4 = n6;
                        if (n6 == 45) {
                            n3 = n5 + 1;
                            n4 = arrc[n3];
                        }
                        if (n4 == 48) {
                            n4 = n3 + 1;
                            n3 = arrc[n4];
                        } else {
                            if (n4 < 49 || n4 > 57) return JsonToken.STRING;
                            n6 = n3 + 1;
                            n5 = arrc[n6];
                            do {
                                n4 = n6;
                                n3 = n5;
                                if (n5 < 48) break;
                                n4 = n6++;
                                n3 = n5;
                                if (n5 > 57) break;
                                n5 = arrc[n6];
                            } while (true);
                        }
                        n5 = n4;
                        n6 = n3;
                        if (n3 == 46) {
                            n3 = n4 + 1;
                            n4 = arrc[n3];
                            do {
                                n5 = n3;
                                n6 = n4;
                                if (n4 < 48) break;
                                n5 = n3++;
                                n6 = n4;
                                if (n4 > 57) break;
                                n4 = arrc[n3];
                            } while (true);
                        }
                        if (n6 == 101) break block10;
                        n3 = n5;
                        if (n6 != 69) break block11;
                    }
                    if ((n3 = arrc[n6 = n5 + 1]) == 43) break block12;
                    n5 = n6;
                    n4 = n3;
                    if (n3 != 45) break block13;
                }
                n5 = n6 + 1;
                n4 = arrc[n5];
            }
            if (n4 < 48 || n4 > 57) return JsonToken.STRING;
            n4 = n5 + 1;
            n5 = arrc[n4];
            do {
                n3 = n4;
                if (n5 < 48) break;
                n3 = n4++;
                if (n5 > 57) break;
                n5 = arrc[n4];
            } while (true);
        }
        if (n3 != n + n2) return JsonToken.STRING;
        return JsonToken.NUMBER;
    }

    private void expect(JsonToken jsonToken) throws IOException {
        this.peek();
        if (this.token == jsonToken) {
            this.advance();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected ");
        stringBuilder.append((Object)jsonToken);
        stringBuilder.append(" but was ");
        stringBuilder.append((Object)this.peek());
        throw new IllegalStateException(stringBuilder.toString());
    }

    private boolean fillBuffer(int n) throws IOException {
        block5 : {
            Object object;
            int n2;
            int n3;
            for (n2 = 0; n2 < (n3 = this.pos); ++n2) {
                if (this.buffer[n2] == '\n') {
                    ++this.bufferStartLine;
                    this.bufferStartColumn = 1;
                    continue;
                }
                ++this.bufferStartColumn;
            }
            n2 = this.limit;
            if (n2 != n3) {
                this.limit = n2 - n3;
                object = this.buffer;
                System.arraycopy(object, n3, object, 0, this.limit);
            } else {
                this.limit = 0;
            }
            this.pos = 0;
            do {
                object = this.in;
                char[] arrc = this.buffer;
                n2 = this.limit;
                if ((n2 = ((Reader)object).read(arrc, n2, arrc.length - n2)) == -1) break block5;
                this.limit += n2;
                if (this.bufferStartLine != 1 || (n2 = this.bufferStartColumn) != 1 || this.limit <= 0 || this.buffer[0] != '\ufeff') continue;
                ++this.pos;
                this.bufferStartColumn = n2 - 1;
            } while (this.limit < n);
            return true;
        }
        return false;
    }

    private int getColumnNumber() {
        int n = this.bufferStartColumn;
        for (int i = 0; i < this.pos; ++i) {
            if (this.buffer[i] == '\n') {
                n = 1;
                continue;
            }
            ++n;
        }
        return n;
    }

    private int getLineNumber() {
        int n = this.bufferStartLine;
        for (int i = 0; i < this.pos; ++i) {
            int n2 = n;
            if (this.buffer[i] == '\n') {
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    private CharSequence getSnippet() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = Math.min(this.pos, 20);
        stringBuilder.append(this.buffer, this.pos - n, n);
        n = Math.min(this.limit - this.pos, 20);
        stringBuilder.append(this.buffer, this.pos, n);
        return stringBuilder;
    }

    private JsonToken nextInArray(boolean bl) throws IOException {
        int n;
        JsonToken jsonToken;
        if (bl) {
            this.replaceTop(JsonScope.NONEMPTY_ARRAY);
        } else {
            n = this.nextNonWhitespace();
            if (n != 44) {
                if (n != 59) {
                    if (n == 93) {
                        JsonToken jsonToken2;
                        this.pop();
                        this.token = jsonToken2 = JsonToken.END_ARRAY;
                        return jsonToken2;
                    }
                    throw this.syntaxError("Unterminated array");
                }
                this.checkLenient();
            }
        }
        n = this.nextNonWhitespace();
        if (n != 44 && n != 59) {
            if (n != 93) {
                --this.pos;
                return this.nextValue();
            }
            if (bl) {
                JsonToken jsonToken3;
                this.pop();
                this.token = jsonToken3 = JsonToken.END_ARRAY;
                return jsonToken3;
            }
        }
        this.checkLenient();
        --this.pos;
        this.value = "null";
        this.token = jsonToken = JsonToken.NULL;
        return jsonToken;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private JsonToken nextInObject(boolean var1_1) throws IOException {
        if (var1_1) {
            if (this.nextNonWhitespace() == 125) {
                this.pop();
                this.token = var2_2 = JsonToken.END_OBJECT;
                return var2_2;
            }
            --this.pos;
        } else {
            var3_5 = this.nextNonWhitespace();
            if (var3_5 != 44 && var3_5 != 59) {
                if (var3_5 != 125) throw this.syntaxError("Unterminated object");
                this.pop();
                this.token = var2_3 = JsonToken.END_OBJECT;
                return var2_3;
            }
        }
        var3_5 = this.nextNonWhitespace();
        if (var3_5 == 34) ** GOTO lbl25
        if (var3_5 != 39) {
            this.checkLenient();
            --this.pos;
            this.name = this.nextLiteral(false);
            if (this.name.isEmpty() != false) throw this.syntaxError("Expected name");
        } else {
            this.checkLenient();
lbl25: // 2 sources:
            this.name = this.nextString((char)var3_5);
        }
        this.replaceTop(JsonScope.DANGLING_NAME);
        this.token = var2_4 = JsonToken.NAME;
        return var2_4;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private String nextLiteral(boolean var1_1) throws IOException {
        var2_2 = null;
        this.valuePos = -1;
        this.valueLength = 0;
        var3_3 = 0;
        block4 : do {
            block19 : {
                block17 : {
                    block18 : {
                        block16 : {
                            if ((var4_4 = this.pos) + var3_3 >= this.limit) break block16;
                            if ((var4_4 = this.buffer[var4_4 + var3_3]) == 9 || var4_4 == 10 || var4_4 == 12 || var4_4 == 13 || var4_4 == 32) break block17;
                            if (var4_4 == 35) ** GOTO lbl-1000
                            if (var4_4 == 44) break block17;
                            if (var4_4 == 47 || var4_4 == 61) ** GOTO lbl-1000
                            if (var4_4 == 123 || var4_4 == 125 || var4_4 == 58) break block17;
                            if (var4_4 == 59) ** GOTO lbl-1000
                            switch (var4_4) {
                                default: {
                                    ++var3_3;
                                    continue block4;
                                }
                                case 92: lbl-1000: // 4 sources:
                                {
                                    this.checkLenient();
                                }
                                case 91: 
                                case 93: 
                            }
                            break block17;
                        }
                        if (var3_3 >= this.buffer.length) break block18;
                        if (this.fillBuffer(var3_3 + 1)) continue;
                        this.buffer[this.limit] = (char)(false ? 1 : 0);
                        break block17;
                    }
                    var5_5 = var2_2;
                    if (var2_2 == null) {
                        var5_5 = new StringBuilder();
                    }
                    var5_5.append(this.buffer, this.pos, var3_3);
                    this.valueLength += var3_3;
                    this.pos += var3_3;
                    var4_4 = 0;
                    var3_3 = 0;
                    if (this.fillBuffer(1)) break block19;
                    var3_3 = var4_4;
                    var2_2 = var5_5;
                }
                if (var1_1 && var2_2 == null) {
                    this.valuePos = this.pos;
                    var2_2 = null;
                } else if (this.skipping) {
                    var2_2 = "skipped!";
                } else if (var2_2 == null) {
                    var2_2 = this.stringPool.get(this.buffer, this.pos, var3_3);
                } else {
                    var2_2.append(this.buffer, this.pos, var3_3);
                    var2_2 = var2_2.toString();
                }
                this.valueLength += var3_3;
                this.pos += var3_3;
                return var2_2;
            }
            var2_2 = var5_5;
        } while (true);
    }

    private int nextNonWhitespace() throws IOException {
        do {
            if (this.pos >= this.limit && !this.fillBuffer(1)) {
                throw new EOFException("End of input");
            }
            char[] arrc = this.buffer;
            int n = this.pos;
            this.pos = n + 1;
            if ((n = arrc[n]) == 9 || n == 10 || n == 13 || n == 32) continue;
            if (n != 35) {
                if (n != 47) {
                    return n;
                }
                if (this.pos == this.limit && !this.fillBuffer(1)) {
                    return n;
                }
                this.checkLenient();
                arrc = this.buffer;
                int n2 = this.pos;
                char c = arrc[n2];
                if (c != '*') {
                    if (c != '/') {
                        return n;
                    }
                    this.pos = n2 + 1;
                    this.skipToEndOfLine();
                    continue;
                }
                this.pos = n2 + 1;
                if (this.skipTo("*/")) {
                    this.pos += 2;
                    continue;
                }
                throw this.syntaxError("Unterminated comment");
            }
            this.checkLenient();
            this.skipToEndOfLine();
        } while (true);
    }

    private String nextString(char c) throws IOException {
        char[] arrc = null;
        do {
            Object object;
            int n;
            int n2 = this.pos;
            while ((n = this.pos) < this.limit) {
                object = this.buffer;
                this.pos = n + 1;
                char c2 = object[n];
                if (c2 == c) {
                    if (this.skipping) {
                        return "skipped!";
                    }
                    if (arrc == null) {
                        return this.stringPool.get((char[])object, n2, this.pos - n2 - 1);
                    }
                    arrc.append((char[])object, n2, this.pos - n2 - 1);
                    return arrc.toString();
                }
                object = arrc;
                n = n2;
                if (c2 == '\\') {
                    object = arrc;
                    if (arrc == null) {
                        object = new StringBuilder();
                    }
                    ((StringBuilder)object).append(this.buffer, n2, this.pos - n2 - 1);
                    ((StringBuilder)object).append(this.readEscapeCharacter());
                    n = this.pos;
                }
                arrc = object;
                n2 = n;
            }
            object = arrc;
            if (arrc == null) {
                object = new StringBuilder();
            }
            ((StringBuilder)object).append(this.buffer, n2, this.pos - n2);
            if (!this.fillBuffer(1)) break;
            arrc = object;
        } while (true);
        throw this.syntaxError("Unterminated string");
    }

    private JsonToken nextValue() throws IOException {
        JsonToken jsonToken;
        int n = this.nextNonWhitespace();
        if (n != 34) {
            if (n != 39) {
                JsonToken jsonToken2;
                if (n != 91) {
                    JsonToken jsonToken3;
                    if (n != 123) {
                        --this.pos;
                        return this.readLiteral();
                    }
                    this.push(JsonScope.EMPTY_OBJECT);
                    this.token = jsonToken3 = JsonToken.BEGIN_OBJECT;
                    return jsonToken3;
                }
                this.push(JsonScope.EMPTY_ARRAY);
                this.token = jsonToken2 = JsonToken.BEGIN_ARRAY;
                return jsonToken2;
            }
            this.checkLenient();
        }
        this.value = this.nextString((char)n);
        this.token = jsonToken = JsonToken.STRING;
        return jsonToken;
    }

    private JsonToken objectValue() throws IOException {
        int n = this.nextNonWhitespace();
        if (n != 58) {
            if (n == 61) {
                char[] arrc;
                this.checkLenient();
                if ((this.pos < this.limit || this.fillBuffer(1)) && (arrc = this.buffer)[n = this.pos] == '>') {
                    this.pos = n + 1;
                }
            } else {
                throw this.syntaxError("Expected ':'");
            }
        }
        this.replaceTop(JsonScope.NONEMPTY_OBJECT);
        return this.nextValue();
    }

    private JsonScope peekStack() {
        List<JsonScope> list = this.stack;
        return list.get(list.size() - 1);
    }

    private JsonScope pop() {
        List<JsonScope> list = this.stack;
        return list.remove(list.size() - 1);
    }

    private void push(JsonScope jsonScope) {
        this.stack.add(jsonScope);
    }

    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        Object object = this.buffer;
        int n = this.pos;
        this.pos = n + 1;
        char c = object[n];
        if (c != 'b') {
            if (c != 'f') {
                if (c != 'n') {
                    if (c != 'r') {
                        if (c != 't') {
                            if (c != 'u') {
                                return c;
                            }
                            if (this.pos + 4 > this.limit && !this.fillBuffer(4)) {
                                throw this.syntaxError("Unterminated escape sequence");
                            }
                            object = this.stringPool.get(this.buffer, this.pos, 4);
                            this.pos += 4;
                            return (char)Integer.parseInt((String)object, 16);
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

    private JsonToken readLiteral() throws IOException {
        this.value = this.nextLiteral(true);
        if (this.valueLength != 0) {
            this.token = this.decodeLiteral();
            if (this.token == JsonToken.STRING) {
                this.checkLenient();
            }
            return this.token;
        }
        throw this.syntaxError("Expected literal value");
    }

    private void replaceTop(JsonScope jsonScope) {
        List<JsonScope> list = this.stack;
        list.set(list.size() - 1, jsonScope);
    }

    private boolean skipTo(String string2) throws IOException {
        block0 : do {
            if (this.pos + string2.length() > this.limit && !this.fillBuffer(string2.length())) {
                return false;
            }
            for (int i = 0; i < string2.length(); ++i) {
                if (this.buffer[this.pos + i] == string2.charAt(i)) continue;
                ++this.pos;
                continue block0;
            }
            break;
        } while (true);
        return true;
    }

    private void skipToEndOfLine() throws IOException {
        while (this.pos < this.limit || this.fillBuffer(1)) {
            char[] arrc = this.buffer;
            int n = this.pos;
            this.pos = n + 1;
            if ((n = arrc[n]) != 13 && n != 10) continue;
            break;
        }
    }

    private IOException syntaxError(String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" at line ");
        stringBuilder.append(this.getLineNumber());
        stringBuilder.append(" column ");
        stringBuilder.append(this.getColumnNumber());
        throw new MalformedJsonException(stringBuilder.toString());
    }

    public void beginArray() throws IOException {
        this.expect(JsonToken.BEGIN_ARRAY);
    }

    public void beginObject() throws IOException {
        this.expect(JsonToken.BEGIN_OBJECT);
    }

    @Override
    public void close() throws IOException {
        this.value = null;
        this.token = null;
        this.stack.clear();
        this.stack.add(JsonScope.CLOSED);
        this.in.close();
    }

    public void endArray() throws IOException {
        this.expect(JsonToken.END_ARRAY);
    }

    public void endObject() throws IOException {
        this.expect(JsonToken.END_OBJECT);
    }

    public boolean hasNext() throws IOException {
        this.peek();
        boolean bl = this.token != JsonToken.END_OBJECT && this.token != JsonToken.END_ARRAY;
        return bl;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public boolean nextBoolean() throws IOException {
        this.peek();
        if (this.token == JsonToken.BOOLEAN) {
            boolean bl = this.value == TRUE;
            this.advance();
            return bl;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected a boolean but was ");
        stringBuilder.append((Object)this.token);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public double nextDouble() throws IOException {
        this.peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a double but was ");
            stringBuilder.append((Object)this.token);
            throw new IllegalStateException(stringBuilder.toString());
        }
        double d = Double.parseDouble(this.value);
        this.advance();
        return d;
    }

    public int nextInt() throws IOException {
        block3 : {
            int n;
            this.peek();
            if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected an int but was ");
                stringBuilder.append((Object)this.token);
                throw new IllegalStateException(stringBuilder.toString());
            }
            try {
                n = Integer.parseInt(this.value);
            }
            catch (NumberFormatException numberFormatException) {
                double d = Double.parseDouble(this.value);
                n = (int)d;
                if ((double)n != d) break block3;
            }
            this.advance();
            return n;
        }
        throw new NumberFormatException(this.value);
    }

    public long nextLong() throws IOException {
        block3 : {
            long l;
            this.peek();
            if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected a long but was ");
                stringBuilder.append((Object)this.token);
                throw new IllegalStateException(stringBuilder.toString());
            }
            try {
                l = Long.parseLong(this.value);
            }
            catch (NumberFormatException numberFormatException) {
                double d = Double.parseDouble(this.value);
                l = (long)d;
                if ((double)l != d) break block3;
            }
            this.advance();
            return l;
        }
        throw new NumberFormatException(this.value);
    }

    public String nextName() throws IOException {
        this.peek();
        if (this.token == JsonToken.NAME) {
            String string2 = this.name;
            this.advance();
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected a name but was ");
        stringBuilder.append((Object)this.peek());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void nextNull() throws IOException {
        this.peek();
        if (this.token == JsonToken.NULL) {
            this.advance();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected null but was ");
        stringBuilder.append((Object)this.token);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public String nextString() throws IOException {
        this.peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a string but was ");
            stringBuilder.append((Object)this.peek());
            throw new IllegalStateException(stringBuilder.toString());
        }
        String string2 = this.value;
        this.advance();
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonToken peek() throws IOException {
        JsonToken jsonToken = this.token;
        if (jsonToken != null) {
            return jsonToken;
        }
        switch (this.peekStack()) {
            default: {
                throw new AssertionError();
            }
            case CLOSED: {
                throw new IllegalStateException("JsonReader is closed");
            }
            case NONEMPTY_DOCUMENT: {
                try {
                    JsonToken jsonToken2 = this.nextValue();
                    if (!this.lenient) throw this.syntaxError("Expected EOF");
                    return jsonToken2;
                }
                catch (EOFException eOFException) {
                    JsonToken jsonToken3;
                    this.token = jsonToken3 = JsonToken.END_DOCUMENT;
                    return jsonToken3;
                }
            }
            case NONEMPTY_OBJECT: {
                return this.nextInObject(false);
            }
            case DANGLING_NAME: {
                return this.objectValue();
            }
            case EMPTY_OBJECT: {
                return this.nextInObject(true);
            }
            case NONEMPTY_ARRAY: {
                return this.nextInArray(false);
            }
            case EMPTY_ARRAY: {
                return this.nextInArray(true);
            }
            case EMPTY_DOCUMENT: 
        }
        this.replaceTop(JsonScope.NONEMPTY_DOCUMENT);
        JsonToken jsonToken4 = this.nextValue();
        if (this.lenient) return jsonToken4;
        if (this.token == JsonToken.BEGIN_ARRAY) return jsonToken4;
        if (this.token == JsonToken.BEGIN_OBJECT) {
            return jsonToken4;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected JSON document to start with '[' or '{' but was ");
        stringBuilder.append((Object)this.token);
        throw new IOException(stringBuilder.toString());
    }

    public void setLenient(boolean bl) {
        this.lenient = bl;
    }

    public void skipValue() throws IOException {
        block6 : {
            int n;
            this.skipping = true;
            if (!this.hasNext() || this.peek() == JsonToken.END_DOCUMENT) break block6;
            int n2 = 0;
            do {
                block9 : {
                    block7 : {
                        block8 : {
                            JsonToken jsonToken = this.advance();
                            if (jsonToken == JsonToken.BEGIN_ARRAY || jsonToken == JsonToken.BEGIN_OBJECT) break block7;
                            if (jsonToken == JsonToken.END_ARRAY) break block8;
                            JsonToken jsonToken2 = JsonToken.END_OBJECT;
                            n = n2;
                            if (jsonToken != jsonToken2) break block9;
                        }
                        n = n2 - 1;
                        {
                            break block9;
                        }
                    }
                    n = n2 + 1;
                }
                n2 = n;
            } while (n != 0);
            this.skipping = false;
            return;
        }
        try {
            IllegalStateException illegalStateException = new IllegalStateException("No element left to skip");
            throw illegalStateException;
        }
        catch (Throwable throwable) {
            this.skipping = false;
            throw throwable;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(" near ");
        stringBuilder.append((Object)this.getSnippet());
        return stringBuilder.toString();
    }

}

