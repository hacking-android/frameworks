/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.HostNameParser;
import gov.nist.core.HostPort;
import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.Protocol;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ViaParser
extends HeaderParser {
    public ViaParser(Lexer lexer) {
        super(lexer);
    }

    public ViaParser(String string) {
        super(string);
    }

    private void parseVia(Via via) throws ParseException {
        block7 : {
            this.lexer.match(4095);
            Token token = this.lexer.getNextToken();
            this.lexer.SPorHT();
            this.lexer.match(47);
            this.lexer.SPorHT();
            this.lexer.match(4095);
            this.lexer.SPorHT();
            Token token2 = this.lexer.getNextToken();
            this.lexer.SPorHT();
            this.lexer.match(47);
            this.lexer.SPorHT();
            this.lexer.match(4095);
            this.lexer.SPorHT();
            Object object = this.lexer.getNextToken();
            this.lexer.SPorHT();
            Protocol protocol = new Protocol();
            protocol.setProtocolName(token.getTokenValue());
            protocol.setProtocolVersion(token2.getTokenValue());
            protocol.setTransport(((Token)object).getTokenValue());
            via.setSentProtocol(protocol);
            via.setSentBy(new HostNameParser(this.getLexer()).hostPort(true));
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ';') {
                this.lexer.consume(1);
                this.lexer.SPorHT();
                object = this.nameValue();
                if (((NameValue)object).getName().equals("branch") && (String)((NameValue)object).getValueAsObject() == null) {
                    throw new ParseException("null branch Id", this.lexer.getPtr());
                }
                via.setParameter((NameValue)object);
                this.lexer.SPorHT();
            }
            if (this.lexer.lookAhead(0) != '(') break block7;
            this.lexer.selectLexer("charLexer");
            this.lexer.consume(1);
            object = new StringBuffer();
            do {
                char c;
                block10 : {
                    block9 : {
                        block8 : {
                            if ((c = this.lexer.lookAhead(0)) != ')') break block8;
                            this.lexer.consume(1);
                            break block9;
                        }
                        if (c == '\\') {
                            ((StringBuffer)object).append(this.lexer.getNextToken().getTokenValue());
                            this.lexer.consume(1);
                            ((StringBuffer)object).append(this.lexer.getNextToken().getTokenValue());
                            this.lexer.consume(1);
                            continue;
                        }
                        if (c != '\n') break block10;
                    }
                    via.setComment(((StringBuffer)object).toString());
                    break;
                }
                ((StringBuffer)object).append(c);
                this.lexer.consume(1);
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected NameValue nameValue() throws ParseException {
        if (debug) {
            this.dbg_enter("nameValue");
        }
        try {
            this.lexer.match(4095);
            Token token = this.lexer.getNextToken();
            this.lexer.SPorHT();
            boolean bl = false;
            try {
                if (this.lexer.lookAhead(0) == '=') {
                    String string;
                    this.lexer.consume(1);
                    this.lexer.SPorHT();
                    if (token.getTokenValue().compareToIgnoreCase("received") == 0) {
                        string = this.lexer.byteStringNoSemicolon();
                    } else if (this.lexer.lookAhead(0) == '\"') {
                        string = this.lexer.quotedString();
                        bl = true;
                    } else {
                        this.lexer.match(4095);
                        string = this.lexer.getNextToken().getTokenValue();
                    }
                    NameValue nameValue = new NameValue(token.getTokenValue().toLowerCase(), string);
                    if (!bl) return nameValue;
                    nameValue.setQuotedValue();
                    return nameValue;
                }
                NameValue nameValue = new NameValue(token.getTokenValue().toLowerCase(), null);
                return nameValue;
            }
            catch (ParseException parseException) {
                NameValue nameValue = new NameValue(token.getTokenValue(), null);
                return nameValue;
            }
        }
        finally {
            if (debug) {
                this.dbg_leave("nameValue");
            }
        }
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            ViaList viaList = new ViaList();
            this.lexer.match(2064);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            do {
                Via via = new Via();
                this.parseVia(via);
                viaList.add(via);
                this.lexer.SPorHT();
                if (this.lexer.lookAhead(0) != ',') continue;
                this.lexer.consume(1);
                this.lexer.SPorHT();
            } while (this.lexer.lookAhead(0) != '\n');
            this.lexer.match(10);
            return viaList;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

