/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface PUserDatabaseHeader
extends Parameters,
Header {
    public static final String NAME = "P-User-Database";

    public String getDatabaseName();

    public void setDatabaseName(String var1);
}

