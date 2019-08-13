/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.DuplicateNameValueList;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.SIPHeader;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Iterator;
import javax.sip.header.Parameters;

public abstract class ParametersHeader
extends SIPHeader
implements Parameters,
Serializable {
    protected DuplicateNameValueList duplicates;
    protected NameValueList parameters;

    protected ParametersHeader() {
        this.parameters = new NameValueList();
        this.duplicates = new DuplicateNameValueList();
    }

    protected ParametersHeader(String string) {
        super(string);
        this.parameters = new NameValueList();
        this.duplicates = new DuplicateNameValueList();
    }

    protected ParametersHeader(String string, boolean bl) {
        super(string);
        this.parameters = new NameValueList(bl);
        this.duplicates = new DuplicateNameValueList();
    }

    @Override
    public Object clone() {
        ParametersHeader parametersHeader = (ParametersHeader)super.clone();
        NameValueList nameValueList = this.parameters;
        if (nameValueList != null) {
            parametersHeader.parameters = (NameValueList)nameValueList.clone();
        }
        return parametersHeader;
    }

    @Override
    protected abstract String encodeBody();

    protected final boolean equalParameters(Parameters parameters) {
        Object object;
        String string;
        boolean bl;
        boolean bl2;
        if (this == parameters) {
            return true;
        }
        Object object2 = this.getParameterNames();
        while (object2.hasNext()) {
            string = object2.next();
            object = this.getParameter(string);
            bl = object == null;
            if (bl ^ (bl2 = (string = parameters.getParameter(string)) == null)) {
                return false;
            }
            if (object == null || ((String)object).equalsIgnoreCase(string)) continue;
            return false;
        }
        object = parameters.getParameterNames();
        while (object.hasNext()) {
            string = (String)object.next();
            object2 = parameters.getParameter(string);
            bl = object2 == null;
            if (bl ^ (bl2 = (string = this.getParameter(string)) == null)) {
                return false;
            }
            if (object2 == null || ((String)object2).equalsIgnoreCase(string)) continue;
            return false;
        }
        return true;
    }

    public String getMultiParameter(String string) {
        return this.duplicates.getParameter(string);
    }

    public Iterator<String> getMultiParameterNames() {
        return this.duplicates.getNames();
    }

    public Object getMultiParameterValue(String string) {
        return this.duplicates.getValue(string);
    }

    public DuplicateNameValueList getMultiParameters() {
        return this.duplicates;
    }

    public NameValue getNameValue(String string) {
        return this.parameters.getNameValue(string);
    }

    @Override
    public String getParameter(String string) {
        return this.parameters.getParameter(string);
    }

    protected boolean getParameterAsBoolean(String object) {
        if ((object = this.getParameterValue((String)object)) == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        if (object instanceof String) {
            return Boolean.valueOf((String)object);
        }
        return false;
    }

    protected float getParameterAsFloat(String string) {
        if (this.getParameterValue(string) != null) {
            try {
                if (this.getParameterValue(string) instanceof String) {
                    return Float.parseFloat(this.getParameter(string));
                }
                float f = ((Float)this.getParameterValue(string)).floatValue();
                return f;
            }
            catch (NumberFormatException numberFormatException) {
                return -1.0f;
            }
        }
        return -1.0f;
    }

    protected int getParameterAsHexInt(String string) {
        if (this.getParameterValue(string) != null) {
            try {
                if (this.getParameterValue(string) instanceof String) {
                    return Integer.parseInt(this.getParameter(string), 16);
                }
                int n = (Integer)this.getParameterValue(string);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                return -1;
            }
        }
        return -1;
    }

    protected int getParameterAsInt(String string) {
        if (this.getParameterValue(string) != null) {
            try {
                if (this.getParameterValue(string) instanceof String) {
                    return Integer.parseInt(this.getParameter(string));
                }
                int n = (Integer)this.getParameterValue(string);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                return -1;
            }
        }
        return -1;
    }

    protected long getParameterAsLong(String string) {
        if (this.getParameterValue(string) != null) {
            try {
                if (this.getParameterValue(string) instanceof String) {
                    return Long.parseLong(this.getParameter(string));
                }
                long l = (Long)this.getParameterValue(string);
                return l;
            }
            catch (NumberFormatException numberFormatException) {
                return -1L;
            }
        }
        return -1L;
    }

    protected GenericURI getParameterAsURI(String object) {
        if ((object = this.getParameterValue((String)object)) instanceof GenericURI) {
            return (GenericURI)object;
        }
        try {
            object = new GenericURI((String)object);
            return object;
        }
        catch (ParseException parseException) {
            return null;
        }
    }

    @Override
    public Iterator<String> getParameterNames() {
        return this.parameters.getNames();
    }

    public Object getParameterValue(String string) {
        return this.parameters.getValue(string);
    }

    public NameValueList getParameters() {
        return this.parameters;
    }

    public boolean hasMultiParameter(String string) {
        return this.duplicates.hasNameValue(string);
    }

    public boolean hasMultiParameters() {
        DuplicateNameValueList duplicateNameValueList = this.duplicates;
        boolean bl = duplicateNameValueList != null && !duplicateNameValueList.isEmpty();
        return bl;
    }

    public boolean hasParameter(String string) {
        return this.parameters.hasNameValue(string);
    }

    public boolean hasParameters() {
        NameValueList nameValueList = this.parameters;
        boolean bl = nameValueList != null && !nameValueList.isEmpty();
        return bl;
    }

    public void removeMultiParameter(String string) {
        this.duplicates.delete(string);
    }

    public void removeMultiParameters() {
        this.duplicates = new DuplicateNameValueList();
    }

    @Override
    public void removeParameter(String string) {
        this.parameters.delete(string);
    }

    public void removeParameters() {
        this.parameters = new NameValueList();
    }

    public void setMultiParameter(NameValue nameValue) {
        this.duplicates.set(nameValue);
    }

    public void setMultiParameter(String string, String string2) {
        NameValue nameValue = new NameValue();
        nameValue.setName(string);
        nameValue.setValue(string2);
        this.duplicates.set(nameValue);
    }

    public void setParameter(NameValue nameValue) {
        this.parameters.set(nameValue);
    }

    protected void setParameter(String object, float f) {
        Float f2 = Float.valueOf(f);
        NameValue nameValue = this.parameters.getNameValue((String)object);
        if (nameValue != null) {
            nameValue.setValueAsObject(f2);
        } else {
            object = new NameValue((String)object, f2);
            this.parameters.set((NameValue)object);
        }
    }

    protected void setParameter(String string, int n) {
        this.parameters.set(string, n);
    }

    protected void setParameter(String string, Object object) {
        this.parameters.set(string, object);
    }

    @Override
    public void setParameter(String object, String string) throws ParseException {
        NameValue nameValue = this.parameters.getNameValue((String)object);
        if (nameValue != null) {
            nameValue.setValueAsObject(string);
        } else {
            object = new NameValue((String)object, string);
            this.parameters.set((NameValue)object);
        }
    }

    protected void setParameter(String string, boolean bl) {
        this.parameters.set(string, bl);
    }

    public void setParameters(NameValueList nameValueList) {
        this.parameters = nameValueList;
    }

    public void setQuotedParameter(String object, String string) throws ParseException {
        NameValue nameValue = this.parameters.getNameValue((String)object);
        if (nameValue != null) {
            nameValue.setValueAsObject(string);
            nameValue.setQuotedValue();
        } else {
            object = new NameValue((String)object, string);
            ((NameValue)object).setQuotedValue();
            this.parameters.set((NameValue)object);
        }
    }
}

