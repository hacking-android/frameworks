/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.ims.SecurityAgreeHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Parameters;

public abstract class SecurityAgree
extends ParametersHeader {
    private String secMechanism;

    public SecurityAgree() {
        this.parameters.setSeparator(";");
    }

    public SecurityAgree(String string) {
        super(string);
        this.parameters.setSeparator(";");
    }

    @Override
    public Object clone() {
        SecurityAgree securityAgree = (SecurityAgree)super.clone();
        String string = this.secMechanism;
        if (string != null) {
            securityAgree.secMechanism = string;
        }
        return securityAgree;
    }

    @Override
    public String encodeBody() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.secMechanism);
        stringBuilder.append(";");
        stringBuilder.append(" ");
        stringBuilder.append(this.parameters.encode());
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof SecurityAgreeHeader;
        boolean bl2 = false;
        if (bl) {
            object = (SecurityAgreeHeader)object;
            if (this.getSecurityMechanism().equals(object.getSecurityMechanism()) && this.equalParameters((Parameters)object)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public String getAlgorithm() {
        return this.getParameter("alg");
    }

    public String getEncryptionAlgorithm() {
        return this.getParameter("ealg");
    }

    public String getMode() {
        return this.getParameter("mod");
    }

    public int getPortClient() {
        return Integer.parseInt(this.getParameter("port-c"));
    }

    public int getPortServer() {
        return Integer.parseInt(this.getParameter("port-s"));
    }

    public float getPreference() {
        return Float.parseFloat(this.getParameter("q"));
    }

    public String getProtocol() {
        return this.getParameter("prot");
    }

    public int getSPIClient() {
        return Integer.parseInt(this.getParameter("spi-c"));
    }

    public int getSPIServer() {
        return Integer.parseInt(this.getParameter("spi-s"));
    }

    public String getSecurityMechanism() {
        return this.secMechanism;
    }

    public void setAlgorithm(String string) throws ParseException {
        if (string != null) {
            this.setParameter("alg", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, SecurityClient, setAlgorithm(), the algorithm parameter is null");
    }

    public void setEncryptionAlgorithm(String string) throws ParseException {
        if (string != null) {
            this.setParameter("ealg", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, SecurityClient, setEncryptionAlgorithm(), the encryption-algorithm parameter is null");
    }

    public void setMode(String string) throws ParseException {
        if (string != null) {
            this.setParameter("mod", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, SecurityClient, setMode(), the mode parameter is null");
    }

    @Override
    public void setParameter(String charSequence, String string) throws ParseException {
        if (string != null) {
            NameValue nameValue = this.parameters.getNameValue(((String)charSequence).toLowerCase());
            if (nameValue == null) {
                nameValue = new NameValue((String)charSequence, string);
                if (((String)charSequence).equalsIgnoreCase("d-ver")) {
                    nameValue.setQuotedValue();
                    if (string.startsWith("\"")) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(string);
                        ((StringBuilder)charSequence).append(" : Unexpected DOUBLE_QUOTE");
                        throw new ParseException(((StringBuilder)charSequence).toString(), 0);
                    }
                }
                super.setParameter(nameValue);
            } else {
                nameValue.setValueAsObject(string);
            }
            return;
        }
        throw new NullPointerException("null value");
    }

    public void setPortClient(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.setParameter("port-c", n);
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, SecurityClient, setPortClient(), the port-c parameter is <0");
    }

    public void setPortServer(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.setParameter("port-s", n);
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, SecurityClient, setPortServer(), the port-s parameter is <0");
    }

    public void setPreference(float f) throws InvalidArgumentException {
        if (!(f < 0.0f)) {
            this.setParameter("q", f);
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, SecurityClient, setPreference(), the preference (q) parameter is <0");
    }

    public void setProtocol(String string) throws ParseException {
        if (string != null) {
            this.setParameter("prot", string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, SecurityClient, setProtocol(), the protocol parameter is null");
    }

    public void setSPIClient(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.setParameter("spi-c", n);
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, SecurityClient, setSPIClient(), the spi-c parameter is <0");
    }

    public void setSPIServer(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.setParameter("spi-s", n);
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, SecurityClient, setSPIServer(), the spi-s parameter is <0");
    }

    public void setSecurityMechanism(String string) throws ParseException {
        if (string != null) {
            this.secMechanism = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, SecurityAgree, setSecurityMechanism(), the sec-mechanism parameter is null");
    }
}

