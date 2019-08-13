/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import java.text.ParseException;
import javax.sip.header.WWWAuthenticateHeader;

public interface WWWAuthenticateHeaderIms
extends WWWAuthenticateHeader {
    public static final String CK = "ck";
    public static final String IK = "ik";

    public String getCK();

    public String getIK();

    public void setCK(String var1) throws ParseException;

    public void setIK(String var1) throws ParseException;
}

