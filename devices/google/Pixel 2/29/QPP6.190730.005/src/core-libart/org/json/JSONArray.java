/*
 * Decompiled with CFR 0.145.
 */
package org.json;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.JSON;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

public class JSONArray {
    @UnsupportedAppUsage
    private final List<Object> values;

    public JSONArray() {
        this.values = new ArrayList<Object>();
    }

    public JSONArray(Object object) throws JSONException {
        if (object.getClass().isArray()) {
            int n = Array.getLength(object);
            this.values = new ArrayList<Object>(n);
            for (int i = 0; i < n; ++i) {
                this.put(JSONObject.wrap(Array.get(object, i)));
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a primitive array: ");
        stringBuilder.append(object.getClass());
        throw new JSONException(stringBuilder.toString());
    }

    public JSONArray(String string) throws JSONException {
        this(new JSONTokener(string));
    }

    public JSONArray(Collection object) {
        this();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                this.put(JSONObject.wrap(object.next()));
            }
        }
    }

    public JSONArray(JSONTokener object) throws JSONException {
        object = ((JSONTokener)object).nextValue();
        if (object instanceof JSONArray) {
            this.values = ((JSONArray)object).values;
            return;
        }
        throw JSON.typeMismatch(object, "JSONArray");
    }

    void checkedPut(Object object) throws JSONException {
        if (object instanceof Number) {
            JSON.checkDouble(((Number)object).doubleValue());
        }
        this.put(object);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof JSONArray && ((JSONArray)object).values.equals(this.values);
        return bl;
    }

    public Object get(int n) throws JSONException {
        Object object;
        block3 : {
            try {
                object = this.values.get(n);
                if (object == null) break block3;
                return object;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Index ");
                stringBuilder.append(n);
                stringBuilder.append(" out of range [0..");
                stringBuilder.append(this.values.size());
                stringBuilder.append(")");
                throw new JSONException(stringBuilder.toString(), indexOutOfBoundsException);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Value at ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" is null.");
        JSONException jSONException = new JSONException(((StringBuilder)object).toString());
        throw jSONException;
    }

    public boolean getBoolean(int n) throws JSONException {
        Object object = this.get(n);
        Boolean bl = JSON.toBoolean(object);
        if (bl != null) {
            return bl;
        }
        throw JSON.typeMismatch(n, object, "boolean");
    }

    public double getDouble(int n) throws JSONException {
        Object object = this.get(n);
        Double d = JSON.toDouble(object);
        if (d != null) {
            return d;
        }
        throw JSON.typeMismatch(n, object, "double");
    }

    public int getInt(int n) throws JSONException {
        Object object = this.get(n);
        Integer n2 = JSON.toInteger(object);
        if (n2 != null) {
            return n2;
        }
        throw JSON.typeMismatch(n, object, "int");
    }

    public JSONArray getJSONArray(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        throw JSON.typeMismatch(n, object, "JSONArray");
    }

    public JSONObject getJSONObject(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        throw JSON.typeMismatch(n, object, "JSONObject");
    }

    public long getLong(int n) throws JSONException {
        Object object = this.get(n);
        Long l = JSON.toLong(object);
        if (l != null) {
            return l;
        }
        throw JSON.typeMismatch(n, object, "long");
    }

    public String getString(int n) throws JSONException {
        Object object = this.get(n);
        String string = JSON.toString(object);
        if (string != null) {
            return string;
        }
        throw JSON.typeMismatch(n, object, "String");
    }

    public int hashCode() {
        return this.values.hashCode();
    }

    public boolean isNull(int n) {
        Object object = this.opt(n);
        boolean bl = object == null || object == JSONObject.NULL;
        return bl;
    }

    public String join(String string) throws JSONException {
        JSONStringer jSONStringer = new JSONStringer();
        jSONStringer.open(JSONStringer.Scope.NULL, "");
        int n = this.values.size();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                jSONStringer.out.append(string);
            }
            jSONStringer.value(this.values.get(i));
        }
        jSONStringer.close(JSONStringer.Scope.NULL, JSONStringer.Scope.NULL, "");
        return jSONStringer.out.toString();
    }

    public int length() {
        return this.values.size();
    }

    public Object opt(int n) {
        if (n >= 0 && n < this.values.size()) {
            return this.values.get(n);
        }
        return null;
    }

    public boolean optBoolean(int n) {
        return this.optBoolean(n, false);
    }

    public boolean optBoolean(int n, boolean bl) {
        block0 : {
            Boolean bl2 = JSON.toBoolean(this.opt(n));
            if (bl2 == null) break block0;
            bl = bl2;
        }
        return bl;
    }

    public double optDouble(int n) {
        return this.optDouble(n, Double.NaN);
    }

    public double optDouble(int n, double d) {
        block0 : {
            Double d2 = JSON.toDouble(this.opt(n));
            if (d2 == null) break block0;
            d = d2;
        }
        return d;
    }

    public int optInt(int n) {
        return this.optInt(n, 0);
    }

    public int optInt(int n, int n2) {
        Integer n3 = JSON.toInteger(this.opt(n));
        n = n3 != null ? n3 : n2;
        return n;
    }

    public JSONArray optJSONArray(int n) {
        Object object = this.opt(n);
        object = object instanceof JSONArray ? (JSONArray)object : null;
        return object;
    }

    public JSONObject optJSONObject(int n) {
        Object object = this.opt(n);
        object = object instanceof JSONObject ? (JSONObject)object : null;
        return object;
    }

    public long optLong(int n) {
        return this.optLong(n, 0L);
    }

    public long optLong(int n, long l) {
        block0 : {
            Long l2 = JSON.toLong(this.opt(n));
            if (l2 == null) break block0;
            l = l2;
        }
        return l;
    }

    public String optString(int n) {
        return this.optString(n, "");
    }

    public String optString(int n, String string) {
        block0 : {
            String string2 = JSON.toString(this.opt(n));
            if (string2 == null) break block0;
            string = string2;
        }
        return string;
    }

    public JSONArray put(double d) throws JSONException {
        this.values.add(JSON.checkDouble(d));
        return this;
    }

    public JSONArray put(int n) {
        this.values.add(n);
        return this;
    }

    public JSONArray put(int n, double d) throws JSONException {
        return this.put(n, (Object)d);
    }

    public JSONArray put(int n, int n2) throws JSONException {
        return this.put(n, (Object)n2);
    }

    public JSONArray put(int n, long l) throws JSONException {
        return this.put(n, (Object)l);
    }

    public JSONArray put(int n, Object object) throws JSONException {
        if (object instanceof Number) {
            JSON.checkDouble(((Number)object).doubleValue());
        }
        while (this.values.size() <= n) {
            this.values.add(null);
        }
        this.values.set(n, object);
        return this;
    }

    public JSONArray put(int n, boolean bl) throws JSONException {
        return this.put(n, (Object)bl);
    }

    public JSONArray put(long l) {
        this.values.add(l);
        return this;
    }

    public JSONArray put(Object object) {
        this.values.add(object);
        return this;
    }

    public JSONArray put(boolean bl) {
        this.values.add(bl);
        return this;
    }

    public Object remove(int n) {
        if (n >= 0 && n < this.values.size()) {
            return this.values.remove(n);
        }
        return null;
    }

    public JSONObject toJSONObject(JSONArray jSONArray) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        int n = Math.min(jSONArray.length(), this.values.size());
        if (n == 0) {
            return null;
        }
        for (int i = 0; i < n; ++i) {
            jSONObject.put(JSON.toString(jSONArray.opt(i)), this.opt(i));
        }
        return jSONObject;
    }

    public String toString() {
        try {
            Object object = new JSONStringer();
            this.writeTo((JSONStringer)object);
            object = ((JSONStringer)object).toString();
            return object;
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    public String toString(int n) throws JSONException {
        JSONStringer jSONStringer = new JSONStringer(n);
        this.writeTo(jSONStringer);
        return jSONStringer.toString();
    }

    @UnsupportedAppUsage
    void writeTo(JSONStringer jSONStringer) throws JSONException {
        jSONStringer.array();
        Iterator<Object> iterator = this.values.iterator();
        while (iterator.hasNext()) {
            jSONStringer.value(iterator.next());
        }
        jSONStringer.endArray();
    }
}

