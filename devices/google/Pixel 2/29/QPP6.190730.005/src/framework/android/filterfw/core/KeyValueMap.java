/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyValueMap
extends HashMap<String, Object> {
    public static KeyValueMap fromKeyValues(Object ... arrobject) {
        KeyValueMap keyValueMap = new KeyValueMap();
        keyValueMap.setKeyValues(arrobject);
        return keyValueMap;
    }

    public float getFloat(String object) {
        object = (object = this.get(object)) != null ? (Float)object : null;
        return ((Float)object).floatValue();
    }

    public int getInt(String object) {
        object = (object = this.get(object)) != null ? (Integer)object : null;
        return (Integer)object;
    }

    public String getString(String string2) {
        if ((string2 = this.get(string2)) == null) {
            string2 = null;
        }
        return string2;
    }

    public void setKeyValues(Object ... arrobject) {
        if (arrobject.length % 2 == 0) {
            for (int i = 0; i < arrobject.length; i += 2) {
                if (arrobject[i] instanceof String) {
                    this.put((String)arrobject[i], arrobject[i + 1]);
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Key-value argument ");
                stringBuilder.append(i);
                stringBuilder.append(" must be a key of type String, but found an object of type ");
                stringBuilder.append(arrobject[i].getClass());
                stringBuilder.append("!");
                throw new RuntimeException(stringBuilder.toString());
            }
            return;
        }
        throw new RuntimeException("Key-Value arguments passed into setKeyValues must be an alternating list of keys and values!");
    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        for (Map.Entry entry : this.entrySet()) {
            StringBuilder stringBuilder;
            Object object = entry.getValue();
            if (object instanceof String) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("\"");
                stringBuilder.append(object);
                stringBuilder.append("\"");
                object = stringBuilder.toString();
            } else {
                object = object.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)entry.getKey());
            stringBuilder.append(" = ");
            stringBuilder.append((String)object);
            stringBuilder.append(";\n");
            stringWriter.write(stringBuilder.toString());
        }
        return stringWriter.toString();
    }
}

