/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ims.SecurityAgree;
import gov.nist.javax.sip.header.ims.SecurityVerifyHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class SecurityVerify
extends SecurityAgree
implements SecurityVerifyHeader,
ExtensionHeader {
    public SecurityVerify() {
        super("Security-Verify");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

