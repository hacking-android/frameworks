/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.MimeVersionHeader;

public class MimeVersion
extends SIPHeader
implements MimeVersionHeader {
    private static final long serialVersionUID = -7951589626435082068L;
    protected int majorVersion;
    protected int minorVersion;

    public MimeVersion() {
        super("MIME-Version");
    }

    @Override
    public String encodeBody() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.majorVersion));
        stringBuilder.append(".");
        stringBuilder.append(Integer.toString(this.minorVersion));
        return stringBuilder.toString();
    }

    @Override
    public int getMajorVersion() {
        return this.majorVersion;
    }

    @Override
    public int getMinorVersion() {
        return this.minorVersion;
    }

    @Override
    public void setMajorVersion(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.majorVersion = n;
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, MimeVersion, setMajorVersion(), the majorVersion parameter is null");
    }

    @Override
    public void setMinorVersion(int n) throws InvalidArgumentException {
        if (n >= 0) {
            this.minorVersion = n;
            return;
        }
        throw new InvalidArgumentException("JAIN-SIP Exception, MimeVersion, setMinorVersion(), the minorVersion parameter is null");
    }
}

