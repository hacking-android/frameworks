/*
 * Decompiled with CFR 0.145.
 */
package org.json;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.json.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;
import org.json.JSONTokener;

public class JSONObject {
    @UnsupportedAppUsage
    private static final Double NEGATIVE_ZERO = 0.0;
    public static final Object NULL = new Object(){

        public boolean equals(Object object) {
            boolean bl = object == this || object == null;
            return bl;
        }

        public int hashCode() {
            return Objects.hashCode(null);
        }

        public String toString() {
            return "null";
        }
    };
    @UnsupportedAppUsage
    private final LinkedHashMap<String, Object> nameValuePairs;

    public JSONObject() {
        this.nameValuePairs = new LinkedHashMap();
    }

    public JSONObject(String string) throws JSONException {
        this(new JSONTokener(string));
    }

    public JSONObject(Map object) {
        this();
        for (Map.Entry entry : object.entrySet()) {
            String string = (String)entry.getKey();
            if (string != null) {
                this.nameValuePairs.put(string, JSONObject.wrap(entry.getValue()));
                continue;
            }
            throw new NullPointerException("key == null");
        }
    }

    public JSONObject(JSONObject jSONObject, String[] arrstring) throws JSONException {
        this();
        for (String string : arrstring) {
            Object object = jSONObject.opt(string);
            if (object == null) continue;
            this.nameValuePairs.put(string, object);
        }
    }

    public JSONObject(JSONTokener object) throws JSONException {
        object = ((JSONTokener)object).nextValue();
        if (object instanceof JSONObject) {
            this.nameValuePairs = ((JSONObject)object).nameValuePairs;
            return;
        }
        throw JSON.typeMismatch(object, "JSONObject");
    }

    public static String numberToString(Number number) throws JSONException {
        if (number != null) {
            double d = number.doubleValue();
            JSON.checkDouble(d);
            if (number.equals(NEGATIVE_ZERO)) {
                return "-0";
            }
            long l = number.longValue();
            if (d == (double)l) {
                return Long.toString(l);
            }
            return number.toString();
        }
        throw new JSONException("Number must be non-null");
    }

    public static String quote(String string) {
        if (string == null) {
            return "\"\"";
        }
        try {
            JSONStringer jSONStringer = new JSONStringer();
            jSONStringer.open(JSONStringer.Scope.NULL, "");
            jSONStringer.value(string);
            jSONStringer.close(JSONStringer.Scope.NULL, JSONStringer.Scope.NULL, "");
            string = jSONStringer.toString();
            return string;
        }
        catch (JSONException jSONException) {
            throw new AssertionError();
        }
    }

    public static Object wrap(Object object) {
        if (object == null) {
            return NULL;
        }
        if (!(object instanceof JSONArray) && !(object instanceof JSONObject)) {
            block10 : {
                if (object.equals(NULL)) {
                    return object;
                }
                try {
                    if (object instanceof Collection) {
                        return new JSONArray((Collection)object);
                    }
                    if (object.getClass().isArray()) {
                        return new JSONArray(object);
                    }
                    if (object instanceof Map) {
                        return new JSONObject((Map)object);
                    }
                    if (!(object instanceof Boolean || object instanceof Byte || object instanceof Character || object instanceof Double || object instanceof Float || object instanceof Integer || object instanceof Long || object instanceof Short || object instanceof String)) {
                        if (object.getClass().getPackage().getName().startsWith("java.")) {
                            object = object.toString();
                            return object;
                        }
                        break block10;
                    }
                    return object;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            return null;
        }
        return object;
    }

    public JSONObject accumulate(String string, Object object) throws JSONException {
        Object object2 = this.nameValuePairs.get(this.checkName(string));
        if (object2 == null) {
            return this.put(string, object);
        }
        if (object2 instanceof JSONArray) {
            ((JSONArray)object2).checkedPut(object);
        } else {
            JSONArray jSONArray = new JSONArray();
            jSONArray.checkedPut(object2);
            jSONArray.checkedPut(object);
            this.nameValuePairs.put(string, jSONArray);
        }
        return this;
    }

    @UnsupportedAppUsage
    public JSONObject append(String object, Object object2) throws JSONException {
        block4 : {
            block3 : {
                Object object3;
                block2 : {
                    object3 = this.nameValuePairs.get(this.checkName((String)object));
                    if (!(object3 instanceof JSONArray)) break block2;
                    object = (JSONArray)object3;
                    break block3;
                }
                if (object3 != null) break block4;
                object3 = new JSONArray();
                this.nameValuePairs.put((String)object, object3);
                object = object3;
            }
            ((JSONArray)object).checkedPut(object2);
            return this;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Key ");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(" is not a JSONArray");
        throw new JSONException(((StringBuilder)object2).toString());
    }

    @UnsupportedAppUsage
    String checkName(String string) throws JSONException {
        if (string != null) {
            return string;
        }
        throw new JSONException("Names must be non-null");
    }

    public Object get(String string) throws JSONException {
        Object object = this.nameValuePairs.get(string);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No value for ");
        ((StringBuilder)object).append(string);
        throw new JSONException(((StringBuilder)object).toString());
    }

    public boolean getBoolean(String string) throws JSONException {
        Object object = this.get(string);
        Boolean bl = JSON.toBoolean(object);
        if (bl != null) {
            return bl;
        }
        throw JSON.typeMismatch(string, object, "boolean");
    }

    public double getDouble(String string) throws JSONException {
        Object object = this.get(string);
        Double d = JSON.toDouble(object);
        if (d != null) {
            return d;
        }
        throw JSON.typeMismatch(string, object, "double");
    }

    public int getInt(String string) throws JSONException {
        Object object = this.get(string);
        Integer n = JSON.toInteger(object);
        if (n != null) {
            return n;
        }
        throw JSON.typeMismatch(string, object, "int");
    }

    public JSONArray getJSONArray(String string) throws JSONException {
        Object object = this.get(string);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        throw JSON.typeMismatch(string, object, "JSONArray");
    }

    public JSONObject getJSONObject(String string) throws JSONException {
        Object object = this.get(string);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        throw JSON.typeMismatch(string, object, "JSONObject");
    }

    public long getLong(String string) throws JSONException {
        Object object = this.get(string);
        Long l = JSON.toLong(object);
        if (l != null) {
            return l;
        }
        throw JSON.typeMismatch(string, object, "long");
    }

    public String getString(String string) throws JSONException {
        Object object = this.get(string);
        String string2 = JSON.toString(object);
        if (string2 != null) {
            return string2;
        }
        throw JSON.typeMismatch(string, object, "String");
    }

    public boolean has(String string) {
        return this.nameValuePairs.containsKey(string);
    }

    public boolean isNull(String object) {
        boolean bl = (object = this.nameValuePairs.get(object)) == null || object == NULL;
        return bl;
    }

    @UnsupportedAppUsage
    public Set<String> keySet() {
        return this.nameValuePairs.keySet();
    }

    public Iterator<String> keys() {
        return this.nameValuePairs.keySet().iterator();
    }

    public int length() {
        return this.nameValuePairs.size();
    }

    public JSONArray names() {
        JSONArray jSONArray = this.nameValuePairs.isEmpty() ? null : new JSONArray(new ArrayList<String>(this.nameValuePairs.keySet()));
        return jSONArray;
    }

    public Object opt(String string) {
        return this.nameValuePairs.get(string);
    }

    public boolean optBoolean(String string) {
        return this.optBoolean(string, false);
    }

    public boolean optBoolean(String object, boolean bl) {
        block0 : {
            if ((object = JSON.toBoolean(this.opt((String)object))) == null) break block0;
            bl = (Boolean)object;
        }
        return bl;
    }

    public double optDouble(String string) {
        return this.optDouble(string, Double.NaN);
    }

    public double optDouble(String object, double d) {
        block0 : {
            if ((object = JSON.toDouble(this.opt((String)object))) == null) break block0;
            d = (Double)object;
        }
        return d;
    }

    public int optInt(String string) {
        return this.optInt(string, 0);
    }

    public int optInt(String object, int n) {
        block0 : {
            if ((object = JSON.toInteger(this.opt((String)object))) == null) break block0;
            n = (Integer)object;
        }
        return n;
    }

    public JSONArray optJSONArray(String object) {
        object = (object = this.opt((String)object)) instanceof JSONArray ? (JSONArray)object : null;
        return object;
    }

    public JSONObject optJSONObject(String object) {
        object = (object = this.opt((String)object)) instanceof JSONObject ? (JSONObject)object : null;
        return object;
    }

    public long optLong(String string) {
        return this.optLong(string, 0L);
    }

    public long optLong(String object, long l) {
        block0 : {
            if ((object = JSON.toLong(this.opt((String)object))) == null) break block0;
            l = (Long)object;
        }
        return l;
    }

    public String optString(String string) {
        return this.optString(string, "");
    }

    public String optString(String string, String string2) {
        if ((string = JSON.toString(this.opt(string))) == null) {
            string = string2;
        }
        return string;
    }

    public JSONObject put(String string, double d) throws JSONException {
        this.nameValuePairs.put(this.checkName(string), JSON.checkDouble(d));
        return this;
    }

    public JSONObject put(String string, int n) throws JSONException {
        this.nameValuePairs.put(this.checkName(string), n);
        return this;
    }

    public JSONObject put(String string, long l) throws JSONException {
        this.nameValuePairs.put(this.checkName(string), l);
        return this;
    }

    public JSONObject put(String string, Object object) throws JSONException {
        if (object == null) {
            this.nameValuePairs.remove(string);
            return this;
        }
        if (object instanceof Number) {
            JSON.checkDouble(((Number)object).doubleValue());
        }
        this.nameValuePairs.put(this.checkName(string), object);
        return this;
    }

    public JSONObject put(String string, boolean bl) throws JSONException {
        this.nameValuePairs.put(this.checkName(string), bl);
        return this;
    }

    public JSONObject putOpt(String string, Object object) throws JSONException {
        if (string != null && object != null) {
            return this.put(string, object);
        }
        return this;
    }

    public Object remove(String string) {
        return this.nameValuePairs.remove(string);
    }

    public JSONArray toJSONArray(JSONArray jSONArray) throws JSONException {
        JSONArray jSONArray2 = new JSONArray();
        if (jSONArray == null) {
            return null;
        }
        int n = jSONArray.length();
        if (n == 0) {
            return null;
        }
        for (int i = 0; i < n; ++i) {
            jSONArray2.put(this.opt(JSON.toString(jSONArray.opt(i))));
        }
        return jSONArray2;
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
        jSONStringer.object();
        for (Map.Entry<String, Object> entry : this.nameValuePairs.entrySet()) {
            jSONStringer.key(entry.getKey()).value(entry.getValue());
        }
        jSONStringer.endObject();
    }

}

