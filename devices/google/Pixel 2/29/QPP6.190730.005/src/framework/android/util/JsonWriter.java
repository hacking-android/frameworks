/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.JsonScope;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class JsonWriter
implements Closeable {
    private String indent;
    private boolean lenient;
    private final Writer out;
    private String separator;
    private final List<JsonScope> stack = new ArrayList<JsonScope>();

    public JsonWriter(Writer writer) {
        this.stack.add(JsonScope.EMPTY_DOCUMENT);
        this.separator = ":";
        if (writer != null) {
            this.out = writer;
            return;
        }
        throw new NullPointerException("out == null");
    }

    private void beforeName() throws IOException {
        Object object;
        block4 : {
            block3 : {
                block2 : {
                    object = this.peek();
                    if (object != JsonScope.NONEMPTY_OBJECT) break block2;
                    this.out.write(44);
                    break block3;
                }
                if (object != JsonScope.EMPTY_OBJECT) break block4;
            }
            this.newline();
            this.replaceTop(JsonScope.DANGLING_NAME);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Nesting problem: ");
        ((StringBuilder)object).append(this.stack);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private void beforeValue(boolean bl) throws IOException {
        int n = 1.$SwitchMap$android$util$JsonScope[this.peek().ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Nesting problem: ");
                            stringBuilder.append(this.stack);
                            throw new IllegalStateException(stringBuilder.toString());
                        }
                        throw new IllegalStateException("JSON must have only one top-level value.");
                    }
                    this.out.append(this.separator);
                    this.replaceTop(JsonScope.NONEMPTY_OBJECT);
                } else {
                    this.out.append(',');
                    this.newline();
                }
            } else {
                this.replaceTop(JsonScope.NONEMPTY_ARRAY);
                this.newline();
            }
        } else {
            if (!this.lenient && !bl) {
                throw new IllegalStateException("JSON must start with an array or an object.");
            }
            this.replaceTop(JsonScope.NONEMPTY_DOCUMENT);
        }
    }

    private JsonWriter close(JsonScope object, JsonScope jsonScope, String string2) throws IOException {
        JsonScope jsonScope2 = this.peek();
        if (jsonScope2 != jsonScope && jsonScope2 != object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Nesting problem: ");
            ((StringBuilder)object).append(this.stack);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object = this.stack;
        object.remove(object.size() - 1);
        if (jsonScope2 == jsonScope) {
            this.newline();
        }
        this.out.write(string2);
        return this;
    }

    private void newline() throws IOException {
        if (this.indent == null) {
            return;
        }
        this.out.write("\n");
        for (int i = 1; i < this.stack.size(); ++i) {
            this.out.write(this.indent);
        }
    }

    private JsonWriter open(JsonScope jsonScope, String string2) throws IOException {
        this.beforeValue(true);
        this.stack.add(jsonScope);
        this.out.write(string2);
        return this;
    }

    private JsonScope peek() {
        List<JsonScope> list = this.stack;
        return list.get(list.size() - 1);
    }

    private void replaceTop(JsonScope jsonScope) {
        List<JsonScope> list = this.stack;
        list.set(list.size() - 1, jsonScope);
    }

    private void string(String string2) throws IOException {
        this.out.write("\"");
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c != '\f') {
                if (c != '\r') {
                    if (c != '\"' && c != '\\') {
                        if (c != '\u2028' && c != '\u2029') {
                            switch (c) {
                                default: {
                                    if (c <= '\u001f') {
                                        this.out.write(String.format("\\u%04x", c));
                                        break;
                                    }
                                    this.out.write(c);
                                    break;
                                }
                                case '\n': {
                                    this.out.write("\\n");
                                    break;
                                }
                                case '\t': {
                                    this.out.write("\\t");
                                    break;
                                }
                                case '\b': {
                                    this.out.write("\\b");
                                    break;
                                }
                            }
                            continue;
                        }
                        this.out.write(String.format("\\u%04x", c));
                        continue;
                    }
                    this.out.write(92);
                    this.out.write(c);
                    continue;
                }
                this.out.write("\\r");
                continue;
            }
            this.out.write("\\f");
        }
        this.out.write("\"");
    }

    public JsonWriter beginArray() throws IOException {
        return this.open(JsonScope.EMPTY_ARRAY, "[");
    }

    public JsonWriter beginObject() throws IOException {
        return this.open(JsonScope.EMPTY_OBJECT, "{");
    }

    @Override
    public void close() throws IOException {
        this.out.close();
        if (this.peek() == JsonScope.NONEMPTY_DOCUMENT) {
            return;
        }
        throw new IOException("Incomplete document");
    }

    public JsonWriter endArray() throws IOException {
        return this.close(JsonScope.EMPTY_ARRAY, JsonScope.NONEMPTY_ARRAY, "]");
    }

    public JsonWriter endObject() throws IOException {
        return this.close(JsonScope.EMPTY_OBJECT, JsonScope.NONEMPTY_OBJECT, "}");
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public JsonWriter name(String string2) throws IOException {
        if (string2 != null) {
            this.beforeName();
            this.string(string2);
            return this;
        }
        throw new NullPointerException("name == null");
    }

    public JsonWriter nullValue() throws IOException {
        this.beforeValue(false);
        this.out.write("null");
        return this;
    }

    public void setIndent(String string2) {
        if (string2.isEmpty()) {
            this.indent = null;
            this.separator = ":";
        } else {
            this.indent = string2;
            this.separator = ": ";
        }
    }

    public void setLenient(boolean bl) {
        this.lenient = bl;
    }

    public JsonWriter value(double d) throws IOException {
        if (!this.lenient && (Double.isNaN(d) || Double.isInfinite(d))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Numeric values must be finite, but was ");
            stringBuilder.append(d);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.beforeValue(false);
        this.out.append(Double.toString(d));
        return this;
    }

    public JsonWriter value(long l) throws IOException {
        this.beforeValue(false);
        this.out.write(Long.toString(l));
        return this;
    }

    public JsonWriter value(Number number) throws IOException {
        if (number == null) {
            return this.nullValue();
        }
        CharSequence charSequence = number.toString();
        if (!this.lenient && (((String)charSequence).equals("-Infinity") || ((String)charSequence).equals("Infinity") || ((String)charSequence).equals("NaN"))) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Numeric values must be finite, but was ");
            ((StringBuilder)charSequence).append(number);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        this.beforeValue(false);
        this.out.append(charSequence);
        return this;
    }

    public JsonWriter value(String string2) throws IOException {
        if (string2 == null) {
            return this.nullValue();
        }
        this.beforeValue(false);
        this.string(string2);
        return this;
    }

    public JsonWriter value(boolean bl) throws IOException {
        this.beforeValue(false);
        Writer writer = this.out;
        String string2 = bl ? "true" : "false";
        writer.write(string2);
        return this;
    }

}

