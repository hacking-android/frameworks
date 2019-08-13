/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.SIPObject;

public class Credentials
extends SIPObject {
    private static String CNONCE;
    private static String DOMAIN;
    private static String NONCE;
    private static String OPAQUE;
    private static String REALM;
    private static String RESPONSE;
    private static String URI;
    private static String USERNAME;
    private static final long serialVersionUID = -6335592791505451524L;
    protected NameValueList parameters = new NameValueList();
    protected String scheme;

    static {
        DOMAIN = "domain";
        REALM = "realm";
        OPAQUE = "opaque";
        RESPONSE = "response";
        URI = "uri";
        NONCE = "nonce";
        CNONCE = "cnonce";
        USERNAME = "username";
    }

    public Credentials() {
        this.parameters.setSeparator(",");
    }

    @Override
    public Object clone() {
        Credentials credentials = (Credentials)super.clone();
        NameValueList nameValueList = this.parameters;
        if (nameValueList != null) {
            credentials.parameters = (NameValueList)nameValueList.clone();
        }
        return credentials;
    }

    @Override
    public String encode() {
        String string = this.scheme;
        CharSequence charSequence = string;
        if (!this.parameters.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(this.parameters.encode());
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    public NameValueList getCredentials() {
        return this.parameters;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setCredentials(NameValueList nameValueList) {
        this.parameters = nameValueList;
    }

    public void setScheme(String string) {
        this.scheme = string;
    }
}

