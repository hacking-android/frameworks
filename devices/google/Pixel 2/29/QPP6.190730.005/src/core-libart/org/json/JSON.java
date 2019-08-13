/*
 * Decompiled with CFR 0.145.
 */
package org.json;

import org.json.JSONException;

class JSON {
    JSON() {
    }

    static double checkDouble(double d) throws JSONException {
        if (!Double.isInfinite(d) && !Double.isNaN(d)) {
            return d;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Forbidden numeric value: ");
        stringBuilder.append(d);
        throw new JSONException(stringBuilder.toString());
    }

    static Boolean toBoolean(Object object) {
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        if (object instanceof String) {
            if ("true".equalsIgnoreCase((String)(object = (String)object))) {
                return true;
            }
            if ("false".equalsIgnoreCase((String)object)) {
                return false;
            }
        }
        return null;
    }

    static Double toDouble(Object object) {
        if (object instanceof Double) {
            return (Double)object;
        }
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        if (object instanceof String) {
            try {
                object = Double.valueOf((String)object);
                return object;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return null;
    }

    static Integer toInteger(Object object) {
        if (object instanceof Integer) {
            return (Integer)object;
        }
        if (object instanceof Number) {
            return ((Number)object).intValue();
        }
        if (object instanceof String) {
            int n;
            try {
                n = (int)Double.parseDouble((String)object);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            return n;
        }
        return null;
    }

    static Long toLong(Object object) {
        if (object instanceof Long) {
            return (Long)object;
        }
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        if (object instanceof String) {
            long l;
            try {
                l = (long)Double.parseDouble((String)object);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            return l;
        }
        return null;
    }

    static String toString(Object object) {
        if (object instanceof String) {
            return (String)object;
        }
        if (object != null) {
            return String.valueOf(object);
        }
        return null;
    }

    public static JSONException typeMismatch(Object object, Object object2, String string) throws JSONException {
        if (object2 == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Value at ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" is null.");
            throw new JSONException(((StringBuilder)object2).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value ");
        stringBuilder.append(object2);
        stringBuilder.append(" at ");
        stringBuilder.append(object);
        stringBuilder.append(" of type ");
        stringBuilder.append(object2.getClass().getName());
        stringBuilder.append(" cannot be converted to ");
        stringBuilder.append(string);
        throw new JSONException(stringBuilder.toString());
    }

    public static JSONException typeMismatch(Object object, String string) throws JSONException {
        if (object == null) {
            throw new JSONException("Value is null.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value ");
        stringBuilder.append(object);
        stringBuilder.append(" of type ");
        stringBuilder.append(object.getClass().getName());
        stringBuilder.append(" cannot be converted to ");
        stringBuilder.append(string);
        throw new JSONException(stringBuilder.toString());
    }
}

