/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.ims.AddressHeaderIms;
import gov.nist.javax.sip.parser.AddressParser;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.address.Address;

abstract class AddressHeaderParser
extends HeaderParser {
    protected AddressHeaderParser(Lexer lexer) {
        super(lexer);
    }

    protected AddressHeaderParser(String string) {
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
    protected void parse(AddressHeaderIms addressHeaderIms) throws ParseException {
        Throwable throwable2222;
        this.dbg_enter("AddressHeaderParser.parse");
        AddressParser addressParser = new AddressParser(this.getLexer());
        addressHeaderIms.setAddress(addressParser.address(true));
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

