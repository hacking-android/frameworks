/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.SIPObject;

public class Challenge
extends SIPObject {
    private static String ALGORITHM;
    private static String DOMAIN;
    private static String OPAQUE;
    private static String QOP;
    private static String REALM;
    private static String RESPONSE;
    private static String SIGNATURE;
    private static String SIGNED_BY;
    private static String STALE;
    private static String URI;
    private static final long serialVersionUID = 5944455875924336L;
    protected NameValueList authParams = new NameValueList();
    protected String scheme;

    static {
        DOMAIN = "domain";
        REALM = "realm";
        OPAQUE = "opaque";
        ALGORITHM = "algorithm";
        QOP = "qop";
        STALE = "stale";
        SIGNATURE = "signature";
        RESPONSE = "response";
        SIGNED_BY = "signed-by";
        URI = "uri";
    }

    public Challenge() {
        this.authParams.setSeparator(",");
    }

    @Override
    public Object clone() {
        Challenge challenge = (Challenge)super.clone();
        NameValueList nameValueList = this.authParams;
        if (nameValueList != null) {
            challenge.authParams = (NameValueList)nameValueList.clone();
        }
        return challenge;
    }

    @Override
    public String encode() {
        StringBuffer stringBuffer = new StringBuffer(this.scheme);
        stringBuffer.append(" ");
        stringBuffer.append(this.authParams.encode());
        return stringBuffer.toString();
    }

    public String getAlgorithm() {
        return (String)this.authParams.getValue(ALGORITHM);
    }

    public NameValueList getAuthParams() {
        return this.authParams;
    }

    public String getDomain() {
        return (String)this.authParams.getValue(DOMAIN);
    }

    public String getOpaque() {
        return (String)this.authParams.getValue(OPAQUE);
    }

    public String getParameter(String string) {
        return (String)this.authParams.getValue(string);
    }

    public String getQOP() {
        return (String)this.authParams.getValue(QOP);
    }

    public String getRealm() {
        return (String)this.authParams.getValue(REALM);
    }

    public String getResponse() {
        return (String)this.authParams.getValue(RESPONSE);
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getSignature() {
        return (String)this.authParams.getValue(SIGNATURE);
    }

    public String getSignedBy() {
        return (String)this.authParams.getValue(SIGNED_BY);
    }

    public String getStale() {
        return (String)this.authParams.getValue(STALE);
    }

    public String getURI() {
        return (String)this.authParams.getValue(URI);
    }

    public boolean hasParameter(String string) {
        boolean bl = this.authParams.getNameValue(string) != null;
        return bl;
    }

    public boolean hasParameters() {
        boolean bl = this.authParams.size() != 0;
        return bl;
    }

    public boolean removeParameter(String string) {
        return this.authParams.delete(string);
    }

    public void removeParameters() {
        this.authParams = new NameValueList();
    }

    public void setAuthParams(NameValueList nameValueList) {
        this.authParams = nameValueList;
    }

    public void setParameter(NameValue nameValue) {
        this.authParams.set(nameValue);
    }

    public void setScheme(String string) {
        this.scheme = string;
    }
}

