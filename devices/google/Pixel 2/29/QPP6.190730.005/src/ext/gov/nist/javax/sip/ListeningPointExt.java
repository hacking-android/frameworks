/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import java.io.IOException;
import javax.sip.ListeningPoint;
import javax.sip.header.ContactHeader;
import javax.sip.header.ViaHeader;

public interface ListeningPointExt
extends ListeningPoint {
    @Override
    public ContactHeader createContactHeader();

    public ViaHeader createViaHeader();

    @Override
    public void sendHeartbeat(String var1, int var2) throws IOException;
}

