/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.javax.sip.address.NetObject;
import gov.nist.javax.sip.address.SipUri;
import java.text.ParseException;
import javax.sip.address.URI;

public class GenericURI
extends NetObject
implements URI {
    public static final String ISUB = "isub";
    public static final String PHONE_CONTEXT_TAG = "context-tag";
    public static final String POSTDIAL = "postdial";
    public static final String PROVIDER_TAG = "provider-tag";
    public static final String SIP = "sip";
    public static final String SIPS = "sips";
    public static final String TEL = "tel";
    private static final long serialVersionUID = 3237685256878068790L;
    protected String scheme;
    protected String uriString;

    protected GenericURI() {
    }

    public GenericURI(String string) throws ParseException {
        try {
            this.uriString = string;
            this.scheme = string.substring(0, string.indexOf(":"));
            return;
        }
        catch (Exception exception) {
            throw new ParseException("GenericURI, Bad URI format", 0);
        }
    }

    @Override
    public String encode() {
        return this.uriString;
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.uriString);
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof URI) {
            object = (URI)object;
            return this.toString().equalsIgnoreCase(object.toString());
        }
        return false;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean isSipURI() {
        return this instanceof SipUri;
    }

    @Override
    public String toString() {
        return this.encode();
    }
}

