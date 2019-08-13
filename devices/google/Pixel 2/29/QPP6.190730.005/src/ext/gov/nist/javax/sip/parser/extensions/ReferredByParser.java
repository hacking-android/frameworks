/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.extensions;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.ReferredBy;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.io.PrintStream;
import java.text.ParseException;

public class ReferredByParser
extends AddressParametersParser {
    protected ReferredByParser(Lexer lexer) {
        super(lexer);
    }

    public ReferredByParser(String string) {
        super(string);
    }

    public static void main(String[] arrstring) throws ParseException {
        arrstring = new String[]{"Referred-By: <sip:dave@denver.example.org?Replaces=12345%40192.168.118.3%3Bto-tag%3D12345%3Bfrom-tag%3D5FFE-3994>\n", "Referred-By: <sip:+1-650-555-2222@ss1.wcom.com;user=phone>;tag=5617\n", "Referred-By: T. A. Watson <sip:watson@bell-telephone.com>\n", "Referred-By: LittleGuy <sip:UserB@there.com>\n", "Referred-By: sip:mranga@120.6.55.9\n", "Referred-By: sip:mranga@129.6.55.9 ; tag=696928473514.129.6.55.9\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            ReferredBy referredBy = (ReferredBy)new ReferredByParser(arrstring[i]).parse();
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("encoded = ");
            stringBuilder.append(referredBy.encode());
            printStream.println(stringBuilder.toString());
        }
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.headerName(2132);
        ReferredBy referredBy = new ReferredBy();
        super.parse(referredBy);
        this.lexer.match(10);
        return referredBy;
    }
}

