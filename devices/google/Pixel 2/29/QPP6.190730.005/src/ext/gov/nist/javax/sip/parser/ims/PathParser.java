/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.Path;
import gov.nist.javax.sip.header.ims.PathList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public class PathParser
extends AddressParametersParser
implements TokenTypes {
    protected PathParser(Lexer lexer) {
        super(lexer);
    }

    public PathParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        char c;
        PathList pathList = new PathList();
        if (debug) {
            this.dbg_enter("PathParser.parse");
        }
        try {
            this.lexer.match(2119);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            do {
                Path path = new Path();
                super.parse(path);
                pathList.add(path);
                this.lexer.SPorHT();
                c = this.lexer.lookAhead(0);
                if (c != ',') break;
                break;
            } while (true);
        }
        catch (Throwable throwable) {
            if (debug) {
                this.dbg_leave("PathParser.parse");
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
                this.dbg_leave("PathParser.parse");
            }
            return pathList;
        }
        throw this.createParseException("unexpected char");
    }
}

