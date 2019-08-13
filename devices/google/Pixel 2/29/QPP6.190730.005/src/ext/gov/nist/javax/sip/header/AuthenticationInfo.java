/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.header.AuthenticationInfoHeader;

public final class AuthenticationInfo
extends ParametersHeader
implements AuthenticationInfoHeader {
    private static final long serialVersionUID = -4371927900917127057L;

    public AuthenticationInfo() {
        super("Authentication-Info");
        this.parameters.setSeparator(",");
    }

    public void add(NameValue nameValue) {
        this.parameters.set(nameValue);
    }

    @Override
    protected String encodeBody() {
        return this.parameters.encode();
    }

    public NameValue getAuthInfo(String string) {
        return this.parameters.getNameValue(string);
    }

    public String getAuthenticationInfo() {
        return this.encodeBody();
    }

    @Override
    public String getCNonce() {
        return this.getParameter("cnonce");
    }

    @Override
    public String getNextNonce() {
        return this.getParameter("nextnonce");
    }

    @Override
    public int getNonceCount() {
        return this.getParameterAsInt("nc");
    }

    @Override
    public String getQop() {
        return this.getParameter("qop");
    }

    @Override
    public String getResponse() {
        return this.getParameter("rspauth");
    }

    @Override
    public void setCNonce(String string) throws ParseException {
        this.setParameter("cnonce", string);
    }

    @Override
    public void setNextNonce(String string) throws ParseException {
        this.setParameter("nextnonce", string);
    }

    @Override
    public void setNonceCount(int n) throws ParseException {
        if (n >= 0) {
            String string = Integer.toHexString(n);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("00000000".substring(0, 8 - string.length()));
            stringBuilder.append(string);
            this.setParameter("nc", stringBuilder.toString());
            return;
        }
        throw new ParseException("bad value", 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setParameter(String charSequence, String string) throws ParseException {
        if (charSequence == null) throw new NullPointerException("null name");
        NameValue nameValue = this.parameters.getNameValue(((String)charSequence).toLowerCase());
        if (nameValue != null) {
            nameValue.setValueAsObject(string);
            return;
        }
        nameValue = new NameValue((String)charSequence, string);
        if (((String)charSequence).equalsIgnoreCase("qop") || ((String)charSequence).equalsIgnoreCase("nextnonce") || ((String)charSequence).equalsIgnoreCase("realm") || ((String)charSequence).equalsIgnoreCase("cnonce") || ((String)charSequence).equalsIgnoreCase("nonce") || ((String)charSequence).equalsIgnoreCase("opaque") || ((String)charSequence).equalsIgnoreCase("username") || ((String)charSequence).equalsIgnoreCase("domain") || ((String)charSequence).equalsIgnoreCase("nextnonce") || ((String)charSequence).equalsIgnoreCase("rspauth")) {
            if (string == null) throw new NullPointerException("null value");
            if (string.startsWith("\"")) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" : Unexpected DOUBLE_QUOTE");
                throw new ParseException(((StringBuilder)charSequence).toString(), 0);
            }
            nameValue.setQuotedValue();
        }
        super.setParameter(nameValue);
    }

    @Override
    public void setQop(String string) throws ParseException {
        this.setParameter("qop", string);
    }

    @Override
    public void setResponse(String string) throws ParseException {
        this.setParameter("rspauth", string);
    }
}

