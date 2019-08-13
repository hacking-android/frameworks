/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import javax.sip.address.URI;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;
import javax.sip.header.Parameters;

public interface PAssociatedURIHeader
extends HeaderAddress,
Parameters,
Header {
    public static final String NAME = "P-Associated-URI";

    public URI getAssociatedURI();

    public void setAssociatedURI(URI var1) throws NullPointerException;
}

