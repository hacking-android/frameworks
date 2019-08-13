/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import javax.sip.address.URI;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface AlertInfoHeader
extends Header,
Parameters {
    public static final String NAME = "Alert-Info";

    public URI getAlertInfo();

    public void setAlertInfo(String var1);

    public void setAlertInfo(URI var1);
}

