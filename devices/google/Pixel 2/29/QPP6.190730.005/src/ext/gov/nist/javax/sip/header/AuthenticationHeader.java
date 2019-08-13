/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.Challenge;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.ProxyAuthorization;
import java.text.ParseException;
import javax.sip.address.URI;

public abstract class AuthenticationHeader
extends ParametersHeader {
    public static final String ALGORITHM = "algorithm";
    public static final String CK = "ck";
    public static final String CNONCE = "cnonce";
    public static final String DOMAIN = "domain";
    public static final String IK = "ik";
    public static final String INTEGRITY_PROTECTED = "integrity-protected";
    public static final String NC = "nc";
    public static final String NONCE = "nonce";
    public static final String OPAQUE = "opaque";
    public static final String QOP = "qop";
    public static final String REALM = "realm";
    public static final String RESPONSE = "response";
    public static final String SIGNATURE = "signature";
    public static final String SIGNED_BY = "signed-by";
    public static final String STALE = "stale";
    public static final String URI = "uri";
    public static final String USERNAME = "username";
    protected String scheme;

    public AuthenticationHeader() {
        this.parameters.setSeparator(",");
    }

    public AuthenticationHeader(String string) {
        super(string);
        this.parameters.setSeparator(",");
        this.scheme = "Digest";
    }

    @Override
    public String encodeBody() {
        this.parameters.setSeparator(",");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.scheme);
        stringBuilder.append(" ");
        stringBuilder.append(this.parameters.encode());
        return stringBuilder.toString();
    }

    public String getAlgorithm() {
        return this.getParameter(ALGORITHM);
    }

    public String getCK() {
        return this.getParameter(CK);
    }

    public String getCNonce() {
        return this.getParameter(CNONCE);
    }

    public String getDomain() {
        return this.getParameter(DOMAIN);
    }

    public String getIK() {
        return this.getParameter(IK);
    }

    public String getIntegrityProtected() {
        return this.getParameter(INTEGRITY_PROTECTED);
    }

    public String getNonce() {
        return this.getParameter(NONCE);
    }

    public int getNonceCount() {
        return this.getParameterAsHexInt(NC);
    }

    public String getOpaque() {
        return this.getParameter(OPAQUE);
    }

    public String getQop() {
        return this.getParameter(QOP);
    }

    public String getRealm() {
        return this.getParameter(REALM);
    }

    public String getResponse() {
        return (String)this.getParameterValue(RESPONSE);
    }

    public String getScheme() {
        return this.scheme;
    }

    public URI getURI() {
        return this.getParameterAsURI(URI);
    }

    public String getUsername() {
        return this.getParameter(USERNAME);
    }

    public boolean isStale() {
        return this.getParameterAsBoolean(STALE);
    }

    public void setAlgorithm(String string) throws ParseException {
        if (string != null) {
            this.setParameter(ALGORITHM, string);
            return;
        }
        throw new NullPointerException("null arg");
    }

    public void setCK(String string) throws ParseException {
        if (string != null) {
            this.setParameter(CK, string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,  AuthenticationHeader, setCk(), The auth-param CK parameter is null");
    }

    public void setCNonce(String string) throws ParseException {
        this.setParameter(CNONCE, string);
    }

    public void setChallenge(Challenge challenge) {
        this.scheme = challenge.scheme;
        this.parameters = challenge.authParams;
    }

    public void setDomain(String string) throws ParseException {
        if (string != null) {
            this.setParameter(DOMAIN, string);
            return;
        }
        throw new NullPointerException("null arg");
    }

    public void setIK(String string) throws ParseException {
        if (string != null) {
            this.setParameter(IK, string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,  AuthenticationHeader, setIk(), The auth-param IK parameter is null");
    }

    public void setIntegrityProtected(String string) throws ParseException {
        if (string != null) {
            this.setParameter(INTEGRITY_PROTECTED, string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,  AuthenticationHeader, setIntegrityProtected(), The integrity-protected parameter is null");
    }

    public void setNonce(String string) throws ParseException {
        if (string != null) {
            this.setParameter(NONCE, string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,  AuthenticationHeader, setNonce(), The nonce parameter is null");
    }

    public void setNonceCount(int n) throws ParseException {
        if (n >= 0) {
            String string = Integer.toHexString(n);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("00000000".substring(0, 8 - string.length()));
            stringBuilder.append(string);
            this.setParameter(NC, stringBuilder.toString());
            return;
        }
        throw new ParseException("bad value", 0);
    }

    public void setOpaque(String string) throws ParseException {
        if (string != null) {
            this.setParameter(OPAQUE, string);
            return;
        }
        throw new NullPointerException("null arg");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setParameter(String charSequence, String string) throws ParseException {
        NameValue nameValue = this.parameters.getNameValue(((String)charSequence).toLowerCase());
        if (nameValue != null) {
            nameValue.setValueAsObject(string);
            return;
        }
        nameValue = new NameValue((String)charSequence, string);
        if (((String)charSequence).equalsIgnoreCase(QOP) || ((String)charSequence).equalsIgnoreCase(REALM) || ((String)charSequence).equalsIgnoreCase(CNONCE) || ((String)charSequence).equalsIgnoreCase(NONCE) || ((String)charSequence).equalsIgnoreCase(USERNAME) || ((String)charSequence).equalsIgnoreCase(DOMAIN) || ((String)charSequence).equalsIgnoreCase(OPAQUE) || ((String)charSequence).equalsIgnoreCase("nextnonce") || ((String)charSequence).equalsIgnoreCase(URI) || ((String)charSequence).equalsIgnoreCase(RESPONSE) || ((String)charSequence).equalsIgnoreCase(IK) || ((String)charSequence).equalsIgnoreCase(CK) || ((String)charSequence).equalsIgnoreCase(INTEGRITY_PROTECTED)) {
            if (!(this instanceof Authorization) && !(this instanceof ProxyAuthorization) || !((String)charSequence).equalsIgnoreCase(QOP)) {
                nameValue.setQuotedValue();
            }
            if (string == null) throw new NullPointerException("null value");
            if (string.startsWith("\"")) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" : Unexpected DOUBLE_QUOTE");
                throw new ParseException(((StringBuilder)charSequence).toString(), 0);
            }
        }
        super.setParameter(nameValue);
    }

    public void setQop(String string) throws ParseException {
        if (string != null) {
            this.setParameter(QOP, string);
            return;
        }
        throw new NullPointerException("null arg");
    }

    public void setRealm(String string) throws ParseException {
        if (string != null) {
            this.setParameter(REALM, string);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception,  AuthenticationHeader, setRealm(), The realm parameter is null");
    }

    public void setResponse(String string) throws ParseException {
        if (string != null) {
            this.setParameter(RESPONSE, string);
            return;
        }
        throw new NullPointerException("Null parameter");
    }

    public void setScheme(String string) {
        this.scheme = string;
    }

    public void setStale(boolean bl) {
        this.setParameter(new NameValue(STALE, bl));
    }

    public void setURI(URI cloneable) {
        if (cloneable != null) {
            cloneable = new NameValue(URI, cloneable);
            ((NameValue)cloneable).setQuotedValue();
            this.parameters.set((NameValue)cloneable);
            return;
        }
        throw new NullPointerException("Null URI");
    }

    public void setUsername(String string) throws ParseException {
        this.setParameter(USERNAME, string);
    }
}

