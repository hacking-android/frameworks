/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ContactList;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.address.Address;
import javax.sip.header.ContactHeader;

public final class Contact
extends AddressParametersHeader
implements ContactHeader {
    public static final String ACTION = "action";
    public static final String EXPIRES = "expires";
    public static final String PROXY = "proxy";
    public static final String Q = "q";
    public static final String REDIRECT = "redirect";
    private static final long serialVersionUID = 1677294871695706288L;
    private ContactList contactList;
    protected boolean wildCardFlag;

    public Contact() {
        super("Contact");
    }

    @Override
    public Object clone() {
        Contact contact = (Contact)super.clone();
        ContactList contactList = this.contactList;
        if (contactList != null) {
            contact.contactList = (ContactList)contactList.clone();
        }
        return contact;
    }

    @Override
    protected String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        if (this.wildCardFlag) {
            stringBuffer.append('*');
        } else {
            if (this.address.getAddressType() == 1) {
                this.address.encode(stringBuffer);
            } else {
                stringBuffer.append('<');
                this.address.encode(stringBuffer);
                stringBuffer.append('>');
            }
            if (!this.parameters.isEmpty()) {
                stringBuffer.append(";");
                this.parameters.encode(stringBuffer);
            }
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof ContactHeader && super.equals(object);
        return bl;
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    public ContactList getContactList() {
        return this.contactList;
    }

    public NameValueList getContactParms() {
        return this.parameters;
    }

    @Override
    public int getExpires() {
        return this.getParameterAsInt(EXPIRES);
    }

    public String getPubGruuParam() {
        return (String)this.parameters.getValue("pub-gruu");
    }

    @Override
    public float getQValue() {
        return this.getParameterAsFloat(Q);
    }

    public String getSipInstanceParam() {
        return (String)this.parameters.getValue("+sip.instance");
    }

    public String getTempGruuParam() {
        return (String)this.parameters.getValue("temp-gruu");
    }

    public boolean getWildCardFlag() {
        return this.wildCardFlag;
    }

    @Override
    public boolean isWildCard() {
        return this.address.isWildcard();
    }

    public void removePubGruuParam() {
        if (this.parameters != null) {
            this.parameters.delete("pub-gruu");
        }
    }

    public void removeSipInstanceParam() {
        if (this.parameters != null) {
            this.parameters.delete("+sip.instance");
        }
    }

    public void removeTempGruuParam() {
        if (this.parameters != null) {
            this.parameters.delete("temp-gruu");
        }
    }

    @Override
    public void setAddress(Address address) {
        if (address != null) {
            this.address = (AddressImpl)address;
            this.wildCardFlag = false;
            return;
        }
        throw new NullPointerException("null address");
    }

    public void setContactList(ContactList contactList) {
        this.contactList = contactList;
    }

    @Override
    public void setExpires(int n) {
        this.parameters.set(EXPIRES, n);
    }

    @Override
    public void setParameter(String string, String object) throws ParseException {
        NameValue nameValue = this.parameters.getNameValue(string);
        if (nameValue != null) {
            nameValue.setValueAsObject(object);
        } else {
            object = new NameValue(string, object);
            if (string.equalsIgnoreCase("methods")) {
                ((NameValue)object).setQuotedValue();
            }
            this.parameters.set((NameValue)object);
        }
    }

    public void setPubGruuParam(String string) {
        this.parameters.set("pub-gruu", string);
    }

    @Override
    public void setQValue(float f) throws InvalidArgumentException {
        if (f != -1.0f && (f < 0.0f || f > 1.0f)) {
            throw new InvalidArgumentException("JAIN-SIP Exception, Contact, setQValue(), the qValue is not between 0 and 1");
        }
        this.parameters.set(Q, Float.valueOf(f));
    }

    public void setSipInstanceParam(String string) {
        this.parameters.set("+sip.instance", string);
    }

    public void setTempGruuParam(String string) {
        this.parameters.set("temp-gruu", string);
    }

    @Override
    public void setWildCard() {
        this.setWildCardFlag(true);
    }

    @Override
    public void setWildCardFlag(boolean bl) {
        this.wildCardFlag = true;
        this.address = new AddressImpl();
        this.address.setWildCardFlag();
    }
}

