/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import java.text.ParseException;
import javax.sip.header.Header;

public interface PrivacyHeader
extends Header {
    public static final String NAME = "Privacy";

    public String getPrivacy();

    public void setPrivacy(String var1) throws ParseException;
}

