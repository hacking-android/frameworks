/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ims.PServedUserHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.address.Address;
import javax.sip.header.ExtensionHeader;

public class PServedUser
extends AddressParametersHeader
implements PServedUserHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    public PServedUser() {
        super("P-Served-User");
    }

    public PServedUser(AddressImpl addressImpl) {
        super("P-Served-User");
        this.address = addressImpl;
    }

    @Override
    public Object clone() {
        return (PServedUser)super.clone();
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.address.encode());
        if (this.parameters.containsKey("regstate")) {
            stringBuffer.append(";");
            stringBuffer.append("regstate");
            stringBuffer.append("=");
            stringBuffer.append(this.getRegistrationState());
        }
        if (this.parameters.containsKey("sescase")) {
            stringBuffer.append(";");
            stringBuffer.append("sescase");
            stringBuffer.append("=");
            stringBuffer.append(this.getSessionCase());
        }
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PServedUser) {
            PServedUserHeader pServedUserHeader = (PServedUserHeader)object;
            return this.getAddress().equals(((PServedUser)object).getAddress());
        }
        return false;
    }

    @Override
    public String getRegistrationState() {
        return this.getParameter("regstate");
    }

    @Override
    public String getSessionCase() {
        return this.getParameter("sescase");
    }

    @Override
    public void setRegistrationState(String object) {
        if (object != null) {
            if (!((String)object).equals("reg") && !((String)object).equals("unreg")) {
                try {
                    object = new InvalidArgumentException("Value can be either reg or unreg");
                    throw object;
                }
                catch (InvalidArgumentException invalidArgumentException) {
                    invalidArgumentException.printStackTrace();
                }
            } else {
                try {
                    this.setParameter("regstate", (String)object);
                }
                catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
            return;
        }
        throw new NullPointerException("regstate Parameter value is null");
    }

    @Override
    public void setSessionCase(String object) {
        if (object != null) {
            if (!((String)object).equals("orig") && !((String)object).equals("term")) {
                try {
                    object = new InvalidArgumentException("Value can be either orig or term");
                    throw object;
                }
                catch (InvalidArgumentException invalidArgumentException) {
                    invalidArgumentException.printStackTrace();
                }
            } else {
                try {
                    this.setParameter("sescase", (String)object);
                }
                catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
            return;
        }
        throw new NullPointerException("sess-case Parameter value is null");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

