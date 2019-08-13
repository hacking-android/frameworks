/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ContentDisposition;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

public class ContentDispositionParser
extends ParametersParser {
    protected ContentDispositionParser(Lexer lexer) {
        super(lexer);
    }

    public ContentDispositionParser(String string) {
        super(string);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public SIPHeader parse() throws ParseException {
        Throwable throwable2222;
        if (debug) {
            this.dbg_enter("ContentDispositionParser.parse");
        }
        this.headerName(2100);
        ContentDisposition contentDisposition = new ContentDisposition();
        contentDisposition.setHeaderName("Content-Disposition");
        this.lexer.SPorHT();
        this.lexer.match(4095);
        contentDisposition.setDispositionType(this.lexer.getNextToken().getTokenValue());
        this.lexer.SPorHT();
        super.parse(contentDisposition);
        this.lexer.SPorHT();
        this.lexer.match(10);
        if (!debug) return contentDisposition;
        this.dbg_leave("ContentDispositionParser.parse");
        return contentDisposition;
        {
            catch (Throwable throwable2222) {
            }
            catch (ParseException parseException) {}
            {
                throw this.createParseException(parseException.getMessage());
            }
        }
        if (!debug) throw throwable2222;
        this.dbg_leave("ContentDispositionParser.parse");
        throw throwable2222;
    }
}

