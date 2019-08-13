/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.TelephoneNumber;
import java.text.ParseException;
import java.util.Iterator;
import javax.sip.address.TelURL;

public class TelURLImpl
extends GenericURI
implements TelURL {
    private static final long serialVersionUID = 5873527320305915954L;
    protected TelephoneNumber telephoneNumber;

    public TelURLImpl() {
        this.scheme = "tel";
    }

    @Override
    public Object clone() {
        TelURLImpl telURLImpl = (TelURLImpl)super.clone();
        TelephoneNumber telephoneNumber = this.telephoneNumber;
        if (telephoneNumber != null) {
            telURLImpl.telephoneNumber = (TelephoneNumber)telephoneNumber.clone();
        }
        return telURLImpl;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.scheme);
        stringBuffer.append(':');
        this.telephoneNumber.encode(stringBuffer);
        return stringBuffer;
    }

    @Override
    public String getIsdnSubAddress() {
        return this.telephoneNumber.getIsdnSubaddress();
    }

    @Override
    public String getParameter(String string) {
        return this.telephoneNumber.getParameter(string);
    }

    @Override
    public Iterator<String> getParameterNames() {
        return this.telephoneNumber.getParameterNames();
    }

    public NameValueList getParameters() {
        return this.telephoneNumber.getParameters();
    }

    @Override
    public String getPhoneContext() {
        return this.getParameter("phone-context");
    }

    @Override
    public String getPhoneNumber() {
        return this.telephoneNumber.getPhoneNumber();
    }

    @Override
    public String getPostDial() {
        return this.telephoneNumber.getPostDial();
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public boolean isGlobal() {
        return this.telephoneNumber.isGlobal();
    }

    @Override
    public boolean isSipURI() {
        return false;
    }

    @Override
    public void removeParameter(String string) {
        this.telephoneNumber.removeParameter(string);
    }

    @Override
    public void setGlobal(boolean bl) {
        this.telephoneNumber.setGlobal(bl);
    }

    @Override
    public void setIsdnSubAddress(String string) {
        this.telephoneNumber.setIsdnSubaddress(string);
    }

    @Override
    public void setParameter(String string, String string2) {
        this.telephoneNumber.setParameter(string, string2);
    }

    @Override
    public void setPhoneContext(String string) throws ParseException {
        if (string == null) {
            this.removeParameter("phone-context");
        } else {
            this.setParameter("phone-context", string);
        }
    }

    @Override
    public void setPhoneNumber(String string) {
        this.telephoneNumber.setPhoneNumber(string);
    }

    @Override
    public void setPostDial(String string) {
        this.telephoneNumber.setPostDial(string);
    }

    public void setTelephoneNumber(TelephoneNumber telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.scheme);
        stringBuilder.append(":");
        stringBuilder.append(this.telephoneNumber.encode());
        return stringBuilder.toString();
    }
}

