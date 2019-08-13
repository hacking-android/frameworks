/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.GenericObject;
import gov.nist.core.GenericObjectList;
import java.util.Map;

public class NameValue
extends GenericObject
implements Map.Entry<String, String> {
    private static final long serialVersionUID = -1857729012596437950L;
    protected final boolean isFlagParameter;
    protected boolean isQuotedString;
    private String name;
    private String quotes;
    private String separator;
    private Object value;

    public NameValue() {
        this.name = null;
        this.value = "";
        this.separator = "=";
        this.quotes = "";
        this.isFlagParameter = false;
    }

    public NameValue(String string, Object object) {
        this(string, object, false);
    }

    public NameValue(String string, Object object, boolean bl) {
        this.name = string;
        this.value = object;
        this.separator = "=";
        this.quotes = "";
        this.isFlagParameter = bl;
    }

    @Override
    public Object clone() {
        NameValue nameValue = (NameValue)super.clone();
        Object object = this.value;
        if (object != null) {
            nameValue.value = NameValue.makeClone(object);
        }
        return nameValue;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        Object object;
        if (this.name != null && (object = this.value) != null && !this.isFlagParameter) {
            if (GenericObject.isMySubclass(object.getClass())) {
                object = (GenericObject)this.value;
                stringBuffer.append(this.name);
                stringBuffer.append(this.separator);
                stringBuffer.append(this.quotes);
                ((GenericObject)object).encode(stringBuffer);
                stringBuffer.append(this.quotes);
                return stringBuffer;
            }
            if (GenericObjectList.isMySubclass(this.value.getClass())) {
                object = (GenericObjectList)this.value;
                stringBuffer.append(this.name);
                stringBuffer.append(this.separator);
                stringBuffer.append(((GenericObjectList)object).encode());
                return stringBuffer;
            }
            if (this.value.toString().length() == 0) {
                if (this.isQuotedString) {
                    stringBuffer.append(this.name);
                    stringBuffer.append(this.separator);
                    stringBuffer.append(this.quotes);
                    stringBuffer.append(this.quotes);
                    return stringBuffer;
                }
                stringBuffer.append(this.name);
                stringBuffer.append(this.separator);
                return stringBuffer;
            }
            stringBuffer.append(this.name);
            stringBuffer.append(this.separator);
            stringBuffer.append(this.quotes);
            stringBuffer.append(this.value.toString());
            stringBuffer.append(this.quotes);
            return stringBuffer;
        }
        if (this.name == null && (object = this.value) != null) {
            if (GenericObject.isMySubclass(object.getClass())) {
                ((GenericObject)this.value).encode(stringBuffer);
                return stringBuffer;
            }
            if (GenericObjectList.isMySubclass(this.value.getClass())) {
                stringBuffer.append(((GenericObjectList)this.value).encode());
                return stringBuffer;
            }
            stringBuffer.append(this.quotes);
            stringBuffer.append(this.value.toString());
            stringBuffer.append(this.quotes);
            return stringBuffer;
        }
        if (this.name != null && (this.value == null || this.isFlagParameter)) {
            stringBuffer.append(this.name);
            return stringBuffer;
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        Object object2;
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (!object.getClass().equals(this.getClass())) {
            return false;
        }
        if (this == (object = (NameValue)object)) {
            return true;
        }
        if (this.name == null && ((NameValue)object).name != null || this.name != null && ((NameValue)object).name == null) {
            return false;
        }
        String string = this.name;
        if (string != null && (object2 = ((NameValue)object).name) != null && string.compareToIgnoreCase((String)object2) != 0) {
            return false;
        }
        if (this.value != null && ((NameValue)object).value == null || this.value == null && ((NameValue)object).value != null) {
            return false;
        }
        object2 = this.value;
        object = ((NameValue)object).value;
        if (object2 == object) {
            return true;
        }
        if (object2 instanceof String) {
            if (this.isQuotedString) {
                return object2.equals(object);
            }
            if (((String)object2).compareToIgnoreCase((String)object) == 0) {
                bl = true;
            }
            return bl;
        }
        return object2.equals(object);
    }

    @Override
    public String getKey() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        Object object = this.value;
        object = object == null ? null : object.toString();
        return object;
    }

    public Object getValueAsObject() {
        Object object = this.isFlagParameter ? "" : this.value;
        return object;
    }

    @Override
    public int hashCode() {
        return this.encode().toLowerCase().hashCode();
    }

    public boolean isValueQuoted() {
        return this.isQuotedString;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void setQuotedValue() {
        this.isQuotedString = true;
        this.quotes = "\"";
    }

    public void setSeparator(String string) {
        this.separator = string;
    }

    @Override
    public String setValue(String string) {
        String string2 = this.value == null ? null : string;
        this.value = string;
        return string2;
    }

    public void setValueAsObject(Object object) {
        this.value = object;
    }
}

