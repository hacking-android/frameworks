/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.SIPObject;
import gov.nist.javax.sip.header.SipRequestLine;
import javax.sip.address.URI;

public class RequestLine
extends SIPObject
implements SipRequestLine {
    private static final long serialVersionUID = -3286426172326043129L;
    protected String method;
    protected String sipVersion;
    protected GenericURI uri;

    public RequestLine() {
        this.sipVersion = "SIP/2.0";
    }

    public RequestLine(GenericURI genericURI, String string) {
        this.uri = genericURI;
        this.method = string;
        this.sipVersion = "SIP/2.0";
    }

    @Override
    public Object clone() {
        RequestLine requestLine = (RequestLine)super.clone();
        GenericURI genericURI = this.uri;
        if (genericURI != null) {
            requestLine.uri = (GenericURI)genericURI.clone();
        }
        return requestLine;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        Object object = this.method;
        if (object != null) {
            stringBuffer.append((String)object);
            stringBuffer.append(" ");
        }
        if ((object = this.uri) != null) {
            ((GenericURI)object).encode(stringBuffer);
            stringBuffer.append(" ");
        }
        stringBuffer.append(this.sipVersion);
        stringBuffer.append("\r\n");
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object.getClass().equals(this.getClass());
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (RequestLine)object;
        try {
            if (this.method.equals(((RequestLine)object).method) && this.uri.equals(((RequestLine)object).uri) && (bl = this.sipVersion.equals(((RequestLine)object).sipVersion))) {
                bl2 = true;
            }
        }
        catch (NullPointerException nullPointerException) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getSipVersion() {
        return this.sipVersion;
    }

    @Override
    public GenericURI getUri() {
        return this.uri;
    }

    @Override
    public String getVersionMajor() {
        if (this.sipVersion == null) {
            return null;
        }
        StringBuilder stringBuilder = null;
        boolean bl = false;
        for (int i = 0; i < this.sipVersion.length() && this.sipVersion.charAt(i) != '.'; ++i) {
            CharSequence charSequence = stringBuilder;
            if (bl) {
                if (stringBuilder == null) {
                    charSequence = new StringBuilder();
                    charSequence.append("");
                    charSequence.append(this.sipVersion.charAt(i));
                    charSequence = charSequence.toString();
                } else {
                    charSequence = new StringBuilder();
                    charSequence.append((String)((Object)stringBuilder));
                    charSequence.append(this.sipVersion.charAt(i));
                    charSequence = charSequence.toString();
                }
            }
            if (this.sipVersion.charAt(i) == '/') {
                bl = true;
            }
            stringBuilder = charSequence;
        }
        return stringBuilder;
    }

    @Override
    public String getVersionMinor() {
        if (this.sipVersion == null) {
            return null;
        }
        StringBuilder stringBuilder = null;
        boolean bl = false;
        for (int i = 0; i < this.sipVersion.length(); ++i) {
            CharSequence charSequence = stringBuilder;
            if (bl) {
                if (stringBuilder == null) {
                    charSequence = new StringBuilder();
                    charSequence.append("");
                    charSequence.append(this.sipVersion.charAt(i));
                    charSequence = charSequence.toString();
                } else {
                    charSequence = new StringBuilder();
                    charSequence.append((String)((Object)stringBuilder));
                    charSequence.append(this.sipVersion.charAt(i));
                    charSequence = charSequence.toString();
                }
            }
            if (this.sipVersion.charAt(i) == '.') {
                bl = true;
            }
            stringBuilder = charSequence;
        }
        return stringBuilder;
    }

    @Override
    public void setMethod(String string) {
        this.method = string;
    }

    @Override
    public void setSipVersion(String string) {
        this.sipVersion = string;
    }

    @Override
    public void setUri(URI uRI) {
        this.uri = (GenericURI)uRI;
    }
}

