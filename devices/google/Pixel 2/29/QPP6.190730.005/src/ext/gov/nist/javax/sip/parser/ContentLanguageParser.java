/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ContentLanguage;
import gov.nist.javax.sip.header.ContentLanguageList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ContentLanguageParser
extends HeaderParser {
    protected ContentLanguageParser(Lexer lexer) {
        super(lexer);
    }

    public ContentLanguageParser(String string) {
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
            this.dbg_enter("ContentLanguageParser.parse");
        }
        ContentLanguageList contentLanguageList = new ContentLanguageList();
        this.headerName(2075);
        while (this.lexer.lookAhead(0) != '\n') {
            this.lexer.SPorHT();
            this.lexer.match(4095);
            Object object = this.lexer.getNextToken();
            Object object2 = new ContentLanguage(((Token)object).getTokenValue());
            this.lexer.SPorHT();
            contentLanguageList.add(object2);
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                this.lexer.SPorHT();
                object2 = this.lexer.getNextToken();
                object = new ContentLanguage(((Token)object2).getTokenValue());
                this.lexer.SPorHT();
                contentLanguageList.add(object);
            }
        }
        if (!debug) return contentLanguageList;
        this.dbg_leave("ContentLanguageParser.parse");
        return contentLanguageList;
        {
            catch (Throwable throwable2222) {
            }
            catch (ParseException parseException) {}
            {
                throw this.createParseException(parseException.getMessage());
            }
        }
        if (!debug) throw throwable2222;
        this.dbg_leave("ContentLanguageParser.parse");
        throw throwable2222;
    }
}

