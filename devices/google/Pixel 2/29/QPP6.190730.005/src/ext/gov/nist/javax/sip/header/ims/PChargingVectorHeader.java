/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface PChargingVectorHeader
extends Header,
Parameters {
    public static final String NAME = "P-Charging-Vector";

    public String getICID();

    public String getICIDGeneratedAt();

    public String getOriginatingIOI();

    public String getTerminatingIOI();

    public void setICID(String var1) throws ParseException;

    public void setICIDGeneratedAt(String var1) throws ParseException;

    public void setOriginatingIOI(String var1) throws ParseException;

    public void setTerminatingIOI(String var1) throws ParseException;
}

