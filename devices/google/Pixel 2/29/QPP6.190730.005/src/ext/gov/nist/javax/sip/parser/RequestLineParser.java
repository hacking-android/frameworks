/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.Parser;
import gov.nist.javax.sip.parser.URLParser;
import java.io.PrintStream;
import java.text.ParseException;
import javax.sip.address.URI;

public class RequestLineParser
extends Parser {
    public RequestLineParser(Lexer lexer) {
        this.lexer = lexer;
        this.lexer.selectLexer("method_keywordLexer");
    }

    public RequestLineParser(String string) {
        this.lexer = new Lexer("method_keywordLexer", string);
    }

    public static void main(String[] object) throws ParseException {
        String[] arrstring = new String[]{"REGISTER sip:192.168.0.68 SIP/2.0\n", "REGISTER sip:company.com SIP/2.0\n", "INVITE sip:3660@166.35.231.140 SIP/2.0\n", "INVITE sip:user@company.com SIP/2.0\n", "REGISTER sip:[2001::1]:5060;transport=tcp SIP/2.0\n", "REGISTER sip:[2002:800:700:600:30:4:6:1]:5060;transport=udp SIP/2.0\n", "REGISTER sip:[3ffe:800:700::30:4:6:1]:5060;transport=tls SIP/2.0\n", "REGISTER sip:[2001:720:1710:0:201:29ff:fe21:f403]:5060;transport=udp SIP/2.0\n", "OPTIONS sip:135.180.130.133 SIP/2.0\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            RequestLine requestLine = new RequestLineParser(arrstring[i]).parse();
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("encoded = ");
            ((StringBuilder)object).append(requestLine.encode());
            printStream.println(((StringBuilder)object).toString());
        }
    }

    public RequestLine parse() throws ParseException {
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            RequestLine requestLine = new RequestLine();
            Object object = this.method();
            this.lexer.SPorHT();
            requestLine.setMethod((String)object);
            this.lexer.selectLexer("sip_urlLexer");
            object = new URLParser(this.getLexer());
            object = ((URLParser)object).uriReference(true);
            this.lexer.SPorHT();
            requestLine.setUri((URI)object);
            this.lexer.selectLexer("request_lineLexer");
            requestLine.setSipVersion(this.sipVersion());
            this.lexer.SPorHT();
            this.lexer.match(10);
            return requestLine;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

