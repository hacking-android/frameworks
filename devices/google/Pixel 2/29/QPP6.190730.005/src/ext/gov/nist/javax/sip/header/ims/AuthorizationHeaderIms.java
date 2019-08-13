/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.AuthorizationHeader;

public interface AuthorizationHeaderIms
extends AuthorizationHeader {
    public static final String NO = "no";
    public static final String YES = "yes";

    public String getIntegrityProtected();

    public void setIntegrityProtected(String var1) throws InvalidArgumentException, ParseException;
}

