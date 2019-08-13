/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.Token;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface PVisitedNetworkIDHeader
extends Parameters,
Header {
    public static final String NAME = "P-Visited-Network-ID";

    public String getVisitedNetworkID();

    public void setVisitedNetworkID(Token var1);

    public void setVisitedNetworkID(String var1);
}

