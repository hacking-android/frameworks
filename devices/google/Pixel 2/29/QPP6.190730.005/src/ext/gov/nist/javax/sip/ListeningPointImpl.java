/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.javax.sip.ListeningPointExt;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import javax.sip.ListeningPoint;
import javax.sip.SipStack;
import javax.sip.address.Address;
import javax.sip.address.URI;
import javax.sip.header.ContactHeader;
import javax.sip.header.ViaHeader;

public class ListeningPointImpl
implements ListeningPoint,
ListeningPointExt {
    protected MessageProcessor messageProcessor;
    int port;
    protected SipProviderImpl sipProvider;
    protected SipStackImpl sipStack;
    protected String transport;

    protected ListeningPointImpl(SipStack sipStack, int n, String string) {
        this.sipStack = (SipStackImpl)sipStack;
        this.port = n;
        this.transport = string;
    }

    public static String makeKey(String charSequence, int n, String string) {
        charSequence = new StringBuffer((String)charSequence);
        ((StringBuffer)charSequence).append(":");
        ((StringBuffer)charSequence).append(n);
        ((StringBuffer)charSequence).append("/");
        ((StringBuffer)charSequence).append(string);
        return ((StringBuffer)charSequence).toString().toLowerCase();
    }

    public Object clone() {
        ListeningPointImpl listeningPointImpl = new ListeningPointImpl(this.sipStack, this.port, null);
        listeningPointImpl.sipStack = this.sipStack;
        return listeningPointImpl;
    }

    @Override
    public ContactHeader createContactHeader() {
        try {
            Object object = this.getIPAddress();
            int n = this.getPort();
            SipUri sipUri = new SipUri();
            sipUri.setHost((String)object);
            sipUri.setPort(n);
            sipUri.setTransportParam(this.transport);
            object = new Contact();
            AddressImpl addressImpl = new AddressImpl();
            addressImpl.setURI(sipUri);
            ((Contact)object).setAddress(addressImpl);
            return object;
        }
        catch (Exception exception) {
            InternalErrorHandler.handleException("Unexpected exception", this.sipStack.getStackLogger());
            return null;
        }
    }

    @Override
    public ViaHeader createViaHeader() {
        return this.getViaHeader();
    }

    @Override
    public String getIPAddress() {
        return this.messageProcessor.getIpAddress().getHostAddress();
    }

    protected String getKey() {
        return ListeningPointImpl.makeKey(this.getIPAddress(), this.port, this.transport);
    }

    public MessageProcessor getMessageProcessor() {
        return this.messageProcessor;
    }

    @Override
    public int getPort() {
        return this.messageProcessor.getPort();
    }

    public SipProviderImpl getProvider() {
        return this.sipProvider;
    }

    @Override
    public String getSentBy() {
        return this.messageProcessor.getSentBy();
    }

    @Override
    public String getTransport() {
        return this.messageProcessor.getTransport();
    }

    public Via getViaHeader() {
        return this.messageProcessor.getViaHeader();
    }

    public boolean isSentBySet() {
        return this.messageProcessor.isSentBySet();
    }

    protected void removeSipProvider() {
        this.sipProvider = null;
    }

    @Override
    public void sendHeartbeat(String object, int n) throws IOException {
        Object object2 = new HostPort();
        ((HostPort)object2).setHost(new Host((String)object));
        ((HostPort)object2).setPort(n);
        object2 = this.messageProcessor.createMessageChannel((HostPort)object2);
        object = new SIPRequest();
        ((SIPMessage)object).setNullRequest();
        ((MessageChannel)object2).sendMessage((SIPMessage)object);
    }

    @Override
    public void setSentBy(String string) throws ParseException {
        this.messageProcessor.setSentBy(string);
    }

    protected void setSipProvider(SipProviderImpl sipProviderImpl) {
        this.sipProvider = sipProviderImpl;
    }
}

