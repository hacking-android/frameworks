/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.address.URI;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface CallInfoHeader
extends Header,
Parameters {
    public static final String NAME = "Call-Info";

    public URI getInfo();

    public String getPurpose();

    public void setInfo(URI var1);

    public void setPurpose(String var1);
}

