/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.header;

import java.text.ParseException;
import javax.sip.address.URI;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface ErrorInfoHeader
extends Header,
Parameters {
    public static final String NAME = "Error-Info";

    public URI getErrorInfo();

    public String getErrorMessage();

    public void setErrorInfo(URI var1);

    public void setErrorMessage(String var1) throws ParseException;
}

