/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPObject;

public class MediaRange
extends SIPObject {
    private static final long serialVersionUID = -6297125815438079210L;
    protected String subtype;
    protected String type;

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.type);
        stringBuffer.append("/");
        stringBuffer.append(this.subtype);
        return stringBuffer;
    }

    public String getSubtype() {
        return this.subtype;
    }

    public String getType() {
        return this.type;
    }

    public void setSubtype(String string) {
        this.subtype = string;
    }

    public void setType(String string) {
        this.type = string;
    }
}

