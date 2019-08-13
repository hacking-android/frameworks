/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.address;

import java.util.ListIterator;
import javax.sip.SipException;
import javax.sip.address.Hop;
import javax.sip.message.Request;

public interface Router {
    public Hop getNextHop(Request var1) throws SipException;

    public ListIterator getNextHops(Request var1);

    public Hop getOutboundProxy();
}

