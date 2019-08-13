/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.util.Collection;
import java.util.Iterator;
import javax.sip.InvalidArgumentException;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.ProviderDoesNotExistException;
import javax.sip.SipException;
import javax.sip.SipProvider;
import javax.sip.TransportNotSupportedException;
import javax.sip.address.Router;

public interface SipStack {
    public ListeningPoint createListeningPoint(int var1, String var2) throws TransportNotSupportedException, InvalidArgumentException;

    public ListeningPoint createListeningPoint(String var1, int var2, String var3) throws TransportNotSupportedException, InvalidArgumentException;

    public SipProvider createSipProvider(ListeningPoint var1) throws ObjectInUseException;

    public void deleteListeningPoint(ListeningPoint var1) throws ObjectInUseException;

    public void deleteSipProvider(SipProvider var1) throws ObjectInUseException;

    public Collection getDialogs();

    public String getIPAddress();

    public Iterator getListeningPoints();

    public Router getRouter();

    public Iterator getSipProviders();

    public String getStackName();

    public boolean isRetransmissionFilterActive();

    public void start() throws ProviderDoesNotExistException, SipException;

    public void stop();
}

