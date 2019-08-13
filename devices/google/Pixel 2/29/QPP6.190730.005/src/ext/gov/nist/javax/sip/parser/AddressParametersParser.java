/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.parser.AddressParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;
import javax.sip.address.Address;

public class AddressParametersParser
extends ParametersParser {
    protected AddressParametersParser(Lexer lexer) {
        super(lexer);
    }

    protected AddressParametersParser(String string) {
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
    protected void parse(AddressParametersHeader addressParametersHeader) throws ParseException {
        Throwable throwable2222;
        block5 : {
            this.dbg_enter("AddressParametersParser.parse");
            AddressParser addressParser = new AddressParser(this.getLexer());
            addressParametersHeader.setAddress(addressParser.address(false));
            this.lexer.SPorHT();
            char c = this.lexer.lookAhead(0);
            if (this.lexer.hasMoreChars() && c != '\u0000' && c != '\n' && this.lexer.startsId()) {
                super.parseNameValueList(addressParametersHeader);
                break block5;
            }
            super.parse(addressParametersHeader);
        }
        this.dbg_leave("AddressParametersParser.parse");
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (ParseException parseException) {}
            {
                throw parseException;
            }
        }
        this.dbg_leave("AddressParametersParser.parse");
        throw throwable2222;
    }
}

