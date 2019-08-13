/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.PVisitedNetworkID;
import gov.nist.javax.sip.header.ims.PVisitedNetworkIDList;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import gov.nist.javax.sip.parser.TokenTypes;
import java.io.Serializable;
import java.text.ParseException;

public class PVisitedNetworkIDParser
extends ParametersParser
implements TokenTypes {
    protected PVisitedNetworkIDParser(Lexer lexer) {
        super(lexer);
    }

    public PVisitedNetworkIDParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        char c;
        Serializable serializable = new PVisitedNetworkIDList();
        if (debug) {
            this.dbg_enter("VisitedNetworkIDParser.parse");
        }
        try {
            this.lexer.match(2123);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            do {
                PVisitedNetworkID pVisitedNetworkID = new PVisitedNetworkID();
                if (this.lexer.lookAhead(0) == '\"') {
                    this.parseQuotedString(pVisitedNetworkID);
                } else {
                    this.parseToken(pVisitedNetworkID);
                }
                ((SIPHeaderList)serializable).add(pVisitedNetworkID);
                this.lexer.SPorHT();
                c = this.lexer.lookAhead(0);
                if (c != ',') break;
                break;
            } while (true);
        }
        catch (Throwable throwable) {
            if (debug) {
                this.dbg_leave("VisitedNetworkIDParser.parse");
            }
            throw throwable;
        }
        {
            this.lexer.match(44);
            this.lexer.SPorHT();
            continue;
        }
        if (c == '\n') {
            if (debug) {
                this.dbg_leave("VisitedNetworkIDParser.parse");
            }
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("unexpected char = ");
        ((StringBuilder)serializable).append(c);
        throw this.createParseException(((StringBuilder)serializable).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void parseQuotedString(PVisitedNetworkID serializable) throws ParseException {
        if (debug) {
            this.dbg_enter("parseQuotedString");
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            if (this.lexer.lookAhead(0) != '\"') {
                throw this.createParseException("unexpected char");
            }
            this.lexer.consume(1);
            do {
                char c;
                if ((c = this.lexer.getNextChar()) == '\"') {
                    ((PVisitedNetworkID)serializable).setVisitedNetworkID(stringBuffer.toString());
                    super.parse((ParametersHeader)serializable);
                    if (debug) {
                        this.dbg_leave("parseQuotedString.parse");
                    }
                    return;
                }
                if (c == '\u0000') break;
                if (c == '\\') {
                    stringBuffer.append(c);
                    stringBuffer.append(this.lexer.getNextChar());
                    continue;
                }
                stringBuffer.append(c);
            } while (true);
            serializable = new ParseException("unexpected EOL", 1);
            throw serializable;
        }
        catch (Throwable throwable) {
            if (debug) {
                this.dbg_leave("parseQuotedString.parse");
            }
            throw throwable;
        }
    }

    protected void parseToken(PVisitedNetworkID pVisitedNetworkID) throws ParseException {
        this.lexer.match(4095);
        pVisitedNetworkID.setVisitedNetworkID(this.lexer.getNextToken());
        super.parse(pVisitedNetworkID);
    }
}

