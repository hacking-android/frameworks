/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ContentEncoding;
import gov.nist.javax.sip.header.ContentEncodingList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ContentEncodingParser
extends HeaderParser {
    protected ContentEncodingParser(Lexer lexer) {
        super(lexer);
    }

    public ContentEncodingParser(String string) {
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
            this.dbg_enter("ContentEncodingParser.parse");
        }
        ContentEncodingList contentEncodingList = new ContentEncodingList();
        this.headerName(2083);
        while (this.lexer.lookAhead(0) != '\n') {
            ContentEncoding contentEncoding = new ContentEncoding();
            contentEncoding.setHeaderName("Content-Encoding");
            this.lexer.SPorHT();
            this.lexer.match(4095);
            contentEncoding.setEncoding(this.lexer.getNextToken().getTokenValue());
            this.lexer.SPorHT();
            contentEncodingList.add(contentEncoding);
            while (this.lexer.lookAhead(0) == ',') {
                contentEncoding = new ContentEncoding();
                this.lexer.match(44);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                this.lexer.SPorHT();
                contentEncoding.setEncoding(this.lexer.getNextToken().getTokenValue());
                this.lexer.SPorHT();
                contentEncodingList.add(contentEncoding);
            }
        }
        if (!debug) return contentEncodingList;
        this.dbg_leave("ContentEncodingParser.parse");
        return contentEncodingList;
        {
            catch (Throwable throwable2222) {
            }
            catch (ParseException parseException) {}
            {
                throw this.createParseException(parseException.getMessage());
            }
        }
        if (!debug) throw throwable2222;
        this.dbg_leave("ContentEncodingParser.parse");
        throw throwable2222;
    }
}

