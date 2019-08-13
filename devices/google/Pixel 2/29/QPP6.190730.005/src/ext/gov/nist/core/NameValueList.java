/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.GenericObject;
import gov.nist.core.NameValue;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NameValueList
implements Serializable,
Cloneable,
Map<String, NameValue> {
    private static final long serialVersionUID = -6998271876574260243L;
    private Map<String, NameValue> hmap;
    private String separator = ";";

    public NameValueList() {
        this.hmap = new LinkedHashMap<String, NameValue>();
    }

    public NameValueList(boolean bl) {
        this.hmap = bl ? new ConcurrentHashMap<String, NameValue>() : new LinkedHashMap<String, NameValue>();
    }

    @Override
    public void clear() {
        this.hmap.clear();
    }

    public Object clone() {
        NameValueList nameValueList = new NameValueList();
        nameValueList.setSeparator(this.separator);
        Iterator<NameValue> iterator = this.hmap.values().iterator();
        while (iterator.hasNext()) {
            nameValueList.set((NameValue)iterator.next().clone());
        }
        return nameValueList;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.hmap.containsKey(object.toString().toLowerCase());
    }

    @Override
    public boolean containsValue(Object object) {
        return this.hmap.containsValue(object);
    }

    public boolean delete(String string) {
        if (this.hmap.containsKey(string = string.toLowerCase())) {
            this.hmap.remove(string);
            return true;
        }
        return false;
    }

    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    public StringBuffer encode(StringBuffer stringBuffer) {
        Iterator<NameValue> iterator;
        if (!this.hmap.isEmpty() && (iterator = this.hmap.values().iterator()).hasNext()) {
            do {
                NameValue nameValue;
                if ((nameValue = iterator.next()) instanceof GenericObject) {
                    ((GenericObject)nameValue).encode(stringBuffer);
                } else {
                    stringBuffer.append(nameValue.toString());
                }
                if (!iterator.hasNext()) break;
                stringBuffer.append(this.separator);
            } while (true);
        }
        return stringBuffer;
    }

    @Override
    public Set<Map.Entry<String, NameValue>> entrySet() {
        return this.hmap.entrySet();
    }

    @Override
    public boolean equals(Object iterator) {
        if (iterator == null) {
            return false;
        }
        if (!iterator.getClass().equals(this.getClass())) {
            return false;
        }
        NameValueList nameValueList = (NameValueList)((Object)iterator);
        if (this.hmap.size() != nameValueList.hmap.size()) {
            return false;
        }
        for (String string : this.hmap.keySet()) {
            NameValue nameValue = this.getNameValue(string);
            NameValue object = nameValueList.hmap.get(string);
            if (object == null) {
                return false;
            }
            if (object.equals(nameValue)) continue;
            return false;
        }
        return true;
    }

    @Override
    public NameValue get(Object object) {
        return this.hmap.get(object.toString().toLowerCase());
    }

    public NameValue getNameValue(String string) {
        return this.hmap.get(string.toLowerCase());
    }

    public Iterator<String> getNames() {
        return this.hmap.keySet().iterator();
    }

    public String getParameter(String object) {
        if ((object = this.getValue((String)object)) == null) {
            return null;
        }
        if (object instanceof GenericObject) {
            return ((GenericObject)object).encode();
        }
        return object.toString();
    }

    public Object getValue(String object) {
        if ((object = this.getNameValue(((String)object).toLowerCase())) != null) {
            return ((NameValue)object).getValueAsObject();
        }
        return null;
    }

    public boolean hasNameValue(String string) {
        return this.hmap.containsKey(string.toLowerCase());
    }

    @Override
    public int hashCode() {
        return this.hmap.keySet().hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.hmap.isEmpty();
    }

    public Iterator<NameValue> iterator() {
        return this.hmap.values().iterator();
    }

    @Override
    public Set<String> keySet() {
        return this.hmap.keySet();
    }

    @Override
    public NameValue put(String string, NameValue nameValue) {
        return this.hmap.put(string, nameValue);
    }

    @Override
    public void putAll(Map<? extends String, ? extends NameValue> map) {
        this.hmap.putAll(map);
    }

    @Override
    public NameValue remove(Object object) {
        return this.hmap.remove(object.toString().toLowerCase());
    }

    public void set(NameValue nameValue) {
        this.hmap.put(nameValue.getName().toLowerCase(), nameValue);
    }

    public void set(String string, Object object) {
        object = new NameValue(string, object);
        this.hmap.put(string.toLowerCase(), (NameValue)object);
    }

    public void setSeparator(String string) {
        this.separator = string;
    }

    @Override
    public int size() {
        return this.hmap.size();
    }

    public String toString() {
        return this.encode();
    }

    @Override
    public Collection<NameValue> values() {
        return this.hmap.values();
    }
}

