/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Server;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ServerParser
extends HeaderParser {
    protected ServerParser(Lexer lexer) {
        super(lexer);
    }

    public ServerParser(String string) {
        super(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("ServerParser.parse");
        }
        Server server = new Server();
        try {
            this.headerName(2066);
            if (this.lexer.lookAhead(0) == '\n') throw this.createParseException("empty header");
            while (this.lexer.lookAhead(0) != '\n') {
                String string;
                CharSequence charSequence;
                if (this.lexer.lookAhead(0) == '\u0000') return server;
                if (this.lexer.lookAhead(0) == '(') {
                    string = this.lexer.comment();
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append('(');
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append(')');
                    server.addProductToken(((StringBuilder)charSequence).toString());
                    continue;
                }
                int n = 0;
                try {
                    int n2;
                    n = n2 = this.lexer.markInputPosition();
                    string = this.lexer.getString('/');
                    charSequence = string;
                    n = n2;
                    if (string.charAt(string.length() - 1) == '\n') {
                        n = n2;
                        charSequence = string.trim();
                    }
                    n = n2;
                    server.addProductToken((String)charSequence);
                }
                catch (ParseException parseException) {
                    this.lexer.rewindInputPosition(n);
                    server.addProductToken(this.lexer.getRest().trim());
                    return server;
                }
            }
            return server;
        }
        finally {
            if (debug) {
                this.dbg_leave("ServerParser.parse");
            }
        }
    }
}

