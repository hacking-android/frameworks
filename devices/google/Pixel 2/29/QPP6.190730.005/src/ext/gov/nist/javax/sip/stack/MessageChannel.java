/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.ServerLogger;
import gov.nist.core.StackLogger;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.message.MessageFactoryImpl;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.UDPMessageChannel;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.text.ParseException;
import javax.sip.address.Address;
import javax.sip.address.Hop;
import javax.sip.header.ContactHeader;
import javax.sip.header.Header;

public abstract class MessageChannel {
    protected transient MessageProcessor messageProcessor;
    protected int useCount;

    private static final boolean copyHeader(String string, String string2, StringBuffer stringBuffer) {
        int n;
        int n2 = string2.indexOf(string);
        if (n2 != -1 && (n = string2.indexOf("\r\n", n2)) != -1) {
            stringBuffer.append(string2.subSequence(n2 - 2, n));
            return true;
        }
        return false;
    }

    private static final boolean copyViaHeaders(String string, StringBuffer stringBuffer) {
        int n = string.indexOf("Via");
        boolean bl = false;
        while (n != -1) {
            int n2 = string.indexOf("\r\n", n);
            if (n2 != -1) {
                stringBuffer.append(string.subSequence(n - 2, n2));
                bl = true;
                n = string.indexOf("Via", n2);
                continue;
            }
            return false;
        }
        return bl;
    }

    public static String getKey(HostPort hostPort, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(":");
        stringBuilder.append(hostPort.getHost().getHostname());
        stringBuilder.append(":");
        stringBuilder.append(hostPort.getPort());
        return stringBuilder.toString().toLowerCase();
    }

    public static String getKey(InetAddress inetAddress, int n, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(":");
        stringBuilder.append(inetAddress.getHostAddress());
        stringBuilder.append(":");
        stringBuilder.append(n);
        return stringBuilder.toString().toLowerCase();
    }

    public abstract void close();

    protected final String createBadReqRes(String charSequence, ParseException serializable) {
        StringBuffer stringBuffer = new StringBuffer(512);
        Serializable serializable2 = new StringBuilder();
        ((StringBuilder)serializable2).append("SIP/2.0 400 Bad Request (");
        ((StringBuilder)serializable2).append(((Throwable)serializable).getLocalizedMessage());
        ((StringBuilder)serializable2).append(')');
        stringBuffer.append(((StringBuilder)serializable2).toString());
        if (!MessageChannel.copyViaHeaders((String)charSequence, stringBuffer)) {
            return null;
        }
        if (!MessageChannel.copyHeader("CSeq", (String)charSequence, stringBuffer)) {
            return null;
        }
        if (!MessageChannel.copyHeader("Call-ID", (String)charSequence, stringBuffer)) {
            return null;
        }
        if (!MessageChannel.copyHeader("From", (String)charSequence, stringBuffer)) {
            return null;
        }
        if (!MessageChannel.copyHeader("To", (String)charSequence, stringBuffer)) {
            return null;
        }
        int n = stringBuffer.indexOf("To");
        if (n != -1 && stringBuffer.indexOf("tag", n) == -1) {
            stringBuffer.append(";tag=badreq");
        }
        if ((serializable2 = MessageFactoryImpl.getDefaultServerHeader()) != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("\r\n");
            ((StringBuilder)serializable).append(serializable2.toString());
            stringBuffer.append(((StringBuilder)serializable).toString());
        }
        n = ((String)charSequence).length();
        if (this instanceof UDPMessageChannel && stringBuffer.length() + n + "Content-Type".length() + ": message/sipfrag\r\n".length() + "Content-Length".length() >= 1300) {
            serializable = new ContentLength(0);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("\r\n");
            ((StringBuilder)charSequence).append(((SIPHeader)serializable).toString());
            stringBuffer.append(((StringBuilder)charSequence).toString());
        } else {
            serializable2 = new ContentType("message", "sipfrag");
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("\r\n");
            ((StringBuilder)serializable).append(serializable2.toString());
            stringBuffer.append(((StringBuilder)serializable).toString());
            serializable = new ContentLength(n);
            serializable2 = new StringBuilder();
            ((StringBuilder)serializable2).append("\r\n");
            ((StringBuilder)serializable2).append(((SIPHeader)serializable).toString());
            stringBuffer.append(((StringBuilder)serializable2).toString());
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("\r\n\r\n");
            ((StringBuilder)serializable).append((String)charSequence);
            stringBuffer.append(((StringBuilder)serializable).toString());
        }
        return stringBuffer.toString();
    }

    public String getHost() {
        return this.getMessageProcessor().getIpAddress().getHostAddress();
    }

    public HostPort getHostPort() {
        HostPort hostPort = new HostPort();
        hostPort.setHost(new Host(this.getHost()));
        hostPort.setPort(this.getPort());
        return hostPort;
    }

    public abstract String getKey();

    public MessageProcessor getMessageProcessor() {
        return this.messageProcessor;
    }

    public abstract String getPeerAddress();

    public HostPort getPeerHostPort() {
        HostPort hostPort = new HostPort();
        hostPort.setHost(new Host(this.getPeerAddress()));
        hostPort.setPort(this.getPeerPort());
        return hostPort;
    }

    protected abstract InetAddress getPeerInetAddress();

    public abstract InetAddress getPeerPacketSourceAddress();

    public abstract int getPeerPacketSourcePort();

    public abstract int getPeerPort();

    protected abstract String getPeerProtocol();

    public int getPort() {
        MessageProcessor messageProcessor = this.messageProcessor;
        if (messageProcessor != null) {
            return messageProcessor.getPort();
        }
        return -1;
    }

    public String getRawIpSourceAddress() {
        String string = this.getPeerAddress();
        String string2 = null;
        try {
            string2 = string = InetAddress.getByName(string).getHostAddress();
        }
        catch (Exception exception) {
            InternalErrorHandler.handleException(exception);
        }
        return string2;
    }

    public abstract SIPTransactionStack getSIPStack();

    public abstract String getTransport();

    public Via getViaHeader() {
        Via via = new Via();
        try {
            via.setTransport(this.getTransport());
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        via.setSentBy(this.getHostPort());
        return via;
    }

    public abstract String getViaHost();

    public HostPort getViaHostPort() {
        HostPort hostPort = new HostPort();
        hostPort.setHost(new Host(this.getViaHost()));
        hostPort.setPort(this.getViaPort());
        return hostPort;
    }

    public abstract int getViaPort();

    public abstract boolean isReliable();

    public abstract boolean isSecure();

    protected void logMessage(SIPMessage sIPMessage, InetAddress inetAddress, int n, long l) {
        if (!this.getSIPStack().getStackLogger().isLoggingEnabled(16)) {
            return;
        }
        int n2 = n;
        if (n == -1) {
            n2 = 5060;
        }
        ServerLogger serverLogger = this.getSIPStack().serverLogger;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getHost());
        stringBuilder.append(":");
        stringBuilder.append(this.getPort());
        String string = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(inetAddress.getHostAddress().toString());
        stringBuilder.append(":");
        stringBuilder.append(n2);
        serverLogger.logMessage(sIPMessage, string, stringBuilder.toString(), true, l);
    }

    public void logResponse(SIPResponse sIPResponse, long l, String string) {
        int n;
        int n2 = n = this.getPeerPort();
        if (n == 0) {
            n2 = n;
            if (sIPResponse.getContactHeaders() != null) {
                n2 = ((AddressImpl)((ContactHeader)sIPResponse.getContactHeaders().getFirst()).getAddress()).getPort();
            }
        }
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.getPeerAddress().toString());
        charSequence.append(":");
        charSequence.append(n2);
        charSequence = charSequence.toString();
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append(this.getHost());
        charSequence2.append(":");
        charSequence2.append(this.getPort());
        charSequence2 = charSequence2.toString();
        this.getSIPStack().serverLogger.logMessage(sIPResponse, (String)charSequence, (String)charSequence2, string, false, l);
    }

    public abstract void sendMessage(SIPMessage var1) throws IOException;

    public void sendMessage(SIPMessage sIPMessage, InetAddress inetAddress, int n) throws IOException {
        long l = System.currentTimeMillis();
        this.sendMessage(sIPMessage.encodeAsBytes(this.getTransport()), inetAddress, n, sIPMessage instanceof SIPRequest);
        this.logMessage(sIPMessage, inetAddress, n, l);
    }

    /*
     * Exception decompiling
     */
    public void sendMessage(SIPMessage var1_1, Hop var2_2) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CATCHBLOCK]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    protected abstract void sendMessage(byte[] var1, InetAddress var2, int var3, boolean var4) throws IOException;

    protected void uncache() {
    }
}

