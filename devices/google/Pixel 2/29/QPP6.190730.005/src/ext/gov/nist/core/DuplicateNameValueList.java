/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.GenericObject;
import gov.nist.core.MultiValueMapImpl;
import gov.nist.core.NameValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DuplicateNameValueList
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -5611332957903796952L;
    private MultiValueMapImpl<NameValue> nameValueMap = new MultiValueMapImpl();
    private String separator = ";";

    public void clear() {
        this.nameValueMap.clear();
    }

    public Object clone() {
        DuplicateNameValueList duplicateNameValueList = new DuplicateNameValueList();
        duplicateNameValueList.setSeparator(this.separator);
        Iterator iterator = this.nameValueMap.values().iterator();
        while (iterator.hasNext()) {
            duplicateNameValueList.set((NameValue)((NameValue)iterator.next()).clone());
        }
        return duplicateNameValueList;
    }

    public boolean delete(String string) {
        if (this.nameValueMap.containsKey(string = string.toLowerCase())) {
            this.nameValueMap.remove(string);
            return true;
        }
        return false;
    }

    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    public StringBuffer encode(StringBuffer stringBuffer) {
        Iterator iterator;
        if (!this.nameValueMap.isEmpty() && (iterator = this.nameValueMap.values().iterator()).hasNext()) {
            do {
                Object e;
                if ((e = iterator.next()) instanceof GenericObject) {
                    ((GenericObject)e).encode(stringBuffer);
                } else {
                    stringBuffer.append(e.toString());
                }
                if (!iterator.hasNext()) break;
                stringBuffer.append(this.separator);
            } while (true);
        }
        return stringBuffer;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!object.getClass().equals(this.getClass())) {
            return false;
        }
        DuplicateNameValueList duplicateNameValueList = (DuplicateNameValueList)object;
        if (this.nameValueMap.size() != duplicateNameValueList.nameValueMap.size()) {
            return false;
        }
        for (String string : this.nameValueMap.keySet()) {
            object = this.getNameValue(string);
            Object object2 = duplicateNameValueList.nameValueMap.get(string);
            if (object2 == null) {
                return false;
            }
            if (object2.equals(object)) continue;
            return false;
        }
        return true;
    }

    public Collection getNameValue(String string) {
        return this.nameValueMap.get(string.toLowerCase());
    }

    public Iterator<String> getNames() {
        return this.nameValueMap.keySet().iterator();
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
            return object;
        }
        return null;
    }

    public boolean hasNameValue(String string) {
        return this.nameValueMap.containsKey(string.toLowerCase());
    }

    public int hashCode() {
        return this.nameValueMap.keySet().hashCode();
    }

    public boolean isEmpty() {
        return this.nameValueMap.isEmpty();
    }

    public Iterator<NameValue> iterator() {
        return this.nameValueMap.values().iterator();
    }

    public NameValue put(String string, NameValue nameValue) {
        return (NameValue)((Object)this.nameValueMap.put(string, nameValue));
    }

    public NameValue remove(Object object) {
        return (NameValue)this.nameValueMap.remove(object);
    }

    public void set(NameValue nameValue) {
        this.nameValueMap.put(nameValue.getName().toLowerCase(), nameValue);
    }

    public void set(String string, Object object) {
        object = new NameValue(string, object);
        this.nameValueMap.put(string.toLowerCase(), (NameValue)object);
    }

    public void setSeparator(String string) {
        this.separator = string;
    }

    public int size() {
        return this.nameValueMap.size();
    }

    public String toString() {
        return this.encode();
    }

    public Collection<NameValue> values() {
        return this.nameValueMap.values();
    }
}

