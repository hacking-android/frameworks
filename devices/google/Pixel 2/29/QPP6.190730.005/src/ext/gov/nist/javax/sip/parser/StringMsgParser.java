/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.Host;
import gov.nist.core.HostNameParser;
import gov.nist.core.LexerCore;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.address.TelephoneNumber;
import gov.nist.javax.sip.header.ExtensionHeaderImpl;
import gov.nist.javax.sip.header.NameMap;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.StatusLine;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.parser.AddressParser;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParseExceptionListener;
import gov.nist.javax.sip.parser.ParserFactory;
import gov.nist.javax.sip.parser.RequestLineParser;
import gov.nist.javax.sip.parser.StatusLineParser;
import gov.nist.javax.sip.parser.URLParser;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import javax.sip.header.ContentLengthHeader;

public class StringMsgParser {
    private static boolean computeContentLengthFromMessage = false;
    private ParseExceptionListener parseExceptionListener;
    private String rawStringMessage;
    protected boolean readBody = true;
    private boolean strict;

    public StringMsgParser() {
    }

    public StringMsgParser(ParseExceptionListener parseExceptionListener) {
        this();
        this.parseExceptionListener = parseExceptionListener;
    }

    public static void main(String[] arrstring) throws ParseException {
        for (int i = 0; i < 20; ++i) {
            new Thread(new 1ParserThread(new String[]{"SIP/2.0 200 OK\r\nTo: \"The Little Blister\" <sip:LittleGuy@there.com>;tag=469bc066\r\nFrom: \"The Master Blaster\" <sip:BigGuy@here.com>;tag=11\r\nVia: SIP/2.0/UDP 139.10.134.246:5060;branch=z9hG4bK8b0a86f6_1030c7d18e0_17;received=139.10.134.246\r\nCall-ID: 1030c7d18ae_a97b0b_b@8b0a86f6\r\nCSeq: 1 SUBSCRIBE\r\nContact: <sip:172.16.11.162:5070>\r\nContent-Length: 0\r\n\r\n", "SIP/2.0 180 Ringing\r\nVia: SIP/2.0/UDP 172.18.1.29:5060;branch=z9hG4bK43fc10fb4446d55fc5c8f969607991f4\r\nTo: \"0440\" <sip:0440@212.209.220.131>;tag=2600\r\nFrom: \"Andreas\" <sip:andreas@e-horizon.se>;tag=8524\r\nCall-ID: f51a1851c5f570606140f14c8eb64fd3@172.18.1.29\r\nCSeq: 1 INVITE\r\nMax-Forwards: 70\r\nRecord-Route: <sip:212.209.220.131:5060>\r\nContent-Length: 0\r\n\r\n", "REGISTER sip:nist.gov SIP/2.0\r\nVia: SIP/2.0/UDP 129.6.55.182:14826\r\nMax-Forwards: 70\r\nFrom: <sip:mranga@nist.gov>;tag=6fcd5c7ace8b4a45acf0f0cd539b168b;epid=0d4c418ddf\r\nTo: <sip:mranga@nist.gov>\r\nCall-ID: c5679907eb954a8da9f9dceb282d7230@129.6.55.182\r\nCSeq: 1 REGISTER\r\nContact: <sip:129.6.55.182:14826>;methods=\"INVITE, MESSAGE, INFO, SUBSCRIBE, OPTIONS, BYE, CANCEL, NOTIFY, ACK, REFER\"\r\nUser-Agent: RTC/(Microsoft RTC)\r\nEvent:  registration\r\nAllow-Events: presence\r\nContent-Length: 0\r\n\r\nINVITE sip:littleguy@there.com:5060 SIP/2.0\r\nVia: SIP/2.0/UDP 65.243.118.100:5050\r\nFrom: M. Ranganathan  <sip:M.Ranganathan@sipbakeoff.com>;tag=1234\r\nTo: \"littleguy@there.com\" <sip:littleguy@there.com:5060> \r\nCall-ID: Q2AboBsaGn9!?x6@sipbakeoff.com \r\nCSeq: 1 INVITE \r\nContent-Length: 247\r\n\r\nv=0\r\no=4855 13760799956958020 13760799956958020 IN IP4  129.6.55.78\r\ns=mysession session\r\np=+46 8 52018010\r\nc=IN IP4  129.6.55.78\r\nt=0 0\r\nm=audio 6022 RTP/AVP 0 4 18\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:4 G723/8000\r\na=rtpmap:18 G729A/8000\r\na=ptime:20\r\n"})).start();
        }
    }

    private SIPMessage processFirstLine(String object) throws ParseException {
        ParseException parseException3;
        block5 : {
            block7 : {
                block6 : {
                    ParseException parseException22;
                    block4 : {
                        if (((String)object).startsWith("SIP/2.0")) break block6;
                        SIPRequest sIPRequest = new SIPRequest();
                        try {
                            Serializable serializable = new StringBuilder();
                            serializable.append((String)object);
                            serializable.append("\n");
                            RequestLineParser requestLineParser = new RequestLineParser(serializable.toString());
                            serializable = requestLineParser.parse();
                            sIPRequest.setRequestLine((RequestLine)serializable);
                        }
                        catch (ParseException parseException22) {
                            ParseExceptionListener parseExceptionListener = this.parseExceptionListener;
                            if (parseExceptionListener == null) break block4;
                            parseExceptionListener.handleException(parseException22, sIPRequest, RequestLine.class, (String)object, this.rawStringMessage);
                        }
                        object = sIPRequest;
                        break block7;
                    }
                    throw parseException22;
                }
                SIPResponse sIPResponse = new SIPResponse();
                try {
                    Serializable serializable = new StringBuilder();
                    serializable.append((String)object);
                    serializable.append("\n");
                    StatusLineParser statusLineParser = new StatusLineParser(serializable.toString());
                    serializable = statusLineParser.parse();
                    sIPResponse.setStatusLine((StatusLine)serializable);
                    object = sIPResponse;
                }
                catch (ParseException parseException3) {
                    ParseExceptionListener parseExceptionListener = this.parseExceptionListener;
                    if (parseExceptionListener == null) break block5;
                    parseExceptionListener.handleException(parseException3, sIPResponse, StatusLine.class, (String)object, this.rawStringMessage);
                    object = sIPResponse;
                }
            }
            return object;
        }
        throw parseException3;
    }

    private void processHeader(String string, SIPMessage sIPMessage) throws ParseException {
        if (string != null && string.length() != 0) {
            block6 : {
                Class class_;
                try {
                    class_ = new Class();
                    ((StringBuilder)((Object)class_)).append(string);
                    ((StringBuilder)((Object)class_)).append("\n");
                    class_ = ParserFactory.createParser(((StringBuilder)((Object)class_)).toString());
                }
                catch (ParseException parseException) {
                    this.parseExceptionListener.handleException(parseException, sIPMessage, null, string, this.rawStringMessage);
                    return;
                }
                try {
                    sIPMessage.attachHeader(((HeaderParser)((Object)class_)).parse(), false);
                }
                catch (ParseException parseException) {
                    if (this.parseExceptionListener == null) break block6;
                    class_ = NameMap.getClassFromName(Lexer.getHeaderName(string));
                    if (class_ == null) {
                        class_ = ExtensionHeaderImpl.class;
                    }
                    this.parseExceptionListener.handleException(parseException, sIPMessage, class_, string, this.rawStringMessage);
                }
            }
            return;
        }
    }

    public static void setComputeContentLengthFromMessage(boolean bl) {
        computeContentLengthFromMessage = bl;
    }

    private String trimEndOfLine(String string) {
        int n;
        if (string == null) {
            return string;
        }
        for (n = string.length() - 1; n >= 0 && string.charAt(n) <= ' '; --n) {
        }
        if (n == string.length() - 1) {
            return string;
        }
        if (n == -1) {
            return "";
        }
        return string.substring(0, n + 1);
    }

    public AddressImpl parseAddress(String string) throws ParseException {
        return new AddressParser(string).address(true);
    }

    public Host parseHost(String string) throws ParseException {
        return new HostNameParser(new Lexer("charLexer", string)).host();
    }

    /*
     * Loose catch block
     * Enabled aggressive exception aggregation
     */
    public SIPHeader parseSIPHeader(String object) throws ParseException {
        int n;
        int n2 = 0;
        int n3 = ((String)object).length() - 1;
        do {
            n = n3;
            if (((String)object).charAt(n2) > ' ') break;
            ++n2;
            continue;
            break;
        } while (true);
        do {
            n3 = ((String)object).charAt(n);
            if (n3 > 32) break;
            --n;
            continue;
            break;
        } while (true);
        StringBuffer stringBuffer = new StringBuffer(n + 1);
        n3 = n2;
        boolean bl = false;
        int n4 = n2;
        for (n2 = n3; n2 <= n; ++n2) {
            boolean bl2;
            char c = ((String)object).charAt(n2);
            if (c != '\r' && c != '\n') {
                n3 = n4;
                bl2 = bl;
                if (bl) {
                    bl2 = false;
                    if (c != ' ' && c != '\t') {
                        n3 = n2;
                    } else {
                        stringBuffer.append(' ');
                        n3 = n2 + 1;
                    }
                }
            } else {
                n3 = n4;
                bl2 = bl;
                if (!bl) {
                    stringBuffer.append(((String)object).substring(n4, n2));
                    bl2 = true;
                    n3 = n4;
                }
            }
            n4 = n3;
            bl = bl2;
        }
        stringBuffer.append(((String)object).substring(n4, n2));
        stringBuffer.append('\n');
        object = ParserFactory.createParser(stringBuffer.toString());
        if (object != null) {
            return ((HeaderParser)object).parse();
        }
        throw new ParseException("could not create parser", 0);
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new ParseException("Empty header.", 0);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public SIPMessage parseSIPMessage(String string) throws ParseException {
        int n;
        Serializable serializable;
        block23 : {
            String string2;
            Serializable serializable2;
            int n2;
            boolean bl;
            String string3;
            if (string == null) return null;
            if (string.length() == 0) {
                return null;
            }
            this.rawStringMessage = string;
            int n3 = 0;
            try {
                while ((n = string.charAt(n3)) < 32) {
                    ++n3;
                }
                string2 = null;
                bl = true;
                serializable = null;
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                return null;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                return null;
            }
            do {
                String string4;
                n2 = n3;
                n = n3;
                int n4 = string.charAt(n3);
                n = n4;
                n4 = n3;
                while (n != 13 && n != 10) {
                    n2 = ++n4;
                    n = n4;
                    char c = string.charAt(n4);
                    n = c;
                }
                string3 = this.trimEndOfLine(string.substring(n3, n4));
                if (string3.length() == 0) {
                    string4 = string2;
                    serializable2 = serializable;
                    if (string2 != null) {
                        this.processHeader(string2, (SIPMessage)serializable);
                        string4 = string2;
                        serializable2 = serializable;
                    }
                } else if (bl) {
                    serializable2 = this.processFirstLine(string3);
                    string4 = string2;
                } else {
                    n3 = string3.charAt(0);
                    if (n3 != 9 && n3 != 32) {
                        if (string2 != null) {
                            this.processHeader(string2, (SIPMessage)serializable);
                        }
                        string4 = string3;
                        serializable2 = serializable;
                    } else {
                        if (string2 == null) throw new ParseException("Bad header continuation.", 0);
                        serializable2 = new StringBuilder();
                        ((StringBuilder)serializable2).append(string2);
                        ((StringBuilder)serializable2).append(string3.substring(1));
                        string4 = ((StringBuilder)serializable2).toString();
                        serializable2 = serializable;
                    }
                }
                n3 = n4;
                if (string.charAt(n4) == '\r') {
                    n3 = n4;
                    if (string.length() > n4 + 1) {
                        n3 = n4;
                        if (string.charAt(n4 + 1) == '\n') {
                            n3 = n4 + 1;
                        }
                    }
                }
                n = n3 + 1;
                bl = false;
                n3 = n;
                string2 = string4;
                serializable = serializable2;
            } while (string3.length() > 0);
            serializable = serializable2;
            break block23;
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                n = n2;
                break block23;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                // empty catch block
            }
        }
        serializable.setSize(n);
        if (!this.readBody) return serializable;
        if (serializable.getContentLength() == null) return serializable;
        if (serializable.getContentLength().getContentLength() != 0) {
            serializable.setMessageContent(string.substring(n), this.strict, computeContentLengthFromMessage, serializable.getContentLength().getContentLength());
            return serializable;
        }
        if (computeContentLengthFromMessage) return serializable;
        if (serializable.getContentLength().getContentLength() != 0) return serializable;
        if (string.endsWith("\r\n\r\n")) return serializable;
        if (this.strict) throw new ParseException("Extraneous characters at the end of the message ", n);
        return serializable;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public SIPMessage parseSIPMessage(byte[] var1_1) throws ParseException {
        if (var1_1 == null) return null;
        if (var1_1.length == 0) {
            return null;
        }
        var2_3 = 0;
        while ((var3_4 = var1_1[var2_3]) < 32) {
            ++var2_3;
        }
        var4_5 = null;
        var5_6 = true;
        var6_7 = null;
        do lbl-1000: // 2 sources:
        {
            var3_4 = var2_3;
            while (var1_1[var3_4] != 13 && (var7_8 = var1_1[var3_4]) != 10) {
                ++var3_4;
            }
            var8_9 = new String(var1_1, var2_3, var3_4 - var2_3, "UTF-8");
            var9_10 = this.trimEndOfLine((String)var8_9);
            if (var9_10.length() == 0) {
                var8_9 = var6_7;
                var10_11 = var4_5;
                if (var6_7 != null) {
                    var8_9 = var6_7;
                    var10_11 = var4_5;
                    if (var4_5 != null) {
                        this.processHeader((String)var6_7, var4_5);
                        var8_9 = var6_7;
                        var10_11 = var4_5;
                    }
                }
                break block16;
            }
            if (!var5_6) ** break block17
            var10_11 = this.processFirstLine(var9_10);
            var8_9 = var6_7;
            break block16;
            break;
        } while (true);
        catch (UnsupportedEncodingException var1_2) {
            throw new ParseException("Bad message encoding!", 0);
        }
        {
            block16 : {
                var2_3 = var9_10.charAt(0);
                if (var2_3 != 9 && var2_3 != 32) {
                    if (var6_7 != null && var4_5 != null) {
                        this.processHeader((String)var6_7, var4_5);
                    }
                    var8_9 = var9_10;
                    var10_11 = var4_5;
                } else {
                    if (var6_7 == null) throw new ParseException("Bad header continuation.", 0);
                    var8_9 = new StringBuilder();
                    var8_9.append((String)var6_7);
                    var8_9.append(var9_10.substring(1));
                    var8_9 = var8_9.toString();
                    var10_11 = var4_5;
                }
            }
            var2_3 = var3_4;
            if (var1_1[var3_4] == 13) {
                var2_3 = var3_4;
                if (var1_1.length > var3_4 + 1) {
                    var2_3 = var3_4;
                    if (var1_1[var3_4 + 1] == 10) {
                        var2_3 = var3_4 + 1;
                    }
                }
            }
            ++var2_3;
            var5_6 = false;
            if (var9_10.length() <= 0) {
                if (var10_11 == null) throw new ParseException("Bad message", 0);
                var10_11.setSize(var2_3);
                if (this.readBody == false) return var10_11;
                if (var10_11.getContentLength() == null) return var10_11;
                if (var10_11.getContentLength().getContentLength() == 0) return var10_11;
                var3_4 = var1_1.length - var2_3;
                var8_9 = new byte[var3_4];
                System.arraycopy(var1_1, var2_3, var8_9, 0, var3_4);
                var10_11.setMessageContent((byte[])var8_9, StringMsgParser.computeContentLengthFromMessage, var10_11.getContentLength().getContentLength());
                return var10_11;
            }
            var6_7 = var8_9;
            var4_5 = var10_11;
            ** while (true)
        }
    }

    public RequestLine parseSIPRequestLine(String string) throws ParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("\n");
        return new RequestLineParser(stringBuilder.toString()).parse();
    }

    public StatusLine parseSIPStatusLine(String string) throws ParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("\n");
        return new StatusLineParser(stringBuilder.toString()).parse();
    }

    public SipUri parseSIPUrl(String string) throws ParseException {
        try {
            Object object = new URLParser(string);
            object = ((URLParser)object).sipURL(true);
            return object;
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" Not a SIP URL ");
            throw new ParseException(stringBuilder.toString(), 0);
        }
    }

    public TelephoneNumber parseTelephoneNumber(String string) throws ParseException {
        return new URLParser(string).parseTelephoneNumber(true);
    }

    public GenericURI parseUrl(String string) throws ParseException {
        return new URLParser(string).parse();
    }

    public void setParseExceptionListener(ParseExceptionListener parseExceptionListener) {
        this.parseExceptionListener = parseExceptionListener;
    }

    public void setStrict(boolean bl) {
        this.strict = bl;
    }

    class 1ParserThread
    implements Runnable {
        String[] messages;

        public 1ParserThread(String[] arrstring) {
            this.messages = arrstring;
        }

        @Override
        public void run() {
            for (int i = 0; i < this.messages.length; ++i) {
                Object object = new StringMsgParser();
                try {
                    SIPMessage sIPMessage = ((StringMsgParser)object).parseSIPMessage(this.messages[i]);
                    object = System.out;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" i = ");
                    stringBuilder.append(i);
                    stringBuilder.append(" branchId = ");
                    stringBuilder.append(sIPMessage.getTopmostVia().getBranch());
                    ((PrintStream)object).println(stringBuilder.toString());
                    continue;
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
            }
        }
    }

}

