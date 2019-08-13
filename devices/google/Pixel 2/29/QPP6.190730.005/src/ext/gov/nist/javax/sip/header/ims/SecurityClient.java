/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ims.SecurityAgree;
import gov.nist.javax.sip.header.ims.SecurityClientHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class SecurityClient
extends SecurityAgree
implements SecurityClientHeader,
ExtensionHeader {
    public SecurityClient() {
        super("Security-Client");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

