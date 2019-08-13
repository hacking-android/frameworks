/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ims.SecurityAgree;
import gov.nist.javax.sip.header.ims.SecurityServerHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class SecurityServer
extends SecurityAgree
implements SecurityServerHeader,
ExtensionHeader {
    public SecurityServer() {
        super("Security-Server");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

