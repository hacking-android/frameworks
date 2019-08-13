/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.address.URI;
import javax.sip.header.AuthorizationHeader;

public interface WWWAuthenticateHeader
extends AuthorizationHeader {
    public static final String NAME = "WWW-Authenticate";

    @Override
    public URI getURI();

    @Override
    public void setURI(URI var1);
}

