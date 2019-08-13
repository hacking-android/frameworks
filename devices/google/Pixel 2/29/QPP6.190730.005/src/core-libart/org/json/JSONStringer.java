/*
 * Decompiled with CFR 0.145.
 */
package org.json;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONStringer {
    @UnsupportedAppUsage
    private final String indent;
    @UnsupportedAppUsage
    final StringBuilder out = new StringBuilder();
    @UnsupportedAppUsage
    private final List<Scope> stack = new ArrayList<Scope>();

    public JSONStringer() {
        this.indent = null;
    }

    @UnsupportedAppUsage
    JSONStringer(int n) {
        char[] arrc = new char[n];
        Arrays.fill(arrc, ' ');
        this.indent = new String(arrc);
    }

    @UnsupportedAppUsage
    private void beforeKey() throws JSONException {
        block4 : {
            block3 : {
                Scope scope;
                block2 : {
                    scope = this.peek();
                    if (scope != Scope.NONEMPTY_OBJECT) break block2;
                    this.out.append(',');
                    break block3;
                }
                if (scope != Scope.EMPTY_OBJECT) break block4;
            }
            this.newline();
            this.replaceTop(Scope.DANGLING_KEY);
            return;
        }
        throw new JSONException("Nesting problem");
    }

    @UnsupportedAppUsage
    private void beforeValue() throws JSONException {
        block8 : {
            block5 : {
                Object object;
                block7 : {
                    block6 : {
                        block4 : {
                            if (this.stack.isEmpty()) {
                                return;
                            }
                            object = this.peek();
                            if (object != Scope.EMPTY_ARRAY) break block4;
                            this.replaceTop(Scope.NONEMPTY_ARRAY);
                            this.newline();
                            break block5;
                        }
                        if (object != Scope.NONEMPTY_ARRAY) break block6;
                        this.out.append(',');
                        this.newline();
                        break block5;
                    }
                    if (object != Scope.DANGLING_KEY) break block7;
                    StringBuilder stringBuilder = this.out;
                    object = this.indent == null ? ":" : ": ";
                    stringBuilder.append((String)object);
                    this.replaceTop(Scope.NONEMPTY_OBJECT);
                    break block5;
                }
                if (object != Scope.NULL) break block8;
            }
            return;
        }
        throw new JSONException("Nesting problem");
    }

    @UnsupportedAppUsage
    private void newline() {
        if (this.indent == null) {
            return;
        }
        this.out.append("\n");
        for (int i = 0; i < this.stack.size(); ++i) {
            this.out.append(this.indent);
        }
    }

    @UnsupportedAppUsage
    private Scope peek() throws JSONException {
        if (!this.stack.isEmpty()) {
            List<Scope> list = this.stack;
            return list.get(list.size() - 1);
        }
        throw new JSONException("Nesting problem");
    }

    @UnsupportedAppUsage
    private void replaceTop(Scope scope) {
        List<Scope> list = this.stack;
        list.set(list.size() - 1, scope);
    }

    @UnsupportedAppUsage
    private void string(String string) {
        this.out.append("\"");
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c != '\f') {
                if (c != '\r') {
                    if (c != '\"' && c != '/' && c != '\\') {
                        switch (c) {
                            default: {
                                if (c <= '\u001f') {
                                    this.out.append(String.format("\\u%04x", c));
                                    break;
                                }
                                this.out.append(c);
                                break;
                            }
                            case '\n': {
                                this.out.append("\\n");
                                break;
                            }
                            case '\t': {
                                this.out.append("\\t");
                                break;
                            }
                            case '\b': {
                                this.out.append("\\b");
                                break;
                            }
                        }
                        continue;
                    }
                    StringBuilder stringBuilder = this.out;
                    stringBuilder.append('\\');
                    stringBuilder.append(c);
                    continue;
                }
                this.out.append("\\r");
                continue;
            }
            this.out.append("\\f");
        }
        this.out.append("\"");
    }

    public JSONStringer array() throws JSONException {
        return this.open(Scope.EMPTY_ARRAY, "[");
    }

    @UnsupportedAppUsage
    JSONStringer close(Scope object, Scope scope, String string) throws JSONException {
        Scope scope2 = this.peek();
        if (scope2 != scope && scope2 != object) {
            throw new JSONException("Nesting problem");
        }
        object = this.stack;
        object.remove(object.size() - 1);
        if (scope2 == scope) {
            this.newline();
        }
        this.out.append(string);
        return this;
    }

    public JSONStringer endArray() throws JSONException {
        return this.close(Scope.EMPTY_ARRAY, Scope.NONEMPTY_ARRAY, "]");
    }

    public JSONStringer endObject() throws JSONException {
        return this.close(Scope.EMPTY_OBJECT, Scope.NONEMPTY_OBJECT, "}");
    }

    public JSONStringer key(String string) throws JSONException {
        if (string != null) {
            this.beforeKey();
            this.string(string);
            return this;
        }
        throw new JSONException("Names must be non-null");
    }

    public JSONStringer object() throws JSONException {
        return this.open(Scope.EMPTY_OBJECT, "{");
    }

    @UnsupportedAppUsage
    JSONStringer open(Scope scope, String string) throws JSONException {
        if (this.stack.isEmpty() && this.out.length() > 0) {
            throw new JSONException("Nesting problem: multiple top-level roots");
        }
        this.beforeValue();
        this.stack.add(scope);
        this.out.append(string);
        return this;
    }

    public String toString() {
        String string = this.out.length() == 0 ? null : this.out.toString();
        return string;
    }

    public JSONStringer value(double d) throws JSONException {
        if (!this.stack.isEmpty()) {
            this.beforeValue();
            this.out.append(JSONObject.numberToString(d));
            return this;
        }
        throw new JSONException("Nesting problem");
    }

    public JSONStringer value(long l) throws JSONException {
        if (!this.stack.isEmpty()) {
            this.beforeValue();
            this.out.append(l);
            return this;
        }
        throw new JSONException("Nesting problem");
    }

    public JSONStringer value(Object object) throws JSONException {
        if (!this.stack.isEmpty()) {
            if (object instanceof JSONArray) {
                ((JSONArray)object).writeTo(this);
                return this;
            }
            if (object instanceof JSONObject) {
                ((JSONObject)object).writeTo(this);
                return this;
            }
            this.beforeValue();
            if (object != null && !(object instanceof Boolean) && object != JSONObject.NULL) {
                if (object instanceof Number) {
                    this.out.append(JSONObject.numberToString((Number)object));
                } else {
                    this.string(object.toString());
                }
            } else {
                this.out.append(object);
            }
            return this;
        }
        throw new JSONException("Nesting problem");
    }

    public JSONStringer value(boolean bl) throws JSONException {
        if (!this.stack.isEmpty()) {
            this.beforeValue();
            this.out.append(bl);
            return this;
        }
        throw new JSONException("Nesting problem");
    }

    static enum Scope {
        EMPTY_ARRAY,
        NONEMPTY_ARRAY,
        EMPTY_OBJECT,
        DANGLING_KEY,
        NONEMPTY_OBJECT,
        NULL;
        
    }

}

