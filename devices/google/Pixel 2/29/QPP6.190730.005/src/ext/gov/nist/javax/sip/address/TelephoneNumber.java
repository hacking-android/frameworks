/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.core.GenericObject;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.NetObject;
import java.util.Iterator;

public class TelephoneNumber
extends NetObject {
    public static final String ISUB = "isub";
    public static final String PHONE_CONTEXT_TAG = "context-tag";
    public static final String POSTDIAL = "postdial";
    public static final String PROVIDER_TAG = "provider-tag";
    protected boolean isglobal;
    protected NameValueList parameters = new NameValueList();
    protected String phoneNumber;

    @Override
    public Object clone() {
        TelephoneNumber telephoneNumber = (TelephoneNumber)super.clone();
        NameValueList nameValueList = this.parameters;
        if (nameValueList != null) {
            telephoneNumber.parameters = (NameValueList)nameValueList.clone();
        }
        return telephoneNumber;
    }

    public void deleteParm(String string) {
        this.parameters.delete(string);
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        if (this.isglobal) {
            stringBuffer.append('+');
        }
        stringBuffer.append(this.phoneNumber);
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        return stringBuffer;
    }

    public String getIsdnSubaddress() {
        return (String)this.parameters.getValue(ISUB);
    }

    public String getParameter(String object) {
        if ((object = this.parameters.getValue((String)object)) == null) {
            return null;
        }
        if (object instanceof GenericObject) {
            return ((GenericObject)object).encode();
        }
        return object.toString();
    }

    public Iterator<String> getParameterNames() {
        return this.parameters.getNames();
    }

    public NameValueList getParameters() {
        return this.parameters;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getPostDial() {
        return (String)this.parameters.getValue(POSTDIAL);
    }

    public boolean hasIsdnSubaddress() {
        return this.hasParm(ISUB);
    }

    public boolean hasParm(String string) {
        return this.parameters.hasNameValue(string);
    }

    public boolean hasPostDial() {
        boolean bl = this.parameters.getValue(POSTDIAL) != null;
        return bl;
    }

    public boolean isGlobal() {
        return this.isglobal;
    }

    public void removeIsdnSubaddress() {
        this.deleteParm(ISUB);
    }

    public void removeParameter(String string) {
        this.parameters.delete(string);
    }

    public void removePostDial() {
        this.parameters.delete(POSTDIAL);
    }

    public void setGlobal(boolean bl) {
        this.isglobal = bl;
    }

    public void setIsdnSubaddress(String string) {
        this.setParm(ISUB, string);
    }

    public void setParameter(String object, String string) {
        object = new NameValue((String)object, string);
        this.parameters.set((NameValue)object);
    }

    public void setParameters(NameValueList nameValueList) {
        this.parameters = nameValueList;
    }

    public void setParm(String object, Object object2) {
        object = new NameValue((String)object, object2);
        this.parameters.set((NameValue)object);
    }

    public void setPhoneNumber(String string) {
        this.phoneNumber = string;
    }

    public void setPostDial(String object) {
        object = new NameValue(POSTDIAL, object);
        this.parameters.set((NameValue)object);
    }
}

